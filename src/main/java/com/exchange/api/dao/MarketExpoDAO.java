package com.exchange.api.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.UserLiability;


public interface MarketExpoDAO {

	public ArrayList<UserLiability> getProfitnLoss(LinkedHashMap<String,String> filterData,Integer matchid);
	
	public ArrayList<UserLiability> getFancyProfitnLoss(LinkedHashMap<String,String> filterData);
	
	public ArrayList<UserLiability> getUserList(String id,String idtype,Integer eventid);
	
	public ArrayList<ExMatchOddsBet> getUserExposure(String id,String idtype, String type);
	
	public ArrayList<ExMatchOddsBet> getPnlComm(String id,String idtype);

	public ArrayList<UserLiability> getNetExposure(String id,String idtype);
	
	/*public TypedAggregation<?> getUserTransaction(String id);
	*/
/*	public TypedAggregation<?> getTransactionByParent(String date,String parentid, String id);
	*/
	
	public ArrayList<UserLiability> getChildLiability(String childid,String child,String query);
	
	public ArrayList<EXUser> getChildCreditLimit(String childid,String child);

	ArrayList<UserLiability> getClientLiability(String childid, String child);
	
	ArrayList<UserLiability> pnlparentIdWise(List<String> ids,Integer usertype,Integer eventid,Integer parentusertype,String marketid);
}
