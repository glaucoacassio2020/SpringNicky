package com.acassio.tech;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/vagas")   // mesmo caminho base do VagaController
public class BuscaController {

    private final BuscaService service;

    public BuscaController(BuscaService service) {
        this.service = service;
    }

    // Busca sem resultado devolve 200 com lista vazia — e isso está
    // CERTO. Você procurou, o resultado é zero. Diferente de pedir
    // um id que não existe, que é 404.
    @GetMapping("/busca")
    public List<VagaResposta> buscar(
            @Size(max = 40, message = "área deve ter no máximo 40 caracteres")
            @RequestParam(required = false) String area,
            @Size(max = 20, message = "senioridade deve ter no máximo 20 caracteres")
            @RequestParam(required = false) String senioridade,
            @RequestParam(required = false) Boolean aceitaIniciante) {
        return service.buscar(area, senioridade, aceitaIniciante);
    }
}
