package com.acassio.tech;

public class RecursoNaoEncontrado extends RuntimeException {
    public RecursoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
