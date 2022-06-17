package com.exchange.api.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.exchange.util.EXConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "userid", unique = true)
	private String userid;

	@NotNull
	@Column(name = "password")
	private String password;


	@NotNull
	@Column(name = "contact")
	private String contact;

	@Column(name = "betlock")
	private Boolean betlock;


	@Column(name = "usertype")
	private Integer usertype;

	@Column(name = "accountlock")
	private Boolean accountlock;

	
     @NotNull
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

	@NotNull
	@Column(name = "adminpartership")
	private BigDecimal adminpartership;

	@Column(name = "subadminpartnership")
	private BigDecimal subadminpartnership;

	@Column(name = "supermastepartnership")
	private BigDecimal supermastepartnership;

	@Column(name = "masterpartership")
	private BigDecimal masterpartership;

	@Column(name = "delearpartership")
	private BigDecimal delearpartership;

	@Column(name = "netexposure")
	private Integer netexposure;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "updaedon")
	private String updaedon;

	
	@Column(name = "appid")
	private Integer appid;



	@Column(name = "passwordtype")
	private String passwordtype;

	@Column(name = "userchain")
	private String userchain;

	@Transient
	private BigDecimal partnershipratio;

	
	@Column(name = "active")
	private  Boolean active;
	
	@NotNull
	@Column(name = "oddsloss")
	private BigDecimal oddsloss;
	

	@NotNull
	@Column(name = "casinocommssion")
	private Double casinocommssion;
	
	
	@NotNull
	@Column(name = "fancyloss")
	private BigDecimal fancyloss;

	

	@NotNull
	@Column(name = "oddslosscommisiontype")
	private String oddslosscommisiontype;

	
	@NotNull
	@Column(name = "fancylosscommisiontype")
	private String fancylosscommisiontype;

	@Column(name = "parentspartnership")
	private Double parentspartnership;

	@Column(name = "availablebalance")
	private BigDecimal availableBalance;

	@Transient
	private String partnership;

	@Transient
	private String partnershipc;

	@Transient
	private BigDecimal pnlamount;

	@Column(name = "uplineamount")
	private Double uplineAmount;

	@Column(name = "creditref")
	private BigDecimal creditRef;

	@Transient
	private Double totalMasterBalance;

	@Transient
	private BigDecimal downlinecreditRef;

	@Transient
	private BigDecimal downlineavailableBalance;

	@Transient
	private Double downlineWithProfitLoss;

	@Transient
	private Double myProfitLoss;

	@Column(name = "availablebalancewithpnl")
	private Double availableBalanceWithPnl;

	
	@Transient
	private Double currentExpo;

	@Transient
	private String parentPassword;

	@Column(name = "adminpartershipc")
	private BigDecimal adminpartershipc;

	@Column(name = "subadminpartnershipc")
	private BigDecimal subadminpartnershipc;

	@Column(name = "supermastepartnershipc")
	private BigDecimal supermastepartnershipc;

	@Column(name = "masterpartershipc")
	private BigDecimal masterpartershipc;

	@Column(name = "delearpartershipc")
	private BigDecimal delearpartershipc;

	@Transient
	private BigDecimal partnershipratioc;



	@Transient
	private String userRoll;

	@Transient
	private String appurl;

	@Transient
	private String appname;
	
	@Column(name = "hisabkitabtype")
	private Integer hisabkitabtype;
	
	

	@Column(name = "betdelay")
	private String betDelay;



	@Column(name = "betlockbyparent")
	private Boolean betlockbyParent;

	@Transient
	private Double balance;

	@Column(name = "betlockby")
	private Integer betlockBy;
	
	@Column(name = "iddirectbet")
	private Boolean isdirectbet;
	
	@Column(name = "iscasinobetlock")
	private Boolean isCasinoBetlock;
	
	
	@Column(name = "casinobetlockby")
	private Integer casinobetlockBy;
	
	@Transient
	private Boolean lCasinoLock;
	
	@Transient
	private Boolean vCasinoLock;
	
	@Column(name = "isaccountclosed")
	private Boolean isaccountclosed;
	
	@Column(name = "accountclosedby")
	private Integer accountclosedby;
	
	@Column(name = "accountclosedbyparent")
	private Boolean accountclosedbyParent;
	
	@Column(name = "betcount")
	private Boolean betCount;
	
	
	
	

	@Transient
	private HashMap<String, HashMap<String, String>> betdelaymap;
	
	
	

	public EXUser getInstance(Object[] row) {

		EXUser bean = new EXUser();
		bean.setId((Integer) row[0]);
		bean.setUserid((String) row[1]);
		bean.setUsername((String) row[2]);
		bean.setAdminid((String) row[3]);
		bean.setSubadminid((String) row[4]);
		bean.setSupermasterid((String) row[5]);
		bean.setMasterid((String) row[6]);
		bean.setUsertype((Integer) row[7]);
		if ((Integer) row[7] == 5) {
			bean.setUserRoll(EXConstants.SubAdmin);
			bean.setPartnership(row[24].toString());
			bean.setPartnershipc(row[26].toString());
		} else if ((Integer) row[7] == 0) {
			bean.setUserRoll(EXConstants.SuperMaster);
			bean.setPartnership(row[18].toString());
			bean.setPartnershipc(row[27].toString());
		} else if ((Integer) row[7] == 1) {
			bean.setUserRoll(EXConstants.Master);
			bean.setPartnership(row[19].toString());
			bean.setPartnershipc(row[28].toString());
		} else if ((Integer) row[7] == 2) {
			bean.setUserRoll(EXConstants.Agent);
			bean.setPartnership(row[20].toString());
			bean.setPartnershipc(row[29].toString());
		} else if ((Integer) row[7] == 3) {
			bean.setUserRoll(EXConstants.User);
			bean.setPartnership("0");
			bean.setPartnershipc("0");
		} else if ((Integer) row[7] == 6) {
			bean.setUserRoll(EXConstants.Power);
			bean.setPartnership("0");
			bean.setPartnershipc("0");
		}

		bean.setParentid((String) row[8]);
		bean.setUserchain((String) row[9]);
		bean.setAppid((Integer) row[10]);
		bean.setFancylosscommisiontype((String) row[11]);
		bean.setAccountlock((Boolean) row[13]);
		bean.setNetexposure((Integer) row[14]);
		bean.setAvailableBalance((BigDecimal) row[16]);
		bean.setCreditRef((BigDecimal) row[17]);
		bean.setAvailableBalanceWithPnl((Double) row[21]);
		bean.setUplineAmount((Double) row[22]);
		bean.setBetlock((Boolean) row[23]);
		bean.setIsCasinoBetlock((Boolean) row[30]);
		bean.setHisabkitabtype((Integer)row[31]);
		bean.setIsaccountclosed((Boolean)row[32]);
		return bean;
	}




	public Boolean getlCasinoLock() {
		return lCasinoLock;
	}




	public void setlCasinoLock(Boolean lCasinoLock) {
		this.lCasinoLock = lCasinoLock;
	}




	public Boolean getvCasinoLock() {
		return vCasinoLock;
	}




	public void setvCasinoLock(Boolean vCasinoLock) {
		this.vCasinoLock = vCasinoLock;
	}

}
