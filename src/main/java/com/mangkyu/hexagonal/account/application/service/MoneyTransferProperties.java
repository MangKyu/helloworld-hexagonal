package com.mangkyu.hexagonal.account.application.service;

import com.mangkyu.hexagonal.account.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferProperties {

    private Money maximumTransferThreshold = Money.of(1_000_000L);

}