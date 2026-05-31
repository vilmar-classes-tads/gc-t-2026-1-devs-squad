package com.projeto.service;
import com.projeto.model.Edital;
import com.projeto.utils.DateValidator;

public class EditalService {
    public void validateSubmissao(Edital edital) throws Exception {
        
        if (edital.getTitulo() == null || edital.getTitulo().isEmpty()) {
            throw new Exception("Erro: O título do edital é obrigatório.");
        }
        if (edital.getNumero() == null || edital.getNumero().isEmpty()) {
            throw new Exception("Erro: O número do edital é obrigatório.");
        }
        if (edital.getAno() == null || edital.getAno().isEmpty()) {
            throw new Exception("Erro: O ano do edital é obrigatório.");
        }
        if (edital.getDataInicioSubmissao() == null) {
            throw new Exception("Erro: A data de início da submissão é obrigatória.");
        }
        if (edital.getDataFimSubmissao() == null) {
            throw new Exception("Erro: A data de fim da submissão é obrigatória.");
        }
        if (edital.getDataInicioAvaliacao() == null) {
            throw new Exception("Erro: A data de início da avaliação é obrigatória.");
        }
        if (edital.getDataFimAvaliacao() == null) {
            throw new Exception("Erro: A data de fim da avaliação é obrigatória.");
        }

        DateValidator validate = new DateValidator();
        boolean resultSubmissao = validate.localDateValidation(edital.getDataFimSubmissao(), edital.getDataInicioSubmissao());
        boolean resultAvaliacao = validate.localDateValidation(edital.getDataFimAvaliacao(), edital.getDataInicioAvaliacao());
        
        if (!resultSubmissao || !resultAvaliacao) {
            throw new Exception("Erro: As datas de submissão ou avaliação são inválidas (a data de início deve ser anterior à de fim).");
        }
    }
}
