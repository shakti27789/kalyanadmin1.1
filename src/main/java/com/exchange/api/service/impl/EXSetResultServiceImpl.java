package com.exchange.api.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.AppCharge;
import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXFacnyResultBean;
import com.exchange.api.bean.EXLedger;
import com.exchange.api.bean.EXMatchAbndAndTie;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.controller.EXbetController;
import com.exchange.api.dao.EXMySqlProcedureDao;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXChipDetailsMongoRepository;
import com.exchange.api.repositiry.EXLedgerRepository;
import com.exchange.api.repositiry.EXMatchAbndAndTieRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyRepositoryNew;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.config.EXSetResultService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;

@Service
@Transactional
public class EXSetResultServiceImpl implements EXSetResultService {


	@Autowired
	EventRepository seriesEventRepository;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	MarketResultRepository marketResultRepository;
	
	@Autowired
	EXUserRepository userRepo;
	
	
	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	MarketRepository eventMarketRepository;

	@Autowired EXbetController betcon;

	@Autowired
	FancyRepository fancyRepository;

	
	@Autowired
	FancyResultRepository fancyresultRepository;

	
	String date1;

	@Autowired
	HttpServletRequest request;

	
	@Autowired
	AppChargeRepo appChargeRepo;
	
	@Autowired
	FancyRepositoryNew fancyRepoNew;
	
	@Autowired
	EXMatchAbndAndTieRepository matchabndtieRepo;
	
	@Autowired
	AppChargeRepo appchargeRepo;
	
	@Autowired
	EXLedgerRepository ledgerRepo;
	
	
	@Autowired
	EXChipDetailRepository exchipRepo;

	
	@Autowired
	EXNativeQueryDao nativeQueryDao;

	@Autowired
	EXMySqlProcedureDao  mysqlProcedureDao;

	
	@Autowired
	EXChipDetailRepository chipRepo;
	
	
	@Autowired
	EXChipDetailsMongoRepository chipRepoMongo;
	 




	@Override
	@Transactional
	public ResponseBean abandendTieMarket(String marketid, String matchstatus, Integer matchid,EXUser usersession) {
		// TODO Auto-generated method stub

		ResponseBean responseBean = null;
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ArrayList<UserLiability> libfancyList = liabilityRepository.findByMatchidAndTypeAndIsactive(matchid,EXConstants.Fancy,true);
		if(libfancyList.size()<1){
			ArrayList<UserLiability> liblist = liabilityRepository.findByMarketidAndIsactiveAndIsresultset(marketid,true,false);
			Integer  i=0;
			for(UserLiability lib :liblist){

				if(lib.getUserid()!=null){
					ArrayList<ExMatchOddsBet> userbetlist = placebetrepository.findByMarketidAndUserid(marketid, lib.getUserid());
					if(userbetlist.size()>0){
						for(ExMatchOddsBet  placeBet :userbetlist){

							Date date = new Date();

							placeBet.setIsactive(false);
							placeBet.setUpdatedon(date);
							placeBet.setAddetoFirestore(false);
							placeBet.setIsmongodbupdated(false);
							placebetrepository.save(placeBet);
							
						}
					}
					
					
					lib.setIsactive(false);
					lib.setOldlibility(lib.getLiability());
					lib.setOldpnl1(lib.getPnl1());
					lib.setOldpnl2(lib.getPnl2());
					lib.setOldpnl3(lib.getPnl3());
					lib.setPnl1(0.0);
					lib.setPnl2(0.0);
					lib.setPnl3(0.0);
					lib.setAddetoFirestore(false);
					lib.setIsmongodbupdated(false);
					lib.setNetpnl(0.0);
					liabilityRepository.save(lib);

					ArrayList<UserLiability> fancylib =liabilityRepository.findByMatchidAndIsactiveAndTypeAndUserid(lib.getMatchid(), true, "Fancy",lib.getUserid());
					if(fancylib.size()<1){
						lib.setIsactive(false);
						liabilityRepository.save(lib);
						
						
					}
				}

					


				i++;	
			}
			
			Market mbean = eventMarketRepository.findByMarketidAndStatus(marketid, true);
			
		
			
			
			if(mbean!=null){
				
				
				EXMatchAbndAndTie mtie = new EXMatchAbndAndTie();
				mtie.setMarketid(marketid);
				mtie.setMatchid(mbean.getEventid());
				mtie.setResult(matchstatus);
				mtie.setMatchname(mbean.getMatchname());
				mtie.setSportid(mbean.getSportid());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				mtie.setDate(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				matchabndtieRepo.save(mtie);
				
			MarketResult marketresult = new MarketResult();

			marketresult.setIsResult(true);
			marketresult.setMarketname(mbean.getMarketname());
			marketresult.setMarkettype(mbean.getMarketname());
			marketresult.setType(matchstatus);
			marketresult.setMarketid(marketid);
			marketresult.setMatchid(mbean.getEventid());
			marketresult.setMatchname(mbean.getMatchname());
			marketresult.setSportid(mbean.getSportid());
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
			marketresult.setSelectionname(matchstatus);
			marketresult.setResult(-1);
			marketresult.setStatus(true);
			
			marketResultRepository.save(marketresult);
			
			
	        ArrayList<Market> mkt = eventMarketRepository.findByEventidAndStatus(matchid,true);
    		
    		if(mkt.size() == 1) {
    			
    			    Event	event = seriesEventRepository.findByeventid(matchid);
	    			event.setStatus(false);
    				seriesEventRepository.save(event);
	    			
	    			
    		}
			
			
    		mbean.setStatus(false);
			mbean.setIsactive(false);
			eventMarketRepository.save(mbean);
		}
			
		
			ledgerRepo.deleteByMarketid(marketid);
			responseBean =new ResponseBean();
			responseBean.setType("success");
			responseBean.setMessage("Match Aabonded successfully");
			responseBean.setTitle("success");
			

		}else{
			responseBean =new ResponseBean();
			responseBean.setType("error");
			responseBean.setMessage("Please Settle All Session before Match Status!");
			responseBean.setTitle("Oops...");
			
		}
		
		
		return responseBean;
	
	}




	@Override
	@Transactional
	public ResponseBean abandendTieMarketRollBack(String marketid, String matchstatus,EXUser usersession) {
		// TODO Auto-generated method stub
		
		
		ArrayList<UserLiability> liblist = liabilityRepository.findByMarketidAndIsactive(marketid,false);
		Integer  i=0;
		ResponseBean responseBean = null;
		for(UserLiability lib :liblist){

			if(lib.getUserid()!=null){
				ArrayList<ExMatchOddsBet> userbetlist = placebetrepository.findByMarketidAndIsactiveAndUserid(marketid,false,lib.getUserid());
				if(userbetlist.size()>0){
					for(ExMatchOddsBet  placeBet :userbetlist){

						Date date = new Date();

						placeBet.setIsactive(true);
						placeBet.setUpdatedon(date);
						placeBet.setAddetoFirestore(false);
						placeBet.setIsmongodbupdated(false);
						placebetrepository.save(placeBet);

					}
				}
			
				lib.setIsactive(true);
				Double oldlib =lib.getOldlibility();
				lib.setLiability(oldlib);
				lib.setPnl1(lib.getOldpnl1());
				lib.setPnl2(lib.getOldpnl2());
				lib.setPnl3(lib.getOldpnl3());
				lib.setOldpnl1(0.0);
				lib.setOldpnl2(0.0);
				lib.setOldpnl3(0.0);
				lib.setAddetoFirestore(false);
				lib.setIsmongodbupdated(false);
				lib.setIsresultset(false);
				liabilityRepository.save(lib);
				
				
				ArrayList<UserLiability> fancylib =liabilityRepository.findByMatchidAndIsactiveAndTypeAndUserid(lib.getMatchid(), true, "Fancy",lib.getUserid());
				if(fancylib.size()<1){

					AppCharge appcharge = appchargeRepo.findByEventidAndUserid(lib.getMatchid().toString(), lib.getUserid());
					if(appcharge!=null){
						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						userRepo.save(userbean);
						appcharge.setMatchsatus(matchstatus);
						appchargeRepo.save(appcharge);
					}
				}


			}else{

				lib.setIsactive(true);
				liabilityRepository.save(lib);
			}

			if(i==liblist.size()-1){
				EXMatchAbndAndTie mtie = matchabndtieRepo.findByMarketid(marketid);
				marketResultRepository.deleteByMarketid(marketid);
				if(mtie!=null){
					Market mbean = eventMarketRepository.findByMarketidAndStatus(marketid, false);
					if(mbean!=null){
						mbean.setStatus(true);
						mbean.setIsactive(true);
						eventMarketRepository.save(mbean);
					}
					Event eventbean = seriesEventRepository.findByEventidAndStatus(lib.getMatchid(), false);
					if(eventbean!=null){
						eventbean.setStatus(true);
						seriesEventRepository.save(eventbean);
					}
				}
				matchabndtieRepo.deleteByMarketid(mtie.getMarketid());
			}	


			i++;	
		}
		 responseBean = new ResponseBean();
		responseBean.setType("success");
		responseBean.setMessage("Match RollBack successfully");
		responseBean.setTitle("success");
		return responseBean;


	}
	
	
	
/*	@Override
	@Transactional
	public String setBetResultsNew(ArrayList<UserLiability> liblist,MarketResult result) {
		// TODO Auto-generated method stub


		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		MarketResult resultbean =null;
		Market market = null;
		Event event =null;



		resultbean =marketResultRepository.findByMarketid(result.getMarketid());
		event = seriesEventRepository.findByeventid(result.getMatchid().toString());
		if(resultbean == null) {



			market = eventMarketRepository.findBymarketid(result.getMarketid());
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.setDate(new Date());
			result.setStatus(true);
			result.setIsResult(true);
			result.setType("Result");
			result.setAppid(result.getAppid());
			if(event!=null) {
				event.setStatus(false);
			}

			market.setStatus(false);
			market.setIsactive(false);
			market.setIsResult(true);
			market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));

			marketResultRepository.save(result);
			eventMarketRepository.save(market);						
			seriesEventRepository.save(event);

		}



		ArrayList<EXUserProfitLoss> profitLossList = exprofitLossRepo.findByEventidAndResultset(result.getMatchid(), false);
		ArrayList<AppCharge> appcharge = appChargeRepo.findByIsresultAndEventid(false, result.getMatchid().toString());
		if(appcharge.size()>0){
			for(AppCharge bean :appcharge ){
				bean.setIsresult(true);
				appChargeRepo.save(bean);
			}
		}
		for(EXUserProfitLoss s : profitLossList){
			EXUser userbean =  userRepo.findByUserid(s.getUserid());
			if(userbean!=null ){
				if(userbean.getUsertype()!=3){
					if(s.getType().equalsIgnoreCase(EXConstants.PnL)){
						userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(BigDecimal.valueOf(s.getMoapp())));

					}
					userRepo.save(userbean);
				}

			}
			s.setResultset(true);
			exprofitLossRepo.save(s);
		}

		if(result!=null){

			
			Double adminpnl=0.0;
			Double subadminpnl=0.0;
			Double supermasterpnl=0.0;
			Double masterpnl=0.0;
			Double dealerpnl=0.0;
			Double userpnl=0.0;
			
			for(UserLiability lib : liblist){

				
				BigDecimal adminbalance = new BigDecimal(0);
				BigDecimal subadminbalance = new BigDecimal(0);
				BigDecimal supermasterbalance = new BigDecimal(0);
				BigDecimal masterbalance = new BigDecimal(0);
				BigDecimal dealerbalance = new BigDecimal(0);


				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;



				Double adminPartnership = 0.0;
				Double subadminPartnership = 0.0;
				Double supermasterPartnership = 0.0;
				Double masterpartnership = 0.0;
				Double dealerpartnership = 0.0;


				if(lib.getUsertype() == 3){
					admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
					subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
	                
	               
					if(lib.getIsactive()){

						ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
						Double usercommssionvalue =0.0;
						if(betlist.size()>0){
							for(ExMatchOddsBet bets :betlist){
	                            Double oldpnl = bets.getPnl();
								*//** if placebet selection id and result selection id is same then  *//*	
								if((result.getSelectionid() == bets.getSelectionid())) {

									bets.setOldpnl(oldpnl);
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
								}else{

									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								}
								bets.setIsactive(false);
								bets.setMatchstatus(EXConstants.result);
								bets.setResult(result.getSelectionid());
								bets.setResultid(result.getId());
								bets.setResultname(result.getSelectionname());
								bets.setStatus("CLOSE");
								if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									usercommssionvalue = bets.getSbaCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
									usercommssionvalue = bets.getDealCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
									usercommssionvalue = bets.getMastCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
									usercommssionvalue = bets.getDealCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									usercommssionvalue = bets.getSumCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									usercommssionvalue = bets.getMastCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
									usercommssionvalue = bets.getDealCommission();
								}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									usercommssionvalue = bets.getDealCommission();
								}
							}

							EXUser userbean = userRepo.findByUserid(lib.getUserid());
							if(userbean!=null){
								Double pnl =0.0;
								if(result.getSelectionid() == lib.getSelection1()){
									pnl = lib.getPnl1();
								}else if(result.getSelectionid() == lib.getSelection2()){
									pnl = lib.getPnl2();
								}else if(result.getSelectionid() == lib.getSelection3()){
									pnl = lib.getPnl3();
								}

	                            String type=null;
								if(pnl>0){
									type= "Dena";
									adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

								}else{
									type= "Lena";
									adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

								}
								Double adminCommission = 0.0;
								Double subadminCommission = 0.0;
								Double supermasterCommission = 0.0;
								Double masterCommission = 0.0;
								Double dealerCommission = 0.0;
								Double userCommission = 0.0;


								Client Commsiion and Match Profit 
								Double commssion=0.0;
								if(pnl>0.0){
									userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(lib.getLiability())));
									userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(pnl.toString())));
								}else{
									if(userbean.getOddsloss()>0.25){

										commssion =	Math.round((-pnl*(userCommission/100.0f))*100.0)/100.0;
										userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(commssion)));

									}
								}

								adminbalance = admin.getSecondrybanlce();
								subadminbalance = subadmin.getSecondrybanlce();
								
								EXLedger ledeger = new EXLedger();
								
								ledeger.setMarketid(result.getMarketid());
								ledeger.setMatchid(result.getMatchid());
								
								if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),(subadmin.getMobappcharge()-adminPartnership) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),(userbean.getMobappcharge()-pnl) ,subadmin.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (-pnl)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
									
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),(((subadmin.getMobappcharge()+adminPartnership)-adminCommission)) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),(userbean.getMobappcharge()-pnl-subadminCommission) ,subadmin.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										
										
									
									}
									
									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									 
									
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									dealerbalance = dealer.getSecondrybanlce();
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));



										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										dealerbalance = dealerbalance.add(BigDecimal.valueOf(subadminCommission));
										dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
										dealerCommission =-pnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));
										

										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										

									}
									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									dealer.setSecondrybanlce(dealerbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(dealer);
									

								}

								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									masterbalance =master.getSecondrybanlce();
									
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										masterbalance = masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
										masterCommission = -pnl*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										

									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									master.setSecondrybanlce(masterbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(master);
									
									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									masterbalance =master.getSecondrybanlce();
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									dealerbalance = dealer.getSecondrybanlce();
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
										dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),userCommission);
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										masterbalance = masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
										masterCommission = (adminPartnership+subadminPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										dealerbalance = dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
										dealerCommission = -pnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										

									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									master.setSecondrybanlce(masterbalance);
									dealer.setSecondrybanlce(dealerbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(master);
									userRepo.save(dealer);
									
								

									
									

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									supermasterbalance = supermaster.getSecondrybanlce();
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
										
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),(subadmin.getMobappcharge()-adminPartnership-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-supermasterCommission) ,supermaster.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										

									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										
										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
										supermasterCommission = -pnl*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-supermasterCommission) ,supermaster.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										

									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									supermaster.setSecondrybanlce(supermasterbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(supermaster);
									
								

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									supermasterbalance = supermaster.getSecondrybanlce();
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									dealerbalance = dealer.getSecondrybanlce();
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
										dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission) ,supermaster.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										dealerbalance = dealerbalance.add(BigDecimal.valueOf(supermasterCommission));
										dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
										dealerCommission = -pnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));


										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										
										
									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									supermaster.setSecondrybanlce(supermasterbalance);
									dealer.setSecondrybanlce(dealerbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(supermaster);
									userRepo.save(dealer);
									
								
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									supermasterbalance = supermaster.getSecondrybanlce();
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									masterbalance =master.getSecondrybanlce();
								
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
										masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
										
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										

									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));


										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance = masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
										masterCommission = -pnl*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										

									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									supermaster.setSecondrybanlce(supermasterbalance);
									master.setSecondrybanlce(masterbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(supermaster);
									userRepo.save(master);
									
									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									supermasterbalance = supermaster.getSecondrybanlce();
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									masterbalance =master.getSecondrybanlce();
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									dealerbalance = dealer.getSecondrybanlce();
									
									if(pnl>0.0){
										adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
										subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
										supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
										masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
										dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
										
										
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission),dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										
										
										
									}else{

										adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance = subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance = masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
										masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										dealerbalance = dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
										dealerCommission = -pnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
										ledger =saveLedger(result.getMarketid(), result.getMatchid(),((userbean.getMobappcharge()-(pnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
										

									}

									admin.setSecondrybanlce(adminbalance);
									subadmin.setSecondrybanlce(subadminbalance);
									supermaster.setSecondrybanlce(supermasterbalance);
									master.setSecondrybanlce(masterbalance);
									dealer.setSecondrybanlce(dealerbalance);
									userRepo.save(admin);
									userRepo.save(subadmin);
									userRepo.save(supermaster);
									userRepo.save(master);
									userRepo.save(dealer);
									
									
								}
								lib.setTotalpnl(pnl);
								lib.setNetpnl(pnl);
								lib.setOldpnl1(lib.getPnl1());
								lib.setOldpnl2(lib.getPnl2());
								lib.setOldpnl3(lib.getPnl3());
								lib.setOldlibility(lib.getLiability());
								lib.setIsresultset(true);
								lib.setUpdatedon(new Date());
								lib.setIsactive(false);
								
								userRepo.save(userbean);
							}
						}
					}
				}
				
			}

		}

		return "OK";


	}*/
	


	@Override
	@Transactional
	public String rollBackFancyResultsNew(ArrayList<UserLiability> liblist, String fancyid,Integer id)  {
		// TODO Auto-generated method stub

		 

		
		ExFancyResult result = fancyresultRepository.findByid(id);
		if(result!=null){
			
			
			for(UserLiability lib : liblist){

			

				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;



				
			

				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 

			
				if(!lib.getIsactive()){
                    Double userpnl =0.0;
                    Integer stack =0;
					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndMarketidAndIsactiveAndIsdeleted(lib.getUserid(), lib.getMarketid(), false,false);
					
					if(betlist.size()>0){
						for(ExMatchOddsBet bets :betlist){
						
							
							bets.setPnl(bets.getOldpnl());
							if((result.getResult()>= bets.getOdds())) {
								if(bets.getIsback()==true){
									bets.setNetpnl(0.0);
								}else{
									bets.setNetpnl(0.0);
									
								}

							}else {
				 				if(bets.getIsback()==true){

									bets.setNetpnl(0.0);
									

								}else{
									bets.setNetpnl(0.0);
								}

							}
							
							bets.setIsactive(true);
							bets.setSelectionid(0);
							bets.setResult(-1);
							bets.setResultid(0);
							bets.setStatus("OPEN");
							bets.setIsmongodbupdated(false);
							bets.setAddetoFirestore(false);
							stack =stack+bets.getStake();
							placebetrepository.save(bets);
							 
						}

						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						
						if(userbean!=null){
							
							userpnl = lib.getNetpnl();

							
							if(userbean.getHisabkitabtype() ==1) {
								nativeQueryDao.updateUserPnl(userbean.getId(), -userpnl,-userpnl,userbean.getHisabkitabtype());
								
							}else {
								nativeQueryDao.updateUserPnl(userbean.getId(), -userpnl,userpnl,userbean.getHisabkitabtype());
								
							}
							
							/*Client Commsiion and Match Profit */

							if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								
								nativeQueryDao.updateUserPnl(admin.getId(), -lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-(lib.getAdminPnl()),subadmin.getHisabkitabtype());
								

							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));							    	 
								
								nativeQueryDao.updateUserPnl(admin.getId(),-lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-(lib.getAdminPnl()),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()),supermaster.getHisabkitabtype());

								///System.out.print("Exception ==>"+(10/0));


							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));	
								
								nativeQueryDao.updateUserPnl(admin.getId(),-lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-(lib.getAdminPnl()),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()),supermaster.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(master.getId(), -lib.getMasterpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()+lib.getSupermasterpnl()),master.getHisabkitabtype());
							}
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
							
