package com.acassio.tech.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record EmpresaEntrada(
        @NotBlank(message = "o nome é obrigatório")
        @Size(max = 120, message = "nome deve ter no máximo 120 caracteres")
        String nome,

        @NotBlank(message = "slug é obrigatório")
        @Size(max = 100, message = "slug deve ter no máximo 100 caracteres")
        @Pattern(regexp = "[a-z0-9-]+", message = "slug só aceita minúsculas, números e hífen")
        String slug,

        @Size(max = 255, message = "site deve ter no máximo 255 caracteres")
        @URL(message = "site deve ser uma URL, por exemplo https://exemplo.com")
        String site,

        @Size(max = 2000, message = "a descrição deve ter no máximo 2000 caracteres")
        String descricao
) { }
