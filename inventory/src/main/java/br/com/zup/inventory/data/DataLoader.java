package br.com.zup.inventory.data;

import java.math.BigDecimal;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.zup.inventory.entity.Inventory;
import br.com.zup.inventory.repository.InventoryRepository;

@Component
public class DataLoader {

	@Autowired
    private InventoryRepository inventoryRepository;

	//method invoked during the startup
    @PostConstruct
    public void loadData() {
    	this.inventoryRepository.save(new Inventory(UUID.randomUUID().toString(), "metallica", 20, new BigDecimal("199.99"), 0));
    }

    @PreDestroy
    public void removeData() {
    	this.inventoryRepository.deleteAll();
    }

}
