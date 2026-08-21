package br.com.ingressos.patterns.strategy;

import br.com.ingressos.patterns.singleton.ConfiguracaoSistema;

/**
 * Contexto do padrão Strategy (equivalente ao "Robo" do exemplo original).
 *
 * O carrinho não sabe (e não precisa saber) COMO cada forma de pagamento
 * funciona: ele apenas delega essa responsabilidade para a estratégia
 * configurada em tempo de execução, via setEstrategiaPagamento().
 */
public class CarrinhoCompra {

	private EstrategiaPagamento estrategiaPagamento;
	private double valorIngressos;

	public void adicionarIngresso(double valor) {
		this.valorIngressos += valor;
	}

	public void setEstrategiaPagamento(EstrategiaPagamento estrategiaPagamento) {
		this.estrategiaPagamento = estrategiaPagamento;
	}

	public boolean finalizarCompra() {
		if (estrategiaPagamento == null) {
			throw new IllegalStateException("Nenhuma forma de pagamento foi selecionada.");
		}
		double taxa = ConfiguracaoSistema.getInstancia().getTaxaServico();
		double valorTotal = valorIngressos * (1 + taxa);

		System.out.println("--- Forma de pagamento: " + estrategiaPagamento.getNome() + " ---");
		boolean aprovado = estrategiaPagamento.pagar(valorTotal);

		// Reseta o carrinho para uma nova compra
		this.valorIngressos = 0;
		return aprovado;
	}
}
