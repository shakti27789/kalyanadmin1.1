package com.exchange.web;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.exchange.api.bean.EXMaintenance;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.bean.Sport;
import com.exchange.api.bean.appBean;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.AppBeanRepo;
import com.exchange.api.repositiry.EXMaintenanceRepository;
import com.exchange.api.repositiry.EXUserLoginRepository;
import com.exchange.api.repositiry.EXUserRepository;
import com.exchange.api.repositiry.SportRepository;
import com.exchange.api.repositiry.UserIpRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXDateUtil;

@Controller
public class EXLogin {

	@Autowired 
	HttpServletRequest request;

	@Autowired
	HttpSession session;

	EXDateUtil dtUtil = new EXDateUtil();
	
	
	@Autowired
	EXUserRepository  userRepo;
	
	@Autowired
	UserIpRepository userIpRepo;
	
	@Autowired
	AppBeanRepo appBeanRepo;

	@Autowired 
	EXMaintenanceRepository maintenceRepo;
	
	@Autowired
	EXUserLoginRepository userLoginRepo;


	@Autowired
	EXNativeQueryDao nativeQueryDao;
	
	@Autowired
	SportRepository sportRepo;
	
	@Transactional
	@RequestMapping("/")   
	public ModelAndView  index() {  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);

		EXMaintenance maintbean = maintenceRepo.findByid(1);
	
        try {
        	EXUser usersession =(EXUser) session.getAttribute("user");
        	InetAddress localhost = InetAddress.getLocalHost(); 
        
        	
        	NetworkInterface network = NetworkInterface.getByInetAddress(localhost);
	    	   byte[] mac = network.getHardwareAddress();
	    	   StringBuilder sb = new StringBuilder();
	   		for (int i = 0; i < mac.length; i++) {
	   			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	   		}
        	if(usersession!=null){
        		if(usersession.getUsertype() == 4 || usersession.getUsertype() == 6) {
        			userLoginRepo.deleteByUserid(usersession.getUserid());
            		
        		}else {
        			userLoginRepo.deleteByUseridAndLoginIp(usersession.getUserid(),sb.toString());
        			
        		}
        			
        		session.removeAttribute("user");
        		session.invalidate();
        	}
			  appBean bean = appUrl();
			 // model.addObject("appName", appName());
			  if(bean!=null){
				  model.addObject("desktopimage", bean.getLoginimage());
				  model.addObject("mobimageimage", bean.getLoginimagemob());
			  }
			  
			  if(maintbean!=null){
					if(maintbean.getIsmaintenace()){
						model.setViewName("manitence");
					}else{
						model.setViewName("adminlogin");
					}
				}else{
					model.setViewName("adminlogin");
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		return model;
	} 

	public String  appName(){  

		
    	String appurl = "www."+request.getServerName().substring(6);
    	appBean appbean  = appBeanRepo.findByAppurl(appurl);
    	
    	
    	return appbean.getAppname();
	}

	
	@ResponseBody
	@RequestMapping(value="/sport")
	public ModelAndView  sport(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
    
		if(user!=null && (user.getUsertype()==0||user.getUsertype()==5 || user.getUsertype()==4 || user.getUsertype()==6))
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				if(user.getUsertype()==0){
					model.setViewName("matchesList");				
				}else{
					model.setViewName("sport");	
				}
			}else {
				model.setViewName("changePassword");
			}
			
		}
		else 
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/subadminsport")
	public ModelAndView  subadminSport(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && (user.getUsertype()==0||user.getUsertype()==5 || user.getUsertype()==4))
		{
			if(user.getUsertype()==0){
				model.setViewName("matchesList");				
			}else{
				model.setViewName("subadminsport");	
			}
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/superstockagesport")
	public ModelAndView  superstockageSport(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null)
		{
			
				model.setViewName("superstockagesport");	
			
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 

	@ResponseBody
	@RequestMapping(value="/dashboard/{sportid}")
	public ModelAndView  dashboard(@PathVariable("sportid")int sportid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null)
		{
			if(user.getUsertype()==3){
				model.setViewName("userhome");				
			}else{
				model.addObject("sportid", sportid);
				model.setViewName("home");
			}
 
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/add-account")
	public ModelAndView  addAccount(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				if(user.getUsertype() !=3){
					
					if( user.getUsertype() == 4){

						model.setViewName("addapplication");
					}else if( user.getUsertype() == 0){

						model.setViewName("adduser");
					}
					else if( user.getUsertype() == 1)
					{
						model.setViewName("adduser");
					}
					else if( user.getUsertype() == 2)
					{
						model.setViewName("adduser");
					}
					else if( user.getUsertype() == 4)
					{
						model.setViewName("adduser");
					}
					else if( user.getUsertype() == 5)
					{
						model.setViewName("adduser");
					}
					else if( user.getUsertype() == 6)
					{
						model.setViewName("adduser");
					} 
			  }	
			  else
				{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
				}
			}else {
				model.setViewName("chnagePassword");
			}
		  
		
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/userList")
	public ModelAndView  userList(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("masterList");
			}else {
				model.setViewName("changePassword"); 
			}
			
		}
		else
		{
			model.setViewName("adminlogin");
		} 

		return model;
	} 
	 
	@ResponseBody
	@RequestMapping(value="/powerUserList")
	public ModelAndView  powerUserList(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("powerUserList");
			}else {
				model.setViewName("changePassword");
			}
			
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	}  
	
