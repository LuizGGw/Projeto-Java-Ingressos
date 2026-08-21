# 🎟️ Padrões de Projeto na Prática — Sistema de Venda de Ingressos

Projeto desenvolvido como desafio de código do bootcamp da **Digital Innovation One (DIO)**, com base no laboratório [`lab-padroes-projeto-java`](https://github.com/digitalinnovationone/lab-padroes-projeto-java).

Em vez de apenas reproduzir os exemplos originais (Robô, CRM, CEP), evoluí o projeto aplicando os mesmos padrões de projeto (Design Patterns) a um domínio próprio: um **sistema de venda de ingressos com controle de assentos**, em **Java puro** (sem frameworks).

## 🧠 Padrões de Projeto aplicados

### 1. Singleton
- `ConfiguracaoSistema` — implementado como **Lazy Holder (Bill Pugh Singleton)**, thread-safe sem uso de `synchronized`, guardando configurações globais (TTL do lock de assento, taxa de serviço).
- `CacheDeAssentos` — implementado como **Enum Singleton** (abordagem recomendada por Joshua Bloch em *Effective Java*), simulando em memória o comportamento de um cache distribuído (como o Redis) usado para travar um assento por um tempo determinado (TTL) durante o processo de compra, evitando que dois usuários reservem o mesmo assento simultaneamente.

### 2. Strategy
- `EstrategiaPagamento` (interface) com três implementações intercambiáveis: `PagamentoPix`, `PagamentoCartaoCredito` e `PagamentoBoleto`.
- `CarrinhoCompra` é o *contexto* do padrão: não conhece os detalhes de cada forma de pagamento, apenas delega a execução para a estratégia configurada em tempo de execução.

### 3. Facade
- `VendaIngressoFacade` oferece um único ponto de entrada (`comprarIngresso(...)`) que orquestra três subsistemas independentes e complexos:
  - `EstoqueAssentosService` (controle/lock de assentos)
  - `PagamentoGatewayService` (processamento de pagamento, reaproveitando o Strategy)
  - `NotificacaoService` (envio de confirmação/falha ao cliente)
- O código cliente (`Main`) não precisa conhecer a ordem correta das chamadas nem tratar o rollback manualmente (ex: liberar o assento caso o pagamento falhe) — tudo isso fica encapsulado no Facade.

## 📂 Estrutura do projeto

```
src/br/com/ingressos/patterns/
├── Main.java
├── singleton/
│   ├── ConfiguracaoSistema.java
│   └── CacheDeAssentos.java
├── strategy/
│   ├── EstrategiaPagamento.java
│   ├── PagamentoPix.java
│   ├── PagamentoCartaoCredito.java
│   ├── PagamentoBoleto.java
│   └── CarrinhoCompra.java
└── facade/
    ├── VendaIngressoFacade.java
    └── subsistemas/
        ├── EstoqueAssentosService.java
        ├── PagamentoGatewayService.java
        └── NotificacaoService.java
```

## ▶️ Como executar

Pré-requisito: JDK 17+ instalado.

```bash
# Compilar
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d bin @sources.txt

# Executar
java -cp bin br.com.ingressos.patterns.Main
```

## 💡 Principais aprendizados

- **Singleton**: existem várias formas de implementar (eager, lazy, lazy holder, enum) e cada uma tem trade-offs de performance, thread-safety e simplicidade.
- **Strategy**: permite adicionar novas formas de pagamento (ex: `PagamentoCriptomoeda`) sem alterar nenhuma linha do código existente — só criar uma nova classe que implementa `EstrategiaPagamento` (aberto para extensão, fechado para modificação — princípio Open/Closed do SOLID).
- **Facade**: reduz o acoplamento entre o código cliente e a complexidade interna de múltiplos subsistemas, tornando a API mais simples de usar e mais fácil de manter.

---
Baseado no laboratório da [DIO](https://www.dio.me).
