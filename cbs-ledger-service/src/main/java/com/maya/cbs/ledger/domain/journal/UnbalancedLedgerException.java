package com.maya.cbs.ledger.domain.journal;

public class UnbalancedLedgerException extends RuntimeException {

    public UnbalancedLedgerException (String message) {
        super(message);
    }
}
