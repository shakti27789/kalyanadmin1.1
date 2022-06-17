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
@Table(name = "t_sportbetdelay")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EXSportBetDealy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	String userid;

	@Column(name = "sportid")
	Integer sportId;

	@Column(name = "mibbet")
	Integer mibbet;

	@Column(name = "maxbet")
	Integer maxbet;

	@Column(name = "betdelay")
	Integer betdelay;
}
