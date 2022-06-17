
package com.exchange.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExFancyResult;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.MarketResult;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.UserLiability;

public interface EXSetResultService {
	
	

	public ResponseBean abandendTieMarket(String marketid, String matchstatus, Integer matchid, EXUser usersession);
	
	public ResponseBean abandendTieMarketRollBack(String marketid, String matchstatus,EXUser usersession);


	public String rollBackFancyResultsNew(ArrayList<UserLiability> liblist, String fancyid, Integer id) throws Exception;

	String setBetResultsWithoutCommssionAndMobApp(ArrayList<UserLiability> liblist, MarketResult result);

	String setFancyResultsWithoutCommssionAndMobApp(ArrayList<UserLiability> liblist, ExFancyResult result);

	String rollBackBetResultsNewWithoutCommssionMobCommssion(ArrayList<UserLiability> liblist, MarketResult result);

	String setFancyResultsCron(ArrayList<UserLiability> liblist, ExFancyResult result);

	String setMarketResultCron(ArrayList<UserLiability> liblist, MarketResult result);

	String setFancyResultsCronClearLiability(ArrayList<UserLiability> liblist, ExFancyResult result);

	String setFancyResultsCronProfitLoss(ArrayList<UserLiability> liblist, ExFancyResult result);

	
	String setVTP20ResultCron(ArrayList<UserLiability> liblist) throws Exception;
	
	String setTP20ResultCron(ArrayList<UserLiability> liblist) throws Exception;

	public void abandendTieMarketCron(String marketid,Integer matchid);

	String rollBackBetResultsNew(ArrayList<UserLiability> liblist, MarketResult result);

	String setMarketResultCronWinner(ArrayList<UserLiability> liblist, MarketResult result);

	String setMarketResultCronNew(ArrayList<UserLiability> liblist, MarketResult result);

	
	
}
