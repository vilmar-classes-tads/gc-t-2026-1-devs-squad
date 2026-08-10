package com.projeto.model;

public class PlanoTrabalho {
    private String arquivoPlano;
    private Membro membro;

    public PlanoTrabalho(String arquivoPlano, Membro membro) {
        this.arquivoPlano = arquivoPlano;
        this.membro = membro;
    }

    // Getters e Setters
    public String getArquivoPlano() { return arquivoPlano; }
    public void setArquivoPlano(String arquivoPlano) { this.arquivoPlano = arquivoPlano; }

    public Membro getMembro() { return membro; }
    public void setMembro(Membro membro) { this.membro = membro; }
}