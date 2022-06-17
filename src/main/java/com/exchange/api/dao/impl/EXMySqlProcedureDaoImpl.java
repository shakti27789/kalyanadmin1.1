package com.exchange.api.dao.impl;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.exchange.api.dao.EXMySqlProcedureDao;
@Repository
public class EXMySqlProcedureDaoImpl implements EXMySqlProcedureDao {


	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Boolean updateChip(String parentuserid, String userid, Double credit, Double debit, String descreption,String fromto,String marketid, Integer matchid, Integer parentid, String type) {
		// TODO Auto-generated method stub
		
		boolean status = false;
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("chipCredit");
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("userid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("credit", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("debit", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("descreption", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("fromto", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("marketid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
		
		
		procedureQuery.setParameter("parentuserid", parentuserid);
		procedureQuery.setParameter("userid", userid);
		procedureQuery.setParameter("credit", credit);
		procedureQuery.setParameter("debit", debit);
		procedureQuery.setParameter("descreption", descreption);
		procedureQuery.setParameter("fromto", fromto);
		
		procedureQuery.setParameter("marketid", marketid);
		procedureQuery.setParameter("matchid", matchid);
		procedureQuery.setParameter("parentid", parentid);
		procedureQuery.setParameter("type", type);
	   
		 status = procedureQuery.execute();
		 
		 return status;
	}

	
	@Override
	@Transactional
	public Boolean updateChipDetailMatchResult(HashMap<String,Object> hm) {
		// TODO Auto-generated method stub
		
		boolean status = false;
		
		//System.out.println("marketname=>"+hm.get("matchname").toString() +"MarketId=>"+hm.get("marketid").toString());
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("resulAccountStatement");
		procedureQuery.registerStoredProcedureParameter("userid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("chip", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("descreption", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("fromto", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("marketid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
		
		procedureQuery.registerStoredProcedureParameter("childid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("collectionname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("commssion", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("markettype", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("mobapp", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("lenadena", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("uplineamount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("clientuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("downlineamount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("pnl", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("sportId", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("marketname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("commssiondiya", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("commssionmila", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("byUserId", String.class, ParameterMode.IN);
		
		
		procedureQuery.setParameter("userid", hm.get("userid").toString());
		procedureQuery.setParameter("chip", new Double(hm.get("chip").toString()));
		procedureQuery.setParameter("descreption", hm.get("descreption").toString());
		procedureQuery.setParameter("fromto", hm.get("fromto").toString());
		procedureQuery.setParameter("marketid", hm.get("marketid").toString());
		procedureQuery.setParameter("matchid", Integer.parseInt(hm.get("matchid").toString()));
		procedureQuery.setParameter("parentid", Integer.parseInt(hm.get("parentid").toString()));
		procedureQuery.setParameter("type", hm.get("type").toString());
		
		procedureQuery.setParameter("childid", hm.get("childid").toString());
		procedureQuery.setParameter("collectionname", hm.get("collectionname").toString());
		procedureQuery.setParameter("commssion",new Double(hm.get("commssion").toString()));
		procedureQuery.setParameter("markettype", hm.get("markettype").toString());
		procedureQuery.setParameter("matchname", hm.get("matchname").toString());
		procedureQuery.setParameter("mobapp",  Integer.parseInt(hm.get("mobapp").toString()));
		procedureQuery.setParameter("lenadena", hm.get("lenadena").toString());
		procedureQuery.setParameter("uplineamount", new Double(hm.get("uplineamount").toString()));
		procedureQuery.setParameter("clientuserid", hm.get("clientuserid").toString());
		procedureQuery.setParameter("downlineamount",new Double(hm.get("downlineamount").toString()) );
		procedureQuery.setParameter("pnl",new Double(hm.get("pnl").toString()) );
		procedureQuery.setParameter("sportId", Integer.parseInt(hm.get("sportId").toString()));
		procedureQuery.setParameter("marketname", hm.get("marketname").toString());
		procedureQuery.setParameter("commssiondiya",new Double(hm.get("commssiondiya").toString()) );
		procedureQuery.setParameter("commssionmila",new Double(hm.get("commssionmila").toString()) );
		procedureQuery.setParameter("byUserId",hm.get("byUserId").toString());
		 status = procedureQuery.execute();
		 
		 return status;
	}


	@Override
	public String updateCreditRef(String parentuserId, String childuserId, Double creditRef) {
		// TODO Auto-generated method stub
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("updateCreditRef");
		
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("creditref", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("parentuserid", parentuserId);
		procedureQuery.setParameter("childuserid", childuserId);
		procedureQuery.setParameter("creditref", creditRef);
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		
		return result;
	}
 

	@Override
	public String cashTranction(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery(hm.get("cashTranscaton").toString());
		
	//	StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("cashTranscaton");
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("isresult", Boolean.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("marketid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("marketname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("markettype", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("result", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("selectionid", Integer.class, ParameterMode.IN);
		
		procedureQuery.registerStoredProcedureParameter("selectionname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("sportid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("sportname", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("status", Boolean.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("amount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("debit", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("credit", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("description", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("fromto", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("settlementtype", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		
		
		
		procedureQuery.setParameter("parentid", Integer.parseInt(hm.get("parentid").toString()));
		procedureQuery.setParameter("childid", Integer.parseInt(hm.get("childid").toString()));
		procedureQuery.setParameter("isresult", new Boolean(hm.get("isresult").toString()));;
		procedureQuery.setParameter("marketid", hm.get("marketid").toString());
		procedureQuery.setParameter("marketname", hm.get("marketname").toString());
		procedureQuery.setParameter("markettype", hm.get("markettype").toString());
		procedureQuery.setParameter("matchid", Integer.parseInt(hm.get("matchid").toString()));
		procedureQuery.setParameter("matchname", hm.get("matchname").toString());
		procedureQuery.setParameter("result", Integer.parseInt(hm.get("result").toString()));
		procedureQuery.setParameter("selectionid", Integer.parseInt(hm.get("selectionid").toString()));
		
		procedureQuery.setParameter("selectionname", hm.get("selectionname").toString());
		procedureQuery.setParameter("sportid", Integer.parseInt(hm.get("sportid").toString()));
		procedureQuery.setParameter("sportname", hm.get("sportname").toString());
		procedureQuery.setParameter("status",new Boolean(hm.get("status").toString()));
		procedureQuery.setParameter("type",  hm.get("type").toString());
		procedureQuery.setParameter("amount", new Double(hm.get("amount").toString()));
		procedureQuery.setParameter("parentuserid", hm.get("parentuserid").toString());
		procedureQuery.setParameter("childuserid", hm.get("childuserid").toString());
		procedureQuery.setParameter("debit",new Double(hm.get("debit").toString()) );
		procedureQuery.setParameter("credit",new Double(hm.get("credit").toString()) );
		procedureQuery.setParameter("description",hm.get("description").toString() );
		procedureQuery.setParameter("fromto",hm.get("fromto").toString() );
		procedureQuery.setParameter("settlementtype",hm.get("settlementtype").toString() );
		
		
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		 
		 return result;
	}

	

	@Override
	public String chipCredit(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("chipCredit");
	
		procedureQuery.registerStoredProcedureParameter("amount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("descreption", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("fromto", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("newuser", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("amount", new Double(hm.get("amount").toString()) );
		procedureQuery.setParameter("parentuserid", hm.get("parentuserid").toString());
		procedureQuery.setParameter("childuserid", hm.get("childuserid").toString());
		procedureQuery.setParameter("descreption", hm.get("descreption").toString());
		procedureQuery.setParameter("parentid", Integer.parseInt(hm.get("parentid").toString()));
		procedureQuery.setParameter("childid", Integer.parseInt(hm.get("childid").toString()));
		procedureQuery.setParameter("type", hm.get("type").toString());
		procedureQuery.setParameter("fromto", hm.get("fromto").toString());
		procedureQuery.setParameter("newuser", hm.get("newuser").toString());
		
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		return result;
	}


	@Override
	public String chipWithdraw(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("chipWithdraw");
		procedureQuery.registerStoredProcedureParameter("amount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("descreption", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("type", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("fromto", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("amount", new Double(hm.get("amount").toString()) );
		procedureQuery.setParameter("parentuserid", hm.get("parentuserid").toString());
		procedureQuery.setParameter("childuserid", hm.get("childuserid").toString());
		procedureQuery.setParameter("descreption", hm.get("descreption").toString());
		procedureQuery.setParameter("parentid", Integer.parseInt(hm.get("parentid").toString()));
		procedureQuery.setParameter("childid", Integer.parseInt(hm.get("childid").toString()));
		procedureQuery.setParameter("type", hm.get("type").toString());
		procedureQuery.setParameter("fromto", hm.get("fromto").toString());
		
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		return result;
	}


	@Override
	public void chipRollBack(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("rollBackChipdtl");
		procedureQuery.registerStoredProcedureParameter("marketid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("matchid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("userid", String.class, ParameterMode.IN);

		procedureQuery.setParameter("marketid", hm.get("marketid").toString());
		procedureQuery.setParameter("matchid", Integer.parseInt(hm.get("matchid").toString()));
		procedureQuery.setParameter("userid", hm.get("userid").toString());
		
		procedureQuery.execute();
		
	
	}
	
	
	@Override
	public String deleteMarketBet(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("deleteMarketBet");
		procedureQuery.registerStoredProcedureParameter("betid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("libid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("lib",  Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("pnl1", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("pnl2", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("pnl3", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		
		
		
		
		procedureQuery.setParameter("betid", Integer.parseInt(hm.get("betid").toString()));
		procedureQuery.setParameter("libid", Integer.parseInt(hm.get("libid").toString()));
		procedureQuery.setParameter("lib", new Double(hm.get("lib").toString()));
		procedureQuery.setParameter("pnl1", new Double(hm.get("pnl1").toString()));
		procedureQuery.setParameter("pnl2", new Double(hm.get("pnl2").toString()));
		procedureQuery.setParameter("pnl3", new Double(hm.get("pnl3").toString()));
		
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		return result;
		
	
	}
	
	
	@Override
	public String updatePartnership(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("updatePartnership");
		procedureQuery.registerStoredProcedureParameter("subadminid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("subadminpartnership", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("partnership", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("userid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("ptype", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		

		procedureQuery.setParameter("subadminid", hm.get("subadminid").toString());
		procedureQuery.setParameter("subadminpartnership",  new Double(hm.get("subadminpartnership").toString()));
		procedureQuery.setParameter("partnership",new Double(hm.get("partnership").toString()));
		procedureQuery.setParameter("userid", hm.get("userid").toString());
		procedureQuery.setParameter("ptype", hm.get("ptype").toString());
		
		
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		return result;
		
	
	}

	

	@Override
	public String changeCreditRef(HashMap<String, Object> hm) {
		// TODO Auto-generated method stub
		
		
		StoredProcedureQuery procedureQuery =entityManager.createStoredProcedureQuery("changeCreditRef");
	
		procedureQuery.registerStoredProcedureParameter("amount", Double.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childuserid", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("parentid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("childid", Integer.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("descreption", String.class, ParameterMode.IN);
		procedureQuery.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
		
		procedureQuery.setParameter("amount", new Double(hm.get("amount").toString()) );
		procedureQuery.setParameter("parentuserid", hm.get("parentuserid").toString());
		procedureQuery.setParameter("childuserid", hm.get("childuserid").toString());
		procedureQuery.setParameter("parentid", Integer.parseInt(hm.get("parentid").toString()));
		procedureQuery.setParameter("childid", Integer.parseInt(hm.get("childid").toString()));
		procedureQuery.setParameter("descreption", hm.get("descreption").toString());
		procedureQuery.execute();
		String result = procedureQuery.getOutputParameterValue("message").toString();
		
		return result;
	}
}
