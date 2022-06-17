package com.exchange.api.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXRsFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.dao.FancyDAO;

@Repository
public class FancyDAOImpl implements FancyDAO {

	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Override
	public ArrayList<MatchFancy> getFancy(String subadminid) {
		// TODO Auto-generated method stub
		
        ArrayList<String> list = new  ArrayList<String>();
        list.add("OPEN");
        list.add("SUSPENDED");
		StringBuffer sb  = new StringBuffer();

		sb.append(" from  MatchFancy mf left join EXRsFancyResult rsfancy on mf.fancyid=rsfancy.fancyId   where mf.issuspendedByAdmin =:issuspendedByAdmin and mf.status in (:status)  ");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());

		query.setBoolean("issuspendedByAdmin", false);
		query.setParameterList("status", list);
		
		ArrayList<MatchFancy> mflist = new ArrayList<MatchFancy>();
		List<Object[]> listResult = query.list();
		for (Object[] aRow : listResult) {
			
			MatchFancy mf = new MatchFancy();
			EXRsFancyResult  rsmf = new EXRsFancyResult();
			 mf = (MatchFancy) aRow[0];
			 rsmf = (EXRsFancyResult) aRow[1];
			 
			 if(mf!=null) {
				
				 if(rsmf !=null) {
					 mf.setResult(rsmf.getResult());
				 }else {
					 mf.setResult("open");
				 }
				 
				 mflist.add(mf);
			 }
			 
			
			//System.out.println(mf.getFancyid()+"==>"+rsmf.getResult());
			
		}
		
	//	ArrayList<MatchFancy> resultList = (ArrayList<MatchFancy>) query.list();

		return mflist;
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public ArrayList<MatchFancy> getFancyByMatchId(Integer matchid) {
		// TODO Auto-generated method stub
		
        ArrayList<String> list = new  ArrayList<String>();
        list.add("OPEN");
        list.add("SUSPENDED");
		StringBuffer sb  = new StringBuffer();

		sb.append(" from  MatchFancy mf left join EXRsFancyResult rsfancy on mf.fancyid=rsfancy.fancyId   where mf.issuspendedByAdmin =:issuspendedByAdmin and mf.status in (:status) and mf.eventid =:matchid  ");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());

		query.setBoolean("issuspendedByAdmin", false);
		query.setParameterList("status", list);
		query.setParameter("matchid", matchid);
		
		ArrayList<MatchFancy> mflist = new ArrayList<MatchFancy>();
		List<Object[]> listResult = query.list();
		for (Object[] aRow : listResult) {
			
			MatchFancy mf = new MatchFancy();
			EXRsFancyResult  rsmf = new EXRsFancyResult();
			 mf = (MatchFancy) aRow[0];
			 rsmf = (EXRsFancyResult) aRow[1];
			 
			 if(mf!=null) {
				
				 if(rsmf !=null) {
					 mf.setResult(rsmf.getResult());
				 }else {
					 mf.setResult("open");
				 }
				 
				 mflist.add(mf);
			 }
			 
			
			
		}
	
		return mflist;
	}
	
	
	
	
	
	

	@Override
	public ArrayList<MatchFancy> getFancyBook(String user, String userid, String fancyid) {
		// TODO Auto-generated method stub
		
		 ArrayList<String> list = new  ArrayList<String>();
	        list.add("OPEN");
	        list.add("SUSPENDED");
			StringBuffer sb  = new StringBuffer();

			sb.append("from  MatchFancy  where fancyid =:fancyid and isactive =:isactive order by odds desc  ");
			
			Query query = (Query) entitymanager.createQuery(sb.toString());

			query.setParameter("fancyid", fancyid);
			query.setBoolean("isactive", true);
			
			
			ArrayList<MatchFancy> resultList = (ArrayList<MatchFancy>) query.list();

			return resultList;
	}

}
