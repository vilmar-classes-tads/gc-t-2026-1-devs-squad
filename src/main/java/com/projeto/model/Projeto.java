package com.projeto.model;

import java.util.ArrayList;
import java.util.List;

public class Projeto {
    
    private String titulo;
    private String resumo;
    private String palavrasChave;
    private String publicoAlvo;
    private String areaTematica;
    private String campus;
    
    private List<String> odsSelecionadas = new ArrayList<>();
    private boolean aceitouTermoCompromisso;
    
    private String status;

    private String cpfCoordenador; 
    private String numeroEdital;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getPalavrasChave() { return palavrasChave; }
    public void setPalavrasChave(String palavrasChave) { this.palavrasChave = palavrasChave; }

    public String getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(String publicoAlvo) { this.publicoAlvo = publicoAlvo; }

    public String getAreaTematica() { return areaTematica; }
    public void setAreaTematica(String areaTematica) { this.areaTematica = areaTematica; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public List<String> getOdsSelecionadas() { return odsSelecionadas; }
    public void setOdsSelecionadas(List<String> odsSelecionadas) { this.odsSelecionadas = odsSelecionadas; }

    public boolean isAceitouTermoCompromisso() { return aceitouTermoCompromisso; }
    public void setAceitouTermoCompromisso(boolean aceitouTermoCompromisso) { this.aceitouTermoCompromisso = aceitouTermoCompromisso; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCpfCoordenador() { return cpfCoordenador; }
    public void setCpfCoordenador(String cpfCoordenador) { this.cpfCoordenador = cpfCoordenador; }

    public String getNumeroEdital() { return numeroEdital; }
    public void setNumeroEdital(String numeroEdital) { this.numeroEdital = numeroEdital; }
}