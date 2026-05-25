package com.projeto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.projeto.model.Usuario;

public class UsuarioRepository {
    private static List<Usuario> bancoDeDados = new ArrayList<>();
    
    public void salvar(Usuario usuario) throws Exception {
        if (buscarPorCpf(usuario.getCpf()).isPresent()) {
            throw new Exception("Erro: CPF já está cadastrado.");
        }
        
        if (buscarPorEmail(usuario.getEmailInstitucional()).isPresent()) {
            throw new Exception("Erro: E-mail institucional já está cadastrado.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new Exception("Erro: A senha deve ter no mínimo 6 caracteres.");
        }

        String senhaCriptografada = "hash_simulado_" + usuario.getSenha();
        usuario.setSenha(senhaCriptografada);

        usuario.getPerfis().add("ROLE_COORDENADOR");
        usuario.getPerfis().add("ROLE_AVALIADOR");

        bancoDeDados.add(usuario);
    }

    public List<Usuario> Listartodos(){
        return bancoDeDados;
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        for (Usuario u : bancoDeDados) {
            if (u.getCpf().equals(cpf)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        for (Usuario u : bancoDeDados) {
            if (u.getEmailInstitucional().equals(email)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}