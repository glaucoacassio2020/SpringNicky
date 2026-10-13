package com.acassio.tech;

public class SlugDuplicado extends RuntimeException {
    public SlugDuplicado(String slug) {
        super("Já existe uma empresa com o slug " + slug);
    }
}
