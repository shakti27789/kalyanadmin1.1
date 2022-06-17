package com.exchange.api.dao;

import java.util.LinkedHashMap;

import com.exchange.api.bean.UserLiability;

public interface UserLiabilityDao {

	public UserLiability getByMarketidAndUserid(String marketid,String userid);

	UserLiability getClientLiability1(String childid, String child);

	UserLiability  getpnl(Integer usertype,String marketid,String userid);

	public LinkedHashMap<String,Object> getUserDetail(String userId,String type);
	
	
}
