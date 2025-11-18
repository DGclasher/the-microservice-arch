package com.clasher.inventory_service;

import com.clasher.inventory_service.model.Inventory;
import com.clasher.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            if (!inventoryRepository.existsBySkuCode("iphone_13")) {
                Inventory inventory = new Inventory();
                inventory.setSkuCode("iphone_13");
                inventory.setQuantity(100);
                inventoryRepository.save(inventory);
            }

            if (!inventoryRepository.existsBySkuCode("iphone_14")) {
                Inventory inventory1 = new Inventory();
                inventory1.setSkuCode("iphone_14");
                inventory1.setQuantity(0);
                inventoryRepository.save(inventory1);
            }
        };
    }
}
