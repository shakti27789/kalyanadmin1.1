package com.exchange.api.bean;

import lombok.Data;

@Data
public class EXSportdetailResponse {

	private String clientname;

	private Double matchamount = 0.0;

	private Double sessionamount = 0.0;

	private Double matchsessionamount = 0.0;

	private Double matchcommssion = 0.0;

	private Double sessioncommssion = 0.0;

	private Double matchsessioncommssion = 0.0;

	private Double netamount = 0.0;

	private Double mshr = 0.0;

	private Double mshramt = 0.0;

	private Double amtsftmshr = 0.0;

	private Double sushr = 0.0;

	private Double sushramt = 0.0;

	private Double amtsftmsushr = 0.0;

	private Double sbashr = 0.0;

	private Double sbashramt = 0.0;

	private Double amtsftmsbashr = 0.0;

	private Double adminshr = 0.0;

	private Double adminshramt = 0.0;

	private Double amtsftmadminshr = 0.0;

	private Double suadminshr = 0.0;

	private Double suadminshramt = 0.0;

	private Double amtsftmsuadminshr = 0.0;

	private Integer mobapp = 0;

	private String usertype;

	private Double upline = 0.0;

	private Double uplineshr = 0.0;

	private String matchname;

	private String matchid;

	private String userchain;

	private String clientid;

	private Double finalamount = 0.0;

	private Double totaldebit = 0.0;

	private Double totalcredit = 0.0;

	private Double debit = 0.0;

	private Double credit = 0.0;

	private Double balance = 0.0;

	private String plusminus;

	private Boolean settelemnt;

	private String collectionname;

	private String remarks;

	private String type;

	private String date;

	private String sportname;

}
