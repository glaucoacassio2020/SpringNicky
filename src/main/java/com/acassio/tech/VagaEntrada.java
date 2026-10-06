package com.acassio.tech;

import jakarta.validation.constraints.*;

public record VagaEntrada(
        @NotBlank(message = "o título é obrigatório")
        @Size(max = 120, message = "o título deve ter no máximo 120 caracteres")
        String titulo,

        @Size(max = 2000, message = "a descrição deve ter no máximo 2000 caracteres")
        String descricao,

        @NotBlank(message = "a área é obrigatória")
        @Size(max = 40, message = "a área deve ter no máximo 40 caracteres")
        @Pattern(regexp = "Front-end|Back-end|Dados|Mobile", message = "área deve ser Front-end, Back-end, Dados ou Mobile")
        String area,

        @NotBlank(message = "a senioridade é obrigatória")
        @Size(max = 20, message = "a senioridade deve ter no máximo 20 caracteres")
        @SenioridadeValida
        String senioridade,

        @Size(max = 120, message = "local deve ter no máximo 120 caracteres")
        String local,

        @NotNull(message = "informe se aceita iniciante")
        Boolean aceitaIniciante,

        @NotBlank(message = "empresaSlug é obrigatório")
        @Size(max = 100, message = "empresaSlug deve ter no máximo 100 caracteres")
        @Pattern(regexp = "[a-z0-9-]+", message = "slug só aceita minúsculas, números e hífen")
        String empresaSlug
) { }
