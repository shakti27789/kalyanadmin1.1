
package com.exchange.api.bean;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.controller.EXmarketController;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CasinoBetLoackRepository;
import com.exchange.api.repositiry.EXFancyResultCronRepository;
import com.exchange.api.repositiry.EXMarketDiffRepository;
import com.exchange.api.repositiry.EXMatchAbndAndTieRepository;
import com.exchange.api.repositiry.EXRsFancyResultRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.config.EXSetResultService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;


@Component
@Configuration
@EnableScheduling
public class ScheduledConfigurationOnePaisaSatya implements SchedulingConfigurer {

	TaskScheduler taskScheduler;
	private ScheduledFuture<?> jobFootballPause;
	private ScheduledFuture<?> job2;
	private ScheduledFuture<?> jobFootballInplay;
	private ScheduledFuture<?> tennis;
	private ScheduledFuture<?> football;
	private ScheduledFuture<?> addAutoAllFancyJob;
	private ScheduledFuture<?> deleteAutoAllFancyJob;
	
    private FirebaseDatabase firebaseDatabase;
    
    EXDateUtil dtUtil = new EXDateUtil();
	
	private Firestore fb;

	
	@Autowired
	MarketRepository MarketRepo;
	
	@Autowired
	SelectionIdRepository selRepo;
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;
	
	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	FancyRepository fancyRepo;
	
	
	
	@Autowired
	LiabilityRepository userLibRepo;
	
	@Autowired
    private EXSetResultService matchresultservice;
	
	@Autowired
	FancyResultRepository fancyresultRepository;

	@Autowired
	MarketResultRepository marketResultRepository;

	@Autowired
	BetRepository placebetrepository;

	
	EXDateUtil dtutil = new EXDateUtil();
	
	@Autowired
	EXMarketDiffRepository  marketDiffRepo;
	
	@Autowired
	EXRsFancyResultRepository rsFancyResultRepo;
	
	@Autowired
	EXMatchAbndAndTieRepository matchabndtieRepo;
	
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	
	@Autowired
	EXUserRepository userRepo;
	
	@Autowired
	CasinoBetLoackRepository casinoBetLockRepo;
	
	
	private void initFirebase() {
		try {

			
		/*	ClassLoader classLoader = getClass().getClassLoader(); 
			InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
			
			
			FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(inputStream))
					.setDatabaseUrl(EXConstants.firestoreDb)
					.build();
			if(FirebaseApp.getApps().isEmpty())
				FirebaseApp.initializeApp(firebaseOptions);
			firebaseDatabase = FirebaseDatabase.getInstance();
			 fb  = com.google.firebase.cloud.FirestoreClient.getFirestore();
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static <T> ArrayList<ArrayList<T>> chopped(ArrayList<T> list, final int L)
	{
		ArrayList<ArrayList<T>> parts = new ArrayList<ArrayList<T>>();
		final int N = list.size();
		for (int i = 0; i < N; i += L)
		{
			parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}



	private static org.apache.log4j.Logger log = Logger.getLogger(ScheduledConfigurationOnePaisaSatya.class);
 
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// TODO Auto-generated method stub
		  initFirebase();
		  ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		  threadPoolTaskScheduler.setPoolSize(20);// Set the pool of threads
		  threadPoolTaskScheduler.setThreadNamePrefix("scheduler-thread");
		  threadPoolTaskScheduler.initialize();
		
		    // jobSetMatchResult(threadPoolTaskScheduler);
		  
		  
			/*
			 * jobAddFootballMatch(threadPoolTaskScheduler);
			 * jobSetMatchResult(threadPoolTaskScheduler);
			 * setFootballMatchResult(threadPoolTaskScheduler);
			 * setTennisMatchResult(threadPoolTaskScheduler);
			 */
			
			
		
		
		 
		  this.taskScheduler = threadPoolTaskScheduler;
		  taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	} 

		
	

	private void jobRemoveAddAutoFancy(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		// TODO Auto-generated method stub

		job2 = threadPoolTaskScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<Event> eventList =	eventRepo.findByStatusAndSportid(true, 4);
					RestTemplate restTemplate = new RestTemplate();
					
					
						for(Event bean:eventList) {
							

							  ArrayList<String> marketIds = new ArrayList<String>();
							  String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+bean.getEventid(), String.class);
							  JSONObject obj = new JSONObject(res);
							  if(obj.getString("status").equalsIgnoreCase("1")) {


								  JSONArray fancyMarket2 = null;
								  JSONArray fancyMarket3 = null;
								  JSONArray oddEvenMarket = null;
								  JSONArray ballSession = null;



								  fancyMarket2 =	new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy2Market"));
								  fancyMarket3 =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy3Market"));
								  oddEvenMarket =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("oddEvenMarket"));
								  ballSession =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("ballSession"));
                               
								  if(fancyMarket2.length()>0) {
									  for(int i=0; i<fancyMarket2.length();i++) {
										  marketIds.add(new JSONObject(fancyMarket2.get(i).toString()).getString("marketId") );
									  }
									 
								  }
								 
								  if(fancyMarket3.length()>0) {
									  for(int i=0; i<fancyMarket3.length();i++) {
										  marketIds.add(new JSONObject(fancyMarket3.get(i).toString()).getString("marketId") );
									  }
									 
								  }
								  
								  if(oddEvenMarket.length()>0) {
									  for(int i=0; i<oddEvenMarket.length();i++) {
										  marketIds.add(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId") );
									  }
									 
								  }
								  
								  if(ballSession.length()>0) {
									  for(int i=0; i<ballSession.length();i++) {
										  marketIds.add(new JSONObject(ballSession.get(i).toString()).getString("marketId") );
									  }
									 
								  }
								  
								  
								 
							  }
							 
							
							
							ArrayList<MatchFancy> mfList = fancyRepo.findByEventidAndIsActive(bean.getEventid(), true);  
							
							  for(MatchFancy  mf :mfList ) {
									
								  if(!marketIds.contains(mf.getFancyid())) {
									  mf.setIsshow(false);
									  fancyRepo.save(mf);
								  }else {
									  mf.setIsshow(true);
									  fancyRepo.save(mf);
								  
								  }  
							  }
						}
						
					
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
			     String cronExp = "0/20 * * * * ?";// Can be pulled from a db .
			//	String cronExp = "0 0/5 *  * * ?";// Can be pulled from a db .Every 5 min
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	
	}


	private void updateFancyResultRsApi(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		// TODO Auto-generated method stub

		job2 = threadPoolTaskScheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<Event> eventList =	eventRepo.findByStatusAndSportid(true, 4);
					RestTemplate restTemplate = new RestTemplate();
					
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
						for(Event bean:eventList) {
							
							 
							ArrayList<MatchFancy> mfList = fancyRepo.findByEventidAndIsActive(bean.getEventid(), true);  
							
							  StringBuilder marketIds = new StringBuilder();

							  for(MatchFancy  mf :mfList ) {
								
								 
								  
								  marketIds.append(mf.getFancyid());
								  marketIds.append(",");
								  EXRsFancyResult  resultdata   =  	 rsFancyResultRepo.findByFancyIdAndMatchidAndIsresultset(mf.getFancyid(), mf.getEventid(),false);
								   if(resultdata !=null){
									  
									   if(mf.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket) || mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
										   ExFancyResult fresult = new  ExFancyResult();
										   
										   
										   if(mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
											  
											   if(resultdata.getResult().equalsIgnoreCase(EXConstants.Back)) {
												  
												   fresult.setResult(1);
												   mf.setIsActive(false);
												   mf.setStatus("CLOSE");
												  
											   }else if(resultdata.getResult().equalsIgnoreCase(EXConstants.Lay)) {
												   fresult.setResult(0);
												   mf.setIsActive(false);
												   mf.setStatus("CLOSE");
											   }else {
												   mf.setStatus(EXConstants.Suspended);
												   
											   }
											   
										   } if(mf.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
											  
											   if(resultdata.getResult().equalsIgnoreCase(EXConstants.Back)) {
												  
												   fresult.setResult(1);
												   mf.setIsActive(false);
												   mf.setStatus("CLOSE");
												  
											   }else if(resultdata.getResult().equalsIgnoreCase(EXConstants.Lay)) {
												   fresult.setResult(0);
												   mf.setIsActive(false);
												   mf.setStatus("CLOSE");
											   }else {
												   mf.setStatus(EXConstants.Suspended);
											   }
											   
										   }
										   
										   if(!mf.getStatus().equalsIgnoreCase(EXConstants.Suspended)) {
											 
											   
											   HttpClient httpClient = HttpClientBuilder.create().build();
											   restTemplate.setRequestFactory(new 
													   HttpComponentsClientHttpRequestFactory(httpClient));    
											   HttpHeaders reqHeaders = new HttpHeaders();
											   HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", reqHeaders);
											   ResponseEntity<String> responseEntity=restTemplate.exchange(EXConstants.BfToken+"event-odds/declare-fancy/"+fresult.getFancyid(), HttpMethod.PATCH, 
													   requestEntity, String.class);


											   fresult.setFancyid(mf.getFancyid());
											   fresult.setFancyname(mf.getName());
											   fresult.setGroupid(mf.getEventid().toString());
											   fresult.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
											   fresult.setResultStatusCron(false);
											   fresult.setResultdeclareby("Api");
											   fresult.setSportname("Cricket");
											   fresult.setSeriesid(0);
											   fresult.setMatchname(mf.getMatchname());
											   fresult.setMatchid(mf.getEventid());
											   fancyresultRepository.save(fresult);
											   resultdata.setIsresultset(true);
											   rsFancyResultRepo.save(resultdata);
											   fancyRepo.save(mf);
											   
										   }
										   
											   
									   }
									 
								   }
								  
							  }
							  
							  if(mfList.size()>0) {
								  String marketid = marketIds.toString();
								  marketid = marketid.substring(0, marketid.length() - ",".length());

								  if(marketid.length()>4) {
									  String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/rsOddsApi2/"+marketid, String.class);
										
									
										 JSONObject obj = new JSONObject(res);
										 if(obj.getInt("status") == 1) {
											 JSONObject dataobj = new JSONObject(obj.getString("data"));
											 JSONArray items = new JSONArray(dataobj.getString("items"));
											 
											 for(int i=0; i<items.length();i++) {
												 
											      if(!items.get(i).toString().equalsIgnoreCase("null")) {
														 
													if(items.getJSONObject(i).getInt("game_over") == 1 ) {
															
														 EXRsFancyResult  resultdata=  	 rsFancyResultRepo.findByFancyIdAndMatchid(items.getJSONObject(i).getString("market_id"), Integer.parseInt(items.getJSONObject(i).getString("event_id")));
														 
													       if(resultdata ==null) {

													    	   resultdata = new EXRsFancyResult();
															   resultdata.setFancyId(items.getJSONObject(i).getString("market_id"));
															   resultdata.setResult(items.getJSONObject(i).getString("win_result"));
															   resultdata.setMatchid(Integer.parseInt(items.getJSONObject(i).getString("event_id")));
															   resultdata.setIsresultset(false);
															   resultdata.setCreatedon(new Date());
															   rsFancyResultRepo.save(resultdata);
															   
													       }     	 
													 	 
													   
													 }
												} 
													
												
											 }
											 
										 } 
								  }
							  }
						}
						
					
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
			    // String cronExp = "0/30 * * * * ?";// Can be pulled from a db .
				String cronExp = "0 0/5 *  * * ?";// Can be pulled from a db .Every 5 min
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	
	}