								nativeQueryDao.updateUserPnl(admin.getId(),-lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-(lib.getAdminPnl()),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()),supermaster.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(master.getId(), -lib.getMasterpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()+lib.getSupermasterpnl()),master.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(dealer.getId(), -lib.getDealerpnl(),-(lib.getAdminPnl()+lib.getSubadminpnl()+lib.getSupermasterpnl()+lib.getMasterpnl()),dealer.getHisabkitabtype());
							}
							lib.setTotalpnl(0.0);
							lib.setNetpnl(0.0);
							lib.setOldpnl1(0.0);
							lib.setOldpnl2(0.0);
							lib.setOldpnl3(0.0);
							lib.setPnl1(0.0);
							lib.setPnl2(0.0);
							lib.setPnl3(0.0);
							lib.setIsresultset(false);
							lib.setUpdatedon(new Date());
							lib.setIsactive(true);
							lib.setOldlibility(0.0);
							lib.setAdminPnl(0.0);
							lib.setSubadminpnl(0.0);
							lib.setSupermasterpnl(0.0);
							lib.setMasterpnl(0.0);
							lib.setDealerpnl(0.0);
							lib.setAddetoFirestore(false);
							lib.setIsmongodbupdated(false);
							lib.setIsProfitLossclear(false);
							
							liabilityRepository.save(lib);
						}
					}
				}
			}
			
		}
		
		 ledgerRepo.deleteByMarketidAndMatchid(fancyid,result.getMatchid());
		 
		 ArrayList<EXChipDetail> chipList = chipRepo.findByMarketIdAndMatchId(result.getFancyid(), result.getMatchid());
		 for(EXChipDetail chipd : chipList) {
			
			 HashMap<String, Object> hm = new HashMap<String, Object>();
			 hm.put("marketid", result.getFancyid());
			 hm.put("matchid", result.getMatchid());
			 hm.put("userid", chipd.getUserId());
			 hm.put("credit", chipd.getCredit());
			 hm.put("debit", chipd.getDebit());
			 hm.put("descreption", chipd.getDescreption());
			 hm.put("fromto", chipd.getFromTo());
			 hm.put("id", chipd.getId());
			 mysqlProcedureDao.chipRollBack(hm);
			   
		 }
		 
		    MatchFancy fancy = fancyRepository.findByFancyidAndEventid(fancyid, result.getMatchid());
			fancy.setStatus("OPEN");
			fancy.setIsActive(true);
			fancyRepository.save(fancy);
			fancyresultRepository.delete(result);
		return "OK";

	
	
	}
	

