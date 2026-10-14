package com.acassio.tech.repository;

import com.acassio.tech.model.Vaga;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VagaRepository {

    private final List<Vaga> vagas = new ArrayList<>(List.of(
            new Vaga(1L, "Pessoa Desenvolvedora Front-end Júnior", "Telas do produto em React e Next.js, pareando com gente mais experiente.", "Front-end", "Júnior", "Remoto", true, "aurora-tech"),
            new Vaga(2L, "Estágio em Front-end", "Primeiro contato com HTML, CSS e JavaScript num time de agência.", "Front-end", "Estágio", "Presencial · Olinda", true, "mareh-digital"),
            new Vaga(3L, "Pessoa Desenvolvedora Front-end Pleno", "Dono de features inteiras, do desenho à publicação.", "Front-end", "Pleno", "Remoto", false, "nuvem-rosa"),
            new Vaga(4L, "Pessoa Desenvolvedora React Júnior", "Interface da plataforma de cursos, com foco em acessibilidade.", "Front-end", "Júnior", "Híbrido · Recife", true, "coral-labs"),
            new Vaga(5L, "Pessoa Desenvolvedora Java Júnior", "APIs REST com Spring Boot, integrando transportadoras.", "Back-end", "Júnior", "Remoto", true, "tucano-systems"),
            new Vaga(6L, "Pessoa Desenvolvedora Back-end Pleno", "Serviços de agendamento e faturamento, com fila e cache.", "Back-end", "Pleno", "Remoto", false, "aurora-tech"),
            new Vaga(7L, "Pessoa Desenvolvedora Node Pleno", "Serviço de trilhas e progresso, em TypeScript.", "Back-end", "Pleno", "Híbrido · Recife", false, "coral-labs"),
            new Vaga(8L, "Analista de Dados Júnior", "SQL, planilhas e construção de painéis para clientes do varejo.", "Dados", "Júnior", "Híbrido · Recife", true, "nuvem-rosa"),
            new Vaga(9L, "Estágio em Dados", "Limpeza de base e primeiros painéis, com acompanhamento semanal.", "Dados", "Estágio", "Remoto", true, "nuvem-rosa"),
            new Vaga(10L, "Pessoa Engenheira de Dados Pleno", "Pipelines de rastreamento de carga, em Python e SQL.", "Dados", "Pleno", "Remoto", false, "tucano-systems"),
            new Vaga(11L, "Pessoa Desenvolvedora Mobile Júnior", "Aplicativos em React Native para clientes da agência.", "Mobile", "Júnior", "Remoto", true, "mareh-digital"),
            new Vaga(12L, "Pessoa Desenvolvedora Android Júnior", "Aplicativo de agenda para as clínicas parceiras.", "Mobile", "Júnior", "Presencial · Recife", false, "aurora-tech")
    ));

    public List<Vaga> todas() {
        return List.copyOf(vagas);
    }

    public Optional<Vaga> porId(Long id) {
        return vagas.stream().filter(registro -> registro.getId().equals(id)).findFirst();
    }

    public Vaga salvar(Vaga nova) {
        vagas.add(nova);
        return nova;
    }

    public Optional<Vaga> trocar(Long id, Vaga nova) {
        for (int i = 0; i < vagas.size(); i++) {
            if (vagas.get(i).getId().equals(id)) {
                vagas.set(i, nova);
                return Optional.of(nova);
            }
        }
        return Optional.empty();
    }

    public boolean remover(Long id) {
        return vagas.removeIf(registro -> registro.getId().equals(id));
    }
}
