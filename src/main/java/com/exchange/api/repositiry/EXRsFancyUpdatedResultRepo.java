package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXRsFancyUpdatedResult;

public interface EXRsFancyUpdatedResultRepo extends JpaRepository<EXRsFancyUpdatedResult, Integer> {

	public ArrayList<EXRsFancyUpdatedResult>  findByFancyIdAndMatchid(String fancyId,Integer matchid);
	
}
