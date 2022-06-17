package com.exchange.api.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.appBean;
import com.exchange.api.dao.EXUserDAO;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;

@Repository
public class EXUserDAOImpl implements EXUserDAO {

	
	@PersistenceContext
	private EntityManager entitymanager;
	
	EXDateUtil dtUtil = new EXDateUtil();
	
	@Autowired
	EXUserRepository userRepo;
	
	
	
	@Override
	public ArrayList<EXUser> getUserByAparentId(String parentid, Boolean active, Integer usertype, String type,Integer id,Boolean isaccountclosed) {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		EXUser userbean =null;
        sb.append("select user.id, user.userid,user.username,user.adminid,user.subadminid,user.supermasterid,user.masterid,user.usertype,user.parentid,user.userchain,user.appid,user.fancylosscommisiontype,user.betlock,user.accountlock,user.netexposure  ");
        sb.append(",user.subadminpartnership,user.availableBalance,user.creditRef,user.supermastepartnership,user.masterpartership,user.delearpartership,user.availableBalanceWithPnl,user.uplineAmount,user.betlock,user.subadminpartnership,user.adminpartershipc,user.subadminpartnershipc,user.supermastepartnershipc,user.masterpartershipc,user.delearpartershipc,user.isCasinoBetlock,hisabkitabtype,isaccountclosed from EXUser user where"); 
        if(type.equalsIgnoreCase("All")){
		 sb.append(" user.parentid=:parentid ");
			
		}else{
			sb.append(" user.parentid=:parentid and user.active =:active and user.usertype =:usertype ");
			
		}
        sb.append(" and id !=:id and user.isaccountclosed=:isaccountclosed order by user.usertype asc");
		try {
			
			
			Query query = (Query) entitymanager.createQuery(sb.toString());
			query.setParameter("parentid", parentid);
			// query.setBoolean("active", true);
			 
			if(!type.equalsIgnoreCase("All")){
				query.setParameter("usertype", usertype);
				
			}
			query.setParameter("id", id); 
			query.setParameter("isaccountclosed", isaccountclosed);
			
			List<Object[]> resultList = query.getResultList();
		
			for (Object[] a : resultList) {
				userbean = new EXUser();
			    userbean =userbean.getInstance(a);
				list.add(userbean);
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
		
	}

	@Override
	public Integer countUserByType(Integer usertype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean masterSignup(EXUser requestData, EXUser usersession,appBean appbean) {
		
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();// TODO Auto-generated method stub
		
		
		 EntityTransaction trans = entitymanager.getTransaction();
		 try {
             trans.begin();
             
             if(requestData.getUsertype() ==5){
            	 entitymanager.persist(appbean);
            	 requestData.setAppid(appbean.getId());
            	 requestData.setUserchain(EXConstants.asba);
            	 SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	 requestData.setCreatedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
            	 requestData.setUpdaedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
            	 requestData.setBetlock(false);
            	 entitymanager.persist(requestData);
            	 EXUser bean = userRepo.findByid(Integer.parseInt(requestData.getParentid()));
            	
            	 bean.setUpdaedon(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID().toString(), "GMT"));
            	/* System.out.print("Exception ==>"+(10/0));*/
            	 
             }
             
             trans.commit();
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             trans.rollback();
         } finally {
        	 entitymanager.close();
         }
		 
		return null;
	}

	@Override
	public Integer countUserByCategory(Integer usertype) {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from EXUser where usertype =:usertype");
		Query query = (Query) entitymanager.createQuery(sb.toString());
		query.setParameter("usertype", usertype);
		
		Integer  count =  Integer.parseInt(query.getSingleResult().toString());
		return count;
	}
	
	@Override
	public Double downlineOccupyBalance(String  parentIds) {
		// TODO Auto-generated method stub 
		String str="2,15,21,22,23";
		ArrayList<EXUser> list = new ArrayList<EXUser>();
		StringBuffer sb  = new StringBuffer();
		sb.append(" select coalesce(sum(if(usr.usertype= 3,(usr.availablebalance + usr.availablebalancewithpnl +coalesce((select sum(-lib.liability ) from  t_libility lib where lib.userid=usr.userid and lib.isactive=true  group by lib.userid ) ,0)),(usr.creditref -usr.uplineamount))),0)  AS pnl ");
		sb.append("from  t_user usr where usr.parentid in ("+parentIds+") ");
		
		Query query = (Query) entitymanager.createNativeQuery(sb.toString());
		//query.setParameterList(1, parentIds);
		
		Object result = query.getSingleResult();
		return (Double)result;
	}

}
