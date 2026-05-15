package com.projeto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projeto.model.Usuario;



public class UsuarioRepository {
    private static List<Usuario> bancoDeDados = new ArrayList<>();
    
    public void salvar(Usuario usuario){
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