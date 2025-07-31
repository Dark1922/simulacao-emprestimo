package com.calculadora.simulacao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class EmprestimoReqDTO {
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private LocalDate primeiroPagamento;
    private BigDecimal valor;
    private BigDecimal taxaJuros;
}
