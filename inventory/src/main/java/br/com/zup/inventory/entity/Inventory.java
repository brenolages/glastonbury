package br.com.zup.inventory.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "inventory")
public class Inventory {

    @Id
    private String id;

    private String partyName;

    private BigDecimal amount;

    private Integer quantity;

    private Integer provisioned;


    public Inventory() {
    }

    public Inventory(String id, String partyName, Integer quantity, BigDecimal amount, Integer provisioned) {
        this.id = id;
        this.partyName = partyName;
        this.quantity = quantity;
        this.amount = amount;
        this.provisioned = provisioned;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getProvisioned() {
		return provisioned;
	}

	public void setProvisioned(Integer provisioned) {
		this.provisioned = provisioned;
	}
}