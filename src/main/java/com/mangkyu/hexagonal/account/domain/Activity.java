package com.mangkyu.hexagonal.account.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class Activity {

    private final ActivityId id;
    private final Account.AccountId ownerAccountId;
    private final Account.AccountId sourceAccountId;
    private final Account.AccountId targetAccountId;
    private final LocalDateTime timestamp;
    private final Money money;

    @Getter
    @RequiredArgsConstructor
    public static class ActivityId {
        private final Long value;
    }

}
