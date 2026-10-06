package com.maya.cbs.ledger.domain.journal;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public final class Ledger {

    private final UUID id;
    private final UUID transactionId;
    private final Currency currency;
    private final Instant createdAt;
    private final List<LedgerEntry> entries;

    private Ledger (UUID id, UUID transactionId, Currency currency, Instant createdAt,
                    List<LedgerEntry> entries) {
        this.id = id;
        this.transactionId = transactionId;
        this.currency = currency;
        this.createdAt = createdAt;
        this.entries = List.copyOf(entries);
    }

    public static Ledger create (UUID id, UUID transactionId, Currency currency, Instant createdAt,
                                 List<LedgerEntry> entries) {
        validate(currency, entries);

        return new Ledger(id, transactionId, currency, createdAt, entries);
    }

    private static void validate (Currency currency, List<LedgerEntry> entries) {
        mustContainAtLeastTwoEntries(entries);
        numberOfEntriesMustBeEven(entries);
        mustContainAtLeastOneDebitEntry(entries);
        mustContainAtLeastOneCreditEntry(entries);
        validateAmounts(entries);
        validateCurrency(currency, entries);
        validateBalance(entries);
    }

    private static void mustContainAtLeastTwoEntries (List<LedgerEntry> entries) {
        if (entries == null || entries.size() < 2) {
            throw new UnbalancedLedgerException("Ledger must contain at least two entries");
        }
    }

    private static void numberOfEntriesMustBeEven (List<LedgerEntry> entries) {
        if (entries.size() % 2 == 1) {
            throw new UnbalancedLedgerException("Ledger must contain even number of entries");
        }
    }

    private static void mustContainAtLeastOneDebitEntry (List<LedgerEntry> entries) {
        if (entries.stream()
            .noneMatch(entry -> entry.getDirection() == EntryDirection.DEBIT)) {
            throw new InvalidLedgerEntryException("Ledger must contain at least one debit entry");
        }
    }

    private static void mustContainAtLeastOneCreditEntry (List<LedgerEntry> entries) {
        if (entries.stream()
            .noneMatch(entry -> entry.getDirection() == EntryDirection.CREDIT)) {
            throw new InvalidLedgerEntryException("Ledger must contain at least one credit entry");
        }
    }

    private static void validateAmounts (List<LedgerEntry> entries) {
        if (entries.stream()
            .anyMatch(entry -> entry.getMoney()
                                   .getAmount()
                                   .signum() <= 0)) {
            throw new IllegalArgumentException("Ledger entry amounts must be greater than zero");
        }
    }

    private static void validateCurrency (Currency currency, List<LedgerEntry> entries) {
        if (entries.stream()
            .anyMatch(entry -> currency != entry.getMoney()
                .getCurrency())) {
            throw new IllegalArgumentException("Ledger entry currency must match ledger currency");
        }
    }

    private static void validateBalance (List<LedgerEntry> entries) {
        BigDecimal debits = total(entries, EntryDirection.DEBIT);
        BigDecimal credits = total(entries, EntryDirection.CREDIT);

        if (debits.compareTo(credits) != 0) {
            throw new IllegalArgumentException("Ledger debits and credits must be equal");
        }
    }

    private static BigDecimal total (List<LedgerEntry> entries, EntryDirection direction) {
        return entries.stream()
            .filter(entry -> entry.getDirection() == direction)
            .map(entry -> entry.getMoney()
                .getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
