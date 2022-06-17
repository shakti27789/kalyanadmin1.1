package com.exchange.api.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.AppCharge;
import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXMinMaxBet;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExFancyBook;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserIp;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CustomSequencesRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MinMaxBetRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.api.service.EXFancyBetService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXGlobalConstantProperties;

@Service
public class EXFancyBetServiceImpl implements EXFancyBetService {

	
	@Autowired
	MarketRepository eventMarketRepository;
	
	@Autowired
	FancyRepository fancyRepository;

	
	ResponseBean responseBean;
	
	@Autowired
	AppChargeRepo appchargeRepo;
	
	@Autowired
	UserIpRepository ipRepo;
	
	
	@Autowired
	EXUserRepository userRepo;
	
	@Autowired
	EXChipDetailRepository exchipRepo;
	
	@Autowired
	BetRepository placebetrepository;
	
	@Autowired
	MinMaxBetRepository maxRepo;
	
	@Autowired
	LiabilityRepository liabilityRepository;
	
	@Autowired
	ExMatchOddsBetDao betlistDao;
	
	@Autowired
	CustomSequencesRepository custumSeqRepo;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;
	
	@Autowired
	EventRepository eventRepo;
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;

	
	
	@Override
	@Transactional
	public ResponseBean placeFancyBet(ExMatchOddsBet placebet, EXUser user) throws InterruptedException,UnknownHostException, ParseException, JSONException, IllegalAccessException, InvocationTargetException {
		return responseBean;
		}
	
	
	
	

