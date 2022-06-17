package com.exchange.api.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.MarketExpoDAO;

@Repository
public class MarketExpoDAOImpl implements MarketExpoDAO {

	@PersistenceContext
	private EntityManager entitymanager;
	
	@Override
	public ArrayList<UserLiability> getProfitnLoss(LinkedHashMap<String, String> filterData,Integer matchid) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();

		sb.append("from  UserLiability where isactive =:isactive and type=:type and matchid =:matchid order by opendate asc ");
		if(!filterData.get("sportid").equals("-1")){
			sb.append(" sportid =:sportid");
		}
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("type", "Match Odds");
		query.setParameter("isactive", true);
		query.setParameter("matchid", matchid);
		if(!filterData.get("sportid").equals("-1")){
			query.setParameter("sportid", Integer.parseInt(filterData.get("sportid")));
		}
		
		
		ArrayList<UserLiability> resultList = (ArrayList<UserLiability>) query.list();

		return resultList;
		
	}

	@Override
	public ArrayList<UserLiability> getFancyProfitnLoss(LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();

		sb.append("from  UserLiability where isactive =:isactive and type=:type order by opendate asc ");
		if(!filterData.get("sportid").equals("-1")){
			sb.append(" sportid =:sportid");
		}
		
		if(filterData.get("type").equals("4")){
			sb.append(" adminid =:adminid");
		}else if(filterData.get("type").equals("1")){
			sb.append("  and masterid =:masterid");
			
		}else if(filterData.get("type").equals("5")){
			sb.append("  and subadminid =:subadminid");
		}else if(filterData.get("type").equals("0")){
			sb.append("  and supermasterid =:supermasterid");
		}else{
			sb.append("  and dealerid =:dealerid");
		}
		
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("type", "Match Odds");
		query.setParameter("isactive", true);
		
		if(!filterData.get("sportid").equals("-1")){
			query.setParameter("sportid", Integer.parseInt(filterData.get("sportid")));
		}
		
		if(filterData.get("type").equals("4")){
			query.setParameter("adminid", filterData.get("id"));
		}else if(filterData.get("type").equals("1")){
			query.setParameter("masterid", filterData.get("id"));
			
		}else if(filterData.get("type").equals("5")){
			query.setParameter("subadminid", filterData.get("id"));
		}else if(filterData.get("type").equals("0")){
			query.setParameter("supermasterid", filterData.get("id"));
		}else{
			query.setParameter("dealerid", filterData.get("id"));
		}
		
		ArrayList<UserLiability> resultList = (ArrayList<UserLiability>) query.list();

		return resultList;
	}

	@Override
	public ArrayList<UserLiability> getUserList(String id, String idtype, Integer matchid) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();

		sb.append("from  UserLiability where isactive =:isactive and type=:type and matchid =:matchid and "+idtype+"=:idtype order by  matchname asc" );
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("type", "Match Odds");
		query.setParameter("matchid",matchid);
		query.setParameter("idtype",id);
		
		ArrayList<UserLiability> resultList = (ArrayList<UserLiability>) query.list();

		return resultList;
		
	}

	@Override
	public ArrayList<ExMatchOddsBet> getUserExposure(String id,String idtype, String type) {
		// TODO Auto-generated method stub

		StringBuffer sb  = new StringBuffer();
		ExMatchOddsBet betlist =null;
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		sb.append("select sum(liability),isactive,type,"+idtype+" from ExMatchOddsBet where isactive =:isactive and type=:type and "+idtype+"=:idtype group by "+idtype );
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("type", type);
		query.setParameter("idtype",id);
		
		List<Object[]> resultList = query.getResultList();
		
		for (Object[] a : resultList) {
			betlist = new ExMatchOddsBet();
			betlist =betlist.getUserExposure(a);
			list.add(betlist);
			
		}
		return list;
	}

	@Override
	public ArrayList<ExMatchOddsBet> getPnlComm(String id, String idtype) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		ExMatchOddsBet betlist =null;
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		sb.append("from ExMatchOddsBet where isactive =:isactive and isdeleted=:isdeleted and "+idtype+"=:idtype" );
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setBoolean("isdeleted", false);
		query.setParameter("idtype",id);
		
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
		return resultList;
	}

	@Override
	public ArrayList<UserLiability> getNetExposure(String id, String idtype) {
		// TODO Auto-generated method stub
		

		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		UserLiability lib =null;
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		sb.append("select sum(liability),isactive,"+idtype+" from UserLiability where isactive =:isactive and "+idtype+"=:idtype  group by "+idtype );
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("idtype",id);
		
		List<Object[]> resultList = query.getResultList();
		
		for (Object[] a : resultList) {
			lib = new UserLiability();
			lib =lib.getChildLiability(a);
			list.add(lib);
			
		}
		return list;
	
	}

	/*@Override
	public TypedAggregation<?> getUserTransaction(String id) {
		// TODO Auto-generated method stub
		return null;
	}*/

