package com.exchange.api.dao;

import java.util.ArrayList;


public interface EXLotusCasinoResultDao {

	ArrayList<Object> findBygameIdAndResultStatus(String gameId, Boolean resultStatus);
	
}
  