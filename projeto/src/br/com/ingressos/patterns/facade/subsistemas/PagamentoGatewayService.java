package br.com.ingressos.patterns.facade.subsistemas;

import br.com.ingressos.patterns.strategy.CarrinhoCompra;
import br.com.ingressos.patterns.strategy.EstrategiaPagamento;

/**
 * Subsistema 2: responsável por processar o pagamento.
 * Internamente reaproveita o padrão Strategy já implementado.
 */
public class PagamentoGatewayService {

	public boolean processarPagamento(double valorIngresso, EstrategiaPagamento estrategia) {
		CarrinhoCompra carrinho = new CarrinhoCompra();
		carrinho.adicionarIngresso(valorIngresso);
		carrinho.setEstrategiaPagamento(estrategia);
		return carrinho.finalizarCompra();
	}
}
