package com.acassio.tech.controller;

import com.acassio.tech.dto.VagaEntrada;
import com.acassio.tech.dto.VagaResposta;
import com.acassio.tech.service.VagaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    private final VagaService service;

    public VagaController(VagaService service) {
        this.service = service;
    }

    @GetMapping
    public List<VagaResposta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public VagaResposta porId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<VagaResposta> criar(@Valid @RequestBody VagaEntrada entrada) {
        VagaResposta salva = service.criar(entrada);
        return ResponseEntity.created(URI.create("/vagas/" + salva.id())).body(salva);
    }

    @PutMapping("/{id}")
    public VagaResposta trocar(@PathVariable Long id,
                                  @Valid @RequestBody VagaEntrada entrada) {
        return service.trocar(id, entrada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
