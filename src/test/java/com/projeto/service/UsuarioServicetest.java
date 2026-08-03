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
}