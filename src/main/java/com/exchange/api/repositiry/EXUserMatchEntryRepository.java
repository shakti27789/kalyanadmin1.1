package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXUserMatchEntry;

public interface EXUserMatchEntryRepository extends JpaRepository<EXUserMatchEntry, String> {

	public EXUserMatchEntry findByUseridAndEventid(String userid,Integer eventid);
	
	// @Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
	public ArrayList<EXUserMatchEntry> findByEventid(Integer eventid);
}
