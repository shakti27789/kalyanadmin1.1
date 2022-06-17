package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exchange.api.bean.Event;

public interface EventRepository extends JpaRepository<Event, String>{

	public Event findByeventidAndAppid(Integer eventid,Integer appid);
	
	public Event findByeventid(Integer eventid);

	public Event findByid(String id);
	
	public Boolean existsByidIn(ArrayList<String> id);
	
	public ArrayList<Event> findAllByidIn(ArrayList<String> id);
	
	public ArrayList<Event> findByStatusAndAdminid(Boolean status,String subadminid);
	
	public ArrayList<Event> findByStatusAndAppid(Boolean status,Integer appid);
	
	public ArrayList<Event> findByStatusAndSeriesid(Boolean status, Integer seriesid);
	
	public Event findByEventidAndStatus(Integer eventid,Boolean status);
	
	
	public ArrayList<Event> findByStatusAndSportidAndAdminid(Boolean status, Integer sportid, String subadminid);
	
	public ArrayList<Event> findByStatusAndSportid(Boolean status, Integer sportid);
	
	public ArrayList<Event> findBySportidAndStatus(Integer sportid, Boolean status);
	
	public Event findByEventidAndAddautoFancy(String eventid, Boolean addautofancy);
	
	public ArrayList<Event> findByStatus(Boolean status);
	
	
	public Event findByid(Integer id);
	
	public ArrayList<Event> findBySeriesid(Integer seriesid);
	
	@Query(nativeQuery = true, value = "SELECT * FROM t_event ev WHERE ev.sportid = :sportid ORDER BY id DESC LIMIT 50")
	public ArrayList<Event> findBySportid(Integer sportid);
	
	public ArrayList<Event> findByType(String type);
	
	
	public Event findByEventid(Integer eventId);
	
	public  ArrayList<Event> findByAddautoFancyAndStatusAndSportid(Boolean eventid, Boolean addautofancy,Integer sportId);
	
	public  ArrayList<Event> findByLiveTvAndType(Boolean liveTv,String type);
	
	
}
