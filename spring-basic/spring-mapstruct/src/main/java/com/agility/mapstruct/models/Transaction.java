package com.agility.mapstruct.models;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Transaction {
    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private BigDecimal total;
}
