package com.acassio.tech;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<PessoaResposta> listar() {
        return repository.findAll().stream().map(PessoaService::paraResposta).toList();
    }

    public PessoaResposta buscarPorId(Long id) {
        return paraResposta(encontrar(id));
    }

    public PessoaResposta criar(PessoaEntrada entrada) {
        Pessoa registro = new Pessoa(entrada.nome(), entrada.email(), entrada.area(), entrada.senioridade());
        return paraResposta(repository.save(registro));
    }

    public PessoaResposta trocar(Long id, PessoaEntrada entrada) {
        Pessoa registro = encontrar(id);
        registro.setNome(entrada.nome());
        registro.setEmail(entrada.email());
        registro.setArea(entrada.area());
        registro.setSenioridade(entrada.senioridade());
        return paraResposta(repository.save(registro));
    }

    public void apagar(Long id) {
        repository.delete(encontrar(id));
    }

    private Pessoa encontrar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontrado("Pessoa não encontrada: " + id));
    }

    public static PessoaResposta paraResposta(Pessoa registro) {
        return new PessoaResposta(registro.getId(), registro.getNome(), registro.getEmail(), registro.getArea(), registro.getSenioridade());
    }
}
