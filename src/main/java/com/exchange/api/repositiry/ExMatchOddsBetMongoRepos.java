package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.ExMatchOddsBetMongo;

@Repository
public interface ExMatchOddsBetMongoRepos extends MongoRepository<ExMatchOddsBetMongo, String>, ExMatchOddsBetMongoCustomRepository {
	
	public ArrayList<ExMatchOddsBetMongo> findByMarketidAndIsactiveAndUserid(String marketId, Boolean isActive,String userId);
	
	public Optional<ExMatchOddsBetMongo> findById(String id);
	
	public ArrayList<ExMatchOddsBetMongo> findByMatchidAndIsactiveAndAdminid(int eventid, boolean b, String id);
	
	public ArrayList<ExMatchOddsBetMongo> findByMatchidAndAdminid(int eventid, String id);
	
	public ExMatchOddsBetMongo findByid(String id);
	
	
	
}
