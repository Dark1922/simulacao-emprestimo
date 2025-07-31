package com.calculadora.simulacao.service;

import com.calculadora.simulacao.dto.EmprestimoDetDTO;
import com.calculadora.simulacao.dto.EmprestimoReqDTO;
import com.calculadora.simulacao.dto.EmprestimoRespDTO;
import com.calculadora.simulacao.model.Emprestimo;
import com.calculadora.simulacao.util.EmprestimoUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoUtil emprestimoUtil;
    private final CalculoEmprestimoService calculoEmprestimoService;
    private final CalculoDataService calculoDataService;

    public EmprestimoService(
            EmprestimoUtil emprestimoUtil,
            CalculoEmprestimoService calculoEmprestimoService,
            CalculoDataService calculoDataService
    ) {
        this.emprestimoUtil = emprestimoUtil;
        this.calculoEmprestimoService = calculoEmprestimoService;
        this.calculoDataService = calculoDataService;
    }

    public EmprestimoRespDTO simularEmprestimo(EmprestimoReqDTO requisicao) {
        Emprestimo emprestimo = emprestimoUtil.buildEmprestimo(requisicao);

        List<LocalDate> datasParcelas = calcularDatasParcelas(emprestimo);
        int totalParcelas = calcularQuantidadeParcelas(emprestimo);

        List<EmprestimoDetDTO> detalhes = calculoEmprestimoService.calcularDetalhesParcelas(
                emprestimo, datasParcelas, totalParcelas
        );

        return new EmprestimoRespDTO(detalhes);

    }

    private List<LocalDate> calcularDatasParcelas(Emprestimo emprestimo) {
        return calculoDataService.gerarDatasAcumuladas(
                emprestimo.getDataInicio(),
                emprestimo.getDataTermino(),
                emprestimo.getPrimeiroPagamento()
        );
    }

    private int calcularQuantidadeParcelas(Emprestimo emprestimo) {
        return calculoEmprestimoService.calcularNumeroParc(
                emprestimo.getPrimeiroPagamento(),
                emprestimo.getDataTermino()
        );
    }
}