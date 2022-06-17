package com.exchange.api.repositiry;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exchange.api.bean.DiamonCasinoResult;


public interface DiamondCasinoResultRepository extends MongoRepository<DiamonCasinoResult, String> {

	public DiamonCasinoResult findByRoundId(String roundId);
	
}
