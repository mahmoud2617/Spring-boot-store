package com.mahmoud.project.mapper;

import com.mahmoud.project.dto.ProductDto;
import com.mahmoud.project.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}
