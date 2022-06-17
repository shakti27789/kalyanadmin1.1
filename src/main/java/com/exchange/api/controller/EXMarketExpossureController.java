package com.exchange.api.controller;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBetMongo;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.SelectionIds;
import com.exchange.api.bean.Sport;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.bean.UserLiabilityMongo;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.dao.MarketDAO;
import com.exchange.api.dao.MarketExpoDAO;
import com.exchange.api.repositiry.AppChargeRepo;
import com.exchange.api.repositiry.BetRepository;
import com.exchange.api.repositiry.EXUserLoginRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.ExMatchOddsBetMongoRepos;
import com.exchange.api.repositiry.FancyResultRepository;
import com.exchange.api.repositiry.LiabilityRepository;
import com.exchange.api.repositiry.MarketRepository;
import com.exchange.api.repositiry.MarketResultRepository;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.api.repositiry.UserLiabilityMongoRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Acl.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class EXMarketExpossureController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	LiabilityRepository liabilityRepository;

	@Autowired
	BetRepository placebetrepository;

	@Autowired
	BetRepository exbetRepo;

	@Autowired
	MarketRepository marketrepo;

	@Autowired
	FancyResultRepository fancyrepo;

	@Autowired
	EXUserRepository userRepo;

	@Autowired
	AppChargeRepo appChargeRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	MarketResultRepository matchResultrepo;

	@Autowired
	MarketExpoDAO marketExpoDAO;

	@Autowired
	MarketDAO marketDao;

	@Autowired
	EXNativeQueryDao nativeQueryDao;

	@Autowired
	SportRepository sportRepo;

	@Autowired
	MarketRepository marketRepository;

	@Autowired
	SelectionIdRepository selectionIdRepository;

	@Autowired
	ExMatchOddsBetMongoRepos exMatchOddsBetRepo;

	private Firestore fb;

	@Autowired
	UserLiabilityMongoRepository userLibMongo;

	/*
	 * @RequestMapping(value="/getMyProfitT20/{marketid}",method=RequestMethod.GET)
	 * public ResponseEntity<Object> getMyProfitT20(@PathVariable String marketid)
	 * throws ParseException{
	 * 
	 * HttpSession session = request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user");
	 * 
	 * if(usersession!=null) {
	 * 
	 * LinkedHashMap<String, Object> hm =new LinkedHashMap<String, Object>();
	 * 
	 * 
	 * 
	 * try{ if(usersession!=null){
	 * 
	 * 
	 * Integer selection1 =1; Integer selection2 =2; Integer selection3 =3;
	 * 
	 * Double pnl1 =0.0; Double pnl2 =0.0; Double pnl3 =0.0;
	 * 
	 * ArrayList<UserLiabilityMongo> lib =
	 * userLibRepoMongo.findByMarketidAndIsactive(marketid, true) ;
	 * 
	 * 
	 * for(UserLiabilityMongo bean : lib){
	 * 
	 * Double temppnl1 =0.0; Double temppnl3 =0.0; ArrayList<ExMatchOddsBetMongo>
	 * betlist =
	 * exMatchOddsBetRepo.findByMarketidAndIsactiveAndUserid(bean.getMarketid(),
	 * true,bean.getUserid());
	 * 
	 * for(ExMatchOddsBetMongo bt :betlist) { if(bt.getSelectionid() == selection1 )
	 * { temppnl1 =temppnl1+ bt.getLiability(); }else if(bt.getSelectionid() ==
	 * selection3 ) { temppnl3 =temppnl3+ bt.getLiability(); } } pnl1= pnl1+
	 * (temppnl1 * (bean.getAdminpartnership()/100)); pnl3= pnl3+ (temppnl3 *
	 * (bean.getAdminpartnership()/100));
	 * 
	 * 
	 * } if(usersession.getUsertype() ==4) { hm.put("pnl1", pnl1); hm.put("pnl3",
	 * pnl3);
	 * 
	 * }else { hm.put("pnl1", 0.0); hm.put("pnl3", 0.0);
	 * 
	 * } hm.put("selection1", selection1); hm.put("selection3", selection3);
	 * 
	 * return new ResponseEntity<Object>(hm,HttpStatus.OK);
	 * 
	 * 
	 * } }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 */

	@RequestMapping(value = "/getUserProfit", method = RequestMethod.POST)
	public ResponseEntity<Object> getUserProfit(@RequestBody LinkedHashMap<String, String> filterData) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");
		String idtype = "";
		if (usersession != null) {
			ArrayList<UserLiability> usersLiability = new ArrayList<>();
			ArrayList<UserLiability> liability = new ArrayList<>();
			new ArrayList<>();
			if (usersession.getUsertype() == 4) {
				idtype = "adminid";
				userRepo.findByparentid(usersession.getParentid());
			} else if (usersession.getUsertype() == 1) {
				idtype = "masterid";
			} else if (usersession.getUsertype() == 5) {
				idtype = "subadminid";
			} else if (usersession.getUsertype() == 0) {
				idtype = "supermasterid";
			} else {
				idtype = "dealerid";
			}

			new ArrayList<String>();
			new HashMap<String, String>();
			new UserLiability();
			// marketExpoDAO.pnlparentIdWise(usersession.getId().toString(),usersession.getUsertype(),Integer.parseInt(filterData.get("eventid")));
			usersLiability.addAll(marketExpoDAO.getUserList(usersession.getId().toString(), idtype,
					Integer.parseInt(filterData.get("eventid"))));
			if (!usersLiability.isEmpty()) {
				for (int i = 0; i < usersLiability.size(); i++) {
					UserLiability user = new UserLiability();
					user = usersLiability.get(i);

					if ((filterData.get("table").equals("1"))) {
						if (filterData.get("type").equals("4")) {
							user.setPnl1(Math.round(
									(user.getPnl1().doubleValue() * (user.getAdminpartnership().doubleValue() / 100.0f))
											* 100.0)
									/ 100.0);
							user.setPnl2(Math.round(
									(user.getPnl2().doubleValue() * (user.getAdminpartnership().doubleValue() / 100.0f))
											* 100.0)
									/ 100.0);
						} else if (filterData.get("type").equals("1")) {
							user.setPnl1(Math.round((user.getPnl1().doubleValue()
									* (user.getMasterpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
							user.setPnl2(Math.round((user.getPnl2().doubleValue()
									* (user.getMasterpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
						} else if (filterData.get("type").equals("5")) {
							user.setPnl1(Math.round((user.getPnl1().doubleValue()
									* (user.getSubadminpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
							user.setPnl2(Math.round((user.getPnl2().doubleValue()
									* (user.getSubadminpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
						} else if (filterData.get("type").equals("0")) {
							user.setPnl1(Math
									.round((user.getPnl1().doubleValue()
											* (user.getSupermasterpartnership().doubleValue() / 100.0f)) * 100.0)
									/ 100.0);
							user.setPnl2(Math
									.round((user.getPnl2().doubleValue()
											* (user.getSupermasterpartnership().doubleValue() / 100.0f)) * 100.0)
									/ 100.0);
						} else {
							user.setPnl1(Math.round((user.getPnl1().doubleValue()
									* (user.getDealerpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
							user.setPnl2(Math.round((user.getPnl2().doubleValue()
									* (user.getDealerpartnership().doubleValue() / 100.0f)) * 100.0) / 100.0);
						}
						if (user.getPnl3() != null) {
							if (filterData.get("type").equals("4")) {
								user.setPnl3(Math
										.round((user.getPnl3().doubleValue()
												* (user.getAdminpartnership().doubleValue() / 100.0f)) * 100.0)
										/ 100.0);
							} else if (filterData.get("type").equals("1")) {
								user.setPnl3(Math
										.round((user.getPnl3().doubleValue()
												* (user.getMasterpartnership().doubleValue() / 100.0f)) * 100.0)
										/ 100.0);
							} else if (filterData.get("type").equals("5")) {
								user.setPnl3(Math
										.round((user.getPnl3().doubleValue()
												* (user.getSubadminpartnership().doubleValue() / 100.0f)) * 100.0)
										/ 100.0);
							} else if (filterData.get("type").equals("0")) {
								user.setPnl3(Math
										.round((user.getPnl3().doubleValue()
												* (user.getSupermasterpartnership().doubleValue() / 100.0f)) * 100.0)
										/ 100.0);
							} else {
								user.setPnl3(Math
										.round((user.getPnl3().doubleValue()
												* (user.getDealerpartnership().doubleValue() / 100.0f)) * 100.0)
										/ 100.0);
							}
						} else {
							user.setPnl3(0.00);
						}

					} else {
						if (user.getPnl3() == null) {
							user.setPnl3(0.00);
						}
					}
					user.setPnl1(0.00 - user.getPnl1());
					user.setPnl2(0.00 - user.getPnl2());
					user.setPnl3(0.00 - user.getPnl3());
					user.setPnl1(Double.valueOf(df.format(user.getPnl1())));
					user.setPnl2(Double.valueOf(df.format(user.getPnl2())));
					user.setPnl3(Double.valueOf(df.format(user.getPnl3())));
					userRepo.findByUserid(user.getUserid());

					liability.add(user);

				}
				return new ResponseEntity<Object>(liability, HttpStatus.OK);
			}
			return new ResponseEntity<Object>(liability, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getUserProfitNew", method = RequestMethod.POST)
	public ResponseEntity<Object> getUserProfitNew(@RequestBody LinkedHashMap<String, String> filterData) {
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0.00");
		HashMap<String, ArrayList<UserLiability>> hm = new HashMap<String, ArrayList<UserLiability>>();
		String idtype = "";

		if (usersession != null) {
			ArrayList<Market> mktlist = marketRepository
					.findByEventidAndStatus(Integer.parseInt(filterData.get("eventid")), true);
			if (mktlist.size() > 0) {
				for (Market mkt : mktlist) {
					ArrayList<UserLiability> usersLiability = new ArrayList<>();

					ArrayList<UserLiability> usersLiabilitytemp = new ArrayList<>();
					UserLiability ulib = new UserLiability();
					new ArrayList<>();

					List<String> ids = new ArrayList<String>();
					List<String> userids = new ArrayList<String>();
					if (usersession.getUsertype() == 4) {
						idtype = "adminid";
						userRepo.findByparentid(usersession.getParentid());
					} else if (usersession.getUsertype() == 6) {
						idtype = "adminid";
						userRepo.findByparentid("-1");
					} else if (usersession.getUsertype() == 1) {
						idtype = "masterid";
					} else if (usersession.getUsertype() == 5) {
						idtype = "subadminid";
					} else if (usersession.getUsertype() == 0) {
						idtype = "supermasterid";
					} else {
						idtype = "dealerid";
					}

					new ArrayList<String>();
					new HashMap<String, String>();
					new UserLiability();
					ArrayList<EXUser> userlist = userRepo.findByParentid(usersession.getId().toString());

					if (usersession.getUsertype() == 4) {
						for (EXUser bean : userlist) {
							if (bean.getUsertype() == 5) {
								ids.add(bean.getId().toString());
							}
						}

						usersLiability = marketExpoDAO.pnlparentIdWise(ids, 5,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());

					} else if (usersession.getUsertype() == 6) {
						userlist = userRepo.findByParentid(usersession.getParentid().toString());

						for (EXUser bean : userlist) {
							if (bean.getUsertype() == 5) {
								// ulib=marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),4,mkt.getMarketid());
								ids.add(bean.getId().toString());
							}
						}
						usersLiability = marketExpoDAO.pnlparentIdWise(ids, 5,
								Integer.parseInt(filterData.get("eventid")), 4, mkt.getMarketid());

					}

					else if (usersession.getUsertype() == 5) {
						// ArrayList<String> supermaterlist =
						for (EXUser bean : userlist) {

							if (bean.getUsertype() == 0) {
								ids.add(bean.getId().toString());

							} else if (bean.getUsertype() == 3) {

								userids.add(bean.getUserid());

							}

						}

						usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(userids, 3,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());
						usersLiability.addAll(usersLiabilitytemp);

					} else if (usersession.getUsertype() == 0) {
						for (EXUser bean : userlist) {
							// ulib
							// =marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
							if (bean.getUsertype() == 1) {

								// usersLiability.add(marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid")));
								// ulib
								// =marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
								ids.add(bean.getId().toString());

							} else if (bean.getUsertype() == 2) {

								// usersLiability.add(marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid")));
								// ulib
								// =marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));

							} else if (bean.getUsertype() == 3) {

								// usersLiability.add(marketExpoDAO.pnlparentIdWise(bean.getUserid(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid")));
								// ulib
								// =marketExpoDAO.pnlparentIdWise(bean.getUserid(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
								userids.add(bean.getUserid().toString());

							}

						}

						usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(ids, 1,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());
						usersLiability.addAll(usersLiabilitytemp);

						usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(userids, 3,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());
						usersLiability.addAll(usersLiabilitytemp);
					} else if (usersession.getUsertype() == 1) {
						for (EXUser bean : userlist) {
							// ulib =
							// marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
							if (bean.getUsertype() == 2) {

								// usersLiability.add(marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid")));
								// ulib =
								// marketExpoDAO.pnlparentIdWise(bean.getId().toString(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
								ids.add(bean.getId().toString());

							} else if (bean.getUsertype() == 3) {

								// usersLiability.add(marketExpoDAO.pnlparentIdWise(bean.getUserid(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid")));
								// ulib =
								// marketExpoDAO.pnlparentIdWise(bean.getUserid(),bean.getUsertype(),Integer.parseInt(filterData.get("eventid")),usersession.getUsertype(),filterData.get("marketid"));
								userids.add(bean.getUserid().toString());

							}
						}

						usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(ids, 2,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());
						usersLiability.addAll(usersLiabilitytemp);

						usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(userids, 3,
								Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
								mkt.getMarketid());
						usersLiability.addAll(usersLiabilitytemp);
					} else if (usersession.getUsertype() == 2) {
						for (EXUser bean : userlist) {
							userids.add(bean.getUserid());

							usersLiabilitytemp = marketExpoDAO.pnlparentIdWise(userids, 3,
									Integer.parseInt(filterData.get("eventid")), usersession.getUsertype(),
									mkt.getMarketid());
							usersLiability.addAll(usersLiabilitytemp);
						}
					}

					if (mkt.getMarketname().equalsIgnoreCase(EXConstants.MatchOdds)) {
						hm.put("bf", usersLiability);
					} else if (mkt.getMarketname().equalsIgnoreCase(EXConstants.Bookmaker)) {
						hm.put("bm", usersLiability);
					}

				}
				return new ResponseEntity<Object>(hm, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/netExposure/{inPlay}/{sportId}", method = RequestMethod.GET, produces = "application/json")
	// @Cacheable(value = "netExposure")
	public ResponseEntity<Object> netExposure(@PathVariable("inPlay") Boolean inPlay,
			@PathVariable("sportId") Integer sportId) {

		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		DecimalFormat df = new DecimalFormat("#0");

		ArrayList<LinkedHashMap<String, Object>> list1 = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> sportmap = new LinkedHashMap<String, Object>();

		LinkedHashMap<String, Object> seriesmap = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> serieslist = new ArrayList<LinkedHashMap<String, Object>>();
		ArrayList<Integer> seriesid = new ArrayList<Integer>();

		LinkedHashMap<String, Object> matchmap = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> matchlist = new ArrayList<LinkedHashMap<String, Object>>();

		LinkedHashMap<String, Object> marketmap = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> marketlst = new ArrayList<LinkedHashMap<String, Object>>();

		LinkedHashMap<String, Object> teammap = new LinkedHashMap<String, Object>();
		ArrayList<LinkedHashMap<String, Object>> teamlist = new ArrayList<LinkedHashMap<String, Object>>();

		Sport sport = sportRepo.findBySportIdAndType(sportId, "Betfair");
		sportmap = new LinkedHashMap<String, Object>();
		sportmap.put("sportId", sport.getSportId());
		sportmap.put("sportName", sport.getName());

		EXDateUtil dtUtil = new EXDateUtil();

		try {

			// ArrayList<Market> marketlist =
			// nativeQueryDao.leftMenuData(sport.getSportId(),"mkt.seriesid",null,inPlay);
			// if(marketlist.size()>0){
			// serieslist = new ArrayList<LinkedHashMap<String,Object>> ();

			matchlist = new ArrayList<LinkedHashMap<String, Object>>();
			ArrayList<Market> marketlist2 = nativeQueryDao.leftMenuData(sport.getSportId(), "mkt.eventid", null,
					inPlay);
			if (marketlist2.size() > 0) {
				for (Market mrktbean : marketlist2) {
					ArrayList<Market> mrktb = new ArrayList<Market>();

					/*
					 * if(inPlay){ mrktb =
					 * marketRepository.findByEventidAndStatusAndInPlay(mrktbean.getEventid(),
					 * true,true); }else{
					 */
					mrktb = marketRepository.findByEventidAndStatus(mrktbean.getEventid(), true);
					// }
					marketlst = new ArrayList<LinkedHashMap<String, Object>>();

					if (mrktb.size() > 0) {
						for (Market mrk : mrktb) {
							teamlist = new ArrayList<LinkedHashMap<String, Object>>();

							matchmap = new LinkedHashMap<String, Object>();
							matchmap.put("marketId", mrktbean.getMarketid());
							matchmap.put("matchId", mrktbean.getEventid());
							matchmap.put("matchName", mrktbean.getMatchname());
							DateFormat dateFormat = new SimpleDateFormat(EXConstants.DATE_YYYY_MM_DD_HMS);

							String strDate = dateFormat.format(dtUtil.addMinusToDate(mrk.getOpendate()));
							matchmap.put("openDate", strDate);
							marketmap = new LinkedHashMap<String, Object>();
							marketmap.put("marketName", mrk.getMarketname());
							marketmap.put("marketId", mrk.getMarketid());
							marketmap.put("matchId", mrk.getEventid());
							marketmap.put("sportId", mrk.getSportid());
							marketmap.put("marketOddStatus", mrk.getStopMarketOdds());
							String partnership = null;

							String gropuBypartnership = null;
							Integer parentId = 0;
							if (usersession.getUsertype() == 4) {
								partnership = "lib.adminpartnership/100";
								gropuBypartnership = "lib.adminpartnership";
								parentId = usersession.getId();
							} else if (usersession.getUsertype() == 6) {
								partnership = "lib.adminpartnership/100";
								gropuBypartnership = "lib.adminpartnership";
								parentId = Integer.parseInt(usersession.getParentid());
							} else if (usersession.getUsertype() == 5) {
								partnership = "lib.subadminpartnership/100";
								gropuBypartnership = "lib.subadminpartnership";
								parentId = usersession.getId();
							} else if (usersession.getUsertype() == 0) {
								partnership = "lib.supermasterpartnership/100";
								gropuBypartnership = "lib.supermasterpartnership";
								parentId = usersession.getId();
							} else if (usersession.getUsertype() == 1) {
								partnership = "lib.masterpartnership/100";
								gropuBypartnership = "lib.masterpartnership";
								parentId = usersession.getId();
							} else {
								partnership = "lib.dealerpartnership/100";
								gropuBypartnership = "lib.dealerpartnership";
								parentId = usersession.getId();
							}

							ArrayList<UserLiability> usersLiability = nativeQueryDao.userPnl(mrk.getMarketid(),
									parentId.toString(), partnership, usersession.getUsertype(), gropuBypartnership);
							UserLiability lib = null;
							if (usersLiability.size() > 0) {

								Double pnl1 = 0.0;
								Double pnl2 = 0.0;
								Double pnl3 = 0.0;
								Integer selection1 = 0;
								Integer selection2 = 0;
								Integer selection3 = 0;
								for (UserLiability libbean : usersLiability) {
									pnl1 = pnl1 + libbean.getPnl1();
									pnl2 = pnl2 + libbean.getPnl2();
									pnl3 = pnl3 + libbean.getPnl3();
									selection1 = libbean.getSelection1();
									selection2 = libbean.getSelection2();
									selection3 = libbean.getSelection3();

								}
								lib = new UserLiability();
								lib.setMarketid(mrk.getMarketid());
								lib.setPnl1(Double.valueOf(df.format(pnl1)));
								lib.setPnl2(Double.valueOf(df.format(pnl2)));
								lib.setPnl3(Double.valueOf(df.format(pnl3)));
								lib.setSelection1(selection1);
								lib.setSelection2(selection2);
								lib.setSelection3(selection3);

							} else {

							}

							if (lib != null) {

								// System.out.println( lib.getMarketid());
								if (!lib.getMarketid().equalsIgnoreCase("1.194720868")) {

									// System.out.println(lib.getMarketid());

									teammap = new LinkedHashMap<String, Object>();
									teammap.put("selectionId", lib.getSelection1());
									teammap.put("selectionName", selectionIdRepository
											.findBySelectionidAndMarketid(lib.getSelection1(), lib.getMarketid())
											.getRunnerName());
									teammap.put("pnl", -Double.valueOf(df.format(lib.getPnl1())));
									teamlist.add(teammap);

									teammap = new LinkedHashMap<String, Object>();
									teammap.put("selectionId", lib.getSelection1());
									teammap.put("selectionName", selectionIdRepository
											.findBySelectionidAndMarketid(lib.getSelection2(), lib.getMarketid())
											.getRunnerName());
									teammap.put("pnl", -Double.valueOf(df.format(lib.getPnl2())));
									teamlist.add(teammap);
									if (lib.getSelection3() != 0) {

										teammap = new LinkedHashMap<String, Object>();
										teammap.put("selectionId", lib.getSelection3());
										teammap.put("selectionName", selectionIdRepository
												.findBySelectionidAndMarketid(lib.getSelection3(), lib.getMarketid())
												.getRunnerName());
										teammap.put("pnl", -Double.valueOf(df.format(lib.getPnl3())));

										teamlist.add(teammap);
									}
								}else {
									ArrayList<SelectionIds> selIds = selectionIdRepository.findByMarketid(lib.getMarketid());
									for(SelectionIds bn : selIds) {
										teammap = new LinkedHashMap<String, Object>();
										teammap.put("selectionId", bn.getSelectionid());
										teammap.put("selectionName",bn.getRunnerName());
										teammap.put("pnl", 0.0);
										teamlist.add(teammap);
									}
								}

								marketmap.put("team", teamlist);
							} else {

								ArrayList<SelectionIds> selIds = selectionIdRepository.findByMarketid(mrk.getMarketid());

							System.out.println("mid==>"+mrk.getMarketid());
								teammap = new LinkedHashMap<String, Object>();
								teammap.put("selectionId", selIds.get(0).getSelectionid());
								teammap.put("selectionName", selIds.get(0).getRunnerName());
								teammap.put("pnl", 0);
								teamlist.add(teammap);

								teammap = new LinkedHashMap<String, Object>();
								teammap.put("selectionId", selIds.get(1).getSelectionid());
								teammap.put("selectionName", selIds.get(1).getRunnerName());
								teammap.put("pnl", 0);
								teamlist.add(teammap);
								if (selIds.size() > 2) {

									teammap = new LinkedHashMap<String, Object>();
									teammap.put("selectionId", selIds.get(2).getSelectionid());
									teammap.put("selectionName", selIds.get(2).getRunnerName());
									teammap.put("pnl", 0);
									teamlist.add(teammap);
								}
								marketmap.put("team", teamlist);

							}
							marketlst.add(marketmap);
						}
					}
					matchmap.put("market", marketlst);
					matchlist.add(matchmap);
				}
			}

			sportmap.put("match", matchlist);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		list1.add(sportmap);

		return new ResponseEntity<Object>(list1, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value="/getMyProfitTeenPati20",method=RequestMethod.POST)
	 * public ResponseEntity<Object> getMyProfitTeenPati20(@RequestBody
	 * LinkedHashMap<String,String> requestData) throws ParseException{
	 * 
	 * HttpSession session = request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user"); EXDateUtil dtutil = new EXDateUtil(); String
	 * partnership =null; DecimalFormat df = new DecimalFormat("#0");
	 * 
	 * String gropuBypartnership =null; Integer parentId =0; String serchBy =null;
	 * if(usersession!=null) { if(usersession.getUsertype() ==4){ // partnership =
	 * "lib.adminpartnership/100"; gropuBypartnership ="lib.adminpartnership";
	 * parentId = usersession.getId(); partnership = "adminpartnership"; serchBy
	 * ="adminid"; }else if(usersession.getUsertype() ==6){ // partnership =
	 * "lib.adminpartnership/100"; gropuBypartnership ="lib.adminpartnership";
	 * parentId =Integer.parseInt(usersession.getParentid()); partnership =
	 * "adminpartnership"; serchBy ="adminid"; }else if(usersession.getUsertype()
	 * ==5){ //partnership = "lib.subadminpartnership/100"; gropuBypartnership
	 * ="lib.subadminpartnership"; parentId = usersession.getId(); partnership =
	 * "subadminpartnership"; serchBy ="subadminid"; }else
	 * if(usersession.getUsertype() ==0){ //partnership =
	 * "lib.supermasterpartnership/100"; gropuBypartnership
	 * ="lib.supermasterpartnership"; parentId = usersession.getId(); partnership =
	 * "supermasterpartnership"; serchBy ="supermasterid"; }else
	 * if(usersession.getUsertype() ==1){ //partnership =
	 * "lib.masterpartnership/100"; gropuBypartnership ="lib.masterpartnership";
	 * parentId = usersession.getId(); partnership = "masterpartnership"; serchBy
	 * ="masterid"; }else { // partnership = "lib.dealerpartnership/100";
	 * gropuBypartnership ="lib.dealerpartnership"; parentId = usersession.getId();
	 * partnership = "dealerpartnership"; serchBy ="dealerid"; }
	 * ArrayList<UserLiability> laibility= new ArrayList<UserLiability>();
	 * LinkedHashMap<String, Object> hm =new LinkedHashMap<String, Object>();
	 * 
	 * 
	 * 
	 * try{ if(usersession!=null){ ArrayList<Market> mrkt =
	 * marketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * requestData.get("eventId")), true, true);
	 * 
	 * 
	 * 
	 * 
	 * if(mrkt.size()>0){ for(Market bean : mrkt){
	 * 
	 * 
	 * 
	 * ClassLoader classLoader = getClass().getClassLoader(); InputStream
	 * inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
	 * FirebaseOptions firebaseOptions;
	 * 
	 * firebaseOptions = new FirebaseOptions.Builder()
	 * .setCredentials(GoogleCredentials.fromStream(inputStream))
	 * .setDatabaseUrl(EXConstants.firestoreDb) .build();
	 * if(FirebaseApp.getApps().isEmpty())
	 * FirebaseApp.initializeApp(firebaseOptions); fb =
	 * com.google.firebase.cloud.FirestoreClient.getFirestore();
	 * 
	 * 
	 * 
	 * ApiFuture<QuerySnapshot> future =
	 * fb.collection("t_libility").whereEqualTo(serchBy,
	 * parentId.toString()).whereEqualTo("marketid", bean.getMarketid()).get();
	 * List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	 * ArrayList<UserLiability> usersLiabilityNew = new ArrayList<UserLiability>();
	 * UserLiability libb = null; ArrayList<UserLiability> usersLiability = new
	 * ArrayList<UserLiability>(); Double pnl1 =0.0; Double pnl2 =0.0; Double pnl3
	 * =0.0; Integer selection1 =0; Integer selection2 =0; Integer selection3 =0;
	 * String marketId =null; for (QueryDocumentSnapshot document : documents) {
	 * 
	 * libb = new UserLiability();
	 * 
	 * 
	 * libb.setPnl1(document.getDouble("pnl1")*document.getDouble(partnership)/100);
	 * libb.setPnl2(document.getDouble("pnl2")*document.getDouble(partnership)/100);
	 * libb.setPnl3(document.getDouble("pnl3")*document.getDouble(partnership)/100);
	 * 
	 * 
	 * libb.setSelection1(Integer.parseInt(document.get("selection1").toString()));
	 * libb.setSelection2(Integer.parseInt(document.get("selection2").toString()));
	 * libb.setSelection3(Integer.parseInt(document.get("selection3").toString()));
	 * libb.setMarketid(document.getString("marketid")); usersLiability.add(libb);
	 * 
	 * pnl1 =pnl1+libb.getPnl1(); pnl2 =pnl2+libb.getPnl2(); pnl3
	 * =pnl3+libb.getPnl3(); selection1 =libb.getSelection1();
	 * selection2=libb.getSelection2(); selection3=libb.getSelection3(); marketId
	 * =libb.getMarketid(); }
	 * 
	 * 
	 * 
	 * UserLiability lib = new UserLiability(); if(usersLiability.size()>0){
	 * 
	 * 
	 * 
	 * lib.setMarketid(marketId); lib.setPnl1(Double.valueOf(df.format(-pnl1)));
	 * lib.setPnl2(Double.valueOf(df.format(-pnl2)));
	 * lib.setPnl3(Double.valueOf(df.format(-pnl3))); lib.setSelection1(selection1);
	 * lib.setSelection2(selection2); lib.setSelection3(selection3);
	 * laibility.add(lib); } } return new
	 * ResponseEntity<Object>(laibility,HttpStatus.OK); }
	 * 
	 * } }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(value="/getMyProfit",method=RequestMethod.POST) public
	 * ResponseEntity<Object> getMyProfit(@RequestBody LinkedHashMap<String,String>
	 * requestData) throws ParseException{
	 * 
	 * HttpSession session = request.getSession(true); EXUser usersession =(EXUser)
	 * session.getAttribute("user"); EXDateUtil dtutil = new EXDateUtil(); String
	 * partnership =null; DecimalFormat df = new DecimalFormat("#0");
	 * 
	 * String gropuBypartnership =null; Integer parentId =0; String serchBy =null;
	 * if(usersession!=null) { if(usersession.getUsertype() ==4){
	 * 
	 * gropuBypartnership ="lib.adminpartnership"; parentId = usersession.getId();
	 * partnership = "adminpartnership"; serchBy ="adminid"; }else
	 * if(usersession.getUsertype() ==6){
	 * 
	 * gropuBypartnership ="lib.adminpartnership"; parentId
	 * =Integer.parseInt(usersession.getParentid()); partnership =
	 * "adminpartnership"; serchBy ="adminid"; }else if(usersession.getUsertype()
	 * ==5){
	 * 
	 * gropuBypartnership ="lib.subadminpartnership"; parentId =
	 * usersession.getId(); partnership = "subadminpartnership"; serchBy
	 * ="subadminid"; }else if(usersession.getUsertype() ==0){ gropuBypartnership
	 * ="lib.supermasterpartnership"; parentId = usersession.getId(); partnership =
	 * "supermasterpartnership"; serchBy ="supermasterid"; }else
	 * if(usersession.getUsertype() ==1){
	 * 
	 * gropuBypartnership ="lib.masterpartnership"; parentId = usersession.getId();
	 * partnership = "masterpartnership"; serchBy ="masterid"; }else {
	 * 
	 * gropuBypartnership ="lib.dealerpartnership"; parentId = usersession.getId();
	 * partnership = "dealerpartnership"; serchBy ="dealerid"; }
	 * ArrayList<UserLiability> laibility= new ArrayList<UserLiability>();
	 * LinkedHashMap<String, Object> hm =new LinkedHashMap<String, Object>();
	 * 
	 * 
	 * 
	 * try{ if(usersession!=null){ ArrayList<Market> mrkt =
	 * marketRepository.findByEventidAndIsactiveAndStatus(Integer.parseInt(
	 * requestData.get("eventId")), true, true);
	 * 
	 * 
	 * 
	 * 
	 * if(mrkt.size()>0){ for(Market bean : mrkt){
	 * 
	 * 
	 * 
	 * ClassLoader classLoader = getClass().getClassLoader(); InputStream
	 * inputStream = classLoader.getResourceAsStream(EXConstants.firestoreFileName);
	 * FirebaseOptions firebaseOptions;
	 * 
	 * firebaseOptions = new FirebaseOptions.Builder()
	 * .setCredentials(GoogleCredentials.fromStream(inputStream))
	 * .setDatabaseUrl(EXConstants.firestoreDb) .build();
	 * if(FirebaseApp.getApps().isEmpty())
	 * FirebaseApp.initializeApp(firebaseOptions); fb =
	 * com.google.firebase.cloud.FirestoreClient.getFirestore();
	 * 
	 * 
	 * 
	 * ApiFuture<QuerySnapshot> future =
	 * fb.collection("t_libility").whereEqualTo(serchBy,
	 * parentId.toString()).whereEqualTo("marketid", bean.getMarketid()).get();
	 * List<QueryDocumentSnapshot> documents = future.get().getDocuments();
	 * ArrayList<UserLiability> usersLiabilityNew = new ArrayList<UserLiability>();
	 * UserLiability libb = null; ArrayList<UserLiability> usersLiability = new
	 * ArrayList<UserLiability>(); Double pnl1 =0.0; Double pnl2 =0.0; Double pnl3
	 * =0.0; Integer selection1 =0; Integer selection2 =0; Integer selection3 =0;
	 * String marketId =null; for (QueryDocumentSnapshot document : documents) {
	 * 
	 * libb = new UserLiability();
	 * 
	 * 
	 * libb.setPnl1(document.getDouble("pnl1")*document.getDouble(partnership)/100);
	 * libb.setPnl2(document.getDouble("pnl2")*document.getDouble(partnership)/100);
	 * libb.setPnl3(document.getDouble("pnl3")*document.getDouble(partnership)/100);
	 * 
	 * 
	 * libb.setSelection1(Integer.parseInt(document.get("selection1").toString()));
	 * libb.setSelection2(Integer.parseInt(document.get("selection2").toString()));
	 * libb.setSelection3(Integer.parseInt(document.get("selection3").toString()));
	 * libb.setMarketid(document.getString("marketid")); usersLiability.add(libb);
	 * 
	 * pnl1 =pnl1+libb.getPnl1(); pnl2 =pnl2+libb.getPnl2(); pnl3
	 * =pnl3+libb.getPnl3(); selection1 =libb.getSelection1();
	 * selection2=libb.getSelection2(); selection3=libb.getSelection3(); marketId
	 * =libb.getMarketid(); }
	 * 
	 * 
	 * UserLiability lib = new UserLiability(); if(usersLiability.size()>0){
	 * 
	 * 
	 * 
	 * lib.setMarketid(marketId); lib.setPnl1(Double.valueOf(df.format(-pnl1)));
	 * lib.setPnl2(Double.valueOf(df.format(-pnl2)));
	 * lib.setPnl3(Double.valueOf(df.format(-pnl3))); lib.setSelection1(selection1);
	 * lib.setSelection2(selection2); lib.setSelection3(selection3);
	 * laibility.add(lib); } } return new
	 * ResponseEntity<Object>(laibility,HttpStatus.OK); }
	 * 
	 * } }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	 * 
	 * }
	 */

	@RequestMapping(value = "/getMyProfitMongo", method = RequestMethod.POST)
	public ResponseEntity<Object> getMyProfitMongo(@RequestBody LinkedHashMap<String, String> requestData)
			throws ParseException {

		DecimalFormat df = new DecimalFormat("#0");

		String parentId = null;
		;
		ArrayList<UserLiabilityMongo> lib1 = new ArrayList<UserLiabilityMongo>();
		parentId = requestData.get("id").toString();

		ArrayList<UserLiability> laibility = new ArrayList<UserLiability>();

		try {
			ArrayList<Market> mrkt = marketRepository
					.findByEventidAndIsactiveAndStatus(Integer.parseInt(requestData.get("eventId")), true, true);

			if (mrkt.size() > 0) {
				for (Market bean : mrkt) {

					if (requestData.get("usertype").equalsIgnoreCase("4")) {

						lib1 = userLibMongo.findByAdminidAndMarketid(parentId.toString(), bean.getMarketid());

					} else if (requestData.get("usertype").equalsIgnoreCase("6")) {

						lib1 = userLibMongo.findByAdminidAndMarketid("1", bean.getMarketid());

					} else if (requestData.get("usertype").equalsIgnoreCase("5")) {

						lib1 = userLibMongo.findBySubadminidAndMarketid(parentId.toString(), bean.getMarketid());

					} else if (requestData.get("usertype").equalsIgnoreCase("0")) {
						lib1 = userLibMongo.findBySupermasteridAndMarketid(parentId.toString(), bean.getMarketid());

					} else if (requestData.get("usertype").equalsIgnoreCase("1")) {

						lib1 = userLibMongo.findByMasteridAndMarketid(parentId.toString(), bean.getMarketid());

					} else if (requestData.get("usertype").equalsIgnoreCase("2")) {

						lib1 = userLibMongo.findByDealeridAndMarketid(parentId.toString(), bean.getMarketid());

					}

					UserLiability libb = null;
					ArrayList<UserLiability> usersLiability = new ArrayList<UserLiability>();
					Double pnl1 = 0.0;
					Double pnl2 = 0.0;
					Double pnl3 = 0.0;
					Integer selection1 = 0;
					Integer selection2 = 0;
					Integer selection3 = 0;
					String marketId = null;
					for (UserLiabilityMongo document : lib1) {

						libb = new UserLiability();

						if (requestData.get("usertype").equalsIgnoreCase("4")) {
							libb.setPnl1(document.getPnl1() * document.getAdminpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getAdminpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getAdminpartnership() / 100);

						} else if (requestData.get("usertype").equalsIgnoreCase("5")) {
							libb.setPnl1(document.getPnl1() * document.getSubadminpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getSubadminpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getSubadminpartnership() / 100);

						} else if (requestData.get("usertype").equalsIgnoreCase("0")) {
							libb.setPnl1(document.getPnl1() * document.getSubadminpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getSubadminpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getSubadminpartnership() / 100);

						} else if (requestData.get("usertype").equalsIgnoreCase("1")) {
							libb.setPnl1(document.getPnl1() * document.getMasterpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getMasterpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getMasterpartnership() / 100);

						} else if (requestData.get("usertype").equalsIgnoreCase("2")) {
							libb.setPnl1(document.getPnl1() * document.getDealerpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getDealerpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getDealerpartnership() / 100);

						} else if (requestData.get("usertype").equalsIgnoreCase("6")) {
							libb.setPnl1(document.getPnl1() * document.getAdminpartnership() / 100);
							libb.setPnl2(document.getPnl2() * document.getAdminpartnership() / 100);
							libb.setPnl3(document.getPnl3() * document.getAdminpartnership() / 100);

						}

						libb.setSelection1(document.getSelection1());
						libb.setSelection2(document.getSelection2());
						libb.setSelection3(document.getSelection3());
						libb.setMarketid(document.getMarketid());
						usersLiability.add(libb);

						pnl1 = pnl1 + libb.getPnl1();
						pnl2 = pnl2 + libb.getPnl2();
						pnl3 = pnl3 + libb.getPnl3();
						selection1 = libb.getSelection1();
						selection2 = libb.getSelection2();
						selection3 = libb.getSelection3();
						marketId = libb.getMarketid();
					}

					UserLiability lib = new UserLiability();
					if (usersLiability.size() > 0) {

						lib.setMarketid(marketId);
						lib.setPnl1(Double.valueOf(df.format(-pnl1)));
						lib.setPnl2(Double.valueOf(df.format(-pnl2)));
						lib.setPnl3(Double.valueOf(df.format(-pnl3)));
						lib.setSelection1(selection1);
						lib.setSelection2(selection2);
						lib.setSelection3(selection3);
						laibility.add(lib);
					}
				}
				return new ResponseEntity<Object>(laibility, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);

	}
}