/*	@Override
	@Transactional
	public String setFancyResultsNew(ArrayList<UserLiability> liblist, ExFancyResult result) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub


		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		MatchFancy fancy = null;
		if(result!=null){
			if(fancyresultRepository.existsByfancyid(result.getFancyid()) == false) {
				fancy = fancyRepository.findByFancyid(result.getFancyid());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				result = fancyresultRepository.save(result);
				fancy.setStatus("CLOSE");
				fancy.setActive(0);
				fancyRepository.save(fancy);


			}
		}


		if(result!=null){

			for(UserLiability lib : liblist){

				BigDecimal adminbalance = new BigDecimal(0);
				BigDecimal subadminbalance = new BigDecimal(0);
				BigDecimal supermasterbalance = new BigDecimal(0);
				BigDecimal masterbalance = new BigDecimal(0);
				BigDecimal dealerbalance = new BigDecimal(0);


				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;



				Double adminPartnership = 0.0;
				Double subadminPartnership = 0.0;
				Double supermasterPartnership = 0.0;
				Double masterpartnership = 0.0;
				Double dealerpartnership = 0.0;
				
				Double adminPartnershipstack = 0.0;
				Double subadminPartnershipstack = 0.0;
				Double supermasterPartnershipstack = 0.0;
				Double masterpartnershipstack = 0.0;
				Double dealerpartnershipstack = 0.0;
				
				Double admprtnership =0.0;
				Double sbaprtnership =0.0;
				Double sumprtnership =0.0;
				Double masprtnership =0.0;
				Double dealprtnership =0.0;


				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 


				if(lib.getIsactive()){
                    Double userpnl =0.0;
                    Integer stack =0;
					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndFancyidAndIsactive(lib.getUserid(), lib.getFancyid(), true);
					Double usercommssionvalue =0.0;
					
					if(betlist.size()>0){
						for(ExMatchOddsBet bets :betlist){

							
							Double oldpnl = bets.getPnl();
							bets.setOldpnl(oldpnl);
							if((result.getResult()>= bets.getOdds())) {
								if(bets.getIsback()==true){

									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}else{
									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}

							}else {
								if(bets.getIsback()==true){

									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());

								}else{
									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}

							}
							
							bets.setIsactive(false);
							bets.setMatchstatus(EXConstants.result);
							bets.setSelectionid(1);
							bets.setResult(result.getResult());
							bets.setResultid(result.getId());
							bets.setStatus("CLOSE");
							userpnl = userpnl+bets.getNetpnl();
							stack =stack+bets.getStake();
							if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								usercommssionvalue = bets.getSbaCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
								usercommssionvalue = bets.getDealCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
								usercommssionvalue = bets.getMastCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
								usercommssionvalue = bets.getDealCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								usercommssionvalue = bets.getSumCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								usercommssionvalue = bets.getMastCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
								usercommssionvalue = bets.getDealCommission();
							}else if(bets.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								usercommssionvalue = bets.getDealCommission();
							}
							 admprtnership =bets.getAdmin();
							 sbaprtnership =bets.getSubadmin();
							 sumprtnership =bets.getSupermaster();
							 masprtnership =bets.getMaster();
							 dealprtnership =bets.getDealer();
						}

						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						
						userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(BigDecimal.valueOf(lib.getLiability())));
						if(userbean!=null){
							
							

                            String type =null;
							if(userpnl>0){
								type ="Dena";
								adminPartnership = Math.round((userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}else{
								type ="Lena";
								adminPartnership = Math.round((-userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}
							
							
							adminPartnershipstack = Math.round((stack*(admprtnership/100.0f))*100.0)/100.0;
							subadminPartnershipstack = Math.round((stack*(sbaprtnership/100.0f))*100.0)/100.0;
							supermasterPartnershipstack = Math.round((stack*(sumprtnership/100.0f))*100.0)/100.0;
							masterpartnershipstack = Math.round((stack*(masprtnership/100.0f))*100.0)/100.0;
							dealerpartnershipstack = Math.round((stack*(dealprtnership/100.0f))*100.0)/100.0;

							
							
							Double adminCommission = 0.0;
							Double subadminCommission = 0.0;
							Double supermasterCommission = 0.0;
							Double masterCommission = 0.0;
							Double dealerCommission = 0.0;
							
							Client Commsiion and Match Profit 

							if(userpnl>0.0){
								userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userpnl.toString())));
							}else{
								if(userbean.getOddsloss()>0.25){

									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										Double commssion = Math.round((-userpnl*(usercommssionvalue/100.0f))*100.0)/100.0;
										userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(commssion)));
	
									}
									
								}
								userbean.setSecondrybanlce(userbean.getSecondrybanlce().subtract(new BigDecimal(-userpnl)));
							}
							if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
								
								Double commssion = Math.round((stack*(usercommssionvalue/100.0f))*100.0)/100.0;
								userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(commssion)));
								
							}

							adminbalance = admin.getSecondrybanlce();
							subadminbalance = subadmin.getSecondrybanlce();
							
							
							
							if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = stack*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-userpnl)-subadminCommission) ,subadmin.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									
									
								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(-adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(-subadminPartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										adminCommission = -adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
                                       
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = -userpnl*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

									}
									 if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = stack*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-userpnl)-subadminCommission) ,subadmin.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									
								}
								
							
								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);

							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));							    	 
								dealerbalance = dealer.getSecondrybanlce();
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission =-(subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(subadminCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									
									
								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(-adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(-subadminPartnership));
									dealerbalance = dealerbalance.add(BigDecimal.valueOf(-dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(subadminCommission));
										dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));
									}
								
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission =-(subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(subadminCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									

								}
								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								dealer.setSecondrybanlce(dealerbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(dealer);

							}

							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));							    	 
								masterbalance = master.getSecondrybanlce();
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
										
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = stack*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
										
										
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									
									
								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = -userpnl*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

									}
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = stack*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									
									
								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								master.setSecondrybanlce(masterbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(master);

							}
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								masterbalance =master.getSecondrybanlce();
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
								dealerbalance = dealer.getSecondrybanlce();
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
									dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
										
										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									
								}else{

									
									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
									dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										
										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = (adminPartnership+subadminPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
										
										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

									}
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(subadminCommission));
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
										
										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									
									
								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								master.setSecondrybanlce(masterbalance);
								dealer.setSecondrybanlce(dealerbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(master);
								userRepo.save(dealer);

							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));							    	 
								supermasterbalance = supermaster.getSecondrybanlce();
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = stack*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));


									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),(subadmin.getMobappcharge()-adminPartnership-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-supermasterCommission) ,supermaster.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
								

								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = -userpnl*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));



									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));
										
										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = stack*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));


									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),(subadmin.getMobappcharge()+adminPartnership-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-supermasterCommission) ,supermaster.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
								
									
								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								supermaster.setSecondrybanlce(supermasterbalance);
								
								userRepo.save(admin);
								///System.out.print("Exception ==>"+(10/0));
								userRepo.save(subadmin);
								userRepo.save(supermaster);

							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								supermasterbalance = supermaster.getSecondrybanlce();
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));	

								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
									dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (subadminPartnershipstack+adminPartnershipstack+subadminPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(supermasterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission) ,supermaster.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
								
								}else{

									
									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (subadminPartnership+adminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(supermasterCommission));
										dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

										
									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));
										
										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (subadminPartnershipstack+adminPartnershipstack+subadminPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(supermasterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

									}
									
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission) ,supermaster.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission) ,dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
								
									

								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								supermaster.setSecondrybanlce(supermasterbalance);
								dealer.setSecondrybanlce(dealerbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(supermaster);
								userRepo.save(dealer);
							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								supermasterbalance = supermaster.getSecondrybanlce();
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));	
								masterbalance   = master.getSecondrybanlce();
								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
									masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = stack*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									

									
									

								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = -userpnl*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = stack*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));
									}
									

									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-masterCommission) ,master.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									
								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								supermaster.setSecondrybanlce(supermasterbalance);
								master.setSecondrybanlce(masterbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(supermaster);
								userRepo.save(master);
							}
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								supermasterbalance = supermaster.getSecondrybanlce();
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								masterbalance =master.getSecondrybanlce();
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
								dealerbalance = dealer.getSecondrybanlce();

								if(userpnl>0.0){
									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
									masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
									dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

										
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()-adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()-(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission),dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									
									
									
									
								}else{

									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
									dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = -adminPartnership*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masprtnership)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));


									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										adminbalance =adminbalance.subtract(BigDecimal.valueOf(adminCommission));

										subadminbalance =subadminbalance.add(BigDecimal.valueOf(adminCommission));
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										subadminbalance =subadminbalance.subtract(BigDecimal.valueOf(subadminCommission));

										supermasterbalance =supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										supermasterbalance =supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));

										masterbalance =masterbalance.add(BigDecimal.valueOf(supermasterCommission));
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										masterbalance =masterbalance.subtract(BigDecimal.valueOf(masterCommission));

										dealerbalance =dealerbalance.add(BigDecimal.valueOf(masterCommission));
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										dealerbalance =dealerbalance.subtract(BigDecimal.valueOf(dealerCommission));

										
									}
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((subadmin.getMobappcharge()+adminPartnership)-adminCommission) ,admin.getId(),subadmin.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),adminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((supermaster.getMobappcharge()+(adminPartnership+subadminPartnership))-subadminCommission) ,subadmin.getId(),supermaster.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),subadminCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((master.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission) ,supermaster.getId(),master.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),supermasterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((dealer.getMobappcharge()+(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-masterCommission) ,master.getId(),dealer.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),masterCommission);
									ledger= saveLedger(result.getFancyid(), result.getMatchid(),((userbean.getMobappcharge()-(userpnl))-dealerCommission),dealer.getId(),userbean.getId(),type,userbean.getUserid(),lib.getMatchname(),"match",lib.getType(),dealerCommission);
									
								}

								admin.setSecondrybanlce(adminbalance);
								subadmin.setSecondrybanlce(subadminbalance);
								supermaster.setSecondrybanlce(supermasterbalance);
								master.setSecondrybanlce(masterbalance);
								dealer.setSecondrybanlce(dealerbalance);
								
								userRepo.save(admin);
								userRepo.save(subadmin);
								userRepo.save(supermaster);
								userRepo.save(master);
								userRepo.save(dealer);
							}
							lib.setTotalpnl(userpnl);
							lib.setNetpnl(userpnl);
							lib.setOldpnl1(lib.getPnl1());
							lib.setOldpnl2(lib.getPnl2());
							lib.setOldpnl3(lib.getPnl3());
							lib.setPnl1(0.0);
							lib.setPnl2(0.0);
							lib.setPnl3(0.0);
							lib.setOldlibility(lib.getLiability());
							lib.setIsresultset(true);
							lib.setUpdatedon(new Date());
							lib.setIsactive(false);
							lib.setLiability(0.0);
							userRepo.save(userbean);
						}
					}
				}
			}

		}

		return "OK";


	
	}*/
	
	
	
	@Override	
	@Transactional
	public String setBetResultsWithoutCommssionAndMobApp(ArrayList<UserLiability> liblist,MarketResult result) {
		// TODO Auto-generated method stub


		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		MarketResult resultbean =null;
		Market market = null;
		Event event =null;
        Integer sportId =0;
        DecimalFormat df1 = new DecimalFormat("#0");

		resultbean =marketResultRepository.findByMarketid(result.getMarketid());
		if(resultbean == null) {



			market = eventMarketRepository.findBymarketid(result.getMarketid());
			sportId = market.getSportid();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.setDate(new Date());
			result.setStatus(true);
			result.setIsResult(true);
			result.setType("Result");
			
			market.setStatus(false);
			market.setIsactive(false);
			market.setIsResult(true);
			market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));

			marketResultRepository.save(result);
			eventMarketRepository.save(market);	
			if(market.getMarketname().equalsIgnoreCase(EXConstants.MatchOdds)){
				event = seriesEventRepository.findByeventid(result.getMatchid());
				
				if(event!=null) {
					event.setStatus(false);
				}
				seriesEventRepository.save(event);
			}
			

		}



		ArrayList<AppCharge> appcharge = appChargeRepo.findByIsresultAndEventid(false, result.getMatchid().toString());
		if(appcharge.size()>0){
			for(AppCharge bean :appcharge ){
				bean.setIsresult(true);
				appChargeRepo.save(bean);
			}
		}
	
		if(result!=null){

			
			
			
			

			Double adminpnl=0.0;
			Double subadminpnl=0.0;
			Double supermasterpnl=0.0;
			Double masterpnl=0.0;
			Double dealerpnl=0.0;
			
			
			Double subadminuplinepnl=0.0;
			Double supermasteruplinepnl=0.0;
			Double masteruplinepnl=0.0;
			Double dealeruplinepnl=0.0;
			
			for(UserLiability lib : liblist){

				
			
				
				

				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;



				Double adminPartnership = 0.0;
				Double subadminPartnership = 0.0;
				Double supermasterPartnership = 0.0;
				Double masterpartnership = 0.0;
				Double dealerpartnership = 0.0;


			
					admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
					subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
	                
	               
					if(lib.getIsactive()){

						ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
					   
						
							for(ExMatchOddsBet bets :betlist){
	                            Double oldpnl = bets.getPnl();
	                          
								/** if placebet selection id and result selection id is same then  */	
								if((result.getSelectionid() == bets.getSelectionid())) {

									
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
								}else{

									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								}
								bets.setIsactive(false);
								bets.setResult(result.getSelectionid());
								bets.setResultid(result.getId());
								bets.setResultname(result.getSelectionname());
								bets.setStatus("CLOSE");
								bets.setOldpnl(oldpnl);
								bets.setAddetoFirestore(false);
							}

							EXUser userbean = userRepo.findByUserid(lib.getUserid());
							
							if(userbean!=null){
								Double pnl =0.0;
								if(result.getSelectionid() == lib.getSelection1()){
									pnl = lib.getPnl1();
								}else if(result.getSelectionid() == lib.getSelection2()){
									pnl = lib.getPnl2();
								}else if(result.getSelectionid() == lib.getSelection3()){
									pnl = lib.getPnl3();
								}

	                            String type=null;
								if(pnl>0){
									type= "Dena";
									adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

								}else{
									type= "Lena";
									adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

								}
								Double adminCommission = (double) 0;
								Double subadminCommission = (double) 0;
								Double supermasterCommission = (double) 0;
								Double masterCommission = (double) 0;
								Double dealerCommission = (double) 0;
								Double userCommission = (double) 0;
								HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
								
								/*Client Commsiion and Match Profit */
								
								if(pnl>0.0){
									//userbal=userbal.add(new BigDecimal(lib.getLiability()));
								//	userbal =userbal.add(new BigDecimal(pnl.toString()));
									
								}else{
									if(userbean.getOddsloss().doubleValue()>0.25){

										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)){
											userCommission =	Math.round((-pnl*(userbean.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
												
										}
										
									}
									
									//userbal=userbal.add(new BigDecimal(lib.getLiability()));
									//userbal =userbal.add(new BigDecimal(pnl.toString()));
									  hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
										 
								}
								//nativeQueryDao.updateUserAvailableBalance(userbean.getId(), userbal);
								
								
								EXLedger ledeger = new EXLedger();
								
								ledeger.setMarketid(result.getMarketid());
								ledeger.setMatchid(result.getMatchid());
								
							   
							   
							    
							   
							   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							   hm.put("descreption", market.getMatchname()+"/"+market.getMarketname()+"["+result.getSelectionname()+"]");hm.put("sportId", sportId);hm.put("marketname", market.getMarketname());
							   hm.put("byUserId", lib.getUserid());

							   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
							   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
							   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
							   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
							   mysqlProcedureDao.updateChipDetailMatchResult(hm);
								
							   	if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl =-subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										
										
										
										hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
									
									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (-pnl)*lib.getSbaCommission()/100.0f;
											
										}
										
										adminpnl = adminPartnership;
										subadminpnl =subadminPartnership;
										subadminuplinepnl=adminPartnership;
										
										
										hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
									}
									
								
									 
									
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = -pnl*lib.getSumCommission()/100.0f;
											
									}
										
									
										adminpnl = adminPartnership;
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl =((adminPartnership+subadminPartnership));
										hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
									}

								

								}/*else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									supermasterbalance = supermaster.getSecondrybanlce();
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									dealerbalance = dealer.getSecondrybanlce();
									if(pnl>0.0){
										
										
										adminpnl = mobappcharge.getSubamintoadminamount()-adminPartnership;
										subadminpnl = (mobappcharge.getSupermastertosubadminamount()-mobappcharge.getSubamintoadminamount())-subadminPartnership;
										subadminuplinepnl=mobappcharge.getSubamintoadminamount()-adminPartnership-adminCommission;
										supermasterpnl = (mobappcharge.getDelalertosupermasteramount()-mobappcharge.getSupermastertosubadminamount())-supermasterPartnership;
										supermasteruplinepnl=mobappcharge.getSupermastertosubadminamount()-(adminPartnership+subadminPartnership)-subadminCommission;
										dealerpnl = (mobappcharge.getMobappcharge()-mobappcharge.getDelalertosupermasteramount())-dealerpartnership;
										dealeruplinepnl=(mobappcharge.getDelalertosupermasteramount()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission;
									
										
										hm.put("chip", (mobappcharge.getSubamintoadminamount()-adminPartnership-adminCommission));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", mobappcharge.getSubamintoadminamount());hm.put("uplineamount", 0.0);hm.put("downlineamount", ((mobappcharge.getSupermastertosubadminamount()-(adminPartnership+subadminPartnership))-subadminCommission));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl);
										
										
										hm.put("chip", ((mobappcharge.getSupermastertosubadminamount()-(adminPartnership+subadminPartnership))-subadminCommission));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", mobappcharge.getMastertosubadminamount());hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((mobappcharge.getDelalertosupermasteramount()-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());
										mysqlProcedureDao.updateChipDetailMatchResult(hm);hm.put("pnl", subadminpnl);
										lib.setSubadminpnl(subadminpnl);
										
										hm.put("chip", ((mobappcharge.getDelalertosupermasteramount()-(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission));hm.put("userid", supermaster.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", mobappcharge.getDelalertosupermasteramount());hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((mobappcharge.getMobappcharge()-(pnl))-dealerCommission));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl);
										
										
										hm.put("chip", ((mobappcharge.getMobappcharge()-(pnl))-dealerCommission));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", dealerCommission);hm.put("mobapp", mobappcharge.getMobappcharge());hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(dealerpnl);
										
										
									}else{
										
											
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
										supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
										dealerCommission = -pnl*lib.getDealCommission()/100.0f;
										
										
										adminpnl = (mobappcharge.getSubamintoadminamount()+adminPartnership)-adminCommission;
										subadminpnl = ((mobappcharge.getSupermastertosubadminamount()-mobappcharge.getSubamintoadminamount())+subadminPartnership)-subadminCommission;
										subadminuplinepnl =(mobappcharge.getSubamintoadminamount()+adminPartnership)-adminCommission;
										supermasterpnl = ((mobappcharge.getDelalertosupermasteramount()-mobappcharge.getSupermastertosubadminamount())+supermasterPartnership)-supermasterCommission;
										supermasteruplinepnl =(mobappcharge.getSupermastertosubadminamount()+adminPartnership+subadminPartnership)-subadminCommission;
										dealerpnl = ((mobappcharge.getMobappcharge()-mobappcharge.getDelalertosupermasteramount())+dealerpartnership)-dealerCommission;
										dealeruplinepnl = (mobappcharge.getDelalertosupermasteramount()+(adminPartnership+subadminPartnership+supermasterPartnership))-supermasterCommission;
											
									
										hm.put("chip", ((mobappcharge.getSubamintoadminamount()+adminPartnership)-adminCommission));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", mobappcharge.getSubamintoadminamount());hm.put("uplineamount", 0.0);hm.put("downlineamount", ((mobappcharge.getSupermastertosubadminamount()+(adminPartnership+subadminPartnership))-subadminCommission));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);hm.put("upline", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl);
										
										
										hm.put("chip", ((mobappcharge.getSupermastertosubadminamount()+(adminPartnership+subadminPartnership))-subadminCommission));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", mobappcharge.getSupermastertosubadminamount());hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", ((mobappcharge.getDelalertosupermasteramount()+(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);hm.put("upline", adminpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl);
										
										
										
										hm.put("chip", ((mobappcharge.getDelalertosupermasteramount()+(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))-supermasterCommission));hm.put("userid", supermaster.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", mobappcharge.getDelalertosupermasteramount());hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((mobappcharge.getMobappcharge()-(pnl))-dealerCommission));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);hm.put("upline", subadminpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(supermasterpnl);
										
										
										
										hm.put("chip", ((mobappcharge.getMobappcharge()-(pnl))-dealerCommission));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", dealerCommission);hm.put("mobapp", mobappcharge.getMobappcharge());hm.put("uplineamount",dealeruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);hm.put("upline", supermasterpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(dealerpnl);
										
									}

									
									
								
									
								}*/else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
										master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = -pnl*lib.getMastCommission()/100.0f;
											
										}
										
										
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl = masterpartnership;
										masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										

									}

									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =(-adminPartnership);
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl = -dealerpartnership;
										dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
										
										
										
									}else{
										
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
											dealerCommission = -pnl*lib.getDealCommission()/100.0f;
											
										}
										
										adminpnl = (adminPartnership);
										subadminpnl = (subadminPartnership);
										subadminuplinepnl =(adminPartnership);
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl=(adminPartnership+subadminPartnership);
										masterpnl = (masterpartnership);
										masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
										
										dealerpnl = dealerpartnership;
										dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										
										hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										masterpnl = (masterpartnership);
										masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
		 								hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
										
									}
									
								} 
							   	
								lib.setTotalpnl(pnl+userCommission);
								lib.setNetpnl(pnl);
								lib.setOldpnl1(lib.getPnl1());
								lib.setOldpnl2(lib.getPnl2());
								lib.setOldpnl3(lib.getPnl3());
								lib.setOldlibility(lib.getLiability());
								lib.setIsresultset(true);
								lib.setUpdatedon(new Date());
								lib.setIsactive(false);
								liabilityRepository.save(lib);
							}
						
					}
				
				
			}

		}
	
		return "OK";


	}
	
	@Override
	@Transactional
	public String setFancyResultsWithoutCommssionAndMobApp(ArrayList<UserLiability> liblist, ExFancyResult result)  {
	
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		MatchFancy fancy = null;
		if(result!=null){
		
			Double admintotalpnl =0.0;
			Double subadmintotalpnl =0.0;
			Double supermastertotalpnl =0.0;
			Double mastertotalpnl =0.0;
			Double dealertotalpnl =0.0;
			for(UserLiability lib : liblist){
			
				
				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;



				Double adminPartnership = 0.0;
				Double subadminPartnership = 0.0;
				Double supermasterPartnership = 0.0;
				Double masterpartnership = 0.0;
				Double dealerpartnership = 0.0;
				
				Double adminPartnershipstack = 0.0;
				Double subadminPartnershipstack = 0.0;
				Double supermasterPartnershipstack = 0.0;
				Double masterpartnershipstack = 0.0;
				Double dealerpartnershipstack = 0.0;
				
				Double admprtnership =0.0;
				Double sbaprtnership =0.0;
				Double sumprtnership =0.0;
				Double masprtnership =0.0;
				Double dealprtnership =0.0;
				
				Double adminpnl=0.0;
				Double subadminpnl=0.0;
				Double supermasterpnl=0.0;
				Double masterpnl=0.0;
				Double dealerpnl=0.0;
				
				Double adminuplinepnl=0.0;
				Double subadminuplinepnl=0.0;
				Double supermasteruplinepnl=0.0;
				Double masteruplinepnl=0.0;
				Double dealeruplinepnl=0.0;
				
				
				
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
				if(lib.getIsactive()){
                    Double userpnl =0.0;
                    Integer stack =0;
                    
                   
                    
					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(lib.getUserid(), lib.getMarketid(),EXConstants.Fancy, true,lib.getMatchid());
					
					if(betlist.size()>0){
						for(ExMatchOddsBet bets :betlist){

							
							Double oldpnl = bets.getPnl();
							bets.setOldpnl(oldpnl);
							if((result.getResult()>= bets.getOdds())) {
								if(bets.getIsback()==true){

									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}else{
									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}

							}else {
								if(bets.getIsback()==true){

									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());

								}else{
									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}

							}
							
							bets.setIsactive(false);
							bets.setSelectionid(1);
							bets.setResult(result.getResult());
							bets.setResultid(result.getId());
							bets.setStatus("CLOSE");
							userpnl = userpnl+bets.getNetpnl();
							stack =stack+bets.getStake();
							   EXUser usr = userRepo.findByUserid(bets.getUserid());
							 admprtnership =usr.getAdminpartership().doubleValue();
							 sbaprtnership =usr.getSubadminpartnership().doubleValue();
							 sumprtnership =usr.getSupermastepartnership().doubleValue();
							 masprtnership =usr.getMasterpartership().doubleValue();
							 dealprtnership =usr.getDelearpartership().doubleValue();
						}
					}
				
					
				
					EXUser userbean = userRepo.findByUserid(lib.getUserid());
					Double userCommission = 0.0;
					
				  //  userbal =userbal.add(BigDecimal.valueOf(lib.getLiability()));
						if(userbean!=null){
							  String type =null;
								if(userpnl>0){
									type ="Dena";
									adminPartnership = Math.round((userpnl*(admprtnership/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((userpnl*( sumprtnership/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((userpnl*(masprtnership/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((userpnl*(dealprtnership/100.0f))*100.0)/100.0;

								}else{
									type ="Lena";
									adminPartnership = Math.round((-userpnl*(admprtnership/100.0f))*100.0)/100.0;
									subadminPartnership = Math.round((-userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
									supermasterPartnership = Math.round((-userpnl*( sumprtnership/100.0f))*100.0)/100.0;
									masterpartnership = Math.round((-userpnl*(masprtnership/100.0f))*100.0)/100.0;
									dealerpartnership = Math.round((-userpnl*(dealprtnership/100.0f))*100.0)/100.0;

								}
								adminPartnershipstack = Math.round((stack*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnershipstack = Math.round((stack*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnershipstack = Math.round((stack*(sumprtnership/100.0f))*100.0)/100.0;
								masterpartnershipstack = Math.round((stack*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnershipstack = Math.round((stack*(dealprtnership/100.0f))*100.0)/100.0;

								
								
								Double adminCommission = 0.0;
								Double subadminCommission = 0.0;
								Double supermasterCommission = 0.0;
								Double masterCommission = 0.0;
								Double dealerCommission = 0.0;
								
								
								if(userpnl>0.0){
								
									
								}else{
									if(userbean.getOddsloss().doubleValue()>0.25){

										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
											userCommission = Math.round((-userpnl*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
											
										}
										
									}
									
								}
								if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
									
									 userCommission = Math.round((stack*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
									
								}
								
								
								
							//	nativeQueryDao.updateUserAvailableBalance(userbean.getId(), userbal);
								//System.out.println(Integer.parseInt(null));
								
							
								HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
								
								hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
								hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
								hm.put("descreption", lib.getMatchname()+"/"+result.getFancyname()+"["+result.getResult()+"]");hm.put("sportId",lib.getSportid());hm.put("marketname", result.getFancyname());
								hm.put("byUserId", lib.getUserid());

								hm.put("chip", userpnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
								hm.put("commssion", 0);hm.put("mobapp",0);hm.put("uplineamount", userpnl);hm.put("downlineamount", 0.0);
								hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", userpnl);
								hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline"+"["+result.getResult()+"]");
								hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
								mysqlProcedureDao.updateChipDetailMatchResult(hm);
								
			 					
								if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(userpnl>0.0){
									
									
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											subadminCommission = stack*lib.getSbaCommission()/100.0f;
											
										}
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (userpnl));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", -(adminPartnership));hm.put("fromto", lib.getUserid());
										hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										lib.setAdminPnl(-adminPartnership-adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp",0);hm.put("uplineamount",-adminPartnership );hm.put("downlineamount", 0.0);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", -(subadminPartnership));hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										lib.setSubadminpnl(-subadminPartnership-((subadminCommission-adminCommission)));
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										//System.out.println(Integer.parseInt(null));
										
									}else{
								
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
											
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = -userpnl*lib.getSbaCommission()/100.0f;
											
										}
										 if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											subadminCommission = stack*lib.getSbaCommission()/100.0f;
											
										}
										
										 hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										 hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-userpnl));
										 hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminPartnership));
										 hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										 lib.setAdminPnl(adminPartnership-adminCommission);

										 mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										 hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										 hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", adminPartnership);hm.put("downlineamount", 0.0);
										 hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminPartnership));
										 hm.put("fromto", subadmin.getUserid()+" To "+"Downline");hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										 lib.setSubadminpnl(subadminPartnership-((subadminCommission-adminCommission)));
										 mysqlProcedureDao.updateChipDetailMatchResult(hm);
										 
									}
									
								
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));							    	 
								
									if(userpnl>0.0){
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = stack*lib.getSumCommission()/100.0f;


										}
										
										adminpnl =-adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
										hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl-adminCommission);
										
										hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										
										hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",0.0);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl)); hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));

									}else{
										
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
											
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = -userpnl*lib.getSumCommission()/100.0f;



										}
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										
											subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
											supermasterCommission = stack*lib.getSumCommission()/100.0f;


										}

										adminpnl =adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										
										
										
										hm.put("chip", adminPartnership);hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (adminPartnership+subadminPartnership));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl-adminCommission);
										
										
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (adminPartnership+subadminPartnership+supermasterPartnership));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", userpnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));	
									
									}
									admintotalpnl = admintotalpnl+adminPartnership;
									subadmintotalpnl = subadmintotalpnl+subadminPartnership;
									supermastertotalpnl = supermastertotalpnl+supermasterPartnership;


								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));	
									
									if(userpnl>0.0){
									
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = stack*lib.getMastCommission()/100.0f;
										}
									
										adminpnl =-adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl-adminCommission);
										
										hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(supermasterpnl)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										
										hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(masterpnl));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
										
										
										hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(userpnl));
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
										

									}else{
									
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
											
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											
											masterCommission = -userpnl*lib.getMastCommission()/100.0f;
										
										}
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = stack*lib.getMastCommission()/100.0f;
										}
										
										adminpnl = adminPartnership;
										subadminpnl =subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl =masterpartnership;
										masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl-adminCommission);
										
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										
										hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
										
										
										hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",0.0);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
										
									}

								
									admintotalpnl = admintotalpnl+adminPartnership;
									subadmintotalpnl = subadmintotalpnl+subadminPartnership;
									supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
									mastertotalpnl = mastertotalpnl+masterpartnership;
									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									if(userpnl>0.0){
										
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										
											dealerCommission = stack*lib.getDealCommission()/100.0f;
										
											
										}
										
										adminpnl =-adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl =-dealerpartnership;
										dealeruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(adminpnl-adminCommission);
										
										hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										
										hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
										
										
										hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
										
										hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", dealerCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
										
									}else{
										
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
											
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masprtnership)*lib.getMastCommission()/100.0f;
											
											dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
											

										}
										if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
											
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;

											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
											
											dealerCommission = stack*lib.getDealCommission()/100.0f;
											
											
										}
										adminpnl =adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl = masterpartnership;
										masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl =dealerpartnership;
										dealeruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
										
										
										
										
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm); 
										lib.setAdminPnl(adminpnl-adminCommission);
										
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
										
										
										
										hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
										
										
										hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
										
										
										hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
										
										
									}
									admintotalpnl = admintotalpnl+adminPartnership;
									subadmintotalpnl = subadmintotalpnl+subadminPartnership;
									supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
									mastertotalpnl = mastertotalpnl+masterpartnership;
									dealerpartnership =dealertotalpnl+dealerpartnership;
								
								}
								
								
								
								
							
						}
						lib.setTotalpnl(userpnl+userCommission);
						lib.setNetpnl(userpnl +userCommission);
						lib.setOldpnl1(lib.getPnl1());
						lib.setOldpnl2(lib.getPnl2());
						lib.setOldpnl3(lib.getPnl3());
						lib.setPnl1(0.0);
						lib.setPnl2(0.0);
						lib.setPnl3(0.0);
						lib.setOldlibility(lib.getLiability());
						lib.setIsresultset(true);
						lib.setUpdatedon(new Date());
		 				lib.setIsactive(false);
						userRepo.save(userbean);
						liabilityRepository.save(lib);
				}
				
			}
			
				
	 				fancy = fancyRepository.findByFancyidAndEventid(result.getFancyid(), result.getMatchid());
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					result = fancyresultRepository.save(result);
					fancy.setStatus("CLOSE");
					fancy.setIsActive(false);
					fancyRepository.save(fancy);
				
				
 				
				
			
			
		}
       	
		
		return "ok";
	}
	
	@Override
	@Transactional
	public String rollBackBetResultsNewWithoutCommssionMobCommssion(ArrayList<UserLiability> liblist,MarketResult result) {
		// TODO Auto-generated method stub


		

		ResponseBean responseBean = new ResponseBean();
		DecimalFormat df = new DecimalFormat("#0.00");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		String marketid= result.getMarketid();
		Market market = null;
		Event event =null;

		
		market = eventMarketRepository.findBymarketid(result.getMarketid());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
		
		market.setStatus(true);
		market.setIsactive(true);
		market.setIsResult(false);
		market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
		eventMarketRepository.save(market);						

		
		ArrayList<Market> mkt = eventMarketRepository.findByEventid(result.getMatchid());
		
		if(mkt.size()>1) {
			if(market.getMarketname().equalsIgnoreCase(EXConstants.MatchOdds)){
				event = seriesEventRepository.findByeventid(result.getMatchid());
				if(event!=null) {
					event.setStatus(true);
				}

				
				event = seriesEventRepository.save(event);
			}
		}else {
			event = seriesEventRepository.findByeventid(result.getMatchid());
			if(event!=null) {
				event.setStatus(true);
			}

			
			event = seriesEventRepository.save(event);
		}
		
		
		
		

		MarketResult marketresult = marketResultRepository.findByid(result.getId());
	
		if(marketresult == null) {

			
			ArrayList<AppCharge> appcharge = appChargeRepo.findByIsresultAndEventid(true, result.getMatchid().toString());
			if(appcharge.size()>0){
				for(AppCharge bean :appcharge ){
					bean.setIsresult(false);
					appChargeRepo.save(bean);
				}
			}
			
      }
	
		
	

		Integer i=1;
		if(result!=null){
			
			for(UserLiability lib : liblist){
				
				System.out.println(i);
				i++;
				BigDecimal adminbalance = new BigDecimal(0);
				BigDecimal subadminbalance = new BigDecimal(0);
				BigDecimal supermasterbalance = new BigDecimal(0);
				BigDecimal masterbalance = new BigDecimal(0);
				BigDecimal dealerbalance = new BigDecimal(0);

				EXUser master = null;
				EXUser dealer = null;
				EXUser admin = null;
				EXUser subadmin = null;
				EXUser supermaster = null;
				
				Double adminPartnership = 0.0;
				Double subadminPartnership = 0.0;
				Double supermasterPartnership = 0.0;
				Double masterpartnership = 0.0;
				Double dealerpartnership = 0.0;
				
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
				
				if(!lib.getIsactive()){
					
					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndMarketidAndIsactiveAndIsdeleted(lib.getUserid(),lib.getMarketid(),false,false);
					
					if(betlist.size()>0){
						for(ExMatchOddsBet bets :betlist){

							if(!bets.getIsdeleted()) {
								Double pnlprev = bets.getOldpnl();
								bets.setPnl(pnlprev);
								if((result.getResult()>= bets.getOdds())) {
									if(bets.getIsback()==true){

										bets.setNetpnl(0.0);
									}else{
										bets.setNetpnl(0.0);
									}

								}else {
									if(bets.getIsback()==true){

										bets.setNetpnl(0.0);
									
									}else{
										bets.setNetpnl(0.0);
									}

								}
								
								

								bets.setIsactive(true);
								bets.setResult(0);
								bets.setResultid(0);
								bets.setResultname("");
								bets.setStatus("OPEN");
								bets.setAddetoFirestore(false);
								bets.setIsmongodbupdated(false);
								placebetrepository.save(bets);	
							}
							
						}
						
						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						if(userbean!=null){
							Double pnl =0.0;
							pnl = lib.getTotalpnl();
							
							if(pnl>0){
								adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;
								
							}else{
								adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;
								
							}
							
						
							BigDecimal userbal = new BigDecimal("0.0");
							
	/*Client Commsiion and Match Profit */
							
							
							if(pnl>0.0){
							//	userbal=userbal.subtract(new BigDecimal(lib.getLiability()));
								
							}else{
								if(userbean.getOddsloss().doubleValue()>0.25){
								
									Double commssion= Math.round((-pnl*(userbean.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
									
								}
								//  userbal =userbal.subtract(new BigDecimal(lib.getLiability()));
							
							}	
						//	nativeQueryDao.updateUserAvailableBalance(userbean.getId(), userbal);
							if(userbean.getHisabkitabtype() ==1) {
								nativeQueryDao.updateUserPnl(userbean.getId(), -pnl,-pnl,userbean.getHisabkitabtype());
								
							}else {
								nativeQueryDao.updateUserPnl(userbean.getId(), -pnl,pnl,userbean.getHisabkitabtype());
								
							}
							if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								if(pnl>0.0){
									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
								}else{
									
									adminbalance =    adminbalance.subtract(BigDecimal.valueOf(adminPartnership));
									
									
									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									
								}
								nativeQueryDao.updateUserPnl(admin.getId(), -lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-lib.getAdminPnl(),subadmin.getHisabkitabtype());
								
							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								if(pnl>0.0){
									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									
								}else{

									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));

									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));

									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
								
								}
								nativeQueryDao.updateUserPnl(admin.getId(), -lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-lib.getAdminPnl(),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()),subadmin.getHisabkitabtype());
								
							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
							
								nativeQueryDao.updateUserPnl(admin.getId(), -lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-lib.getAdminPnl(),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()),supermaster.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(master.getId(), -lib.getMasterpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()+lib.getSupermasterpnl()),master.getHisabkitabtype());
								
							}
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									if(pnl>0.0){
									adminbalance = adminbalance.add(BigDecimal.valueOf(adminPartnership));
									subadminbalance = subadminbalance.add(BigDecimal.valueOf(subadminPartnership));
									supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(supermasterPartnership));
									masterbalance = masterbalance.add(BigDecimal.valueOf(masterpartnership));
									dealerbalance = dealerbalance.add(BigDecimal.valueOf(dealerpartnership));
								}else{ 

									adminbalance = adminbalance.subtract(BigDecimal.valueOf(adminPartnership));

									subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(subadminPartnership));
									
									supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterPartnership));
									
									masterbalance = masterbalance.subtract(BigDecimal.valueOf(masterpartnership));
									
									dealerbalance = dealerbalance.subtract(BigDecimal.valueOf(dealerpartnership));


								}

								nativeQueryDao.updateUserPnl(admin.getId(), -lib.getAdminPnl(),0.0,admin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(subadmin.getId(), -lib.getSubadminpnl(),-lib.getAdminPnl(),subadmin.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(supermaster.getId(), -lib.getSupermasterpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()),supermaster.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(master.getId(), -lib.getMasterpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()+lib.getSupermasterpnl()),master.getHisabkitabtype());
								nativeQueryDao.updateUserPnl(dealer.getId(), -lib.getDealerpnl(),-(lib.getSubadminpnl()+lib.getAdminPnl()+lib.getSupermasterpnl()+lib.getMasterpnl()),dealer.getHisabkitabtype());
								
							}
							lib.setTotalpnl(0.0);
							lib.setNetpnl(0.0);
							lib.setOldpnl1(0.0);
							lib.setOldpnl2(0.0);
							lib.setOldpnl3(0.0);
							lib.setLiability(lib.getOldlibility());
							lib.setIsresultset(false);
							lib.setIsactive(true);
							lib.setOldlibility(0.0);
							lib.setAddetoFirestore(false);
							lib.setIsmongodbupdated(false);
							userRepo.save(userbean);
							liabilityRepository.save(lib);
						}
						
						
					}
				}
			}
			ledgerRepo.deleteByMarketid(marketid);
			chipRepoMongo.deleteBymarketId(marketid);
			ArrayList<EXChipDetail> chipList = chipRepo.findByMarketIdAndMatchId(marketid, result.getMatchid());
			 for(EXChipDetail chipd : chipList) {
				
				 System.out.println(chipd.getUserId());
				 if(!chipd.getUserId().equalsIgnoreCase("mango")) {
					 HashMap<String, Object> hm = new HashMap<String, Object>();
					 hm.put("marketid", marketid);
					 hm.put("matchid", result.getMatchid());
					 hm.put("userid", chipd.getUserId());
					 mysqlProcedureDao.chipRollBack(hm);
				 }
				
				   
			 }
			marketResultRepository.deleteByMarketid(marketid);
			
			
			
		}

		return "OK";


	}
	
	
	@Override
	@Transactional
	public String rollBackBetResultsNew(ArrayList<UserLiability> liblist,MarketResult result) {
		return date1;
		
	}
	
	public EXLedger  saveLedger(String marketid, Integer matchid, Double amount,Integer parentid,Integer childid,String type,String userid,String matchname,String collectionname,String markettype,Double commssion,Integer mobapp,Double uplineamount,Double downlineamount){
		
		EXLedger ledger = new EXLedger();
		 ledger.setAmount(amount);
		 ledger.setChildid(childid);
		 ledger.setMarketid(marketid);
		 ledger.setMatchid(matchid);
		 ledger.setParentid(parentid);
		 ledger.setType(type);
		 ledger.setUserid(userid);
		 ledger.setCreatedon(new Date());
		 ledger.setMatchname(matchname);
		 ledger.setCollectionname(collectionname);
		 ledger.setMarkettype(markettype);
		 ledger.setCommssion(commssion);
		 ledger.setMobapp(mobapp);
		 ledger.setUplineamount(uplineamount);
		 ledger.setDownlineamount(downlineamount);
		 ledgerRepo.save(ledger);
		  return ledger;
	}
	
