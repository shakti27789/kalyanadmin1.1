package com.exchange.api.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.SelectionIds;
import com.exchange.api.bean.Series;
import com.exchange.api.controller.EXbetController;
import com.exchange.api.controller.EXmarketController;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.SeriesRepository;
import com.exchange.util.EXGlobalConstantProperties;

@Service("AutomaticMarkertResultService")
public class AutomaticMarkertResultServiceImpl implements AutomaticMarkertResultService {

	@Autowired
	MarketRepository eventMarketRepository;
	
	@Autowired
	SelectionIdRepository selectionIdRepository;
	
	@Autowired
	SeriesRepository seriesRepository;
	
	@Autowired
	EXmarketController marController;
	
	@Autowired
	MarketResultRepository resultRepository;
	
	
	@Autowired
	EXbetController betController;

	/*@Autowired
	AsyncController asyncCont;
	*/
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;
	
	
	@Override
	public Boolean setMarkertResultAutomatic() {
		// TODO Auto-generated method stub
	
			return false;	
		
		
	}


	
	@Override
	public Boolean setMarkertResultAutomaticByService() {
		return null;}



	@Override
	public Boolean setMarkertResultAutomaticByMatch(String marketid) {
		// TODO Auto-generated method stub
		return null;
	}

}
