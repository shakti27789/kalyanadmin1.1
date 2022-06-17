package com.exchange.api.service;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.text.ParseException;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.ResponseBean;


public interface EXBetService {

	public ResponseBean placeBet(ExMatchOddsBet placebet,EXUser usersession) throws InterruptedException, UnknownHostException, ParseException, JSONException, IllegalAccessException, InvocationTargetException;

	
	public ResponseBean deleteMarketBetBySeries(String marketid, Integer start, Integer end, EXUser usersession) throws Exception; 
	
	public ResponseBean rollbackMatchBet(Integer id)throws Exception;


	Double getDaimondBets(Boolean isBack, Double price, int selectionids, String uri, Double range, String Bhaotype,
			Market market);
}
