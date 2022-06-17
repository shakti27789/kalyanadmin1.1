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
public class EXBollywoodController {


	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EventRepository eventRepo;
	
	@RequestMapping(value = "/VAAAData", method = RequestMethod.GET)
	public ResponseEntity<Object> VAAAData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/betex/aaa/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/AAAData", method = RequestMethod.GET)
	public ResponseEntity<Object> AAAData() throws Exception {
	    

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
		
			 data = restTemplate.getForObject("https://development-924a5.firebaseio.com/diamond/aaa/data.json",String.class);
			
		}
		
	    String Url="https://casino-video.jmk888.com/aaa-live";
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	

	}
	
	
	@RequestMapping(value = "/VBWTableData", method = RequestMethod.GET)
	public ResponseEntity<Object> VBWTableData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/betex/bwd/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/BwtableData", method = RequestMethod.GET)
	public ResponseEntity<Object> BwtableData() throws Exception {
	    

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
		
			 data = restTemplate.getForObject("https://development-924a5.firebaseio.com/diamond/aaa/data.json",String.class);
			
		}
		
	    String Url="https://casino-video.jmk888.com/ddb-live";
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	

	}
	
	
}
