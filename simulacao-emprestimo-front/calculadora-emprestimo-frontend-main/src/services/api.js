import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
});

export const simulateEmp = (emprestimoData) => {
  return api.post('/api/v1/emprestimos/calcular_simulacao', emprestimoData, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
};