package br.com.ingressos.patterns.facade.subsistemas;

/**
 * Subsistema 3: responsável por avisar o cliente sobre o resultado da compra.
 * Em um cenário real, poderia disparar e-mail, SMS, push notification, etc.
 */
public class NotificacaoService {

	public void enviarConfirmacao(String emailCliente, String idAssento) {
		System.out.println("[Notificação] E-mail enviado para " + emailCliente
				+ ": seu ingresso para o assento " + idAssento + " foi confirmado!");
	}

	public void enviarFalhaPagamento(String emailCliente) {
		System.out.println("[Notificação] E-mail enviado para " + emailCliente
				+ ": não foi possível concluir o pagamento do seu ingresso.");
	}
}
