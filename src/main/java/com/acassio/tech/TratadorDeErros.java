package com.acassio.tech;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException erro, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, String> campos = new LinkedHashMap<>();
        erro.getBindingResult().getFieldErrors()
                .forEach(campo -> campos.put(campo.getField(), campo.getDefaultMessage()));
        ProblemDetail problema = problema(HttpStatus.BAD_REQUEST,
                "Um ou mais campos estão inválidos.");
        problema.setProperty("campos", campos);
        return handleExceptionInternal(erro, problema, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail parametroInvalido(ConstraintViolationException erro) {
        Map<String, String> campos = new LinkedHashMap<>();
        erro.getConstraintViolations().forEach(violacao ->
                campos.put(violacao.getPropertyPath().toString(), violacao.getMessage()));
        ProblemDetail problema = problema(HttpStatus.BAD_REQUEST, "Parâmetros inválidos.");
        problema.setProperty("campos", campos);
        return problema;
    }

    @ExceptionHandler(RecursoNaoEncontrado.class)
    public ProblemDetail naoEncontrado(RecursoNaoEncontrado erro) {
        return problema(HttpStatus.NOT_FOUND, erro.getMessage());
    }

    @ExceptionHandler(SlugDuplicado.class)
    public ProblemDetail slugDuplicado(SlugDuplicado erro) {
        ProblemDetail problema = problema(HttpStatus.CONFLICT, erro.getMessage());
        problema.setProperty("campos", Map.of("slug", "este slug já está cadastrado"));
        return problema;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail dadoInvalido(IllegalArgumentException erro) {
        return problema(HttpStatus.BAD_REQUEST, erro.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflitoNoBanco(DataIntegrityViolationException erro) {
        return problema(HttpStatus.CONFLICT, "Os dados conflitam com um registro existente.");
    }

    // Também padroniza erros do Spring, como JSON malformado e tipo de parâmetro errado.
    @Override
    protected ResponseEntity<Object> createResponseEntity(
            Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body instanceof ProblemDetail problema
                && (problema.getProperties() == null || !problema.getProperties().containsKey("campos"))) {
            problema.setProperty("campos", Map.of());
        }
        return super.createResponseEntity(body, headers, status, request);
    }

    private ProblemDetail problema(HttpStatus status, String detalhe) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(status, detalhe);
        problema.setProperty("campos", Map.of());
        return problema;
    }
}
