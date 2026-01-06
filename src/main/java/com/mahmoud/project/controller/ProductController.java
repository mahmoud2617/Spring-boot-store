package com.mahmoud.project.controller;

import com.mahmoud.project.dto.ProductDto;
import com.mahmoud.project.mapper.ProductMapper;
import com.mahmoud.project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    ProductRepository productRepository;
    ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(name = "categoryId", required = false) String categoryId
    ) {
        if (isParseableToByte(categoryId)) {
            return productRepository.findAllByCategoryId(Byte.parseByte(categoryId))
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }

        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    private static boolean isParseableToByte(String s) {
        try {
            Byte.parseByte(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
