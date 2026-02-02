package com.mahmoud.project.service;

import com.mahmoud.project.dto.ProductDto;
import com.mahmoud.project.entity.Category;
import com.mahmoud.project.entity.Product;
import com.mahmoud.project.exception.CategoryNotFoundException;
import com.mahmoud.project.exception.ProductNotFoundException;
import com.mahmoud.project.mapper.ProductMapper;
import com.mahmoud.project.repository.CategoryRepository;
import com.mahmoud.project.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    public List<ProductDto> getAllProducts(Byte categoryId) {
        return (categoryId == null)?
            productRepository.findAll()
                    .stream()
                    .map(productMapper::toDto)
                    .toList() :
            productRepository.findAllByCategoryId(categoryId)
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
}

    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if (category == null) {
            throw new CategoryNotFoundException();
        }

        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);

        return productDto;
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDtoRequest) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        productMapper.update(productDtoRequest, product);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto patchProduct(Long id, ProductDto productDtoRequest) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        productMapper.patch(productDtoRequest, product);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ProductNotFoundException();
        }

        productRepository.delete(product);
    }
}
