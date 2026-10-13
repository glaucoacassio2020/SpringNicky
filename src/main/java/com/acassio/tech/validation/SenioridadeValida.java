package com.acassio.tech;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenioridadeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface SenioridadeValida {
    String message() default "senioridade deve ser Estágio, Júnior, Pleno ou Sênior";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
