package com.projeto.model;

import java.time.LocalDate;

public class Edital {
    
    private String titulo;
    private String numero;
    private String ano;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private LocalDate dataInicioAvaliacao;
    private LocalDate dataFimAvaliacao;

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }
    public void setDataInicioSubmissao(LocalDate dataInicioSubmissao) {
        this.dataInicioSubmissao = dataInicioSubmissao;
    }
    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }
    public void setDataFimSubmissao(LocalDate dataFimSubmissao) {
        this.dataFimSubmissao = dataFimSubmissao;
    }
    public LocalDate getDataInicioAvaliacao() {
        return dataInicioAvaliacao;
    }
    public void setDataInicioAvaliacao(LocalDate dataInicioAvaliacao) {
        this.dataInicioAvaliacao = dataInicioAvaliacao;
    }
    public LocalDate getDataFimAvaliacao() {
        return dataFimAvaliacao;
    }
    public void setDataFimAvaliacao(LocalDate dataFimAvaliacao) {
        this.dataFimAvaliacao = dataFimAvaliacao;
    }

}