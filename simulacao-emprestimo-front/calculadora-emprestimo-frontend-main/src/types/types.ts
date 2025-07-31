export interface EmprestimoRequest {
  dataInicio: string;
  dataTermino: string;
  primeiroPagamento: string;
  valor: string;
  taxaJuros: string;
}

export interface DadosEmprestimo {
  dataVencimento: string;
  valorPrincipal: number;
  saldoDevedor: number;
  numeroParcela: string;
  valorParcela: number;
  amortizacao: number;
  saldo: number;
  provisao: number;
  jurosAcumulados: number;
  jurosPagos: number;
}

export interface EmprestimoResponse {
  data: {
    detalhes: DadosEmprestimo[];
  };
}