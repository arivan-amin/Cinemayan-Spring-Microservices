package com.maya.cbs.ledger.domain.journal;

public enum EntryDirection {
    DEBIT,
    CREDIT;

    public EntryDirection opposite () {
        return this == DEBIT ? CREDIT : DEBIT;
    }
}
