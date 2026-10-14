package com.acassio.tech.service;

import com.acassio.tech.model.Pessoa;
import com.acassio.tech.dto.PessoaEntrada;
import com.acassio.tech.repository.PessoaRepository;
import com.acassio.tech.dto.PessoaResposta;
import com.acassio.tech.exception.RecursoNaoEncontrado;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository repository;
    private long proximoId = 7;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<PessoaResposta> listar() {
        return repository.todas().stream().map(PessoaService::paraResposta).toList();
    }

    public PessoaResposta buscarPorId(Long id) {
        return paraResposta(encontrar(id));
    }

    public PessoaResposta criar(PessoaEntrada entrada) {
        Pessoa registro = new Pessoa(entrada.nome(), entrada.email(), entrada.area(), entrada.senioridade());
        registro.setId(proximoId++);
        return paraResposta(repository.salvar(registro));
    }

    public PessoaResposta trocar(Long id, PessoaEntrada entrada) {
        Pessoa registro = encontrar(id);
        registro.setNome(entrada.nome());
        registro.setEmail(entrada.email());
        registro.setArea(entrada.area());
        registro.setSenioridade(entrada.senioridade());
        return paraResposta(repository.trocar(id, registro)
                .orElseThrow(() -> new RecursoNaoEncontrado("Pessoa não encontrada: " + id)));
    }

    public void apagar(Long id) {
        if (!repository.remover(id)) {
            throw new RecursoNaoEncontrado("Pessoa não encontrada: " + id);
        }
    }

    private Pessoa encontrar(Long id) {
        return repository.porId(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Pessoa não encontrada: " + id));
    }

    public static PessoaResposta paraResposta(Pessoa registro) {
        return new PessoaResposta(registro.getId(), registro.getNome(), registro.getEmail(), registro.getArea(), registro.getSenioridade());
    }
}
