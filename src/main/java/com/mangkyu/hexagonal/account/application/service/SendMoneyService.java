package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.application.port.in.SendMoneyCommand;
import com.mangkyu.hexagonal.account.application.port.in.SendMoneyUserCase;
import com.mangkyu.hexagonal.account.application.port.out.AccountLock;
import com.mangkyu.hexagonal.account.application.port.out.LoadAccountPort;
import com.mangkyu.hexagonal.account.application.port.out.UpdateAccountStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Transactional
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUserCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(final SendMoneyCommand sendMoneyCommand) {
        // TODO: 비즈니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력값 반환
        return false;
    }

}
