package com.exchange.api.repositiry;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.DiamonCasinoResult;
import com.exchange.api.bean.EXChipDetailMongo;
import com.exchange.util.EXDateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EXChipDetailsMongoCustomRepositoryImpl implements EXChipDetailsMongoCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<EXChipDetailMongo> getStatementM(String userId, String startDate, String endDate, String reportType,Integer pageNo) {

		final Query query = new Query();
		DateFormat formatter = null;
		EXDateUtil dateUtil = new EXDateUtil();
		List<EXChipDetailMongo> listobj = new ArrayList<>();
		try {
			Date startDate1 = null, endDate1 = null;
			Date updatedEndDate = null, updatedStartDate = null;

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
			
			updatedEndDate = dateUtil.addSecondsMinutesAndHours((datef.parse(datef.format(formatter.parse(endDate)))), 
					59, 59, 23);
			updatedStartDate = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(startDate))));

			final List<Criteria> criteria = new ArrayList<>();
			if (userId != null && !userId.isEmpty())
				criteria.add(Criteria.where("userid").is(userId));
			if (reportType != null && !reportType.isEmpty() && !reportType.equalsIgnoreCase("-1")) {
				criteria.add(Criteria.where("type").is(reportType));
			}

			if (startDate != null && !startDate.isEmpty() && (endDate != null && !endDate.isEmpty())) {
				criteria.add(Criteria.where("createdon").lt(updatedEndDate).gt(updatedStartDate));
			}

			if (!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])))
						.with(Sort.by(Sort.Direction.DESC, "createdon")).skip(pageNo * 500).limit(500);
			}
			
			List<EXChipDetailMongo> queryresults = mongoTemplate.find(query, EXChipDetailMongo.class);

		    long count = mongoTemplate.count(query, EXChipDetailMongo.class);
		
			for (EXChipDetailMongo exChipDetailMongo : queryresults) {
				String date = formatter.format(exChipDetailMongo.getCreatedOn());
				//System.out.println(dateUtil.convertToDate(exChipDetailMongo.getCreatedOn(), "yyyy-MM-dd"));
				EXChipDetailMongo obj = new EXChipDetailMongo();
				obj.setDate(dateUtil.convertToString(exChipDetailMongo.getCreatedOn(), "yyyy-MM-dd HH:mm:ss"));
				obj.setDescreption(exChipDetailMongo.getDescreption());
				obj.setFromTo(exChipDetailMongo.getFromTo());
				obj.setCredit(exChipDetailMongo.getCredit());
				obj.setDebit(exChipDetailMongo.getDebit());
				obj.setClosing(exChipDetailMongo.getClosing());
				obj.setMarketId(exChipDetailMongo.getMarketId());
				obj.setMatchId(exChipDetailMongo.getMatchId());
				obj.setId(exChipDetailMongo.getId());
				listobj.add(obj);
			}
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}
		
		return listobj;
	}

	@Override
	public List<EXChipDetailMongo> getStatementSettlementM(String userId, String startDate, String endDate) {

		final Query query = new Query();
		DateFormat formatter = null;
		try {
			Date startDate1 = null, endDate1 = null;

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
			
			EXDateUtil dateUtil = new EXDateUtil();


			try {
					
				endDate1	 = dateUtil.addSecondsMinutesAndHours((datef.parse(datef.format(formatter.parse(endDate)))), 
						59, 59, 23);
				startDate1 = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(startDate))));
			
			} catch (ParseException e) {
				log.info("exception occured: " + e.getMessage());
			}

			final List<Criteria> criteria = new ArrayList<>();
			if (userId != null && !userId.isEmpty())
				criteria.add(Criteria.where("userid").is(userId));

			if (startDate != null && !startDate.isEmpty() && (endDate != null && !endDate.isEmpty())) {
				criteria.add(Criteria.where("createdon").lt(endDate1).gt(startDate1));
			}

			criteria.add(Criteria.where("issettlement").is(true));
			
			if (!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])))
						.with(Sort.by(Sort.Direction.DESC, "createdon"));
			}
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}
		List<EXChipDetailMongo> queryresults = mongoTemplate.find(query, EXChipDetailMongo.class);
		List<EXChipDetailMongo> listobj = new ArrayList<>();
		for (EXChipDetailMongo exChipDetailMongo : queryresults) {
			String date = formatter.format(exChipDetailMongo.getCreatedOn());
			EXChipDetailMongo obj = new EXChipDetailMongo();
			obj.setDate(date);
			obj.setDescreption(exChipDetailMongo.getDescreption());
			obj.setFromTo(exChipDetailMongo.getFromTo());
			obj.setCredit(exChipDetailMongo.getCredit());
			obj.setDebit(exChipDetailMongo.getDebit());
			obj.setClosing(exChipDetailMongo.getClosing());
			obj.setId(exChipDetailMongo.getId());
			listobj.add(obj);
		}
		return listobj;
	}
	@Override
	public long getStatementMCount(String userId, String startDate, String endDate, String reportType) {

		final Query query = new Query();
		DateFormat formatter = null;
		try {
			Date startDate1 = null, endDate1 = null;
			Date updatedEndDate = null, updatedStartDate = null;

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
			EXDateUtil dateUtil = new EXDateUtil();

			try {
				updatedEndDate = dateUtil.addSecondsMinutesAndHours((datef.parse(datef.format(formatter.parse(endDate)))), 
						59, 59, 23);
				updatedStartDate = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(startDate))));
				
			} catch (ParseException e) {
				log.info("exception occured: " + e.getMessage());
			}

			final List<Criteria> criteria = new ArrayList<>();
			if (userId != null && !userId.isEmpty())
				criteria.add(Criteria.where("userid").is(userId));
			if (reportType != null && !reportType.isEmpty() && !reportType.equalsIgnoreCase("-1")) {
				criteria.add(Criteria.where("type").is(reportType));
			}

			if (startDate != null && !startDate.isEmpty() && (endDate != null && !endDate.isEmpty())) {
				//criteria.add(Criteria.where("createdon").lt(endDate1).gt(startDate1));
				criteria.add(Criteria.where("createdon").lt(updatedEndDate).gt(updatedStartDate));
			}

			if (!criteria.isEmpty()) {
				query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])))
						.with(Sort.by(Sort.Direction.DESC, "createdon"));
			}
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}
		List<EXChipDetailMongo> queryresults = mongoTemplate.find(query, EXChipDetailMongo.class);

	    long count = mongoTemplate.count(query, EXChipDetailMongo.class);
		
		return count;
	}
	
	
	@Override
	public 	List<DiamonCasinoResult> getCasinoResult(String startDate, String eventId) {

		final Query query = new Query();
		DateFormat formatter = null;
		EXDateUtil dateUtil = new EXDateUtil();
		List<EXChipDetailMongo> listobj = new ArrayList<>();
		List<DiamonCasinoResult> queryresults = new 	ArrayList<DiamonCasinoResult>();
		try {
			Date startDate1 = null, endDate1 = null;
			Date updatedEndDate = null, updatedStartDate = null;

			formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat datef = new SimpleDateFormat("yyyy/MM/dd");
			
		
			final List<Criteria> criteria = new ArrayList<>();
			updatedStartDate = dateUtil.convertISTtoGMT(datef.parse(datef.format(formatter.parse(startDate))));

			

		
			
			criteria.add(Criteria.where("updatedAt").is(updatedStartDate));
			
			criteria.add(Criteria.where("gameId").is(eventId));
			
			 queryresults = mongoTemplate.find(query, DiamonCasinoResult.class);

		
			 System.out.println(queryresults);
				
		
		} catch (Exception ex) {
			log.info("Exception occured: " + ex.getMessage());
		}
		
		return queryresults;
	}
	
}
