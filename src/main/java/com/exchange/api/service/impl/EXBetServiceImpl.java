package com.exchange.api.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.AppCharge;
import com.exchange.api.bean.CustomSequences;
import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.SelectionIds;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXMySqlProcedureDao;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.EXUserDAO;
import com.exchange.api.dao.UserLiabilityDao;
import com.exchange.api.dao.impl.EXUserDAOImpl;
import com.exchange.api.dao.impl.ExMatchOddsBetDaoImpl;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CustomSequencesRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXMatchAbndAndTieRepository;

import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.MinMaxBetRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.api.service.EXBetService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.exchange.util.EXGlobalConstantProperties;

@Service
public class EXBetServiceImpl implements EXBetService {

	@Autowired
	EventRepository seriesEventRepository;

	@Autowired
	MarketResultRepository marketResultRepository;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	EXUserRepository userRepo;
	@Autowired
	EXChipDetailRepository exchipRepo;
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	MinMaxBetRepository maxRepo;

	@Autowired
	LiabilityRepository liabilityRepository;
	
	@Autowired
	UserLiabilityDao libDao;

	@Autowired
	MarketRepository eventMarketRepository;

	@Autowired
	SelectionIdRepository selectionIdRepository;


	ResponseBean responseBean;
	DateFormat formatter;
	Date date,startDate,endDate;
	String date1;

	@Autowired
	FancyRepository fancyRepository;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	FancyResultRepository fancyresultRepository;

	

	
	@Autowired
	UserIpRepository ipRepo;

	@Autowired
	AppChargeRepo appchargeRepo;

	@Autowired
	EXChipDetailRepository exChipDetailRepository;

	@Autowired
	EXGlobalConstantProperties exglobalConst;

	
	@Autowired
	MarketResultRepository matchResultrepo;
	
	@Autowired
	EXMatchAbndAndTieRepository matchabndtieRepo;
	EXDateUtil  dtutil = new EXDateUtil();		
	
	
	EXUserDAO exuserDao = new EXUserDAOImpl();
	
	@Autowired
    EXChipDetailRepository chipDtlRepo;
	
	@Autowired
	ExMatchOddsBetDaoImpl betlistDao;
	
	@Autowired
	CustomSequencesRepository custumSeqRepo;
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;

	
	@Autowired
	EXMySqlProcedureDao  mysqlProcedureDao;
	
	@Override
	@Transactional
	
/*	public EXChipDetail SaveChip(String chips,String userid,Boolean Profitloos, String ToUserName,String matchnameandodds, String parentid, String ByUser, String type,String adminid, String masterid, String dealerid, BigDecimal userbalance,String CommId, Double GivenComm,String Usertype,BigDecimal previousbalance,Integer eventid) {
		EXChipDetail chipdetail = new EXChipDetail();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		chipdetail.setChips(Double.parseDouble(chips));
		chipdetail.setUserid(userid);
		if(Profitloos == true) {
			chipdetail.setNarration("Chip Credited to "+ToUserName+" by "+matchnameandodds);

		}else {
			chipdetail.setNarration("Chip Withdraw from "+ToUserName+" by "+matchnameandodds);	
		}

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		chipdetail.setCreatedOn(new Date());
		chipdetail.setParentid(parentid);
		chipdetail.setCrdr(Profitloos);
		chipdetail.setType(type);
		chipdetail.setAdminid(adminid);
		chipdetail.setMasterid(masterid);
		chipdetail.setDealerid(dealerid);
		chipdetail.setUserbalance(userbalance);
		chipdetail.setCommgiven(GivenComm);
		chipdetail.setUsertype(Usertype);
		chipdetail.setPreviousbalance(previousbalance.doubleValue());
		chipdetail.setEventid(eventid);
		return chipdetail;

	}
	 */
	
