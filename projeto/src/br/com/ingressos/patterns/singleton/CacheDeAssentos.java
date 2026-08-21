package br.com.ingressos.patterns.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Singleton implementado com ENUM.
 *
 * Segundo Joshua Bloch (Effective Java), essa é considerada a forma
 * mais segura de implementar um Singleton em Java, pois a própria
 * linguagem garante:
 * - Instância única (a JVM cuida disso na carga da classe);
 * - Proteção contra quebra via Reflection;
 * - Proteção contra quebra via Serialização/Deserialização.
 *
 * Aqui ele simula, em memória, o comportamento de um cache distribuído
 * (como o Redis) usado para "travar" um assento durante o processo de
 * compra, evitando que dois usuários reservem o mesmo lugar ao mesmo
 * tempo (mesma ideia do lock com TTL usado no case de venda de ingressos).
 */
public enum CacheDeAssentos {

	INSTANCIA;

	private final Map<String, Long> assentosTravados = new ConcurrentHashMap<>();

	/**
	 * Tenta travar um assento. Retorna false se ele já estiver travado
	 * por outro usuário e o TTL ainda não tiver expirado.
	 */
	public synchronized boolean tentarTravar(String idAssento) {
		limparLocksExpirados();
		if (assentosTravados.containsKey(idAssento)) {
			return false;
		}
		long ttlMillis = ConfiguracaoSistema.getInstancia().getTtlLockAssentoSegundos() * 1000L;
		assentosTravados.put(idAssento, System.currentTimeMillis() + ttlMillis);
		return true;
	}

	public void liberar(String idAssento) {
		assentosTravados.remove(idAssento);
	}

	private void limparLocksExpirados() {
		long agora = System.currentTimeMillis();
		assentosTravados.entrySet().removeIf(entry -> entry.getValue() < agora);
	}
}
