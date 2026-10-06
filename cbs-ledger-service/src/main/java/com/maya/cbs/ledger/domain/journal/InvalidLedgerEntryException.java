package com.maya.cbs.ledger.domain.journal;

public class InvalidLedgerEntryException extends RuntimeException {

    public InvalidLedgerEntryException (String message) {
        super(message);
    }
}
