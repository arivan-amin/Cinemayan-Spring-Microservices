package com.maya.cbs.ledger.domain.journal;

import com.maya.cbs.ledger.domain.base.Money;
import lombok.*;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class LedgerEntry {

    UUID id;
    UUID accountId;
    Money money;
    EntryDirection direction;

    public LedgerEntry (UUID accountId, Money money, EntryDirection direction) {
        requireNonNull(accountId, "accountId");
        requireNonNull(money, "money");
        requireNonNull(direction, "direction");
        if (!money.isPositive()) {
            throw new InvalidLedgerEntryException("Ledger entry amount must be greater than zero");
        }
        this.accountId = accountId;
        this.money = money;
        this.direction = direction;
    }

    public boolean isDebit () {
        return direction == EntryDirection.DEBIT;
    }

    public boolean isCredit () {
        return direction == EntryDirection.CREDIT;
    }
}
