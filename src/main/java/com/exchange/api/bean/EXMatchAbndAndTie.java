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
@Table(name = "t_matchabondendtie")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMatchAbndAndTie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "marketid")
	private String marketid;

	@Column(name = "marketname")
	private String marketname;

	@Column(name = "sportid")
	private Integer sportid;

	@Column(name = "sportname")
	private String sportname;

	@Column(name = "matchid")
	private Integer matchid;

	@Column(name = "matchname")
	private String matchname;

	@Column(name = "result")
	private String result;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "date")
	private String date;
}
