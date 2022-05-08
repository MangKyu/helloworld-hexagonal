package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.application.port.in.GetAccountBalanceQuery;
import com.mangkyu.hexagonal.account.application.port.out.LoadAccountPort;
import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(Account.AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                .calculateBalance();
    }

}