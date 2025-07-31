import { useState, useEffect } from 'react';
import { simulateEmp } from '../../../services/api.js';
import { EmprestimoRequest, EmprestimoResponse } from '../../../types/types';
import { validateDates } from '../../../util/validators';

export const useEmprestimo = () => {

  const [formData, setFormData] = useState<EmprestimoRequest>({
    dataInicio: '',
    dataTermino: '',
    primeiroPagamento: '',
    valor: '',
    taxaJuros: ''
  });

  const [emprestimoResponse, setEmprestimoResponse] = useState<EmprestimoResponse | null>(null);

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    const isValid = Object.values(formData).every(value => value !== '') && !error;
    setIsFormValid(isValid);
  }, [formData, error]);

  const handleChange = (name: string, value: string) => {
    if (name === 'clear') {
      setFormData({
        dataInicio: '',
        dataTermino: '',
        primeiroPagamento: '',
        valor: '',
        taxaJuros: ''
      });
      setEmprestimoResponse(null);
      setError('');
      return;
    }

    setFormData(prev => ({ ...prev, [name]: value }));
    
    const [isValid, errorMsg] = validateDates({ ...formData, [name]: value });
    setError(errorMsg);
  };

 const handleSubmit = async (formValues: {
    dataInicio: string;
    dataTermino: string;
    primeiroPagamento: string;
    valor: string;
    taxaJuros: number; 
  }) => {
    setIsLoading(true);
    setError('');
    
    const [isValid, errorMsg] = validateDates(formData);
    if (!isValid || !isFormValid) {
      setError(errorMsg);
      return;
    }

    setIsLoading(true);
    setError('');

    try {
    const response = await simulateEmp({
        dataInicio: formValues.dataInicio,
        dataTermino: formValues.dataTermino,
        primeiroPagamento: formValues.primeiroPagamento,
        valor: formValues.valor,
        taxaJuros: formValues.taxaJuros
      });
      
      if (response && response.data && Array.isArray(response.data.detalhes)) {
        setEmprestimoResponse(response);
      } else {
        throw new Error('Formato de resposta inesperado da API');
      }
    } catch (error) {
      console.error("Erro na simulação:", error);
      setError(error instanceof Error ? error.message : "Erro ao calcular empréstimo");
    } finally {
      setIsLoading(false);
    }
  };

 
  return {
    formData,
    emprestimoResponse, 
    isLoading, 
    error,             
    isFormValid,        
    handleChange,       
    handleSubmit         
  };
};