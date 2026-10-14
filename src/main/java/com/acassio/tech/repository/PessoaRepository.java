package com.acassio.tech.repository;

import com.acassio.tech.model.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepository {

    private final List<Pessoa> pessoas = new ArrayList<>(List.of(
            new Pessoa(1L, "Ana Nogueira", "ana@exemplo.com", "Front-end", "Júnior"),
            new Pessoa(2L, "Bruno Sales", "bruno@exemplo.com", "Back-end", "Júnior"),
            new Pessoa(3L, "Carla Menezes", "carla@exemplo.com", "Dados", "Estágio"),
            new Pessoa(4L, "Diego Tavares", "diego@exemplo.com", "Front-end", "Pleno"),
            new Pessoa(5L, "Elisa Prado", "elisa@exemplo.com", "Mobile", "Júnior"),
            new Pessoa(6L, "Fábio Vasconcelos", "fabio@exemplo.com", "Back-end", "Pleno")
    ));

    public List<Pessoa> todas() {
        return List.copyOf(pessoas);
    }

    public Optional<Pessoa> porId(Long id) {
        return pessoas.stream().filter(registro -> registro.getId().equals(id)).findFirst();
    }

    public Pessoa salvar(Pessoa nova) {
        pessoas.add(nova);
        return nova;
    }

    public Optional<Pessoa> trocar(Long id, Pessoa nova) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getId().equals(id)) {
                pessoas.set(i, nova);
                return Optional.of(nova);
            }
        }
        return Optional.empty();
    }

    public boolean remover(Long id) {
        return pessoas.removeIf(registro -> registro.getId().equals(id));
    }
}
