package com.exchange.api.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXBetfairMarket;
import com.exchange.api.bean.EXLotusMarket;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.Market;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXGlobalConstantProperties;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXBetfairMarketController {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(EXuserController.class);


	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	MarketRepository  marketRepo;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;
	
	@RequestMapping(value = "/matchMarketList")
	public ResponseEntity<?> matchMarketList(@RequestParam String eventid){

		ArrayList<EXBetfairMarket> list = new ArrayList<EXBetfairMarket>();
		EXBetfairMarket bean =null;
		    
		RestTemplate restTemplate = new RestTemplate();
		String apiurl = restTemplate.getForObject(EXConstants.developerApi,String.class);
		String Stresponse = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1)+"listMarketTypes&EventID="+eventid, String.class);
		
		
		try {
			JSONArray market = new JSONArray(Stresponse);
			for(int i=0; i<market.length(); i++){
				
				JSONObject  array = market.getJSONObject(i);
				bean = new EXBetfairMarket();
				bean.setMarketId(array.getString("marketId"));
				bean.setMarketName(array.getString("marketName"));
				bean.setTotalMatched(array.getInt("totalMatched"));
				bean.setId(array.getString("marketId").split("\\.")[1]);
				list.add(bean);
			}

		//Event ev = eventRepo.findByeventid(Integer.parseInt(eventid));
		//if(ev.getSeriesid() == 922982) {
			String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+eventid, String.class);
			JSONObject obj = new JSONObject(new JSONObject(res).getString("data"));
			JSONArray ary = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("bookMaker"));
			
			for(int i=0; i<ary.length();i++) {
				
				bean = new EXBetfairMarket();
				bean.setMarketId(new JSONObject(ary.get(i).toString()).getString("market_id"));
				if(new JSONObject(ary.get(i).toString()).getString("marketName").contains("Bookmaker") || new JSONObject(ary.get(i).toString()).getString("marketName").contains("BOOKMAKER")) {
					bean.setMarketName("Bookmaker");
					
				}else {
					bean.setMarketName(new JSONObject(ary.get(i).toString()).getString("marketName"));
					
				}
				bean.setTotalMatched(0);
				bean.setId(new JSONObject(ary.get(i).toString()).getString("market_id").split("\\.")[1]);
				list.add(bean);
			}
		//}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(list,HttpStatus.OK);

	}
	
	
	
	@RequestMapping("/oneDayTeenPatti")
	public ResponseEntity<String>  oneDayTeenPatti(@RequestParam String id)
	{
		
		

		try
		{

			RestTemplate restTemplate = new RestTemplate();
			
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			headers.add("Referer",  "http://4bet.in");
			
			
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	        ResponseEntity<String> respEntity = restTemplate.exchange("https://shroute.casinovid.in/diamondvideo/?id=id", HttpMethod.GET, entity, String.class);
			
	        
	        
	        String s1=respEntity.getBody().replace("Player_files/encrypt.js", "http://bm.com:8081/Player_files/encrypt.js");
			String s2=s1.replace("Player_files/common.js", "http://bm.com:8081/Player_files/common.js");
			String s3=s2.replace("Player_files/player.js", "http://bm.com:8081/Player_files/player.js");
			String s4=s3.replace("Player_files/nmediaplayer.js", "http://bm.com:8081/Player_files/nmediaplayer.js");
			String s5=s4.replace("Player_files/player__.js", "http://bm.com:8081/Player_files/player__.js");
	         
			return new ResponseEntity<String>(s5, HttpStatus.OK);
			
		}
		catch(Exception e){
			log.error("error==>"+e);
		}
		return new ResponseEntity<String>( HttpStatus.OK);
	
	}
}
