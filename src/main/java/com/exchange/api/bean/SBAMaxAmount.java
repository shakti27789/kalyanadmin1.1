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
@Table(name = "t_sbamax")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SBAMaxAmount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "eventid")
	private Integer eventid;

	@Column(name = "maxbet")
	private Integer maxbet;

	@Column(name = "maxbetfancy")
	private Integer maxbetfancy;

	@Column(name = "maxbetbookmaker")
	private Integer maxbetBookmaker;

}
