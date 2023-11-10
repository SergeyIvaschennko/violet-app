package com.example.web.service.impl;

import com.example.web.dto.ProductDto;
import com.example.web.models.Product;
import com.example.web.models.UserEntity;
import com.example.web.repository.ProductRepository;
import com.example.web.repository.UserRepository;
import com.example.web.security.SecurityUtil;
import com.example.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> clubs = productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));//сортировка по id
        return clubs.stream().map((club) -> mapToProductDto(club)).collect(Collectors.toList());
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Product product = mapToProduct(productDto);
        product.setCreatedBy(user);
        productRepository.save(product);
        return product;
    }

    @Override
    public ProductDto findProductById(long productId) {
        Product product = productRepository.findById(productId).get();
        return mapToProductDto(product);
    }

    @Override
    public void updateProducts(ProductDto productDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Product product = mapToProduct(productDto);
        product.setCreatedBy(user);
        productRepository.save(product);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product mapToProduct(ProductDto product) {
        Product productDto = Product.builder()
                .id(product.getId())
                .title(product.getTitle())
                .photoUrl(product.getPhotoUrl())
                .amount(product.getAmount())
                .price(product.getPrice())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .updatedOn(product.getUpdatedOn())
                .build();

        return productDto;
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .photoUrl(product.getPhotoUrl())
                .amount(product.getAmount())
                .price(product.getPrice())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .updatedOn(product.getUpdatedOn())
                .build();

        return productDto;

    }
}

