package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.List;

import com.exchange.api.bean.DiamonCasinoResult;
import com.exchange.api.bean.EXChipDetailMongo;

public interface EXChipDetailsMongoCustomRepository  {
	
	public List<EXChipDetailMongo> getStatementM(String userId,String startDate,String endDate,String reportType,Integer pageNo);

	public List<EXChipDetailMongo> getStatementSettlementM(String userId, String startDate, String endDate);

	long getStatementMCount(String userId, String startDate, String endDate, String reportType);

	List<DiamonCasinoResult> getCasinoResult(String startDate, String eventId);


}
