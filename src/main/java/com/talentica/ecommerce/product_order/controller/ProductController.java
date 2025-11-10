package com.talentica.ecommerce.product_order.controller;

import com.talentica.ecommerce.product_order.dto.ProductDto;
import com.talentica.ecommerce.product_order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/products")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, name = "category") String categorySlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getAllProducts(name, categorySlug, pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/products/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
