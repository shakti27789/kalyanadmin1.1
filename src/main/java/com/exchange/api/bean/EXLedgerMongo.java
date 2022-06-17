package com.exchange.api.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "t_ledger")
public class EXLedgerMongo {

	@Id
	public String id;

	private Integer parentid;

	private Integer childid;

	private String marketid;

	private Integer matchid;

	private Double amount;
	
	private Double pnl;

	private Double commssion;

	private String type;

	private String userid;

	private Date createdon;

	private String collectionname;

	private String matchname;

	private String markettype;

	private String marketname;

	private String username;

	private Integer mobapp;

	private String plusmins;

	private Double uplineamount = 0.0;

	private Double downlineamount = 0.0;
	
	private Double downlineamountlenadena = 0.0;


	private Double pnlamount;

	private String result;

	private String matchstatus;

	private String opendate;

	private Integer sportid;

	private String date;

	private String fancyname;

	private String fancyid;

	private Integer serialno;

	private String sportId;

	private Double commssionMila;

	private Double commssionDiya;

	private String byUserId;

	/*
	 * public EXLedgerMongo userLedger(Object[] row) {
	 * 
	 * EXLedgerMongo bean = new EXLedgerMongo();
	 * 
	 * bean.setAmount((Double) row[0]); bean.setChildid((Integer) row[1]);
	 * bean.setUserid((String) row[2]); bean.setUsername((String) row[3]);
	 * bean.setParentid((Integer) row[4]); return bean; }
	 * 
	 * public EXLedgerMongo userCashTransaction(Object[] row) {
	 * 
	 * EXLedgerMongo bean = new EXLedgerMongo();
	 * 
	 * bean.setAmount((Double) row[0]); bean.setChildid((Integer) row[1]);
	 * bean.setUserid((String) row[2]); bean.setUsername((String) row[3]);
	 * bean.setCreatedon((Date) row[4]); bean.setMatchname((String) row[5]);
	 * bean.setType((String) row[6]); bean.setCollectionname((String) row[7]);
	 * bean.setMatchid((Integer) row[8]); return bean; }
	 * 
	 * public EXLedgerMongo profitLoss(Object[] row) {
	 * 
	 * EXLedgerMongo bean = new EXLedgerMongo();
	 * 
	 * bean.setPnlamount((Double) row[0]); bean.setUplineamount((Double) row[1]);
	 * bean.setDownlineamount((Double) row[2]); bean.setMatchid((Integer) row[3]);
	 * bean.setMatchname((String) row[4]); bean.setCreatedon((Date) row[5]);
	 * bean.setMarkettype((String) row[6]); bean.setMarketname((String) row[7]);
	 * BigInteger b1 = new BigInteger(row[8].toString());
	 * bean.setSerialno(b1.intValue()); bean.setMarketid((String) row[9]);
	 * bean.setCommssionMila((Double) row[10]); bean.setCommssionDiya((Double)
	 * row[11]); return bean; }
	 * 
	 * public EXLedgerMongo sessionPlusMinus(Object[] row) {
	 * 
	 * EXLedgerMongo bean = new EXLedgerMongo();
	 * 
	 * bean.setPnlamount((Double) row[0]); bean.setFancyname((String) row[1]);
	 * bean.setMatchid((Integer) row[2]); bean.setMatchname((String) row[3]);
	 * bean.setFancyid((String) row[4]); bean.setResult(((Integer)
	 * row[5]).toString()); return bean; }
	 * 
	 * public EXLedgerMongo profitLossDetail(Object[] row) {
	 * 
	 * EXLedgerMongo bean = new EXLedgerMongo();
	 * 
	 * bean.setPnlamount((Double) row[0]); bean.setUplineamount((Double) row[1]);
	 * bean.setDownlineamount((Double) row[2]); bean.setMatchid((Integer) row[3]);
	 * bean.setMatchname((String) row[4]); bean.setCreatedon((Date) row[5]);
	 * bean.setMarkettype((String) row[6]); bean.setMarketname((String) row[7]);
	 * BigInteger b1 = new BigInteger(row[8].toString());
	 * bean.setSerialno(b1.intValue()); bean.setMarketid((String) row[9]);
	 * bean.setCommssionDiya((Double) row[10]); bean.setCommssionMila((Double)
	 * row[11]); bean.setSportid(Integer.parseInt((String) row[12]));
	 * 
	 * return bean; }
	 */
}
