package com.mangkyu.hexagonal.account.domain;

import java.util.List;

public class ActivityWindow {

    private final List<Activity> activities;

    public ActivityWindow(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Money calculateBalance(AccountId accountId) {
        Money depositBalance = activities.stream()
                .filter(a -> a.getTargetAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);

        Money withdrawalBalance = activities.stream()
                .filter(a -> a.getSourceAccountId().equals(accountId))
                .map(Activity::getMoney)
                .reduce(Money.ZERO, Money::add);

        return Money.add(depositBalance, withdrawalBalance.negate());
    }

}
