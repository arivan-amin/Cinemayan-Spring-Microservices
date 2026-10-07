package com.maya.cbs.ledger.domain.journal;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.CurrencyMismatchException;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

@Value
public class LedgerTransaction {

    UUID id;
    UUID referenceId;
    Currency currency;
    Instant createdAt;
    List<LedgerEntry> entries;

    private LedgerTransaction (UUID id, UUID referenceId, Currency currency, Instant createdAt,
                               List<LedgerEntry> entries) {
        this.id = id;
        this.referenceId = referenceId;
        this.currency = currency;
        this.createdAt = createdAt;
        this.entries = entries;
    }

    public static LedgerTransaction create (UUID id, UUID referenceId, Currency currency,
                                            Instant createdAt, List<LedgerEntry> entries) {
        requireNonNull(id, "id");
        requireNonNull(referenceId, "referenceId");
        requireNonNull(currency, "currency");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(entries, "entries");

        List<LedgerEntry> copy = List.copyOf(entries);
        validate(currency, copy);
        return new LedgerTransaction(id, referenceId, currency, createdAt, copy);
    }

    private static void validate (Currency currency, List<LedgerEntry> entries) {
        mustContainAtLeastTwoEntries(entries);
        mustContainBothDirections(entries);
        mustMatchCurrency(currency, entries);
        mustBeBalanced(entries);
    }

    private static void mustContainAtLeastTwoEntries (List<LedgerEntry> entries) {
        if (entries.size() < 2) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least two entries");
        }
    }

    private static void mustContainBothDirections (List<LedgerEntry> entries) {
        if (entries.stream()
            .noneMatch(LedgerEntry::isDebit)) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least one debit entry");
        }
        if (entries.stream()
            .noneMatch(LedgerEntry::isCredit)) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least one credit entry");
        }
    }

    private static void mustMatchCurrency (Currency currency, List<LedgerEntry> entries) {
        if (entries.stream()
            .anyMatch(entry -> entry.money.getCurrency() != currency)) {
            throw new CurrencyMismatchException(
                "Ledger entry currency must match ledger transaction currency");
        }
    }

    private static void mustBeBalanced (List<LedgerEntry> entries) {
        BigDecimal debits = total(entries, LedgerEntry::isDebit);
        BigDecimal credits = total(entries, LedgerEntry::isCredit);
        if (debits.compareTo(credits) != 0) {
            throw new UnbalancedLedgerException(
                "Ledger transaction debits and credits must be equal");
        }
    }

    private static BigDecimal total (List<LedgerEntry> entries, Predicate<LedgerEntry> side) {
        return entries.stream()
            .filter(side)
            .map(ledgerEntry -> ledgerEntry.getMoney()
                .getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
