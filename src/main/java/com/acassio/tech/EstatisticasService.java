package com.acassio.tech;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstatisticasService {

    // Recebe o REPOSITORY da parte 1, não o service dela.
    private final VagaRepository vagas;

    public EstatisticasService(VagaRepository vagas) {
        this.vagas = vagas;
    }

    public Map<String, Object> resumo() {
        List<Vaga> todas = vagas.findAll();

        return Map.of(
                "totalDeVagas", todas.size(),

                "aceitamIniciante", todas.stream()
                        .filter(Vaga::isAceitaIniciante)
                        .count(),

                "porArea", todas.stream()
                        .collect(Collectors.groupingBy(Vaga::getArea, Collectors.counting())),

                "porSenioridade", todas.stream()
                        .collect(Collectors.groupingBy(Vaga::getSenioridade, Collectors.counting()))
        );
    }
}
