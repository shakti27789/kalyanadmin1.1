package com.exchange.api.bean;

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
@Table(name = "t_rsfancy_result")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXRsFancyResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "fancyid")
	private String fancyId;

	@Column(name = "result")
	private String result;
	
	@Column(name = "matchid")
	private Integer matchid;
	
	@Column(name = "isresultset")
	private Boolean isresultset;
	
	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "ismysqlupdated")
	Boolean ismysqlupdated;
}
