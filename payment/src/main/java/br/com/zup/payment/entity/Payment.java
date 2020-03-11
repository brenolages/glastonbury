package br.com.zup.payment.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "payment")
public class Payment {

    @Id
    private String id;

    private String customer;

    private BigDecimal totalAmount;

    private Integer quantity;


    public Payment() {
    }

    public Payment(String id, String customer, BigDecimal totalAmount, Integer quantity) {
        this.id = id;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}