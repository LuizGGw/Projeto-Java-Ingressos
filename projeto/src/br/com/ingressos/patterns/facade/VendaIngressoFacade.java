package br.com.ingressos.patterns.facade;

import br.com.ingressos.patterns.facade.subsistemas.EstoqueAssentosService;
import br.com.ingressos.patterns.facade.subsistemas.NotificacaoService;
import br.com.ingressos.patterns.facade.subsistemas.PagamentoGatewayService;
import br.com.ingressos.patterns.strategy.EstrategiaPagamento;

/**
 * Facade: fornece uma interface única e simplificada para o fluxo completo
 * de compra de ingresso, escondendo a complexidade da orquestração entre
 * os subsistemas de Estoque, Pagamento e Notificação.
 *
 * Sem o Facade, o código cliente precisaria conhecer e coordenar
 * manualmente os 3 subsistemas (e a ordem correta das chamadas, e o
 * tratamento de falha/rollback). Com o Facade, tudo isso vira uma
 * única chamada: comprarIngresso(...).
 */
public class VendaIngressoFacade {

	private final EstoqueAssentosService estoqueAssentosService;
	private final PagamentoGatewayService pagamentoGatewayService;
	private final NotificacaoService notificacaoService;

	public VendaIngressoFacade() {
		this.estoqueAssentosService = new EstoqueAssentosService();
		this.pagamentoGatewayService = new PagamentoGatewayService();
		this.notificacaoService = new NotificacaoService();
	}

	public void comprarIngresso(String idAssento, double valorIngresso,
			EstrategiaPagamento formaPagamento, String emailCliente) {

		boolean assentoReservado = estoqueAssentosService.reservarAssento(idAssento);
		if (!assentoReservado) {
			System.out.println("Compra cancelada: assento indisponível.");
			return;
		}

		boolean pagamentoAprovado = pagamentoGatewayService.processarPagamento(valorIngresso, formaPagamento);

		if (pagamentoAprovado) {
			estoqueAssentosService.confirmarVenda(idAssento);
			notificacaoService.enviarConfirmacao(emailCliente, idAssento);
		} else {
			// Rollback: libera o assento para não ficar travado indevidamente
			estoqueAssentosService.liberarAssento(idAssento);
			notificacaoService.enviarFalhaPagamento(emailCliente);
		}
	}
}
