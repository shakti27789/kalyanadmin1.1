package com.exchange.api.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXBetfairMarket;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.repositiry.EXDimondSkyFancyMatchRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXGlobalConstantProperties;

@Service("LotuseventService")
public class LotusEventsServiceImpl implements LotusEventsService {

	
	@Autowired
	FancyRepository fancyRepository;
	
	@Autowired
	MarketRepository marketRepository;
	


	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	SelectionIdRepository selectionIdRepository;
	
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;

	@Autowired
	MarketRepository eventMarketRepository;
	
	
	@Autowired
	EXDimondSkyFancyMatchRepository exdaimonskymatchfancyRepo;


	
/*	@Override
	public ArrayList<MatchFancy> getMatchEventList(String eventId,String fancyprovider) throws JSONException {
		Market bean  = eventMarketRepository.findByStatusAndEventidAndMarketname(true,Integer.parseInt(eventId),"Match Odds");
	
		ArrayList<MatchFancy> eventList = new ArrayList<>();
			try{
			
					RestTemplate restTemplate = new RestTemplate();
					String url =null;
					JSONArray fancarray = new JSONArray();
					if(fancyprovider.equalsIgnoreCase(EXConstants.Daimond)){
						fancyprovider ="daimond";
						HttpHeaders headers = new HttpHeaders();
						headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
						//ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl2, HttpMethod.GET,entity,String.class);
						ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl1+eventId, HttpMethod.GET,entity,String.class);
						fancarray  = new JSONArray(result1.getBody().toString());
							
					}else{
						fancyprovider ="skyexhange";
						 url  =exglobalConst.getDeveloperbetfair()+"//getSession/"+bean.getEventid()+"/skyexhange";
						 
							 
					}	
					
					
					if(fancarray.length()>0){
						
							
							
						for(int i=0;i<fancarray.length();i++){
								 MatchFancy list = new MatchFancy();
	                        	 list.setName(fancarray.getJSONObject(i).getString("RunnerName"));
	                        	 list.setIds(fancarray.getJSONObject(i).getString("SelectionId"));
	                        	 list.setFancyid(fancarray.getJSONObject(i).getString("SelectionId"));
	                        	 list.setEventid(Integer.parseInt(eventId));
	                        	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(fancarray.getJSONObject(i).getString("SelectionId"), Integer.parseInt(eventId));
	                        	 if(mfancy!=null){
	                        		 list.setIsActive(true); 
	                        	 }else{
	                        		 list.setIsActive(false);
	                        	 }
	                        	 
	                        	 if(fancarray.getJSONObject(i).has("statusLabel")){
	                        		 list.setStatus(fancarray.getJSONObject(i).getString("statusLabel"));
	                        	 }
	                        	
	                        	 eventList.add(list);
							
							
						}
							
						
					}
					
		               
			
			
	            
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return eventList;
		
	}*/
	
	
	@Override
	public ArrayList<MatchFancy> getMatchEventList(String eventId,String fancyprovider) throws JSONException {
		Market bean  = eventMarketRepository.findByStatusAndEventidAndMarketname(true,Integer.parseInt(eventId),"Match Odds");
	
		ArrayList<MatchFancy> eventList = new ArrayList<>();
			try{
			
					RestTemplate restTemplate = new RestTemplate();
					String url =null;
					JSONArray fancarray = new JSONArray();
					if(fancyprovider.equalsIgnoreCase(EXConstants.Diamond)){
						fancyprovider ="daimond";
						HttpHeaders headers = new HttpHeaders();
						headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
						//ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl2, HttpMethod.GET,entity,String.class);
						//ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl1+eventId, HttpMethod.GET,entity,String.class);
			            ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl3+eventId+"/cards.json", HttpMethod.GET,entity,String.class);
						if(!result1.getBody().toString().equalsIgnoreCase("0")) {
							fancarray  = new JSONArray(result1.getBody().toString());
							if(fancarray.length()>0){
								
								
								
								for(int i=0;i<fancarray.length();i++){
										 MatchFancy list = new MatchFancy();
			                        	 list.setName(fancarray.getJSONObject(i).getString("nat"));
			                        	 list.setIds(eventId+fancarray.getJSONObject(i).getString("sid"));
			                        	 list.setFancyid(eventId+fancarray.getJSONObject(i).getString("sid"));
			                        	 list.setEventid(Integer.parseInt(eventId));
			                        	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(eventId+fancarray.getJSONObject(i).getString("sid"), Integer.parseInt(eventId));
			                        	 if(mfancy!=null){
			                        		 list.setIsActive(true); 
			                        		 list.setMtype(mfancy.getMtype());
			                        	 }else{
			                        		 list.setIsActive(false);
			                        	 }
			                        	
			                        	 if(fancarray.getJSONObject(i).has("s")){
			                        		 list.setStatus(fancarray.getJSONObject(i).getString("s"));
			                        	 }
			                        	
			                        	 eventList.add(list);
									
									
								}
									
								
							}
							
						}
			            
							
					}
					
					
		               
			
			
	            
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return eventList;
		
	}

/*	@Override
	public Boolean SaveFancy(String id, Integer eventId,Integer appid,String fancyid,String marketid,String skyfancyid) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<MatchFancy> eventList = new ArrayList<>();
		try{
			
			String IsBettable = "";
			     MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(fancyid, eventId);
			     Event event = eventRepo.findByEventidAndStatus(eventId, true);
			     JSONArray fancarray = new JSONArray();
			     if(mfancy == null){
			    	 String fancyprovider = event.getFancyprovider();
			    		String url =null;
						if(fancyprovider.equalsIgnoreCase(EXConstants.Daimond)){
							fancyprovider ="daimond";
							HttpHeaders headers = new HttpHeaders();
							headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
							ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl1+eventId, HttpMethod.GET,entity,String.class);
								fancarray  = new JSONArray(result1.getBody().toString());
								
						}else{
							fancyprovider ="skyexhange";
							 url  =exglobalConst.getDeveloperbetfair()+"//getSession/"+eventId+"/skyexhange";
							 
								 
						}	
						
						if(fancarray.length()>0){

							
							for(int i=0;i<fancarray.length();i++){
								if(fancyid.equalsIgnoreCase(fancarray.getJSONObject(i).getString("SelectionId"))){

									MatchFancy list = new MatchFancy();

									list.setRunnerid(id);
									list.setName(fancarray.getJSONObject(i).getString("RunnerName"));
									list.setSportId(4);
									list.setEventid(eventId);
									list.setIsplay(true);
									list.setMaxLiabilityPerBet(0);
									list.setBetDelay(0);
									list.setIsBettable(1);
									list.setFancyid(fancarray.getJSONObject(i).getString("SelectionId"));
									list.setStatus("OPEN");
									list.setMatchname(eventRepo.findByeventid(eventId).getEventname());
									list.setMaxLiabilityPerMarket(0);
									list.setIssuspendedByAdmin(false);
									list.setSkyfancyid(skyfancyid);
									list.setIsActive(true);
									eventList.add(list); 

									fancyRepository.save(list);
									return false;  
								}
							}
							   
						}
				
			   }else{
				   return true;
			   }
	             
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	
	}*/

