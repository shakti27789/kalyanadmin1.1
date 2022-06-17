package com.exchange.api.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.ExMatchOddsBet;

public interface ExMatchOddsBetDao {

	public ArrayList<ExMatchOddsBet>  getBetsForResult(String marketid,String sorttype, String type);
	
	public ArrayList<ExMatchOddsBet> getMainDateWiseBets(String userid, int type, LinkedHashMap<String, String> filterData);
	
	public ArrayList<ExMatchOddsBet> getSubadminCommission(Integer resultid,String child,Boolean settled,String childid,String query);
	
	public ArrayList<ExMatchOddsBet> getSupermasterCommission(Integer resultid,String subadminid,String child,Boolean settled,String id,String query);

	public ArrayList<ExMatchOddsBet> getMasterCommission(Integer resultid,String supermastername,String subadminname,String child,Boolean settled,String childid,String query);
	
	public ArrayList<ExMatchOddsBet> getDealerCommission(Integer resultid,String mastername,String supermastername,String subadminname,String child,Boolean settled,String childid,String query);
	
	public ArrayList<ExMatchOddsBet> getResultBets(String fancyid, String usertype, String userid,Integer page,Integer count);

	public ArrayList<ExMatchOddsBet> getUserCommission(Integer resultid, String dealer, String child, Boolean settled,String userid, String query);
	
	public ArrayList<ExMatchOddsBet> getBetsRollBack(Integer Integer, String type, Integer page,Integer count);
	
	public String abandendTieMarketRollBack(String marketid, String matchstatus,Integer matchid);
	
	public ArrayList<ExMatchOddsBet> getBets(LinkedHashMap<String,String> filterData);
	
	public ArrayList<ExMatchOddsBet>  getMatchedBets(String id,String type,Boolean active,Integer matchid,String marketname,Integer startpage,Integer endpage,Boolean count);
	
	public ArrayList<ExMatchOddsBet> getMarketbetBySeries(String marketid, Integer start, Integer end, Boolean isdeleted);
	
	public ArrayList<ExMatchOddsBet> getBetsResult(String marketid, String sorttype, String type,Integer page,Integer count,Boolean setActive,Boolean isActive);
	
	public ArrayList<ExMatchOddsBet> getDateWiseBets(String userid,LinkedHashMap<String,String> filterData);
	
	public  ArrayList<EXChipDetail> getpnlAndComm(String userid,String parentid,LinkedHashMap<String, String> filterData);
	
	public ArrayList<ExMatchOddsBet> getfancybetBySeries(String fancyid, Integer start, Integer end, Boolean isdeleted);
	
	public ArrayList<ExMatchOddsBet> getFancyBook(String user,String userid,String fancyid,Integer matchid);
	
	

	public Integer getNextSequence(String marketid);
	
	public void updateSeries(String marketid,String markettype,Integer id );

	
	public ArrayList<ExMatchOddsBet> betHistory(String marketId, Integer matchId, Boolean isdeleted, String userid,Boolean isactive,Integer usertype);


	public ArrayList<ExMatchOddsBet> currentBets(Boolean isdeleted, String userid, Boolean isactive, Integer usertype,String type);

	public Long  getMatchedBetsTotalCount(String id,String type,Boolean active,Integer matchid,String marketname);

	ArrayList<ExMatchOddsBet> getFancyBookNew(String user, String userid, String fancyid, String partnership);

	
	ArrayList<ExMatchOddsBet> getFancyBookExposure(String user, String userid, List<String> fancyids,
			String partnership);

	
	
}
