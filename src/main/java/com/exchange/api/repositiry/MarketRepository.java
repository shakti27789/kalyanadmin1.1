package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, String>{

	
	public ArrayList<Market> findBySportidAndStatus(Integer sportid, Boolean status);
	
	public Market findBymarketid(String marketid);
	
	public ArrayList<Market> findByEventidAndStatus(Integer eventid, Boolean status);
	
	public ArrayList<Market> findByMarketnameAndIsactiveAndIsResult(String marketname, Boolean isactive, Boolean isResult);
	
	long deleteByMarketid(String marketid);

	public ArrayList<Market> findByStatus(Boolean status);
	
	public Market findByMarketidAndStatus(String marketid,Boolean status);
	
	public Market findByStatusAndEventidAndMarketname(Boolean status,Integer eventid,String marketname);
	
	public ArrayList<Market> findByEventidAndIsactiveAndStatus(Integer eventid, Boolean isactive, Boolean status);
	
	public Market findByEventidAndIsactiveAndMarketname(Integer eventid, Boolean isactive,String matchodds);
	
	public ArrayList<Market> findByEventid(Integer eventid);

	
	
	
	
	
}
