package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exchange.api.bean.EXChipDetailMongo;

public interface EXChipDetailsMongoRepository extends MongoRepository<EXChipDetailMongo, String> {

	long deleteBymarketId(String marketid);
	
	ArrayList<EXChipDetailMongo>  findByUserIdAndType(String userId,String type);
	
	
}
