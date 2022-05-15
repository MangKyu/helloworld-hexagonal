package com.mangkyu.hexagonal.account.adapter.out.persistence;

import com.mangkyu.hexagonal.account.domain.Account;
import com.mangkyu.hexagonal.account.domain.ActivityWindow;
import com.mangkyu.hexagonal.account.domain.Money;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static com.mangkyu.hexagonal.common.AccountTestData.defaultAccount;
import static com.mangkyu.hexagonal.common.ActivityTestData.defaultActivity;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountPersistenceAdapterTest {

    private AccountPersistenceAdapter adapterUnderTest;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void init() {
        adapterUnderTest = new AccountPersistenceAdapter(accountRepository, activityRepository, new AccountMapper());
    }

    @Test
    @Sql("classpath:AccountPersistenceAdapterTest.sql")
    void loadsAccount() {
        Account account = adapterUnderTest.loadAccount(new Account.AccountId(1L), LocalDateTime.of(2018, 8, 10, 0, 0));

        assertThat(account.getActivityWindow().getActivities()).hasSize(2);
        assertThat(account.calculateBalance()).isEqualTo(Money.of(500));
    }

    @Test
    void updatesActivities() {
        Account account = defaultAccount()
                .withBaselineBalance(Money.of(555L))
                .withActivityWindow(new ActivityWindow(
                        Lists.newArrayList(
                                defaultActivity()
                                        .withId(null)
                                        .withMoney(Money.of(1L)).build()))
                ).build();

        adapterUnderTest.updateActivities(account);

        assertThat(activityRepository.count()).isEqualTo(1);

        ActivityEntity savedActivity = activityRepository.findAll().get(0);
        assertThat(savedActivity.getAmount()).isEqualTo(1L);
    }

}