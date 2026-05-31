package com.projeto.utils;
import java.time.LocalDate;

public class DateValidator {


    public boolean localDateValidation ( LocalDate fim , LocalDate inicio ){
        return fim.isAfter(inicio)   ;
    }
}
