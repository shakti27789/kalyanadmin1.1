package com.exchange.api.dao;

import java.util.ArrayList;

import com.exchange.api.bean.MatchFancy;

public interface FancyDAO {

	public ArrayList<MatchFancy> getFancy(String subadminid);
	
	public ArrayList<MatchFancy> getFancyBook(String user,String userid,String fancyid);

	ArrayList<MatchFancy> getFancyByMatchId(Integer matchid);
	
}
