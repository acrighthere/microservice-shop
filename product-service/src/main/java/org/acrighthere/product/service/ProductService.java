package org.acrighthere.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acrighthere.product.dto.ProductRequest;
import org.acrighthere.product.dto.ProductResponse;
import org.acrighthere.product.model.Product;
import org.acrighthere.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product {} has been created", product.getId());
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),product.getSkuCode(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
       return productRepository.findAll()
               .stream()
               .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getSkuCode(),product.getPrice()))
               .toList();
    }
}
