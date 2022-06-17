package com.exchange.api.service;

import java.util.ArrayList;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ResponseBean;

public interface EXuserService {

	public  ResponseBean masterSignup(EXUser requestData,EXUser usersession);
	
	public ArrayList<EXUser> getUserByAparentId(String parentid, Boolean active, Integer usertype,String type,Integer id,Boolean isaccountclosed);
}
