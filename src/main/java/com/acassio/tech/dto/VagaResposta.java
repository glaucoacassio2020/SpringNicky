package com.acassio.tech;

public record VagaResposta(
        Long id,
        String titulo,
        String area,
        String senioridade,
        String local,
        boolean aceitaIniciante,
        String empresaSlug
) { }
