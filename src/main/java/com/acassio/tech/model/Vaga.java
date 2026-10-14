package com.acassio.tech.model;


public class Vaga {

    private Long id;

    private String titulo;

    private String descricao;

    private String area;

    private String senioridade;

    private String local;

    private boolean aceitaIniciante;

    private String empresaSlug;

    public Vaga() {
    }

    public Vaga(Long id, String titulo, String descricao, String area, String senioridade, String local, boolean aceitaIniciante, String empresaSlug) {
        this(titulo, descricao, area, senioridade, local, aceitaIniciante, empresaSlug);
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
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
