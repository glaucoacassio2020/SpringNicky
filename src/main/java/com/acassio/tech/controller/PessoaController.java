package com.acassio.tech.controller;

import com.acassio.tech.dto.PessoaEntrada;
import com.acassio.tech.dto.PessoaResposta;
import com.acassio.tech.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public List<PessoaResposta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PessoaResposta porId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PessoaResposta> criar(@Valid @RequestBody PessoaEntrada entrada) {
        PessoaResposta salva = service.criar(entrada);
        return ResponseEntity.created(URI.create("/pessoas/" + salva.id())).body(salva);
    }

    @PutMapping("/{id}")
    public PessoaResposta trocar(@PathVariable Long id,
                                  @Valid @RequestBody PessoaEntrada entrada) {
        return service.trocar(id, entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
