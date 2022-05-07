package com.mangkyu.hexagonal.account.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
public class Money {

    public static Money ZERO = Money.of(0L);

    private final BigInteger amount;

    public static Money of(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Input money is less than 0");
        }
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

    public Long toLong() {
        return amount.longValue();
    }
}
