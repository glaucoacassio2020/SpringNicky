package com.acassio.tech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApiTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void listaDadosEmMemoriaSemExporDescricaoOuSenha() throws Exception {
        mvc.perform(get("/vagas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[0].descricao").doesNotExist());
        mvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].senha").doesNotExist());
        mvc.perform(get("/empresas")).andExpect(jsonPath("$", hasSize(5)));
        mvc.perform(get("/estatisticas"))
                .andExpect(jsonPath("$.totalDeVagas").value(12))
                .andExpect(jsonPath("$.aceitamIniciante").value(7));
    }

    @Test
    void recusaCorpoVazioNoPostENoPut() throws Exception {
        for (String rota : new String[]{"/vagas", "/empresas", "/pessoas"}) {
            mvc.perform(post(rota).contentType("application/json").content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                    .andExpect(jsonPath("$.campos").isNotEmpty());
            String chave = rota.equals("/empresas") ? "aurora-tech" : "1";
            mvc.perform(put(rota + "/" + chave).contentType("application/json").content("{}"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    void validaEmailESenioridade() throws Exception {
        for (String email : new String[]{"maria@", "@exemplo.com", "maria exemplo.com"}) {
            mvc.perform(post("/pessoas").contentType("application/json").content("""
                    {"nome":"Maria","email":"%s","area":"Dados","senioridade":"Júnior"}
                    """.formatted(email)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.campos.email").exists());
        }
        mvc.perform(post("/pessoas").contentType("application/json").content("""
                {"nome":"Maria","email":"maria@exemplo.com","area":"Dados","senioridade":"Mestre"}
                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.senioridade").exists());
    }

    @Test
    void validaSlugSiteEDuplicidade() throws Exception {
        mvc.perform(post("/empresas").contentType("application/json").content("""
                {"nome":"Teste","slug":"Nome Inválido","site":"nao-e-url"}
                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.slug").exists())
                .andExpect(jsonPath("$.campos.site").exists());
        mvc.perform(post("/empresas").contentType("application/json").content("""
                {"nome":"Teste","slug":"aurora-tech"}
                """)).andExpect(status().isConflict());
    }

    @Test
    void crudPessoaGeraIdEPreservaIdNaAtualizacao() throws Exception {
        String entrada = """
                {"nome":"Teste","email":"teste@exemplo.com","area":"Dados","senioridade":"Estágio"}
                """;
        String endereco = mvc.perform(post("/pessoas").contentType("application/json").content(entrada))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", startsWith("/pessoas/")))
                .andReturn().getResponse().getHeader("Location");
        mvc.perform(get(endereco)).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Teste"));
        for (int i = 0; i < 2; i++) {
            mvc.perform(put(endereco).contentType("application/json").content(entrada))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(Integer.parseInt(endereco.substring(9))));
        }
        mvc.perform(delete(endereco)).andExpect(status().isNoContent());
        mvc.perform(delete(endereco)).andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"));
    }

    @Test
    void novaVagaApareceNaBuscaENasEstatisticas() throws Exception {
        String endereco = mvc.perform(post("/vagas").contentType("application/json").content("""
                {"titulo":"Teste em memória","descricao":"Descrição guardada em memória",
                 "area":"Dados","senioridade":"Sênior","aceitaIniciante":false,"empresaSlug":"aurora-tech"}
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").doesNotExist())
                .andReturn().getResponse().getHeader("Location");
        mvc.perform(get("/vagas/busca").param("area", "Dados").param("senioridade", "Sênior"))
                .andExpect(jsonPath("$[0].titulo").value("Teste em memória"));
        mvc.perform(get("/estatisticas")).andExpect(jsonPath("$.totalDeVagas").value(13));
        mvc.perform(delete(endereco)).andExpect(status().isNoContent());
        mvc.perform(get(endereco)).andExpect(status().isNotFound());
    }

    @Test
    void buscaValidaParametrosEMantemListaVazia() throws Exception {
        mvc.perform(get("/vagas/busca").param("area", "Inexistente"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
        mvc.perform(get("/vagas/busca").param("area", "a".repeat(300)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"))
                .andExpect(jsonPath("$.campos").isNotEmpty());
        mvc.perform(get("/vagas/busca").param("aceitaIniciante", "talvez"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos").exists());
    }

    @Test
    void errosDeHttpUsamProblemDetails() throws Exception {
        mvc.perform(post("/pessoas").contentType("application/json").content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/problem+json"));
        mvc.perform(get("/vagas/999999")).andExpect(status().isNotFound());
        mvc.perform(get("/pessoas/999999")).andExpect(status().isNotFound());
        mvc.perform(get("/empresas/inexistente")).andExpect(status().isNotFound());
        mvc.perform(get("/vagas/abc")).andExpect(status().isBadRequest());
    }
}
