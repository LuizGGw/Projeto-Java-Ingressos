package br.com.ingressos.patterns.strategy;

public class PagamentoPix implements EstrategiaPagamento {

	private final String chavePix;

	public PagamentoPix(String chavePix) {
		this.chavePix = chavePix;
	}

	@Override
	public boolean pagar(double valor) {
		System.out.printf("Gerando QR Code PIX para a chave '%s' no valor de R$ %.2f...%n", chavePix, valor);
		System.out.println("Pagamento via PIX aprovado instantaneamente.");
		return true;
	}

	@Override
	public String getNome() {
		return "PIX";
	}
}
