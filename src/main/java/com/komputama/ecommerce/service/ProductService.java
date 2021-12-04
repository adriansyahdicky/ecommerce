package com.komputama.ecommerce.service;

import com.komputama.ecommerce.dto.response.ProductDto;
import com.komputama.ecommerce.entity.Product;
import com.komputama.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProductById(String productId){
        Optional<ProductDto> productDto =
                Optional.of(productRepository.findById(productId).
                        map(ProductService::mapProductById)).get();
        return productDto.orElseGet(() -> ProductDto.builder().build());
    }

    private static ProductDto mapProductById(Product product){
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice()).build();

    }

}
