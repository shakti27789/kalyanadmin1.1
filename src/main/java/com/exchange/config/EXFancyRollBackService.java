package com.exchange.config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.controller.EXbetController;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.service.FancyService;
import com.exchange.util.EXConstants;

@Service
public class EXFancyRollBackService {

	
	@Autowired
	EventRepository seriesEventRepository;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	MarketResultRepository marketResultRepository;
	@Autowired
	EXUserRepository userRepo;
	@Autowired
	EXChipDetailRepository exchipRepo;

	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	MarketRepository eventMarketRepository;

	@Autowired EXbetController betcon;

	@Autowired
	FancyRepository fancyRepository;

	
	@Autowired
	FancyResultRepository fancyresultRepository;

	@Autowired
	private FancyService fancyService;

	String date1;

	@Autowired
	HttpServletRequest request;
	
	
	@Autowired
	AppChargeRepo appChargeRepo;
	
	@Async("asyncExecutor")
	public CompletableFuture<String> rollBackFancyResult(String fancyid,ExFancyResult result,List<ExMatchOddsBet> list) throws InterruptedException
	{
		return null;/*

		
		ResponseBean responseBean = new ResponseBean();
		DecimalFormat df = new DecimalFormat("#0.00");
		
	
		//MatchFancy matchfancy = null;
		UserLiability userLiability = null;
		
		ArrayList<EXChipDetail> chipList = new ArrayList<>();
		HashMap<String ,ArrayList<Object>> hm = new HashMap<String ,ArrayList<Object>>();
		
		EXUser users = null;
		String userid = "";
		
		BigDecimal balance = new BigDecimal(0);
		Double UserwisetotalWinLossValue = 0.00;
		
		EXUser master = null;
		EXUser dealer = null;
		EXUser admin = null;
		EXUser subadmin = null;
		EXUser supermaster = null;
		
		BigDecimal adminBalance = new BigDecimal(0);
		BigDecimal subadminbalance = new BigDecimal(0);
		BigDecimal supermasterbalance = new BigDecimal(0);
		BigDecimal masterBalance = new BigDecimal(0);
		BigDecimal dealerBalance = new BigDecimal(0);
		BigDecimal previousbal = new BigDecimal(0);
		Double adminCommission = 0.0;
		Double subadminCommission = 0.0;
		Double supermasterCommission = 0.0;
		Double masterCommission = 0.0;
		Double dealerCommission = 0.0;
		Double userCommission = 0.0;
		
		Double adminPartnership = 0.0;
		Double subadminPartnership = 0.0;
		Double supermasterPartnership = 0.0;
		Double masterpartnership = 0.0;
		Double dealerpartnership = 0.0;
		
		
		if(fancyresultRepository.existsByfancyid(result.getFancyid()) == true){
			
			
			
			Integer i=0;
			ExMatchOddsBet betstemp =null;
			if(list.size()>0) {
				for(ExMatchOddsBet bets : list){
					if(bets.getIsactive()==true){
						
						bets.setResult(result.getResult());
						bets.setResultid(result.getId());
						bets.setIsactive(false);
						bets.setIsdeleted(false);
						bets.setStatus("CLOSE");
					
							
						userLiability = liabilityRepository.findByFancyidAndUserid(bets.getFancyid(),bets.getUserid());
						if(userLiability!=null) {
							userLiability.setIsactive(false);
						}
						users = new EXUser();
						users = userRepo.findByUserid(bets.getUserid());
						balance = users.getSecondrybanlce();
						balance = balance.add(BigDecimal.valueOf(userLiability.getLiability()));
						userLiability.setLiability(0.00);
						if(!userid.equalsIgnoreCase(bets.getUserid())){
							if(!userid.equals("")){
								String adminid = "";
								String subadminid = "";
								String supermasterid = "";
								String masterid = "";
								String dealerid = "";
								if(UserwisetotalWinLossValue<0.0){
									UserwisetotalWinLossValue=0.0-UserwisetotalWinLossValue;
									EXUser userbean = userRepo.findByUserid(userid);
									
									if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){

										
										
										if(!adminid.equalsIgnoreCase(userbean.getAdminid())){
											admin = userRepo.findByid(userbean.getAdminid());							    	 
											adminid=userbean.getAdminid();
										}

										if(!subadminid.equalsIgnoreCase(userbean.getSubadminid())){
											subadmin = userRepo.findByid(userbean.getSubadminid());							    	 
											subadminid=userbean.getSubadminid();
										}
										subadminbalance=subadmin.getSecondrybanlce();
										
										 if subadmin create direct client
										if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
											dealerid="0";
											masterid="0";
											supermasterid="0";
											dealer =null;
											master=null;
											supermaster=null;
										}
										 if subadmin create direct super agent and he create direct client
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
											
											supermaster = null;							    	 
											supermasterid="0";
											master =null;							    	 
											dealer = null;								    	 
											dealerid="0";
											
											if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
												master = userRepo.findByid(userbean.getMasterid());							    	 
												masterid=userbean.getMasterid();
											}
											masterBalance = master.getSecondrybanlce();
										}		

										 if subadmin create direct agent and he create direct client
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
											
											supermaster = null;							    	 
											supermasterid="0";
											master =null;							    	 
											masterid = "0";
											if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
												dealer = userRepo.findByid(userbean.getDealerid());							    	 
												dealerid=userbean.getDealerid();
											}
											dealerBalance = dealer.getSecondrybanlce();
										}
										  if subadmin create direct master and he create direct Agent And  he createClient	
											else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
												
											supermaster = null;							    	 
											supermasterid="0";
											
											if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
												master = userRepo.findByid(userbean.getMasterid());							    	 
												masterid=userbean.getMasterid();
											}
											
											if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
												dealer = userRepo.findByid(userbean.getDealerid());							    	 
												dealerid=userbean.getDealerid();
											}
											masterBalance = master.getSecondrybanlce();
											dealerBalance = dealer.getSecondrybanlce();
										}
										 if supermaster create direct client 
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
											
											if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
												supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
												supermasterid=users.getSupermasterid();
											}
											master =null;							    	 
											masterid = "0";
											dealer = null;								    	 
											dealerid="0";
											supermasterbalance =supermaster.getSecondrybanlce();
										}
										if supermaster create direct agent and her crrate users
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
											
											if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
												supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
												supermasterid=users.getSupermasterid();
											}
											master =null;							    	 
											masterid = "0";
											if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
												dealer = userRepo.findByid(userbean.getDealerid());							    	 
												dealerid=userbean.getDealerid();
											}
											supermasterbalance =supermaster.getSecondrybanlce();
											dealerBalance = dealer.getSecondrybanlce();
										}
										if super stokist crreat Master  and he create users
										else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
											
										
											if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
												supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
												supermasterid=userbean.getSupermasterid();
											}

											if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
												master = userRepo.findByid(userbean.getMasterid());							    	 
												masterid = users.getMasterid();
											}
											dealer = null;								    	 
											dealerid="0";
											supermasterbalance =supermaster.getSecondrybanlce();
											masterBalance = master.getSecondrybanlce();
										}else{
											if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
												supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
												supermasterid=userbean.getSupermasterid();
											}

											if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
												master = userRepo.findByid(userbean.getMasterid());							    	 
												masterid = userbean.getMasterid();
											}

											if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
												dealer = userRepo.findByid(userbean.getDealerid());							    	 
												dealerid=users.getDealerid();
											}
											
											supermasterbalance =supermaster.getSecondrybanlce();
											masterBalance = master.getSecondrybanlce();
											dealerBalance = dealer.getSecondrybanlce();
										}

										
										if(userbean!=null){

											
											adminPartnership = Math.round((UserwisetotalWinLossValue*(users.getAdminpartership().doubleValue()/100.0f))*100.0)/100.0;
											subadminPartnership = Math.round((UserwisetotalWinLossValue*(users.getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0;
											supermasterPartnership = Math.round((UserwisetotalWinLossValue*(users.getSupermastepartnership().doubleValue()/100.0f))*100.0)/100.0;
											masterpartnership = Math.round((UserwisetotalWinLossValue*(users.getMasterpartership().doubleValue()/100.0f))*100.0)/100.0;
											dealerpartnership = Math.round((UserwisetotalWinLossValue*(users.getDelearpartership().doubleValue()/100.0f))*100.0)/100.0;
											
											if(subadmin.getFancywin().doubleValue()<0.25){
												
												adminPartnership =0.0;
												adminCommission=0.0;
											
											}
											
											userCommission = Math.round((UserwisetotalWinLossValue*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
											adminCommission = Math.round((adminPartnership*(subadmin.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
									
											if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
												if(adminCommission>0.0){
												  	previousbal =adminBalance;
								    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
								    				
								    				previousbal =subadminbalance;
								    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
								    				adminCommission= 0.0-adminCommission;
								                    
								                }
												if(userCommission>0.0){
								                	 previousbal= subadminbalance;
								                	 subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(userCommission));
													 subadminCommission =0.0-userCommission;
													 
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
														
													}else{
														if(adminCommission<0.0){
															subadminCommission =-adminCommission;
														}
														
													}
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){

												
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												if(dealer.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												
												if(adminCommission>0.0){
													
													  	previousbal =adminBalance;
									    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
									    				
									    				previousbal =subadminbalance;
									    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
									    				 adminCommission= 0.0-adminCommission;
									    			
									                }
												if(subadminCommission>0.0){
								                	previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =dealerBalance;
								     				dealerBalance = dealerBalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								                 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                	 }
								                	
								                 }
												if(userCommission>0.0){
								                	 previousbal =dealerBalance;
								                	 dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
													 dealerCommission =0.0-userCommission;
													 
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
													
													}else{
														if(subadminCommission<0.0){
															dealerCommission =-subadminCommission;
														}
														
													} 
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){

												 
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												if(master.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
												
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
														
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												 
												 if(adminCommission>0.0){
														//setProfitnLossCommRecived(subadmin, adminCommission);
									                	previousbal =adminBalance;
									    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
									    				
									    				previousbal =subadminbalance;
									    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
									    				 adminCommission= 0.0-adminCommission;
									    			
									                }
												 if(subadminCommission>0.0){
								                	 //setProfitnLossCommRecived(master, subadminCommission);
								                	previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =masterBalance;
								     				masterBalance = masterBalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								                 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                	 }
								                	
								                 } 
												 if(userCommission>0.0){
								                	 previousbal =masterBalance;
								                	 masterBalance = masterBalance.subtract(BigDecimal.valueOf(userCommission));
													 masterCommission =0.0-userCommission;
													
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
													 
													}else{
														if(subadminCommission<0.0){
															masterCommission =-subadminCommission;
														}
														
													}
												
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){

												
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
													
												}
												if(supermaster.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												
												if(adminCommission>0.0){
													//setProfitnLossCommRecived(subadmin, adminCommission);
								                	previousbal =adminBalance;
								    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
								    				
								    				previousbal =subadminbalance;
								    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
								    				 adminCommission= 0.0-adminCommission;
								    			
								                }
												if(subadminCommission>0.0){
								                	 //setProfitnLossCommRecived(supermaster, subadminCommission);
								                	 previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								                 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                	 }
								                	
								                 } 
												if(userCommission>0.0){
								                	 previousbal =supermasterbalance;
								                	 supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(userCommission));
													 supermasterCommission =0.0-userCommission;
													 
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
													
													}else{
														if(supermasterCommission<0.0){
															supermasterCommission =-subadminCommission;
														}
														
													}
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){


												Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+masterpartnership;
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												
												if(master.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												if(dealer.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													masterpartnership =0.0;
													dealerCommission=0.0;
													totaluplinepartenershi1  = adminPartnership+subadminPartnership+masterpartnership;
													masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}else{
													masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												
												if(adminCommission>0.0){
														//setProfitnLossCommRecived(subadmin, adminCommission);
									                	previousbal =adminBalance;
									    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
									    				
									    				previousbal =subadminbalance;
									    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
									    				 adminCommission= 0.0-adminCommission;
									    			
									                }
												 if(subadminCommission>0.0){
													 //setProfitnLossCommRecived(master, subadminCommission);
								                	 previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =masterBalance;
								     				masterBalance = masterBalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								                 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                	 }
								                	
								                 }
												 if(masterCommission>0.0){
														previousbal =masterBalance;
														masterBalance = masterBalance.subtract(BigDecimal.valueOf(masterCommission));
														


														previousbal =dealerBalance;
														dealerBalance = dealerBalance.add(BigDecimal.valueOf(masterCommission));
														masterCommission =0.0-masterCommission;
									                }else{
									                
									                	
									                	if(subadminCommission<0.0){
									                		masterCommission=-subadminCommission;
									                	}
									                	
									                } 
												 if(userCommission>0.0){
														previousbal =dealerBalance;
														dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
														dealerCommission =0.0-userCommission;
														
														 previousbal = userbean.getSecondrybanlce();
														 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
														 
													       
													}else{
														if(masterCommission<0.0){
															dealerCommission =-masterCommission;
														}
														
													} 
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){

												
												Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership;
												Double totaluplinepartenershi2  = adminPartnership+subadminPartnership+supermasterPartnership;
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												if(supermaster.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													   		
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												if(master.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													supermasterPartnership =0.0;
													masterCommission=0.0;
													totaluplinepartenershi2  = adminPartnership+subadminPartnership+supermasterPartnership;
													supermasterCommission=Math.round((totaluplinepartenershi2.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}else{
													supermasterCommission=Math.round((totaluplinepartenershi2.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
												
												}
												if(dealer.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													supermasterPartnership =0.0;
													masterpartnership =0.0;
													dealerCommission=0.0;
													totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership;
													masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}else{
													
													masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												
												
												if(adminCommission>0.0){
														previousbal =adminBalance;
									    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
									    				
									    				previousbal =subadminbalance;
									    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
									    				adminCommission= 0.0-adminCommission;
									    			
									                }
												
												 if(subadminCommission>0.0){
								                	 previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								     			  }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                		
								                	 }
								                	
								                 } 
												 
												 if(supermasterCommission>0.0){
								                	 previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
								     				
								     				previousbal =masterBalance;
								     				masterBalance = masterBalance.add(BigDecimal.valueOf(supermasterCommission));
								     				
								     				supermasterCommission =0.0-supermasterCommission;
								     				
								                 }else{
								                	 if(subadminCommission<0.0){
								                		 supermasterCommission=-subadminCommission;
								                		
								                	 }
								                	
								                 }
												 if(masterCommission>0.0){
														
														previousbal =masterBalance;
														masterBalance = masterBalance.subtract(BigDecimal.valueOf(masterCommission));
														


														previousbal =dealerBalance;
														dealerBalance = dealerBalance.add(BigDecimal.valueOf(masterCommission));
														masterCommission =0.0-masterCommission;
														
									                }else{
									                	if(supermasterCommission<0.0){
									                		masterCommission=-supermasterCommission;
									                	
									                	}
									                	
									                }
												 
												 if(userCommission>0.0){
														previousbal =dealerBalance;
														dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
														dealerCommission =0.0-userCommission;
														
														 previousbal = userbean.getSecondrybanlce();
														 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
														 
														
													}else{
														if(masterCommission<0.0){
															dealerCommission =-masterCommission;
															
														}
														
													}
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){

												
												Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												if(supermaster.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												if(master.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													supermasterPartnership =0.0;
													masterCommission=0.0;
													totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
													supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
												
												}else{
													supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
												
												}
												
												
												if(adminCommission>0.0){
														previousbal =adminBalance;
									    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
									    				
									    				previousbal =subadminbalance;
									    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
									    				 adminCommission= 0.0-adminCommission;
									    			
									                }
												 if(subadminCommission>0.0){
								                	previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								     			 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                		
								                	 }
								                	
								                 }
												 if(supermasterCommission>0.0){
								                	 previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
								     				

								     				previousbal =masterBalance;
								     				masterBalance = masterBalance.add(BigDecimal.valueOf(supermasterCommission));
								     				
								     				supermasterCommission =0.0-supermasterCommission;
								     			 }else{
								                	 if(subadminCommission<0.0){
								                		 supermasterCommission=-subadminCommission;
								                		
								                	 }
								                	
								                 }
												 if(userCommission>0.0){
								                	 previousbal =masterBalance;
													 masterBalance = masterBalance.subtract(BigDecimal.valueOf(userCommission));
													 masterCommission =0.0-userCommission;
													 
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
													
													}else{
														if(masterCommission<0.0){
															masterCommission =-supermasterCommission;
														}
														
													} 
											
											}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){

												
												Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
												Double totaluplinepartenership  = adminPartnership+subadminPartnership;
												
												if(subadmin.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													adminCommission=0.0;
													
												}
												if(supermaster.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													subadminCommission=0.0;
													totaluplinepartenership  = adminPartnership+subadminPartnership;
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
														
												}
												else{
													subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												if(dealer.getFancywin().doubleValue()<0.25){
													adminPartnership =0.0;
													subadminPartnership =0.0;
													supermasterPartnership =0.0;
													dealerCommission=0.0;
													totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
													supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}else{
													supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
													
												}
												
												
												if(adminCommission>0.0){
														//setProfitnLossCommRecived(subadmin, adminCommission);
										                	previousbal =adminBalance;
										    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
										    				
										    				previousbal =subadminbalance;
										    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
										    				 adminCommission= 0.0-adminCommission;
										    			
										                }
												 if(subadminCommission>0.0){
								                	 //setProfitnLossCommRecived(supermaster, subadminCommission);
								                	 previousbal =subadminbalance;
								     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
								     				

								     				previousbal =supermasterbalance;
								     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
								     				subadminCommission =0.0-subadminCommission;
								                 }else{
								                	 if(adminCommission<0.0){
								                		 subadminCommission =-adminCommission;
								                	 }
								                	
								                 }
												 if(supermasterCommission>0.0){
									            	   //setProfitnLossCommRecived(dealer, supermasterCommission);
									                	 previousbal =supermasterbalance;
									     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
									     				

									     				previousbal =dealerBalance;
									     				dealerBalance = dealerBalance.add(BigDecimal.valueOf(supermasterCommission));
									     				
									     				supermasterCommission =0.0-supermasterCommission;
									                 }else{
									                	 if(subadminCommission<0.0){
									                		 supermasterCommission=-subadminCommission;
									                	 }
									                	
									                 } 
												 
												 if(userCommission>0.0){
								                	 previousbal =dealerBalance;
								                	 dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
													 dealerCommission =0.0-userCommission;
													 
													 previousbal = userbean.getSecondrybanlce();
													 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
													
													}else{
														if(supermasterCommission<0.0){
															dealerCommission =-supermasterCommission;
														}
														
													}
											
											}
										
										}
										betstemp.setCommission(adminCommission);
										betstemp.setSubadminCommission(subadminCommission);
										betstemp.setSupermasterCommission(supermasterCommission);
										betstemp.setMasterCommission(masterCommission);
										betstemp.setDealerCommission(dealerCommission);
										betstemp.setUserCommision(userCommission);
										betstemp.setCommisiontype(EXConstants.clientCommssionGiven);
										
									
									if(!adminid.equalsIgnoreCase("0")) {
										admin.setSecondrybanlce(adminBalance);
									}

									if(!subadminid.equalsIgnoreCase("0")) {
										subadmin.setSecondrybanlce(subadminbalance);
									}

									if(!supermasterid.equalsIgnoreCase("0")) {
										supermaster.setSecondrybanlce(supermasterbalance);
									}

									if(!masterid.equalsIgnoreCase("0")) {
										master.setSecondrybanlce(masterBalance);
									}

									if(!dealerid.equalsIgnoreCase("0")) {
										dealer.setSecondrybanlce(dealerBalance);
									}

							  	   if(!adminid.equalsIgnoreCase("0")) {
										userRepo.save(admin);
									}

									if(!subadminid.equalsIgnoreCase("0")) {
										userRepo.save(subadmin);	
									}

									if(!supermasterid.equalsIgnoreCase("0")) {
										userRepo.save(supermaster);
									}

									if(!masterid.equalsIgnoreCase("0")) {
										userRepo.save(master);
									}

									if(!dealerid.equalsIgnoreCase("0")) {
										userRepo.save(dealer);
									}
									placebetrepository.save(betstemp);
									userRepo.save(userbean);
									exchipRepo.saveAll(chipList);
									UserwisetotalWinLossValue=0.0;
									
									}
								}else{
									UserwisetotalWinLossValue=0.0;
									
								}
								
								
							}
							
						}
						
                       
                        	if((result.getResult()>= bets.getOdds()) && users!=null) {
                        		
                        		//UserwisetotalWinLossValue=	ifResultisGreterThenOdds(bets,users,userLiability,UserwisetotalWinLossValue);
    							
    						}else {
    							
    							//UserwisetotalWinLossValue =	ifResultisLesseThenOdds(bets,users,userLiability,UserwisetotalWinLossValue);
    						}
    					
                       userid= bets.getUserid();
                        betstemp=bets;
						
					}
					i++;

				}
				if(!userid.equals("")){
					String adminid = "";
					String subadminid = "";
					String supermasterid = "";
					String masterid = "";
					String dealerid = "";
					if(UserwisetotalWinLossValue<0.0){
						UserwisetotalWinLossValue=0.0-UserwisetotalWinLossValue;
						EXUser userbean = userRepo.findByUserid(userid);
						
						if(userbean.getFancylosscommisiontype().equalsIgnoreCase(EXConstants.MatchPlusSessionMinus)){

							
							
							if(!adminid.equalsIgnoreCase(userbean.getAdminid())){
								admin = userRepo.findByid(userbean.getAdminid());							    	 
								adminid=userbean.getAdminid();
							}

							if(!subadminid.equalsIgnoreCase(userbean.getSubadminid())){
								subadmin = userRepo.findByid(userbean.getSubadminid());							    	 
								subadminid=userbean.getSubadminid();
							}
							subadminbalance=subadmin.getSecondrybanlce();
							
							 if subadmin create direct client
							if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
								dealerid="0";
								masterid="0";
								supermasterid="0";
								dealer =null;
								master=null;
								supermaster=null;
							}
							 if subadmin create direct super agent and he create direct client
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){
								
								supermaster = null;							    	 
								supermasterid="0";
								master =null;							    	 
								dealer = null;								    	 
								dealerid="0";
								
								if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
									master = userRepo.findByid(userbean.getMasterid());							    	 
									masterid=userbean.getMasterid();
								}
								masterBalance = master.getSecondrybanlce();
							}		

							 if subadmin create direct agent and he create direct client
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){
								
								supermaster = null;							    	 
								supermasterid="0";
								master =null;							    	 
								masterid = "0";
								if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
									dealer = userRepo.findByid(userbean.getDealerid());							    	 
									dealerid=userbean.getDealerid();
								}
								dealerBalance = dealer.getSecondrybanlce();
							}
							  if subadmin create direct master and he create direct Agent And  he createClient	
								else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){
									
								supermaster = null;							    	 
								supermasterid="0";
								
								if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
									master = userRepo.findByid(userbean.getMasterid());							    	 
									masterid=userbean.getMasterid();
								}
								
								if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
									dealer = userRepo.findByid(userbean.getDealerid());							    	 
									dealerid=userbean.getDealerid();
								}
								masterBalance = master.getSecondrybanlce();
								dealerBalance = dealer.getSecondrybanlce();
							}
							 if supermaster create direct client 
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){
								
								if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
									supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
									supermasterid=users.getSupermasterid();
								}
								master =null;							    	 
								masterid = "0";
								dealer = null;								    	 
								dealerid="0";
								supermasterbalance =supermaster.getSecondrybanlce();
							}
							if supermaster create direct agent and her crrate users
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){
								
								if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
									supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
									supermasterid=users.getSupermasterid();
								}
								master =null;							    	 
								masterid = "0";
								if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
									dealer = userRepo.findByid(userbean.getDealerid());							    	 
									dealerid=userbean.getDealerid();
								}
								supermasterbalance =supermaster.getSecondrybanlce();
								dealerBalance = dealer.getSecondrybanlce();
							}
							if super stokist crreat Master  and he create users
							else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){
								
							
								if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
									supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
									supermasterid=userbean.getSupermasterid();
								}

								if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
									master = userRepo.findByid(userbean.getMasterid());							    	 
									masterid = users.getMasterid();
								}
								dealer = null;								    	 
								dealerid="0";
								supermasterbalance =supermaster.getSecondrybanlce();
								masterBalance = master.getSecondrybanlce();
							}else{
								if(!supermasterid.equalsIgnoreCase(userbean.getSupermasterid())){
									supermaster = userRepo.findByid(userbean.getSupermasterid());							    	 
									supermasterid=userbean.getSupermasterid();
								}

								if(!masterid.equalsIgnoreCase(userbean.getMasterid())){
									master = userRepo.findByid(userbean.getMasterid());							    	 
									masterid = userbean.getMasterid();
								}

								if(!dealerid.equalsIgnoreCase(userbean.getDealerid())){
									dealer = userRepo.findByid(userbean.getDealerid());							    	 
									dealerid=users.getDealerid();
								}
								
								supermasterbalance =supermaster.getSecondrybanlce();
								masterBalance = master.getSecondrybanlce();
								dealerBalance = dealer.getSecondrybanlce();
							}

							
							if(userbean!=null){

								
								adminPartnership = Math.round((UserwisetotalWinLossValue*(users.getAdminpartership().doubleValue()/100.0f))*100.0)/100.0;
								subadminPartnership = Math.round((UserwisetotalWinLossValue*(users.getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0;
								supermasterPartnership = Math.round((UserwisetotalWinLossValue*(users.getSupermastepartnership().doubleValue()/100.0f))*100.0)/100.0;
								masterpartnership = Math.round((UserwisetotalWinLossValue*(users.getMasterpartership().doubleValue()/100.0f))*100.0)/100.0;
								dealerpartnership = Math.round((UserwisetotalWinLossValue*(users.getDelearpartership().doubleValue()/100.0f))*100.0)/100.0;
								
								if(subadmin.getFancywin().doubleValue()<0.25){
									
									adminPartnership =0.0;
									adminCommission=0.0;
								
								}
								
								userCommission = Math.round((UserwisetotalWinLossValue*(userbean.getFancyloss().doubleValue()/100.0f))*100.0)/100.0;
								adminCommission = Math.round((adminPartnership*(subadmin.getOddsloss().doubleValue()/100.0f))*100.0)/100.0;
						
								if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbac)){
									if(adminCommission>0.0){
									  	previousbal =adminBalance;
					    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
					    				
					    				previousbal =subadminbalance;
					    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
					    				 adminCommission= 0.0-adminCommission;
					                    
					                }
									if(userCommission>0.0){
					                	 previousbal= subadminbalance;
					                	 subadminbalance = subadminbalance.subtract(BigDecimal.valueOf(userCommission));
										 subadminCommission =0.0-userCommission;
										 
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										 	
										}else{
											if(adminCommission<0.0){
												subadminCommission =-adminCommission;
											}
											
										}
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbadc)){

									
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									if(dealer.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									
									if(adminCommission>0.0){
										
										  	previousbal =adminBalance;
						    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
						    				
						    				previousbal =subadminbalance;
						    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
						    				 adminCommission= 0.0-adminCommission;
						    			
						                }
									if(subadminCommission>0.0){
					                	previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =dealerBalance;
					     				dealerBalance = dealerBalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					                 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                	 }
					                	
					                 }
									if(userCommission>0.0){
					                	 previousbal =dealerBalance;
					                	 dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
										 dealerCommission =0.0-userCommission;
										 
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										
										}else{
											if(subadminCommission<0.0){
												dealerCommission =-subadminCommission;
											}
											
										} 
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamc)){

									 
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									if(master.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
									
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
											
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									 
									 if(adminCommission>0.0){
											//setProfitnLossCommRecived(subadmin, adminCommission);
						                	previousbal =adminBalance;
						    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
						    			
						    				previousbal =subadminbalance;
						    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
						    				adminCommission= 0.0-adminCommission;
						    			
						                }
									 if(subadminCommission>0.0){
					                	 //setProfitnLossCommRecived(master, subadminCommission);
					                	previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =masterBalance;
					     				masterBalance = masterBalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					                 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                	 }
					                	
					                 } 
									 if(userCommission>0.0){
					                	 previousbal =masterBalance;
					                	 masterBalance = masterBalance.subtract(BigDecimal.valueOf(userCommission));
										 masterCommission =0.0-userCommission;
										
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										
										}else{
											if(subadminCommission<0.0){
												masterCommission =-subadminCommission;
											}
											
										}
									
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)){

									
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
										
									}
									if(supermaster.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									
									if(adminCommission>0.0){
										//setProfitnLossCommRecived(subadmin, adminCommission);
					                	previousbal =adminBalance;
					    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
					    				
					    				previousbal =subadminbalance;
					    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
					    				 adminCommission= 0.0-adminCommission;
					    			
					                }
									if(subadminCommission>0.0){
					                	 //setProfitnLossCommRecived(supermaster, subadminCommission);
					                	 previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					                 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                	 }
					                	
					                 } 
									if(userCommission>0.0){
					                	 previousbal =supermasterbalance;
					                	 supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(userCommission));
										 supermasterCommission =0.0-userCommission;
										 
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										
										}else{
											if(supermasterCommission<0.0){
												supermasterCommission =-subadminCommission;
											}
											
										}
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)){


									Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+masterpartnership;
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									
									if(master.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									if(dealer.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										masterpartnership =0.0;
										dealerCommission=0.0;
										totaluplinepartenershi1  = adminPartnership+subadminPartnership+masterpartnership;
										masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}else{
										masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									
									if(adminCommission>0.0){
											//setProfitnLossCommRecived(subadmin, adminCommission);
						                	previousbal =adminBalance;
						    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
						    				
						    				previousbal =subadminbalance;
						    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
						    				  adminCommission= 0.0-adminCommission;
						    			
						                }
									 if(subadminCommission>0.0){
										 //setProfitnLossCommRecived(master, subadminCommission);
					                	 previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =masterBalance;
					     				masterBalance = masterBalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					                 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                	 }
					                	
					                 }
									 if(masterCommission>0.0){
											previousbal =masterBalance;
											masterBalance = masterBalance.subtract(BigDecimal.valueOf(masterCommission));
											

											previousbal =dealerBalance;
											dealerBalance = dealerBalance.add(BigDecimal.valueOf(masterCommission));
											masterCommission =0.0-masterCommission;
						                }else{
						                
						                	
						                	if(subadminCommission<0.0){
						                		masterCommission=-subadminCommission;
						                	}
						                	
						                } 
									 if(userCommission>0.0){
											previousbal =dealerBalance;
											dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
											dealerCommission =0.0-userCommission;
											
											 previousbal = userbean.getSecondrybanlce();
											 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
											
										       
										}else{
											if(masterCommission<0.0){
												dealerCommission =-masterCommission;
											}
											
										} 
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)){

									
									Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership;
									Double totaluplinepartenershi2  = adminPartnership+subadminPartnership+supermasterPartnership;
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									if(supermaster.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										   		
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									if(master.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										supermasterPartnership =0.0;
										masterCommission=0.0;
										totaluplinepartenershi2  = adminPartnership+subadminPartnership+supermasterPartnership;
										supermasterCommission=Math.round((totaluplinepartenershi2.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}else{
										supermasterCommission=Math.round((totaluplinepartenershi2.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
									
									}
									if(dealer.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										supermasterPartnership =0.0;
										masterpartnership =0.0;
										dealerCommission=0.0;
										totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership+masterpartnership;
										masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}else{
										
										masterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									
									
									if(adminCommission>0.0){
											previousbal =adminBalance;
						    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
						    				
						    				previousbal =subadminbalance;
						    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
						    				 adminCommission= 0.0-adminCommission;
						    			
						                }
									
									 if(subadminCommission>0.0){
					                	 previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					     			  }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                		
					                	 }
					                	
					                 } 
									 
									 if(supermasterCommission>0.0){
					                	 previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
					     				

					     				previousbal =masterBalance;
					     				masterBalance = masterBalance.add(BigDecimal.valueOf(supermasterCommission));
					     				
					     				supermasterCommission =0.0-supermasterCommission;
					     				
					                 }else{
					                	 if(subadminCommission<0.0){
					                		 supermasterCommission=-subadminCommission;
					                		
					                	 }
					                	
					                 }
									 if(masterCommission>0.0){
											
											previousbal =masterBalance;
											masterBalance = masterBalance.subtract(BigDecimal.valueOf(masterCommission));
											

											previousbal =dealerBalance;
											dealerBalance = dealerBalance.add(BigDecimal.valueOf(masterCommission));
											masterCommission =0.0-masterCommission;
											
						                }else{
						                	if(supermasterCommission<0.0){
						                		masterCommission=-supermasterCommission;
						                	
						                	}
						                	
						                }
									 
									 if(userCommission>0.0){
											previousbal =dealerBalance;
											dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
											dealerCommission =0.0-userCommission;
											
											 previousbal = userbean.getSecondrybanlce();
											 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
											 
											
										}else{
											if(masterCommission<0.0){
												dealerCommission =-masterCommission;
												
											}
											
										}
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)){

									
									Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									if(supermaster.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									if(master.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										supermasterPartnership =0.0;
										masterCommission=0.0;
										totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
										supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
									
									}else{
										supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(master.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
									
									}
									
									
									if(adminCommission>0.0){
											previousbal =adminBalance;
						    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
						    				
						    				previousbal =subadminbalance;
						    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
						    				 adminCommission= 0.0-adminCommission;
						    			
						                }
									 if(subadminCommission>0.0){
					                	previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					     			 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                		
					                	 }
					                	
					                 }
									 if(supermasterCommission>0.0){
					                	 previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
					     			

					     				previousbal =masterBalance;
					     				masterBalance = masterBalance.add(BigDecimal.valueOf(supermasterCommission));
					     				
					     				supermasterCommission =0.0-supermasterCommission;
					     			 }else{
					                	 if(subadminCommission<0.0){
					                		 supermasterCommission=-subadminCommission;
					                		
					                	 }
					                	
					                 }
									 if(userCommission>0.0){
					                	 previousbal =masterBalance;
										 masterBalance = masterBalance.subtract(BigDecimal.valueOf(userCommission));
										 masterCommission =0.0-userCommission;
										 
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										
										}else{
											if(masterCommission<0.0){
												masterCommission =-supermasterCommission;
											}
											
										} 
								
								}else if(userbean.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)){

									
									Double totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
									Double totaluplinepartenership  = adminPartnership+subadminPartnership;
									
									if(subadmin.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										adminCommission=0.0;
										
									}
									if(supermaster.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										subadminCommission=0.0;
										totaluplinepartenership  = adminPartnership+subadminPartnership;
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
											
									}
									else{
										subadminCommission = Math.round((totaluplinepartenership.doubleValue()*(supermaster.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									if(dealer.getFancywin().doubleValue()<0.25){
										adminPartnership =0.0;
										subadminPartnership =0.0;
										supermasterPartnership =0.0;
										dealerCommission=0.0;
										totaluplinepartenershi1  = adminPartnership+subadminPartnership+supermasterPartnership;
										supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}else{
										supermasterCommission=Math.round((totaluplinepartenershi1.doubleValue()*(dealer.getFancywin().doubleValue()/100.0f))*100.0)/100.0;
										
									}
									
									
									if(adminCommission>0.0){
											//setProfitnLossCommRecived(subadmin, adminCommission);
							                	previousbal =adminBalance;
							    				adminBalance = adminBalance.subtract(BigDecimal.valueOf(adminCommission));
							    				
							    				previousbal =subadminbalance;
							    				subadminbalance =new BigDecimal(subadminbalance.toString()).add(new BigDecimal(adminCommission.toString()));
							    				  adminCommission= 0.0-adminCommission;
							    			
							                }
									 if(subadminCommission>0.0){
					                	 //setProfitnLossCommRecived(supermaster, subadminCommission);
					                	 previousbal =subadminbalance;
					     				subadminbalance =subadminbalance.subtract(new BigDecimal(subadminCommission));
					     				

					     				previousbal =supermasterbalance;
					     				supermasterbalance = supermasterbalance.add(BigDecimal.valueOf(subadminCommission));
					     				subadminCommission =0.0-subadminCommission;
					                 }else{
					                	 if(adminCommission<0.0){
					                		 subadminCommission =-adminCommission;
					                	 }
					                	
					                 }
									 if(supermasterCommission>0.0){
						            	   //setProfitnLossCommRecived(dealer, supermasterCommission);
						                	 previousbal =supermasterbalance;
						     				supermasterbalance = supermasterbalance.subtract(BigDecimal.valueOf(supermasterCommission));
						     				

						     				previousbal =dealerBalance;
						     				dealerBalance = dealerBalance.add(BigDecimal.valueOf(supermasterCommission));
						     				
						     				supermasterCommission =0.0-supermasterCommission;
						                 }else{
						                	 if(subadminCommission<0.0){
						                		 supermasterCommission=-subadminCommission;
						                	 }
						                	
						                 } 
									 
									 if(userCommission>0.0){
					                	 previousbal =dealerBalance;
					                	 dealerBalance = dealerBalance.subtract(BigDecimal.valueOf(userCommission));
										 dealerCommission =0.0-userCommission;
										 
										 previousbal = userbean.getSecondrybanlce();
										 userbean.setSecondrybanlce(userbean.getSecondrybanlce().add(new BigDecimal(userCommission)));
										
										}else{
											if(supermasterCommission<0.0){
												dealerCommission =-supermasterCommission;
											}
											
										}
								
								}
							
							}
							betstemp.setCommission(adminCommission);
							betstemp.setSubadminCommission(subadminCommission);
							betstemp.setSupermasterCommission(supermasterCommission);
							betstemp.setMasterCommission(masterCommission);
							betstemp.setDealerCommission(dealerCommission);
							betstemp.setUserCommision(userCommission);
							betstemp.setCommisiontype(EXConstants.clientCommssionGiven);
							
						
						if(!adminid.equalsIgnoreCase("0")) {
							admin.setSecondrybanlce(adminBalance);
						}

						if(!subadminid.equalsIgnoreCase("0")) {
							subadmin.setSecondrybanlce(subadminbalance);
						}

						if(!supermasterid.equalsIgnoreCase("0")) {
							supermaster.setSecondrybanlce(supermasterbalance);
						}

						if(!masterid.equalsIgnoreCase("0")) {
							master.setSecondrybanlce(masterBalance);
						}

						if(!dealerid.equalsIgnoreCase("0")) {
							dealer.setSecondrybanlce(dealerBalance);
						}

				  	   if(!adminid.equalsIgnoreCase("0")) {
							userRepo.save(admin);
						}

						if(!subadminid.equalsIgnoreCase("0")) {
							userRepo.save(subadmin);	
						}

						if(!supermasterid.equalsIgnoreCase("0")) {
							userRepo.save(supermaster);
						}

						if(!masterid.equalsIgnoreCase("0")) {
							userRepo.save(master);
						}

						if(!dealerid.equalsIgnoreCase("0")) {
							userRepo.save(dealer);
						}
						placebetrepository.save(betstemp);
						userRepo.save(userbean);
						exchipRepo.saveAll(chipList);
						
						}
					}else{
						UserwisetotalWinLossValue=0.0;
						
					}
					UserwisetotalWinLossValue=0.0;
					
			}

				
			}

		
			
			if(result != null){

				if((fancyService.setBetResult(result.getFancyid(),result.getResult(),result.getId()) == true)){
					responseBean.setTitle("success");
					responseBean.setType("success");
					responseBean.setMessage("Fancy result setted");
					return CompletableFuture.completedFuture("OK");
				}else {
					responseBean.setType("error");
					responseBean.setMessage("Something went wrong");
					responseBean.setTitle("Oops...");
					return CompletableFuture.completedFuture("Bad Request");

				}

			}


		}


		return CompletableFuture.completedFuture("Bad Request");

	
	*/}
}
