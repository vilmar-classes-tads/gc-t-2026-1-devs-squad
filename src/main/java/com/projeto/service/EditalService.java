package com.projeto.service;
import com.projeto.model.Edital;
import com.projeto.utils.DateValidator;

public class EditalService {
    public boolean validatesumbmissao (Edital edital){
    DateValidator validate = new DateValidator();
    boolean resultsubmissao = validate.localDateValidation(edital.getDataFimSubmissao() ,edital.getDataInicioSubmissao());
    boolean resultavaliacao = validate.localDateValidation(edital.getDataFimAvaliacao(), edital.getDataInicioAvaliacao());
    
    return resultsubmissao &&resultavaliacao;
}



}
