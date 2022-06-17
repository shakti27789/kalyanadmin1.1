package com.exchange.api.bean;

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
@Table(name = "t_mobapp")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppCharge {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	
	@Column(name = "eventid")
	String eventid;
	
	@Column(name = "userid")
	String userid;
	
	@Column(name = "subadminid")
	String subadminid;
	
	@Column(name = "adminid")
	String adminid;
	
	@Column(name = "suprmasterid")
	String suprmasterid;
	
	@Column(name = "masterid")
	String masterid;
	
	@Column(name = "dealerid")
	String dealerid;
	
	@Column(name = "appid")
	Integer appid;
	
	@Column(name = "isssersettled")
	private Boolean isUserSettled;
	
	@Column(name = "ismastersettled")
	private Boolean isMasterSettled;
	
	@Column(name = "isdealerdettled")
	private Boolean isDealerSettled;
	
	@Column(name = "issuperastersettled")
	private Boolean isSuperMasterSettled;
	
	@Column(name = "issubadminsettled")
	private Boolean isSubadminSettled;
	
	@Column(name = "subamintoadminamount")
	Integer subamintoadminamount=0;;
	
	@Column(name = "supermastertosubadminamount")
	Integer supermastertosubadminamount=0;
	
	@Column(name = "mastertosupermastermamount")
	Integer mastertosupermastermamount=0;
	
	@Column(name = "dealertomasteramont")
	Integer dealertomasteramont=0;
	
	@Column(name = "usertodealeramount")
	Integer usertodealeramount=0;
	
	@Column(name = "usertosubadminamount")
	Integer usertosubadminamount=0;
	
	@Column(name = "usertomasteramount")
	Integer usertomasteramount=0;
	
	@Column(name = "usertosupermasteramount")
	Integer usertosupermasteramount=0;
	
	@Column(name = "mastertosubadminamount")
	Integer mastertosubadminamount=0;
	
	@Column(name = "dealertosubadminamount")
	Integer dealertosubadminamount=0;
	
	@Column(name = "isresult")
	private Boolean isresult;
	
	@Column(name = "delalertosupermasteramount")
	Integer delalertosupermasteramount=0;
	
	@Column(name = "userchain")
	private String userchain;
	
	@Column(name = "matchsatus")
	private String matchsatus;
	
	
	@Column(name = "mobappcharge")
	private Integer mobappcharge=0;
	
}