	@ResponseBody
	@RequestMapping(value="/userListByparentId/{parentId}/{Id}")
	public ModelAndView  userListByparentId(@PathVariable("parentId")Integer parentId,@PathVariable("Id")Integer Integer){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("masterListByParentId");
			}else {
				model.setViewName("changePassword");
			}
			
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	
	@RequestMapping(value="/openFancy")
	public ModelAndView  openFancy(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		  
		if(user!=null)
		{
			if(user.getUsertype() == 4 || user.getUsertype() == 6){
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("openFancy");
				}else {
					model.setViewName("changePassword");
				}
				
			}else{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/finishedMatchFancy")
	public ModelAndView  finishedMatchFancy(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		  
		if(user!=null)
		{
			if(user.getUsertype() == 4 || user.getUsertype() == 6){
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("finishedMatchFancy");
				}else {
					model.setViewName("changePassword");
				}
				
			}else{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/reactTest")
	public ModelAndView  reactTest(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() == 5)
		{
			
			model.setViewName("reactTest");
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/EVENT/{sportid}/{eventid}/{marketid}")
	public ModelAndView  event(@PathVariable("sportid")int sportid,@PathVariable("eventid")Integer  eventid,@PathVariable("marketid")String  marketid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()==3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.addObject("sportid", sportid);
				model.addObject("eventid", eventid);
				model.setViewName("event");
			}else {
				model.setViewName("changePassword");
			}
		
		}else{
			model.setViewName("login");
		}
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/matchOddPage")
	public ModelAndView  matchOddPage(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null)
		{
			
			model.setViewName("onedayodds");
		}else{
			model.setViewName("login");
		}
		return model;
	} 
	
	

	
	
	@RequestMapping(value="/activeMatch")
	public ModelAndView  activeMatch(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && (user.getUsertype() == 5||user.getUsertype() == 4||user.getUsertype() == 0 || user.getUsertype() == 6))
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("activeMatch");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/betHistory")
	public ModelAndView  betHistory(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() != 3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("betHistory");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/inactiveMatch")
	public ModelAndView  inactiveMatch(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null && (user.getUsertype() == 5||user.getUsertype() == 4||user.getUsertype() == 0))
		{
			model.setViewName("inactiveMatch");
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/marketResult")
	public ModelAndView  marketResult(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() == 5)
		{
			model.setViewName("marketResult");
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/openMarket")
	public ModelAndView  openMarket(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		    
		if(user!=null)
		{
			if(user.getUsertype() == 4 || user.getUsertype() == 6){
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("openMarket");
				}else {
					model.setViewName("changePassword");
				}
				
			}else{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		
		return model;
	} 
	
	@RequestMapping(value="/myBet")
	public ModelAndView  myBet(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()==3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("mybet");
			}else {
				model.setViewName("chnagePassword");
			}
			
		}else{
			model.setViewName("login");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/profitLoss")
	public ModelAndView  profitLoss(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()==3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("profitloss");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("login");
		}
		

		return model;
	} 
	
	
	
	public appBean  appUrl(){
		String url = request.getRequestURL().toString();
        String domain =null;
		appBean bean=null;
		  try {
				URL url1 = new URL(url);
				 domain = url1.getHost();
				  String[] arrOfStr= domain.split(".com", 2); 
				  String appid ="appid_"+arrOfStr[0]; 
				  if(appid.equalsIgnoreCase("appid_bm") || appid.equalsIgnoreCase("appid_local") || appid.equalsIgnoreCase("appid_timexbook")){
					   bean= appBeanRepo.findByid(Integer.parseInt(EXConstants.appid_bm));
					 
				  }else  if(appid.equalsIgnoreCase("appid_11bet24")){
					   bean= appBeanRepo.findByid(Integer.parseInt(EXConstants.appid_11bet24));
					
				  }else  if(appid.equalsIgnoreCase("appid_jmk888")){
					   bean= appBeanRepo.findByid(Integer.parseInt(EXConstants.appid_jmk888));
					  
				  }
		  }catch(Exception e){
			  
		  }
		return bean;
		
	}
	
	@Transactional
	@RequestMapping(value="/admin")
	public ModelAndView adminLogin(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);

		EXMaintenance maintbean = maintenceRepo.findByid(1);
	
        try {
        	EXUser usersession =(EXUser) session.getAttribute("user");
        	if(usersession!=null){
        		userLoginRepo.deleteByUserid(usersession.getUserid());
        		session.removeAttribute("user");
        		session.invalidate();
        	}
			  appBean bean = appUrl();
			  if(bean!=null){
				  model.addObject("desktopimage", bean.getLoginimage());
				  model.addObject("mobimageimage", bean.getLoginimagemob());
			  }
			  
			  if(maintbean!=null){
					if(maintbean.getIsmaintenace()){
						model.setViewName("manitence");
					}else{
						model.setViewName("adminlogin");
					}
				}else{
					model.setViewName("adminlogin");
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		return model;
	} 
	
	@RequestMapping(value="/minMaxBet")
	public ModelAndView minMaxBet(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() == 4)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("minMaxBet");
			}else {
				model.setViewName("changePassword");
			}
			model.setViewName("minMaxBet");
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/netExposure")
	public ModelAndView netExposure(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() != 3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
			     model.setViewName("netExposure");
			}else {
				model.setViewName("changePassword");
			}
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}	
	
	@RequestMapping(value="/openMarketList")
	public ModelAndView openMarketist(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null   && user.getUsertype()!=3){
			model.setViewName("openMarketList");			
		}else{
			model.setViewName("adminlogin");

		}
		

		return model;
	}	
	
	@RequestMapping(value="/message")
	public ModelAndView  message(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()==4)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("message");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}

	@ResponseBody
	@RequestMapping(value="/innerExpo/{sportid}/{eventid}/{marketid}")
	public ModelAndView  innerExpo(@PathVariable("sportid")int sportid,@PathVariable("eventid")Integer  eventid,@PathVariable("marketid")String  marketid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.addObject("sportid", sportid);
				model.addObject("eventid", eventid);
				model.setViewName("innerExpo");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/liveMatchbets")
	public ModelAndView  liveMatchbets(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("liveMatchbets");
				
			}else {
				model.setViewName("changePassword");
			}
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/profitAndLoss")
	public ModelAndView  profitAndLoss(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("profitAndLoss");	
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="/openBets")
	public ModelAndView  openBets(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		
		if(user!=null)
		{
			if(user!=null  && user.getUsertype()!=3){
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("openBets");
					
				}else {
					model.setViewName("changePassword");
					
				}
			}else{
				session.removeAttribute("user");
				model.setViewName("login");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		
		

		return model;
	}
	
	@RequestMapping(value="/statements")
	public ModelAndView  statements(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null   && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("statements");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	 
	@RequestMapping(value="/changePartnership")
	public ModelAndView  changePartnership(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null   && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("changePartnership");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/settlementlist")
	public ModelAndView  settlementlist(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null   && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("settlementlist");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	
	
	@RequestMapping(value="/deletedBets")
	public ModelAndView  deletedBets(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()==5 || user.getUsertype()==4)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("deletedBets");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/deleteBets")
	public ModelAndView  deleteBets(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null  && user.getUsertype()==5 || user.getUsertype()==4)
		{
			
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("deleteBets");
			}else {
				model.setViewName("changePassword");
			}
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}

	@RequestMapping(value="/resultBets/{resultid}")
	public ModelAndView  resultBets(@PathVariable("resultid")String resultid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			model.setViewName("resultBets");			
		}else{
			model.setViewName("adminlogin");
		}


		return model;
	}
	

	
	

	
	
	@RequestMapping(value="/cashTransaction")
	public ModelAndView  cashTransaction(){  
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		if(user!=null && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("cashTransaction");		
			}else {
				model.setViewName("changePassword");	
			}
			
					
		}else{
			model.setViewName("adminlogin");	
		}


		return model;
	}

	@RequestMapping(value="/inactiveUser")
	public ModelAndView  inactiveUser(){  
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		if(user!=null  && user.getUsertype()==5){
			model.setViewName("inactiveUser");				
		}else{
			model.setViewName("adminlogin");	
		}

		return model;
	}

	


	
	@RequestMapping(value="/statementByUser/{userid}/{username}")
	public ModelAndView  statementByUser(@PathVariable("userid")String userid,@PathVariable("username")String username){  
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();

		if(user!=null){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("statementByUser");	
				
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");	
		}

		
		return model;
	}
	
	@RequestMapping(value="/ledger")
	public ModelAndView  ledger(){  
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		
		if(user!=null)
		{
			if(user!=null  && user.getUsertype()!=3){
				model.setViewName("ledger");
			}else{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}	
	



	
	
	
	
	
	@RequestMapping(value="/minMaxBetSetAmount")
	public ModelAndView minMaxBetSetAmount(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null)
		{
			if(user.getUsertype() == 4){
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("minMaxBetSetAmount");
					
				}else {
					model.setViewName("changePassword");
					
				}
			}else{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
			}
	
		}else{
			model.setViewName("adminlogin");
		}
		
		

		return model;
	} 
	
	@RequestMapping(value="/sportDetail")
	public ModelAndView  sportDetail(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("sportDetail");	
					
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");
		}
		return model;
	}
	
	
	
	
	

	@RequestMapping(value="/cashTransactionNew")
	public ModelAndView  cashTransactionNew(){  
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		ModelAndView model = new ModelAndView();
		if(user!=null && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("cashTransactionNew");	
				
			}else {
				model.setViewName("chnagePasword");	
				
			}
		}else{
			model.setViewName("adminlogin");	
		}


		return model;
	}
	
	
	@Transactional
	@ResponseBody
	@RequestMapping(value="/adminlogin", method = RequestMethod.GET)
	public ModelAndView  adminlogin(HttpServletRequest req) throws UnknownHostException, ParseException, SocketException{  
		
		 InetAddress ipAddr1 = InetAddress.getLocalHost();
       
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser usersession =(EXUser) session.getAttribute("user");
		EXUser user = null;
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
        ResponseBean rbean =new ResponseBean();
        String url = request.getRequestURL().toString();
        String domain =null;
       
      if(usersession!=null){
    	
    	  if(checkPasswordType(usersession.getUserid()).equalsIgnoreCase("new")) {
    		  if(usersession.getUsertype()==6){
    	  			model.setViewName("sport");
    	  			
    	  		}else if(usersession.getUsertype()==7){
    	  			model.setViewName("openMarket");
    	  			
    	  		}else{
    	  			if(usersession.getUsertype() ==4){
    	  				model.setViewName("netExposure");
    	  			}else{
    	  				model.setViewName("netExposure");
    	  			}
    	  		}
    	  }else {
    		  model.setViewName("changePassword");
    	  }
    	 
      }else{
    		model.setViewName("adminlogin");
      }
        
      return model;
	} 
	
	

	
	
	


	

	
	
	@RequestMapping(value="/currentBets")
	public ModelAndView  currentBets(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null && user.getUsertype() != 3)
		{
			 if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				 model.setViewName("currentBets");
			 }else {
				 model.setViewName("changePassword");
			 }
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/powerUser")
	public ModelAndView  powerUser(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null)
		{

			if (user.getUsertype() == 4) {
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("poweruser");
				}else {
					model.setViewName("changePassword");
				}
				
			}

		}	
		  else
			{
			session.removeAttribute("user");
			model.setViewName("adminlogin");
			}
		
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/changePassword")
	public ModelAndView  changePassword(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null)
		{

			if (user.getUsertype() == 4) {
				model.setViewName("changePassword");
								
			}

		}	
		  else
			{
			session.removeAttribute("user");
			model.setViewName("adminlogin");
			}
		
		

		return model;
	} 
	
	public String checkPasswordType(String userId){
	
		String passwordType = userRepo.findByUserid(userId).getPasswordtype();
		return passwordType;
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/addManualMatch")
	public ModelAndView  addManualMatch(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null)
		{

			if (user.getUsertype() == 4 || user.getUsertype() == 6) {
				if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("addmanualmatch");
				}else {
					model.setViewName("changePassword");
				}
				
			}

		}	
		  else
			{
			session.removeAttribute("user");
			model.setViewName("adminlogin");
			}
		
		

		return model;
	} 
	

