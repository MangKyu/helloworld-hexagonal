package com.mangkyu.hexagonal.account.application.port.out;

import com.mangkyu.hexagonal.account.domain.AccountId;

public interface AccountLock {

	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);

}