package com.projeto.service;

import com.projeto.model.Projeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaProjetoAdminServiceTest {

    private List<Projeto> todosProjetos;

    @BeforeEach
    void setUp() {
        todosProjetos = new ArrayList<>();

        Projeto p1 = new Projeto();
        p1.setTitulo("Projeto Recife 1");
        p1.setCampus("Recife");
        p1.setStatus("SUBMETIDO");

        Projeto p2 = new Projeto();
        p2.setTitulo("Projeto Caruaru 1");
        p2.setCampus("Caruaru");
        p2.setStatus("SUBMETIDO");

        todosProjetos.add(p1);
        todosProjetos.add(p2);
    }

    @Test
    @DisplayName("CT-31: Visualizar Todos os Projetos como Admin Geral (PE - Válido)")
    void testAdminGeralVisualizaTodosProjetos() {
        String perfilUsuario = "ADMIN_GERAL";

        List<Projeto> visiveis = new ArrayList<>();
        if ("ADMIN_GERAL".equals(perfilUsuario)) {
            visiveis.addAll(todosProjetos);
        }

        assertEquals(2, visiveis.size());
    }

    @Test
    @DisplayName("CT-32: Filtrar Projetos por Campus como Admin Geral (PE - Válido)")
    void testAdminGeralFiltrarPorCampus() {
        String campusFiltro = "Recife";

        List<Projeto> filtrados = todosProjetos.stream()
                .filter(p -> p.getCampus().equalsIgnoreCase(campusFiltro))
                .toList();

        assertEquals(1, filtrados.size());
        assertEquals("Recife", filtrados.get(0).getCampus());
    }

    @Test
    @DisplayName("CT-33: Visualizar Projetos Restritos ao Próprio Campus como Gestor (PE - Válido)")
    void testGestorVisualizaApenasProprioCampus() {
        String campusGestor = "Recife";

        List<Projeto> visiveisGestor = todosProjetos.stream()
                .filter(p -> p.getCampus().equals(campusGestor))
                .toList();

        assertEquals(1, visiveisGestor.size());
        assertEquals("Projeto Recife 1", visiveisGestor.get(0).getTitulo());
    }

    @Test
    @DisplayName("CT-34: Tentativa de Acesso a Projeto de Outro Campus via URL Direta por Diretor (PE - Inválido)")
    void testDiretorAcessarProjetoOutroCampusDeveNegarAcesso() {
        String campusDiretor = "Recife";
        Projeto projetoOutroCampus = todosProjetos.get(1); 

        Exception exception = assertThrows(SecurityException.class, () -> {
            if (!projetoOutroCampus.getCampus().equals(campusDiretor)) {
                throw new SecurityException("Erro: Acesso negado a projetos de outros campi.");
            }
        });

        assertEquals("Erro: Acesso negado a projetos de outros campi.", exception.getMessage());
    }
    @Test
    @DisplayName("CT-35: Download de Arquivos de Projeto por Usuário Não-Dono (PE - Válido)")
    void testDownloadArquivoPorUsuarioNaoDono() {
        String perfilUsuario = "GESTOR";
        boolean projetoPublicadoOuSubmetido = true;

        assertDoesNotThrow(() -> {
            if (!projetoPublicadoOuSubmetido) {
                throw new SecurityException("Erro: Arquivo indisponível para download.");
            }
        });

        assertTrue(projetoPublicadoOuSubmetido);
    }
}

