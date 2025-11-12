package com.petshop.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order POJO class for request/response serialization
 * Follows Single Responsibility Principle - Represents Order data structure only
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status; // placed, approved, delivered
    private Boolean complete;
}