/*	private void jobSetFancyResult(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
					ArrayList<ExFancyResult> fancyList =	fancyresultRepository.findByresultStatusCron(false);
					if(fancyList.size()>0) {
						for(ExFancyResult bean:fancyList) {
							       
							           userlib = userLibRepo.findByMatchidAndMarketidAndIsactive(bean.getMatchid(),bean.getFancyid(),true);
									   matchresultservice.setFancyResultsCron(userlib, bean);
							    	   bean.setResultStatusCron(true);
							    	   fancyresultRepository.save(bean);
							    	   MatchFancy mfbean = new MatchFancy();
							    	   mfbean = fancyRepo.findByFancyid(bean.getFancyid());
							    	   mfbean.setIsresultFinished(true);
							    	   fancyRepo.save(mfbean);
							      
								
						}
						
					}
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
				String cronExp = "0/30 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	*/
	
	
	
	
	
	

	private void jobSetMatchResult(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
					ArrayList<MarketResult> marketlist =	marketResultRepository.findByresultStatusCron(false);
					if(marketlist.size()>0) {
						for(MarketResult bean:marketlist) {
							   
							if(bean.getResult()!=-1) {
								   bean.setResultStatusCron(true);
						    	   marketResultRepository.save(bean);
						    	   
								
								 //  userlib = userLibRepo.findByMatchidAndMarketidAndIsactive(bean.getMatchid(),bean.getMarketid(),true);
						    	   
								   userlib = userLibRepo.findByMarketidAndIsactiveAndIsresultset(bean.getMarketid(), true, false);
						    	   matchresultservice.setMarketResultCron(userlib, bean);
						    	   Market mbean = new Market();
						    	   mbean =MarketRepo.findBymarketid(bean.getMarketid());
						    	   mbean.setIsresultFinished(true);
						    	   MarketRepo.save(mbean);
							}else {
								   bean.setResultStatusCron(true);
						    	   marketResultRepository.save(bean);
						    	
						    	   matchresultservice.abandendTieMarketCron(bean.getMarketid(), bean.getMatchid());
						    	   Market mbean = new Market();
						    	   mbean =MarketRepo.findBymarketid(bean.getMarketid());
						    	   mbean.setIsresultFinished(true);
						    	   MarketRepo.save(mbean);
						    }
							   
					    	  
						}
					}
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db every 2 min .
				  String cronExp = "0/30 * * * * ?";// Can be pulled from a db every 30 sec .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	private void jobSetVTP20Result(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
							   
					InetAddress ipAddr1 = InetAddress.getLocalHost(); 
		  			 
					if(ipAddr1.getHostAddress().equalsIgnoreCase("172.31.22.143")) {
						   userlib = userLibRepo.findByMatchidAndIsactive(5041,true);
						
						 
						   
						   matchresultservice.setVTP20ResultCron(userlib);
				    	 
				}
					
							
							  
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db every 2 min .
				  String cronExp = "0/20 * * * * ?";// Can be pulled from a db every 30 sec .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobSetTP20Result(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
							   
					InetAddress ipAddr1 = InetAddress.getLocalHost(); 
		  			 
					//if(ipAddr1.getHostAddress().equalsIgnoreCase("172.31.22.143")) {
						   userlib = userLibRepo.findByMatchidAndIsactive(5046,true);
				    	   matchresultservice.setTP20ResultCron(userlib);
				//	}
					
							
							  
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db every 2 min .
				  String cronExp = "0/20 * * * * ?";// Can be pulled from a db every 30 sec .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	private void jobSetDTP20Result(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
							   
					InetAddress ipAddr1 = InetAddress.getLocalHost(); 
		  			 
					if(ipAddr1.getHostAddress().equalsIgnoreCase("172.31.22.143")) {
						   userlib = userLibRepo.findByMatchidAndIsactive(5046,true);
				    	   matchresultservice.setTP20ResultCron(userlib);
					}
					
							
							  
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db every 2 min .
				  String cronExp = "0/20 * * * * ?";// Can be pulled from a db every 30 sec .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	
	
	
	
	private void jobMatchedMBet(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					
					
		  			 
						ArrayList<ExMatchOddsBet> betlist =	placebetrepository.findByaddetoFirestore(false);
								if(betlist.size()>0) {
									for(ExMatchOddsBet bean:betlist) {
										
										DocumentReference docRef = fb.collection("t_betlist").document(bean.getId().toString());
										Map<String, Object> data = new HashMap<>();
										LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
										ObjectMapper m = new ObjectMapper();
										Map<String,Object> map = m.convertValue(bean, Map.class);
										ApiFuture<WriteResult> result = docRef.set(map);
										bean.setAddetoFirestore(true);
										placebetrepository.save(bean);
									}
									  
								}
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobLiablity(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					
						ArrayList<UserLiability> liblist =	userLibRepo.findByaddetoFirestore(false);
								if(liblist.size()>0) {
									for(UserLiability bean:liblist) {
										
										DocumentReference docRef = fb.collection("t_libility").document(bean.getId().toString());
										LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
										ObjectMapper m = new ObjectMapper();
										Map<String,Object> map = m.convertValue(bean, Map.class);
										ApiFuture<WriteResult> result = docRef.set(map);
										bean.setAddetoFirestore(true);
										userLibRepo.save(bean);
										bean.setAddetoFirestore(true);
									}
									
								}
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobMatchedMBetElection(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<Event> eventlist = eventRepo.findByStatusAndSportid(true, 2378961);
					for(Event ebean :eventlist ) {
						ArrayList<ExMatchOddsBet> betlist =	placebetrepository.findByaddetoFirestoreAndMatchid(false,ebean.getEventid());
								if(betlist.size()>0) {
									for(ExMatchOddsBet bean:betlist) {
										
										DocumentReference docRef = fb.collection("t_betlist").document(bean.getId().toString());
										Map<String, Object> data = new HashMap<>();
										LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
										ObjectMapper m = new ObjectMapper();
										Map<String,Object> map = m.convertValue(bean, Map.class);
										
										ApiFuture<WriteResult> result = docRef.set(map);
										bean.setAddetoFirestore(true);
										placebetrepository.save(bean);
									}
									
								}
					}
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobLiablityElection(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					ArrayList<Event> eventlist = eventRepo.findByStatusAndSportid(true, 2378961);
					for(Event ebean :eventlist ) {
						ArrayList<UserLiability> liblist =	userLibRepo.findByaddetoFirestoreAndMatchid(false,ebean.getEventid());
								if(liblist.size()>0) {
									for(UserLiability bean:liblist) {
										
										DocumentReference docRef = fb.collection("t_libility").document(bean.getId().toString());
										LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
										ObjectMapper m = new ObjectMapper();
										Map<String,Object> map = m.convertValue(bean, Map.class);
										ApiFuture<WriteResult> result = docRef.set(map);
										bean.setAddetoFirestore(true);
										userLibRepo.save(bean);
										bean.setAddetoFirestore(true);
									}
									
								}
					}
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	private void jobFinishmatchUpdatetofirestore(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					
						ArrayList<UserLiability> liblist =	userLibRepo.findByaddetoFirestore(false);
								if(liblist.size()>0) {
									for(UserLiability bean:liblist) {
										
										ArrayList<ExMatchOddsBet> betlist =	placebetrepository.findByaddetoFirestore(false);
										
										
										if(betlist.size()>0) {
											for(ExMatchOddsBet betbean:betlist) {
												
												DocumentReference docRef = fb.collection("t_betlist").document(betbean.getId().toString());
												LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
												ObjectMapper betm = new ObjectMapper();
												Map<String,Object> map = betm.convertValue(betbean, Map.class);
												
												ApiFuture<WriteResult> betbeanresult = docRef.set(map);
												betbean.setAddetoFirestore(true);
												placebetrepository.save(betbean);
											}
											
										}
										
										
										DocumentReference docRef = fb.collection("t_libility").document(bean.getId().toString());
										LinkedHashMap<String,Object> map1 = new LinkedHashMap<String,Object>();
										ObjectMapper m = new ObjectMapper();
										Map<String,Object> map = m.convertValue(bean, Map.class);
										ApiFuture<WriteResult> result = docRef.set(map);
										bean.setAddetoFirestore(true);
										userLibRepo.save(bean);
										bean.setAddetoFirestore(true);
									}
									
								}
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	private void deleteOldBet(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					    
					ArrayList<ExMatchOddsBet> betlist  = (ArrayList<ExMatchOddsBet>) placebetrepository.findByIsactive(false);
					    	
						for(ExMatchOddsBet bean:betlist) {
					    		
					    		Date Date1  = bean.getCreatedon();
								Date Date2= new Date();
								long diff = Date2.getTime() - Date1.getTime();
								//System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					    		if(diff>7) {
					    			placebetrepository.delete(bean);
					    		}
					    		
					    		
					    	}
					    	
					    	
					    
						
					
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	private void deleteOldBetSession(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					    
					    ArrayList<ExFancyResult> mktresult  = (ArrayList<ExFancyResult>) fancyresultRepository.findAll();{
					    	
					    	for(ExFancyResult mkt : mktresult ) {
					    		
					    		Date Date1  = dtUtil.convertToDate2(mkt.getCreatedon());
								Date Date2= new Date();
								long diff = Date2.getTime() - Date1.getTime();
								//System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					    		if(diff>8) {
					    			ArrayList<ExMatchOddsBet> betlist =	 placebetrepository.findByMarketid(mkt.getFancyid());
									if(betlist.size()>0) {
										for(ExMatchOddsBet bean:betlist) {
											placebetrepository.delete(bean);
										}
										
									}
					    		}
					    		
					    		
					    	}
					    	
					    	
					    }
						
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	private void setFootballMatchResult(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					Calendar calendar = new GregorianCalendar();
					TimeZone timeZone = calendar.getTimeZone();
					RestTemplate restTemplate = new RestTemplate();
					String apiurl = restTemplate.getForObject("https://aaaa-14afe-cc669-betfair.firebaseio.com/developerApiKalyan.json", String.class);
					ArrayList <Market> mktlist = MarketRepo.findBySportidAndStatus(1, true);
					
					 for(Market mbean : mktlist) {
						  if(!mbean.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
			                
							  MarketResult result=null;
							  
							
							  String responsedata = restTemplate.getForObject("http://18.168.176.234:8081/developer/betFairApiOdds/"+mbean.getMarketid(), String.class);
				    			
								
							  JSONObject jobj = new JSONObject(responsedata);
							  
							  JSONArray responsea = new JSONArray(jobj.getString("eventTypes"));
							  
							  for(int i = 0;i<responsea.length();i++){
								
								  JSONArray eventNodes = new JSONArray(responsea.getJSONObject(i).getString("eventNodes"));
								  for(int j = 0;j<eventNodes.length();j++){
									  
									  JSONArray marketNodes = new JSONArray(eventNodes.getJSONObject(j).getString("marketNodes"));
									  for(int k = 0;k<marketNodes.length();k++){
										  String state =marketNodes.getJSONObject(k).getString("state");
										  JSONObject ststusobj = new JSONObject(state);
										  JSONArray runners = marketNodes.getJSONObject(k).getJSONArray("runners");
										  Event event =null;
										  SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  							    		
										  if(ststusobj.getString("status").equalsIgnoreCase("CLOSED")) {
											  for(int l=0;l<runners.length();l++){
												  
												  String runnersstate = runners.getJSONObject(l).getString("state");
												  JSONObject runnersstateobj = new JSONObject(runnersstate);
												 if(runnersstateobj.getString("status").equalsIgnoreCase("WINNER")) {
													  
													 System.out.println(mbean.getMarketid());
					    								String selectionName=selRepo.findBySelectionidAndMarketid(runners.getJSONObject(l).getInt("selectionId"), mbean.getMarketid()).getRunnerName();

					    								result = new MarketResult();
					    								result.setSelectionid(runners.getJSONObject(l).getInt("selectionId"));
					    								result.setSelectionname(selectionName);
					    								MarketResult resultbean =null;
					    							    	
					    							    	resultbean =marketResultRepository.findByMarketid(result.getMarketid());
					    							    	if(resultbean == null) {

					    							    		result.setDate(new Date());
					    							    		result.setStatus(true);
					    							    		result.setIsResult(true);
					    							    		result.setType("Result");
					    							    		result.setResultStatusCron(false);
					    							    		result.setMarketid(mbean.getMarketid());
					    							    		result.setSportid(mbean.getSportid());
					    							    		result.setSportname("Football");
					    							    		result.setMarketname(EXConstants.MatchOdds);
					    							    		result.setMarkettype(mbean.getMarketname());
					    							    		result.setMatchid(mbean.getEventid());
					    							    		result.setMatchname(mbean.getMatchname());
					    							    		result.setResult(runners.getJSONObject(j).getInt("selectionId"));
					    							    		result.setSelectionname(selectionName);
					    							    		
					    							    		marketResultRepository.save(result);
						    							    	
					    							    	}
					    							
												  }else if(runnersstateobj.getString("status").equalsIgnoreCase("REMOVED")) {
													  
													  Thread.sleep(2000);
														
															
															EXMatchAbndAndTie mtie = matchabndtieRepo.findByMarketid(mbean.getMarketid());
															if(mtie == null) {
																
																mtie = new EXMatchAbndAndTie();
																
																mtie.setMarketid(mbean.getMarketid());
																mtie.setMatchid(mbean.getEventid());
																mtie.setResult("abandoned");
																mtie.setMatchname(mbean.getMatchname());
																mtie.setSportid(mbean.getSportid());
															//	SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
																mtie.setDate(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
																matchabndtieRepo.save(mtie);
																
																MarketResult marketresult = new MarketResult();

																marketresult.setIsResult(true);
																marketresult.setMarketname(mbean.getMarketname());
																marketresult.setMarkettype(mbean.getMarketname());
																marketresult.setType("abandoned");
																marketresult.setMarketid(mbean.getMarketid());
																marketresult.setMatchid(mbean.getEventid());
																marketresult.setMatchname(mbean.getMatchname());
																marketresult.setSportid(mbean.getSportid());
																marketresult.setResultStatusCron(false);
																if(mbean.getSportid() == 4){
																	marketresult.setSportname("Cricket");
																	
																}else if(mbean.getSportid() == 1){
																	marketresult.setSportname("Soccer");
																	
																}else if(mbean.getSportid() == 2){
																	marketresult.setSportname("Tennis");
																	
																}else if(mbean.getSportid() == 2378961){
																	marketresult.setSportname("Election");
																	
																}
																
																marketresult.setDate(new Date());
																
																marketresult.setSelectionid(-1);
																marketresult.setSelectionname("abandoned");
																marketresult.setResult(-1);
																marketresult.setStatus(true);
																
																
																   
																   ArrayList<Market> mkt = MarketRepo.findByEventidAndStatus(mbean.getEventid(),true);
					        							    		
					    							    			if(mkt.size() == 1) {
					    							    				Event eventbean = eventRepo.findByEventidAndStatus(mbean.getEventid(), true);
					    							    				eventbean.setStatus(false);
					    							    				eventbean.setIsactive(false);
					        							    			eventRepo.save(eventbean);
					    							    			}
					    							    			
					    							    		    mbean.setStatus(false);
						    							    		mbean.setIsactive(false);
						    							    		mbean.setIsResult(true);
						    							    		mbean.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
						    							    		
						    							    		marketResultRepository.save(marketresult);
																	MarketRepo.save(mbean);
															}
														
														}
											  }
										 
											   
	    							    		ArrayList<Market> mkt = MarketRepo.findByEventidAndStatus(mbean.getEventid(),true);
	        							    		
	    							    			if(mkt.size() ==1) {
	    							    				event = eventRepo.findByeventid(result.getMatchid());
	        							    			event.setStatus(false);
	        							    			event.setIsactive(false);
	        							    			eventRepo.save(event);
	    							    			}
	    							    			
	    							    			mbean.setStatus(false);
	 	    							    		mbean.setIsactive(false);
	 	    							    		mbean.setIsResult(true);
	 	    							    		mbean.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
	 	    							    		
	    							    			
	    							    		MarketRepo.save(mbean);	
	    							    		
										  }
											 
									  }
										 
								  }
							  }
			    				
			    		}
					  }
					
					    	
				
						
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
			//	String cronExp = "0 0/5 *  * * ?";// Can be pulled from a db .
				String cronExp = "0/30 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
}
	
	private void setTennisMatchResult(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					Calendar calendar = new GregorianCalendar();
					TimeZone timeZone = calendar.getTimeZone();
					RestTemplate restTemplate = new RestTemplate();
					String apiurl = restTemplate.getForObject("https://aaaa-14afe-cc669-betfair.firebaseio.com/developerApiKalyan.json", String.class);
					ArrayList <Market> mktlist = MarketRepo.findBySportidAndStatus(2, true);
					
					 for(Market mbean : mktlist) {
						  if(!mbean.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
			                
							  MarketResult result=null;
							  
			                
							  String responsedata = restTemplate.getForObject("http://18.168.176.234:8081/developer/betFairApiOdds/"+mbean.getMarketid(), String.class);
				    			
								
							  JSONObject jobj = new JSONObject(responsedata);
							  
							  JSONArray responsea = new JSONArray(jobj.getString("eventTypes"));
							  
							  for(int i = 0;i<responsea.length();i++){
								
								  JSONArray eventNodes = new JSONArray(responsea.getJSONObject(i).getString("eventNodes"));
								  for(int j = 0;j<eventNodes.length();j++){
									  
									  JSONArray marketNodes = new JSONArray(eventNodes.getJSONObject(j).getString("marketNodes"));
									  for(int k = 0;k<marketNodes.length();k++){
										  String state =marketNodes.getJSONObject(k).getString("state");
										  JSONObject ststusobj = new JSONObject(state);
										  JSONArray runners = marketNodes.getJSONObject(k).getJSONArray("runners");
										  
										  if(ststusobj.getString("status").equalsIgnoreCase("CLOSED")) {
											  for(int l=0;l<runners.length();l++){
												  
												  String runnersstate = runners.getJSONObject(l).getString("state");
												  JSONObject runnersstateobj = new JSONObject(runnersstate);
												 if(runnersstateobj.getString("status").equalsIgnoreCase("WINNER")) {
													  

					    								String selectionName=selRepo.findBySelectionidAndMarketid(runners.getJSONObject(l).getInt("selectionId"), mbean.getMarketid()).getRunnerName();
					    								result = new MarketResult();
					    								result.setSelectionid(runners.getJSONObject(l).getInt("selectionId"));
					    								result.setSelectionname(selectionName);
					    								MarketResult resultbean =null;
					    							    	Event event =null;
					    							    	
					    							    	resultbean =marketResultRepository.findByMarketid(result.getMarketid());
					    							    	if(resultbean == null) {

					    							    		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    							    		result.setDate(new Date());
					    							    		result.setStatus(true);
					    							    		result.setIsResult(true);
					    							    		result.setType("Result");
					    							    		result.setResultStatusCron(false);
					    							    		result.setMarketid(mbean.getMarketid());
					    							    		result.setSportid(mbean.getSportid());
					    							    		result.setSportname("Tennis");
					    							    		result.setMarketname(EXConstants.MatchOdds);
					    							    		result.setMarkettype(mbean.getMarketname());
					    							    		result.setMatchid(mbean.getEventid());
					    							    		result.setMatchname(mbean.getMatchname());
					    							    		result.setResult(runners.getJSONObject(j).getInt("selectionId"));
					    							    		result.setSelectionname(selectionName);
					    							    		
					    							    		
					    							    		//market =   new Market();
					    							    		
					    							    		ArrayList<Market> mkt = MarketRepo.findByEventidAndStatus(mbean.getEventid(),true);
				        							    		
				    							    			if(mkt.size() ==1) {
				    							    				event = eventRepo.findByeventid(result.getMatchid());
				        							    			event.setStatus(false);
				        							    			event.setIsactive(false);
				        							    			eventRepo.save(event);
				    							    			}
				    							    			
				    							    			
				    							    			mbean.setStatus(false);
					    							    		mbean.setIsactive(false);
					    							    		mbean.setIsResult(true);
					    							    		mbean.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					    							    			
					    							    		marketResultRepository.save(result);
				    							    			
				    							    			
				    							    		     MarketRepo.save(mbean);	

					    							    	}
					    							
												  }else if(runnersstateobj.getString("status").equalsIgnoreCase("REMOVED")) {
													  
													  Thread.sleep(2000);
														
															
															EXMatchAbndAndTie mtie = matchabndtieRepo.findByMarketid(mbean.getMarketid());
															if(mtie == null) {
																
																mtie = new EXMatchAbndAndTie();
																
																mtie.setMarketid(mbean.getMarketid());
																mtie.setMatchid(mbean.getEventid());
																mtie.setResult("abandoned");
																mtie.setMatchname(mbean.getMatchname());
																mtie.setSportid(mbean.getSportid());
																SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
																mtie.setDate(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
																matchabndtieRepo.save(mtie);
																
																MarketResult marketresult = new MarketResult();

																marketresult.setIsResult(true);
																marketresult.setMarketname(mbean.getMarketname());
																marketresult.setMarkettype(mbean.getMarketname());
																marketresult.setType("abandoned");
																marketresult.setMarketid(mbean.getMarketid());
																marketresult.setMatchid(mbean.getEventid());
																marketresult.setMatchname(mbean.getMatchname());
																marketresult.setSportid(mbean.getSportid());
																marketresult.setResultStatusCron(false);
																if(mbean.getSportid() == 4){
																	marketresult.setSportname("Cricket");
																	
																}else if(mbean.getSportid() == 1){
																	marketresult.setSportname("Soccer");
																	
																}else if(mbean.getSportid() == 2){
																	marketresult.setSportname("Tennis");
																	
																}else if(mbean.getSportid() == 2378961){
																	marketresult.setSportname("Election");
																	
																}
																
																marketresult.setDate(new Date());
																
																marketresult.setSelectionid(-1);
																marketresult.setSelectionname("abandoned");
																marketresult.setResult(-1);
																marketresult.setStatus(true);
																
																
																   
																   ArrayList<Market> mkt = MarketRepo.findByEventidAndStatus(mbean.getEventid(),true);
					        							    		
					    							    			if(mkt.size() == 1) {
					    							    				Event eventbean = eventRepo.findByEventidAndStatus(mbean.getEventid(), true);
					    							    				eventbean.setStatus(false);
					    							    				eventbean.setIsactive(false);
					        							    			eventRepo.save(eventbean);
					    							    			}
					    							    			
					    							    		    mbean.setStatus(false);
						    							    		mbean.setIsactive(false);
						    							    		mbean.setIsResult(true);
						    							    		mbean.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
						    							    		
						    							    		marketResultRepository.save(marketresult);
																	MarketRepo.save(mbean);
															}
														
														}
												  
											  }
										  }
											 
									  }
										 
								  }
							  }
			    			}
					  }
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/10 * * * * ?";// Can be pulled from a db .
				//String cronExp = "0 0/5 *  * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
}
	
	private void deleteOldLaibility(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					
						
					ArrayList<UserLiability> userlib  = (ArrayList<UserLiability>) userLibRepo.findByIsactive(false);
					for(UserLiability bean:userlib) {
						Date Date1  = bean.getCreatedon();
						Date Date2= new Date();
						long diff = Date2.getTime() - Date1.getTime();
						//System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			    		if(diff>8) {
			    			userLibRepo.delete(bean);
			    		}
					}
					
				
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	

	private void deleteFancy(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					
					
		  			 
						ArrayList<MatchFancy> betlist =	fancyRepo.findByEventid(30440517);
								if(betlist.size()>0) {
									for(MatchFancy bean:betlist) {
										
										if(bean.getStatus().equalsIgnoreCase("CLOSE")) {
											 ApiFuture<WriteResult> docRef = fb.collection("30440517").document(bean.getFancyid()).delete();
										}
										
										
									}
									  
								}
					
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobAddAutoFancy(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					RestTemplate restTemplate = new RestTemplate();
					ArrayList<Event> eventList =	eventRepo.findByAddautoFancyAndStatusAndSportid(true, true,4);
					
					if(eventList.size()>0) {
						for(Event bean:eventList) {
							
							
							
							String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+bean.getEventid(), String.class);
							
						
							JSONObject obj = new JSONObject(res);
							if(obj.getString("status").equalsIgnoreCase("1")) {
								

								JSONArray fancyMarket2 = null;
								JSONArray fancyMarket3 = null;
								JSONArray oddEvenMarket = null;
								JSONArray ballSession = null;
								
								
								
									fancyMarket2 =	new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy2Market"));
									fancyMarket3 =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("fancy3Market"));
							        oddEvenMarket =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("oddEvenMarket"));
							     //   ballSession =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("ballSession"));
								/*	khadoSession =  new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("khadoSession"));
								*/	
								
							      
							      for(int i=0; i<fancyMarket2.length();i++) {
										
										MatchFancy mf = fancyRepo.findByFancyidAndEventid(new JSONObject(fancyMarket2.get(i).toString()).getString("marketId"), bean.getEventid());
										   
										if(mf ==null) {
										 	MatchFancy list = new MatchFancy();

											list.setRunnerid(new JSONObject(fancyMarket2.get(i).toString()).getString("marketId"));
											list.setName(new JSONObject(fancyMarket2.get(i).toString()).getString("title"));
											list.setSportId(4);
											list.setEventid(bean.getEventid());
											list.setIsplay(true);
											list.setMaxLiabilityPerBet(0);
											list.setBetDelay(0);
											list.setIsBettable(1);
											list.setFancyid(new JSONObject(fancyMarket2.get(i).toString()).getString("marketId"));
											list.setStatus("OPEN");
											list.setMatchname(bean.getEventname());
											list.setMaxLiabilityPerMarket(0);
											list.setIssuspendedByAdmin(false);
											list.setSkyfancyid("0");
											list.setIsActive(true);
											list.setiSfsmarkedInactive(false);
											list.setIsresultFinished(false);
											list.setMtype("player");
											list.setMaxLiabilityPerMarket(bean.getPlayermaxbetfancy()*10);
											
											list.setIsshow(true);
											
											list.setOddstype("F2");
											fancyRepo.save(list);
											
										}
										
									}
									
								
							
								
									
								for(int i=0; i<fancyMarket3.length();i++) {
									
									MatchFancy mf = fancyRepo.findByFancyidAndEventid(new JSONObject(fancyMarket3.get(i).toString()).getString("marketId"), bean.getEventid());
									   
									if(mf ==null) {
									 	MatchFancy list = new MatchFancy();

										list.setRunnerid(new JSONObject(fancyMarket3.get(i).toString()).getString("marketId"));
										list.setName(new JSONObject(fancyMarket3.get(i).toString()).getString("title"));
										list.setSportId(4);
										list.setEventid(bean.getEventid());
										list.setIsplay(true);
										list.setMaxLiabilityPerBet(0);
										list.setBetDelay(0);
										list.setIsBettable(1);
										list.setFancyid(new JSONObject(fancyMarket3.get(i).toString()).getString("marketId"));
										list.setStatus("OPEN");
										list.setMatchname(bean.getEventname());
										list.setMaxLiabilityPerMarket(0);
										list.setIssuspendedByAdmin(false);
										list.setSkyfancyid("0");
										list.setIsActive(true);
										list.setiSfsmarkedInactive(false);
										list.setIsresultFinished(false);
										list.setMtype("player");
										list.setMaxLiabilityPerMarket(bean.getPlayermaxbetfancy()*10);
										
										list.setIsshow(true);
										
										list.setOddstype("F3");
										fancyRepo.save(list);
										
									}
									
								}
								
								for(int i=0; i<oddEvenMarket.length();i++) {
									
									MatchFancy mf = fancyRepo.findByFancyidAndEventid(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"), bean.getEventid());
									   
									if(mf ==null) {
									  MatchFancy list = new MatchFancy();

										list.setRunnerid(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"));
										list.setName(new JSONObject(oddEvenMarket.get(i).toString()).getString("title"));
										list.setSportId(4);
										list.setEventid(bean.getEventid());
										list.setIsplay(true);
										list.setMaxLiabilityPerBet(0);
										list.setBetDelay(0);
										list.setIsBettable(1);
										list.setFancyid(new JSONObject(oddEvenMarket.get(i).toString()).getString("marketId"));
										list.setStatus("OPEN");
										list.setMatchname(bean.getEventname());
										list.setMaxLiabilityPerMarket(0);
										list.setIssuspendedByAdmin(false);
										list.setSkyfancyid("0");
										list.setIsActive(true);
										list.setiSfsmarkedInactive(false);
										list.setIsresultFinished(false);
										list.setMtype("player");
										list.setMaxLiabilityPerMarket(bean.getPlayermaxbetfancy()*10);
										
										list.setIsshow(true);
										
										
										list.setOddstype("OE");
										fancyRepo.save(list);
										
									}
									
									
									
								}
								
						/*		for(int i=0; i<ballSession.length();i++) {
									
									MatchFancy mf = fancyRepo.findByFancyidAndEventid(new JSONObject(ballSession.get(i).toString()).getString("marketId"), bean.getEventid());
									   
									if(mf ==null) {
									  MatchFancy list = new MatchFancy();

										list.setRunnerid(new JSONObject(ballSession.get(i).toString()).getString("marketId"));
										list.setName(new JSONObject(ballSession.get(i).toString()).getString("title"));
										list.setSportId(4);
										list.setEventid(bean.getEventid());
										list.setIsplay(true);
										list.setMaxLiabilityPerBet(0);
										list.setBetDelay(0);
										list.setIsBettable(1);
										list.setFancyid(new JSONObject(ballSession.get(i).toString()).getString("marketId"));
										list.setStatus("OPEN");
										list.setMatchname(bean.getEventname());
										list.setMaxLiabilityPerMarket(0);
										list.setIssuspendedByAdmin(false);
										list.setSkyfancyid("0");
										list.setIsActive(true);
										list.setiSfsmarkedInactive(false);
										list.setIsresultFinished(false);
										list.setMtype("player");
										list.setMaxLiabilityPerMarket(bean.getPlayermaxbetfancy()*10);
										
										list.setIsshow(true);
										
										
										list.setOddstype(EXConstants.Ball);
										fancyRepo.save(list);
										
									}
									
								}*/
								
							}
				    		
							
						}
						
						
					}
				}		catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}

		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db every 2 min .
				  String cronExp = "0/10 * * * * ?";// Can be pulled from a db every 30 sec .
				 // String cronExp = "0 0/2 *  * * ?";// Can be pulled from a db . EVERY 2 MINUT
				return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
	
	private void jobAddFootballMatch(TaskScheduler scheduler) {
		job2 = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				try
				{
					 ArrayList<Integer> sportlist = new ArrayList<Integer>();
					 
					 sportlist.add(1);
					 //sportlist.add(4);
			    	 sportlist.add(2);
					
					for(int a=0;a<sportlist.size();a++) {
						 RestTemplate restTemplate = new RestTemplate();
							
						 HttpHeaders headers = new HttpHeaders();
						 headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				            
						
						
						HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				        ResponseEntity<String> respEntity = restTemplate.exchange(EXConstants.RSApi+"sessionApi/eventList/"+sportlist.get(a), HttpMethod.GET, entity, String.class);
				        
						JSONObject obj  = new JSONObject(respEntity.getBody());
					
						JSONArray ary  = new JSONArray(obj.getString("data"));
						
						
						for(int i=0; i<ary.length();i++) {
							
							JSONObject objdata = new JSONObject(ary.get(i).toString());
							
							JSONArray evntary  = new JSONArray(objdata.getString("event"));
							
							if(objdata.getString("event").equalsIgnoreCase("31104708")) {
								System.out.println("ok");
							}
							
							EXDateUtil dtUtil = new EXDateUtil();
							Calendar calendar = new GregorianCalendar();
							TimeZone timeZone = calendar.getTimeZone();
							
							for(int j=0; j<evntary.length();j++) {
							
							
								Event ev = eventRepo.findByEventidAndStatus(Integer.parseInt(new JSONObject(evntary.get(j).toString()).getString("eventId")), true);

								Integer  time=Integer.parseInt(new JSONObject(evntary.get(j).toString()).getString("time"));
								
							    Date currentDate = new Date(time.longValue()*1000);
							    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								
								if(ev == null) {
									
									
									Event event =new Event();
									
									event.setEventname(new JSONObject(evntary.get(j).toString()).getString("name"));
									event.setEventid(Integer.parseInt(new JSONObject(evntary.get(j).toString()).getString("eventId")));
									event.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
									event.setAddautoFancy(false);
									event.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
									event.setFancyprovider(EXConstants.Diamond);
									event.setFancypause(false);
									event.setIsactive(true);
									event.setLiveTv(false);
									event.setBetLock(false);
									event.setMaxbet(10000);
									event.setMinbet(100);
									event.setMaxbetBookmaker(10000);
									event.setMinbetBookmaker(100);
									event.setMaxbetfancy(10000);
									event.setMinbetfancy(100);
									event.setPlayermaxbetfancy(10000);
									event.setBetdelayfancy(5);
									event.setBetdelay(5);
									event.setBetLock(false);
									event.setSportid(sportlist.get(a));
									event.setStatus(true);
									event.setCountrycode("GBP");
									event.setTimezone("GMT");
									event.setType("Betfair");
									event.setAdminid("1");
									event.setTotalExposure(50000);
									event.setOpenDate(dtutil.convertBetfairToDate(currentDate, "yyyy-MM-dd HH:mm:ss"));

									
									//System.out.println();
									
									eventRepo.save(event);
									
									EXMarketDiff  mktDiff =	marketDiffRepo.findByeventid(event.getEventid());
									if(mktDiff ==null) {
										mktDiff =  new EXMarketDiff();
										mktDiff.setBackdiff(0);
										mktDiff.setLaydiff(0);
										mktDiff.setEventid(event.getEventid());
										marketDiffRepo.save(mktDiff);
									}
									
								}	
								
								    String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+Integer.parseInt(new JSONObject(evntary.get(j).toString()).getString("eventId")), String.class);
								    Event evt = eventRepo.findByEventidAndStatus(Integer.parseInt(new JSONObject(new JSONObject(res).getString("data")).getString("eventId")), true);
									
								    if(evt!=null) {
										
								    	JSONObject dataobj = new JSONObject(new JSONObject(res).getString("data"));
										JSONArray othermarket  = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("otherMarket"));
										
										for(int l =0;l<othermarket.length();l++ ) {
										System.out.println(othermarket.getJSONObject(l).getString("marketId"));
											Market  mark = MarketRepo.findBymarketid(othermarket.getJSONObject(l).getString("marketId"));
											
											if(othermarket.getJSONObject(l).getString("marketId").equalsIgnoreCase("1.196399220")) {
												System.out.println("Hello");
											}
											
											if(mark == null) {
												
												
												mark = new Market();
												mark.setStopMarketOdds(true);
												mark.setMarketid(othermarket.getJSONObject(l).getString("marketId"));
												mark.setMatchname(dataobj.getString("title"));
												mark.setStatus(true);
												mark.setIsactive(true);
												mark.setIspause(true);
												mark.setIsResult(false);
												
												mark.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
												mark.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
												mark.setMarketname(othermarket.getJSONObject(l).getString("marketName"));
												mark.setAppid(1);
												mark.setEventid(evt.getEventid());
												mark.setSeriesid(evt.getSeriesid());
												mark.setSportid(evt.getSportid());
												mark.setAddautoFancy(false);
												mark.setPauseByAdmin(false);
												mark.setOpendate(dtUtil.addHourToDate(currentDate));
												mark.setInPlay(false);
												mark.setPauseByAdmin(true);
												mark.setiSfsmarkedInactive(false);
												mark.setIsresultFinished(false);
												mark.setIsautomatchActive(true);
												mark.setOddsprovider("Betfair");
												mark.setStartdate(dtutil.convertBetfairToDate(currentDate, "yyyy-MM-dd HH:mm:ss"));
												MarketRepo.save(mark);
												
												JSONArray   runnerarry=  new JSONArray(othermarket.getJSONObject(l).getString("runners"));
												 for(int k=0; k< runnerarry.length(); k++ ) {
													 
													 SelectionIds sel= selRepo.findBySelectionidAndMarketid(runnerarry.getJSONObject(k).getInt("secId"), mark.getMarketid());
													 if(sel == null) {
														 sel = new SelectionIds();
														 sel.setMarketid( mark.getMarketid());
														 sel.setRunnerName(runnerarry.getJSONObject(k).getString("runnerName").replaceAll("[-+.^:,]",""));
														 sel.setSelectionid(runnerarry.getJSONObject(k).getInt("secId"));
														 sel.setCreatedon(new Date());
														 selRepo.save(sel);
													 }
													 
												 }
												
												
											}
										}
										
									
										
				/*Add Bookmaker Market*/
										
										
								/*		JSONArray bookmakermarketsArray = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("bookMaker"));
										
										for(int b=0; i<bookmakermarketsArray.length();i++) {
											
											Market  bookmakermarket = MarketRepo.findBymarketid(new JSONObject(bookmakermarketsArray.get(b).toString()).getString("market_id"));
											if(bookmakermarket == null) {
											
												bookmakermarket = new Market();
												bookmakermarket.setStopMarketOdds(true);
												bookmakermarket.setMarketid(new JSONObject(bookmakermarketsArray.get(b).toString()).getString("market_id"));
												bookmakermarket.setMatchname(dataobj.getString("title"));
												bookmakermarket.setStatus(true);
												bookmakermarket.setIsactive(true);
												bookmakermarket.setIspause(true);
												bookmakermarket.setIsResult(false);
												
												bookmakermarket.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
												bookmakermarket.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
												bookmakermarket.setMarketname(EXConstants.Bookmaker);
												bookmakermarket.setAppid(1);
												bookmakermarket.setEventid(evt.getEventid());
												bookmakermarket.setSeriesid(evt.getSeriesid());
												bookmakermarket.setSportid(evt.getSportid());
												bookmakermarket.setAddautoFancy(false);
												bookmakermarket.setPauseByAdmin(false);
												bookmakermarket.setOpendate(dtUtil.addHourToDate(currentDate));
												bookmakermarket.setInPlay(false);
												bookmakermarket.setPauseByAdmin(true);
												bookmakermarket.setiSfsmarkedInactive(false);
												bookmakermarket.setIsresultFinished(false);
												bookmakermarket.setIsautomatchActive(true);
												bookmakermarket.setOddsprovider("Betfair");
												bookmakermarket.setStartdate(dtutil.convertBetfairToDate(currentDate, "yyyy-MM-dd HH:mm:ss"));
												MarketRepo.save(bookmakermarket);
												
												JSONArray   runnerarry=  new JSONArray(new JSONObject(bookmakermarketsArray.get(b).toString()).getString("runners"));
												 for(int k=0; k< runnerarry.length(); k++ ) {
													 
													 SelectionIds sel= selRepo.findBySelectionidAndMarketid(runnerarry.getJSONObject(k).getInt("secId"), new JSONObject(bookmakermarketsArray.get(b).toString()).getString("market_id"));
													 if(sel == null) {
														 sel = new SelectionIds();
														 sel.setMarketid(new JSONObject(bookmakermarketsArray.get(b).toString()).getString("market_id"));
														 sel.setRunnerName(runnerarry.getJSONObject(k).getString("runner"));
														 sel.setSelectionid(runnerarry.getJSONObject(k).getInt("secId"));
														 selRepo.save(sel);
													 }
													 
												 }
											}
											
											
										}*/
										
										
									
										
												
									}
									
								
								
								
							}

						}
					 }
					
					
					
				}catch(Exception e)
				{
					e.printStackTrace();
					log.error(e);

				}
			}
			
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				//String cronExp = "0/1 * * * * ?";// Can be pulled from a db .
				  String cronExp = "0/30 * * * * ?";// Can be pulled from a db every 30 sec .
				// String cronExp = "0 0/30 *  * * ?";// Can be pulled from a db .//Every 30 Minut
					return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
			}
		});
	}
	
//	@Scheduled(fixedDelay = 2000)
	private void AutoCasinoBetLoack() {

		ArrayList<EXUser> userlist = (ArrayList<EXUser>) userRepo.findByIsCasinoBetlock(true);
		for(EXUser usr :userlist) {

			CasinoBetLoack bean1 = casinoBetLockRepo.findByUserid(usr.getId());
			if(bean1==null) {
				
					bean1 = new CasinoBetLoack();
					bean1.setUserid(usr.getId());
					bean1.setTabletype("B");
					bean1.setCreatedon(new Date());
					casinoBetLockRepo.save(bean1);
								
			}
		}
		System.out.println("done");
	}
	

    //@Scheduled(fixedDelay = 1000)
	private void  matchBetfairBetsPreveenApi() throws Exception
	{
		      
    	 Integer selId =2954266;
    	 Boolean isBack =false;
    	 Double price =1.85;
		 RestTemplate restTemplate = new RestTemplate();
		 String odds = restTemplate.getForObject(EXConstants.BfToken+"event-odds/odds/31285105", String.class);
			
		 JSONObject obj = new JSONObject(odds);
		 if(obj.getInt("statusCode") == 200 && obj.getString("message").equalsIgnoreCase("Data found")) {
			 JSONArray array =new JSONArray(new JSONObject(obj.getString("data")).getString("Odds"));
			 
			JSONObject obj1 = new JSONObject(array.get(0));
			 for(int i=0;i<array.length();i++) {
				if(new JSONObject(array.get(i).toString()).getString("status").equalsIgnoreCase("OPEN")) {
					 System.out.println(new JSONObject(array.get(i).toString()).getString("runner"));
					 JSONArray  runnerArray = new JSONArray(new JSONObject(array.get(i).toString()).getString("runner")); 
					 for(int j=0;j<runnerArray.length();j++) {
						 if(new JSONObject(runnerArray.get(j).toString()).getInt("selectionId") == selId) {
							 System.out.println(new JSONObject(runnerArray.get(j).toString()).getDouble("back1price"));
							 if(isBack == true && new JSONObject(runnerArray.get(j).toString()).getDouble("back1price")!=0.0) {
								 BigDecimal value = new BigDecimal(String.valueOf(new JSONObject(runnerArray.get(j).toString()).getDouble("back1price"))).subtract(new BigDecimal(price.toString()));
								 BigDecimal backSize =new BigDecimal(String.valueOf(new JSONObject(runnerArray.get(j).toString()).getDouble("back1price")));
									if(Double.valueOf(backSize.doubleValue()) >= price && value.doubleValue() <= 0.03 ){
										
										System.out.println("odds ok");
										
										//return backsize.doubleValue();
										
									}else{
										//return 0.0;
										System.out.println("odds ok fail");
										
									}
							 }else {
								 BigDecimal value =new BigDecimal(price.toString()).subtract(new BigDecimal(String.valueOf(new JSONObject(runnerArray.get(j).toString()).getDouble("lay1price"))));
								 BigDecimal laySize =new BigDecimal(String.valueOf(new JSONObject(runnerArray.get(j).toString()).getDouble("lay1price")));
								 if(Double.valueOf(laySize.doubleValue()) <= price && value.doubleValue() <= 0.03){
										
									//	return laysize.doubleValue();
									 System.out.println("odds ok LAY");
										
									}else{
									//	return 0.0;
										System.out.println("odds ok fail LAY");
										
									}
							 }
						 }
					 }
				 }else {
					 System.out.println("Odds Chnage");
					 
				 }
			 }
			 
		 }
			
		 
	}

}
