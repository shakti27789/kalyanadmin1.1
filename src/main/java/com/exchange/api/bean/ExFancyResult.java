package com.exchange.api.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_fancyresult")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExFancyResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "fancyid")
	private String fancyid;

	@Column(name = "groupid")
	private String groupid;

	@Column(name = "fancyname")
	private String fancyname;

	@Column(name = "sportid")
	private int sportid;

	@Column(name = "sportname")
	private String sportname;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "seriesid")
	private int seriesid;

	@Column(name = "seriesname")
	private String seriesname;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "result")
	private Integer result;

	@Column(name = "isresult")
	private Boolean isResult;

	@Column(name = "resultstatuscron")
	private Boolean resultStatusCron;
	
	@Column(name = "resultdeclareby")
	private String resultdeclareby;

	@Column(name = "isprofitlossclear")
	private Boolean isprofitlossclear;
	
	@Transient
	private String resultDetail;
	
	@Column(name = "fancytype")
	private String fancytype;

}