public EXLedger  saveLedgerNew(HashMap<String,Object> hm){
		
		EXLedger ledger = new EXLedger();
		 ledger.setAmount(new Double(hm.get("chip").toString()));
		 ledger.setChildid(Integer.parseInt(hm.get("childid").toString()));
		 ledger.setMarketid(hm.get("marketid").toString());
		 ledger.setMatchid(Integer.parseInt(hm.get("matchid").toString()));
		 ledger.setParentid(Integer.parseInt(hm.get("parentid").toString()));
		 ledger.setType(hm.get("lenadena").toString());
		 ledger.setUserid(hm.get("clientuserid").toString());
		 ledger.setCreatedon(new Date());
		 ledger.setMatchname(hm.get("matchname").toString());
		 ledger.setCollectionname(hm.get("collectionname").toString());
		 ledger.setMarkettype(hm.get("markettype").toString());
		 ledger.setCommssion(new Double(hm.get("commssion").toString()));
		 ledger.setMobapp(Integer.parseInt(hm.get("mobapp").toString()));
		 ledger.setUplineamount(new Double(hm.get("uplineamount").toString()));
		 ledger.setDownlineamount(new Double(hm.get("downlineamount").toString()));
		 ledgerRepo.save(ledger);
		 
		  return ledger;
	}

   public EXChipDetail  saveLedgerNew1(HashMap<String,Object> hm){
	
	
	 EXChipDetail chipdtl = new EXChipDetail();
	 chipdtl.setClosing(new BigDecimal("0.0"));
	 if(new Double(hm.get("chip").toString())>0){
		 chipdtl.setCredit(new Double(hm.get("chip").toString())); 
	 }else{
		 chipdtl.setDebit(new Double(hm.get("chip").toString()));  
	 }
	 chipdtl.setParentId(Integer.parseInt(hm.get("parentid").toString()));
	 chipdtl.setDescreption(hm.get("descreption").toString());
	 chipdtl.setFromTo(hm.get("fromto").toString());
	 chipdtl.setMarketId(hm.get("marketid").toString());
	 chipdtl.setMatchId(Integer.parseInt(hm.get("matchid").toString()));
	 chipdtl.setType(hm.get("type").toString());
	 chipdtl.setCreatedOn(new Date());
	 exchipRepo.save(chipdtl);
	
	
	 
	  return chipdtl;
}




