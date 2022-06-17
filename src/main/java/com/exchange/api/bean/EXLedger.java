package com.exchange.api.bean;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_ledger")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXLedger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "parentid")
	private Integer parentid;

	@Column(name = "childid")
	private Integer childid;

	@Column(name = "marketid")
	private String marketid;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "pnl")
	private Double pnl;

	@Column(name = "commssion")
	private Double commssion;

	@Column(name = "type")
	private String type;

	@Column(name = "userid")
	private String userid;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "collectionname")
	private String collectionname;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "markettype")
	private String markettype;

	@Column(name = "marketname")
	private String marketname;

	@javax.persistence.Transient
	private String username;

	@Column(name = "mobapp")
	private Integer mobapp;

	@Column(name = "plusmins")
	private String plusmins;

	@Column(name = "uplineamount")
	private Double uplineamount = 0.0;

	@Column(name = "downlineamount")
	private Double downlineamount = 0.0;
	
	@Column(name = "downlineamountlenadena")
	private Double downlineamountlenadena = 0.0;


	@javax.persistence.Transient
	private Double pnlamount;

	@Column(name = "result")
	private String result;

	@javax.persistence.Transient
	private String matchstatus;

	@javax.persistence.Transient
	private String opendate;

	@javax.persistence.Transient
	private Integer sportid;

	@javax.persistence.Transient
	private String date;

	@javax.persistence.Transient
	private String fancyname;

	@javax.persistence.Transient
	private String fancyid;

	@javax.persistence.Transient
	private Integer serialno;

	@Column(name = "sportid")
	private String sportId;

	@Column(name = "commssionmila")
	private Double commssionMila;

	@Column(name = "commssiondiya")
	private Double commssionDiya;

	@Column(name = "byuserid")
	private String byUserId;

	
	@Column(name = "ismongodbupdated")
	private Boolean ismongodbupdated = false;
	
	public EXLedger userLedger(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setAmount((Double) row[0]);
		bean.setChildid((Integer) row[1]);
		bean.setUserid((String) row[2]);
		bean.setUsername((String) row[3]);
		bean.setParentid((Integer) row[4]);
		return bean;
	}

	public EXLedger userCashTransaction(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setAmount((Double) row[0]);
		bean.setChildid((Integer) row[1]);
		bean.setUserid((String) row[2]);
		bean.setUsername((String) row[3]);
		bean.setCreatedon((Date) row[4]);
		bean.setMatchname((String) row[5]);
		bean.setType((String) row[6]);
		bean.setCollectionname((String) row[7]);
		bean.setMatchid((Integer) row[8]);
		return bean;
	}

	public EXLedger profitLoss(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setPnlamount((Double) row[0]);
		bean.setUplineamount((Double) row[1]);
		bean.setDownlineamount((Double) row[2]);
		bean.setMatchid((Integer) row[3]);
		bean.setMatchname((String) row[4]);
		bean.setCreatedon((Date) row[5]);
		bean.setMarkettype((String) row[6]);
		bean.setMarketname((String) row[7]);
		BigInteger b1 = new BigInteger(row[8].toString());
		bean.setSerialno(b1.intValue());
		bean.setMarketid((String) row[9]);
		bean.setCommssionMila((Double) row[10]);
		bean.setCommssionDiya((Double) row[11]);
		return bean;
	}
	
	
	public EXLedger profitLosOk(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setPnlamount((Double) row[0]);
		bean.setAmount((Double) row[1]);
		bean.setUplineamount((Double) row[2]);
		bean.setDownlineamount((Double) row[3]);
		bean.setId((Integer) row[4]);
		bean.setUserid((String) row[5]);
		bean.setByUserId((String) row[6]);
		return bean;
	}

	public EXLedger sessionPlusMinus(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setPnlamount((Double) row[0]);
		bean.setFancyname((String) row[1]);
		bean.setMatchid((Integer) row[2]);
		bean.setMatchname((String) row[3]);
		bean.setFancyid((String) row[4]);
		bean.setResult(((Integer) row[5]).toString());
		return bean;
	}

	public EXLedger profitLossDetail(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setPnlamount((Double) row[0]);
		bean.setUplineamount((Double) row[1]);
		bean.setDownlineamount((Double) row[2]);
		bean.setMatchid((Integer) row[3]);
		bean.setMatchname((String) row[4]);
		bean.setCreatedon((Date) row[5]);
		bean.setMarkettype((String) row[6]);
		bean.setMarketname((String) row[7]);
		BigInteger b1 = new BigInteger(row[8].toString());
		bean.setSerialno(b1.intValue());
		bean.setMarketid((String) row[9]);
		bean.setCommssionDiya((Double) row[10]);
		bean.setCommssionMila((Double) row[11]);
		bean.setSportid(Integer.parseInt((String) row[12]));
		   
		return bean;
	}
	
	public EXLedger matchUserWiseAmont(Object[] row) {

		EXLedger bean = new EXLedger();

		bean.setUplineamount((Double) row[0]);
		bean.setUserid((String) row[1]);
		
		return bean;
	}
}
