package com.exchange.api.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.util.EXConstants;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXDTController {


	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EventRepository eventRepo;;
	
	@RequestMapping(value = "/VDT20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> VDT20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/betex/dt20/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	} 
	
	
	
	@RequestMapping(value = "/DT20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> DT20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/dt20/data/data.json",String.class);
			    String Url="https://casino-video.jmk888.com/dragon-tiger-20-live";
				result.put("url", Url);
				result.put("data", data);
		}
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	} 
	
	@RequestMapping(value = "/DTOneDayData", method = RequestMethod.GET)
	public ResponseEntity<Object> DTOneDayData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/dtoneday/data/data.json",String.class);
			    String Url="https://casino-video.jmk888.com/dragon-tiger-1day-live";
				result.put("url", Url);
				result.put("data", data);
		}
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	} 
	
	
	
	@RequestMapping(value = "/VDTL20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> VDTL20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/betex/dtl20/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	
	@RequestMapping(value = "/resultDetailVDT20/{marketId}", method = RequestMethod.GET)
	public ResponseEntity<Object> resultDetailVteeePatti(@PathVariable String marketId) throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject(EXConstants.casinoIp+"/CasinoAdmin/GameResultById?mid="+marketId,String.class);
			
		}else {
			
		}	
	     return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/DTPoker20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> DTPoker20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/poker20/data/data.json",String.class);
			    String Url="https://casino-video.jmk888.com/poker20-live";
				result.put("url", Url);
				result.put("data", data);
		}
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	} 
	
	
	
	
	@RequestMapping(value = "/DTPokerOnedayData", method = RequestMethod.GET)
	public ResponseEntity<Object> DTPokerOnedayData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/poker1day/data/data.json",String.class);
			    String Url="https://casino-video.jmk888.com/poker1day-live";
				result.put("url", Url);
				result.put("data", data);
		}
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	} 
	
	
	@RequestMapping(value = "/DTAb20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> DTAb20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		
		String data =null;
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/ab20/data/data.json",String.class);
			    String Url="https://casino-video.jmk888.com/diamond-andar-bahar";
				result.put("url", Url);
				result.put("data", data);
		}
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	} 
	
	

}
