package br.com.zup.inventory.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.zup.inventory.entity.Inventory;
import br.com.zup.inventory.entity.OrderItem;
import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.event.PurchaseCreatedEvent;
import br.com.zup.inventory.repository.InventoryRepository;
import br.com.zup.inventory.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private KafkaTemplate<String, PurchaseCreatedEvent> template;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventory, KafkaTemplate<String, PurchaseCreatedEvent> template) {
        this.inventoryRepository = inventory;
        this.template = template;
    }

	@Override
	public boolean purchaseOrder(OrderCreatedEvent orderEvent) {
		try {
			Inventory inventory = this.inventoryRepository.findAll().get(0);
			PurchaseCreatedEvent purchaseCreatedEvent = new PurchaseCreatedEvent(orderEvent, inventory.getQuantity(), new BigDecimal(0));

			Integer totalAvailable = Integer.sum(inventory.getQuantity(), -1*inventory.getProvisioned());
			Integer totalTickets = 0;
			BigDecimal totalAmountOrder = purchaseCreatedEvent.getTotalAmount();
			for(OrderItem item: orderEvent.getItemIds()) {
				totalTickets = Integer.sum(totalTickets, item.getQuantity());
				totalAmountOrder = totalAmountOrder.add(item.getAmount());
			}

			if(totalAvailable.compareTo(totalTickets) < 0)
			{
				purchaseCreatedEvent.setDescription("total quantity ".concat(totalTickets + " unavailable."));
				soldOut(purchaseCreatedEvent);
				return false;
			}

			BigDecimal totalAmountInventory = inventory.getAmount().multiply(new BigDecimal(totalTickets));

			if(totalAmountOrder.compareTo(totalAmountInventory) < 0) {
				purchaseCreatedEvent.setDescription("Insufficient funds.");
				purchaseCreatedEvent.setTotalAmount(totalAmountOrder);
				soldOut(purchaseCreatedEvent);
				return false;
			}
			purchaseCreatedEvent.setQuantityPurchase(totalTickets);
			purchaseCreatedEvent.setTotalAmount(totalAmountOrder);
			purchaseCreatedEvent.setDescription("ok");
			this.template.send("ticket-booked-orders", purchaseCreatedEvent);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void soldOut(PurchaseCreatedEvent purchaseCreatedEvent) {
		this.template.send("sold-out-orders", purchaseCreatedEvent);
	}

	@Override
	public void failurePayment(PurchaseCreatedEvent purchaseCreatedEvent) {
		Inventory inventory = this.inventoryRepository.findAll().get(0);
		inventory.setProvisioned(Integer.sum(inventory.getProvisioned(), -1*purchaseCreatedEvent.getQuantityPurchase()));
		this.inventoryRepository.save(inventory);

		this.template.send("sold-out-orders", purchaseCreatedEvent);
	}

	@Override
	public void successPaymentOrders(PurchaseCreatedEvent purchaseCreatedEvent) {
		Inventory inventory = this.inventoryRepository.findAll().get(0);

		inventory.setProvisioned(Integer.sum(inventory.getProvisioned(), purchaseCreatedEvent.getQuantityPurchase()));
		this.inventoryRepository.save(inventory);
	}
}