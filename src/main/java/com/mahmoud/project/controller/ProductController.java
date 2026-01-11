package com.mahmoud.project.controller;

import com.mahmoud.project.dto.ProductDto;
import com.mahmoud.project.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Product")
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(name = "categoryId", required = false)
            @Positive
            Byte categoryId
    ) {
        return productService.getAllProducts(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "id") Long id) {
        ProductDto productDto = productService.getProduct(id);

        return ResponseEntity.status(200).body(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDtoRequest,
            UriComponentsBuilder uriBuilder
    ) {
        ProductDto productDto = productService.createProduct(productDtoRequest);
        URI uri = uriBuilder.path("/products/{id}")
                .buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ProductDto productDtoRequest
    ) {
        ProductDto productDto = productService.updateProduct(id, productDtoRequest);

        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto productDtoRequest
    ) {
        ProductDto productDto = productService.patchProduct(id, productDtoRequest);

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
