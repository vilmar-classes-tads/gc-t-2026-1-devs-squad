package com.projeto.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.projeto.model.Edital;
import com.projeto.service.EditalService;

public class EditalRepository {
    private static List<Edital> bancoDeDados = new ArrayList<>();
    private EditalService editalService = new EditalService();
    
    public void salvar(Edital edital) throws Exception {
    
        editalService.validateSubmissao(edital);

        if (buscarPorNumero(edital.getNumero()).isPresent()) {
            throw new Exception("Erro: Número de edital já está cadastrado.");
        }

        bancoDeDados.add(edital);
    }

    public List<Edital> Listartodos() {
        return bancoDeDados;
    }

    public Optional<Edital> buscarPorNumero(String numero) {
        for (Edital e : bancoDeDados) {
            if (e.getNumero().equals(numero)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }
}