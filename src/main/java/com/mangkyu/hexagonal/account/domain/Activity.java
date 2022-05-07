package com.mangkyu.hexagonal.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Activity {

    private final AccountId ownerAccountId;
    private final AccountId targetAccountId;
    private final AccountId sourceAccountId;
    private final LocalDateTime timestamp;
    private final Money money;

}
