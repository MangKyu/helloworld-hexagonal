package com.mangkyu.hexagonal.account.application.port.in;

import com.mangkyu.hexagonal.account.domain.AccountId;
import com.mangkyu.hexagonal.account.domain.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(AccountId accountId);

}