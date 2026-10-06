package com.maya.cbs.ledger.domain.base;

import lombok.Getter;

@Getter
public enum Currency {
    IQD(3),
    USD(2),
    EUR(2),
    GBP(2),
    AED(3);

    private final int maxAllowedFractionDigits;

    Currency (int maxFractionDigits) {
        maxAllowedFractionDigits = maxFractionDigits;
    }
}
