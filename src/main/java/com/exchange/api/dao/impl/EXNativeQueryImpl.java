package com.exchange.api.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXLedger;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.UserLiability;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;

@Repository
public class EXNativeQueryImpl implements EXNativeQueryDao {

	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Autowired
	private EXUserRepository userRepo;
	
	
	EXDateUtil dtutil = new EXDateUtil();


	@Override
	public ArrayList<EXChipDetail> getpnlAndComm1(String userid, String parentid,LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select *From t_chipdetail where userid =? ");
		ArrayList<String> list = new ArrayList<String>();
		
		if(filterData.containsKey("eventid")){
			sb.append(" and eventid =?");
		}
		
		 StringBuilder sbString = new StringBuilder("");
		 String sportids[] =filterData.get("type").split(",");
			for(String id : sportids){
				sbString.append(id).append(",");
			}
			
			String strList = sbString.toString();
			if( strList.length() > 0 )
         strList = strList.substring(0, strList.length() - 1);
		sb.append(" and type in ("+filterData.get("type")+") and created_on Between ? and ? order By created_on desc ");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString(),EXChipDetail.class);
		
		query.setParameter(1, userid);
		if(filterData.containsKey("eventid")){
			query.setParameter(2, filterData.get("eventid"));
			query.setParameter(3, filterData.get("startdate"));
			query.setParameter(4, filterData.get("enddate"));
		}else{
			query.setParameter(2, filterData.get("startdate"));
			query.setParameter(3, filterData.get("enddate"));
		}
		
		
		ArrayList<EXChipDetail> listResult = (ArrayList<EXChipDetail>) query.getResultList();
		
		entitymanager.close();
		