	public Double getFancyBookLowestValue(String fancyid,String userid,Integer matchId) {
		DecimalFormat df = new DecimalFormat("#0");
		
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			fancyList.addAll(placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(userid, fancyid,EXConstants.Fancy,true,matchId));
			ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
			
			if(!fancyList.isEmpty()){
				int first = 0;
				for(int i=0;i<fancyList.size();i++){
					int min=0;
					int max=0;
					Double liab = fancyList.get(i).getLiability();
					Double pnl = fancyList.get(i).getPnl();
					int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
					if(first==0){
						min = odds-20;
						if(min<0) min=0;
						max=odds+20;
						for(int j=min;j<=max;j++){
							ExFancyBook book = new ExFancyBook();
							
							book.setRates(Integer.parseInt(df.format(j)));
							if(fancyList.get(i).getIsback()==true){
								if(j<odds)	book.setPnl(0.00-fancyList.get(i).getLiability());
								else book.setPnl(fancyList.get(i).getPnl());
							}else{
								if(j>=odds)	book.setPnl(0.00-fancyList.get(i).getLiability());
								else 
									book.setPnl(fancyList.get(i).getPnl());								
							}
							
							fancyBook.add(book);

						}
	
						
						first=1;
					}else{
						
						Double lastpnl = fancyBook.get(fancyBook.size()-1).getPnl();
					    min = fancyBook.get(0).getRates();


						if(min<0) min=0;
						
						if(odds+20<=fancyBook.get(fancyBook.size()-1).getRates()){
							max = fancyBook.get(fancyBook.size()-1).getRates()-min;
						}else{
							max=((odds+20) - fancyBook.get(fancyBook.size()-1).getRates());
							max=max+fancyBook.size()-1;
						}
						
							for(int j=0;j<=max;j++){
								
								if(fancyBook.size()-1<j){
									ExFancyBook book = new ExFancyBook();
									book.setRates(min);
									book.setPnl(lastpnl);
									fancyBook.add(book);
								}
																
									fancyBook.get(j).setRates(Integer.parseInt(df.format(min)));
									if(fancyList.get(i).getIsback()==true){
										if(min<odds)	fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()-liab);
										else 
											fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()+pnl);
									}else if(fancyList.get(i).getIsback()==false){
										if(min<odds){
											fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()+pnl);	
										}			
										else{
											fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()-liab);
										} 

									}									

								min++;

						}
						
					}
				}
				Double lowval = 0.00;
				for(int i = 0; i <fancyBook.size(); i++){
					if(fancyBook.get(i).getPnl()< lowval){
						lowval = fancyBook.get(i).getPnl();
					}
				}
				
				return lowval;					
					
				}else{
					return 0.0;	
				}
		
	}
	
	public  int chekFancyStatus(Boolean isBack,Double pricevalue,String daimondfancyid,String uri,Integer eventid,String skyfancyid){


		int isMatched = 2;

		RestTemplate restTemplate = new RestTemplate();
		// String Stresponse = restTemplate.getForObject(uri, String.class);
		JSONObject res = null;
		Integer price = (int)Math.round(pricevalue); 
		  
		String skyexhange=null;
		Integer fancystatusdaimond=1;
		Integer fancystatussky=1;
		
		Integer fancystatusdaimondprice=1;
		Integer fancystatusskyprice=1;
		
		Event bean = eventRepo.findByEventidAndStatus(eventid, true);
		Market market = eventMarketRepository.findByStatusAndEventidAndMarketname(true, eventid, EXConstants.MatchOdds);
		Integer skybackprice =0;
		Integer daimondbackprice =0;
		Integer skylayprice =0;
		Integer daimondlayprice =0;
		if(market!=null){
			try{ /*if main fancy id daimond*/
				
				if(bean.getFancyprovider().equalsIgnoreCase(EXConstants.Diamond)){
					fancystatusdaimondprice=0;
				//	String result = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"//getSession/"+eventid+"/daimond", String.class);
					String result = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"/fancyApiDaimond1/"+bean.getEventid()+"/"+market.getMarketid(), String.class);
					if(result!=null){
						JSONArray fancarray = new JSONArray(result.toString());
						for(int i=0;i<fancarray.length();i++){
							JSONObject obj = fancarray.getJSONObject(i);
							String fid =obj.getString("id");
							if(daimondfancyid.equalsIgnoreCase(fid)){

								if(!obj.has("statusLabel")){
									obj.put("statusLabel", "");
								}
								if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.BallRunning)){
									fancystatusdaimond =1;
								}else  if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.Suspended)){
									fancystatusdaimond =1;
								}else{
									if(isBack.equals(true)){
									daimondbackprice=Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"));
										if(Double.parseDouble( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line")) == price){
											fancystatusdaimond =0;				
										}else{
											fancystatusdaimond =1;
										}								

									}else{
									  daimondlayprice =Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"));
										if(Double.parseDouble( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line")) == price){
											fancystatusdaimond =0;				
										}else{
											fancystatusdaimond =1;
										}
									}
								}
							}
						}
					}else{
						fancystatusdaimond =1;
					}
					if(!skyfancyid.equalsIgnoreCase("-1")){
						skyexhange = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"//getSession/"+eventid+"/skyexhange", String.class);
						if(skyexhange!=null){
						JSONArray fancarray = new JSONArray(skyexhange.toString());
							for(int i=0;i<fancarray.length();i++){
								JSONObject obj = fancarray.getJSONObject(i);
								String fid =obj.getString("id");
								if(!obj.has("statusLabel")){
									obj.put("statusLabel", "");
								}
								
								if(skyfancyid.equalsIgnoreCase(fid)){

									if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.BallRunning)){
										fancystatussky =1;
									}else  if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.Suspend)){
										fancystatussky =1;
									}else{
										if(isBack.equals(true)){

											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line"))== price){
												fancystatussky =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line"))-1 == price){
												fancystatussky =0;	
											}
											else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line"))+1 == price){
												fancystatussky =0;	
											}else{
												fancystatussky =1;
											}
											
											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price")) == daimondbackprice){
												fancystatusskyprice =0;				
											}else if(daimondbackprice - Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))<=25 && daimondbackprice - Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))>0){
												fancystatusskyprice =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))-daimondbackprice<=25 && Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))-daimondbackprice>0){
												fancystatusskyprice =0;				
											}
											else{
												fancystatusskyprice =1;
											}

										}else{

											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))== price){
												fancystatussky =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))-1 == price){
												fancystatussky =0;	
											}
											else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))+1 == price){
												fancystatussky =0;	
											}else{
												fancystatussky =1;
											}
											
											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price")) == daimondlayprice){
												fancystatusskyprice =0;				
											}else if(daimondlayprice-Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))<=25 &&daimondlayprice-Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))>0 ){
												fancystatusskyprice =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))-daimondlayprice<=25 && Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))-daimondlayprice>0){
												fancystatusskyprice =0;				
											}
											else{
												fancystatusskyprice =1;
											}
											
										}
									}
								}
							}
						}else{
							fancystatussky =1;
						}
					}else{
						fancystatussky =0;
						fancystatusskyprice =0;
					}


				}else{ /*if main fancy id sky*/

					fancystatusskyprice=0;
					String result = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"//getSession/"+eventid+"/skyexhange", String.class);
					if(result!=null){
						JSONArray fancarray = new JSONArray(result.toString());
				
						for(int i=0;i<fancarray.length();i++){
							JSONObject obj = fancarray.getJSONObject(i);
							String fid =obj.getString("id");
							if(!obj.has("statusLabel")){
								obj.put("statusLabel", "");
							}
							if(daimondfancyid.equalsIgnoreCase(fid)){

								if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.BallRunning)){
									fancystatussky =1;
								}else  if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.Suspend)){
									fancystatussky =1;
								}else{
									if(isBack.equals(true)){
                                       skybackprice= Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"));
										if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line")) == price){
											fancystatussky =0;				
										}else{
											fancystatussky =1;
										}								

									}else{
										  skylayprice= Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"));
											
										if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line")) == price){
											fancystatussky =0;				
										}else{
											fancystatussky =1;
										}
									}
								}
							}
						}
					}else{
						fancystatussky =1;
					}
					if(!skyfancyid.equalsIgnoreCase("-1")){
						skyexhange = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"//getSession/"+eventid+"/daimond", String.class);
						if(skyexhange!=null){
							JSONArray fancarray = new JSONArray(skyexhange.toString());
							for(int i=0;i<fancarray.length();i++){
								JSONObject obj = fancarray.getJSONObject(i);
								String fid =obj.getString("id");
								if(skyfancyid.equalsIgnoreCase(fid)){

									if(!obj.has("statusLabel")){
										obj.put("statusLabel", "");
									}
									if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.BallRunning)){
										fancystatusdaimond =1;
									}else  if(obj.getString("statusLabel").toString().equalsIgnoreCase(EXConstants.Suspended)){
										fancystatusdaimond =1;
									}else{
										if(isBack.equals(true))
										{if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line"))== price){
											fancystatusdaimond =0;				
										}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line")) -1== price){
											fancystatusdaimond =0;	
										}
										else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("line")) +1== price){
											fancystatusdaimond =0;	
										}else{
											fancystatusdaimond =1;
										}
										
										if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price")) == skybackprice){
											fancystatusdaimondprice =0;				
										}else if(skybackprice - Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))<=25 && skybackprice - Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))>0){
											fancystatusdaimondprice =0;				
										}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))-skybackprice<=25 && Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")).getJSONObject(0).getString("price"))-skybackprice>0){
											fancystatusdaimondprice =0;				
										}
										else{
											fancystatusdaimondprice =1;
										}}else{
											
											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))== price){
												fancystatusdaimond =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))-1 == price){
												fancystatusdaimond =0;	
											}
											else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("line"))+1 == price){
												fancystatusdaimond =0;	
											}else{
												fancystatusdaimond =1;
											}
											
											if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price")) == skylayprice){
												fancystatusdaimondprice =0;				
											}else if(skylayprice-Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))<=25 &&skylayprice-Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))>0 ){
												fancystatusdaimondprice =0;				
											}else if(Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))-skylayprice<=25 && Integer.parseInt( new JSONArray(new JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay")).getJSONObject(0).getString("price"))-skylayprice>0){
												fancystatusdaimondprice =0;				
											}
											else{
												fancystatusdaimondprice =1;
											}
										}
									}
								}
							}
						}else{
							fancystatusdaimond =1;
						}
					}else{
						fancystatusdaimond =0;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		}

		Integer fancystatus =1;
		if(fancystatusdaimond==0 && fancystatusdaimondprice== 0  && fancystatussky ==0 && fancystatusskyprice==0){
			fancystatus =0;
		}
		return fancystatus;

	
	}
	
	
	public Double getFancyBookLowestValueNew(@RequestParam String fancyid,String userid,ExMatchOddsBet placebet) {
		    DecimalFormat df = new DecimalFormat("#00");
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			ArrayList<Integer> sortedlist = new ArrayList<>(); 
			fancyList.addAll(placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(userid, fancyid,EXConstants.Fancy,true, placebet.getMatchid()));
			Integer mindds =0;
			Integer maxodds =0;
		
				for(ExMatchOddsBet bet :fancyList){
					sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
				}
				fancyList.add(placebet);
				sortedlist.add(Integer.parseInt(df.format(placebet.getOdds())));
				 Collections.sort(sortedlist); 
				 mindds = sortedlist.get(0)-25;
				 maxodds = sortedlist.get(sortedlist.size()-1)+25;
			
			
			 
			 Double lowval = 0.00;
			ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
			
			if(!fancyList.isEmpty()){
				int first = 0;
				for(int i=0;i<fancyList.size();i++){
					
					if(first==0){
					
						for(int j=mindds;j<=maxodds;j++){
							ExFancyBook book = new ExFancyBook();
							
							book.setRates(Integer.parseInt(df.format(j)));
							book.setPnl(0.0);
							fancyBook.add(book);
						}
						
						first=1;
					}
				}

				
			  	
	                	
				 for(int j=0; j<=fancyBook.size()-1;j++){
	                	
					 Integer min= fancyBook.get(j).getRates();
	                	
	                		for(int i=0;i<=fancyList.size()-1;i++){
		                		
	                			
	                			if(i==0){
	                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
			                    			
			                    		}
			                		}
	                			}else{
	                				
			                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			if(min<fancyList.get(i).getOdds()){
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
				                    			
			                    			}else{
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
				                    				
			                    			}
			                				
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			
			                				if(min<fancyList.get(i).getOdds()){
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
				                    			
			                				}else{
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
				                    			
			                				}
			                				
			                    		
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
			                    			
			                    		}
			                		}
	                			}
	                			
	                		
		                	}
	                	
	                
	                }
	                
				
				
				
					for(int i = 0; i <fancyBook.size(); i++){
						if(fancyBook.get(i).getPnl()< lowval){
							lowval = fancyBook.get(i).getPnl();
						}
					}
					
				}
			return lowval;		
			
		
		
		}




	/*@Override
	@Transactional
	public ResponseBean deleteFancyBetBySeries(String fancyid, Integer start, Integer end,EXUser usersession) throws Exception {
		// TODO Auto-generated method stub
		ResponseBean responseBean = new ResponseBean();
		
		
		ArrayList<ExMatchOddsBet> betlist = betlistDao.getfancybetBySeries(fancyid, start, end,false);
		
		if(betlist.size()>0){
			for(ExMatchOddsBet placeBet :betlist){
				UserLiability oldlibility = new UserLiability();
				EXUser user = userRepo.findByUserid(placeBet.getUserid());
				UserLiability liability = new UserLiability();
				if (placeBet != null) {
					Date date = new Date();
					placeBet.setIsactive(false);
					placeBet.setIsdeleted(true);
					placeBet.setDeletedBy(usersession.getId().toString());
					placeBet.setUpdatedon(date);
					placebetrepository.save(placeBet);
					
					Double val =	getFancyBookLowestValueNewdeleteBet(fancyid,placeBet.getUserid(),placeBet.getMatchid());
					liability = liabilityRepository.findByMarketidAndIsactiveAndIsresultsetAndMatchidAndUserid(placeBet.getFancyid(),true,false,placeBet.getMatchid(), placeBet.getUserid());
					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(oldlibility, liability);
					if(liability!=null){
						liability.setLibilitynew(-val);
					}
					Double userbal =oldlibility.getLibilitynew()-liability.getLibilitynew();
					nativeQueryDao.updateUserAvailableBalance(user.getId(), new BigDecimal(userbal));
					liabilityRepository.save(liability);
				}
			}
		
	}
		responseBean.setType("success");
		responseBean.setMessage("Bet Roll Back successfully");
		responseBean.setTitle("success");
		return responseBean;
}*/
	
	/*@Override
	@Transactional
	public ResponseBean rollBackFanyBet(Integer id) throws Exception {
		// TODO Auto-generated method stub
		ResponseBean responseBean = new ResponseBean();
		
		
		ExMatchOddsBet placeBet = placebetrepository.findByid(id);
		
		if(placeBet!=null){
			
				UserLiability oldlibility = new UserLiability();
				EXUser user = userRepo.findByUserid(placeBet.getUserid());
				UserLiability liability = new UserLiability();
				if (placeBet != null) {
					Date date = new Date();
					placeBet.setIsactive(true);
					placeBet.setIsdeleted(false);
					placeBet.setDeletedBy("");
					placeBet.setUpdatedon(date);
					placebetrepository.save(placeBet);
					
					Double val =	getFancyBookLowestValueNewdeleteBet(placeBet.getFancyid(),placeBet.getUserid(),placeBet.getMatchid());
					liability = liabilityRepository.findByMarketidAndIsactiveAndIsresultsetAndMatchidAndUserid(placeBet.getFancyid(),true,false,placeBet.getMatchid(), placeBet.getUserid());
					
					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(oldlibility, liability);
					if(liability!=null){
						liability.setLibilitynew(-val);
					}
					Double userbal =oldlibility.getLibilitynew()-liability.getLibilitynew();
					nativeQueryDao.updateUserAvailableBalance(user.getId(), new BigDecimal(userbal));
					liabilityRepository.save(liability);
				}
			
		
	}
		responseBean.setType("success");
		responseBean.setMessage("Bet Roll Back successfully");
		responseBean.setTitle("success");
		return responseBean;
}*/
	
	public Double getFancyBookLowestValueNewdeleteBet(@RequestParam String fancyid,String userid,Integer matchId) {
	    DecimalFormat df = new DecimalFormat("#00");
		ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		fancyList.addAll(placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(userid, fancyid,EXConstants.Fancy,true,matchId));
		Integer mindds =0;
		Integer maxodds =0;
	
			for(ExMatchOddsBet bet :fancyList){
				sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
			}
			
			 Collections.sort(sortedlist); 
			 mindds = sortedlist.get(0)-25;
			 maxodds = sortedlist.get(sortedlist.size()-1)+25;
		
		
		 
		 Double lowval = 0.00;
		ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
		
		if(!fancyList.isEmpty()){
			int first = 0;
			for(int i=0;i<fancyList.size();i++){
				
				if(first==0){
				
					for(int j=mindds;j<=maxodds;j++){
						ExFancyBook book = new ExFancyBook();
						
						book.setRates(Integer.parseInt(df.format(j)));
						book.setPnl(0.0);
						fancyBook.add(book);
					}
					
					first=1;
				}
			}

			
		  	
                	
			 for(int j=0; j<=fancyBook.size()-1;j++){
                	
				 Integer min= fancyBook.get(j).getRates();
                	
                		for(int i=0;i<=fancyList.size()-1;i++){
	                		
                			
                			if(i==0){
                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
		                			
		                			if(fancyList.get(i).getIsback()==false){
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
		                    			
		                    		}else{
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
		                    			
		                    		}
		                			
		                		}else{
		                			
		                			
		                			if(fancyList.get(i).getIsback()==false){
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
		                    			
		                    		}else{
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
		                    			
		                    		}
		                		}
                			}else{
                				
		                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
		                			
		                			if(fancyList.get(i).getIsback()==false){
		                    			if(min<fancyList.get(i).getOdds()){
		                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
			                    			
		                    			}else{
		                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
			                    				
		                    			}
		                				
		                    		}else{
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
		                    			
		                    		}
		                			
		                		}else{
		                			
		                			
		                			if(fancyList.get(i).getIsback()==false){
		                    			
		                				if(min<fancyList.get(i).getOdds()){
		                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
			                    			
		                				}else{
		                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
			                    			
		                				}
		                				
		                    		
		                    			
		                    		}else{
		                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
		                    			
		                    		}
		                		}
                			}
                			
                		
	                	}
                	
                
                }
                
			
			
			
				for(int i = 0; i <fancyBook.size(); i++){
					if(fancyBook.get(i).getPnl()< lowval){
						lowval = fancyBook.get(i).getPnl();
					}
				}
				
			}
		return lowval;		
		
	
	
	}

	  @Transactional
		@Override
		public ResponseBean deleteFancyBetById(String fancyid, Integer id, EXUser usersession) throws Exception {	
			    ResponseBean responseBean = new ResponseBean();
				
				
			 	ExMatchOddsBet placeBet = placebetrepository.findByid(id);
				
				if(placeBet!=null){
					
						UserLiability oldlibility = new UserLiability();
						UserLiability liability = new UserLiability();
						if (placeBet != null) {
							Date date = new Date();
							placeBet.setIsactive(false);
							placeBet.setIsdeleted(true);
							placeBet.setDeletedBy(usersession.getId().toString());
							placeBet.setUpdatedon(date);
							placeBet.setAddetoFirestore(false);
							placebetrepository.save(placeBet);
							
							Double val =	getFancyLaibilities(fancyid,placeBet.getUserid(),placeBet);
							liability = liabilityRepository.findByMatchidAndMarketidAndIsactiveAndUserid(placeBet.getMatchid(),placeBet.getMarketid(),true, placeBet.getUserid());
							BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
							BeanUtils.copyProperties(oldlibility, liability);
							if(liability!=null){
								liability.setLiability(-val);
							}
							liabilityRepository.save(liability);
						}
					
					responseBean.setType("success");
					responseBean.setMessage("Bet deleted successfully");
					responseBean.setTitle("success");
					return responseBean;
			}
				
				responseBean.setMessage("Something went wrong");
				responseBean.setType("error");
				responseBean.setTitle("Oops...");
				return responseBean;
		}


    @Transactional
	@Override
	public ResponseBean deleteFancyBetBySeries(String fancyid, Integer start, Integer end, EXUser usersession) throws Exception {	
		    ResponseBean responseBean = new ResponseBean();
			
			
		 	ArrayList<ExMatchOddsBet> betlist = betlistDao.getfancybetBySeries(fancyid, start, end,false);
			
			if(betlist.size()>0){
				for(ExMatchOddsBet placeBet :betlist){
					UserLiability oldlibility = new UserLiability();
					UserLiability liability = new UserLiability();
					if (placeBet != null) {
						Date date = new Date();
						placeBet.setIsactive(false);
						placeBet.setIsdeleted(true);
						placeBet.setDeletedBy(usersession.getId().toString());
						placeBet.setUpdatedon(date);
						placeBet.setAddetoFirestore(false);
						placeBet.setIsmongodbupdated(false);
						placebetrepository.save(placeBet);
						
						Double val =	getFancyLaibilities(fancyid,placeBet.getUserid(),placeBet);
						liability = liabilityRepository.findByMatchidAndMarketidAndIsactiveAndUserid(placeBet.getMatchid(),placeBet.getMarketid(),true, placeBet.getUserid());
						BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
						BeanUtils.copyProperties(oldlibility, liability);
						if(liability!=null){
							liability.setLiability(-val);
						}
						liability.setIsmongodbupdated(false);
						liabilityRepository.save(liability);
					}
				}
				responseBean.setType("success");
				responseBean.setMessage("Bet deleted successfully");
				responseBean.setTitle("success");
				return responseBean;
		}
			
			responseBean.setMessage("Something went wrong");
			responseBean.setType("error");
			responseBean.setTitle("Oops...");
			return responseBean;
	}
			
	

			public Double getFancyLaibilities(@RequestParam String fancyid,String userid,ExMatchOddsBet placebet) {
			    DecimalFormat df = new DecimalFormat("#00");
				ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
				ArrayList<Integer> sortedlist = new ArrayList<>(); 
				fancyList.addAll(placebetrepository.findByMarketidAndMatchidAndIsactiveAndUserid(fancyid, placebet.getMatchid(),true,userid));
				Integer mindds =0;
				Integer maxodds =0;
			
					for(ExMatchOddsBet bet :fancyList){
						sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
						System.out.println(bet.getPnl());
					}
					sortedlist.add(Integer.parseInt(df.format(placebet.getOdds())));
					 Collections.sort(sortedlist); 
					 mindds = sortedlist.get(0)-25;
					 maxodds = sortedlist.get(sortedlist.size()-1)+25;
				
				
				 
				 Double lowval = 0.00;
				ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
				
				if(!fancyList.isEmpty()){
					int first = 0;
					for(int i=0;i<fancyList.size();i++){
						
						if(first==0){
						
							for(int j=mindds;j<=maxodds;j++){
								ExFancyBook book = new ExFancyBook();
								
								book.setRates(Integer.parseInt(df.format(j)));
								book.setPnl(0.0);
								fancyBook.add(book);
							}
							
							first=1;
						}
					}

					
				  	
		                	
					 for(int j=0; j<=fancyBook.size()-1;j++){
		                	
						 Integer min= fancyBook.get(j).getRates();
		                	
		                		for(int i=0;i<=fancyList.size()-1;i++){
			                		
		                			
		                			if(i==0){
		                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
				                    			
				                    		}
				                		}
		                			}else{
		                				
				                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			if(min<fancyList.get(i).getOdds()){
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
					                    			
				                    			}else{
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
					                    				
				                    			}
				                				
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			
				                				if(min<fancyList.get(i).getOdds()){
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
					                    			
				                				}else{
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
					                    			
				                				}
				                				
				                    		
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getPnl())));
				                    			
				                    		}
				                		}
		                			}
		                			
		                		
			                	}
		                	
		                
		                }
		                
					
					
					
						for(int i = 0; i <fancyBook.size(); i++){
							if(fancyBook.get(i).getPnl()< lowval){
								lowval = fancyBook.get(i).getPnl();
							}
						}
						
					}
				return lowval;		
			
			}


	@Transactional
	@Override
	public ResponseBean rollBackFanyBet(Integer id) throws Exception {
		ResponseBean responseBean = new ResponseBean();
		
		
		ExMatchOddsBet placeBet = placebetrepository.findByid(id);
		
		if(placeBet!=null){
			
				UserLiability oldlibility = new UserLiability();
				UserLiability liability = new UserLiability();
				if (placeBet != null) {
					Date date = new Date();
					placeBet.setIsactive(true);
					placeBet.setIsdeleted(false);
					placeBet.setDeletedBy("");
					placeBet.setUpdatedon(date);
					placebetrepository.save(placeBet);
					
					Double val =	getFancyLaibilities(placeBet.getMarketid(),placeBet.getUserid(),placeBet);
					liability = liabilityRepository.findByMatchidAndMarketidAndIsactiveAndUserid(placeBet.getMatchid(),placeBet.getMarketid(),true, placeBet.getUserid());
					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					BeanUtils.copyProperties(oldlibility, liability);
					if(liability!=null){
						liability.setLiability(-val);
					}
					liabilityRepository.save(liability);
				}
				responseBean.setType("success");
				responseBean.setMessage("Bet deleted successfully");
				responseBean.setTitle("success");
				return responseBean;	
		}
		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return responseBean;
	}
	
	}
