package com.mangkyu.hexagonal.account.application.port.in;

public interface SendMoneyUserCase {

    boolean sendMoney(SendMoneyCommand sendMoneyCommand);

}
