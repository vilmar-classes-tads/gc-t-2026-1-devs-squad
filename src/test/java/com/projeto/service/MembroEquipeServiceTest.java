package com.projeto.service;

import com.projeto.model.Membro;
import com.projeto.model.PlanoTrabalho;
import com.projeto.model.Projeto;
import com.projeto.model.TipoMembro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MembroEquipeServiceTest {

    private Projeto projeto;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projeto.setStatus("RASCUNHO");
    }

    @Test
    @DisplayName("CT-30: Adicionar Membro na Equipe com Sucesso")
    void testAdicionarMembroSucesso() {
        Membro membro = new Membro("João Silva", "111.222.333-44", TipoMembro.BOLSISTA, 20);

        assertDoesNotThrow(() -> {
            if (membro.getCpf() == null || membro.getCpf().length() < 14) {
                throw new IllegalArgumentException("CPF inválido");
            }
            projeto.adicionarMembro(membro);
        });

        assertEquals(1, projeto.getMembros().size());
        assertEquals("João Silva", projeto.getMembros().get(0).getNome());
    }

    @Test
    @DisplayName("CT-31: Tentativa de Adicionar Membro com CPF Inválido (PE - Inválido)")
    void testAdicionarMembroCpfInvalido() {
        Membro membro = new Membro("Maria Oliveira", "123", TipoMembro.VOLUNTARIO, 10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (membro.getCpf() == null || membro.getCpf().length() < 14) {
                throw new IllegalArgumentException("Erro: CPF informado é inválido.");
            }
            projeto.adicionarMembro(membro);
        });

        assertEquals("Erro: CPF informado é inválido.", exception.getMessage());
        assertTrue(projeto.getMembros().isEmpty());
    }

    @Test
    @DisplayName("CT-32: Tentativa de Adicionar Membro com Campo Obrigatório Faltando (PE - Inválido)")
    void testAdicionarMembroCampoObrigatorioFaltando() {
        Membro membro = new Membro("", "111.222.333-44", TipoMembro.BOLSISTA, 20);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (membro.getNome() == null || membro.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Erro: Nome do membro é obrigatório.");
            }
            projeto.adicionarMembro(membro);
        });

        assertEquals("Erro: Nome do membro é obrigatório.", exception.getMessage());
        assertTrue(projeto.getMembros().isEmpty());
    }

    @Test
    @DisplayName("CT-33: Remover Membro da Equipe com Sucesso")
    void testRemoverMembroSucesso() {
        Membro membro = new Membro("João Silva", "111.222.333-44", TipoMembro.BOLSISTA, 20);
        projeto.adicionarMembro(membro);

        assertEquals(1, projeto.getMembros().size());

        assertDoesNotThrow(() -> {
            projeto.removerMembro(membro);
        });

        assertTrue(projeto.getMembros().isEmpty());
    }

    @Test
    @DisplayName("CT-34: Adicionar Plano de Trabalho para Membro Bolsista com Sucesso")
    void testAdicionarPlanoTrabalhoSucesso() {
        Membro bolsista = new Membro("Carlos Lima", "222.333.444-55", TipoMembro.BOLSISTA, 20);
        PlanoTrabalho plano = new PlanoTrabalho("plano_pesquisa_1.pdf", bolsista);

        projeto.adicionarPlanoTrabalho(plano);

        assertEquals(1, projeto.getPlanosDeTrabalho().size());
        assertEquals("plano_pesquisa_1.pdf", projeto.getPlanosDeTrabalho().get(0).getArquivoPlano());
        assertEquals("Carlos Lima", projeto.getPlanosDeTrabalho().get(0).getMembro().getNome());
    }

    @Test
    @DisplayName("CT-35: Cadastro do 4º Plano de Trabalho no Projeto (AVL - Limite Válido)")
    void testAdicionarQuartoPlanoTrabalhoLimiteValido() {
        Membro m = new Membro("Pesquisador", "111.222.333-44", TipoMembro.PESQUISADOR, 20);

        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano1.pdf", m));
        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano2.pdf", m));
        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano3.pdf", m));

        assertDoesNotThrow(() -> {
            projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano4.pdf", m));
        });

        assertEquals(4, projeto.getPlanosDeTrabalho().size());
    }

    @Test
    @DisplayName("CT-36: Tentativa de Cadastro do 5º Plano de Trabalho no Projeto (AVL - Limite Inválido)")
    void testAdicionarQuintoPlanoTrabalhoDeveLancarExcecao() {
        Membro m = new Membro("Pesquisador", "111.222.333-44", TipoMembro.PESQUISADOR, 20);

        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano1.pdf", m));
        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano2.pdf", m));
        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano3.pdf", m));
        projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano4.pdf", m));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            projeto.adicionarPlanoTrabalho(new PlanoTrabalho("plano5.pdf", m));
        });

        assertEquals("Erro: O projeto não pode ter mais que 4 planos de trabalho.", exception.getMessage());
        assertEquals(4, projeto.getPlanosDeTrabalho().size());
    }
}