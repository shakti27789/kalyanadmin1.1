package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.exchange.api.bean.EXLedgerMongo;

public interface EXLedgerMongoCustomRepository {
	
	public Map<String, Object> getLedgerStatementDetails(String childid, String matchid, String date, Integer pagenumber);

	
	Map<String, Object> getuserPnlToday(String userId, String matchid, String date);


	ArrayList<EXLedgerMongo> getCassinoProfitLoss(String startdate, String enddate, Integer usertype, String userid,LinkedHashMap<String,String> filterData,ArrayList<Integer> matchIds);

	//public Long getLedgerStatementCount(String childid, String matchid, String date);

}
