package com.clasher.inventory_service.service;

import com.clasher.inventory_service.dto.InventoryResponse;
import com.clasher.inventory_service.model.Inventory;
import com.clasher.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setSkuCode(inventory.getSkuCode());
        inventoryResponse.setInStock(inventory.getQuantity() > 0);
        return inventoryResponse;
    };

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInstock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(this::mapToInventoryResponse).toList();
    }
}
