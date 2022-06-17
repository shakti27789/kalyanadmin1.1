package com.exchange.api.bean;

import java.math.BigDecimal;
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
@Table(name = "t_chipdetail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXLedgerNew {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "parentid")
	private Integer parentid;

	@Column(name = "marketid")
	private String marketid;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "type")
	private String type;

	@Column(name = "userid")
	private String userid;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "descreption")
	private String descreption;

	@Column(name = "credit")
	private Double credit;

	@Column(name = "debit")
	private Double debit;

	@Column(name = "closing")
	private BigDecimal closing;

	@Column(name = "fromto")
	private String fromTo;
}
