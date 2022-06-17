package com.exchange.api.bean;

import lombok.Data;

@Data
public class EXBetfairMarket {

	private String id;

	private String marketId;

	private String marketName;

	private Integer totalMatched;
}
