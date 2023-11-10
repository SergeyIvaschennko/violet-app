package com.example.web.service;

import com.example.web.dto.ProductDto;
import com.example.web.models.Product;

import java.util.List;
public interface ProductService {
    List<ProductDto> findAllProducts();

    Product saveProduct(ProductDto productDto);

    ProductDto findProductById(long productId);

    void updateProducts(ProductDto product);

    void delete(Long productId);
}