	@Override
	public Boolean SaveFancy(String id, Integer eventId,Integer appid,String fancyid,String skyfancyid,String mytype,String provider) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		 
		ArrayList<MatchFancy> eventList = new ArrayList<>();
		try{
			
			String IsBettable = "";
			     MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(fancyid, eventId);
			     Event event = eventRepo.findByEventidAndStatus(eventId, true);
			     JSONArray fancarray = new JSONArray();
			     if(mfancy == null){
			    	 String fancyprovider = event.getFancyprovider();
			    	 String url =null;
			    	
			    	 String oddstype =null;
			    		
			    	    Event ev =eventRepo.findByeventid(eventId);
			    	    
			    	if(provider.equalsIgnoreCase(EXConstants.RS)) {
			    		String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+eventId, String.class);
						JSONObject obj = new JSONObject(res);
						if(obj.getString("status").equalsIgnoreCase("1")) {
							

							JSONArray fancyMarket = null;
							
							if(fancyid.contains("F2")) {
								fancyMarket =	new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy2Market"));
								oddstype ="F2";
							}else if(fancyid.contains("F3")) { 
								fancyMarket =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy3Market"));
								oddstype ="F3";
							}else if(fancyid.contains("OE")) { 
								
								fancyMarket =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("oddEvenMarket"));
								oddstype ="OE";
							}else if(fancyid.contains("BB")) { 
								
								fancyMarket =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("oddEvenMarket"));
								oddstype ="BB";
							}
							
							for(int i=0; i<fancyMarket.length();i++) {
								if(fancyid.equalsIgnoreCase(new JSONObject(fancyMarket.get(i).toString()).getString("marketId"))){


									MatchFancy list = new MatchFancy();

									list.setRunnerid(id);
									list.setName(new JSONObject(fancyMarket.get(i).toString()).getString("title"));
									list.setSportId(4);
									list.setEventid(eventId);
									list.setIsplay(true);
									list.setMaxLiabilityPerBet(0);
									list.setBetDelay(0);
									list.setIsBettable(1);
									list.setFancyid(new JSONObject(fancyMarket.get(i).toString()).getString("marketId"));
									list.setStatus("OPEN");
									list.setMatchname(eventRepo.findByeventid(eventId).getEventname());
									list.setIssuspendedByAdmin(false);
									list.setSkyfancyid(skyfancyid);
									list.setIsActive(true);
									list.setiSfsmarkedInactive(false);
									list.setIsresultFinished(false);
									
									if(mytype.equalsIgnoreCase("session")) {
										list.setMaxLiabilityPerMarket(ev.getMaxbetfancy()*10);
										
									}else {
										list.setMaxLiabilityPerMarket(ev.getPlayermaxbetfancy()*10);
										
									}
									list.setMtype(mytype);
									list.setIsshow(true);
									list.setOddstype(oddstype);
									list.setAddby("no api");
									list.setProvider(provider);
									list.setCreatedon(new Date());
									eventList.add(list); 

									fancyRepository.save(list);
									return false;  
								
								}
							}
						}
			    		
			    		
			    	}	else if(provider.equalsIgnoreCase(EXConstants.Diamond)) {
			    		String res = restTemplate.getForObject(EXConstants.dimondFancy+eventId, String.class);	
						if(res!=null) {
							JSONObject obj = new JSONObject(res);
							if(obj.getString("t3")!=null && !obj.getString("t3").equalsIgnoreCase("null")) {
								JSONArray jarray1 =  new JSONArray(obj.getString("t3"));
								for(int i=0; i<jarray1.length();i++) {
									JSONObject obj1 = new JSONObject(jarray1.get(i).toString());
									if(!obj1.getString("ballsess").equalsIgnoreCase("3")) {
										if(fancyid.equalsIgnoreCase(obj1.getString("mid")+obj1.getString("sid")+"FY")) {
											MatchFancy mf = fancyRepository.findByFancyidAndEventid(obj1.getString("mid")+obj1.getString("sid")+"FY", eventId);
											if(mf ==null) {

											 	MatchFancy list = new MatchFancy();
											 			
												list.setRunnerid(obj1.getString("mid")+obj1.getString("sid")+"FY");
												list.setName(obj1.getString("nat"));
												list.setSportId(4);
												list.setEventid(eventId);
												list.setIsplay(true);
												list.setMaxLiabilityPerBet(0);
												list.setBetDelay(0);
												list.setIsBettable(1);
												list.setFancyid(obj1.getString("mid")+obj1.getString("sid")+"FY");
												list.setStatus("OPEN");
												list.setMatchname(eventRepo.findByeventid(eventId).getEventname());
												list.setMaxLiabilityPerMarket(0);
												list.setIssuspendedByAdmin(false);
												list.setSkyfancyid("0");
												list.setIsActive(true);
												list.setiSfsmarkedInactive(false);
												list.setIsresultFinished(false);
												if(mytype.equalsIgnoreCase("session")) {
													list.setMaxLiabilityPerMarket(ev.getMaxbetfancy()*10);
													list.setMtype("player");
													
												}else {
													list.setMaxLiabilityPerMarket(ev.getPlayermaxbetfancy()*10);
													list.setMtype("player");
													
													
												}
												list.setAddby("no api");
												list.setIsshow(true);
												list.setOddstype("F2");
												list.setProvider(EXConstants.Diamond);
												list.setCreatedon(new Date());
												fancyRepository.save(list);
												return false;  
												
												
											}
										}
										
									}
								}
							}
						}
			    	}			    	
			    		
					/*	if(fancyprovider.equalsIgnoreCase(EXConstants.Daimond)){
							fancyprovider ="daimond";
							HttpHeaders headers = new HttpHeaders();
							headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				            ResponseEntity<String> result1 = restTemplate.exchange(EXConstants.fancyUrl3+eventId+"/cards.json", HttpMethod.GET,entity,String.class);
							
							fancarray  = new JSONArray(result1.getBody().toString());
						    if(!result1.getBody().toString().equalsIgnoreCase("0")) {
						    	if(fancarray.length()>0){

									
									for(int i=0;i<fancarray.length();i++){
										if(fancyid.equalsIgnoreCase(eventId+fancarray.getJSONObject(i).getString("sid"))){

											MatchFancy list = new MatchFancy();

											list.setRunnerid(eventId+""+id);
											list.setName(fancarray.getJSONObject(i).getString("nat"));
											list.setSportId(4);
											list.setEventid(eventId);
											list.setIsplay(true);
											list.setMaxLiabilityPerBet(0);
											list.setBetDelay(0);
											list.setIsBettable(1);
											list.setFancyid(eventId+fancarray.getJSONObject(i).getString("sid"));
											list.setStatus("OPEN");
											list.setMatchname(eventRepo.findByeventid(eventId).getEventname());
											list.setMaxLiabilityPerMarket(0);
											list.setIssuspendedByAdmin(false);
											list.setSkyfancyid(skyfancyid);
											list.setIsActive(true);
											list.setiSfsmarkedInactive(false);
											list.setIsresultFinished(false);
											list.setMtype(mytype);
											list.setIsshow(true);
											eventList.add(list); 

											fancyRepository.save(list);
											return false;  
										}
									}
									   
								}
						    }		
						    
						}*/
						
						
				
			   }else{
				   return true;
			   }
	             
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	
	}
	
	
	@Override
	public LinkedHashMap<String,Object> getMatchFancy(String eventId,String fancyprovider) throws JSONException {
		Market bean  = eventMarketRepository.findByStatusAndEventidAndMarketname(true,Integer.parseInt(eventId),"Match Odds");
	
		Event event = eventRepo.findByeventid(Integer.parseInt(eventId));
				
		LinkedHashMap<String,Object> hm = new LinkedHashMap<String,Object>();
		ArrayList<MatchFancy> fancyMarket2List = new ArrayList<>();
		ArrayList<MatchFancy> fancyMarket3List = new ArrayList<>();
		ArrayList<MatchFancy> oddEvenMarketList = new ArrayList<>();
		ArrayList<MatchFancy> ballByBallMarketList= new ArrayList<>();
		ArrayList<MatchFancy> DaimonfancyList= new ArrayList<>();
			try{
				RestTemplate restTemplate = new RestTemplate();
				
				if(event.getFancyprovider().equalsIgnoreCase(EXConstants.RS) || event.getFancyprovider().equalsIgnoreCase(EXConstants.Both)) {
					
				
					String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+eventId, String.class);
					JSONObject obj = new JSONObject(res);
					if(obj.getString("status").equalsIgnoreCase("1")) {
						
						
						JSONArray fancy2Market = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy2Market"));
						JSONArray fancy3Market = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy3Market"));
						JSONArray oddEvenMarket = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("oddEvenMarket"));
						JSONArray ballSession = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("ballSession"));
						
						
						for(int i=0; i<fancy2Market.length();i++) {
						
							

					     MatchFancy list = new MatchFancy();
					     list.setOddstype("Session");
	                   	 list.setName(new JSONObject(fancy2Market.get(i).toString()).getString("title"));
	                   	 list.setIds(new JSONObject(fancy2Market.get(i).toString()).getString("marketId"));
	                   	 list.setFancyid(new JSONObject(fancy2Market.get(i).toString()).getString("marketId"));
	                   	 list.setEventid(Integer.parseInt(new JSONObject(fancy2Market.get(i).toString()).getString("eventId")));
	                   	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(new JSONObject(fancy2Market.get(i).toString()).getString("marketId"), Integer.parseInt(eventId));
	                   	 if(mfancy!=null){
	                   		 list.setIsActive(true); 
	                   		 list.setMtype(mfancy.getMtype());
	                   		 list.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket());
	                   	 }else{
	                   		 list.setIsActive(false);
	                   		list.setMaxLiabilityPerMarket(0);
	                   	 }
	                   	  list.setId(i+1);
	                   	 Integer status =new JSONObject(fancy2Market.get(i).toString()).getInt("suspended");
	                   		 
	                   		 list.setStatus(status.toString());
	                   		fancyMarket2List.add(list);
					}
						
						for(int i=0; i<fancy3Market.length();i++) {
							
							

						     MatchFancy list = new MatchFancy();
						     list.setOddstype("Other -*/+.3Market");
		                   	 list.setName(new JSONObject(fancy3Market.get(i).toString()).getString("title"));
		                   	 list.setIds(new JSONObject(fancy3Market.get(i).toString()).getString("marketId"));
		                   	 list.setFancyid(new JSONObject(fancy3Market.get(i).toString()).getString("marketId"));
		                   	 list.setEventid(Integer.parseInt(new JSONObject(fancy3Market.get(i).toString()).getString("eventId")));
		                   	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(new JSONObject(fancy3Market.get(i).toString()).getString("marketId"), Integer.parseInt(eventId));
		                   	 if(mfancy!=null){
		                   		 list.setIsActive(true); 
		                   		 list.setMtype(mfancy.getMtype());
		                   		 list.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket());
		                   	 }else{
		                   		 list.setIsActive(false);
		                   		list.setMaxLiabilityPerMarket(0);
		                   	 }
		                   	
		                   	 Integer status =new JSONObject(fancy3Market.get(i).toString()).getInt("suspended");
		                   		 
		                   	 list.setStatus(status.toString());
		                   	 list.setId(i+1);
		                   	fancyMarket3List.add(list);
						}
						

						for(int i=0; i<oddEvenMarket.length();i++) {
							
							

						     MatchFancy list = new MatchFancy();
						     list.setOddstype("oddEvenMarket");
		                   	 list.setName(new JSONObject(oddEvenMarket.get(i).toString()).getString("title"));
		                   	 list.setIds(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"));
		                   	 list.setFancyid(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"));
		                   	 list.setEventid(Integer.parseInt(new JSONObject(oddEvenMarket.get(i).toString()).getString("eventId")));
		                   	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"), Integer.parseInt(eventId));
		                   	 if(mfancy!=null){
		                   		 list.setIsActive(true); 
		                   		 list.setMtype(mfancy.getMtype());
		                   		 list.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket());
		                   	 }else{
		                   		 list.setIsActive(false);
		                   		list.setMaxLiabilityPerMarket(0);
		                   	 }
		                   	
		                   	 Integer status =new JSONObject(oddEvenMarket.get(i).toString()).getInt("suspended");
		                   		 
		                   	 list.setStatus(status.toString());
		                   	 list.setId(i+1);
		                   	 oddEvenMarketList.add(list);
						}
						
						for(int i=0; i<ballSession.length();i++) {
							
							
							
						     MatchFancy list = new MatchFancy();
						     list.setOddstype("ballSession");
		                   	 list.setName(new JSONObject(ballSession.get(i).toString()).getString("title"));
		                   	 list.setIds(new JSONObject(ballSession.get(i).toString()).getString("marketId"));
		                   	 list.setFancyid(new JSONObject(ballSession.get(i).toString()).getString("marketId"));
		                   	 list.setEventid(Integer.parseInt(new JSONObject(ballSession.get(i).toString()).getString("eventId")));
		                   	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(new JSONObject(ballSession.get(i).toString()).getString("marketId"), Integer.parseInt(eventId));
		                  	 if(mfancy!=null){
		                   		 list.setIsActive(true); 
		                   		 list.setMtype(mfancy.getMtype());
		                   		 list.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket());
		                   	 }else{
		                   		 list.setIsActive(false);
		                   		list.setMaxLiabilityPerMarket(0);
		                   	 }
		                   	
		                   	 Integer status =new JSONObject(ballSession.get(i).toString()).getInt("suspended");
		                   		 
		                   	 list.setStatus(status.toString());
		                   	 list.setId(i+1);
		                   	 ballByBallMarketList.add(list);
						}
						
					}
				}
				
				if(event.getFancyprovider().equalsIgnoreCase(EXConstants.Diamond) || event.getFancyprovider().equalsIgnoreCase(EXConstants.Both)) {
					
							
							String result = restTemplate.getForObject("http://marketsarket.in:3000/getbm?eventId="+bean.getEventid(), String.class);	
							if(result!=null) {
								
								JSONObject obj1 = new JSONObject(result);
								JSONArray jarray1 =  new JSONArray(obj1.getString("t3"));
								
							for(int i=0; i<jarray1.length();i++) {
								
								if(!new JSONObject(jarray1.get(i).toString()).getString("ballsess").equalsIgnoreCase("3")) {
									
								
								
							     MatchFancy list = new MatchFancy();
							     list.setOddstype("Session");
			                   	 list.setName(new JSONObject(jarray1.get(i).toString()).getString("nat"));
			                   	 list.setIds(new JSONObject(jarray1.get(i).toString()).getString("mid")+new JSONObject(jarray1.get(i).toString()).getString("sid")+"FY");
			                   	 list.setFancyid(new JSONObject(jarray1.get(i).toString()).getString("mid")+new JSONObject(jarray1.get(i).toString()).getString("sid")+"FY");
			                   	 list.setEventid(bean.getEventid());
			                   	 MatchFancy mfancy = fancyRepository.findByFancyidAndEventid(new JSONObject(jarray1.get(i).toString()).getString("mid")+new JSONObject(jarray1.get(i).toString()).getString("sid")+"FY", Integer.parseInt(eventId));
			                  	 if(mfancy!=null){
			                   		 list.setIsActive(true); 
			                   		 list.setMtype(mfancy.getMtype());
			                   		 list.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket());
			                   	 }else{
			                   		 list.setIsActive(false);
			                   		list.setMaxLiabilityPerMarket(0);
			                   	 }
			                   	
			                   	 String status =new JSONObject(jarray1.get(i).toString()).getString("gstatus");
			                   		 
			                   	 list.setStatus(status.toString());
			                   	 list.setId(i+1);
			                   	DaimonfancyList.add(list);
							}
							
						}
						}
							
				
				}
					
					
					
					
			
					
			LinkedHashMap<String,Object> hm1= new LinkedHashMap<String,Object>();			
		               
			
			hm.put("fancyMarket2", fancyMarket2List);
			hm.put("fancyMarket3", fancyMarket3List);
			hm.put("oddEvenMarket", oddEvenMarketList);
			hm.put("ballByBallMarket", ballByBallMarketList);
			hm.put("Daimonfancy", DaimonfancyList);
	            
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return hm;
		
	}
}
