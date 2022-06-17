package com.exchange.api.service.impl;

import java.lang.reflect.InvocationTargetException;	
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.ParameterMode;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXSportBetDealy;
import com.exchange.api.bean.EXStake;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.EXUserMongo;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.appBean;
import com.exchange.api.dao.EXMySqlProcedureDao;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.EXUserDAO;
import com.exchange.api.dao.impl.EXMySqlProcedureDaoImpl;
import com.exchange.api.repositiry.AppBeanRepo;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXStakeRepository;
import com.exchange.api.repositiry.EXUserMongoRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.ExSportBetDealyRepository;
import com.exchange.api.service.EXuserService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
@Service
@Transactional
public class EXuserServiceImpl implements EXuserService {

	@Autowired
	EXUserRepository userRepo;
	
	@Autowired
	AppBeanRepo appBeanRepo;

	EXDateUtil dtUtil = new EXDateUtil();
	
	@Autowired
	EXChipDetailRepository chipdetailRepo;
	
	@Autowired
	EXUserDAO exuserDao;

	@Autowired
	EXNativeQueryDao nativeQueryDao;
	
	@Autowired
	EXMySqlProcedureDao mySqlProcedureDao;
	
	@Autowired
	ExSportBetDealyRepository sportDelayRep;
	
	@Autowired
	EXStakeRepository stakeRepo;
	
	@Autowired
	EXUserMongoRepository userMongoRepo;
	
