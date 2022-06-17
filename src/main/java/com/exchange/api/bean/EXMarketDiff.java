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
@Table(name = "t_market_diff")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMarketDiff {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;


	@Column(name = "eventid")
	private Integer eventid;
	
	@Column(name = "backdiff")
	private Integer backdiff;
	
	@Column(name = "laydiff")
	private Integer laydiff;
	
	@Column(name = "isactive")
	private Boolean isactive;

	
	
}