@Override
public String setFancyResultsCron(ArrayList<UserLiability> liblist, ExFancyResult result) {
	
	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	if(result!=null){
	
		Double admintotalpnl =0.0;
		Double subadmintotalpnl =0.0;
		Double supermastertotalpnl =0.0;
		Double mastertotalpnl =0.0;
		Double dealertotalpnl =0.0;
		
		MatchFancy fncy =fancyRepository.findByFancyid(result.getFancyid());
		for(UserLiability lib : liblist){
		
			
			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;
			
			Double adminPartnershipstack = 0.0;
			Double subadminPartnershipstack = 0.0;
			Double supermasterPartnershipstack = 0.0;
			Double masterpartnershipstack = 0.0;
			Double dealerpartnershipstack = 0.0;
			
			Double admprtnership =0.0;
			Double sbaprtnership =0.0;
			Double sumprtnership =0.0;
			Double masprtnership =0.0;
			Double dealprtnership =0.0;
			
			Double adminpnl=0.0;
			Double subadminpnl=0.0;
			Double supermasterpnl=0.0;
			Double masterpnl=0.0;
			Double dealerpnl=0.0;
			
			
			Double subadminuplinepnl=0.0;
			Double supermasteruplinepnl=0.0;
			Double masteruplinepnl=0.0;
			Double dealeruplinepnl=0.0;
			
			
			
			admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
			subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
			if(lib.getIsactive()){
                Double userpnl =0.0;
                Integer stack =0;
                
              
				ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndMarketidAndIsactiveAndIsdeleted(lib.getUserid(), lib.getMarketid(), true,false);
				
				if(betlist.size()>0){
					for(ExMatchOddsBet bets :betlist){

						
						Double oldpnl = bets.getPnl();
						bets.setOldpnl(oldpnl);
						
						/*if(result.getResult()>2001) {
							
							
								if(result.getResult()>2001) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
								}else {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}
								}
								
							}*/
						   
					
						 if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market) || fncy.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
							 if((result.getResult()>= (bets.getOdds()))) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}

								}else {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
						 }else  if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
							 if(result.getResult() == 1) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
							 	}else  if(result.getResult() == 0) {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
							
						 }else  if(fncy.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
							 if(result.getResult() == 1) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
							 	}else  if(result.getResult() == 0) {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
							
						 }
						
						
						bets.setIsactive(false);
						bets.setSelectionid(1);
						bets.setResult(result.getResult());
						bets.setResultid(result.getId());
						bets.setStatus("CLOSE");
						bets.setAddetoFirestore(false);
						bets.setIsmongodbupdated(false);
						userpnl = userpnl+bets.getNetpnl();
						
						
						
						 stack =stack+bets.getStake();
						 EXUser usr = userRepo.findByUserid(bets.getUserid());
						 admprtnership =usr.getAdminpartership().doubleValue();
						 sbaprtnership =usr.getSubadminpartnership().doubleValue();
						 sumprtnership =usr.getSupermastepartnership().doubleValue();
						 masprtnership =usr.getMasterpartership().doubleValue();
						 dealprtnership =usr.getDelearpartership().doubleValue();
						 
						 placebetrepository.save(bets);
							
					}
				}
			
				
			
				EXUser userbean = userRepo.findByUserid(lib.getUserid());
				Double userCommission = 0.0;
				
			  	if(userbean!=null){
						  String type =null;
							if(userpnl>0){
								type ="Dena";
								adminPartnership = Math.round((userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}else{
								type ="Lena";
								adminPartnership = Math.round((-userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}
							adminPartnershipstack = Math.round((stack*(admprtnership/100.0f))*100.0)/100.0;
							subadminPartnershipstack = Math.round((stack*(sbaprtnership/100.0f))*100.0)/100.0;
							supermasterPartnershipstack = Math.round((stack*(sumprtnership/100.0f))*100.0)/100.0;
							masterpartnershipstack = Math.round((stack*(masprtnership/100.0f))*100.0)/100.0;
							dealerpartnershipstack = Math.round((stack*(dealprtnership/100.0f))*100.0)/100.0;

							
							
							Double adminCommission = 0.0;
							Double subadminCommission = 0.0;
							Double supermasterCommission = 0.0;
							Double masterCommission = 0.0;
							Double dealerCommission = 0.0;
							
							
							if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
								
								if(lib.getSportid() == 2378961) {
									 userCommission=0.0;	
								}else {
									if (fncy.getMtype().equalsIgnoreCase("session")) {
										   userCommission = Math.round((stack*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
											
											   
									   }else {
										   userCommission=0.0;
									   }
								}
								
								
							}
							
							String facny_result =null;
							
							if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market) || fncy.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
								
								facny_result  = result.getResult().toString();
								
							}else if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
							
								if(result.getResult() ==1) {
									facny_result = "Back";
								}else if(result.getResult() ==0) {
									facny_result = "Lay";
								}
							}else if(fncy.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
							
								if(result.getResult() ==1) {
									facny_result = "Odd";
								}else if(result.getResult() ==0) {
									facny_result = "Even";
								}
							}
							
						//System.out.println(Integer.parseInt(null));
							System.out.println("upline amount==>"+userpnl);
						
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							hm.put("descreption", lib.getMatchname()+"/"+result.getFancyname()+"["+facny_result+"]");hm.put("sportId",lib.getSportid());hm.put("marketname", result.getFancyname());
							hm.put("byUserId", lib.getUserid());

							hm.put("chip", userpnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							hm.put("commssion", 0);hm.put("mobapp",0);hm.put("uplineamount", userpnl);hm.put("downlineamount", 0.0);
							hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", userpnl);
							hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline"+"["+result.getResult()+"]");
							hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
							mysqlProcedureDao.updateChipDetailMatchResult(hm);
							
		 					
							if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								if(userpnl>0.0){
								
								
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
									   if (fncy.getMtype().equalsIgnoreCase("session")) {
										   adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											subadminCommission = stack*lib.getSbaCommission()/100.0f;
											   
									   }
										
									}
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (userpnl));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", -(adminPartnership));hm.put("fromto", lib.getUserid());
									hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									lib.setAdminPnl(-adminPartnership-adminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
									
									hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp",0);hm.put("uplineamount",-adminPartnership );hm.put("downlineamount", 0.0);
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", -(subadminPartnership));hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									lib.setSubadminpnl(-subadminPartnership-((subadminCommission-adminCommission)));
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
								}else{
							
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										subadminCommission = -userpnl*lib.getSbaCommission()/100.0f;
										
									}
									 if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										  if (fncy.getMtype().equalsIgnoreCase("session")) {
											  adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											  subadminCommission = stack*lib.getSbaCommission()/100.0f;
										  }
									}
									
									 hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									 hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-userpnl));
									 hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminPartnership));
									 hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									 lib.setAdminPnl(adminPartnership-adminCommission);

									 mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
									 hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
									 hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", adminPartnership);hm.put("downlineamount", 0.0);
									 hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminPartnership));
									 hm.put("fromto", subadmin.getUserid()+" To "+"Downline");hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									 lib.setSubadminpnl(subadminPartnership-((subadminCommission-adminCommission)));
									 mysqlProcedureDao.updateChipDetailMatchResult(hm);
									 
								}
								
							
							admintotalpnl = admintotalpnl+adminPartnership;
							subadmintotalpnl = subadmintotalpnl+subadminPartnership;

							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));							    	 
							
								if(userpnl>0.0){
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										 if (fncy.getMtype().equalsIgnoreCase("session")) {
											    adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
												
												subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
												
												supermasterCommission = stack*lib.getSumCommission()/100.0f;
												 
										  }

									}
									
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									
									
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl)); hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));

								}else{
									
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
										 if (fncy.getMtype().equalsIgnoreCase("session")) {
											    adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
												
												subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
												
												supermasterCommission = -userpnl*lib.getSumCommission()/100.0f;
												 
										 }


									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
											supermasterCommission = stack*lib.getSumCommission()/100.0f;
											 
										 }

									}

									adminpnl =adminPartnership;
									subadminpnl = subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									
									
									
									hm.put("chip", adminPartnership);hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (adminPartnership+subadminPartnership));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (adminPartnership+subadminPartnership+supermasterPartnership));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", userpnl);
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));	
								
								}
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;


							}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));	
								
								if(userpnl>0.0){
								
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = stack*lib.getMastCommission()/100.0f;
											
										}
									}
								
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									masterpnl = -masterpartnership;
									masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
									
									
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(supermasterpnl)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(masterpnl));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(userpnl));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									

								}else{
								
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										
											if (fncy.getMtype().equalsIgnoreCase("session")) {
												adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
												
												subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
												
												supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
												
												masterCommission = -userpnl*lib.getMastCommission()/100.0f;
											
										}
									
									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = stack*lib.getMastCommission()/100.0f;
											
										}
									}
									
									adminpnl = adminPartnership;
									subadminpnl =subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									masterpnl =masterpartnership;
									masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
									
									
									hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
								}

							
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
								mastertotalpnl = mastertotalpnl+masterpartnership;
								
							}
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
							
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
								if(userpnl>0.0){
									
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										
											dealerCommission = stack*lib.getDealCommission()/100.0f;
											
										}
										
									}
									
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									masterpnl = -masterpartnership;
									masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
									dealerpnl =-dealerpartnership;
									dealeruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
									hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", dealerCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
									hm.put("fromto", dealer.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
									
								}else{
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masprtnership)*lib.getMastCommission()/100.0f;
											
											dealerCommission = -userpnl*lib.getDealCommission()/100.0f;
										}
										
										
										

									}
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
										
										if (fncy.getMtype().equalsIgnoreCase("session")) {
											adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;

											subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
											
											supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
											
											masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
											
											dealerCommission = stack*lib.getDealCommission()/100.0f;
											
										}
										
									}
									adminpnl =adminPartnership;
									subadminpnl = subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									masterpnl = masterpartnership;
									masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
									dealerpnl =dealerpartnership;
									dealeruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
									
									
									
									
									hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm); 
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
									hm.put("fromto", dealer.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
									
									
								}
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
								mastertotalpnl = mastertotalpnl+masterpartnership;
								dealerpartnership =dealertotalpnl+dealerpartnership;
							
							}
							
							
							
							
						
					}
					lib.setTotalpnl(userpnl+userCommission);
					lib.setNetpnl(userpnl +userCommission);
					lib.setOldpnl1(lib.getPnl1());
					lib.setOldpnl2(lib.getPnl2());
					lib.setOldpnl3(lib.getPnl3());
					lib.setPnl1(0.0);
					lib.setPnl2(0.0);
					lib.setPnl3(0.0);
					lib.setOldlibility(lib.getLiability());
					lib.setIsresultset(true);
					lib.setUpdatedon(new Date());
	 				lib.setIsactive(false);
	 				lib.setIsLibilityclear(true);
	 				lib.setIsProfitLossclear(true);
	 				lib.setAddetoFirestore(false);
	 				lib.setIsmongodbupdated(false);
					userRepo.save(userbean);
					liabilityRepository.save(lib);
			}
			
		}
		
	}
   	
	
	return "ok";
}





@Override	
@Transactional

public String setMarketResultCron(ArrayList<UserLiability> liblist,MarketResult result) {
	// TODO Auto-generated method stub


	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	MarketResult resultbean =null;
	Market market = null;
	Event event =null;
   
    DecimalFormat df1 = new DecimalFormat("#0");

	
    market = eventMarketRepository.findBymarketid(result.getMarketid());


	if(result!=null){

		
		Double adminpnl=0.0;
		Double subadminpnl=0.0;
		Double supermasterpnl=0.0;
		Double masterpnl=0.0;
		Double dealerpnl=0.0;
		
		Double subadminuplinepnl=0.0;
		Double supermasteruplinepnl=0.0;
		Double masteruplinepnl=0.0;
		Double dealeruplinepnl=0.0;
		
		for(UserLiability lib : liblist){

			 Integer sportId =lib.getSportid();
			
			
			

			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;


			
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
                
               
				if(lib.getIsactive()){

					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
					Double pnl =0.0;
					
						for(ExMatchOddsBet bets :betlist){
                            Double oldpnl = bets.getPnl();
                          
                          //  System.out.println("selectionname==>"+bets.getSelectionname()+"=selection Id==>"+bets.getSelectionid());
							/** if placebet selection id and result selection id is same then  */	
							if((result.getSelectionid() == bets.getSelectionid())) {

								
								if(bets.getIsback()==true){

									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}else{
									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}
							}else{

								if(bets.getIsback()==true){

									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}else{
									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}

							}
							bets.setIsactive(false);
							bets.setResult(result.getSelectionid());
							bets.setResultid(result.getId());
							bets.setResultname(result.getSelectionname());
							bets.setStatus("CLOSE");
							bets.setOldpnl(oldpnl);
							bets.setAddetoFirestore(false);
							bets.setIsmongodbupdated(false);
							placebetrepository.save(bets);
							
							pnl = pnl + bets.getNetpnl();
						 
							
						}

						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						
						if(userbean!=null){
							
						if(result.getSelectionid() == lib.getSelection1()){
								pnl = lib.getPnl1();
							}else if(result.getSelectionid() == lib.getSelection2()){
								pnl = lib.getPnl2();
							}else if(result.getSelectionid() == lib.getSelection3()){
								pnl = lib.getPnl3();
							}

                            String type=null;
							if(pnl>0){
								type= "Dena";
								adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}else{
								type= "Lena";
								adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}
							Double adminCommission = (double) 0;
							Double subadminCommission = (double) 0;
							Double supermasterCommission = (double) 0;
							Double masterCommission = (double) 0;
							Double dealerCommission = (double) 0;
							Double userCommission = (double) 0;
							BigDecimal userbal =new BigDecimal("0.0");
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							/*Client Commsiion and Match Profit */
							Double commssion=0.0;
							if(pnl>0.0){
								//userbal=userbal.add(new BigDecimal(lib.getLiability()));
							//	userbal =userbal.add(new BigDecimal(pnl.toString()));
								
							}else{
								if(userbean.getOddsloss().doubleValue()>0.25){

									if((market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker3)) && market.getEventid() != 28127348){
										userCommission =	Math.round((-pnl*(userbean.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
											
									}
									
								}
								
								 hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
									 
							}
							
							
							EXLedger ledeger = new EXLedger();
							
							ledeger.setMarketid(result.getMarketid());
							ledeger.setMatchid(result.getMatchid());
							
						   
						   
						    
						   
							   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							   hm.put("descreption", market.getMatchname()+"/"+market.getMarketname()+"["+result.getSelectionname()+"]");hm.put("sportId", sportId);hm.put("marketname", market.getMarketname());
							   hm.put("byUserId", lib.getUserid());

							   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
							   hm.put("downlineamountlenadena", 0.0);
								
							   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
							   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
							   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
						       mysqlProcedureDao.updateChipDetailMatchResult(hm);
							
						       if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl =-subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										
										
										
										hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
									
									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker3)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (-pnl)*lib.getSbaCommission()/100.0f;
											
										}
										
										adminpnl = adminPartnership;
										subadminpnl =subadminPartnership;
										subadminuplinepnl=adminPartnership;
										
										
										hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
									}
									
								
									 
									
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker3)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = -pnl*lib.getSumCommission()/100.0f;
											
									}
										
									
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl =((adminPartnership+subadminPartnership));
										
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
									
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
									}

								

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
										master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker3)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = -pnl*lib.getMastCommission()/100.0f;
											
										}
										
										
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl = masterpartnership;
										masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										

									}

									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =(-adminPartnership);
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl = -dealerpartnership;
										dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
										
										
										
									}else{
										
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) || market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker3)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
											dealerCommission = -pnl*lib.getDealCommission()/100.0f;
											
										}
										
										adminpnl = (adminPartnership);
										subadminpnl = (subadminPartnership);
										subadminuplinepnl =(adminPartnership);
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl=(adminPartnership+subadminPartnership);
										masterpnl = (masterpartnership);
										masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
										
										dealerpnl = dealerpartnership;
										dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										
										hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										masterpnl = (masterpartnership);
										masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
										
									}
									
								} 
							lib.setTotalpnl(pnl+userCommission);
							lib.setNetpnl(pnl+userCommission);
							lib.setOldpnl1(lib.getPnl1());
							lib.setOldpnl2(lib.getPnl2());
							lib.setOldpnl3(lib.getPnl3());
							lib.setOldlibility(lib.getLiability());
							lib.setIsresultset(true);
							lib.setUpdatedon(new Date());
							lib.setIsactive(false);
							lib.setIsLibilityclear(true);
			 				lib.setAddetoFirestore(false);
			 				lib.setIsmongodbupdated(false);
							liabilityRepository.save(lib);
							
						}
					
				}
			
			
		}

	}

	return "OK";


}

@Override	
@Transactional

