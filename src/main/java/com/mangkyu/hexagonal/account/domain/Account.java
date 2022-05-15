package com.mangkyu.hexagonal.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class Account {

    private final AccountId id;
    private final Money baselineBalance;
    private final ActivityWindow activityWindow;

    public static Account withoutId(
            Money baselineBalance,
            ActivityWindow activityWindow) {
        return new Account(null, baselineBalance, activityWindow);
    }

    public static Account withId(
            AccountId accountId,
            Money baselineBalance,
            ActivityWindow activityWindow) {
        return new Account(accountId, baselineBalance, activityWindow);
    }

    public Money calculateBalance() {
        return Money.add(
                baselineBalance,
                activityWindow.calculateBalance(id)
        );
    }

    public boolean deposit(Money money, AccountId sourceAccountId) {
        Activity deposit = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(sourceAccountId)
                .targetAccountId(this.id)
                .timestamp(LocalDateTime.now())
                .money(money).build();

        this.activityWindow.addActivity(deposit);
        return true;
    }

    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)){
            return false;
        }

        Activity withdrawal = Activity.builder()
                .ownerAccountId(this.id)
                .sourceAccountId(this.id)
                .targetAccountId(targetAccountId)
                .timestamp(LocalDateTime.now())
                .money(money).build();

        activityWindow.addActivity(withdrawal);
        return true;
    }

    private boolean mayWithdraw(Money money) {
        return Money.add(
                        this.calculateBalance(),
                        money.negate())
                .isPositiveOrZero();
    }


    public Optional<AccountId> getId(){
        return Optional.ofNullable(this.id);
    }

    @Getter
    @RequiredArgsConstructor
    public static class AccountId {

        private final long value;

    }

}
