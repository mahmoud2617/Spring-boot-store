package com.mahmoud.project.mapper;

import com.mahmoud.project.dto.OrderDto;
import com.mahmoud.project.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items", source = "orderItems")
    OrderDto toDto(Order order);
}