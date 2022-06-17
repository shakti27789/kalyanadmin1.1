package com.exchange.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.api.bean.EXChipDetail;
import com.exchange.api.bean.EXChipDetailMongo;
import com.exchange.api.bean.EXUser;
import com.exchange.api.dao.EXNativeQueryDao;
import com.exchange.api.repositiry.EXChipDetailRepository;
import com.exchange.api.repositiry.EXChipDetailsMongoCustomRepository;
import com.exchange.api.repositiry.EXChipDetailsMongoRepository;

@ControllerAdvice
@RestController
@CrossOrigin
@RequestMapping("/api")
public class EXChipDetailController {

	@Autowired
	EXChipDetailRepository chipRepo;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EXNativeQueryDao nativeQueryDao;
	
	@Autowired
	EXChipDetailsMongoCustomRepository eXChipDetailsMongoCustomRepository;
	
	@Autowired
	EXChipDetailsMongoRepository chipmongo;
	
   
	
	private static org.apache.log4j.Logger log = Logger.getLogger(EXChipDetailController.class);
	
	@RequestMapping(value = "/chipData", method = RequestMethod.GET)
	public ResponseEntity<Object> chipData(){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		EXChipDetail chipdtl = new EXChipDetail();
		ArrayList<EXChipDetail>list =chipRepo.findByUserIdOrderByCreatedOnAsc("apple");
		if(list.size()>0){
			 return new ResponseEntity<Object>(list,HttpStatus.OK);
		}else{
			 return new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT);
		}
	   
	}
	
	@RequestMapping(value = "/getStatementByUser/{userId}/{startDate}/{endDate}/{reportType}/{childid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStatementByUser(@PathVariable("userId") String userId,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,@PathVariable("reportType") String reportType,@PathVariable String childid){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		EXChipDetail chipdtl = new EXChipDetail();
		if(childid !=null) {
			if(childid.equalsIgnoreCase("-1")) {
				childid = userId;
			}
		}
		ArrayList<EXChipDetail>list =nativeQueryDao.getStatement(childid, startDate, endDate,reportType);
		if(list.size()>0){
			 return new ResponseEntity<Object>(list,HttpStatus.OK);
		}else{
			 return new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT);
		}
	   
	}
	
	@RequestMapping(value = "/getStatementSettlement/{userId}/{startDate}/{endDate}/{childid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStatementSettlement(@PathVariable("userId") String userId,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,@PathVariable String childid){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		EXChipDetail chipdtl = new EXChipDetail();
		if(childid !=null) {
			if(childid.equalsIgnoreCase("-1")) {
				childid = userId;
			}
		}
		ArrayList<EXChipDetail>list =nativeQueryDao.getStatementSettlement(childid, startDate, endDate);
		if(list.size()>0){
			 return new ResponseEntity<Object>(list,HttpStatus.OK);
		}else{
			 return new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT);
		}
	   
	}
	
	@RequestMapping(value = "/getStatementByUserMQuery/{userId}/{startDate}/{endDate}/{reportType}/{childid}/{pageNo}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStatementByUserMQuery(@PathVariable("userId") String userId,@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate,@PathVariable("reportType") String reportType,@PathVariable String childid,@PathVariable Integer pageNo){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		if(usersession != null) {
			if(childid !=null) {
				if(childid.equalsIgnoreCase("-1")) {
					childid = userId;
				}
			}
			long count = eXChipDetailsMongoCustomRepository.getStatementMCount(childid, startDate, endDate,reportType);
			int i = (int) count;

			  int nOfPages = i / 500;
			  
			  if (nOfPages % 500 > 0) {

		            nOfPages++;
		        }
			  
			List<EXChipDetailMongo>list =eXChipDetailsMongoCustomRepository.getStatementM(childid, startDate, endDate,reportType,pageNo);
			
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.put("data", list);
			hm.put("totalpage", nOfPages);
			hm.put("pageNo", pageNo+1);
			
			if(list.size()>0){
				 return new ResponseEntity<Object>(hm,HttpStatus.OK);
			}else{
				 return new ResponseEntity<Object>(hm,HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/getStatementSettlementMQuery/{userId}/{startDate}/{endDate}/{childid}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStatementSettlementMQuery(@PathVariable("userId") String userId,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,@PathVariable String childid){
		
		HttpSession session = request.getSession(true);
		EXUser usersession = (EXUser) session.getAttribute("user");
		
		if(usersession != null) {
			if(childid !=null) {
				if(childid.equalsIgnoreCase("-1")) {
					childid = userId;
				}
			}
			List<EXChipDetailMongo> list =eXChipDetailsMongoCustomRepository.getStatementSettlementM(childid, startDate, endDate);
			if(list.size()>0){
				 return new ResponseEntity<Object>(list,HttpStatus.OK);
			}else{
				 return new ResponseEntity<Object>(list,HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(value = "/getStatementBalance/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getStatementSettlementMQuery(@PathVariable("userId") String userId){
		
		
		ArrayList<EXChipDetailMongo> list = chipmongo.findByUserIdAndType(userId, "balance");
		
		double credit =0;
		double debit =0;
		for(EXChipDetailMongo bean :list) {
			credit = credit+bean.getDebit();
			debit = debit+bean.getCredit();
		}
		System.out.println(credit);
		System.out.println(debit);
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	
	
}
	

