# API e Front de Simulação de Empréstimos

## 📌 Visão Geral
Este projeto consiste em uma API REST robusta desenvolvida com **Spring Boot** para cálculos financeiros de empréstimos. A solução oferece:

- 📅 Simulação completa de financiamentos
- 🧮 Cálculo preciso de parcelas (PRICE)
- 📊 Detalhamento de amortização e juros
- 📈 Projeção do saldo devedor

## ✨ Funcionalidades Principais
- **Cálculo de Parcelas**: Gera o plano de pagamentos com valores precisos
- **Detalhamento Financeiro**: Fornece a divisão entre amortização e juros em cada parcela
- **Projeção Temporal**: Calcula datas exatas de vencimento
- **Múltiplas Visualizações**: Retorna dados brutos (JSON) ou formatados para aplicações front-end

## 🛠️ Tecnologias Utilizadas
- Java 17
- Spring Boot 3.x
- Maven
- Lombok


## 📋 Requisitos
- JDK 17+
- Maven 3.6+

<img width="908" height="557" alt="1" src="https://github.com/user-attachments/assets/5d334c6b-2eb7-4d11-b7a5-0f40917b0872" />

<img width="1038" height="443" alt="2" src="https://github.com/user-attachments/assets/99818264-1d53-4e3a-b873-02975a3c7c27" />

<img width="850" height="451" alt="3" src="https://github.com/user-attachments/assets/52465b94-6b55-4aea-931d-98690e619342" />

<img width="633" height="434" alt="4" src="https://github.com/user-attachments/assets/219412a3-9cdc-4020-b0e4-ba79f0f3c20c" />

## 🚀 Como Executar
```bash
mvn spring-boot:run


## Como usar


2. **Build**:  
   ```
   mvn clean install
   ```
3. **Executar**:  
   ```
   mvn spring-boot:run
   ```

   **Front**:  
   ```
   clonar o projeto e dar os seguintes comando:
   npm install
   npm run dev
   ```


4. ## Exemplo de Requisição

```json
POST /api/v1/emprestimos/calcular_simulacao
Content-Type: application/json

{
  "dataInicio": "2024-01-01",
  "dataTermino": "2034-01-01",
  "primeiroPagamento": "2024-02-15",
  "valor": 140000,
  "taxaJuros": 0.05
}
```

## Exemplo de Resposta

```json
{
  [
    {
            "dataVencimento": "2024-01-31",
            "valorPrincipal": 0,
            "saldoDevedor": 140570.38,
            "numeroParcela": "",
            "valorParcela": 0,
            "amortizacao": 0,
            "saldo": 140000.00,
            "provisao": 570.38,
            "jurosAcumulados": 570.38,
            "jurosPagos": 0
        },
    ...
  ]
}
```
