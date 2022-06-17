package com.exchange.api.bean;

import lombok.Data;

@Data
public class EventRates {

	private String id;

	private String matchname;
	private Boolean inplay;
	private String date;
	private String back1;
	private String back2;
	private String back3;
	private String lay1;
	private String lay2;
	private String lay3;
	private String backsize1;
	private String backsize2;
	private String backsize3;
	private String laysize1;
	private String laysize2;
	private String laysize3;
	private Integer eventid;
	private String marketid;

	private Integer sportid;
}
