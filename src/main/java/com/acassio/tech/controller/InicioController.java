package com.acassio.tech;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {
    private final VagaService service;

    public InicioController(VagaService service) {
        this.service = service;
    }

    @GetMapping("/boas-vindas")
    public String boasVindas() {
        return "Bem-vindo à API Leque de Vagas!";
    }

    @GetMapping("/destaque")
    public VagaResposta destaque() {
        return service.destaque();
    }
}
