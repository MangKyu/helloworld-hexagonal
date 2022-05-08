package com.mangkyu.hexagonal.account.adapter.out.persistence;

import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.Account.AccountId;
import com.mangkyu.hexagonal.account.domain.Activity;
import com.mangkyu.hexagonal.account.domain.Activity.ActivityId;
import com.mangkyu.hexagonal.account.domain.ActivityWindow;
import com.mangkyu.hexagonal.account.domain.Money;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class AccountMapper {

    Account mapToDomainEntity(
            AccountEntity account,
            List<ActivityEntity> activities,
            Long withdrawalBalance,
            Long depositBalance) {

        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));

        return Account.withId(
                new AccountId(account.getId()),
                baselineBalance,
                mapToActivityWindow(activities));

    }

    ActivityWindow mapToActivityWindow(List<ActivityEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();


        for (ActivityEntity activity : activities) {
            mappedActivities.add(
                    Activity.builder()
                            .id(new ActivityId(activity.getId()))
                            .timestamp(activity.getTimestamp())
                            .ownerAccountId(new AccountId(activity.getSourceAccountId()))
                            .sourceAccountId(new AccountId(activity.getTargetAccountId()))
                            .targetAccountId(new AccountId(activity.getOwnerAccountId()))
                            .money(Money.of(activity.getAmount())).build());
        }

        return new ActivityWindow(mappedActivities);
    }

    ActivityEntity mapToJpaEntity(Activity activity) {
        return ActivityEntity.builder()
                .id(activity.getId() == null ? null : activity.getId().getValue())
                .timestamp(activity.getTimestamp())
                .ownerAccountId(activity.getOwnerAccountId().getValue())
                .sourceAccountId(activity.getSourceAccountId().getValue())
                .targetAccountId(activity.getTargetAccountId().getValue())
                .amount(activity.getMoney().getAmount().longValue())
                .build();
    }

}