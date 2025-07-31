package com.calculadora.simulacao.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

@Component
public class DataUtil {

    public LocalDate tratarParaDiaUtil(LocalDate date) {
        DayOfWeek dia = date.getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY) {
            return date.plusDays(2);
        } else if (dia == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        }
        return date;
    }

    public boolean isPagamentoDiario(LocalDate dataAtual, LocalDate dataPrimeiroPagamento) {
        int mesesCorrido = (int) ChronoUnit.MONTHS.between(
            dataPrimeiroPagamento.withDayOfMonth(1),
            dataAtual.withDayOfMonth(1)
        );

        LocalDate dataPagamentoCorrente = dataPrimeiroPagamento.plusMonths(mesesCorrido);

        LocalDate ajusteData = tratarParaDiaUtil(dataPagamentoCorrente);

        return dataAtual.equals(ajusteData);
    }
}
