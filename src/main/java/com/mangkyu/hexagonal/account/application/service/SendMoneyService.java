package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.application.port.in.SendMoneyCommand;
import com.mangkyu.hexagonal.account.application.port.in.SendMoneyRequest;
import com.mangkyu.hexagonal.account.application.port.out.AccountLock;
import com.mangkyu.hexagonal.account.application.port.out.LoadAccountPort;
import com.mangkyu.hexagonal.account.application.port.out.UpdateAccountStatePort;
import com.mangkyu.hexagonal.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
class SendMoneyService implements SendMoneyCommand {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyRequest request) {
        checkThreshold(request);

        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(
                request.getSourceAccountId(),
                baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(
                request.getTargetAccountId(),
                baselineDate);

        Account.AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        Account.AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(request.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(request.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyRequest request) {
        if(request.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), request.getMoney());
        }
    }

}