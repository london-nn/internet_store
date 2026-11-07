package com.group.internet_store.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force=true)
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final String createdAt;
    private final String updatedAt;
    private final BigDecimal price;
}
