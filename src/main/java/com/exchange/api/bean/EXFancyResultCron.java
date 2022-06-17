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
@Table(name = "t_fancy_result_cron")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXFancyResultCron {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "eventid")
	Integer eventId;

	@Column(name = "fancyid")
	String fancyId;

	@Column(name = "resultstatus")
	Boolean resultStatus;

	@Column(name = "result")
	Integer result;
}
