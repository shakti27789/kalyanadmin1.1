
package com.exchange.api.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "t_chipdetail")
public class EXChipDetailMongo {

	@Id
	public String id;

	@Field(value = "userid")
	private String userId;

	@Field(value = "parentid")
	private Integer parentId;

	@Field(value = "createdon")
	private Date createdOn;

	@Field(value = "descreption")
	private String descreption;

	@Field(value = "credit")
	private Double credit;

	@Field(value = "debit")
	private Double debit;

	@Field(value = "type")
	private String type;

	@Field(value = "closing")
	private BigDecimal closing;

	@Field(value = "marketid")
	private String marketId;

	@Field(value = "matchid")
	private Integer matchId;

	@Field(value = "fromto")
	private String fromTo;

	@Transient
	private String Date;

	@Field(value = "chips")
	private Double chips;

	@Field(value = "issettlement")
	private Boolean isSettlement;
}