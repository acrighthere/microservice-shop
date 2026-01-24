package org.acrighthere.inventory.service;

import lombok.RequiredArgsConstructor;
import org.acrighthere.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
       return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode,quantity);
    }
}
