package com.mangkyu.hexagonal.account.application.port.out;

import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);

}
