package com.mangkyu.hexagonal.account.application.port.in;

import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.Money;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
public class SendMoneyRequest {

    @NotNull
    private final Account.AccountId sourceAccountId;

    @NotNull
    private final Account.AccountId targetAccountId;

    @NotNull
    private final Money money;

}