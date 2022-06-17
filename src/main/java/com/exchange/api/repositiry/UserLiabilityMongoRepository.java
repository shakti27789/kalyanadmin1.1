package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.exchange.api.bean.UserLiability;
import com.exchange.api.bean.UserLiabilityMongo;

public interface UserLiabilityMongoRepository  extends MongoRepository<UserLiabilityMongo, String> {

	ArrayList<UserLiabilityMongo> findByMarketidAndIsactive(String marketid,Boolean IsActive);
	
	ArrayList<UserLiabilityMongo> findByMarketidAndIsactiveAndSubadminid(String marketid,Boolean IsActive,String adminid);
	
	ArrayList<UserLiabilityMongo> findByMarketidAndIsactiveAndSupermasterid(String marketid,Boolean IsActive,String adminid);
	
	ArrayList<UserLiabilityMongo> findByMarketidAndIsactiveAndMasterid(String marketid,Boolean IsActive,String adminid);
	
	ArrayList<UserLiabilityMongo> findByMarketidAndIsactiveAndDealerid(String marketid,Boolean IsActive,String adminid);
	

ArrayList<UserLiabilityMongo> findByAdminidAndMarketid(String adminid,String marketId);
	
	ArrayList<UserLiabilityMongo> findBySubadminidAndMarketid(String adminid,String marketId);

	ArrayList<UserLiabilityMongo> findBySupermasteridAndMarketid(String adminid,String marketId);

	ArrayList<UserLiabilityMongo> findByMasteridAndMarketid(String adminid,String marketId);

	ArrayList<UserLiabilityMongo> findByDealeridAndMarketid(String adminid,String marketId);
	

}
