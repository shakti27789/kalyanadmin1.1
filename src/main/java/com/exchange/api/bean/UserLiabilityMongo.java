package com.exchange.api.bean;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "t_libility")
public class UserLiabilityMongo {

	@Id
	public String id;

	private String userid;

	private String dealerid;

	private String masterid;

	private String adminid;
	
	private String subadminid;

	private String supermasterid;

	private int sportid;

	private Double totalpnl;

	private Double liability;


	private String marketid;

	
	private Integer matchid;

	
	private String type;

	
	private int selection1;

	
	private Double adminpartnership = 0.0;


	private Double masterpartnership = 0.0;

	
	private Double dealerpartnership = 0.0;


	private Double subadminpartnership = 0.0;

	private Double supermasterpartnership = 0.0;

	private int selection2;

	private int selection3;

	private Double pnl1 = 0.0;

	private Double pnl2 = 0.0;

	private Double pnl3 = 0.0;

	private String matchname;

	private Boolean isactive;

	private Double netpnl;

	
	private Boolean isresultset;

	
	private Double oldlibility;

	

	private Double oldpnl1;

	
	private Double oldpnl2;

	
	private Double oldpnl3;

	
	
	
	private Double sbaCommission = 0.0;

	
	private Double sumCommission = 0.0;


	private Double mastCommission = 0.0;

	
	private Double dealCommission = 0.0;

	
	private Double admCommission = 0.0;

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "createdon")
	private Date createdon;

	
	

	
	private Integer stack = 0;

	
	private Double adminPnl = 0.0;


	private Double subadminpnl = 0.0;

	
	private Double supermasterpnl = 0.0;

	
	private Double masterpnl = 0.0;

	
	private Double dealerpnl = 0.0;

	private Boolean addetoFirestore;

	
	private Boolean isLibilityclear;


	private Boolean isProfitLossclear;
	
	
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