public String setVTP20ResultCron(ArrayList<UserLiability> liblist) throws Exception {
	// TODO Auto-generated method stub


	
	
    Integer sportId =0;
    DecimalFormat df1 = new DecimalFormat("#0");

	
   
	
		Double adminpnl=0.0;
		Double subadminpnl=0.0;
		Double supermasterpnl=0.0;
		Double masterpnl=0.0;
		Double dealerpnl=0.0;
		
		Double subadminuplinepnl=0.0;
		Double supermasteruplinepnl=0.0;
		Double masteruplinepnl=0.0;
		Double dealeruplinepnl=0.0;
		String roundid=null;
		for(UserLiability lib : liblist){

			
			
			
			sportId= lib.getSportid();
			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;

			 
             Double pnl =0.0;
				
             RestTemplate restTemplate = new RestTemplate();
			
				 admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				 subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
				 String apiurl = restTemplate.getForObject("https://aaaa-14afe-cc669-betfair.firebaseio.com/teenpattiresultapi.json", String.class);
				
				
				 String result = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1)+"="+lib.getMarketid(), String.class);
	    		 JSONObject obj = new  JSONObject(result);
				
			
				if(obj.has("winner") &&  obj.getBoolean("status")) {
					if(obj.getString("winner").equalsIgnoreCase("1") || obj.getString("winner").equalsIgnoreCase("2")) {
						if(lib.getIsactive()){

							ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
						   
							    String player = null;
								for(ExMatchOddsBet bets :betlist){
		                         
									
									
									Double oldpnl = bets.getPnl();
									Integer resultid =0;
									
									if(Integer.parseInt(obj.getString("winner")) == 1 ) {
										resultid=1;
										player = "PLAYER A";
									}else {
										resultid=3;	
										player = "PLAYER B";
									}
		                          
									/** if placebet selection id and result selection id is same then  */	
									if((resultid == bets.getSelectionid())) {

										
										if(bets.getIsback()==true){

											bets.setNetpnl(bets.getPnl());
											bets.setPnl(bets.getPnl());
										}
									}else{

										if(bets.getIsback()==true){

											bets.setNetpnl(-bets.getLiability());
											bets.setPnl(-bets.getLiability());
										}

									}
									bets.setIsactive(false);
									bets.setResult(1);
									bets.setResultid(Integer.parseInt(obj.getString("winner")));
									bets.setResultname(player);
									bets.setStatus("CLOSE");
									bets.setOldpnl(oldpnl);
									bets.setAddetoFirestore(false);
									bets.setIsmongodbupdated(false);
									placebetrepository.save(bets);
									pnl = pnl+bets.getNetpnl();
								}

								EXUser userbean = userRepo.findByUserid(lib.getUserid());
								
								if(userbean!=null){
									

		                            String type=null;
									if(pnl>0){
										type= "Dena";
										adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
										subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
										supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
										masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
										dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

									}else{
										type= "Lena";
										adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
										subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
										supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
										masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
										dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

									}
									Double adminCommission = (double) 0;
									Double subadminCommission = (double) 0;
									Double supermasterCommission = (double) 0;
									Double masterCommission = (double) 0;
									Double dealerCommission = (double) 0;
									Double userCommission = (double) 0;
									HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
									
									/*Client Commsiion and Match Profit */
									Double commssion=0.0;
								
									 hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
									EXLedger ledeger = new EXLedger();
									
									ledeger.setMarketid(lib.getMarketid());
									ledeger.setMatchid(lib.getMatchid());
									
								   
								   
								    
								   
									   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
									   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
									   hm.put("descreption", lib.getMatchname()+"/"+lib.getType()+"=>"+lib.getMarketid()+"=>"+player );hm.put("sportId", sportId);hm.put("marketname", lib.getType());
									   hm.put("byUserId", lib.getUserid());

									   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
									   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
									   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
									   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
									   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
								       mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
								       if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
											if(pnl>0.0){
												
												adminpnl = -adminPartnership;
												subadminpnl =-subadminPartnership;
												subadminuplinepnl =-adminPartnership;
												
												
												
												hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												
												
												
												
												hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
												
											
											}else{
												
												
												adminpnl = adminPartnership;
												subadminpnl =subadminPartnership;
												subadminuplinepnl=adminPartnership;
												
												
												hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
												
												hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
												
											}
											
										
											 
											
											
										}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
											supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
											if(pnl>0.0){
												
												
												adminpnl = -adminPartnership;
												subadminpnl = -subadminPartnership;
												subadminuplinepnl =-adminPartnership;
												supermasterpnl = -supermasterPartnership;
												supermasteruplinepnl =-(adminPartnership+subadminPartnership);
												
												
												hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
												
												hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
												
												
												
												hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
												

											}else{
											
											
												adminpnl = adminPartnership;
												hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
												
												
												subadminpnl = subadminPartnership;
												subadminuplinepnl =adminPartnership;
												hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
												
												
												supermasterpnl = (supermasterPartnership);
												supermasteruplinepnl =((adminPartnership+subadminPartnership));
												hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
												
												
												
											}

										

										}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
											supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
												master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
											
											if(pnl>0.0){
												
												
												adminpnl = -adminPartnership;
												subadminpnl = -subadminPartnership;
												subadminuplinepnl =-adminPartnership;
												supermasterpnl = -supermasterPartnership;
												supermasteruplinepnl=-(adminPartnership+subadminPartnership);
												masterpnl = -masterpartnership;
												masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
												
												
												hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
												
												
												hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
												
												
												hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
												
												
												hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
												hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
												hm.put("fromto", master.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
												

											}else{
												
												
												
												adminpnl = adminPartnership;
												subadminpnl = subadminPartnership;
												subadminuplinepnl =adminPartnership;
												supermasterpnl = supermasterPartnership;
												supermasteruplinepnl =(adminPartnership+subadminPartnership);
												masterpnl = masterpartnership;
												masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
												
												
												hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
												
												
												hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
												
												
												
												hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
												
												
												
												hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
												hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
												hm.put("fromto", master.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
												

											}

											
										}
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
											
											supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
											master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
											dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
											
											if(pnl>0.0){
												
												adminpnl = -adminPartnership;
												subadminpnl = -subadminPartnership;
												subadminuplinepnl =(-adminPartnership);
												supermasterpnl = -supermasterPartnership;
												supermasteruplinepnl=-(adminPartnership+subadminPartnership);
												masterpnl = -masterpartnership;
												masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
												dealerpnl = -dealerpartnership;
												dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
												
												
												hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
												
												
												hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
												
												
												
												hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
												
												
												
												hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
												hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
												hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
												hm.put("fromto", master.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
												
												
												hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
												hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
												hm.put("fromto", dealer.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
												
												
												
											}else{
												
											
												adminpnl = (adminPartnership);
												subadminpnl = (subadminPartnership);
												subadminuplinepnl =(adminPartnership);
												supermasterpnl = (supermasterPartnership);
												supermasteruplinepnl=(adminPartnership+subadminPartnership);
												masterpnl = (masterpartnership);
												masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
												
												dealerpnl = dealerpartnership;
												dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
												
												
												hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
												hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
												hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
												hm.put("fromto", admin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
												
												
												
												hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
												hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
												hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
												hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
												
												hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
												hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
												hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
												hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
												
												masterpnl = (masterpartnership);
												masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
												hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
												hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
												hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
												hm.put("fromto", master.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
												
												
												hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
												hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
				 								hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
												hm.put("fromto", dealer.getUserid()+" To "+" Downline");
												hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
												hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
												
												mysqlProcedureDao.updateChipDetailMatchResult(hm);
												lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
												
											}
											
										} 
									lib.setTotalpnl(pnl+userCommission);
									lib.setNetpnl(pnl);
									lib.setOldpnl1(lib.getPnl1());
									lib.setOldpnl2(lib.getPnl2());
									lib.setOldpnl3(lib.getPnl3());
									lib.setOldlibility(lib.getLiability());
									lib.setIsresultset(true);
									lib.setUpdatedon(new Date());
									lib.setIsactive(false);
									lib.setIsLibilityclear(true);
					 				lib.setAddetoFirestore(false);
					 				lib.setIsmongodbupdated(false);
									liabilityRepository.save(lib);
									
								}
							}	
					}else {
						ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
						for(ExMatchOddsBet  placeBet :betlist){
							Integer resultid =0;
							String player=null;
							if(Integer.parseInt(obj.getString("winner")) == 3 ) {
								resultid=3;
								player = EXConstants.tie;
							}else {
								resultid=2;	
								player = EXConstants.abandoned;
							}
							Date date = new Date();
							placeBet.setIsactive(false);
							placeBet.setUpdatedon(date);
							placeBet.setResultid(resultid);
							placeBet.setResultname(player);
							placeBet.setIsmongodbupdated(false);
							placeBet.setAddetoFirestore(false);
							placebetrepository.save(placeBet);
							
						}

						lib.setIsactive(false);
						lib.setAddetoFirestore(false);
						lib.setIsmongodbupdated(false);
						liabilityRepository.save(lib);
					}
				}
				
				
				 
				
				
				
			
			
		}

	

	return "OK";


}

@Override	
@Transactional

public String setTP20ResultCron(ArrayList<UserLiability> liblist) throws Exception {
	// TODO Auto-generated method stub


	
	
    Integer sportId =0;
    DecimalFormat df1 = new DecimalFormat("#0");

	
   
	
		Double adminpnl=0.0;
		Double subadminpnl=0.0;
		Double supermasterpnl=0.0;
		Double masterpnl=0.0;
		Double dealerpnl=0.0;
		
		Double subadminuplinepnl=0.0;
		Double supermasteruplinepnl=0.0;
		Double masteruplinepnl=0.0;
		Double dealeruplinepnl=0.0;
		
		for(UserLiability lib : liblist){

			
			
			
			sportId= lib.getSportid();
			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;

			 
             Double pnl =0.0;
				
             RestTemplate restTemplate = new RestTemplate();
			
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
                
				String result = restTemplate.getForObject("http://13.127.71.85:3000/getteen20-result", String.class);
				JSONObject obj = new  JSONObject(result);
				
			
				if(obj.getBoolean("success")) {
					
					JSONArray ary = new JSONArray(obj.getString("data"));
					for(int i=0; i<ary.length();i++) {
						
						JSONObject winnerobj = (JSONObject) ary.get(i);
						System.out.println(lib.getMarketid());
						if(winnerobj.getString("mid").equalsIgnoreCase(lib.getMarketid())) {
							if(winnerobj.getString("result").equalsIgnoreCase("1") || winnerobj.getString("result").equalsIgnoreCase("3")) {
								if(lib.getIsactive()){

									ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
								   
									    String player = null;
										for(ExMatchOddsBet bets :betlist){
				                         
											
											
											Double oldpnl = bets.getPnl();
											Integer resultid =0;
											
											if(Integer.parseInt(winnerobj.getString("result")) == 1 ) {
												resultid=1;
												player = "PLAYER A";
											}else {
												resultid=3;	
												player = "PLAYER B";
											}
				                          
											/** if placebet selection id and result selection id is same then  */	
											if((resultid == bets.getSelectionid())) {

												
												if(bets.getIsback()==true){

													bets.setNetpnl(bets.getPnl());
													bets.setPnl(bets.getPnl());
												}
											}else{

												if(bets.getIsback()==true){

													bets.setNetpnl(-bets.getLiability());
													bets.setPnl(-bets.getLiability());
												}

											}
											bets.setIsactive(false);
											bets.setResult(1);
											bets.setResultid(Integer.parseInt(winnerobj.getString("result")));
											bets.setResultname(player);
											bets.setStatus("CLOSE");
											bets.setOldpnl(oldpnl);
											bets.setAddetoFirestore(false);
											bets.setIsmongodbupdated(false);
											placebetrepository.save(bets);
											pnl = pnl+bets.getNetpnl();
										}

										EXUser userbean = userRepo.findByUserid(lib.getUserid());
										
										if(userbean!=null){
											

				                            String type=null;
											if(pnl>0){
												type= "Dena";
												adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
												subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
												supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
												masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
												dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

											}else{
												type= "Lena";
												adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
												subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
												supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
												masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
												dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

											}
											Double adminCommission = (double) 0;
											Double subadminCommission = (double) 0;
											Double supermasterCommission = (double) 0;
											Double masterCommission = (double) 0;
											Double dealerCommission = (double) 0;
											Double userCommission = (double) 0;
											HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
											
											/*Client Commsiion and Match Profit */
											Double commssion=0.0;
										
											 hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
												
											EXLedger ledeger = new EXLedger();
											
											ledeger.setMarketid(lib.getMarketid());
											ledeger.setMatchid(lib.getMatchid());
											
										   
										   
										    
										   
											   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
											   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
											   hm.put("descreption", lib.getMatchname()+"/"+lib.getType()+"=>"+lib.getMarketid()+"=>"+player );hm.put("sportId", sportId);hm.put("marketname", lib.getType());
											   hm.put("byUserId", lib.getUserid());

											   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
											   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
											   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
											   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
											   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
										       mysqlProcedureDao.updateChipDetailMatchResult(hm);
											
										       if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
													if(pnl>0.0){
														
														adminpnl = -adminPartnership;
														subadminpnl =-subadminPartnership;
														subadminuplinepnl =-adminPartnership;
														
														
														
														hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														
														
														
														
														hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
														
													
													}else{
														
														
														adminpnl = adminPartnership;
														subadminpnl =subadminPartnership;
														subadminuplinepnl=adminPartnership;
														
														
														hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
														
														hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
														
													}
													
												
													 
													
													
												}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
													supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
													if(pnl>0.0){
														
														
														adminpnl = -adminPartnership;
														subadminpnl = -subadminPartnership;
														subadminuplinepnl =-adminPartnership;
														supermasterpnl = -supermasterPartnership;
														supermasteruplinepnl =-(adminPartnership+subadminPartnership);
														
														
														hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
														
														hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
														
														
														
														hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
														

													}else{
													
													
														adminpnl = adminPartnership;
														hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
														
														
														subadminpnl = subadminPartnership;
														subadminuplinepnl =adminPartnership;
														hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
														
														
														supermasterpnl = (supermasterPartnership);
														supermasteruplinepnl =((adminPartnership+subadminPartnership));
														hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
														
														
														
													}

												

												}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
													supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
														master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
													
													if(pnl>0.0){
														
														
														adminpnl = -adminPartnership;
														subadminpnl = -subadminPartnership;
														subadminuplinepnl =-adminPartnership;
														supermasterpnl = -supermasterPartnership;
														supermasteruplinepnl=-(adminPartnership+subadminPartnership);
														masterpnl = -masterpartnership;
														masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
														
														
														hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
														
														
														hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
														
														
														hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
														
														
														hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
														hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
														hm.put("fromto", master.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
														

													}else{
														
														
														
														adminpnl = adminPartnership;
														subadminpnl = subadminPartnership;
														subadminuplinepnl =adminPartnership;
														supermasterpnl = supermasterPartnership;
														supermasteruplinepnl =(adminPartnership+subadminPartnership);
														masterpnl = masterpartnership;
														masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
														
														
														hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
														
														
														hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
														
														
														
														hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
														
														
														
														hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
														hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
														hm.put("fromto", master.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
														

													}

													
												}
												else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
													
													supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
													master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
													dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
													
													if(pnl>0.0){
														
														adminpnl = -adminPartnership;
														subadminpnl = -subadminPartnership;
														subadminuplinepnl =(-adminPartnership);
														supermasterpnl = -supermasterPartnership;
														supermasteruplinepnl=-(adminPartnership+subadminPartnership);
														masterpnl = -masterpartnership;
														masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
														dealerpnl = -dealerpartnership;
														dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
														
														
														hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
														
														
														hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
														
														
														
														hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
														
														
														
														hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
														hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
														hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
														hm.put("fromto", master.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
														
														
														hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
														hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
														hm.put("fromto", dealer.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
														
														
														
													}else{
														
													
														adminpnl = (adminPartnership);
														subadminpnl = (subadminPartnership);
														subadminuplinepnl =(adminPartnership);
														supermasterpnl = (supermasterPartnership);
														supermasteruplinepnl=(adminPartnership+subadminPartnership);
														masterpnl = (masterpartnership);
														masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
														
														dealerpnl = dealerpartnership;
														dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
														
														
														hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
														hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
														hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
														hm.put("fromto", admin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
														
														
														
														hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
														hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
														hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
														hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
														
														hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
														hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
														hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
														hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
														
														masterpnl = (masterpartnership);
														masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
														hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
														hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
														hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
														hm.put("fromto", master.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
														
														
														hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
														hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
						 								hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
														hm.put("fromto", dealer.getUserid()+" To "+" Downline");
														hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
														
														hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
														
														mysqlProcedureDao.updateChipDetailMatchResult(hm);
														lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
														
													}
													
												} 
											lib.setTotalpnl(pnl+userCommission);
											lib.setNetpnl(pnl);
											lib.setOldpnl1(lib.getPnl1());
											lib.setOldpnl2(lib.getPnl2());
											lib.setOldpnl3(lib.getPnl3());
											lib.setOldlibility(lib.getLiability());
											lib.setIsresultset(true);
											lib.setUpdatedon(new Date());
											lib.setIsactive(false);
											lib.setIsLibilityclear(true);
							 				lib.setAddetoFirestore(false);
							 				lib.setIsmongodbupdated(false);
											liabilityRepository.save(lib);
											
										}
									}	
							}else {
								ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
								for(ExMatchOddsBet  placeBet :betlist){
									Integer resultid =0;
									String player=null;
									if(Integer.parseInt(winnerobj.getString("result")) == 2 ) {
										resultid=3;
										player = EXConstants.tie;
									}else {
										resultid=2;	
										player = EXConstants.abandoned;
									}
									Date date = new Date();
									placeBet.setIsactive(false);
									placeBet.setUpdatedon(date);
									placeBet.setResultid(resultid);
									placeBet.setResultname(player);
									placeBet.setIsmongodbupdated(false);
									placeBet.setAddetoFirestore(false);
									placebetrepository.save(placeBet);
									
								}

								lib.setIsactive(false);
								lib.setAddetoFirestore(false);
								lib.setIsmongodbupdated(false);
								liabilityRepository.save(lib);
							}
						}
					}
							
				     			
					
		
				}
				
				
				 
				
				
				
			
			
		}

	

	return "OK";


}








@Override
public String setFancyResultsCronClearLiability(ArrayList<UserLiability> liblist, ExFancyResult result) {
	

	if(result!=null){
	
		MatchFancy fncy =fancyRepository.findByFancyid(result.getFancyid());
		for(UserLiability lib : liblist){
		
			
			
						    	 
			if(lib.getIsactive()){
                Double userpnl =0.0;
                Integer stack =0;
                
              
				ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(lib.getUserid(), lib.getMarketid(),EXConstants.Fancy, true,lib.getMatchid());
				
				if(betlist.size()>0){
					for(ExMatchOddsBet bets :betlist){

						
						Double oldpnl = bets.getPnl();
						bets.setOldpnl(oldpnl);
					
						 if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market) || fncy.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
							 if((result.getResult()>= (bets.getOdds()))) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}

								}else {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
						 }else  if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
							 if(result.getResult() == 1) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
							 	}else  if(result.getResult() == 0) {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
							
						 }else  if(fncy.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
							 if(result.getResult() == 1) {
									if(bets.getIsback()==true){

										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}else{
										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());
									}
							 	}else  if(result.getResult() == 0) {
									if(bets.getIsback()==true){

										bets.setNetpnl(-bets.getLiability());
										bets.setPnl(-bets.getLiability());

									}else{
										bets.setNetpnl(bets.getPnl());
										bets.setPnl(bets.getPnl());
									}

								} 
							
						 }
						
						
						bets.setIsactive(false);
						bets.setSelectionid(1);
						bets.setResult(result.getResult());
						bets.setResultid(result.getId());
						bets.setStatus("CLOSE");
						bets.setAddetoFirestore(false);
						userpnl = userpnl+bets.getNetpnl();
						stack =stack+bets.getStake();
						  
					}
				}
			
				
			
				EXUser userbean = userRepo.findByUserid(lib.getUserid());
				Double userCommission = 0.0;
				
			  	if(userbean!=null){
						  String type =null;
							if(userpnl>0){
								type ="Dena";
								
							}else{
								type ="Lena";
								
							}
						
							
							if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.BetByBet)){
								
								if(lib.getSportid() == 2378961) {
									 userCommission=0.0;	
								}else {
									if (fncy.getMtype().equalsIgnoreCase("session")) {
										   userCommission = Math.round((stack*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
											
											   
									   }else {
										   userCommission=0.0;
									   }
								}
								
								
							}
							
							String facny_result =null;
							
							if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market) || fncy.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
								
								facny_result  = result.getResult().toString();
								
							}else if(fncy.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
							
								if(result.getResult() ==1) {
									facny_result = "Back";
								}else if(result.getResult() ==0) {
									facny_result = "Lay";
								}
							}else if(fncy.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
							
								if(result.getResult() ==1) {
									facny_result = "Odd";
								}else if(result.getResult() ==0) {
									facny_result = "Even";
								}
							}
							
							
							 userCommission = 0.0;;
								
							
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							hm.put("descreption", lib.getMatchname()+"/"+result.getFancyname()+"["+facny_result+"]");hm.put("sportId",lib.getSportid());hm.put("marketname", result.getFancyname());
							hm.put("byUserId", lib.getUserid());

							hm.put("chip", userpnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							hm.put("commssion", 0);hm.put("mobapp",0);hm.put("uplineamount", userpnl);hm.put("downlineamount", 0.0);
							hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", userpnl);
							hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline"+"["+result.getResult()+"]");
							hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
							mysqlProcedureDao.updateChipDetailMatchResult(hm);
							
							
					}
					lib.setTotalpnl(userpnl+userCommission);
					lib.setNetpnl(userpnl +userCommission);
					lib.setOldpnl1(lib.getPnl1());
					lib.setOldpnl2(lib.getPnl2());
					lib.setOldpnl3(lib.getPnl3());
					lib.setPnl1(0.0);
					lib.setPnl2(0.0);
					lib.setPnl3(0.0);
					lib.setOldlibility(lib.getLiability());
					lib.setIsresultset(true);
					lib.setUpdatedon(new Date());
	 				lib.setIsactive(false);
	 				lib.setIsLibilityclear(true);
	 				lib.setIsmongodbupdated(false);
					liabilityRepository.save(lib);
			}
			
		}
		
	}
   	
	
	return "ok";
}


