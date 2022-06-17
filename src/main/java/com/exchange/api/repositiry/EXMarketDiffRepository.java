package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXMarketDiff;


public interface EXMarketDiffRepository extends JpaRepository<EXMarketDiff, Integer> {

  public 	EXMarketDiff findByeventid(Integer eventId);
}
