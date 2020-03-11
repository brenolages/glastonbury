package br.com.zup.payment.service;

import br.com.zup.payment.event.PurchaseCreatedEvent;

public interface PaymentService {

	void bookedOrder(PurchaseCreatedEvent purchaseCreatedEvent);
}
