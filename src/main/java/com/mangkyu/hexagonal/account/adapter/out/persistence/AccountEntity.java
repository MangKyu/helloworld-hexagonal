package com.mangkyu.hexagonal.account.adapter.out.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor
class AccountEntity {

    @Id
    @GeneratedValue
    private Long id;

}