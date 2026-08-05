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

    // --- NOVOS ATRIBUTOS DA EQUIPE E PLANOS (ISSUE 3) ---
    private List<Membro> membros = new ArrayList<>();
    private List<PlanoTrabalho> planosDeTrabalho = new ArrayList<>();

    // --- GETTERS E SETTERS ORIGINAIS ---
    
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

    // --- MÉTODOS DE GERENCIAMENTO DE EQUIPE E PLANOS (ISSUE 3) ---

    public List<Membro> getMembros() { 
        return membros; 
    }

    public void setMembros(List<Membro> membros) { 
        this.membros = membros; 
    }

    public void adicionarMembro(Membro membro) {
        this.membros.add(membro);
    }
    
    public void removerMembro(Membro membro) {
        this.membros.remove(membro);
    }

    public List<PlanoTrabalho> getPlanosDeTrabalho() { 
        return planosDeTrabalho; 
    }

    public void setPlanosDeTrabalho(List<PlanoTrabalho> planosDeTrabalho) { 
        this.planosDeTrabalho = planosDeTrabalho; 
    }

    /**
     * Adiciona um plano de trabalho ao projeto.
     * Implementa a validação da Issue 3 (Máximo de 4 planos).
     * @param plano O plano de trabalho a ser adicionado.
     * @throws IllegalStateException caso o limite de 4 planos já tenha sido atingido.
     */
    public void adicionarPlanoTrabalho(PlanoTrabalho plano) {
        if (this.planosDeTrabalho.size() >= 4) {
            throw new IllegalStateException("Erro: O projeto não pode ter mais que 4 planos de trabalho.");
        }
        this.planosDeTrabalho.add(plano);
    }
}