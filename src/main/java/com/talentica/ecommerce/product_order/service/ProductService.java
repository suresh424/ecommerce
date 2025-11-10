package com.talentica.ecommerce.product_order.service;

import com.talentica.ecommerce.product_order.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Integer id, ProductDto productDto);
    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);
    Page<ProductDto> getAllProducts(String name, String categorySlug, Pageable pageable);
}
