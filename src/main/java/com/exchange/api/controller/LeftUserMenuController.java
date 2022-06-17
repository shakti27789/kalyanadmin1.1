package com.exchange.api.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.EXMatchMessage;
import com.exchange.api.bean.EXUser;
import com.exchange.api.bean.Event;
import com.exchange.api.bean.Message;
import com.exchange.api.bean.ResponseBean;
import com.exchange.api.repositiry.EXMatchMessageRepository;
import com.exchange.api.repositiry.EventRepository;
import com.exchange.api.repositiry.MessageRepository;
import com.exchange.api.repositiry.SeriesRepository;
import com.exchange.util.EXDateUtil;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class LeftUserMenuController {
	
	@Autowired
	SeriesRepository seriesRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EXMatchMessageRepository matchMessageRepo;

	
	@RequestMapping(value = "/getEventBySport",method = RequestMethod.GET)
	public ResponseEntity<Object> getSeriesBySport(@RequestParam(name = "sportid",required = true) Integer sportid, @RequestParam(name = "status",required = true) Boolean status){
		ArrayList<Event> list = (ArrayList<Event>) eventRepository.findBySportidAndStatus(sportid, status);
		if(list.size()>0)
		{
			
			return new ResponseEntity<Object>(list,HttpStatus.OK);		
		}
		else
		{
			return new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT);		
		}


	}
	
	
	
	@RequestMapping(value = "/setMessage",method = RequestMethod.POST)
	public ResponseEntity<Object> setMessage(@RequestBody Message message){
		ResponseBean responseBean = new ResponseBean();
		EXDateUtil dtUtil = new EXDateUtil();
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session .getAttribute("user");
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Message bean = null;
		try{
			if(usersession!=null){
				if(message.getMsgtype()!=null &&  !message.getMsgtype().equalsIgnoreCase("-1")){
					if(message.getMsgtype().equalsIgnoreCase("O")){
						 bean =	messageRepository.findByMsgtypeAndMsgid(message.getMsgtype(), "2");
					}else if(message.getMsgtype().equalsIgnoreCase("C")){
						 bean=	messageRepository.findByMsgtypeAndMsgid(message.getMsgtype(), "1");
					}
					if(bean!=null){
						bean.setDate(dtUtil.convTimeZone2(dateFormater.format(new Date()), timeZone.getID(), "GMT"));
						bean.setMessage(message.getMessage());
						messageRepository.save(bean);
						responseBean.setType("success");
						responseBean.setMessage("Message Saved Successfully");
						responseBean.setTitle("success");
						return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
					}
				}else{
					responseBean.setType("error");
					responseBean.setMessage("Please Enter Message");
					responseBean.setTitle("Oops..");
					return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
				}
				

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/getMessage",method = RequestMethod.GET)
	public ResponseEntity<Object> getMessage(@RequestParam String msgtype){
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session.getAttribute("user");
		Message bean = null;
		if(usersession!=null){
			if(msgtype.equalsIgnoreCase("O")){
				 bean =	messageRepository.findByMsgtypeAndMsgid(msgtype, "2");
			}else if(msgtype.equalsIgnoreCase("C")){
				 bean=	messageRepository.findByMsgtypeAndMsgid(msgtype, "1");
			}
			if(bean!=null)
			{	
				return new ResponseEntity<Object>(bean,HttpStatus.OK);		
			}
			else
			{
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);		
			}			
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);		
	}
	
	
	@RequestMapping(value = "/getSportLength",method = RequestMethod.GET)
	public ResponseEntity<Object> getSportLength(){
		ArrayList<Event> cricketlist = (ArrayList<Event>) eventRepository.findBySportidAndStatus(4, true);
		ArrayList<Event> tennislist = (ArrayList<Event>) eventRepository.findBySportidAndStatus(2, true);
		ArrayList<Event> soccerlist = (ArrayList<Event>) eventRepository.findBySportidAndStatus(1, true);
		LinkedHashMap<String, Object> hm= new LinkedHashMap<String, Object>();
		hm.put("cricket",cricketlist.size());
		hm.put("tennis", tennislist.size());
		hm.put("soccer",soccerlist.size());
		hm.put("status", "Success");

		return new ResponseEntity<Object>(hm,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/setMatchMessage",method = RequestMethod.POST)
	public ResponseEntity<Object> setMatchMessage(@RequestBody EXMatchMessage message){
		ResponseBean responseBean = new ResponseBean();
		HttpSession session = request.getSession();
		EXUser usersession = (EXUser) session .getAttribute("user");
		try{
			if(usersession!=null){
				if(message.getMatchid()!=-1 && message.getMessage() !=null){
					
					ArrayList<EXMatchMessage> list = matchMessageRepo.findByMatchid(message.getMatchid());
						 
						 for(EXMatchMessage bean : list ) {
							 bean.setIsactive(false);
							 matchMessageRepo.save(bean);
						 }
						 message.setCreatedon(new Date());
						 message.setIsactive(true);
						 matchMessageRepo.save(message);
						 
					    responseBean.setType("success");
						responseBean.setMessage("Message Saved Successfully");
						responseBean.setTitle("success");
						return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
				}else{
					responseBean.setType("error");
					responseBean.setMessage("Please Enter Message");
					responseBean.setTitle("Oops..");
					return new ResponseEntity<Object>(responseBean,HttpStatus.OK);
				}
				

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		responseBean.setType("error");
		responseBean.setMessage("Something went wrong");
		responseBean.setTitle("Oops..");
		return new ResponseEntity<Object>(responseBean,HttpStatus.BAD_REQUEST);
	}
	
}
