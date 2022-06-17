package com.exchange.sheduler;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.EXUserMongo;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXUserMongoRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.config.EXSetResultService;

@Component
@Configuration
@EnableScheduling
@RestController
@RequestMapping("/api")
public class EXFancySheduler  {

	
	@Autowired
	LiabilityRepository userLibRepo;
	
	@Autowired
    private EXSetResultService matchresultservice;
	
	
	@Autowired
	FancyResultRepository fancyresultRepository;

	@Autowired
	FancyRepository fancyRepo;
	
	
	@Autowired
	BetRepository placebetrepository;
	
	@Autowired
	EXUserRepository  userRepo;;

	
	@Autowired
	EXUserMongoRepository userMongoRepo;


	private static org.apache.log4j.Logger log = Logger.getLogger(EXFancySheduler.class);
	
	 
  // @Scheduled(fixedDelay = 10000)
	private void jobSetFancyResult() {
		ArrayList<UserLiability> userlib = new ArrayList<UserLiability>();
		ArrayList<ExFancyResult> fancyList =	fancyresultRepository.findByresultStatusCron(false);

		//System.out.println("ok");
		if(fancyList.size()>0) {
			for(ExFancyResult bean:fancyList) {

				
					
					
					userlib = userLibRepo.findByMatchidAndMarketidAndIsactive(bean.getMatchid(),bean.getFancyid(),true);
					matchresultservice.setFancyResultsCron(userlib, bean);
					
				   if(userlib.size() == 0) {
						
					   bean.setResultStatusCron(true);
					   bean.setIsprofitlossclear(true);
					   fancyresultRepository.save(bean);

					   
					   MatchFancy mfbean = new MatchFancy();
						mfbean = fancyRepo.findByFancyid(bean.getFancyid());
						mfbean.setIsresultFinished(true);
						mfbean.setStatus("CLOSE");
						mfbean.setIsActive(false);
						fancyRepo.save(mfbean);
						
						
					}

					
				
				



			}
		}
	}

	
	//  @Scheduled(fixedDelay = 20000)
		private void Mongo() {
			
			ArrayList<EXUser> usrlist = (ArrayList<EXUser>) userRepo.findAll();
			//System.out.println("ok");
			if(usrlist.size()>0) {
				for(EXUser bean:usrlist) {
					
					System.out.println(bean.getUserid());
					EXUserMongo userbeanMongo = new EXUserMongo();
					BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
					try {
						BeanUtils.copyProperties(userbeanMongo, bean);
						userMongoRepo.save(userbeanMongo);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}


	/*	  @Transactional
		  @Scheduled(fixedDelay = 20000)
			private void jobUpdateBalance() {
			  
			  ArrayList<EXUser> list = userRepo.findByUsertype(3);
			  if(list.size()>0) {
				  for(EXUser bean : list) {
					  System.out.println(bean.getAvailableBalanceWithPnl());
						
					  bean.setAvailableBalanceWithPnl(bean.getAvailableBalanceWithPnl()+1);
					  EXUser ubean = userRepo.findByUserid(bean.getUserid()) ;
					  System.out.println(ubean.getAvailableBalanceWithPnl());
					  userRepo.save(bean);
				  }
			  }
		  }

	*/
		
		  

		 // @Scheduled(fixedDelay = 2000)
			private void jobSetFancyResultBetsUpdate() throws Exception {
			
				
			  String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

		        //Add Telegram token (given Token is fake)
		        String apiToken = "5381892559:AAFWnIbC8BKpGquUnStM72exxV-ydMvVt7E";
		      
		        //Add chatId (given chatId is fake)
		        String chatId = "@Kalyanbot1";
		        String text = "Hello world!";

		        urlString = String.format(urlString, apiToken, chatId, text);

		        try {
		            URL url = new URL(urlString);
		            URLConnection conn = url.openConnection();
		            InputStream is = new BufferedInputStream(conn.getInputStream());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				
				
				/*ArrayList<ExFancyResult> fancyList =	fancyresultRepository.findByresultStatusCron(false);

				if(fancyList.size()>0) {
					for(ExFancyResult result:fancyList) {
						
					ArrayList<ExMatchOddsBet> list =	placebetrepository.findByMarketidAndIsactive(result.getFancyid(),true);
					
					 for(ExMatchOddsBet bets :list  ) {
						 
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
						 bets.setIsactive(false);
						 bets.setIsmongodbupdated(true);
						 bets.setResult(result.getResult());
						 placebetrepository.save(bets);
					 }
					
					 result.setResultStatusCron(true);
					 fancyresultRepository.save(result);
					}
				}*/
			}
		
		
	}

		


