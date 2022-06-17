package com.exchange.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXFancyResultCron;
import com.exchange.api.bean.EXFancyRollBackDetail;
import com.exchange.api.bean.EXMatchFancy;
import com.exchange.api.bean.EXRsFancyResult;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXFancyResultCronRepository;
import com.exchange.api.repositiry.EXFancyRollBackDetailRepository;
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
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@RestController
@RequestMapping("/api")
public class EXSetResultController {

	@Autowired
    private EXSetResultService matchresultservice;
	
	
	
	
	
	@Autowired EXbetController btcont;
	
	@Autowired FancyResultRepository fancyResultRepository;
	
	@Autowired MarketResultRepository marketResultRepository;
	
	@Autowired BetRepository betRepository;
	
	@Autowired
	LiabilityRepository liabilityRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ExMatchOddsBetDao betlistDao;
	
	@Autowired
	EXUserRepository userRepo;
	
	@Autowired
	AppChargeRepo appchargeRepo;
	
	@Autowired
	FancyRepository matchfancyRepo;
	
	@Autowired
	LiabilityRepository userLibRepo;
	
	@Autowired
	EXMatchAbndAndTieRepository matchabndtieRepo;

	@Autowired
	EXFancyResultCronRepository fancyResultCron;
	 
	
	@Autowired
	FancyResultRepository fancyresultRepository;
	
	@Autowired
	FancyRepository fancyRepository;

	@Autowired
	MarketRepository eventMarketRepository;
	
	@Autowired
	EventRepository seriesEventRepository;

	@Autowired
	SelectionIdRepository selectionIdRepository;
	

	@Autowired
	EXRsFancyResultRepository rsFancyResultRepo;
	
	@Autowired
	EXFancyRollBackDetailRepository fanncyRollbackDetailRepo;
	
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;

	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	    
	    @RequestMapping(value = "/setMarketResult", method = RequestMethod.POST ,consumes="application/json")
	    public  ResponseEntity<Object> setMarketResult(@Valid @RequestBody MarketResult result) throws Exception
	    {
	    	ResponseBean responseBean = new ResponseBean();

	    	HttpSession session = request.getSession(true);
			EXUser usersession = (EXUser) session.getAttribute("user");
				
			Market mkt1 = eventMarketRepository.findBymarketid(result.getMarketid());
			if(mkt1.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || mkt1.getMarketname().equalsIgnoreCase(EXConstants.MatchOdds)) {
			/*	ArrayList<MatchFancy>  list = matchfancyRepo.findByEventidAndIsActiveAndStatusAndIssuspendedByAdmin(result.getMatchid(), true, "OPEN",false);
		    	if(list.size()>0){
		    		responseBean.setType("error");
		    		responseBean.setMessage("Please set All Fancy Result Before Match Result");
		    		responseBean.setTitle("Oops...");
		    		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
		    	}else{
		    		
		    		responseBean =	setResult(result);
		    	}*/
				
				responseBean =	setResult(result);
			}else {
				responseBean =  setResult(result);
			}
	    
	    	return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
			

	    }
	    
	    public ResponseBean setResult( MarketResult result) {

	    	Integer sportId =0;
	    	ResponseBean responseBean = new ResponseBean();
    		
			MarketResult resultbean =null;
	    	Market market = null;
	    	Event event =null;
	    	
	    	resultbean =marketResultRepository.findByMarketid(result.getMarketid());
	    	if(resultbean == null) {


	    		market = eventMarketRepository.findBymarketid(result.getMarketid());
	    		sportId = market.getSportid();
	    		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		result.setDate(new Date());
	    		result.setStatus(true);
	    		result.setIsResult(true);
	    		result.setType("Result");
	    		result.setResultStatusCron(false);
				
	    		
	    		market.setStatus(false);
	    		market.setIsactive(false);
	    		market.setIsResult(true);
	    		market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
	    			
	    		
	    		ArrayList<Market> mkt = eventMarketRepository.findByEventidAndStatus(result.getMatchid(),true);
	    		
	    		if(mkt.size() == 1) {
	    			
		    			event = seriesEventRepository.findByeventid(result.getMatchid());
		    			event.setStatus(false);
	    				seriesEventRepository.save(event);
		    			marketResultRepository.save(result);
			    		eventMarketRepository.save(market);	
		    			
	    		}
	    	else {
    		
	    		marketResultRepository.save(result);
	    		eventMarketRepository.save(market);	
	    		
    		}
	    		
	   }


	    	responseBean.setType("success");
	    	responseBean.setMessage("Result set successfully");
	    	responseBean.setTitle("success");
	    	return responseBean;


	    }
	    
	   
	    
