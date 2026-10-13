package com.acassio.tech.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = true, length = 2000)
    private String descricao;

    @Column(nullable = false, length = 40)
    private String area;

    @Column(nullable = false, length = 20)
    private String senioridade;

    @Column(nullable = true, length = 120)
    private String local;

    @Column(nullable = false)
    private boolean aceitaIniciante;

    @Column(nullable = false, length = 100)
    private String empresaSlug;

    public Vaga() {
    }

    public Vaga(String titulo, String descricao, String area, String senioridade, String local, boolean aceitaIniciante, String empresaSlug) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.area = area;
        this.senioridade = senioridade;
        this.local = local;
        this.aceitaIniciante = aceitaIniciante;
        this.empresaSlug = empresaSlug;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSenioridade() {
        return senioridade;
    }

    public void setSenioridade(String senioridade) {
        this.senioridade = senioridade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public boolean isAceitaIniciante() {
        return aceitaIniciante;
    }

    public void setAceitaIniciante(boolean aceitaIniciante) {
        this.aceitaIniciante = aceitaIniciante;
    }

    public String getEmpresaSlug() {
        return empresaSlug;
    }

    public void setEmpresaSlug(String empresaSlug) {
        this.empresaSlug = empresaSlug;
    }
}
