package com.maya.cbs.ledger.domain.base;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

@Value
public class Money {

    BigDecimal amount;
    Currency currency;

    public Money (BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
        if (amount.scale() > currency.getMaxAllowedFractionDigits()) {
            throw new IllegalArgumentException("Amount exceeds currency precision scale");
        }
        this.amount = amount;
        this.currency = currency;
    }
}