	    @RequestMapping(value = "/setFancyResult", method = RequestMethod.POST ,consumes="application/json")
	    public  ResponseEntity<Object> setFancyResult(@Valid @RequestBody ExFancyResult result) throws Exception 
	    {
			ResponseBean responseBean = new ResponseBean();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HttpHeaders headers = new HttpHeaders();
		 
			
			HttpSession session = request.getSession(true);
			EXUser usersession =(EXUser) session.getAttribute("user");
			
	    
	  if(usersession!=null) {
		  if(result.getResult()!= -1) {
	    		
		    	
	    		 MatchFancy fancy = fancyRepository.findByFancyidAndEventid(result.getFancyid(), result.getMatchid());
					if(fancy!=null) {
						
						 fancy.setStatus("CLOSE");
						 fancy.setIsActive(false);
						 fancyRepository.save(fancy);
						
						 ExFancyResult fresult	  = fancyresultRepository.findByfancyid(result.getFancyid());
						if(fresult ==null) {
							 result.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
							 result.setResultStatusCron(false);
							 result.setResultdeclareby(usersession.getUserid());
							 result.setIsprofitlossclear(false);
							 result.setFancytype(fancy.getOddstype());
							 fancyresultRepository.save(result);
						}
					
						 
						
				    		
				    		RestTemplate restTemplate = new RestTemplate();
					    	HttpClient httpClient = HttpClientBuilder.create().build();
					    	restTemplate.setRequestFactory(new 
					    	HttpComponentsClientHttpRequestFactory(httpClient));    
					    	HttpHeaders reqHeaders = new HttpHeaders();
					    	HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", reqHeaders);
					    	ResponseEntity<String> responseEntity=restTemplate.exchange(EXConstants.BfToken+"event-odds/declare-fancy/"+result.getFancyid(), HttpMethod.PATCH, 
					    	requestEntity, String.class);
					    	   responseBean.setType("success");
					    		responseBean.setMessage("Result set successfully");
					    		responseBean.setTitle("success");
					}
				 
	    		   
	    	}else {
	    		responseBean.setType("error");
	    		responseBean.setMessage("please enter Correct result");
	    		responseBean.setTitle("Oops...");
	    		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
	    	}
	  }else {
  		responseBean.setType("error");
  		responseBean.setMessage("please login again");
  		responseBean.setTitle("Oops...");
  		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
  	}
	    	
	    	
	    	return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
	    	
	    	
	    } 
	    
	    
	    @RequestMapping(value = "/setFancyReslultByApi", method = RequestMethod.POST ,consumes="application/json")
	    public  ResponseEntity<Object> setFancyReslultByApi(@RequestBody ExFancyResult result) throws Exception 
	    {
			ResponseBean responseBean = new ResponseBean();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HttpHeaders headers = new HttpHeaders();
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
			
			HttpSession session = request.getSession(true);
			EXUser usersession =(EXUser) session.getAttribute("user");
			
			
			if(usersession!=null) {
				ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
		    	
			    
		    	EXRsFancyResult resultdata  = rsFancyResultRepo.findByFancyIdAndMatchidAndIsresultset(result.getFancyid(), result.getMatchid(), false);
		    	 if(resultdata !=null) {
		    		
		    		
		    		MatchFancy fancy = null;
	 				 
		 			fancy = fancyRepository.findByFancyidAndEventid(result.getFancyid(), result.getMatchid());
		 				
		    		
		 	    	result.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
		 	    	result.setResultStatusCron(false);
		 	    	result.setResultdeclareby(usersession.getUserid());
		 	    	result.setGroupid(result.getMatchid().toString());
		 	    	result.setSportid(4);	 
		 	    	result.setSportname("Cricket");
		 	    	result.setSeriesid(0);
		 	    	result.setMatchname(fancy.getMatchname());
		 	    	result.setFancyname(fancy.getName());
		 	    	result.setIsprofitlossclear(false); 
		 	    	if(fancy.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {

		 	    		if(resultdata.getResult().equalsIgnoreCase(EXConstants.Back)) {

		 	    			result.setResult(1);
		 	    			fancy.setStatus("CLOSE");
		 	    			fancy.setIsActive(false);


		 	    		}else if(resultdata.getResult().equalsIgnoreCase(EXConstants.Lay)) {
		 	    			result.setResult(0);
		 	    			fancy.setStatus("CLOSE");
		 	    			fancy.setIsActive(false);

		 	    		}else {
		 	    			fancy.setStatus(EXConstants.fancySuspend);
		 	    		}

		 	    	} else if(fancy.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {

		 	    		if(resultdata.getResult().equalsIgnoreCase(EXConstants.Back)) {

		 	    			result.setResult(1);
		 	    			fancy.setStatus("CLOSE"); 
		 	    			fancy.setIsActive(false);


		 	    		}else if(resultdata.getResult().equalsIgnoreCase(EXConstants.Lay)) {
		 	    			result.setResult(2);
		 	    			fancy.setStatus("CLOSE");
		 	    			fancy.setIsActive(false);

		 	    		}else {
		 	    			fancy.setStatus(EXConstants.fancySuspend);
		 	    		}

		 	    	}else {
		 	    		fancy.setStatus("CLOSE");
	 	    			fancy.setIsActive(false);
		 	    	}
		 				 
		 	    	
		 	   	    fancyRepository.save(fancy);
					fancyresultRepository.save(result);
					rsFancyResultRepo.save(resultdata);
			   
						   if(fancy.getStatus().equalsIgnoreCase(EXConstants.fancySuspend)) {

							   responseBean.setType("error");
							   responseBean.setMessage("result can not set  for this fancy..");
							   responseBean.setTitle("Oops...");
							   return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);


						   }	else {



							   

			 				
		 					RestTemplate restTemplate = new RestTemplate();
		 					HttpClient httpClient = HttpClientBuilder.create().build();
		 					restTemplate.setRequestFactory(new 
		 							HttpComponentsClientHttpRequestFactory(httpClient));    
		 					HttpHeaders reqHeaders = new HttpHeaders();
		 					HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", reqHeaders);
		 					ResponseEntity<String> responseEntity=restTemplate.exchange(EXConstants.BfToken+"event-odds/declare-fancy/"+result.getFancyid(), HttpMethod.PATCH, 
		 							requestEntity, String.class);




		 					responseBean.setType("success");
	 						responseBean.setMessage("Result set successfully");
	 						responseBean.setTitle("success");
	 						return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
		 				}
		 			 }else{
		 				  responseBean.setType("error");
						   responseBean.setMessage("please login to continue..");
						   responseBean.setTitle("Oops...");
						   return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
		    	 }
			}else{
    		    responseBean.setType("success");
 	    		responseBean.setMessage("Can Not Set Result for this  fancy from API");
 	    		responseBean.setTitle("success");
 	    		return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
    	 }
	}
			
			
	    	
	    	
	    	
	    	
	   
	    

	    @RequestMapping(value = "/rollbackFancy", method = RequestMethod.DELETE ,consumes="application/json")
	    public  ResponseEntity<Object> rollbackFancy(@RequestParam(name = "id",required = true) Integer id,@RequestParam(name = "fancyid",required = true) String fancyid,@RequestParam(name = "matchid",required = true) Integer matchid) throws Exception
	    {
			
	    	ResponseBean responseBean = new ResponseBean();
	    	 ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();
	    	 
	    	 HttpSession session = request.getSession(true);
	 		EXUser usersession = (EXUser) session.getAttribute("user");
	    	 
	    	/* MarketResult bean = marketResultRepository.findByMatchid(matchid);
	    	 if(bean != null){*/

	 		if(usersession!=null) {
	 			 MatchFancy mf = fancyRepository.findByFancyid(fancyid);
		    	    if(mf.getIsresultFinished()) {
		    	    
		    	    	ExFancyResult fresult =	fancyresultRepository.findByfancyid(fancyid);
		    	    	if(fresult.getIsprofitlossclear()) {
		    	    		 
		    	    		 ArrayList<UserLiability> liblist =  userLibRepo.findByIsLibilityclearAndIsProfitLossclearAndMarketid(true,false,fresult.getFancyid());
		    	    		if(liblist.size() == 0) {
		    	    			 ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
				    	    	 
				 		        userlib = userLibRepo.findByMatchidAndMarketidAndIsactive(matchid,fancyid,false);
				 		    	
				 		    	
				 		    	String betResult = matchresultservice.rollBackFancyResultsNew(userlib,fancyid,id);
				 		    	

				 		    	 if(betResult.equalsIgnoreCase("OK")){
				 		    		
				 		    		 EXFancyRollBackDetail bean = new  EXFancyRollBackDetail();
				 		    		 bean.setDoneBy(usersession.getUserid());
				 		    		 bean.setStatus("RollBack");
				 		    		 bean.setFancyId(fancyid);
				 		    		 bean.setCreatedon(new Date());
				 		    		 fanncyRollbackDetailRepo.save(bean);
				 		    		 
				 		    		 mf.setStatus("OPEN");
				 		    		 mf.setIsActive(true);
				 		    		 fancyRepository.save(mf);
				 					responseBean.setType("success");
				 					responseBean.setMessage("Fancy rollback successfully");
				 					responseBean.setTitle("success");
				 			       
				 		    	}else {
				 		    		responseBean.setType("error");
				 					responseBean.setMessage("Something went wrong");
				 					responseBean.setTitle("Oops...");
				 		            
				 		    	}
		    	    		}else {
		    	    			{ 
		    	    			  responseBean.setType("error");
								  responseBean.setMessage("please wait fancy Result  is being set");
								  responseBean.setTitle("Oops..."); }
		    	    		}
		    	    		
							
							  }else {
							  responseBean.setType("error");
							  responseBean.setMessage("please wait fancy Result  is being set");
							  responseBean.setTitle("Oops..."); }
							 
		    	    	 
		    	    }else {
		    	    	responseBean.setType("please wait fancy Result  is being set");
	 					responseBean.setMessage("Something went wrong");
	 					responseBean.setTitle("Oops...");
		    	    }
		    	   
	 		}
	    	   
	    	 
	    	
	    	    return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
	    } 
	   
	    
	    
	    @RequestMapping(value = "/rollbackMarketNew", method = RequestMethod.DELETE ,consumes="application/json")
	    public  ResponseEntity<Object> rollbackMarketNew(@Valid @RequestBody MarketResult result) throws InterruptedException, ExecutionException
	    {
	    	HttpSession session = request.getSession(true);
			EXUser usersession = (EXUser) session.getAttribute("user");

	    	ResponseBean responseBean = new ResponseBean();
	    	
	    	Market mbean = eventMarketRepository.findBymarketid(result.getMarketid());
	    	if(mbean.getIsresultFinished()) {
	    		ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
		    	//userlib = userLibRepo.findByMarketidAndIsactiveAndIsresultset(result.getMarketid(), false, true);
	    		userlib = nativeQueryDao.useLibRollBack(mbean.getMarketid());
		    	String betResult = matchresultservice.rollBackBetResultsNewWithoutCommssionMobCommssion(userlib, result);
	    		//System.out.println(userlib);
	    		//String betResult="OK";
		    	if(betResult.equalsIgnoreCase("OK")){

		    		responseBean.setType("success");
		    		responseBean.setMessage("Market rollback successfully");
		    		responseBean.setTitle("success");
		    		return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
		    	}else {
		    		responseBean.setType("error");
		    		responseBean.setMessage("Something went wrong");
		    		responseBean.setTitle("Oops...");
		    		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
		    	}
	    	}else {
	    		responseBean.setType("error");
	    		responseBean.setMessage("Please wait while result is being set");
	    		responseBean.setTitle("Oops...");
	    		return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
	    	}
	    	
	    	

	    } 
	    
		
		@RequestMapping(value = "/abandendTieMarket", method = RequestMethod.POST)
		public ResponseEntity<Object> abandendTieMarket(@RequestParam String marketid,@RequestParam String matchstatus,Integer matchid) {

			ResponseBean responseBean = null;
			DecimalFormat df = new DecimalFormat("#0.00");

			HttpSession session = request.getSession(true);
			EXUser usersession = (EXUser) session.getAttribute("user");

			try{
				if (usersession != null) {
					responseBean = matchresultservice.abandendTieMarket(marketid, matchstatus, matchid, usersession);
					if(responseBean==null){
						 responseBean = new ResponseBean();
						responseBean.setMessage("Something went wrong");
						responseBean.setType("error");
						responseBean.setTitle("Oops...");
					}
				}else{
					 responseBean = new ResponseBean();
					responseBean.setMessage("Something went wrong");
					responseBean.setType("error");
					responseBean.setTitle("Oops...");
				}


			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			return new ResponseEntity<Object>(responseBean,HttpStatus.OK);	

		}
		
		@RequestMapping(value = "/abandendTieMarketRollBack", method = RequestMethod.POST)
		public ResponseEntity<Object> abandendTieMarketRollBack(@RequestParam String marketid,@RequestParam String matchstatus) {

			ResponseBean responseBean =null;
			DecimalFormat df = new DecimalFormat("#0.00");

			HttpSession session = request.getSession(true);
			EXUser usersession = (EXUser) session.getAttribute("user");

			try{
				if (usersession != null) {
					
					responseBean = matchresultservice.abandendTieMarketRollBack(marketid,matchstatus,usersession);
					if(responseBean==null){
						responseBean = new ResponseBean();
						responseBean.setMessage("Something went wrong");
						responseBean.setType("error");
						responseBean.setTitle("Oops...");
						
					}
				}else{
					responseBean = new ResponseBean();
					responseBean.setMessage("Something went wrong");
					responseBean.setType("error");
					responseBean.setTitle("Oops...");
					
				}

				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
			return new ResponseEntity<Object>(responseBean,HttpStatus.OK);	

		}
		
		
}
