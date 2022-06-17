package com.exchange.api.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

import com.exchange.api.bean.CasinoBetLoack;
import com.exchange.api.bean.EXBetfairMarket;
import com.exchange.api.bean.EXDimondSkyFancyMatch;
import com.exchange.api.bean.EXMarketDiff;
import com.exchange.api.bean.EXMinMaxBet;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.EXUserMatchEntry;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.EventRates;
import com.exchange.api.bean.ExFancyBook;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.ExMinMaxBetSetAmount;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.MatchFancy;
import com.exchange.api.bean.OddSuspensionOnBm;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.SBABetLock;
import com.exchange.api.bean.SBAFancyBetLock;
import com.exchange.api.bean.SBAMaxAmount;
import com.exchange.api.bean.SBANetExposure;
import com.exchange.api.bean.SelectionIds;
import com.exchange.api.bean.Series;
import com.exchange.api.bean.Sport;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.dao.MarketDAO;
import com.exchange.api.dao.UserLiabilityDao;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.CasinoBetLoackRepository;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXDimondSkyFancyMatchRepository;
import com.exchange.api.repositiry.EXMarketDiffRepository;
import com.exchange.api.repositiry.EXUserMatchEntryRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.ExMinMaxBetSetAmountRepository;
import com.exchange.api.repositiry.FancyRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.MinMaxBetRepository;
import com.exchange.api.repositiry.SBABetLockRepository;
import com.exchange.api.repositiry.SBAFancyBetLockRepository;
import com.exchange.api.repositiry.SBAMaxAmountRepository;
import com.exchange.api.repositiry.SBANetExposureRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.SeriesRepository;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.api.service.AutomaticMarkertResultService;
import com.exchange.api.service.EventService;
import com.exchange.api.service.LotusEventsService;
import com.exchange.api.service.SelectionIdService;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.exchange.util.EXGlobalConstantProperties;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.common.eventbus.EventBus;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.UserRecord;

@ControllerAdvice
@RestController
@CrossOrigin
@Transactional
@RequestMapping("/api")
public class EXmarketController {

	@Autowired
	EventRepository seriesEventRepository;

	@Autowired
	SeriesRepository seriesRepository;

	@Autowired
	MinMaxBetRepository maxbetRepo;

	@Autowired
	MarketRepository eventMarketRepository;

	@Autowired
	SportRepository sportRepository;

	@Autowired
	FancyRepository fancyRepository;
	@Autowired
	HttpServletRequest request;

	@Autowired
	EXGlobalConstantProperties exglobalConst;

	@Autowired
	EventService eventService;

	@Autowired
	FancyRepository matchFancyResult;

	@Autowired
	SelectionIdRepository selectionIdRepository;

	@Autowired
	LotusEventsService lotusEventsService;

	@Autowired
	SelectionIdService selectionIdService;

	@Autowired
	MarketResultRepository marketResultRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	EXUserRepository exUserRepository;

	@Autowired
	EXUserMatchEntryRepository exusermatchentryRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	SBAMaxAmountRepository sBAMaxAmountRepository;

	EXDateUtil dtUtil = new EXDateUtil();

	@Autowired
	EXChipDetailRepository exchipRepo;

	@Autowired
	AutomaticMarkertResultService automaticMarkertResultService;

	@Autowired
	ExMinMaxBetSetAmountRepository exminmaxsetamountRepo;

	@Autowired
	AppChargeRepo appchargeRepo;

	@Autowired
	EXDimondSkyFancyMatchRepository exdaimondskyFancyRepo;

	EXDateUtil dtutil = new EXDateUtil();

	@Autowired
	MarketDAO marketDao;

	@Autowired
	UserLiabilityDao userLibDao;

	@Autowired
	EXNativeQueryDao nativeQueryDao;

	private Firestore fb;

	@Autowired
	SelectionIdRepository selectionRepo;

	@Autowired
	SBABetLockRepository sBABetLockRepository;

	@Autowired
	SBAFancyBetLockRepository sBAFancyBetLockRepository;

	@Autowired
	SBANetExposureRepository sBANetExposureRepository;

	@Autowired
	EXMarketDiffRepository  marketDiffRepo;
	
	
	ExFancyController facnycontroller;
	
	@Autowired
	ExMatchOddsBetDao betlistDao;
	
	@Autowired
	CasinoBetLoackRepository casinoBetLockRepo;
	
	
	private static org.apache.log4j.Logger log = Logger.getLogger(EXmarketController.class);

