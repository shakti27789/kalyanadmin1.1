package com.exchange.api.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import org.apache.tomcat.jni.Library;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.EventRates;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.SelectionIds;
import com.exchange.api.bean.Sport;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.util.EXDateUtil;
import com.exchange.util.EXGlobalConstantProperties;

@Service("eventService")
public class EventServiceImpl implements EventService{

		@Autowired
	MarketRepository marketRepository;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;

	@Autowired
	SelectionIdRepository selectionIdRepository;
	
	@Autowired
	LiabilityRepository liabilityRepository;
	
	@Autowired 
	SportRepository sportRepo;
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;

	@Override
	public ArrayList<EventRates> getEventRatesList(ArrayList<Market> marketList,String type ) {
		StringBuffer marketids = new StringBuffer();
	
		ArrayList<EventRates> ratesList = new ArrayList<>();
		ArrayList<EventRates> inPlayList = new ArrayList<>();
		String json =null;
		try{
			for(Market mrkt :marketList){
				
		
				
				Date date = new Date();
				  DateFormat est = new SimpleDateFormat();
				   TimeZone estTime = TimeZone.getTimeZone("IST");
				  DateFormat gmt = new SimpleDateFormat();
				  TimeZone gmtTime = TimeZone.getTimeZone("GMT");
				  est.setTimeZone(gmtTime);
				  gmt.setTimeZone(estTime);
				EXDateUtil util = new EXDateUtil();
				
				String sport = null;
				if(mrkt.getSportid()==4){
					sport = "Cricket";
				}else if(mrkt.getSportid()==2){
					sport = "Tennis";
				}else if(mrkt.getSportid()==1){
					sport = "Football";
				}
				EventRates List = new EventRates();
				List.setDate(util.convTimeZone(mrkt.getOpendate(), "GMT", "IST", "yyyy-MM-dd HH:mm:ss"));
    			List.setMarketid(mrkt.getMarketid());
    			List.setMatchname(mrkt.getMatchname());
    			List.setEventid(mrkt.getEventid());
    			List.setSportid(mrkt.getSportid());
    			inPlayList.add(List);
				/*json = new String(Files.readAllBytes(Paths.get(sport+"/"+mrkt.getEventid()+".json")), StandardCharsets.UTF_8);
				
				 if(json!=null){
					 JSONArray response = new JSONArray(json.toString());
					 for (int i = 0;i<response.length();i++){
						 			EventRates List = new EventRates();
						 			
						 			List.setDate(util.convTimeZone(mrkt.getOpendate(), "GMT", "IST", "yyyy-MM-dd HH:mm:ss"));
		                			List.setInplay(response.getJSONObject(i).getBoolean("inplay"));
		                        	List.setMarketid(response.getJSONObject(i).getString("marketId"));
		                			List.setMatchname(marketRepository.findBymarketid(response.getJSONObject(i).getString("marketId").toString()).getMatchname());
		                			List.setEventid(marketRepository.findBymarketid(response.getJSONObject(i).getString("marketId").toString()).getEventid());
		                			List.setSportid(marketRepository.findBymarketid(response.getJSONObject(i).getString("marketId").toString()).getSportid());
		                	
		                	JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
		                		
		                	JSONObject ex = runners.getJSONObject(0).getJSONObject("ex");
		                	JSONObject ex1 = runners.getJSONObject(1).getJSONObject("ex");

		                	if(ex.getJSONArray("availableToBack").length()!=0 && ex.getJSONArray("availableToBack")!=null){
		                    	JSONArray availableToBack = ex.getJSONArray("availableToBack");            		
		                        List.setBack1(availableToBack.getJSONObject(0).getString("price"));
		                        List.setBacksize1(availableToBack.getJSONObject(0).getString("size"));
		                	}
		                	if(ex.getJSONArray("availableToLay").length()!=0 && ex.getJSONArray("availableToLay")!=null){
		                		JSONArray availableToLay = ex.getJSONArray("availableToLay");
		                        List.setLay1(availableToLay.getJSONObject(0).getString("price"));
		                        List.setLaysize1(availableToLay.getJSONObject(0).getString("size"));
		                     }
		                	if(ex1.getJSONArray("availableToBack").length()!=0 && ex1.getJSONArray("availableToBack")!=null){
		                    	JSONArray availableToBack1 = ex1.getJSONArray("availableToBack");
		                    	List.setBack2(availableToBack1.getJSONObject(0).getString("price")); 
		                    	List.setBacksize2(availableToBack1.getJSONObject(0).getString("size"));
		                	}
		                	if(ex1.getJSONArray("availableToLay").length()!=0 && ex1.getJSONArray("availableToLay")!=null){
		                		JSONArray availableToLay1 = ex1.getJSONArray("availableToLay");
		                        List.setLay2(availableToLay1.getJSONObject(0).getString("price"));
		                        List.setLaysize2(availableToLay1.getJSONObject(0).getString("size"));
		                	}
		                    	
		                	if(response.getJSONObject(i).getInt("numberOfRunners") == 3){
		                    	JSONObject ex2 = runners.getJSONObject(2).getJSONObject("ex");
		                    	if(ex2.getJSONArray("availableToBack").length()!=0 && ex2.getJSONArray("availableToBack")!=null){
		                    		JSONArray availableToBack2 = ex2.getJSONArray("availableToBack");	
		                        	List.setBack3(availableToBack2.getJSONObject(0).getString("price"));
		                        	List.setBacksize3(availableToBack2.getJSONObject(0).getString("size"));
		                    	}
		                    	if(ex2.getJSONArray("availableToLay").length()!=0 && ex2.getJSONArray("availableToLay")!=null){
		                    		JSONArray availableToLay2 = ex2.getJSONArray("availableToLay");
		                        	List.setLay3(availableToLay2.getJSONObject(0).getString("price"));
		                        	List.setLaysize3(availableToLay2.getJSONObject(0).getString("size"));
		                    	}                    	                    			

		                    }	
		                	inPlayList.add(List);	
			             
						  }
					
				 }*/
			}
			
                
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inPlayList;
	}

	@Override
	public String lotusMatchOdds(Integer eventid, Integer sportid) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json=null;
		String result = restTemplate.getForObject("https://www.matador.games/api/exchange/odds/sma-event/MTGJ/"+sportid+"/"+eventid, String.class);
		try {
			 json  = new JSONObject(result);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}

	@Override
	public ArrayList<EventRates> getInPlayList(ArrayList<Market> market) {
		StringBuffer marketids = new StringBuffer();
		//ArrayList<Market> marketList = marketRepository.findByIsactiveAndSportidAndMarketname(true, sportid,"Match Odds");
		for(int i=0;i<market.size();i++){
			if(marketids.length()!=0){
				marketids.append(","+market.get(i).getMarketid());
			}else{
				marketids.append(market.get(i).getMarketid());				
			}
		}
			
		ArrayList<EventRates> ratesList = new ArrayList<>();
		ArrayList<EventRates> inPlayList = new ArrayList<>();
		
		RestTemplate restTemplate = new RestTemplate();
		String Stresponse = restTemplate.getForObject("https://raviapi.azurewebsites.net/api/listmarketBook?marketIds=1.159355822"+marketids, String.class);
		
		try{
			JSONArray response=  new JSONArray(Stresponse);
            
                for (int i = 0;i<response.length();i++){
                	EventRates List = new EventRates();
                	for(int j=0;j<market.size();j++){
                		if(market.get(j).getMarketid().equals(response.getJSONObject(i).getString("marketId")) && response.getJSONObject(i).getBoolean("complete")!= true){
                        	List.setInplay(response.getJSONObject(i).getBoolean("inplay"));
                        	List.setMarketid(response.getJSONObject(i).getString("marketId"));
                			List.setMatchname(market.get(j).getMatchname());
                			List.setEventid(market.get(j).getEventid());
                			List.setSportid(market.get(j).getSportid());
       
                	if(List.getInplay().equals(true)){
                	inPlayList.add(List);	
                	}else{
                		ratesList.add(List);	
                		}
                	}
                }
            }
            
         inPlayList.addAll(ratesList);
                
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inPlayList;

	}
	
	
	@Override
	public ArrayList<EventRates> getEventNameList(ArrayList<Market> marketList) {
		StringBuffer marketids = new StringBuffer();
		//ArrayList<Market> marketList = marketRepository.findByIsactiveAndSportidAndMarketname(true, sportid,"Match Odds");
		for(int i=0;i<marketList.size();i++){
			if(marketids.length()!=0){
				marketids.append(","+marketList.get(i).getMarketid());
			}else{
				marketids.append(marketList.get(i).getMarketid());				
			}
		}
			 
		ArrayList<EventRates> ratesList = new ArrayList<>();
		ArrayList<EventRates> inPlayList = new ArrayList<>();
		
		RestTemplate restTemplate = new RestTemplate();
		String Stresponse = restTemplate.getForObject("https://raviapi.azurewebsites.net/api/listmarketBook?marketIds="+marketids, String.class);
		
		try{
			if(!Stresponse.equalsIgnoreCase("[]")){
				JSONArray response=  new JSONArray(Stresponse);
	            
                for (int i = 0;i<response.length();i++){
                	EventRates List = new EventRates();
                	for(int j=0;j<marketList.size();j++){
                		if(marketList.get(j).getMarketid().equals(response.getJSONObject(i).getString("marketId"))&&response.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")){
                        	List.setInplay(response.getJSONObject(i).getBoolean("inplay"));
                        	List.setMarketid(response.getJSONObject(i).getString("marketId"));
                			List.setMatchname(marketList.get(j).getMatchname());
                			List.setEventid(marketList.get(j).getEventid());
                			List.setSportid(marketList.get(j).getSportid());
                			inPlayList.add(List);
                		}
                }
            }
			}
			
                            
		}catch (Exception e) {
			e.printStackTrace();
		}
		return inPlayList;
	}
	
	/*@Override
	public JSONObject getEventRatesListInplay(ArrayList<Market> marketList,String type,EXUser usersession ) {
		StringBuffer marketids = new StringBuffer();
	
		ArrayList<EventRates> ratesList = new ArrayList<>();
		ArrayList<EventRates> inPlayList = new ArrayList<>();
		JSONObject matchOdds = new JSONObject();
		
		String json =null;
		try{
			JSONArray result = new JSONArray();

			for(Market mrkt :marketList){
				
				
				String sportname = null;
				if(mrkt.getSportid()==4){
					sportname = "Cricket";
				}else if(mrkt.getSportid()==2){
					sportname = "Tennis";
				}else if(mrkt.getSportid()==1){
					sportname = "Football";
				}
				
				json = new String(Files.readAllBytes(Paths.get(sportname+"/"+mrkt.getEventid()+".json")), StandardCharsets.UTF_8);
				
				 if(json!=null){
					 JSONArray response = new JSONArray(json.toString());
					 for (int i = 0;i<response.length();i++){
						 		if(response.getJSONObject(i).getBoolean("inplay") && response.getJSONObject(i).getString("status").equalsIgnoreCase("OPEN")){
						 			UserLiability liability = new UserLiability();
						 			liability = liabilityRepository.findByMarketidAndUseridAndTypeAndIsactive(mrkt.getMarketid(), usersession.getUserid(),"Match Odds",true);

						 									 			
						 			JSONObject odds = new JSONObject();	
									odds.put("id", response.getJSONObject(i).getString("marketId"));
									odds.put("name", "Match Odds");
									odds.put("sid", mrkt.getCricexchageid());
									odds.put("betfair", true);
									if(mrkt.getIspause()){
										odds.put("ispause", "betting-disabled");
											
									}else{										
										odds.put("ispause", "");
											
									}
									JSONArray runner = new JSONArray();
									BigDecimal back1= new BigDecimal("0.0");
									BigDecimal back2 =new BigDecimal("0.0");
									BigDecimal back3 =new BigDecimal("0.0");
									BigDecimal lay1= new BigDecimal("0.0");
									BigDecimal lay2=new BigDecimal("0.0");
									BigDecimal lay3=new BigDecimal("0.0");
									BigDecimal team1 =new BigDecimal("0.0");
									BigDecimal team2 =new BigDecimal("0.0");
									BigDecimal team3 =new BigDecimal("0.0");
									BigDecimal back4 =new BigDecimal("0.0");
									JSONArray runners1 =null;
									JSONArray backrunner1 =null;
									JSONArray layrunner1 =null;
									JSONArray back =null;
									ArrayList<Integer> selIds = new ArrayList<Integer>();
									JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
									for(int j=0;j<runners.length();j++){
										back = new JSONArray();
										JSONArray lay = new JSONArray();
										JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");
										if(ex.getJSONArray("availableToBack").length()!=0 && ex.getJSONArray("availableToBack")!=null){
											JSONArray availableBack = ex.getJSONArray("availableToBack");            		
											selIds.add(j+1);           		
										//	String bhaotype = "0.0"+usersession.getBhaotype();
											if(availableBack.length()>2){
												for(int b=0;b<availableBack.length();b++){
													JSONObject backObject = new JSONObject();
													BigDecimal backsize = new BigDecimal(availableBack.getJSONObject(b).getString("price"));
													if(j==0){
														if(b == 0){
															back1 =backsize;
														}
													}
													else if(j==1){
														if(b == 0){
															back2 =backsize;
														}
													}
													else if(j==2){
														if(b == 0){
															back3 =backsize;
														}
													}

													backObject.put("price", backsize);
													backObject.put("size", availableBack.getJSONObject(b).get("size"));
													back.put(backObject);
												}
											}else if(availableBack.length()==2){


												JSONObject backObject1 = new JSONObject();
												JSONObject backObject2 = new JSONObject();
												JSONObject backObject3 = new JSONObject();
												backObject1.put("price", new BigDecimal(availableBack.getJSONObject(0).getString("price")));
												backObject1.put("size",new BigDecimal(availableBack.getJSONObject(0).getString("size")));
												backObject2.put("price", new BigDecimal(availableBack.getJSONObject(1).getString("price")));
												backObject2.put("size",new BigDecimal(availableBack.getJSONObject(1).getString("size")));
												backObject3.put("price", 0.0);
												backObject3.put("size",0.0);
												back.put(backObject1);
												back.put(backObject2);
												back.put(backObject3);
											}
											else if(availableBack.length()==1){

												JSONObject backObject1 = new JSONObject();
												JSONObject backObject2 = new JSONObject();
												JSONObject backObject3 = new JSONObject();
												backObject1.put("price", new BigDecimal(availableBack.getJSONObject(0).getString("price")));
												backObject1.put("size",new BigDecimal(availableBack.getJSONObject(0).getString("size")));
												backObject2.put("price", 0.0);
												backObject2.put("size",0.0);
												backObject3.put("price", 0.0);
												backObject3.put("size",0.0);
												back.put(backObject1);
												back.put(backObject2);
												back.put(backObject3);

											}

										}else{

											JSONObject backObject1 = new JSONObject();
											JSONObject backObject2 = new JSONObject();
											JSONObject backObject3 = new JSONObject();
											backObject1.put("price", 0.0);
											backObject1.put("size",0.0);
											backObject2.put("price", 0.0);
											backObject2.put("size",0.0);
											backObject3.put("price", 0.0);
											backObject3.put("size",0.0);
											back.put(backObject1);
											back.put(backObject2);
											back.put(backObject3);
										}

										if(ex.getJSONArray("availableToLay").length()!=0 && ex.getJSONArray("availableToLay")!=null){
											JSONArray availableLay = ex.getJSONArray("availableToLay");

											if(availableLay.length()>2){
												for(int l = 0;l<availableLay.length();l++){
													JSONObject layObject = new JSONObject();
													layObject.put("price", availableLay.getJSONObject(l).get("price")); 
													layObject.put("size", availableLay.getJSONObject(l).get("size"));
													BigDecimal laysize = new BigDecimal(availableLay.getJSONObject(l).getString("price"));
													if(j==0){
														if(l == 0){
															lay1 =laysize;
														}
													}
													else if(j==1){
														if(l == 0){
															lay2 =laysize;
														}
													}

													else if(j==2){
														if(l == 0){
															lay3 =laysize;
														}
													}

													lay.put(layObject);
												}    
											}else if(availableLay.length()==2){
												JSONObject layObject1 = new JSONObject();
												JSONObject layObject2 = new JSONObject();
												JSONObject layObject3 = new JSONObject();
												layObject1.put("price", new BigDecimal(availableLay.getJSONObject(0).getString("price")));
												layObject2.put("size",new BigDecimal(availableLay.getJSONObject(0).getString("size")));
												layObject3.put("price", new BigDecimal(availableLay.getJSONObject(1).getString("price")));
												layObject1.put("size",new BigDecimal(availableLay.getJSONObject(1).getString("price")));
												layObject2.put("price", 0.0);
												layObject3.put("size",0.0);
												lay.put(layObject1);
												lay.put(layObject2);
												lay.put(layObject3);

											}else if(availableLay.length()==1){
												JSONObject layObject1 = new JSONObject();
												JSONObject layObject2 = new JSONObject();
												JSONObject layObject3 = new JSONObject();
												layObject1.put("price", new BigDecimal(availableLay.getJSONObject(0).getString("price")));
												layObject2.put("size",new BigDecimal(availableLay.getJSONObject(0).getString("size")));
												layObject3.put("price",0.0);
												layObject1.put("size",0.0);
												layObject2.put("price", 0.0);
												layObject3.put("size",0.0);
												lay.put(layObject1);
												lay.put(layObject2);
												lay.put(layObject3);

											}

										}else{


											JSONObject layObject1 = new JSONObject();
											JSONObject layObject2 = new JSONObject();
											JSONObject layObject3 = new JSONObject();
											layObject1.put("price", 0.0);
											layObject2.put("size",0.0);
											layObject3.put("price", 0.0);
											layObject1.put("size",0.0);
											layObject2.put("price", 0.0);
											layObject3.put("size",0.0);
											lay.put(layObject1);
											lay.put(layObject2);
											lay.put(layObject3);

										}

										if(j==0){
											team1 = back1.add(lay1);

										}

										if(j==1){
											team2 = back2.add(lay2);

										}

										if(j==2){
											team3 = back3.add(lay3);

										}

										JSONObject selectionid = new JSONObject();
										selectionid.put("id", runners.getJSONObject(j).getString("selectionId"));
										selectionid.put("name", selectionIdRepository.findBySelectionidAndMarketid(Integer.parseInt(runners.getJSONObject(j).getString("selectionId")),mrkt.getMarketid()).getRunnerName());
										selectionid.put("back", back);
										selectionid.put("lay",lay);
										if(liability!=null){
											if(j==0){
												selectionid.put("pnllib", liability.getPnl1());
											} if(j==1){
												selectionid.put("pnllib",liability.getPnl2());
											}if(j==2){
												selectionid.put("pnllib",liability.getPnl3());
											}
										}
									
										
										runner.put(selectionid);
										
										
									}
									
									if(selIds.size()>2){
										if(mrkt.getSportid() ==2){

											for(int k =0; k<runner.length();k++){
												
											    runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",  Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));
											   	runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price",  0.0);
											    
											    
											}
										}else{
											

											   if Test Match or Football 
											if(Double.valueOf(team1.toString())<Double.valueOf(team2.toString()) &&Double.valueOf(team1.toString())<Double.valueOf(team3.toString())){


												for(int k =0; k<runner.length();k++){
													if(k==0){
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>1.14){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1.subtract(new BigDecimal("0.01")));

															}else{


																if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString()) == 1.01){
																	runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", 0);

																}


															}

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));
														}

													}

												}
											}else{
												for(int k =0; k<runner.length();k++){
													if(k==0){

														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(1).getString("price")));

														}else if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())== 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));

														}else{
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay1);

														}
													}

												}
											}
											if(Double.valueOf(team2.toString())<Double.valueOf(team1.toString()) &&Double.valueOf(team2.toString())<Double.valueOf(team3.toString())){

												for(int k =0; k<runner.length();k++){
													if(k==1){
		 

														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>1.14){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2.subtract(new BigDecimal("0.01")));

															}else{
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString()) ==1.01){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", 0);

															}
					
															}
														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));
														}
													}
												}
											}
											else {
												for(int k =0; k<runner.length();k++){
													if(k==1){


														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(1).getString("price")));

														}else if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", lay2);

														}
													}

												}
											}

											if(Double.valueOf(team3.toString())<Double.valueOf(team1.toString()) &&Double.valueOf(team3.toString())<Double.valueOf(team1.toString())){

												for(int k =0; k<runner.length();k++){
													if(k==2){

														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>1.14){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3.subtract(new BigDecimal("0.01")));

															}else{
																if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString()) == 1.01){
																	runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", 0);
																}
																

															}
														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));
														}
													}
												}
											}
											else {
												for(int k =0; k<runner.length();k++){
													if(k==2){


														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(1).getString("price")));

														}else if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));

														}else{
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay3);

														}
													}

												}
											}
										
										
										}
									}else{
										if(mrkt.getSportid() ==2){

											for(int k =0; k<runner.length();k++){
												
											    runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",  Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));
											   	runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", 0.0);
											    
											    
											}
										}else{
											
											 if Odi,T20,T10 Match or Tennis 
											if(Double.valueOf(team1.toString())<Double.valueOf(team2.toString())){


												for(int k =0; k<runner.length();k++){
													if(k==0){
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>1.14){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1.subtract(new BigDecimal("0.01")));

															}else{


																if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString()) == 1.01){
																	runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", 0);

																}


															}

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));
														}
													
													}

												}
											}else{
												for(int k =0; k<runner.length();k++){
													if(k==0){

														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);

														}
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(1).getString("price")));

														}else if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));

														}else{
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay1);

														}
													}

												}
											}
											if(Double.valueOf(team2.toString())<Double.valueOf(team1.toString())){

												for(int k =0; k<runner.length();k++){
													if(k==1){
		 

														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>1.14){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2.subtract(new BigDecimal("0.01")));

															}else{
															if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString()) ==1.01){
																runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", 0);

															}
														}
														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);

														}
														
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));
														}
													}
												}
											}
											else {
												for(int k =0; k<runner.length();k++){
													if(k==1){


														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));

														}else{
															runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);

														}
														
														if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", Double.valueOf(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(1).getString("price")));

														}else if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString()) == 1.01){
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", new BigDecimal("1.02"));

														}else{
															runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay2);

														}
													}

												}
											}

											
										}
									}
							


									JSONObject event = new JSONObject();
									JSONObject sport = new JSONObject();
									JSONObject minmax = new JSONObject();
									sport.put("sportid", mrkt.getSportid());
									sport.put("name", sportname);
									event.put("name", mrkt.getMatchname());
									event.put("id", mrkt.getEventid());
									Event evnt = eventRepo.findByeventid(market.getEventid().toString());
									if(eventid!=null){
										minmax.put("minbet", evnt.getMinbet());
										minmax.put("maxbet", evnt.getMaxbet());
									}
									odds.put("event",event);
									odds.put("sport",sport);
									odds.put("minmax",minmax);
									odds.put("runners", runner);
									result.put(odds);

						 		
						 		
						 		
						 }	
			                	
			            }
					
				 }
			}
			 matchOdds.put("result", result);
			
			for(EXdevelopersOdss beanodds : devOdddsbeanlist){
				if(beanodds !=null){}
				
			}
			
              
           if(type.equalsIgnoreCase("matchlist")){
        	   inPlayList.addAll(ratesList);
           } 
        
                
		}catch (Exception e) {
			e.printStackTrace();
		}
		return matchOdds;
	}
*/
	@Override
	 public ArrayList<LinkedHashMap<String, Object>> leftMenuData() {
		// TODO Auto-generated method stub
		//JSONArray array = new JSONArray();  
		LinkedHashMap<String,Object> sportmap = new LinkedHashMap<String,Object>();
		ArrayList<LinkedHashMap<String,Object>> list1 = new ArrayList<LinkedHashMap<String,Object>> ();
		ArrayList<Integer> seriesid = new ArrayList<Integer>();
		ArrayList<String> dateliststr = new ArrayList<String>();
		 
		LinkedHashMap<String,Object> seriesmap = new LinkedHashMap<String,Object>();  
		ArrayList<LinkedHashMap<String,Object>> serieslist = new ArrayList<LinkedHashMap<String,Object>> ();
		
		LinkedHashMap<String,Object> datemap = new LinkedHashMap<String,Object>();  
		ArrayList<LinkedHashMap<String,Object>> datelist = new ArrayList<LinkedHashMap<String,Object>> ();
		
		LinkedHashMap<String,Object> matchmap = new LinkedHashMap<String,Object>();  
		ArrayList<LinkedHashMap<String,Object>> matchlist = new ArrayList<LinkedHashMap<String,Object>> ();
		
		LinkedHashMap<String,Object> marketmap = new LinkedHashMap<String,Object>();  
		ArrayList<LinkedHashMap<String,Object>> marketlst = new ArrayList<LinkedHashMap<String,Object>> ();
		ArrayList<Sport> sportlist  = sportRepo.findByStatus(true);
	    
		for(Sport sport : sportlist){
			sportmap=new LinkedHashMap<String,Object>();
			sportmap.put("sportId", sport.getSportId());
			sportmap.put("sportName", sport.getName());
			
			
					
					ArrayList<Market> marketlist = nativeQueryDao.leftMenuData(sport.getSportId(),"mkt.seriesid",null,false);
					if(marketlist.size()>0){
						serieslist = new ArrayList<LinkedHashMap<String,Object>> ();
						for(Market mrkt : marketlist){
							//if(!seriesid.contains(mrkt.getSeriesId())){
								dateliststr =  new ArrayList<String>();
								datelist  =  new ArrayList<LinkedHashMap<String,Object>> ();
									seriesmap = new LinkedHashMap<String,Object>(); 
									
									seriesmap.put("seriesName",mrkt.getSeriesName());
									seriesmap.put("seriesId",mrkt.getSeriesid());
									seriesid.add(mrkt.getSeriesid());
									ArrayList<Market> marketlist2 = nativeQueryDao.leftMenuData(sport.getSportId(),"mkt.eventid",mrkt.getSeriesid(),false);
									if(marketlist2.size()>0){
										for(Market mrktbean :marketlist2 ){
											if(!dateliststr.contains(mrktbean.getStartdate())){
												matchlist =new ArrayList<LinkedHashMap<String,Object>> ();
												datemap = new LinkedHashMap<String,Object>(); 
												datemap.put("startDate", mrktbean.getStartdate());	
												datelist.add(datemap);	
												dateliststr.add(mrktbean.getStartdate());
												
												matchmap =  new LinkedHashMap<String,Object>(); 
												matchmap.put("marketId", mrktbean.getMarketid());
												matchmap.put("matchId", mrktbean.getEventid());
												matchmap.put("matchName",mrktbean.getMatchname());
												ArrayList<Market> mrktb = marketRepository.findByEventidAndStatus(mrktbean.getEventid(), true);
												 marketlst = new ArrayList<LinkedHashMap<String,Object>> ();
												if(mrktb.size()>0){
													for(Market mrk : mrktb){
														marketmap =  new LinkedHashMap<String,Object>(); 
														marketmap.put("marketName", mrk.getMarketname());
														marketmap.put("marketId", mrk.getMarketid());
														marketmap.put("sportId", mrk.getSportid());
														marketmap.put("eventId", mrk.getEventid());
														marketlst.add(marketmap);
													}
												}
											
												matchmap.put("market",marketlst);
												matchlist.add(matchmap);
												datemap.put("match", matchlist);
											}else{
												matchmap =  new LinkedHashMap<String,Object>(); 
												matchmap.put("marketId", mrktbean.getMarketid());
												matchmap.put("matchName",mrktbean.getMatchname());
												matchmap.put("matchId", mrktbean.getEventid());
												ArrayList<Market> mrktb = marketRepository.findByEventidAndStatus(mrktbean.getEventid(), true);
												marketlst = new ArrayList<LinkedHashMap<String,Object>> ();
												if(mrktb.size()>0){
													for(Market mrk : mrktb){
														marketmap =  new LinkedHashMap<String,Object>(); 
														marketmap.put("marketName", mrk.getMarketname());
														marketmap.put("marketId", mrk.getMarketid());
														marketmap.put("sportId", mrk.getSportid());
														marketmap.put("eventId", mrk.getEventid());
														marketlst.add(marketmap);
													}
												}
												matchmap.put("market",marketlst);
												matchlist.add(matchmap);
												datemap.put("match", matchlist);
											}
										}
										
									}
									
								
									seriesmap.put("date",datelist);
									serieslist.add(seriesmap);
									
									sportmap.put("series", serieslist);
							
							}
						list1.add(sportmap);

					}
			
			
			
		}
	
		
		return list1;
	}

	@Override
	public JSONObject getEventRatesListInplay(ArrayList<Market> market, String type, EXUser usersession) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
