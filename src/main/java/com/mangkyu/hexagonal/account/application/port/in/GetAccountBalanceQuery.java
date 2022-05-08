package com.mangkyu.hexagonal.account.application.port.in;

import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(Account.AccountId accountId);

}