package com.exchange.api.repositiry;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.skip;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Sum;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXLedgerMongo;
import com.exchange.util.EXDateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EXLedgerMongoCustomRepositoryImpl implements EXLedgerMongoCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Map<String, Object> getLedgerStatementDetails(String userId, String matchid, String date,
			Integer pagenumber) {

		Map<String, Object> response = new HashMap<String, Object>();

		final Query query = new Query();
		DateFormat formatter = null;
		Integer defaultPageSize = 500;
		final List<Criteria> criteria = new ArrayList<>();
		Date startDate1 = null, endDate1 = null, updatedStartDate = null, updatedEndDate = null;
		Integer limit = 500;
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");

			Pageable pageable = PageRequest.of(pagenumber, defaultPageSize);

			try {
				log.info("date: " + date);
				EXDateUtil dateUtil = new EXDateUtil();
				updatedEndDate = dateUtil.addSecondsMinutesAndHours((datef.parse(datef.format(formatter.parse(date)))),
						59, 59, 23);
				updatedStartDate = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(date))));

			} catch (ParseException e) {
				log.info("exception occured: " + e.getMessage());
			}

		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}

		Criteria criterias = new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]));

		 /* PageRequest pageable = PageRequest.of(pagenumber, defaultPageSize);
		
		  AggregationResults<?> result = mongoTemplate.aggregate(
		  Aggregation.newAggregation(
		  Aggregation.match(Criteria.where("userid").is(userId)),
		  Aggregation.match(Criteria.where("matchid").is(Integer.parseInt(matchid))),
		  Aggregation.match(Criteria.where("createdon").lt(updatedEndDate).gt(
		  updatedStartDate)),
		  Aggregation.group("marketid","id").sum("pnl").as("amount")
		  
		  
		  ,skip(pageable.getPageNumber() * pageable.getPageSize()),
		  limit(pageable.getPageSize()) ), EXLedgerMongo.class, HashMap.class );
		 */
		
		
		
		
		
		
		
		GroupOperation sum = group("marketid").sum("pnl").as("amount").first("createdon").as("createdon");
		SortOperation sortByCount = sort(Direction.DESC, "createdon");
		GroupOperation groupFirstAndLast = group().first("_id").as("minZipState")
				  .first("matchid").as("minZipCount");
		AggregationResults<?> result = mongoTemplate
				.aggregate(Aggregation.newAggregation(Aggregation.match(Criteria.where("userid").is(userId)),
						Aggregation.match(Criteria.where("matchid").is(Integer.parseInt(matchid))),
						  Aggregation.match(Criteria.where("createdon").lt(updatedEndDate).gt(updatedStartDate)),sum,sortByCount
				), EXLedgerMongo.class, HashMap.class);
		

		
		
		
           
		
		response.put("data", result.getMappedResults());
		return response;
	}

	public Page<EXLedgerMongo> list(final Pageable pageable, Criteria criterias) {
		final Aggregation agg = newAggregation(skip(pageable.getPageNumber() * pageable.getPageSize()),
				limit(pageable.getPageSize()));

		TypedAggregation<EXLedgerMongo> aggregation = newAggregation(EXLedgerMongo.class, match(criterias),
				group("marketid", "pnl", "createdon").sum("pnl").as("amount"),
				skip(pageable.getPageNumber() * pageable.getPageSize()), sort(Sort.Direction.DESC, "createdon"),
				limit(pageable.getPageSize())

		);

		final List<EXLedgerMongo> results = mongoTemplate.aggregate(agg, "t_ledger", EXLedgerMongo.class)
				.getMappedResults();

		return new PageImpl<>(results, pageable, 425);
	}

	private Collection<EXLedgerMongo> removeDuplicateRecordsByMarketId(List<EXLedgerMongo> queryresults) {
		// System.out.println("inside removeDuplicateRecordsByMarketId");
		Map<String, EXLedgerMongo> objMap = new LinkedHashMap<>();
		for (EXLedgerMongo obj : queryresults) {
			EXLedgerMongo current = objMap.get(obj.getMarketid());
			if (current == null) {
				objMap.put(obj.getMarketid(), obj);
			} else {
				current.setPnl(current.getPnl() + obj.getPnl());
			}
		}
		Collection<EXLedgerMongo> response = objMap.values();
		// System.out.println("response: "+response);
		return response;
	}

	public List<?> getTotalProfit(String userId, String matchId) {
		AggregationResults<?> result = mongoTemplate.aggregate(Aggregation.newAggregation(Aggregation.unwind("matchid"),
				Aggregation.match(Criteria.where("userid").is(userId)),
				Aggregation.match(Criteria.where("matchid").is(Integer.parseInt(matchId))),
				Aggregation.group("matchid").sum("pnl").as("totalprofit"),
				Aggregation.project("totalprofit").and("_id").as("matchid")// .and("*")
		), EXLedgerMongo.class, HashMap.class);
		return result.getMappedResults();
	}

	
	
	@Override
	public Map<String, Object> getuserPnlToday(String userId, String matchid, String date) {

		Map<String, Object> response = new HashMap<String, Object>();

		DateFormat formatter = null;
		
		Date  updatedStartDate = null, updatedEndDate = null;
		
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");

			
			EXDateUtil dateUtil = new EXDateUtil();
			updatedEndDate = dateUtil.addSecondsMinutesAndHours((datef.parse(datef.format(formatter.parse(date)))),
					59, 59, 23);
			updatedStartDate = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(date))));

		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}

		
			
		AggregationResults<?> resulttodayProfit = mongoTemplate
				.aggregate(Aggregation.newAggregation(Aggregation.match(Criteria.where("userid").is(userId)),
						Aggregation.match(Criteria.where("matchid").is(Integer.parseInt(matchid))),
						  Aggregation.match(Criteria.where("createdon").lt(updatedEndDate).gt(
								  updatedStartDate)),
						  Aggregation.group("matchid").sum("pnl").as("amount")
						  
				), EXLedgerMongo.class, HashMap.class);
		
		
		

		List<?> totalProfit = getTotalProfit(userId, matchid);
		
		
		
		response.put("todayProfit", resulttodayProfit.getMappedResults());
		response.put("totalProfit", totalProfit);
		
		return response;
	}
	
	@Override
	public ArrayList<EXLedgerMongo> getCassinoProfitLoss(String startdate, String enddate, Integer usertype, String userId,LinkedHashMap<String,String> filterData,ArrayList<Integer> matchIds){

		Map<String, Object> response = new HashMap<String, Object>();

		DateFormat formatter = null;
		Date  updatedStartDate = null, updatedEndDate = null;
		ArrayList<EXLedgerMongo> list =new ArrayList<EXLedgerMongo>();
		List<EXLedgerMongo> queryresults = null;
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");

			
				EXDateUtil dateUtil = new EXDateUtil();
				
				dateUtil.convDateTimeZone(startdate, "GMT", "GMT", "yyyy-M-d HH:mm:ss");
				
				updatedStartDate = dateUtil.addHourToDate(dateUtil.convertToDate(startdate, "yyyy-M-d HH:mm:ss"));
				
				updatedEndDate = dateUtil.addHourToDate(dateUtil.convertToDate(enddate, "yyyy-M-d HH:mm:ss"));
				
				
			
			    
			AggregationResults<EXLedgerMongo> result = mongoTemplate
						.aggregate(Aggregation.newAggregation(EXLedgerMongo.class,Aggregation.match(Criteria.where("userid").is(userId)),
								Aggregation.match(Criteria.where("matchid").in(matchIds)),
								  Aggregation.match(Criteria.where("createdon").lt(updatedEndDate).gt(updatedStartDate)),
								Aggregation.group("matchid","matchname").sum("pnl").as("pnlamount").sum("uplineamount").as("uplineamount").sum("downlineamountlenadena").as("downlineamount")
						), EXLedgerMongo.class);
				

				
				   list.addAll( result.getMappedResults());

				//System.out.println(result.getMappedResults());
				 response.put("data", result.getMappedResults());
				 //System.out.println(list);
				
				 for(EXLedgerMongo m : list) {
				//	 System.out.println(m.getMatchid());
					 m.setUplineamount(-m.getUplineamount());
				 }
			
					
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}

		
	   
		return list;
	}

}