
package com.exchange.api.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_chipdetail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXChipDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userId;

	@Column(name = "parentid")
	private Integer parentId;

	@Column(name = "createdon")
	private Date createdOn;

	@Column(name = "descreption")
	private String descreption;

	@Column(name = "credit")
	private Double credit;

	@Column(name = "debit")
	private Double debit;

	@Column(name = "type")
	private String type;

	@Column(name = "closing")
	private BigDecimal closing;

	@Column(name = "marketid")
	private String marketId;

	@Column(name = "matchid")
	private Integer matchId;

	@Column(name = "fromto")
	private String fromTo;

	@Transient
	private String Date;

	@Column(name = "chips")
	private Double chips;

	@Column(name = "issettlement")
	private Boolean isSettlement;
	
	@Column(name = "ismongodbupdated")
	private Boolean ismongodbupdated = false;
}