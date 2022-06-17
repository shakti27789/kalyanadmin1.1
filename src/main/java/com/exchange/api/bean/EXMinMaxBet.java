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
@Table(name = "t_minmaxbet")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMinMaxBet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "minbet")
	private Integer minbet;

	@Column(name = "maxbet")
	private Integer maxbet;

	@Column(name = "type")
	private String type;

	@Column(name = "betdelay")
	private Integer betdelay;

	@Column(name = "appid")
	private Integer appid;
}
