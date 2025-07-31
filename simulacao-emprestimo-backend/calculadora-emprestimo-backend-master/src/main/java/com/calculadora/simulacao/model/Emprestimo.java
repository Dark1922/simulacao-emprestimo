package com.calculadora.simulacao.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Emprestimo {
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private LocalDate primeiroPagamento;
    private BigDecimal valor;
    private BigDecimal taxaJuros;
}
