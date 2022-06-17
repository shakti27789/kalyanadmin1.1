package com.exchange.api.service;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.text.ParseException;

import org.json.JSONException;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.ResponseBean;

public interface EXFancyBetService {

	public ResponseBean placeFancyBet(ExMatchOddsBet placebet,EXUser usersession) throws InterruptedException, UnknownHostException, ParseException, JSONException, IllegalAccessException, InvocationTargetException;

	public ResponseBean deleteFancyBetBySeries(String fancyid,Integer start,Integer end,EXUser usersession) throws Exception;

	ResponseBean rollBackFanyBet(Integer id) throws Exception;

	ResponseBean deleteFancyBetById(String fancyid, Integer id, EXUser usersession) throws Exception;

	
}
