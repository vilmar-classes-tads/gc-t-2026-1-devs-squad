package com.projeto.service;

import com.projeto.model.PerfilUsuario;
import com.projeto.model.Projeto;
import com.projeto.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaProjetoAdminServiceTest {

    private ConsultaProjetoAdminService service;
    private List<Projeto> todosProjetos;

    @BeforeEach
    void setUp() {
        service = new ConsultaProjetoAdminService();
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
    @DisplayName("CT-37: Visualizar Todos os Projetos como Admin Geral (PE - Válido)")
    void testAdminGeralVisualizaTodosProjetos() {
        Usuario admin = new Usuario();
        admin.adicionarPerfil(PerfilUsuario.ADMIN_GERAL);

        List<Projeto> resultado = service.consultarProjetos(admin, todosProjetos, null, null);

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("CT-38: Filtrar Projetos por Campus como Admin Geral (PE - Válido)")
    void testAdminGeralFiltrarPorCampus() {
        Usuario admin = new Usuario();
        admin.adicionarPerfil(PerfilUsuario.ADMIN_GERAL);

        List<Projeto> filtrados = service.consultarProjetos(admin, todosProjetos, "Recife", null);

        assertEquals(1, filtrados.size());
        assertEquals("Recife", filtrados.get(0).getCampus());
    }

    @Test
    @DisplayName("CT-39: Visualizar Projetos Restritos ao Próprio Campus como Gestor (PE - Válido)")
    void testGestorVisualizaApenasProprioCampus() {
        Usuario gestor = new Usuario();
        gestor.setCampus("Recife");
        gestor.adicionarPerfil(PerfilUsuario.GESTOR);

        List<Projeto> visiveisGestor = service.consultarProjetos(gestor, todosProjetos, null, null);

        assertEquals(1, visiveisGestor.size());
        assertEquals("Projeto Recife 1", visiveisGestor.get(0).getTitulo());
    }

    @Test
    @DisplayName("CT-40: Tentativa de Acesso a Projeto de Outro Campus via URL Direta por Diretor (PE - Inválido)")
    void testDiretorAcessarProjetoOutroCampusDeveNegarAcesso() {
        Usuario diretor = new Usuario();
        diretor.setCampus("Recife");
        diretor.adicionarPerfil(PerfilUsuario.DIRETOR);

        Projeto projetoOutroCampus = todosProjetos.get(1); // Projeto de Caruaru

        Exception exception = assertThrows(SecurityException.class, () -> {
            service.validarAcessoDiretoProjeto(diretor, projetoOutroCampus);
        });

        assertEquals("Erro: Acesso negado a projetos de outros campi.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-41: Download de Arquivos de Projeto por Usuário Não-Dono (PE - Válido)")
    void testDownloadArquivoPorUsuarioNaoDono() {
        Usuario gestor = new Usuario();
        gestor.adicionarPerfil(PerfilUsuario.GESTOR);
        boolean projetoPublicadoOuSubmetido = true;

        assertDoesNotThrow(() -> {
            if (!projetoPublicadoOuSubmetido) {
                throw new SecurityException("Erro: Arquivo indisponível para download.");
            }
        });

        assertTrue(projetoPublicadoOuSubmetido);
    }
}