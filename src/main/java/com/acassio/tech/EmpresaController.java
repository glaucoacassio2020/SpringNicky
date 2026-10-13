package com.acassio.tech;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmpresaResposta> listar() {
        return service.listar();
    }

    @GetMapping("/{slug}")
    public EmpresaResposta porSlug(@PathVariable String slug) {
        return service.buscarPorSlug(slug);
    }

    @PostMapping
    public ResponseEntity<EmpresaResposta> criar(@Valid @RequestBody EmpresaEntrada entrada) {
        EmpresaResposta salva = service.criar(entrada);
        return ResponseEntity.created(URI.create("/empresas/" + salva.slug())).body(salva);
    }

    @PutMapping("/{slug}")
    public EmpresaResposta trocar(@PathVariable String slug,
                                  @Valid @RequestBody EmpresaEntrada entrada) {
        return service.trocar(slug, entrada);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> apagar(@PathVariable String slug) {
        service.apagar(slug);
        return ResponseEntity.noContent().build();
    }
}
