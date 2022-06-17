package com.exchange.api.service;

import java.util.ArrayList;

import com.exchange.api.bean.SelectionIds;

public interface SelectionIdService {

	public ArrayList<SelectionIds> getSelectionsList(String  marketid);
	
	public ArrayList<SelectionIds> SaveSelectionId(String marketid,String eventid);
}
