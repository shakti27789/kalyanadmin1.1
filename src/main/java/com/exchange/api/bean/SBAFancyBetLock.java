package com.exchange.api.bean;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_sbafancybetlock")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SBAFancyBetLock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private Integer userid;

	@Column(name = "eventid")
	private Integer eventid;

	@Column(name = "fancybetlock")
	private Boolean fancybetlock;
}
