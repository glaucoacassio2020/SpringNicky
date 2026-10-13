package com.acassio.tech.exception;

public class SlugDuplicado extends RuntimeException {
    public SlugDuplicado(String slug) {
        super("Já existe uma empresa com o slug " + slug);
    }
}
