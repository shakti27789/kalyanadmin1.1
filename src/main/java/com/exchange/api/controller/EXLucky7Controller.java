package com.exchange.api.controller;

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
import com.exchange.api.repositiry.EventRepository;
import com.exchange.util.EXConstants;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXLucky7Controller {


	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EventRepository eventRepo;
	
	@RequestMapping(value = "/VLucky7Data", method = RequestMethod.GET)
	public ResponseEntity<Object> VLucky7Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/betex/lucky7a/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/Lucky7Data", method = RequestMethod.GET)
	public ResponseEntity<Object> Lucky7Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/lucky7a/data/data.json",String.class);
			
		}
		
		//String Url="https://streaming.fawk.app/#/sevens/stream/98789?token="+obj.getString("CasinoToken");
		String Url="https://casino-video.jmk888.com/lucky7-b-live";
			
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/VRouletteData", method = RequestMethod.GET)
	public ResponseEntity<Object> VRouletteData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			
			 data = restTemplate.getForObject("https://development-924a5.firebaseio.com/betex/roulette/data/data.json",String.class);
			
		}
		
		//String Url="https://streaming.fawk.app/#/sevens/stream/98789?token="+obj.getString("CasinoToken");
		String Url="https://casino-video.jmk888.com/roulette";
			
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/resultDetailVLucky7/{marketId}", method = RequestMethod.GET)
	public ResponseEntity<Object> resultDetailVLucky7(@PathVariable String marketId) throws Exception {
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
}
