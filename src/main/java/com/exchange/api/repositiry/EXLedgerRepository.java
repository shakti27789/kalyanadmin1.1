package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXLedger;

@Repository
public interface EXLedgerRepository extends JpaRepository<EXLedger, Integer> {

	long deleteByMarketid(String marketid);
	
	public ArrayList<EXLedger> findByMatchidAndParentidAndChildid(Integer matchid,Integer parentid,Integer childid);
	
	long deleteByMarketidAndMatchid(String marketid,Integer matchId);
	
	EXLedger findByMarketidAndMatchidAndUserid(String marketId,Integer matchId,String userid);
	
	public EXLedger findByUseridAndMarketidAndByUserId(String userId,String marketId,String byUserId);
	
	
}
