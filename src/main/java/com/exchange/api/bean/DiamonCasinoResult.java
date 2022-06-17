package com.exchange.api.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "diamond_casino_result")
public class DiamonCasinoResult {

	@Id
	String id;

	private String roundId;

	private String mid;

	private String gameId;

	private DiamondData data;

	private Boolean resultStatus;

	private String result;

	private String winner;

}
