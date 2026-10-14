package com.acassio.tech.repository;

import com.acassio.tech.model.Empresa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaRepository {

    private final List<Empresa> empresas = new ArrayList<>(List.of(
            new Empresa(1L, "Aurora Tech", "aurora-tech", "https://aurora.tech", "Produto de gestão para clínicas. Time pequeno, remoto desde 2020."),
            new Empresa(2L, "Nuvem Rosa", "nuvem-rosa", "https://nuvemrosa.com.br", "Consultoria de dados para o varejo do Nordeste."),
            new Empresa(3L, "Mareh Digital", "mareh-digital", "https://mareh.digital", "Agência de produtos digitais em Olinda."),
            new Empresa(4L, "Coral Labs", "coral-labs", "https://corallabs.io", "Plataforma de educação corporativa."),
            new Empresa(5L, "Tucano Systems", "tucano-systems", "https://tucano.sys.br", "Integrações e APIs para logística.")
    ));

    public List<Empresa> todas() {
        return List.copyOf(empresas);
    }

    public Optional<Empresa> porSlug(String slug) {
        return empresas.stream().filter(registro -> registro.getSlug().equals(slug)).findFirst();
    }

    public Empresa salvar(Empresa nova) {
        empresas.add(nova);
        return nova;
    }

    public Optional<Empresa> trocar(String slug, Empresa nova) {
        for (int i = 0; i < empresas.size(); i++) {
            if (empresas.get(i).getSlug().equals(slug)) {
                empresas.set(i, nova);
                return Optional.of(nova);
            }
        }
        return Optional.empty();
    }

    public boolean remover(String slug) {
        return empresas.removeIf(registro -> registro.getSlug().equals(slug));
    }
}
