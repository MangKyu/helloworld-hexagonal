package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.application.port.in.SendMoneyRequest;
import com.mangkyu.hexagonal.account.application.port.out.AccountLock;
import com.mangkyu.hexagonal.account.application.port.out.LoadAccountPort;
import com.mangkyu.hexagonal.account.application.port.out.UpdateAccountStatePort;
import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class SendMoneyServiceTest {

    private final LoadAccountPort loadAccountPort =
            Mockito.mock(LoadAccountPort.class);

    private final AccountLock accountLock =
            Mockito.mock(AccountLock.class);

    private final UpdateAccountStatePort updateAccountStatePort =
            Mockito.mock(UpdateAccountStatePort.class);

    private final SendMoneyService sendMoneyService =
            new SendMoneyService(loadAccountPort, accountLock, updateAccountStatePort, moneyTransferProperties());

    @Test
    void givenWithdrawalFails_thenOnlySourceAccountIsLockedAndReleased() {

        Account.AccountId sourceAccountId = new Account.AccountId(41L);
        Account sourceAccount = givenAnAccountWithId(sourceAccountId);

        Account.AccountId targetAccountId = new Account.AccountId(42L);
        Account targetAccount = givenAnAccountWithId(targetAccountId);

        givenWithdrawalWillFail(sourceAccount);
        givenDepositWillSucceed(targetAccount);

        SendMoneyRequest request = new SendMoneyRequest(
                sourceAccountId,
                targetAccountId,
                Money.of(300L));

        boolean success = sendMoneyService.sendMoney(request);

        assertThat(success).isFalse();

        then(accountLock).should().lockAccount(eq(sourceAccountId));
        then(accountLock).should().releaseAccount(eq(sourceAccountId));
        then(accountLock).should(times(0)).lockAccount(eq(targetAccountId));
    }

    @Test
    void transactionSucceeds() {

        Account sourceAccount = givenSourceAccount();
        Account targetAccount = givenTargetAccount();

        givenWithdrawalWillSucceed(sourceAccount);
        givenDepositWillSucceed(targetAccount);

        Money money = Money.of(500L);

        SendMoneyRequest command = new SendMoneyRequest(
                sourceAccount.getId().get(),
                targetAccount.getId().get(),
                money);

        boolean success = sendMoneyService.sendMoney(command);

        assertThat(success).isTrue();

        Account.AccountId sourceAccountId = sourceAccount.getId().get();
        Account.AccountId targetAccountId = targetAccount.getId().get();

        then(accountLock).should().lockAccount(eq(sourceAccountId));
        then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
        then(accountLock).should().releaseAccount(eq(sourceAccountId));

        then(accountLock).should().lockAccount(eq(targetAccountId));
        then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
        then(accountLock).should().releaseAccount(eq(targetAccountId));

        thenAccountsHaveBeenUpdated(sourceAccountId, targetAccountId);
    }

    private void thenAccountsHaveBeenUpdated(Account.AccountId... accountIds){
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        then(updateAccountStatePort).should(times(accountIds.length))
                .updateActivities(accountCaptor.capture());

        List<Account.AccountId> updatedAccountIds = accountCaptor.getAllValues()
                .stream()
                .map(Account::getId)
                .map(Optional::get)
                .collect(Collectors.toList());

        for(Account.AccountId accountId : accountIds){
            assertThat(updatedAccountIds).contains(accountId);
        }
    }

    private void givenDepositWillSucceed(Account account) {
        given(account.deposit(any(Money.class), any(Account.AccountId.class)))
                .willReturn(true);
    }

    private void givenWithdrawalWillFail(Account account) {
        given(account.withdraw(any(Money.class), any(Account.AccountId.class)))
                .willReturn(false);
    }

    private void givenWithdrawalWillSucceed(Account account) {
        given(account.withdraw(any(Money.class), any(Account.AccountId.class)))
                .willReturn(true);
    }

    private Account givenTargetAccount(){
        return givenAnAccountWithId(new Account.AccountId(42L));
    }

    private Account givenSourceAccount(){
        return givenAnAccountWithId(new Account.AccountId(41L));
    }

    private Account givenAnAccountWithId(Account.AccountId id) {
        Account account = Mockito.mock(Account.class);
        given(account.getId())
                .willReturn(Optional.of(id));
        given(loadAccountPort.loadAccount(eq(account.getId().get()), any(LocalDateTime.class)))
                .willReturn(account);
        return account;
    }

    private MoneyTransferProperties moneyTransferProperties(){
        return new MoneyTransferProperties(Money.of(Long.MAX_VALUE));
    }

}