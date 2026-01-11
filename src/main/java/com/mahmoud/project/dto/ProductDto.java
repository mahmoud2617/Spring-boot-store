package com.mahmoud.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductDto {
    private Long id;

    @NotBlank(message = "Name must have at least one character.")
    @Size(max = 255, message = "Name mustn't be greater than 255 character.")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @PositiveOrZero(message = "Price cannot be negative.")
    private BigDecimal price;

    private Byte categoryId;
}