	@ResponseBody
	@RequestMapping(value="/casino/{sportId}")
	public ModelAndView  casino(@PathVariable("sportId")Integer sportId){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				if(sportId == 504) {
					model.setViewName("TEENPATTI");	
				}else if(sportId == 501) {
					model.setViewName("LUCKY7");	
				}else if(sportId == 503) {
					model.setViewName("DRAGANTIGER");	
				}else if(sportId == 502) {
					model.setViewName("BBCASINO");	
				}else if(sportId == 505) {
					model.setViewName("ANDARBAHAR");	
				}else if(sportId == 506) {
					model.setViewName("ROULETTE");	
				}else if(sportId == 507) {
					model.setViewName("POKER");	
				}
				 
				
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/VCasino")
	public ModelAndView  VCasino(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("VCasino");	
				 
				
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}  
	
	
	@ResponseBody
	@RequestMapping(value="/LiveCasino")
	public ModelAndView  LiveCasino(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("LiveCasino");	
				 
				
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/casino/tponeday/{matchId}")
	public ModelAndView  tponeday(@PathVariable("matchId")Integer matchId){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("teenpattioneday");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
/*	@ResponseBody
	@RequestMapping(value="/casino/TeenPatti20/{eventid}")
	public ModelAndView dtpt20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("dteenpattit20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} */
	
	@ResponseBody
	@RequestMapping(value="/casino/VTeenPatti20/{eventid}")
	public ModelAndView  tp2020(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vteenpattit20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	  
	
	@ResponseBody
	@RequestMapping(value="/casino/VTeenPattiOpen/{eventid}")
	public ModelAndView  VTeenPattiOpen(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vopenteenpattit20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/casino/VAB2/{eventid}")
	public ModelAndView  VAndarBahar2(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("VAndarBahar2");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/DLTeenPatti20/{eventid}")
	public ModelAndView  DLTeenPatti20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("teenpattit20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/DLTeenPatti1Day/{eventid}")
	public ModelAndView  DLTeenPatti1Day(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("teenpattioneday");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	 
	@ResponseBody 
	@RequestMapping(value="/casino/VRoulette/{eventid}")
	public ModelAndView  VRoulette(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("VRoulette");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/VLucky7/{eventid}")
	public ModelAndView  VLucky7(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vlucky7");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/VAB20/{eventid}")
	public ModelAndView  Ab20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vAndarBahar");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/DLLucky7A/{eventid}")
	public ModelAndView  Lucky7(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("lucky7");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	
	@ResponseBody
	@RequestMapping(value="/casino/VDT20/{eventid}")
	public ModelAndView  VDT20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vdragontiger");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/DLDT20/{eventid}")
	public ModelAndView  DLDT20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("dragontiger20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/DLDT1DAY/{eventid}")
	public ModelAndView  DLDT1DAY(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("dragontigeroneday");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			   model.setViewName("adminlogin");
		}
		

		return model;
	} 
	

	
	@ResponseBody
	@RequestMapping(value="/casino/VAAA/{eventid}")
	public ModelAndView  VAAA(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vaaa");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
		
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/DLAAA/{eventid}")
	public ModelAndView  DLAAA(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("aaa");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
		
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/VBWD/{eventid}")
	public ModelAndView  VBWD(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vbbtable");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
		
		
		
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/DLBWD/{eventid}")
	public ModelAndView  DLBWD(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("bwtable");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/DLPOKER20/{eventid}")
	public ModelAndView  DLPOKER20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("poker20");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
	} 
	
	@ResponseBody
	@RequestMapping(value="/casino/DLAB20/{eventid}")
	public ModelAndView  DLAB20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("andarbahar");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
	} 
	
	
	@ResponseBody
	@RequestMapping(value="/casino/DLPOKER1DAY/{eventid}")
	public ModelAndView  DLPOKER1DAY(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("pokeroneday");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model; 
	} 
	 
	 
	@ResponseBody
	@RequestMapping(value="/casino/VDTL20/{eventid}")
	public ModelAndView  VDTL20(@PathVariable("eventid")Integer eventid){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("vdragontigerlion");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@ResponseBody
	@RequestMapping(value="/addAccountNew")
	public ModelAndView  addAccountNew(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null) 
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				if(user.getUsertype() !=3){
					
					if( user.getUsertype() == 4){

						model.setViewName("addapplication");
					}else if( user.getUsertype() == 0){

						model.setViewName("addUserNew");
					}
					else if( user.getUsertype() == 1)
					{
						model.setViewName("addUserNew");
					}
					else if( user.getUsertype() == 2)
					{
						model.setViewName("addUserNew");
					}
					else if( user.getUsertype() == 4)
					{
						model.setViewName("addUserNew");
					}
					else if( user.getUsertype() == 5)
					{
						model.setViewName("addUserNew");
					}
					else if( user.getUsertype() == 6)
					{
						model.setViewName("addUserNew");
					} 
			  }	
			  else
				{
				session.removeAttribute("user");
				model.setViewName("adminlogin");
				}
			}else {
				model.setViewName("chnagePassword");
			}
		  
		
		}
		else
		{
			model.setViewName("adminlogin");
		}

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/lotvTv2")
	public ModelAndView  lotvTv2(){  

		ModelAndView model = new ModelAndView();
		/*
		 * HttpSession session = request.getSession(true); EXUser user =(EXUser)
		 * session.getAttribute("user"); if(user!=null && user.getUsertype()!=3) {
		 * if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
		 * model.setViewName("tv"); }else { model.setViewName("changePassword"); }
		 * 
		 * }else{ model.setViewName("adminlogin"); }
		 */
		
		model.setViewName("tv");

		return model;
	} 
	
	@ResponseBody
	@RequestMapping(value="/lenadena")
	public ModelAndView  lenadena(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("lenadena");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	} 
	
	@RequestMapping(value="/casinoresult")
	public ModelAndView  casinoresult(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null   && user.getUsertype()!=3)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("casinoresult");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
	
	@RequestMapping(value="/casinoProfitLoss")
	public ModelAndView  casinoProfitLoss(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("casinoprofitloss");	
					
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");
		}
		return model;
	}
	
	@RequestMapping(value="/matchMessage")
	public ModelAndView  matchMessage(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("matchMessage");	
					
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");
		}
		return model;
	}
	
	
	@RequestMapping(value="/closedaccount")
	public ModelAndView  closedAccount(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("closedaccount");	
					
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");
		}
		return model;
	}
	
	 
	@RequestMapping(value="/casinoPnL")
	public ModelAndView  casinoPnL(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");
		if(user!=null  && user.getUsertype()!=3){
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
					model.setViewName("casinoPnL");	
					
			}else {
				model.setViewName("changePassword");	
				
			}
		}else{
			model.setViewName("adminlogin");
		}
		return model;
	}
	
	
	@RequestMapping(value="/websoket")
	public ModelAndView  websoket(){  

		ModelAndView model = new ModelAndView();
			model.setViewName("websoket");	
					
			
		
		return model;
	}
	
	@RequestMapping(value="/democasino")
	public ModelAndView  democasino(){  

		ModelAndView model = new ModelAndView();
			model.setViewName("democasino");	
					
			
		
		return model;
	}
	
	@RequestMapping(value="/userBetCount")
	public ModelAndView  userBetCount(){  

		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession(true);
		EXUser user =(EXUser) session.getAttribute("user");

		if(user!=null   && user.getUsertype()==4)
		{
			if(checkPasswordType(user.getUserid()).equalsIgnoreCase("new")) {
				model.setViewName("userBetCounts");
			}else {
				model.setViewName("changePassword");
			}
			
		}else{
			model.setViewName("adminlogin");
		}
		

		return model;
	}
}
