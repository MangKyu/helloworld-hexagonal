package com.mangkyu.hexagonal.account.application.port.in;

import javax.validation.Valid;

public interface SendMoneyCommand {

    boolean sendMoney(@Valid SendMoneyRequest sendMoneyRequest);

}
