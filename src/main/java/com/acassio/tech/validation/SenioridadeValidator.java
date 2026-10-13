package com.acassio.tech.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class SenioridadeValidator implements ConstraintValidator<SenioridadeValida, String> {
    private static final Set<String> VALIDAS = Set.of("Estágio", "Júnior", "Pleno", "Sênior");

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null || VALIDAS.contains(valor);
    }
}
