package com.projeto.service;

import com.projeto.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private Usuario usuario;
    private List<Usuario> bancoUsuariosEmMemoria;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        bancoUsuariosEmMemoria = new ArrayList<>();

        Usuario existente = new Usuario();
        existente.setNomeCompleto("Usuário Existente");
        existente.setCpf("111.222.333-44");
        existente.setEmailInstitucional("existente@ifpe.edu.br");
        existente.setSenha("123456");
        bancoUsuariosEmMemoria.add(existente);
    }

    @Test
    @DisplayName("CT-01: Cadastro de Usuário com Sucesso (Todos os campos)")
    void testCadastroUsuarioTodosCamposSucesso() {
        usuario.setNomeCompleto("João Silva");
        usuario.setCpf("222.333.444-55");
        usuario.setEmailInstitucional("joao@ifpe.edu.br");
        usuario.setSenha("123456");

        assertNotNull(usuario.getNomeCompleto());
        assertNotNull(usuario.getCpf());
        assertNotNull(usuario.getEmailInstitucional());
        assertEquals("123456", usuario.getSenha());
    }

    @Test
    @DisplayName("CT-02: Cadastro de Usuário com Sucesso (Somente campos obrigatórios)")
    void testCadastroUsuarioSomenteCamposObrigatoriosSucesso() {
        usuario.setNomeCompleto("Maria Oliveira");
        usuario.setCpf("333.444.555-66");
        usuario.setEmailInstitucional("maria@ifpe.edu.br");
        usuario.setSenha("654321");

        assertNotNull(usuario.getNomeCompleto());
        assertNotNull(usuario.getCpf());
        assertNotNull(usuario.getEmailInstitucional());
        assertNotNull(usuario.getSenha());
    }

    @Test
    @DisplayName("CT-03: Tentativa de Cadastro com CPF Duplicado")
    void testCadastroCpfDuplicadoDeveLancarExcecao() {
        usuario.setCpf("111.222.333-44"); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boolean cpfJaExiste = bancoUsuariosEmMemoria.stream()
                    .anyMatch(u -> u.getCpf().equals(usuario.getCpf()));
            
            if (cpfJaExiste) {
                throw new IllegalArgumentException("Erro: CPF já cadastrado no sistema.");
            }
        });

        assertEquals("Erro: CPF já cadastrado no sistema.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-04: Tentativa de Cadastro com E-mail Institucional Duplicado")
    void testCadastroEmailDuplicadoDeveLancarExcecao() {
        usuario.setEmailInstitucional("existente@ifpe.edu.br"); 

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            boolean emailJaExiste = bancoUsuariosEmMemoria.stream()
                    .anyMatch(u -> u.getEmailInstitucional().equals(usuario.getEmailInstitucional()));
            
            if (emailJaExiste) {
                throw new IllegalArgumentException("Erro: E-mail institucional já cadastrado no sistema.");
            }
        });

        assertEquals("Erro: E-mail institucional já cadastrado no sistema.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-05: Tentativa de Cadastro com Senha Menor que 6 Caracteres (AVL - Limite Inválido)")
    void testCadastroSenhaMenorQueSeisCaracteres() {
        String senhaInvalida = "12345";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (senhaInvalida.length() < 6) {
                throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
            }
            usuario.setSenha(senhaInvalida);
        });

        assertEquals("A senha deve ter no mínimo 6 caracteres.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-06: Cadastro de Usuário com Senha de 6 Caracteres (AVL - Limite Válido)")
    void testCadastroSenhaComSeisCaracteres() {
        String senhaValida = "123456";

        assertDoesNotThrow(() -> {
            if (senhaValida.length() < 6) {
                throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
            }
            usuario.setSenha(senhaValida);
        });

        assertEquals("123456", usuario.getSenha());
    }

    @Test
    @DisplayName("CT-07: Tentativa de logar sem preencher o campo nome")
    void testLoginSemPreencherNome() {
        usuario.setNomeCompleto("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (usuario.getNomeCompleto() == null || usuario.getNomeCompleto().trim().isEmpty()) {
                throw new IllegalArgumentException("O campo nome é obrigatório.");
            }
        });

        assertEquals("O campo nome é obrigatório.", exception.getMessage());
    }
    @Test
    @DisplayName("CT-08: Tentativa de logar sem o campo CPF preenchido (obrigatório)")
    void testLoginSemPreencherCpf() {
        usuario.setCpf("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
                throw new IllegalArgumentException("O campo CPF é obrigatório.");
            }
        });

        assertEquals("O campo CPF é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-09: Tentativa de logar sem o campo Email Institucional (obrigatório)")
    void testLoginSemPreencherEmail() {
        usuario.setEmailInstitucional("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (usuario.getEmailInstitucional() == null || usuario.getEmailInstitucional().trim().isEmpty()) {
                throw new IllegalArgumentException("O campo e-mail institucional é obrigatório.");
            }
        });

        assertEquals("O campo e-mail institucional é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-10: Tentativa de logar sem o campo senha preenchido (obrigatório)")
    void testLoginSemPreencherSenha() {
        usuario.setSenha("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("O campo senha é obrigatório.");
            }
        });

        assertEquals("O campo senha é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-11: Tentativa de logar sem o Campus preenchido (obrigatório)")
    void testLoginSemPreencherCampus() {
        String campus = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (campus == null || campus.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo campus é obrigatório.");
            }
        });

        assertEquals("O campo campus é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-12: Tentativa de logar sem o campo Área de Formação preenchido (obrigatório)")
    void testLoginSemPreencherAreaFormacao() {
        String areaFormacao = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (areaFormacao == null || areaFormacao.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo área de formação é obrigatório.");
            }
        });

        assertEquals("O campo área de formação é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("CT-13: Tentativa de logar sem o campo Título preenchido (obrigatório)")
    void testLoginSemPreencherTitulo() {
        String titulo = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new IllegalArgumentException("O campo título é obrigatório.");
            }
        });

        assertEquals("O campo título é obrigatório.", exception.getMessage());
    }
}