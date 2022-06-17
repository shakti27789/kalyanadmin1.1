package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXRsFancyResult;

public interface EXRsFancyResultRepository extends JpaRepository<EXRsFancyResult, Integer> {

	public EXRsFancyResult  findByFancyIdAndMatchid(String fancyId,Integer matchid);
	
	public EXRsFancyResult  findByFancyIdAndMatchidAndIsresultset(String fancyId,Integer matchid,Boolean isresultset);
	
	
}
