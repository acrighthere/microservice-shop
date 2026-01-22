package org.acrighthere.product.сontroller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.acrighthere.product.dto.ProductRequest;
import org.acrighthere.product.dto.ProductResponse;
import org.acrighthere.product.model.Product;
import org.acrighthere.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}
