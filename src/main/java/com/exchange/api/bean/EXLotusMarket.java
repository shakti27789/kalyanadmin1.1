package com.exchange.api.bean;

import lombok.Data;

@Data
public class EXLotusMarket {

	private String id;

	private String marketId;

	private String marketName;

	private String timezone;

	private String opendate;

	private String countrycode;

	private Integer eventid;

	private String eventname;

	private String sportid;

	private Boolean addautoFancy;

	private Integer minbet;
	private Integer maxbet;
	private Integer betdelay;

	private String fancyprovider;
}
