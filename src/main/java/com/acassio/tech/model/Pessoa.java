package com.acassio.tech.model;


public class Pessoa {

    private Long id;

    private String nome;

    private String email;

    private String area;

    private String senioridade;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, String email, String area, String senioridade) {
        this(nome, email, area, senioridade);
        this.id = id;
    }

    public Pessoa(String nome, String email, String area, String senioridade) {
        this.nome = nome;
        this.email = email;
        this.area = area;
        this.senioridade = senioridade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
