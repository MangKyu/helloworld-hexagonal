package com.mangkyu.hexagonal.account.application.port.in;

import com.mangkyu.hexagonal.account.domain.AccountId;
import com.mangkyu.hexagonal.account.domain.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendMoneyCommand {

    private final AccountId sourceAccountId;

    private final AccountId targetAccountId;

    private final Money money;

}