	@RequestMapping(value = "/addSeries", method = RequestMethod.POST, consumes = "application/json")
//	@CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> addSeries(@Valid @RequestBody Series series) {

		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		try {
			if (seriesRepository.findByseriesidAndAppid(series.getSeriesid(), series.getAppid()) == null) {

				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				series.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				Series seriesBean2 = seriesRepository.save(series);
				if (seriesBean2 != null) {

					responseBean.setType("success");
					responseBean.setMessage("Series added successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

				} else {

					responseBean.setType("error");
					responseBean.setMessage("Something went wrong");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}
			} else {

				Series bean = seriesRepository.findByseriesidAndAppid(series.getSeriesid(), series.getAppid());
				if (bean != null) {
					bean.setStatus(series.getStatus());
					seriesRepository.save(bean);
					responseBean.setType("success");
					responseBean.setMessage("Series added successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}

				return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			log.error("addSeries==> " + e);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateSeries", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSeries(@RequestBody Series series) {

		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		try {
			if (seriesRepository.findByseriesidAndAppid(series.getSeriesid(), series.getAppid()) == null) {
				responseBean.setType("error");
				responseBean.setMessage("Series not found");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
			}

			Series bean = seriesRepository.findByseriesidAndAppid(series.getSeriesid(), series.getAppid());

			if (series.getStatus() != null)
				bean.setStatus(series.getStatus());
			if (series.getMarketcount() != null)
				bean.setMarketcount(series.getMarketcount());
			if (series.getSeriesname() != null)
				bean.setSeriesname(series.getSeriesname());
			if (series.getSeriesid() != null)
				bean.setSeriesid(series.getSeriesid());
			if (series.getSportid() != null)
				bean.setSportid(series.getSportid());

			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bean.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));

			bean = seriesRepository.save(bean);

			if (bean != null) {

				responseBean.setType("success");
				responseBean.setMessage("Series updated successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("Update Series==>" + e);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/deleteSeries", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSeries(@RequestBody ArrayList<String> ids) {

		ResponseBean responseBean = new ResponseBean();

		try {
			if (seriesRepository.existsByidIn(ids) == false) {

				responseBean.setType("error");
				responseBean.setMessage("Series not found");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
			}

			ArrayList<Series> list = new ArrayList<>();

			list.addAll(seriesRepository.findAllByidIn(ids));

			if (list.isEmpty()) {
				responseBean.setType("success");
				responseBean.setMessage("No data found");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);

			} else if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {
					list.get(i).setIsactive(false);
				}

				seriesRepository.saveAll(list);

				for (int i = 0; i < list.size(); i++) {

					if (!seriesRepository.findAllByidIn(ids).get(i).getIsactive().equals(list.get(i).getIsactive())) {

						responseBean.setType("error");
						responseBean.setMessage("Unable to delete series");
						responseBean.setTitle("Oops...");
						return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
					}
				}

				responseBean.setType("success");
				responseBean.setMessage("Series deleted successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("Delete Sereis==>" + e);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/rollbackSeries", method = RequestMethod.PUT)
	public ResponseEntity<Object> rollbackSeries(@RequestBody ArrayList<String> ids) {

		ResponseBean responseBean = new ResponseBean();
		try {
			if (seriesRepository.existsByidIn(ids) == false) {

				responseBean.setType("error");
				responseBean.setMessage("Series not found");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
			}

			ArrayList<Series> list = new ArrayList<>();

			list.addAll(seriesRepository.findAllByidIn(ids));

			if (list.isEmpty()) {
				responseBean.setType("success");
				responseBean.setMessage("No data found");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
			} else if (list.size() != 0) {

				for (int i = 0; i < list.size(); i++) {
					list.get(i).setIsactive(true);
				}

				seriesRepository.saveAll(list);

				for (int i = 0; i < list.size(); i++) {

					if (!seriesRepository.findAllByidIn(ids).get(i).getIsactive().equals(list.get(i).getIsactive())) {

						responseBean.setType("error");
						responseBean.setMessage("Unable to rollback series");
						responseBean.setTitle("Oops...");
						return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
					}
				}

				responseBean.setType("success");
				responseBean.setMessage("Series rollback successful");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("rollbarckSeries==> " + e);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	// @CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> addEvent(@Valid @RequestBody Event event) throws ParseException {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();

		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		
		RestTemplate restTemplate = new RestTemplate();
		
		
		try {
			if (event.getMinbet() == null) {
				event.setMinbet(0);
			} else if (event.getMinbet() < 0) {
				responseBean.setType("error");
				responseBean.setMessage("Min Bet Can Not be Lesser Then 0");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
			if (event.getMaxbet() == null) {
				event.setMaxbet(0);
			} else if (event.getMaxbet() < 0) {
				responseBean.setType("error");
				responseBean.setMessage("Max Bet Can Not be Lesser Then 0");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
			if (event.getBetdelay() == null) {
				event.setMaxbet(0);
			} else if (event.getBetdelay() < 0) {
				responseBean.setType("error");
				responseBean.setMessage("BetDelay Can Not be Lesser Then 0");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
			if (event.getFancyprovider().equalsIgnoreCase("-1")) {
				responseBean.setType("error");
				responseBean.setMessage("please Select fancy Provider");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
			else {
				if (seriesEventRepository.findByeventid(event.getEventid()) == null) {

					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					event.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					event.setAddautoFancy(false);
					event.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					event.setFancypause(false);
					event.setIsactive(true);
					event.setLiveTv(false);
					event.setBetLock(false);
					event.setAddautoFancy(false);
					event.setAddautoFancyDiamond(false);
					event.setTotalExposure(5000000);
					
					if(event.getSportid()>500) {
						event.setType("Casino");
					}else {
						event.setType("Betfair");
					}
					
					if (usersession.getUsertype() == 4) {
						event.setAdminid(usersession.getId().toString());
					} else {
						if (usersession.getUsertype() == 4) {
							event.setAdminid(usersession.getParentid());
						}
					}
					if (event.getMinbet() == null) {
						event.setMinbet(0);
					} else if (event.getMinbet() < 0) {
						responseBean.setType("error");
						responseBean.setMessage("Min Bet Can Not be Lesser Then 0");
						responseBean.setTitle("Oops...");
					}
					if (event.getMaxbet() == null) {
						event.setMaxbet(0);
					} else if (event.getMaxbet() < 0) {
						responseBean.setType("error");
						responseBean.setMessage("Max Bet Can Not be Lesser Then 0");
						responseBean.setTitle("Oops...");
					}
					if (event.getBetdelay() == null) {
						event.setMaxbet(0);
					} else if (event.getBetdelay() < 0) {
						responseBean.setType("error");
						responseBean.setMessage("BetDelay Can Not be Lesser Then 0");
						responseBean.setTitle("Oops...");
					}

					if (event.getSportid() == 4 || event.getSportid() == 1 || event.getSportid() == 2) {

						if(event.getSeriesid() !=922982) {
							Instant instant = Instant.parse(event.getOpenDate());

							LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Kolkata"));
							Date out = Date.from(result.atZone(ZoneId.systemDefault()).toInstant());
							event.setOpenDate(dtutil.convertBetfairToDate(out, "yyyy-MM-dd HH:mm:ss"));
						}else {

							String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+event.getEventid(), String.class);
							JSONObject jobj = new JSONObject(new JSONObject(res).getString("data"));

							Integer  time=Integer.parseInt(jobj.getString("time"));

							long currentDateTime = System.currentTimeMillis();

							Date currentDate = new Date(time.longValue()*1000);
							event.setOpenDate(dtutil.convertBetfairToDate(currentDate, "yyyy-MM-dd HH:mm:ss"));

						}
					} else {
						Date out = new Date();
						event.setOpenDate(dtutil.convertBetfairToDate(out, "yyyy-MM-dd HH:mm:ss"));

					}

					seriesEventRepository.save(event);
					EXMarketDiff  mktDiff =  new EXMarketDiff();
					mktDiff.setBackdiff(0);
					mktDiff.setLaydiff(0);
					mktDiff.setEventid(event.getEventid());
					marketDiffRepo.save(mktDiff);
					
					if (seriesEventRepository.findByeventidAndAppid(event.getEventid(), event.getAppid()) != null) {

						responseBean.setType("success");
						responseBean.setMessage("Event added successfully");
						responseBean.setTitle("success");
						return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
					}

				} else {

					Event bean = seriesEventRepository.findByeventidAndAppid(event.getEventid(), event.getAppid());
					if (bean != null) {
						bean.setStatus(event.getStatus());
						bean.setAddautoFancy(false);
						seriesEventRepository.save(bean);
						responseBean.setType("success");
						responseBean.setMessage("Event added successfully");
						responseBean.setTitle("success");
					}

					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	

	@RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEvent(@RequestBody Event event) {

		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		Event eventList = seriesEventRepository.findByeventidAndAppid(event.getEventid(), event.getAppid());
		if (eventList != null) {
			if (!betRepository.findByIsactiveAndMatchid(true, event.getEventid()).isEmpty()) {
				responseBean.setType("error");
				responseBean.setMessage("Please Delete All the Bets for This Match");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
			}

			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			eventList.setStatus(event.getStatus());
			eventList.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
			seriesEventRepository.save(eventList);
			responseBean.setType("success");
			responseBean.setMessage("Event updated successfully");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEvent(@RequestBody ArrayList<String> ids) {

		ResponseBean responseBean = new ResponseBean();

		if (seriesEventRepository.existsByidIn(ids) == false) {

			responseBean.setType("error");
			responseBean.setMessage("Event Not Available");
			responseBean.setTitle("Oops...");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
		}

		ArrayList<Event> list = new ArrayList<>();

		list.addAll(seriesEventRepository.findAllByidIn(ids));

		if (list.isEmpty()) {
			responseBean.setType("success");
			responseBean.setMessage("No data found");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
		} else if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setIsactive(false);
			}

			seriesEventRepository.saveAll(list);

			for (int i = 0; i < list.size(); i++) {

				if (!seriesEventRepository.findAllByidIn(ids).get(i).getIsactive().equals(list.get(i).getIsactive())) {

					responseBean.setType("error");
					responseBean.setMessage("Unable to delete event");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
				}
			}

			responseBean.setType("success");
			responseBean.setMessage("Event deleted successfully");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/rollbackEvent", method = RequestMethod.PUT)
	public ResponseEntity<Object> rollbackEvent(@RequestBody ArrayList<String> ids) {

		ResponseBean responseBean = new ResponseBean();

		if (seriesEventRepository.existsByidIn(ids) == false) {

			responseBean.setType("error");
			responseBean.setMessage("Event Not Available");
			responseBean.setTitle("Oops...");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
		}

		ArrayList<Event> list = new ArrayList<>();

		list.addAll(seriesEventRepository.findAllByidIn(ids));

		if (list.isEmpty()) {
			responseBean.setType("success");
			responseBean.setMessage("No data found");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.NOT_FOUND);
		} else if (list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {
				list.get(i).setIsactive(true);
			}

			seriesEventRepository.saveAll(list);

			for (int i = 0; i < list.size(); i++) {

				if (!seriesEventRepository.findAllByidIn(ids).get(i).getIsactive().equals(list.get(i).getIsactive())) {

					responseBean.setType("error");
					responseBean.setMessage("Unable to rollback event");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
				}
			}

			responseBean.setType("success");
			responseBean.setMessage("Event rollback successful");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateMarket", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMarket(@RequestBody Market market) {

		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		HttpSession session = request.getSession();
		EXUser user = (EXUser) session.getAttribute("user");

		if (user != null) {
			Market markett = eventMarketRepository.findBymarketid(market.getMarketid());

			// EXSuperStockageMarket markett =
			// exSuperStockageMarketRepo.findByMarketid(market.getMarketid());
			if (markett != null) {
				if (!betRepository.findByIsactiveAndMatchid(true, market.getEventid()).isEmpty()) {
					responseBean.setType("error");
					responseBean.setMessage("Please Delete All the Bets for This Match");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}

				eventMarketRepository.deleteByMarketid(markett.getMarketid());
				eventMarketRepository.deleteByMarketid(markett.getMarketid() + "BM");
				responseBean.setType("success");
				responseBean.setMessage("Market updated successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateMarketBySubadmin", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMarketBySubadmin(
			@RequestBody Market market) {/*
											 * 
											 * ResponseBean responseBean = new ResponseBean(); EXDateUtil dtUtil = new
											 * EXDateUtil(); Calendar calendar = new GregorianCalendar(); TimeZone
											 * timeZone = calendar.getTimeZone(); HttpSession session =
											 * request.getSession(); EXUser user = (EXUser)
											 * session.getAttribute("user"); SimpleDateFormat dateFormater = new
											 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); if(user!=null){
											 * 
											 * Market marketbean =
											 * eventMarketRepository.findByMarketidAndStatus(market.getMarketid(),
											 * true); EXSubAdminMarket mark = new EXSubAdminMarket();
											 * 
											 * EXSubAdminMarket markett =
											 * exSubAdminMarketRepo.findBySubadminidAndMarketid(user.getId().toString(),
											 * market.getMarketid()); if(markett!=null){
											 * if(!betRepository.findByIsactiveAndAppidAndMatchid(true,
											 * market.getAppid(),market.getEventid()).isEmpty()){
											 * responseBean.setType("error");
											 * responseBean.setMessage("Please Delete All the Bets for This Match");
											 * responseBean.setTitle("Oops..."); return new
											 * ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST); }
											 * 
											 * }else{ mark = new EXSubAdminMarket();
											 * mark.setMarketid(marketbean.getMarketid());
											 * mark.setMatchname(marketbean.getMatchname()); mark.setStatus(true);
											 * mark.setIsactive(marketbean.getIsactive());
											 * mark.setIspause(marketbean.getIspause());
											 * mark.setIsResult(marketbean.getIsResult());
											 * mark.setCricexchageid(marketbean.getCricexchageid());
											 * mark.setOddsprovider("Betfair");
											 * mark.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()),
											 * timeZone.getID(), "GMT"));
											 * mark.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()),
											 * timeZone.getID(), "GMT"));
											 * mark.setMarketname(marketbean.getMarketname());
											 * mark.setAppid(user.getAppid()); mark.setEventid(marketbean.getEventid());
											 * mark.setSeriesid(marketbean.getSeriesid());
											 * mark.setSportid(marketbean.getSportid());
											 * mark.setSubadminid(user.getId().toString()); mark.setStatus(false);
											 * mark.setIsactive(false);
											 * mark.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()),
											 * timeZone.getID(), "GMT")); if(exSubAdminMarketRepo.save(mark)!=null){
											 * responseBean.setType("success");
											 * responseBean.setMessage("Market updated successfully");
											 * responseBean.setTitle("success"); return new
											 * ResponseEntity<Object>(responseBean,HttpStatus.OK);
											 * 
											 * } } } responseBean.setType("error");
											 * responseBean.setMessage("Something went wrong");
											 * responseBean.setTitle("Oops..."); return new
											 * ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
											 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}


	@RequestMapping(value = "/checkIfSeriesAdded", method = RequestMethod.GET)
	public ResponseEntity<Object> checkIfSeriesAdded() {
		ArrayList<Series> list = (ArrayList<Series>) seriesRepository.findByStatus(true);
		if (list.size() > 0) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/findEventByStatus", method = RequestMethod.GET)
	public ResponseEntity<Object> findEventByStatus() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<Event> list = new ArrayList<>();
			if (usersession.getUsertype() == 6) {
				list = (ArrayList<Event>) seriesEventRepository.findByStatusAndAdminid(true, usersession.getParentid());
			} else {
				list = (ArrayList<Event>) seriesEventRepository.findByStatusAndAdminid(true,
						usersession.getId().toString());
			}

			if (list.size() > 0) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/findMarketByStatus", method = RequestMethod.GET)
	public ResponseEntity<Object> findMarketByStatus() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<Market> list = new ArrayList<>();
			list = (ArrayList<Market>) eventMarketRepository.findByStatus(true);

			if (list.size() > 0) {

				/*
				 * for(int i=0; i<list.size(); i++){
				 * 
				 * Market subadminmarket =
				 * eventMarketRepository.findByMarketidAndStatus(list.get(i).getMarketid(),
				 * true); if(subadminmarket !=null){ list.remove(list.get(i)); } }
				 */

				for (Market mar : list) {

					mar.setIds(mar.getMarketid().split("\\.")[1]);

				}
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/findMarketByStatusAndSubadminid", method = RequestMethod.GET)
	public ResponseEntity<Object> findMarketByStatusAndSubadminid() {/*
																		 * HttpSession session = request.getSession();
																		 * EXUser usersession = (EXUser)
																		 * session.getAttribute("user");
																		 * if(usersession!=null){ ArrayList<Market>
																		 * mlist = (ArrayList<Market>)
																		 * eventMarketRepository.findByStatus(true);
																		 * if(mlist.size()>0) {
																		 * if(usersession.getUsertype()==5){
																		 * 
																		 * for(int i=0; i<mlist.size(); i++){
																		 * 
																		 * EXSubAdminMarket subadminmarket =
																		 * exSubAdminMarketRepo.
																		 * findByMarketidAndStatusAndSubadminid(mlist.
																		 * get(i).getMarketid(),
																		 * false,usersession.getId().toString());
																		 * if(subadminmarket !=null){
																		 * mlist.remove(mlist.get(i)); } } }
																		 * 
																		 * 
																		 * 
																		 * for(Market mar :mlist){
																		 * 
																		 * 
																		 * mar.setIds(mar.getMarketid().split("\\.")[1])
																		 * ;
																		 * 
																		 * } return new
																		 * ResponseEntity<Object>(mlist,HttpStatus.OK);
																		 * } else { return new
																		 * ResponseEntity<Object>(mlist,HttpStatus.
																		 * NO_CONTENT); }
																		 * 
																		 * }else{ return new
																		 * ResponseEntity<Object>(HttpStatus.BAD_REQUEST
																		 * ); }
																		 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/findMarketByStatusAndSuperStockage", method = RequestMethod.GET)
	public ResponseEntity<Object> findMarketByStatusAndSuperStockage() {/*
																		 * HttpSession session = request.getSession();
																		 * EXUser usersession = (EXUser)
																		 * session.getAttribute("user");
																		 * if(usersession!=null){ ArrayList<Market>
																		 * mlist = (ArrayList<Market>)
																		 * eventMarketRepository.findByStatus(true);
																		 * if(mlist.size()>0) {
																		 * 
																		 * if(usersession.getUsertype()==0){
																		 * 
																		 * 
																		 * for(int i=0; i<mlist.size(); i++){
																		 * 
																		 * EXSubAdminMarket subadminmarket =
																		 * exSubAdminMarketRepo.
																		 * findByMarketidAndStatusAndSubadminid(mlist.
																		 * get(i).getMarketid(),
																		 * false,usersession.getParentid());
																		 * if(subadminmarket !=null){
																		 * mlist.remove(mlist.get(i)); } }
																		 * 
																		 * 
																		 * for(int i=0; i<mlist.size(); i++){
																		 * 
																		 * EXSuperStockageMarket superstockagemarket =
																		 * exSuperStockageMarketRepo.
																		 * findByMarketidAndStatusAndSupermasterid(mlist
																		 * .get(i).getMarketid(),
																		 * false,usersession.getId().toString());
																		 * if(superstockagemarket !=null){
																		 * mlist.remove(mlist.get(i)); } } }
																		 * 
																		 * for(Market mar :mlist){
																		 * 
																		 * 
																		 * mar.setIds(mar.getMarketid().split("\\.")[1])
																		 * ;
																		 * 
																		 * } return new
																		 * ResponseEntity<Object>(mlist,HttpStatus.OK);
																		 * } else { return new
																		 * ResponseEntity<Object>(mlist,HttpStatus.
																		 * NO_CONTENT); }
																		 * 
																		 * }else{ return new
																		 * ResponseEntity<Object>(HttpStatus.BAD_REQUEST
																		 * ); }
																		 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/findMarketByStatusAndParentId", method = RequestMethod.GET)
	public ResponseEntity<Object> findMarketByStatusAndParentId() {/*
																	 * HttpSession session = request.getSession();
																	 * EXUser usersession = (EXUser)
																	 * session.getAttribute("user");
																	 * if(usersession!=null){
																	 * ArrayList<EXSubAdminMarket> list = new
																	 * ArrayList<>(); list =
																	 * (ArrayList<EXSubAdminMarket>)
																	 * exSubAdminMarketRepo.findBysubadminidAndStatus(
																	 * usersession.getParentid(),true);
																	 * if(list.size()>0) { for(EXSubAdminMarket mar
																	 * :list){
																	 * 
																	 * 
																	 * mar.setId(mar.getMarketid().split("\\.")[1]);
																	 * 
																	 * } return new
																	 * ResponseEntity<Object>(list,HttpStatus.OK); }
																	 * else { return new
																	 * ResponseEntity<Object>(list,HttpStatus.NO_CONTENT
																	 * ); }
																	 * 
																	 * }else{ return new
																	 * ResponseEntity<Object>(HttpStatus.BAD_REQUEST); }
																	 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/addSport", method = RequestMethod.POST, consumes = "application/json")
//	@CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> addSport(@Valid @RequestBody Sport sport) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		Sport sportbean = null;
		if (usersession.getUsertype() == 4 || usersession.getUsertype() == 6) {
			if (sport != null) {
				sportbean = sportRepository.findBySportIdAndAdminid(sport.getSportId(), sport.getAdminid());
				if (sportbean == null) {
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sport.setCreatedOn(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					sport.setUpdatedOn(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					sport.setAdminid("-1");
					sport = sportRepository.save(sport);
					if (sport.getId() != null) {
						responseBean.setType("success");
						responseBean.setMessage("Sport added successfully");
						responseBean.setTitle("success");
						return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
					} else {

						responseBean.setType("error");
						responseBean.setMessage("Something went wrong");
						responseBean.setTitle("Oops...");
						return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
					}

				} else {
					sportbean.setStatus(sport.getStatus());
					SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sportbean.setUpdatedOn(
							dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					sportbean = sportRepository.save(sportbean);
					responseBean.setType("success");
					responseBean.setMessage("Sport added successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}

			}
		} else {
			responseBean.setType("success");
			responseBean.setMessage("permission Denied..");
			responseBean.setTitle("success");
			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/checkIfSportAdded", method = RequestMethod.GET)
	public ResponseEntity<Object> checkIfSportAdded() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		String id = "";
		if (usersession != null) {
			if (usersession.getParentid().equalsIgnoreCase("-1")) {
				id = usersession.getParentid();
			} else {
				id = "-1";
			}
			ArrayList<Sport> list = (ArrayList<Sport>) sportRepository.findBystatusAndAdminid(true, id);
			if (list.size() > 0) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/activeSportByAdmin", method = RequestMethod.GET)
	public ResponseEntity<Object> activeSportByAdmin() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<Sport> list = (ArrayList<Sport>) sportRepository.findBystatusAndAdminid(true,
					usersession.getAdminid());
			if (list.size() > 0) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/updateSport", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSport(@RequestBody Sport sport) {

		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();

		if (sport.getSportId() != null) {
			Sport bean = sportRepository.findBySportIdAndAdminid(sport.getSportId(), sport.getAdminid());

			if (bean != null) {
				bean.setStatus(sport.getStatus());
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				bean.setUpdatedOn(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				bean = sportRepository.save(bean);
				responseBean.setType("success");
				responseBean.setMessage("Sport updated successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} else {
				responseBean.setType("error");
				responseBean.setMessage("Sport Not Found");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.NO_CONTENT);
			}
		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/updateLotusEvent", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateLotusEvent(@RequestParam String marketid, @RequestParam Integer eventid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();

		if (usersession != null) {
			try {
				Market marketbean = null;
				Event eventbean = null;
				eventbean = seriesEventRepository.findByeventidAndAppid(eventid, usersession.getAppid());
				marketbean = eventMarketRepository.findByMarketidAndStatus(marketid, true);
				if (marketbean != null) {
					if (!betRepository.findByIsactiveAndMatchid(true, marketbean.getEventid()).isEmpty()) {
						responseBean.setType("error");
						responseBean.setMessage("Please Delete All the Bets for This Match");
						responseBean.setTitle("Oops...");
						return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
					} else {

						marketbean.setStatus(false);
						marketbean.setIsactive(false);
						eventMarketRepository.save(marketbean);
					}

				}

				if (eventbean != null) {
					eventbean.setStatus(false);
					seriesEventRepository.save(eventbean);
				}

				responseBean.setTitle("success");
				responseBean.setType("success");
				responseBean.setMessage("Market Updated successfully...");

				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "getEventRates", method = RequestMethod.GET)
	public ResponseEntity<Object> getEventRates(@RequestParam(name = "sportid", required = true) Integer sportid)
			throws ParseException {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {

			ArrayList<Market> marketlist = marketDao.getinPlayMatches(sportid, null);
			ArrayList<EventRates> list = eventService.getEventRatesList(marketlist, "matchlist");

			if (list.isEmpty()) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "getEventsName", method = RequestMethod.GET)
	public ResponseEntity<Object> getEventsName(@RequestParam(name = "sportid", required = true) Integer sportid,
			@RequestParam(name = "matchName", required = true) String matchregex) {
		ArrayList<Market> eventList = new ArrayList<>();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {

			eventList = marketDao.getEventRates(sportid, matchregex, usersession.getSupermasterid(), 1);
			ArrayList<EventRates> list = eventService.getEventRatesList(eventList, "matchlist");

			if (list.isEmpty()) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "getLotusEventList", method = RequestMethod.GET)
	public ResponseEntity<Object> getLotusEventList(@RequestParam(name = "eventid", required = true) String eventid,
			@RequestParam String fancyprovider) throws JSONException {

		LinkedHashMap<String, Object> list = lotusEventsService.getMatchFancy(eventid, fancyprovider);
	
		
		if (list.isEmpty()) {
			return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
	} 

	@RequestMapping(value = "/saveLotusFancy", method = RequestMethod.POST)
	public ResponseEntity<Object> saveLotusFancy(@RequestParam String id, @RequestParam Integer eventid,@RequestParam String fancyid, @RequestParam String skyfancyid,@RequestParam String mtype,@RequestParam String provider) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			String marketname =null;
			if(eventid  ==28127348) {
				marketname = EXConstants.Winner;
			}else {
				marketname = EXConstants.MatchOdds;
			}
			Market bean = eventMarketRepository.findByStatusAndEventidAndMarketname(true, eventid, marketname);
			if (lotusEventsService.SaveFancy(id, eventid, usersession.getAppid(), fancyid, skyfancyid,mtype,provider)) {
				responseBean.setType("error");
				responseBean.setMessage("Something went wrong");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
			} 

			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy Added successfully...");

			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @RequestMapping(value = "/getMatchFancy",method = RequestMethod.GET) public
	 * ResponseEntity<Object> getMatchFancy(@RequestParam String eventid) throws
	 * JSONException, IOException{ HttpSession session = request.getSession(true);
	 * ResponseBean responseBean = new ResponseBean(); EXUser usersession =(EXUser)
	 * session.getAttribute("user"); Market marketbean =
	 * eventMarketRepository.findByStatusAndEventidAndMarketname(true,
	 * Integer.parseInt(eventid), "Match Odds");
	 * 
	 * JSONObject res1 = new JSONObject();
	 * 
	 * 
	 * JSONObject statusjson = new JSONObject(); JSONObject status1 = new
	 * JSONObject();
	 * 
	 * statusjson.put("statusCode", "0"); statusjson.put("statusDesc", "Success");
	 * status1.put("status", statusjson);
	 * 
	 * if(usersession!=null){ if(marketbean!=null){
	 * 
	 * ArrayList<MatchFancy> list = new ArrayList<>(); list =
	 * matchFancyResult.findByeventidAndActive(Integer.parseInt(eventid),1);
	 * 
	 * ArrayList<String> fncyl = new ArrayList<String>(); for(MatchFancy fn :list){
	 * fncyl.add(fn.getFancyid()); } if(list.size()>0){ JSONArray array2 = new
	 * JSONArray();
	 * 
	 * 
	 * JSONObject res = null; String json = new
	 * String(Files.readAllBytes(Paths.get("Daimond"+"/"+marketbean.getEventid()+
	 * ".json")), StandardCharsets.UTF_8); if(json!=null){
	 * 
	 * //System.out.println("json"+json); JSONArray jsonarray = new
	 * JSONArray(json.toString());
	 * 
	 * 
	 * for(int i=0;i<jsonarray.length();i++) {
	 * 
	 * 
	 * JSONObject obj = jsonarray.getJSONObject(i);
	 * 
	 * if(fncyl.contains(obj.getString("id"))){
	 * 
	 * 
	 * JSONObject runnersjson = new JSONObject(); JSONArray backarray = new
	 * JSONArray(); JSONArray layarray = new JSONArray(); JSONArray runnerarray =
	 * new JSONArray();
	 * 
	 * res = new JSONObject(); res.put("id",
	 * obj.getString("id").split("-")[1].split("\\.")[0]);
	 * res.put("fancyid",obj.getString("id")); res.put("name",
	 * obj.getString("name")); if(obj.has("statusLabel")){
	 * if(obj.getString("statusLabel").equalsIgnoreCase(EXConstants.Suspended)){
	 * 
	 * JSONObject backjson = new JSONObject();
	 * backjson.put("line",EXConstants.Suspended); backjson.put("price",
	 * EXConstants.Suspended); backjson.put("size", null); JSONObject layjson = new
	 * JSONObject(); layjson.put("line",EXConstants.Suspended); layjson.put("price",
	 * EXConstants.Suspended); layjson.put("size", null); backarray.put(backjson);
	 * layarray.put(layjson);
	 * 
	 * res.put("back",backarray); res.put("lay",layarray); }else
	 * if(obj.getString("statusLabel").equalsIgnoreCase(EXConstants.BallRunning)){
	 * JSONObject backjson = new JSONObject();
	 * backjson.put("line",EXConstants.BallRunning); backjson.put("price",
	 * EXConstants.BallRunning); backjson.put("size", null); JSONObject layjson =
	 * new JSONObject(); layjson.put("line",EXConstants.BallRunning);
	 * layjson.put("price", EXConstants.BallRunning); layjson.put("size", null);
	 * backarray.put(backjson); layarray.put(layjson);
	 * 
	 * res.put("back",backarray); res.put("lay",layarray); } else{ res.put("back",
	 * new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")));
	 * res.put("lay", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay"))); } }
	 * 
	 * else{ res.put("back", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")));
	 * res.put("lay", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay"))); }
	 * 
	 * runnerarray.put(res); runnersjson.put("runners", runnerarray);
	 * runnersjson.put("name", obj.getString("name"));
	 * runnersjson.put("provider","FANCY"); runnersjson.put("oddsType",
	 * "HAAR_JEET"); if(obj.has("statusLabel")){ runnersjson.put("statusLabel",
	 * obj.getString("statusLabel")); runnersjson.put("status",
	 * obj.getString("statusLabel")); }else{ runnersjson.put("statusLabel", "");
	 * runnersjson.put("status", "OPEN"); }
	 * 
	 * runnersjson.put("mtype", "INNINGS_RUNS");
	 * 
	 * runnersjson.put("btype", "LINE"); runnersjson.put("eventTypeId", eventid);
	 * runnersjson.put("inplay", true); UserLiability liab =
	 * liabilityRepository.findByFancyidAndUseridAndMatchid(marketbean.getMarketid()
	 * +"-"+obj.getString("id"), usersession.getUserid(),Integer.parseInt(eventid));
	 * if(liab!=null){ runnersjson.put("netexpo", liab.getPnl1()); }
	 * array2.put(runnersjson); }
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * res1.put("result", array2); res1.put("status", statusjson); JSONArray
	 * resultnew = new JSONArray(); if(usersession!=null){ return new
	 * ResponseEntity<Object>(array2.toString(),HttpStatus.OK); }
	 * 
	 * }
	 * 
	 * 
	 * else{
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.NO_CONTENT); }
	 * 
	 * 
	 * 
	 * 
	 * } }
	 * 
	 * responseBean.setType("error");
	 * responseBean.setMessage("Something went wrong");
	 * responseBean.setTitle("Oops..."); return new
	 * ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST); }
	 */

	@RequestMapping(value = "/getActiveSportList", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveSportList(@RequestParam(name = "status", required = true) Boolean status) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {

			ArrayList<Sport> list = (ArrayList<Sport>) sportRepository.findByTypeAndStatus("Betfair", true);
			try {
				if (list.size() > 0) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/getActiveSeriesListBySport", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveSeriesListBySport(
			@RequestParam(name = "sportid", required = true) Integer sportid,
			@RequestParam(name = "status", required = true) Boolean status) {
		ArrayList<Series> list = (ArrayList<Series>) seriesRepository.findBySportidAndStatus(sportid, status);
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

	@RequestMapping(value = "/getActiveEventListBySport", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveEventListBySport(
			@RequestParam(name = "sportid", required = true) Integer sportid,
			@RequestParam(name = "status", required = true) Boolean status) {

		// ArrayList<Event> list = (ArrayList<Event>)
		// seriesEventRepository.findByStatusAndSportidAndAdminid(status, sportid,
		// subadminid);
		ArrayList<Event> list = (ArrayList<Event>) seriesEventRepository.findByStatusAndSportid(status, sportid);
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
	
	@RequestMapping(value = "/getFancyProvider/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getFancyProvider(@PathVariable Integer eventId) {

		
		Event list =  seriesEventRepository.findByEventidAndStatus(eventId, true);
		try {
			if (list!=null) {

				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
		}

	}
	
	
	@RequestMapping(value = "/getActiveCasinoList", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveCasinoList(@RequestParam Boolean livetv) {

		// ArrayList<Event> list = (ArrayList<Event>)
		// seriesEventRepository.findByStatusAndSportidAndAdminid(status, sportid,
		// subadminid);
		ArrayList<Event> list = (ArrayList<Event>) seriesEventRepository.findByLiveTvAndType(livetv, "Casino");
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

	@RequestMapping(value = "/getActiveMarketListByEvent", method = RequestMethod.GET)
	public ResponseEntity<Object> getActiveMarketListByEvent(
			@RequestParam(name = "eventid", required = true) Integer eventid,
			@RequestParam(name = "isactive", required = true) Boolean isactive,
			@RequestParam(name = "status", required = true) Boolean status) {

		// ArrayList<Market> list = (ArrayList<Market>)
		// eventMarketRepository.findByEventidAndIsactiveAndStatusAndSubadminid(eventid,
		// isactive, status,subadminid);
		ArrayList<Market> bean = eventMarketRepository.findByEventidAndIsactiveAndStatus(eventid, isactive, status);
		try {
			if (bean != null) {
				return new ResponseEntity<Object>(bean, HttpStatus.OK);

			} else {
				return new ResponseEntity<Object>(bean, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/getFacnyListByMatchId", method = RequestMethod.GET)
	public ResponseEntity<Object> getFacnyListByMatchId(
			@RequestParam(name = "eventid", required = true) Integer eventid) {

		ArrayList<MatchFancy> bean = fancyRepository.findByEventidAndIsActive(eventid, false);
		try {
			if (bean.size() > 0) {
				return new ResponseEntity<Object>(bean, HttpStatus.OK);

			} else {
				return new ResponseEntity<Object>(bean, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "getSelctionListByMarketId", method = RequestMethod.GET)
	public ResponseEntity<Object> getSelctionListByMarketId(
			@RequestParam(name = "marketid", required = true) String marketid) {

		// ArrayList<SelectionIds> list =
		// selectionIdService.getSelectionsList(marketid);
		ArrayList<SelectionIds> list = selectionIdRepository.findByMarketid(marketid);

		if (list == null) {
			return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getActiveMarkerResultList", method = RequestMethod.GET)
	// public ResponseEntity<Object> getActiveMarkerResultList( @RequestParam(name =
	// "status",required = true) Boolean status, @RequestParam(name =
	// "appid",required = true) Integer appid){
	public ResponseEntity<Object> getActiveMarkerResultList(
			@RequestParam(name = "status", required = true) Boolean status) {
		//HttpSession session = request.getSession(true);
		//EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<MarketResult> list = (ArrayList<MarketResult>) marketResultRepository.findTop100ByStatusAndTypeOrderByIdDesc(status,"Result");
		try {
			if (list.size() > 0) {
				for (MarketResult result : list) {
					result.setDate(result.getDate());
				}

				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/setMinMaxBet", method = RequestMethod.POST)
	public ResponseEntity<Object> setMinMaxBet(@RequestBody EXMinMaxBet maxbet) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXMinMaxBet minbet = maxbetRepo.findByTypeAndAppid(maxbet.getType(), usersession.getAppid());
			if (minbet != null) {
				if (maxbet.getMaxbet() != null) {
					minbet.setMaxbet(maxbet.getMaxbet());
				}
				if (maxbet.getMinbet() != null) {
					minbet.setMinbet(maxbet.getMinbet());
				}
				if (maxbet.getBetdelay() != null) {
					minbet.setBetdelay(maxbet.getBetdelay());
				}

				if (maxbet.getBetdelay() == null && maxbet.getMaxbet() == null && maxbet.getMinbet() == null) {
					responseBean.setType("error");
					responseBean.setMessage("Kindly Fill All The Fields");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
				}

				if (maxbetRepo.save(minbet) != null) {
					responseBean.setType("success");
					responseBean.setMessage("Bet Limit Saved successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}
			} else {

				if (maxbet.getBetdelay() == null && maxbet.getMaxbet() == null && maxbet.getMinbet() == null) {
					responseBean.setType("error");
					responseBean.setMessage("Kindly Fill All The Fields");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
				}

				if (maxbetRepo.save(maxbet) != null) {
					responseBean.setType("success");
					responseBean.setMessage("Bet Limit Saved successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				}
			}

		}

		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getMinMaxBet", method = RequestMethod.GET)
	public ResponseEntity<Object> getMinMaxBet() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			ArrayList<EXMinMaxBet> bets = (ArrayList<EXMinMaxBet>) maxbetRepo.findByAppid(usersession.getAppid());

			try {
				if (!bets.isEmpty()) {
					return new ResponseEntity<Object>(bets, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(bets, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/setMarketResultAutomatic", method = RequestMethod.POST)
	public ResponseEntity<Object> setMarketResultAutomatic() {
		Boolean result = automaticMarkertResultService.setMarkertResultAutomatic();
		try {
			if (result == true) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/getMatchBymarketname", method = RequestMethod.GET)
	public ResponseEntity<Object> getMatchBymarketname(@RequestParam(name = "status", required = true) Boolean status,
			@RequestParam(name = "sportid", required = true) Integer sportid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {

			ArrayList<Event> list = eventRepo.findByStatusAndSportid(status, sportid);

			try {

				return new ResponseEntity<Object>(list, HttpStatus.OK);

			} catch (Exception e) {
				return new ResponseEntity<Object>(HttpStatus.CONFLICT);
			}

		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getMatchBymarketnameforfancy", method = RequestMethod.GET)
	public ResponseEntity<Object> getMatchBymarketnameforfancy(
			@RequestParam(name = "status", required = true) Boolean status) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			ArrayList<Event> list = new ArrayList<>();

			
			list = (ArrayList<Event>) eventRepo.findByStatusAndSportid(status, 4);

			try {
				if (list.size() > 0) {
				

					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	
	@RequestMapping(value = "/findBySportid/{sportid}", method = RequestMethod.GET)
	public ResponseEntity<Object> findBySportid(@PathVariable Integer sportid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
 
		if (usersession != null) {
			ArrayList<Event> list = new ArrayList<>();

			
			list = (ArrayList<Event>) eventRepo.findBySportid(sportid);

			try {
				if (list.size() > 0) {
				

					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/getInPlay", method = RequestMethod.GET)
	public ResponseEntity<String> getInPlay(@RequestParam Integer sportid) {
		ArrayList<Market> eventList = new ArrayList<>();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {
			eventList = marketDao.getinPlayMatches(sportid, "1");

			JSONObject matchOdds = eventService.getEventRatesListInplay(eventList, "inplay", usersession);

			// if(!list.isEmpty()){
			return new ResponseEntity<String>(matchOdds.toString(), HttpStatus.OK);
			// }

			// return new ResponseEntity<Object>( HttpStatus.NO_CONTENT);
		}
		// return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	

	@RequestMapping(value = "/setMarkertResultAutomaticByMatch", method = RequestMethod.POST)
	public ResponseEntity<Object> setMarkertResultAutomaticByMatch(@RequestParam String marketid) {
		Boolean result = automaticMarkertResultService.setMarkertResultAutomaticByMatch(marketid);
		try {
			if (result == true) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

	}

/*	@RequestMapping(value = "/changeMatchProvider", method = RequestMethod.POST)
	public ResponseEntity<Object> changeMatchProvider(@RequestParam int eventid) {
		Market market = eventMarketRepository.findByEventid(eventid);
		ResponseBean responseBean = new ResponseBean();

		if (market != null) {
			if (market.getOddsprovider().equalsIgnoreCase("lotus")) {
				market.setOddsprovider("Betfair");
			} else {
				market.setOddsprovider("Lotus");
			}

			if (eventMarketRepository.save(market) != null) {
				responseBean.setType("success");
				responseBean.setMessage("Match Provider Changed successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
	}
*/
	@RequestMapping(value = "/setMarkertResultAutomaticByService", method = RequestMethod.POST)
	public ResponseEntity<Object> setMarkertResultAutomaticByService() {
		Boolean result = automaticMarkertResultService.setMarkertResultAutomaticByService();
		try {
			if (result == true) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/cricketExchMatch", method = RequestMethod.GET)
	public ResponseEntity<Object> cricketExchMatch() throws Exception {

		ArrayList<Event> list = new ArrayList<Event>();

		RestTemplate restTemplate = new RestTemplate();
		String cricexchresult = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/liveMatches.json",
				String.class);

		JSONObject cricexchjson = new JSONObject(cricexchresult);
		Iterator<String> keys = cricexchjson.keys();
		JSONObject cricexchobj = null;

		try {
			ArrayList<String> keyval = new ArrayList();

			while (keys.hasNext()) {

				String keyValue = (String) keys.next();
				keyval.add(keyValue);
				cricexchobj = cricexchjson.getJSONObject(keyValue);
				if (cricexchobj.has("status")) {
					if ((cricexchobj.getInt("status") == 0) || (cricexchobj.getInt("status") == 1)) {
						Event matches = new Event();
						matches.setCricexchageId(keyValue);
						matches.setEventname(cricexchobj.getString("title"));
						list.add(matches);
					}

				}
			}

			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(list, HttpStatus.BAD_REQUEST);

	}




	
	@RequestMapping(value = "/checkEventActive", method = RequestMethod.GET)
	public ResponseEntity<Object> checkEventActive(@RequestParam Integer eventid) {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			Event eventbean = eventRepo.findByeventid(eventid);

			try {
				if (eventbean != null) {
					if (eventbean.getStatus()) {
						return new ResponseEntity<Object>(HttpStatus.OK);
					} else {
						return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/getevent", method = RequestMethod.GET)
	public ResponseEntity<Object> getevent(@RequestParam Integer eventid) {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			Event eventbean = eventRepo.findByEventidAndStatus(eventid, true);
			String event = "event id not found";
			try {
				if (eventbean != null) {
					if (eventbean.getStatus()) {
						return new ResponseEntity<Object>(eventbean, HttpStatus.OK);
					} else {
						return new ResponseEntity<Object>(event, HttpStatus.NO_CONTENT);
					}
				}

			} catch (Exception e) {

			}
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/getMatchesList/{sportId}", method = RequestMethod.GET)
	public ResponseEntity<Object> add(@PathVariable Integer sportId) {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<Event> eventbean = null;
		if (usersession != null) {

			eventbean = eventRepo.findByStatusAndSportid(true, sportId);
			try {
				if (eventbean != null) {
					return new ResponseEntity<Object>(eventbean, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
				}
			} catch (Exception e) {

			}
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}




	@RequestMapping(value = "/addMarketByAdmin", method = RequestMethod.POST)
	// @CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> addMarketByAdmin(@Valid @RequestBody Market market) {

		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			
			
			
			Market mark = eventMarketRepository.findByMarketidAndStatus(market.getMarketid(), true);
		//	Market bookmark = eventMarketRepository.findByMarketidAndStatus(market.getMarketid() + "BM", true);
			
			Event ev = eventRepo.findByeventid(market.getEventid());
			
			if(!ev.getFancyprovider().equalsIgnoreCase(EXConstants.Diamond) || ev.getFancyprovider().equalsIgnoreCase(EXConstants.Both)){
				if (mark == null) {

					if(market.getMarketname().equalsIgnoreCase("Match Odds") ) {
						
						ArrayList<SelectionIds> selectionList = selectionIdRepository.findByMarketid(market.getMarketid());
						if (selectionList.size() == 0) {
							selectionList = selectionIdService.SaveSelectionId(market.getMarketid(),
									market.getEventid().toString());
						}
					}else 	if(market.getMarketname().contains("BOOKMAKER")) {
						market.setMarketname("Bookmaker");
					}
					

					Date out = new Date();
					if(ev.getSeriesid()!=922982) {

						
						if(market.getMarketname().equalsIgnoreCase("Bookmaker")) {
							
							Integer  time=Integer.parseInt(market.getStartdate());
							out = new Date(time.longValue()*1000);
						}else {
							Instant instant = Instant.parse(market.getStartdate());
							LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Kolkata"));
							out = Date.from(result.atZone(ZoneId.systemDefault()).toInstant());

						}

						
					/*	Integer  time=Integer.parseInt(market.getStartdate());
						out = new Date(time.longValue()*1000);*/

					}else {
						Integer  time=Integer.parseInt(market.getStartdate());
						out = new Date(time.longValue()*1000);
					}
					
					
					mark = new Market();
					
					if(!market.getMarketname().equalsIgnoreCase("Match Odds")) {
						RestTemplate restTemplate = new RestTemplate();
						String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+market.getEventid(), String.class);
						JSONArray ary = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("bookMaker"));
						
						for(int i=0; i<ary.length();i++) {
							
						     if(new JSONObject(ary.get(i).toString()).getString("market_id").equalsIgnoreCase(market.getMarketid())) {
						    	System.out.println(new JSONObject(ary.get(i).toString()).getString("marketName").replaceAll("\\s+",""));
						    	 if(new JSONObject(ary.get(i).toString()).getString("marketName").contains("Bookmaker") || new JSONObject(ary.get(i).toString()).getString("marketName").equalsIgnoreCase("TOSS") || new JSONObject(ary.get(i).toString()).getString("marketName").contains("BOOKMAKER")) {
										JSONArray runners = new JSONArray(new JSONObject(ary.get(i).toString()).getString("runners"));
										for(int k = 0; k<runners.length(); k++) {
											
											
											SelectionIds Idsbook=new SelectionIds();
											Idsbook.setMarketid(market.getMarketid());
											Idsbook.setSelectionid(runners.getJSONObject(k).getInt("secId"));
											Idsbook.setRunnerName(runners.getJSONObject(k).getString("runner"));
											Idsbook.setCreatedon(new Date());
											 
											selectionIdRepository.save(Idsbook);
										}
									}
						     }
								
								
											
							
						
					}
					
						mark.setOddsprovider("RS");
					}else {
						mark.setOddsprovider("Betfair");
					}
				
				//	System.out.println(dtutil.convertBetfairToDate(out, "yyyy-MM-dd HH:mm:ss"));
					
					mark.setStopMarketOdds(true);
					mark.setMarketid(market.getMarketid());
					mark.setMatchname(market.getMatchname());
					mark.setStatus(true);
					mark.setIsactive(true);
					mark.setIspause(true);
					mark.setIsResult(false);
					mark.setCricexchageid(market.getCricexchageid());
				
					
					mark.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					mark.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					mark.setMarketname(market.getMarketname());
					mark.setAppid(usersession.getAppid());
					mark.setEventid(market.getEventid());
					mark.setSeriesid(market.getSeriesid());
					mark.setSportid(market.getSportid());
					mark.setAddautoFancy(false);
					mark.setPauseByAdmin(false);
					mark.setOpendate(dtUtil.addHourToDate(out));
					mark.setInPlay(false);
					mark.setPauseByAdmin(true);
					mark.setiSfsmarkedInactive(false);
					mark.setIsresultFinished(false);
					mark.setIsautomatchActive(true);
					mark.setOdds_suspension_on_bm_status(OddSuspensionOnBm.off);
					
					
					
					
				/*	if (market.getSportid() == 4) {
						bookmark = new Market();
						bookmark.setStopMarketOdds(true);
						bookmark.setMarketid(market.getMarketid() + "BM");
						bookmark.setMatchname(market.getMatchname());
						bookmark.setStatus(true);
						bookmark.setIsactive(true);
						bookmark.setIspause(true);
						bookmark.setIsResult(false);
						bookmark.setCricexchageid(market.getCricexchageid());
						bookmark.setOddsprovider(EXConstants.Bookmaker);
						bookmark.setCreatedon(
								dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
						bookmark.setUpdatedon(
								dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
						bookmark.setMarketname(EXConstants.Bookmaker);
						bookmark.setAppid(usersession.getAppid());
						bookmark.setEventid(market.getEventid());
						bookmark.setSeriesid(market.getSeriesid());
						bookmark.setSportid(market.getSportid());
						bookmark.setAddautoFancy(false);
						bookmark.setPauseByAdmin(false);
						bookmark.setOpendate(dtUtil.addHourToDate(out));
						bookmark.setInPlay(false);
						bookmark.setPauseByAdmin(true);
						bookmark.setiSfsmarkedInactive(false);
						bookmark.setIsresultFinished(false);
						mark.setIsautomatchActive(false);
						// bookmark.setResultstatusCron(false);
						eventMarketRepository.save(bookmark);
					}*/

					if (eventMarketRepository.save(mark) != null) {

						responseBean.setType("success");
						responseBean.setMessage("Market added successfully");
						responseBean.setTitle("success");
						return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

					} else {

						responseBean.setType("error");
						responseBean.setMessage("Unable to add market");
						responseBean.setTitle("Oops...");
						return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
					}

				}
			}else {
				responseBean.setType("error");
				responseBean.setMessage("please change event Into Both");
				responseBean.setTitle("Oops...");
				return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/findFancyByEventId", method = RequestMethod.GET)
	public ResponseEntity<Object> findFancyByEventId(@RequestParam Integer eventid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {

			ArrayList<MatchFancy> mflist = matchFancyResult.findByEventidAndIsActive(eventid, true);
			if (mflist.size() > 0) {
				return new ResponseEntity<Object>(mflist, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(mflist, HttpStatus.NO_CONTENT);
			}

		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/findFancyByStatusForSubAdmin", method = RequestMethod.GET)
	public ResponseEntity<Object> findFancyByStatusForSubAdmin(
			@RequestParam Integer eventid) {/*
											 * HttpSession session = request.getSession(); EXUser usersession = (EXUser)
											 * session.getAttribute("user"); if(usersession!=null){
											 * ArrayList<MatchFancy> list = new ArrayList<>(); list =
											 * (ArrayList<MatchFancy>) matchFancyResult.findByeventidAndActive(eventid,
											 * 1);
											 * 
											 * if(list.size()>0) {
											 * 
											 * for(int i=0; i<list.size(); i++){
											 * 
											 * EXSubAdminMatchFancy subadminFancy =
											 * EXsubadminMatchFancyRepo.findByFancyidAndStopAndSubadminid(list.get(i).
											 * getFancyid(),true,usersession.getId().toString()); if(subadminFancy
											 * !=null){ list.remove(list.get(i)); } }
											 * 
											 * 
											 * 
											 * return new ResponseEntity<Object>(list,HttpStatus.OK); } else { return
											 * new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT); }
											 * 
											 * }else{ return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST); }
											 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateFancyBySubadmin", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateFancyBySubadmin(@RequestParam String fancyid) {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			/*
			 * 
			 * try{
			 * 
			 * EXSubAdminMatchFancy subadminmatchFany =
			 * EXsubadminMatchFancyRepo.findByFancyid(fancyid); if(subadminmatchFany
			 * ==null){
			 * 
			 * MatchFancy mfancy = matchFancyResult.findByFancyid(fancyid);
			 * if(mfancy!=null){ subadminmatchFany = new EXSubAdminMatchFancy();
			 * subadminmatchFany.setRunnerid(mfancy.getRunnerid());
			 * subadminmatchFany.setId(); subadminmatchFany.setName(mfancy.getName());
			 * subadminmatchFany.setSportId(mfancy.getSportId());
			 * subadminmatchFany.setMatchId(mfancy.getMatchId());
			 * subadminmatchFany.setEventid(mfancy.getMatchId());
			 * subadminmatchFany.setBackLine(0); subadminmatchFany.setBackPrice(0);
			 * subadminmatchFany.setLayLine(0);
			 * 
			 * subadminmatchFany.setLayPrice(0); subadminmatchFany.setIsplay(true);
			 * subadminmatchFany.setMaxLiabilityPerBet(mfancy.getMaxLiabilityPerBet());
			 * subadminmatchFany.setBetDelay(mfancy.getBetDelay());
			 * 
			 * subadminmatchFany.setIsBettable(mfancy.getIsBettable());
			 * subadminmatchFany.setIsApi(1);
			 * 
			 * subadminmatchFany.setFancyid(mfancy.getFancyid());
			 * subadminmatchFany.setSeriesId(mfancy.getSeriesId());
			 * subadminmatchFany.set__v(0); subadminmatchFany.setAppid(mfancy.getAppid());
			 * subadminmatchFany.setProvider(mfancy.getProvider());
			 * subadminmatchFany.setStatus(mfancy.getStatus());
			 * subadminmatchFany.setOddsType(mfancy.getOddsType());
			 * subadminmatchFany.setMtype(mfancy.getMtype());
			 * subadminmatchFany.setBtype(mfancy.getBtype());
			 * subadminmatchFany.setSeriesname(mfancy.getSeriesname());
			 * subadminmatchFany.setMatchname(mfancy.getMatchname());
			 * subadminmatchFany.setMaxLiabilityPerMarket(mfancy.getMaxLiabilityPerMarket())
			 * ; subadminmatchFany.setActive(0); subadminmatchFany.setStop(true);
			 * 
			 * subadminmatchFany.setSubadminid(usersession.getId().toString());
			 * EXsubadminMatchFancyRepo.save(subadminmatchFany);
			 * responseBean.setType("success");
			 * responseBean.setMessage("Fancy Removed successfully");
			 * responseBean.setTitle("success"); return new
			 * ResponseEntity<Object>(responseBean,HttpStatus.OK);
			 * 
			 * }
			 * 
			 * } else{
			 * 
			 * 
			 * subadminmatchFany.setActive(1); subadminmatchFany.setStop(false);
			 * EXsubadminMatchFancyRepo.delete(subadminmatchFany);
			 * responseBean.setType("success");
			 * responseBean.setMessage("Fancy added successfully");
			 * responseBean.setTitle("success"); return new
			 * ResponseEntity<Object>(responseBean,HttpStatus.OK); } }catch(Exception e){
			 * e.printStackTrace(); }
			 * 
			 */}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/findFancyByStatusAndSuperStockage", method = RequestMethod.GET)
	public ResponseEntity<Object> findFancyByStatusAndSuperStockage(@RequestParam Integer eventid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			/*
			 * ArrayList<MatchFancy> list = new ArrayList<>(); list =
			 * (ArrayList<MatchFancy>) matchFancyResult.findByeventidAndActive(eventid, 1);
			 * ArrayList<Market> mlist = (ArrayList<Market>)
			 * eventMarketRepository.findByStatus(true); if(list.size()>0) {
			 * 
			 * if(usersession.getUsertype()==0){
			 * 
			 * 
			 * for(int i=0; i<list.size(); i++){
			 * 
			 * EXSubAdminMatchFancy subadminFancy =
			 * EXsubadminMatchFancyRepo.findByFancyidAndStopAndSubadminid(list.get(i).
			 * getFancyid(),true,usersession.getParentid()); if(subadminFancy !=null){
			 * list.remove(list.get(i)); } }
			 * 
			 * 
			 * for(int i=0; i<list.size(); i++){
			 * 
			 * 
			 * EXSuperstockageMatchFancy superstockagefancybean =
			 * exsupermasterMatchFancyRepo.findByFancyidAndStopAndSupermasterid(list.get(i).
			 * getFancyid(), true,usersession.getId().toString()); if(superstockagefancybean
			 * !=null){ list.remove(list.get(i)); } } }
			 * 
			 * 
			 * return new ResponseEntity<Object>(list,HttpStatus.OK); } else { return new
			 * ResponseEntity<Object>(mlist,HttpStatus.NO_CONTENT); }
			 * 
			 */} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateFancyBySuperStockage", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateFancyBySuperStockage(
			@RequestParam String fancyid) {/*
											 * 
											 * HttpSession session = request.getSession(); EXUser usersession = (EXUser)
											 * session.getAttribute("user"); ResponseBean responseBean = new
											 * ResponseBean(); if(usersession!=null){
											 * 
											 * try{
											 * 
											 * EXSuperstockageMatchFancy superstockagefancymatchFany =
											 * exsupermasterMatchFancyRepo.findByFancyid(fancyid);
											 * if(superstockagefancymatchFany ==null){
											 * 
											 * MatchFancy mfancy = matchFancyResult.findByFancyid(fancyid);
											 * if(mfancy!=null){ superstockagefancymatchFany = new
											 * EXSuperstockageMatchFancy();
											 * superstockagefancymatchFany.setRunnerid(mfancy.getRunnerid());
											 * superstockagefancymatchFany.setId(mfancy.getId());
											 * superstockagefancymatchFany.setName(mfancy.getName());
											 * superstockagefancymatchFany.setSportId(mfancy.getSportId());
											 * superstockagefancymatchFany.setMatchId(mfancy.getMatchId());
											 * superstockagefancymatchFany.setEventid(mfancy.getMatchId());
											 * superstockagefancymatchFany.setBackLine(0);
											 * superstockagefancymatchFany.setBackPrice(0);
											 * superstockagefancymatchFany.setLayLine(0);
											 * 
											 * superstockagefancymatchFany.setLayPrice(0);
											 * superstockagefancymatchFany.setIsplay(true);
											 * superstockagefancymatchFany.setMaxLiabilityPerBet(mfancy.
											 * getMaxLiabilityPerBet());
											 * superstockagefancymatchFany.setBetDelay(mfancy.getBetDelay());
											 * 
											 * superstockagefancymatchFany.setIsBettable(mfancy.getIsBettable());
											 * superstockagefancymatchFany.setIsApi(1);
											 * 
											 * superstockagefancymatchFany.setFancyid(mfancy.getFancyid());
											 * superstockagefancymatchFany.setSeriesId(mfancy.getSeriesId());
											 * superstockagefancymatchFany.set__v(0);
											 * superstockagefancymatchFany.setAppid(mfancy.getAppid());
											 * superstockagefancymatchFany.setProvider(mfancy.getProvider());
											 * superstockagefancymatchFany.setStatus(mfancy.getStatus());
											 * superstockagefancymatchFany.setOddsType(mfancy.getOddsType());
											 * superstockagefancymatchFany.setMtype(mfancy.getMtype());
											 * superstockagefancymatchFany.setBtype(mfancy.getBtype());
											 * superstockagefancymatchFany.setSeriesname(mfancy.getSeriesname());
											 * superstockagefancymatchFany.setMatchname(mfancy.getMatchname());
											 * superstockagefancymatchFany.setMaxLiabilityPerMarket(mfancy.
											 * getMaxLiabilityPerMarket()); superstockagefancymatchFany.setActive(0);
											 * superstockagefancymatchFany.setStop(true);
											 * 
											 * superstockagefancymatchFany.setSupermasterid(usersession.getId().toString
											 * ()); exsupermasterMatchFancyRepo.save(superstockagefancymatchFany);
											 * responseBean.setType("success");
											 * responseBean.setMessage("Fancy Removed successfully");
											 * responseBean.setTitle("success"); return new
											 * ResponseEntity<Object>(responseBean,HttpStatus.OK);
											 * 
											 * }
											 * 
											 * } else{
											 * 
											 * 
											 * superstockagefancymatchFany.setActive(1);
											 * superstockagefancymatchFany.setStop(false);
											 * exsupermasterMatchFancyRepo.delete(superstockagefancymatchFany);
											 * responseBean.setType("success");
											 * responseBean.setMessage("Fancy added successfully");
											 * responseBean.setTitle("success"); return new
											 * ResponseEntity<Object>(responseBean,HttpStatus.OK); } }catch(Exception
											 * e){ e.printStackTrace(); }
											 * 
											 * }
											 * 
											 * return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
											 */
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * public void setInPlay(@RequestParam Integer sportid){ ArrayList<Market>
	 * eventList = new ArrayList<>(); HttpSession session = request.getSession();
	 * EXUser usersession = (EXUser) session.getAttribute("user");
	 * if(usersession!=null){ eventList =
	 * marketDao.getinPlayMatches(sportid,usersession.getSupermasterid());
	 * 
	 * if(!eventList.isEmpty()){ ArrayList<EventRates> list =
	 * eventService.getEventRatesListInplay(eventList,"inplay");
	 * 
	 * if(!list.isEmpty()){ for(EventRates event : list){
	 * if(event.getInplay()==true){ Market market =
	 * eventMarketRepository.findBymarketid(event.getMarketid()); if(market!=null){
	 * market.setIspause(false); market.setPauseByAdmin(false);
	 * eventMarketRepository.save(market); } } } } } } }
	 */

	/*
	 * @RequestMapping(value = "/getAdminMatchFancy",method = RequestMethod.GET)
	 * public ResponseEntity<String> getAdminMatchFancy(@RequestParam String
	 * eventid,@RequestParam String type,@RequestParam String marketid) throws
	 * JSONException, IOException{ HttpSession session = request.getSession(true);
	 * EXUser usersession =(EXUser) session.getAttribute("user"); RestTemplate
	 * restTemplate = new RestTemplate(); // String lotusresultresult =
	 * restTemplate.getForObject(exglobal.toConst.getLotusfancyurl()+"/"+eventid+"/"
	 * +marketid, String.class); //JSONObject json = new
	 * JSONObject(lotusresultresult);
	 * 
	 * JSONObject fancyjson =null; JSONArray arry1 = new JSONArray(); JSONObject
	 * res1 = new JSONObject(); ArrayList<MatchFancy> mf =
	 * matchFancyResult.findByEventid(Integer.parseInt(eventid));
	 * 
	 * if(mf.size()>0){
	 * 
	 * JSONObject statusjson = new JSONObject(); JSONObject status1 = new
	 * JSONObject();
	 * 
	 * statusjson.put("statusCode", "0"); statusjson.put("statusDesc", "Success");
	 * status1.put("status", statusjson); JSONArray array2 = new JSONArray();
	 * 
	 * res1.put("result", array2); res1.put("status", statusjson);
	 * 
	 * }else{ return new ResponseEntity<String>(HttpStatus.NO_CONTENT); }
	 * 
	 * 
	 * ArrayList<EXMinMaxBet> bets = (ArrayList<EXMinMaxBet>) maxbetRepo.findAll();
	 * ArrayList<MatchFancy> list =
	 * matchFancyResult.findByEventidAndIsActive(Integer.parseInt(eventid),true);
	 * JSONArray resultnew = new JSONArray(); DecimalFormat df = new
	 * DecimalFormat("#0.00"); String fancyname ="";
	 * 
	 * 
	 * for(MatchFancy bean :list){ //JSONArray jsonarray = new
	 * JSONArray(devbean.getOdds());
	 * 
	 * 
	 * String json = new
	 * String(Files.readAllBytes(Paths.get("Daimond"+"/"+eventid+".json")),
	 * StandardCharsets.UTF_8); JSONArray lotusmatchjsonarray= new JSONArray();
	 * if(json!=null){ lotusmatchjsonarray= new JSONArray(json.toString()); }
	 * 
	 * 
	 * JSONObject res = null; JSONArray array2 = new JSONArray();
	 * 
	 * for(int i=0;i<lotusmatchjsonarray.length();i++) {
	 * 
	 * 
	 * JSONObject obj = lotusmatchjsonarray.getJSONObject(i);
	 * 
	 * 
	 * JSONObject runnersjson = new JSONObject(); JSONArray backarray = new
	 * JSONArray(); JSONArray layarray = new JSONArray(); JSONArray runnerarray =
	 * new JSONArray();
	 * 
	 * res = new JSONObject();
	 * if(lotusmatchjsonarray.getJSONObject(i).getString("provider").
	 * equalsIgnoreCase(EXConstants.fancy)) res.put("id",
	 * obj.getString("id").split("-")[1].split("\\.")[0]);
	 * res.put("fancyid",obj.getString("id")); res.put("name",
	 * obj.getString("name")); if(obj.has("statusLabel")){
	 * if(obj.getString("statusLabel").equalsIgnoreCase(EXConstants.Suspended)){
	 * 
	 * JSONObject backjson = new JSONObject();
	 * backjson.put("line",EXConstants.Suspended); backjson.put("price",
	 * EXConstants.Suspended); backjson.put("size", null); JSONObject layjson = new
	 * JSONObject(); layjson.put("line",EXConstants.Suspended); layjson.put("price",
	 * EXConstants.Suspended); layjson.put("size", null); backarray.put(backjson);
	 * layarray.put(layjson);
	 * 
	 * res.put("back",backarray); res.put("lay",layarray); }else
	 * if(obj.getString("statusLabel").equalsIgnoreCase(EXConstants.BallRunning)){
	 * JSONObject backjson = new JSONObject();
	 * backjson.put("line",EXConstants.BallRunning); backjson.put("price",
	 * EXConstants.BallRunning); backjson.put("size", null); JSONObject layjson =
	 * new JSONObject(); layjson.put("line",EXConstants.BallRunning);
	 * layjson.put("price", EXConstants.BallRunning); layjson.put("size", null);
	 * backarray.put(backjson); layarray.put(layjson);
	 * 
	 * res.put("back",backarray); res.put("lay",layarray); } else{ res.put("back",
	 * new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")));
	 * res.put("lay", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay"))); } }
	 * 
	 * else{ res.put("back", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("back")));
	 * res.put("lay", new JSONArray(new
	 * JSONArray(obj.getString("runners")).getJSONObject(0).getString("lay"))); }
	 * 
	 * runnerarray.put(res); runnersjson.put("runners", runnerarray);
	 * runnersjson.put("name", obj.getString("name"));
	 * runnersjson.put("provider","FANCY");
	 * runnersjson.put("id",obj.getString("id")); runnersjson.put("oddsType",
	 * "HAAR_JEET"); if(obj.has("statusLabel")){ runnersjson.put("statusLabel",
	 * obj.getString("statusLabel")); runnersjson.put("status",
	 * obj.getString("statusLabel")); }else{ runnersjson.put("statusLabel", "");
	 * runnersjson.put("status", "OPEN"); }
	 * 
	 * runnersjson.put("mtype", "INNINGS_RUNS");
	 * 
	 * runnersjson.put("btype", "LINE"); runnersjson.put("eventTypeId", eventid);
	 * runnersjson.put("inplay", true);
	 * 
	 * array2.put(runnersjson);
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * { for(int i=0;i<array2.length();i++) { JSONObject obj =
	 * array2.getJSONObject(i);
	 * if(obj.getString("id").equalsIgnoreCase(bean.getFancyid())){
	 * obj.put("isplay", bean.getIsplay()); ArrayList<UserLiability> usersLiability
	 * = null;
	 * 
	 * if(usersession.getUsertype()==4){ usersLiability =
	 * liabilityRepository.findByFancyidAndAdminidAndIsactiveAndType(obj.getString(
	 * "id"),usersession.getId().toString(),true,"Fancy"); }else{ usersLiability =
	 * liabilityRepository.findByFancyidAndAppidAndIsactiveAndType(obj.getString(
	 * "id"),usersession.getAppid(),true,"Fancy"); } UserLiability user = new
	 * UserLiability(); String matchname = "";
	 * 
	 * for(int j=0;j<usersLiability.size();j++){
	 * if(matchname.equals(usersLiability.get(j).getFancyid())){ UserLiability user2
	 * = usersLiability.get(j); if(type.equals("1")){
	 * if(usersession.getUsertype()==4){
	 * user2.setPnl1(Math.round((user2.getPnl1().doubleValue()*(user2.
	 * getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user2.setLiability(Math.round((user2.getLiability().doubleValue()*(user2.
	 * getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==1){
	 * user2.setPnl1(Math.round((user2.getPnl1().doubleValue()*(user2.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user2.setLiability(Math.round((user2.getLiability().doubleValue()*(user2.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==0){
	 * user2.setPnl1(Math.round((user2.getPnl1().doubleValue()*(user2.
	 * getSupermasterpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user2.setLiability(Math.round((user2.getLiability().doubleValue()*(user2.
	 * getSupermasterpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==5){
	 * user2.setPnl1(Math.round((user2.getPnl1().doubleValue()*(user2.
	 * getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user2.setLiability(Math.round((user2.getLiability().doubleValue()*(user2.
	 * getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0); }else{
	 * user2.setPnl1(Math.round((user2.getPnl1().doubleValue()*(user2.
	 * getDealerpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user2.setLiability(Math.round((user2.getLiability().doubleValue()*(user2.
	 * getDealerpartnership().doubleValue()/100.0f))*100.0)/100.0); }
	 * user.setPnl1(user.getPnl1()+user2.getPnl1());
	 * user.setLiability(user.getLiability()+user2.getLiability()); }else{
	 * user.setPnl1(user.getPnl1()+user2.getPnl1());
	 * user.setLiability(user.getLiability()+user2.getLiability()); } }else{
	 * if(type.equals("1")){ if(user.getUserid()!=null){
	 * 
	 * user.setPnl1(0.00-user.getPnl1());
	 * user.setPnl1(Double.valueOf(df.format(user.getPnl1())));
	 * if(user.getLiability()!=null && user.getPnl1()>0.00){
	 * if(user.getLiability()>0){
	 * user.setLiability(user.getLiability()-(user.getLiability()*2)); } }
	 * 
	 * obj.put("netexpo", user.getPnl1()); obj.put("liability",
	 * user.getLiability()); } user = new UserLiability(); matchname =
	 * usersLiability.get(j).getFancyid(); user = usersLiability.get(j);
	 * if(usersession.getUsertype()==4){
	 * user.setPnl1(Math.round((user.getPnl1().doubleValue()*(user.
	 * getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user.setLiability(Math.round((user.getLiability().doubleValue()*(user.
	 * getAdminpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==1){
	 * user.setPnl1(Math.round((user.getPnl1().doubleValue()*(user.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user.setLiability(Math.round((user.getLiability().doubleValue()*(user.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==0){
	 * user.setPnl1(Math.round((user.getPnl1().doubleValue()*(user.
	 * getSupermasterpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user.setLiability(Math.round((user.getLiability().doubleValue()*(user.
	 * getSupermasterpartnership().doubleValue()/100.0f))*100.0)/100.0); }else
	 * if(usersession.getUsertype()==5){
	 * user.setPnl1(Math.round((user.getPnl1().doubleValue()*(user.
	 * getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user.setLiability(Math.round((user.getLiability().doubleValue()*(user.
	 * getSubadminpartnership().doubleValue()/100.0f))*100.0)/100.0); }else{
	 * user.setPnl1(Math.round((user.getPnl1().doubleValue()*(user.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0);
	 * user.setLiability(Math.round((user.getLiability().doubleValue()*(user.
	 * getMasterpartnership().doubleValue()/100.0f))*100.0)/100.0); } }else{
	 * if(user.getUserid()!=null){
	 * 
	 * if(user.getLiability()!=null){ if(user.getLiability()>0 &&
	 * user.getPnl1()>0.00){
	 * user.setLiability(user.getLiability()-(user.getLiability()*2)); } }
	 * obj.put("netexpo", user.getPnl1()); obj.put("liability",
	 * user.getLiability());
	 * 
	 * } user = new UserLiability(); matchname = usersLiability.get(j).getFancyid();
	 * user = usersLiability.get(j); } } } if(user.getLiability()!=null){
	 * if(user.getLiability()>0 && user.getPnl1()>0.00){
	 * user.setLiability(user.getLiability()-(user.getLiability()*2)); } }
	 * obj.put("netexpo", user.getPnl1()); obj.put("liability",
	 * user.getLiability());
	 * 
	 * JSONArray runners= obj.getJSONArray("runners"); obj.put("statusLabel",
	 * "Ball Running"); obj.put("mypt", type);
	 * 
	 * for(int j=0; j<runners.length();j++) { JSONObject runnerobj =
	 * runners.getJSONObject(j); {
	 * 
	 * if(runnerobj.getString("name").toString().equalsIgnoreCase(bean.getName())){
	 * 
	 * if(bets.size()>1){ obj.put("minbet", bets.get(1).getMinbet());
	 * obj.put("maxbet", bets.get(1).getMaxbet()); }
	 * if(!fancyname.equalsIgnoreCase(bean.getFancyid())){ resultnew.put(obj);
	 * fancyname = bean.getFancyid(); } } } } } } } } return new
	 * ResponseEntity<String>(resultnew.toString(),HttpStatus.OK); }
	 */

	/*
	 * @RequestMapping(value = "/getMatchOdds",method = RequestMethod.GET) public
	 * ResponseEntity<String> getMatchOdds(@RequestParam String
	 * eventid,@RequestParam String sportid) throws JSONException{
	 * 
	 * 
	 * HttpSession session = request.getSession(); EXUser usersession = (EXUser)
	 * session.getAttribute("user"); DecimalFormat df = new DecimalFormat("#0.00");
	 * Market market = null; if(usersession!=null){
	 * 
	 * if(usersession.getUsertype() ==3){ EXUserMatchEntry usermatch =
	 * exusermatchentryRepo.findByUseridAndEventid(usersession.getUserid(),
	 * Integer.parseInt(eventid)); if(usermatch==null){ usermatch = new
	 * EXUserMatchEntry(); usermatch.setEventid(Integer.parseInt(eventid));
	 * usermatch.setUserid(usersession.getUserid());
	 * exusermatchentryRepo.save(usermatch); } }
	 * 
	 * if(usersession.getUsertype()==0){ Market bean1 =
	 * eventMarketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * eventid),true,true); if(bean1!=null) {
	 * 
	 * market = bean1; } }else if(usersession.getUsertype()==5){ Market bean1 =
	 * eventMarketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * eventid),true,true); if(bean1!=null) {
	 * 
	 * market =bean1; } }else if(usersession.getUsertype()==4){ Market bean1 =
	 * eventMarketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * eventid),true,true); if(bean1!=null) {
	 * 
	 * market =bean1; } }else{
	 * if(usersession.getSupermasterid().equalsIgnoreCase("0")){ Market bean1 =
	 * eventMarketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * eventid),true,true); if(bean1!=null) {
	 * 
	 * market =bean1; } }else{ market =
	 * eventMarketRepository.findByEventidAndStatusAndIsactive(Integer.parseInt(
	 * eventid),true,true); }
	 * 
	 * } //market =
	 * eventMarketRepository.findByEventidAndStatusAndIsactive(Integer.parseInt(
	 * eventid),true,true); }
	 * 
	 * 
	 * TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String
	 * authType) -> true;
	 * 
	 * SSLContext sslContext = null; try { sslContext =
	 * org.apache.http.ssl.SSLContexts.custom() .loadTrustMaterial(null,
	 * acceptingTrustStrategy) .build(); } catch (KeyManagementException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (NoSuchAlgorithmException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (KeyStoreException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
	 * 
	 * CloseableHttpClient httpClient = HttpClients.custom()
	 * .setSSLSocketFactory(csf) .build();
	 * 
	 * HttpComponentsClientHttpRequestFactory requestFactory = new
	 * HttpComponentsClientHttpRequestFactory();
	 * 
	 * requestFactory.setHttpClient(httpClient);
	 * 
	 * RestTemplate restTemplate = new RestTemplate(requestFactory); JSONObject
	 * matchOdds = new JSONObject(); JSONArray result = new JSONArray();
	 * 
	 * if(market !=null){
	 * 
	 * String marketid =market.getMarketid();
	 * 
	 * 
	 * //EXdevelopersOdss devOdddsbean = new EXdevelopersOdss(); // devOdddsbean
	 * =exdevelopersMatchoddsRepo.findByMarketidAndType(marketid,"Match Odds");
	 * 
	 * String resultstr = restTemplate.getForObject(
	 * "http://ec2-13-235-211-104.ap-south-1.compute.amazonaws.com:8283/bm-exchange-middle-layer/get-market-odds?marketId="
	 * +marketid, String.class); JSONObject json = new JSONObject(resultstr);
	 * if(json.getInt("statusCode")== 200){ JSONObject data
	 * =json.getJSONObject("data"); //System.out.println("data==>"+data); JSONArray
	 * response = new JSONArray(); response.put(data);
	 * if(usersession.getBhaotype()==2){ for(int i = 0;i<response.length();i++){
	 * JSONObject odds = new JSONObject(); odds.put("id",
	 * response.getJSONObject(i).getString("marketId")); odds.put("name",
	 * "Match Odds"); odds.put("betfair", true); JSONArray runner = new JSONArray();
	 * BigDecimal back1= new BigDecimal("0.0"); BigDecimal back2 =new
	 * BigDecimal("0.0"); BigDecimal back3 =new BigDecimal("0.0"); BigDecimal lay1=
	 * new BigDecimal("0.0"); BigDecimal lay2=new BigDecimal("0.0"); BigDecimal
	 * lay3=new BigDecimal("0.0"); BigDecimal team1 =new BigDecimal("0.0");
	 * BigDecimal team2 =new BigDecimal("0.0"); BigDecimal team3 =new
	 * BigDecimal("0.0"); BigDecimal back4 =new BigDecimal("0.0"); JSONArray
	 * runners1 =null; JSONArray backrunner1 =null; JSONArray layrunner1 =null;
	 * JSONArray back =null;
	 * 
	 * JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
	 * for(int j=0;j<runners.length();j++){ back = new JSONArray(); JSONArray lay =
	 * new JSONArray(); JSONObject ex =
	 * runners.getJSONObject(j).getJSONObject("ex");
	 * if(ex.getJSONArray("availableToBack").length()!=0 &&
	 * ex.getJSONArray("availableToBack")!=null){ JSONArray availableBack =
	 * ex.getJSONArray("availableToBack"); ; String bhaotype =
	 * "0.0"+usersession.getBhaotype(); for(int b=0;b<availableBack.length();b++){
	 * JSONObject backObject = new JSONObject(); BigDecimal bBigDecimal = new
	 * BigDecimal("0.01"); BigDecimal fixedval = new BigDecimal("0.10"); BigDecimal
	 * backsize = new BigDecimal(availableBack.getJSONObject(b).getString("price"));
	 * if(j==0){ if(b == 0){ back1 =backsize; } } else if(j==1){ if(b == 0){ back2
	 * =backsize; } } else if(j==2){ if(b == 0){ back3 =backsize; } }
	 * 
	 * backObject.put("price", backsize); backObject.put("size",
	 * availableBack.getJSONObject(b).get("size")); back.put(backObject); } }
	 * 
	 * if(ex.getJSONArray("availableToLay").length()!=0 &&
	 * ex.getJSONArray("availableToLay")!=null){ JSONArray availableLay =
	 * ex.getJSONArray("availableToLay");
	 * 
	 * for(int l = 0;l<availableLay.length();l++){ JSONObject layObject = new
	 * JSONObject(); layObject.put("price",
	 * availableLay.getJSONObject(l).get("price")); layObject.put("size",
	 * availableLay.getJSONObject(l).get("size")); BigDecimal laysize = new
	 * BigDecimal(availableLay.getJSONObject(l).getString("price")); if(j==0){ if(l
	 * == 0){ lay1 =laysize; } } else if(j==1){ if(l == 0){ lay2 =laysize; } }
	 * 
	 * else if(j==2){ if(l == 0){ lay3 =laysize; } }
	 * 
	 * lay.put(layObject); } }
	 * 
	 * if(j==0){ team1 = back1.add(lay1);
	 * 
	 * }
	 * 
	 * if(j==1){ team2 = back2.add(lay2);
	 * 
	 * }
	 * 
	 * if(j==2){ team3 = back3.add(lay3);
	 * 
	 * }
	 * 
	 * JSONObject selectionid = new JSONObject(); selectionid.put("id",
	 * runners.getJSONObject(j).getString("selectionId")); selectionid.put("name",
	 * selectionIdRepository.findBySelectionidAndMarketid(Integer.parseInt(runners.
	 * getJSONObject(j).getString("selectionId")),marketid).getRunnerName());
	 * selectionid.put("back", back); selectionid.put("lay",lay);
	 * runner.put(selectionid); } BigDecimal back5 = new BigDecimal("0.0");
	 * BigDecimal back6 = new BigDecimal("0.0");
	 * 
	 * if(team2.compareTo(team1)==1 && team2.compareTo(team3)==1){
	 * 
	 * back4 =back1.subtract(new BigDecimal("0.01"));
	 * 
	 * } else if(team1.compareTo(team2) ==1 && team1.compareTo(team3)==1){ back5
	 * =back2.subtract(new BigDecimal("0.01")); } else {
	 * 
	 * back6 =back3.subtract(new BigDecimal("0.01")); }
	 * 
	 * 
	 * for(int k =0; k<runner.length();k++){ if(back4.compareTo(new
	 * BigDecimal("0.0")) == 1){
	 * 
	 * if(k==0){
	 * 
	 * runners1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
	 * for(int m =0; m< runners1.length(); m++){ if(m==0){
	 * runner.getJSONObject(k).getJSONArray("back").getJSONObject(m).put("price",
	 * back4); } } } else{ backrunner1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
	 * layrunner1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToLay");
	 * for(int m =0; m< backrunner1.length(); m++){ if(m==1){
	 * 
	 * runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",
	 * backrunner1.getJSONObject(m).getString("price"));
	 * 
	 * } }
	 * 
	 * for(int m =0; m< layrunner1.length(); m++){ if(m==1){
	 * 
	 * runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price",
	 * layrunner1.getJSONObject(m).getString("price"));
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * 
	 * }else{ if(k==1){
	 * 
	 * runners1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
	 * for(int m =0; m< runners1.length(); m++){ if(m==0){
	 * 
	 * if(back5.compareTo(new BigDecimal("0.0")) ==1){
	 * runners1.getJSONObject(m).put("price", back5);
	 * runner.getJSONObject(k).getJSONArray("back").getJSONObject(m).put("price",
	 * back5); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }else{ backrunner1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
	 * layrunner1=
	 * runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToLay");
	 * for(int m =0; m< backrunner1.length(); m++){ if(m==1){
	 * 
	 * runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price",
	 * backrunner1.getJSONObject(m).getString("price")); } }
	 * 
	 * for(int m =0; m< layrunner1.length(); m++){ if(m==1){
	 * 
	 * runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price",
	 * layrunner1.getJSONObject(m).getString("price"));
	 * 
	 * } } }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * JSONObject event = new JSONObject(); JSONObject minmax = new JSONObject();
	 * event.put("name", market.getMatchname()); event.put("id",
	 * market.getEventid()); Event evnt =
	 * eventRepo.findByeventid(market.getEventid().toString()); if(eventid!=null){
	 * minmax.put("minbet", evnt.getMinbet()); minmax.put("maxbet",
	 * evnt.getMaxbet()); } odds.put("event",event); odds.put("minmax",minmax);
	 * odds.put("runners", runner); result.put(odds);
	 * 
	 * } }else{ for(int i = 0;i<response.length();i++){ JSONObject odds = new
	 * JSONObject(); odds.put("id",
	 * response.getJSONObject(i).getString("marketId")); odds.put("name",
	 * "Match Odds"); odds.put("betfair", true); JSONArray runner = new JSONArray();
	 * 
	 * 
	 * JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
	 * 
	 * for(int j=0;j<runners.length();j++){ JSONObject ex =
	 * runners.getJSONObject(j).getJSONObject("ex"); JSONArray back = new
	 * JSONArray(); JSONArray lay = new JSONArray();
	 * if(ex.getJSONArray("availableToBack").length()!=0 &&
	 * ex.getJSONArray("availableToBack")!=null){ JSONArray availableBack =
	 * ex.getJSONArray("availableToBack");
	 * 
	 * for(int b=0;b<availableBack.length();b++){ JSONObject backObject = new
	 * JSONObject();
	 * 
	 * backObject.put("price", availableBack.getJSONObject(b).getString("price"));
	 * //backObject.put("price", availableBack.getJSONObject(b).get("price"));
	 * backObject.put("size", availableBack.getJSONObject(b).get("size"));
	 * back.put(backObject); }
	 * 
	 * }
	 * 
	 * if(ex.getJSONArray("availableToLay").length()!=0 &&
	 * ex.getJSONArray("availableToLay")!=null){ JSONArray availableLay =
	 * ex.getJSONArray("availableToLay");
	 * 
	 * for(int l = 0;l<availableLay.length();l++){ JSONObject layObject = new
	 * JSONObject(); layObject.put("price",
	 * availableLay.getJSONObject(l).get("price")); layObject.put("size",
	 * availableLay.getJSONObject(l).get("size")); lay.put(layObject); } }
	 * JSONObject selectionid = new JSONObject(); selectionid.put("id",
	 * runners.getJSONObject(j).getString("selectionId")); selectionid.put("name",
	 * selectionIdRepository.findBySelectionidAndMarketid(Integer.parseInt(runners.
	 * getJSONObject(j).getString("selectionId")),marketid).getRunnerName());
	 * selectionid.put("back", back); selectionid.put("lay",lay);
	 * runner.put(selectionid); }
	 * 
	 * JSONObject event = new JSONObject(); event.put("name",
	 * market.getMatchname()); event.put("id", market.getEventid());
	 * odds.put("event",event); odds.put("runners", runner); JSONObject minmax = new
	 * JSONObject(); Event evnt =
	 * eventRepo.findByeventid(market.getEventid().toString()); if(eventid!=null){
	 * minmax.put("minbet", evnt.getMinbet()); minmax.put("maxbet",
	 * evnt.getMaxbet()); } odds.put("minmax",minmax); result.put(odds); } } }
	 * 
	 * 
	 * } matchOdds.put("result", result);
	 * 
	 * //}
	 * 
	 * 
	 * if(matchOdds.length()==0){ return new
	 * ResponseEntity<String>(HttpStatus.NO_CONTENT); }else{ return new
	 * ResponseEntity<String>(matchOdds.toString(),HttpStatus.OK); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/getMatchFancyApi", method = RequestMethod.GET)
	public ResponseEntity<Object> getMatchFancyApi(@RequestParam String eventid) throws JSONException {
		ResponseBean responseBean = new ResponseBean();
		Market marketbean = eventMarketRepository.findByStatusAndEventidAndMarketname(true, Integer.parseInt(eventid),
				"Match Odds");

		JSONObject res1 = new JSONObject();

		JSONObject statusjson = new JSONObject();
		JSONObject status1 = new JSONObject();

		statusjson.put("statusCode", "0");
		statusjson.put("statusDesc", "Success");
		status1.put("status", statusjson);

		if (marketbean != null) {
			/*
			 * 
			 * EXMinMaxBet bets = maxbetRepo.findByType("Fancy"); ArrayList<MatchFancy> list
			 * = new ArrayList<>(); list =
			 * matchFancyResult.findByeventidAndActive(Integer.parseInt(eventid),1);
			 * 
			 * 
			 * JSONArray array2 = new JSONArray();
			 * 
			 * if(fancylist.size()>0){
			 * 
			 * res1.put("result", array2); res1.put("status", statusjson); return new
			 * ResponseEntity<Object>(res1.toString(),HttpStatus.OK); }else{ return new
			 * ResponseEntity<Object>(HttpStatus.NO_CONTENT); }
			 * 
			 */}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);

	}

	

	@RequestMapping(value = "/minMaxBetSetAmount", method = RequestMethod.POST)
	public ResponseEntity<Object> minMaxBetSetAmount(@RequestBody ExMinMaxBetSetAmount minmaxbet) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			if (minmaxbet.getAmount() < 1) {
				responseBean.setType("error");
				responseBean.setMessage("Please Enter Data");
				responseBean.setTitle("Oops...");
			} else {
				if (exminmaxsetamountRepo.findByAmountAndType(minmaxbet.getAmount(), minmaxbet.getType()) == null) {
					exminmaxsetamountRepo.save(minmaxbet);
					responseBean.setMessage("Set Amount Successfully!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					responseBean.setMessage("This Amount is already set for this category!");
					responseBean.setType("error");
					responseBean.setTitle("Oops...");
				}

			}

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMinMaxBetSetAmount", method = RequestMethod.GET)
	public ResponseEntity<Object> getMinMaxBetSetAmount(@RequestParam String type) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMinMaxBetSetAmount> list = new ArrayList<ExMinMaxBetSetAmount>();
		if (usersession != null) {
			list = exminmaxsetamountRepo.findByType(type);

		}

		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDefaultMinMaxBetSetAmount", method = RequestMethod.GET)
	public ResponseEntity<Object> getDefaultMinMaxBetSetAmount() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMinMaxBetSetAmount> list = new ArrayList<ExMinMaxBetSetAmount>();
		ArrayList<ExMinMaxBetSetAmount> minmax = new ArrayList<ExMinMaxBetSetAmount>();
		if (usersession != null) {
			minmax = exminmaxsetamountRepo.findByIsActive(true);
		}

		return new ResponseEntity<Object>(minmax, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteMinMaxBetSetAmount", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteMinMaxBetSetAmount(@RequestParam Integer id) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMinMaxBetSetAmount> list = new ArrayList<ExMinMaxBetSetAmount>();
		if (usersession != null) {
			ExMinMaxBetSetAmount bean = exminmaxsetamountRepo.findById(id);
			exminmaxsetamountRepo.delete(bean);
			responseBean.setMessage("Deleted Successfully!");
			responseBean.setType("success");
			responseBean.setTitle("Success");

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateMinMaxBetdelayEvent", method = RequestMethod.POST)
	public ResponseEntity<Object> updateMinMaxBetdelayEvent(@RequestParam Integer eventid, @RequestParam String type,
			@RequestParam Integer amount) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		log.info("usersession: " + usersession);
		if (usersession != null) {
			Event bean = eventRepo.findByeventid(eventid);
			if (bean != null) {
				if (type.equalsIgnoreCase("minbet")) {
					bean.setMinbet(amount);
				} else if (type.equalsIgnoreCase("maxbet")) {
					bean.setMaxbet(amount);
				} else if (type.equalsIgnoreCase("betdelay")) {
					bean.setBetdelay(amount);
				} else if (type.equalsIgnoreCase("maxbetfancy")) {
					bean.setMaxbetfancy(amount);
				} else if (type.equalsIgnoreCase("betdelayfancy")) {
					bean.setBetdelayfancy(amount);
				} else if (type.equalsIgnoreCase("maxbetBookmaker")) {
					bean.setMaxbetBookmaker(amount);
				}else if (type.equalsIgnoreCase("playermaxbetfancy")) {
					bean.setPlayermaxbetfancy(amount);
				}
				else if (type.equalsIgnoreCase("betdelayBookmaker")) {
					bean.setBetdelayBookmaker(amount);
				}else if (type.equalsIgnoreCase("maxbetBookmaker2")) {
					bean.setMaxbetBookmaker2(amount);
				}
				
				
				
				eventRepo.save(bean);
				responseBean.setMessage("Chnaged Successfully!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			}

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/userMatchActiveUser", method = RequestMethod.GET)
	public ResponseEntity<Object> userMatchActiveUser(@RequestParam String eventid) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		ArrayList<EXUserMatchEntry> list = (ArrayList<EXUserMatchEntry>) exusermatchentryRepo
				.findByEventid(Integer.parseInt(eventid));
		if (list.size() > 0) {
			hm.put("activeuser", list.size());

		} else {
			hm.put("activeuser", 0);
		}
		Integer betusercount = (int) appchargeRepo.countByEventid(eventid);

		hm.put("betuser", betusercount);

		return new ResponseEntity<Object>(hm, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCricketExchnageScorreId", method = RequestMethod.POST)
	public ResponseEntity<Object> updateCricketExchnageScorreId(@RequestParam Integer eventid,
			@RequestParam String cricexchageid) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		try {
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				if (bean != null) {
					bean.setCricexchageId(cricexchageid);
					eventRepo.save(bean);
					responseBean.setMessage("Score id Chnaged Successfully!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/macthlist", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> macthlist() {

		RestTemplate restTemplate = new RestTemplate();
		String cricexchresult = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/liveMatches.json",
				String.class);

		return new ResponseEntity<Object>(cricexchresult.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/tennisFootballScore", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> tennisFootballScore(@RequestParam String eventids) {

		RestTemplate restTemplate = new RestTemplate();
		String socre = restTemplate
				.getForObject("https://ips.betfair.com/inplayservice/v1/scores?_ak=nzIFcwyWhrlwYMrh&alt=json&eventIds="
						+ eventids + "&locale=en_GB&productType=EXCHANGE&regionCode=UK", String.class);

		return new ResponseEntity<Object>(socre.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/getScore", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getScore(@RequestParam("key") String key) throws JSONException {

		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>();
		RestTemplate restTemplate = new RestTemplate();
		DecimalFormat df = new DecimalFormat("#0.00");
		try {

			String p1 = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/sV1/" + key + "/p1.json",
					String.class);
			String p2 = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/sV1/" + key + "/p2.json",
					String.class);
			String live = restTemplate
					.getForObject("https://cricket-exchange.firebaseio.com/liveMatches/" + key + ".json", String.class);

			if (key != "undefined") {
				if (live != "null") {

					JSONObject obj1 = new JSONObject(p1.toString());
					JSONObject obj2 = new JSONObject(p2.toString());

					JSONObject liveobj = new JSONObject(live);

					if (liveobj.getString("type").equalsIgnoreCase("1")) {

						if (liveobj.getString("status").equalsIgnoreCase("2")
								|| liveobj.getString("status").equalsIgnoreCase("1")) {
							if (liveobj.getString("inning").equalsIgnoreCase("1")
									|| liveobj.getString("inning").equalsIgnoreCase("2")) {
								Integer balldone2 = liveobj.getInt("ballsdone");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone")));
								liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone", numberOfOvers);
							}
							if (liveobj.getString("inning").equalsIgnoreCase("2")) {
								Integer balldone2 = liveobj.getInt("ballsdone2");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score2")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone2")));
								// liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone2", numberOfOvers);
							}
							if (liveobj.getString("inning").equalsIgnoreCase("3")) {
								Integer balldone2 = liveobj.getInt("ballsdone");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone")));
								liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone", numberOfOvers);
							}
							if (liveobj.getString("inning").equalsIgnoreCase("4")) {
								Integer balldone2 = liveobj.getInt("ballsdone");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone")));
								liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone", numberOfOvers);
							}
							if (liveobj.getString("inning").equalsIgnoreCase("6")) {
								Integer balldone2 = liveobj.getInt("ballsdone");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone")));
								liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone", numberOfOvers);
							}

						}
					} else {
						if (liveobj.getString("status").equalsIgnoreCase("2")
								|| liveobj.getString("status").equalsIgnoreCase("1")) {
							if (liveobj.getString("inning").equalsIgnoreCase("1")
									|| liveobj.getString("inning").equalsIgnoreCase("2")) {
								Integer balldone2 = liveobj.getInt("ballsdone");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone")));
								liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone", numberOfOvers);
							}
							if (liveobj.getString("inning").equalsIgnoreCase("2")) {
								Integer balldone2 = liveobj.getInt("ballsdone2");
								String round = String.valueOf(Math.round(balldone2 / 6));
								String mod = String.valueOf(balldone2 % 6);
								double numberOfOvers = Double.valueOf(round + "." + mod);

								String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score2")) * 6
										/ Double.valueOf(liveobj.getInt("ballsdone2")));
								// liveobj.put("currentrunrate", currentrunrate);
								liveobj.put("ballsdone2", numberOfOvers);
							}

						}
					}

					if (obj1.getInt("p1b") != 0) {
						Integer ballface = obj1.getInt("p1b");
						Integer run = obj1.getInt("p1r");
						String strikerate = df.format(Double.valueOf(run) * 100 / Double.valueOf(ballface));

						obj1.put("p1s", strikerate);

					}
					if (obj1.getInt("p1b") != 0) {
						Integer ballface = obj1.getInt("p2b");
						Integer run = obj1.getInt("p2r");
						String strikerate = df.format(Double.valueOf(run) * 100 / Double.valueOf(ballface));

						obj1.put("p2s", strikerate);

					}

					JSONObject obj3 = new JSONObject(live.toString());
					if (obj2.has("c2")) {
						obj2.remove("c2");
					}

					/*
					 * result.put("p1",obj1.toString()); result.put("p2",obj2.toString());
					 * result.put("live",obj3.toString()); /result.put("live",live.toString());
					 */
					list.add(obj1.toString());
					list.add(obj2.toString());
					list.add(liveobj.toString());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(list.toString(), HttpStatus.OK);

	}



	@RequestMapping(value = "/updateDimondSkyFancy", method = RequestMethod.POST)
	public ResponseEntity<Object> updateDimondSkyFancy(@RequestParam String skyfancyid,
			@RequestParam String daimondfancyid) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXDimondSkyFancyMatch bean = exdaimondskyFancyRepo.findByDaimondfancyidAndSkyfancyid(daimondfancyid,
					skyfancyid);
			if (bean == null) {
				bean = new EXDimondSkyFancyMatch();
				bean.setSkyfancyid(skyfancyid);
				bean.setDaimondfancyid(daimondfancyid);
				exdaimondskyFancyRepo.save(bean);
				responseBean.setMessage("Facncy Match Successfully Done!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			}

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateFancyProvider/{eventId}/{fancyProvider}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateFancyProvider(@PathVariable Integer eventId,@PathVariable String fancyProvider) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
		Event bean = eventRepo.findByeventid(eventId);
		
			if (bean != null) {
				bean.setFancyprovider(fancyProvider);
				eventRepo.save(bean);
				responseBean.setMessage("Updated  Successfully Done!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			}

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "/updatefancyProvider",method = RequestMethod.POST)
	 * public ResponseEntity<Object> updatefancyProvider(@RequestParam Integer
	 * eventid,@RequestParam String fancyprovider){ HttpSession session =
	 * request.getSession(); ResponseBean responseBean = new ResponseBean(); EXUser
	 * usersession = (EXUser) session.getAttribute("user"); if(usersession!=null){
	 * ArrayList<ExMatchOddsBet> bets = betRepository.findByMatchidAndType(eventid,
	 * EXConstants.Fancy); if(bets.size()>0){ responseBean.setType("error");
	 * responseBean.setMessage("Please Set all fancy result first");
	 * responseBean.setTitle("Oops..."); }else{ Event bean
	 * =eventRepo.findByEventidAndStatus(eventid, true); if(bean!=null){
	 * bean.setFancyprovider(fancyprovider); eventRepo.save(bean);
	 * responseBean.setMessage("Fancy Provider Chnaged Successfully!");
	 * responseBean.setType("success"); responseBean.setTitle("Success"); }
	 * 
	 * } }
	 * 
	 * return new ResponseEntity<Object>(responseBean,HttpStatus.OK); }
	 */


	@RequestMapping(value = "/getInplayAdmin", method = RequestMethod.GET)
	public ResponseEntity<String> getInplayAdmin(@RequestParam Integer sportid) {

		ArrayList<EventRates> ratesList = new ArrayList<>();
		ArrayList<EventRates> inPlayList = new ArrayList<>();
		String json = null;
		JSONArray result = new JSONArray();
		JSONArray score = new JSONArray();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		JSONObject matchOdds = new JSONObject();
		ArrayList<Market> marketList = marketDao.getinPlayMatches(sportid, "1");
		DecimalFormat df = new DecimalFormat("#0.00");
		try {
			for (Market mrkt : marketList) {

				JSONObject jsonobj = new JSONObject();
				String sport = null;
				if (mrkt.getSportid() == 4) {
					sport = "Cricket";
				} else if (mrkt.getSportid() == 2) {
					sport = "Tennis";
				} else if (mrkt.getSportid() == 1) {
					sport = "Football";
				}

				json = new String(Files.readAllBytes(Paths.get(sport + "/" + mrkt.getEventid() + ".json")),
						StandardCharsets.UTF_8);

				JSONArray response = new JSONArray(json.toString());
				UserLiability userlib = userLibDao.getpnl(usersession.getUsertype(), mrkt.getMarketid(),
						usersession.getId().toString());

				

					for (int i = 0; i < response.length(); i++) {
						JSONObject odds = new JSONObject();
						odds.put("id", response.getJSONObject(i).getString("marketId"));
						odds.put("name", "Match Odds");
						odds.put("betfair", true);
						JSONArray runner = new JSONArray();
						BigDecimal back1 = new BigDecimal("0.0");
						BigDecimal back2 = new BigDecimal("0.0");
						BigDecimal back3 = new BigDecimal("0.0");
						BigDecimal lay1 = new BigDecimal("0.0");
						BigDecimal lay2 = new BigDecimal("0.0");
						BigDecimal lay3 = new BigDecimal("0.0");
						BigDecimal team1 = new BigDecimal("0.0");
						BigDecimal team2 = new BigDecimal("0.0");
						BigDecimal team3 = new BigDecimal("0.0");
						BigDecimal back4 = new BigDecimal("0.0");
						JSONArray runners1 = null;
						JSONArray backrunner1 = null;
						JSONArray layrunner1 = null;
						JSONArray back = null;

						JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
						for (int j = 0; j < runners.length(); j++) {
							back = new JSONArray();
							JSONArray lay = new JSONArray();
							JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");
							if (ex.getJSONArray("availableToBack").length() != 0
									&& ex.getJSONArray("availableToBack") != null) {
								JSONArray availableBack = ex.getJSONArray("availableToBack");

								if (availableBack.length() > 2) {
									for (int b = 0; b < availableBack.length(); b++) {
										JSONObject backObject = new JSONObject();
										BigDecimal bBigDecimal = new BigDecimal("0.01");
										BigDecimal fixedval = new BigDecimal("0.10");
										BigDecimal backsize = new BigDecimal(
												availableBack.getJSONObject(b).getString("price"));
										if (j == 0) {
											if (b == 0) {
												back1 = backsize;
											}
										} else if (j == 1) {
											if (b == 0) {
												back2 = backsize;
											}
										} else if (j == 2) {
											if (b == 0) {
												back3 = backsize;
											}
										}

										backObject.put("price", backsize);
										backObject.put("size", availableBack.getJSONObject(b).get("size"));
										back.put(backObject);
									}
								} else if (availableBack.length() == 2) {

									JSONObject backObject1 = new JSONObject();
									JSONObject backObject2 = new JSONObject();
									JSONObject backObject3 = new JSONObject();
									backObject1.put("price",
											new BigDecimal(availableBack.getJSONObject(0).getString("price")));
									backObject1.put("size",
											new BigDecimal(availableBack.getJSONObject(0).getString("size")));
									backObject2.put("price",
											new BigDecimal(availableBack.getJSONObject(1).getString("price")));
									backObject2.put("size",
											new BigDecimal(availableBack.getJSONObject(1).getString("size")));
									backObject3.put("price", 0.0);
									backObject3.put("size", 0.0);
									back.put(backObject1);
									back.put(backObject2);
									back.put(backObject3);
								} else if (availableBack.length() == 1) {

									JSONObject backObject1 = new JSONObject();
									JSONObject backObject2 = new JSONObject();
									JSONObject backObject3 = new JSONObject();
									backObject1.put("price",
											new BigDecimal(availableBack.getJSONObject(0).getString("price")));
									backObject1.put("size",
											new BigDecimal(availableBack.getJSONObject(0).getString("size")));
									backObject2.put("price", 0.0);
									backObject2.put("size", 0.0);
									backObject3.put("price", 0.0);
									backObject3.put("size", 0.0);
									back.put(backObject1);
									back.put(backObject2);
									back.put(backObject3);

								}

							} else {

								JSONObject backObject1 = new JSONObject();
								JSONObject backObject2 = new JSONObject();
								JSONObject backObject3 = new JSONObject();
								backObject1.put("price", 0.0);
								backObject1.put("size", 0.0);
								backObject2.put("price", 0.0);
								backObject2.put("size", 0.0);
								backObject3.put("price", 0.0);
								backObject3.put("size", 0.0);
								back.put(backObject1);
								back.put(backObject2);
								back.put(backObject3);
							}

							if (ex.getJSONArray("availableToLay").length() != 0
									&& ex.getJSONArray("availableToLay") != null) {
								JSONArray availableLay = ex.getJSONArray("availableToLay");

								if (availableLay.length() > .2) {
									for (int l = 0; l < availableLay.length(); l++) {
										JSONObject layObject = new JSONObject();
										layObject.put("price", availableLay.getJSONObject(l).get("price"));
										layObject.put("size", availableLay.getJSONObject(l).get("size"));
										BigDecimal laysize = new BigDecimal(
												availableLay.getJSONObject(l).getString("price"));
										if (j == 0) {
											if (l == 0) {
												lay1 = laysize;
											}
										} else if (j == 1) {
											if (l == 0) {
												lay2 = laysize;
											}
										}

										else if (j == 2) {
											if (l == 0) {
												lay3 = laysize;
											}
										}

										lay.put(layObject);
									}
								} else if (availableLay.length() == 2) {
									JSONObject layObject1 = new JSONObject();
									JSONObject layObject2 = new JSONObject();
									JSONObject layObject3 = new JSONObject();
									layObject1.put("price",
											new BigDecimal(availableLay.getJSONObject(0).getString("price")));
									layObject2.put("size",
											new BigDecimal(availableLay.getJSONObject(0).getString("size")));
									layObject3.put("price",
											new BigDecimal(availableLay.getJSONObject(1).getString("price")));
									layObject1.put("size",
											new BigDecimal(availableLay.getJSONObject(1).getString("price")));
									layObject2.put("price", 0.0);
									layObject3.put("size", 0.0);
									lay.put(layObject1);
									lay.put(layObject2);
									lay.put(layObject3);

								} else if (availableLay.length() == 1) {
									JSONObject layObject1 = new JSONObject();
									JSONObject layObject2 = new JSONObject();
									JSONObject layObject3 = new JSONObject();
									layObject1.put("price",
											new BigDecimal(availableLay.getJSONObject(0).getString("price")));
									layObject2.put("size",
											new BigDecimal(availableLay.getJSONObject(0).getString("size")));
									layObject3.put("price", 0.0);
									layObject1.put("size", 0.0);
									layObject2.put("price", 0.0);
									layObject3.put("size", 0.0);
									lay.put(layObject1);
									lay.put(layObject2);
									lay.put(layObject3);

								}

							} else {

								JSONObject layObject1 = new JSONObject();
								JSONObject layObject2 = new JSONObject();
								JSONObject layObject3 = new JSONObject();
								layObject1.put("price", 0.0);
								layObject2.put("size", 0.0);
								layObject3.put("price", 0.0);
								layObject1.put("size", 0.0);
								layObject2.put("price", 0.0);
								layObject3.put("size", 0.0);
								lay.put(layObject1);
								lay.put(layObject2);
								lay.put(layObject3);

							}

							if (j == 0) {
								team1 = back1.add(lay1);

							}

							if (j == 1) {
								team2 = back2.add(lay2);

							}

							if (j == 2) {
								team3 = back3.add(lay3);

							}

							JSONObject selectionid = new JSONObject();
							selectionid.put("id", runners.getJSONObject(j).getString("selectionId"));
							selectionid.put("name",
									selectionIdRepository.findBySelectionidAndMarketid(
											Integer.parseInt(runners.getJSONObject(j).getString("selectionId")),
											mrkt.getMarketid()).getRunnerName());
							selectionid.put("back", back);
							selectionid.put("lay", lay);
							runner.put(selectionid);
						}

						JSONObject event = new JSONObject();
						JSONObject minmax = new JSONObject();
						event.put("name", mrkt.getMatchname());
						event.put("id", mrkt.getEventid());

						odds.put("event", event);
						odds.put("minmax", minmax);
						odds.put("runners", runner);
						result.put(odds);
						// jsonobj.put(odds)
						// score.put(getScore(mrkt.getCricexchageid()));
					}

				

			}
			matchOdds.put("result", result);
			// matchOdds.put("score", score);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (matchOdds.length() == 0) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(matchOdds.toString(), HttpStatus.OK);
		}
	}

	public JSONObject getScoreNetExposore(@RequestParam("key") String key) throws JSONException {

		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		ArrayList<String> list = new ArrayList<String>();
		RestTemplate restTemplate = new RestTemplate();
		DecimalFormat df = new DecimalFormat("#0.00");
		JSONObject obj = new JSONObject();
		try {
			String p1 = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/sV1/" + key + "/p1.json",
					String.class);
			String p2 = restTemplate.getForObject("https://cricket-exchange.firebaseio.com/sV1/" + key + "/p2.json",
					String.class);
			String live = restTemplate
					.getForObject("https://cricket-exchange.firebaseio.com/liveMatches/" + key + ".json", String.class);
			JSONObject obj1 = new JSONObject(p1.toString());
			JSONObject obj2 = new JSONObject(p2.toString());

			JSONObject liveobj = new JSONObject(live);

			if (liveobj.getString("type").equalsIgnoreCase("1")) {

				if (liveobj.getString("status").equalsIgnoreCase("2")
						|| liveobj.getString("status").equalsIgnoreCase("1")) {
					if (liveobj.getString("inning").equalsIgnoreCase("1")
							|| liveobj.getString("inning").equalsIgnoreCase("2")) {
						Integer balldone2 = liveobj.getInt("ballsdone");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone")));
						liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone", numberOfOvers);
					}
					if (liveobj.getString("inning").equalsIgnoreCase("2")) {
						Integer balldone2 = liveobj.getInt("ballsdone2");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score2")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone2")));
						// liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone2", numberOfOvers);
					}
					if (liveobj.getString("inning").equalsIgnoreCase("3")) {
						Integer balldone2 = liveobj.getInt("ballsdone");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone")));
						liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone", numberOfOvers);
					}
					if (liveobj.getString("inning").equalsIgnoreCase("4")) {
						Integer balldone2 = liveobj.getInt("ballsdone");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone")));
						liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone", numberOfOvers);
					}
					if (liveobj.getString("inning").equalsIgnoreCase("6")) {
						Integer balldone2 = liveobj.getInt("ballsdone");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone")));
						liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone", numberOfOvers);
					}

				}
			} else {
				if (liveobj.getString("status").equalsIgnoreCase("2")
						|| liveobj.getString("status").equalsIgnoreCase("1")) {
					if (liveobj.getString("inning").equalsIgnoreCase("1")
							|| liveobj.getString("inning").equalsIgnoreCase("2")) {
						Integer balldone2 = liveobj.getInt("ballsdone");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone")));
						liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone", numberOfOvers);
					}
					if (liveobj.getString("inning").equalsIgnoreCase("2")) {
						Integer balldone2 = liveobj.getInt("ballsdone2");
						String round = String.valueOf(Math.round(balldone2 / 6));
						String mod = String.valueOf(balldone2 % 6);
						double numberOfOvers = Double.valueOf(round + "." + mod);

						String currentrunrate = df.format(Double.valueOf(liveobj.getInt("score2")) * 6
								/ Double.valueOf(liveobj.getInt("ballsdone2")));
						// liveobj.put("currentrunrate", currentrunrate);
						liveobj.put("ballsdone2", numberOfOvers);
					}

				}
			}

			if (obj1.getInt("p1b") != 0) {
				Integer ballface = obj1.getInt("p1b");
				Integer run = obj1.getInt("p1r");
				String strikerate = df.format(Double.valueOf(run) * 100 / Double.valueOf(ballface));

				obj1.put("p1s", strikerate);

			}
			if (obj1.getInt("p1b") != 0) {
				Integer ballface = obj1.getInt("p2b");
				Integer run = obj1.getInt("p2r");
				String strikerate = df.format(Double.valueOf(run) * 100 / Double.valueOf(ballface));

				obj1.put("p2s", strikerate);

			}

			JSONObject obj3 = new JSONObject(live.toString());
			if (obj2.has("c2")) {
				obj2.remove("c2");
			}

			/*
			 * result.put("p1",obj1.toString()); result.put("p2",obj2.toString());
			 * result.put("live",obj3.toString()); /result.put("live",live.toString());
			 */
			list.add(obj1.toString());
			list.add(obj2.toString());
			list.add(liveobj.toString());

			obj.put("p1", obj1);
			// obj.put("p2", obj2);
			obj.put("live", liveobj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return obj;

	}

	@RequestMapping(value = "teamId", method = RequestMethod.GET)
	public ResponseEntity<Object> teamId(@RequestParam String marketid) {

		ArrayList<SelectionIds> selids = selectionIdRepository.findByMarketid(marketid);

		if (selids.size() > 0) {
			return new ResponseEntity<Object>(selids, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/editFancy", method = RequestMethod.POST)
	public ResponseEntity<Object> editFancy(@RequestBody LinkedHashMap<String, Object> reqestData)
			throws JSONException, IOException {
		HttpSession session = request.getSession(true);
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");

		JSONObject res1 = new JSONObject();

		JSONObject statusjson = new JSONObject();
		JSONObject status1 = new JSONObject();

		if (usersession != null) {

			JSONArray array2 = new JSONArray();

			JSONObject res = null;
			String json = new String(
					Files.readAllBytes(Paths.get("Daimond" + "/" + reqestData.get("eventid") + ".json")),
					StandardCharsets.UTF_8);
			if (json != null) {

				// System.out.println("json"+json);
				JSONArray jsonarray = new JSONArray(json.toString());

				for (int i = 0; i < jsonarray.length(); i++) {

					JSONObject obj = jsonarray.getJSONObject(i);

					if (reqestData.get("fancyid").toString().equalsIgnoreCase(obj.getString("id"))) {

						JSONObject runnersjson = new JSONObject();
						JSONArray backarray = new JSONArray();
						JSONArray layarray = new JSONArray();
						JSONArray runnerarray = new JSONArray();

						JSONObject backjson = new JSONObject();
						backjson.put("line", reqestData.get("yes"));
						backjson.put("price", reqestData.get("yesrate"));
						backjson.put("size", "");
						JSONObject layjson = new JSONObject();
						layjson.put("line", reqestData.get("no"));
						layjson.put("price", reqestData.get("norate"));
						layjson.put("size", "");
						backarray.put(backjson);
						layarray.put(layjson);

						res = new JSONObject();

						res.put("id", obj.getString("id").split("-")[1].split("\\.")[0]);
						res.put("fancyid", obj.getString("id"));
						res.put("name", obj.getString("name"));

						res.put("back", backarray);
						res.put("lay", layarray);
						runnerarray.put(res);
						obj.put("runners", runnerarray);
						obj.put("statusLabel", "");
						obj.put("status", "OPEN");

						array2.put(obj);
					} else {
						array2.put(obj);
					}

				}
				File file = new File("Daimond/" + reqestData.get("eventid") + ".json");

				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(array2.toString());
				bw.close();

			}

		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/leftMenuData", method = RequestMethod.GET, produces = "application/json")
	// @Cacheable(value = "leftMenuData")
	public ResponseEntity<Object> leftMenuData() {

		ArrayList<LinkedHashMap<String, Object>> marketlist = eventService.leftMenuData();
		return new ResponseEntity<Object>(marketlist, HttpStatus.OK);
	}

	@RequestMapping(value = "/activeMarketBySportId", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> activeMarketBySportId() {

		ArrayList<Market> marketlist = eventMarketRepository
				.findByMarketnameAndIsactiveAndIsResult(EXConstants.MatchOdds, true, false);
		return new ResponseEntity<Object>(marketlist, HttpStatus.OK);
	}

	@RequestMapping(value = "/findByEventIdAndIsActive/{eventid}/{isactive}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> findByEventIdAndIsActive(@PathVariable Integer eventid,
			@PathVariable Boolean isactive) {

		ArrayList<Market> marketlist = eventMarketRepository.findByEventidAndIsactiveAndStatus(eventid, isactive, true);
		ArrayList<String> list = new ArrayList<String>();
		
		
	
		ArrayList<HashMap<String, Object>> hm3 = new ArrayList<HashMap<String, Object>>();
		if (marketlist.size() > 0) {

			for (Market mrkt : marketlist) {
				mrkt.setMarketIdWithOutDecimal(mrkt.getMarketid().split("\\.")[1]);
				HashMap<String, Object> hm = new HashMap<String, Object>();


				try {
			/*		ClassLoader classLoader = getClass().getClassLoader();
					InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);

					FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
							.setCredentials(GoogleCredentials.fromStream(inputStream))
							.setDatabaseUrl(EXConstants.firestoreDb).build();
					if (FirebaseApp.getApps().isEmpty())
						FirebaseApp.initializeApp(firebaseOptions);
					fb = com.google.firebase.cloud.FirestoreClient.getFirestore();
					
					
					DocumentReference docRef = fb.collection(eventid.toString())
							.document(mrkt.getMarketid().split("\\.")[1]);
					ApiFuture<DocumentSnapshot> future = docRef.get();
					DocumentSnapshot document = future.get();
					if (document.exists()) {
						hm = (HashMap<String, Object>) document.getData();
						HashMap<String, Object> hm1 =	(HashMap<String, Object>) hm.get("Odds");
						hm3.add((HashMap<String, Object>) hm1);
					}
				*/	
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			return new ResponseEntity<Object>(hm3, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/stopStartMarketOdds/{stopMarketOdds}/{marketId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> stopStartMarketOdds(@PathVariable Boolean stopMarketOdds,
			@PathVariable String marketId) {
		ResponseBean responseBean = new ResponseBean();
		Market mrkt = eventMarketRepository.findByMarketidAndStatus(marketId, true);
		if (mrkt != null) {
			if (stopMarketOdds) {
				responseBean.setMessage("Market Started Successfully..");
			} else {
				responseBean.setMessage("Market Stoped Successfully..");
			}
			mrkt.setStopMarketOdds(stopMarketOdds);
			eventMarketRepository.save(mrkt);
			responseBean.setType("success");

			responseBean.setTitle("success");
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		responseBean.setType("error");
		responseBean.setMessage("Some Thing Went Wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

	} 

/*	@RequestMapping(value = "/activeFancy/{eventId}/{Id}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> activeFancy(@PathVariable Integer eventId, @PathVariable String Id,@PathVariable Integer usertype) {

		ResponseBean responseBean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<String> list1 = new ArrayList<String>();
		JSONArray arr = new JSONArray();
		ArrayList<HashMap<String, Object>> hm3 = new ArrayList<HashMap<String, Object>>();
		List<String> fancyIds  = new ArrayList<String>();
		System.out.println("Id"+Id);
		
			try {
				ArrayList<MatchFancy> fancyList = fancyRepository.findByEventidAndIsActive(eventId, true);

				ClassLoader classLoader = getClass().getClassLoader();
				InputStream inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);

				FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(inputStream))
						.setDatabaseUrl(EXConstants.firestoreDb).build();
				if (FirebaseApp.getApps().isEmpty())
					FirebaseApp.initializeApp(firebaseOptions);
				fb = com.google.firebase.cloud.FirestoreClient.getFirestore();
				for (MatchFancy fancy : fancyList) {
					
					HashMap<String, Object> hm = new HashMap<String, Object>();

					
					DocumentReference docRef = fb.collection(fancy.getEventid().toString())
							.document(fancy.getFancyid());
					ApiFuture<DocumentSnapshot> future = docRef.get();
					DocumentSnapshot document = future.get();
					if (document.exists()) {
						hm = (HashMap<String, Object>) document.getData();
						HashMap<String, Object> hm1 =	(HashMap<String, Object>) hm.get("Fancy");
						hm1.put("pnl", 100);
						hm3.add((HashMap<String, Object>) hm1);
						fancyIds =new ArrayList<String>();
						fancyIds.add(hm1.get("fancyId").toString());
						
					}
				   ArrayList<ExFancyBook> mfancyBookList =	getFancyExposure(fancyIds,Id,usertype);
				   
				    for (Map<String, Object> entry : hm3) {
				    
				    	for(ExFancyBook fb : mfancyBookList) {
				    		if(fb.getFancyid().equalsIgnoreCase(entry.get("fancyId").toString())) {
				    			entry.put("pnl", fb.getPnl());
				    		}
				    	}
				    	//System.out.println(entry.get("fancyId"));
				    }
				
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		return new ResponseEntity<Object>(hm3, HttpStatus.OK);
	}*/

	@RequestMapping(value = "/activeMarkets/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Object> activeMarkets(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Market> marketlist = eventMarketRepository.findByEventidAndIsactiveAndStatus(eventId, true, true);
			if (marketlist.size() > 0) {
				for (Market market : marketlist) {
					list.add(market.getMarketid().split("\\.")[1]);
				}
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/fancyLock/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> fancyLock(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
				Event evnt = eventRepo.findByeventid(eventId);
				evnt.setFancypause(true);
				eventRepo.save(evnt);
				responseBean.setType("success");
				responseBean.setMessage("Fancy Locked Successfully successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/fancyUnLock/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> fancyUnLock(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
				Event evnt = eventRepo.findByeventid(eventId);
				evnt.setFancypause(false);
				eventRepo.save(evnt);
				responseBean.setType("success");
				responseBean.setMessage("Fancy UnLocked Successfully successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/betLockMatch/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> betLock(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
				Event evnt = eventRepo.findByeventid(eventId);
				evnt.setBetLock(true);
				eventRepo.save(evnt);
				responseBean.setType("success");
				responseBean.setMessage("Bet Locked Successfully successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	

	@RequestMapping(value = "/betUnLockMatch/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> betUnLock(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
				Event evnt = eventRepo.findByeventid(eventId);
				evnt.setBetLock(false);
				eventRepo.save(evnt);
				responseBean.setType("success");
				responseBean.setMessage("Bet UnLocked Successfully successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/matchFancyStatus/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> matchFancyStatus(@PathVariable Integer eventId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
				Event evnt = eventRepo.findByeventid(eventId);

				return new ResponseEntity<Object>(evnt, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/TvOnOff/{livetv}/{eventId}", method = RequestMethod.POST)
	public ResponseEntity<Object> TvOnOff(@PathVariable Boolean livetv, @PathVariable Integer eventId) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		try {
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventId);
				if (bean != null) {
					bean.setLiveTv(livetv);
					eventRepo.save(bean);
					responseBean.setMessage("Live Tv Successfully!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/eventDetail/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Object> checkMatchStatus(@PathVariable Integer eventId) throws Exception {

		Event event = eventRepo.findByEventidAndStatus(eventId, true);
		if (event != null) {
			return new ResponseEntity<Object>(event, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(value = "/marketDetail/{marketId}", method = RequestMethod.GET)
	public ResponseEntity<Object> marketDetail(@PathVariable String marketId) throws Exception {

		Market market = eventMarketRepository.findByMarketidAndStatus(marketId, true);
		if (market != null) {
			return new ResponseEntity<Object>(market, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/sportsList/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<Object> sportsList() throws Exception {

		ArrayList<Sport> sports = sportRepository.findByStatus(true);
		if (sports.size() > 0) {
			return new ResponseEntity<Object>(sports, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@Transactional
	@RequestMapping(value = "/createMatch", method = RequestMethod.POST)
	// @CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> createMatch(@RequestBody HashMap<String, String> requestData) {

		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			String opendate = requestData.get("opendate") + " " + requestData.get("hour") + ":"
					+ requestData.get("minut") + ":00";

			if (seriesEventRepository.findByeventid(Integer.parseInt(requestData.get("matchId"))) == null) {
				Event event = new Event();
				event.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				event.setAddautoFancy(false);
				event.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				event.setFancyprovider(EXConstants.Diamond);
				event.setFancypause(false);
				event.setIsactive(true);
				event.setLiveTv(false);
				event.setMinbet(Integer.parseInt(requestData.get("minbet")));
				event.setMaxbet(Integer.parseInt(requestData.get("maxbet")));
				event.setBetdelay(Integer.parseInt(requestData.get("betdelay")));
				event.setOpenDate(opendate);
				event.setEventid(Integer.parseInt(requestData.get("matchId")));
				event.setEventname(requestData.get("matchName"));
				event.setMinbetfancy(Integer.parseInt(requestData.get("minbetfancy")));
				event.setMaxbetfancy(Integer.parseInt(requestData.get("maxbetfancy")));
				event.setBetdelayfancy(Integer.parseInt(requestData.get("betdelayfancy")));
				event.setMinbetBookmaker(Integer.parseInt(requestData.get("minbet")));
				event.setMaxbetBookmaker(Integer.parseInt(requestData.get("maxbet")));
				event.setLiveTv(false);
				event.setAppid(usersession.getAppid());
				event.setAdminid("-1");
				event.setAddautoFancy(false);
				event.setStatus(true);
				event.setSportid(4);
				event.setBetLock(false);
				event.setSeriesid(Integer.parseInt(requestData.get("seriesid")));
				event.setIsactive(true);
				event.setFancyprovider("Daimond");
				event.setPlayermaxbetfancy(Integer.parseInt(requestData.get("playermaxbetfancy")));
				seriesEventRepository.save(event);
				
				EXMarketDiff  mktDiff =  new EXMarketDiff();
				mktDiff.setBackdiff(1);
				mktDiff.setLaydiff(0);
				mktDiff.setEventid(event.getEventid());
				marketDiffRepo.save(mktDiff);

				if (seriesEventRepository.findByeventidAndAppid(event.getEventid(), event.getAppid()) != null) {

					Market mark = eventMarketRepository.findByMarketidAndStatus(requestData.get("marketId"), true);
					if (mark == null) {

						ArrayList<SelectionIds> selectionList1 = selectionIdRepository
								.findByMarketid(requestData.get("marketId") + "BM");
						if (selectionList1.size() == 0) {
							SelectionIds Id11 = new SelectionIds();
							SelectionIds Id21 = new SelectionIds();

							Id11.setMarketid(requestData.get("marketId") + "BM");
							Id11.setSelectionid(1);
							Id11.setRunnerName(requestData.get("team1"));

							Id21.setMarketid(requestData.get("marketId") + "BM");
							Id21.setSelectionid(2);
							Id21.setRunnerName(requestData.get("team2"));

							selectionList1.add(Id11);
							selectionList1.add(Id21);
							selectionRepo.saveAll(selectionList1);

							ArrayList<SelectionIds> selectionList = selectionIdRepository
									.findByMarketid(requestData.get("marketId"));
							if (selectionList.size() == 0) {
								SelectionIds Id1 = new SelectionIds();
								SelectionIds Id2 = new SelectionIds();

								Id1.setMarketid(requestData.get("marketId"));
								Id1.setSelectionid(101);
								Id1.setRunnerName(requestData.get("team1"));

								Id2.setMarketid(requestData.get("marketId"));
								Id2.setSelectionid(102);
								Id2.setRunnerName(requestData.get("team2"));

								selectionList.add(Id1);
								selectionList.add(Id2);
								selectionRepo.saveAll(selectionList);

							}

							Market mark1 = new Market();
							mark1.setStopMarketOdds(true);
							mark1.setMarketid(requestData.get("marketId"));
							mark1.setMatchname(requestData.get("matchName"));
							mark1.setStatus(true);
							mark1.setIsactive(true);
							mark1.setIspause(true);
							mark1.setIsResult(false);
							mark1.setCricexchageid("0");
							mark1.setOddsprovider("Manual");
							mark1.setCreatedon(
									dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
							mark1.setUpdatedon(
									dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
							mark1.setMarketname(EXConstants.MatchOdds);
							mark1.setAppid(usersession.getAppid());
							mark1.setEventid(Integer.parseInt(requestData.get("matchId")));
							mark1.setSeriesid(Integer.parseInt(requestData.get("seriesid")));
							mark1.setSportid(4);
							mark1.setAddautoFancy(false);
							mark1.setPauseByAdmin(false);
							mark1.setOpendate(dtUtil.convertToDate2(opendate));
							mark1.setInPlay(false);
							mark1.setPauseByAdmin(true);
							mark1.setiSfsmarkedInactive(false);
							eventMarketRepository.save(mark1);

							mark = new Market();
							mark.setStopMarketOdds(true);
							mark.setMarketid(requestData.get("marketId") + "BM");
							mark.setMatchname(requestData.get("matchName"));
							mark.setStatus(true);
							mark.setIsactive(true);
							mark.setIspause(true);
							mark.setIsResult(false);
							mark.setCricexchageid("0");
							mark.setOddsprovider("Manual");
							mark.setCreatedon(
									dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
							mark.setUpdatedon(
									dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
							mark.setMarketname("Bookmaker");
							mark.setAppid(usersession.getAppid());
							mark.setEventid(Integer.parseInt(requestData.get("matchId")));
							mark.setSeriesid(Integer.parseInt(requestData.get("seriesid")));
							mark.setSportid(4);
							mark.setAddautoFancy(false);
							mark.setPauseByAdmin(false);
							mark.setOpendate(dtUtil.convertToDate2(opendate));
							mark.setInPlay(false);
							mark.setPauseByAdmin(true);
							mark.setiSfsmarkedInactive(false);
							eventMarketRepository.save(mark);

							responseBean.setType("success");
							responseBean.setMessage("Match Added added successfully");
							responseBean.setTitle("success");
							return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/seriesList", method = RequestMethod.GET)
	public ResponseEntity<Object> seriesList() throws Exception {

		ArrayList<Series> series = seriesRepository.findByStatus(true);
		if (series.size() > 0) {
			return new ResponseEntity<Object>(series, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/getSport", method = RequestMethod.GET)
	public ResponseEntity<Object> getSport() {

		JSONArray sportArrray = new JSONArray();

		RestTemplate restTemplate = new RestTemplate();
		String apiurl = restTemplate.getForObject(EXConstants.developerApi,String.class);

		String sportresult = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1) + "listEventTypes",
				String.class);
		ArrayList<Sport> sportlist = sportRepository.findByType("Casino");
		try {

			sportArrray = new JSONArray(sportresult);
			if (sportlist.size() > 0) {
				for (Sport bean : sportlist) {
					JSONObject marketcount = new JSONObject();
					JSONObject sportJson = new JSONObject();
					sportJson.put("name", bean.getName());
					sportJson.put("id", bean.getSportId());
					marketcount.put("marketCount", 5);
					marketcount.put("eventType", sportJson);
					sportArrray.put(marketcount);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(sportArrray.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/getSeries/{sportId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSeries(@PathVariable Integer sportId) {

		JSONArray sportArrray = new JSONArray();

		RestTemplate restTemplate = new RestTemplate();
		try {
			if (sportId == 1001) {
				Series series = seriesRepository.findBySportid(sportId);
				if (series != null) {
					JSONObject marketcount = new JSONObject();
					JSONObject competition = new JSONObject();
					competition.put("name", series.getSeriesname());
					competition.put("id", series.getSeriesid());
					marketcount.put("marketCount", 5);
					marketcount.put("competitionRegion", "INR");
					marketcount.put("competition", competition);
					sportArrray.put(marketcount);
				}

			} else {
				String apiurl = restTemplate
						.getForObject(EXConstants.developerApi,String.class);

				String sportresult = restTemplate.getForObject(
						apiurl.substring(1, apiurl.length() - 1) + "listCompetitions&EventTypeID=" + sportId,
						String.class);
				sportArrray = new JSONArray(sportresult);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(sportArrray.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/getEvent/{sportId}/{seriesId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEvent(@PathVariable Integer sportId, @PathVariable Integer seriesId) {

		JSONArray eventArrray = new JSONArray();

		RestTemplate restTemplate = new RestTemplate();
		try {
			if (sportId > 10000000) {
				ArrayList<Event> event = eventRepo.findBySeriesid(seriesId);
				if (event.size() > 0) {
					for (Event bean : event) {
						JSONObject marketCount = new JSONObject();
						JSONObject eventJson = new JSONObject();
						eventJson.put("name", bean.getEventname());
						eventJson.put("id", bean.getEventid());
						eventJson.put("countryCode", "INR");
						eventJson.put("timezone", "GMT");
						marketCount.put("marketCount", 5);
						marketCount.put("event", eventJson);
						eventArrray.put(marketCount);
					}

				}

			} else {
				
				if(seriesId !=922982) {
					String apiurl = restTemplate
							.getForObject(EXConstants.developerApi,String.class);
					String eventresult = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1)
							+ "listEvents&EventTypeID=" + sportId + "&CompetitionID=" + seriesId, String.class);

					
					eventArrray = new JSONArray(eventresult);
				}else {
					
					 HttpHeaders headers = new HttpHeaders();
					 headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			            
					
					
					HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			        ResponseEntity<String> respEntity = restTemplate.exchange(EXConstants.RSApi+"sessionApi/eventList/"+sportId, HttpMethod.GET, entity, String.class);
			        
					JSONObject obj  = new JSONObject(respEntity.getBody());
				
					JSONArray ary  = new JSONArray(obj.getString("data"));
					
					
					for(int i=0; i<ary.length();i++) {
						
						JSONObject objdata = new JSONObject(ary.get(i).toString());
						
						JSONArray evntary  = new JSONArray(objdata.getString("event"));
						
						
						for(int j=0; j<evntary.length();j++) {
						
							JSONObject marketCount = new JSONObject();
							JSONObject eventJson = new JSONObject();
							
							
							Integer  time=Integer.parseInt(new JSONObject(evntary.get(j).toString()).getString("time"));
							
						    long currentDateTime = System.currentTimeMillis();
					     
					         Date currentDate = new Date(time.longValue()*1000);
							
							
							eventJson.put("name",new JSONObject(evntary.get(j).toString()).getString("name"));
							eventJson.put("id",new JSONObject(evntary.get(j).toString()).getString("eventId"));
							eventJson.put("countryCode", "INR");
							eventJson.put("timezone", "GMT");
							eventJson.put("openDate", new JSONObject(evntary.get(j).toString()).getString("time"));
							marketCount.put("marketCount", 1);
							marketCount.put("event", eventJson);
							eventArrray.put(marketCount);
						}

						
						
			
					
					}
					
					/**/
				}
				

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(eventArrray.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/findSportByTypeAndStatus", method = RequestMethod.GET)
	public ResponseEntity<Object> findSportByTypeAndStatus() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			ArrayList<Sport> list = new ArrayList<>();
			list = sportRepository.findByTypeAndStatus("VCasino", true);

			if (list.size() > 0) {
				return new ResponseEntity<Object>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/teamname/{marketid}", method = RequestMethod.GET)
	public ResponseEntity<Object> addmatch(@PathVariable String marketid) {

		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<SelectionIds> selids = null;
		if (usersession != null) {
			selids = selectionRepo.findByMarketid(marketid);

		}

		return new ResponseEntity<Object>(selids, HttpStatus.OK);
	}

	@RequestMapping(value = "/getVirtualCasinoList", method = RequestMethod.GET)
	public ResponseEntity<Object> getVirtualCasinoList() {

		JSONArray sportArrray = new JSONArray();

		RestTemplate restTemplate = new RestTemplate();
		String apiurl = restTemplate.getForObject(
				"https://aaaa-14afe-cc669-betfair.firebaseio.com/virtualCasinoSportlistApi.json", String.class);

		String sportresult = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1), String.class);
		try {

			sportArrray = new JSONArray(sportresult);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(sportArrray.toString(), HttpStatus.OK);

	}

	@RequestMapping(value = "/getCasinoEvent/{sportId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCasinoEvent(@PathVariable Integer sportId) {

		JSONArray eventArrray = new JSONArray();

		RestTemplate restTemplate = new RestTemplate();
		try {
			String apiurl = restTemplate.getForObject(
					"https://aaaa-14afe-cc669-betfair.firebaseio.com/virtualCsinoEventListApi.json", String.class);
			String eventresult = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1) + "" + sportId,
					String.class);

			eventArrray = new JSONArray(eventresult);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(eventArrray.toString(), HttpStatus.OK);

	}

	/*
	 * New API'S Implemented 30/12/2020
	 */
	@RequestMapping(value = "/updateSBAMinMaxEvent/{eventid}/{amount}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateSBAMinMaxEvent(@PathVariable Integer eventid, @PathVariable Integer amount) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				SBAMaxAmount event = null;
				if (bean != null) {
					int num1 = bean.getMaxbet();
					int num2 = bean.getMaxbetBookmaker();
					int num3 = bean.getMaxbetfancy();

					log.info("num1: " + num1 + " num2: " + num2 + " num3: " + num3);

					int minValue = num3 > (num1 > num2 ? num1 : num2) ? num3 : (num1 > num2 ? num1 : num2);
					log.info("minValue: " + minValue);
					if (minValue >= amount) {
						event = sBAMaxAmountRepository.findByEventidAndUserid(eventid, usersession.getId());

						if (event == null) {
							event = new SBAMaxAmount();
							event.setMaxbet(amount);
							event.setMaxbetfancy(amount);
							event.setMaxbetBookmaker(amount);
							event.setUserid(usersession.getId());
							event.setEventid(eventid);
							log.info("event: " + event);
						} else {
							event.setMaxbet(amount);
							event.setMaxbetfancy(amount);
							event.setMaxbetBookmaker(amount);
						}

						sBAMaxAmountRepository.save(event);
						responseBean.setMessage("Process Successfully!");
						responseBean.setType("success");
						responseBean.setTitle("Success");

					} else {
						prepareResponse(responseBean, "Please provide coorect amount value");
					}
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/removeSBAMinMaxEvent/{eventid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removeSBAMinMaxEvent(@PathVariable Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				SBAMaxAmount event = sBAMaxAmountRepository.findByEventidAndUserid(eventid, usersession.getId());

				if (event != null) {
					sBAMaxAmountRepository.deleteByEventidAndUserid(eventid, usersession.getId());
					responseBean.setMessage("Reocrds Successfully Deleted!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/sbaBetLock", method = RequestMethod.POST)
	public ResponseEntity<Object> sbaBetLock(@RequestParam Integer eventid, @RequestParam Boolean betlock) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			SBABetLock event = null;
			log.info("usersession: " + usersession);
			if (usersession != null) {
				event = sBABetLockRepository.findByEventidAndUserid(eventid, usersession.getId());

				log.info("eventid: "+eventid+" Id:  "+ usersession.getId());
				
				if (event == null) {
					event = new SBABetLock();
					event.setEventid(eventid);
					event.setBetlock(betlock);
					event.setUserid(usersession.getId());
				} else {
					event.setBetlock(betlock);
				}

				sBABetLockRepository.save(event);

				responseBean.setMessage("Reocrds Successfully Proceeds!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/sbaBetUnLock", method = RequestMethod.DELETE)
	public ResponseEntity<Object> sbaBetUnLock(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				SBABetLock event = sBABetLockRepository.findByEventidAndUserid(eventid, usersession.getId());

				log.info("eventid: "+eventid+" Id:  "+ usersession.getId());
				if (event != null) {
					sBABetLockRepository.deleteByEventidAndUserid(eventid, usersession.getId());
					responseBean.setMessage("Reocrds Successfully Deleted!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/sbaFancyBetLock", method = RequestMethod.POST)
	public ResponseEntity<Object> sbaFancyBetLock(@RequestParam Integer eventid, @RequestParam Boolean fancybetlock) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			SBAFancyBetLock event = null;
			log.info("usersession: " + usersession);
			if (usersession != null) {
				event = sBAFancyBetLockRepository.findByEventidAndUserid(eventid, usersession.getId());

				if (event == null) {
					event = new SBAFancyBetLock();
					event.setEventid(eventid);
					event.setFancybetlock(fancybetlock);
					event.setUserid(usersession.getId());
				} else {
					event.setFancybetlock(fancybetlock);
				}

				sBAFancyBetLockRepository.save(event);

				responseBean.setMessage("Reocrds Successfully Proceeds!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/sbaFancyBetUnLock", method = RequestMethod.DELETE)
	public ResponseEntity<Object> sbaFancyBetUnLock(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				SBAFancyBetLock event = sBAFancyBetLockRepository.findByEventidAndUserid(eventid, usersession.getId());

				if (event != null) {
					sBAFancyBetLockRepository.deleteByEventidAndUserid(eventid, usersession.getId());
					responseBean.setMessage("Reocrds Successfully Deleted!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					responseBean.setMessage("Something went wrong....");
					responseBean.setType("error");
					responseBean.setTitle("Oops..");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	private ResponseBean prepareResponse(ResponseBean responseBean, String message) {
		responseBean.setMessage(message);
		responseBean.setType("error");
		responseBean.setTitle("Oops..");
		return responseBean;
	}

	@RequestMapping(value = "/updateEventExposure/{eventid}/{totalExposure}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateEventExposure(@PathVariable Integer eventid,@PathVariable Integer totalExposure) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				if (bean != null) {
				  if(bean.getSportid() == 4) {
					   
						prepareResponse(responseBean, "event Exposure Can not update on Cricket..");
				  }else {
					    bean.setTotalExposure(totalExposure);
						eventRepo.save(bean);
						responseBean.setMessage("Reocrds Successfully Proceed!");
						responseBean.setType("success");
						responseBean.setTitle("Success");
						
				  }
					
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateSBANetExposure", method = RequestMethod.POST)
	public ResponseEntity<Object> updateSBANetExposure(@RequestParam Integer eventid, @RequestParam Integer netExposure) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				log.info("bean: "+bean);
				if (bean != null && bean.getTotalExposure() != null && bean.getTotalExposure() >= netExposure) {
					SBANetExposure exposure = sBANetExposureRepository.findByEventidAndUserid(eventid,
							usersession.getId());
					log.info("data: "+eventid+" "+
							usersession.getId());
					log.info("exposure: "+exposure);
					if (exposure != null) {
						exposure.setNetexposure(netExposure);
					} else {
						exposure = new SBANetExposure();
						exposure.setEventid(eventid);
						exposure.setUserid(usersession.getId());
						exposure.setNetexposure(netExposure);
					}
					sBANetExposureRepository.save(exposure);
					responseBean.setMessage("Reocrds Successfully Proceed!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					prepareResponse(responseBean, "Please provide correct input.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

/*	@RequestMapping(value = "/removeEventExposure", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removeEventExposure(@RequestParam Integer eventid) {
		log.info("inside sbaBetUnLock");
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				if (bean != null) {
					SBANetExposure exposure = sBANetExposureRepository.findByEventidAndUserid(eventid,
							usersession.getId());
					log.info("data: "+eventid+" "+
							usersession.getId());
					log.info("exposure: "+exposure);
					if (exposure != null) {
						sBANetExposureRepository.deleteByEventidAndUserid(eventid, usersession.getId());
					} else {
						prepareResponse(responseBean, "Please provide correct input.....");
					}
					responseBean.setMessage("Reocrds Successfully Proceed!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}*/
	
	
	@RequestMapping(value = "/removeEventExposure", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removeEventExposure(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				if (bean != null) {
					bean.setTotalExposure(0);
					eventRepo.save(bean);
					responseBean.setMessage("Removed SuccessFully!..");
					responseBean.setType("success");
					responseBean.setTitle("Success");
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	@RequestMapping(value = "/sbaBetLockStatus", method = RequestMethod.POST)
	public ResponseEntity<Object> sbaBetLockStatus(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			SBABetLock event = null;
			log.info("usersession: " + usersession);
			if (usersession != null) {
				event = sBABetLockRepository.findByEventidAndUserid(eventid, usersession.getId());
				log.info("Data: "+eventid+" "+usersession.getId());
				if(event != null) {
					return new ResponseEntity<Object>(event, HttpStatus.OK);
				} else {
					prepareResponse(responseBean, "Something went wrong.....");
				}
				
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	 
	@RequestMapping(value = "/sbaFancyBetLockStatus", method = RequestMethod.POST)
	public ResponseEntity<Object> sbaFancyBetLockStatus(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			SBAFancyBetLock event = null;
			log.info("usersession: " + usersession);
			if (usersession != null) {
				log.info("Data: "+eventid+" "+usersession.getId());
				event = sBAFancyBetLockRepository.findByEventidAndUserid(eventid, usersession.getId());
				return new ResponseEntity<Object>(event, HttpStatus.OK);
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/selectedBetMaximumBetSBA/{eventid}", method = RequestMethod.GET)
	public ResponseEntity<Object> selectedBetMaximumBetSBA(@PathVariable Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		SBAMaxAmount event = null;
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				log.info("Data: "+eventid+" "+usersession.getId());
				event = sBAMaxAmountRepository.findByEventidAndUserid(eventid, usersession.getId());

				if (event != null) {
					return new ResponseEntity<Object>(event, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(event, HttpStatus.NO_CONTENT);
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(event, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sbaNetExposureStatus", method = RequestMethod.POST)
	public ResponseEntity<Object> sbaNetExposureStatus(@RequestParam Integer eventid) {
		ResponseBean responseBean = new ResponseBean();
		try {
			HttpSession session = request.getSession();
			EXUser usersession = (EXUser) session.getAttribute("user");
			log.info("usersession: " + usersession);
			if (usersession != null) {
				Event bean = eventRepo.findByeventid(eventid);
				if (bean != null) {
					SBANetExposure exposure = sBANetExposureRepository.findByEventidAndUserid(eventid,
							usersession.getId());
					
					responseBean.setMessage("Reocrds Successfully Proceed!");
					responseBean.setType("success");
					responseBean.setTitle("Success");
					return new ResponseEntity<Object>(exposure, HttpStatus.OK);
				} else {
					prepareResponse(responseBean, "Please provide correct input.....");
				}
			} else {
				prepareResponse(responseBean, "UserSession is null");
			}
		} catch (Exception e) {
			log.info("Exception Occured: " + e.getMessage());
			prepareResponse(responseBean, "Something went wrong.....");
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	@RequestMapping(value = "/getSelectedBookMakerBackLayDiff/{eventid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSelectedBookMakerBackLayDiff(@PathVariable Integer  eventid) {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
		EXMarketDiff mktDiff =	marketDiffRepo.findByeventid(eventid);

			if (mktDiff !=null) {
				return new ResponseEntity<Object>(mktDiff, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(mktDiff, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/updateBookMakerBackLayDiff/{eventid}/{backdiff}/{laydiff}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateBookMakerBackLayDiff(@PathVariable Integer eventid, @PathVariable Integer backdiff,@PathVariable Integer laydiff) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<ExMinMaxBetSetAmount> list = new ArrayList<ExMinMaxBetSetAmount>();
		if (usersession != null) {
			EXMarketDiff bean = marketDiffRepo.findByeventid(eventid);
			if (bean != null) {
				bean.setBackdiff(backdiff);
				bean.setLaydiff(laydiff);
				marketDiffRepo.save(bean);
				
			}else {
				bean = new EXMarketDiff();
				bean.setEventid(eventid);
				bean.setBackdiff(backdiff);
				bean.setLaydiff(laydiff);
				marketDiffRepo.save(bean);
			}
			
			marketDiffRepo.save(bean);
			responseBean.setMessage("Updated Successfully!");
			responseBean.setType("success");
			responseBean.setTitle("Success");
			
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateCasinoBackDiff/{eventid}/{backdiff}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateCasinoBackDiff(@PathVariable Integer eventid, @PathVariable Integer backdiff) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXMarketDiff bean = marketDiffRepo.findByeventid(eventid);
			if (bean != null) {
				bean.setBackdiff(backdiff);
				marketDiffRepo.save(bean);
				
			}
			
			
			responseBean.setMessage("Updated Successfully!");
			responseBean.setType("success");
			responseBean.setTitle("Success");
			
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateCasinoLayDiff/{eventid}/{laydiff}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateCasinoLayDiff(@PathVariable Integer eventid, @PathVariable Integer laydiff) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXMarketDiff bean = marketDiffRepo.findByeventid(eventid);
			if (bean != null) {
				bean.setLaydiff(laydiff);
				marketDiffRepo.save(bean);
				
			}
			
			
			responseBean.setMessage("Updated Successfully!");
			responseBean.setType("success");
			responseBean.setTitle("Success");
			
		}

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/betfairSportList", method = RequestMethod.GET)
	public ResponseEntity<Object> betfairSportList() {
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");

		if (usersession != null) {

			ArrayList<Sport> list = (ArrayList<Sport>) sportRepository.findByStatusAndType(true,"Betfair");
			try {
				if (list.size() > 0) {
					return new ResponseEntity<Object>(list, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(list, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
	}
	
	

	public ArrayList<ExFancyBook> getFancyExposure(List<String> fancyIds,String userid,Integer usertype) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<ExFancyBook> fancyExpo = new ArrayList<ExFancyBook>();
		
		// fancyIds  = new ArrayList<String>();
		//fancyIds.add("3041102712")	;
		
		LinkedHashMap<String, ArrayList<ExFancyBook>> hm2 = new LinkedHashMap<String, ArrayList<ExFancyBook>>();
		
		ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
		
			if(usertype==4){
				fancyList= betlistDao.getFancyBookExposure("adminid", userid, fancyIds,"usr.adminpartership");			
			}else if(usertype==6){
				fancyList= betlistDao.getFancyBookExposure("adminid", userid, fancyIds,"usr.adminpartership");			
			}else if(usertype==1){
				fancyList =betlistDao.getFancyBookExposure("masterid", userid, fancyIds,"usr.masterpartership");		
			}else if(usertype==0){
				fancyList = betlistDao.getFancyBookExposure("supermasterid", userid,fancyIds,"usr.supermastepartnership");		
			}else if(usertype==5){
				fancyList =betlistDao.getFancyBookExposure("subadminid", userid, fancyIds,"usr.subadminpartnership");						
			}else if(usertype==2){
				fancyList= betlistDao.getFancyBookExposure("dealerid", userid, fancyIds,"usr.delearpartership");		
			}
			
			HashMap<String,Object> hm = new  HashMap<String,Object> ();
			
			for(String mf : fancyIds) {
				
				
				if(mf.contains(EXConstants.Fancy2Market) || mf.contains(EXConstants.Ball) || mf.contains(EXConstants.Fancy2MarketDiamond) ) {
					ArrayList<Integer> sortedlist = new ArrayList<>(); 
					//if(mf.equalsIgnoreCase("304110276")) {
					Integer mindds =0;
					Integer maxodds =0;


					ArrayList<ExMatchOddsBet> tempfancyList = new  ArrayList<ExMatchOddsBet>();
					ArrayList<ExFancyBook> fancyBook = new ArrayList<ExFancyBook>();


					if(fancyList.size()>0){

						for(ExMatchOddsBet bet :fancyList){


							if(mf.equalsIgnoreCase(bet.getMarketid())) {
								sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
								tempfancyList.add(bet);
							}

						}

						if(sortedlist.size()>0) {
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


											if(tempfancyList.get(i).getIsback()==false){

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

					}
					Double lowval = 0.00;

					for(int i = 0; i <fancyBook.size(); i++){
						if(fancyBook.get(i).getPnl()< lowval){
							lowval = fancyBook.get(i).getPnl();
						}
					}
					ExFancyBook bn =new ExFancyBook();
					bn.setFancyid(mf);
					bn.setPnl(lowval);
					fancyExpo.add(bn);
				
				}else if(mf.contains(EXConstants.Fancy3Market)) {
					
					Double pnl1=0.0;
					Double pnl2=0.0;
					
					for(ExMatchOddsBet bet :fancyList){


						if(mf.equalsIgnoreCase(bet.getMarketid())) {
                          
							if(bet.getIsback()) {
								pnl1= pnl1-(bet.getPnl()*bet.getPartnership()/100);
								pnl2= pnl2+(bet.getLiability()*bet.getPartnership()/100);
								
							}else {
								pnl1= pnl1+(bet.getLiability()*bet.getPartnership()/100);
								pnl2= pnl2-(bet.getPnl()*bet.getPartnership()/100);
							}
							
						}

					}
					
					ExFancyBook bn =new ExFancyBook();
					
					if(pnl1<=0) {
						bn.setFancyid(mf);
						bn.setPnl(pnl1);
					}else if(pnl2<=0) {
						bn.setFancyid(mf);
						bn.setPnl(pnl2);
					}
					
					fancyExpo.add(bn);
				}else if(mf.contains(EXConstants.OddEvenMarket)) {
					
					Double pnl1=0.0;
					Double pnl2=0.0;
					
					for(ExMatchOddsBet bet :fancyList){


						if(mf.equalsIgnoreCase(bet.getMarketid())) {
                          
							if(bet.getIsback()) {
								pnl1= pnl1-(bet.getPnl()*bet.getPartnership()/100);
								pnl2= pnl2+(bet.getLiability()*bet.getPartnership()/100);
								
							}else {
								pnl1= pnl1+(bet.getLiability()*bet.getPartnership()/100);
								pnl2= pnl2-(bet.getPnl()*bet.getPartnership()/100);
							}
							
							
						}

					}
					
					ExFancyBook bn =new ExFancyBook();
					
					if(pnl1<=0) {
						bn.setFancyid(mf);
						bn.setPnl(pnl1);
					}else if(pnl2<=0) {
						bn.setFancyid(mf);
						bn.setPnl(pnl2);
					}
					fancyExpo.add(bn);
				}
				hm2.put(mf, fancyExpo);
				
				
				
					
					
				//}
				
				}
			
			
		return fancyExpo;	 
		}
	
	@RequestMapping(value = "/switchFancy", method = RequestMethod.POST)
	public ResponseEntity<Object> switchFancy(@RequestParam String fancyid, @RequestParam String mtype) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {

			MatchFancy mf = fancyRepository.findByFancyid(fancyid);
			if(mf!=null) {
			Event ev =	eventRepo.findByeventid(mf.getEventid());
			   if(mtype.equalsIgnoreCase("player")) {
				   mf.setMaxLiabilityPerMarket(ev.getPlayermaxbetfancy()*10);
					
			   }else {
				   mf.setMaxLiabilityPerMarket(ev.getMaxbetfancy()*10);
					
			   }
			   
				mf.setMtype(mtype);
				fancyRepository.save(mf);
			}
		//	fancyRepository
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy successfully Updated...");

			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

		}else {
			responseBean.setType("error");
			responseBean.setMessage("please login to contiue...");
			responseBean.setTitle("Oops...");
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value = "/updateMaxLibPerMarket", method = RequestMethod.POST)
	public ResponseEntity<Object> updateMaxLibPerMarket(@RequestParam String fancyid, @RequestParam Integer Lib) {
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {

			MatchFancy mf = fancyRepository.findByFancyid(fancyid);
			if(mf!=null) {
				mf.setMaxLiabilityPerMarket(Lib);
				fancyRepository.save(mf);
			}else {
				responseBean.setType("error");
				responseBean.setMessage("Fancy Not Active...");
				responseBean.setTitle("Oops...");
			}
		//	fancyRepository
			responseBean.setTitle("success");
			responseBean.setType("success");
			responseBean.setMessage("Fancy  successfully Updated...");

			return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value = "/checkBookMakerBackLayDiff/{eventid}", method = RequestMethod.GET)
	public ResponseEntity<Object> updateBookMakerBackLayDiff(@PathVariable Integer eventid) {
		HttpSession session = request.getSession();
		
		EXUser usersession = (EXUser) session.getAttribute("user");
		if (usersession != null) {
			EXMarketDiff bean = marketDiffRepo.findByeventid(eventid);
			
			
			return new ResponseEntity<Object>(bean, HttpStatus.OK);
			
		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	

	@RequestMapping(value = "/activeFancyNew/{eventId}/{Id}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> activeFancyNew(@PathVariable Integer eventId, @PathVariable String Id,@PathVariable Integer usertype) {

		ResponseBean responseBean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<String> list1 = new ArrayList<String>();
		JSONArray arr = new JSONArray();
		ArrayList<HashMap<String, Object>> hm3 = new ArrayList<HashMap<String, Object>>();
		 ArrayList<ExFancyBook> mfancyBookList =new  ArrayList<ExFancyBook>();
		List<String> fancyIds  = new ArrayList<String>();
		
			try {
				ArrayList<MatchFancy> fancyList = fancyRepository.findByEventidAndIsActive(eventId, true);

			 
				  
				for (MatchFancy fancy : fancyList) {
					
					
					fancyIds.add(fancy.getFancyid());
				}	
				
				if(fancyIds.size()>0) {
					mfancyBookList =	getFancyExposure(fancyIds,Id,usertype);
					
				}
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		return new ResponseEntity<Object>(mfancyBookList, HttpStatus.OK);
	}
	
	public ArrayList<ExFancyBook> getFancyExposureNew(List<String> fancyIds,String userid,Integer usertype) throws JSONException {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");
		ArrayList<ExFancyBook> fancyExpo = new ArrayList<ExFancyBook>();
		
		 fancyIds  = new ArrayList<String>();
		fancyIds.add("3041102712")	;
		
		LinkedHashMap<String, ArrayList<ExFancyBook>> hm2 = new LinkedHashMap<String, ArrayList<ExFancyBook>>();
		
		ArrayList<ExMatchOddsBet> fancyList = new ArrayList<>();
		
			if(usertype==4){
				fancyList= betlistDao.getFancyBookExposure("adminid", userid, fancyIds,"usr.adminpartership");			
			}else if(usertype==6){
				fancyList= betlistDao.getFancyBookExposure("adminid", userid, fancyIds,"usr.adminpartership");			
			}else if(usertype==1){
				fancyList =betlistDao.getFancyBookExposure("masterid", userid, fancyIds,"usr.masterpartership");		
			}else if(usertype==0){
				fancyList = betlistDao.getFancyBookExposure("supermasterid", userid,fancyIds,"usr.supermastepartnership");		
			}else if(usertype==5){
				fancyList =betlistDao.getFancyBookExposure("subadminid", userid, fancyIds,"usr.subadminpartnership");						
			}else if(usertype==2){
				fancyList= betlistDao.getFancyBookExposure("dealerid", userid, fancyIds,"usr.delearpartership");		
			}
			
			HashMap<String,Object> hm = new  HashMap<String,Object> ();
			
			for(String mf : fancyIds) {
				// fancyExpo = new ArrayList<ExFancyBook>();
				
				ArrayList<Integer> sortedlist = new ArrayList<>(); 
				
				if(mf.equalsIgnoreCase("3041102712")) {
					Integer mindds =0;
					Integer maxodds =0;
				
					
					ArrayList<ExMatchOddsBet> tempfancyList = new  ArrayList<ExMatchOddsBet>();
					ArrayList<ExFancyBook> fancyBook = new ArrayList<ExFancyBook>();
					
					
					if(fancyList.size()>0){
						
						for(ExMatchOddsBet bet :fancyList){
								
								
								if(mf.equalsIgnoreCase(bet.getMarketid())) {
									sortedlist.add(Integer.parseInt(df.format(bet.getOdds())));
									tempfancyList.add(bet);
								}
							
						}
						
						if(sortedlist.size()>0) {
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
						
					  }
					Double lowval = 0.00;
					
					for(int i = 0; i <fancyBook.size(); i++){
						if(fancyBook.get(i).getPnl()< lowval){
							lowval = fancyBook.get(i).getPnl();
						}
					}
					ExFancyBook bn =new ExFancyBook();
					bn.setFancyid(mf);
					bn.setPnl(lowval);
					fancyExpo.add(bn);
					hm2.put(mf, fancyExpo);
					
				}
				
			
			
				
				
				
			
				
			}
			
			
		return fancyExpo;	 
		}
	
	@RequestMapping(value = "/activeFancyNew1/{eventId}/{Id}/{usertype}", method = RequestMethod.GET)
	public ResponseEntity<Object> activeFancyNew1(@PathVariable Integer eventId, @PathVariable String Id,@PathVariable Integer usertype) {

		ResponseBean responseBean = new ResponseBean();

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ArrayList<String> list1 = new ArrayList<String>();
		JSONArray arr = new JSONArray();
		ArrayList<HashMap<String, Object>> hm3 = new ArrayList<HashMap<String, Object>>();
		 ArrayList<ExFancyBook> mfancyBookList =new  ArrayList<ExFancyBook>();
		List<String> fancyIds  = new ArrayList<String>();
		
			try {
				ArrayList<MatchFancy> fancyList = fancyRepository.findByEventidAndIsActive(eventId, true);

			 
				  
				for (MatchFancy fancy : fancyList) {
					
					fancyIds.add(fancy.getFancyid());
				}	
				
				if(fancyIds.size()>0) {
					mfancyBookList =	getFancyExposureNew(fancyIds,Id,usertype);
					
				}
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		return new ResponseEntity<Object>(mfancyBookList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/OddsApi/{eventId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> DaimondBookmakerManish(@PathVariable String eventId) {

		RestTemplate restTemplate = new RestTemplate();
		String session = restTemplate.getForObject("http://3.109.65.15/event-odds/odds/"+eventId, String.class);

		return new ResponseEntity<Object>(session.toString(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delCache", method = RequestMethod.GET)
	@CacheEvict(cacheNames="fancyExpoCache",allEntries=true)
	public ResponseEntity<Object> delCache() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
    @RequestMapping(value ="/scoreApiGo",method = RequestMethod.GET,produces= "application/json")
		public String  scoreApiGo(@RequestParam String evntId)
		{
		
		 try 
			{
			 
			   
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			
				headers.add("referer", "https://goexchange.bet/");
	            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
				ResponseEntity<String> Data = restTemplate.exchange("https://7777exch.com/cricscorecardnewtest.aspx?Eventid="+evntId, HttpMethod.GET
						,entity,String.class);
				String s2 = Data.getBody().toString().replace("<!DOCTYPE html>", "");		
				
				String s3 = s2.toString().replace("/css/scoreCard/sportradar.css", "https://7777exch.com/css/scoreCard/sportradar.css");		
				
				File filedata = new File("src/main/webapp/WEB-INF/view/pages/"+evntId+".jsp");
				FileWriter fwdata=new FileWriter(filedata.getAbsoluteFile()); 
				BufferedWriter bwdata=new BufferedWriter(fwdata);
				bwdata.write(s3); 
				bwdata.close();
				
			/*	LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
				hm.put("Lucky7AData", Lucky7AData.getBody());
				*/
				return s3.toString();
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	
	
	@RequestMapping(value = "/addMarket", method = RequestMethod.POST)
	// @CacheEvict(value = "leftMenuData", allEntries = true)
	public ResponseEntity<Object> addMarket(@Valid @RequestBody Market market) {

		HttpSession session = request.getSession(true);

		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {

			
			
			
			Market mark = eventMarketRepository.findByMarketidAndStatus(market.getMarketid(), true);
			
			Event ev = eventRepo.findByeventid(market.getEventid());
			
			if (mark == null) {

				if(!market.getMarketname().equalsIgnoreCase("Bookmaker")) {
					
					ArrayList<SelectionIds> selectionList = selectionIdRepository.findByMarketid(market.getMarketid());
					if (selectionList.size() == 0) {
						selectionList = selectionIdService.SaveSelectionId(market.getMarketid(),
								market.getEventid().toString());
					}
				}
				

				Date out = new Date();
				if(ev.getSeriesid()!=922982) {


					
					Integer  time=Integer.parseInt(market.getStartdate());
					out = new Date(time.longValue()*1000);

				}else {
					Integer  time=Integer.parseInt(market.getStartdate());
					out = new Date(time.longValue()*1000);
				}
				
				if(market.getMarketname().equalsIgnoreCase("Bookmaker") || market.getMarketname().equalsIgnoreCase("TOSS")) {
					RestTemplate restTemplate = new RestTemplate();
					String res = restTemplate.getForObject(EXConstants.RSApi+"sessionApi/virtualEventOdds/"+market.getEventid(), String.class);
					JSONArray ary = new JSONArray(new JSONObject(new JSONObject(res).getString("data")).getString("bookMaker"));
					
					for(int i=0; i<ary.length();i++) {
						
						if(new JSONObject(ary.get(i).toString()).getString("marketName").contains("Bookmaker")) {
							
							JSONArray runners = new JSONArray(new JSONObject(ary.get(i).toString()).getString("runners"));
							for(int k = 0; k<runners.length(); k++) {
								
								SelectionIds Idsbook=new SelectionIds();
								Idsbook.setMarketid(market.getMarketid());
								Idsbook.setSelectionid(runners.getJSONObject(k).getInt("secId"));
								Idsbook.setRunnerName(runners.getJSONObject(k).getString("runner"));
								Idsbook.setCreatedon(new Date());
								 
								selectionIdRepository.save(Idsbook);
							}
						}
						
						
					
				}
				}
			
			//	System.out.println(dtutil.convertBetfairToDate(out, "yyyy-MM-dd HH:mm:ss"));
				
				mark = new Market();
				mark.setStopMarketOdds(true);
				mark.setMarketid(market.getMarketid());
				mark.setMatchname(market.getMatchname());
				mark.setStatus(true);
				mark.setIsactive(true);
				mark.setIspause(true);
				mark.setIsResult(false);
				mark.setCricexchageid(market.getCricexchageid());
				if(market.getMarketname().equalsIgnoreCase("Bookmaker")) {
					mark.setOddsprovider("Bookmaker");
				}else {
					mark.setOddsprovider("Betfair");
				}
				
				mark.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				mark.setUpdatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
				mark.setMarketname(market.getMarketname());
				mark.setAppid(usersession.getAppid());
				mark.setEventid(market.getEventid());
				mark.setSeriesid(market.getSeriesid());
				mark.setSportid(market.getSportid());
				mark.setAddautoFancy(false);
				mark.setPauseByAdmin(false);
				mark.setOpendate(dtUtil.addHourToDate(out));
				mark.setInPlay(false);
				mark.setPauseByAdmin(true);
				mark.setiSfsmarkedInactive(false);
				mark.setIsresultFinished(false);
				mark.setIsautomatchActive(true);
				
				
				
				
			/*	if (market.getSportid() == 4) {
					bookmark = new Market();
					bookmark.setStopMarketOdds(true);
					bookmark.setMarketid(market.getMarketid() + "BM");
					bookmark.setMatchname(market.getMatchname());
					bookmark.setStatus(true);
					bookmark.setIsactive(true);
					bookmark.setIspause(true);
					bookmark.setIsResult(false);
					bookmark.setCricexchageid(market.getCricexchageid());
					bookmark.setOddsprovider(EXConstants.Bookmaker);
					bookmark.setCreatedon(
							dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					bookmark.setUpdatedon(
							dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
					bookmark.setMarketname(EXConstants.Bookmaker);
					bookmark.setAppid(usersession.getAppid());
					bookmark.setEventid(market.getEventid());
					bookmark.setSeriesid(market.getSeriesid());
					bookmark.setSportid(market.getSportid());
					bookmark.setAddautoFancy(false);
					bookmark.setPauseByAdmin(false);
					bookmark.setOpendate(dtUtil.addHourToDate(out));
					bookmark.setInPlay(false);
					bookmark.setPauseByAdmin(true);
					bookmark.setiSfsmarkedInactive(false);
					bookmark.setIsresultFinished(false);
					mark.setIsautomatchActive(false);
					// bookmark.setResultstatusCron(false);
					eventMarketRepository.save(bookmark);
				}*/

				if (eventMarketRepository.save(mark) != null) {

					responseBean.setType("success");
					responseBean.setMessage("Market added successfully");
					responseBean.setTitle("success");
					return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

				} else {

					responseBean.setType("error");
					responseBean.setMessage("Unable to add market");
					responseBean.setTitle("Oops...");
					return new ResponseEntity<Object>(responseBean, HttpStatus.CONFLICT);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/casinoBetLock/{tabletype}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Object> betLock(@PathVariable String tabletype,@PathVariable Integer userId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		if (usersession != null) {
			try {
			//	L
				CasinoBetLoack bean1 = casinoBetLockRepo.findByUserid(userId);
				if(bean1!=null) {
					bean1.setUserid(userId);
					bean1.setTabletype("B");
					bean1.setCreatedon(new Date());
					casinoBetLockRepo.save(bean1);
				}else {
					
					bean1 = new CasinoBetLoack();
					bean1.setUserid(userId);
					bean1.setTabletype(tabletype);
					bean1.setCreatedon(new Date());
					casinoBetLockRepo.save(bean1);
				}
			
				responseBean.setType("success");
				responseBean.setMessage("BetLock Success Fully..");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
			responseBean.setType("error");
			responseBean.setMessage("Some thing went Wrong..");
			responseBean.setTitle("Oops...");

		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@RequestMapping(value = "/casinoBetUnLock/{tabletype}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Object> casinoBetUnLock(@PathVariable String tabletype,@PathVariable Integer userId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		ResponseBean responseBean = new ResponseBean();
		//if (usersession != null) {
			try {
			//	L
				CasinoBetLoack bean1 = casinoBetLockRepo.findByUserid(userId);
				if(bean1!=null) {
					
					
					if(bean1.getTabletype().equalsIgnoreCase("B")) {
						if(tabletype.equalsIgnoreCase("L")) {
							bean1.setUserid(userId);
							bean1.setTabletype("V");
							bean1.setCreatedon(new Date());
							casinoBetLockRepo.save(bean1);
							
							
						}else if(tabletype.equalsIgnoreCase("V")) {
							bean1.setUserid(userId);
							bean1.setTabletype("L");
							bean1.setCreatedon(new Date());
							casinoBetLockRepo.save(bean1);
						}
					}else if(bean1.getTabletype().equalsIgnoreCase("V")) {
						if(tabletype.equalsIgnoreCase("V")) {
							casinoBetLockRepo.delete(bean1);
						}
					}else if(bean1.getTabletype().equalsIgnoreCase("L")) {
						if(tabletype.equalsIgnoreCase("L")) {
							casinoBetLockRepo.delete(bean1);
						}
					}
					
					responseBean.setType("success");
					responseBean.setMessage("BetUnLock Success Fully..");
					responseBean.setTitle("success");
				}else {
					responseBean.setType("error");
					responseBean.setMessage("Invalid User ...");
					responseBean.setTitle("Oops...");
				}
			
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			responseBean.setMessage("Somthing went wrong!");
			responseBean.setType("error");
			responseBean.setTitle("Oops...");
		
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/updateAddAutoFancy/{eventId}/{fancyType}", method = RequestMethod.POST)
	public ResponseEntity<Object> updateAddAutoFancy(@PathVariable Integer eventId,@PathVariable String fancyType) {
		HttpSession session = request.getSession();
		ResponseBean responseBean = new ResponseBean();
		EXUser usersession = (EXUser) session.getAttribute("user");
		log.info("usersession: " + usersession);
  	  if (usersession != null) {
			Event bean = eventRepo.findByeventid(eventId);
			
			 if(fancyType.equalsIgnoreCase(EXConstants.RS)) {
				 if(bean.getAddautoFancy()) {
					 bean.setAddautoFancy(false);
				 }else {
					 bean.setAddautoFancy(true);
				 }
			 }
			 
			 if(fancyType.equalsIgnoreCase(EXConstants.Diamond)) {
				 if(bean.getAddautoFancyDiamond()) {
					 bean.setAddautoFancyDiamond(false);
				 }else {
					 bean.setAddautoFancyDiamond(true);
				 }
			 }
				
				eventRepo.save(bean);
				responseBean.setMessage("Updated Chnaged Successfully!");
				responseBean.setType("success");
				responseBean.setTitle("Success");
			}

		

		return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/updateMarketOddsStatus/{marketid}/{oddsstatus}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateMarketOddsStatus(@PathVariable String marketid,@PathVariable String oddsstatus) {

		ResponseBean responseBean = new ResponseBean();
			HttpSession session = request.getSession();
		EXUser user = (EXUser) session.getAttribute("user");

		if (user != null) {
			Market markett = eventMarketRepository.findBymarketid(marketid);

			// EXSuperStockageMarket markett =
			// exSuperStockageMarketRepo.findByMarketid(market.getMarketid());
			if (markett != null) {
				if(oddsstatus.equalsIgnoreCase(OddSuspensionOnBm.both.toString())) {
					markett.setOdds_suspension_on_bm_status(OddSuspensionOnBm.both);
					
				}else if(oddsstatus.equalsIgnoreCase(OddSuspensionOnBm.off.toString())) {
					markett.setOdds_suspension_on_bm_status(OddSuspensionOnBm.off);
					
				}else if(oddsstatus.equalsIgnoreCase(OddSuspensionOnBm.ball_running.toString())) {
					markett.setOdds_suspension_on_bm_status(OddSuspensionOnBm.ball_running);
					
				}
				else if(oddsstatus.equalsIgnoreCase(OddSuspensionOnBm.suspended.toString())) {
					markett.setOdds_suspension_on_bm_status(OddSuspensionOnBm.suspended);
					
				}
				
				eventMarketRepository.save(markett);
				

				responseBean.setType("success");
				responseBean.setMessage("Market updated successfully");
				responseBean.setTitle("success");
				return new ResponseEntity<Object>(responseBean, HttpStatus.OK);

			}
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops...");
		return new ResponseEntity<Object>(responseBean, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/scoreApi/{eventId}")
	public ResponseEntity<String>  scoreApi(@PathVariable Integer eventId)
	{
		try
		{

			RestTemplate restTemplate = new RestTemplate();
			String cards = restTemplate.getForObject("https://internal-consumer-apis.jmk888.com/score-card/event/"+eventId, String.class);
			
			
			return new ResponseEntity<String>(cards, HttpStatus.OK);
			
		}
		catch(Exception e){
			log.error("error==>"+e);
		}
		return new ResponseEntity<String>( HttpStatus.OK);
	}
}
