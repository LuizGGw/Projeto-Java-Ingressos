package br.com.ingressos.patterns.facade.subsistemas;

import br.com.ingressos.patterns.singleton.CacheDeAssentos;

/**
 * Subsistema 1: responsável por controlar a disponibilidade dos assentos.
 * Simula a mesma lógica de locking distribuído (Redis + TTL) discutida
 * no contexto de venda de ingressos.
 */
public class EstoqueAssentosService {

	public boolean reservarAssento(String idAssento) {
		boolean travou = CacheDeAssentos.INSTANCIA.tentarTravar(idAssento);
		if (travou) {
			System.out.println("[Estoque] Assento " + idAssento + " travado com sucesso para o comprador.");
		} else {
			System.out.println("[Estoque] Assento " + idAssento + " já está reservado por outro usuário.");
		}
		return travou;
	}

	public void confirmarVenda(String idAssento) {
		// Após o pagamento aprovado, o assento sai do "lock temporário"
		// e passa a ser considerado definitivamente vendido.
		System.out.println("[Estoque] Assento " + idAssento + " confirmado como VENDIDO.");
	}

	public void liberarAssento(String idAssento) {
		CacheDeAssentos.INSTANCIA.liberar(idAssento);
		System.out.println("[Estoque] Assento " + idAssento + " liberado novamente para venda.");
	}
}
