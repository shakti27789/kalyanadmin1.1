package com.exchange.api.repositiry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXUser;

@Repository
public interface EXUserRepository extends JpaRepository<EXUser, String> {

	public EXUser findByUseridAndPassword(String userid,String password);

	public ArrayList<EXUser> findByParentidAndUsertype(String parentid, Integer usertype);
	

	public EXUser findByUserid(String userid);

	public EXUser findByUsername(String username);

	public ArrayList<EXUser> findByparentid(String parentid);


	public ArrayList<EXUser> findByAppid(Integer appid);

	public ArrayList<EXUser> findByAdminid(String adminid);
	
	public ArrayList<EXUser> findBySubadminid(String subadminid);

	public ArrayList<EXUser> findByparentidAndUsertype(String parentid,Integer usertype);

	public ArrayList<EXUser> findByMasteridAndUsertype(String masterid,Integer usertype);

	public EXUser findByParentidAndUsertypeAndUserid(String parentid, Integer usertype,String userid);

	public ArrayList<EXUser> findByParentid(String parentid);
	
	
	
	public EXUser findByParentidAndId(String parentid,String id);
	
	public ArrayList<EXUser> findBySubadminidAndUsertype(String subadminid,Integer usertype);
	
	public ArrayList<EXUser> findBySupermasteridAndUsertype(String supermasterid,Integer usertype);
	
	
	public ArrayList<EXUser> findByDealeridAndUsertype(String dealerid,Integer usertype);
	
	public EXUser findByid(Integer id);
	

	public ArrayList<EXUser> findBySupermasterid(String Supermasterid);
	
	public ArrayList<EXUser> findByMasterid(String Masterid);
	
	public ArrayList<EXUser> findByDealerid(String DealerId);
	
	

	public ArrayList<EXUser> findByUsertype(Integer usertype);
	
	@Query("SELECT tu.userid FROM EXUser tu WHERE id IN (:id)")
	public List<String> findByIds(ArrayList<Integer> id);

	@Query(nativeQuery = true, value = "SELECT * FROM t_user mf WHERE mf.iscasinobetlock = :iscasinobetlock ORDER BY id DESC ")
	public ArrayList<EXUser> findByIsCasinoBetlock(@Param("iscasinobetlock") Boolean iscasinobetlock);
	
	
	public ArrayList<EXUser> findByBetCount(Boolean betcount);

}
