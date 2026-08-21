package br.com.ingressos.patterns;

import br.com.ingressos.patterns.facade.VendaIngressoFacade;
import br.com.ingressos.patterns.singleton.CacheDeAssentos;
import br.com.ingressos.patterns.singleton.ConfiguracaoSistema;
import br.com.ingressos.patterns.strategy.PagamentoBoleto;
import br.com.ingressos.patterns.strategy.PagamentoCartaoCredito;
import br.com.ingressos.patterns.strategy.PagamentoPix;

/**
 * Classe de demonstração dos padrões de projeto aplicados a um
 * sistema de venda de ingressos.
 */
public class Main {

	public static void main(String[] args) {

		System.out.println("========== 1. SINGLETON ==========");
		// Independentemente de quantas vezes chamarmos getInstancia(),
		// o hashCode impresso será sempre o mesmo.
		ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstancia();
		ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstancia();
		System.out.println("config1 == config2 ? " + (config1 == config2));
		System.out.println("TTL configurado do lock: " + config1.getTtlLockAssentoSegundos() + "s");

		System.out.println();
		System.out.println("========== 2. STRATEGY (via Facade) ==========");
		VendaIngressoFacade venda = new VendaIngressoFacade();

		// Compra 1: pagamento aprovado no PIX
		venda.comprarIngresso(
				"A-12",
				150.00,
				new PagamentoPix("cliente@email.com"),
				"cliente@email.com");

		System.out.println();

		// Compra 2: tentativa de reservar o MESMO assento (simula concorrência)
		venda.comprarIngresso(
				"A-12",
				150.00,
				new PagamentoCartaoCredito("1234567890123456", 3),
				"outro-cliente@email.com");

		System.out.println();

		// Compra 3: assento diferente, pagamento no cartão em 3x
		venda.comprarIngresso(
				"B-07",
				200.00,
				new PagamentoCartaoCredito("9876543210123456", 3),
				"maria@email.com");

		System.out.println();

		// Compra 4: pagamento via boleto (não aprovado na hora -> assento é liberado)
		venda.comprarIngresso(
				"C-03",
				180.00,
				new PagamentoBoleto(),
				"joao@email.com");

		System.out.println();
		System.out.println("========== 3. FACADE ==========");
		System.out.println("Note que 'VendaIngressoFacade.comprarIngresso(...)' escondeu toda a");
		System.out.println("complexidade de orquestrar Estoque + Pagamento + Notificação em uma única chamada.");

		System.out.println();
		System.out.println("Assento C-03 foi liberado após falha no boleto? "
				+ CacheDeAssentos.INSTANCIA.tentarTravar("C-03"));
	}
}
