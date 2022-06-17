package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.ExMatchOddsBet;


@Repository
public interface BetRepository extends JpaRepository<ExMatchOddsBet, String>{

	ArrayList<ExMatchOddsBet> findByMarketidAndIsactiveOrderByIdDesc(String marketid, Boolean isactive);
	
	public ExMatchOddsBet findByUseridAndMarketidAndSeries(String userid, String b,Integer series);

	public ArrayList<ExMatchOddsBet> findByUseridAndIsactive(String userid, boolean b);

	public ArrayList<ExMatchOddsBet> findByUseridAndMatchidAndIsactive(String userid, int eventid, boolean b);

	void deleteById(Integer id);

	public ArrayList<ExMatchOddsBet> findByResultidAndMasterid(Integer resultid, String masterid);

	public ArrayList<ExMatchOddsBet> findByMatchidAndIsactiveAndAdminid(int eventid, boolean b, String id);

	public ExMatchOddsBet findByid(Integer id);
	
	
	
	public ArrayList<ExMatchOddsBet> findByUseridAndMarketidAndTypeAndIsactiveAndMatchid(String userId,String marketId,String type,Boolean isActive,Integer matchId);

	public ArrayList<ExMatchOddsBet> findByMarketidAndIsactiveAndIsdeletedAndType(String fancyid, boolean b, boolean c,String type);

	
	public ArrayList<ExMatchOddsBet> findByMarketid(String marketid);

	public ArrayList<ExMatchOddsBet> findByMarketidAndUserid(String marketid, String userid);

	public ArrayList<ExMatchOddsBet> findByMarketidAndIsactiveAndUserid(String marketid,boolean b, String userid);
	
	public ArrayList<ExMatchOddsBet> findByUseridAndMarketidAndTypeAndIsactiveAndMatchidAndStatus(String userId,String marketId,String type,Boolean isActive,Integer matchId,String status);

	public ExMatchOddsBet findByidAndIsdeletedAndIsactive(Integer id,Boolean isdeleted,Boolean active);

	public ArrayList<ExMatchOddsBet> findByMarketidAndMatchidAndIsactiveAndUserid(String marketId,Integer matchId,Boolean isactive,String userid);
	
	public ArrayList<ExMatchOddsBet> findByIsactiveAndMatchid(Boolean isactive,Integer matchid);
	
	@Query("select bt from ExMatchOddsBet bt where bt.marketid=?1 and bt.isactive=?2 and bt.adminid=?3")
	Stream<ExMatchOddsBet>   findByMarketidAndIsactiveAndAdminid(String marketid,Boolean isactive,String adminid);
	
	
	public ArrayList<ExMatchOddsBet> findByMatchidAndUseridAndMarketname(Integer matchId,String userid,String marketname);
	
	public ArrayList<ExMatchOddsBet> findByIsactive(Boolean isactive);
	
	
	public ArrayList<ExMatchOddsBet> findByaddetoFirestoreAndMatchid(Boolean isactive,Integer matchid);
	
	
	public ArrayList<ExMatchOddsBet> findByaddetoFirestoreAndMarketid(Boolean isactive,String matchid);
	
	public ArrayList<ExMatchOddsBet> findByaddetoFirestore(Boolean isactive);
	
	ArrayList<ExMatchOddsBet> findByMarketidAndIsactiveAndIsdeletedOrderByMatchedtimeDesc(String marketid, Boolean isactive,Boolean isdeleted);
	
	
	public ArrayList<ExMatchOddsBet> findByUseridAndMarketidAndTypeAndIsactiveAndMatchidAndIsdeleted(String userId,String marketId,String type,Boolean isActive,Integer matchId,Boolean isDeleted);

	
	public ArrayList<ExMatchOddsBet> findByUseridAndMarketidAndIsactiveAndIsdeleted(String userId,String marketId,Boolean isActive,Boolean isDeleted);

	public ArrayList<ExMatchOddsBet> findByMarketidAndIsactive(String marketid,Boolean isactive);
}
