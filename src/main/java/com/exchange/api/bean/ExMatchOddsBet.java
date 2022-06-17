package com.exchange.api.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author HP
 *
 */
@Data
@Entity
@Table(name = "t_betlist")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ExMatchOddsBet {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userid;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "isback")
	private Boolean isback;

	@Column(name = "islay")
	private Boolean islay;

	@Column(name = "pricevalue")
	private Double pricevalue;

	@Column(name = "odds")
	private Double odds;

	@Column(name = "placetime")
	private String placeTime;

	@Column(name = "matchedtime")
	private String matchedtime;

	@Column(name = "marketid")
	private String marketid;

	@Column(name = "marketname")
	private String marketname;

	@Column(name = "status")
	private String status;

	@Column(name = "selectionid")
	private int selectionid;

	@Column(name = "selectionname")
	private String selectionname;

	@Column(name = "updatedon")
	private Date updatedon;


	@Column(name = "result")
	private Integer result;

	@Column(name = "resultid")
	private Integer resultid = 0;;

	@Column(name = "resultname")
	private String resultname;

	@Column(name = "deletedby")
	private String deletedBy;

	@Column(name = "type")
	private String type;

	@Column(name = "selectionids")
	private String selectionids;

	@Column(name = "date")
	private String date;

	@Column(name = "parentid")
	private String parentid;

	@Column(name = "adminid")
	private String adminid;

	@Column(name = "subadminid")
	private String subadminid;

	@Column(name = "supermasterid")
	private String supermasterid;

	@Column(name = "masterid")
	private String masterid;

	@Column(name = "dealerid")
	private String dealerid;

	
	@Column(name = "createdon")
	private Date createdon;

	

	@Column(name = "stake")
	private int stake;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "sportid")
	private int sportid;

	@Column(name = "isactive")
	private Boolean isactive;

	@Column(name = "pnl")
	private Double pnl;

	@Column(name = "liability")
	private Double liability;

	@Column(name = "netpnl")
	private Double netpnl;

	@Column(name = "isdeleted")
	private Boolean isdeleted;

	@Column(name = "userip")
	private String Userip;

	@Column(name = "series")
	private Integer series;

	@Column(name = "userchain")
	private String userchain;
	
	@Column(name = "diffrence")
	private Integer diffrence;

	
	@javax.persistence.Transient
	private Integer serialno;

	@Transient
	private String remarks;

	@javax.persistence.Transient
	private String eventType;

	@Column(name = "oldpnl")
	private Double oldpnl = 0.0;

	@javax.persistence.Transient
	private Double partnership;

	@Column(name = "addedtofirestore")
	private Boolean addetoFirestore;
	
	@Column(name = "ismongodbupdated")
	private Boolean ismongodbupdated = false;
	
	
	

	public ExMatchOddsBet getInstance(Object[] row) {

		ExMatchOddsBet bean = new ExMatchOddsBet();
		bean.setId((Integer) row[0]);

		bean.setAdminid((String) row[6]);
		bean.setSubadminid((String) row[7]);
		bean.setSupermasterid((String) row[8]);
		bean.setMasterid((String) row[9]);
		bean.setDealerid((String) row[10]);
		bean.setUserid((String) row[11]);
		bean.setMatchid((Integer) row[12]);
		bean.setOdds((Double) row[13]);
		bean.setIsback((Boolean) row[14]);
		bean.setIslay((Boolean) row[15]);
		bean.setMarketname((String) row[16]);
		bean.setPricevalue((Double) row[17]);
		bean.setStatus((String) row[18]);
		bean.setSelectionid((Integer) row[19]);
		bean.setSelectionname((String) row[20]);
		bean.setParentid((String) row[21]);
		bean.setStake((Integer) row[22]);
		bean.setUserchain((String) row[23]);
		bean.setMatchname((String) row[24]);
		bean.setPnl((Double) row[25]);
		bean.setLiability((Double) row[26]);
		bean.setIsactive((Boolean) row[27]);
		bean.setMarketid((String) row[28]);

		return bean;
	}

	public ExMatchOddsBet getMainDateWiseBets(Object[] row) {

		ExMatchOddsBet bean = new ExMatchOddsBet();
		bean.setUserid((String) row[0]);

		bean.setMatchname((String) row[1]);
		bean.setSportid((Integer) row[2]);
		bean.setResultid((Integer) row[3]);
		bean.setType((String) row[4]);
		bean.setMarketid((String) row[5]);
		bean.setUserchain((String) row[6]);
		bean.setResult((Integer) row[7]);
		bean.setPnl((Double) row[8]);
		bean.setNetpnl((Double) row[9]);

		bean.setDate((String) row[10]);

		return bean;

	}

	public ExMatchOddsBet getUserExposure(Object[] row) {

		ExMatchOddsBet bean = new ExMatchOddsBet();

		bean.setLiability((Double) row[0]);

		return bean;
	}

	public ExMatchOddsBet getFancyBook(Object[] row) {

		ExMatchOddsBet bean = new ExMatchOddsBet();
		bean.setStake(Integer.parseInt(row[0].toString()));
		bean.setOdds((Double) row[1]);
		bean.setPnl((Double) row[2]);
		bean.setLiability((Double) row[3]);
		bean.setPartnership(new BigDecimal(row[4].toString()).doubleValue());
		bean.setIsback((Boolean) row[5]);
		bean.setMarketid((String) row[6]);
		return bean;
	}

	public ExMatchOddsBet betHistory(Object[] row) {

		ExMatchOddsBet bean = new ExMatchOddsBet();
		bean.setUserid((String) row[0]);
		bean.setMatchname((String) row[1]);
		bean.setSelectionname((String) row[2]);
		bean.setMarketname((String) row[3]);
		bean.setOdds((Double) row[4]);
		bean.setStake((Integer) row[5]);
		bean.setNetpnl((Double) row[6]);
		bean.setPlaceTime((String) row[7]);
		bean.setMatchedtime((String) row[8]);
		bean.setIsback((Boolean) row[9]);
		BigInteger b1 = new BigInteger(row[10].toString());
		bean.setSerialno(b1.intValue());
		return bean;
	}
}
