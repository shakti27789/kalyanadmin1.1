package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXChipDetail;

@Repository
public interface EXChipDetailRepository extends JpaRepository<EXChipDetail, Integer> {
	
	ArrayList<EXChipDetail> findByUserIdOrderByCreatedOnAsc(String userid);
	
	long deleteByMarketId(String marketid);

	long deleteByMarketIdAndMatchId(String marketid,Integer matchId);
	
	
    public ArrayList<EXChipDetail> findByMarketIdAndMatchId(String marketId,Integer matchId);
    
   // public ArrayList<EXChipDetail> findByMarketIdAnduserId(String marketId,String matchId);
    
	
}
