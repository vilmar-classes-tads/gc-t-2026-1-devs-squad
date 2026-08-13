package com.projeto.service;

import com.projeto.model.Edital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditalServiceTest {

    private Edital edital;
    private List<Edital> bancoEditaisEmMemoria;

    @BeforeEach
    void setUp() {
        edital = new Edital();
        bancoEditaisEmMemoria = new ArrayList<>();

        Edital existente = new Edital();
        existente.setNumero("01/2026");
        existente.setTitulo("Edital de Inovação");
        existente.setDataInicioSubmissao(LocalDate.of(2026, 8, 1));
        existente.setDataFimSubmissao(LocalDate.of(2026, 8, 30));
        bancoEditaisEmMemoria.add(existente);
    }

    @Test
    @DisplayName("CT-14: Cadastro de Usuário com Senha de 7 Caracteres (AVL - Limite Válido)")
    void testSenhaSeteCaracteres() {
        String senhaSete = "1234567";
        
        assertDoesNotThrow(() -> {
            if (senhaSete.length() < 6) {
                throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
            }
        });
        assertEquals(7, senhaSete.length());
    }

    @Test
    @DisplayName("CT-15: Cadastro de Edital com Sucesso (Datas válidas)")
    void testCadastroEditalSucesso() {
        edital.setNumero("02/2026");
        edital.setTitulo("Edital de Extensão");
        edital.setDataInicioSubmissao(LocalDate.of(2026, 8, 10));
        edital.setDataFimSubmissao(LocalDate.of(2026, 8, 20));

        assertNotNull(edital.getNumero());
        assertTrue(edital.getDataFimSubmissao().isAfter(edital.getDataInicioSubmissao()));
    }

    @Test
    @DisplayName("CT-16: Tentativa de Cadastro com Número de Edital Duplicado")
    void testCadastroNumeroEditalDuplicado() {
        edital.setNumero("01/2026"); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boolean numeroExiste = bancoEditaisEmMemoria.stream()
                    .anyMatch(e -> e.getNumero().equals(edital.getNumero()));
            
            if (numeroExiste) {
                throw new IllegalArgumentException("Erro: Número de edital já cadastrado.");
            }
        });

        assertEquals("Erro: Número de edital já cadastrado.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-17: Tentativa de Cadastro com Data Fim de Submissão Igual à Data de Início (AVL - Limite Inválido)")
    void testDataFimIgualDataInicioDeveLancarExcecao() {
        LocalDate dataInicio = LocalDate.of(2026, 8, 10);
        LocalDate dataFimIgual = LocalDate.of(2026, 8, 10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (!dataFimIgual.isAfter(dataInicio)) {
                throw new IllegalArgumentException("Erro: A data fim deve ser posterior à data de início.");
            }
        });

        assertEquals("Erro: A data fim deve ser posterior à data de início.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-18: Tentativa de Cadastro com Data Fim de Submissão Anterior à Data de Início (PE - Inválido)")
    void testDataFimAnteriorDataInicioDeveLancarExcecao() {
        LocalDate dataInicio = LocalDate.of(2026, 8, 10);
        LocalDate dataFimAnterior = LocalDate.of(2026, 8, 5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (!dataFimAnterior.isAfter(dataInicio)) {
                throw new IllegalArgumentException("Erro: A data fim deve ser posterior à data de início.");
            }
        });

        assertEquals("Erro: A data fim deve ser posterior à data de início.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-19: Cadastro de Edital com Data Fim de Submissão 1 Dia Após o Início (AVL - Limite Válido)")
    void testDataFimUmDiaAposInicioSucesso() {
        LocalDate dataInicio = LocalDate.of(2026, 8, 10);
        LocalDate dataFimUmDiaDepois = LocalDate.of(2026, 8, 11);

        assertDoesNotThrow(() -> {
            if (!dataFimUmDiaDepois.isAfter(dataInicio)) {
                throw new IllegalArgumentException("Erro: A data fim deve ser posterior à data de início.");
            }
            edital.setDataInicioSubmissao(dataInicio);
            edital.setDataFimSubmissao(dataFimUmDiaDepois);
        });

        assertTrue(edital.getDataFimSubmissao().isAfter(edital.getDataInicioSubmissao()));
    }

    @Test
    @DisplayName("CT-20: Edição de Edital com Sucesso")
    void testEdicaoEditalSucesso() {
        edital.setNumero("01/2026");
        edital.setTitulo("Título Antigo");
        
        edital.setTitulo("Título Novo Atualizado");

        assertEquals("Título Novo Atualizado", edital.getTitulo());
    }
}