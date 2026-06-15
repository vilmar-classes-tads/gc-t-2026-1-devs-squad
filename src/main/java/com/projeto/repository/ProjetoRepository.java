package com.projeto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.projeto.model.Projeto;
import com.projeto.service.ProjetoService;

public class ProjetoRepository {
    
    private static List<Projeto> bancoDeDadosProjetos = new ArrayList<>();
    private ProjetoService projetoService = new ProjetoService();

    public void salvarRascunho(Projeto projeto) throws Exception {
        Optional<Projeto> projetoExistente = buscarPorTitulo(projeto.getTitulo());

        if (projetoExistente.isPresent()) {   
            projetoService.editarProjeto(projetoExistente.get());
            bancoDeDadosProjetos.remove(projetoExistente.get());
        }

        projeto.setStatus("RASCUNHO");
        bancoDeDadosProjetos.add(projeto);
    }

    public List<Projeto> listarTodos() { 
        return bancoDeDadosProjetos; 
    }

    public Optional<Projeto> buscarPorTitulo(String titulo) {
        for (Projeto p : bancoDeDadosProjetos) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}