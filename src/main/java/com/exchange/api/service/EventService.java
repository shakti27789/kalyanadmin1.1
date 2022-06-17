package com.exchange.api.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.EventRates;
import com.exchange.api.bean.Market;

public interface EventService {
	
	public ArrayList<EventRates> getEventRatesList(ArrayList<Market> market,String type );

	public JSONObject getEventRatesListInplay(ArrayList<Market> market,String type,EXUser usersession );
	
	public ArrayList<EventRates> getInPlayList(ArrayList<Market> market);
	
	public String lotusMatchOdds(Integer eventid, Integer sportid);
	
	public ArrayList<EventRates> getEventNameList(ArrayList<Market> marketList);
	
	public ArrayList<LinkedHashMap<String,Object>> leftMenuData();
}
