package com.acassio.tech;

// Senha nunca deve fazer parte da resposta da API.
public record PessoaResposta(
        Long id,
        String nome,
        String email,
        String area,
        String senioridade
) { }
