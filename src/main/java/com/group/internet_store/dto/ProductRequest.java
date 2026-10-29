package com.group.internet_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    @NotBlank
    @Size(min = 1, max = 200)
    private String description;
    @NotNull
    @Positive
    private long price;
}
