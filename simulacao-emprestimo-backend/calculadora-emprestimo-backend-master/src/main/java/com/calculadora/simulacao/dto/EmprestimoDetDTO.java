package com.calculadora.simulacao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmprestimoDetDTO {

    private LocalDate dataVencimento;

    private BigDecimal valorPrincipal;

    private BigDecimal saldoDevedor;

    private String numeroParcela;

    private BigDecimal valorParcela;
    
    private BigDecimal amortizacao;

    private BigDecimal saldo;

    private BigDecimal provisao;

    private BigDecimal jurosAcumulados;

    private BigDecimal jurosPagos;
}