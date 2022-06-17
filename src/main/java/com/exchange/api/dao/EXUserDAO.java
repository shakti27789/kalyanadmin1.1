package com.exchange.api.dao;

import java.util.ArrayList;

import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.appBean;

public interface EXUserDAO {

	public ArrayList<EXUser> getUserByAparentId(String parentid,Boolean active, Integer usertype,String type,Integer id,Boolean isaccountclosed);
	
	public  Integer countUserByType(Integer usertype);
	
	public Boolean masterSignup(EXUser requestData,EXUser usersession,appBean appbean);
	
	public Integer countUserByCategory(Integer usertype);

	Double downlineOccupyBalance(String parentIds);
}
