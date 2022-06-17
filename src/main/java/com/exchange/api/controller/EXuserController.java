package com.exchange.api.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.exchange.api.bean.CasinoBetLoack;
import com.exchange.api.bean.EXKhataBook;
import com.exchange.api.bean.EXMatchSessionCommssion;
import com.exchange.api.bean.EXStake;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.EXUserLogin;
import com.exchange.api.bean.ExCashTransation;
import com.exchange.api.bean.ExMatchOddsBetMongo;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserIp;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.bean.appBean;
import com.exchange.api.dao.EXMySqlProcedureDao;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.EXUserDAO;
import com.exchange.api.dao.EXUserRediesDao;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.dao.MarketExpoDAO;
import com.exchange.api.dao.UserLiabilityDao;
import com.exchange.api.repositiry.AppBeanRepo;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CasinoBetLoackRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXKhataBookRepository;
import com.exchange.api.repositiry.EXLedgerNewRepository;
import com.exchange.api.repositiry.EXLedgerRepository;
import com.exchange.api.repositiry.EXMatchSessionCommssionRepository;
import com.exchange.api.repositiry.EXStakeRepository;
import com.exchange.api.repositiry.EXUserLoginRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.ExMatchOddsBetMongoCustomRepository;
import com.exchange.api.repositiry.ExMatchOddsBetMongoRepos;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.api.service.EXuserService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RestController
@CrossOrigin
@RequestMapping("/api")
public class EXuserController {

	// private static org.apache.log4j.Logger log =
	// Logger.getLogger(EXuserController.class);
	/* demom */
	@Autowired
	EXUserRepository userRepo;
	
	/*
	 * @Autowired EXUserRediesDao rediesdao;
	 */

	@Autowired
	HttpSession session;

	@Autowired
	UserIpRepository userIpRepo;

	@Autowired
	EXChipDetailRepository chipdetailRepo;

	@Autowired
	HttpServletRequest request;

	EXDateUtil dtUtil = new EXDateUtil();

	@Autowired
	BetRepository exbetRepo;

	@Autowired
	MarketRepository marketrepo;

	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	FancyResultRepository fancyrepo;

	@Autowired
	MarketExpoDAO marketExpoDAO;

	@Autowired
	AppBeanRepo appBeanRepo;

	@Autowired
	AppChargeRepo appchargeRepo;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	MarketResultRepository matchResultrepo;

	@Autowired
	EXuserService exUserService;

	@Autowired
	ExMatchOddsBetDao betlistDao;

	@Autowired
	UserLiabilityDao userLibDao;

	@Autowired
	EXUserDAO userDao;

	@Autowired
	EXLedgerRepository ledgerRepo;

	@Autowired
	EXUserLoginRepository userLoginRepo;

	@Autowired
	EXMatchSessionCommssionRepository matchSessionCommRepo;

	@Autowired
	EXNativeQueryDao nativeQueryDao;

	@Autowired
	EXMySqlProcedureDao mysqlProcedureDao;

	@Autowired
	EXChipDetailRepository exchipRepo;

	@Autowired
	EXLedgerNewRepository ledgerNewRepo;

	@Autowired
	EXKhataBookRepository khataBookRepo;

	@Autowired
	EXStakeRepository stakeRepo;

	@Autowired
	ExMatchOddsBetMongoRepos exMatchOddsBetMongoRepos;
	
