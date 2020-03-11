package br.com.zup.inventory.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.zup.inventory.entity.OrderItem;

public class OrderCreatedEvent {

	private String orderId;
    private String customerId;
    private BigDecimal amount;
    private LocalDateTime time;
    private List<OrderItem> itemIds;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String customerId, BigDecimal amount, LocalDateTime time, List<OrderItem> itemIds) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.itemIds = itemIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<OrderItem> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<OrderItem> itemIds) {
        this.itemIds = itemIds;
    }

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
}
