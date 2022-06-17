package com.exchange.api.dao;

import java.text.ParseException;
import java.util.ArrayList;

import com.exchange.api.bean.Event;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;

public interface MarketDAO {

	public ArrayList<Market> getEventRates(Integer sportid,String matchregex,String supermasterid, Integer i);
	
	public ArrayList<Market> getinPlayMatches(Integer sportid,String supermasterid);
	
	public ArrayList<Event> getEvents();

	ArrayList<MarketResult> getEventsByDate(String startdate, String enddate,String sorted) throws ParseException;
	
	
}
