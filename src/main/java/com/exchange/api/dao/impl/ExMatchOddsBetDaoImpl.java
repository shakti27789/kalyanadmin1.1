package com.exchange.api.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Sport;
import com.exchange.api.dao.ExMatchOddsBetDao;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.util.EXConstants;

@Repository
public class ExMatchOddsBetDaoImpl implements ExMatchOddsBetDao {

	
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	@Autowired
	SportRepository sportRepo;
	
	 
	
	@Override
	public ArrayList<ExMatchOddsBet> getBetsForResult(String marketid, String sorttype, String type) {
		// TODO Auto-generated method stub


		StringBuffer sb  = new StringBuffer();

		sb.append("from  ExMatchOddsBet where ");
		if(type.equalsIgnoreCase("Odds")) {
			sb.append("marketid =:marketid");
		}else {
			sb.append("fancyid =:marketid");
		}

		sb.append(" and isactive =:active  order by userid asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(type.equalsIgnoreCase("Odds")) {
			query.setParameter("marketid", marketid);
		}else {
			query.setParameter("marketid", marketid);
		}
		query.setBoolean("active", true);
		
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;

	}



	@Override
	public ArrayList<ExMatchOddsBet> getMainDateWiseBets(String userid, int type,LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub

		StringBuffer sb  = new StringBuffer();
		ExMatchOddsBet betlist =null;
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		// group("userid","date","admin","subadmin","supermaster","matchname","sportid","resultid","master","type","fancyid","marketid","marketname","dealer","fancyname","userchain","result","resultname")
         
		sb.append("select userid,matchname,sportid,resultid,type,marketid,marketname,userchain,result,resultname,pnl,netpnl");
		sb.append(" ,date");
		sb.append(" from ExMatchOddsBet where  ");
		if(type == 0){
			sb.append(" supermasterid=:supermasterid ");

		}else if(type ==1){
			sb.append(" masterid=:masterid ");
		}else if(type ==4){
			sb.append(" adminid=:adminid ");
		}else if(type ==5){
			sb.append(" subadminid=:subadminid ");
		}else{
			sb.append(" dealerid=:dealerid ");
		}
		sb.append(" and isactive =:active and type!= :type");

		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(type == 0){
			query.setParameter("supermasterid", userid);

		}else if(type ==1){
			query.setParameter("masterid", userid);
		}else if(type ==4){
			query.setParameter("adminid", userid);
		}else if(type ==5){
			query.setParameter("subadminid", userid);
		}
		else{
			query.setParameter("dealerid", userid);
		}
		query.setBoolean("active", false);
		query.setParameter("type", "Settlement");
		List<Object[]> resultList = query.getResultList();
		for (Object[] a : resultList) {
			betlist = new ExMatchOddsBet();
			betlist =betlist.getMainDateWiseBets(a);
			list.add(betlist);
			
		}

		return list;
	}



	@Override
	public ArrayList<ExMatchOddsBet> getSubadminCommission(Integer resultid, String child, Boolean settled,String childid, String query1) {
		// TODO Auto-generated method stub
		
	
		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		if(resultid!=0){
			sb.append(" resultid=:resultid ");

		}else{
			
			sb.append(" isSubadminSettled=:isSubadminSettled and subadminid =:subadminid  ");
		}
		sb.append(" and type!= :type group by subadminname,subadminid order by  subadminname asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(resultid!=0){
			query.setParameter("resultid", resultid);

		}else{
			
			query.setBoolean("isSubadminSettled", settled);
			query.setParameter("subadminid", childid);
		}
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
	}



