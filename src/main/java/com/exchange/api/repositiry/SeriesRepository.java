package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.Series;

public interface SeriesRepository extends JpaRepository<Series, String>{

	public Series findByseriesidAndAppid(Integer seriesid,Integer appid);
	
	public Series findByid(String id);
	
	public Boolean existsByidIn(ArrayList<String> id);
	
	public ArrayList<Series> findAllByidIn(ArrayList<String> id);
	
	public ArrayList<Series> findByStatus(Boolean status);
	
	public ArrayList<Series> findBySportidAndStatus(Integer sportid, Boolean status);
	
	public Series findBySportid(Integer sportid);
}