	@Override
	@Transactional
	public  ResponseBean masterSignup(EXUser requestData,EXUser usersession) {
	//	System.out.println("inside masterSignup");
		EXUser userbean = null;
		EXUser username = null;
		ResponseBean rbean = new ResponseBean();
		BigDecimal bg1;
		BigDecimal bg2;
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		
		    BigDecimal amount  = requestData.getAvailableBalance();
		    if(usersession.getAvailableBalance().doubleValue()>requestData.getAvailableBalance().doubleValue()) {
		    	 requestData.setUsername(requestData.getUsername().replaceAll("\\s", "").toLowerCase());
			    requestData.setUserid(requestData.getUserid().replaceAll("\\s", "").toLowerCase());
				requestData.setAccountlock(false);
				requestData.setPasswordtype("old");
				requestData.setPassword(requestData.getPassword().toLowerCase());
				requestData.setIsaccountclosed(false);
				requestData.setAvailableBalanceWithPnl(0.0);
				requestData.setUplineAmount(0.0);
				userbean = userRepo.findByUserid(requestData.getUserid());
			    requestData.setAvailableBalance(new BigDecimal("0"));
			    requestData.setPasswordtype("old");
			    requestData.setIsaccountclosed(false);
			    requestData.setIsdirectbet(false);
			    requestData.setActive(true);
			    requestData.setBetlockBy(0);
			    requestData.setBetCount(false);
			    if(requestData.getUsertype() !=5) {
			    	
			    	requestData.setBetlockbyParent(usersession.getBetlockbyParent());
			    	requestData.setBetlock(usersession.getBetlock());
			    	requestData.setIsCasinoBetlock(usersession.getIsCasinoBetlock());
			    	requestData.setCasinobetlockBy(usersession.getCasinobetlockBy());
			    	requestData.setBetlockBy(usersession.getBetlockBy());
			    }else {
			    	requestData.setBetlockbyParent(false);
			    	requestData.setBetlock(false);
			    	requestData.setIsCasinoBetlock(false);
			    	requestData.setCasinobetlockBy(0);
			    	requestData.setBetlockBy(0);
					
			    }
			  
					if (userbean == null) {
						
						requestData.setPassword(requestData.getPassword().toLowerCase());
						requestData.setUserid(requestData.getUserid().toLowerCase());
						
						if (requestData.getUsertype() == 5) {
							BigDecimal subadminpartnership = requestData.getPartnershipratio();
							BigDecimal subadminpartnershipc = requestData.getPartnershipratioc();
							
							bg1 = new BigDecimal("100");
							
							subadminpartnership = bg1.subtract(subadminpartnership);
							subadminpartnershipc =bg1.subtract(subadminpartnershipc);


							requestData.setSubadminpartnership(subadminpartnership);
							requestData.setSupermastepartnership(new BigDecimal("0"));
							requestData.setMasterpartership(new BigDecimal("0"));
							requestData.setDelearpartership(new BigDecimal("0"));
							requestData.setParentspartnership(requestData.getAdminpartership().doubleValue());
							
							requestData.setSubadminpartnershipc(subadminpartnershipc);
							requestData.setSupermastepartnershipc(new BigDecimal("0"));
							requestData.setMasterpartershipc(new BigDecimal("0"));
							requestData.setDelearpartershipc(new BigDecimal("0"));


							appBean newapp = new appBean();
							newapp.setId(requestData.getAppid());
							
							newapp.setAppname(requestData.getAppname());
							newapp.setAppurl(requestData.getAppurl());
							newapp.setActive(true);
							newapp.setCreatedon(new Date());
							newapp.setUpdatedon(new Date());
							newapp.setIsMultiple(true);
							newapp.setHisabkitabtype(requestData.getHisabkitabtype());
							appBean appdtl =null;
							
							// need to check with id basis start
							appBean findByid = appBeanRepo.findByid(newapp.getId());
							
							
							if(findByid == null) {
								newapp.setIsMultiple(true);
								appdtl =appBeanRepo.save(newapp);
								appdtl.setAppId(appdtl.getId());
								appBeanRepo.save(appdtl);
							} else {
								if(newapp.getAppurl().equalsIgnoreCase(findByid.getAppurl())){
									if(findByid.getIsMultiple().equals(true)){
										newapp.setIsMultiple(true);
										appdtl =appBeanRepo.save(newapp);
										newapp.setAppId(findByid.getId());
										appdtl = appBeanRepo.save(newapp);
									}else{
										rbean.setMessage("Please Enter a Diffrent URL");
										rbean.setType("error");
										rbean.setTitle("Oops...");
										return rbean;
									}

								}else{
									newapp.setIsMultiple(true);
									appdtl =appBeanRepo.save(newapp);

								}
							}
							

							requestData.setUserchain(EXConstants.asba);
							if(appdtl!=null){
								requestData.setAppid(appdtl.getId());
								requestData.setUserchain(EXConstants.asba);

							}


						}else if (requestData.getUsertype() == 0) {
							BigDecimal supermasterpartnership = requestData.getPartnershipratio();
							BigDecimal supermasterpartnershipc = requestData.getPartnershipratioc();
						
							requestData.setUserchain(EXConstants.sbasu);
							requestData.setParentspartnership(usersession.getSubadminpartnership().doubleValue());
							
							requestData.setSupermastepartnership(usersession.getSubadminpartnership().subtract(supermasterpartnership));
							requestData.setSubadminpartnership(supermasterpartnership);
							requestData.setMasterpartership(new BigDecimal("0"));
							requestData.setDelearpartership(new BigDecimal("0"));

							requestData.setSupermastepartnershipc(usersession.getSubadminpartnershipc().subtract(supermasterpartnershipc));
							requestData.setSubadminpartnershipc(supermasterpartnershipc);
							requestData.setMasterpartershipc(new BigDecimal("0"));
							requestData.setDelearpartershipc(new BigDecimal("0"));
							requestData.setHisabkitabtype(usersession.getHisabkitabtype());

						}else if (requestData.getUsertype() == 1) {

						    	BigDecimal masterprtnship = requestData.getPartnershipratio();
						    	BigDecimal masterprtnshipc = requestData.getPartnershipratioc();
								bg1 = new BigDecimal("100");
								bg2 = new BigDecimal("0");
								
								if(usersession.getUsertype() ==5){
									requestData.setMasterpartership(usersession.getSubadminpartnership().subtract(masterprtnship));
									requestData.setSubadminpartnership(masterprtnship);
									requestData.setSupermastepartnership(bg2);
									requestData.setDelearpartership(bg2);
									
									requestData.setMasterpartershipc(usersession.getSubadminpartnershipc().subtract(masterprtnshipc));
									requestData.setSubadminpartnershipc(masterprtnshipc);
									requestData.setSupermastepartnershipc(bg2);
									requestData.setDelearpartershipc(bg2);
									
									requestData.setUserchain(EXConstants.sbam);
									requestData.setParentspartnership(usersession.getSubadminpartnership().doubleValue());
									
									
									
								}else{
									requestData.setSubadminpartnership(usersession.getSubadminpartnership());
									requestData.setMasterpartership(usersession.getSupermastepartnership().subtract(masterprtnship));
									requestData.setSupermastepartnership(masterprtnship);

									requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
									requestData.setMasterpartershipc(usersession.getSupermastepartnershipc().subtract(masterprtnshipc));
									requestData.setSupermastepartnershipc(masterprtnshipc);
									
									
									requestData.setUserchain(EXConstants.sbasum);
									requestData.setParentspartnership(usersession.getSupermastepartnership().doubleValue());
						
								}
								


								requestData.setDelearpartership(new BigDecimal("0"));
								requestData.setDelearpartershipc(new BigDecimal("0"));
								requestData.setHisabkitabtype(usersession.getHisabkitabtype());

							
							 
						} else if (requestData.getUsertype() == 2) {

							bg2 = new BigDecimal("0");
						
							BigDecimal dealerpartnership = requestData.getPartnershipratio();
							BigDecimal dealerpartnershipc = requestData.getPartnershipratioc();

							if(usersession.getUsertype() == 5){
								requestData.setMasterpartership(bg2);
								requestData.setSupermastepartnership(bg2);
								requestData.setDelearpartership(usersession.getSubadminpartnership().subtract(dealerpartnership));
								requestData.setSubadminpartnership(dealerpartnership);
								requestData.setParentspartnership(usersession.getSubadminpartnership().doubleValue());
								
								requestData.setMasterpartershipc(bg2);
								requestData.setSupermastepartnershipc(bg2);
								requestData.setDelearpartershipc(usersession.getSubadminpartnershipc().subtract(dealerpartnershipc));
								requestData.setSubadminpartnershipc(dealerpartnershipc);
								
							
								

							}else if(usersession.getUsertype() == 0){
								
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setMasterpartership(bg2);
								requestData.setDelearpartership(usersession.getSupermastepartnership().subtract(dealerpartnership));
								requestData.setSupermastepartnership(dealerpartnership);
								requestData.setParentspartnership(requestData.getSupermastepartnership().doubleValue());
								
								
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setMasterpartershipc(bg2);
								requestData.setDelearpartershipc(usersession.getSupermastepartnershipc().subtract(dealerpartnershipc));
								requestData.setSupermastepartnershipc(dealerpartnershipc);
								
								
								
								
							}

							else{
								requestData.setDelearpartership(usersession.getMasterpartership().subtract(dealerpartnership));
								requestData.setMasterpartership(dealerpartnership);
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setSupermastepartnership(usersession.getSupermastepartnership());
								requestData.setParentspartnership(usersession.getMasterpartership().doubleValue());
								
								requestData.setDelearpartershipc(usersession.getMasterpartershipc().subtract(dealerpartnershipc));
								requestData.setMasterpartershipc(dealerpartnershipc);
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setSupermastepartnershipc(usersession.getSupermastepartnershipc());
							
								
							
							
								
							}
						
							if(usersession.getUserchain()!=null){
								if(usersession.getUserchain().equalsIgnoreCase(EXConstants.asba)){
									requestData.setUserchain(EXConstants.sbad);

								} else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbam)){
									requestData.setUserchain(EXConstants.sbamd);
								}

								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasu)){
									requestData.setUserchain(EXConstants.sbasud);

								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasum)){
									requestData.setUserchain(EXConstants.sbasumd);

								}

							}
							requestData.setHisabkitabtype(usersession.getHisabkitabtype());

						     EXUser master = userRepo.findByid(Integer.parseInt(requestData.getParentid()));
						     
						}

						else if (requestData.getUsertype() == 3) {
							
							
							
							//delay.setBetdelay(requestData);
							
							requestData.setDealerid(requestData.getParentid());
							
							requestData.setBetDelay(requestData.getBetdelaymap().toString());
							requestData.setAdminpartership(usersession.getAdminpartership());
							requestData.setAdminid(usersession.getAdminid());
							requestData.setNetexposure(10000000);
							  requestData.setHisabkitabtype(usersession.getHisabkitabtype());

							if(usersession.getUserchain()!=null){
								if(usersession.getUserchain().equalsIgnoreCase(EXConstants.asba)){
									requestData.setUserchain(EXConstants.sbac);

								} else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbad)){
									requestData.setUserchain(EXConstants.sbadc);
								}

								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbam)){
									requestData.setUserchain(EXConstants.sbamc);

								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbamd)){
									requestData.setUserchain(EXConstants.sbamdc);

								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasu)){
									requestData.setUserchain(EXConstants.sbasuc);

								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasum)){
									requestData.setUserchain(EXConstants.sbasumc);
								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasud)){
									requestData.setUserchain(EXConstants.sbasudc);
								}
								else if(usersession.getUserchain().equalsIgnoreCase(EXConstants.sbasumd)){
									requestData.setUserchain(EXConstants.sbasumdc);
								}
							}

							if(usersession.getUsertype()== 5){

								requestData.setSupermasterid(usersession.getId().toString());
								requestData.setMasterid(usersession.getId().toString());
								requestData.setDealerid(usersession.getId().toString());
								
								requestData.setParentspartnership(usersession.getSubadminpartnership().doubleValue());
								requestData.setSubadminid(usersession.getId().toString());
								
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setSupermastepartnership(usersession.getSupermastepartnership());
								requestData.setMasterpartership(usersession.getMasterpartership());
								requestData.setDelearpartership(usersession.getDelearpartership());
								
								
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setSupermastepartnershipc(usersession.getSupermastepartnershipc());
								requestData.setMasterpartershipc(usersession.getMasterpartershipc());
								requestData.setDelearpartershipc(usersession.getDelearpartershipc());
								
							}

							if(usersession.getUsertype()== 0){

								requestData.setSupermasterid(usersession.getId().toString().toString());
								requestData.setMasterid(usersession.getId().toString().toString());
								requestData.setDealerid(usersession.getId().toString());
								
								
								
								requestData.setParentspartnership(usersession.getSupermastepartnership().doubleValue());
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setSupermastepartnership(usersession.getSupermastepartnership());
								requestData.setMasterpartership(usersession.getMasterpartership());
								requestData.setDelearpartership(usersession.getDelearpartership());
								
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setSupermastepartnershipc(usersession.getSupermastepartnershipc());
								requestData.setMasterpartershipc(usersession.getMasterpartershipc());
								requestData.setDelearpartershipc(usersession.getDelearpartershipc());
								

							}


							if(usersession.getUsertype()== 1){

								requestData.setSupermasterid(usersession.getSupermasterid());
								requestData.setMasterid(usersession.getMasterid());
								requestData.setDealerid(usersession.getId().toString());
								
							
								
								requestData.setParentspartnership(usersession.getDelearpartership().doubleValue());
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setSupermastepartnership(usersession.getSupermastepartnership());
								requestData.setMasterpartership(usersession.getMasterpartership());
								requestData.setDelearpartership(usersession.getDelearpartership());
								
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setSupermastepartnershipc(usersession.getSupermastepartnershipc());
								requestData.setMasterpartershipc(usersession.getMasterpartershipc());
								requestData.setDelearpartershipc(usersession.getDelearpartershipc());
								

							}
							if(usersession.getUsertype()== 2){
								requestData.setParentspartnership(usersession.getMasterpartership().doubleValue());	requestData.setSupermasterid(usersession.getSupermasterid());
								requestData.setMasterid(usersession.getMasterid());
								requestData.setDealerid(usersession.getId().toString());
								
							
								requestData.setParentspartnership(usersession.getDelearpartership().doubleValue());
								requestData.setSubadminpartnership(usersession.getSubadminpartnership());
								requestData.setSupermastepartnership(usersession.getSupermastepartnership());
								requestData.setMasterpartership(usersession.getMasterpartership());
								requestData.setDelearpartership(usersession.getDelearpartership());
								
								requestData.setSubadminpartnershipc(usersession.getSubadminpartnershipc());
								requestData.setSupermastepartnershipc(usersession.getSupermastepartnershipc());
								requestData.setMasterpartershipc(usersession.getMasterpartershipc());
								requestData.setDelearpartershipc(usersession.getDelearpartershipc());
								

							}
						
                           
							if (requestData.getNetexposure() == null) {
								requestData.setNetexposure(0);
							}

							 EXStake stake = new EXStake();
	                            stake.setStack1(1000);
	                            stake.setStack2(2000);
	                            stake.setStack3(5000);
	                            stake.setStack4(10000);
	                            stake.setStack5(25000);
	                            stake.setStack6(50000);
	                            stake.setStack7(75000);
	                            stake.setStack8(100000);
	                            stake.setStack9(200000);
	                            stake.setStack10(500000);
	                            stake.setUserid(requestData.getUserid());
	                            stakeRepo.save(stake);
	                          
						}

						SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						requestData.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
						requestData.setUpdaedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
						userbean = userRepo.save(requestData);
						if(userbean.getUsertype() ==5){
							userbean.setSubadminid(userbean.getId().toString());
							userbean.setSupermasterid(userbean.getId().toString());
							userbean.setMasterid(userbean.getId().toString());
							userbean.setDealerid(userbean.getId().toString());
							
							
							userRepo.save(userbean);
						}else if(userbean.getUsertype() ==0){
							userbean.setSupermasterid(userbean.getId().toString());
							userbean.setMasterid(userbean.getId().toString());
							userbean.setDealerid(userbean.getId().toString());
							
							userRepo.save(userbean);
						}else if(userbean.getUsertype() ==1){
							if(usersession.getUsertype() ==5){
								userbean.setSupermasterid(usersession.getSubadminid());
								userbean.setMasterid(userbean.getId().toString());
								userbean.setDealerid(userbean.getId().toString());
								
								
								
							}else{

								userbean.setSupermasterid(usersession.getSupermasterid());
								userbean.setMasterid(userbean.getId().toString());
								userbean.setDealerid(userbean.getId().toString());
								
							
							}
							
							
						}else if(userbean.getUsertype() ==2){
							if(usersession.getUsertype() ==5){
								userbean.setSupermasterid(usersession.getSubadminid());
								userbean.setMasterid(usersession.getSubadminid());
								userbean.setDealerid(userbean.getId().toString());
								
								
								
							}else if(usersession.getUsertype() ==1){
								userbean.setSupermasterid(usersession.getSupermasterid());
								userbean.setMasterid(usersession.getMasterid());
								userbean.setDealerid(userbean.getId().toString());
								
							
								
							}else if(usersession.getUsertype() ==0){
								userbean.setSupermasterid(usersession.getSupermasterid());
								userbean.setMasterid(usersession.getSupermasterid());
								userbean.setDealerid(userbean.getId().toString());
								
							
							}
						}
							
						//requestData.setAvailableBalance(new BigDecimal("0"));
						if(requestData.getHisabkitabtype()== 2) {
							userbean.setUplineAmount(-requestData.getCreditRef().doubleValue());
						}
						userRepo.save(userbean);
						if (userbean != null) {
							    
							    HashMap<String, Object> hm1 = new  HashMap<String, Object>();
							   
							   
								hm1.put("amount", requestData.getCreditRef().doubleValue());
								hm1.put("parentuserid", usersession.getUserid());
								hm1.put("childuserid", userbean.getUserid());
								hm1.put("descreption", "Opening Balance By " + usersession.getUserid() + " To " + requestData.getUserid());
								hm1.put("parentid", usersession.getId());
								hm1.put("childid", userbean.getId());
								hm1.put("type", EXConstants.OpeningBalance);
								hm1.put("fromto", usersession.getUserid()+" To "+requestData.getUserid());
								hm1.put("newuser", "Yes");
								String message1 =mySqlProcedureDao.chipCredit(hm1);
								
								if(message1.equalsIgnoreCase(EXConstants.Success)) {
									
									if(requestData.getHisabkitabtype()== 1) {
										userbean.setAvailableBalance(amount);
									}
								
									userRepo.save(userbean);
									
									EXUserMongo userbeanMongo = new EXUserMongo();
									BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
									try {
										BeanUtils.copyProperties(userbeanMongo, userbean);
										userMongoRepo.save(userbeanMongo);
									} catch (IllegalAccessException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									
									
									
									rbean.setMessage("User Created successfully...");
									rbean.setType("success");
									rbean.setTitle("Success");
								
								}else{
									
									
									rbean.setMessage(message1);
									rbean.setType("error");
									rbean.setTitle("Oops...");
									
								
								}
							

							return rbean;

						}

					} else {
						rbean.setMessage("This user id not availabe!");
						rbean.setType("error");
						rbean.setTitle("Oops...");
						return rbean;
					}
				
		    }else {
		    	rbean.setMessage("Inssuficiant Balance");
				rbean.setType("error");
				rbean.setTitle("Oops...");
				return rbean;
		    }
		    
			
		

		
		return rbean;
	}




	@Override
	public ArrayList<EXUser> getUserByAparentId(String parentid, Boolean active, Integer usertype, String type,Integer id,Boolean isaccountclosed) {
		// TODO Auto-generated method stub
		
		ArrayList<EXUser>  list = exuserDao.getUserByAparentId(parentid, active, usertype,type,id,isaccountclosed);
		 
		return list;
	}

}