/*	@Override
	public TypedAggregation<?> getTransactionByParent(String date, String parentid, String id) {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public ArrayList<UserLiability> getChildLiability(String childid, String child, String query) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		UserLiability lib =null;
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		sb.append("select sum(liability) as liability,isactive,userid from UserLiability where isactive =:isactive and userid =:userid  group by userid " );
		
		Query sqlquery = (Query) entitymanager.createQuery(sb.toString());
		
		sqlquery.setBoolean("isactive", true);
		sqlquery.setParameter("userid","4");
		
		List<Object[]> resultList = sqlquery.getResultList();
		
		for (Object[] a : resultList) {
			lib = new UserLiability();
			lib =lib.getChildLiability(a);
			list.add(lib);
			
		}
		return list;
	}
	
	
	@Override
	public ArrayList<UserLiability> getClientLiability(String childid, String child) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		UserLiability lib =null;
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		sb.append("select sum(liability) as liability,isactive,userid from UserLiability where isactive =:isactive and userid =:userid  group by userid " );
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("userid",childid);
		
		List<Object[]> resultList = query.getResultList();
		
		for (Object[] a : resultList) {
			lib = new UserLiability();
			lib =lib.getChildLiability(a);
			list.add(lib);
			
		}
		return list;
	}

	

	@Override
	public ArrayList<EXUser> getChildCreditLimit(String childid, String child) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("from EXUser where "+child+" =:childid and  active =:active");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("childid",childid);
		query.setBoolean("active", true);
		
		ArrayList<EXUser> resultList = (ArrayList<EXUser>) query.list();
		return resultList;
	}

	@Override
	public ArrayList<UserLiability> pnlparentIdWise(List<String> ids, Integer usertype, Integer matchid,Integer parentusertype,String marketid) {
		// TODO Auto-generated method stub
		
		 
		StringBuffer sb  = new StringBuffer();
		String adminid =null;
		UserLiability lib =null;
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		String partnership =null;
		if(parentusertype ==5){
			partnership ="lib.subadminpartnership";
		}else if(parentusertype ==0){
			partnership ="lib.supermasterpartnership";
		}if(parentusertype ==1){
			partnership ="lib.masterpartnership";
		}if(parentusertype ==2){
			partnership ="lib.dealerpartnership";
		}if(parentusertype ==4){
			partnership ="lib.adminpartnership";
		}
		
		
		/*sb.append(" select  -sum(lib.pnl1*"+partnership+" )/100 as pnl1,-sum(lib.pnl2* "+partnership+")/100 as pnl2,-sum(lib.pnl3 * "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid,usr.parentid ");
		sb.append(" from UserLiability lib Inner Join EXUser usr where and lib.matchid =:matchid  and lib.isactive = :isactive and lib.marketid= :marketid and adminid =? ");
		*/
		//List<String> ids = Arrays.asList("858","850");
		 if(usertype == 5){
			sb.append(" select  -sum(lib.pnl1*"+partnership+" )/100 as pnl1,-sum(lib.pnl2* "+partnership+")/100 as pnl2,-sum(lib.pnl3 * "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid ");
			sb.append(" from UserLiability lib,EXUser usr where lib.subadminid = usr.id and lib.matchid =:matchid and lib.subadminid in (:subadminid) and lib.isactive = :isactive and lib.marketid= :marketid  group by lib.subadminid "); 
			
		}else if(usertype == 0){
			sb.append(" select  -sum(lib.pnl1* "+partnership+")/100 as pnl1,-sum(lib.pnl2* "+partnership+")/100 as pnl2,-sum(lib.pnl3 * "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid ");
			sb.append(" from UserLiability lib,EXUser usr where lib.supermasterid = usr.id and lib.matchid =:matchid and lib.supermasterid in (:supermasterid) and usr.id=:supermasterid and lib.isactive = :isactive  and lib.marketid= :marketid group by lib.supermasterid"); 
			
		}else if(usertype == 1){
			sb.append(" select  -sum(lib.pnl1* "+partnership+")/100 as pnl1,-sum(lib.pnl2*"+partnership+")/100 as pnl2,-sum(lib.pnl3 * "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid ");
			sb.append(" from UserLiability lib,EXUser usr where lib.masterid = usr.id and lib.matchid =:matchid and lib.masterid in (:masterid) and usr.id = :masterid and lib.isactive = :isactive and lib.marketid= :marketid group by lib.masterid"); 
			
		}else if(usertype == 2){
			sb.append(" select  -sum(lib.pnl1* "+partnership+")/100 as pnl1,-sum(lib.pnl2* "+partnership+")/100 as pnl2,-sum(lib.pnl3 * "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid ");
			sb.append(" from UserLiability lib,EXUser usr where lib.dealerid = usr.id and lib.matchid =:matchid and lib.dealerid in (:dealerid) and usr.id = :dealerid and lib.isactive = :isactive and  lib.marketid= :marketid group by lib.dealerid"); 
			
		}else if(usertype == 3){
			sb.append(" select  -sum(lib.pnl1 * "+partnership+")/100 as pnl1,-sum(lib.pnl2* "+partnership+")/100 as pnl2,-sum(lib.pnl3* "+partnership+")/100 as pnl3,usr.username,lib.adminid,lib.subadminid,lib.supermasterid,lib.masterid,lib.dealerid ");
			sb.append(" from UserLiability lib,EXUser usr where lib.userid = usr.userid and lib.matchid =:matchid  and usr.userid in (:userid) and lib.isactive = :isactive  and lib.marketid= :marketid group by lib.userid "); 
			
		}
		 
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		//query.setParameter("type", "Match Odds");
		query.setParameter("matchid",matchid);
		query.setParameter("marketid",marketid);
		if(usertype == 4){
			//query.setParameter("adminid",userid);
		} else if(usertype == 5){
			query.setParameterList("subadminid",ids);
		}else if(usertype == 0){
		query.setParameterList("supermasterid",ids);
		}else if(usertype == 1){
			query.setParameterList("masterid",ids);
		}else if(usertype == 2){
			query.setParameterList("dealerid",ids);
		}else if(usertype == 3){
			query.setParameter("userid",ids);
		}
		
		List<Object[]> result = query.getResultList();
 
		for (Object[] a : result) {
			lib = new UserLiability();
			lib =lib.getpnlChidIdWise(a);
			list.add(lib);
			
		}
		return list;
		
	}

}
