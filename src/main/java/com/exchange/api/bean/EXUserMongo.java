package com.exchange.api.bean;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.Data;

@Data
@Document(collection = "t_user")
public class EXUserMongo {	


	@Id
	public String id;
	
	@Field(value = "username")
	private String username;

	@Field(value = "userid")
	private String userid;

	@NotNull
	@Field(value = "password")
	private String password;


	@NotNull
	@Field(value = "contact")
	private String contact;

	@Field(value = "betlock")
	private Boolean betlock;


	@Field(value = "usertype")
	private Integer usertype;

	@Field(value = "accountlock")
	private Boolean accountlock;

	
     @NotNull
	@Field(value = "parentid")
	private String parentid;

	@Field(value = "adminid")
	private String adminid;

	@Field(value = "subadminid")
	private String subadminid;

	@Field(value = "supermasterid")
	private String supermasterid;

	@Field(value = "masterid")
	private String masterid;

	@Field(value = "dealerid")
	private String dealerid;

	@NotNull
	@Field(value = "adminpartership")
	private BigDecimal adminpartership;

	@Field(value = "subadminpartnership")
	private BigDecimal subadminpartnership;

	@Field(value = "supermastepartnership")
	private BigDecimal supermastepartnership;

	@Field(value = "masterpartership")
	private BigDecimal masterpartership;

	@Field(value = "delearpartership")
	private BigDecimal delearpartership;

	@Field(value = "netexposure")
	private Integer netexposure;

	@Field(value = "createdon")
	private String createdon;

	@Field(value = "updaedon")
	private String updaedon;

	
	@Field(value = "appid")
	private Integer appid;



	@Field(value = "passwordtype")
	private String passwordtype;

	@Field(value = "userchain")
	private String userchain;

	@Transient
	private BigDecimal partnershipratio;

	
	@Field(value = "active")
	private  Boolean active;
	
	@NotNull
	@Field(value = "oddsloss")
	private BigDecimal oddsloss;
	

	@NotNull
	@Field(value = "casinocommssion")
	private Double casinocommssion;
	
	
	@NotNull
	@Field(value = "fancyloss")
	private BigDecimal fancyloss;

	

	@NotNull
	@Field(value = "oddslosscommisiontype")
	private String oddslosscommisiontype;

	
	@NotNull
	@Field(value = "fancylosscommisiontype")
	private String fancylosscommisiontype;

	@Field(value = "parentspartnership")
	private Double parentspartnership;

	@Field(value = "availablebalance")
	private BigDecimal availableBalance;

	@Transient
	private String partnership;

	@Transient
	private String partnershipc;

	@Transient
	private BigDecimal pnlamount;

	@Field(value = "uplineamount")
	private Double uplineAmount;

	@Field(value = "creditref")
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

	@Field(value = "availablebalancewithpnl")
	private Double availableBalanceWithPnl;

	
	@Transient
	private Double currentExpo;

	@Transient
	private String parentPassword;

	@Field(value = "adminpartershipc")
	private BigDecimal adminpartershipc;

	@Field(value = "subadminpartnershipc")
	private BigDecimal subadminpartnershipc;

	@Field(value = "supermastepartnershipc")
	private BigDecimal supermastepartnershipc;

	@Field(value = "masterpartershipc")
	private BigDecimal masterpartershipc;

	@Field(value = "delearpartershipc")
	private BigDecimal delearpartershipc;

	@Transient
	private BigDecimal partnershipratioc;



	@Transient
	private String userRoll;

	@Transient
	private String appurl;

	@Transient
	private String appvalue;
	
	@Field(value = "hisabkitabtype")
	private Integer hisabkitabtype;
	
	

	@Field(value = "betdelay")
	private String betDelay;



	@Field(value = "betlockbyparent")
	private Boolean betlockbyParent;

	@Transient
	private Double balance;

	@Field(value = "betlockby")
	private Integer betlockBy;
	
	@Field(value = "iddirectbet")
	private Boolean isdirectbet;
	
	@Field(value = "iscasinobetlock")
	private Boolean isCasinoBetlock;
	
	
	@Field(value = "casinobetlockby")
	private Integer casinobetlockBy;
	
	@Transient
	private Boolean lCasinoLock;
	
	@Transient
	private Boolean vCasinoLock;
	
	@Field(value = "isaccountclosed")
	private Boolean isaccountclosed;
	
	@Field(value = "accountclosedby")
	private Integer accountclosedby;
	
	@Field(value = "accountclosedbyparent")
	private Boolean accountclosedbyParent;
	
	
	
	

	@Transient
	private HashMap<String, HashMap<String, String>> betdelaymap;
	
	
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