@Override
public String setFancyResultsCronProfitLoss(ArrayList<UserLiability> liblist, ExFancyResult result) {
	
	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	if(result!=null){
	
		Double admintotalpnl =0.0;
		Double subadmintotalpnl =0.0;
		Double supermastertotalpnl =0.0;
		Double mastertotalpnl =0.0;
		Double dealertotalpnl =0.0;
		for(UserLiability lib : liblist){
		
			
			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;
			
			Double adminPartnershipstack = 0.0;
			Double subadminPartnershipstack = 0.0;
			Double supermasterPartnershipstack = 0.0;
			Double masterpartnershipstack = 0.0;
			Double dealerpartnershipstack = 0.0;
			
			Double admprtnership =0.0;
			Double sbaprtnership =0.0;
			Double sumprtnership =0.0;
			Double masprtnership =0.0;
			Double dealprtnership =0.0;
			
			Double adminpnl=0.0;
			Double subadminpnl=0.0;
			Double supermasterpnl=0.0;
			Double masterpnl=0.0;
			Double dealerpnl=0.0;
			
			Double adminuplinepnl=0.0;
			Double subadminuplinepnl=0.0;
			Double supermasteruplinepnl=0.0;
			Double masteruplinepnl=0.0;
			Double dealeruplinepnl=0.0;
			
			
			
			admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
			subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
			if(lib.getIsactive()){
                Double userpnl =lib.getNetpnl();
                Integer stack =lib.getStack();
                
              
			
				
			
				
			
				//EXUser userbean = userRepo.findByUserid(lib.getUserid());
				Double userCommission = 0.0;
				EXFacnyResultBean fancyresultbean =null;
			//  	if(userbean!=null){
						  String type =null;
							if(userpnl>0){
								type ="Dena";
								adminPartnership = Math.round((userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}else{
								type ="Lena";
								adminPartnership = Math.round((-userpnl*(admprtnership/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-userpnl*(sbaprtnership/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-userpnl*( sumprtnership/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-userpnl*(masprtnership/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-userpnl*(dealprtnership/100.0f))*100.0)/100.0;

							}
							adminPartnershipstack = Math.round((stack*(admprtnership/100.0f))*100.0)/100.0;
							subadminPartnershipstack = Math.round((stack*(sbaprtnership/100.0f))*100.0)/100.0;
							supermasterPartnershipstack = Math.round((stack*(sumprtnership/100.0f))*100.0)/100.0;
							masterpartnershipstack = Math.round((stack*(masprtnership/100.0f))*100.0)/100.0;
							dealerpartnershipstack = Math.round((stack*(dealprtnership/100.0f))*100.0)/100.0;

							
							
							Double adminCommission = 0.0;
							Double subadminCommission = 0.0;
							Double supermasterCommission = 0.0;
							Double masterCommission = 0.0;
							Double dealerCommission = 0.0;
							
							
							
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							
							if(fancyresultbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								if(userpnl>0.0){
								
								
									
									
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										subadminCommission = stack*lib.getSbaCommission()/100.0f;
										
									
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (userpnl));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", -(adminPartnership));hm.put("fromto", lib.getUserid());
									hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									lib.setAdminPnl(-adminPartnership-adminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
									
									hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", subadminCommission);hm.put("mobapp",0);hm.put("uplineamount",-adminPartnership );hm.put("downlineamount", 0.0);
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", -(subadminPartnership));hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									lib.setSubadminpnl(-subadminPartnership-((subadminCommission-adminCommission)));
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
								}else{
							
									
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										subadminCommission = stack*lib.getSbaCommission()/100.0f;
										
									
									 hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									 hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-userpnl));
									 hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminPartnership));
									 hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									 lib.setAdminPnl(adminPartnership-adminCommission);

									 mysqlProcedureDao.updateChipDetailMatchResult(hm);
									
									 hm.put("chip", (-userpnl));hm.put("userid", subadmin.getUserid());hm.put("childid", fancyresultbean.getIduser());
									 hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", adminPartnership);hm.put("downlineamount", 0.0);
									 hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminPartnership));
									 hm.put("fromto", subadmin.getUserid()+" To "+"Downline");hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									 lib.setSubadminpnl(subadminPartnership-((subadminCommission-adminCommission)));
									 mysqlProcedureDao.updateChipDetailMatchResult(hm);
									 
								}
								
							
							admintotalpnl = admintotalpnl+adminPartnership;
							subadmintotalpnl = subadmintotalpnl+subadminPartnership;

							}else if(fancyresultbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));							    	 
							
								if(userpnl>0.0){
									
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
										supermasterCommission = stack*lib.getSumCommission()/100.0f;


									
									
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									
									
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl)); hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));

								}else{
									
									
										
										adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
										
										subadminCommission = (subadminPartnership+adminPartnership)*lib.getSbaCommission()/100.0f;
										
										supermasterCommission = -userpnl*lib.getSumCommission()/100.0f;



										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
									
										subadminCommission = (subadminPartnershipstack+adminPartnershipstack)*lib.getSbaCommission()/100.0f;
										supermasterCommission = stack*lib.getSumCommission()/100.0f;


									
									adminpnl =adminPartnership;
									subadminpnl = subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									
									
									
									hm.put("chip", adminPartnership);hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (adminPartnership+subadminPartnership));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (adminPartnership+subadminPartnership+supermasterPartnership));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", supermaster.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", userpnl);
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));	
								
								}
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;


							}else if(fancyresultbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));	
								
								if(userpnl>0.0){
								
									
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										
										masterCommission = stack*lib.getMastCommission()/100.0f;
									
								
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									masterpnl = -masterpartnership;
									masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
									
									
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(supermasterpnl)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(masterpnl));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(userpnl));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									

								}else{
								
								
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
									
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										
										masterCommission = stack*lib.getMastCommission()/100.0f;
									
									
									adminpnl = adminPartnership;
									subadminpnl =subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									masterpnl =masterpartnership;
									masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
									
									
									hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", master.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
								}

							
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
								mastertotalpnl = mastertotalpnl+masterpartnership;
								
							}
							else if(fancyresultbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
								supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
							
								master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
								
								dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
								if(userpnl>0.0){
									
									
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;
										
										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
									
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
									
										dealerCommission = stack*lib.getDealCommission()/100.0f;
									
										
									
									
									adminpnl =-adminPartnership;
									subadminpnl = -subadminPartnership;
									subadminuplinepnl =-adminPartnership;
									supermasterpnl = -supermasterPartnership;
									supermasteruplinepnl =-(adminPartnership+subadminPartnership);
									masterpnl = -masterpartnership;
									masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
									dealerpnl =-dealerpartnership;
									dealeruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
									
									hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-(adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", -(adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", -(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
									hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", dealerCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
									hm.put("fromto", dealer.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
									
								}else{
									
									
										
										adminCommission = adminPartnershipstack*lib.getAdmCommission()/100.0f;

										subadminCommission = (adminPartnershipstack+subadminPartnershipstack)*lib.getSbaCommission()/100.0f;
										
										supermasterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack)*lib.getSumCommission()/100.0f;
										
										masterCommission = (adminPartnershipstack+subadminPartnershipstack+supermasterPartnershipstack+masterpartnershipstack)*lib.getMastCommission()/100.0f;
										
										dealerCommission = stack*lib.getDealCommission()/100.0f;
										
										
									
									adminpnl =adminPartnership;
									subadminpnl = subadminPartnership;
									subadminuplinepnl =adminPartnership;
									supermasterpnl = supermasterPartnership;
									supermasteruplinepnl =(adminPartnership+subadminPartnership);
									masterpnl = masterpartnership;
									masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
									dealerpnl =dealerpartnership;
									dealeruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership);
									
									
									
									
									hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
									hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
									hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", (adminpnl));
									hm.put("fromto", admin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm); 
									lib.setAdminPnl(adminpnl-adminCommission);
									
									hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
									hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((adminPartnership+subadminPartnership+supermasterPartnership)));
									hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", (subadminpnl));
									hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSubadminpnl(subadminpnl-(subadminCommission-adminCommission));
									
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
									hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", (supermasterpnl));
									hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setSupermasterpnl(supermasterpnl-(supermasterCommission-subadminCommission));
									
									
									hm.put("chip", (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
									hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount",(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership+dealerpartnership));
									hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", (masterpnl));
									hm.put("fromto", master.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setMasterpnl(masterpnl-(masterCommission-supermasterCommission));
									
									
									hm.put("chip", (-userpnl));hm.put("userid", dealer.getUserid());hm.put("childid", fancyresultbean.getIduser());
									hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount",0.0);
									hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", (dealerpnl));
									hm.put("fromto", dealer.getUserid()+" To "+" Downline");
									hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
									
									mysqlProcedureDao.updateChipDetailMatchResult(hm);
									lib.setDealerpnl(dealerpnl-(dealerCommission-masterCommission));
									
									
								}
								admintotalpnl = admintotalpnl+adminPartnership;
								subadmintotalpnl = subadmintotalpnl+subadminPartnership;
								supermastertotalpnl = supermastertotalpnl+supermasterPartnership;
								mastertotalpnl = mastertotalpnl+masterpartnership;
								dealerpartnership =dealertotalpnl+dealerpartnership;
							
							}
							
					
					lib.setTotalpnl(userpnl+userCommission);
					lib.setNetpnl(userpnl +userCommission);
					lib.setOldpnl1(lib.getPnl1());
					lib.setOldpnl2(lib.getPnl2());
					lib.setOldpnl3(lib.getPnl3());
					lib.setPnl1(0.0);
					lib.setPnl2(0.0);
					lib.setPnl3(0.0);
					lib.setOldlibility(lib.getLiability());
					lib.setIsresultset(true);
					lib.setUpdatedon(new Date());
	 				lib.setIsactive(false);
	 				lib.setIsProfitLossclear(true);
					liabilityRepository.save(lib);
			}
			
		}
		
	}
   	
	
	return "ok";
}



public void abandendTieMarketCron(String marketid,Integer matchid) {
	// TODO Auto-generated method stub

	
		ArrayList<UserLiability> liblist = liabilityRepository.findByMarketidAndIsactiveAndIsresultset(marketid,true,false);
		for(UserLiability lib :liblist){

			if(lib.getUserid()!=null){
				ArrayList<ExMatchOddsBet> userbetlist = placebetrepository.findByMarketidAndUserid(marketid, lib.getUserid());
				if(userbetlist.size()>0){
					for(ExMatchOddsBet  placeBet :userbetlist){

						Date date = new Date();

						placeBet.setIsactive(false);
						placeBet.setUpdatedon(date);
						placeBet.setAddetoFirestore(false);
						placeBet.setIsmongodbupdated(false);
						placebetrepository.save(placeBet);
						
					}
				}
				
				
				lib.setIsactive(false);
				lib.setOldlibility(lib.getLiability());
				lib.setOldpnl1(lib.getPnl1());
				lib.setOldpnl2(lib.getPnl2());
				lib.setOldpnl3(lib.getPnl3());
				lib.setPnl1(0.0);
				lib.setPnl2(0.0);
				lib.setPnl3(0.0);
				lib.setAddetoFirestore(false);
				lib.setIsmongodbupdated(false);
				liabilityRepository.save(lib);

			}

				


		
		}
		
	ledgerRepo.deleteByMarketid(marketid);
	
}


@Override	
@Transactional

