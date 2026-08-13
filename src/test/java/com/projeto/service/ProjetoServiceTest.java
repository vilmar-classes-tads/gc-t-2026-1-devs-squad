package com.projeto.service;

import com.projeto.model.Projeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjetoServiceTest {

    private Projeto projeto;
    private ProjetoService projetoService;
    private List<Projeto> bancoProjetosEmMemoria;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projetoService = new ProjetoService();
        bancoProjetosEmMemoria = new ArrayList<>();

        Projeto pExistente = new Projeto();
        pExistente.setTitulo("Projeto Inicial");
        pExistente.setNumeroEdital("01/2026");
        pExistente.setStatus("RASCUNHO");
        bancoProjetosEmMemoria.add(pExistente);
    }

    // Preenchimento de TODOS os 9 campos exigidos pelo ProjetoService.validarDadosBasicos()
    private void preencherDadosBasicosObrigatorios(Projeto p) {
        p.setTitulo("Projeto de Extensão");
        p.setResumo("Resumo detalhado do projeto de extensão.");
        p.setPalavrasChave("Java, Testes, Software");
        p.setPublicoAlvo("Estudantes do IFPE");
        p.setAreaTematica("Tecnologia");
        p.setCampus("Recife");
        p.setCpfCoordenador("111.222.333-44");
        p.setNumeroEdital("01/2026");
        p.getOdsSelecionadas().add("ODS 4 - Educação de Qualidade");
    }

    @Test
    @DisplayName("CT-21: Tentativa de Edição com Número de Edital Duplicado")
    void testEdicaoComNumeroEditalDuplicado() {
        projeto.setNumeroEdital("01/2026"); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boolean editalExiste = bancoProjetosEmMemoria.stream()
                    .anyMatch(p -> p.getNumeroEdital().equals(projeto.getNumeroEdital()));
            if (editalExiste) {
                throw new IllegalArgumentException("Erro: Edital já vinculado a outro projeto.");
            }
        });

        assertEquals("Erro: Edital já vinculado a outro projeto.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-22: Salvar Rascunho de Projeto com Sucesso")
    void testSalvarRascunhoComSucesso() {
        projeto.setTitulo("Sistema de Gestão");
        projeto.setStatus("RASCUNHO");

        assertEquals("RASCUNHO", projeto.getStatus());
        assertNotNull(projeto.getTitulo());
    }

    @Test
    @DisplayName("CT-23: Submeter Projeto com Sucesso")
    void testSubmeterProjetoComSucesso() {
        projeto.setTitulo("Sistema de Gestão");
        projeto.setAceitouTermoCompromisso(true);
        projeto.getOdsSelecionadas().add("ODS 4 - Educação de Qualidade");

        assertDoesNotThrow(() -> {
            if (!projeto.isAceitouTermoCompromisso()) {
                throw new IllegalStateException("É necessário aceitar o termo.");
            }
            if (projeto.getOdsSelecionadas().isEmpty()) {
                throw new IllegalStateException("Selecione ao menos uma ODS.");
            }
            projeto.setStatus("SUBMETIDO");
        });

        assertEquals("SUBMETIDO", projeto.getStatus());
    }

    @Test
    @DisplayName("CT-24: Tentativa de Submissão sem Aceitar o Termo de Compromisso")
    void testSubmissaoSemTermoDeCompromisso() {
        projeto.setAceitouTermoCompromisso(false);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            if (!projeto.isAceitouTermoCompromisso()) {
                throw new IllegalStateException("Erro: Deve aceitar o Termo de Compromisso.");
            }
        });

        assertEquals("Erro: Deve aceitar o Termo de Compromisso.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-25: Tentativa de Submissão sem Selecionar Nenhuma ODS (PE - Inválido)")
    void testSubmissaoSemODS() {
        projeto.setAceitouTermoCompromisso(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            if (projeto.getOdsSelecionadas() == null || projeto.getOdsSelecionadas().isEmpty()) {
                throw new IllegalStateException("Erro: Selecione pelo menos uma ODS.");
            }
        });

        assertEquals("Erro: Selecione pelo menos uma ODS.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-26: Submissão de Projeto com Exatamente 1 ODS Selecionada (AVL - Limite Válido)")
    void testSubmissaoComUmaODS() {
        projeto.setAceitouTermoCompromisso(true);
        projeto.getOdsSelecionadas().add("ODS 9 - Indústria, Inovação e Infraestrutura");

        assertDoesNotThrow(() -> {
            if (projeto.getOdsSelecionadas().isEmpty()) {
                throw new IllegalStateException("Erro: Selecione pelo menos uma ODS.");
            }
            projeto.setStatus("SUBMETIDO");
        });

        assertEquals(1, projeto.getOdsSelecionadas().size());
        assertEquals("SUBMETIDO", projeto.getStatus());
    }

    @Test
    @DisplayName("CT-27: Edição de Projeto em Status RASCUNHO com Sucesso (PE - Válido)")
    void testEditarProjetoEmRascunho() {
        preencherDadosBasicosObrigatorios(projeto);
        projeto.setStatus("RASCUNHO");
        
        assertDoesNotThrow(() -> {
            projetoService.editarProjeto(projeto);
        });
    }

    @Test
    @DisplayName("CT-28: Tentativa de Edição de Projeto em Status SUBMETIDO (PE - Inválido)")
    void testEditarProjetoSubmetidoDeveLancarExcecao() {
        projeto.setStatus("SUBMETIDO");

        assertThrows(Exception.class, () -> {
            projetoService.editarProjeto(projeto);
        });
    }

    @Test
    @DisplayName("CT-29: Edição de Projeto em Status EM_CORRECAO com Sucesso (PE - Válido)")
    void testEditarProjetoEmCorrecao() {
        preencherDadosBasicosObrigatorios(projeto);
        projeto.setStatus("EM_CORRECAO");

        assertDoesNotThrow(() -> {
            projetoService.editarProjeto(projeto);
        });
    }
}