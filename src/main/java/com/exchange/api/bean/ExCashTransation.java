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
public class ExCashTransation {

	private String date;

	private Date createdon;

	private String postdate;

	private String collectionname;

	private Double debit;

	private Double credit;

	private Double balance;

	private String paymenttype;

	private String remark;

	private String plusminus;

	private String userid;

	private String parentid;

	private Double amount;

	private String paymentmode;

	private String username;

	private Double currentbal;

	private Double openbal;

	private Double closebal;

	private String type;

	private String password;

	private Boolean mastetbalanceplus;

}
