package br.com.zup.inventory.service;

import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.event.PurchaseCreatedEvent;

public interface InventoryService {

	boolean purchaseOrder(OrderCreatedEvent orderEvent);
	void failurePayment(PurchaseCreatedEvent purchaseCreatedEvent);
	void successPaymentOrders(PurchaseCreatedEvent purchaseCreatedEvent);
}
