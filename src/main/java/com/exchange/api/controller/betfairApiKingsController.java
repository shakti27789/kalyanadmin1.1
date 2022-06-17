package com.exchange.api.controller;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@ControllerAdvice
@RequestMapping("/")
public class betfairApiKingsController {
	
	@RequestMapping(value ="/listEventTypesKings",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listEventTypesKings()
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();

			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject request = new JSONObject();
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONArray eventTypeIds = new JSONArray();
				
				request.put("eventTypeIds",eventTypeIds);
				filter.put("filter", request);
				params.put("params", filter);
				HttpEntity<String> entity = new HttpEntity<String>(params.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/sports", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				//System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	 
	 @RequestMapping(value ="/listCompetitionsKings/{sportid}",method = RequestMethod.GET, produces= "application/json")
		public ResponseEntity<Object>  listCompetitionsKings(@PathVariable("sportid") String sportid)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();

			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject request = new JSONObject();
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONArray eventTypeIds = new JSONArray();
				eventTypeIds.put(sportid);
				request.put("inPlayOnly",false);
				request.put("eventTypeIds",eventTypeIds);
				filter.put("filter", request);
				params.put("params", filter);
				HttpEntity<String> entity = new HttpEntity<String>(params.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/competitions", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				//System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	 @RequestMapping(value ="/listEventsKings/{sportid}//{comptid}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listEventsKings(@PathVariable("sportid") Integer sportid,@PathVariable("comptid") String comptid)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();

			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject request = new JSONObject();
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONArray eventTypeIds = new JSONArray();
				eventTypeIds.put(sportid);
				JSONArray competitionIds = new JSONArray();
				competitionIds.put(comptid);
			    request.put("competitionIds",competitionIds);
				request.put("eventTypeIds",eventTypeIds);
				filter.put("filter", request);
				params.put("params", filter);
				HttpEntity<String> entity = new HttpEntity<String>(params.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/events", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}

	 
	 @RequestMapping(value ="/listMarketTypesKings/{eventid}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listMarketTypesKings(@PathVariable("eventid") Integer eventid)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONObject paramsobj = new JSONObject();
				JSONArray eventIds = new JSONArray();
				JSONArray marketProjection = new JSONArray();
				marketProjection.put("MARKET_START_TIME");
				marketProjection.put("RUNNER_DESCRIPTION");
				eventIds.put(eventid);
				filter.put("eventIds", eventIds);
				params.put("maxResults", 200);
				params.put("filter", filter);
				
				paramsobj.put("params", params);
				HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/marketCatalouges", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	 @RequestMapping(value ="/marketRunnerKings/{marketid}/{eventid}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  marketRunnerKings(@PathVariable("marketid") String marketid,@PathVariable("eventid") String eventid)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONObject paramsobj = new JSONObject();
				JSONArray marketIds = new JSONArray();
				JSONArray eventIds = new JSONArray();
				JSONArray marketProjection = new JSONArray();
				JSONArray marketTypeCodes = new JSONArray();
				
				marketProjection.put("MARKET_START_TIME");
				marketProjection.put("RUNNER_DESCRIPTION");
				marketIds.put(marketid);
				marketTypeCodes.put("MATCH_ODDS");
				eventIds.put(eventid);
				
				
				filter.put("marketIds", marketIds);
				filter.put("marketTypeCodes", marketTypeCodes);
				filter.put("eventIds", eventIds);
				params.put("maxResults", 1);
				params.put("marketProjection", marketProjection);
				params.put("filter", filter);
				paramsobj.put("params", params);
				HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/marketCatalouges", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	 @RequestMapping(value ="/listMarketBookKings/{marketId}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listMarketBookKings(@PathVariable("marketId") String marketId)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();
				String market[] =marketId.split(",");
			 	
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject request = new JSONObject();
				JSONObject priceProjection = new JSONObject();
				JSONObject params = new JSONObject();
				JSONObject paramsobj = new JSONObject();
				JSONArray marketIds = new JSONArray();
				for(String s :market){
					marketIds.put(s);
			 	}

			
				JSONArray priceData = new JSONArray();
				priceData.put("EX_BEST_OFFERS");
				request.put("inPlayOnly",false);
				
				priceProjection.put("priceData", priceData);
				priceProjection.put("virtualise",true);
				params.put("marketIds", marketIds);
				params.put("priceProjection", priceProjection);
				paramsobj.put("params", params);
				HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/odds", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}

	 
	 
	 @RequestMapping(value ="/listMarketBookKingsApi/{marketId}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listMarketBookKingsApi(@PathVariable("marketId") String marketId)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();
				String market[] =marketId.split(",");
			 	
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject request = new JSONObject();
				JSONObject priceProjection = new JSONObject();
				JSONObject params = new JSONObject();
				JSONObject paramsobj = new JSONObject();
				JSONArray marketIds = new JSONArray();
				for(String s :market){
					marketIds.put(s);
			 	}

			
				/*JSONArray priceData = new JSONArray();
				priceData.put("EX_BEST_OFFERS");
				request.put("inPlayOnly",false);
				
				priceProjection.put("priceData", priceData);
				priceProjection.put("virtualise",true);*/
				params.put("marketIds", marketIds);
				//params.put("listMarketBookParameters ", priceProjection);
				/*paramsobj.put("params", params);*/
				HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://185.16.206.13:8080/api//listMarketBook", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				System.out.println(response);
				///return new ResponseEntity<String>(result, HttpStatus.OK);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	 
	 
	 @RequestMapping(value ="/allMarketRunnerKings/{marketid}/{eventid}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  allMarketRunnerKings(@PathVariable("marketid") String marketid,@PathVariable("eventid") String eventid)
		{
		
		 try
			{
			   
			 	RestTemplate restTemplate = new RestTemplate();
			 	HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				JSONObject filter = new JSONObject();
				JSONObject params = new JSONObject();
				JSONObject paramsobj = new JSONObject();
				JSONArray marketIds = new JSONArray();
				JSONArray eventIds = new JSONArray();
				JSONArray marketProjection = new JSONArray();
				JSONArray marketTypeCodes = new JSONArray();
				
				marketProjection.put("MARKET_START_TIME");
				marketProjection.put("RUNNER_DESCRIPTION");
				marketProjection.put("RUNNER_METADATA");
				marketIds.put(marketid);
		/*		marketTypeCodes.put("MATCH_ODDS");
				marketTypeCodes.put("Over/Under 7.5 Goals");
				marketTypeCodes.put("MATCH_ODDS");
				marketTypeCodes.put("MATCH_ODDS");*/
				eventIds.put(eventid);
				
				
				filter.put("marketIds", marketIds);
				filter.put("marketTypeCodes", marketTypeCodes);
				filter.put("eventIds", eventIds);
				params.put("maxResults", 100);
				params.put("marketProjection", marketProjection);
				params.put("filter", filter);
				paramsobj.put("params", params);
				HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
				ResponseEntity<String> response = restTemplate.exchange("http://api.fairbetexch.com/api/rest/marketCatalouges", HttpMethod.POST, entity, String.class);
				if (response.getStatusCode() == HttpStatus.OK) 
				{
					return new ResponseEntity<Object>(response.getBody(), HttpStatus.OK);
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 

	 @RequestMapping(value ="/listMarketBookCricflame3/{marketIds}",method = RequestMethod.GET,produces= "application/json")
		public ResponseEntity<Object>  listMarketBookCricflame3(@PathVariable("marketIds") String marketIds)
		{ 
		
		 try
			{
			  
			 	RestTemplate restTemplate = new RestTemplate();
			   String result = restTemplate.getForObject("http://3.7.102.54/listMarketBookCricflame3/"+marketIds, String.class);
			   return new ResponseEntity<Object>(result, HttpStatus.OK);
			   
        }
			catch(Exception e){
				e.printStackTrace();
			}
		return new ResponseEntity<Object>( HttpStatus.OK);
		}
	 
	        @RequestMapping(value ="/dim",method = RequestMethod.GET)
			public ResponseEntity<Object>  casinoTvT(){
			
			 try 
				{
				     HashMap<String, String> hm = new HashMap<String, String>();
					
				  
				    HttpHeaders headers = new HttpHeaders();
				    RestTemplate restTemplate = new RestTemplate();
					
					 HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
						
					ResponseEntity<String> getcasinoTvToken = restTemplate.exchange("http://backlayexchange.com/new/luckya", HttpMethod.GET,entity,String.class);
					
					 hm.put("getcasinoTvToken", getcasinoTvToken.getBody());
						
					 return new ResponseEntity<Object>(getcasinoTvToken.getBody(), HttpStatus.OK);
				
				 
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return new ResponseEntity<Object>( HttpStatus.BAD_REQUEST);
			}
	 
}
