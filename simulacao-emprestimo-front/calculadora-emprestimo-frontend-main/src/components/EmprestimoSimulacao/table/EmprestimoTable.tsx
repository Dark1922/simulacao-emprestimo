import React from 'react';
import { DadosEmprestimo } from '../../../types/types';
import { formatCurrency, formatDate } from '../../../util/formatters';
import './EmprestimoTable.css';

interface EmprestimoTableProps {
  dadosEmprestimo: DadosEmprestimo[];
  empAmount: string;
}

export const EmprestimoTable: React.FC<EmprestimoTableProps> = ({ 
  dadosEmprestimo, 
  empAmount 
}) => {
  return (
<div className="table-container">
  <h2 className="table-title">Detalhamento das Parcelas</h2>
  
  <div className="table-responsive">
    <table className="emprestimo-detalhes-table">
      <thead>
        <tr className="main-header">
          <th colSpan={3}>Empréstimo</th>
          <th colSpan={2}>Parcela</th>
          <th colSpan={5}>Principal</th>
        </tr>
        <tr className="sub-header">
          <th>Data Competência</th>
          <th>Valor de Empréstimo</th>
          <th>Saldo Devedor</th>
          <th>Parcela</th>
          <th>Valor Parcela Total</th>
          <th>Amortização</th>
          <th>Saldo</th>
          <th>Provisão</th>
          <th>Juros Acum.</th>
          <th>Valor Pago</th>
        </tr>
      </thead>
      <tbody>
        {dadosEmprestimo.map((item, index) => (
          <tr key={index} className={index % 2 === 0 ? 'even-row' : 'odd-row'}>
            <td>{formatDate(item.dataVencimento)}</td>
            <td>{index === 0 ? formatCurrency(parseFloat(empAmount)) : formatCurrency(0)}</td>
            <td>{formatCurrency(item.saldoDevedor)}</td>
            <td>{item.numeroParcela || " " }</td>
            <td>{formatCurrency(item.valorParcela || 0)}</td>
            <td>{formatCurrency(item.amortizacao || 0)}</td>
            <td>{formatCurrency(item.saldo || 0)}</td>
            <td>{formatCurrency(item.provisao || 0)}</td>
            <td>{formatCurrency(item.jurosAcumulados || 0)}</td>
            <td>{formatCurrency(item.jurosPagos || 0)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
</div>
  );
};