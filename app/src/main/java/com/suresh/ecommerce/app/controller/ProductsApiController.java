package com.suresh.ecommerce.app.controller;

import com.suresh.api.ProductsApi;
import com.suresh.api.model.ProductRequest;
import com.suresh.api.model.ProductResponse;
import com.suresh.ecommerce.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductsApiController implements ProductsApi {

    private final ProductService productService;

    public ProductsApiController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<List<ProductResponse>> listProducts() {
        List<ProductResponse> products = productService.listProducts();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        ProductResponse created = productService.createProduct(productRequest);
        return ResponseEntity.status(201).body(created);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Integer id) {
        ProductResponse product = productService.getProductById(id.longValue());
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        ProductResponse updated = productService.updateProduct(id.longValue(), productRequest);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        productService.deleteProduct(id.longValue());
        return ResponseEntity.noContent().build();
    }
}