package com.calculadora.simulacao.controller;


import com.calculadora.simulacao.validation.ValidadorEmprestimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calculadora.simulacao.dto.EmprestimoReqDTO;
import com.calculadora.simulacao.dto.EmprestimoRespDTO;
import com.calculadora.simulacao.service.EmprestimoService;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/calcular_simulacao")
    public ResponseEntity<EmprestimoRespDTO> calcularEmprestimo(@RequestBody EmprestimoReqDTO emprestimoReqDTO) {
        ValidadorEmprestimo.validar(emprestimoReqDTO);
        return ResponseEntity.ok(emprestimoService.simularEmprestimo(emprestimoReqDTO));
    }
}
