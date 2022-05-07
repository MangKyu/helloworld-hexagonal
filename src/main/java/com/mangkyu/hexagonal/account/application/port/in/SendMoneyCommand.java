package com.mangkyu.hexagonal.account.application.port.in;

import com.mangkyu.hexagonal.account.domain.AccountId;
import com.mangkyu.hexagonal.account.domain.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
public class SendMoneyCommand {

    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

}