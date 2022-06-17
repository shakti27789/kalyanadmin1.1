package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXMinMaxBet;

public interface MinMaxBetRepository extends JpaRepository<EXMinMaxBet, String>{

	EXMinMaxBet findByTypeAndAppid(String type,Integer appid);
	
	EXMinMaxBet findByType(String type);
	
	ArrayList<EXMinMaxBet> findByAppid(Integer appid);
}
