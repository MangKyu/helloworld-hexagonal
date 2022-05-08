package com.mangkyu.hexagonal.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    @Query("select a from ActivityEntity a " +
            "where a.ownerAccountId = :ownerAccountId " +
            "and a.timestamp >= :since")
    List<ActivityEntity> findByOwnerSince(
            @Param("ownerAccountId") Long ownerAccountId,
            @Param("since") LocalDateTime since);

    @Query("select sum(a.amount) from ActivityEntity a " +
            "where a.targetAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Long getDepositBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") LocalDateTime until);

    @Query("select sum(a.amount) from ActivityEntity a " +
            "where a.sourceAccountId = :accountId " +
            "and a.ownerAccountId = :accountId " +
            "and a.timestamp < :until")
    Long getWithdrawalBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") LocalDateTime until);

}