package com.exchange.api.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.UserLiabilityDao;

@Repository
public class UserLiabilityDaoImpl implements UserLiabilityDao {

	
	@PersistenceContext
	private EntityManager entitymanager;

	@Override
	public UserLiability getByMarketidAndUserid(String marketid, String userid) {
		
		StringBuffer sb  = new StringBuffer();

		sb.append("select * From t_libility where marketid =? and userid =? ");
		Query query = (Query) entitymanager.createNativeQuery(sb.toString(),UserLiability.class);
		query.setParameter(1, marketid);
		query.setParameter(2, userid);
		UserLiability resultList = (UserLiability) query.getSingleResult();
		//Object[] author = (Object[]) query.getSingleResult();
	    
		
		//System.out.println(author);
		return resultList;
	}
	
	@Override
	public UserLiability getClientLiability1(String childid, String child) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		UserLiability lib =null;
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		
	
		sb.append("select sum(liability) as liability,isactive,userid from t_libility where isactive =true and userid ='c1' group by userid" );
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		
	/*	query.setParameter(1, true);
		query.setParameter(2,childid);*/
		
		Object[] author = (Object[]) query.getSingleResult();
		UserLiability liblist = new UserLiability();
		liblist.setLiability(Double.valueOf(author[0].toString()));
		liblist.setIsactive(Boolean.valueOf(author[1].toString()));
		liblist.setUserid(author[1].toString());
	
		return liblist;
	}
	
	@Override
	public UserLiability getpnl(Integer usertype,String marketid,String userid) {
		
		StringBuffer sb  = new StringBuffer();
		
		if(usertype ==4){
			sb.append("select  sum(lib.pnl1 * lib.adminpartnership )/100 pnl1,sum(lib.pnl2 * lib.adminpartnership )/100 as pnl2,sum(lib.pnl3 * lib.adminpartnership )/100 as  pnl3 from t_libility lib where lib.marketid=? and lib.adminid=? " );
				
		}else if(usertype ==5){
			sb.append("select  sum(lib.pnl1 * lib.subadminpartnership )/100 pnl1,sum(lib.pnl2 * lib.subadminpartnership )/100 as pnl2,sum(lib.pnl3 * lib.subadminpartnership )/100 as  pnl3 from t_libility lib where lib.marketid=? and lib.subadminid=? " );
			
		}else if(usertype ==0){
			sb.append("select  sum(lib.pnl1 * lib.subadminpartnership )/100 pnl1,sum(lib.pnl2 * lib.subadminpartnership )/100 as pnl2,sum(lib.pnl3 * lib.subadminpartnership )/100 as  pnl3 from t_libility lib where lib.marketid=? and lib.supermasterid =? " );
			
		}else if(usertype ==1){
			sb.append("select  sum(lib.pnl1 * lib.masterpartnership )/100 pnl1,sum(lib.pnl2 * lib.masterpartnership )/100 as pnl2,sum(lib.pnl3 * lib.masterpartnership )/100 as  pnl3 from t_libility lib where lib.marketid=? and lib.masterid =?  " );
			
		}else if(usertype ==2){
			sb.append("select  sum(lib.pnl1 * lib.dealerpartnership )/100 pnl1,sum(lib.pnl2 * lib.dealerpartnership )/100 as pnl2,sum(lib.pnl3 * lib.dealerpartnership )/100 as  pnl3 from t_libility lib where lib.marketid=? and lib.dealerid =? " );
			
		}
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, marketid);
		query.setParameter(2, userid);
		
		

		Object[] libilities = (Object[]) query.getSingleResult();
		UserLiability liblist = new UserLiability();
		if(libilities[0] !=null){
			liblist.setPnl1(Double.valueOf(libilities[0].toString()));
		}
		if(libilities[1] !=null){
			liblist.setPnl2(Double.valueOf(libilities[1].toString()));
		}
		if(libilities[2] !=null){
			liblist.setPnl3(Double.valueOf(libilities[2].toString()));
		}
		
		
		
		
		return liblist;
	}
	public LinkedHashMap<String, Object> getUserDetail(String userId,String type) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		LinkedHashMap<String, Object> hm =new LinkedHashMap<String, Object>();
		if(type.equalsIgnoreCase("lenadena")) {
			sb.append("select COALESCE(SUM(lib.liability),0) as laiblity ,((usr.availablebalancewithpnl)- COALESCE(SUM(lib.liability),0)) as availablebalance from ");
			
		}else {
			sb.append("select COALESCE(SUM(lib.liability),0) as laiblity ,((usr.availablebalance+usr.availablebalancewithpnl)- COALESCE(SUM(lib.liability),0)) as availablebalance from ");
			
		}
		sb.append("t_user usr LEFT JOIN t_libility lib ON usr.userid = lib.userid and usr.userid=? and lib.userid=? and lib.isactive=true where usr.userid=? ");
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setParameter(3, userId);
		List<Object[]> result = query.getResultList();
		for (Object[] a : result) {
			
			hm.put("laibility",  a[0].toString());
			hm.put("balance",  a[1].toString());
		}
		
		return hm;
	} 
	
}
