package br.com.ingressos.patterns.singleton;

/**
 * Singleton "Lazy Holder" (Bill Pugh Singleton).
 *
 * Representa as configurações globais do sistema de venda de ingressos
 * (ex.: tempo de expiração do lock de assento, taxa de serviço, etc).
 *
 * Por que esse padrão?
 * - É thread-safe SEM precisar de blocos "synchronized" (o que evita
 *   custo de performance em ambientes concorrentes, como uma API que
 *   recebe muitas requisições simultâneas de compra de ingresso).
 * - A instância só é criada quando "InstanceHolder" é referenciada
 *   pela primeira vez (lazy initialization), aproveitando a garantia
 *   da JVM de que o carregamento de uma classe é uma operação atômica.
 *
 * Evolução em relação ao exemplo original do laboratório: aqui o
 * Singleton carrega estado de configuração real, não fica vazio.
 */
public class ConfiguracaoSistema {

	// TTL (em segundos) do lock de reserva de assento, simulando o Redis
	private final int ttlLockAssentoSegundos;

	// Taxa de serviço cobrada em cima do valor do ingresso (%)
	private final double taxaServico;

	private ConfiguracaoSistema() {
		this.ttlLockAssentoSegundos = 300; // 5 minutos
		this.taxaServico = 0.10; // 10%
	}

	private static class InstanceHolder {
		private static final ConfiguracaoSistema INSTANCIA = new ConfiguracaoSistema();
	}

	public static ConfiguracaoSistema getInstancia() {
		return InstanceHolder.INSTANCIA;
	}

	public int getTtlLockAssentoSegundos() {
		return ttlLockAssentoSegundos;
	}

	public double getTaxaServico() {
		return taxaServico;
	}
}
