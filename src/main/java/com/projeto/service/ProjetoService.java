package com.projeto.service;

import com.projeto.model.Projeto;

public class ProjetoService {

    private static final String STATUS_RASCUNHO = "RASCUNHO";
    private static final String STATUS_EM_CORRECAO = "EM_CORRECAO";

    public void validarDadosBasicos(Projeto projeto) throws Exception {
        if (projeto == null) {
            throw new Exception("Erro: O projeto e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getTitulo())) {
            throw new Exception("Erro: O titulo do projeto e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getResumo())) {
            throw new Exception("Erro: O resumo do projeto e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getPalavrasChave())) {
            throw new Exception("Erro: As palavras-chave do projeto sao obrigatorias.");
        }
        if (isCampoTextoVazio(projeto.getPublicoAlvo())) {
            throw new Exception("Erro: O publico-alvo do projeto e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getAreaTematica())) {
            throw new Exception("Erro: A area tematica do projeto e obrigatoria.");
        }
        if (isCampoTextoVazio(projeto.getCampus())) {
            throw new Exception("Erro: O campus do projeto e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getCpfCoordenador())) {
            throw new Exception("Erro: O CPF do coordenador e obrigatorio.");
        }
        if (isCampoTextoVazio(projeto.getNumeroEdital())) {
            throw new Exception("Erro: O numero do edital e obrigatorio.");
        }
        if (projeto.getOdsSelecionadas() == null || projeto.getOdsSelecionadas().isEmpty()) {
            throw new Exception("Erro: Selecione ao menos uma ODS.");
        }
    }

    public void editarProjeto(Projeto projetoExistente) throws Exception {
        if (projetoExistente == null) {
            throw new Exception("Erro: O projeto e obrigatorio.");
        }
        if (!STATUS_RASCUNHO.equals(projetoExistente.getStatus())
                && !STATUS_EM_CORRECAO.equals(projetoExistente.getStatus())) {
            throw new Exception("Erro: Nao e permitido editar projetos ja submetidos.");
        }

        validarDadosBasicos(projetoExistente);
    }

    private boolean isCampoTextoVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
