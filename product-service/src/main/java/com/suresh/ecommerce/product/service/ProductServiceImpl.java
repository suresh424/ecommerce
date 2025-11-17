package com.suresh.ecommerce.product.service;

import com.suresh.api.model.ProductRequest;
import com.suresh.api.model.ProductResponse;
import com.suresh.ecommerce.product.model.ProductEntity;
import com.suresh.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        ProductEntity entity = toEntity(request);
        entity.setActive(true);
        ProductEntity saved = productRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));
        return toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> listProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());

        ProductEntity saved = productRepository.save(entity);
        return toResponse(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductEntity toEntity(ProductRequest request) {
        ProductEntity entity = new ProductEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setStock(request.getStock());
        return entity;
    }

    private ProductResponse toResponse(ProductEntity entity) {
        ProductResponse response = new ProductResponse();
        response.setId(entity.getId().intValue());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setPrice(BigDecimal.valueOf(entity.getPrice().doubleValue()));
        response.setStock(entity.getStock());
        return response;
    }

}