	public  Double getDaimondBets(Boolean isBack,Double price,int selectionids,String uri,Double range, String Bhaotype,Market market)
	{
		return range;/*


		EXdevelopersOdss devOdddsbean = new EXdevelopersOdss();
		devOdddsbean =exdevelopersMatchoddsRepo.findByFancyidAndFancyname(market.getMarketid(), "Daimond");
		
		try{
			
			JSONArray result = new JSONArray();

			if(Bhaotype.equalsIgnoreCase("2")){

				if(devOdddsbean!=null){

					JSONObject json  = new JSONObject(devOdddsbean.getOdds());

					if(!json.toString().equalsIgnoreCase("{}")){

						if(json.has("market")){
							JSONArray response= json.getJSONArray("market");
							 for(int i = 0;i<response.length();i++){

								 JSONObject obj = response.getJSONObject(i);

								 JSONObject odds = new JSONObject();	
								 odds.put("id", obj.getString("marketId"));
								 odds.put("name", "Match Odds");
								 odds.put("betfair", true);
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
								 JSONArray runners = response.getJSONObject(i).getJSONArray("events");
									for(int j=0;j<runners.length();j++){
										back = new JSONArray();
										JSONArray lay = new JSONArray();
										String bhaotype = "0.0"+Bhaotype;
										JSONObject backObject = new JSONObject();
										BigDecimal bBigDecimal = new BigDecimal("0.01");
										BigDecimal fixedval = new BigDecimal("0.10");
										BigDecimal backsize = new BigDecimal(runners.getJSONObject(j).getString("BackPrice1"));
										BigDecimal laysize = new BigDecimal(runners.getJSONObject(j).getString("LayPrice1"));
										if(j==0){
											
												back1 =backsize;
												lay1 =laysize;
												team1 = backsize.add(laysize);

										}
										else if(j==1){
											
												back2 =backsize;
												lay2 =laysize;
												team2 = backsize.add(laysize);
										}
										else if(j==2){
											back3 =backsize;
											lay3 =laysize;
											team3 = backsize.add(laysize);
										}

										backObject.put("price", backsize);
										backObject.put("size", runners.getJSONObject(j).getString("BackSize1"));
										back.put(backObject);
										
										JSONObject layObject = new JSONObject();
										layObject.put("price", laysize); 
										layObject.put("size", runners.getJSONObject(j).getString("LaySize1"));
										lay.put(layObject);
										
										
										JSONObject selectionid = new JSONObject();
										selectionid.put("id", runners.getJSONObject(j).getString("SelectionId"));
										selectionid.put("name", runners.getJSONObject(j).getString("RunnerName"));
										selectionid.put("back", back);
										selectionid.put("lay",lay);
										runner.put(selectionid);
										
										
									}
									
									if(team2.compareTo(team1)==1 && team2.compareTo(team3)==1){

										
										for(int k =0; k<runner.length();k++){
											if(k==0){
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1.subtract(new BigDecimal("0.01")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);
													
												}
												
											}
											
										}
									}else{
										for(int k =0; k<runner.length();k++){
											if(k==0){
												
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1.subtract(new BigDecimal("0.05")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back1);
													
												}
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
													runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay1.add(new BigDecimal("0.05")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay1);
													
												}
												
												
											
											}
											
										}
									}
									 if(team1.compareTo(team2) ==1 && team1.compareTo(team3)==1){
										for(int k =0; k<runner.length();k++){
										if(k==1){
										
										
											if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
												runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2.subtract(new BigDecimal("0.01")));
												
											}else{
												runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);
												
											}
										}
									}
								}
									else {
										for(int k =0; k<runner.length();k++){
											if(k==1){
											
											
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2.subtract(new BigDecimal("0.05")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back2);
													
												}
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
													runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay2.add(new BigDecimal("0.05")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", lay2);
													
												}
											}
										
									}
								}
									 
									 if(team3.compareTo(team1) ==1 && team3.compareTo(team2)==1){
											for(int k =0; k<runner.length();k++){
											if(k==2){
											
												if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.0){
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3.subtract(new BigDecimal("0.01")));
													
												}else{
													runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3);
													
												}
											}
										}
									}
										else {
											for(int k =0; k<runner.length();k++){
												if(k==2){
												
													
													if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).getString("price").toString())>0.10){
														runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3.subtract(new BigDecimal("0.05")));
														
													}else{
														runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", back3);
														
													}
													if(Double.parseDouble(runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).getString("price").toString())>0.10){
														runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay3.add(new BigDecimal("0.05")));
														
													}else{
														runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", lay3);
														
													}
												}
											
										}
									}
									
									JSONObject event = new JSONObject();
									event.put("name", market.getMatchname());
									event.put("id", market.getEventid());
									odds.put("event",event);
									odds.put("runners", runner);
									result.put(odds);
							 
								 }
						}
					}
				}

				
				
			}


			for (int i = 0;i<result.length();i++){

				JSONArray runners = result.getJSONObject(i).getJSONArray("runners");            		

				for(int j=0;j<runners.length();j++){
					if(runners.getJSONObject(j).getInt("id")==selectionids){
						//JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");

						if(isBack==true&&runners.getJSONObject(j).getJSONArray("back").length()!=0 && runners.getJSONObject(j).getJSONArray("back")!=null){
							JSONArray availableBack = runners.getJSONObject(j).getJSONArray("back");  
							BigDecimal backsize = new BigDecimal(availableBack.getJSONObject(0).getString("price"));
							if(Double.valueOf(backsize.doubleValue()) >= price && backsize.doubleValue() <= Double.parseDouble(new BigDecimal(price.toString()).add(new BigDecimal(range.toString())).toString())){
								return backsize.doubleValue();
								
							}
							
							else if(Double.valueOf(backsize.doubleValue()) <= price && Double.valueOf(backsize.doubleValue()) >= Double.parseDouble(new BigDecimal(price.toString()).subtract(new BigDecimal(range.toString())).toString())){
								return backsize.doubleValue();
							}
							else if(Double.valueOf(backsize.doubleValue()) == price){
								return backsize.doubleValue();
							}else{
								return 1.0;
							}

						}

						else if(isBack==false&&runners.getJSONObject(j).getJSONArray("lay").length()!=0 && runners.getJSONObject(j).getJSONArray("lay")!=null){
							
							
							JSONArray availableLay = runners.getJSONObject(j).getJSONArray("lay");
							BigDecimal laysize = new BigDecimal(availableLay.getJSONObject(0).getString("price"));
							if(Double.valueOf(laysize.doubleValue()) >= price && laysize.doubleValue() <= Double.parseDouble(new BigDecimal(price.toString()).add(new BigDecimal(range.toString())).toString())){
								return laysize.doubleValue();
								
							}
							
							else if(Double.valueOf(laysize.doubleValue()) <= price && Double.valueOf(laysize.doubleValue()) >= Double.parseDouble(new BigDecimal(price.toString()).subtract(new BigDecimal(range.toString())).toString())){
								return laysize.doubleValue();
							}
							else if(Double.valueOf(laysize.doubleValue()) == price){
								return laysize.doubleValue();
							}else{
								return 1.0;
							}
							
							
							

						}

					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1.0;
	*/}
	