public String setMarketResultCronWinner(ArrayList<UserLiability> liblist,MarketResult result) {
	// TODO Auto-generated method stub


	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	MarketResult resultbean =null;
	Market market = null;
	Event event =null;
   
    DecimalFormat df1 = new DecimalFormat("#0");

	
    market = eventMarketRepository.findBymarketid(result.getMarketid());


	if(result!=null){

		
		Double adminpnl=0.0;
		Double subadminpnl=0.0;
		Double supermasterpnl=0.0;
		Double masterpnl=0.0;
		Double dealerpnl=0.0;
		
		Double subadminuplinepnl=0.0;
		Double supermasteruplinepnl=0.0;
		Double masteruplinepnl=0.0;
		Double dealeruplinepnl=0.0;
		
		for(UserLiability lib : liblist){

			 Integer sportId =lib.getSportid();
			
			
			

			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;


			
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
                
               
				if(lib.getIsactive()){

					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
					Double pnl =0.0;
					
						for(ExMatchOddsBet bets :betlist){
                            Double oldpnl = bets.getPnl();
                          
                          //  System.out.println("selectionname==>"+bets.getSelectionname()+"=selection Id==>"+bets.getSelectionid());
							/** if placebet selection id and result selection id is same then  */	
							if((result.getSelectionid() == bets.getSelectionid())) {

								
								if(bets.getIsback()==true){

									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}else{
									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}
							}else{

								if(bets.getIsback()==true){

									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}else{
									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}

							}
							bets.setIsactive(false);
							bets.setResult(result.getSelectionid());
							bets.setResultid(result.getId());
							bets.setResultname(result.getSelectionname());
							bets.setStatus("CLOSE");
							bets.setOldpnl(oldpnl);
							bets.setAddetoFirestore(false);
							bets.setIsmongodbupdated(false);
							pnl = pnl + bets.getNetpnl();
						  
							
						}

						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						
						if(userbean!=null){
							
						/*	if(result.getSelectionid() == lib.getSelection1()){
								pnl = lib.getPnl1();
							}else if(result.getSelectionid() == lib.getSelection2()){
								pnl = lib.getPnl2();
							}else if(result.getSelectionid() == lib.getSelection3()){
								pnl = lib.getPnl3();
							}
*/
                            String type=null;
							if(pnl>0){
								type= "Dena";
								adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}else{
								type= "Lena";
								adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}
							Double adminCommission = (double) 0;
							Double subadminCommission = (double) 0;
							Double supermasterCommission = (double) 0;
							Double masterCommission = (double) 0;
							Double dealerCommission = (double) 0;
							Double userCommission = (double) 0;
							BigDecimal userbal =new BigDecimal("0.0");
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							/*Client Commsiion and Match Profit */
							Double commssion=0.0;
							if(pnl>0.0){
								//userbal=userbal.add(new BigDecimal(lib.getLiability()));
							//	userbal =userbal.add(new BigDecimal(pnl.toString()));
								
							}else{
								if(userbean.getOddsloss().doubleValue()>0.25){

									if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) && market.getEventid() != 28529194){
										userCommission =	Math.round((-pnl*(userbean.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
											
									}
									
								}
								
								 hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
									 
							}
							
							
							EXLedger ledeger = new EXLedger();
							
							ledeger.setMarketid(result.getMarketid());
							ledeger.setMatchid(result.getMatchid());
							
						   
						   
						    
						   
							   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							   hm.put("descreption", market.getMatchname()+"/"+market.getMarketname()+"["+result.getSelectionname()+"]");hm.put("sportId", sportId);hm.put("marketname", market.getMarketname());
							   hm.put("byUserId", lib.getUserid());

							   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
							   hm.put("downlineamountlenadena", 0.0);
								
							   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
							   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
							   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
						       mysqlProcedureDao.updateChipDetailMatchResult(hm);
							
						       if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl =-subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										
										
										
										hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
									
									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (-pnl)*lib.getSbaCommission()/100.0f;
											
										}
										
										adminpnl = adminPartnership;
										subadminpnl =subadminPartnership;
										subadminuplinepnl=adminPartnership;
										
										
										hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
									}
									
								
									 
									
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = -pnl*lib.getSumCommission()/100.0f;
											
									}
										
									
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl =((adminPartnership+subadminPartnership));
										
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
									
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
									}

								

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
										master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = -pnl*lib.getMastCommission()/100.0f;
											
										}
										
										
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl = masterpartnership;
										masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										

									}

									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =(-adminPartnership);
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl = -dealerpartnership;
										dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
										
										
										
									}else{
										
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
											dealerCommission = -pnl*lib.getDealCommission()/100.0f;
											
										}
										
										adminpnl = (adminPartnership);
										subadminpnl = (subadminPartnership);
										subadminuplinepnl =(adminPartnership);
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl=(adminPartnership+subadminPartnership);
										masterpnl = (masterpartnership);
										masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
										
										dealerpnl = dealerpartnership;
										dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										
										hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										masterpnl = (masterpartnership);
										masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
										
									}
									
								} 
							lib.setTotalpnl(pnl+userCommission);
							lib.setNetpnl(pnl+userCommission);
							lib.setOldpnl1(lib.getPnl1());
							lib.setOldpnl2(lib.getPnl2());
							lib.setOldpnl3(lib.getPnl3());
							lib.setOldlibility(lib.getLiability());
							lib.setIsresultset(true);
							lib.setUpdatedon(new Date());
							lib.setIsactive(false);
							lib.setIsLibilityclear(true);
			 				lib.setAddetoFirestore(false);
			 				lib.setIsmongodbupdated(false);
							liabilityRepository.save(lib);
							
						}
					
				}
			
			
		}

	}

	return "OK";


}



@Override	
@Transactional

public String setMarketResultCronNew(ArrayList<UserLiability> liblist,MarketResult result) {
	// TODO Auto-generated method stub


	EXDateUtil dtUtil = new EXDateUtil();
	Calendar calendar = new GregorianCalendar();
	TimeZone timeZone = calendar.getTimeZone();
	MarketResult resultbean =null;
	Market market = null;
	Event event =null;
   
    DecimalFormat df1 = new DecimalFormat("#0");

	
    market = eventMarketRepository.findBymarketid(result.getMarketid());


	if(result!=null){

		
		Double adminpnl=0.0;
		Double subadminpnl=0.0;
		Double supermasterpnl=0.0;
		Double masterpnl=0.0;
		Double dealerpnl=0.0;
		
		Double subadminuplinepnl=0.0;
		Double supermasteruplinepnl=0.0;
		Double masteruplinepnl=0.0;
		Double dealeruplinepnl=0.0;
		
		for(UserLiability lib : liblist){

			 Integer sportId =lib.getSportid();
			
			
			

			EXUser master = null;
			EXUser dealer = null;
			EXUser admin = null;
			EXUser subadmin = null;
			EXUser supermaster = null;



			Double adminPartnership = 0.0;
			Double subadminPartnership = 0.0;
			Double supermasterPartnership = 0.0;
			Double masterpartnership = 0.0;
			Double dealerpartnership = 0.0;


			
				admin = userRepo.findByid(Integer.parseInt(lib.getAdminid()));							    	 
				subadmin = userRepo.findByid(Integer.parseInt(lib.getSubadminid()));							    	 
                
               
				if(lib.getIsactive()){

					ArrayList<ExMatchOddsBet> betlist = placebetrepository.findByMarketidAndUserid(lib.getMarketid(), lib.getUserid());
					Double pnl =0.0;
					
						for(ExMatchOddsBet bets :betlist){
                            Double oldpnl = bets.getPnl();
                          
                          //  System.out.println("selectionname==>"+bets.getSelectionname()+"=selection Id==>"+bets.getSelectionid());
							/** if placebet selection id and result selection id is same then  */	
							if((result.getSelectionid() == bets.getSelectionid())) {

								
								if(bets.getIsback()==true){

									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}else{
									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}
							}else{

								if(bets.getIsback()==true){

									bets.setNetpnl(-bets.getLiability());
									bets.setPnl(-bets.getLiability());
								}else{
									bets.setNetpnl(bets.getPnl());
									bets.setPnl(bets.getPnl());
								}

							}
							bets.setIsactive(false);
							bets.setResult(result.getSelectionid());
							bets.setResultid(result.getId());
							bets.setResultname(result.getSelectionname());
							bets.setStatus("CLOSE");
							bets.setOldpnl(oldpnl);
							bets.setAddetoFirestore(false);
							bets.setIsmongodbupdated(false);
							//pnl = pnl + bets.getNetpnl();
						/*  
							*/
						}

						EXUser userbean = userRepo.findByUserid(lib.getUserid());
						
						if(userbean!=null){
							
							if(result.getSelectionid() == lib.getSelection1()){
								pnl = lib.getPnl1();
							}else if(result.getSelectionid() == lib.getSelection2()){
								pnl = lib.getPnl2();
							}else if(result.getSelectionid() == lib.getSelection3()){
								pnl = lib.getPnl3();
							}

                            String type=null;
							if(pnl>0){
								type= "Dena";
								adminPartnership = Math.round((pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}else{
								type= "Lena";
								adminPartnership = Math.round((-pnl*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((-pnl*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((-pnl*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((-pnl*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((-pnl*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;

							}
							Double adminCommission = (double) 0;
							Double subadminCommission = (double) 0;
							Double supermasterCommission = (double) 0;
							Double masterCommission = (double) 0;
							Double dealerCommission = (double) 0;
							Double userCommission = (double) 0;
							BigDecimal userbal =new BigDecimal("0.0");
							HashMap<String ,Object> hm  = new  HashMap<String ,Object>();
							
							/*Client Commsiion and Match Profit */
							Double commssion=0.0;
							if(pnl>0.0){
								//userbal=userbal.add(new BigDecimal(lib.getLiability()));
							//	userbal =userbal.add(new BigDecimal(pnl.toString()));
								
							}else{
								if(userbean.getOddsloss().doubleValue()>0.25){

									if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker) && market.getEventid() != 28127348){
										userCommission =	Math.round((-pnl*(userbean.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
											
									}
									
								}
								
								 hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
									 
							}
							
							
							EXLedger ledeger = new EXLedger();
							
							ledeger.setMarketid(result.getMarketid());
							ledeger.setMatchid(result.getMatchid());
							
						   
						   
						    
						   
							   hm.put("marketid", lib.getMarketid());hm.put("matchid", lib.getMatchid());hm.put("lenadena", type);hm.put("matchname", lib.getMatchname());
							   hm.put("collectionname", "match");hm.put("markettype", lib.getType());hm.put("type", EXConstants.Match);
							   hm.put("descreption", market.getMatchname()+"/"+market.getMarketname()+"["+result.getSelectionname()+"]");hm.put("sportId", sportId);hm.put("marketname", market.getMarketname());
							   hm.put("byUserId", lib.getUserid());

							   hm.put("chip", pnl);hm.put("userid", lib.getUserid());hm.put("childid", userbean.getId());
							   hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", pnl);hm.put("downlineamount", 0.0);
							   hm.put("downlineamountlenadena", 0.0);
							   
							   
								
							   hm.put("parentid", userbean.getParentid());hm.put("clientuserid", lib.getUserid());hm.put("pnl", pnl);
							   hm.put("fromto", userRepo.findByid(Integer.parseInt(userbean.getParentid())).getUserid()+" To "+" Downline");
							   hm.put("commssiondiya", 0.0);hm.put("commssionmila", userCommission);
						       
						       if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl =-subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										
										hm.put("uplinepnlamount", subadminpnl);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										hm.put("chip", (adminpnl));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (-pnl));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										hm.put("uplinepnlamount", adminpnl);
										
										
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp",(0));hm.put("uplineamount",subadminuplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
									
									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (-pnl)*lib.getSbaCommission()/100.0f;
											
										}
										
										adminpnl = adminPartnership;
										subadminpnl =subadminPartnership;
										subadminuplinepnl=adminPartnership;
										
										
										hm.put("chip", (((adminpnl))));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp",0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-pnl)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										hm.put("chip", ((-pnl)));hm.put("userid", subadmin.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
									}
									
								
									 
									
									
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl =-(adminPartnership+subadminPartnership);
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										hm.put("chip", (-(adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", (-(pnl)));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = -pnl*lib.getSumCommission()/100.0f;
											
									}
										
									
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl =((adminPartnership+subadminPartnership));
										
										hm.put("chip", (adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
									
										hm.put("chip", (adminPartnership+subadminPartnership));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (-pnl));hm.put("userid", supermaster.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount",supermasteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
									}

								

								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
										master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									
									if(pnl>0.0){
										
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =-adminPartnership;
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount",subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										hm.put("chip", -(pnl));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										

									}else{
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = -pnl*lib.getMastCommission()/100.0f;
											
										}
										
										
										adminpnl = adminPartnership;
										subadminpnl = subadminPartnership;
										subadminuplinepnl =adminPartnership;
										supermasterpnl = supermasterPartnership;
										supermasteruplinepnl =(adminPartnership+subadminPartnership);
										masterpnl = masterpartnership;
										masteruplinepnl=(adminPartnership+subadminPartnership+supermasterPartnership);
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", (((adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila", adminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", master.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount",masteruplinepnl );hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										

									}

									
								}
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){
									
									supermaster = userRepo.findByid(Integer.parseInt(lib.getSupermasterid()));	
									master = userRepo.findByid(Integer.parseInt(lib.getMasterid()));
									dealer = userRepo.findByid(Integer.parseInt(lib.getDealerid()));
									
									if(pnl>0.0){
										
										adminpnl = -adminPartnership;
										subadminpnl = -subadminPartnership;
										subadminuplinepnl =(-adminPartnership);
										supermasterpnl = -supermasterPartnership;
										supermasteruplinepnl=-(adminPartnership+subadminPartnership);
										masterpnl = -masterpartnership;
										masteruplinepnl =-(adminPartnership+subadminPartnership+supermasterPartnership);
										dealerpnl = -dealerpartnership;
										dealeruplinepnl =(-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", (-adminPartnership));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership))));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl)));
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership))));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl)));
										
										
										
										hm.put("chip", ((-(adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", (-(pnl)));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl)));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp",0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl)));
										
										
										
									}else{
										
										if(market.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
											adminCommission = adminPartnership*lib.getAdmCommission()/100.0f;
											subadminCommission = (adminPartnership+subadminPartnership)*lib.getSbaCommission()/100.0f;
											supermasterCommission = (adminPartnership+subadminPartnership+supermasterPartnership)*lib.getSumCommission()/100.0f;
											masterCommission = (adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership)*lib.getMastCommission()/100.0f;
											dealerCommission = -pnl*lib.getDealCommission()/100.0f;
											
										}
										
										adminpnl = (adminPartnership);
										subadminpnl = (subadminPartnership);
										subadminuplinepnl =(adminPartnership);
										supermasterpnl = (supermasterPartnership);
										supermasteruplinepnl=(adminPartnership+subadminPartnership);
										masterpnl = (masterpartnership);
										masteruplinepnl =(adminPartnership+subadminPartnership+supermasterPartnership);
										
										dealerpnl = dealerpartnership;
										dealeruplinepnl =((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership));
										
										
										hm.put("chip", ((adminPartnership)));hm.put("userid", admin.getUserid());hm.put("childid", subadmin.getId());
										hm.put("commssion", adminCommission);hm.put("mobapp", 0);hm.put("uplineamount", 0.0);hm.put("downlineamount", ((adminPartnership+subadminPartnership)));
										hm.put("downlineamountlenadena", subadminuplinepnl);
										hm.put("parentid", EXConstants.adminParentId);hm.put("clientuserid", admin.getUserid());hm.put("pnl", adminpnl);
										hm.put("fromto", admin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -adminCommission);hm.put("commssionmila", 0.0);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setAdminPnl(Double.valueOf(df1.format(adminpnl))-Double.valueOf(df1.format(adminCommission)));
										
										
										
										hm.put("chip", ((adminPartnership+subadminPartnership)));hm.put("userid", subadmin.getUserid());hm.put("childid", supermaster.getId());
										hm.put("commssion", subadminCommission);hm.put("mobapp", 0);hm.put("uplineamount", subadminuplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership))));
										hm.put("downlineamountlenadena", supermasteruplinepnl);
										hm.put("parentid", admin.getId());hm.put("clientuserid", subadmin.getUserid());hm.put("pnl", subadminpnl);
										hm.put("fromto", subadmin.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -subadminCommission);hm.put("commssionmila",adminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSubadminpnl(Double.valueOf(df1.format(subadminpnl))-(Double.valueOf(df1.format(subadminCommission))-Double.valueOf(df1.format(adminCommission))));
										
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership))));hm.put("userid", supermaster.getUserid());hm.put("childid", master.getId());
										hm.put("commssion", supermasterCommission);hm.put("mobapp", 0);hm.put("uplineamount", supermasteruplinepnl);hm.put("downlineamount", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));
										hm.put("downlineamountlenadena", masteruplinepnl);
										hm.put("parentid", subadmin.getId());hm.put("clientuserid", supermaster.getUserid());hm.put("pnl", supermasterpnl);
										hm.put("fromto", supermaster.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -supermasterCommission);hm.put("commssionmila", subadminCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setSupermasterpnl(Double.valueOf(df1.format(supermasterpnl))-(Double.valueOf(df1.format(supermasterCommission))-Double.valueOf(df1.format(subadminCommission))));
										
										masterpnl = (masterpartnership);
										masteruplinepnl=((adminPartnership+subadminPartnership+supermasterPartnership));
										hm.put("chip", (((adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership))));hm.put("userid", master.getUserid());hm.put("childid", dealer.getId());
										hm.put("commssion", masterCommission);hm.put("mobapp", 0);hm.put("uplineamount", masteruplinepnl);hm.put("downlineamount", ((-(pnl))));
										hm.put("downlineamountlenadena", dealeruplinepnl);
										hm.put("parentid", supermaster.getId());hm.put("clientuserid", master.getUserid());hm.put("pnl", masterpnl);
										hm.put("fromto", master.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -masterCommission);hm.put("commssionmila", supermasterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setMasterpnl(Double.valueOf(df1.format(masterpnl))-(Double.valueOf(df1.format(masterCommission))-Double.valueOf(df1.format(supermasterCommission))));
										
										
										hm.put("chip", ((-(pnl))));hm.put("userid", dealer.getUserid());hm.put("childid", userbean.getId());
										hm.put("commssion", userCommission);hm.put("mobapp", 0);hm.put("uplineamount", dealeruplinepnl);hm.put("downlineamount", 0.0);
										hm.put("downlineamountlenadena", pnl);
										hm.put("parentid", master.getId());hm.put("clientuserid", dealer.getUserid());hm.put("pnl", dealerpnl);
										hm.put("fromto", dealer.getUserid()+" To "+" Downline");
										hm.put("commssiondiya", 0.0);hm.put("commssionmila", 0.0);
										
										hm.put("commssiondiya", -dealerCommission);hm.put("commssionmila", masterCommission);
										
										mysqlProcedureDao.updateChipDetailMatchResult(hm);
										lib.setDealerpnl(Double.valueOf(df1.format(dealerpnl))-(Double.valueOf(df1.format(dealerCommission))-Double.valueOf(df1.format(masterCommission))));
										
									}
									
								} 
							lib.setTotalpnl(pnl+userCommission);
							lib.setNetpnl(pnl+userCommission);
							lib.setOldpnl1(lib.getPnl1());
							lib.setOldpnl2(lib.getPnl2());
							lib.setOldpnl3(lib.getPnl3());
							lib.setOldlibility(lib.getLiability());
							lib.setIsresultset(true);
							lib.setUpdatedon(new Date());
							lib.setIsactive(false);
							lib.setIsLibilityclear(true);
			 				lib.setAddetoFirestore(false);
			 				lib.setIsmongodbupdated(false);
							liabilityRepository.save(lib);
							
						}
					
				}
			
			
		}

	}

	return "OK";
}

}




