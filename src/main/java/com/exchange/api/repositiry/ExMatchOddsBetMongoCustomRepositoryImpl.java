package com.exchange.api.repositiry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXLedgerMongo;
import com.exchange.api.bean.EXMatchViewer;
import com.exchange.api.bean.ExMatchOddsBetMongo;
import com.exchange.util.EXDateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ExMatchOddsBetMongoCustomRepositoryImpl implements ExMatchOddsBetMongoCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// @Override
	public List<ExMatchOddsBetMongo> findBetListByProperties(Integer matchid, Boolean isActive, String adminid,
			String subadminid, String supermaster, String master, String dealerid) {
		final Query query = new Query();// .with(page);
//	     query.fields().include("id").include("name");
		final List<Criteria> criteria = new ArrayList<>();
		if (matchid != null && matchid > 0)
			criteria.add(Criteria.where("matchid").is(matchid));
		if (isActive != null)
			criteria.add(Criteria.where("isactive").is(isActive));
		if (adminid != null && !adminid.isEmpty())
			criteria.add(Criteria.where("adminid").is(adminid));
		if (subadminid != null && !subadminid.isEmpty())
			criteria.add(Criteria.where("subadminid").is(subadminid));
		if (supermaster != null && !supermaster.isEmpty())
			criteria.add(Criteria.where("supermasterid").in(supermaster));
		if (master != null && !master.isEmpty())
			criteria.add(Criteria.where("masterid").is(master));
		if (dealerid != null)
			criteria.add(Criteria.where("dealerid").is(dealerid));

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		}
		return mongoTemplate.find(query, ExMatchOddsBetMongo.class);
	}

	@Override
	public List<ExMatchOddsBetMongo> findBetListByProperties(Integer matchid, Boolean isActive, String key,
			String value, String date, String userid) {
		
		DateFormat formatter = null;
		Date startDate1 = null, endDate1 = null;
		
		final Query query = new Query();// .with(page);
		
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
			
			log.info("date: " + date);

			startDate1 = datef.parse(datef.format(formatter.parse(date)));

			endDate1 = DateUtils.addMinutes((DateUtils.addHours(startDate1, 23)), 59); // add hours && minutes
			
//		     query.fields().include("id").include("name");
			final List<Criteria> criteria = new ArrayList<>();
			if (matchid != null && matchid > 0)
				criteria.add(Criteria.where("matchid").is(matchid));
			if (isActive != null)
				criteria.add(Criteria.where("isactive").is(isActive));
			if (key.contentEquals("adminid"))
				criteria.add(Criteria.where("adminid").is(value));
			if (key.contentEquals("subadminid"))
				criteria.add(Criteria.where("subadminid").is(value));
			if (key.contentEquals("supermasterid"))
				criteria.add(Criteria.where("supermasterid").in(value));
			if (key.contentEquals("masterid"))
				criteria.add(Criteria.where("masterid").is(value));
			if (key.contentEquals("dealerid"))
				criteria.add(Criteria.where("dealerid").is(value));
			/*
			 * if (key.contentEquals("userid"))
			 * criteria.add(Criteria.where("userid").is(value));
			 */
			if (date != null)
				//criteria.add(Criteria.where("createdon").lte(date));
				criteria.add(Criteria.where("createdon").lt(endDate1).gt(startDate1));
			
			if(userid != null && !userid.equalsIgnoreCase("-1"))
				criteria.add(Criteria.where("userid").is(userid));

			if (!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
			}
			log.info("query: " + query);
			log.info("Query Result:" + mongoTemplate.find(query, ExMatchOddsBetMongo.class));
		} catch(Exception ex) {
			log.info("Exception Occured: "+ex.getMessage());
		}
		
		return mongoTemplate.find(query, ExMatchOddsBetMongo.class);
	}
	
	@Override
	public List<ExMatchOddsBetMongo> findBetListByProperties(Integer matchid, Boolean isActive, String key,
			String value, String userid) {
		final Query query = new Query();// .with(page);
//	     query.fields().include("id").include("name");
		final List<Criteria> criteria = new ArrayList<>();
		if (matchid != null && matchid > 0)
			criteria.add(Criteria.where("matchid").is(matchid));
		if (isActive != null)
			criteria.add(Criteria.where("isactive").is(isActive));
		if (key.contentEquals("adminid"))
			criteria.add(Criteria.where("adminid").is(value));
		if (key.contentEquals("subadminid"))
			criteria.add(Criteria.where("subadminid").is(value));
		if (key.contentEquals("supermasterid"))
			criteria.add(Criteria.where("supermasterid").in(value));
		if (key.contentEquals("masterid"))
			criteria.add(Criteria.where("masterid").is(value));
		if (key.contentEquals("dealerid"))
			criteria.add(Criteria.where("dealerid").is(value));
		
		
		if(userid != null && !userid.equalsIgnoreCase("-1"))
			criteria.add(Criteria.where("userid").is(userid));

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		}
		return mongoTemplate.find(query, ExMatchOddsBetMongo.class);
	}

	@Override
	public LinkedHashMap<String, Object> findBetListDetailsByProperties(Integer matchid, Boolean isActive, String key,
			String value, String type) {
		final Query query = new Query();// .with(page);
//	     query.fields().include("id").include("name");
		final List<Criteria> criteria = new ArrayList<>();
		if (matchid != null && matchid > 0)
			criteria.add(Criteria.where("matchid").is(matchid));
		if (isActive != null)
			criteria.add(Criteria.where("isactive").is(isActive));
		if (type != null)
			criteria.add(Criteria.where("type").is(type));
		if (key.contentEquals("adminid"))
			criteria.add(Criteria.where("adminid").is(value));
		if (key.contentEquals("subadminid"))
			criteria.add(Criteria.where("subadminid").is(value));
		if (key.contentEquals("supermasterid"))
			criteria.add(Criteria.where("supermasterid").in(value));
		if (key.contentEquals("masterid"))
			criteria.add(Criteria.where("masterid").is(value));
		/*
		 * if (key.contentEquals("dealerid"))
		 * criteria.add(Criteria.where("dealerid").is(value)); if
		 * (key.contentEquals("userid"))
		 * criteria.add(Criteria.where("userid").is(value));
		 */

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(Sort.by(Sort.Direction.DESC, "matchedtime")).limit(100);
		}
	
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("betsList", mongoTemplate.find(query, ExMatchOddsBetMongo.class));
		map.put("totalcount", mongoTemplate.count(query, ExMatchOddsBetMongo.class));
		
		//return mongoTemplate.find(query, ExMatchOddsBetMongo.class);
		return map;
	}

	@Override
	public ArrayList<ExMatchOddsBetMongo> findBetList(Integer matchid, Integer sportId, Boolean isActive,String searchBy, String parentid,String userid, String marketId,Boolean isdeleted,Integer limit) {
		
		final Query query = new Query();// .with(page);
//	     query.fields().include("id").include("name");
		final List<Criteria> criteria = new ArrayList<>();
		
	/*	if(userid.equalsIgnoreCase("0")) {
		
			if(sportId !=-1) {
				criteria.add(Criteria.where("sportid").is(sportId));
				
			}
			
			if(matchid !=-1) {
				criteria.add(Criteria.where("matchid").is(matchid));
					
			}
			
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			if(!marketId.equalsIgnoreCase("-1"))
			criteria.add(Criteria.where("marketid").is(marketId));
			criteria.add(Criteria.where(searchBy).is(parentid));
			
		}*/ if(matchid ==-1 && sportId == -1 && marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else if(matchid != -1 && sportId == -1 && marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else if(matchid != -1 && sportId == -1 && !marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("marketid").is(marketId));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("sportid").is(sportId));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("marketid").is(marketId));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}
		
		

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(Sort.by(Sort.Direction.DESC, "matchedtime")).limit(limit);
		}
		
		
		return  (ArrayList<ExMatchOddsBetMongo>) mongoTemplate.find(query, ExMatchOddsBetMongo.class);
	}
	
	
	@Override
	public ArrayList<EXMatchViewer> getMatchUserCount(Integer matchid) {

	
		ArrayList<EXMatchViewer> list   = new ArrayList<EXMatchViewer>();
		try {
			/*AggregationResults<?> result = mongoTemplate
					.aggregate(Aggregation.newAggregation(Aggregation.match(Criteria.where("matchId").is(matchid)),
							Aggregation.group("appName").count().as("count")
					), EXMatchViewer.class, HashMap.class);
			
			*/
			
			
			AggregationResults<EXMatchViewer> result = mongoTemplate
					.aggregate(Aggregation.newAggregation(EXMatchViewer.class,
							Aggregation.match(Criteria.where("matchId").in(matchid)),
							
							Aggregation.group("appName").count().as("appName")
					), EXMatchViewer.class);
			
			list.addAll(result.getMappedResults());	
			

		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}

		
		return  list;
	}
	
	@Override
	public Integer getMatchBetUserCount(Integer matchid) {

		Integer count =0;
		
		try {
		
			
			
			
			AggregationResults<?> result = mongoTemplate
					.aggregate(Aggregation.newAggregation(
							Aggregation.match(Criteria.where("matchid").is(matchid)),
							Aggregation.group("userid").count().as("count")
					), ExMatchOddsBetMongo.class, HashMap.class);
			
			
			count  =result.getMappedResults().size();
			
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}

		
		return count;
	}

	@Override
	public ArrayList<ExMatchOddsBetMongo> findBetListByParentIdUserId(Integer matchid, Integer sportId,
			Boolean isActive, String searchBy, String parentid, String userid, String marketId, Boolean isdeleted,
			Integer limit) {
		
		final Query query = new Query();// .with(page);
//	     query.fields().include("id").include("name");
		final List<Criteria> criteria = new ArrayList<>();
		
		if(matchid ==-1 && sportId == -1 && marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else if(matchid != -1 && sportId == -1 && marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else if(matchid != -1 && sportId == -1 && !marketId.equalsIgnoreCase("-1")) {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("marketid").is(marketId));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}else {
			
			criteria.add(Criteria.where(searchBy).is(parentid));
			criteria.add(Criteria.where("sportid").is(sportId));
			criteria.add(Criteria.where("matchid").is(matchid));
			criteria.add(Criteria.where("isdeleted").is(isdeleted));
			criteria.add(Criteria.where("marketid").is(marketId));
			criteria.add(Criteria.where("isactive").is(isActive));
			
		}
		
		

		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(Sort.by(Sort.Direction.DESC, "matchedtime")).limit(limit);
		}
		
		
		return  (ArrayList<ExMatchOddsBetMongo>) mongoTemplate.find(query, ExMatchOddsBetMongo.class);
	}
}
