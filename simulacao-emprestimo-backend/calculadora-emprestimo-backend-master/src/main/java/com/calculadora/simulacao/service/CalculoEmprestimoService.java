package com.calculadora.simulacao.service;

import com.calculadora.simulacao.dto.EmprestimoDetDTO;
import com.calculadora.simulacao.model.Emprestimo;
import com.calculadora.simulacao.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculoEmprestimoService {

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);
    private static final int SCALE = 2;

    @Autowired
    private CalculoJurosService calculoJurosService;

    @Autowired
    private DataUtil dataUtil;

    public List<EmprestimoDetDTO> calcularDetalhesParcelas(
            Emprestimo emprestimoDTO,
            List<LocalDate> datasAcumuladas,
            int numeroParcelas) {

        List<EmprestimoDetDTO> detalhes = new ArrayList<>();
        BigDecimal valorEmprestimo = emprestimoDTO.getValor();
        BigDecimal amortizacaoMensal = valorEmprestimo.divide(
                new BigDecimal(numeroParcelas),
                2,
                RoundingMode.HALF_UP
        );
        BigDecimal saldo = valorEmprestimo;
        BigDecimal taxaJuros = emprestimoDTO.getTaxaJuros();
        BigDecimal jurosAcumulados = BigDecimal.ZERO;
        LocalDate dataAnterior = emprestimoDTO.getDataInicio();

        int contadorParcelas = 0;
        boolean ultimaParcelaAjustada = false;

        for (LocalDate dataAtual : datasAcumuladas) {
            if (saldo.compareTo(BigDecimal.ZERO) <= 0 && !dataAtual.equals(emprestimoDTO.getDataInicio())) {
                break;
            }

            long diasEntre = ChronoUnit.DAYS.between(dataAnterior, dataAtual);
            BigDecimal provisao = calcularProvisaoJuros(saldo, jurosAcumulados, taxaJuros, diasEntre);
            jurosAcumulados = jurosAcumulados.add(provisao).setScale(2, RoundingMode.HALF_UP);

            boolean diaPagamento = dataUtil.isPagamentoDiario(dataAtual, emprestimoDTO.getPrimeiroPagamento());

            BigDecimal jurosPagos = BigDecimal.ZERO;
            BigDecimal principal = BigDecimal.ZERO;
            BigDecimal valorParcela = BigDecimal.ZERO;

            if (diaPagamento && contadorParcelas < numeroParcelas) {
                jurosPagos = jurosAcumulados.setScale(2, RoundingMode.HALF_UP);

                // Cálculo especial para a última parcela
                if (contadorParcelas == numeroParcelas - 1) {
                    principal = saldo;
                    // Ajuste fino para garantir que o saldo chegue a zero
                    if (principal.add(jurosPagos).compareTo(valorParcela) != 0) {
                        jurosPagos = jurosPagos.add(saldo.subtract(principal));
                    }
                } else {
                    principal = amortizacaoMensal;
                    if (principal.compareTo(saldo) > 0) {
                        principal = saldo;
                    }
                }

                valorParcela = principal.add(jurosPagos);
                saldo = saldo.subtract(principal).setScale(2, RoundingMode.HALF_UP);
                jurosAcumulados = BigDecimal.ZERO;
                contadorParcelas++;
            }

            BigDecimal saldoDevedor = diaPagamento
                    ? saldo
                    : saldo.add(jurosAcumulados).setScale(2, RoundingMode.HALF_UP);

            String numeroParcela = diaPagamento ? contadorParcelas + "/" + numeroParcelas : "";

            EmprestimoDetDTO detalhe = EmprestimoDetDTO.builder()
                    .dataVencimento(dataAtual)
                    .saldo(saldo.setScale(2, RoundingMode.HALF_UP))
                    .saldoDevedor(saldoDevedor)
                    .provisao(provisao.setScale(2, RoundingMode.HALF_UP))
                    .valorParcela(valorParcela)
                    .amortizacao(principal)
                    .jurosPagos(jurosPagos)
                    .jurosAcumulados(diaPagamento ? BigDecimal.ZERO : jurosAcumulados)
                    .numeroParcela(numeroParcela)
                    .valorPrincipal(dataAtual.equals(emprestimoDTO.getDataInicio()) ? valorEmprestimo : BigDecimal.ZERO)
                    .build();

            detalhes.add(detalhe);
            dataAnterior = dataAtual;
        }

        return detalhes;
    }

    public int calcularNumeroParc(LocalDate dataPrimeiroPag, LocalDate dataFinal) {
        long meses = ChronoUnit.MONTHS.between(
                dataPrimeiroPag.withDayOfMonth(1),
                dataFinal.withDayOfMonth(dataFinal.lengthOfMonth())
        );

        return (int) meses + 1;
    }

    private BigDecimal calcularProvisaoJuros(BigDecimal saldo, BigDecimal acumulado, BigDecimal taxa, long dias) {
        return calculoJurosService.calcularJuros(saldo, acumulado, taxa, dias)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
