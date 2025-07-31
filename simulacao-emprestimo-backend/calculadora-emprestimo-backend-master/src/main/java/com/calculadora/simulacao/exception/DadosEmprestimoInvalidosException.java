package com.calculadora.simulacao.exception;

import lombok.Getter;

@Getter
public class DadosEmprestimoInvalidosException extends RuntimeException {

    public DadosEmprestimoInvalidosException(String mensagem) {
        super(mensagem);
    }

}