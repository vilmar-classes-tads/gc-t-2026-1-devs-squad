package com.projeto.service;

import com.projeto.model.PerfilUsuario;
import com.projeto.model.Projeto;
import com.projeto.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultaProjetoAdminService {

    /**
     * Consulta e filtra projetos com base nas permissões do perfil do usuário e nos filtros selecionados.
     * 
     * @param usuarioLogado Usuário que está realizando a consulta
     * @param todosProjetos Lista global de projetos cadastrados no sistema
     * @param filtroCampus Campus opcional para filtro (usado por ADMIN_GERAL)
     * @param filtroStatus Status opcional para filtro (ex: SUBMETIDO)
     * @return Lista de projetos permitidos e filtrados
     */
    public List<Projeto> consultarProjetos(Usuario usuarioLogado, List<Projeto> todosProjetos, String filtroCampus, String filtroStatus) {
        if (usuarioLogado == null || usuarioLogado.getPerfis() == null || usuarioLogado.getPerfis().isEmpty()) {
            throw new SecurityException("Erro: Usuário não autenticado ou sem perfil atribuído.");
        }

        List<Projeto> projetosPermitidos = new ArrayList<>();

        // 1. Aplica restrição baseada no perfil
        if (usuarioLogado.getPerfis().contains(PerfilUsuario.ADMIN_GERAL)) {
            projetosPermitidos.addAll(todosProjetos);
        } else if (usuarioLogado.getPerfis().contains(PerfilUsuario.GESTOR) || 
                   usuarioLogado.getPerfis().contains(PerfilUsuario.DIRETOR)) {
            
            String campusUsuario = usuarioLogado.getCampus();
            if (campusUsuario == null || campusUsuario.trim().isEmpty()) {
                throw new SecurityException("Erro: Usuário gestor/diretor sem campus associado.");
            }

            projetosPermitidos = todosProjetos.stream()
                    .filter(p -> campusUsuario.equalsIgnoreCase(p.getCampus()))
                    .collect(Collectors.toList());
        } else {
            throw new SecurityException("Erro: Perfil sem permissão para acessar o painel administrativo.");
        }

        // 2. Aplica filtros adicionais selecionados na tela
        return projetosPermitidos.stream()
                .filter(p -> filtroCampus == null || filtroCampus.trim().isEmpty() || p.getCampus().equalsIgnoreCase(filtroCampus))
                .filter(p -> filtroStatus == null || filtroStatus.trim().isEmpty() || p.getStatus().equalsIgnoreCase(filtroStatus))
                .collect(Collectors.toList());
    }

    /**
     * Valida o acesso direto a um determinado projeto por URL.
     */
    public void validarAcessoDiretoProjeto(Usuario usuarioLogado, Projeto projeto) {
        if (usuarioLogado.getPerfis().contains(PerfilUsuario.ADMIN_GERAL)) {
            return; // Admin Geral acessa qualquer projeto
        }

        if (usuarioLogado.getPerfis().contains(PerfilUsuario.GESTOR) || 
            usuarioLogado.getPerfis().contains(PerfilUsuario.DIRETOR)) {
            
            if (!usuarioLogado.getCampus().equalsIgnoreCase(projeto.getCampus())) {
                throw new SecurityException("Erro: Acesso negado a projetos de outros campi.");
            }
        }
    }
}