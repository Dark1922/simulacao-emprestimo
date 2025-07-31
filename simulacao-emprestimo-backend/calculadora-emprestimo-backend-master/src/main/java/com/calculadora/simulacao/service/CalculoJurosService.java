package com.calculadora.simulacao.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CalculoJurosService {

    private static final int ESCALA_PADRAO = 10;
    private static final int ESCALA_FINAL = 2;
    private static final int ANO_BASE_360 = 360;

    private static final MathContext MC_PADRAO = new MathContext(ESCALA_PADRAO, RoundingMode.HALF_UP);

    public BigDecimal calcularJuros(BigDecimal saldo, BigDecimal jurosAcumulado, BigDecimal taxa, long dias) {
        BigDecimal saldoTotal = saldo.add(jurosAcumulado, MC_PADRAO);
        BigDecimal fatorTempo = BigDecimal.valueOf(dias)
                .divide(BigDecimal.valueOf(ANO_BASE_360), ESCALA_PADRAO, RoundingMode.HALF_UP);

        BigDecimal fatorCrescimento = calcularFatorComposto(taxa, fatorTempo);
        BigDecimal fatorJuros = fatorCrescimento.subtract(BigDecimal.ONE, MC_PADRAO);

        return saldoTotal.multiply(fatorJuros, MC_PADRAO)
                .setScale(ESCALA_FINAL, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularFatorComposto(BigDecimal taxa, BigDecimal tempoFracionado) {
        double base = BigDecimal.ONE.add(taxa, MC_PADRAO).doubleValue();
        double expoente = tempoFracionado.doubleValue();
        double resultado = Math.pow(base, expoente);
        return BigDecimal.valueOf(resultado);
    }
}
