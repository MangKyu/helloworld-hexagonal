package com.mangkyu.hexagonal.account.domain;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

public class Account {

    private AccountId id;
    private Money baselineBalance;
    private ActivityWindow activityWindow;

    public Money calculateBalance() {
        return Money.add(
                baselineBalance,
                activityWindow.calculateBalance(id)
        );
    }

    public boolean deposit(Money money, AccountId sourceAccountId) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountId,
                this.id,
                LocalDateTime.now(),
                money);
        this.activityWindow.addActivity(deposit);
        return true;
    }

    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)){
            return false;
        }

        Activity withdrawal = new Activity(
                id,
                id,
                targetAccountId,
                LocalDateTime.now(),
                money
        );

        activityWindow.addActivity(withdrawal);
        return true;
    }

    private boolean mayWithdraw(Money money) {
        return Money.add(
                        this.calculateBalance(),
                        money.negate())
                .isPositiveOrZero();
    }

}