		return listResult;
	}
	
	/*@Override
	public EXChipDetail chipGiventoChipd(String userid) {
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select sum(if(chip.type in('Opening Balance','Deposit'),(chip.chips),0)) as creditbalance,sum(if(chip.type in('Withdraw'),(chip.chips),0)) as debitbalance");
		sb.append(" From t_chipdetail chip where chip.userid =? and  chip.depositetype =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		
		query.setParameter(1, userid);
		query.setParameter(2, EXConstants.Other);
		
		List<Object[]> result = query.getResultList();
		
		EXChipDetail bean =null;
		
		for (Object[] a : result) {

			bean = new EXChipDetail();
			if(a[0]!=null){
				bean.setCreditbalance(Double.valueOf(a[0].toString()));

			}
			if(a[1]!=null){
				bean.setDebitbalance(Double.valueOf(a[1].toString()));
			}

		}
		
		return bean;
	}*/

	@Override
	public ArrayList<EXLedger> userLedger(Integer parentid,Integer childid) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select sum( led.uplineamount) amount,led.childid,led.userid,usr.username,led.parentid From t_ledger led, t_user usr where led.parentid=? ");
		if(childid!=0){
			
			sb.append("and childid =?");
		}
		sb.append("  and usr.userid = led.userid  group by  led.userid ");
		sb.append(" order by usr.username asc;");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentid);
		if(childid!=0){
			query.setParameter(2, childid);
		}
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.userLedger(a);
			ledgerlist.add(ledger);
		}
		
		entitymanager.close();
		
		return ledgerlist;
	}
	
	@Override
	public ArrayList<EXLedger> userCashTransaction(Integer parentid,Integer childid,Integer pagintype,Integer startpage) {
		// TODO Auto-generated method stub
		
		 
		StringBuffer sb  = new StringBuffer();
		sb.append("select sum( led.amount) amount,led.childid, usr.userid,usr.username ,led.createdon,led.matchname,led.type,led.collectionname,led.matchid From t_ledger led, t_user usr,t_matchresult mch where led.parentid=? ");
		if(childid!=0){
			
			sb.append("and childid =?");
		}
		sb.append("  and usr.id = led.childid and mch.matchid=led.matchid group by  led.matchid");
		sb.append(" order by led.createdon desc");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentid);
		if(childid!=0){
			query.setParameter(2, childid);
		}
		if(startpage>-1){
			query.setFirstResult(startpage);
			query.setMaxResults(0 + 100);
		}
	
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.userCashTransaction(a);
			ledgerlist.add(ledger);
		}
		
		entitymanager.close();
		
		return ledgerlist;
	}

	@Override
	@Transactional
	public void updateLedgerUpline(EXLedger ledger) {
		
	StringBuffer sb  = new StringBuffer();
	sb.append("update t_ledger set uplineamount=? where parentid =? and matchid=? and marketid =? and userid =?");
	
	Query query = (Query) entitymanager.createNativeQuery(sb.toString());
	query.setParameter(1, ledger.getAmount());
	query.setParameter(2, ledger.getChildid());
	query.setParameter(3, ledger.getMatchid());
	query.setParameter(4, ledger.getMarketid());
	query.setParameter(5, ledger.getUserid());
	query.executeUpdate();
	entitymanager.close();
	
}

	@Override
	public ArrayList<EXLedger> profitLoss(Date startdate, Date enddate, Integer usertype, String userid,LinkedHashMap<String,String> filterData) throws ParseException {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(led.pnl) as pnl ,sum(-led.uplineamount) as uplineamount ,sum(led.downlineamount) as downlineamount ,led.matchid,led.matchname,led.createdon,led.markettype,led.marketname,ROW_NUMBER() OVER (  ORDER BY led.createdon  ) seerialno,led.marketid,sum(led.commssionmila) as commssionmila,sum(led.commssiondiya) as commssiondiya ");
		sb.append(" from t_ledger led  where  led.collectionname='match' and led.userid=? "); 
		if(startdate !=null && enddate !=null){
			sb.append(" and led.createdon BETWEEN ? and ?  ");
		}
		if(!filterData.get("sportId").equalsIgnoreCase("-1")) {
			sb.append(" and sportid =?");
		}
		
		if(!filterData.get("matchId").equalsIgnoreCase("-1")) {
			sb.append(" and matchId =?");
		}
		
		sb.append(" group by led.matchid order by led.createdon desc");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userid);
		if(startdate!=null && enddate !=null){
			query.setParameter(2, startdate);
			query.setParameter(3, enddate);	
			
		}if(!filterData.get("sportId").equalsIgnoreCase("-1")) {
			query.setParameter(4, Integer.parseInt(filterData.get("sportId")));	
		}if(!filterData.get("matchId").equalsIgnoreCase("-1")) {
			query.setParameter(5, Integer.parseInt(filterData.get("matchId")));	
		}
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] row : result) {



			EXLedger bean = new EXLedger();

			bean.setPnlamount((Double) row[0]);
			bean.setUplineamount((Double) row[1]);
			bean.setDownlineamount((Double) row[2]);
			bean.setMatchid((Integer) row[3]);
			bean.setMatchname((String) row[4]);
			//bean.setCreatedon(dtutil.convertToDate((String) row[5], "yyyy-MM-dd HH:mm:ss").getDate());
			bean.setDate(row[5].toString());
			//(Date) 
			bean.setMarkettype((String) row[6]);
			bean.setMarketname((String) row[7]);
			BigInteger b1 = new BigInteger(row[8].toString());
			bean.setSerialno(b1.intValue());
			bean.setMarketid((String) row[9]);
			bean.setCommssionMila((Double) row[10]);
			bean.setCommssionDiya((Double) row[11]);
			
			ledgerlist.add(bean);
		
			
		}
		entitymanager.close();
		
		return ledgerlist;
	}

	@Override
	public ArrayList<EXLedger> sessionPlusMinus(Integer matchid,Integer parentid) {
		// TODO Auto-generated method stubmatchis
		StringBuffer sb  = new StringBuffer();
		sb.append("select sum(led.amount) as pnlamount,mf.name,led.matchid,led.matchname,led.marketid,fres.result From t_ledger led,t_matchfancy mf,t_fancyresult fres");
		sb.append(" where led.matchid =? and led.markettype='Fancy' and led.parentid=? and mf.fancyid = led.marketid and fres.fancyid = led.marketid group by led.marketid");
	
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, matchid);
		query.setParameter(2, parentid);
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.sessionPlusMinus(a);
			ledgerlist.add(ledger);
		}
		
		entitymanager.close();
		
		return ledgerlist;
		
	}

	@Override
	public Market getMarketAndSelId(String eventid) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		Market bean = new Market();
		sb.append("select mrkt.marketid,GROUP_CONCAT(selid.runner_name) as runnername,GROUP_CONCAT(selid.selectionid) as selctionid,evn.eventid,mrkt.sportid,evn.minbet,evn.maxbet,mrkt.oddsprovider,mrkt.ispause,mrkt.cricexchageid  ");
		sb.append(" From t_market mrkt,t_selectionid selid,t_event evn where mrkt.eventid= ?  and mrkt.marketid= selid.marketid and evn.eventid=mrkt.eventid and mrkt.isactive=? ");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, eventid);
		query.setParameter(2, true);
		List<Object[]> resultList = query.getResultList();
		for (Object[] a : resultList) {
			bean.setMarketid((String) a[0]);
			bean.setRunnername((String) a[1]);
			bean.setSelectionids((String) a[2]);
			bean.setEventid(Integer.parseInt((String) a[3]));
			bean.setSportid((Integer) a[4]);
			bean.setMinbet((Integer) a[5]);
			bean.setMaxbet((Integer) a[6]);
			bean.setOddsprovider((String) a[7]);
			bean.setIspause((Boolean) a[8]);
			bean.setCricexchageid((String) a[9]);
		}
		
		entitymanager.close();
		
		return bean;
	}
	
	@Override
	public ArrayList<Market> getOpenMarket(Integer sportid,Boolean isPause) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		ArrayList<Market> list = new ArrayList<Market>();
		sb.append("select mrkt.marketid,mrkt.eventid,evn.fancyprovider,mrkt.status,evn.fancypause,evn.minbet,evn.maxbet,mrkt.inplay,");
		sb.append("evn.open_date,mrkt.ispause,mrkt.matchname,mrkt.sportid,mrkt.marketname,mrkt.id From t_market mrkt,t_event evn  ");
		sb.append("  where mrkt.status=? and mrkt.sportid=? and mrkt.eventid=evn.eventid and mrkt.isactive=true and mrkt.ispause=?  ");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, true);
		query.setParameter(2, sportid);
		query.setParameter(3, isPause);
		List<Object[]> resultList = query.getResultList();
		for (Object[] a : resultList) {
			Market bean = new Market();
			bean.setMarketid((String) a[0]);
			bean.setEventid((Integer) a[1]);
			bean.setFancyprovider((String) a[2]);
			bean.setStatus((Boolean) a[3]);
			bean.setFancypause((Boolean) a[4]);
			bean.setMinbet((Integer) a[5]);
			bean.setMaxbet((Integer) a[6]);
			bean.setInPlay((Boolean) a[7]);
			bean.setDate(a[8].toString());
			bean.setIspause((Boolean) a[9]);
			bean.setMatchname((String) a[10]);
			bean.setSportid((Integer) a[11]);
			bean.setMarketname((String) a[12]);
			bean.setId((Integer) a[13]);
			list.add(bean);
		}
		
		entitymanager.close();
		
		return list;
	}

	public void updateUserAvailableBalance(Integer id, BigDecimal secbal) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("update t_user usr set usr.availablebalance=usr.availablebalance+"+secbal+" where usr.id =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		
		entitymanager.close();
		
		
	}
	
	@Override
	@Transactional
	public void updateUserPnl(Integer id, Double userpnl,Double uplineamount,Integer hisabkitabtype) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		
		if(hisabkitabtype ==1) {
			sb.append("update t_user usr set usr.availablebalancewithpnl=usr.availablebalancewithpnl +"+userpnl+",usr.uplineamount =usr.uplineamount+"+uplineamount+" where usr.id =?");
			
		}else {
			sb.append("update t_user usr set usr.availablebalancewithpnl=usr.availablebalancewithpnl +"+userpnl+",usr.uplineamount =usr.uplineamount+"+(-uplineamount)+" where usr.id =?");
			
		}
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		
		entitymanager.close();
		
		
	}
	
	
	@Override
	@Transactional
	public void updateAvailableBalance(Integer id, BigDecimal secbal) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("update t_user usr set usr.availablebalance=usr.availablebalance+"+secbal+" where usr.id =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		entitymanager.close();
		
	}
	
	
	@Override
	@Transactional
	public void updateAvailableBalanceWithPnl(Integer id, BigDecimal availablebalancewithpnl,Integer userType,Double uplineAmount) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		if(userType ==3){
			sb.append("update t_user usr set usr.availablebalancewithpnl=usr.availablebalancewithpnl+"+availablebalancewithpnl+",usr.availablebalance=usr.availablebalance"+availablebalancewithpnl+",usr.uplineamount=usr.uplineamount+"+uplineAmount+" where usr.id =?");
				
		}else{
			sb.append("update t_user usr set usr.availablebalancewithpnl=usr.availablebalancewithpnl+"+availablebalancewithpnl+",usr.uplineamount=usr.uplineamount+"+uplineAmount+" where usr.id =?");
				
		}
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		entitymanager.close();
		
	}
	
	@Override
	@Transactional
	public void updateCreditRef(Integer id, BigDecimal secbal) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("update t_user usr set usr.creditref=usr.creditref+"+secbal+" where usr.id =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		entitymanager.close();
		
	}
	
	
	@Override
	@Transactional
	public void updateUserBalanceChipDW(String id, BigDecimal balance) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("update t_user usr set usr.secondrybanlce=usr.secondrybanlce+"+balance+",usr.availablebalance =usr.availablebalance+"+balance+" where usr.userid =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, id);

		query.executeUpdate();
		
		entitymanager.close();
		
		
	}
	@Override
	@Transactional
	public void deleteUserLogin(String userid) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("delete from t_userlogin  where userid= ?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userid);

		query.executeUpdate();
		entitymanager.close();
		
	}

	@Override
	public ArrayList<Market> leftMenuData(Integer sportid, String groupby, Integer seriesid,Boolean inPlay) {
		// TODO Auto-generated method stub
		 
		StringBuffer sb  = new StringBuffer();
		ArrayList<Market> list = new ArrayList<Market>();
		
		sb.append("  select  sp.sportid,sp.name,cast(mkt.opendate as Date) as Date,mkt.matchname,mkt.eventid  from t_sport sp,t_market mkt where ");
		sb.append("  mkt.isactive=? and mkt.sportid=?   " );
		
		
		sb.append("  group by mkt.eventid order by mkt.opendate asc ");
		try{
			Query query = (Query) entitymanager.createNativeQuery(sb.toString());
			query.setParameter(1, true);
			query.setParameter(2, sportid);
			if(seriesid!=null){
				query.setParameter(5, seriesid);
			}
			List<Object[]> resultList = query.getResultList();
			for (Object[] a : resultList) {
				Market bean = new Market();
				bean.setSportid((Integer) a[0]);
				bean.setSportName((String) a[1]);
				bean.setStartdate(a[2].toString());
				bean.setMatchname((String) a[3]);
				bean.setEventid((Integer) a[4]);
				list.add(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		entitymanager.close();
		
		return list;
	}
	
	@Override
	public EXUser headerData(Integer parentid,Integer childid) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		/*sb.append("select usr.id,COALESCE(usr.creditref,0) as creditref,COALESCE(SUM(led.amount),0) as totalmasterbalance,usr.availablebalance ,COALESCE(SUM(led.amount),0) as upline  from t_user usr,t_ledger led where usr.id =? and led.parentid =? ");
		if(childid!=0){
			
			sb.append("and childid =?");
		}
		sb.append("  and usr.id = led.childid ");
		*/
		sb.append("select usr.id,COALESCE(usr.creditref,0) as creditref,usr.availablebalance from t_user usr  where usr.id=? and usr.parentid=? ");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, childid);
		query.setParameter(2, parentid);
		/*if(childid!=0){
			query.setParameter(3, childid);
		}
		*/
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
			
			/*bean.setId((Integer) a[0]);
			bean.setCreditRef(new BigDecimal(a[1].toString()));
			bean.setTotalMasterBalance((Double) a[2]);
			bean.setAvailableBalance(new BigDecimal(a[3].toString()));
			bean.setUplineAmount((Double) a[4]);*/
			
			bean.setId((Integer) a[0]);
			bean.setCreditRef(new BigDecimal(a[1].toString()));
			//bean.setTotalMasterBalance((Double) a[2]);
			bean.setAvailableBalance(new BigDecimal(a[2].toString()));
			//bean.setUplineAmount((Double) a[4]);
		}
		entitymanager.close();
		
		return bean;
	}

	@Override
	public EXUser creditRefAndAvailableBalSum(Integer parentId) {
		// TODO Auto-generated method stub
		 
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select sum(usr.creditref) as creditref,sum(usr.availablebalance) as availablebalance From t_user usr where usr.parentid=? and usr.usertype!=4 ");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
			if(a[0]!=null){
				bean.setDownlinecreditRef(new BigDecimal(a[0].toString()));
				
			}if(a[1]!=null){
				bean.setDownlineavailableBalance(new BigDecimal(a[1].toString()));
				
			}
			
		}
		entitymanager.close();
		
		return bean;
	}

	@Override
	public EXUser profitLossByParentId(Integer userId) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select COALESCE(SUM(led.pnl),0) as amount, COALESCE(SUM(led.uplineamount),0) uplineamount,COALESCE(SUM(led.downlineamount -led.amount),0) downlineamount from t_ledger led where  led.userid = ? and  led.markettype !='Settlement'");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
				bean.setDownlineWithProfitLoss((Double) a[2]);
				bean.setMyProfitLoss((Double) a[0]);
			}
			
		entitymanager.close();
		
		return bean;
	}


	@Override
	public EXUser profitLossByChildId(Integer childId) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select COALESCE(SUM(led.amount),0) as amount, COALESCE(SUM(led.uplineamount),0) uplineamount,COALESCE(SUM(led.downlineamount -led.amount),0) downlineamount from t_ledger led where  led.childid= ? ");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, childId);
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
				bean.setDownlineWithProfitLoss((Double) a[2]);
				bean.setMyProfitLoss((Double) a[0]);
			}
		
		entitymanager.close();
		
		return bean;
		
	}
	
	@Override
	public EXUser profitLossByUserId(String userId) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select (COALESCE(SUM(led.amount),0)) as amount, COALESCE(SUM(led.uplineamount),0) uplineamount,COALESCE(SUM(led.downlineamount -led.amount),0) downlineamount,led.userid from t_ledger led where  led.userid= ? ");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
				bean.setDownlineWithProfitLoss((Double) a[2]);
				if((String) a[3] !=null){
					if(userRepo.findByUserid((String) a[3]).getUsertype()==3){
						bean.setMyProfitLoss((Double) a[0]);
						bean.setUplineAmount(0.0);
					}else{
						bean.setMyProfitLoss((Double) a[0]);
						bean.setUplineAmount((Double) a[1]);
					}
				}else{
					bean.setMyProfitLoss((Double) a[0]);
				    bean.setUplineAmount((Double) a[0]);}
				}
		entitymanager.close();
		
		return bean;
	}

	@Override
	public ArrayList<EXChipDetail> getStatement(String userId, String startDate, String endDate,String reportType) {
		// TODO Auto-generated method stub 
		
		ArrayList<EXChipDetail> list = new ArrayList<EXChipDetail>();
		StringBuffer sb  = new StringBuffer();
		sb.append("SELECT Date(chipd.createdon) AS date,chipd.descreption,chipd.fromto,chipd.credit,chipd.debit,chipd.closing,chipd.id   FROM t_chipdetail chipd where chipd.userid= ?");
		sb.append(" and chipd.createdon between ? and ? order by chipd.createdon desc "); 
		if(!reportType.equalsIgnoreCase("-1")){
			sb.append(" and type =? "); 
		}
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);

		query.setParameter(2, startDate);
		query.setParameter(3, endDate);	
		if(!reportType.equalsIgnoreCase("-1")){
			query.setParameter(4, reportType);	
			
		}
		
		List<Object[]> result = query.getResultList();
		for (Object[] a : result) {
			EXChipDetail bean = new EXChipDetail();
			bean.setDate(a[0].toString());
			bean.setDescreption(a[1].toString());
			bean.setFromTo(a[2].toString());
			bean.setCredit((Double) a[3]);
			bean.setDebit((Double) a[4]);
			bean.setClosing((BigDecimal) a[5]);
			bean.setId((Integer) a[6]);
			list.add(bean);
		}
		entitymanager.close();
		
		return list;
	}
	
	@Override
	public Double settlementSum(String userId) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select COALESCE(SUM(led1.amount),0) as amount from t_ledger led1 where  led1.userid=? and led1.markettype='Settlement'");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		
		
		Object result = query.getSingleResult();
		
		entitymanager.close();
		
		return (Double)result;
	}

	@Override
	public ArrayList<UserLiability> userPnl(String marketId, String userId,String partnership,Integer userType,String gropuBypartnership) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		ArrayList<UserLiability> list = new ArrayList<UserLiability>();
		
		sb.append("select sum(lib.pnl1)*"+partnership +" as pnl1,");
		sb.append("sum(lib.pnl2)*"+partnership+" as pnl2,");
		
		sb.append("sum(lib.pnl3)*"+partnership+" as pnl3, mkt.marketid,lib.selection1,lib.selection2,lib.selection3");
		
		sb.append(" From t_libility lib ,t_market mkt  where lib.marketid=?");
		sb.append(" and mkt.isactive=true and mkt.marketid=? and " );
		if(userType == 4){
			sb.append("lib.adminid=?" );
		}else if(userType == 6){
			sb.append("lib.adminid=?" );
		}else if(userType == 5){
			sb.append("lib.subadminid=?" );
		}else if(userType == 0){
			sb.append("lib.supermasterid=?" );
		}else if(userType == 1){
			sb.append("lib.masterid=?" );
		}else{
			sb.append("lib.dealerid=?" );	
		}
		sb.append(" group by "+gropuBypartnership );	
		
		UserLiability bean =null;
		try{
			Query query = (Query) entitymanager.createNativeQuery(sb.toString());
			query.setParameter(1, marketId);
			query.setParameter(2, marketId);
			query.setParameter(3, userId);
			
			List<Object[]> resultList = query.getResultList();
			for (Object[] a : resultList) {
				
				if(a[0]!=null){
					bean = new UserLiability();
					bean.setPnl1((Double)a[0]);
					bean.setPnl2((Double)a[1]);
					bean.setPnl3((Double)a[2]);
					bean.setMarketid(a[3].toString());
					bean.setSelection1((Integer)a[4]);
					bean.setSelection2((Integer)a[5]);
					bean.setSelection3((Integer)a[6]);
					list.add(bean);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		entitymanager.close();
		
		return list;
	}

	@Override
	public Double userCurrentExpo(String userId) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select COALESCE(SUM(lib.liability),0)  as liability From t_libility lib where lib.isactive=? and lib.userid=?");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, true);
		query.setParameter(2, userId);
		
		
		Object result = query.getSingleResult();
		
		entitymanager.close();
		
		return (Double)result;
	}

	@Override
	public EXUser profitLossByUserIdWithOutSettlement(String userId) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select (COALESCE(SUM(led.pnl),0)) as amount, COALESCE(SUM(led.uplineamount),0) uplineamount,COALESCE(SUM(led.downlineamount -led.amount),0) downlineamount,led.userid,COALESCE(SUM(led.commssiondiya),0) commssiondiya ,COALESCE(SUM(led.commssionmila),0) commssionmila from t_ledger led where  led.userid= ? and led.markettype!='Settlement' ");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXUser> data = new ArrayList<EXUser>(); 
		EXUser bean  =  new EXUser();
		for (Object[] a : result) {
				bean.setDownlineWithProfitLoss((Double) a[2]);
				if((String) a[3] !=null){
					if(userRepo.findByUserid((String) a[3]).getUsertype()==3){
						bean.setMyProfitLoss((Double) a[0]+ (Double) a[4] +(Double) a[5]);
						bean.setUplineAmount(0.0);
					}else{
						bean.setMyProfitLoss((Double) a[0]+ (Double) a[4] +(Double) a[5]);
						bean.setUplineAmount((Double) a[1]);
					}
				}else{
					bean.setMyProfitLoss((Double) a[0]+ (Double) a[4] +(Double) a[5]);
				    bean.setUplineAmount((Double) a[0]+ (Double) a[4] +(Double) a[5]);
				   }
				}
			
		entitymanager.close();
		
		return bean;
	}
	
	public void updateFancyStatus(Integer id, String status,Boolean isActive) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("update t_matchfancy mf set mf.isactive=?,mf.status =? where mf.id =?");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, isActive);
		query.setParameter(2, status);
		query.setParameter(3, id);
		query.executeUpdate();
		
		
		entitymanager.close();
		
	}

	@Override
	public ArrayList<HashMap<String,String>> userByUserLike(Integer usertype, Integer parentuserid, String userid) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select usr.userid,usr.id From t_user usr where usr.userid like '"+userid+"%'  ");
		if(usertype ==4) {
			sb.append(" and adminid =?");
		}else if(usertype ==5) {
			sb.append(" and subadminid =?");
		}else if(usertype ==0) {
			sb.append(" and supermasterid =?");
		}else if(usertype ==1) {
			sb.append(" and masterid =?");
		}else  {
			sb.append(" and dealerid =?");
		}
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentuserid);
		
		List<Object[]> result = query.getResultList();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Object[] a : result) {
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("id", a[0].toString());
			hm.put("text", a[0].toString());
			list.add(hm);
			
		} 
		entitymanager.close();
		
		return list;
	}

	
	@Override
	public ArrayList<HashMap<String,String>> subAdminByUserLike(Integer usertype, Integer parentuserid, String userid) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select usr.userid,usr.id,usr.usertype From t_user usr where usr.userid like '"+userid+"%'  ");
		if(usertype ==4) {
			sb.append(" and adminid =?");
		}else if(usertype ==5) {
			sb.append(" and subadminid =?");
		}else if(usertype ==0) {
			sb.append(" and supermasterid =?");
		}else if(usertype ==1) {
			sb.append(" and masterid =?");
		}else  {
			sb.append(" and dealerid =?");
		}
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentuserid);
		
		List<Object[]> result = query.getResultList();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (Object[] a : result) {
			HashMap<String,String> hm = new HashMap<String,String>();
			if(Integer.parseInt(a[2].toString()) == 5 ) {
				hm.put("id", a[0].toString());
				hm.put("text", a[0].toString());
				list.add(hm);
			}
			
			
		} 
		entitymanager.close();
		
		return list;
	}
	@Override
	public Double downLinePnlMatchWise(Integer matchid, Integer parentId, String groupby) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(if(usr.usertype= 3,(led.uplineamount),-led.uplineamount)) AS pnl From t_ledger led,t_user usr  where led.parentid =? and led.matchid=? and usr.userid=led.userid group by "+groupby);
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchid);
		
		List results = query.getResultList();
		
		//Object result = query.getSingleResult();
		
		//entitymanager.close();
			
		entitymanager.close();
		
		if (results.isEmpty()) {
		    return 0.0; // handle no-results case
		} else {
		    return (Double) results.get(0);
		}
	}
	
	
	@Override
	public Double downLinePnlMatchWiseCasino(Integer matchid, Integer parentId, String groupby,LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(if(usr.usertype= 3,(led.uplineamount),-led.uplineamount)) AS pnl From t_ledger led,t_user usr  where led.parentid =? and led.matchid=? and usr.userid=led.userid and led.createdon BETWEEN ? and ?  group by "+groupby);
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchid);
		query.setParameter(3, filterData.get("startdate"));
		query.setParameter(4, filterData.get("enddate"));	
		
		List results = query.getResultList();
		
		entitymanager.close();
		
		if (results.isEmpty()) {
		    return 0.0; // handle no-results case
		} else {
		    return (Double) results.get(0);
		}
			
		
	}


	
	@Override
	public Double downLinePnlMatchWiseAndMarketId(Integer matchid, Integer parentId,String marketId) {
		// TODO Auto-generated method stub
	//	System.out.println(marketId);
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(if(usr.usertype= 3,(led.uplineamount),-led.uplineamount)) AS pnl From t_ledger led,t_user usr  where led.parentid =? and led.matchid=? and led.marketid =? and usr.userid=led.userid group by marketid");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchid);
		query.setParameter(3, marketId);
		
		
		List results = query.getResultList();
		
		entitymanager.close();
		
		if (results.isEmpty()) {
		    return 0.0; // handle no-results case
		} else {
		    return (Double) results.get(0);
		}
	}

	
	@Override
	public ArrayList<EXLedger> profitLossDetail(Integer usertype, String userid,LinkedHashMap<String,String> filterData) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(led.pnl) as pnl ,sum(-led.uplineamount) as uplineamount ,sum(led.downlineamount) as downlineamount ,led.matchid,led.matchname,led.createdon,led.markettype,led.marketname,ROW_NUMBER() OVER (  ORDER BY led.createdon  ) seerialno,led.marketid ");
		sb.append(",sum(led.commssiondiya) as commssiondiya,sum(led.commssionmila) as commssionmila,led.sportid from t_ledger led  where  led.collectionname='match' and led.userid=? and led.matchid=? "); 
	
		sb.append(" group by led.marketid order by led.createdon desc");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userid);
		query.setParameter(2, Integer.parseInt(filterData.get("matchId")));
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.profitLossDetail(a);
			ledgerlist.add(ledger);
		}
		
		entitymanager.close();
		
		
		return ledgerlist;
	}
	
	@Override
	public Double downleverPnl(String parentId) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select  ROUND(COALESCE(sum(if(usr.usertype = 3,(usr.uplineamount),-usr.uplineamount)),0)) AS uplineamount from t_user usr where usr.parentid=?");
	
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		
		
		Object result = query.getSingleResult();
		
		entitymanager.close();
		
		return (Double)result;
	}

	@Override
	public ArrayList<UserLiability> userList(LinkedHashMap<String,String> filterData, String id,Integer userType) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select *From t_libility lib where matchid =? and marketid =? ");
		ArrayList<String> list = new ArrayList<String>();
		
		if(userType ==4){
			sb.append(" and adminid =?");
		}else if(userType ==5){
			sb.append(" and subadminid =?");
		}if(userType ==0){
			sb.append(" and supermasterid =?");
		}if(userType ==1){
			sb.append(" and masterid =?");
		}if(userType ==2){
			sb.append(" and dealerid =?");
		}
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString(),UserLiability.class);
		
		query.setParameter(1, filterData.get("matchId"));
		query.setParameter(2, filterData.get("marketId"));
		query.setParameter(3, id);
	
		
		ArrayList<UserLiability> listResult = (ArrayList<UserLiability>) query.getResultList();
		
		entitymanager.close();
		
		return listResult;
	}
	
	
	@Override
	public EXLedger commSum(LinkedHashMap<String,String> filterData, String id) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(led.commssiondiya) as commssiondiya,sum(led.commssionmila) as commssionmila,led.pnl From t_ledger led where led.matchid =? and led.marketid =? and led.userid=?  ");
		
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		
		query.setParameter(1, filterData.get("matchId"));
		query.setParameter(2, filterData.get("marketId"));
		query.setParameter(3, id);
	
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		EXLedger ledger =null;
		for (Object[] a : result) {
			ledger = new EXLedger();
			ledger.setCommssionDiya((Double) a[0]);
			ledger.setCommssionMila((Double) a[1]);
			ledger.setPnl((Double) a[2]);
		}
		entitymanager.close();
		
		return ledger;
	}


	@Override
	public ArrayList<EXChipDetail> getStatementSettlement(String userId, String startDate, String endDate) {
		// TODO Auto-generated method stub 
		
		ArrayList<EXChipDetail> list = new ArrayList<EXChipDetail>();
		StringBuffer sb  = new StringBuffer();
		sb.append("SELECT Date(chipd.createdon) AS date,chipd.descreption,chipd.fromto,chipd.credit,chipd.debit,chipd.closing,chipd.id   FROM t_chipdetail chipd where chipd.userid= ?");
		sb.append(" and chipd.createdon between ? and ? and issettlement =? "); 
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userId);

		query.setParameter(2, startDate);
		query.setParameter(3, endDate);	
		query.setParameter(4, true);
		
		List<Object[]> result = query.getResultList();
		for (Object[] a : result) {
			EXChipDetail bean = new EXChipDetail();
			bean.setDate(a[0].toString());
			bean.setDescreption(a[1].toString());
			bean.setFromTo(a[2].toString());
			bean.setCredit((Double) a[3]);
			bean.setDebit((Double) a[4]);
			bean.setClosing((BigDecimal) a[5]);
			bean.setId((Integer) a[6]);
			list.add(bean);
		}
		entitymanager.close();
		
		return list;
	}
	
	@Override
	public ArrayList<EXLedger> profitLossCasino(String startdate, String enddate, Integer usertype, String userid,LinkedHashMap<String,String> filterData) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		
      // ArrayList<Integer> matchIds = new ArrayList<Integer>();
		
		//matchIds.add(5052);
		
		String  matchIds = "5041,5042,5051,5052,5021,5022,5011,5031,5032,5047,5048,5017,5036,5037,5026,5027,5076,5077,5056";
		
		sb.append("select  sum(led.pnl) as pnl ,sum(-led.uplineamount) as uplineamount ,sum(led.downlineamount) as downlineamount ,led.matchid,led.matchname,led.createdon,led.markettype,led.marketname,ROW_NUMBER() OVER (  ORDER BY led.createdon  ) seerialno,led.marketid,sum(led.commssionmila) as commssionmila,sum(led.commssiondiya) as commssiondiya ");
		sb.append(" from t_ledger led  where   led.userid=?   "); 
		if(startdate !=null && enddate !=null){
			sb.append(" and led.createdon BETWEEN ? and ? and led.matchid  in ("+matchIds+") ");
		}
		
		sb.append(" group by led.matchid order by led.createdon desc");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, userid);
		if(startdate!=null && enddate !=null){
			query.setParameter(2, startdate);
			query.setParameter(3, enddate);	
			
		}
		
		//query.setParameter(4, 5041);
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.profitLoss(a);
			ledgerlist.add(ledger);
		}
		entitymanager.close();
		
		return ledgerlist;
	}
	
	
	@Override
	public ArrayList<UserLiability> useLibRollBack(String marketid) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select *from t_libility lib where lib.id");
		ArrayList<String> list = new ArrayList<String>();
		
	
		sb.append(" in(select usr.id " + 
				" from t_chipdetail chipd, t_libility usr where chipd.marketid='"+marketid+"' and " + 
				" usr.marketid='"+marketid+"'  and usr.userid=chipd.userid )");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString(),UserLiability.class);
		
	
		
		
		ArrayList<UserLiability> listResult = (ArrayList<UserLiability>) query.getResultList();
		
		entitymanager.close();
		
		return listResult;
	}

	@Override
	public ArrayList<EXLedger> matchUserWiseAmont(Integer parentId, Integer matchId) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(if(usr.usertype= 3,(-led.uplineamount),led.uplineamount)) as uplineamount,led.userid  from t_ledger led, t_user usr   where   led.parentid=?");
		sb.append(" and led.matchid=?  and usr.userid=led.userid group by led.userid order by led.userid  "); 
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchId);	
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.matchUserWiseAmont(a);
			ledgerlist.add(ledger);
		}
		entitymanager.close();
		
		return ledgerlist;
	}
	
	@Override
	public ArrayList<EXLedger> matchUserWiseAmontCasino(Integer parentId ,Integer matchId,String startdate, String enddate) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(if(usr.usertype= 3,(-led.uplineamount),led.uplineamount)) as uplineamount,led.userid  from t_ledger led, t_user usr   where   led.parentid=?");
		sb.append(" and led.matchid=?  and usr.userid=led.userid  "); 
		
		if(startdate !=null && enddate !=null){
			sb.append(" and led.createdon BETWEEN ? and ?  ");
		}
		sb.append("  group by led.userid order by led.userid  ");
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchId);	
		if(startdate!=null && enddate !=null){
			query.setParameter(3, startdate);
			query.setParameter(4, enddate);	
			
		}
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.matchUserWiseAmont(a);
			ledgerlist.add(ledger);
		}
		entitymanager.close();
		
		return ledgerlist;
	}
	
	@Override
	public ArrayList<EXLedger> marketUserWiseAmont(Integer parentId, Integer matchId,String marketId) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  sum(led.uplineamount) as uplineamount,led.userid  from t_ledger led  where   led.parentid=?");
		sb.append(" and led.matchid=? and led.marketid=? group by led.userid order by led.userid  "); 
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter(1, parentId);
		query.setParameter(2, matchId);	
		query.setParameter(3, marketId);	
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.matchUserWiseAmont(a);
			ledgerlist.add(ledger);
		}
		entitymanager.close();
		
		return ledgerlist;
	}
	
	@Override
	public ArrayList<EXLedger> profitLossCasinoOk() {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		
      // ArrayList<Integer> matchIds = new ArrayList<Integer>();
		
		//matchIds.add(5052);
		
		String  matchIds = "5041,5042,5051,5052,5021,5022,5011,5031,5032,5047,5048,5017,5036,5037,5026,5027,5076,5077,5056";
		
		sb.append("select  led.pnl,led.amount,led.uplineamount,led.downlineamount,led.id,led.userid,led.byuserid from t_ledger led inner join t_ledger led2");
		sb.append(" on led.amount = led2.amount and led.childid = led2.childid and led.collectionname = led2.collectionname and led.marketid = led2.marketid and led.markettype = led2.markettype   "); 
		sb.append("and led.matchid = led2.matchid and led.parentid = led2.parentid and led.userid = led2.userid and led.`type` = led2.`type` and led.id <> led2.id and led.byuserid = led2.byuserid and led.sportid = led2.sportid"); 
		sb.append("  and led.pnl = led2.pnl and led.downlineamount = led2.downlineamount and led.childid = led2.childid and led.uplineamount = led2.uplineamount and led.id < led2.id inner join t_user tuser  "); 
		sb.append("  where  led.createdon between '2022-01-05 00:00:00' and '2022-01-06 23:59:59' and led.sportid!=4 and led.matchid=5036  "); 
		sb.append("  and led2.createdon between '2022-01-05 00:00:00' and '2022-01-06 23:59:59' and led2.sportid!=4 and led2.matchid=5036  "); 
		sb.append("  and led.userid = tuser.userid and led2.userid = tuser.userid and tuser.usertype <> 3 and tuser.usertype<>4  "); 
		
		
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		
		
		List<Object[]> result = query.getResultList();
		ArrayList<EXLedger> ledgerlist = new ArrayList<EXLedger>(); 
		for (Object[] a : result) {
			EXLedger ledger  =  new EXLedger();
			ledger = ledger.profitLosOk(a);
			ledgerlist.add(ledger);
		}
		entitymanager.close();
		
		return ledgerlist;
	}
	
	@Override
	@Transactional
	public void updateUserUpline(EXLedger ledger) {
		
	StringBuffer sb  = new StringBuffer();
	sb.append("update t_user set uplineamount = uplineamount+"+ledger.getUplineamount()+" where userid =?");
	
	Query query = (Query) entitymanager.createNativeQuery(sb.toString());
	query.setParameter(1, ledger.getUserid());
	
	query.executeUpdate();
	entitymanager.close();
	
}
}
