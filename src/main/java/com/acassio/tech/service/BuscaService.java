package com.acassio.tech;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscaService {

    // O repository da parte 1, não o service dela — igual à aula 02.
    private final VagaRepository repository;

    public BuscaService(VagaRepository repository) {
        this.repository = repository;
    }

    // Boolean, e não boolean: só o objeto pode ser null, e null é
    // exatamente como "o filtro não foi enviado" chega aqui.
    public List<VagaResposta> buscar(String area, String senioridade,
                             Boolean aceitaIniciante) {
        return repository.findAll().stream()
                .filter(v -> area == null
                        || v.getArea().equalsIgnoreCase(area))
                .filter(v -> senioridade == null
                        || v.getSenioridade().equalsIgnoreCase(senioridade))
                .filter(v -> aceitaIniciante == null
                        || v.isAceitaIniciante() == aceitaIniciante)
                .map(VagaService::paraResposta)
                .toList();
    }
}
