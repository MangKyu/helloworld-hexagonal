package com.mangkyu.hexagonal.account.application.port.in;

import javax.validation.Valid;

public interface SendMoneyUserCase {

    boolean sendMoney(@Valid SendMoneyCommand sendMoneyCommand);

}
