import { EmprestimoRequest } from "../types/types";

export const validateDates = (formData: EmprestimoRequest): [boolean, string] => {
  const { dataInicio, dataTermino, primeiroPagamento } = formData;
  
  if (!dataInicio || !dataTermino || !primeiroPagamento) {
    return [false, ''];
  }

  if (new Date(dataTermino) <= new Date(dataInicio)) {
    return [false, 'A data final deve ser maior que a data inicial.'];
  }
  if (new Date(primeiroPagamento) <= new Date(dataInicio)) {
    return [false, 'A data do primeiro pagamento deve ser maior que a data inicial.'];
  }
  if (new Date(primeiroPagamento) >= new Date(dataTermino)) {
    return [false, 'A data do primeiro pagamento deve ser menor que a data final.'];
  }
  
  return [true, ''];
};