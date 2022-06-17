package com.exchange.api.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ResponseBean {

	private String type;
	private String message;
	private String title;
	private String balance;
	private String matchBet;
	private String rate;
	private Integer amount;
	private String team;
	private String mode;
	private String run;

	private LinkedHashMap<String, ArrayList<?>> hashmap;

	private ArrayList<?> data;

}
