package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.MarketResult;


public interface MarketResultRepository extends JpaRepository<MarketResult, String>{

	
	public MarketResult findByMarketid(String marketid);
	
	
	
	public ArrayList<MarketResult> findByStatus(Boolean status);
	
	public ArrayList<MarketResult> findByIsResult(Boolean isResult);
	
	public MarketResult findByMatchidAndMarketname(Integer matchid,String matchodds);
	
	public MarketResult findByMatchid(Integer matchid);
	
	public MarketResult findByid(Integer id);
	
	//@Query(value="DELETE FROM MarketResult WHERE id =?1")
	@Modifying
	public long deleteById(Integer id);
	
	@Modifying
	public long deleteByMarketid(String marketid);
	
	public ArrayList<MarketResult> findTop100ByStatusAndTypeOrderByIdDesc(Boolean status,String type);
	
	public ArrayList<MarketResult> findByresultStatusCron(Boolean status);
}
