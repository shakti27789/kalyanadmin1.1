package com.exchange.api.bean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
@Table(name = "t_libility")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserLiability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userid;

	@Column(name = "dealerid")
	private String dealerid;

	@Column(name = "masterid")
	private String masterid;

	@Column(name = "adminid")
	private String adminid;
	
	@Column(name = "subadminid")
	private String subadminid;

	@Column(name = "supermasterid")
	private String supermasterid;

	@Column(name = "sportid")
	private int sportid;

	@Column(name = "totalpnl")
	private Double totalpnl;

	@Column(name = "liability")
	private Double liability;

	@Column(name = "marketid")
	private String marketid;

	@Column(name = "matchid")
	private Integer matchid;

	
	@Column(name = "type")
	private String type;

	@Column(name = "selection1")
	private int selection1;

	@Column(name = "adminpartnership")
	private Double adminpartnership = 0.0;

	@Column(name = "masterpartnership")
	private Double masterpartnership = 0.0;

	@Column(name = "dealerpartnership")
	private Double dealerpartnership = 0.0;

	@Column(name = "subadminpartnership")
	private Double subadminpartnership = 0.0;

	@Column(name = "supermasterpartnership")
	private Double supermasterpartnership = 0.0;

	@Column(name = "selection2")
	private int selection2;

	@Column(name = "selection3")
	private int selection3;

	@Column(name = "pnl1")
	private Double pnl1 = 0.0;

	@Column(name = "pnl2")
	private Double pnl2 = 0.0;

	@Column(name = "pnl3")
	private Double pnl3 = 0.0;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "isactive")
	private Boolean isactive;

	
	@Column(name = "netpnl")
	private Double netpnl;

	
	@Column(name = "isresultset")
	private Boolean isresultset;

	@Column(name = "oldlibility")
	private Double oldlibility;

	
	@Column(name = "oldpnl1")
	private Double oldpnl1;

	@Column(name = "oldpnl2")
	private Double oldpnl2;

	@Column(name = "oldpnl3")
	private Double oldpnl3;

	
	
	@Column(name = "sbacommission")
	private Double sbaCommission = 0.0;

	@Column(name = "sumcommission")
	private Double sumCommission = 0.0;

	@Column(name = "mastcommission")
	private Double mastCommission = 0.0;

	@Column(name = "dealcommission")
	private Double dealCommission = 0.0;

	@Column(name = "admcommission")
	private Double admCommission = 0.0;

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "createdon")
	private Date createdon;

	
	

	@Column(name = "stack ", columnDefinition = "Integer default '0.0'")
	private Integer stack = 0;

	@Column(name = "adminpnl ", columnDefinition = "Double default '0.0'")
	private Double adminPnl = 0.0;

	@Column(name = "subadminpnl ", columnDefinition = "Double default '0.0'")
	private Double subadminpnl = 0.0;

	@Column(name = "supermasterpnl ", columnDefinition = "Double default '0.0'")
	private Double supermasterpnl = 0.0;

	@Column(name = "masterpnl ", columnDefinition = "Double default '0.0'")
	private Double masterpnl = 0.0;

	@Column(name = "dealerpnl ", columnDefinition = "Double default '0.0'")
	private Double dealerpnl = 0.0;

	@Column(name = "addedtofirestore")
	private Boolean addetoFirestore;

	@Column(name = "islibilityclear")
	private Boolean isLibilityclear;

	@Column(name = "isprofitlossclear")
	private Boolean isProfitLossclear;
	
	@Column(name = "ismongodbupdated")
	private Boolean ismongodbupdated = false;

	public UserLiability getChildLiability(Object[] row) {

		UserLiability bean = new UserLiability();

		bean.setLiability((Double) row[0]);
		bean.setIsactive(true);
		bean.setUserid((String) row[2]);

		return bean;
	}

	public UserLiability getpnlChidIdWise(Object[] row) {
		DecimalFormat df = new DecimalFormat("#0");

		UserLiability bean = new UserLiability();
		if ((Double) row[0] != null) {
			bean.setPnl1(Double.valueOf(df.format((Double) row[0])));
			bean.setPnl2(Double.valueOf(df.format((Double) row[1])));
			bean.setPnl3(Double.valueOf(df.format((Double) row[2])));
			bean.setUserid((String) row[3]);
			bean.setAdminid((String) row[4]);
			bean.setSubadminid((String) row[5]);
			bean.setSupermasterid((String) row[6]);
			bean.setMasterid((String) row[7]);
			bean.setDealerid((String) row[8]);
		} else {
			bean.setPnl1((Double) row[0]);
			bean.setPnl2((Double) row[1]);
			bean.setPnl3((Double) row[2]);
			bean.setUserid((String) row[3]);
			bean.setAdminid((String) row[4]);
			bean.setSubadminid((String) row[5]);
			bean.setSupermasterid((String) row[6]);
			bean.setMasterid((String) row[7]);
		}

		return bean;
	}

}
