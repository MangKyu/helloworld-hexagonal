package com.mangkyu.hexagonal.account.domain;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
public class Money {

    public static Money ZERO = Money.of(0L);

    private final BigInteger amount;

    private static Money of(long value) {
        return new Money(BigInteger.valueOf(value));
    }

    private static Money of(BigInteger bigInteger) {
        return new Money(bigInteger);
    }

    public static Money add(Money a, Money b) {
        return Money.of(a.amount.add(b.amount));
    }

    public Money negate() {
        return new Money(this.amount.negate());
    }

    public boolean isPositiveOrZero(){
        return this.amount.compareTo(BigInteger.ZERO) >= 0;
    }

}
