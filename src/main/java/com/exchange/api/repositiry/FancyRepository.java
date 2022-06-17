package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXMatchFancy;
import com.exchange.api.bean.MatchFancy;

@Repository
public interface FancyRepository extends JpaRepository<MatchFancy, String>{

	public MatchFancy findByFancyid(String fancyid);
	
	public MatchFancy findByFancyidAndEventid(String fancyid,Integer eventid);
	
	public MatchFancy findByid(Integer id);
	
	ArrayList<MatchFancy> findByEventid(Integer eventid);
	
	public ArrayList<MatchFancy> findByEventidAndIsActive(Integer eventId,Boolean isActive);

	public ArrayList<MatchFancy> findByEventidAndIsActiveAndStatusAndIssuspendedByAdmin(Integer matchid, Boolean isActive,String string, boolean b);

	public ArrayList<MatchFancy> findByEventidAndStatusOrEventidAndStatusOrEventidAndStatusOrEventidAndStatus(
			int parseInt, String string, int parseInt2, String string2, int parseInt3, String string3, int parseInt4,
			String string4);

	@Query(nativeQuery = true, value = "SELECT * FROM t_matchfancy mf WHERE mf.status = :status ORDER BY id DESC LIMIT 1000")
	public ArrayList<MatchFancy> findByStatus(@Param("status") String status);

	public MatchFancy findByFancyidAndStatus(String fancyid,String status);

	
	public ArrayList<MatchFancy> findByEventidAndStatus(Integer eventId,String status);

	
	
}