	@Autowired
	CasinoBetLoackRepository casinoBetLockRepo;

	
	
	
	@RequestMapping(value = "masterSignup", method = RequestMethod.POST, consumes = "application/json")
	// @CacheEvict(value = "userByParentId", allEntries = true)
	public ResponseEntity<Object> masterSignup(@Valid @RequestBody EXUser requestData) {

		log.info("inside masterSignup");
		log.info(EXConstants.LOG_REQUEST + " - " + requestData);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			if (requestData.getParentPassword().toLowerCase()
					.equalsIgnoreCase(usersession.getPassword().toLowerCase())) {
				rbean = exUserService.masterSignup(requestData, usersession);
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);
			} else {
				log.info(EXConstants.passwordNotOk);
				rbean.setMessage(EXConstants.passwordNotOk);
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}

		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/user/{parentid}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> userList(@PathVariable("parentid") String parentid,
			@PathVariable("usertype") Integer usertype, @RequestParam Boolean active, @RequestParam String type,@RequestParam Boolean isaccountclosed) {
		log.info("inside userList");
		DecimalFormat df = new DecimalFormat("#0.00");
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		EXUser usersession = (EXUser) session.getAttribute("user");

		ArrayList<EXUser> master = new ArrayList<EXUser>();
		ArrayList<EXUser> totalList = new ArrayList<EXUser>();
		ArrayList<EXUser> client = new ArrayList<EXUser>();
		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> hm1 = new LinkedHashMap<String, Object>();
		DecimalFormat df1 = new DecimalFormat("#0");

		if (usersession != null) {
			list = exUserService.getUserByAparentId(parentid, active, usertype, type, usersession.getId(),isaccountclosed);
			EXUser userbean = new EXUser();
			try {
				BigDecimal creditRef = new BigDecimal("0.0");
				BigDecimal availableBalance = new BigDecimal("0.0");
				Double uplineAmount = 0.0;
				Double availableBalanceWithPnl = 0.0;
				Double profitLoss = 0.0;
				Double balance = 0.0;
				if (list.size() > 0) {
					for (EXUser bean : list) {
					
                       if (bean.getUsertype() != 6) {

							if (bean.getUsertype() == 3) {

								bean.setCurrentExpo(nativeQueryDao.userCurrentExpo(bean.getUserid()));

								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								bean.setUplineAmount(Double.valueOf(df1.format(bean.getUplineAmount())));
								bean.setAvailableBalance(new BigDecimal(bean.getAvailableBalanceWithPnl().toString()));
								bean.setBalance(bean.getAvailableBalanceWithPnl().doubleValue());

								CasinoBetLoack cBetLoackbean = casinoBetLockRepo.findByUserid(bean.getId());
							//	System.out.println(bean.getUserid() +"Id==>"+bean.getId());
								if(cBetLoackbean!=null) {
									if(cBetLoackbean.getTabletype().equalsIgnoreCase("B")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(true);
									}else if(cBetLoackbean.getTabletype().equalsIgnoreCase("L")) {
										bean.setvCasinoLock(false);
										bean.setlCasinoLock(true);
									}if(cBetLoackbean.getTabletype().equalsIgnoreCase("V")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(false);
									}
								}else {
									bean.setvCasinoLock(false);
									bean.setlCasinoLock(false);
								}
								client.add(bean);

							} else {
								if(bean.getHisabkitabtype() ==1) {
									bean.setBalance(bean.getAvailableBalance().doubleValue());
									
								}else {
									bean.setBalance(bean.getAvailableBalance().doubleValue()+bean.getAvailableBalanceWithPnl());
									
								}
								bean.setAvailableBalanceWithPnl(Double.valueOf(df1.format(userDao.downlineOccupyBalance(bean.getId().toString()))));
								
								if(bean.getHisabkitabtype() ==1) {
									bean.setUplineAmount(Double.valueOf(df1.format(-bean.getUplineAmount())));
									
								}else {
									bean.setUplineAmount(Double.valueOf(df1.format(bean.getUplineAmount())));
									
								}
								bean.setAvailableBalance(
										bean.getCreditRef().add(new BigDecimal(bean.getUplineAmount())));

								
								CasinoBetLoack cBetLoackbean = casinoBetLockRepo.findByUserid(bean.getId());
						//		System.out.println(bean.getUserid());
								if(cBetLoackbean!=null) {
									if(cBetLoackbean.getTabletype().equalsIgnoreCase("B")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(true);
									}else if(cBetLoackbean.getTabletype().equalsIgnoreCase("L")) {
										bean.setvCasinoLock(false);
										bean.setlCasinoLock(true);
									}if(cBetLoackbean.getTabletype().equalsIgnoreCase("V")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(false);
									}
								}else {
									bean.setvCasinoLock(false);
									bean.setlCasinoLock(false);
								}
								
								master.add(bean);
								
									
							}

							creditRef = creditRef.add(bean.getCreditRef());
							availableBalance = availableBalance.add(bean.getAvailableBalance());
							uplineAmount = uplineAmount + bean.getUplineAmount();
							availableBalanceWithPnl = availableBalanceWithPnl + bean.getAvailableBalanceWithPnl();
							balance = balance + bean.getBalance().doubleValue();

						}

					}
					userbean.setCreditRef(creditRef);
					userbean.setAvailableBalance(availableBalance);
					userbean.setId(usersession.getId());
					userbean.setUplineAmount(uplineAmount);
					userbean.setAvailableBalanceWithPnl(Double.valueOf(df1.format(availableBalanceWithPnl)));
					userbean.setBalance(balance);
					totalList.add(userbean);
					totalList.addAll(master);
					hm1.put("master", master);
					hm1.put("client", client);
					hm1.put("total", totalList);

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				} else {

					hm1.put("master", master);

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				}
			} catch (Exception e) {
				//log.error("an exception was thrown", e);
				e.printStackTrace();
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	@Transactional
	@RequestMapping(value = "/accountLock/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> accountLock(@PathVariable("id") Integer id) {
		log.info("inside accountLock - id: " + id);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);

		if (id != null) {

			EXUser ubean = userRepo.findByid(id);
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(true);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(true);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(true);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(true);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 3) {
					ubean.setAccountlock(true);
					userRepo.save(ubean);
					/*rediesdao.deleteUserById(ubean.getUserid());*/
				}

				rbean.setMessage("Account Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@Transactional
	@RequestMapping(value = "/accountUnLock/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> accountUnLock(@PathVariable("id") Integer id) {
		log.info("inside accountUnLock - id: " + id);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);

		if (id != null) {

			EXUser ubean = userRepo.findByid(id);
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(false);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(false);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(false);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setAccountlock(false);
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
				} else if (ubean.getUsertype() == 3) {
					ubean.setAccountlock(false);
					userRepo.save(ubean);
					/*rediesdao.deleteUserById(ubean.getUserid());*/
				}

				rbean.setMessage("Account Un Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@Transactional
	@RequestMapping(value = "/chipCredit", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> chipCredit(@RequestBody HashMap<String, String> requestData) {
		log.info("inside chipCredit");
		log.info(EXConstants.LOG_REQUEST + " " + requestData);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (usersession != null) {

			try {
				
				Integer amt =Integer.parseInt(requestData.get("chips"));
				if(amt>0) {
					if (requestData.get("password").toLowerCase()
							.equalsIgnoreCase(usersession.getPassword().toLowerCase())) {

						EXUser parentbean = userRepo.findByid(Integer.parseInt(requestData.get("parentid")));
						if (parentbean != null) {

							EXUser childbean = userRepo.findByUserid(requestData.get("userid"));
							HashMap<String, Object> hm1 = new HashMap<String, Object>();

							hm1.put("amount", Double.parseDouble(requestData.get("chips")));
							hm1.put("parentuserid", parentbean.getUserid());
							hm1.put("childuserid", childbean.getUserid());
							hm1.put("descreption",
									"Chip Credited By " + parentbean.getUserid() + " To " + childbean.getUserid()+"==>"+requestData.get("descreption"));
							hm1.put("parentid", usersession.getId());
							hm1.put("childid", childbean.getId());
							hm1.put("type", EXConstants.Balance);
							hm1.put("newuser", "no");
							hm1.put("fromto", usersession.getUserid() + " To " + childbean.getUserid());

							String message1 = mysqlProcedureDao.chipCredit(hm1);

							if (message1.equalsIgnoreCase(EXConstants.Success)) {
								rbean.setMessage("Chip Credited Successfully..!");
								rbean.setType("success");
								rbean.setTitle("Success");
							} else {
								rbean.setMessage(message1);
								rbean.setType("error");
								rbean.setTitle("Oops...");
							}

						}
					} else {
						rbean.setMessage(EXConstants.passwordNotOk);
						rbean.setType("error");
						rbean.setTitle("Oops...");
					}
				}else {
					rbean.setMessage("Please enter Valid Amount..");
					rbean.setType("error");
					rbean.setTitle("Oops...");
				}
				

				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

			}

		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");

		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@Transactional
	@RequestMapping(value = "chipWithdraw", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> chipWithdraw(@RequestBody HashMap<String, String> requestData) {
		log.info("inside chipWithdraw");
		log.info(EXConstants.LOG_REQUEST + " " + requestData);
		HttpSession session = request.getSession(true);
		EXUser user = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		
		try {
			if (user != null) {

				Integer amt =Integer.parseInt(requestData.get("chips"));
				if(amt>0) {
				if (requestData.get("password").toLowerCase().equalsIgnoreCase(user.getPassword().toLowerCase())) {

					EXUser childbean = userRepo.findByUserid(requestData.get("userid"));
					EXUser parentbean = userRepo.findByid(Integer.parseInt(requestData.get("parentid")));
					HashMap<String, Object> hm1 = new HashMap<String, Object>();

					hm1.put("amount", Double.parseDouble(requestData.get("chips")));
					hm1.put("parentuserid", parentbean.getUserid());
					hm1.put("childuserid", childbean.getUserid());
					hm1.put("descreption", "Chip Withdraw By " + parentbean.getUserid() + " To " + childbean.getUserid()+"==>"+requestData.get("descreption"));
					hm1.put("parentid", parentbean.getId());
					hm1.put("childid", childbean.getId());
					hm1.put("type", EXConstants.Balance);
					hm1.put("fromto", childbean.getUserid() + " To " + childbean.getUserid());

					Thread.sleep(1000);
					String message1 = mysqlProcedureDao.chipWithdraw(hm1);

					if (message1.equalsIgnoreCase(EXConstants.Success)) {
						rbean.setMessage("Chip Withdraw Successfully..!");
						rbean.setType("success");
						rbean.setTitle("Success");
					} else {
						rbean.setMessage(message1);
						rbean.setType("error");
						rbean.setTitle("Oops...");
					}
				} else {
					rbean.setMessage(EXConstants.passwordNotOk);
					rbean.setType("error");
					rbean.setTitle("Oops...");
				}
			}else {
					rbean.setMessage("Please enter Valid Amount..");
					rbean.setType("error");
					rbean.setTitle("Oops...");
				}
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} else {

				rbean.setMessage("Please Login to Continue!");
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	// comment my sql
	/*
	 * @RequestMapping(value = "/editOneClickBet", method = RequestMethod.POST)
	 * public ResponseEntity<Object> editOneClickBet(@RequestBody OneClickBetting
	 * oneClickBetting) { HttpSession session = request.getSession(true); EXUser
	 * user = (EXUser) session.getAttribute("user"); EXUser userbean = new EXUser();
	 * ResponseBean rbean = new ResponseBean();
	 * 
	 * if (user != null) {
	 * 
	 * userbean = userRepo.findByUserid(user.getUserid()); if (userbean != null) {
	 * userbean.setBetting(oneClickBetting);
	 * 
	 * if (userRepo.save(userbean) != null) {
	 * rbean.setMessage("Bets Update Successfully!"); rbean.setType("success");
	 * rbean.setTitle("Success"); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.OK); } } else { rbean.setMessage("Somthing went wrong!");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } }
	 * rbean.setMessage("Please Login to Continue!"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST); }
	 */

	@RequestMapping(value = "/checkUserLogin")
	public ResponseEntity<Object> checkUserLogin(HttpServletRequest req) throws UnknownHostException, SocketException {
		// req = InetAddress.getLocalHost();
		log.info("inside checkUserLogin");
		log.info(EXConstants.LOG_REQUEST + " " + req);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser user = (EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (user != null) {

			EXUserLogin userip = userLoginRepo.findByUseridAndSessionid(user.getUserid(), session.getId());
			if (userip == null) {
				session.removeAttribute("user");

			}

		}

		rbean.setMessage("Please Login to Continue!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		model.addObject("result", rbean);

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getParentChildBalance/{childId}/{parentId}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentChildBalance(@PathVariable("childId") String childId,
			@PathVariable("parentId") String parentId, @PathVariable("type") String type) {
		log.info("inside getUserAccDetails");
		EXUser parentbean;
		EXUser childbean;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");

		if (usersession != null) {
			parentbean = userRepo.findByUserid(usersession.getUserid());
			childbean = userRepo.findByUserid(childId);
			if (type.equalsIgnoreCase("o")) {
				parentbean.setAvailableBalanceWithPnl(
						parentbean.getAvailableBalance().doubleValue() + parentbean.getAvailableBalanceWithPnl());
				parentbean.setAvailableBalance(parentbean.getAvailableBalance());

			} else {
				parentbean.setAvailableBalanceWithPnl(parentbean.getAvailableBalance().doubleValue());
				parentbean.setAvailableBalance(parentbean.getAvailableBalance());

			}

			if (childbean.getUsertype() == 3) {
				LinkedHashMap<String, Object> map = userLibDao.getUserDetail(childbean.getUserid(), type);
				if (type.equalsIgnoreCase("lenadena")) {
					childbean.setAvailableBalanceWithPnl(childbean.getUplineAmount());

				} else {
					childbean.setAvailableBalanceWithPnl(Double.valueOf(map.get("balance").toString()));

				}

			} else {
				LinkedHashMap<String, Object> map = userLibDao.getUserDetail(childbean.getUserid(), type);
				if (type.equalsIgnoreCase("lenadena")) {
					childbean.setAvailableBalanceWithPnl(-childbean.getUplineAmount());

				} else {
					childbean.setAvailableBalanceWithPnl(childbean.getAvailableBalance().doubleValue());

				}

			}

			LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();

			hm.put("availableBalanceParentPnl", df.format(parentbean.getAvailableBalanceWithPnl()));
			hm.put("availableBalanceParent", df.format(parentbean.getAvailableBalance()));
			hm.put("availableBalanceChild", df.format(childbean.getAvailableBalanceWithPnl()));
			hm.put("availableBcreditRefChild", df.format(childbean.getCreditRef()));
			return new ResponseEntity<Object>(hm, HttpStatus.OK);

		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/getParentChildBalanceSettlement/{childId}/{parentId}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Object> getParentChildBalanceSettlement(@PathVariable("childId") String childId,
			@PathVariable("parentId") String parentId, @PathVariable("type") String type) {
		log.info("inside getUserAccDetails");
		EXUser parentbean;
		EXUser childbean;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");

		if (usersession != null) {
			parentbean = userRepo.findByUserid(usersession.getUserid());
			childbean = userRepo.findByUserid(childId);
			if (type.equalsIgnoreCase("lenadena")) {
				parentbean.setAvailableBalanceWithPnl(
						parentbean.getAvailableBalance().doubleValue() + parentbean.getAvailableBalanceWithPnl());
				parentbean.setAvailableBalance(parentbean.getAvailableBalance());

			} else {
				parentbean.setAvailableBalanceWithPnl(parentbean.getAvailableBalance().doubleValue());
				parentbean.setAvailableBalance(parentbean.getAvailableBalance());

			}

			if (childbean.getUsertype() == 3) {
				LinkedHashMap<String, Object> map = userLibDao.getUserDetail(childbean.getUserid(), type);
				if (type.equalsIgnoreCase("lenadena")) {
					childbean.setAvailableBalanceWithPnl(childbean.getUplineAmount());

				} else {
					childbean.setAvailableBalanceWithPnl(Double.valueOf(map.get("balance").toString()));

				}

			} else {
				LinkedHashMap<String, Object> map = userLibDao.getUserDetail(childbean.getUserid(), type);
				if (type.equalsIgnoreCase("lenadena")) {
					childbean.setAvailableBalanceWithPnl(-childbean.getUplineAmount());

				} else {
					childbean.setAvailableBalanceWithPnl(childbean.getAvailableBalance().doubleValue());

				}

			}

			LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();

			hm.put("availableBalanceParentPnl", df.format(parentbean.getAvailableBalanceWithPnl()));
			hm.put("availableBalanceParent", df.format(parentbean.getAvailableBalance()));
			hm.put("availableBalanceChild", df.format(childbean.getAvailableBalanceWithPnl()));
			hm.put("availableBcreditRefChild", df.format(childbean.getCreditRef()));
			return new ResponseEntity<Object>(hm, HttpStatus.OK);

		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	/*
	 * @RequestMapping(value = "/getParentChildBalance/{childId}/{parentId}", method
	 * = RequestMethod.GET) public ResponseEntity<Object>
	 * getUserAccDetails(@PathVariable("childId") String
	 * childId,@PathVariable("parentId") String parentId) { EXUser parentbean;
	 * EXUser childbean; HttpSession session = request.getSession(true); EXUser
	 * usersession = (EXUser) session.getAttribute("user"); DecimalFormat df = new
	 * DecimalFormat("#0.00");
	 * 
	 * if (usersession != null) { parentbean =
	 * userRepo.findByUserid(usersession.getUserid()); childbean =
	 * userRepo.findByUserid(childId); if(parentbean.getUsertype() ==3){
	 * parentbean.setAvailableBalanceWithPnl(parentbean.getAvailableBalance().
	 * doubleValue());
	 * 
	 * }else{
	 * parentbean.setAvailableBalanceWithPnl(parentbean.getAvailableBalance().
	 * doubleValue()+(-nativeQueryDao.profitLossByUserIdWithOutSettlement(parentbean
	 * .getUserid()).getUplineAmount()));
	 * 
	 * }
	 * 
	 * 
	 * if(childbean.getUsertype() ==3){
	 * childbean.setAvailableBalanceWithPnl(childbean.getAvailableBalance().
	 * doubleValue());
	 * 
	 * }else{ childbean.setAvailableBalanceWithPnl(childbean.getAvailableBalance().
	 * doubleValue()+(-nativeQueryDao.profitLossByUserIdWithOutSettlement(childbean.
	 * getUserid()).getUplineAmount());
	 * 
	 * }
	 * 
	 * 
	 * LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
	 * 
	 * hm.put("availableBalanceParent",
	 * df.format(parentbean.getAvailableBalanceWithPnl()));
	 * hm.put("availableBalanceChild",
	 * df.format(childbean.getAvailableBalanceWithPnl()));
	 * hm.put("availableBcreditRefChild", df.format(childbean.getCreditRef()));
	 * return new ResponseEntity<Object>(hm, HttpStatus.OK);
	 * 
	 * 
	 * 
	 * } return new ResponseEntity<Object>(HttpStatus.NO_CONTENT); }
	 */

	@RequestMapping(value = "addChipToAdmin", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> addChipToAdmin(@RequestBody HashMap<String, String> requestData) {
		log.info("inside addChipToAdmin");
		log.info(EXConstants.LOG_REQUEST + " " + requestData);
		BigDecimal bg1;
		ResponseBean rbean = new ResponseBean();
		EXUser userbean = null;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		if (usersession != null) {
			if (usersession.getPassword().equalsIgnoreCase(requestData.get("parentPassword"))) {
				userbean = userRepo.findByUserid(usersession.getUserid());
				if (userbean == null) {
					rbean.setMessage("User not Found");
					rbean.setType("error");
					rbean.setTitle("Oops...");
					return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
				} else {
					// nativeQueryDao.updateUserAvailableBalance(userbean.getId(),
					// requestData.getAvailableBalance());

					HashMap<String, Object> hm1 = new HashMap<String, Object>();

					hm1.put("amount", Double.parseDouble(requestData.get("chips")));
					hm1.put("parentuserid", usersession.getUserid());
					hm1.put("childuserid", usersession.getUserid());
					hm1.put("descreption",
							"Chip Credited By " + usersession.getUserid() + " To " + usersession.getUserid());
					hm1.put("parentid", usersession.getId());
					hm1.put("childid", usersession.getId());
					hm1.put("type", EXConstants.Balance);
					hm1.put("fromto", usersession.getUserid() + " To " + usersession.getUserid());
					hm1.put("newuser", "no");
					String message1 = mysqlProcedureDao.chipCredit(hm1);

					if (message1.equalsIgnoreCase(EXConstants.Success)) {
						rbean.setMessage("Chip Credited Successfully..!");
						rbean.setType("success");
						rbean.setTitle("Success");
					} else {
						rbean.setMessage(message1);
						rbean.setType("error");
						rbean.setTitle("Oops...");
					}

					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}
			} else {
				rbean.setMessage(EXConstants.passwordNotOk);
				rbean.setType("error");
				rbean.setTitle("Oops...");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);
			}
		}

		rbean.setMessage("User not Found");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getLoginTime", method = RequestMethod.GET)
	public ResponseEntity<Object> getLoginTime() {
		log.info("inside getLoginTime");
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		Calendar calendar = new GregorianCalendar();
		if (usersession != null) {
			UserIp user = userIpRepo.findByuserid(usersession.getId().toString().toString());
			user.setLastlogin(String.valueOf(dtUtil.convTimeZone2((user.getLastlogin()), "GMT", "GMT+5:30")));

			return new ResponseEntity<Object>(user.getLastlogin(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/getUserDetail/{userid}/{childtype}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserDetail(@PathVariable("userid") String userid,
			@PathVariable("childtype") Integer childtype) {
		log.info("inside getUserDetail");
		ResponseBean responseBean = new ResponseBean();
		EXUser user = null;

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			user = userRepo.findByid(Integer.parseInt(userid));
			if (user != null) {
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @RequestMapping(value = "/updatePartnership", method = RequestMethod.POST)
	 * public ResponseEntity<Object> updatePartnership(@RequestBody
	 * LinkedHashMap<String, String> partnership) { HttpSession session =
	 * request.getSession(true); EXUser usersession = (EXUser)
	 * session.getAttribute("user"); ResponseBean rbean = new ResponseBean(); Double
	 * adminPartnership = Double.valueOf(partnership.get("adminPartnership"));
	 * Double masterPartnership =
	 * Double.valueOf(partnership.get("masterPartnership")); //Integer total =
	 * Integer.parseInt(partnership.get("total")); Integer parenttype =
	 * Integer.parseInt(partnership.get("parenttype")); Integer childtype =
	 * Integer.parseInt(partnership.get("childtype")); Double downlinemaxpartnership
	 * = Double.valueOf(partnership.get("downlinemaxpartnership"));
	 * 
	 * if (usersession != null) {
	 * 
	 * 
	 * EXUser user1 = new EXUser(); user1 =
	 * userRepo.findByid(Integer.parseInt(partnership.get("master")));
	 * 
	 * if(parenttype == 4) {
	 * 
	 * if(user1!=null){
	 * 
	 * ArrayList<EXUser> supermaster =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * if(masterPartnership<downlinemaxpartnership){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); }
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findBySubadminidAndIsactive(user1.getId().toString(),true)
	 * ;
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * if(supermaster.size()>0){
	 * 
	 * user1.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSubadminpartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * for(EXUser bean :supermaster){
	 * 
	 * if(bean.getUsertype()==0){
	 * 
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * bean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).subtract(
	 * bean.getSupermastepartnership())); userRepo.save(bean);
	 * 
	 * ArrayList<EXUser> master
	 * =userRepo.findByParentidAndActive(bean.getId().toString(),true);
	 * 
	 * if(master.size()>0){ for(EXUser masterbean :master){
	 * 
	 * if(masterbean.getUsertype()==1){
	 * 
	 * masterbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(masterbean.getSupermastepartnership().add(masterbean.
	 * getMasterpartership()))); userRepo.save(masterbean);
	 * 
	 * ArrayList<EXUser> dealer
	 * =userRepo.findByParentidAndActive(masterbean.getId().toString(),true);
	 * if(dealer.size()>0){
	 * 
	 * for(EXUser dealbean : dealer){ if(dealbean.getUsertype() ==2){
	 * 
	 * dealbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * dealbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealbean.getSupermastepartnership().add(dealbean.getMasterpartership
	 * ()).add(dealbean.getDelearpartership()))); userRepo.save(dealbean);
	 * 
	 * ArrayList<EXUser> userlist
	 * =userRepo.findByParentidAndActive(dealbean.getId().toString(),true);
	 * if(userlist.size()>0){ for(EXUser userbean : userlist){
	 * 
	 * userbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * userbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(userbean.getSupermastepartnership().add(userbean.getMasterpartership
	 * ()).add(userbean.getDelearpartership()))); userRepo.save(userbean); } }
	 * }else{ dealbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * dealbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealbean.getSupermastepartnership().add(dealbean.getMasterpartership
	 * ()))); userRepo.save(dealbean); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }else if(masterbean.getUsertype()==2){
	 * 
	 * masterbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(masterbean.getSupermastepartnership().add(masterbean.
	 * getDelearpartership()))); userRepo.save(masterbean);
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(masterbean.getId().toString(),3,
	 * true);{ if(client.size()>0){ for(EXUser clientbean :client){
	 * 
	 * clientbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(clientbean.getDelearpartership().add(clientbean.
	 * getSupermastepartnership()))); userRepo.save(clientbean); }
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * }else if(masterbean.getUsertype()==3){
	 * 
	 * masterbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(masterbean.getSupermastepartnership())); userRepo.save(masterbean);
	 * }
	 * 
	 * }
	 * 
	 * }else{
	 * 
	 * if(BigDecimal.valueOf(masterPartnership).compareTo(bean.
	 * getSupermastepartnership()) ==0){
	 * bean.setSubadminpartnership(bean.getSupermastepartnership().subtract(
	 * BigDecimal.valueOf(masterPartnership)));
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * userRepo.save(bean);
	 * 
	 * user1.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSubadminpartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * }else if(BigDecimal.valueOf(masterPartnership).compareTo(bean.
	 * getSupermastepartnership()) == 1){
	 * bean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).subtract(
	 * bean.getSupermastepartnership()));
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * userRepo.save(bean);
	 * 
	 * user1.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSubadminpartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * } } } if(bean.getUsertype()==1){
	 * 
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * bean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).subtract(
	 * bean.getMasterpartership())); userRepo.save(bean);
	 * 
	 * ArrayList<EXUser> dealer
	 * =userRepo.findByParentidAndActive(bean.getId().toString(),true);
	 * if(dealer.size()>0){ for(EXUser dealerbean :dealer){
	 * if(dealerbean.getUsertype() ==2){
	 * 
	 * dealerbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * dealerbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealerbean.getMasterpartership().add(dealerbean.getDelearpartership(
	 * )))); userRepo.save(dealerbean);
	 * 
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(dealerbean.getId().toString(),3,
	 * true);{ if(client.size()>0){ for(EXUser clientbean :client){
	 * clientbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(clientbean.getMasterpartership().add(clientbean.getDelearpartership(
	 * )))); userRepo.save(clientbean);
	 * 
	 * }
	 * 
	 * } } }else if(dealerbean.getUsertype() ==3){
	 * 
	 * dealerbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * dealerbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealerbean.getMasterpartership())); userRepo.save(dealerbean); }
	 * 
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * if(bean.getUsertype()==2){
	 * 
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * bean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).subtract(
	 * bean.getDelearpartership())); userRepo.save(bean);
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(bean.getId().toString(),3,true);
	 * { if(client.size()>0){ for(EXUser clientbean :client){
	 * 
	 * clientbean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(clientbean.getDelearpartership())); userRepo.save(clientbean); }
	 * 
	 * } } } if(bean.getUsertype()==3){
	 * bean.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * bean.setSubadminpartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(bean); } } }else{
	 * user1.setAdminpartership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSubadminpartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1); } }
	 * 
	 * 
	 * }else if(parenttype ==5 && childtype ==0 ) {
	 * 
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findBysupermasteridAndIsactive(user1.getId().toString(),
	 * true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * 
	 * if(masterPartnership<downlinemaxpartnership){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } if(user1!=null){
	 * 
	 * user1.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSupermastepartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> master =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(master.size()>0){
	 * 
	 * for(EXUser masterbean : master){
	 * 
	 * if(masterbean.getUsertype()==1){
	 * 
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(masterbean.getMasterpartership())); userRepo.save(masterbean);
	 * 
	 * ArrayList<EXUser> dealer
	 * =userRepo.findByParentidAndActive(masterbean.getId().toString(),true);
	 * if(dealer.size()>0){ for(EXUser dealerbean :dealer){
	 * if(dealerbean.getUsertype() ==2){
	 * 
	 * dealerbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * dealerbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealerbean.getMasterpartership().add(dealerbean.getDelearpartership(
	 * )))); userRepo.save(dealerbean);
	 * 
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(dealerbean.getId().toString(),3,
	 * true);{ if(client.size()>0){ for(EXUser clientbean :client){
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(clientbean.getMasterpartership().add(clientbean.getDelearpartership(
	 * )))); userRepo.save(clientbean);
	 * 
	 * }
	 * 
	 * } } }else if(dealerbean.getUsertype() ==3){
	 * 
	 * dealerbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * dealerbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(dealerbean.getMasterpartership())); userRepo.save(dealerbean); }
	 * 
	 * 
	 * } }
	 * 
	 * }else if(masterbean.getUsertype()==2){
	 * 
	 * 
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(masterbean.getDelearpartership())); userRepo.save(masterbean);
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(masterbean.getId().toString().
	 * toString(),3,true);{ if(client.size()>0){ for(EXUser clientbean :client){
	 * 
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership).
	 * subtract(clientbean.getDelearpartership())); userRepo.save(clientbean); }
	 * 
	 * } }
	 * 
	 * }else if(masterbean.getUsertype() == 3){
	 * masterbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * masterbean.setSupermastepartnership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(masterbean); } } }else{
	 * user1.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setSupermastepartnership(BigDecimal.valueOf(masterPartnership));
	 * 
	 * } }
	 * 
	 * } else if(parenttype ==5 && childtype ==1 ) {
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findByMasteridAndIsactive(user1.getId().toString(),true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * 
	 * if(masterPartnership<downlinemaxpartnership){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } if(user1!=null){
	 * 
	 * user1.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> dealerlist =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(dealerlist.size()>0){
	 * 
	 * for(EXUser dealer : dealerlist){
	 * 
	 * if(dealer.getUsertype()==2){
	 * 
	 * 
	 * dealer.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * dealer.setMasterpartership(BigDecimal.valueOf(masterPartnership).subtract(
	 * dealer.getDelearpartership())); userRepo.save(dealer);
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(dealer.getId().toString(),3,true
	 * );{ if(client.size()>0){ for(EXUser clientbean :client){
	 * 
	 * clientbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setMasterpartership(BigDecimal.valueOf(masterPartnership).subtract
	 * (clientbean.getDelearpartership())); userRepo.save(clientbean); }
	 * 
	 * } }
	 * 
	 * }else if(dealer.getUsertype() == 3){
	 * dealer.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * dealer.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(dealer); } } }else{
	 * user1.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * 
	 * } }
	 * 
	 * 
	 * } else if(parenttype ==5 && childtype ==2 ) {
	 * 
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findByDealeridAndIsactive(user1.getId().toString(),true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * BigDecimal total = new BigDecimal("0.0"); total = user1.getAdminpartership();
	 * 
	 * if(masterPartnership > new BigDecimal("100").subtract(total).doubleValue()){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); }
	 * 
	 * if(user1!=null){
	 * 
	 * user1.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> userlist =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(userlist.size()>0){
	 * 
	 * for(EXUser userbean :userlist){
	 * userbean.setSubadminpartnership(BigDecimal.valueOf(adminPartnership));
	 * userbean.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(userbean); } }
	 * 
	 * } } else if(parenttype ==0 && childtype ==1) {
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findByMasteridAndIsactive(user1.getId().toString(),true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * if(masterPartnership<downlinemaxpartnership){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } if(user1!=null){
	 * 
	 * user1.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> dealerlist =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(dealerlist.size()>0){
	 * 
	 * for(EXUser dealer : dealerlist){
	 * 
	 * if(dealer.getUsertype()==2){
	 * 
	 * 
	 * dealer.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * dealer.setMasterpartership(BigDecimal.valueOf(masterPartnership).subtract(
	 * dealer.getDelearpartership())); userRepo.save(dealer);
	 * 
	 * ArrayList<EXUser> client
	 * =userRepo.findByParentidAndUsertypeAndActive(dealer.getId().toString(),3,true
	 * );{ if(client.size()>0){ for(EXUser clientbean :client){
	 * 
	 * clientbean.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * clientbean.setMasterpartership(BigDecimal.valueOf(masterPartnership).subtract
	 * (clientbean.getDelearpartership())); userRepo.save(clientbean); }
	 * 
	 * } }
	 * 
	 * }else if(dealer.getUsertype() == 3){
	 * dealer.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * dealer.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(dealer); } } }else{
	 * user1.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setMasterpartership(BigDecimal.valueOf(masterPartnership));
	 * 
	 * } }
	 * 
	 * } else if(parenttype ==0 && childtype ==2) {
	 * 
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findByDealeridAndIsactive(user1.getId().toString(),true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } }
	 * 
	 * 
	 * BigDecimal total = new BigDecimal("0.0"); total =
	 * user1.getAdminpartership().add(user1.getSubadminpartnership());
	 * 
	 * if(masterPartnership > new BigDecimal("100").subtract(total).doubleValue()){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); }
	 * 
	 * if(user1!=null){
	 * 
	 * user1.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * user1.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> userlist =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(userlist.size()>0){
	 * 
	 * for(EXUser userbean :userlist){
	 * userbean.setSupermastepartnership(BigDecimal.valueOf(adminPartnership));
	 * userbean.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(userbean); } }
	 * 
	 * }
	 * 
	 * } else if(parenttype ==1) {
	 * 
	 * ArrayList<ExMatchOddsBet> betlist =
	 * placebetrepository.findByMasteridAndIsactive(user1.getId().toString(),true);
	 * 
	 * if(betlist.size()>0){ for(ExMatchOddsBet usrbean :betlist){
	 * rbean.setMessage("Please Clear Net Exposore of user "+usrbean.getUsername()
	 * +" to Change Partnership"); rbean.setType("error");
	 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.BAD_REQUEST);
	 * 
	 * } } BigDecimal total = new BigDecimal("0.0"); total =
	 * user1.getAdminpartership().add(user1.getSubadminpartnership().add(user1.
	 * getSupermastepartnership()));
	 * 
	 * if(masterPartnership > new BigDecimal("100").subtract(total).doubleValue()){
	 * 
	 * rbean.setMessage("Please Decrease Downline Partnership");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } if(user1!=null){
	 * 
	 * user1.setMasterpartership(BigDecimal.valueOf(adminPartnership));
	 * user1.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(user1);
	 * 
	 * ArrayList<EXUser> userlist =
	 * userRepo.findByParentidAndActive(user1.getId().toString(),true);
	 * 
	 * if(userlist.size()>0){
	 * 
	 * for(EXUser userbean :userlist){
	 * userbean.setMasterpartership(BigDecimal.valueOf(adminPartnership));
	 * userbean.setDelearpartership(BigDecimal.valueOf(masterPartnership));
	 * userRepo.save(userbean); } }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * rbean.setMessage("Updated Successfully"); rbean.setType("success");
	 * rbean.setTitle("success..."); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.OK); } return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	 * }
	 */

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable("id") String id) {
		log.info("inside deleteUser");
		log.info(EXConstants.LOG_REQUEST + " id: " + id);
		ResponseBean rbean = new ResponseBean();

		if (id != null) {

			EXUser user = null;
			Double liability = 0.00;

			Optional<EXUser> useroptional = userRepo.findById(id);
			ArrayList<UserLiability> liab = marketExpoDAO.getNetExposure(id, "userid");
			if (liab.size() > 0) {
				liability = Double.valueOf(liab.get(0).getLiability());
			}

			if (liability != 0.00) {
				rbean.setMessage("Please Clear Exposure To Continue!");
				rbean.setType("error");
				rbean.setTitle("Oops...");
				return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
			}
			if (useroptional.isPresent()) {
				user = new EXUser();
				user = useroptional.get();
				if (userRepo.save(user) != null) {
					rbean.setMessage("User Deleted successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/rollbackUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> rollbackUser(@PathVariable("id") String id) {
		log.info("inside rollbackUser");
		log.info(EXConstants.LOG_REQUEST + " id: " + id);
		ResponseBean rbean = new ResponseBean();

		if (id != null) {

			EXUser user = null;

			Optional<EXUser> useroptional = userRepo.findById(id);
			if (useroptional.isPresent()) {

				user = new EXUser();
				user = useroptional.get();
				if (userRepo.save(user) != null) {

					rbean.setMessage("User Activated successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getInactiveUser", method = RequestMethod.GET)
	public ResponseEntity<Object> getInactiveUser() throws JSONException {
		log.info("inside getInactiveUser");
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<EXUser> inactiveUser = new ArrayList<>();
		if (usersession != null) {
			inactiveUser.addAll(userRepo.findByAppid(usersession.getAppid()));
		}
		JSONArray inactiveUserList = new JSONArray();
		try {
			if (!inactiveUser.isEmpty()) {
				for (int i = 0; i < inactiveUser.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("userid", inactiveUser.get(i).getUserid());
					obj.put("mastername", userRepo.findById(inactiveUser.get(i).getMasterid()).get().getUserid());
					obj.put("dealername", userRepo.findById(inactiveUser.get(i).getDealerid()).get().getUserid());
					obj.put("id", inactiveUser.get(i).getId().toString());
					inactiveUserList.put(obj);
				}
				return new ResponseEntity<Object>(inactiveUserList.toString(), HttpStatus.OK);
			}
			return new ResponseEntity<Object>(inactiveUserList.toString(), HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserList() throws JSONException {
		log.info("inside getUserList");
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			JSONArray usersList = new JSONArray();
			ArrayList<EXUser> users = null;
			if (usersession.getUsertype() == 4) {
				users = userRepo.findByParentidAndUsertype(usersession.getId().toString().toString(), 5);
			} else {
				users = userRepo.findBySubadminid(usersession.getId().toString().toString());
			}

			if (!users.isEmpty()) {
				String IP = "";
				for (int i = 0; i < users.size(); i++) {
					UserIp ip = userIpRepo.findByuserid(users.get(i).getId().toString());
					if (ip != null) {
						IP = ip.getIpdetail();
					}
					JSONObject obj = new JSONObject();
					obj.put("username", users.get(i).getUsername());
					obj.put("id", users.get(i).getId().toString());
					obj.put("userid", users.get(i).getUserid());
					obj.put("ip", IP);
					obj.put("cratedon", dtUtil.convTimeZone2(users.get(i).getCreatedon(), "GMT", "GMT+5:30"));
					if (users.get(i).getUsertype() == 1) {
						obj.put("usertype", "Master");
					} else if (users.get(i).getUsertype() == 2) {
						obj.put("usertype", "Dealer");
					} else {
						obj.put("usertype", "Client");
					}

					UserIp ipdetail = userIpRepo.findByuserid(users.get(i).getId().toString());
					if (ipdetail != null) {
						if (ipdetail.getLastlogin() != null) {
							obj.put("lastlogin", dtUtil.convTimeZone2(ipdetail.getLastlogin(), "GMT", "GMT+5:30"));
						} else {
							obj.put("lastlogin", "Not Available");
						}

						if (ipdetail.getLastlogout() != null) {
							obj.put("lastlogout", dtUtil.convTimeZone2(ipdetail.getLastlogout(), "GMT", "GMT+5:30"));
						} else {
							obj.put("lastlogout", "Not Available");
						}

						if (ipdetail.getIpdetail() != null) {
							obj.put("ipaddress", ipdetail.getIpdetail());
						} else {
							obj.put("ipaddress", "Not Available");
						}

						if (ipdetail.getLogoutby() != null) {
							obj.put("logoutby", ipdetail.getLogoutby());
						} else {
							obj.put("logoutby", "Not Availble");
						}

					} else {
						obj.put("logoutby", "Not Availble");
						obj.put("ipaddress", "Not Available");
						obj.put("lastlogout", "Not Available");
						obj.put("lastlogin", "Not Available");
					}
					usersList.put(obj);
				}
				return new ResponseEntity<Object>(usersList.toString(), HttpStatus.OK);
			}
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/checklogoutUser")
	public ResponseEntity<Object> checklogoutUser() throws Exception {
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		Calendar calendar = new GregorianCalendar();
		if (usersession != null) {
			InetAddress localhost = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(localhost);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			EXUserLogin userip = null;
			if (usersession.getUsertype() == 4 || usersession.getUsertype() == 6) {
				userip = userLoginRepo.findByUseridAndSessionidAndLoginIp(usersession.getUserid(), session.getId(),
						sb.toString());

			}
			if (userip == null) {
				session.removeAttribute("user");
				return new ResponseEntity<Object>(HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		rbean.setMessage("Please Login to Continue!");
		rbean.setType("error");
		rbean.setTitle("Oops...");

		return new ResponseEntity<Object>(rbean, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/setlogoutUser", method = RequestMethod.POST)
	public ResponseEntity<Object> setlogoutUser(@RequestParam String id,
			@RequestBody LinkedHashMap<String, String> logout) throws ParseException {
		log.info("inside setlogoutUser");
		log.info(EXConstants.LOG_REQUEST + " " + logout);
		ResponseBean rbean = new ResponseBean();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView model = new ModelAndView();
		EXUser user = userRepo.findByid(Integer.parseInt(id));
		Date endDate;
		int days = 0, hours = 0;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (user != null) {
			UserIp userip = userIpRepo.findByuserid(user.getId().toString());
			if (userip != null) {
				if (logout.get("days") != "") {
					days = Integer.parseInt(logout.get("days"));
				}
				if (logout.get("hours") != "") {
					hours = Integer.parseInt(logout.get("hours"));
				}
				userip.setLogout(true);
				if (usersession.getUsertype() != 3) {
					userip.setLogoutby("Admin");
				} else {
					session.removeAttribute("user");
					userip.setLogoutby("Self");
				}

				endDate = dateFormater.parse(
						dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
				calendar.setTime(endDate);
				calendar.add(Calendar.DATE, days);
				calendar.add(Calendar.HOUR_OF_DAY, hours);
				endDate = calendar.getTime();
				userip.setLogoutduration(endDate);
				if (userIpRepo.save(userip) != null) {
					rbean.setMessage("User Logout successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(rbean, HttpStatus.ACCEPTED);
			}
		}

		rbean.setMessage("Please Login to Continue!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		model.addObject("result", rbean);

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/logout")
	public ResponseEntity<Object> logout(@RequestParam String id) {
		log.info("inside logout");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			session.removeAttribute("user");
			session.invalidate();

		}

		rbean.setMessage("Please Login to Continue!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		model.addObject("result", rbean);

		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/getSecondaryBalanceById", method =
	 * RequestMethod.GET) public ResponseEntity<Object>
	 * getSecondaryBalanceById(@RequestParam Integer id) { LinkedHashMap<String,
	 * String> result = new LinkedHashMap<>(); EXUser user = userRepo.findByid(id);
	 * 
	 * if (user != null) { result.put("balance",
	 * String.valueOf(user.getSecondrybanlce())); result.put("master",
	 * String.valueOf(user.getMasterpartership())); return new
	 * ResponseEntity<Object>(result, HttpStatus.OK); }
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.NO_CONTENT); }
	 */
	@RequestMapping(value = "/betLock/{id}", method = RequestMethod.POST)
	public ResponseEntity<Object> betLock(@PathVariable("id") String id) {
		log.info("inside betLock");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		if (id != null) {

			EXUser user = null;

			Optional<EXUser> useroptional = userRepo.findById(id);

			if (useroptional.isPresent()) {
				user = new EXUser();
				user = useroptional.get();
				user.setBetlock(true);
				if (userRepo.save(user) != null) {
					rbean.setMessage("User BetLocked successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/betUnLock/{id}", method = RequestMethod.POST)
	public ResponseEntity<Object> betUnLock(@PathVariable("id") String id) {
		log.info("inside betUnLock");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		if (id != null) {

			EXUser user = null;

			Optional<EXUser> useroptional = userRepo.findById(id);

			if (useroptional.isPresent()) {
				user = new EXUser();
				user = useroptional.get();
				if (userRepo.save(user) != null) {
					rbean.setMessage("User BetUnlocked successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/useraccount/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> useraccount(@PathVariable("id") Integer id) {
		log.info("inside useraccount");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		if (id != null) {

			EXUser user = userRepo.findByid(id);

			if (user != null) {
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			}

		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/makesubadminActInct", method = RequestMethod.POST)
	public ResponseEntity<Object> makesubadminActInct(@RequestParam Integer appid) {
		log.info("inside makesubadminActInct");
		log.info(EXConstants.LOG_REQUEST + " " + appid);
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		ResponseBean rbean = new ResponseBean();
		appBean appdeatails = appBeanRepo.findByid(String.valueOf(appid));

		if (appdeatails != null) {
			if (appdeatails.getActive() == true) {
				appdeatails.setActive(false);
				appBeanRepo.save(appdeatails);
				rbean.setMessage("Site blocked successfully");
				rbean.setType("success");
				rbean.setTitle("success");

			} else {
				appdeatails.setActive(true);
				appBeanRepo.save(appdeatails);
				rbean.setMessage("Site unblocked successfully");
				rbean.setType("success");
				rbean.setTitle("success");

			}
			return new ResponseEntity<Object>(rbean, HttpStatus.OK);
		}

		rbean.setMessage("Something Went Wrong");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.NO_CONTENT);
	}

	/*
	 * @RequestMapping(value="/saveSubadminDetails",method=RequestMethod.POST)
	 * public ResponseEntity<Object> saveSubadminDetails(@RequestBody EXUser
	 * requestData){ HttpSession session = request.getSession(true); ResponseBean
	 * rbean = new ResponseBean(); SimpleDateFormat dateFormater = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Calendar calendar = new
	 * GregorianCalendar(); TimeZone timeZone = calendar.getTimeZone(); EXUser
	 * usersession =(EXUser) session.getAttribute("user"); if(usersession!=null) {
	 * EXUser user = userRepo.findByUserid(requestData.getUserid()); appBean
	 * appdetails = appBeanRepo.findByid(requestData.getAppid());
	 * 
	 * 
	 * appdetails.setAppname(requestData.getAppname());
	 * appdetails.setAppurl(requestData.getAppurl()); appdetails.setUpdatedon(new
	 * Date()); user.setUpdaedon(dtUtil.convTimeZone2(dateFormater.format(new
	 * Date()), timeZone.getID().toString(), "GMT"));
	 * user.setReference(requestData.getReference());
	 * user.setUsername(requestData.getUsername());
	 * user.setContact(requestData.getContact());
	 * user.setBhaotype(requestData.getBhaotype());
	 * user.setPassword(requestData.getPassword());
	 * user.setCommisiontype(requestData.getCommisiontype());
	 * user.setMobappcharge(requestData.getMobappcharge());
	 * user.setCommision(requestData.getCommision());
	 * if(user.getPrimarybalance().compareTo(requestData.getPrimarybalance()) ==0){
	 * user.setPrimarybalance(requestData.getPrimarybalance()); }else{ EXChipDetail
	 * chipdetail = new EXChipDetail(); EXChipDetail chipdetail2 = new
	 * EXChipDetail();
	 * chipdetail.setChips(Double.parseDouble(requestData.getPrimarybalance().
	 * toString())); chipdetail.setUserid(usersession.getUserid());
	 * chipdetail.setNarration("Credit limit changed for " +user.getUserid());
	 * chipdetail.setCreatedOn(new Date());
	 * chipdetail.setParentid(usersession.getId().toString().toString());
	 * chipdetail.setType(EXConstants.CrdLimit);
	 * chipdetail.setUserbalance(user.getSecondrybanlce());
	 * chipdetail.setCrdr(true);
	 * user.setPrimarybalance(requestData.getPrimarybalance());
	 * 
	 * chipdetail2.setChips(Double.parseDouble(requestData.getPrimarybalance().
	 * toString())); chipdetail2.setUserid(user.getUserid());
	 * chipdetail2.setNarration("Credit limit changed By "
	 * +usersession.getUserid()); chipdetail2.setCreatedOn(new Date());
	 * chipdetail2.setParentid(user.getParentid());
	 * chipdetail2.setType(EXConstants.CrdLimit);
	 * chipdetail2.setUserbalance(user.getSecondrybanlce());
	 * chipdetail2.setCrdr(true); chipdetailRepo.save(chipdetail);
	 * chipdetailRepo.save(chipdetail2);
	 * 
	 * }
	 * 
	 * 
	 * if(requestData.getLosscommision().equalsIgnoreCase("")){
	 * requestData.setLosscommision("0"); }
	 * user.setLosscommision(requestData.getLosscommision());
	 * user.setAppname(requestData.getAppname());
	 * user.setAppurl(requestData.getAppurl());
	 * user.setCommisionpercentage(requestData.getCommisionpercentage());
	 * 
	 * ArrayList<UserLiability> masterexpo =
	 * marketExpoDAO.getNetExposure(user.getUserid(),"subadminid");
	 * 
	 * if (masterexpo.size()>0) { if (masterexpo.get(0).getLiability() != 0.00) {
	 * rbean.setMessage("Please Clear Exposure To Continue!");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.OK); } }
	 * appBeanRepo.save(appdetails); userRepo.save(user);
	 * rbean.setMessage("Updated successfully"); rbean.setType("success");
	 * rbean.setTitle("success"); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.OK); }
	 * 
	 * rbean.setMessage("Something went wrong"); rbean.setType("error");
	 * rbean.setTitle("opps.."); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.BAD_REQUEST); }
	 */

	/*
	 * @RequestMapping(value="/saveSpMasterDetails",method=RequestMethod.POST)
	 * public ResponseEntity<Object> saveSpMasterDetails(@RequestBody EXUser
	 * requestData){ HttpSession session = request.getSession(true); ResponseBean
	 * rbean = new ResponseBean(); SimpleDateFormat dateFormater = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Calendar calendar = new
	 * GregorianCalendar(); TimeZone timeZone = calendar.getTimeZone(); EXUser
	 * usersession =(EXUser) session.getAttribute("user"); DecimalFormat df = new
	 * DecimalFormat("#0.00"); if(usersession!=null) { EXUser user =
	 * userRepo.findByUserid(requestData.getUserid());
	 * 
	 * 
	 * user.setUpdaedon(dtUtil.convTimeZone2(dateFormater.format(new Date()),
	 * timeZone.getID().toString(), "GMT"));
	 * 
	 * user.setReference(requestData.getReference());
	 * user.setUsername(requestData.getUsername());
	 * user.setContact(requestData.getContact());
	 * if(requestData.getPassword().length()<6){
	 * rbean.setMessage("Password should be minimum 6 digits.!");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); }
	 * user.setPassword(requestData.getPassword());
	 * user.setMobappcharge(requestData.getMobappcharge());
	 * user.setOddsloss(requestData.getOddsloss());
	 * user.setFancyloss(requestData.getFancyloss());
	 * user.setFancywin(requestData.getFancywin());
	 * if(user.getPrimarybalance().compareTo(requestData.getPrimarybalance()) ==0){
	 * user.setPrimarybalance(requestData.getPrimarybalance()); }else{ EXChipDetail
	 * chipdetail = new EXChipDetail(); EXChipDetail chipdetail2 = new
	 * EXChipDetail();
	 * chipdetail.setChips(Double.parseDouble(requestData.getPrimarybalance().
	 * toString())); chipdetail.setUserid(usersession.getUserid());
	 * chipdetail.setNarration("Credit limit changed for " +user.getUserid());
	 * chipdetail.setCreatedOn(new Date());
	 * chipdetail.setParentid(usersession.getId().toString().toString());
	 * chipdetail.setType(EXConstants.CrdLimit);
	 * chipdetail.setUserbalance(user.getSecondrybanlce());
	 * chipdetail.setCrdr(true);
	 * user.setPrimarybalance(requestData.getPrimarybalance());
	 * 
	 * chipdetail2.setChips(Double.parseDouble(requestData.getPrimarybalance().
	 * toString())); chipdetail2.setUserid(user.getUserid());
	 * chipdetail2.setNarration("Credit limit changed By "
	 * +usersession.getUserid()); chipdetail2.setCreatedOn(new Date());
	 * chipdetail2.setParentid(user.getParentid());
	 * chipdetail2.setType(EXConstants.CrdLimit);
	 * chipdetail2.setUserbalance(user.getSecondrybanlce());
	 * chipdetail2.setCrdr(true); chipdetailRepo.save(chipdetail);
	 * chipdetailRepo.save(chipdetail2);
	 * 
	 * }
	 * 
	 * String searchvia = ""; String id = ""; if(user.getUsertype() == 3) { id =
	 * user.getUserid(); searchvia = "userid"; }else if(user.getUsertype() == 0) {
	 * id = user.getId().toString(); searchvia = "supermasterid"; }else
	 * if(user.getUsertype() == 1) { id = user.getId().toString(); searchvia =
	 * "masterid"; }else if(user.getUsertype() == 2) { id = user.getId().toString();
	 * searchvia = "dealerid"; } ArrayList<UserLiability> masterexpo =
	 * marketExpoDAO.getNetExposure(id,searchvia);
	 * 
	 * if (masterexpo.size()>0) { if (masterexpo.get(0).getLiability() != 0.00) {
	 * rbean.setMessage("Please Clear Exposure To Continue!");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.OK); } }
	 * 
	 * userRepo.save(user); rbean.setMessage("Updated successfully");
	 * rbean.setType("success"); rbean.setTitle("success"); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.OK); }
	 * 
	 * rbean.setMessage("Something went wrong"); rbean.setType("error");
	 * rbean.setTitle("opps.."); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.BAD_REQUEST); }
	 */

	@RequestMapping(value = "/takeappcharge/{eventid}", method = RequestMethod.GET)
	public ResponseEntity<Object> takeappcharge(@PathVariable("eventid") String eventid) {
		log.info("inside takeappcharge");
		log.info(EXConstants.LOG_REQUEST + " " + eventid);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		/*
		 * if(usersession!=null) { AppCharge appcharge =
		 * appchargeRepo.findByEventidAndUseridAndAppid(eventid,
		 * usersession.getUserid(), usersession.getAppid()); if(appcharge ==null) {
		 * AppCharge newappcharge = new AppCharge();
		 * newappcharge.setUserid(usersession.getUserid());
		 * newappcharge.setEventid(eventid);
		 * newappcharge.setSubadminid(usersession.getSubadminid());
		 * newappcharge.setAdminid(usersession.getAdminid());
		 * newappcharge.setAppid(usersession.getAppid());
		 * newappcharge.setAmount(usersession.getMobappcharge()); admin =
		 * userRepo.findByid(usersession.getAdminid()); subadmin =
		 * userRepo.findByid(usersession.getSubadminid());
		 * 
		 * if(admin!=null && subadmin!=null) {
		 * subadmin.setSecondrybanlce(subadmin.getSecondrybanlce().subtract(BigDecimal.
		 * valueOf(usersession.getMobappcharge())));
		 * admin.setSecondrybanlce(admin.getSecondrybanlce().add(BigDecimal.valueOf(
		 * usersession.getMobappcharge()))); UpdatedList.add(subadmin);
		 * UpdatedList.add(admin); userRepo.saveAll(UpdatedList);
		 * 
		 * appchargeRepo.save(newappcharge); rbean.setMessage("Successfully Paid");
		 * rbean.setType("success"); rbean.setTitle("success"); return new
		 * ResponseEntity<Object>(rbean,HttpStatus.OK); }
		 * 
		 * 
		 * }else { rbean.setMessage("Already Paid"); rbean.setType("error");
		 * rbean.setTitle("opps.."); return new
		 * ResponseEntity<Object>(rbean,HttpStatus.NO_CONTENT); } }
		 */
		rbean.setMessage("Something went wrong");
		rbean.setType("error");
		rbean.setTitle("opps..");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	/*
	 * @RequestMapping(value="/setOddsRange/{oddsRange}",method=RequestMethod.POST)
	 * public ResponseEntity<Object> setOddsRange(@PathVariable("oddsRange") String
	 * range){ ResponseBean rbean = new ResponseBean(); HttpSession session =
	 * request.getSession(); EXUser usersession = (EXUser)
	 * session.getAttribute("user");
	 * 
	 * if(usersession!=null){ EXUser user = userRepo.findByid(usersession.getId());
	 * if(user!=null){ user.setOddsRange(Double.parseDouble(range));
	 * usersession.setOddsRange(Double.parseDouble(range));
	 * session.removeAttribute("user"); session.setAttribute("user", usersession);
	 * if(userRepo.save(user)!=null){
	 * rbean.setMessage("Range Successfully Updated"); rbean.setType("success");
	 * rbean.setTitle("success"); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.OK); } } }
	 * rbean.setMessage("Something went wrong"); rbean.setType("error");
	 * rbean.setTitle("opps.."); return new
	 * ResponseEntity<Object>(rbean,HttpStatus.BAD_REQUEST); }
	 */
	/*
	 * @RequestMapping(value="/getOddsRange",method=RequestMethod.GET) public
	 * ResponseEntity<Object> getOddsRange(){ ResponseBean rbean = new
	 * ResponseBean(); HttpSession session = request.getSession(); EXUser
	 * usersession = (EXUser) session.getAttribute("user");
	 * 
	 * if(usersession!=null){ EXUser user = userRepo.findByid(usersession.getId());
	 * if(user!=null){
	 * 
	 * return new ResponseEntity<Object>(user.getOddsRange(),HttpStatus.OK); } }
	 * return new ResponseEntity<Object>(rbean,HttpStatus.BAD_REQUEST); }
	 * 
	 */

	@RequestMapping(value = "/getAppURLList", method = RequestMethod.GET)
	public ResponseEntity<Object> getAppURLList() {
		log.info("inside getAppURLList");
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<appBean> list = new ArrayList<>();
		if (usersession != null) {
			list = appBeanRepo.findByIsMultipleAndActive(true, true);

			// For Removing Dublicate Values
			for (int in = 0; in <= list.size() - 1; in++) {
				for (int j = list.size() - 1; j > in; j--) {
					if (list.get(j).getAppurl().trim().equalsIgnoreCase(list.get(in).getAppurl().trim())) {

						list.remove(in);
						if (in == 0) {
							j = list.size();
						}
					}
				}
			}
			// For Removing Dublicate Values
			return new ResponseEntity<Object>(list, HttpStatus.OK);

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getChild/{childtype}/{downlinetype}", method = RequestMethod.GET)
	public ResponseEntity<Object> getChild(@PathVariable("childtype") Integer childtype,
			@PathVariable("downlinetype") String downlinetype) {
		log.info("inside getChild");
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		ResponseBean responseBean = new ResponseBean();
		ArrayList<EXUser> userlist = new ArrayList<EXUser>();
		EXUser bean = null;
		if (usersession != null) {
			if (downlinetype.equalsIgnoreCase(EXConstants.DownLine)) {
				userlist = userRepo.findByParentidAndUsertype(usersession.getId().toString().toString(), childtype);

			}

			else {
				bean = userRepo.findByid(Integer.parseInt(usersession.getParentid()));
				if (bean != null) {
					userlist.add(bean);
				}
			}
		}
		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		hm.put("data", userlist);
		hm.put("status", "Success");

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@RequestMapping(value = "/searchUserdetail/{userid}", method = RequestMethod.GET)
	public ResponseEntity<Object> searchUserdetail(@PathVariable("userid") String userid) {
		log.info("inside searchUserdetail");
		log.info(EXConstants.LOG_REQUEST + " " + userid);
		ResponseBean responseBean = new ResponseBean();
		EXUser user = null;

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			user = userRepo.findByUserid(userid);

			if (user != null) {
				if (user.getParentid().equalsIgnoreCase(usersession.getId().toString().toString())) {
					return new ResponseEntity<Object>(user, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
				}

			} else {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/findUserType/{userid}", method = RequestMethod.GET)
	public ResponseEntity<Object> findUserType(@PathVariable("userid") String userid) {
		log.info("inside findUserType");
		log.info(EXConstants.LOG_REQUEST + " " + userid);
		ResponseBean responseBean = new ResponseBean();
		EXUser user = null;

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			user = userRepo.findByUserid(userid);

			if (user != null) {
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getMaxPartShip", method = RequestMethod.POST)
	public ResponseEntity<Object> getMaxPartShip(@RequestBody LinkedHashMap<String, String> partnership) {
		log.info("inside getMaxPartShip");
		log.info(EXConstants.LOG_REQUEST + " " + partnership);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		BigDecimal temp = new BigDecimal("0.0");
		HashMap<String, String> hm = new HashMap<String, String>();
		if (usersession != null) {
			EXUser user1 = new EXUser();
			try {
				user1 = userRepo.findByid(Integer.parseInt(partnership.get("master")));

				if (user1 != null) {
					ArrayList<EXUser> userlist = userRepo.findByParentid(partnership.get("master"));
					EXUser bean = userRepo.findByid(Integer.parseInt(partnership.get("master")));
					if (bean != null) {

						if (bean.getUsertype() == 5) {
							hm.put("p1", bean.getAdminpartership().toString());
							hm.put("p2", bean.getSubadminpartnership().toString());

						} else if (bean.getUsertype() == 0) {
							hm.put("p1", bean.getSubadminpartnership().toString());
							hm.put("p2", bean.getSupermastepartnership().toString());
						}

						else if (bean.getUsertype() == 1) {

							if (usersession.getUsertype() == 4 && bean.getUsertype() == 1) {
								hm.put("p1", bean.getSubadminpartnership().toString());
								hm.put("p2", bean.getMasterpartership().toString());
							}

							else if (usersession.getUsertype() == 0 && bean.getUsertype() == 1) {
								hm.put("p1", bean.getSupermastepartnership().toString());
								hm.put("p2", bean.getMasterpartership().toString());
							}

							else if (usersession.getUsertype() == 5 && bean.getUsertype() == 1) {
								hm.put("p1", bean.getSubadminpartnership().toString());
								hm.put("p2", bean.getMasterpartership().toString());
							}

						} else if (bean.getUsertype() == 2) {
							if (usersession.getUsertype() == 4 && bean.getUsertype() == 2) {
								hm.put("p1", bean.getSubadminpartnership().toString());
								hm.put("p2", bean.getDelearpartership().toString());
							} else if (usersession.getUsertype() == 0 && bean.getUsertype() == 2) {
								hm.put("p1", bean.getSupermastepartnership().toString());
								hm.put("p2", bean.getDelearpartership().toString());
							} else if (usersession.getUsertype() == 1 && bean.getUsertype() == 2) {
								hm.put("p1", bean.getMasterpartership().toString());
								hm.put("p2", bean.getDelearpartership().toString());
							} else if (usersession.getUsertype() == 5 && bean.getUsertype() == 2) {
								hm.put("p1", bean.getSubadminpartnership().toString());
								hm.put("p2", bean.getDelearpartership().toString());
							}
						}

					}

					if (userlist.size() > 0) {
						for (EXUser userbean : userlist) {

							if (userbean.getUsertype() == 2) {

								if (userbean.getDelearpartership().compareTo(temp) == 1) {
									temp = userbean.getDelearpartership();
								}
							} else if (userbean.getUsertype() == 1) {

								if (userbean.getMasterpartership().compareTo(temp) == 1) {
									temp = userbean.getMasterpartership();
								}
							} else if (userbean.getUsertype() == 0) {

								if (userbean.getSupermastepartnership().compareTo(temp) == 1) {
									temp = userbean.getSupermastepartnership();
								}
							} else if (userbean.getUsertype() == 3) {
								if (userlist.size() == 1 && bean.getUsertype() == 2) {
									temp = bean.getDelearpartership();
								}
							}

						}
					}
					hm.put("partnership", temp.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@RequestMapping(value = "/minusPartnership", method = RequestMethod.POST)
	public ResponseEntity<Object> minusPartnership(@RequestBody LinkedHashMap<String, String> partnership) {
		log.info("inside minusPartnership");
		log.info(EXConstants.LOG_REQUEST + " " + partnership);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		BigDecimal masterPartnership = new BigDecimal(partnership.get("masterPartnership").toString());
		BigDecimal adminPartnershiphidden = new BigDecimal(partnership.get("adminPartnershiphidden").toString());
		BigDecimal masterPartnershiphidden = new BigDecimal(partnership.get("masterPartnershiphidden").toString());
		BigDecimal downlinemaxpartnership = new BigDecimal(partnership.get("downlinemaxpartnership").toString());

		HashMap<String, String> hm = new HashMap<String, String>();
		if (usersession != null) {

			if (masterPartnership.compareTo(downlinemaxpartnership) == 1
					|| masterPartnership.compareTo(downlinemaxpartnership) == 0) {
				hm.put("max", "true");
				BigDecimal temp = masterPartnershiphidden.subtract(masterPartnership);
				BigDecimal temp1 = adminPartnershiphidden.add(temp);
				if (temp1.doubleValue() < 0.0) {
					hm.put("max", "minus");

				}
				hm.put("adminPartnership", temp1.toString());

			} else {

				EXUser userbean = userRepo.findByid(Integer.parseInt(partnership.get("master")));
				if (userbean.getUsertype() == 2) {
					BigDecimal temp = masterPartnershiphidden.subtract(masterPartnership);
					BigDecimal temp1 = adminPartnershiphidden.add(temp);
					hm.put("adminPartnership", temp1.toString());

				} else {
					hm.put("max", "false");
				}

			}
		}
		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/betLockByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> betLockByParent(@PathVariable("id") String id) {
		log.info("inside betLockByParent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setBetlock(true);
						/*if (Integer.parseInt(bean.getParentid()) != usersession.getId()) {
							bean.setBetlockbyParent(true);

						}*/
						bean.setBetlockbyParent(true);
						bean.setBetlockBy(usersession.getId());
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setBetlock(true);
						
						/*if (Integer.parseInt(bean.getParentid()) != usersession.getId()) {
							bean.setBetlockbyParent(true);
						}*/
						bean.setBetlockbyParent(true);
						bean.setBetlockBy(usersession.getId());
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setBetlock(true);
						/*if (Integer.parseInt(bean.getParentid()) != usersession.getId()) {
							bean.setBetlockbyParent(true);
						}*/
						bean.setBetlockbyParent(true);
						bean.setBetlockBy(usersession.getId());
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setBetlock(true);
						/*if (Integer.parseInt(bean.getParentid()) != usersession.getId()) {
							bean.setBetlockbyParent(true);
						}*/
						bean.setBetlockbyParent(true);
						bean.setBetlockBy(usersession.getId());
						/*rediesdao.deleteUserById(bean.getUserid());*/
					}
					userRepo.saveAll(userbeanlist);
					
				} else if (ubean.getUsertype() == 3) {
					ubean.setBetlock(true);
					ubean.setBetlockBy(usersession.getId());
					ubean.setBetlockbyParent(true);
					userRepo.save(ubean);
					/*rediesdao.deleteUserById(ubean.getUserid());*/
				}

				rbean.setMessage("Bet Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);

	}

	@Transactional
	@RequestMapping(value = "/betUnLockByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> betUnLockByParent(@PathVariable("id") String id) {
		log.info("inside betUnLockByParent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getBetlock()) {
							if (bean.getBetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setBetlockbyParent(false);
								bean.setBetlock(false);
								bean.setBetlockBy(0);
								/*rediesdao.deleteUserById(bean.getUserid());*/
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						

					}
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						if(bean.getBetlock()) {
							if (bean.getBetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setBetlockbyParent(false);
								bean.setBetlock(false);
								bean.setBetlockBy(0);
								/*rediesdao.deleteUserById(bean.getUserid());*/
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getBetlock()) {
							if (bean.getBetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setBetlockbyParent(false);
								bean.setBetlock(false);
								bean.setBetlockBy(0);
								/*rediesdao.deleteUserById(bean.getUserid());*/
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						if(bean.getBetlock()) {
							if (bean.getBetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setBetlockbyParent(false);
								bean.setBetlock(false);
								bean.setBetlockBy(0);
								/*rediesdao.deleteUserById(bean.getUserid());*/
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 3) {
					if(ubean.getBetlock()) {
						if (ubean.getBetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

							ubean.setBetlockbyParent(false);
							ubean.setBetlock(false);
							ubean.setBetlockBy(0);
							/*rediesdao.deleteUserById(ubean.getUserid());*/
						} else {

							rbean.setMessage("Please ask upline to Unlock Bet..");
							rbean.setType("error");
							rbean.setTitle("Oops...");
							return new ResponseEntity<Object>(rbean, HttpStatus.OK);
						}
						userRepo.save(ubean);
					}
					
				}

				rbean.setMessage("Bet Un Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getParent/", method = RequestMethod.GET)
	public ResponseEntity<Object> getParent() {
		log.info("inside getParent");
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		ArrayList<EXUser> userlist = null;

		if (usersession != null) {

			userlist = userRepo.findByParentidAndUsertype(usersession.getId().toString().toString(),
					usersession.getUsertype());

		}

		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		hm.put("data", userlist);
		hm.put("status", "Success");

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPartnership", method = RequestMethod.GET)
	public ResponseEntity<Object> getPartnership(@RequestParam String userid) {
		log.info("inside getPartnership");
		log.info(EXConstants.LOG_REQUEST + " " + userid);

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		EXUser user = null;

		if (usersession != null) {

			user = userRepo.findByUserid(userid);
			if (user.getUsertype() == 5) {
				hm.put("upline", user.getAdminpartership());
				hm.put("self", user.getSubadminpartnership());
				hm.put("uplinename", "Super Admin");
				hm.put("selfname", "Admin");
			} else if (user.getUsertype() == 0) {
				hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
				hm.put("self", user.getSupermastepartnership());
				hm.put("uplinename", "Admin");
				hm.put("selfname", "Sub Admin");
			} else if (user.getUsertype() == 1) {
				if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasum)) {
					hm.put("upline", user.getAdminpartership().add(user.getSupermastepartnership())
							.add(user.getSubadminpartnership()));
					hm.put("uplinename", "Subadmin");
				} else {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
					hm.put("uplinename", "Admin");
				}
				hm.put("self", user.getMasterpartership());

				hm.put("selfname", "Supermaster");
			} else if (user.getUsertype() == 2) {
				if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasumd)) {
					hm.put("upline", user.getAdminpartership().add(user.getSupermastepartnership())
							.add(user.getSubadminpartnership()).add(user.getMasterpartership()));
					hm.put("uplinename", "Super Master");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasud)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership())
							.add(user.getSupermastepartnership()));
					hm.put("uplinename", "Subadmin");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbamd)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership())
							.add(user.getMasterpartership()));
					hm.put("uplinename", "SuperMaster");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbad)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
					hm.put("uplinename", "Admin");
					hm.put("selfname", "Master");
				}
				hm.put("self", user.getDelearpartership());
			} else if (user.getUsertype() == 3) {
				if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasumdc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSupermastepartnership())
							.add(user.getSubadminpartnership()).add(user.getMasterpartership()));
					hm.put("self", user.getDelearpartership());
					hm.put("uplinename", "Super Master");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasudc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership())
							.add(user.getSupermastepartnership()));
					hm.put("self", user.getDelearpartership());
					hm.put("uplinename", "Subadmin");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasumc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership())
							.add(user.getSupermastepartnership()));
					hm.put("self", user.getMasterpartership());
					hm.put("uplinename", "Subadmin");
					hm.put("selfname", "Super Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbasuc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
					hm.put("self", user.getSupermastepartnership());
					hm.put("uplinename", "Admin");
					hm.put("selfname", "Super Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbamdc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership())
							.add(user.getMasterpartership()));
					hm.put("self", user.getDelearpartership());
					hm.put("uplinename", "Super Master");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbamc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
					hm.put("self", user.getMasterpartership());
					hm.put("uplinename", "Admin");
					hm.put("selfname", "SuperMaster");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbadc)) {
					hm.put("upline", user.getAdminpartership().add(user.getSubadminpartnership()));
					hm.put("self", user.getDelearpartership());
					hm.put("uplinename", "Admin");
					hm.put("selfname", "Master");
				} else if (user.getUserchain().equalsIgnoreCase(EXConstants.sbac)) {
					hm.put("upline", user.getAdminpartership());
					hm.put("self", user.getSubadminpartnership());
					hm.put("uplinename", "Super Admin");
					hm.put("selfname", "Admin");
				}

			}

		}

		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@RequestMapping("/tvApi")
	public ResponseEntity<String> tvApi(@RequestParam String eventId) {
		try {

			RestTemplate restTemplate = new RestTemplate();
			String tvUrl = restTemplate.getForObject("https://aaaa-14afe-cc669-betfair.firebaseio.com/liveTV1.json",
					String.class);
			tvUrl = tvUrl.substring(1, tvUrl.length() - 1);
			String result = restTemplate.getForObject(tvUrl + eventId, String.class);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping("/tvApi2")
	public ResponseEntity<String> tvApi2() {
		try {

			RestTemplate restTemplate = new RestTemplate();
			String tvUrl = restTemplate.getForObject("http://18.132.195.200/tv/", String.class);

			return new ResponseEntity<String>(tvUrl, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping("/tvApiMob")
	public ResponseEntity<String> tvApiMob(@RequestParam String eventId) {
		try {

			RestTemplate restTemplate = new RestTemplate();
			// String result =
			// restTemplate.getForObject("http://cricketmasti99.com/channel.php",
			// String.class);

			String result = restTemplate.getForObject("http://104.211.200.90:3002/api/odds/liveTv?eventId=" + eventId,
					String.class);
			// return new ResponseEntity<String>(HttpStatus.OK);
			String str = result.replace("/player", "http://galaxyinfo.tech/player");
			// str.replace("<head>", "<head><link rel='stylesheet' type='text/css'
			// href='https://dzm0kbaskt4pv.cloudfront.net/js/videoplayer/v1.css'>");
			return new ResponseEntity<String>(str.replace("<head>",
					"<head><link rel='stylesheet' type='text/css' href='http://galaxyinfo.tech/v1-mobile.css'>"),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping("/countUserByCategory")
	public ResponseEntity<Object> countUserByCategory() {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			if (usersession != null) {
				Integer admincount = userDao.countUserByCategory(5);
				Integer subadmincount = userDao.countUserByCategory(0);
				Integer supermastercount = userDao.countUserByCategory(1);
				Integer mastercount = userDao.countUserByCategory(2);
				Integer clientcount = userDao.countUserByCategory(3);

				hm.put("admincount", admincount);
				hm.put("subadmincount", subadmincount);
				hm.put("supermastercount", supermastercount);
				hm.put("mastercount", mastercount);
				hm.put("clientcount", clientcount);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/setCashTransactionNew", method =
	 * RequestMethod.POST) public ResponseEntity<Object>
	 * setCashTransactionNew(@RequestBody ExCashTransation trnsaction) throws
	 * ParseException { HttpSession session = request.getSession(true); EXUser
	 * usersession = (EXUser) session.getAttribute("user"); DecimalFormat df = new
	 * DecimalFormat("#0.00"); ResponseBean rbean = new ResponseBean();
	 * if(trnsaction.getPassword().toLowerCase().equalsIgnoreCase(usersession.
	 * getPassword().toLowerCase())){ EXUser user =
	 * userRepo.findByUserid(trnsaction.getUserid()); if (usersession != null) {
	 * if(trnsaction.getType().equalsIgnoreCase(EXConstants.Dena)){
	 * nativeQueryDao.updateAvailableBalance(usersession.getId(),new
	 * BigDecimal(-trnsaction.getAmount()));
	 * nativeQueryDao.updateAvailableBalance(user.getId(),new
	 * BigDecimal(trnsaction.getAmount()));
	 * 
	 * }else{ if(trnsaction.getMastetbalanceplus()){
	 * nativeQueryDao.updateAvailableBalance(usersession.getId(),new
	 * BigDecimal(trnsaction.getAmount()));
	 * nativeQueryDao.updateAvailableBalance(user.getId(),new
	 * BigDecimal(-trnsaction.getAmount())); }else{
	 * nativeQueryDao.updateAvailableBalance(user.getId(),new
	 * BigDecimal(-trnsaction.getAmount())); }
	 * 
	 * trnsaction.setAmount(-trnsaction.getAmount()); }
	 * 
	 * if(trnsaction.getPaymenttype().equalsIgnoreCase(EXConstants.Dena)){
	 * 
	 * MarketResult mrresult = new MarketResult(); EXLedger ledger = new EXLedger();
	 * 
	 * mrresult.setDate(new Date()); mrresult.setIsResult(true);
	 * mrresult.setMatchid((int) (long) new Date().getTime());
	 * mrresult.setMarketid(mrresult.getMatchid().toString());
	 * mrresult.setMatchname("Amount paid");
	 * mrresult.setSelectionname("Amount paid");
	 * mrresult.setSportname("Settlement"); mrresult.setType("Settlement");
	 * mrresult.setMarkettype("Settlement"); mrresult.setMarketname("Settlement");
	 * mrresult.setResult(-1); mrresult.setSelectionid(1); mrresult.setStatus(true);
	 * mrresult.setSportid(0); mrresult.setSportname("Settlement");
	 * 
	 * 
	 * 
	 * ledger.setMatchname("Amount Paid"); ledger.setMarkettype("Settlement");
	 * ledger.setCollectionname(trnsaction.getPaymentmode());
	 * ledger.setMatchid((int) (long) new Date().getTime()); ledger.setCreatedon(new
	 * Date()); ledger.setParentid(usersession.getId());
	 * ledger.setChildid(user.getId()); if(user.getUsertype() ==3){
	 * ledger.setUplineamount(trnsaction.getAmount());
	 * 
	 * }else{ ledger.setAmount(trnsaction.getAmount());
	 * 
	 * } ledger.setAmount(trnsaction.getAmount());
	 * ledger.setMarketid(ledger.getMatchid().toString());
	 * ledger.setType(trnsaction.getRemark()); ledger.setUserid(user.getUserid());
	 * matchResultrepo.save(mrresult); ledgerRepo.save(ledger);
	 * 
	 * rbean.setMessage("Transaction Created successfully!");
	 * rbean.setType("success"); rbean.setTitle("Success"); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.OK); }else{
	 * 
	 * MarketResult mrresult = new MarketResult(); mrresult.setDate(new Date());
	 * mrresult.setIsResult(true); mrresult.setMatchid((int) (long) new
	 * Date().getTime()); mrresult.setMarketid(mrresult.getMatchid().toString());
	 * mrresult.setMatchname("Amount Recived");
	 * mrresult.setSelectionname("Amount Recived");
	 * mrresult.setSportname("Settlement"); mrresult.setType("Settlement");
	 * mrresult.setMarkettype("Settlement"); mrresult.setMarketname("Settlement");
	 * mrresult.setResult(-1); mrresult.setSelectionid(1); mrresult.setStatus(true);
	 * mrresult.setSportid(0); mrresult.setSportname("Settlement");
	 * 
	 * 
	 * 
	 * EXLedger ledger = new EXLedger(); ledger.setMatchname("Amount Paid");
	 * ledger.setMarkettype("Settlement");
	 * ledger.setCollectionname(trnsaction.getPaymentmode());
	 * ledger.setMatchid((int) (long) new Date().getTime()); ledger.setCreatedon(new
	 * Date()); ledger.setParentid(usersession.getId());
	 * ledger.setChildid(user.getId()); if(user.getUsertype() ==3){
	 * ledger.setUplineamount(trnsaction.getAmount());
	 * 
	 * }else{ ledger.setAmount(trnsaction.getAmount());
	 * 
	 * }
	 * 
	 * ledger.setAmount(trnsaction.getAmount());
	 * ledger.setMarketid(ledger.getMatchid().toString());
	 * ledger.setType(trnsaction.getRemark()); ledger.setUserid(user.getUserid());
	 * 
	 * matchResultrepo.save(mrresult); ledgerRepo.save(ledger);
	 * rbean.setMessage("Transaction Created successfully!");
	 * rbean.setType("success"); rbean.setTitle("Success");
	 * 
	 * }
	 * 
	 * 
	 * } }else{ rbean.setMessage(EXConstants.passwordNotOk); rbean.setType("error");
	 * rbean.setTitle("Oops...");
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); }
	 */

	@Transactional
	@RequestMapping(value = "/setCashTransactionNew", method = RequestMethod.POST)
	public ResponseEntity<Object> setCashTransactionNew(@RequestBody ExCashTransation trnsaction)
			throws ParseException {
		log.info("inside setCashTransactionNew");
		log.info(EXConstants.LOG_REQUEST + " " + trnsaction);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");
		ResponseBean rbean = new ResponseBean();
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if (trnsaction.getPassword().toLowerCase().equalsIgnoreCase(usersession.getPassword().toLowerCase())) {
			EXUser user = userRepo.findByUserid(trnsaction.getUserid());
			if (usersession != null ) {
				if(trnsaction.getAmount()>0) {
					Integer matchId = (int) (long) new Date().getTime();
					hm.put("parentid", usersession.getId());
					hm.put("childid", user.getId());
					hm.put("isresult", true);
					hm.put("marketid", matchId);
					hm.put("matchid", matchId);
					hm.put("selectionid", 1);
					hm.put("sportid", 0);
					hm.put("marketname", "Settlement");
					hm.put("markettype", "Settlement");
					hm.put("sportname", "Settlement");
					hm.put("status", 1);
					hm.put("type", "Settlement");
					hm.put("debit", 0);
					hm.put("credit", 0);
					hm.put("parentuserid", usersession.getUserid());
					hm.put("childuserid", user.getUserid());
					hm.put("result", 1);
					hm.put("fromto", usersession.getUserid() + " To" + user.getUserid());

					if (trnsaction.getPaymenttype().equalsIgnoreCase(EXConstants.Dena)) {

						hm.put("matchname", "Amount paid");
						hm.put("selectionname", "Amount paid");
						hm.put("selectionname", "Amount paid");
						hm.put("amount", -trnsaction.getAmount());
						hm.put("settlementtype", "dena");
						hm.put("description",
								"Settlement By" + usersession.getUserid() + " To" + user.getUserid() + "(lena)");

					} else {

						hm.put("matchname", "Amount Recived");
						hm.put("selectionname", "Amount Recived");
						hm.put("amount", trnsaction.getAmount());
						hm.put("settlementtype", "lena");
						hm.put("description",
								"Settlement By" + usersession.getUserid() + " To" + user.getUserid() + "(dena)");

					}
					
					if(user.getUsertype() ==3) {
						hm.put("cashTranscaton", "cashTranscatonUser");
					}else {
						hm.put("cashTranscaton", "cashTranscaton");
					}
					
					String result = mysqlProcedureDao.cashTranction(hm);
					if (result.equalsIgnoreCase(EXConstants.Success)) {
						rbean.setMessage("Transaction Created successfully!");
						rbean.setType("success");
						rbean.setTitle("Success");
						return new ResponseEntity<Object>(rbean, HttpStatus.OK);
					} else {
						rbean.setMessage(result);
						rbean.setType("error");
						rbean.setTitle("Oops...");
					}
		
				}else {
					rbean.setMessage("Please enter Valid Amount..");
					rbean.setType("error");
					rbean.setTitle("Oops...");
				}
			
			}
		} else {
			rbean.setMessage(EXConstants.passwordNotOk);
			rbean.setType("error");
			rbean.setTitle("Oops...");

		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/makeSettlement/{id}", method = RequestMethod.GET)
	 * public ResponseEntity<Object> makeSettlement(@PathVariable("id") Integer id)
	 * {
	 * 
	 * HttpSession session = request.getSession(true); EXUser usersession = (EXUser)
	 * session.getAttribute("user"); EXUser user = userRepo.findByid(id);
	 * 
	 * ResponseBean rbean = new ResponseBean();
	 * 
	 * if(user!=null){
	 * 
	 * ArrayList<UserLiability> libilitylist = new ArrayList<UserLiability>(); if
	 * (user.getUsertype() == 5) { libilitylist
	 * =liabilityRepository.findBySubadminidAndIsactive(user.getUserid(), true);
	 * 
	 * }else if (user.getUsertype() == 0) {
	 * 
	 * libilitylist
	 * =liabilityRepository.findBySubadminidAndIsactive(user.getUserid(), true);
	 * }else if (user.getUsertype() == 1) { libilitylist
	 * =liabilityRepository.findByMasteridAndIsactive(user.getUserid(), true); }else
	 * if (user.getUsertype() == 2) { libilitylist
	 * =liabilityRepository.findByDealeridAndIsactive(user.getUserid(), true); }else
	 * if (user.getUsertype() == 3) {
	 * 
	 * libilitylist =liabilityRepository.findByUseridAndIsactive(user.getUserid(),
	 * true); } if(libilitylist.size()>0){
	 * rbean.setMessage("Please Ask to Downline to clear Libility");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);
	 * 
	 * }else{
	 * 
	 * EXChipDetail chipdetail = new EXChipDetail(); EXChipDetail chipdetailparent =
	 * new EXChipDetail();
	 * 
	 * 
	 * chipdetail.setNarration("Settlement made  By "+usersession.getUserid());
	 * chipdetail.setUserid(user.getUserid()); chipdetail.setCreatedOn(new Date());
	 * chipdetail.setParentid(user.getParentid());
	 * chipdetail.setType(EXConstants.Settlement);
	 * chipdetail.setUserbalance(user.getThirdbalance());
	 * 
	 * if(Double.valueOf(user.getThirdbalance().toString())<Double.valueOf(user.
	 * getSecondrybanlce().toString())){ chipdetail.setCrdr(false);
	 * chipdetail.setChips(Double.valueOf(user.getSecondrybanlce().toString())-
	 * Double.valueOf(user.getThirdbalance().toString()));
	 * 
	 * }else{ chipdetail.setCrdr(true);
	 * if(Double.valueOf(usersession.getSecondrybanlce().toString())>Double.valueOf(
	 * user.getSecondrybanlce().toString())-
	 * Double.valueOf(user.getThirdbalance().toString())){
	 * chipdetail.setChips(Double.valueOf(user.getThirdbalance().toString())-
	 * Double.valueOf(user.getSecondrybanlce().toString()));
	 * 
	 * }else{ rbean.setMessage("You Do not have enough Coin to Update");
	 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST); } }
	 * 
	 * chipdetailparent.setNarration("Settlement made  By "+usersession.getUserid())
	 * ; chipdetailparent.setUserid(usersession.getUserid());
	 * chipdetailparent.setCreatedOn(new Date());
	 * chipdetailparent.setParentid(user.getParentid());
	 * chipdetailparent.setType(EXConstants.Settlement);
	 * chipdetailparent.setUserbalance(user.getThirdbalance());
	 * 
	 * if(Double.valueOf(user.getThirdbalance().toString())<Double.valueOf(user.
	 * getSecondrybanlce().toString())){ chipdetailparent.setCrdr(true);
	 * chipdetailparent.setChips(Double.valueOf(user.getSecondrybanlce().toString())
	 * - Double.valueOf(user.getThirdbalance().toString())); }else{
	 * chipdetailparent.setCrdr(false);
	 * chipdetailparent.setChips(Double.valueOf(user.getThirdbalance().toString())-
	 * Double.valueOf(user.getSecondrybanlce().toString())); }
	 * 
	 * chipdetailRepo.save(chipdetail); chipdetailRepo.save(chipdetailparent);
	 * user.setSecondrybanlce(user.getThirdbalance()); userRepo.save(user);
	 * 
	 * rbean.setMessage("Settlement Successfull"); rbean.setType("success");
	 * rbean.setTitle("Success"); return new ResponseEntity<Object>(rbean,
	 * HttpStatus.OK);
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	 * 
	 * }
	 */
	@RequestMapping(value = "/settledUser/{startdate}/{enddate}", method = RequestMethod.GET)
	public ResponseEntity<Object> settledUser(@PathVariable("startdate") String startdate,
			@PathVariable("startdate") String enddate) {

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int day = 1;
		switch (day) {
		case Calendar.SUNDAY:

			calendar.add(Calendar.DATE, -4);
			log.info(dateFormat.format(calendar.getTime()));
			break;
		case Calendar.MONDAY:

			calendar.add(Calendar.DATE, -4);
			log.info(dateFormat.format(calendar.getTime()));

			break;
		case Calendar.TUESDAY:
			System.out.print(day);
			calendar.add(Calendar.DATE, -day);
			log.info(dateFormat.format(calendar.getTime()));
			break;
		case Calendar.WEDNESDAY:
			// etc.
			System.out.print(day);
			calendar.add(Calendar.DATE, -(day - 3));
			log.info(dateFormat.format(calendar.getTime()));

			break;
		case Calendar.THURSDAY:
			calendar.add(Calendar.DATE, -(day - 3));
			log.info(dateFormat.format(calendar.getTime()));

			break;
		case Calendar.FRIDAY:
			calendar.add(Calendar.DATE, -(day - 3));
			log.info(dateFormat.format(calendar.getTime()));

			break;
		case Calendar.SATURDAY:
			calendar.add(Calendar.DATE, -(day - 3));
			log.info(dateFormat.format(calendar.getTime()));

			break;
		}

		return null;

	}

	@RequestMapping("/tvApiOld")
	public ResponseEntity<String> tvApiOld(@RequestParam String marketid) {
		try {

			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject("http://cricketmasti99.com/channel.php", String.class);

			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "loginadmin", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> loginadmin(@RequestBody EXUser login) {

		
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXUser user = null;

		ResponseBean rbean = new ResponseBean();
		String systemipaddress = "";
		try {
			/*URL url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
			systemipaddress = sc.readLine().trim();
			*/InetAddress localhost = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(localhost);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			Calendar calendar = new GregorianCalendar();
			TimeZone timeZone = calendar.getTimeZone();

			String url = request.getRequestURL().toString();
			String domain = null;
			InetAddress ipAddr1 = InetAddress.getLocalHost();
			EXUserLogin userlogin = null;
			if (usersession != null) {

				if (usersession.getUsertype() == 4 || usersession.getUsertype() == 6) {
					userlogin = userLoginRepo.findByUserid(login.getUserid().toLowerCase());

				} else {
					userlogin = userLoginRepo.findByUseridAndLoginIp(login.getUserid().toLowerCase(),
							localhost.getHostAddress().trim());

				}
				userLoginRepo.delete(userlogin);
			}

			URL url1 = new URL(url);
			domain = url1.getHost();
			if (usersession != null) {
				session.removeAttribute("user");
				usersession = null;
			}

			if (login.getUserid() == null) {
				rbean.setMessage("Invalid User");
				rbean.setType("error");
				rbean.setTitle("Oops...");

			}
			if (login.getPassword() == null) {
				rbean.setMessage("Invalid User");
				rbean.setType("error");
				rbean.setTitle("Oops...");

			}
			if (login.getUserid().toLowerCase().equalsIgnoreCase(login.getPassword().toLowerCase())) {
				user = userRepo.findByUserid(login.getUserid().toLowerCase());
				if (user != null) {
					rbean.setMessage("Invalid User* TC");
					rbean.setType("error");
					rbean.setTitle("Oops...");
					model.addObject("result", rbean);
					model.setViewName("loginhome");
					userRepo.save(user);
				}
			} else {
				user = userRepo.findByUseridAndPassword(login.getUserid().toLowerCase(),
						login.getPassword().toLowerCase());

				if (user == null) {
					rbean.setMessage("Invalid User");
					rbean.setType("error");
					rbean.setTitle("Oops...");

				} else if (user.getUsertype() == 3) {
					rbean.setMessage("Invalid User");
					rbean.setType("error");
					rbean.setTitle("Oops...");

				} else {

					UserIp userip = new UserIp();

//		              		req  = InetAddress.getLocalHost();
					
					/*
					 * appBean appdtl = appBeanRepo.findByid(user.getAppid()); if(user.getUsertype()
					 * != 4) { if(appdtl != null) { String appurl = appdtl.getAppurl();
					 * log.info("appurl: "+appurl+" url:"+url); if(!url.contains(appurl)) {
					 * log.info("Invalid user"); rbean.setMessage("Invalid User");
					 * rbean.setType("error"); rbean.setTitle("Oops..."); return new
					 * ResponseEntity<Object>(rbean, HttpStatus.OK); } } }
					 * 
					 */

					// appBean appdtl = appBeanRepo.findByid(user.getAppid());
					/*
					 * ArrayList<String> items = new
					 * ArrayList(Arrays.asList(appdtl.getAppurl().split("\\s*,\\s*")));
					 * if(!items.contains(domain)){
					 * 
					 * rbean.setMessage("Invalid User"); rbean.setType("error");
					 * rbean.setTitle("Oops..."); return new ResponseEntity<Object>(rbean,
					 * HttpStatus.OK);
					 * 
					 * }
					 */
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String remoteAddr = request.getHeader("X-FORWARDED-FOR");
					if (request != null) {
						remoteAddr = request.getHeader("X-FORWARDED-FOR");
						if (remoteAddr == null || "".equals(remoteAddr)) {
							remoteAddr = request.getRemoteAddr();

						}
					}

					if (user.getAccountlock()) {
						rbean.setMessage("Account Locked Please contact your Upline..!");
						rbean.setType("error");
						rbean.setTitle("Oops...");

					}else 	if (user.getIsaccountclosed()) {
						rbean.setMessage("Account Closed Please contact your Upline..!");
						rbean.setType("error");
						rbean.setTitle("Oops...");

					} else {
						session.setAttribute("user", user);
						rbean.setMessage("user Verify Successfully");
						rbean.setType("success");
						rbean.setTitle("Success");

						if (user.getUsertype() == 4 || user.getUsertype() == 6) {
							userLoginRepo.deleteByUserid(user.getUserid());
						} else {
							userLoginRepo.deleteByUseridAndLoginIp(user.getUserid(), localhost.getHostAddress().trim());

						}
						
						
						 HttpHeaders headers = new HttpHeaders();
						 headers.setContentType(MediaType.APPLICATION_JSON);
						 
						 headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
				            
						 RestTemplate restTemplate = new RestTemplate();
						 
						 JSONObject jObj  = new JSONObject();
						 jObj.put("userId", login.getUserid());
						 jObj.put("password", login.getPassword());
						 
						
						HttpEntity<String> entity = new HttpEntity<String>(jObj.toString(), headers);
						 ResponseEntity<String> respEntity = restTemplate.exchange(EXConstants.BfToken+"auth/login", HttpMethod.POST, entity, String.class);
						   
						 if (respEntity.getStatusCode() == HttpStatus.OK) {
							
							 JSONObject obj  = new JSONObject(respEntity.getBody());
							 
							 
							 
							 
							 if(obj.getInt("statusCode")==200) {
								 session.setAttribute("token", new JSONObject(obj.getString("data")).getString("token"));
							 }
							 
						} 
						
						userlogin = new EXUserLogin();
						userlogin.setSessionid(request.getSession().getId());
						userlogin.setUserid(user.getUserid());
						userlogin.setLogintime(new Date());
						userlogin.setLoginIp(sb.toString());
						session.setAttribute("sessionId", request.getSession().getId());
						session.setAttribute("user", user);
					//	session.setAttribute("appName", appName());
						userLoginRepo.save(userlogin);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception Occured: " + e.getMessage());
		}

		return new ResponseEntity<Object>(rbean, HttpStatus.OK);

	}
	public String  appName(){  

		
    	String appurl = "www."+request.getServerName().substring(6);
    	appBean appbean  = appBeanRepo.findByAppurl(appurl);
    	
    	
    	return appbean.getAppname();
	}

	@RequestMapping(value = "/commssion", method = RequestMethod.GET)
	public ResponseEntity<Object> commssion(@RequestParam String type) {

		ArrayList<EXMatchSessionCommssion> comm = matchSessionCommRepo.findByTypeOrderByCommssion(type);
		if (comm.size() > 0) {
			return new ResponseEntity<Object>(comm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/headerData/{parentId}/{Id}", method = RequestMethod.GET)
	public ResponseEntity<Object> headerData(@PathVariable("parentId") String parentId,
			@PathVariable("Id") Integer Id) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");

		if (usersession != null) {
			try {
				EXUser data = null;
				data = nativeQueryDao.headerData(Integer.parseInt(parentId), Id);
				EXUser bean = nativeQueryDao.creditRefAndAvailableBalSum(Id);
				EXUser ubean = userRepo.findByid(Id);
				EXUser bean1 = nativeQueryDao.profitLossByUserIdWithOutSettlement(ubean.getUserid());
				ArrayList<EXUser> parentlist = userRepo.findByparentid(Id.toString());
				ArrayList<Integer> parentIds = new ArrayList<Integer>();

				if (data != null) {
					if (bean != null) {
						if (ubean.getUsertype() == 6) {
							data.setTotalMasterBalance(0.0);
							data.setDownlineavailableBalance(new BigDecimal("0.0"));
							data.setDownlineWithProfitLoss(0.0);
							data.setMyProfitLoss(0.0);
							data.setDownlinecreditRef(new BigDecimal("0.0"));
							data.setAvailableBalanceWithPnl(0.0);
							data.setUplineAmount(0.0);
							data.setAvailableBalance(new BigDecimal("0.0"));
						} else {
							data.setTotalMasterBalance(data.getCreditRef().doubleValue() - ubean.getUplineAmount());
							data.setDownlineavailableBalance(bean.getDownlineavailableBalance());
							data.setDownlineWithProfitLoss(nativeQueryDao.downleverPnl(Id.toString()));
							data.setMyProfitLoss(bean1.getMyProfitLoss());
							data.setDownlinecreditRef(bean.getDownlinecreditRef());
							data.setAvailableBalanceWithPnl(
									data.getAvailableBalance().doubleValue() + ubean.getAvailableBalanceWithPnl());
							data.setUplineAmount(Double.valueOf(df.format(-ubean.getUplineAmount())));
							data.setAvailableBalance(data.getAvailableBalance());
						}

					}
					return new ResponseEntity<Object>(data, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}

		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/chipData", method = RequestMethod.POST) public
	 * ResponseEntity<Object> chipData(){ EXChipDetail chipdtl = new EXChipDetail();
	 * EXLedgerNew ledger = new EXLedgerNew(); ledger.setUserid("1");
	 * chipdtl.setType("sss"); chipdtl.setCreatedOn(new Date());
	 * exchipRepo.save(chipdtl); ledgerNewRepo.save(ledger); ArrayList<String> lis =
	 * new ArrayList<>(); lis.add("My"); lis.add("Name"); log.info(lis.get(2));
	 * 
	 * return new ResponseEntity<Object>(chipdtl,HttpStatus.OK); }
	 */

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody HashMap<String, String> resuestData) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (usersession != null) {
			if (userRepo.findByid(Integer.parseInt(resuestData.get("parentId"))).getPassword()
					.equalsIgnoreCase(resuestData.get("parentPassword"))) {
				EXUser user = userRepo.findByUserid(resuestData.get("childId"));
				user.setPassword(resuestData.get("newPassword"));
				userRepo.save(user);
				rbean.setMessage("password changed Successfully..");
				rbean.setType("success");
				rbean.setTitle("Success");
			} else {
				rbean.setMessage("Please Enter Your Correct Password!..");
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/selfChangePassword", method = RequestMethod.POST)
	public ResponseEntity<Object> selfChangePassword(@RequestBody HashMap<String, String> resuestData) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (usersession != null) {
			if (userRepo.findByUserid(resuestData.get("userId")).getPassword()
					.equalsIgnoreCase(resuestData.get("currentPassword"))) {
				EXUser user = userRepo.findByUserid(resuestData.get("userId"));
				user.setPassword(resuestData.get("newPassword"));
				user.setPasswordtype("new");
				userRepo.save(user);
				rbean.setMessage("password changed Successfully..");
				rbean.setType("success");
				rbean.setTitle("Success");
			} else {
				rbean.setMessage("Please Enter Your Correct Password!..");
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(value = "/updateCredtRef", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> updateCredtRef(@RequestBody HashMap<String, String> requestData) {
		HttpSession session = request.getSession(true);
		EXUser user = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (user != null) {
			if (requestData.get("password").toLowerCase().equalsIgnoreCase(user.getPassword().toLowerCase())) {

				EXUser parentbean = userRepo.findByid(Integer.parseInt(requestData.get("parentid")));
				if (parentbean != null) {

					String message = mysqlProcedureDao.updateCreditRef(user.getUserid(), requestData.get("userid"),
							Double.parseDouble(requestData.get("creditRef")));
					log.info(message);
					if (message.equalsIgnoreCase("Success")) {
						rbean.setMessage("limit Updated Success Fully..");
						rbean.setType("success");
						rbean.setTitle("Success");
						return new ResponseEntity<Object>(rbean, HttpStatus.OK);
					} else {
						rbean.setMessage(message);
						rbean.setType("success");
						rbean.setTitle("Success");
					}

				}
			} else {
				rbean.setMessage(EXConstants.passwordNotOk);
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}

		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/checkUserId", method = RequestMethod.GET)
	public ResponseEntity<Object> checkUserId(@RequestParam String userId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (usersession != null) {
			if (userRepo.findByUserid(userId) == null) {
				rbean.setMessage("user Id Not Exist");
				rbean.setType("success");
				rbean.setTitle("Success");
			} else {
				rbean.setMessage("userId already Exist!..");
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/userByUserLike", method = RequestMethod.GET)
	public ResponseEntity<Object> userByUserLike(@RequestParam String term) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();
		ArrayList<HashMap<String, String>> list = null;
		if (usersession != null) {

			list = nativeQueryDao.userByUserLike(usersession.getUsertype(), usersession.getId(), term);

			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/subAdminByUserLike", method = RequestMethod.GET)
	public ResponseEntity<Object> subAdminByUserLike(@RequestParam String term) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();
		ArrayList<HashMap<String, String>> list = null;
		if (usersession != null && usersession.getUsertype() == 4 ) {

			list = nativeQueryDao.subAdminByUserLike(usersession.getUsertype(), usersession.getId(), term);

			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/evictCache", method = RequestMethod.GET)
	// @CacheEvict(value = "netExposure", allEntries = true)
	public void evictCache() {
		log.info("cache cleared" + request.getRequestURL());
	}

	@RequestMapping(value = "addPowerUser", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> addPowerUser(@RequestBody HashMap<String, String> requestData) {

		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			if (requestData.get("userid") != null && requestData.get("password") != null) {
				EXUser user = new EXUser();
				user.setUserid(requestData.get("userid"));
				user.setPassword(requestData.get("password"));
				user.setUsername(requestData.get("userid"));
				user.setOddslosscommisiontype("None");
				user.setFancyloss(new BigDecimal("0"));
				user.setOddslosscommisiontype("None");
				user.setCasinocommssion(0.0);
				user.setContact("None");
				user.setParentid("1");
				user.setAdminpartership(new BigDecimal("0"));
				user.setOddsloss(new BigDecimal("0"));
				user.setIsaccountclosed(false);
				user.setFancylosscommisiontype("None");

				user.setUsertype(6);
				user.setCreditRef(new BigDecimal("0.0"));
				user.setAccountlock(false);
				user.setAvailableBalance(new BigDecimal("0.0"));
				user.setAvailableBalanceWithPnl(0.0);
				user.setUplineAmount(0.0);
				user.setAppid(usersession.getAppid());
				user.setPasswordtype("old");
				userRepo.save(user);

				rbean.setMessage("user Created Successfully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);
			} else {
				rbean.setMessage(EXConstants.passwordNotOk);
				rbean.setType("error");
				rbean.setTitle("Oops...");
			}

		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/viewDetail", method = RequestMethod.GET)
	public ResponseEntity<Object> viewDetail(@RequestParam String userId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();
		HashMap<String, Object> hm = null;
		if (usersession != null) {

			EXUser user = userRepo.findByUserid(userId);
			hm = new HashMap<String, Object>();
			hm.put("matchCommssion", user.getOddsloss());
			hm.put("sessionCommssion", user.getFancyloss());
			hm.put("password", user.getPassword());
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "userByParentId/{parentId}", method = RequestMethod.GET)
	// @CacheEvict(value = "userByParentId", allEntries = true)
	public ResponseEntity<Object> userByParentId(@PathVariable String parentId) {

		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<EXUser> list = userRepo.findByparentid(parentId);
			ArrayList<EXUser> newlist = new ArrayList<EXUser>();
			for (EXUser usr : list) {
				EXUser ubean = new EXUser();
				ubean.setUserid(usr.getUserid());
				newlist.add(ubean);
			}
			return new ResponseEntity<Object>(newlist, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "updateKhataBook/{childId}/{amount}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateKhataBook(@PathVariable String childId, @PathVariable String amount) {

		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXKhataBook khataBook = khataBookRepo.findByChildIdAndParentId(childId, usersession.getId().toString());
			if (khataBook != null) {
				khataBookRepo.delete(khataBook);

			}

			khataBook = new EXKhataBook();
			khataBook.setChildId(childId);
			khataBook.setParentId(usersession.getId().toString());
			khataBook.setAmount(amount);
			khataBookRepo.save(khataBook);
			rbean.setMessage("KhataBook Updated Successfully!");
			rbean.setType("success");
			rbean.setTitle("Success");
			return new ResponseEntity<Object>(rbean, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "viewKhataBook/{childId}", method = RequestMethod.GET)
	public ResponseEntity<Object> viewKhataBook(@PathVariable String childId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXKhataBook khataBook = khataBookRepo.findByChildIdAndParentId(childId, usersession.getId().toString());

			return new ResponseEntity<Object>(khataBook, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/lenadena/{parentid}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> lenadena(@PathVariable("parentid") String parentid,
			@PathVariable("usertype") Integer usertype, @RequestParam Boolean active, @RequestParam String type) {
		DecimalFormat df = new DecimalFormat("#0.00");
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		EXUser usersession = (EXUser) session.getAttribute("user");

		ArrayList<EXUser> master = new ArrayList<EXUser>();
		ArrayList<EXUser> totalList = new ArrayList<EXUser>();
		ArrayList<EXUser> client = new ArrayList<EXUser>();
		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> hm1 = new LinkedHashMap<String, Object>();
		DecimalFormat df1 = new DecimalFormat("#0");

		if (usersession != null) {
			list = exUserService.getUserByAparentId(parentid, active, usertype, type, usersession.getId(),false);
			EXUser userbean = new EXUser();
			try {
				BigDecimal creditRef = new BigDecimal("0.0");
				BigDecimal availableBalance = new BigDecimal("0.0");
				Double uplineAmount = 0.0;
				Double availableBalanceWithPnl = 0.0;
				Double profitLoss = 0.0;
				HashMap<String, Object> lena = new HashMap<String, Object>();
				HashMap<String, Object> dena = new HashMap<String, Object>();
				ArrayList<EXUser> lenalist = new ArrayList<EXUser>();
				ArrayList<EXUser> denalist = new ArrayList<EXUser>();
				EXUser ubean = null;
				if (list.size() > 0) {
					for (EXUser bean : list) {
						ubean = new EXUser();
						if (bean.getUsertype() == 3) {
							bean.setUplineAmount(Double.valueOf(df1.format(bean.getUplineAmount())));
							if (bean.getUplineAmount() != 0 && bean.getUplineAmount() < 0) {
								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								ubean.setUserid(bean.getUserid());
								ubean.setUplineAmount(bean.getUplineAmount());
								ubean.setParentid(bean.getParentid());
								ubean.setUsername(bean.getUsername());
								ubean.setUsertype(bean.getUsertype());
								ubean.setHisabkitabtype(bean.getHisabkitabtype());
								lenalist.add(ubean);

							} else if (bean.getUplineAmount() != 0 && bean.getUplineAmount() > 0) {
								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								ubean.setUserid(bean.getUserid());
								ubean.setUplineAmount(bean.getUplineAmount());
								ubean.setParentid(bean.getParentid());
								ubean.setUsername(bean.getUsername());
								ubean.setUsertype(bean.getUsertype());
								ubean.setHisabkitabtype(bean.getHisabkitabtype());
								
								denalist.add(ubean);

							}

						} else {
							bean.setUplineAmount(Double.valueOf(df1.format(-bean.getUplineAmount())));

							if (bean.getUplineAmount() != 0 && bean.getUplineAmount() < 0) {
								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								ubean.setUserid(bean.getUserid());
								ubean.setUplineAmount(bean.getUplineAmount());
								ubean.setParentid(bean.getParentid());
								ubean.setUsername(bean.getUsername());
								ubean.setUsertype(bean.getUsertype());
								ubean.setHisabkitabtype(bean.getHisabkitabtype());
								
								lenalist.add(ubean);

							} else if (bean.getUplineAmount() != 0 && bean.getUplineAmount() > 0) {
								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								ubean.setUserid(bean.getUserid());
								ubean.setUplineAmount(bean.getUplineAmount());
								ubean.setParentid(bean.getParentid());
								ubean.setUsername(bean.getUsername());
								ubean.setUsertype(bean.getUsertype());
								ubean.setHisabkitabtype(bean.getHisabkitabtype());
								
								denalist.add(ubean);

							}

						}

					}

					hm1.put("lena", lenalist);
					hm1.put("dena", denalist);

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				} else {

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				}
			} catch (Exception e) {
				log.error("an exception was thrown", e);
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	/*
	 * @RequestMapping(value = "updateStake", method = RequestMethod.GET) public
	 * ResponseEntity<Object> updateStake() {
	 * 
	 * 
	 * ArrayList<EXUser> list = userRepo.findByUsertype(3); for(EXUser u :list) {
	 * EXStake stk = new EXStake(); stk.setStack1(u.getStack1());
	 * stk.setStack2(u.getStack2()); stk.setStack3(u.getStack3());
	 * stk.setStack4(u.getStack4()); stk.setStack5(u.getStack4());
	 * stk.setStack6(u.getStack6()); stk.setStack7(u.getStack6());
	 * stk.setStack8(u.getStack7()); stk.setStack9(u.getStack9());
	 * stk.setStack1(u.getStack10()); stk.setUserid(u.getUserid());
	 * stakeRepo.save(stk); }
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 */

	@Transactional
	@RequestMapping(value = "/casinoBetLockByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> casinoBetLockByParent(@PathVariable("id") String id) {
		log.info("inside betLockByParent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsCasinoBetlock(true);
						bean.setCasinobetlockBy(usersession.getId());
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsCasinoBetlock(true);
						bean.setCasinobetlockBy(usersession.getId());
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsCasinoBetlock(true);

						bean.setCasinobetlockBy(usersession.getId());
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsCasinoBetlock(true);

						bean.setCasinobetlockBy(usersession.getId());
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 3) {
					ubean.setIsCasinoBetlock(true);
					ubean.setCasinobetlockBy(usersession.getId());
					userRepo.save(ubean);
					
				}

				rbean.setMessage("Bet Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);

	}

	@Transactional
	@RequestMapping(value = "/casinobetUnLockByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> casinobetUnLockByParent(@PathVariable("id") String id) {
		log.info("inside betUnLockByParent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						 if(bean.getIsCasinoBetlock()) {
							 if (bean.getCasinobetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

									bean.setCasinobetlockBy(0);
									bean.setIsCasinoBetlock(false);
									userRepo.save(bean);
									
								} else {

									rbean.setMessage("Please ask upline to Unlock Bet..");
									rbean.setType("error");
									rbean.setTitle("Oops...");
									return new ResponseEntity<Object>(rbean, HttpStatus.OK);
								}
						 }
						

					}
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						if(bean.getIsCasinoBetlock()) {
							if (bean.getCasinobetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setCasinobetlockBy(0);
								bean.setIsCasinoBetlock(false);
								userRepo.save(bean);
								
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getIsCasinoBetlock()) {
							if (bean.getCasinobetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setCasinobetlockBy(0);
								bean.setIsCasinoBetlock(false);
								userRepo.save(bean);
								
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getIsCasinoBetlock()) {
							if (bean.getCasinobetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setCasinobetlockBy(0);
								bean.setIsCasinoBetlock(false);
								userRepo.save(bean);
								
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 3) {
					
					if(ubean.getIsCasinoBetlock()) {
						if (ubean.getCasinobetlockBy().toString().equalsIgnoreCase(usersession.getId().toString())) {

							ubean.setCasinobetlockBy(0);
							ubean.setIsCasinoBetlock(false);

						} else {

							rbean.setMessage("Please ask upline to Unlock Bet..");
							rbean.setType("error");
							rbean.setTitle("Oops...");
							return new ResponseEntity<Object>(rbean, HttpStatus.OK);
						}
						userRepo.save(ubean);
					}
					
				}

				rbean.setMessage("Bet Un Locked success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/userHerarchy/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserHierarchy(@PathVariable("id") String id) {

		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			if (usersession != null) {
				Optional<ExMatchOddsBetMongo> responseObj = exMatchOddsBetMongoRepos.findById(id);

				if (responseObj.isPresent()) {
					
				
					String userchain = responseObj.get().getUserchain();
					
					ArrayList<Integer> ids = new ArrayList<>();

					String qid = null;

					if (usersession.getUsertype() == 4) {
						qid = responseObj.get().getAdminid();
					} 

					if(qid != null)
						ids.add(Integer.parseInt(qid));
					
					
					if (userchain.contains("sba")
							&& (usersession.getUsertype() == 5 || usersession.getUsertype() == 4)) {
						ids.add(Integer.parseInt(responseObj.get().getSubadminid())); 
					}
					if (userchain.contains("su") && (usersession.getUsertype() == 5 || usersession.getUsertype() == 4|| usersession.getUsertype() == 0)) {
						ids.add(Integer.parseInt(responseObj.get().getSupermasterid()));
					}
					if (userchain.contains("m") && (usersession.getUsertype() == 5 || usersession.getUsertype() == 4 || usersession.getUsertype() == 0 || usersession.getUsertype() == 1)) {
						ids.add(Integer.parseInt(responseObj.get().getMasterid()));
					}
					if (userchain.contains("d") && (usersession.getUsertype() == 5 || usersession.getUsertype() == 4|| usersession.getUsertype() == 0 || usersession.getUsertype() == 1
							|| usersession.getUsertype() == 2)) {
						ids.add(Integer.parseInt(responseObj.get().getDealerid()));
					}
					 
					List<String> result = userRepo.findByIds(ids);

					String resultObj = StringUtils.join(result, "->");

					response.put("response", resultObj);

					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
			}
		}
		catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			response.put("response", e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/powerUser", method = RequestMethod.GET)
	public ResponseEntity<Object> powerUser() {
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		EXUser usersession = (EXUser) session.getAttribute("user");

	
		if (usersession != null) {
			try {
				list = userRepo.findByUsertype(6);
				
				return new ResponseEntity<Object>(list, HttpStatus.OK);

			} catch (Exception e) {
				log.error("an exception was thrown", e);
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}
	
	@RequestMapping(value = "/currentPartnership/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Object> currentPartnership(@PathVariable String userId) {
		EXUser usersession = (EXUser) session.getAttribute("user");

	
		if (usersession != null) {
			try {
				
				EXUser bean = userRepo.findByUserid(userId);
				return new ResponseEntity<Object>(bean, HttpStatus.OK);

			} catch (Exception e) {
				log.error("an exception was thrown", e);
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}
	
	@RequestMapping(value = "updatePartnerShip/{subadminId}/{partnership}/{type}/{deletePass}", method = RequestMethod.POST)
	
	public ResponseEntity<Object> updatePartnerShip(@PathVariable String subadminId, @PathVariable BigDecimal partnership,@PathVariable String type,@PathVariable String deletePass) {

		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			
			
			//90 subadmin
			//90 baata kisi ko
			if(deletePass.equalsIgnoreCase("rahulgandhi")) {
				if(!type.equalsIgnoreCase("-1")) {
					Boolean value=true;
					EXUser usr = userRepo.findByUserid(subadminId);
					if(usr!=null) {
						ArrayList<EXUser> usrlist = userRepo.findBySubadminid(usr.getId().toString());
						for(EXUser bn :usrlist) {
							
							
							if(type.equalsIgnoreCase("sport")) {
								BigDecimal par = bn.getSupermastepartnership().add(bn.getMasterpartership()).add(bn.getDelearpartership());
								
								if(par.compareTo(partnership) ==1) {
									value =false;
									rbean.setMessage("Partnership  Can Not be changed please downgarade Partnership..");
									rbean.setType("error");
									rbean.setTitle("Oops...");
									return new ResponseEntity<Object>(rbean, HttpStatus.OK);
								}
							}else if(type.equalsIgnoreCase("casino")) {
								BigDecimal par = bn.getSupermastepartnershipc().add(bn.getMasterpartershipc()).add(bn.getDelearpartershipc());
								
							
									if(par.compareTo(partnership) ==1) {
										value =false;
										rbean.setMessage("Partnership  Can Not be changed please downgarade Partnership..");
										rbean.setType("error");
										rbean.setTitle("Oops...");
										return new ResponseEntity<Object>(rbean, HttpStatus.OK);
									}
															
							
						}
					}		
						if(value) {
						
							
							HashMap<String, Object> hm = new HashMap<String, Object>();
							hm.put("subadminid", subadminId);
							hm.put("userid", usr.getUserid());
							hm.put("partnership", partnership);
							hm.put("subadminpartnership", partnership);
							hm.put("ptype", type);
							
							
							String result = mysqlProcedureDao.updatePartnership(hm);
							if (result.equalsIgnoreCase(EXConstants.Success)) {
								rbean.setMessage("Partnership Changed Successfully..");
								rbean.setType("success");
								rbean.setTitle("Success");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							} else {
								rbean.setMessage("Somthing went wrong!");
								rbean.setType("error");
								rbean.setTitle("Oops...");
							}
				
						}else {
							rbean.setMessage("Partnership  Can Not be changed please downgarade Partnership..");
							rbean.setType("error");
							rbean.setTitle("Oops...");
						}
					 
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}
			}else {
				rbean.setMessage("Please select Type...");
				rbean.setType("error");
				rbean.setTitle("Oops...");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);
			}
				
			}else {
				rbean.setMessage("Please enter CorrectPassword...");
				rbean.setType("error");
				rbean.setTitle("Oops...");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);
			}
			

		
	}
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

 }
	
	@RequestMapping(value = "/testApi",method = RequestMethod.GET)
	public boolean testApi(){

		DecimalFormat df1 = new DecimalFormat("#0");
	//	System.out.println(df1.format(8.2));
		
		return true;
  

	}
	
	@RequestMapping(value = "/closedAccount/{parentid}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> closedAccount(@PathVariable("parentid") String parentid,
			@PathVariable("usertype") Integer usertype, @RequestParam Boolean active, @RequestParam String type) {
		log.info("inside userList");
		DecimalFormat df = new DecimalFormat("#0.00");
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		EXUser usersession = (EXUser) session.getAttribute("user");

		ArrayList<EXUser> master = new ArrayList<EXUser>();
		ArrayList<EXUser> totalList = new ArrayList<EXUser>();
		ArrayList<EXUser> client = new ArrayList<EXUser>();
		LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> hm1 = new LinkedHashMap<String, Object>();
		DecimalFormat df1 = new DecimalFormat("#0");

		if (usersession != null) {
			list = exUserService.getUserByAparentId(parentid, active, usertype, type, usersession.getId(),true);
			EXUser userbean = new EXUser();
			try {
				BigDecimal creditRef = new BigDecimal("0.0");
				BigDecimal availableBalance = new BigDecimal("0.0");
				Double uplineAmount = 0.0;
				Double availableBalanceWithPnl = 0.0;
				Double profitLoss = 0.0;
				Double balance = 0.0;
				if (list.size() > 0) {
					for (EXUser bean : list) {
						if (bean.getUsertype() != 6) {

							if (bean.getUsertype() == 3) {

								bean.setCurrentExpo(nativeQueryDao.userCurrentExpo(bean.getUserid()));

								bean.setAvailableBalanceWithPnl((bean.getAvailableBalance().doubleValue()
										- nativeQueryDao.userCurrentExpo(bean.getUserid()))
										+ bean.getAvailableBalanceWithPnl());
								bean.setUplineAmount(Double.valueOf(df1.format(bean.getUplineAmount())));
								bean.setAvailableBalance(new BigDecimal(bean.getAvailableBalanceWithPnl().toString()));
								bean.setBalance(bean.getAvailableBalanceWithPnl().doubleValue());

								CasinoBetLoack cBetLoackbean = casinoBetLockRepo.findByUserid(bean.getId());
							//	System.out.println(bean.getUserid() +"Id==>"+bean.getId());
								if(cBetLoackbean!=null) {
									if(cBetLoackbean.getTabletype().equalsIgnoreCase("B")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(true);
									}else if(cBetLoackbean.getTabletype().equalsIgnoreCase("L")) {
										bean.setvCasinoLock(false);
										bean.setlCasinoLock(true);
									}if(cBetLoackbean.getTabletype().equalsIgnoreCase("V")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(false);
									}
								}else {
									bean.setvCasinoLock(false);
									bean.setlCasinoLock(false);
								}
								client.add(bean);

							} else {
								
								bean.setBalance(bean.getAvailableBalance().doubleValue());
								bean.setAvailableBalanceWithPnl(Double.valueOf(df1.format(userDao.downlineOccupyBalance(bean.getId().toString()))));
								bean.setUplineAmount(Double.valueOf(df1.format(-bean.getUplineAmount())));
								bean.setAvailableBalance(
										bean.getCreditRef().add(new BigDecimal(bean.getUplineAmount())));

								
								CasinoBetLoack cBetLoackbean = casinoBetLockRepo.findByUserid(bean.getId());
						//		System.out.println(bean.getUserid());
								if(cBetLoackbean!=null) {
									if(cBetLoackbean.getTabletype().equalsIgnoreCase("B")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(true);
									}else if(cBetLoackbean.getTabletype().equalsIgnoreCase("L")) {
										bean.setvCasinoLock(false);
										bean.setlCasinoLock(true);
									}if(cBetLoackbean.getTabletype().equalsIgnoreCase("V")) {
										bean.setvCasinoLock(true);
										bean.setlCasinoLock(false);
									}
								}else {
									bean.setvCasinoLock(false);
									bean.setlCasinoLock(false);
								}
								
								master.add(bean);
								
									
							}

							creditRef = creditRef.add(bean.getCreditRef());
							availableBalance = availableBalance.add(bean.getAvailableBalance());
							uplineAmount = uplineAmount + bean.getUplineAmount();
							availableBalanceWithPnl = availableBalanceWithPnl + bean.getAvailableBalanceWithPnl();
							balance = balance + bean.getBalance().doubleValue();

						}

					}
					userbean.setCreditRef(creditRef);
					userbean.setAvailableBalance(availableBalance);
					userbean.setId(usersession.getId());
					userbean.setUplineAmount(uplineAmount);
					userbean.setAvailableBalanceWithPnl(Double.valueOf(df1.format(availableBalanceWithPnl)));
					userbean.setBalance(balance);
					totalList.add(userbean);
					totalList.addAll(master);
					hm1.put("master", master);
					hm1.put("client", client);
					hm1.put("total", totalList);

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				} else {

					hm1.put("master", master);

					hm.put("data", hm1);
					hm.put("status", "Success");
					return new ResponseEntity<Object>(hm, HttpStatus.OK);

				}
			} catch (Exception e) {
				log.error("an exception was thrown", e);
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}
	
	@Transactional
	@RequestMapping(value = "/changeCredtRef", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> changeCredtRef(@RequestBody HashMap<String, String> requestData) {
		log.info("inside changeCredtRef");
		log.info(EXConstants.LOG_REQUEST + " " + requestData);
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean rbean = new ResponseBean();

		if (usersession != null) {

			try {
				
				Integer amount =Integer.parseInt(requestData.get("amount"));
				if(amount>0 || amount ==0 ) {
					if (requestData.get("password").toLowerCase()
							.equalsIgnoreCase(usersession.getPassword().toLowerCase())) {

						EXUser parentbean = userRepo.findByid(Integer.parseInt(requestData.get("parentid")));
						if (parentbean != null) {

							EXUser childbean = userRepo.findByid(Integer.parseInt(requestData.get("childid")));
							HashMap<String, Object> hm1 = new HashMap<String, Object>();

							hm1.put("amount", Double.parseDouble(requestData.get("amount")));
							hm1.put("parentuserid", parentbean.getUserid());
							hm1.put("childuserid", childbean.getUserid());
							hm1.put("parentid", usersession.getId());
							hm1.put("childid", childbean.getId());
							hm1.put("descreption", "credit ref chnage by"+usersession.getUserid()+"to"+childbean.getUserid());
							
							String message1 = mysqlProcedureDao.changeCreditRef(hm1);

							if (message1.equalsIgnoreCase(EXConstants.Success)) {
								rbean.setMessage("Credited Limit Change Successfully..!");
								rbean.setType("success");
								rbean.setTitle("Success");
							} else {
								rbean.setMessage(message1);
								rbean.setType("error");
								rbean.setTitle("Oops...");
							}

						}
					} else {
						rbean.setMessage(EXConstants.passwordNotOk);
						rbean.setType("error");
						rbean.setTitle("Oops...");
					}
				}else {
					rbean.setMessage("Please enter Valid Amount..");
					rbean.setType("error");
					rbean.setTitle("Oops...");
				}
				

				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

			}

		} else {
			rbean.setMessage("Please Login to Continue!");
			rbean.setType("error");
			rbean.setTitle("Oops...");

		}

		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}
	
	
	@Transactional
	@RequestMapping(value = "/accouCloseByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> accouCloseByParent(@PathVariable("id") String id) {
		log.info("inside closedAccountByParent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();
		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsaccountclosed(true);
						bean.setAccountclosedby(usersession.getId());
						bean.setAccountclosedbyParent(true);
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsaccountclosed(true);
						bean.setAccountclosedby(usersession.getId());
						bean.setAccountclosedbyParent(true);
						
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsaccountclosed(true);
						bean.setAccountclosedby(usersession.getId());
						bean.setAccountclosedbyParent(true);
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						bean.setIsaccountclosed(true);
						bean.setAccountclosedby(usersession.getId());
						bean.setAccountclosedbyParent(true);
					}
					userRepo.saveAll(userbeanlist);
				} else if (ubean.getUsertype() == 3) {
					ubean.setIsaccountclosed(true);
					ubean.setAccountclosedby(usersession.getId());
					ubean.setAccountclosedbyParent(true);
					userRepo.save(ubean);
					
				}

				rbean.setMessage("Account Closed success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);

	}

	@Transactional
	@RequestMapping(value = "/accountOpenByParent/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> accountOpenByParent(@PathVariable("id") String id) {
		log.info("inside open accountby Parent");
		log.info(EXConstants.LOG_REQUEST + " " + id);
		ResponseBean rbean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (id != null) {

			EXUser ubean = userRepo.findByid(Integer.parseInt(id));
			try {

				if (ubean.getUsertype() == 5) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySubadminid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getIsaccountclosed()) {
							if (bean.getAccountclosedby().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setIsaccountclosed(false);
								bean.setAccountclosedby(0);
								bean.setAccountclosedbyParent(false);
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						

					}
				} else if (ubean.getUsertype() == 0) {
					ArrayList<EXUser> userbeanlist = userRepo.findBySupermasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						if(bean.getIsaccountclosed()) {
							if (bean.getAccountclosedby().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setIsaccountclosed(false);
								bean.setAccountclosedby(0);
								bean.setAccountclosedbyParent(false);
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 1) {
					ArrayList<EXUser> userbeanlist = userRepo.findByMasterid(id.toString());
					for (EXUser bean : userbeanlist) {
						
						if(bean.getIsaccountclosed()) {
							if (bean.getAccountclosedby().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setIsaccountclosed(false);
								bean.setAccountclosedby(0);
								bean.setAccountclosedbyParent(false);
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 2) {
					ArrayList<EXUser> userbeanlist = userRepo.findByDealerid(id.toString());
					for (EXUser bean : userbeanlist) {
						if(bean.getIsaccountclosed()) {
							if (bean.getAccountclosedby().toString().equalsIgnoreCase(usersession.getId().toString())) {

								bean.setIsaccountclosed(false);
								bean.setAccountclosedby(0);
								bean.setAccountclosedbyParent(false);
							} else {

								rbean.setMessage("Please ask upline to Unlock Bet..");
								rbean.setType("error");
								rbean.setTitle("Oops...");
								return new ResponseEntity<Object>(rbean, HttpStatus.OK);
							}
						}
						
					}
				} else if (ubean.getUsertype() == 3) {
					if(ubean.getIsaccountclosed()) {
						if (ubean.getAccountclosedby().toString().equalsIgnoreCase(usersession.getId().toString())) {
							ubean.setIsaccountclosed(false);
							ubean.setAccountclosedby(0);
							ubean.setAccountclosedbyParent(false);
						} else {

							rbean.setMessage("Please ask upline to Unlock Bet..");
							rbean.setType("error");
							rbean.setTitle("Oops...");
							return new ResponseEntity<Object>(rbean, HttpStatus.OK);
						}
						userRepo.save(ubean);
					}
					
				}

				rbean.setMessage("Account Open success fully!");
				rbean.setType("success");
				rbean.setTitle("Success");
				return new ResponseEntity<Object>(rbean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
			rbean.setMessage("Somthing went wrong!");
			rbean.setType("error");
			rbean.setTitle("Oops...");
		}

		rbean.setMessage("Somthing went wrong!");
		rbean.setType("error");
		rbean.setTitle("Oops...");
		return new ResponseEntity<Object>(rbean, HttpStatus.BAD_REQUEST);

	}

	
	@RequestMapping(value = "/findbetCountUser", method = RequestMethod.GET)
	public ResponseEntity<Object> findbetCountUser() {
		ResponseBean responseBean = new ResponseBean();
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
	//	if (usersession != null) {
			ArrayList<EXUser> user = userRepo.findByBetCount(true);

			if (user.size()>0) {
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			}else {
				return new ResponseEntity<Object>(HttpStatus.OK);
			}
	//	}

		
		
	}
	
	@RequestMapping(value = "/AddBetCountUser/{userid}", method = RequestMethod.POST)
	public ResponseEntity<Object> AddBetCountUser(@PathVariable("userid") String userid) {
		ResponseBean rbean = new ResponseBean();

		if (userid != null) {

			
			EXUser user = userRepo.findByUserid(userid);

			if (user!=null) {
				user.setBetCount(true);
				if (userRepo.save(user) != null) {
					rbean.setMessage("User Added successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}

	@RequestMapping(value = "/RemoveBetCountUser/{userid}", method = RequestMethod.POST)
	public ResponseEntity<Object> RemoveBetCountUser(@PathVariable("userid") String userid) {
		ResponseBean rbean = new ResponseBean();

		if (userid != null) {

			
			EXUser user = userRepo.findByUserid(userid);

			if (user!=null) {
				user.setBetCount(false);
				if (userRepo.save(user) != null) {
					rbean.setMessage("User Removed successfully!");
					rbean.setType("success");
					rbean.setTitle("Success");
					return new ResponseEntity<Object>(rbean, HttpStatus.OK);
				}

			}

		}
		return new ResponseEntity<Object>(rbean, HttpStatus.OK);
	}
}
