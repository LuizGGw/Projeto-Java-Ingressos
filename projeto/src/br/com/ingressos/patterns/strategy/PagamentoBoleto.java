package br.com.ingressos.patterns.strategy;

public class PagamentoBoleto implements EstrategiaPagamento {

	@Override
	public boolean pagar(double valor) {
		System.out.printf("Gerando boleto no valor de R$ %.2f, vencimento em 3 dias úteis...%n", valor);
		System.out.println("Boleto emitido. Pagamento pendente de compensação.");
		// Regra de negócio: boleto não é aprovado na hora.
		return false;
	}

	@Override
	public String getNome() {
		return "Boleto Bancário";
	}
}
