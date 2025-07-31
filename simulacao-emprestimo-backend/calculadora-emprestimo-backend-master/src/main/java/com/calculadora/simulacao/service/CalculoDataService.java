package com.calculadora.simulacao.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.calculadora.simulacao.util.DataUtil;

@Service
public class CalculoDataService {

    private final DataUtil dataUtil;
    private final CalculoEmprestimoService calculoEmprestimoService;

    public CalculoDataService(DataUtil dataUtil, CalculoEmprestimoService calculoEmprestimoService) {
        this.dataUtil = dataUtil;
        this.calculoEmprestimoService = calculoEmprestimoService;
    }

    public List<LocalDate> gerarDatasAcumuladas(LocalDate dataInicio, LocalDate dataTermino, LocalDate primeiroPagamento) {
        Set<LocalDate> datas = new LinkedHashSet<>();
        datas.add(dataInicio);

        adicionarUltimoDiaDoMes(dataInicio, primeiroPagamento, datas);
        adicionarDataSeValida(primeiroPagamento, dataInicio, dataTermino, datas);

        int totalParcelas = calculoEmprestimoService.calcularNumeroParc(primeiroPagamento, dataTermino);

        LocalDate dataAtual = primeiroPagamento;

        for (int i = 1; i <= totalParcelas; i++) {
            LocalDate ultimoDiaMesAnterior = dataAtual.minusMonths(1).withDayOfMonth(
                    dataAtual.minusMonths(1).lengthOfMonth()
            );
            if (ultimoDiaMesAnterior.isAfter(dataInicio) && !datas.contains(ultimoDiaMesAnterior)) {
                datas.add(dataUtil.tratarParaDiaUtil(ultimoDiaMesAnterior));
            }

            int diaPagamento = Math.min(primeiroPagamento.getDayOfMonth(), dataAtual.lengthOfMonth());
            LocalDate dataPagamento = dataAtual.withDayOfMonth(diaPagamento);
            datas.add(dataUtil.tratarParaDiaUtil(dataPagamento));

            dataAtual = dataAtual.plusMonths(1);
        }

        if (!datas.contains(dataTermino)) {
            datas.add(dataUtil.tratarParaDiaUtil(dataTermino));
        }

        return new ArrayList<>(datas);
    }

    private void adicionarUltimoDiaDoMes(LocalDate dataInicial, LocalDate dataPrimeiroPagamento, Set<LocalDate> datas) {
        LocalDate ultimoDiaMes = dataInicial.withDayOfMonth(dataInicial.lengthOfMonth());
        if (!ultimoDiaMes.equals(dataInicial) && ultimoDiaMes.isBefore(dataPrimeiroPagamento)) {
            datas.add(dataUtil.tratarParaDiaUtil(ultimoDiaMes));
        }
    }

    private void adicionarDataSeValida(LocalDate data, LocalDate dataInicio, LocalDate dataFinal, Set<LocalDate> datas) {
        if (data.isAfter(dataInicio) && !data.isAfter(dataFinal)) {
            datas.add(dataUtil.tratarParaDiaUtil(data));
        }
    }
}