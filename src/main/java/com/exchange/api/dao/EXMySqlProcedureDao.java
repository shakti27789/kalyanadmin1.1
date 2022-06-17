package com.exchange.api.dao;

import java.util.HashMap;

public interface EXMySqlProcedureDao {

	public Boolean updateChip(String parentuserId,String userId,Double credit,Double debit,String descreption,String fromto,String marketId,Integer matchId,Integer parentId,String type);
    
	public Boolean updateChipDetailMatchResult(HashMap<String,Object> hm);
	
	public String updateCreditRef(String parentuserId,String childuserId,Double creditRef);
	
	public String cashTranction(HashMap<String,Object> hm);

	public String chipCredit(HashMap<String, Object> hm);
	
	public String chipWithdraw(HashMap<String, Object> hm);
	
	public void chipRollBack(HashMap<String, Object> hm);

	public String deleteMarketBet(HashMap<String, Object> hm);

	public String updatePartnership(HashMap<String, Object> hm);

	String changeCreditRef(HashMap<String, Object> hm);
    
}