	@Override
	public ArrayList<ExMatchOddsBet> getSupermasterCommission(Integer resultid, String subadminid, String child,Boolean settled, String childid, String query1) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		if(resultid!=0){
			sb.append(" resultid=:resultid and subadminid=:subadminid  ");

		}else{
			
			sb.append(" isSuperMasterSettled=:isSuperMasterSettled and "+query1+" =:childid");
		}
		sb.append(" and type!= :type group by supermastername,supermasterid order by  supermastername asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(resultid!=0){
			query.setParameter("resultid", resultid);
			query.setParameter("subadminid", subadminid);

		}else{
			
			query.setBoolean("isSuperMasterSettled", settled);
			query.setParameter("childid", childid);
		}
		
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
		
		
	}



	@Override
	public ArrayList<ExMatchOddsBet> getResultBets(String fancyid, String usertype, String userid, Integer page,Integer count) {
		// TODO Auto-generated method stub


		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		sb.append(" fancyid=:fancyid and ");

		if(usertype.equalsIgnoreCase("adminid")){
			
			sb.append(" adminid=:adminid ");
			
		}else if(usertype.equalsIgnoreCase("subadminid")){
			
			sb.append(" subadminid=:subadminid ");
		}else if(usertype.equalsIgnoreCase("supermasterid")){
			
			sb.append(" supermasterid=:supermasterid ");
		}else if(usertype.equalsIgnoreCase("masterid")){
			
			sb.append(" masterid=:masterid ");
		}else if(usertype.equalsIgnoreCase("dealerid")){
			
			sb.append(" dealerid=:dealerid ");
		}
		
		sb.append(" and type!= :type order by  userid asc");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());

		query.setParameter("fancyid", fancyid);
		if(usertype.equalsIgnoreCase("adminid")){
			
			query.setParameter("adminid", userid);
			
		}else if(usertype.equalsIgnoreCase("subadminid")){
			query.setParameter("subadminid", userid);
			
		}else if(usertype.equalsIgnoreCase("supermasterid")){
			query.setParameter("supermasterid", userid);
			
		}else if(usertype.equalsIgnoreCase("masterid")){
			
			query.setParameter("masterid", userid);
			
		}else if(usertype.equalsIgnoreCase("dealerid")){
			
			query.setParameter("dealerid", userid);
		}
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
		
		


	}



	@Override
	public ArrayList<ExMatchOddsBet> getMasterCommission(Integer resultid,String supermastername,String subadminname,String child,Boolean settled,String childid,String query1) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		if(resultid!=0){
			sb.append(" resultid=:resultid and supermastername=:supermastername ");
		
		}else{
			
			sb.append(" isMasterSettled=:isMasterSettled and "+query1+" =:childid");
		}
		sb.append(" and type!= :type group by mastername,masterid order by  mastername asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(resultid!=0){
			query.setParameter("resultid", resultid);
			query.setParameter("supermastername", supermastername);

		}else{
			
			query.setBoolean("isMasterSettled", settled);
			query.setParameter("childid", childid);
		}
		
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
	
		
	}



	@Override
	public ArrayList<ExMatchOddsBet> getDealerCommission(Integer resultid, String mastername, String supermastername,String subadminname, String child, Boolean settled, String childid, String query1) {
		// TODO Auto-generated method stub
		
		
		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		if(resultid!=0){
			sb.append(" resultid=:resultid and mastername=:mastername ");
		
		}else{
			
			sb.append(" isDealerSettled=:isDealerSettled and "+query1+" =:childid");
		}
		sb.append(" and type!= :type group by dealername,dealerid order by  dealername asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(resultid!=0){
			query.setParameter("resultid", resultid);
			query.setParameter("mastername", mastername);

		}else{
			
			query.setBoolean("isDealerSettled", settled);
			query.setParameter("childid", childid);
		}
		
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
		
	}
	
	
	
	
	@Override
	   public ArrayList<ExMatchOddsBet>  getUserCommission(Integer resultid, String dealername,String child,Boolean settled,String userid,String query1) {
		
		StringBuffer sb  = new StringBuffer();
		sb.append(" from ExMatchOddsBet  where ");
		if(resultid!=0){
			sb.append(" resultid=:resultid and dealername=:dealername ");
		
		}else{
			
			sb.append(" isUserSettled=:isUserSettled  and "+query1+" =:userid");
		}
		sb.append(" and type!= :type group by username,dealername,dealer,userid,userchain order by  userid asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(resultid!=0){
			query.setParameter("resultid", resultid);
			query.setParameter("dealername", dealername);

		}else{
			
			query.setBoolean("isUserSettled", settled);
			query.setParameter("userid", userid);
		}
		
		query.setParameter("type", "Settlement");
		 ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
			
		
		return resultList;
		
	}



	@Override
	public ArrayList<ExMatchOddsBet> getBetsRollBack(Integer resultid, String type, Integer page, Integer count) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();

		sb.append("from  ExMatchOddsBet where ");
 
		sb.append(" resultid =:resultid and type =:type order by userid  asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());


		query.setParameter("resultid", resultid);
		query.setParameter("type", type);
		
		
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;
	}



	@Override
	public String abandendTieMarketRollBack(String marketid, String matchstatus, Integer matchid) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ArrayList<ExMatchOddsBet> getBets(LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
	ArrayList<Integer> sportids = new ArrayList<Integer>();
		sb.append("from  ExMatchOddsBet where ");
		
		if(!filterData.get("matchid").equals("-1")){
			sb.append(" matchid=:matchid and");
			
		}if(!filterData.get("isback").equals("-1")){
			sb.append(" isback=:isback");
		}
	
		if(filterData.get("sportid").equalsIgnoreCase("-1")){
			ArrayList<Sport> list = sportRepo.findByStatus(true);
			for(Sport sp : list){
				sportids.add(sp.getSportId());
			}
		
		}else{
			sportids.add(Integer.parseInt(filterData.get("sportid")));
		}
		
		sb.append("  sportid in (:sportids)");
		
		
		
		if(!filterData.get("isdeleted").equals("-1")){
			sb.append("  and isdeleted=:isdeleted");
		}
		if(!filterData.get("masterid").equals("-1")){
			sb.append("  and masterid=:masterid");
		}
		if(!filterData.get("status").equals("-1")){
			sb.append("  and status=:status");

		}
		if(!filterData.get("dealerid").equals("-1")){
			sb.append("  and dealerid=:dealerid");
		}
		if(!filterData.get("userid").equals("-1")){
			sb.append("  and userid=:userid");
		}
		if(!filterData.get("type").equals("-1")){
			sb.append("  and type=:type");
		}
		if(!filterData.get("adminid").equals("-1")){
			sb.append("  and adminid=:adminid");
		}
		
		if(!filterData.get("subadminid").equals("-1")){
			sb.append("  and subadminid=:subadminid");
		}
		
		if(!filterData.get("supermasterid").equals("-1")){
			sb.append("  and supermasterid=:supermasterid");
		}
		
		if(!filterData.get("isactive").equals("-1")){
			sb.append("  and isactive=:isactive");
		}
		
		if(!filterData.get("marketid").equals("-1")){
			sb.append("  and marketid=:marketid");
		}
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		if(!filterData.get("matchid").equals("-1")){
			query.setParameter("matchid", Integer.parseInt(filterData.get("matchid")));
			
		}if(!filterData.get("isback").equals("-1")){
			query.setBoolean("isback",true);
		}
		query.setParameterList("sportids", sportids);
	
		if(!filterData.get("isdeleted").equals("-1")){
			query.setBoolean("isdeleted", Boolean.parseBoolean(filterData.get("isdeleted")));
		}
		if(!filterData.get("masterid").equals("-1")){
			query.setParameter("masterid", filterData.get("masterid"));
		}
		if(!filterData.get("status").equals("-1")){
			query.setParameter("status", filterData.get("status"));

		}
		if(!filterData.get("dealerid").equals("-1")){
			query.setParameter("dealerid", filterData.get("dealerid"));

		}
		if(!filterData.get("userid").equals("-1")){
			query.setParameter("userid", filterData.get("userid"));
		}
		if(!filterData.get("type").equals("-1")){
			query.setParameter("type", filterData.get("type"));
		}
		if(!filterData.get("adminid").equals("-1")){
			query.setParameter("adminid", filterData.get("adminid"));
		}
		
		if(!filterData.get("subadminid").equals("-1")){
			query.setParameter("subadminid", filterData.get("subadminid"));
		}
		
		if(!filterData.get("supermasterid").equals("-1")){
			query.setParameter("supermasterid", filterData.get("supermasterid"));
		}
		
		if(!filterData.get("isactive").equals("-1")){
			query.setBoolean("isactive", Boolean.valueOf(filterData.get("isactive")));
		}
		
		if(!filterData.get("marketid").equals("-1")){
			query.setParameter("marketid", filterData.get("marketid"));
		}
		
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
		return resultList;

	}



	@Override
	public ArrayList<ExMatchOddsBet> getMatchedBets(String id, String type, Boolean active, Integer matchid,String marketname,Integer startpage,Integer endpage,Boolean count) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		
		sb.append("from  ExMatchOddsBet where ");
		
	
		if(type.equalsIgnoreCase("adminid")){
			sb.append(" adminid=:adminid and");
		}
		if(type.equalsIgnoreCase("masterid")){
			sb.append(" masterid=:masterid and");
		}
		if(type.equalsIgnoreCase("dealerid")){
			sb.append(" dealerid=:dealerid and");
		}
		if(type.equalsIgnoreCase("subadminid")){
			sb.append(" subadminid=:subadminid and");
		}
		if(type.equalsIgnoreCase("supermasterid")){
			sb.append(" supermasterid=:supermasterid and");
		}
		sb.append(" matchid=:matchid and");
		sb.append(" isactive=:isactive and type!= :type and marketname =:marketname order by matchedtime desc");
		
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		query.setParameter("type", "Settlement");
		
		
		if(type.equalsIgnoreCase("adminid")){
			query.setParameter("adminid", id);
		}
		if(type.equalsIgnoreCase("masterid")){
			query.setParameter("masterid", id);
		}
		if(type.equalsIgnoreCase("dealerid")){
			query.setParameter("dealerid", id);
		}
		if(type.equalsIgnoreCase("subadminid")){
			query.setParameter("subadminid", id);
		}
		if(type.equalsIgnoreCase("supermasterid")){
			query.setParameter("supermasterid", id);
		}
		query.setParameter("matchid", matchid);
		query.setBoolean("isactive", true);
		query.setParameter("marketname", marketname);
	
		if(endpage>0) {
			if(startpage >-1){
				query.setFirstResult(startpage);
				query.setMaxResults(0 + endpage);
			}
		}
		
	
		
	
		
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
		return resultList;
	}

	


	@Override
	public ArrayList<ExMatchOddsBet> getMarketbetBySeries(String marketid, Integer start, Integer end,Boolean isdeleted) {
		StringBuffer sb  = new StringBuffer();
	
		ArrayList<Integer> sportids= new ArrayList<Integer>();
		sb.append("from  ExMatchOddsBet where marketid =:marketid and series >= :start and series <=:end and isdeleted =:isdeleted ");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("marketid", marketid);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setBoolean("isdeleted", isdeleted);
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;
	}



	@Override
	public ArrayList<ExMatchOddsBet> getBetsResult(String marketid, String sorttype, String type, Integer page,
			Integer count, Boolean setActive, Boolean isActive) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();

		sb.append("from  ExMatchOddsBet where ");
		if(type.equalsIgnoreCase("Odds")) {
			sb.append("marketid =:marketid");
		}else {
			sb.append("fancyid =:marketid");
		}

		sb.append(" and isactive =:active order by"+sorttype+"asc");
		Query query = (Query) entitymanager.createQuery(sb.toString());

		if(type.equalsIgnoreCase("Odds")) {
			query.setParameter("marketid", marketid);
		}else {
			query.setParameter("fancyid", marketid);
		}
		query.setBoolean("active", true);
		

		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;

	}



	@Override
	public ArrayList<ExMatchOddsBet> getDateWiseBets(String userid, LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub

		StringBuffer sb  = new StringBuffer();
		ArrayList<Integer> sportids= new ArrayList<Integer>();
		sb.append("from  ExMatchOddsBet where userid =:userid and status =:status ");
		if(filterData.containsKey("eventid")){
			sb.append(" and matchid =:matchid");
		}
		if(filterData.get("sportid").equals("Cricket")){
			sportids.add(4);

		}
		else if(filterData.get("sportid").equals("All")){
			sportids.add(1);
			sportids.add(2);
			sportids.add(4);

		}else if(filterData.get("sportid").equals("Soccer")){
			sportids.add(1);
		}else if(filterData.get("sportid").equals("Tennis")){
			sportids.add(2);
		}
		sb.append(" and sportid in (:sportids) and type!= :type order by createdon asc ");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());

		query.setParameter("userid",userid);
		query.setParameter("status", "CLOSE");

		if(filterData.containsKey("eventid")){
			query.setParameter("matchid", Integer.parseInt(filterData.get("eventid")));
		}

		query.setParameterList("sportids", sportids);
		
		query.setParameter("type", "Settlement");
		

		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;

	}



	@Override
	public ArrayList<EXChipDetail> getpnlAndComm(String userid, String parentid,LinkedHashMap<String, String> filterData) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("from  EXChipDetail where userid =:userid ");
		ArrayList<String> list = new ArrayList<String>();
		
		if(filterData.containsKey("eventid")){
			sb.append(" and eventid =:eventid");
		}
		sb.append(" and type in (:type) and createdOn Between :startdate and :enddate order By createdOn ");
		
		
		
		Query query = (Query) entitymanager.createQuery(sb.toString());

		
		query.setParameter("userid", userid);
		if(filterData.containsKey("eventid")){
			query.setParameter("eventid", Integer.parseInt(filterData.get("eventid")));
		}
		if(filterData.containsKey("type")){
			
			String market[] =filterData.get("type").split(",");
			for(String mark :market){
				list.add(mark);
			}
			query.setParameterList("type", list);
		}
		query.setParameter("startdate",filterData.get("startdate"));
		query.setParameter("enddate", filterData.get("enddate"));
		ArrayList<EXChipDetail> resultList = (ArrayList<EXChipDetail>) query.list();

		return resultList;
	}



	@Override
	public ArrayList<ExMatchOddsBet> getfancybetBySeries(String fancyid, Integer start, Integer end,
			Boolean isdeleted) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		ArrayList<Integer> sportids= new ArrayList<Integer>();
		sb.append("from  ExMatchOddsBet where marketid =:marketid and series>= :start and series <=:end and isdeleted =:isdeleted ");
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("marketid", fancyid);
		query.setParameter("start", start);
		query.setParameter("end", end);
		query.setBoolean("isdeleted", isdeleted);
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		return resultList;
		
	}



	@Override
	public ArrayList<ExMatchOddsBet> getFancyBook(String user, String userid, String fancyid,Integer matchid) {
		// TODO Auto-generated method stub
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		
			StringBuffer sb  = new StringBuffer();

			//sb.append("select sum(bt.stake) as stake ,bt.odds,sum(bt.pnl) as pnl,sum(bt.liability) as liblity from  ExMatchOddsBet bt  where bt.marketid =:marketid and bt.isactive =:isactive and bt."+user+" =:userid and bt.matchid =:matchid order by bt.odds desc   ");
			
			sb.append("from  ExMatchOddsBet  where marketid =:marketid and isactive =:isactive and "+user+" =:userid and matchid =:matchid order by odds desc   ");
			
			
			Query query = (Query) entitymanager.createQuery(sb.toString());

			query.setParameter("userid", userid);
			query.setParameter("marketid", fancyid);
			query.setParameter("matchid", matchid);
			query.setBoolean("isactive", true);
			
			 
			/*
			 * List<Object[]> resultList = query.getResultList(); ExMatchOddsBet betlist
			 * =null; for (Object[] a : resultList) { betlist = new ExMatchOddsBet();
			 * betlist =betlist.getFancyBook(a); list.add(betlist); }
			 */
			ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

			return resultList;

	}





	

	

	@Override
	public Integer getNextSequence(String marketid) {
		// TODO Auto-generated method stub
		Integer seq=1;
		StringBuffer sb  = new StringBuffer();

		sb.append("select count(*) from  ExMatchOddsBet where marketid =:marketid");
		
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		query.setParameter("marketid", marketid);
		
		Long count = (Long)query.uniqueResult();
		
		if((int)(long) count>0){
			seq = (int)(long) count+1;
		}
		
		return seq;
	}
	
	public void updateSeries(String marketid,String markettype,Integer id ){
		
		StringBuffer sb  = new StringBuffer();
		if(markettype.equalsIgnoreCase(EXConstants.MatchOdds)){
			sb.append("UPDATE t_betlist bet, ( SELECT (count(*)) AS cnt FROM t_betlist where marketid =:marketid ) AS bt SET bet.series = bt.cnt");
			
		}else{
			sb.append("UPDATE t_betlist bet, ( SELECT (count(*)) AS cnt FROM t_betlist where fancyid =:fancyid ) AS bt SET bet.series = bt.cnt");
			
		}
		sb.append(" WHERE bet.id =:id ");
		if(markettype.equalsIgnoreCase(EXConstants.MatchOdds)){
			sb.append(" and bet.marketid= :marketid");
		}else{
			sb.append(" and bet.fancyid= :fancyid");
		}
		sb.append(" and bet.type =:markettype");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		query.setParameter("id", id);
		query.setParameter("markettype", markettype);
		if(markettype.equalsIgnoreCase(EXConstants.MatchOdds)){
			query.setParameter("marketid", marketid);
		}else{
			query.setParameter("fancyid", marketid);
		}
		
		query.executeUpdate();
		
	}
	 
	
	@Override
	public ArrayList<ExMatchOddsBet> betHistory(String marketId, Integer matchId, Boolean isdeleted,String userid,Boolean isactive,Integer usertype) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("select userid,matchname,selectionname,marketname,odds,stake,netpnl,placetime,matchedtime,isback,ROW_NUMBER() OVER (  ORDER BY createdon desc  ) as seriolno from  t_betlist where  matchId =:matchId and isdeleted =:isdeleted and isactive =:isactive   ");
		if(!marketId.equalsIgnoreCase("-1")) {
			sb.append("and marketId =:marketId  ");
		}
		
		if(usertype == 4) {
			sb.append(" and adminid =:adminid");
			
		}else if(usertype == 5) {
			sb.append(" and subadminid =:subadminid");
			
		}else if(usertype == 0) {
			sb.append(" and supermasterid =:supermasterid");
			
		}else if(usertype == 1) {
			sb.append(" and masterid =:masterid");
			
		}else if(usertype == 2) {
			sb.append(" and dealerid =:dealerid");
			
		}
		Integer  startpage =0;
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		
		//query.setParameter("marketId", marketId);
		query.setParameter("matchId", matchId);
		query.setBoolean("isdeleted", isdeleted);
		query.setParameter("isactive", isactive);
		if(!marketId.equalsIgnoreCase("-1")) {
			query.setParameter("marketId", marketId);
			
		}
		if(usertype == 4) {
			query.setParameter("adminid", userid);
			
		}else if(usertype == 5) {
			query.setParameter("subadminid", userid);
			
		}else if(usertype == 0) {
			query.setParameter("supermasterid", userid);
			
		}else if(usertype == 1) {
			query.setParameter("masterid", userid);
			
		}else if(usertype == 2) {
			query.setParameter("dealerid", userid);
			
		}
		
		
		if(startpage >-1){
			query.setFirstResult(startpage);
			query.setMaxResults(0 + 1000);
		}
	
		//ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		
		List<Object[]> resultList = query.getResultList();
		ExMatchOddsBet betlist =null;
		for (Object[] a : resultList) {
		betlist = new ExMatchOddsBet();
		betlist =betlist.betHistory(a);
		list.add(betlist);
		}
		 
		return list;
		
	}



	@Override
	public ArrayList<ExMatchOddsBet> currentBets(Boolean isdeleted, String userId, Boolean isactive, Integer usertype,String type) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		sb.append("from  ExMatchOddsBet where isdeleted =:isdeleted and isactive =:isactive   ");
			sb.append(" and userid =:userid");
			
		
		
		
			
		Integer  startpage =0;
		Query query = (Query) entitymanager.createQuery(sb.toString());
		
		
		query.setBoolean("isdeleted", isdeleted);
		query.setParameter("isactive", isactive);
			query.setParameter("userid", userId);
			
		
	
		ArrayList<ExMatchOddsBet> resultList = (ArrayList<ExMatchOddsBet>) query.list();

		
		return resultList;
		
	}



	@Override
	public Long getMatchedBetsTotalCount(String id, String type, Boolean active, Integer matchid,String marketname) {
		// TODO Auto-generated method stub
		
		StringBuffer sb  = new StringBuffer();
		
		sb.append(" select count(*) from  ExMatchOddsBet where ");
		
	
		if(type.equalsIgnoreCase("adminid")){
			sb.append(" adminid=:adminid and");
		}
		if(type.equalsIgnoreCase("masterid")){
			sb.append(" masterid=:masterid and");
		}
		if(type.equalsIgnoreCase("dealerid")){
			sb.append(" dealerid=:dealerid and");
		}
		if(type.equalsIgnoreCase("subadminid")){
			sb.append(" subadminid=:subadminid and");
		}
		if(type.equalsIgnoreCase("supermasterid")){
			sb.append(" supermasterid=:supermasterid and");
		}
		sb.append(" matchid=:matchid and");
		sb.append(" isactive=:isactive and type!= :type and marketname =:marketname order by series desc");
		
		
		Query query = (Query) entitymanager.createQuery(sb.toString());
		query.setParameter("type", "Settlement");
		
		
		if(type.equalsIgnoreCase("adminid")){
			query.setParameter("adminid", id);
		}
		if(type.equalsIgnoreCase("masterid")){
			query.setParameter("masterid", id);
		}
		if(type.equalsIgnoreCase("dealerid")){
			query.setParameter("dealerid", id);
		}
		if(type.equalsIgnoreCase("subadminid")){
			query.setParameter("subadminid", id);
		}
		if(type.equalsIgnoreCase("supermasterid")){
			query.setParameter("supermasterid", id);
		}
		query.setParameter("matchid", matchid);
		query.setBoolean("isactive", true);
		query.setParameter("marketname", marketname);
		
	
		
	
		
		Long count = (Long)query.uniqueResult();
		return count;
	}
	
	
	@Override
	public ArrayList<ExMatchOddsBet> getFancyBookNew(String user, String userid, String fancyid,String partnership) {
		// TODO Auto-generated method stub
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		
			StringBuffer sb  = new StringBuffer();

			//sb.append("select sum(bt.stake) as stake ,bt.odds,sum(bt.pnl) as pnl,sum(bt.liability) as liblity from  ExMatchOddsBet bt  where bt.marketid =:marketid and bt.isactive =:isactive and bt."+user+" =:userid and bt.matchid =:matchid order by bt.odds desc   ");
			
			sb.append("select  bt.stake,bt.odds,bt.pnl,bt.liability,"+partnership+",bt.isback,bt.marketid  from  t_betlist bt, t_user usr  where marketid =:marketid and isactive =:isactive and bt."+user+" =:userid and bt.userid=usr.userid  order by odds desc   ");
			
			
			Query query = (Query) entitymanager.createNativeQuery(sb.toString());

			query.setParameter("userid", userid);
			query.setParameter("marketid", fancyid);
			query.setBoolean("isactive", true);
			
			 
			/*
			 * List<Object[]> resultList = query.getResultList(); ExMatchOddsBet betlist
			 * =null; for (Object[] a : resultList) { betlist = new ExMatchOddsBet();
			 * betlist =betlist.getFancyBook(a); list.add(betlist); }
			 */
			List<Object[]> resultList1 = query.getResultList();
			ArrayList<ExMatchOddsBet> resultList =null;
			ExMatchOddsBet betlist=null;
			for (Object[] a : resultList1) {
				betlist = new ExMatchOddsBet();
				betlist =betlist.getFancyBook(a);
				list.add(betlist);
				}
			
			return list;

	}
	
	@Override
