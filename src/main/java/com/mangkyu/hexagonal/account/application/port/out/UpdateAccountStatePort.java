package com.mangkyu.hexagonal.account.application.port.out;

import com.mangkyu.hexagonal.account.domain.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}