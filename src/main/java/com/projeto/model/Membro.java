package com.projeto.model;

public class Membro {
    private String nome;
    private String cpf;
    private TipoMembro tipo;
    private int cargaHoraria;

    public Membro(String nome, String cpf, TipoMembro tipo, int cargaHoraria) {
        this.nome = nome;
        this.cpf = cpf;
        this.tipo = tipo;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public TipoMembro getTipo() { return tipo; }
    public void setTipo(TipoMembro tipo) { this.tipo = tipo; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
}