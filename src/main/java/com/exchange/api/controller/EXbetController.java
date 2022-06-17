package com.exchange.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
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
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.AppCharge;
import com.exchange.api.bean.EXCommDetail;
import com.exchange.api.bean.EXLedger;
import com.exchange.api.bean.EXMatchAbndAndTie;
import com.exchange.api.bean.EXMatchViewer;
import com.exchange.api.bean.EXSportdetailResponse;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.ExCashTransation;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.ExMatchOddsBetMongo;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.Sport;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.bean.UserLiabilityMongo;
import com.exchange.api.bean.filterKeyBetList;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.EXUserDAO;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.dao.MarketDAO;
import com.exchange.api.repositiry.AppBeanRepo;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXLedgerRepository;
import com.exchange.api.repositiry.EXMatchAbndAndTieRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.ExMatchOddsBetMongoRepos;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.MinMaxBetRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.api.service.EXBetService;
import com.exchange.api.service.FancyService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.exchange.util.EXGlobalConstantProperties;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXbetController {

	private static Logger log = Logger.getLogger(EXbetController.class);

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
	MarketRepository eventMarketRepository;

	@Autowired
	SelectionIdRepository selectionIdRepository;

	ResponseBean responseBean;
	DateFormat formatter;
	Date date, startDate, endDate;
	String date1;

	@Autowired
	FancyRepository fancyRepository;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	FancyResultRepository fancyresultRepository;

	@Autowired
	private FancyService fancyService;

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
	MarketResultRepository matchResultrepo;

	@Autowired
	EXMatchAbndAndTieRepository matchabndtieRepo;
	EXDateUtil dtutil = new EXDateUtil();

	@Autowired
	EXUserDAO exuserDao;

	@Autowired
	EXBetService exBetService;

	@Autowired
	ExMatchOddsBetDao betlistDao;

	@Autowired
	EXNativeQueryDao nativeQueryDao;

	@Autowired
	MarketDAO marketDao;

	@Autowired
	EXLedgerRepository ledgerRepo;

	@Autowired
	SportRepository sportRepo;

	private Firestore fb;

	@Autowired
	ExMatchOddsBetMongoRepos exMatchOddsBetMongoRepos;

	@RequestMapping(value = "/placeBetChekMobAppChardeducuted", method = RequestMethod.POST, consumes = "application/json")
	public boolean placeBetChekMobAppChardeducuted(@Valid @RequestBody ExMatchOddsBet placebet)
			throws InterruptedException, ParseException {

		AppCharge newappcharge = appchargeRepo.findByEventidAndUserid(String.valueOf(placebet.getMatchid()),
				placebet.getUserid());

		if (newappcharge == null) {
			return false;
		}
		return true;

	}

	
	// API to get bets as per the filter
	@RequestMapping(value = "/getbets", method = RequestMethod.POST)
	public ResponseEntity<Object> getBets(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (usersession != null) {
			betsList.addAll(betlistDao.getBets(filterData));

			try {
				if (!betsList.isEmpty()) {
					for (int i = 0; i < betsList.size(); i++) {
						betsList.get(i).setDate(dtUtil.convTimeZone2(dateFormater.format(betsList.get(i).getCreatedon()), "GMT+5:30", "GMT+5:30"));
					}
					return new ResponseEntity<Object>(betsList, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {

			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@Transactional
	@RequestMapping(value = "/rollbackMatchBet", method = RequestMethod.POST)
	public ResponseEntity<?> rollbackMatchBet(@RequestParam Integer id) throws Exception {

		responseBean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			responseBean = exBetService.rollbackMatchBet(id);
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

		}

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getTotalLiabilities", method = RequestMethod.GET)
	public ResponseEntity<Object> getTotalLiabilities() {
		ArrayList<UserLiability> liability;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");

		if (usersession != null) {
			liability = liabilityRepository.findByUseridAndIsactive(usersession.getUserid(), true);
			Double totalLiability = 0.00;
			if (liability.size() > 0) {

				for (UserLiability lib : liability) {

					totalLiability = totalLiability + lib.getLiability();

				}
				return new ResponseEntity<Object>(df.format(totalLiability), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getMatchList", method = RequestMethod.GET)
	// public ResponseEntity<Object> getMatchList(@RequestParam(name =
	// "marketname",required = true) String marketname, @RequestParam(name =
	// "isactive",required = true) Boolean isactive,@RequestParam Integer appid){
	public ResponseEntity<Object> getMatchList(@RequestParam(name = "marketname", required = true) String marketname,
			@RequestParam(name = "isactive", required = true) Boolean isactive) {
		ArrayList<Market> list = null;
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {

			list = (ArrayList<Market>) eventMarketRepository.findByMarketnameAndIsactiveAndIsResult(marketname,
					isactive, false);
			EXDateUtil dtUtil = new EXDateUtil();
			try {

				if (list.size() > 0) {
					String marketid = "";
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setCreatedon(dtUtil.convTimeZone2(list.get(i).getCreatedon(), "GMT", "GMT+5:30"));

					}
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/getActiveMatchBetList", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveMatchBetList(
			@RequestParam(name = "marketid", required = true) String marketid,
			@RequestParam(name = "isactive", required = true) Boolean isactive) {
		HttpSession session = request.getSession();
		EXUser user = (EXUser) session.getAttribute("user");

		if (user != null) {
			ArrayList<ExMatchOddsBet> list = (ArrayList<ExMatchOddsBet>) placebetrepository.findByMarketidAndIsactiveOrderByIdDesc(marketid, isactive);
			try {
				if (list.size() > 0) {

					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}



	@RequestMapping(value = "/getMatchedBets", method = RequestMethod.GET)
	public ResponseEntity<Object> getMatchedBets(@RequestParam(name = "eventid", required = true) int eventid) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> bets = new ArrayList<ExMatchOddsBet>();
		if (usersession != null) {
			if (eventid == 0) {
				bets = placebetrepository.findByUseridAndIsactive(usersession.getUserid(), true);

			} else {
				bets = placebetrepository.findByUseridAndMatchidAndIsactive(usersession.getUserid(), eventid, true);

			}
			if (bets.size() > 0) {
				ArrayList<ExMatchOddsBet> matchedBets = new ArrayList<>();
				ArrayList<ExMatchOddsBet> fancyBets = new ArrayList<>();
				ArrayList<ExMatchOddsBet> unMatchedBets = new ArrayList<>();
				for (int i = 0; i < bets.size(); i++) {
					if (bets.get(i).getType().equals("Match Odds")) {
						unMatchedBets.add(bets.get(i));
					} else if (bets.get(i).getType().equals("Match Odds")) {
						matchedBets.add(bets.get(i));
					} else if (bets.get(i).getType().equals("Fancy")) {
						fancyBets.add(bets.get(i));
					}
				}

				LinkedHashMap<String, ArrayList<?>> map = new LinkedHashMap<>();
				map.put("matched", matchedBets);
				map.put("unMatched", unMatchedBets);
				map.put("fancy", fancyBets);

				return new ResponseEntity<Object>(map, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getDateWisebets", method = RequestMethod.POST)
	public ResponseEntity<Object> getDateWisebets(@RequestBody LinkedHashMap<String, String> filterData) {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();
		ArrayList<ExMatchOddsBet> betsList2 = new ArrayList<>();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#0.00");
		LinkedHashMap<Integer, String> hm1 = new LinkedHashMap<Integer, String>();
		LinkedHashMap<String, Integer> hm2 = new LinkedHashMap<String, Integer>();
		ArrayList<Integer> eventids = new ArrayList<Integer>();
		ArrayList<ExMatchOddsBet> betsList3 = new ArrayList<>();
		if (usersession != null) {
			betsList.addAll(betlistDao.getDateWiseBets(usersession.getUserid(), filterData));
			String duplicateDate = null;
			try {
				if (!betsList.isEmpty()) {

					for (ExMatchOddsBet bets : betsList) {
						/*
						 * if(bets.getType().equalsIgnoreCase("Fancy")){
						 * bets.setResultname(bets.getResult().toString()); }
						 */
					}

					return new ResponseEntity<Object>(betsList, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getAdminMatchedBets", method = RequestMethod.GET)
	public ResponseEntity<Object> getAdminMatchedBets(@RequestParam Integer eventid, @RequestParam Integer startpage,
			@RequestParam Integer endpage) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		ArrayList<ExMatchOddsBet> bets = null;
		ArrayList<ExMatchOddsBet> fancybets = null;
		ArrayList<ExMatchOddsBet> bookmakerbets = null;

		Long matchoddsbetcount = (long) 0;
		Long fancybetcount = (long) 0;
		Long bookmakerbetcount = (long) 0;
		if (usersession != null) {

			try {
				if (usersession.getUsertype().equals(4)) {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "adminid",
							true, eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getId().toString(), "adminid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "adminid", true,
							eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getId().toString(), "adminid", true, eventid,
							EXConstants.Fancy, startpage, endpage, false);

					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "adminid",
							true, eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getId().toString(), "adminid", true, eventid,
							EXConstants.Bookmaker, startpage, endpage, false);

				} else if (usersession.getUsertype().equals(6)) {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getParentid(), "adminid", true,
							eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getParentid(), "adminid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getParentid(), "adminid", true,
							eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getParentid(), "adminid", true, eventid,
							EXConstants.Fancy, startpage, endpage, false);

					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getParentid(), "adminid", true,
							eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getParentid(), "adminid", true, eventid,
							EXConstants.Bookmaker, startpage, endpage, false);

				} else if (usersession.getUsertype().equals(5)) {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(),
							"subadminid", true, eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getId().toString(), "subadminid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "subadminid",
							true, eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getId().toString(), "subadminid", true, eventid,
							EXConstants.Fancy, startpage, endpage, false);
					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(),
							"subadminid", true, eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getId().toString(), "subadminid", true,
							eventid, EXConstants.Bookmaker, startpage, endpage, false);

				} else if (usersession.getUsertype().equals(0)) {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(),
							"supermasterid", true, eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getId().toString(), "supermasterid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "supermasterid",
							true, eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getId().toString(), "supermasterid", true,
							eventid, EXConstants.Fancy, startpage, endpage, false);

					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(),
							"supermasterid", true, eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getId().toString(), "supermasterid", true,
							eventid, EXConstants.Bookmaker, startpage, endpage, false);

				} else if (usersession.getUsertype().equals(1)) {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "masterid",
							true, eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getId().toString(), "masterid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "masterid",
							true, eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getId().toString(), "masterid", true, eventid,
							EXConstants.Fancy, startpage, endpage, false);
					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "masterid",
							true, eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getId().toString(), "masterid", true, eventid,
							EXConstants.Bookmaker, startpage, endpage, false);

				} else {
					matchoddsbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "dealerid",
							true, eventid, EXConstants.MatchOdds);
					bets = betlistDao.getMatchedBets(usersession.getId().toString(), "dealerid", true, eventid,
							EXConstants.MatchOdds, startpage, endpage, false);
					fancybetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "dealerid",
							true, eventid, EXConstants.Fancy);
					fancybets = betlistDao.getMatchedBets(usersession.getId().toString(), "dealerid", true, eventid,
							EXConstants.Fancy, startpage, endpage, false);

					bookmakerbetcount = betlistDao.getMatchedBetsTotalCount(usersession.getId().toString(), "dealerid",
							true, eventid, EXConstants.Bookmaker);
					bookmakerbets = betlistDao.getMatchedBets(usersession.getId().toString(), "dealerid", true, eventid,
							EXConstants.Bookmaker, startpage, endpage, false);

				}

				ArrayList<ExMatchOddsBet> matchedBets = new ArrayList<>();
				ArrayList<ExMatchOddsBet> fancyBets = new ArrayList<>();

				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

				map.put("fancy", fancybets);
				map.put("fancycount", fancybetcount);

				map.put("matched", bets);
				map.put("matchedcount", matchoddsbetcount);

				map.put("bookmakerbets", bookmakerbets);
				map.put("bookmakerbetcount", bookmakerbetcount);

				return new ResponseEntity<Object>(map, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	// API to get bets as per the filter
	@RequestMapping(value = "/getdeletedbets", method = RequestMethod.POST)
	public ResponseEntity<Object> getdeletedbets(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();
		ArrayList<ExMatchOddsBet> deletedbetsList = new ArrayList<>();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (usersession != null) {
			betsList.addAll(betlistDao.getBets(filterData));

			try {
				if (!betsList.isEmpty()) {
					for (int i = 0; i < betsList.size(); i++) {
						// if(betsList.get(i).getPnlresult()==null ||
						// betsList.get(i).getPnlresult()==0.0) {
						betsList.get(i).setDate(dtUtil.convTimeZone2(
								dateFormater.format(betsList.get(i).getCreatedon()), "GMT+5:30", "GMT+5:30"));
						ExMatchOddsBet bets = new ExMatchOddsBet();
						bets.setId(betsList.get(i).getId());
						bets.setDate(betsList.get(i).getDate());
						bets.setCreatedon(betsList.get(i).getCreatedon());
						bets.setUserid(betsList.get(i).getUserid());
						bets.setMatchname(betsList.get(i).getMatchname());
						bets.setMarketname(betsList.get(i).getMarketname());
						bets.setSelectionname(betsList.get(i).getSelectionname());

						bets.setType(betsList.get(i).getType());
						bets.setPricevalue(betsList.get(i).getPricevalue());
						bets.setOdds(betsList.get(i).getOdds());
						bets.setStake(betsList.get(i).getStake());
						bets.setPnl(betsList.get(i).getPnl());
						bets.setIsback(betsList.get(i).getIsback());
						bets.setIslay(betsList.get(i).getIslay());
						bets.setSportid(betsList.get(i).getSportid());

						deletedbetsList.add(bets);
						// }

					}
					if (deletedbetsList.isEmpty()) {
						return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
					} else {
						return new ResponseEntity<Object>(deletedbetsList, HttpStatus.OK);
					}

				} else {
					return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @RequestMapping(value = "/getBetResults",method = RequestMethod.GET) public
	 * ResponseEntity<Object> getBetResults(@RequestParam Integer
	 * resultid,@RequestParam Integer page,@RequestParam Integer count) throws
	 * ParseException{ SimpleDateFormat dateFormater = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); SimpleDateFormat date = new
	 * SimpleDateFormat("MMM dd, yyyy hh:mm:ss a"); HttpSession session =
	 * request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user");
	 * 
	 * if(usersession!=null){
	 * 
	 * String usertype = ""; if(usersession.getUsertype()==4){ usertype="adminid"; }
	 * else if(usersession.getUsertype()==5){ usertype="subadminid"; } else
	 * if(usersession.getUsertype()==0){ usertype="supermasterid"; }else
	 * if(usersession.getUsertype()==1){ usertype="masterid"; }else{
	 * usertype="dealerid"; }
	 * 
	 * ArrayList<ExMatchOddsBet> resultList = new ArrayList<>();
	 * 
	 * resultList =betlistDao.getResultBets("", usertype,
	 * usersession.getId().toString(),page,count); EXDateUtil dtUtil = new
	 * EXDateUtil(); if(!resultList.isEmpty()){ for(int
	 * i=0;i<resultList.size();i++){ //
	 * //resultList.get(i).setDate(date.format(dateFormater.parse(dateFormater.
	 * format(resultList.get(i).getCreatedon())))); EXUser bean
	 * =userRepo.findByUserid(resultList.get(i).getUserid()); EXUser subadmin
	 * =userRepo.findByUserid(bean.getUserid()); if(bean!=null){
	 * //resultList.get(i).setCommssionshare(bean.getFancywin());
	 * if(bean.getCommisiontype().equalsIgnoreCase("commision")){
	 * resultList.get(i).setCommisiontype(bean.getCommision()); }else{
	 * resultList.get(i).setCommisiontype(String.valueOf(bean.getMobappcharge())); }
	 * if(bean!=null){ resultList.get(i).setCommssionshare(subadmin.getFancywin());
	 * }
	 * 
	 * resultList.get(i).setDate(dtUtil.convTimeZone2(dateFormater.format(resultList
	 * .get(i).getCreatedon()), "GMT+5:30", "GMT+5:30")); } } return new
	 * ResponseEntity<Object>(resultList,HttpStatus.OK); }else{ return new
	 * ResponseEntity<Object>(responseBean,HttpStatus.NO_CONTENT); }
	 * 
	 * } return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST); }
	 */
	/*
	 * @RequestMapping(value = "/suspendBets",method = RequestMethod.DELETE) public
	 * ResponseEntity<?> suspendBets(@RequestParam(name = "eventid",required = true)
	 * int eventid){
	 * 
	 * responseBean = new ResponseBean(); ExMatchOddsBet placeBet = new
	 * ExMatchOddsBet(); UserLiability liability = new UserLiability();
	 * DecimalFormat df = new DecimalFormat("#0.00"); HttpSession session =
	 * request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user"); String id =""; if(usersession.getUsertype() ==
	 * 4) { id = usersession.getId().toString(); }else { id =
	 * usersession.getAdminid(); } ArrayList<ExMatchOddsBet> bets =
	 * placebetrepository.findByMatchidAndIsactiveAndAdminid(eventid, true,id);
	 * if(usersession != null&& !bets.isEmpty()){ for(int b=0;b<bets.size();b++){
	 * placeBet = bets.get(b); EXUser user =
	 * userRepo.findByUserid(placeBet.getUserid()); Double balance =
	 * Double.valueOf(user.getSecondrybanlce().toString());
	 * 
	 * date = new Date();
	 * 
	 * placeBet.setIsactive(false);
	 * placeBet.setDeletedBy(usersession.getId().toString());
	 * placeBet.setUpdatedon(date);
	 * 
	 * Double price = placeBet.getPricevalue() - 1.00; Double stake =
	 * Double.valueOf(df.format(placeBet.getStake())); Double pnl =
	 * Double.valueOf(df.format(price*stake)); liability =
	 * liabilityRepository.findByMarketidAndUserid(placeBet.getMarketid(),placeBet.
	 * getUserid()); String[] temp = placeBet.getSelectionids().split(",");
	 * 
	 * if(placeBet.getIsback()==true){ if(temp.length==2){
	 * if(temp[0].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl1(liability.getPnl1() - pnl);
	 * liability.setPnl2(liability.getPnl2() + stake);
	 * 
	 * }else if(temp[1].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl2(liability.getPnl2() - pnl);
	 * liability.setPnl1(liability.getPnl1() + stake); } } else if(temp.length ==3){
	 * 
	 * if(temp[0].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl1(liability.getPnl1() - pnl);
	 * liability.setPnl2(liability.getPnl2() + stake);
	 * liability.setPnl3(liability.getPnl3() + stake);
	 * 
	 * }else if(temp[1].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl2(liability.getPnl2() - pnl);
	 * liability.setPnl1(liability.getPnl1() + stake);
	 * liability.setPnl3(liability.getPnl3() + stake);
	 * 
	 * }else if(temp[2].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl3(liability.getPnl3() - pnl);
	 * liability.setPnl2(liability.getPnl2() + stake);
	 * liability.setPnl1(liability.getPnl1() + stake); } } }else{
	 * 
	 * if(temp.length==2){
	 * if(temp[0].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl1(liability.getPnl1() + pnl);
	 * liability.setPnl2(liability.getPnl2() - stake);
	 * 
	 * }else if(temp[1].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl2(liability.getPnl2() + pnl);
	 * liability.setPnl1(liability.getPnl1() - stake); } }
	 * 
	 * else if(temp.length ==3){
	 * 
	 * if(temp[0].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl1(liability.getPnl1() + pnl);
	 * liability.setPnl2(liability.getPnl2() - stake);
	 * liability.setPnl3(liability.getPnl3() - stake);
	 * 
	 * }else if(temp[1].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl2(liability.getPnl2() + pnl);
	 * liability.setPnl1(liability.getPnl1() - stake);
	 * liability.setPnl3(liability.getPnl3() - stake);
	 * 
	 * }else if(temp[2].equals(String.valueOf(placeBet.getSelectionid()))){
	 * liability.setPnl3(liability.getPnl3() + pnl);
	 * liability.setPnl2(liability.getPnl2() - stake);
	 * liability.setPnl1(liability.getPnl1() - stake); } } }
	 * 
	 * liability.setLiability(liability.getPnl1()<liability.getPnl2()?
	 * (liability.getPnl1()<liability.getPnl3()?liability.getPnl1():liability.
	 * getPnl3()) :
	 * (liability.getPnl2()<liability.getPnl3()?liability.getPnl2():liability.
	 * getPnl3())); Double liabilityDiff;
	 * liability.setLiability(Double.valueOf(df.format(0.00-liability.getLiability()
	 * ))); liabilityDiff =
	 * liability.getLiability()-liabilityRepository.findByMarketidAndUserid(placeBet
	 * .getMarketid(),user.getUserid()).getLiability();
	 * 
	 * ArrayList<UserLiability> liabilities =
	 * liabilityRepository.findByUserid(user.getUserid()); Double totalLiability =
	 * 0.00; if(liabilities != null){ for(int i=0;i<liabilities.size();i++){
	 * totalLiability = totalLiability + liabilities.get(i).getLiability(); }
	 * totalLiability=totalLiability+liabilityDiff; }
	 * liability.setTotalpnl(liability.getPnl1()>liability.getPnl2()?
	 * (liability.getPnl1()>liability.getPnl3()?liability.getPnl1():liability.
	 * getPnl3()) :
	 * (liability.getPnl2()>liability.getPnl3()?liability.getPnl2():liability.
	 * getPnl3()));
	 * 
	 * liability.setPnl1(Double.valueOf(df.format(liability.getPnl1())));
	 * liability.setPnl2(Double.valueOf(df.format(liability.getPnl2())));
	 * liability.setPnl3(Double.valueOf(df.format(liability.getPnl3())));
	 * 
	 * if(user!=null){
	 * user.setSecondrybanlce(BigDecimal.valueOf(Double.valueOf(df.format(balance-
	 * liabilityDiff)))); usersession.setSecondrybanlce(user.getSecondrybanlce()); }
	 * placebetrepository.deleteById(placeBet.getId());
	 * liabilityRepository.save(liability); userRepo.save(user); if(placeBet!=null
	 * && liabilityRepository.findByid(liability.getId()).getLiability()
	 * !=liability.getLiability() &&
	 * userRepo.findByid(user.getId()).getSecondrybanlce() !=
	 * user.getSecondrybanlce()){
	 * 
	 * responseBean.setType("error");
	 * responseBean.setMessage("Something went wrong");
	 * responseBean.setTitle("Oops.."); return new
	 * ResponseEntity<ResponseBean>(responseBean,HttpStatus.BAD_REQUEST); } }
	 * responseBean.setType("success");
	 * responseBean.setMessage("Bets Deleted successfully");
	 * responseBean.setTitle("success"); return new
	 * ResponseEntity<ResponseBean>(responseBean,HttpStatus.OK);
	 * 
	 * } responseBean.setType("error");
	 * responseBean.setMessage("Something went wrong");
	 * responseBean.setTitle("Oops.."); return new
	 * ResponseEntity<ResponseBean>(responseBean,HttpStatus.BAD_REQUEST); }
	 * 
	 * 
	 */
	@RequestMapping(value = "/pauseplaymarket", method = RequestMethod.PUT)
	public ResponseEntity<Object> pauseplaymarket(@RequestParam(name = "marketid", required = true) String marketid) {
		responseBean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		try {
			Market bean = null;
			if (usersession != null) {
				bean = eventMarketRepository.findBymarketid(marketid);
				if (bean != null) {
					if (bean.getPauseByAdmin()) {
						bean.setPauseByAdmin(false);
						bean.setInPlay(true);
						bean.setIsautomatchActive(false);
					} else {
						bean.setPauseByAdmin(true);
						bean.setInPlay(false);
						bean.setIsautomatchActive(true);
					}
					if (bean.getIspause()) {
						bean.setIspause(false);
					} else {
						bean.setIspause(true);
					}
					eventMarketRepository.save(bean);
					responseBean.setType("success");
					responseBean.setMessage("Match pause/play updated Successfully.");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			responseBean.setType("error");
			responseBean.setMessage("Something went wrong");
			responseBean.setTitle("Oops..");
			return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/getIsPause", method = RequestMethod.GET)
	public ResponseEntity<Object> getIsPause(@RequestParam(name = "marketid", required = true) String marketid) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		responseBean = new ResponseBean();
		Market bean = eventMarketRepository.findBymarketid(marketid);
		try {

			if (bean != null) {

				return new ResponseEntity<Object>(bean, HttpStatus.OK);
			} else {

				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

	}

	public String setResult1(MarketResult result) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		DecimalFormat df = new DecimalFormat("#0.00");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		MarketResult resultbean = null;

		Market market = null;
		Event event = null;

		if (result != null) {
			resultbean = marketResultRepository.findByMarketid(result.getMarketid());
			event = seriesEventRepository.findByeventid(result.getMatchid());
			if (resultbean == null) {

				market = eventMarketRepository.findBymarketid(result.getMarketid());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result.setDate(new Date());
				result.setStatus(true);
				result.setIsResult(true);
				if (event != null) {
					event.setStatus(false);
				}
				result = marketResultRepository.save(result);

				market.setStatus(false);
				market.setIsactive(false);
				market.setIsResult(true);
				market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				eventMarketRepository.save(market);

				event = seriesEventRepository.save(event);

				return result.getId().toString();

			}
		}
		return "bad request";
	}

	public String makeMarkertTrue(MarketResult result) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		Market market = null;
		Event event = null;

		event = seriesEventRepository.findByeventid(result.getMatchid());

		market = eventMarketRepository.findBymarketid(result.getMarketid());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (event != null) {
			event.setStatus(true);
		}
		market.setStatus(true);
		market.setIsactive(true);
		market.setIsResult(false);
		market.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
		eventMarketRepository.save(market);

		event = seriesEventRepository.save(event);

		return "true";
	}

	public String setFancyResult(ExFancyResult result) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		DecimalFormat df = new DecimalFormat("#0.00");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ExFancyResult resultbean = null;

		MatchFancy fancy = null;

		if (result != null) {

			if (fancyresultRepository.existsByfancyid(result.getFancyid()) == false) {
				fancy = fancyRepository.findByFancyid(result.getFancyid());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				result = fancyresultRepository.save(result);
				fancy.setIsActive(false);
				fancyRepository.save(fancy);

				/*
				 * for(MatchFancy matchfancy : fancy){ matchfancy.setActive(0);
				 * fancyRepository.save(matchfancy); }
				 */

				return result.getId().toString();

			}
		}
		return "bad request";
	}

	public String makeFancyTrue(Integer id) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ExFancyResult result = fancyresultRepository.findByid(id);

		MatchFancy fancy = null;

		fancy = fancyRepository.findByFancyid(result.getFancyid());
		fancy.setIsActive(true);
		fancyRepository.save(fancy);
		return "true";
	}

	@RequestMapping(value = "/deleteMarketBetBySeries", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMarketBetBySeries(@RequestParam String marketid,@RequestParam Integer start,@RequestParam Integer end,@RequestParam String deletePass) {

		ResponseBean responseBean = new ResponseBean();
		DecimalFormat df = new DecimalFormat("#0.00");

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		RestTemplate restTemplate = new RestTemplate();
		try {
			if (usersession != null) {

			//	responseBean = exBetService.deleteMarketBetBySeries(marketid, start, end, usersession);
				String password = restTemplate.getForObject("https://aaaa-14afe-cc669-betfair.firebaseio.com/deleteBet.json", String.class);
				
				if(deletePass == null) {
					responseBean.setType("error");
					responseBean.setMessage("please EnterBet Delete Password..");
					responseBean.setTitle("Oops..");
				}else {
					if(deletePass.equalsIgnoreCase("rahulgandhi")) {
						responseBean = exBetService.deleteMarketBetBySeries(marketid, start, end, usersession);
					}
						
					
				/*	if(deletePass.equalsIgnoreCase(new JSONObject(password).getString("password"))) {
						responseBean = exBetService.deleteMarketBetBySeries(marketid, start, end, usersession);
					}else {
						
						responseBean.setType("error");
						responseBean.setMessage("Please Entener Correct Password");
						responseBean.setTitle("Oops..");
					}*/
				}
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

	}

	@RequestMapping(value = "/userProfitLoss", method = RequestMethod.POST)
	public ResponseEntity<Object> userProfitLoss(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();

		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#0.00");
		LinkedHashMap<Integer, String> hm1 = new LinkedHashMap<Integer, String>();
		LinkedHashMap<String, Integer> hm2 = new LinkedHashMap<String, Integer>();
		ArrayList<Integer> eventids = new ArrayList<Integer>();
		ArrayList<ExMatchOddsBet> betsList3 = new ArrayList<>();
		if (usersession != null) {
			betsList.addAll(betlistDao.getDateWiseBets(usersession.getUserid(), filterData));
			String duplicateDate = null;
			try {
				if (!betsList.isEmpty()) {
					for (int i = 0; i < betsList.size(); i++) {
						if (!eventids.contains(betsList.get(i).getMatchid())) {
							eventids.add(betsList.get(i).getMatchid());
						}
					}

					if (eventids.size() > 0) {

						for (int i = 0; i < eventids.size(); i++) {
							Double totalpnl = 0.0;
							ExMatchOddsBet bets = new ExMatchOddsBet();
							;
							filterData.put("eventid", eventids.get(i).toString());
							ArrayList<ExMatchOddsBet> betsList2 = new ArrayList<>();
							betsList2.addAll(betlistDao.getDateWiseBets(usersession.getUserid(), filterData));
							Integer k = 0;
							for (ExMatchOddsBet bet : betsList2) {
								totalpnl = totalpnl + bet.getNetpnl();
								if (k == 0) {
									bets.setMatchname(bet.getMatchname());
									bets.setMatchid(bet.getMatchid());
									k++;
								}
							}
							bets.setNetpnl(totalpnl);
							betsList3.add(bets);
						}

					}
					return new ResponseEntity<Object>(betsList3, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		responseBean.setMessage("Something went wrong");
		responseBean.setType("error");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/AbondedOrTieMatchList", method = RequestMethod.GET)
	public ResponseEntity<Object> AbondedOrTieMatchList() {

		ArrayList<EXMatchAbndAndTie> list = (ArrayList<EXMatchAbndAndTie>) matchabndtieRepo.findAll();
		if (list.size() > 0) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);

		}

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/sportdetailHomeScreen", method = RequestMethod.POST)
	public ResponseEntity<Object> sportdetailHomeScreen(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		DecimalFormat df = new DecimalFormat("#0");

		Double totalpnl =0.0;
		Double totalupline =0.0;
		Double totaldownline =0.0;
		Double commssiondiya =0.0;
		Double commssiondmila =0.0;
		
		if (usersession != null) {

			Date startdate = dtutil.convertToDate(filterData.get("startdate"), "yyyy-MM-dd HH:mm:ss");
		//	Date enddate = dtutil.add(dtutil.convertToDate(filterData.get("enddate"), "yyyy-MM-dd HH:mm:ss"), 0, 2, 0);
			Date enddate = dtutil.convertToDate(filterData.get("enddate"), "yyyy-MM-dd HH:mm:ss");
			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			Integer id = userRepo.findByUserid(userId).getId();
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.profitLoss(startdate, enddate, usersession.getUsertype(),userId, filterData);
			for (EXLedger led : ledgerlist) {

				led.setDownlineamount(nativeQueryDao.downLinePnlMatchWise(led.getMatchid(), id, "led.matchid"));
				totalpnl = totalpnl+ led.getPnlamount();
				totalupline = totalupline+led.getUplineamount();
				totaldownline =totaldownline + led.getDownlineamount();
				commssiondiya = commssiondiya+led.getCommssionDiya();
				commssiondmila = commssiondmila +led.getCommssionMila();

			}
			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("list", ledgerlist);
			hm.put("totalupline", totalupline);
			hm.put("totaldownline", totaldownline);
			hm.put("commssiondiya", commssiondiya);
			hm.put("commssiondmila", commssiondmila);
			hm.put("totalpnl", totalpnl);
			
			// hm.put("total", ledgertotaledgerwithoutdatelistlwithoutdate);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/matchUserWiseAmont", method = RequestMethod.POST)
	public ResponseEntity<Object> matchUserWiseAmont(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		DecimalFormat df = new DecimalFormat("#0");

	
		if (usersession != null) {

			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			Integer parenrId = userRepo.findByUserid(userId).getId();
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.matchUserWiseAmont(parenrId, Integer.parseInt(filterData.get("matchId")));
			

			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("list", ledgerlist);
			
			
			// hm.put("total", ledgertotaledgerwithoutdatelistlwithoutdate);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}
	
	@RequestMapping(value = "/matchUserWiseAmontCasino", method = RequestMethod.POST)
	public ResponseEntity<Object> matchUserWiseAmontCasino(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		DecimalFormat df = new DecimalFormat("#0");

	
		if (usersession != null) {
			
			
			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			Integer parenrId = userRepo.findByUserid(userId).getId();
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.matchUserWiseAmontCasino(parenrId, Integer.parseInt(filterData.get("matchId")),filterData.get("startdate"), filterData.get("enddate"));
			

			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("list", ledgerlist);
			
			
			// hm.put("total", ledgertotaledgerwithoutdatelistlwithoutdate);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}
	
	@RequestMapping(value = "/marketUserWiseAmont", method = RequestMethod.POST)
	public ResponseEntity<Object> marketUserWiseAmont(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		DecimalFormat df = new DecimalFormat("#0");

	
		if (usersession != null) {

			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			Integer parenrId = userRepo.findByUserid(userId).getId();
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.marketUserWiseAmont(parenrId, Integer.parseInt(filterData.get("matchId")),filterData.get("marketId"));
			

			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("list", ledgerlist);
			
			
			// hm.put("total", ledgertotaledgerwithoutdatelistlwithoutdate);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}
	
	@RequestMapping(value = "/profitLossCasino", method = RequestMethod.POST)
	public ResponseEntity<Object> profitLossCasino(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		/*usersession =new EXUser();
		usersession.setUserid("mango");
		usersession.setUsertype(4);*/
		DecimalFormat df = new DecimalFormat("#0");

		Double totalpnl =0.0;
		Double totalupline =0.0;
		Double totaldownline =0.0;
		if (usersession != null) {

			Date startdate = dtutil.convertToDate(filterData.get("startdate"), "yyyy-MM-dd HH:mm:ss");
			Date enddate = dtutil.convertToDate(filterData.get("enddate"), "yyyy-MM-dd HH:mm:ss");
			String userId = null;
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				userId = filterData.get("userId");
			} else {
				userId = usersession.getUserid();
			}
			Integer id = userRepo.findByUserid(userId).getId();
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.profitLossCasino(filterData.get("startdate"), filterData.get("enddate"), usersession.getUsertype(),userId, filterData);
			for (EXLedger led : ledgerlist) {

				led.setDownlineamount(nativeQueryDao.downLinePnlMatchWiseCasino(led.getMatchid(), id, "led.matchid", filterData));
				
				totalpnl = totalpnl+ led.getPnlamount();
				totalupline = totalupline+led.getUplineamount();
				totaldownline =totaldownline + led.getDownlineamount();
			}

			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("data", ledgerlist);
			hm.put("totalupline", totalupline);
			hm.put("totaldownline", totaldownline);
			hm.put("totalpnl", totalpnl);
			// hm.put("total", ledgertotaledgerwithoutdatelistlwithoutdate);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	
	
	@RequestMapping(value = "/profitLossDetail", method = RequestMethod.POST)
	public ResponseEntity<Object> profitLossDetail(@RequestBody LinkedHashMap<String, String> filterData)
			throws ParseException {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		DecimalFormat df = new DecimalFormat("#0");

		if (usersession != null) {

			
			String userId = null;
			Integer userType =0;
			Integer Id =0;
			
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				EXUser user = userRepo.findByUserid(filterData.get("userId"));
				
				userId = user.getUserid();
				userType = user.getUsertype();
				Id = user.getId();
				
			}else {
				userId =usersession.getUserid();
				userType = usersession.getUsertype();
				Id = usersession.getId();
			}
			
			
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.profitLossDetail(userType,
					userId, filterData);
			for (EXLedger led : ledgerlist) {

				led.setDownlineamount(nativeQueryDao.downLinePnlMatchWiseAndMarketId(led.getMatchid(),
						Id, led.getMarketid()));
			}

			HashMap<String, Object> hm = new HashMap<String, Object>();

			hm.put("list", ledgerlist);
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/commDetail", method = RequestMethod.POST)
	public ResponseEntity<Object> commDetail(@RequestBody LinkedHashMap<String, String> filterData) {
		
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");
		
		if(usersession!=null) {
			String userId = null;
			Integer userType =0;
			Integer Id =0;
			
			if (!filterData.get("userId").equalsIgnoreCase("-1")) {
				EXUser user = userRepo.findByUserid(filterData.get("userId"));
				
				userId = user.getUserid();
				userType = user.getUsertype();
				Id = user.getId();
				
			}else {
				userId =usersession.getUserid();
				userType = usersession.getUsertype();
				Id = usersession.getId();
			}
			
			
			ArrayList<UserLiability> libList = nativeQueryDao.userList(filterData, Id.toString(), userType);
			ArrayList<EXCommDetail> list =new  ArrayList<EXCommDetail>();
			
			 
			


			
			 for(UserLiability lib : libList) {
				
				 
				 EXCommDetail  commdetail = new EXCommDetail();
				 EXLedger ledger = nativeQueryDao.commSum(filterData, lib.getUserid());
				 commdetail.setUserId(lib.getUserid());
				 commdetail.setProfitLoss(ledger.getPnl());
				 commdetail.setStack(lib.getStack());
				
				 EXLedger ledger1 = ledgerRepo.findByUseridAndMarketidAndByUserId(userId, lib.getMarketid(), lib.getUserid());
				 Double  adminPartnershipstack =0.0;
				 Double  subadminPartnershipstack =0.0;
				 Double  supermasterPartnershipstack =0.0;
				 Double  masterpartnershipstack =0.0;
				 Double  dealerpartnershipstack =0.0;
				if(lib.getType().equalsIgnoreCase("Fancy")) {
					   adminPartnershipstack = Math.round((lib.getStack()*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
					   subadminPartnershipstack = Math.round((lib.getStack()*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
					   supermasterPartnershipstack = Math.round((lib.getStack()*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
					   masterpartnershipstack = Math.round((lib.getStack()*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
					   dealerpartnershipstack = Math.round((lib.getStack()*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;
					  
				 }else {
					 if(lib.getTotalpnl()<0.0) {
						  
						   adminPartnershipstack = Math.round((lib.getNetpnl()*(lib.getAdminpartnership()/100.0f))*100.0)/100.0;
						   subadminPartnershipstack = Math.round((lib.getNetpnl()*(lib.getSubadminpartnership()/100.0f))*100.0)/100.0;
						   supermasterPartnershipstack = Math.round((lib.getNetpnl()*(lib.getSupermasterpartnership()/100.0f))*100.0)/100.0;
						   masterpartnershipstack = Math.round((lib.getNetpnl()*(lib.getMasterpartnership()/100.0f))*100.0)/100.0;
						   dealerpartnershipstack = Math.round((lib.getNetpnl()*(lib.getDealerpartnership()/100.0f))*100.0)/100.0;
						 
					 }
				 }		
				 if(usersession.getUsertype()==4) {
						
					 
					 commdetail.setUplineParnership(0.0);
					 commdetail.setUplineCommssion(0.0);
					 commdetail.setOurParnetshipWithUpline(lib.getAdminpartnership());
					 commdetail.setTotalCommssion(ledger1.getCommssionDiya());
				     list.add(commdetail);
				 }else if(usersession.getUsertype()==5) {
					
					 commdetail.setUplineParnership(lib.getAdminpartnership());
					 commdetail.setUplineCommssion(ledger1.getCommssionMila());
					 commdetail.setOurParnetshipWithUpline(lib.getAdminpartnership()+lib.getSubadminpartnership());
					 commdetail.setTotalCommssion(ledger1.getCommssionDiya());
				     list.add(commdetail);
				 }else if(usersession.getUsertype()==0) {
					
					 commdetail.setUplineParnership(lib.getAdminpartnership()+lib.getSubadminpartnership());
					 commdetail.setUplineCommssion(ledger1.getCommssionMila());
					 commdetail.setOurParnetshipWithUpline(lib.getAdminpartnership()+lib.getSubadminpartnership()+lib.getSupermasterpartnership());
					 commdetail.setTotalCommssion(ledger1.getCommssionDiya());
				     list.add(commdetail);
				 }else if(usersession.getUsertype()==1) {
					
					 commdetail.setUplineParnership(lib.getAdminpartnership()+lib.getSubadminpartnership()+lib.getSupermasterpartnership());
					 commdetail.setUplineCommssion(ledger1.getCommssionMila());
					 commdetail.setOurParnetshipWithUpline(lib.getAdminpartnership()+lib.getSubadminpartnership()+lib.getSupermasterpartnership()+lib.getMasterpartnership());
					 commdetail.setTotalCommssion(ledger1.getCommssionDiya());
				     list.add(commdetail);
				 }else if(usersession.getUsertype()==2) {
					
					 commdetail.setUplineParnership(lib.getAdminpartnership()+lib.getSubadminpartnership()+lib.getSupermasterpartnership()+lib.getMasterpartnership());
					 commdetail.setUplineCommssion(ledger1.getCommssionMila());
					 commdetail.setOurParnetshipWithUpline(lib.getAdminpartnership()+lib.getSubadminpartnership()+lib.getSupermasterpartnership()+lib.getMasterpartnership()+lib.getDealerpartnership());
					 commdetail.setTotalCommssion(ledger1.getCommssionDiya());
				     list.add(commdetail);
				 }
			 } 
			 return new ResponseEntity<Object>(list,HttpStatus.OK);
		}
		
		
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			
	}

	@RequestMapping(value = "/userCashTransaction", method = RequestMethod.GET)
	public ResponseEntity<Object> userCashTransaction(@RequestParam String id, @RequestParam String downlinetype,
			@RequestParam Integer pagintype, Integer startpage) throws ParseException {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#");
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>();
		ArrayList<EXLedger> ledgerlisttotal = new ArrayList<EXLedger>();
		if (downlinetype.equalsIgnoreCase("Dn")) {
			if (usersession.getUsertype() == 3) {
				ledgerlist = nativeQueryDao.userCashTransaction(0, Integer.parseInt(id), pagintype, startpage);
				ledgerlisttotal = nativeQueryDao.userCashTransaction(0, Integer.parseInt(id), pagintype, -1);
			} else {
				ledgerlist = nativeQueryDao.userCashTransaction(usersession.getId(), Integer.parseInt(id), pagintype,
						startpage);
				ledgerlisttotal = nativeQueryDao.userCashTransaction(usersession.getId(), Integer.parseInt(id),
						pagintype, -1);
			}

		} else {
			ledgerlist = nativeQueryDao.userCashTransaction(Integer.parseInt(usersession.getParentid()),
					usersession.getId(), pagintype, startpage);
			ledgerlisttotal = nativeQueryDao.userCashTransaction(Integer.parseInt(usersession.getParentid()),
					usersession.getId(), pagintype, -1);
		}
		ArrayList<EXSportdetailResponse> list = new ArrayList<EXSportdetailResponse>();
		ArrayList<EXSportdetailResponse> list2 = new ArrayList<EXSportdetailResponse>();
		Double debit = 0.0;
		Double credit = 0.0;
		Double balance = 0.0;
		Double debittotal = 0.0;
		Double credittotal = 0.0;
		Double balancetotal = 0.0;
		Integer i = 0;
		Integer j = 0;
		for (EXLedger ledger : ledgerlist) {
			EXSportdetailResponse spdtl = new EXSportdetailResponse();
			if (usersession.getUsertype() == 3) {
				ledger.setAmount(-ledger.getAmount());
			}
			if (ledger.getAmount() > 0.0) {
				credit = credit + ledger.getAmount();
				balance = balance + ledger.getAmount();

				spdtl.setBalance(Double.valueOf(df.format(balance)));
				spdtl.setCredit(Double.valueOf(df.format(ledger.getAmount())));
				spdtl.setPlusminus("Agent Minus");

			} else {
				debit = debit - ledger.getAmount();
				spdtl.setDebit(Double.valueOf(df.format(-ledger.getAmount())));
				balance = balance + ledger.getAmount();
				spdtl.setBalance(Double.valueOf(df.format(balance)));
				spdtl.setPlusminus("Agent Plus");

			}
			spdtl.setMatchname(ledger.getMatchname());
			spdtl.setDate(new SimpleDateFormat("dd-MM-yyyy").format(ledger.getCreatedon()));
			spdtl.setRemarks(ledger.getType());
			spdtl.setCollectionname(ledger.getCollectionname());
			spdtl.setMatchid(ledger.getMatchid().toString());

			Market market = eventMarketRepository.findBymarketid(ledger.getMarketid());
			if (market != null) {
				Sport sport = sportRepo.findBySportId(market.getSportid());
				spdtl.setSportname(sport.getName());
			}

			list.add(spdtl);
			i++;
		}
		for (EXLedger ledger : ledgerlisttotal) {
			EXSportdetailResponse spdtl = new EXSportdetailResponse();
			if (usersession.getUsertype() == 3) {
				ledger.setAmount(-ledger.getAmount());
			}
			if (ledger.getAmount() > 0.0) {
				credittotal = credittotal + ledger.getAmount();
				balancetotal = balancetotal + ledger.getAmount();

				spdtl.setBalance(Double.valueOf(df.format(balancetotal)));
				spdtl.setCredit(Double.valueOf(df.format(ledger.getAmount())));
				spdtl.setPlusminus("Agent Minus");

			} else {
				debittotal = debittotal - ledger.getAmount();
				spdtl.setDebit(Double.valueOf(df.format(-ledger.getAmount())));
				balancetotal = balancetotal + ledger.getAmount();
				spdtl.setBalance(Double.valueOf(df.format(balancetotal)));
				spdtl.setPlusminus("Agent Plus");

			}

			if (j == ledgerlisttotal.size() - 1) {
				EXSportdetailResponse finalspdtl = new EXSportdetailResponse();
				finalspdtl.setCredit(Double.valueOf(df.format(credittotal)));
				finalspdtl.setDebit(Double.valueOf(df.format(debittotal)));
				finalspdtl.setBalance(Double.valueOf(df.format(spdtl.getBalance())));
				list2.add(finalspdtl);
			}
			j++;
		}

		LinkedHashMap<String, Object> hm1 = new LinkedHashMap<String, Object>();
		hm1.put("matchamount", list);
		hm1.put("finalamount", list2);

		return new ResponseEntity<Object>(hm1, HttpStatus.OK);

	}

	@RequestMapping(value = "/userLedger", method = RequestMethod.GET)
	public ResponseEntity<Object> userLedger(@RequestParam String parentid, @RequestParam String type)
			throws ParseException {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExCashTransation> tranlistdena = new ArrayList<ExCashTransation>();
		ArrayList<ExCashTransation> tranlistlena = new ArrayList<ExCashTransation>();
		HashMap<String, ArrayList<ExCashTransation>> hmtran = new HashMap<String, ArrayList<ExCashTransation>>();

		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>();
		DecimalFormat df = new DecimalFormat("#");

		if (type.equalsIgnoreCase("D")) {
			ledgerlist = nativeQueryDao.userLedger(Integer.parseInt(parentid), 0);

		} else {
			ledgerlist = nativeQueryDao.userLedger(Integer.parseInt(usersession.getParentid()), usersession.getId());
		}

		for (EXLedger ledger : ledgerlist) {
			ExCashTransation tran = new ExCashTransation();
			Double settmentAmount = nativeQueryDao.settlementSum(ledger.getUserid());
			if (ledger.getAmount() < 0) {

				tran.setOpenbal(Double.valueOf(df.format(-ledger.getAmount())) + settmentAmount);
				tran.setClosebal(Double.valueOf(df.format(-ledger.getAmount())) + settmentAmount);
				tran.setUsername(ledger.getUsername());
				tranlistdena.add(tran);

			} else {
				tran.setOpenbal(Double.valueOf(df.format(ledger.getAmount())) + settmentAmount);
				tran.setClosebal(Double.valueOf(df.format(ledger.getAmount())) + settmentAmount);
				tran.setUsername(ledger.getUsername());
				tranlistlena.add(tran);
			}

		}

		if (type.equalsIgnoreCase("D")) {
			hmtran.put("dena", tranlistdena);
			hmtran.put("lena", tranlistlena);
		} else {
			hmtran.put("lena", tranlistdena);
			hmtran.put("dena", tranlistlena);
		}

		return new ResponseEntity<Object>(hmtran, HttpStatus.OK);

	}

	public void cashtranentry(EXUser bean, EXUser usersession, String downlinetype) throws ParseException {
	}

	/*
	 * @RequestMapping(value = "/sportdetailHomeScreenNew",method =
	 * RequestMethod.GET) public ResponseEntity<Object> sportdetailHomeScreenNew()
	 * throws ParseException{
	 * 
	 * responseBean = new ResponseBean(); HttpSession session =
	 * request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user");
	 * 
	 * DecimalFormat df = new DecimalFormat("#0.00"); ArrayList<EXLedger> ledger =
	 * new ArrayList<EXLedger>(); ledger = (ArrayList<EXLedger>)
	 * ledgerRepo.findAll(); for(EXLedger led :ledger){
	 * if(led.getCollectionname()!=null){
	 * if(led.getCollectionname().equalsIgnoreCase("match")){
	 * nativeQueryDao.updateLedgerUpline(led);; } }
	 * 
	 * }
	 * 
	 * 
	 * return null; }
	 */
	@RequestMapping(value = "/sessionPlusMinus", method = RequestMethod.GET)
	public ResponseEntity<Object> sessionPlusMinus(@RequestParam Integer eventid) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		ArrayList<EXLedger> ledgerlist = nativeQueryDao.sessionPlusMinus(eventid, usersession.getId());
		EXLedger ledger = new EXLedger();
		LinkedHashMap<String, Object> hm1 = new LinkedHashMap<String, Object>();
		Double totalnetpnl = 0.0;
		for (EXLedger bean : ledgerlist) {
			totalnetpnl = totalnetpnl + bean.getPnlamount();
		}
		ledger.setPnlamount(totalnetpnl);
		hm1.put("sessionamount", ledgerlist);
		hm1.put("finalamount", ledger);
		return new ResponseEntity<Object>(hm1, HttpStatus.OK);

	}

	@RequestMapping(value = "/betByFancyId", method = RequestMethod.GET)
	public ResponseEntity<Object> betByFancyId(@RequestParam String fancyid) {
		// resultList =betlistDao.getResultBets("", usertype,
		// usersession.getId().toString(),page,count);

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");

		String usertype = "";
		if (usersession.getUsertype() == 4) {
			usertype = "adminid";
		} else if (usersession.getUsertype() == 5) {
			usertype = "subadminid";
		} else if (usersession.getUsertype() == 0) {
			usertype = "supermasterid";
		} else if (usersession.getUsertype() == 1) {
			usertype = "masterid";
		} else {
			usertype = "dealerid";
		}
		// ArrayList<ExMatchOddsBet> betlist
		// =placebetrepository.findByFancyidAndIsactiveAndIsdeleted(fancyid, false,
		// false);
		ArrayList<ExMatchOddsBet> betlist = betlistDao.getResultBets(fancyid, usertype, usersession.getId().toString(),
				0, 0);

		if (betlist.size() > 0) {
			return new ResponseEntity<Object>(betlist, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	

	@RequestMapping(value = "/getAdminViewMoreBets/{eventid}/{id}/{usertype}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAdminViewMoreBets(@PathVariable Integer eventid, @PathVariable String id,@PathVariable Integer usertype,@PathVariable String type) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
	
		ArrayList<ExMatchOddsBet> bets = null;
		
		Long matchoddsbetcount = (long) 0;
	

		if (usersession != null) {

			try {
				if (usertype ==4) {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets(id, "adminid", true, eventid,
								EXConstants.MatchOdds, 0, 0, false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets(id, "adminid", true, eventid,
								EXConstants.Bookmaker, 0, 0, false);

					} else {
						bets = betlistDao.getMatchedBets(id, "adminid", true, eventid,
								EXConstants.Fancy, 0, 0, false);

					}

				} else if (usertype ==6) {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets("1", "adminid", true, eventid, EXConstants.MatchOdds, 0, 0,
								false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets("1", "adminid", true, eventid, EXConstants.Bookmaker, 0, 0,
								false);

					} else {
						bets = betlistDao.getMatchedBets("1", "adminid", true, eventid, EXConstants.Fancy, 0, 0, false);

					}
				} else if (usertype ==5) {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets(id, "subadminid", true, eventid,
								EXConstants.MatchOdds, 0, 0, false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets(id, "subadminid", true, eventid,
								EXConstants.Bookmaker, 0, 0, false);

					} else {
						bets = betlistDao.getMatchedBets(id, "subadminid", true, eventid,
								EXConstants.Fancy, 0, 0, false);

					}

				} else if (usertype ==0) {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets(id, "supermasterid", true, eventid,
								EXConstants.MatchOdds, 0, 0, false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets(id, "supermasterid", true, eventid,
								EXConstants.Bookmaker, 0, 0, false);

					} else {
						bets = betlistDao.getMatchedBets(id, "supermasterid", true, eventid,
								EXConstants.Fancy, 0, 0, false);

					}
				} else if (usertype ==1) {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets(id, "masterid", true, eventid,
								EXConstants.MatchOdds, 0, 0, false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets(id, "masterid", true, eventid,
								EXConstants.Bookmaker, 0, 0, false);

					} else {
						bets = betlistDao.getMatchedBets(id, "masterid", true, eventid,
								EXConstants.Fancy, 0, 0, false);

					}

				} else {

					if (type.equalsIgnoreCase(EXConstants.MatchOdds)) {
						bets = betlistDao.getMatchedBets(id, "dealerid", true, eventid,
								EXConstants.MatchOdds, 0, 0, false);

					} else if (type.equalsIgnoreCase(EXConstants.Bookmaker)) {
						bets = betlistDao.getMatchedBets(id, "dealerid", true, eventid,
								EXConstants.Bookmaker, 0, 0, false);

					} else {
						bets = betlistDao.getMatchedBets(id, "dealerid", true, eventid,
								EXConstants.Fancy, 0, 0, false);

					}
				}

				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

				map.put("matched", bets);
				map.put("matchedcount", matchoddsbetcount);

				return new ResponseEntity<Object>(map, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(HttpStatus.OK);

	}

	@RequestMapping(value = "/betlist", method = RequestMethod.GET)
	public boolean betlist() throws InterruptedException, ParseException {

		ArrayList<UserLiability> lib = liabilityRepository.findBySupermasteridAndTypeAndMatchid("148", "Fancy",
				30031644);
		for (UserLiability s : lib) {
			ArrayList<ExMatchOddsBet> bet = placebetrepository.findByMatchidAndUseridAndMarketname(30031644,
					s.getUserid(), "Fancy");
			for (ExMatchOddsBet bt : bet) {
				bt.setSupermasterid(s.getSupermasterid());
				placebetrepository.save(bt);
			}
		}

		return false;

	}

	@RequestMapping(value = "/betHistoryBets", method = RequestMethod.POST)
	public ResponseEntity<Object> betHistoryBets(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> betlist = new ArrayList<ExMatchOddsBet>();
		ArrayList<ExMatchOddsBetMongo> betList= new ArrayList<ExMatchOddsBetMongo>();
		
		if (usersession != null) {

			try {

				String parentId = null;;
				String serchBy = null;

				String userId = null;
				if (!filterData.get("userId").equalsIgnoreCase("-1")) {
					userId = filterData.get("userId");
					EXUser user = userRepo.findByUserid(userId);
					
					if (user.getUsertype() == 4) {
						parentId = user.getId().toString();
						serchBy = "adminid";
					} else if (user.getUsertype() == 5) {
						parentId = user.getId().toString();
						serchBy = "subadminid";
					} else if (user.getUsertype() == 0) {
						parentId =user.getId().toString();
						serchBy = "supermasterid";
					} else if (user.getUsertype() == 1) {
						parentId = user.getId().toString();
						serchBy = "masterid";
					} else if (user.getUsertype() == 2) { 
						parentId = user.getId().toString();
						serchBy = "dealerid";
					}else {
						parentId = user.getUserid();
						serchBy = "userid";
					}
					
				} else {
					if (usersession.getUsertype() == 4) {
						parentId = usersession.getId().toString();
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 6) {
						parentId = usersession.getParentid();
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 5) {
						parentId = usersession.getId().toString();
						serchBy = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						serchBy = "supermasterid";
					} else if (usersession.getUsertype() == 1) {
						parentId = usersession.getId().toString();
						serchBy = "masterid";
					} else {
						parentId = usersession.getId().toString();
						serchBy = "dealerid";
					}
				
				}
				
				betList = exMatchOddsBetMongoRepos.findBetList(Integer.parseInt(filterData.get("matchid")), Integer.parseInt(filterData.get("sportid")),false,serchBy,parentId,filterData.get("childid"),filterData.get("marketid"),false,5000);
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betList, HttpStatus.OK);
	}

	@RequestMapping(value = "/currentBetsNew", method = RequestMethod.POST)
	public ResponseEntity<Object> currentBetsNew(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> betlist = new ArrayList<ExMatchOddsBet>();

		ArrayList<ExMatchOddsBetMongo> betList= new ArrayList<ExMatchOddsBetMongo>();
		
		if (usersession != null) {

			try {

				Integer parentId = 0;
				String serchBy = null;


				if (usersession.getUsertype() == 4) {
					parentId = usersession.getId();
					serchBy = "adminid";
					
				} else if (usersession.getUsertype() == 6) {
					parentId = Integer.parseInt(usersession.getParentid());
					serchBy = "adminid";
				} else if (usersession.getUsertype() == 5) {
					parentId = usersession.getId();
					serchBy = "subadminid";
				} else if (usersession.getUsertype() == 0) {
					parentId = usersession.getId();
					serchBy = "supermasterid";
				} else if (usersession.getUsertype() == 1) {
					parentId = usersession.getId();
					serchBy = "masterid";
				} else {
					parentId = usersession.getId();
					serchBy = "dealerid";
				}
				ExMatchOddsBet betbean = null;
	
				 
				 betList = exMatchOddsBetMongoRepos.findBetList(Integer.parseInt(filterData.get("matchid")), Integer.parseInt(filterData.get("sportid")),true,serchBy,parentId.toString(),filterData.get("childid"),filterData.get("marketid"),false,10000);
					
	
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betList, HttpStatus.OK);
	}

	@RequestMapping(value = "/betHistory", method = RequestMethod.POST)
	public ResponseEntity<Object> betHistory(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();

		ArrayList<ExMatchOddsBetMongo> betList= new ArrayList<ExMatchOddsBetMongo>();
		
		
		if (usersession != null) {

			try {
			
				Integer parentId = 0;
				String serchBy = null;

				String userId = null;
				if (!filterData.get("userId").equalsIgnoreCase("-1")) {
					userId = filterData.get("userId");
					EXUser user = userRepo.findByUserid(userId);
					
					if (user.getUsertype() == 4) {
						parentId = user.getId();
						serchBy = "adminid";
					} else if (user.getUsertype() == 6) {
						parentId = Integer.parseInt(user.getParentid());
						serchBy = "adminid";
					} else if (user.getUsertype() == 5) {
						parentId = user.getId();
						serchBy = "subadminid";
					} else if (user.getUsertype() == 0) {
						parentId =user.getId();
						serchBy = "supermasterid";
					} else if (user.getUsertype() == 1) {
						parentId = user.getId();
						serchBy = "masterid";
					}else if (user.getUsertype() == 2) {
						parentId = user.getId();
						serchBy = "dealerid";
					} else {
						parentId = Integer.parseInt(user.getParentid());
						serchBy = "userid";
					}
					
				} else {
					if (usersession.getUsertype() == 4) {
						parentId = usersession.getId();
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 6) {
						parentId = Integer.parseInt(usersession.getParentid());
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 5) {
						parentId = usersession.getId();
						serchBy = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						serchBy = "supermasterid";
					} else if (usersession.getUsertype() == 1) {
						parentId = usersession.getId();
						serchBy = "masterid";
					} else {
						parentId = usersession.getId();
						serchBy = "dealerid";
					}
				
				}
		
				
				betList = exMatchOddsBetMongoRepos.findBetListByParentIdUserId(Integer.parseInt(filterData.get("matchId")), -1,false,serchBy,parentId.toString(),"0",filterData.get("marketId"),false,5000);
				
				
				
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betList, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletedBets", method = RequestMethod.POST)
	public ResponseEntity<Object> deletedBets(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
	//	ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();
		ArrayList<ExMatchOddsBetMongo> betList= new ArrayList<ExMatchOddsBetMongo>();
		
		
		if (usersession != null) {

			try {
				// betsList.addAll(betlistDao.betHistory(filterData.get("marketId"),
				// Integer.parseInt(filterData.get("matchId")), false, filterData.get("userId"),
				// false,usersession.getUsertype()));

				Integer parentId = 0;
				String serchBy = null;

		/*		ClassLoader classLoader = getClass().getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
				FirebaseOptions firebaseOptions;

				firebaseOptions = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(inputStream))
						.setDatabaseUrl(EXConstants.firestoreDb).build();
				if (FirebaseApp.getApps().isEmpty())
					FirebaseApp.initializeApp(firebaseOptions);
				fb = com.google.firebase.cloud.FirestoreClient.getFirestore();
*/
				if (usersession.getUsertype() == 4) {
					parentId = usersession.getId();
					serchBy = "adminid";
				} else if (usersession.getUsertype() == 6) {
					parentId = Integer.parseInt(usersession.getParentid());
					serchBy = "adminid";
				} else if (usersession.getUsertype() == 5) {
					parentId = usersession.getId();
					serchBy = "subadminid";
				} else if (usersession.getUsertype() == 0) {
					serchBy = "supermasterid";
				} else if (usersession.getUsertype() == 1) {
					parentId = usersession.getId();
					serchBy = "masterid";
				} else {
					parentId = usersession.getId();
					serchBy = "dealerid";
				}
				ExMatchOddsBet betbean = null;
		/*		ApiFuture<QuerySnapshot> future = null;
				if (filterData.get("marketId").equalsIgnoreCase("-1")) {
					future = fb.collection("t_betlist").whereEqualTo(serchBy, parentId.toString())
							.whereEqualTo("isdeleted", true).get();

				} else {
					future = fb.collection("t_betlist").whereEqualTo(serchBy, parentId.toString())
							.whereEqualTo("matchid", Integer.parseInt(filterData.get("matchId")))
							.whereEqualTo("marketid", filterData.get("marketId")).whereEqualTo("isdeleted", true).get();

				}
*/
				betList = exMatchOddsBetMongoRepos.findBetList(Integer.parseInt(filterData.get("matchId")), -1,false,serchBy,parentId.toString(),"0",filterData.get("marketId"),true,5000);
				
	/*			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
				Integer i = 1;
				for (QueryDocumentSnapshot document : documents) {
					betbean = new ExMatchOddsBet();
					String userchain = null;

					betbean.setUserid(document.getString("userid"));
					betbean.setMatchname(document.getString("matchname"));
					betbean.setSelectionname(document.getString("selectionname"));
					betbean.setMarketname(document.getString("marketname"));
					betbean.setOdds(document.getDouble("odds"));
					betbean.setStake(Integer.parseInt(document.get("stake").toString()));
					betbean.setNetpnl(document.getDouble("netpnl"));
					betbean.setMatchedtime(document.getString("matchedtime"));
					betbean.setUserchain(userchain);
					betbean.setPricevalue(document.getDouble("pricevalue"));
					betbean.setIsback(document.getBoolean("isback"));
					betbean.setId(Integer.parseInt(document.get("id").toString()));
					betbean.setSerialno(i);

					betsList.add(betbean);
					i++;
				}
*/
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betList, HttpStatus.OK);
	}

	// New API to get the betHistory details
	@RequestMapping(value = "/betHistoryDetails", method = RequestMethod.POST)
	public ResponseEntity<Object> betHistoryDetails(@RequestBody filterKeyBetList filterData) throws ParseException {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		

		List<ExMatchOddsBetMongo> betsList = new ArrayList<>();

		

		if (usersession != null) {
			try {
				if (filterData.getMatchid() != null && filterData.getMatchid() > 0 && !filterData.getIsactive()
						) {
					String serchBy = null;
					
						if (usersession.getUsertype() == 4) {
							serchBy = "adminid";
						} else if (usersession.getUsertype() == 6) {
							serchBy = "adminid";
						} else if (usersession.getUsertype() == 5) {
							serchBy = "subadminid";
						} else if (usersession.getUsertype() == 0) {
							serchBy = "supermasterid";
						} else if (usersession.getUsertype() == 1) {
							serchBy = "masterid";
						} else if (usersession.getUsertype() == 2) {
							serchBy = "dealerid";
						} 
						
					log.info("key: " + serchBy);
					betsList = exMatchOddsBetMongoRepos.findBetListByProperties(filterData.getMatchid(),
							filterData.getIsactive(), serchBy, filterData.getId(), filterData.getDate(), usersession.getUserid());
					
					
					log.info("betsList: " + betsList);
				} else {
					log.info("validation failed");
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return new ResponseEntity<Object>(betsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/betHistoryDetailsWithStatusTrue", method = RequestMethod.POST)
	public ResponseEntity<Object> betHistoryDetailsWithStatusTrue(@RequestBody filterKeyBetList filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		/*
		 * EXUser usersession = new EXUser(); usersession.setId(1);
		 * usersession.setUsertype(4);
		 */
		List<ExMatchOddsBetMongo> betsList = new ArrayList<>();

		if (usersession != null) {
			try {
				if (filterData.getMatchid() != null && filterData.getMatchid() > 0 && filterData.getIsactive() 
						&& filterData.getUserid() != null) {
					log.info("validation success");
					String serchBy = null;
					if (usersession.getUsertype() == 4) {
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 6) {
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 5) {
						serchBy = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						serchBy = "supermasterid";
					} else if (usersession.getUsertype() == 1) {
						serchBy = "masterid";
					} else if (usersession.getUsertype() == 2) {
						serchBy = "dealerid";
					}  
					
					String userId = filterData.getUserid();
					log.info("key: " + serchBy);
					betsList = exMatchOddsBetMongoRepos.findBetListByProperties(filterData.getMatchid(),
							filterData.getIsactive(), serchBy, filterData.getId(), userId);
					log.info("betsList: " + betsList);
				} else {
					log.info("validation failed");
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return new ResponseEntity<Object>(betsList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAdminMatchedBetsDetails", method = RequestMethod.GET)
	public ResponseEntity<Object> getAdminMatchedBetsDetails(@RequestParam Integer matchid, @RequestParam String type,
			@RequestParam String id, @RequestParam boolean isactive) {

		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");

		

		LinkedHashMap<String, Object> resultObj = new LinkedHashMap<String, Object>();

		if (usersession != null) {
			try {
				if (matchid != null && matchid > 0 && isactive && type != null && id != null) {
					String serchBy = null;

					if (usersession.getUsertype() == 4) {
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 5) {
						serchBy = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						serchBy = "supermasterid";
					} else if (usersession.getUsertype() == 1) {
						serchBy = "masterid";
					} else if (usersession.getUsertype() == 2) {
						serchBy = "dealerid";
					} else if (usersession.getUsertype() == 6) {
						serchBy = "adminid";
					}

					resultObj = exMatchOddsBetMongoRepos.findBetListDetailsByProperties(matchid, isactive, serchBy, id,type);
					
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return new ResponseEntity<Object>(resultObj, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/currentBetsNetExpo/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Object> currentBetsNetExpo(@PathVariable String userId) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> betlist = new ArrayList<ExMatchOddsBet>();

		if (usersession != null) {

			try {

				
				
                 betlist = betlistDao.currentBets(false, userId, true, usersession.getUsertype(), null);
                
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betlist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getBetsPush",method = RequestMethod.GET)
	public ResponseEntity<Object> getBetsPush() throws IOException{
		
		//System.out.println(bets.getMatchid());
	     ArrayList<ExMatchOddsBetMongo> bets1 =	exMatchOddsBetMongoRepos.findByMatchidAndAdminid(2102101733, "1");
		
		Integer i=0;
		for( ExMatchOddsBetMongo data : bets1) {
			
		/*	ExMatchOddsBet obj = 	placebetrepository.findByUseridAndMarketidAndSeries(data.getUserid(),data.getMarketid(),data.getSeries());
			
			if(obj == null) {
				
				ExMatchOddsBet userLiabilitySqlObj = new ExMatchOddsBet();
				BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
				try {
					BeanUtils.copyProperties(userLiabilitySqlObj,data);
				} catch (IllegalAccessException e) {
					log.info("Exception Occured: " + e.getMessage());
				} catch (InvocationTargetException e) {
					log.info("Exception Occured: " + e.getMessage());
				}
				
				userLiabilitySqlObj.setAddetoFirestore(true);
				placebetrepository.save(userLiabilitySqlObj);
				
				System.out.println(i++); 
				
			}
			*/
			data.setIsactive(false);
			exMatchOddsBetMongoRepos.save(data);
			
		}
		
		
	
/*	ClassLoader classLoader = getClass().getClassLoader(); 
	InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
	FirebaseOptions firebaseOptions;

	firebaseOptions = new FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.fromStream(inputStream))
			.setDatabaseUrl(EXConstants.firestoreDb)
			.build();
	if(FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(firebaseOptions);
		 fb  = com.google.firebase.cloud.FirestoreClient.getFirestore();
		
		 ApiFuture<WriteResult> docRef = fb.collection("t_betlist").document("2102101733").delete();
		 
	*/	
		
		return null;
		
	}
	
	@RequestMapping(value = "/getLibPush",method = RequestMethod.POST)
	public ResponseEntity<Object> getLibPush( @RequestBody ArrayList<UserLiability> bets){
		
		//System.out.println(bets.getMatchid());
		
		ArrayList<ExMatchOddsBetMongo> bets1 =	exMatchOddsBetMongoRepos.findByMatchidAndIsactiveAndAdminid(2102101733, true, "1");
		
		
		for( UserLiability data : bets) {
			UserLiability lib  =liabilityRepository.findByMarketidAndUserid(data.getMarketid(), data.getUserid());
			if(lib == null) {
				
				liabilityRepository.save(data);
			}
			
			System.out.println(data.getId());
		}
		
		
		
		return null;
		
	}
	
	
	
	
	@RequestMapping(value = "/getMatchUserCount/{matchid}",method = RequestMethod.GET)
	public ResponseEntity<Object> getMatchUserCount(@PathVariable Integer matchid){
		
		//System.out.println(bets.getMatchid());
		
		 ArrayList<EXMatchViewer> list = exMatchOddsBetMongoRepos.getMatchUserCount(matchid);
		 HashMap<String,Object> hm = new  HashMap<String,Object>();
		 Integer totalUser = 0;
		 if(list .size()>0) {
			 for(EXMatchViewer bean : list) {
				 
				 totalUser  = totalUser+ Integer.parseInt(bean.getAppName());
				 
			 }
		 }
		 
		
		 
	
		exMatchOddsBetMongoRepos.getMatchBetUserCount(matchid);
		
		 hm.put("totalUser", totalUser);
		 hm.put("list", list);
		 hm.put("betUserCount", exMatchOddsBetMongoRepos.getMatchBetUserCount(matchid));
		 return new ResponseEntity<Object>(hm, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/betHistoryAccStemement", method = RequestMethod.POST)
	public ResponseEntity<Object> betHistoryAccStemement(@RequestBody LinkedHashMap<String, String> filterData) {
		responseBean = new ResponseBean();
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMatchOddsBet> betsList = new ArrayList<>();

		ArrayList<ExMatchOddsBetMongo> betList= new ArrayList<ExMatchOddsBetMongo>();
		
		
		if (usersession != null) {

			try {
			
				String parentId = null;
				String serchBy = null;

				String userId = null;
				if (!filterData.get("userId").equalsIgnoreCase("-1")) {
					userId = filterData.get("userId");
					EXUser user = userRepo.findByUserid(userId);
					
					if (user.getUsertype() == 4) {
						parentId = user.getId().toString();
						serchBy = "adminid";
					} else if (user.getUsertype() == 6) {
						parentId = usersession.getParentid();
						serchBy = "adminid";
					} else if (user.getUsertype() == 5) {
						parentId = user.getId().toString();
						serchBy = "subadminid";
					} else if (user.getUsertype() == 0) {
						parentId =user.getId().toString();
						serchBy = "supermasterid";
					} else if (user.getUsertype() == 1) {
						parentId = user.getId().toString();
						serchBy = "masterid";
					}else if (user.getUsertype() == 2) {
						parentId = user.getId().toString();
						serchBy = "dealerid";
					} else {
						parentId = user.getUserid();
						serchBy = "userid";
					}
					
				} else {
					if (usersession.getUsertype() == 4) {
						parentId = usersession.getId().toString();
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 6) {
						parentId =usersession.getParentid();
						serchBy = "adminid";
					} else if (usersession.getUsertype() == 5) {
						parentId = usersession.getId().toString();
						serchBy = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						serchBy = "supermasterid";
					} else if (usersession.getUsertype() == 1) {
						parentId = usersession.getId().toString();
						serchBy = "masterid";
					} else {
						parentId =usersession.getUserid();
						serchBy = "userid";
					}
				
				}
		
				
				betList = exMatchOddsBetMongoRepos.findBetListByParentIdUserId(Integer.parseInt(filterData.get("matchId")), -1,false,serchBy,parentId.toString(),"0",filterData.get("marketId"),false,5000);
				
				
				
			

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(betList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profitLossCasinoOk", method = RequestMethod.GET)
	public ResponseEntity<Object> profitLossCasinoOk()
			throws ParseException {

		
		    System.out.println("Ok");
			ArrayList<EXLedger> ledgerlist = nativeQueryDao.profitLossCasinoOk();
			

			System.out.println(ledgerlist.size());
			HashMap<String, Object> hm = new HashMap<String, Object>();
			Integer i=1;
			Double uplineamount =0.0;
			
			for(EXLedger led : ledgerlist) {
				
				if(led.getUserid().equalsIgnoreCase("dlp")) {
					uplineamount = uplineamount+led.getUplineamount();
					System.out.println(uplineamount);
				}
				
				nativeQueryDao.updateUserUpline(led);
				
				i++;
			}

		
			return new ResponseEntity<Object>(hm, HttpStatus.OK);
		

	

	}
	
	@RequestMapping(value = "/betDetail/{id}",method = RequestMethod.GET)
	public ResponseEntity<Object> betDetail(@PathVariable String id){
		
		//System.out.println(bets.getMatchid());
		
		ExMatchOddsBetMongo bet = exMatchOddsBetMongoRepos.findByid(id);
		
		System.out.println(bet.getDeviceInfo());
		 
	
		
		 return new ResponseEntity<Object>(HttpStatus.OK);
		
	}
	
}
