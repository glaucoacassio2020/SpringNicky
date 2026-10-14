package com.acassio.tech.service;

import com.acassio.tech.model.Vaga;
import com.acassio.tech.dto.VagaEntrada;
import com.acassio.tech.repository.VagaRepository;
import com.acassio.tech.dto.VagaResposta;
import com.acassio.tech.exception.RecursoNaoEncontrado;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VagaService {

    private final VagaRepository repository;
    private long proximoId = 13;

    public VagaService(VagaRepository repository) {
        this.repository = repository;
    }

    public List<VagaResposta> listar() {
        return repository.todas().stream().map(VagaService::paraResposta).toList();
    }

    public VagaResposta buscarPorId(Long id) {
        return paraResposta(encontrar(id));
    }

    public VagaResposta destaque() {
        return repository.todas().stream()
                .findFirst()
                .map(VagaService::paraResposta)
                .orElseThrow(() -> new RecursoNaoEncontrado("Não há vagas para destacar."));
    }

    public VagaResposta criar(VagaEntrada entrada) {
        Vaga registro = new Vaga(entrada.titulo(), entrada.descricao(), entrada.area(), entrada.senioridade(), entrada.local(), entrada.aceitaIniciante(), entrada.empresaSlug());
        registro.setId(proximoId++);
        return paraResposta(repository.salvar(registro));
    }

    public VagaResposta trocar(Long id, VagaEntrada entrada) {
        Vaga registro = encontrar(id);
        registro.setTitulo(entrada.titulo());
        registro.setDescricao(entrada.descricao());
        registro.setArea(entrada.area());
        registro.setSenioridade(entrada.senioridade());
        registro.setLocal(entrada.local());
        registro.setAceitaIniciante(entrada.aceitaIniciante());
        registro.setEmpresaSlug(entrada.empresaSlug());
        return paraResposta(repository.trocar(id, registro)
                .orElseThrow(() -> new RecursoNaoEncontrado("Vaga não encontrada: " + id)));
    }

    public void apagar(Long id) {
        if (!repository.remover(id)) {
            throw new RecursoNaoEncontrado("Vaga não encontrada: " + id);
        }
    }

    private Vaga encontrar(Long id) {
        return repository.porId(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Vaga não encontrada: " + id));
    }

    public static VagaResposta paraResposta(Vaga registro) {
        return new VagaResposta(registro.getId(), registro.getTitulo(), registro.getArea(), registro.getSenioridade(), registro.getLocal(), registro.isAceitaIniciante(), registro.getEmpresaSlug());
    }
}
