package com.calculadora.simulacao.validation;

import com.calculadora.simulacao.dto.EmprestimoReqDTO;
import com.calculadora.simulacao.exception.DadosEmprestimoInvalidosException;
import com.calculadora.simulacao.exception.RegraNegocioException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidadorEmprestimo {

    public static void validar(EmprestimoReqDTO requisicao) {
        List<String> erros = new ArrayList<>();

        // Validação concisa com mensagens customizadas
        Validate.notNull(requisicao, "Requisição não pode ser nula");

        try {
            // Valida campos nulos
            validarCampoNulo(requisicao.getValor(), "valor", erros);
            validarCampoNulo(requisicao.getTaxaJuros(), "taxaJuros", erros);
            validarCampoNulo(requisicao.getDataInicio(), "dataInicio", erros);
            validarCampoNulo(requisicao.getDataTermino(), "dataTermino", erros);
            validarCampoNulo(requisicao.getPrimeiroPagamento(), "primeiroPagamento", erros);

            // Validações de valor
            validarPositivo(requisicao.getValor(), "Valor do empréstimo", erros);
            validarPositivo(requisicao.getTaxaJuros(), "Taxa de juros", erros);

            if (!erros.isEmpty()) {
                throw new DadosEmprestimoInvalidosException("Dados inválidos: ".concat(erros.toString()));
            }

            // Validações de data
            validarDatas(requisicao.getDataInicio(), requisicao.getDataTermino(),
                    requisicao.getPrimeiroPagamento());

        } catch (IllegalArgumentException e) {
            erros.add(e.getMessage());
        }


    }

    private static void validarCampoNulo(Object campo, String nomeCampo, List<String> erros) {
        if (ObjectUtils.isEmpty(campo)) {
            erros.add(nomeCampo + " não pode ser nulo/vazio");
        }
    }

    private static void validarPositivo(BigDecimal valor, String nomeCampo, List<String> erros) {
        if (valor != null && valor.compareTo(BigDecimal.ZERO) <= 0) {
            erros.add(nomeCampo + " deve ser maior que zero");
        }
    }

    private static void validarDatas(LocalDate inicio, LocalDate termino,
                                     LocalDate primeiroPagamento) {
        // Validação de regras de negócio
        if (termino.isBefore(inicio) ||
                termino.isEqual(inicio)) {
            throw new RegraNegocioException("A data de término deve ser maior que a data de início.");
        }

        if (inicio.isAfter(termino)) {
            throw new IllegalArgumentException("Data de início não pode ser após data de término");
        }

        if (!(primeiroPagamento.isAfter(inicio) &&
                primeiroPagamento.isBefore(termino))) {
            throw new RegraNegocioException("A data do primeiro pagamento deve ser maior que a data de início " +
                    "e antes da data de término.");
        }
    }
}