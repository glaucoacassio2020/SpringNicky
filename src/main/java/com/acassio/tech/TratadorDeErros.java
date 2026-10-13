package com.acassio.tech;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    // Trata os campos inválidos enviados no corpo do POST ou PUT.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail camposInvalidos(MethodArgumentNotValidException erro) {
        Map<String, String> campos = new LinkedHashMap<>();
        for (FieldError campo : erro.getBindingResult().getFieldErrors()) {
            campos.put(campo.getField(), campo.getDefaultMessage());
        }
        ProblemDetail problema = problema(HttpStatus.BAD_REQUEST,
                "Um ou mais campos estão inválidos.");
        problema.setProperty("campos", campos);
        return problema;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail parametroInvalido(ConstraintViolationException erro) {
        Map<String, String> campos = new LinkedHashMap<>();
        for (var violacao : erro.getConstraintViolations()) {
            campos.put(violacao.getPropertyPath().toString(), violacao.getMessage());
        }
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail jsonInvalido() {
        return problema(HttpStatus.BAD_REQUEST, "O corpo da requisição deve conter um JSON válido.");
    }

    // Exemplo: /pessoas/abc, quando o id precisa ser um número.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipoInvalido(MethodArgumentTypeMismatchException erro) {
        return problema(HttpStatus.BAD_REQUEST, "Valor inválido para o parâmetro " + erro.getName());
    }

    // Monta a resposta usada pelos métodos acima.
    private ProblemDetail problema(HttpStatus status, String detalhe) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(status, detalhe);
        problema.setProperty("campos", Map.of());
        return problema;
    }
}
