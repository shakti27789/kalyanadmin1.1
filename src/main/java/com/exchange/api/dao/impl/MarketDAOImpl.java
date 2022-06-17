package com.exchange.api.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.Event;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.dao.MarketDAO;
import com.exchange.util.EXDateUtil;

@Repository
public class MarketDAOImpl  implements  MarketDAO{

	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Override
	public ArrayList<Market> getEventRates(Integer sportid, String matchregex, String supermasterid, Integer i) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();

		sb.append("from  Market where  isactive =:isactive and marketname =:marketname  and sportid =:sportid order by opendate");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("marketname", "Match Odds");
		query.setParameter("sportid", sportid);
		
		ArrayList<Market> resultList = (ArrayList<Market>) query.list();

		return resultList;
	}

	@Override
	public ArrayList<Market> getinPlayMatches(Integer sportid, String supermasterid) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();

		sb.append("from  Market where  isactive =:isactive and marketname =:marketname  and sportid =:sportid order by opendate asc");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("isactive", true);
		query.setParameter("marketname", "Match Odds");
		query.setParameter("sportid", sportid);
		
		///List<Object[]> resultList = query.getResultList();
		
		ArrayList<Market> resultList = (ArrayList<Market>) query.list();

		return resultList;
	}

	

	@Override
	public ArrayList<Event> getEvents() {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();

		sb.append("from  Event where  status =:status order by matchstartdate");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setBoolean("status", true);
		
		ArrayList<Event> resultList = (ArrayList<Event>) query.list();

		return resultList;
		
	}
	
	
	@Override
	public ArrayList<MarketResult> getEventsByDate(String startdate,String enddate,String sorted) throws ParseException {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		sb.append("from  MarketResult ");
		if(startdate !=null && enddate!=null){
			sb.append(" where date between :startdate and :enddate order BY date desc");
		}else{
			sb.append(" order BY date "+sorted);
		}
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		EXDateUtil dtUtil = new EXDateUtil();
		
		if(startdate !=null && enddate!=null){
			query.setDate("startdate", dtUtil.convertToDate(startdate, "yyyy-MM-dd HH:mm:ss"));
			query.setDate("enddate", dtUtil.add(dtUtil.convertToDate(enddate, "yyyy-MM-dd HH:mm:ss"), 0, 2, 0));
				
		}
		ArrayList<MarketResult> resultList = (ArrayList<MarketResult>) query.list();

		return resultList;
		
	}

	

}
