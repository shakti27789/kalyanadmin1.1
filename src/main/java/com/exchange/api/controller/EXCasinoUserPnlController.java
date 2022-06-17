package com.exchange.api.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.EXChipDetailMongo;
import com.exchange.api.bean.EXLedger;
import com.exchange.api.bean.EXLedgerMongo;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.repositiry.EXLedgerMongoCustomRepository;
import com.exchange.util.EXDateUtil;

@ControllerAdvice
@RestController
@CrossOrigin
@RequestMapping("/api")
public class EXCasinoUserPnlController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EXLedgerMongoCustomRepository eXLedgerMongoCustomRepository;
	
	EXDateUtil dtutil = new EXDateUtil();
	
	ResponseBean responseBean;
	
	@RequestMapping(value = "/getuserPnlMQuery/{childid}/{matchid}/{date}/{pagenumber}", method = RequestMethod.GET)
	public ResponseEntity<Object> getuserPnlMQuery( 
			@PathVariable("childid") String childid, @PathVariable("matchid") String matchid, @PathVariable("date") String date, @PathVariable("pagenumber") Integer pagenumber){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		
		if (usersession != null) {
			if (StringUtils.isNotBlank(usersession.getUserid()) && StringUtils.isNotBlank(childid)
					&& StringUtils.isNotBlank(matchid) && StringUtils.isNotBlank(date)) {
				if (childid != null) {
					if (childid.equalsIgnoreCase("-1")) {
						childid = usersession.getUserid();
					}
				}
				Map<String, Object> list = eXLedgerMongoCustomRepository
						.getLedgerStatementDetails(childid, matchid, date, pagenumber);
				if (list.size() > 0) {
					
					
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}
			}
		}
		 
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/getuserPnlToday/{childid}/{matchid}/{date}", method = RequestMethod.GET)
	public ResponseEntity<Object> getuserPnlToday( 
			@PathVariable("childid") String childid, @PathVariable("matchid") String matchid, @PathVariable("date") String date){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		
		if (usersession != null) {
			if (StringUtils.isNotBlank(usersession.getUserid()) && StringUtils.isNotBlank(childid)
					&& StringUtils.isNotBlank(matchid) && StringUtils.isNotBlank(date)) {
				if (childid != null) {
					if (childid.equalsIgnoreCase("-1")) {
						childid = usersession.getUserid();
					}
				}
				Map<String, Object> list = eXLedgerMongoCustomRepository
						.getuserPnlToday(childid, matchid, date);
				if (list.size() > 0) {
					
					
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}
			}
		}
		 
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	
	

	@RequestMapping(value = "/casinoProfitLoss", method = RequestMethod.POST)
	public ResponseEntity<Object> casinoProfitLoss(@RequestBody LinkedHashMap<String, String> filterData)throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		/*
		 * usersession =new EXUser(); usersession.setUserid("mango");
		 * usersession.setUsertype(4);
		 */
		DecimalFormat df = new DecimalFormat("#0");

		if (usersession != null) {
			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			
			ArrayList<Integer> matchIds   =new  ArrayList<Integer>();
			matchIds.add(5051);
			matchIds.add(5052);
			matchIds.add(5041);
			matchIds.add(5042);
			matchIds.add(5021);
			matchIds.add(5022);
			matchIds.add(5011);
			matchIds.add(5031);
			matchIds.add(5032);
			
			
			
			ArrayList<EXLedgerMongo> list =  eXLedgerMongoCustomRepository.getCassinoProfitLoss(filterData.get("startdate"), filterData.get("enddate"), usersession.getUsertype(), userId, filterData,matchIds);
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", list);
			
			
			
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

}







