package com.talentica.ecommerce.product_order.service.impl;

import com.talentica.ecommerce.product_order.dto.ProductDto;
import com.talentica.ecommerce.product_order.model.*;
import com.talentica.ecommerce.product_order.repository.*;
import com.talentica.ecommerce.product_order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private InventoryRepository inventoryRepository;

    // simple in-memory cache
    private final Map<Integer, ProductDto> productCache = new ConcurrentHashMap<>();

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findBySlug(productDto.getCategorySlug())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setSku(productDto.getSku());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setActive(true);
        product.setCategory(category);

        Product saved = productRepository.save(product);

        Inventory inv = new Inventory();
        inv.setProduct(saved);
        inv.setQuantity(productDto.getInitialStock() != null ? productDto.getInitialStock() : 0);
        inventoryRepository.save(inv);

        ProductDto result = ProductDto.fromEntity(saved, inv.getQuantity());
        productCache.put(result.getId(), result);
        return result;
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        productRepository.save(product);

        productCache.remove(id);
        return ProductDto.fromEntity(product, product.getInventory().getQuantity());
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
        productCache.remove(id);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        return productCache.computeIfAbsent(id, key ->
                productRepository.findById(id)
                        .map(p -> ProductDto.fromEntity(p, p.getInventory().getQuantity()))
                        .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Override
    public Page<ProductDto> getAllProducts(String name, String categorySlug, Pageable pageable) {
        Page<Product> products;
        if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (categorySlug != null) {
            Category cat = categoryRepository.findBySlug(categorySlug)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            products = productRepository.findByCategory(cat, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products.map(p -> ProductDto.fromEntity(p, p.getInventory().getQuantity()));
    }
}
