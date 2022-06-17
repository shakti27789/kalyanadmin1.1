package com.exchange.api.service;

public interface FancyService {
	
	public Boolean setBetResult(String marketid,Integer result,	Integer resultid);
	
	public Boolean rollBackResult(Integer id);
}
