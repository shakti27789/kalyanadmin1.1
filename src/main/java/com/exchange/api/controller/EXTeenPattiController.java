package com.exchange.api.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.exchange.api.bean.DiamonCasinoResult;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.dao.EXLotusCasinoResultDao;
import com.exchange.api.repositiry.DiamondCasinoResultRepository;
import com.exchange.api.repositiry.EXChipDetailsMongoCustomRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.util.EXConstants;


@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXTeenPattiController {

	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EventRepository eventRepo;;
	
	
	@Autowired
	DiamondCasinoResultRepository diamondCasinoResultRepository;
	
	@Autowired
	EXChipDetailsMongoCustomRepository eXChipDetailsMongoCustomRepository;
	
	 
     
	
	@RequestMapping(value = "/VTP20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> TP20Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/t20/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/VOPENTPData", method = RequestMethod.GET)
	public ResponseEntity<Object> VOPENTPData() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject(EXConstants.casinoFirbase+"betex/otp/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/TP20Data", method = RequestMethod.GET)
	public ResponseEntity<Object> DTP20Data() throws Exception {
	    

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
		
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/t20/data.json",String.class);
			
		}
		
		//String Url="https://streaming.fawk.app/#/sevens/stream/56768?token="+obj.getString("CasinoToken");
	//	  String Url="https://casino-video.jmk888.com/teenpatti-oneday-live";
		  String Url="https://shroute.casinovid.in/diamondvideo/?id=3030";
		
		
		
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	

	}
	
	@RequestMapping(value = "/TeenPatti1DayData", method = RequestMethod.GET)
	public ResponseEntity<Object> TeenPatti1DayData() throws Exception {
	    

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String token =null;
		String data =null;  
		JSONObject obj = new JSONObject();
			
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
		
			 data = restTemplate.getForObject("https://aaaa-14afe.firebaseio.com/diamond/odtp/data.json",String.class);
			
		}
		
	    String Url="https://shroute.casinovid.in/diamondvideo/?id=3031";
		result.put("url", Url);
		result.put("data", data);
		
	    return new ResponseEntity<Object>(result, HttpStatus.OK);

	

	}
	
	@RequestMapping(value = "/resultDetailVteeePatti/{marketId}", method = RequestMethod.GET)
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
	
	
	@RequestMapping(value = "/resultDetailLiveCasino/{marketId}", method = RequestMethod.GET)
	public ResponseEntity<Object> resultDetailLiveCasino(@PathVariable String marketId) throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		 DiamonCasinoResult  result =null;
			if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			   result = diamondCasinoResultRepository.findByRoundId(marketId);
			System.out.println(result.getData());
		}else {
			
		}	
	     return new ResponseEntity<Object>(result.getData(), HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/casinoresult/{eventId}/{date}", method = RequestMethod.GET)
	public ResponseEntity<Object> casinoresult(@PathVariable Integer eventId,@PathVariable String date) throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 ArrayList<Integer> livegameIds = new ArrayList<Integer>();
		      livegameIds.add(5047);
		      livegameIds.add(5048);
		      livegameIds.add(5017);
		      livegameIds.add(5036);
		      livegameIds.add(5037);
		      livegameIds.add(5026);
		      livegameIds.add(5027);
		      livegameIds.add(5076);
		      livegameIds.add(5077);
		      livegameIds.add(5056);
		      
		      
			 if(livegameIds.contains(eventId)) {
				 data = restTemplate.getForObject(EXConstants.DaimonResultUrl+"/diamond-casino/results?gameId="+eventId+"&startDate="+date,String.class);
				// List<DiamonCasinoResult> resultData =	 eXChipDetailsMongoCustomRepository.getCasinoResult(date, eventId.toString());
		
				 
			 }else {
				 data = restTemplate.getForObject(EXConstants.casinoIp+"/CasinoAdmin/GameResultByIdAndDate?eventid="+eventId+"&date="+date,String.class);
				
			 }
			
			 JSONObject obj = new JSONObject(data);
			// if(obj.getBoolean("status")) {
				 return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);
			// }
		}	
	    return new ResponseEntity<Object>( HttpStatus.OK);

	}
	
	@RequestMapping(value = "/casinoEvent", method = RequestMethod.GET)
	public ResponseEntity<Object> casinoEvent() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if(usersession!=null) {
			ArrayList<Event> eventlist = eventRepo.findByType("Casino");
			return new ResponseEntity<Object>(eventlist, HttpStatus.OK);
		}	
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/VAB2Data", method = RequestMethod.GET)
	public ResponseEntity<Object> VAB2Data() throws Exception {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String data =null;
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		if(usersession!=null) {
			RestTemplate restTemplate = new RestTemplate();
			 data = restTemplate.getForObject(EXConstants.casinoFirbase+"betex/ab2/data/data.json",String.class);
			
		}else {
			
		}	
	    return new ResponseEntity<Object>(data.toString(), HttpStatus.OK);

	}
	
}
