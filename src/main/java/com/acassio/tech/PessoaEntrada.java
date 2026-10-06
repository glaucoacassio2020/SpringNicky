package com.acassio.tech;

import jakarta.validation.constraints.*;

public record PessoaEntrada(
        @NotBlank(message = "o nome é obrigatório")
        @Size(max = 120, message = "nome deve ter no máximo 120 caracteres")
        String nome,

        @NotBlank(message = "o e-mail é obrigatório")
        @Size(max = 254, message = "email deve ter no máximo 254 caracteres")
        @Email(message = "e-mail inválido")
        String email,

        @NotBlank(message = "a área é obrigatória")
        @Size(max = 40, message = "a área deve ter no máximo 40 caracteres")
        @Pattern(regexp = "Front-end|Back-end|Dados|Mobile", message = "área deve ser Front-end, Back-end, Dados ou Mobile")
        String area,

        @NotBlank(message = "a senioridade é obrigatória")
        @Size(max = 20, message = "a senioridade deve ter no máximo 20 caracteres")
        @SenioridadeValida
        String senioridade
) { }