	public  Double getBetfairBets(Boolean isBack,Double price,int selectionids,String uri,Double range, String Bhaotype,Market market) throws JSONException
	{

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject request = new JSONObject();
		JSONObject priceProjection = new JSONObject();
		JSONObject params = new JSONObject();
		JSONObject paramsobj = new JSONObject();
		JSONArray marketIds = new JSONArray();
			marketIds.put(market.getMarketid());
	 
	
		JSONArray priceData = new JSONArray();
		priceData.put("EX_BEST_OFFERS");
		request.put("inPlayOnly",false);
		
		priceProjection.put("priceData", priceData);
		priceProjection.put("virtualise",true);
		params.put("marketIds", marketIds);
		params.put("priceProjection", priceProjection);
		paramsobj.put("params", params);
		HttpEntity<String> entity = new HttpEntity<String>(paramsobj.toString(), headers);
		RestTemplate restTemplate = new RestTemplate();
		String json = null;
		ResponseEntity<String> responsedata = restTemplate.exchange("http://api.fairbetexch.com/api/rest/odds", HttpMethod.POST, entity, String.class);
		/*try {
			 json  = new String(Files.readAllBytes(Paths.get("Cricket"+"/"+market.getEventid()+".json")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		try{
			Market marketbean= nativeQueryDao.getMarketAndSelId(market.getEventid().toString());
			String[] runnername =marketbean.getRunnername().split(",");

			if(responsedata.getStatusCode() == HttpStatus.OK){
				JSONArray response = new JSONArray(new JSONArray(responsedata.getBody()).toString());
				
			    JSONArray result = new JSONArray();
			    if(Bhaotype.equalsIgnoreCase("2")){

			    	 // response = new JSONArray(json.toString());
					for(int i = 0;i<response.length();i++){

						JSONObject odds = new JSONObject();	
						odds.put("id", response.getJSONObject(i).getString("marketId"));
						odds.put("name", "Match Odds");
						odds.put("sid", market.getCricexchageid());
						odds.put("betfair", true);
						odds.put("ispause", market.getIspause());
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
								String bhaotype = "0.0"+Bhaotype;
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
							selectionid.put("name", runnername[j]);
							selectionid.put("back", back);
							selectionid.put("lay",lay);
							runner.put(selectionid);
						}

						if(selIds.size()>2){
							if(market.getSportid() ==2){
								for(int k =0; k<runner.length();k++){

									runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",  Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));
									runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price",  0.0);


								}

							}else{


								/* if Test Match or Football */
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
							if(market.getSportid() ==2){

								/* if Odi,T20,T10 Match or Tennis */

								for(int k =0; k<runner.length();k++){

									runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",  Double.valueOf(runner.getJSONObject(k).getJSONArray("back").getJSONObject(1).getString("price")));
									runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price",  0.0);


								}
							}else{


								/* if Odi,T20,T10 Match or Tennis */
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
						JSONObject minmax = new JSONObject();
						event.put("name", market.getMatchname());
						event.put("id", market.getEventid());
						minmax.put("minbet", market.getMinbet());
						minmax.put("maxbet", market.getMaxbet());

						odds.put("event",event);
						odds.put("minmax",minmax);
						odds.put("runners", runner);
						result.put(odds);

					
					}	

				}else{

					for(int i = 0;i<response.length();i++){
						JSONObject odds = new JSONObject();	
						odds.put("id", response.getJSONObject(i).getString("marketId"));
						odds.put("name", "Match Odds");
						odds.put("betfair", true);
						JSONArray runner = new JSONArray();


						JSONArray runners = response.getJSONObject(i).getJSONArray("runners");

						for(int j=0;j<runners.length();j++){
							JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");
							JSONArray back = new JSONArray();
							JSONArray lay = new JSONArray();
							if(ex.getJSONArray("availableToBack").length()!=0 && ex.getJSONArray("availableToBack")!=null){
								JSONArray availableBack = ex.getJSONArray("availableToBack");            		

								for(int b=0;b<availableBack.length();b++){
									JSONObject backObject = new JSONObject();

									backObject.put("price", availableBack.getJSONObject(b).getString("price"));
									//backObject.put("price", availableBack.getJSONObject(b).get("price"));
									backObject.put("size", availableBack.getJSONObject(b).get("size"));
									back.put(backObject);
								}

							}

							if(ex.getJSONArray("availableToLay").length()!=0 && ex.getJSONArray("availableToLay")!=null){
								JSONArray availableLay = ex.getJSONArray("availableToLay");

								for(int l = 0;l<availableLay.length();l++){
									JSONObject layObject = new JSONObject();
									layObject.put("price", availableLay.getJSONObject(l).get("price")); 
									layObject.put("size", availableLay.getJSONObject(l).get("size"));
									lay.put(layObject);
								}            		
							}
							JSONObject selectionid = new JSONObject();
							selectionid.put("id", runners.getJSONObject(j).getString("selectionId"));
							selectionid.put("name", selectionIdRepository.findBySelectionidAndMarketid(Integer.parseInt(runners.getJSONObject(j).getString("selectionId")),market.getMarketid()).getRunnerName());
							selectionid.put("back", back);
							selectionid.put("lay",lay);
							runner.put(selectionid);
						}

						JSONObject event = new JSONObject();
						event.put("name", market.getMatchname());
						event.put("id", market.getEventid());
						odds.put("event",event);
						odds.put("runners", runner);
						result.put(odds);
					}	

				}
				for (int i = 0;i<result.length();i++){

					JSONArray runners = result.getJSONObject(i).getJSONArray("runners");            		

					for(int j=0;j<runners.length();j++){
						if(runners.getJSONObject(j).getInt("id")==selectionids){
							//JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");

							if(isBack==true&&runners.getJSONObject(j).getJSONArray("back").length()!=0 && runners.getJSONObject(j).getJSONArray("back")!=null){
								JSONArray availableBack = runners.getJSONObject(j).getJSONArray("back");  
								BigDecimal backsize = new BigDecimal(availableBack.getJSONObject(0).getString("price"));
								if(Double.valueOf(backsize.doubleValue()) >= price && backsize.doubleValue() <= Double.parseDouble(new BigDecimal(price.toString()).add(new BigDecimal(range.toString())).toString())){
									return backsize.doubleValue();
									
								}
								
								else if(Double.valueOf(backsize.doubleValue()) <= price && Double.valueOf(backsize.doubleValue()) >= Double.parseDouble(new BigDecimal(price.toString()).subtract(new BigDecimal(range.toString())).toString())){
									return backsize.doubleValue();
								}
								else if(Double.valueOf(backsize.doubleValue()) == price){
									return backsize.doubleValue();
								}else{
									return 1.0;
								}

							}

							else if(isBack==false&&runners.getJSONObject(j).getJSONArray("lay").length()!=0 && runners.getJSONObject(j).getJSONArray("lay")!=null){
								
								
								JSONArray availableLay = runners.getJSONObject(j).getJSONArray("lay");
								BigDecimal laysize = new BigDecimal(availableLay.getJSONObject(0).getString("price"));
								if(Double.valueOf(laysize.doubleValue()) >= price && laysize.doubleValue() <= Double.parseDouble(new BigDecimal(price.toString()).add(new BigDecimal(range.toString())).toString())){
									return laysize.doubleValue();
									
								}
								
								else if(Double.valueOf(laysize.doubleValue()) <= price && Double.valueOf(laysize.doubleValue()) >= Double.parseDouble(new BigDecimal(price.toString()).subtract(new BigDecimal(range.toString())).toString())){
									return laysize.doubleValue();
								}
								else if(Double.valueOf(laysize.doubleValue()) == price){
									return laysize.doubleValue();
								}else{
									return 1.0;
								}
								
								
								

							}

						}
					}
				}
			}
			


		
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1.0;
	}
	
	public UserLiability getLiblityBeforeBet(String marketid,String userid,ExMatchOddsBet placebet){
		
		 DecimalFormat df = new DecimalFormat("#00");
			ArrayList<ExMatchOddsBet> betList = new ArrayList<>();
			ArrayList<Integer> sortedlist = new ArrayList<>(); 
			Double pnl1=0.0;
			Double pnl2=0.0;
			Double pnl3=0.0;
			
			
			UserLiability liability = new UserLiability();
			LinkedHashMap<Integer, Double> lib = new LinkedHashMap<Integer, Double>();
			betList.addAll(placebetrepository.findByMarketidAndUserid(marketid, userid));
		
		
				
				betList.add(placebet);
				ArrayList<SelectionIds> selids = selectionIdRepository.findByMarketid(marketid);
			    if(selids.size() ==2){
			    
			    		for (ExMatchOddsBet bt : betList){
			    			if(bt.getSelectionid() == selids.get(0).getSelectionid()){
			    				if(bt.getIsback()){
			    					pnl1 = pnl1+bt.getPnl();
				    				
				    				if(pnl2<0.0){
				    					pnl2 = pnl2-bt.getLiability();
				    				}else{
				    					pnl2 = pnl2-bt.getLiability();
				    				}
			    				}else{
			    					
			    					if(pnl1<0.0){
				    					pnl1 = pnl1-bt.getLiability();
				    				}else{
				    					pnl1 = pnl1-bt.getLiability();
				    				}
			    					pnl2 = pnl2+bt.getPnl();
			    				}
			    				
			    			}else{
			    				if(bt.getIsback()){
			    					
			    					if(pnl1<0.0){
				    					pnl1 = pnl1-bt.getLiability();
				    				}else{
				    					pnl1 = pnl1-bt.getLiability();
				    				}
			    					pnl2 = pnl2+bt.getPnl();;
				    			}else{
				    				pnl1 = pnl1+bt.getPnl();
				    				
				    				if(pnl2<0.0){
				    					pnl2 = pnl2-bt.getLiability();
				    				}else{
				    					pnl2 = pnl2-bt.getLiability();
				    				}
				    			}
			    				
			    				
			    			}
			    		}
			    	
			    	
			    }else{
			    	for (ExMatchOddsBet bt : betList){
		    			if(bt.getSelectionid() == selids.get(0).getSelectionid()){
		    				if(bt.getIsback()){
		    					pnl1 = pnl1+bt.getPnl();
			    				
			    				if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
			    				if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
		    				}else{
		    					
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					pnl2 = pnl2+bt.getPnl();
		    					pnl3 = pnl3+bt.getPnl();
		    				}
		    				
		    			}else if(bt.getSelectionid() == selids.get(1).getSelectionid()){

		    				if(bt.getIsback()){
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
		    					pnl2 = pnl2+bt.getPnl();;
			    			}else{
			    				pnl1 = pnl1+bt.getPnl();
			    				pnl3 = pnl3+bt.getPnl();
			    				if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
			    			}
		    				
		    				
		    			
		    			}else{
		    				if(bt.getIsback()){
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
		    					pnl3 = pnl3+bt.getPnl();;
			    			}else{
			    				pnl1 = pnl1+bt.getPnl();
			    				pnl2 = pnl3+bt.getPnl();
			    				if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
			    		 	}
		    				
		    			}
		    		}
			    }
		
			    liability.setPnl1(pnl1);
			    liability.setPnl2(pnl2);
			    liability.setPnl3(pnl3);
			  
			    
			   
			    ArrayList<Double> list = new ArrayList<Double>();
			    list.add(pnl1);
			    list.add(pnl2);
			    list.add(pnl3);
			    Collections.sort(list);
			    if(list.get(0)<0){
			    	liability.setLiability(-list.get(0));
			    }else{
			    	liability.setLiability(0.0);
			    }
			  //System.out.println(list.get(0));
		return liability;
		
	}
	
	public UserLiability getMarketLiability(String marketid,String userid,EXUser user){
		
		 DecimalFormat df = new DecimalFormat("#00");
			ArrayList<ExMatchOddsBet> betList = new ArrayList<>();
			ArrayList<Integer> sortedlist = new ArrayList<>(); 
			Double pnl1=0.0;
			Double pnl2=0.0;
			Double pnl3=0.0;
			UserLiability liability =new UserLiability();
			
			if(user.getUsertype()==3){
				betList.addAll(placebetrepository.findByMarketidAndUserid(marketid, userid));
				
			}
			
				ArrayList<SelectionIds> selids = selectionIdRepository.findByMarketid(marketid);
			    if(selids.size() ==2){
			    
			    		for (ExMatchOddsBet bt : betList){
			    			if(bt.getSelectionid() == selids.get(0).getSelectionid()){
			    				if(bt.getIsback()){
			    					pnl1 = pnl1+bt.getPnl();
				    				
				    				if(pnl2<0.0){
				    					pnl2 = pnl2-bt.getLiability();
				    				}else{
				    					pnl2 = pnl2-bt.getLiability();
				    				}
			    				}else{
			    					
			    					if(pnl1<0.0){
				    					pnl1 = pnl1-bt.getLiability();
				    				}else{
				    					pnl1 = pnl1-bt.getLiability();
				    				}
			    					pnl2 = pnl2+bt.getPnl();
			    				}
			    				
			    			}else{
			    				if(bt.getIsback()){
			    					
			    					if(pnl1<0.0){
				    					pnl1 = pnl1-bt.getLiability();
				    				}else{
				    					pnl1 = pnl1-bt.getLiability();
				    				}
			    					pnl2 = pnl2+bt.getPnl();;
				    			}else{
				    				pnl1 = pnl1+bt.getPnl();
				    				
				    				if(pnl2<0.0){
				    					pnl2 = pnl2-bt.getLiability();
				    				}else{
				    					pnl2 = pnl2-bt.getLiability();
				    				}
				    			}
			    				
			    				
			    			}
			    		}
			    	
			    	
			    }else{
			    	for (ExMatchOddsBet bt : betList){
		    			if(bt.getSelectionid() == selids.get(0).getSelectionid()){
		    				if(bt.getIsback()){
		    					pnl1 = pnl1+bt.getPnl();
			    				
			    				if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
			    				if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
		    				}else{
		    					
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					pnl2 = pnl2+bt.getPnl();
		    					pnl3 = pnl3+bt.getPnl();
		    				}
		    				
		    			}else if(bt.getSelectionid() == selids.get(1).getSelectionid()){

		    				if(bt.getIsback()){
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
		    					pnl2 = pnl2+bt.getPnl();;
			    			}else{
			    				pnl1 = pnl1+bt.getPnl();
			    				pnl3 = pnl3+bt.getPnl();
			    				if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
			    			}
		    				
		    				
		    			
		    			}else{
		    				if(bt.getIsback()){
		    					
		    					if(pnl1<0.0){
			    					pnl1 = pnl1-bt.getLiability();
			    				}else{
			    					pnl1 = pnl1-bt.getLiability();
			    				}
		    					if(pnl2<0.0){
			    					pnl2 = pnl2-bt.getLiability();
			    				}else{
			    					pnl2 = pnl2-bt.getLiability();
			    				}
		    					pnl3 = pnl3+bt.getPnl();;
			    			}else{
			    				pnl1 = pnl1+bt.getPnl();
			    				pnl2 = pnl3+bt.getPnl();
			    				if(pnl3<0.0){
			    					pnl3 = pnl3-bt.getLiability();
			    				}else{
			    					pnl3 = pnl3-bt.getLiability();
			    				}
			    		 	}
		    				
		    			}
		    		}
			    }
		
			    liability.setPnl1(pnl1);
			    liability.setPnl2(pnl2);
			    liability.setPnl3(pnl3);
			  
			    
			   
			    ArrayList<Double> list = new ArrayList<Double>();
			    list.add(pnl1);
			    list.add(pnl2);
			    list.add(pnl3);
			    Collections.sort(list);
			    if(list.get(0)<0){
			    	liability.setLiability(-list.get(0));
			    }else{
			    	liability.setLiability(0.0);
			    }
			  //System.out.println(list.get(0));
		return liability;
		
	}
	
	@Override
	@Transactional
	public ResponseBean deleteMarketBetBySeries(@RequestParam String marketid,@RequestParam Integer start,@RequestParam Integer end,EXUser usersession) throws Exception {
		
		
		DecimalFormat df = new DecimalFormat("#0.00");
		ArrayList<ExMatchOddsBet> betlist = betlistDao.getMarketbetBySeries(marketid, start, end,false);
		ArrayList<SelectionIds> selids = selectionIdRepository.findByMarketid(marketid);

		UserLiability liability = new UserLiability();
		UserLiability oldlibility = new UserLiability();
		ResponseBean responseBean = new ResponseBean();
		
		if(betlist.size()>0){
			for(ExMatchOddsBet placebet :betlist){
				placebet = placebetrepository.findByid(placebet.getId());
				
				/*placebet.setIsmongodbupdated(false);
				date = new Date();

				placebet.setIsactive(false);
				placebet.setIsdeleted(true);
				placebet.setDeletedBy(usersession.getId().toString());
				placebet.setUpdatedon(date);
				placebet.setAddetoFirestore(false);
*/
				liability = liabilityRepository.findByMarketidAndUserid(placebet.getMarketid(),placebet.getUserid());
			/*	liability.setIsmongodbupdated(false);
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(oldlibility, liability);	
				*/
				Double pnl1=0.0;
				Double pnl2=0.0;
				Double pnl3=0.0;
				Double lib=0.0;
				if(selids.size() == 2){
					if(placebet.getSelectionid() == selids.get(0).getSelectionid()){
						if(placebet.getIsback()){
							pnl1 =-placebet.getPnl();
							pnl2=placebet.getLiability();
							pnl3=0.0;
							lib= -placebet.getLiability();
						}else{
							pnl1 =placebet.getLiability();
							pnl2=-placebet.getPnl();
							pnl3=0.0;
							lib=  placebet.getPnl();
						}
					}else{
						if(placebet.getIsback()){
							pnl1 =placebet.getLiability();
							pnl2=-placebet.getPnl();
							pnl3=0.0;
							lib= -placebet.getLiability();
						}else{
							pnl1 =-placebet.getPnl();
							pnl2= placebet.getLiability();
							pnl3=0.0;
							lib=  placebet.getPnl();		
						}
					}
					
				}else{ 
					if(placebet.getSelectionid() == selids.get(0).getSelectionid()){
						if(placebet.getIsback()){
							pnl1 =-placebet.getPnl();
							pnl2=placebet.getLiability();
							pnl3=placebet.getLiability();
							lib= -placebet.getLiability();
						}else{
							pnl1 =placebet.getLiability();
							pnl2=-placebet.getPnl();
							pnl3=-placebet.getPnl();
							lib=  placebet.getPnl();
						}
					}else if(placebet.getSelectionid() == selids.get(1).getSelectionid()){
						if(placebet.getIsback()){
							
							pnl1 =placebet.getLiability();
							pnl2=-placebet.getPnl();
							pnl3=placebet.getLiability();
							lib= -placebet.getLiability();
						}else{
							
							pnl1 =-placebet.getPnl();
							pnl2= placebet.getLiability();
							pnl3=-placebet.getPnl();
							lib=  placebet.getPnl();			}
					}else{
						if(placebet.getIsback()){
							pnl1 =placebet.getLiability();
							pnl2=placebet.getLiability();
							pnl3=-placebet.getPnl();
							lib= -placebet.getLiability();
						}else{
							pnl1 =-placebet.getPnl();
							pnl2=-placebet.getPnl();
							pnl3=placebet.getLiability();
							lib=  placebet.getPnl();		
						}
					}
					
				
				
			}
				
				 HashMap<String, Object> hm = new HashMap<String, Object>();
				 
					hm.put("pnl1",pnl1);
					hm.put("pnl2",pnl2);
					hm.put("pnl3",pnl3);
					hm.put("lib",lib);
					hm.put("betid",placebet.getId());
					hm.put("libid",liability.getId());
				 
				    mysqlProcedureDao.deleteMarketBet(hm);
				
			
				/*  ArrayList<Double> list = new ArrayList<Double>();
				    list.add(pnl1);
				    list.add(pnl2);
				    list.add(pnl3);
				    Collections.sort(list);
				    if(list.get(0)<0){
				    	liability.setLiability(-list.get(0));
				    }else{
				    	liability.setLiability(0.0);
				    }

				liability.setPnl1(pnl1);
				liability.setPnl2(pnl2);
				liability.setPnl3(pnl3);
			
				placebetrepository.save(placebet);
				liabilityRepository.save(liability);
		     */

		}
		
		
			responseBean.setType("success");
			responseBean.setMessage("Bet deleted successfully");
			responseBean.setTitle("success");
			return responseBean;
	}
		return responseBean;
}

	@Override
	@Transactional
	public ResponseBean rollbackMatchBet(Integer id) throws Exception {
		// TODO Auto-generated method stub
		   
		
		ExMatchOddsBet placebet = placebetrepository.findByidAndIsdeletedAndIsactive(id,true,false);
		if(placebet!=null) {
			EXUser user = userRepo.findByUserid(placebet.getUserid());
			ArrayList<SelectionIds> selids = selectionIdRepository.findByMarketid(placebet.getMarketid());

			UserLiability liability = new UserLiability();
			UserLiability oldlibility = new UserLiability();
			ResponseBean responseBean = new ResponseBean();
			
			Market mrkt = eventMarketRepository.findByMarketidAndStatus(placebet.getMarketid(), true);
			if(mrkt!=null) {
				date = new Date();

				placebet.setIsactive(true);
				placebet.setDeletedBy("");
				placebet.setIsdeleted(false);
				placebet.setUpdatedon(date);
				placebet.setAddetoFirestore(false);
				liability = liabilityRepository.findByMarketidAndUserid(placebet.getMarketid(),placebet.getUserid());
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				BeanUtils.copyProperties(oldlibility, liability);	
				
				Double pnl1=0.0;
				Double pnl2=0.0;
				Double pnl3=0.0;
				if(selids.size() == 2){
					if(placebet.getSelectionid() == selids.get(0).getSelectionid()){
						if(placebet.getIsback()){
							pnl1 =liability.getPnl1()+placebet.getPnl();
							pnl2=liability.getPnl2()-placebet.getLiability();
						}else{
							pnl1 =liability.getPnl1()-placebet.getLiability();
							pnl2=liability.getPnl2()+placebet.getPnl();
						}
					}else{
						if(placebet.getIsback()){
							pnl1 =liability.getPnl1()-placebet.getLiability();
							pnl2=liability.getPnl2()+placebet.getPnl();	
						}else{
							pnl1=liability.getPnl1()+placebet.getPnl();
							pnl2=liability.getPnl2()-placebet.getLiability();
						}
					}
					
				}else{
					if(placebet.getSelectionid() == selids.get(0).getSelectionid()){
						if(placebet.getIsback()){
							pnl1 =liability.getPnl1()-placebet.getPnl();
							pnl2=liability.getPnl2()+placebet.getLiability();
							pnl3=liability.getPnl3()+placebet.getLiability();
						}else{
							pnl1 =liability.getPnl1()+placebet.getLiability();
							pnl2=liability.getPnl2()-placebet.getPnl();
							pnl3=liability.getPnl3()-placebet.getPnl();
						}
					}else if(placebet.getSelectionid() == selids.get(1).getSelectionid()){
						if(placebet.getIsback()){
							pnl1 =liability.getPnl1()+placebet.getLiability();
							pnl2=liability.getPnl2()-placebet.getPnl();	
							pnl3 =liability.getPnl3()+placebet.getLiability();
						}else{
							pnl1=liability.getPnl1()-placebet.getPnl();
							pnl2=liability.getPnl2()+placebet.getLiability();
							pnl3=liability.getPnl3()-placebet.getPnl();
						}
					}else{
						if(placebet.getIsback()){
							pnl1 =liability.getPnl1()+placebet.getLiability();
							pnl2=liability.getPnl2()+placebet.getLiability();	
							pnl3 =liability.getPnl3()-placebet.getPnl();
						}else{
							pnl1=liability.getPnl1()-placebet.getPnl();
							pnl2=liability.getPnl2()-placebet.getPnl();
							pnl3=liability.getPnl3()+placebet.getLiability();
						}
					}
					
				}
				
				  ArrayList<Double> list = new ArrayList<Double>();
				    list.add(pnl1);
				    list.add(pnl2);
				    list.add(pnl3);
				    Collections.sort(list);
				    if(list.get(0)<0){
				    	liability.setLiability(-list.get(0));
				    }else{
				    	liability.setLiability(0.0);
				    }

				liability.setPnl1(pnl1);
				liability.setPnl2(pnl2);
				liability.setPnl3(pnl3);
				liability.setAddetoFirestore(false);
				placebetrepository.save(placebet);
				liabilityRepository.save(liability);
				responseBean.setType("success");
				responseBean.setMessage("Bet Roll Back successfully");
				responseBean.setTitle("success");

				
			}else {
				responseBean.setType("error");
				responseBean.setMessage("Bet Can not Roll Back Market settled..");
				responseBean.setTitle("Oops..");
			}
		}else {
			responseBean.setType("error");
			responseBean.setMessage("Bet Can not Roll Back ...");
			responseBean.setTitle("Oops..");
		}
		
		
		return responseBean;
		
	}

	@Override
	public ResponseBean placeBet(ExMatchOddsBet placebet, EXUser usersession) throws InterruptedException,
			UnknownHostException, ParseException, JSONException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}
}
