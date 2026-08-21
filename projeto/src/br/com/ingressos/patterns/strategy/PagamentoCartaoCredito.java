package br.com.ingressos.patterns.strategy;

public class PagamentoCartaoCredito implements EstrategiaPagamento {

	private final String numeroCartao;
	private final int parcelas;

	public PagamentoCartaoCredito(String numeroCartao, int parcelas) {
		this.numeroCartao = numeroCartao;
		this.parcelas = parcelas;
	}

	@Override
	public boolean pagar(double valor) {
		String final4Digitos = numeroCartao.substring(Math.max(0, numeroCartao.length() - 4));
		double valorParcela = valor / parcelas;
		System.out.printf("Cobrando R$ %.2f em %dx de R$ %.2f no cartão final %s...%n",
				valor, parcelas, valorParcela, final4Digitos);
		System.out.println("Pagamento no cartão de crédito aprovado.");
		return true;
	}

	@Override
	public String getNome() {
		return "Cartão de Crédito";
	}
}
