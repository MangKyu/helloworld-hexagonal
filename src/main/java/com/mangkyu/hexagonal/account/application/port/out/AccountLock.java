package com.mangkyu.hexagonal.account.application.port.out;

import com.mangkyu.hexagonal.account.domain.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}