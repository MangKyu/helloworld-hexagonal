package com.mangkyu.hexagonal.account.adapter.in.web;

import com.mangkyu.hexagonal.account.application.port.in.GetAccountBalanceQuery;
import com.mangkyu.hexagonal.account.domain.AccountId;
import com.mangkyu.hexagonal.account.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetAccountBalanceController {

    private final GetAccountBalanceQuery getAccountBalanceQuery;

    @GetMapping(path = "/accounts/{accountId}")
    ResponseEntity<Long> getBalance(@PathVariable("accountId") Long accountId) {

        Money accountBalance = getAccountBalanceQuery.getAccountBalance(new AccountId(accountId));
        return ResponseEntity.ok(accountBalance.toLong());
    }

}
