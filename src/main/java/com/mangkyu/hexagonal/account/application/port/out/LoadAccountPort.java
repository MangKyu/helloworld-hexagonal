package com.mangkyu.hexagonal.account.application.port.out;

import com.mangkyu.hexagonal.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);

}
