package com.exchange.api.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONException;

import com.exchange.api.bean.MatchFancy;

public interface LotusEventsService {

	public ArrayList<MatchFancy> getMatchEventList(String  eventId,String fancyprovider) throws JSONException;
	
	public Boolean SaveFancy(String id, Integer eventId,Integer appid,String fancyid,String skyfancyid,String mytype,String provider);

	LinkedHashMap<String, Object> getMatchFancy(String eventId, String fancyprovider) throws JSONException;
	
	
}
