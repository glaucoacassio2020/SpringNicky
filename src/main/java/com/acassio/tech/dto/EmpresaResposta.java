package com.acassio.tech.dto;

public record EmpresaResposta(
        Long id,
        String nome,
        String slug,
        String site,
        String descricao
) { }
