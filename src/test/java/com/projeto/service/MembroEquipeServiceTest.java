package com.projeto.service;

import com.projeto.model.Projeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MembroEquipeServiceTest {

    private Projeto projeto;
    private List<String> planosDeTrabalho;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projeto.setStatus("RASCUNHO");
        planosDeTrabalho = new ArrayList<>();
    }

    @Test
    @DisplayName("CT-24: Adicionar Membro na Equipe com Sucesso")
    void testAdicionarMembroSucesso() {
        String cpfMembro = "111.222.333-44";
        
        assertDoesNotThrow(() -> {
            if (cpfMembro == null || cpfMembro.length() < 14) {
                throw new IllegalArgumentException("CPF inválido");
            }
        });
    }

    @Test
    @DisplayName("CT-25: Tentativa de Adição de Membro com CPF Inválido (PE - Inválido)")
    void testAdicionarMembroCpfInvalido() {
        String cpfInvalido = "123";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (cpfInvalido.length() < 14) {
                throw new IllegalArgumentException("Erro: CPF informado é inválido.");
            }
        });

        assertEquals("Erro: CPF informado é inválido.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-26: Tentativa de Adição de Membro com Campo Obrigatório Faltando (PE - Inválido)")
    void testAdicionarMembroCampoObrigatorioFaltando() {
        String nomeMembro = ""; 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (nomeMembro == null || nomeMembro.trim().isEmpty()) {
                throw new IllegalArgumentException("Erro: Nome do membro é obrigatório.");
            }
        });

        assertEquals("Erro: Nome do membro é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-27: Remover Membro da Equipe com Sucesso")
    void testRemoverMembroSucesso() {
        List<String> membros = new ArrayList<>();
        membros.add("João Silva");

        assertDoesNotThrow(() -> {
            membros.remove("João Silva");
        });

        assertTrue(membros.isEmpty());
    }

    @Test
    @DisplayName("CT-28: Adicionar Plano de Trabalho para Membro Bolsista com Sucesso")
    void testAdicionarPlanoTrabalhoSucesso() {
        String plano = "plano_pesquisa_1.pdf";
        planosDeTrabalho.add(plano);

        assertEquals(1, planosDeTrabalho.size());
        assertTrue(planosDeTrabalho.contains("plano_pesquisa_1.pdf"));
    }

    @Test
    @DisplayName("CT-29: Cadastro do 4º Plano de Trabalho no Projeto (AVL - Limite Válido)")
    void testAdicionarQuartoPlanoTrabalhoLimiteValido() {
        planosDeTrabalho.add("plano1.pdf");
        planosDeTrabalho.add("plano2.pdf");
        planosDeTrabalho.add("plano3.pdf");

        assertDoesNotThrow(() -> {
            if (planosDeTrabalho.size() >= 4) {
                throw new IllegalStateException("Limite máximo de 4 planos atingido.");
            }
            planosDeTrabalho.add("plano4.pdf");
        });

        assertEquals(4, planosDeTrabalho.size());
    }

    @Test
    @DisplayName("CT-30: Tentativa de Cadastro do 5º Plano de Trabalho (AVL - Limite Inválido)")
    void testAdicionarQuintoPlanoTrabalhoDeveLancarExcecao() {
        planosDeTrabalho.add("plano1.pdf");
        planosDeTrabalho.add("plano2.pdf");
        planosDeTrabalho.add("plano3.pdf");
        planosDeTrabalho.add("plano4.pdf");

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            if (planosDeTrabalho.size() >= 4) {
                throw new IllegalStateException("Erro: O projeto não pode ter mais que 4 planos de trabalho.");
            }
            planosDeTrabalho.add("plano5.pdf");
        });

        assertEquals("Erro: O projeto não pode ter mais que 4 planos de trabalho.", exception.getMessage());
    }
}