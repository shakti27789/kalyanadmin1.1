package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXMatchViewer;
import com.exchange.api.bean.ExMatchOddsBetMongo;


public interface ExMatchOddsBetMongoCustomRepository {
	
	/*
	 * public List<ExMatchOddsBetMongo> findBetListByProperties( Integer matchid,
	 * Boolean isActive, String adminid, String subadminid, String supermaster,
	 * String master, String dealerid);
	 */
	
	public List<ExMatchOddsBetMongo> findBetListByProperties(Integer matchid, Boolean isActive, String key, String value, String date, String userid);
	
	public List<ExMatchOddsBetMongo> findBetListByProperties(Integer matchid, Boolean isActive, String key, String value, String userid);
	
	public LinkedHashMap<String, Object> findBetListDetailsByProperties(Integer matchid, Boolean isActive, String key, String value, String type);
	
	
	public ArrayList<ExMatchOddsBetMongo> findBetList(Integer matchid,Integer sportId, Boolean isActive, String searchBy,  String parentid,String userid,String marketId,Boolean isdeleted,Integer limit);

	public ArrayList<EXMatchViewer> getMatchUserCount(Integer matchid);

	public Integer getMatchBetUserCount(Integer matchid);
	
	public ArrayList<ExMatchOddsBetMongo> findBetListByParentIdUserId(Integer matchid,Integer sportId, Boolean isActive, String searchBy,  String parentid,String userid,String marketId,Boolean isdeleted,Integer limit);

	

}
