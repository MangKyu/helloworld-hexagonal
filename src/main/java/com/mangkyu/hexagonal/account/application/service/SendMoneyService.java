package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.application.port.in.SendMoneyRequest;
import com.mangkyu.hexagonal.account.application.port.in.SendMoneyCommand;
import com.mangkyu.hexagonal.account.application.port.out.AccountLock;
import com.mangkyu.hexagonal.account.application.port.out.LoadAccountPort;
import com.mangkyu.hexagonal.account.application.port.out.UpdateAccountStatePort;
import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.AccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
@Transactional
@RequiredArgsConstructor
class SendMoneyService implements SendMoneyCommand {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyRequest sendMoneyRequest) {
        requireAccountExists(sendMoneyRequest.getSourceAccountId());
        requireAccountExists(sendMoneyRequest.getTargetAccountId());
        // TODO: 비즈니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력값 반환
        return false;
    }

    private void requireAccountExists(AccountId targetAccountId) {
        Account account = loadAccountPort.loadAccount(targetAccountId, LocalDateTime.now());
        if (account == null) {
            throw new IllegalArgumentException("Account not exists");
        }
    }

}
