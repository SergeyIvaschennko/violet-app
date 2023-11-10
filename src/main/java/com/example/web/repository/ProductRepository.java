package com.example.web.repository;

import com.example.web.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Optional<Club> findByTitle(String url);

}
