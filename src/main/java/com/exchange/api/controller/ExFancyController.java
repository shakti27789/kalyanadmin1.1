package com.exchange.api.controller;

import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.EXFancyRollBackDetail;
import com.exchange.api.bean.EXRsFancyUpdatedResult;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExFancyBook;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.dao.FancyDAO;
import com.exchange.api.repositiry.AppBeanRepo;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CustomSequencesRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXFancyRollBackDetailRepository;
import com.exchange.api.repositiry.EXRsFancyUpdatedResultRepo;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MinMaxBetRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.api.service.EXFancyBetService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.exchange.util.EXGlobalConstantProperties;
import com.google.cloud.firestore.Firestore;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class ExFancyController {

	@Autowired
	FancyRepository fancyRepository;

	@Autowired
	FancyResultRepository fancyresultRepository;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	EXUserRepository userRepo;

	@Autowired
	MinMaxBetRepository maxRepo;

	
	@Autowired
	HttpServletRequest request;

	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	MarketRepository eventMarketRepository;
	
	@Autowired
	EXUserRepository exUserRepository;
	
		DateFormat formatter;
	String date;

	
	EXDateUtil dtUtil = new EXDateUtil();

	@Autowired
	EXChipDetailRepository exchipRepo;
	
	@Autowired
	AppBeanRepo appBeanRepo;
	
	@Autowired
	UserIpRepository ipRepo;
	
	@Autowired
	AppChargeRepo appchargeRepo;
	
	@Autowired
	EXChipDetailRepository exChipDetailRepository;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;
	
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;
	
	
	
	@Autowired
	EventRepository eventRepo;
	
	@Autowired
	ExMatchOddsBetDao betlistDao;
	
	@Autowired
	FancyDAO fancyDAO;
	
	@Autowired
	CustomSequencesRepository custumSeqRepo;
	
	@Autowired
	EXFancyBetService exfancyBetService;
	
	@Autowired
	EXFancyRollBackDetailRepository fancyRollbackDetailRepo;
	
	@Autowired
    EXRsFancyUpdatedResultRepo rsfancyUpdatedRepo;
	

	private Firestore fb;

	@RequestMapping(value = "/getFancy", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancy() {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");
		ArrayList<MatchFancy> list =fancyDAO.getFancy("");

		ArrayList<MatchFancy> fancyMarket2List = new ArrayList<>();
		ArrayList<MatchFancy> fancyMarket3List = new ArrayList<>();
		ArrayList<MatchFancy> oddEvenMarketList = new ArrayList<>();
		ArrayList<MatchFancy> ballByBallMarketList= new ArrayList<>();
		
		
		if (list.size()>0) {
		
			for(MatchFancy mf :list) {
				if(mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market)) {
					fancyMarket2List.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
					fancyMarket3List.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
					oddEvenMarketList.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
					ballByBallMarketList.add(mf);
				}
			}
			
            LinkedHashMap<String,Object> hm= new LinkedHashMap<String,Object>();			
		               
			
			hm.put("fancyMarket2", fancyMarket2List);
			hm.put("fancyMarket3", fancyMarket3List);
			hm.put("oddEvenMarket", oddEvenMarketList);
			hm.put("ballByBallMarket", ballByBallMarketList);
			
			
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No fancy found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getFancyResult/{startpage}/{endpage}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyResult(@PathVariable Integer startpage,@PathVariable Integer endpage ) {
		ResponseBean responseBean = new ResponseBean();
		ArrayList<ExFancyResult> list = new ArrayList<>();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");

        Pageable pageable = PageRequest.of(startpage, endpage, Sort.Direction.DESC, "createdon");
        
        Page<ExFancyResult> page  =  fancyresultRepository.findAll(pageable);
       if ( page.getContent().size()>0) {
			for(ExFancyResult result : list){
				result.setCreatedon(dtUtil.convTimeZone2(result.getCreatedon(), "GMT", "GMT+5:30"));
			}
			list.addAll( page.getContent());
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy result list");
			responseBean.setData(list);
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No result found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/getFancyResultByMatch/{startpage}/{endpage}/{matchId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyResult(@PathVariable Integer startpage,@PathVariable Integer endpage,@PathVariable Integer matchId ) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");

       
        ArrayList<ExFancyResult> list  =  fancyresultRepository.findBymatchid(matchId);
       if ( list.size()>0) {
			for(ExFancyResult result : list){
				//result.setCreatedon(dtUtil.convTimeZone2(result.getCreatedon(), "GMT", "GMT+5:30"));
				 StringBuilder str = new StringBuilder("");
				 
				
				ArrayList<EXRsFancyUpdatedResult> list2 =	rsfancyUpdatedRepo.findByFancyIdAndMatchid(result.getFancyid(), matchId);
				if(list2.size()>0) {
					if(result.getResultdeclareby().equalsIgnoreCase("Api")) {
						str.append("Api Result=>");
						
					}else {
						str.append("Manual Result=>").append(result.getResult());
						str.append("Api Result=>");
					}
					
					for(EXRsFancyUpdatedResult bn :list2) {
						 str.append(bn.getResult()).append(",");
					}
				}else {
					
					if(result.getResultdeclareby().equalsIgnoreCase("Api")) {
						str.append("Api Result=>").append(result.getResult());;
						
					}else {
						str.append("Manual Result=>").append(result.getResult());
					}
				}
				
				result.setResultDetail(str.toString());
			}
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy result list");
			responseBean.setData(list);
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No result found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/placeFancyBet", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> placeFancyBet(@Valid @RequestBody ExMatchOddsBet placebet) throws InterruptedException, ParseException, IllegalAccessException, InvocationTargetException, UnknownHostException, JSONException {



		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXUser user = userRepo.findByUserid(usersession.getUserid());
		if(usersession!=null){
			responseBean = exfancyBetService.placeFancyBet(placebet, user);
			
		}
		
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	
	
	}
	


/*	@RequestMapping(value = "/deleteFancyBet", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFancyBet(@RequestParam Integer id) {

		ResponseBean responseBean = new ResponseBean();
		ExMatchOddsBet placeBet = new ExMatchOddsBet();
		UserLiability liability = new UserLiability();
		DecimalFormat df = new DecimalFormat("#0.00");

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			placeBet = placebetrepository.findByid(id);
			EXUser user = userRepo.findByUserid(placeBet.getUserid());
			Double balance = Double.valueOf(user.getSecondrybanlce().toString());

			if (placeBet != null) {

				
				Date date = new Date();

				placeBet.setIsactive(false);
				placeBet.setIsdeleted(true);
				placeBet.setDeletedBy(usersession.getId().toString());
				placeBet.setUpdatedon(date);

				Double price = placeBet.getPricevalue() / 100;
				Double stake = Double.valueOf(df.format(placeBet.getStake()));
				Double pnl = Double.valueOf(df.format(price * stake));
				liability = liabilityRepository.findByFancyidAndUserid(placeBet.getFancyid(), placeBet.getUserid());

				if (placeBet.getIsback() == true) {
					if (liability.getSelection1() == placeBet.getSelectionid()) {
						liability.setPnl1(liability.getPnl1() - pnl);
						liability.setPnl2(liability.getPnl2() + stake);

					} else if (liability.getSelection2() == placeBet.getSelectionid()) {

						liability.setPnl2(liability.getPnl2() - pnl);
						liability.setPnl1(liability.getPnl1() + stake);

					} else if (liability.getSelection3() == placeBet.getSelectionid()) {
						liability.setPnl3(liability.getPnl3() - pnl);
						liability.setPnl2(liability.getPnl2() + stake);
					}
				} else {
					if (liability.getSelection1() == placeBet.getSelectionid()) {
						liability.setPnl1(liability.getPnl1() + pnl);
						liability.setPnl2(liability.getPnl2() - stake);

					} else if (liability.getSelection2() == placeBet.getSelectionid()) {
						liability.setPnl2(liability.getPnl2() + pnl);
						liability.setPnl1(liability.getPnl1() - stake);

					} else if (liability.getSelection3() == placeBet.getSelectionid()) {
						liability.setPnl3(liability.getPnl3() + pnl);
						liability.setPnl2(liability.getPnl2() - stake);
					}
				}

				liability.setLiability(liability.getPnl1()<liability.getPnl2()? liability.getPnl1(): liability.getPnl2());

				liability.setLiability(Double.valueOf(df.format(0.00-liability.getLiability())));
				Double liabilityDiff;

				liabilityDiff = liability.getLiability() - liabilityRepository.findByFancyidAndUserid(placeBet.getFancyid(), placeBet.getUserid()).getLiability();

				ArrayList<UserLiability> liabilities = liabilityRepository.findByUserid(user.getUserid());
				Double totalLiability = 0.00;
				if (liabilities != null) {
					for (int i = 0; i < liabilities.size(); i++) {
						totalLiability = totalLiability + liabilities.get(i).getLiability();
					}
					totalLiability = totalLiability + liabilityDiff;
				}
				liability.setTotalpnl(liability.getPnl1() > liability.getPnl2() ? liability.getPnl1() : liability.getPnl2());

				liability.setPnl1(Double.valueOf(df.format(liability.getPnl1())));
				liability.setPnl2(Double.valueOf(df.format(liability.getPnl2())));

				if (user != null) {
					user.setSecondrybanlce(BigDecimal.valueOf(Double.valueOf(df.format(balance - liabilityDiff))));
					usersession.setSecondrybanlce(user.getSecondrybanlce());
				}

				if (placebetrepository.save(placeBet) != null && liabilityRepository.save(liability) != null
						&& userRepo.save(user) != null) {
					ArrayList<EXUser> UpdatedList = new ArrayList<>();
					ArrayList<EXChipDetail> ChipsDetails = new ArrayList<>();
					ArrayList<ExMatchOddsBet> BetsList = placebetrepository.findByUseridAndMatchidAndIsactive(placeBet.getUserid(),placeBet.getMatchid(),true);
					if(BetsList.size()==0){
						EXUser admin = null;
						EXUser subadmin = null;
						EXUser supermaster = null;
						EXUser master = null;
						EXUser dealer = null;
						AppCharge newappcharge = appchargeRepo.findByEventidAndUserid(String.valueOf(placeBet.getMatchid()), placeBet.getUserid());
						admin = userRepo.findByid(placeBet.getAdminid());
						subadmin = userRepo.findByid(placeBet.getSubadminid());
						supermaster = userRepo.findByid(placeBet.getSupermasterid());
						master = userRepo.findByid(placeBet.getMasterid());
						dealer = userRepo.findByid(placeBet.getDealerid());
						
						EXChipDetail adminchips = exChipDetailRepository.findByUseridAndParentidAndType(admin.getUserid(),placeBet.getMatchid()+"", "PDC");
						EXChipDetail subadminchips = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(subadmin.getUserid(), placeBet.getMatchid()+"", "PDC",true,"subadmin");
						EXChipDetail subadminchipsminus = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(subadmin.getUserid(), placeBet.getMatchid()+"", "PDC",false,"subadmin");
						
						if(placeBet.getSubadminid().equalsIgnoreCase(placeBet.getSupermasterid()) && placeBet.getSubadminid().equalsIgnoreCase(placeBet.getMasterid()) && placeBet.getSubadminid().equalsIgnoreCase(placeBet.getDealerid())){
							placeBet.setSupermasterid("0");
							placeBet.setMasterid("0");
						}else if(placeBet.getSubadminid().equalsIgnoreCase(placeBet.getSupermasterid())){
							placeBet.setSupermasterid("0");
						}else if(placeBet.getSupermasterid().equalsIgnoreCase(placeBet.getMasterid()) && placeBet.getSupermasterid().equalsIgnoreCase(placeBet.getDealerid())){
							placeBet.setMasterid("0");
						}else if(placeBet.getMasterid().equalsIgnoreCase(placeBet.getDealerid())){
							placeBet.setMasterid("0");
						}
						
						EXChipDetail supermasterchips = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(supermaster.getUserid(), placeBet.getMatchid()+"", "PDC",true,"supermaster");
						EXChipDetail supermasterchipsminus = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(supermaster.getUserid(), placeBet.getMatchid()+"", "PDC",false,"supermaster");
						EXChipDetail masterchips = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(master.getUserid(), placeBet.getMatchid()+"", "PDC",true,"master");
						EXChipDetail masterchipsminus = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(master.getUserid(), placeBet.getMatchid()+"", "PDC",false,"master");
						EXChipDetail dealerchips = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(dealer.getUserid(), placeBet.getMatchid()+"", "PDC",true,"dealer");
						EXChipDetail dealerchipsminus = exChipDetailRepository.findByUseridAndParentidAndTypeAndCrdrAndUsertype(dealer.getUserid(), placeBet.getMatchid()+"", "PDC",false,"dealer");
						if(adminchips==null){
							adminchips.setChips(adminchips.getChips()-subadmin.getMobappcharge());
							adminchips.setUserbalance(admin.getSecondrybanlce());
						}
						
						if(subadminchipsminus==null){
							subadminchipsminus.setChips(subadminchipsminus.getChips()-subadmin.getMobappcharge());
							subadminchipsminus.setUserbalance(subadmin.getSecondrybanlce());
						}
						
						if(!placeBet.getSupermasterid().equals("0")){
							supermaster.setSecondrybanlce(supermaster.getSecondrybanlce().add(BigDecimal.valueOf(supermaster.getMobappcharge())));
							subadmin.setSecondrybanlce(subadmin.getSecondrybanlce().subtract(BigDecimal.valueOf(supermaster.getMobappcharge())));
							
							if(supermasterchipsminus==null){
								supermasterchipsminus.setChips(supermasterchipsminus.getChips()-supermaster.getMobappcharge());
								supermasterchipsminus.setUserbalance(supermaster.getSecondrybanlce());
							}
							
							if(subadminchips==null){
								subadminchips.setChips(subadminchips.getChips()-supermaster.getMobappcharge());
								subadminchips.setUserbalance(subadmin.getSecondrybanlce());
							}
							
						}
						if(!placeBet.getMasterid().equals("0")){
							master.setSecondrybanlce(master.getSecondrybanlce().add(BigDecimal.valueOf(master.getMobappcharge())));
							supermaster.setSecondrybanlce(supermaster.getSecondrybanlce().subtract(BigDecimal.valueOf(master.getMobappcharge())));
							
							if(masterchipsminus == null){
								masterchipsminus.setChips(masterchipsminus.getChips()-master.getMobappcharge());
								masterchipsminus.setUserbalance(master.getSecondrybanlce());
							}
							
							if(supermasterchips == null){
								supermasterchips.setChips(supermasterchips.getChips()-master.getMobappcharge());
								supermasterchips.setUserbalance(supermaster.getSecondrybanlce());
							}
						}
						
						if(!placeBet.getDealerid().equals("0")){
							dealer.setSecondrybanlce(dealer.getSecondrybanlce().add(BigDecimal.valueOf(dealer.getMobappcharge())));
							master.setSecondrybanlce(master.getSecondrybanlce().subtract(BigDecimal.valueOf(dealer.getMobappcharge())));
							user.setSecondrybanlce(user.getSecondrybanlce().add(BigDecimal.valueOf(user.getMobappcharge())));
							dealer.setSecondrybanlce(dealer.getSecondrybanlce().subtract(BigDecimal.valueOf(user.getMobappcharge())));	
							if(dealerchipsminus == null){
								dealerchipsminus.setChips(dealerchipsminus.getChips()-dealer.getMobappcharge());
								dealerchipsminus.setUserbalance(dealer.getSecondrybanlce());
							}
							
							if(dealerchips == null){
								dealerchips.setChips(dealerchips.getChips()-user.getMobappcharge());
								dealerchips.setUserbalance(dealer.getSecondrybanlce());
							}
							
							if(masterchips == null){
								masterchips.setChips(masterchips.getChips()-dealer.getMobappcharge());
								masterchips.setUserbalance(master.getSecondrybanlce());
							}
						}
						subadmin.setSecondrybanlce(subadmin.getSecondrybanlce().add(BigDecimal.valueOf(subadmin.getMobappcharge())));
						admin.setSecondrybanlce(admin.getSecondrybanlce().subtract(BigDecimal.valueOf(subadmin.getMobappcharge())));
						
						UpdatedList.add(subadmin);
						UpdatedList.add(admin);
						if(!placeBet.getSupermasterid().equals("0")){
							UpdatedList.add(supermaster);
						}
						if(!placeBet.getMasterid().equals("0")){
							UpdatedList.add(master);
						}
						if(!placeBet.getDealerid().equals("0")){
							UpdatedList.add(dealer);
						}
						UpdatedList.add(user);
						userRepo.saveAll(UpdatedList);
						if(adminchips!=null){
							ChipsDetails.add(adminchips);
						}
						if(subadminchips!=null){
							ChipsDetails.add(subadminchips);
						}
						
						if(subadminchipsminus!=null){
							ChipsDetails.add(subadminchipsminus);
						}
						
						if(supermasterchips!=null){
							ChipsDetails.add(supermasterchips);
						}
						
						if(supermasterchipsminus!=null){
							ChipsDetails.add(supermasterchipsminus);
						}
						
						if(masterchips!=null){
							ChipsDetails.add(masterchips);
						}
						if(masterchipsminus!=null){
							ChipsDetails.add(masterchipsminus);
						}
						
						if(dealerchips!=null){
							ChipsDetails.add(dealerchips);
						}
						
						if(dealerchipsminus!=null){
							ChipsDetails.add(dealerchipsminus);
						}
						
						if(!ChipsDetails.isEmpty()){
							exChipDetailRepository.saveAll(ChipsDetails);
						}
						exChipDetailRepository.deleteByUseridAndParentidAndType(user.getUserid(), String.valueOf(placeBet.getMatchid()), "PDC");
						appchargeRepo.deleteById(newappcharge.getId());
					}

					responseBean.setType("success");
					responseBean.setMessage("Bet deleted successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
				}
			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
	}*/



	@RequestMapping(value = "/rollbackFancyBet", method = RequestMethod.POST)
	public ResponseEntity<?> rollbackFancyBet(@RequestParam Integer id) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		ExMatchOddsBet placeBet = new ExMatchOddsBet();
		UserLiability liability = new UserLiability();
		DecimalFormat df = new DecimalFormat("#0.00");

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			responseBean =exfancyBetService.rollBackFanyBet(id);
		}
		
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getFancyByMatch", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyByMatch(@RequestParam Integer matchid) {
		ResponseBean responseBean = new ResponseBean();
		
		ArrayList<MatchFancy> fancyMarket2List = new ArrayList<>();
		ArrayList<MatchFancy> fancyMarket3List = new ArrayList<>();
		ArrayList<MatchFancy> oddEvenMarketList = new ArrayList<>();
		ArrayList<MatchFancy> ballByBallMarketList= new ArrayList<>();
		
		 
		//ArrayList<MatchFancy> list = new ArrayList<>();

		//list = fancyRepository.findByEventidAndStatusOrEventidAndStatusOrEventidAndStatusOrEventidAndStatus(Integer.parseInt(matchid), "OPEN",Integer.parseInt(matchid), "SUSPENDED",Integer.parseInt(matchid),"",Integer.parseInt(matchid),"Ball Running");
		
		ArrayList<MatchFancy> list =fancyDAO.getFancyByMatchId(matchid);
		
		if (!list.isEmpty()) {
		
			for(MatchFancy mf :list) {
				
				
				if(mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy2Market)) {
					fancyMarket2List.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.Fancy3Market)) {
					fancyMarket3List.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.OddEvenMarket)) {
					oddEvenMarketList.add(mf);
				}else if(mf.getOddstype().equalsIgnoreCase(EXConstants.Ball)) {
					ballByBallMarketList.add(mf);
				}
			}
			
            LinkedHashMap<String,Object> hm= new LinkedHashMap<String,Object>();			
		               
			
			hm.put("fancyMarket2", fancyMarket2List);
			hm.put("fancyMarket3", fancyMarket3List);
			hm.put("oddEvenMarket", oddEvenMarketList);
			hm.put("ballByBallMarket", ballByBallMarketList);
			
			
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No fancy found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	/*@RequestMapping(value = "/getFancyBook", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyBook(@RequestParam String fancyid) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			fancyList.addAll(placebetrepository.findByUseridAndFancyidAndIsactive(usersession.getUserid(), fancyid,true));
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
						min = odds-10;
						if(min<0) min=0;
						max=odds+5;
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
						
						if(odds+5<=fancyBook.get(fancyBook.size()-1).getRates()){
							max = fancyBook.get(fancyBook.size()-1).getRates()-min;
						}else{
							max=((odds+5) - fancyBook.get(fancyBook.size()-1).getRates());
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
										if(min<odds)fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()+pnl);			
										else fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()-liab);

									}									
								
								min++;
								
						}
						
					}
				}
				
				ArrayList<ExFancyBook> fancybook = new ArrayList<>();
				if(!fancyBook.isEmpty()){
					Integer range = 0;
					Integer start = 0;
					Double pnl = 0.00;
					ExFancyBook book = new ExFancyBook();
					for(int i=0;i<fancyBook.size();i++){
						if(pnl.equals(fancyBook.get(i).getPnl())){
							range = fancyBook.get(i).getRates();
							if(book.getPnl()== null){
								book.setPnl(pnl);
							}
						}else{
							if(book.getPnl()!= null){
								book.setRateRange(start.toString()+" to "+range.toString());
								fancybook.add(book);	
							}
							
							book = new ExFancyBook();
							pnl = fancyBook.get(i).getPnl();
							range = fancyBook.get(i).getRates();
							start = range;
							
							book.setPnl(pnl);
						}
					}
					if(book.getPnl()!= null){
						book.setRateRange(start.toString()+" to "+range.toString());
						fancybook.add(book);	
					}
				}
				
				return new ResponseEntity<Object>(fancyBook, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);	
		}*/
	
	@RequestMapping(value = "/setFancyPause", method = RequestMethod.PUT)
	public ResponseEntity<Object> setFancyPause(@RequestParam String eventid) {
		ResponseBean responseBean = new ResponseBean();
		ArrayList<MatchFancy> fancies = fancyRepository.findByEventidAndIsActive(Integer.parseInt(eventid),true);
		
		if(fancies!=null){
			for(int i =0;i<fancies.size();i++){
				if(fancies.get(i).getIsplay()==null||fancies.get(i).getIsplay()==true){
					fancies.get(i).setIsplay(false);
				}else{
					fancies.get(i).setIsplay(true);
				}
			}
			
			if(fancyRepository.saveAll(fancies)!=null){
				responseBean.setTitle("success");
				responseBean.setType("success");
				responseBean.setMessage("Fancy has been Play/Paused");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);	
	}
	
/*	@RequestMapping(value = "/getParentFancyBook", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentFancyBook(@RequestParam String fancyid,Integer back,Integer lay ) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#00");
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			if(usersession.getUsertype()==4){
				fancyList= betlistDao.getFancyBook("adminid", usersession.getId().toString(), fancyid);			
			}else if(usersession.getUsertype()==1){
				fancyList =betlistDao.getFancyBook("masterid", usersession.getId().toString(), fancyid);		
			}else if(usersession.getUsertype()==0){
				fancyList = betlistDao.getFancyBook("supermasterid", usersession.getId().toString(), fancyid);		
			}else if(usersession.getUsertype()==5){
				fancyList =betlistDao.getFancyBook("subadminid", usersession.getId().toString(), fancyid);						
			}else if(usersession.getUsertype()==2){
				fancyList= betlistDao.getFancyBook("dealerid", usersession.getId().toString(), fancyid);		
			}

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
						min = lay-25;
						if(min<0) min=0;
						max=back+25;
						for(int j=min;j<=max;j++){
							ExFancyBook book = new ExFancyBook();
							
							book.setRates(Integer.parseInt(df.format(j)));
							book.setPnl(0.0);
							fancyBook.add(book);
						}
						
						first=1;
					}
				}

				
				 for(int j=0; j<=fancyBook.size()-1;j++){
	                	
	                	
	                	for(int i=0;i<=fancyList.size()-1;i++){
	                		
	                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
	                			
	                			if(fancyList.get(i).getIsback()==false){
	                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
	                    			
	                    		}else{
	                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getLiability())));
	                    			
	                    			
	                    		}
	                			
	                		}else{
	                			
	                			
	                			if(fancyList.get(i).getIsback()==false){
	                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+fancyList.get(i).getLiability())));
	                    			
	                    		
	                    			
	                    		}else{
	                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-fancyList.get(i).getLiability())));
	                    			
	                    		}
	                		}
	                	}
	                }
				 for(int j=0; j<=fancyBook.size()-1;j++){
					
					 for(int i=0;i<=fancyList.size()-1;i++){

						 if(usersession.getUsertype()==4){
							 fancyBook.get(j).setPnl(Double.valueOf(df.format((fancyBook.get(j).getPnl())*(fancyList.get(i).getAdmin().doubleValue()/100.0f)*100.0/100.0)));	
							 
							 
							 
						 }else if(usersession.getUsertype()==1){
							 fancyBook.get(j).setPnl( Double.valueOf(df.format((fancyBook.get(j).getPnl())*(fancyList.get(i).getMaster().doubleValue()/100.0f)*100.0/100.0)));	
							

						 }else if(usersession.getUsertype()==0){
							 fancyBook.get(j).setPnl( Double.valueOf(df.format((fancyBook.get(j).getPnl())*(fancyList.get(i).getSupermaster().doubleValue()/100.0f)*100.0/100.0)));	
							
						 }else if(usersession.getUsertype()==5){
							 fancyBook.get(j).setPnl( Double.valueOf(df.format((fancyBook.get(j).getPnl())*(fancyList.get(i).getSubadmin().doubleValue()/100.0f)*100.0/100.0)));
							

						 }else if(usersession.getUsertype()==2){
							 Double.valueOf(df.format((fancyBook.get(j).getPnl())*(fancyList.get(i).getDealer().doubleValue()/100.0f)*100.0/100.0));

						 }
					break;
					 } 
				 }
				
				
				if(!fancyBook.isEmpty()){
		
					if(!fancyBook.isEmpty()){
						return new ResponseEntity<Object>(fancyBook, HttpStatus.OK);					
					}else{
						return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);	
					}

				}else{
					return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);	
				} 
			}
		}
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);	
		}
	*/
	
	@Transactional
	@RequestMapping(value = "/suspendFancy", method = RequestMethod.DELETE)
	public ResponseEntity<?> suspendFancy(@RequestParam String fancyid,@RequestParam Integer eventid ) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
        MatchFancy fancybean = fancyRepository.findByFancyidAndEventid(fancyid, eventid);
     	if (usersession != null) {
			
			ArrayList<UserLiability> liblist =liabilityRepository.findByMarketidAndIsactiveAndIsresultsetAndMatchid(fancyid,true,false,eventid);
			if(liblist.size()>0){
				
				
				for(UserLiability lib  : liblist){
					
				
					ArrayList<ExMatchOddsBet> placeBetList = placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(lib.getUserid(),fancyid,EXConstants.Fancy,true,eventid);
					
					for(ExMatchOddsBet placeBet :placeBetList){
						
						if (placeBet != null) {

							placeBet.setIsactive(false);
							placeBet.setUpdatedon(new Date());
							placeBet.setAddetoFirestore(false);
							placeBet.setStatus(EXConstants.fancySuspend);
							placeBet.setIsmongodbupdated(false);
							placebetrepository.save(placeBet);
							

						}
					}
					
				
					lib.setIsactive(false);
					lib.setUpdatedon(new Date());
					lib.setAddetoFirestore(false);
					lib.setIsmongodbupdated(false);
					liabilityRepository.save(lib);
					
					
					
				     
				        RestTemplate restTemplate = new RestTemplate();
				    	HttpClient httpClient = HttpClientBuilder.create().build();
				    	restTemplate.setRequestFactory(new 
				    	HttpComponentsClientHttpRequestFactory(httpClient));    
				    	HttpHeaders reqHeaders = new HttpHeaders();
				    	HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", reqHeaders);
				    	ResponseEntity<String> responseEntity=restTemplate.exchange(EXConstants.BfToken+"event-odds/declare-fancy/"+fancyid, HttpMethod.PATCH, 
				    	requestEntity, String.class);
			
					  
					
				}
			}

		    	 
                  	 if(fancybean!=null){
                  		fancybean.setIssuspendedByAdmin(true);
                  		fancybean.setStatus(EXConstants.fancySuspend);
                  		fancybean.setIsActive(false);
                  		fancybean.setSuspendedBy(usersession.getUserid());
                  		fancyRepository.save(fancybean);
              
              	 
               }
		
                  	responseBean.setType("success");
            		responseBean.setMessage("Facy Suspended successfully");
            		responseBean.setTitle("success");
            		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			
		}
		
		 
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/rollbackSuspendedFancy", method = RequestMethod.PUT)
	public ResponseEntity<?> rollbackSuspendedFancy(@RequestParam String fancyid,@RequestParam Integer eventid ) throws Exception {


		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		 MatchFancy fancybean = fancyRepository.findByFancyidAndEventid(fancyid,eventid);
         if (usersession != null) {
			
			ArrayList<UserLiability> liblist =liabilityRepository.findByMarketidAndIsactiveAndIsresultsetAndMatchid(fancyid,false,false,eventid);
			if(liblist.size()>0){
				
				
				for(UserLiability lib  : liblist){
					
					EXUser user = userRepo.findByUserid(lib.getUserid());
					
					ArrayList<ExMatchOddsBet> placeBetList = placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchidAndStatus(lib.getUserid(),fancyid,EXConstants.Fancy,false,eventid,EXConstants.fancySuspend);
					
					for(ExMatchOddsBet placeBet :placeBetList){
						
						if (placeBet != null) {

							placeBet.setIsactive(true);
							placeBet.setStatus(EXConstants.fancyOpen);
							placeBet.setUpdatedon(new Date());
							placeBet.setIsmongodbupdated(false);
							placebetrepository.save(placeBet);
							

						}
					}
					
					lib.setIsactive(true);
					lib.setUpdatedon(new Date());
					lib.setIsmongodbupdated(false);
					liabilityRepository.save(lib);
					
					
			/*		ClassLoader classLoader = getClass().getClassLoader(); 
					InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
					FirebaseOptions firebaseOptions;
				
					firebaseOptions = new FirebaseOptions.Builder()
							.setCredentials(GoogleCredentials.fromStream(inputStream))
							.setDatabaseUrl(EXConstants.firestoreDb)
							.build();
					if(FirebaseApp.getApps().isEmpty())
	 					FirebaseApp.initializeApp(firebaseOptions);
	 				 fb  = com.google.firebase.cloud.FirestoreClient.getFirestore();
	 				 DocumentReference docRef = fb.collection(eventid.toString()).document(fancyid);
	 				 ApiFuture<DocumentSnapshot> future = docRef.get();
	 				
	 				HashMap<String,Object> hm2 = new HashMap<String,Object>();
	 					
	 				 DocumentSnapshot document = future.get();
					 if (document.exists()) {
						 hm2 =  (HashMap<String, Object>) document.getData().get("Fancy"); 
		  				 hm2.put("isActive", true);
					 }
	 				 	
	 				
	 				LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
	 				Map<String, Object> data = new HashMap<>();
	 				
	 				data.put("Fancy", hm2);
	 				ApiFuture<WriteResult> result1 = docRef.update(data);
	 				*/
	 				
				}
			}

		   if(fancybean!=null){
			 fancybean.setIssuspendedByAdmin(false);
			 fancybean.setIsActive(true);
			 fancybean.setStatus(EXConstants.fancyOpen);
    		 fancyRepository.save(fancybean);}
		
			responseBean.setType("success");
			responseBean.setMessage("Facy Suspended successfully");
			responseBean.setTitle("success");
			return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
			
		}
	
		
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
	
	}
	
	@RequestMapping(value = "/getSuspendedFancy", method = RequestMethod.GET)
	public ResponseEntity<Object> getSuspendedFancy() {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");
		
		ArrayList<MatchFancy> list = fancyRepository.findByStatus(EXConstants.fancySuspend);
		if (!list.isEmpty()) {
			
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy result list");
			responseBean.setData(list);
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No result found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/findByEventidAndStatus/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Object> findByEventidAndStatus(@PathVariable Integer eventId) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");
		
		ArrayList<MatchFancy> list = fancyRepository.findByEventidAndStatus(eventId,EXConstants.fancySuspend);
		if (!list.isEmpty()) {
			
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy result list");
			responseBean.setData(list);
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		} else if (list.isEmpty()) {
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("No result found");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value = "/getActiveFncyList", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveFncyList(@RequestParam String eventid) {
		ArrayList<MatchFancy> fancies = fancyRepository.findByEventidAndIsActive(Integer.parseInt(eventid),true);
		
		if(fancies.size()>0){
			
		return new ResponseEntity<Object>(fancies, HttpStatus.OK);
			
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);	
	}
	
	@RequestMapping(value = "/getActiveFancyBet", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveFancyBet(@RequestParam String fancyid) {
		ArrayList<ExMatchOddsBet> fancies = placebetrepository.findByMarketidAndIsactiveAndIsdeletedOrderByMatchedtimeDesc(fancyid,true,false);
		
		EXDateUtil dtUtil = new EXDateUtil();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(fancies.size()>0){
			for(int i=0;i<fancies.size();i++){
				fancies.get(i).setDate(dtUtil.convTimeZone2(dateFormater.format(fancies.get(i).getCreatedon()), "GMT+5:30", "GMT+5:30"));
			}
			
		return new ResponseEntity<Object>(fancies, HttpStatus.OK);
			 
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);	
	}
	
	
	@RequestMapping(value = "/deleteFancyBetBySeries", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFancyBetBySeries(@RequestParam String fancyid,@RequestParam Integer start,@RequestParam Integer end,@RequestParam String deletePass) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		//ExMatchOddsBet placeBet = new ExMatchOddsBet();
		UserLiability liability = new UserLiability();
		
		DecimalFormat df = new DecimalFormat("#0.00");

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			if(deletePass.equalsIgnoreCase("rahulgandhi")) {
				responseBean	 = exfancyBetService.deleteFancyBetBySeries(fancyid, start, end, usersession);
			}
			
		}
		
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/deleteFancyBetById", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFancyBetById(@RequestParam String fancyid,@RequestParam Integer id) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		//ExMatchOddsBet placeBet = new ExMatchOddsBet();
		UserLiability liability = new UserLiability();
		
		DecimalFormat df = new DecimalFormat("#0.00");

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			
			responseBean = exfancyBetService.deleteFancyBetById(fancyid, id, usersession);
		}
		
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getFancyBetBySeries", method = RequestMethod.DELETE)
	public ResponseEntity<Object> getFancyBetBySeries(@RequestParam String fancyid,@RequestParam Integer start,@RequestParam Integer end) {

		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			ArrayList<ExMatchOddsBet> betlist =  betlistDao.getfancybetBySeries(fancyid, start, end,false);
			if(betlist.size()>0){
				return new ResponseEntity<Object>(betlist,HttpStatus.OK);
			}
			
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
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
		Integer skybackprice =0;
		Integer daimondbackprice =0;
		Integer skylayprice =0;
		Integer daimondlayprice =0;
		if(bean!=null){
			try{ /*if main fancy id daimond*/
				
				if(bean.getFancyprovider().equalsIgnoreCase(EXConstants.Diamond)){
					fancystatusdaimondprice=0;
					String result = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"//getSession/"+eventid+"/daimond", String.class);
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
	
	public Double getFancyBookLowestValue(String fancyid,String userid,Integer matchId) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		if(usersession!=null){
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
										if(min<odds)fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()+pnl);			
										else fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()-liab);

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
		return 0.0;
		}
	
	
	@RequestMapping(value = "/getFancyLib", method = RequestMethod.GET)
	public Double getFancyLib(@RequestParam String fancyid,@RequestParam String userid,Integer matchId) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			fancyList.addAll(placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(userid, fancyid,EXConstants.Fancy,true,matchId));
			ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
			ArrayList<ExFancyBook> fancyBook1 = new ArrayList<>();
			
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
										if(min<odds)fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()+pnl);			
										else fancyBook.get(j).setPnl(fancyBook.get(j).getPnl()-liab);

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
				for(int i=0;i<fancyList.size();i++){
				Integer	min =0;
				Integer max =0;
				int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
					if(i==0){
						min = odds-1;
						max=odds+1;
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
							fancyBook1.add(book);

						}
					}else{
						if(odds<min){
							min =odds-1;
						}if(max<odds){
							max=odds+1;
						}
						for(int j=min;j<=max;j++){
							ExFancyBook book = new ExFancyBook();
							book.setRates(Integer.parseInt(df.format(j)));
							if(fancyList.get(i).getIsback()==true){
								
							}
						}
					}
				}
				
			
				return lowval;					
					
				}else{
					return 0.0;	
				}
				
			
		
		}
	
	
	@RequestMapping(value = "/getFancyBook", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyBook(@RequestParam String fancyid,String back,String lay,Integer matchId) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
		
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			fancyList.addAll(placebetrepository.findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(usersession.getUserid(), fancyid,EXConstants.Fancy,true,matchId));
			
			Integer mindds =0;
			Integer maxodds =0;
		
				for(ExMatchOddsBet bet :fancyList){
					sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
				}
				 Collections.sort(sortedlist); 
				 mindds = sortedlist.get(0)-25;
				 maxodds = sortedlist.get(sortedlist.size()-1)+25;
			
			
			if(!fancyList.isEmpty()){

				int first = 0;
				for(int i=0;i<fancyList.size();i++){
					int min=0;
					int max=0;
					Double liab = fancyList.get(i).getLiability();
					Double pnl = fancyList.get(i).getPnl();
					int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
					if(first==0){
						min = odds-25;
						if(min<0) min=0;
						max=odds+25;
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
	                
				
				
				
					
				
			}
		}
		
		return new ResponseEntity<Object>(fancyBook,HttpStatus.OK);	
		}
	
	
	@RequestMapping(value = "/getParentFancyBook", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentFancyBook(@RequestParam String fancyid,String back,String lay,Integer matchId) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
		
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			if(usersession.getUsertype()==4){
				fancyList= betlistDao.getFancyBook("adminid", usersession.getId().toString(), fancyid,matchId);			
			}else if(usersession.getUsertype()==6){
				fancyList= betlistDao.getFancyBook("adminid", usersession.getParentid(), fancyid,matchId);			
			}else if(usersession.getUsertype()==1){
				fancyList =betlistDao.getFancyBook("masterid", usersession.getId().toString(), fancyid,matchId);		
			}else if(usersession.getUsertype()==0){
				fancyList = betlistDao.getFancyBook("supermasterid", usersession.getId().toString(), fancyid,matchId);		
			}else if(usersession.getUsertype()==5){
				fancyList =betlistDao.getFancyBook("subadminid", usersession.getId().toString(), fancyid,matchId);						
			}else if(usersession.getUsertype()==2){
				fancyList= betlistDao.getFancyBook("dealerid", usersession.getId().toString(), fancyid,matchId);		
			}
			Integer mindds =0;
			Integer maxodds =0;
		
			
			
			if(fancyList.size()>0){

				
				for(ExMatchOddsBet bet :fancyList){
					sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
				}
				 Collections.sort(sortedlist); 
				 mindds = sortedlist.get(0)-25;
				 maxodds = sortedlist.get(sortedlist.size()-1)+25;
			
				
				int first = 0;
				for(int i=0;i<fancyList.size();i++){
					int min=0;
					int max=0;
					Double liab = fancyList.get(i).getLiability();
					Double pnl = fancyList.get(i).getPnl();
					int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
					if(first==0){
						min = odds-25;
						if(min<0) min=0;
						max=odds+25;
						for(int j=mindds;j<=maxodds;j++){
							ExFancyBook book = new ExFancyBook();
							
							book.setRates(Integer.parseInt(df.format(j)));
							book.setPnl(0.0);
							fancyBook.add(book);
						}
						
						first=1;
					}
				}

				
			  	
				//adminPartnership = Math.round((-pnl.doubleValue()*(lib.getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0;
				Double partnership =0.0;
				
				 for(int j=0; j<=fancyBook.size()-1;j++){
	                	
					 Integer min= fancyBook.get(j).getRates();
	                	
	                		for(int i=0;i<=fancyList.size()-1;i++){
		                		
	                			if(usersession.getUsertype() ==4) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getAdminpartership().doubleValue();
		                		}else if(usersession.getUsertype() ==5) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getSubadminpartnership().doubleValue();
		                		}else if(usersession.getUsertype() ==0) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getSupermastepartnership().doubleValue();
		                		}else if(usersession.getUsertype() ==1) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getMasterpartership().doubleValue();
		                		}else if(usersession.getUsertype() ==2) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getDelearpartership().doubleValue();
		                		}
	                			
	                		//adminPartnership = Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0;
	                			//System.out.println(fancyBook.get(j).getRates());
	                			if(i==0){
	                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-(Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0))));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+ Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                		}
	                			}else{
	                				
			                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			if(min<fancyList.get(i).getOdds()){
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()- Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                    			}else{
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    				
			                    			}
			                				
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			
			                				if(min<fancyList.get(i).getOdds()){
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                				}else{
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                				}
			                				
			                    		
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                		}
	                			}
	                			
	                		
		                	}
	                		//System.out.println(fancyBook.get(j).getPnl());
	                
	                }
	                
				
				
				
					
				
			}
		}
		
		return new ResponseEntity<Object>(fancyBook,HttpStatus.OK);	
		}
	
	
	@RequestMapping(value = "/getParentFancyBookNew", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentFancyBookNew(@RequestParam String fancyid,String back,String lay,Integer matchId) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
		
		
		
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			if(usersession.getUsertype()==4){
				fancyList= betlistDao.getFancyBookNew("adminid", usersession.getId().toString(), fancyid,"usr.adminpartership");			
			}else if(usersession.getUsertype()==6){
				fancyList= betlistDao.getFancyBookNew("adminid", usersession.getParentid(), fancyid,"usr.adminpartership");			
			}else if(usersession.getUsertype()==1){
				fancyList =betlistDao.getFancyBookNew("masterid", usersession.getId().toString(), fancyid,"usr.masterpartership");		
			}else if(usersession.getUsertype()==0){
				fancyList = betlistDao.getFancyBookNew("supermasterid", usersession.getId().toString(), fancyid,"usr.supermastepartnership");		
			}else if(usersession.getUsertype()==5){
				fancyList =betlistDao.getFancyBookNew("subadminid", usersession.getId().toString(), fancyid,"usr.subadminpartnership");						
			}else if(usersession.getUsertype()==2){
				fancyList= betlistDao.getFancyBookNew("dealerid", usersession.getId().toString(), fancyid,"usr.delearpartership");		
			}
			Integer mindds =0;
			Integer maxodds =0;
		
			
			
			if(fancyList.size()>0){

				Double pnl1=0.0;
				Double pnl2=0.0;
				
				for(ExMatchOddsBet bet :fancyList){
					
					 if(bet.getMarketid().contains(EXConstants.Fancy3Market) || bet.getMarketid().contains(EXConstants.OddEvenMarket)) {
						
						
						if(bet.getIsback()) {
							pnl1= pnl1-(bet.getPnl()*bet.getPartnership()/100);
							pnl2= pnl2+(bet.getLiability()*bet.getPartnership()/100);
							
						}else {
							pnl1= pnl1+(bet.getLiability()*bet.getPartnership()/100);
							pnl2= pnl2-(bet.getPnl()*bet.getPartnership()/100);
						}
						
					
						
					
						
						
						
					}else  if(bet.getMarketid().contains(EXConstants.Fancy2Market) || bet.getMarketid().contains(EXConstants.Ball)) {
						sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));	
					}
					
					
				}
				if(fancyid.contains(EXConstants.Fancy3Market) || fancyid.contains(EXConstants.OddEvenMarket)) {
					ExFancyBook bn =new ExFancyBook();
					bn.setBack(pnl1);
					bn.setLay(pnl2);
					fancyBook.add(bn);
				}
				else if(fancyid.contains(EXConstants.Fancy2Market) || fancyid.contains(EXConstants.Ball)) {
					 Collections.sort(sortedlist); 
					 mindds = sortedlist.get(0)-25;
					 maxodds = sortedlist.get(sortedlist.size()-1)+25;
				     if(mindds<0) {
				    	 mindds = mindds-(mindds); 
				     } 
					
					int first = 0;
					for(int i=0;i<fancyList.size();i++){
						int min=0;
						int max=0;
						Double liab = fancyList.get(i).getLiability();
						Double pnl = fancyList.get(i).getPnl();
						int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
						if(first==0){
							min = odds-25;
							if(min<0) min=0;
							max=odds+25;
							for(int j=mindds;j<=maxodds;j++){
								ExFancyBook book = new ExFancyBook();
								
								book.setRates(Integer.parseInt(df.format(j)));
								book.setPnl(0.0);
								fancyBook.add(book);
								
							}
							
							first=1;
						}
					}
					

				  	
					
					Double partnership =0.0;
					
					 for(int j=0; j<=fancyBook.size()-1;j++){
		                	
						 Integer min= fancyBook.get(j).getRates();
		                	
		                		for(int i=0;i<=fancyList.size()-1;i++){
			                		
		                			partnership =fancyList.get(i).getPartnership();
		                		
		                			if(i==0){
		                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-(Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0))));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+ Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                		}
		                			}else{
		                				
				                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			if(min<fancyList.get(i).getOdds()){
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()- Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                    			}else{
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    				
				                    			}
				                				
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			
				                				if(min<fancyList.get(i).getOdds()){
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                				}else{
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                				}
				                				
				                    		
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                		}
		                			}
		                			
		                		
			                	}
		                		
		                
		                }
				}		
				 

				
	                
				
				
				
					
				
			}
		}
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("data", fancyBook);
		hm.put("fancyid", fancyid);
		
		return new ResponseEntity<Object>(hm,HttpStatus.OK);	
		}
	
	@RequestMapping(value = "/getParentFancyLiblity", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentFancyLiblity(@RequestParam String fancyid,String back,String lay,Integer matchId) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		ArrayList<ExFancyBook> fancyBook = new ArrayList<>();
		
		if(usersession!=null){
			ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
			if(usersession.getUsertype()==4){
				fancyList= betlistDao.getFancyBookNew("adminid", usersession.getId().toString(), fancyid,"usr.adminpartership");			
			}else if(usersession.getUsertype()==6){
				fancyList= betlistDao.getFancyBookNew("adminid", usersession.getParentid(), fancyid,"usr.adminpartership");			
			}else if(usersession.getUsertype()==1){
				fancyList =betlistDao.getFancyBookNew("masterid", usersession.getId().toString(), fancyid,"usr.masterpartership");		
			}else if(usersession.getUsertype()==0){
				fancyList = betlistDao.getFancyBookNew("supermasterid", usersession.getId().toString(), fancyid,"usr.supermastepartnership");		
			}else if(usersession.getUsertype()==5){
				fancyList =betlistDao.getFancyBookNew("subadminid", usersession.getId().toString(), fancyid,"usr.subadminpartnership");						
			}else if(usersession.getUsertype()==2){
				fancyList= betlistDao.getFancyBookNew("dealerid", usersession.getId().toString(), fancyid,"usr.delearpartership");		
			}
			Integer mindds =0;
			Integer maxodds =0;
		
			
			
			if(fancyList.size()>0){

				
				for(ExMatchOddsBet bet :fancyList){
					sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
				}
				 Collections.sort(sortedlist); 
				 mindds = sortedlist.get(0)-25;
				 maxodds = sortedlist.get(sortedlist.size()-1)+25;
			
				
				int first = 0;
				for(int i=0;i<fancyList.size();i++){
					int min=0;
					int max=0;
					Double liab = fancyList.get(i).getLiability();
					Double pnl = fancyList.get(i).getPnl();
					int odds = Integer.parseInt(df.format(fancyList.get(i).getOdds()));
					if(first==0){
						min = odds-25;
						if(min<0) min=0;
						max=odds+25;
						for(int j=mindds;j<=maxodds;j++){
							ExFancyBook book = new ExFancyBook();
							
							book.setRates(Integer.parseInt(df.format(j)));
							book.setPnl(0.0);
							fancyBook.add(book);
						}
						
						first=1;
					}
				}

				
			  	
				//adminPartnership = Math.round((-pnl.doubleValue()*(lib.getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0;
				Double partnership =0.0;
				
				 for(int j=0; j<=fancyBook.size()-1;j++){
	                	
					 Integer min= fancyBook.get(j).getRates();
	                	
	                		for(int i=0;i<=fancyList.size()-1;i++){
		                		
	                			partnership =fancyList.get(i).getPartnership();
	                		/*	if(usersession.getUsertype() ==4) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getAdminpartership().doubleValue();
		                		}else if(usersession.getUsertype() ==5) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getSubadminpartnership().doubleValue();
		                		}else if(usersession.getUsertype() ==0) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getSupermastepartnership().doubleValue();
		                		}else if(usersession.getUsertype() ==1) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getMasterpartership().doubleValue();
		                		}else if(usersession.getUsertype() ==2) {
	                				partnership =userRepo.findByUserid(fancyList.get(i).getUserid()).getDelearpartership().doubleValue();
		                		}
	                			*/
	                		//adminPartnership = Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0;
	                			//System.out.println(fancyBook.get(j).getRates());
	                			if(i==0){
	                				if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-(Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0))));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+ Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                		}
	                			}else{
	                				
			                		if( Double.valueOf(fancyBook.get(j).getRates()) <  fancyList.get(i).getOdds()){
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			if(min<fancyList.get(i).getOdds()){
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()- Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                    			}else{
			                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    				
			                    			}
			                				
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                			
			                		}else{
			                			
			                			
			                			if(fancyList.get(i).getIsback()==false){
			                    			
			                				if(min<fancyList.get(i).getOdds()){
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                				}else{
			                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((fancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
			                				}
			                				
			                    		
			                    			
			                    		}else{
			                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((fancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
			                    			
			                    		}
			                		}
	                			}
	                			
	                		
		                	}
	                		//System.out.println(fancyBook.get(j).getPnl());
	                
	                }
	                
				
				
				
					
				
			}
		}
		
		return new ResponseEntity<Object>(fancyBook,HttpStatus.OK);	
		}
	
	@RequestMapping(value = "/getFancyExposure", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyExposure(Integer matchId) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<Integer> sortedlist = new ArrayList<>(); 
		
		
		ArrayList<MatchFancy> activeFancyList = fancyRepository.findByEventidAndIsActive(matchId, true);
		List<String> fancyIds  = new ArrayList<String>();
		
		for(MatchFancy mf : activeFancyList) {
			fancyIds.add(mf.getFancyid());
			 
			
		}
			
		ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
		
			if(usersession.getUsertype()==4){
				fancyList= betlistDao.getFancyBookExposure("adminid", usersession.getId().toString(), fancyIds,"usr.adminpartership");			
			}else if(usersession.getUsertype()==6){
				fancyList= betlistDao.getFancyBookExposure("adminid", usersession.getParentid(), fancyIds,"usr.adminpartership");			
			}else if(usersession.getUsertype()==1){
				fancyList =betlistDao.getFancyBookExposure("masterid", usersession.getId().toString(), fancyIds,"usr.masterpartership");		
			}else if(usersession.getUsertype()==0){
				fancyList = betlistDao.getFancyBookExposure("supermasterid", usersession.getId().toString(),fancyIds,"usr.supermastepartnership");		
			}else if(usersession.getUsertype()==5){
				fancyList =betlistDao.getFancyBookExposure("subadminid", usersession.getId().toString(), fancyIds,"usr.subadminpartnership");						
			}else if(usersession.getUsertype()==2){
				fancyList= betlistDao.getFancyBookExposure("dealerid", usersession.getId().toString(), fancyIds,"usr.delearpartership");		
			}
			
			HashMap<String,Object> hm = new  HashMap<String,Object> ();
			ArrayList<ExFancyBook> fancyExpo = new ArrayList<ExFancyBook>();
			
			for(MatchFancy mf : activeFancyList) {
				
				Integer mindds =0;
				Integer maxodds =0;
			
				
				ArrayList<ExMatchOddsBet> tempfancyList = new  ArrayList<ExMatchOddsBet>();
				ArrayList<ExFancyBook> fancyBook = new ArrayList<ExFancyBook>();
				
				
				if(fancyList.size()>0){
					
					for(ExMatchOddsBet bet :fancyList){
							
							
							if(mf.getFancyid().equalsIgnoreCase(bet.getMarketid())) {
								sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
								tempfancyList.add(bet);
							
							
							
							
							
						}
						
					}
					 Collections.sort(sortedlist); 
					 mindds = sortedlist.get(0)-25;
					 maxodds = sortedlist.get(sortedlist.size()-1)+25;
				
					
					int first = 0;
					for(int i=0;i<tempfancyList.size();i++){
						int min=0;
						int max=0;
						Double liab = tempfancyList.get(i).getLiability();
						Double pnl = tempfancyList.get(i).getPnl();
						int odds = Integer.parseInt(df.format(tempfancyList.get(i).getOdds()));
						if(first==0){
							min = odds-25;
							if(min<0) min=0;
							max=odds+25;
							for(int j=mindds;j<=maxodds;j++){
								ExFancyBook book = new ExFancyBook();
								
								book.setRates(Integer.parseInt(df.format(j)));
								book.setPnl(0.0);
								fancyBook.add(book);
							}
							
							first=1;
						}
					}

					
				  	
				
					Double partnership =0.0;
					
					 for(int j=0; j<=fancyBook.size()-1;j++){
		                	
						 Integer min= fancyBook.get(j).getRates();
		                	
		                		for(int i=0;i<=tempfancyList.size()-1;i++){
			                		
		                			partnership =tempfancyList.get(i).getPartnership();
		                		
		                			if(i==0){
		                				if( Double.valueOf(fancyBook.get(j).getRates()) <  tempfancyList.get(i).getOdds()){
				                			
				                			if(tempfancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-(Math.round(((tempfancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0))));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((tempfancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(tempfancyList.get(i).getIsback()==false){
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+ Math.round(((tempfancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((tempfancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                		}
		                			}else{
		                				
				                		if( Double.valueOf(fancyBook.get(j).getRates()) <  tempfancyList.get(i).getOdds()){
				                			
				                			if(tempfancyList.get(i).getIsback()==false){
				                    			if(min<tempfancyList.get(i).getOdds()){
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()- Math.round(((tempfancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                    			}else{
				                    				fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((tempfancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    				
				                    			}
				                				
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((tempfancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                			
				                		}else{
				                			
				                			
				                			if(fancyList.get(i).getIsback()==false){
				                    			
				                				if(min<tempfancyList.get(i).getOdds()){
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((tempfancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                				}else{
				                					fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()+Math.round(((tempfancyList.get(i).getLiability())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
					                    			
				                				}
				                				
				                    		
				                    			
				                    		}else{
				                    			fancyBook.get(j).setPnl(Double.valueOf(df.format(fancyBook.get(j).getPnl()-Math.round(((tempfancyList.get(i).getPnl())*(partnership.doubleValue()/100.0f))*100.0)/100.0)));
				                    			
				                    		}
				                		}
		                			}
		                		
		                		
			                	}
		                		
		                
		                }
		                
					
					
					
						
					
				}
			
				Double lowval = 0.00;
				for(int i = 0; i <fancyBook.size(); i++){
					if(fancyBook.get(i).getPnl()< lowval){
						lowval = fancyBook.get(i).getPnl();
					}
				}
				
				ExFancyBook bn =new ExFancyBook();
				bn.setFancyid(mf.getFancyid());
				bn.setPnl(lowval);
				fancyExpo.add(bn);
			}
			
			
		
		
		
		
		return new ResponseEntity<Object>(fancyExpo,HttpStatus.OK);	
		}
	
	@RequestMapping(value = "/fancyShowHide/{fancyId}/{isshow}", method = RequestMethod.POST)
	public ResponseEntity<?> fancyShowHide(@PathVariable String fancyId,@PathVariable Boolean isshow) throws Exception {

		ResponseBean responseBean = new ResponseBean();
		

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			MatchFancy mf = fancyRepository.findByFancyid(fancyId);
			mf.setIsshow(isshow);
			fancyRepository.save(mf);
			responseBean.setType("success");
			responseBean.setMessage("updated successfully");
			responseBean.setTitle("success");
		}
		
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/fancyRollBackDetail/{fancyId}", method = RequestMethod.GET)
	public ResponseEntity<Object> fancyRollBackDetail(@PathVariable String fancyId) throws Exception {

		

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<EXFancyRollBackDetail> fancy = new ArrayList<EXFancyRollBackDetail>();
		if (usersession != null) {
              fancy = fancyRollbackDetailRepo.findByfancyId(fancyId);
			
		}
		
		return new ResponseEntity<Object>(fancy,HttpStatus.OK);	
	}
}


