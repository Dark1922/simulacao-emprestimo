package com.calculadora.simulacao.util;

import org.springframework.stereotype.Component;

import com.calculadora.simulacao.dto.EmprestimoReqDTO;
import com.calculadora.simulacao.model.Emprestimo;

@Component
public class EmprestimoUtil {

    public Emprestimo buildEmprestimo(EmprestimoReqDTO emprestimoReqDTO) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataInicio(emprestimoReqDTO.getDataInicio());
        emprestimo.setDataTermino(emprestimoReqDTO.getDataTermino());
        emprestimo.setPrimeiroPagamento(emprestimoReqDTO.getPrimeiroPagamento());
        emprestimo.setValor(emprestimoReqDTO.getValor());
        emprestimo.setTaxaJuros(emprestimoReqDTO.getTaxaJuros());
        return emprestimo;
    }
}
