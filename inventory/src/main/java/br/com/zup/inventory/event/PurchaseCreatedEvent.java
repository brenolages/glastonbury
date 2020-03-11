package br.com.zup.inventory.event;

import java.math.BigDecimal;

public class PurchaseCreatedEvent {

	private OrderCreatedEvent orderEvent;
    private int quantityPurchase;
    private BigDecimal totalAmount;
    private String description;

    public PurchaseCreatedEvent() {
    }

    public PurchaseCreatedEvent(OrderCreatedEvent orderEvent, int quantityPurchase, BigDecimal totalAmount) {
        this.orderEvent = orderEvent;
        this.quantityPurchase = quantityPurchase;
        this.totalAmount = totalAmount;
    }

	public OrderCreatedEvent getOrderEvent() {
		return orderEvent;
	}

	public void setOrderEvent(OrderCreatedEvent orderEvent) {
		this.orderEvent = orderEvent;
	}

	public int getQuantityPurchase() {
		return quantityPurchase;
	}

	public void setQuantityPurchase(int quantityPurchase) {
		this.quantityPurchase = quantityPurchase;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
