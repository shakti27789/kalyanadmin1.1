package com.exchange.api.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXLedger;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.Market;
import com.exchange.api.bean.UserLiability;

public interface EXNativeQueryDao {

	
	
	
	public  ArrayList<EXChipDetail> getpnlAndComm1(String userid,String parentid,LinkedHashMap<String, String> filterData);


	ArrayList<EXLedger> userLedger(Integer parentid,Integer childid);

	ArrayList<EXLedger> userCashTransaction(Integer parentid, Integer childid,Integer pagintype,Integer startpage); 
	
	public void updateLedgerUpline(EXLedger ledger);
	
	public ArrayList<EXLedger> profitLoss(Date startdate,Date enddate,Integer usertype,String userId,LinkedHashMap<String,String> filterData) throws ParseException;  
	
	public ArrayList<EXLedger> sessionPlusMinus(Integer eventid,Integer parentid);
	
	public Market getMarketAndSelId(String marketid);

	ArrayList<Market> getOpenMarket(Integer sportid,Boolean isPause);
	
	void updateUserAvailableBalance(Integer id, BigDecimal secbal);

	

	void deleteUserLogin(String userid);

	void updateUserBalanceChipDW(String userid , BigDecimal balance);

	void updateAvailableBalance(Integer id, BigDecimal secbal);
	
	public ArrayList<Market> leftMenuData(Integer sportid,String groupby,Integer seriesid,Boolean inPlay);

	void updateCreditRef(Integer id, BigDecimal secbal);

	public EXUser headerData(Integer parentid, Integer childid);
	
	public EXUser creditRefAndAvailableBalSum(Integer parentId); 
	
	public EXUser profitLossByParentId(Integer parentId); 
	
	public EXUser profitLossByChildId(Integer childId); 
	
	public ArrayList<EXChipDetail> getStatement(String userId,String startDate,String endDate,String reportType);

	EXUser profitLossByUserId(String userId);

	Double settlementSum(String userId);
	 
	public	ArrayList<UserLiability> userPnl(String marketId, String userId, String partnership,Integer usertype,String gropuBypartnership);
	
	public Double userCurrentExpo(String userId);

	void updateUserPnl(Integer id, Double pnl,Double uplineamount,Integer hisabkitabtype);
	
	public EXUser profitLossByUserIdWithOutSettlement(String userId);

	public void updateAvailableBalanceWithPnl(Integer id, BigDecimal availablebalancewithpnl,Integer userType,Double uplineAmount);

	void updateFancyStatus(Integer id, String status, Boolean isActive);

	public ArrayList<HashMap<String, String>> userByUserLike(Integer usertype , Integer parentuserid , String userid);
	
	public Double downLinePnlMatchWise(Integer matchid,Integer paarentId, String groupby);
	
	public ArrayList<EXLedger> profitLossDetail(Integer usertype,String userId,LinkedHashMap<String,String> filterData);

	public Double downLinePnlMatchWiseAndMarketId(Integer matchid,Integer paarentId, String marketid);
	
	public Double downleverPnl(String parentid);
	
	public ArrayList<UserLiability> userList(LinkedHashMap<String,String> filterData,String id,Integer userType);
	
	public EXLedger commSum(LinkedHashMap<String,String> filterData,String id);


	ArrayList<EXChipDetail> getStatementSettlement(String userId, String startDate, String endDate);


	ArrayList<EXLedger> profitLossCasino(String startdate, String enddate, Integer usertype, String userid,
			LinkedHashMap<String, String> filterData);


	Double downLinePnlMatchWiseCasino(Integer matchid, Integer parentId, String groupby,LinkedHashMap<String, String> filterData);


	ArrayList<HashMap<String, String>> subAdminByUserLike(Integer usertype, Integer parentuserid, String userid);

    ArrayList<UserLiability> useLibRollBack(String marketid);
    
    public ArrayList<EXLedger> matchUserWiseAmont(Integer parentId ,Integer matchId);
    
    public ArrayList<EXLedger> marketUserWiseAmont(Integer parentId ,Integer matchId,String marketId);

    public ArrayList<EXLedger> matchUserWiseAmontCasino(Integer parentId ,Integer matchId,String startdate, String enddate);
    

	ArrayList<EXLedger> profitLossCasinoOk();


	void updateUserUpline(EXLedger ledger);
    
	
	
}
