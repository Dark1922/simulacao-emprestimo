import React from 'react';
import { NumericFormat } from 'react-number-format';
import { EmprestimoRequest } from '../../../types/types';
import './EmprestimoForm.css';

interface EmprestimoFormProps {
  formData: EmprestimoRequest;
  onChange: (name: string, value: string) => void;
  onSubmit: (data: {
    dataInicio: string;
    dataTermino: string;
    primeiroPagamento: string;
    valor: string;
    taxaJuros: number; 
  }) => void;
  isLoading: boolean;
  isFormValid: boolean;
  error: string;
}

export const EmprestimoForm: React.FC<EmprestimoFormProps> = ({
  formData,
  onChange,
  onSubmit,
  isLoading,
  isFormValid,
  error
}) => {
  const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.name, e.target.value);
  };

  const handleTaxaChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/\D/g, '').slice(0, 2);
    onChange('taxaJuros', value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!isFormValid) return;

    const payload = {
      dataInicio: formData.dataInicio,
      dataTermino: formData.dataTermino,
      primeiroPagamento: formData.primeiroPagamento,
      valor: formData.valor,
      taxaJuros: parseFloat(formData.taxaJuros) / 100
    };

    onSubmit(payload);
  };

  return (
    <div className="form-container">
      <div className="form-header">
        <h2>Simulação de Empréstimo</h2>
        <p className="form-subtitle">Preencha os dados para simular seu empréstimo</p>
      </div>

      {error && <div className="form-error">{error}</div>}

      <div className="form-grid">
        <div className="form-group">
          <label>Data Inicial</label>
          <input
            type="date"
            name="dataInicio"
            value={formData.dataInicio}
            onChange={handleDateChange}
            className="form-input"
            required
          />
        </div>

        <div className="form-group">
          <label>Data Final</label>
          <input
            type="date"
            name="dataTermino"
            value={formData.dataTermino}
            onChange={handleDateChange}
            className="form-input"
            min={formData.dataInicio}
            required
          />
        </div>

        <div className="form-group">
          <label>Primeiro Pagamento</label>
          <input
            type="date"
            name="primeiroPagamento"
            value={formData.primeiroPagamento}
            onChange={handleDateChange}
            className="form-input"
            min={formData.dataInicio}
            max={formData.dataTermino}
            required
          />
        </div>

        <div className="form-group">
          <label>Valor do Empréstimo (R$)</label>
          <NumericFormat
            value={formData.valor}
            onValueChange={({ value }) => onChange('valor', value)}
            thousandSeparator="."
            decimalSeparator=","
            decimalScale={2}
            fixedDecimalScale
            className="form-input"
            placeholder="0,00"
            allowNegative={false}
            required
          />
        </div>

        <div className="form-group">
          <label>Taxa de Juros (%)</label>
          <div className="input-with-suffix">
            <input
              type="text"
              name="taxaJuros"
              value={formData.taxaJuros}
              onChange={handleTaxaChange}
              className="form-input"
              placeholder="5"
              maxLength={2}
              required
            />
            <span className="input-suffix">%</span>
          </div>
          <small className="input-hint">Digite apenas números (ex: 5 para 5%)</small>
        </div>
      </div>

      <div className="form-actions">
        <button
          type="submit"
          onClick={handleSubmit}
          className="btn btn-primary"
          disabled={!isFormValid || isLoading}
        >
          {isLoading ? (
            <>
              <span className="spinner"></span>
              Calculando...
            </>
          ) : (
            'Simular Empréstimo'
          )}
        </button>
        <button
          type="button"
          className="btn btn-secondary"
          onClick={() => onChange('clear', '')}
        >
          Limpar Campos
        </button>
      </div>
    </div>
  );
};