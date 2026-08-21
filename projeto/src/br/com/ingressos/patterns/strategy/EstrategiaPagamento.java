package br.com.ingressos.patterns.strategy;

/**
 * Strategy: contrato comum para as diferentes formas de pagamento
 * disponíveis na compra de um ingresso.
 *
 * Cada implementação encapsula seu próprio algoritmo/regra de negócio,
 * permitindo trocar a forma de pagamento em tempo de execução sem
 * alterar o código do "contexto" (CarrinhoCompra).
 */
public interface EstrategiaPagamento {

	/**
	 * @param valor valor total da compra
	 * @return true se o pagamento foi aprovado
	 */
	boolean pagar(double valor);

	/**
	 * Nome amigável da estratégia, usado para exibir no comprovante.
	 */
	String getNome();
}
