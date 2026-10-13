package com.acassio.tech.controller;

import java.util.ArrayList;
import java.util.List;

import com.acassio.tech.model.Produto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final List<Produto> produtos = new ArrayList<>();

    public ProdutoController() {
        produtos.add(new Produto(1, "Mouse", 120.00));
        produtos.add(new Produto(2, "Monitor", 1900.00));
        produtos.add(new Produto(3, "Teclado", 750.00));
    }

    @GetMapping
    public List<Produto> listar() {
        return produtos;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        produtos.add(produto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable int id) {
        boolean removeu = produtos.removeIf(produto -> produto.getId() == id);
        if (!removeu) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Produto removido com sucesso!");
    }
}
