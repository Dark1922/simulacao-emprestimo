import React from 'react';
import { useEmprestimo } from '../../../src/components/EmprestimoSimulacao/hooks/useEmprestimo';
import { EmprestimoForm } from '../../../src/components/EmprestimoSimulacao/form/EmprestimoForm';
import { EmprestimoTable } from '../../..//src/components/EmprestimoSimulacao/table/EmprestimoTable';
import './emprestimoSimulacao.css';

const empSimult = () => {
 
    const {
    formData,
    emprestimoResponse,
    isLoading,
    error,
    isFormValid,
    handleChange,
    handleSubmit 
  } = useEmprestimo();

    const handleFormSubmit = (formValues: {
    dataInicio: string;
    dataTermino: string;
    primeiroPagamento: string;
    valor: string;
    taxaJuros: number;
  }) => {
    handleSubmit(formValues);
  };

  return (
    <div className="emp-simulation-container">
      <div className="emp-simulation-card">
        
        <EmprestimoForm
          formData={formData}
          onChange={handleChange}
          onSubmit={handleFormSubmit}
          isLoading={isLoading}
          isFormValid={isFormValid}
          error={error}
        />
        
        {emprestimoResponse?.data?.detalhes && emprestimoResponse.data.detalhes.length > 0 && (
          <EmprestimoTable 
            dadosEmprestimo={emprestimoResponse.data.detalhes} 
            loanAmount={formData.valor} 
          />
        )}
      </div>
    </div>
  );
};

export default empSimult;