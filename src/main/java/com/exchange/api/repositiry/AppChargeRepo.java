package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.AppCharge;

public interface AppChargeRepo extends JpaRepository<AppCharge, String>,AppChargeDao{

	public AppCharge findByEventidAndUseridAndAppid(String eventid, String userid, Integer appid);
	
	ArrayList<AppCharge> findBySubadminidAndIsSubadminSettledAndEventid(String subadminid,Boolean issubadminsettled,String eventid);
	
	ArrayList<AppCharge> findBySuprmasteridAndIsSuperMasterSettledAndEventid(String supermasterid,Boolean issupermastersettled,String eventid);
	
	ArrayList<AppCharge> findByMasteridAndIsMasterSettledAndEventid(String masterid,Boolean ismastersettled,String eventid);
	
	ArrayList<AppCharge> findByDealeridAndIsDealerSettledAndEventid(String masterid,Boolean isdelearsettled,String eventid);
	
	ArrayList<AppCharge> findByUseridAndIsUserSettledAndEventid(String userid,Boolean isusersettled,String eventid);
	
	public AppCharge findByEventidAndUserid(String eventid, String userid);

	public ArrayList<AppCharge> findByUseridAndIsUserSettled(String userid, Boolean isusersettled);
	
	public ArrayList<AppCharge> findByIsSubadminSettledAndIsresult(Boolean isubadminsetteled,Boolean isresult);
	
	public ArrayList<AppCharge> findBySubadminidAndIsresult(String subadminid,Boolean isresult);
	
	public ArrayList<AppCharge> findBysuprmasteridAndIsresult(String supermasterid,Boolean isresult);
	
	public ArrayList<AppCharge> findBymasteridAndIsresult(String masterid,Boolean isresult);
	
	public ArrayList<AppCharge> findByDealeridAndIsresult(String dealerid,Boolean isresult);
	
	public ArrayList<AppCharge> findByIsresultAndEventid(Boolean isresult,String eventid);
	

	public ArrayList<AppCharge> findByUseridAndIsresult(String userid, Boolean isresult);
	
	public ArrayList<AppCharge> findBySubadminidAndIsresultAndIsSubadminSettled(String subadminid,Boolean isresult,Boolean isubadminsetteled);

	public ArrayList<AppCharge> findBySuprmasteridAndIsresultAndIsSubadminSettled(String supermasterid,Boolean isresult,Boolean isubadminsetteled);
	
	public ArrayList<AppCharge> findBymasteridAndIsresultAndIsSubadminSettled(String masterid,Boolean isresult,Boolean isubadminsetteled);
	
	public ArrayList<AppCharge> findByDealeridAndIsresultAndIsSubadminSettled(String dealerid,Boolean isresult,Boolean isubadminsetteled);
	
	public ArrayList<AppCharge> findByDealeridAndIsresultAndIsMasterSettled(String masterid,Boolean isresult,Boolean ismastersettelled);
	
	public ArrayList<AppCharge> findBySuprmasteridAndIsresultAndIsSuperMasterSettled(String supermasterid,Boolean isresult,Boolean issupermastersettled);
	
	public ArrayList<AppCharge> findByDealeridAndIsresultAndIsDealerSettled(String dealerid,Boolean isresult,Boolean usdealersettled);
	
	public ArrayList<AppCharge> findBySubadminidAndEventid(String subadminid,String eventid);
	
	public ArrayList<AppCharge> findBySuprmasteridAndEventid(String supermasterid,String eventid);
	
	public ArrayList<AppCharge> findByMasteridAndEventid(String masterid,String eventid);
	
	public ArrayList<AppCharge> findByDealeridAndEventid(String dealerid,String eventid);
	
	public AppCharge findByUseridAndEventid(String userid,String eventid);
	
	public AppCharge findByUseridAndEventidAndIsresult(String userid,String eventid,Boolean resultset);
	
	

	
	
}