package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXLedgerNew;

@Repository
public interface EXLedgerNewRepository extends JpaRepository<EXLedgerNew, Integer> {

}