//	@Cacheable(value="fancyExpoCache")
	public ArrayList<ExMatchOddsBet> getFancyBookExposure(String user, String userid, List<String> fancyids,String partnership) {
		// TODO Auto-generated method stub
		ArrayList<ExMatchOddsBet> list = new ArrayList<ExMatchOddsBet>();
		
			StringBuffer sb  = new StringBuffer();

			//sb.append("select sum(bt.stake) as stake ,bt.odds,sum(bt.pnl) as pnl,sum(bt.liability) as liblity from  ExMatchOddsBet bt  where bt.marketid =:marketid and bt.isactive =:isactive and bt."+user+" =:userid and bt.matchid =:matchid order by bt.odds desc   ");
			
			sb.append("select  bt.stake,bt.odds,bt.pnl,bt.liability,"+partnership+",bt.isback,bt.marketid  from  ExMatchOddsBet bt, EXUser usr  where bt.isactive =:isactive and bt."+user+" =:userid and bt.userid=usr.userid and bt.marketid IN :marketid  order by odds desc   ");
			
			
			Query query = (Query) entitymanager.createQuery(sb.toString());

			query.setParameter("userid", userid);
			query.setParameter("marketid", fancyids);
			query.setBoolean("isactive", true);
			
			
			//sb.append("  sportid in (:sportids)");
			
			
			List<Object[]> resultList1 = query.getResultList();
			ArrayList<ExMatchOddsBet> resultList =null;
			ExMatchOddsBet betlist=null;
			for (Object[] a : resultList1) {
				betlist = new ExMatchOddsBet();
				betlist =betlist.getFancyBook(a);
				list.add(betlist);
				}
			
			return list;

	}

}	


