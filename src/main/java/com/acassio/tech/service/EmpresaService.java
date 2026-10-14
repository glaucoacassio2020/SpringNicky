package com.acassio.tech.service;

import com.acassio.tech.model.Empresa;
import com.acassio.tech.dto.EmpresaEntrada;
import com.acassio.tech.repository.EmpresaRepository;
import com.acassio.tech.dto.EmpresaResposta;
import com.acassio.tech.exception.RecursoNaoEncontrado;
import com.acassio.tech.exception.SlugDuplicado;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;
    private long proximoId = 6;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<EmpresaResposta> listar() {
        return repository.todas().stream().map(EmpresaService::paraResposta).toList();
    }

    public EmpresaResposta buscarPorSlug(String slug) {
        return paraResposta(encontrar(slug));
    }

    public EmpresaResposta criar(EmpresaEntrada entrada) {
        if (repository.porSlug(entrada.slug()).isPresent()) {
            throw new SlugDuplicado(entrada.slug());
        }
        Empresa registro = new Empresa(entrada.nome(), entrada.slug(), entrada.site(), entrada.descricao());
        registro.setId(proximoId++);
        return paraResposta(repository.salvar(registro));
    }

    public EmpresaResposta trocar(String slug, EmpresaEntrada entrada) {
        Empresa registro = encontrar(slug);
        if (!slug.equals(entrada.slug())) {
            throw new IllegalArgumentException("o slug do corpo deve ser igual ao da URL");
        }
        registro.setNome(entrada.nome());
        registro.setSlug(entrada.slug());
        registro.setSite(entrada.site());
        registro.setDescricao(entrada.descricao());
        return paraResposta(repository.trocar(slug, registro)
                .orElseThrow(() -> new RecursoNaoEncontrado("Empresa não encontrada: " + slug)));
    }

    public void apagar(String slug) {
        if (!repository.remover(slug)) {
            throw new RecursoNaoEncontrado("Empresa não encontrada: " + slug);
        }
    }

    private Empresa encontrar(String slug) {
        return repository.porSlug(slug)
                .orElseThrow(() -> new RecursoNaoEncontrado("Empresa não encontrada: " + slug));
    }

    public static EmpresaResposta paraResposta(Empresa registro) {
        return new EmpresaResposta(registro.getId(), registro.getNome(), registro.getSlug(), registro.getSite(), registro.getDescricao());
    }
}
