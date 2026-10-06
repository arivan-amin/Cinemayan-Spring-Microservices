package com.maya.cbs.ledger.domain.journal;

import com.maya.cbs.ledger.domain.base.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LedgerEntry {

    private final UUID id;
    private final UUID accountId;
    private final Money money;
    private final EntryDirection direction;
}
