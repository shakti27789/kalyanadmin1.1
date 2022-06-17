package com.exchange.api.bean;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
@ControllerAdvice
public class CasinoApi {
	@RequestMapping(value ="/listEventTypesCasino",method = RequestMethod.GET,produces= "application/json")
	public ResponseEntity<Object>  listEventTypes(){
		
		   ArrayList< HashMap<String,Object>> list =new  ArrayList< HashMap<String,Object>>();
		   HashMap<String,Object> lucky7 = new HashMap<String,Object>();
		   lucky7.put("id", "501");
		   lucky7.put("name", "Lucky7");
		   
		   
		   HashMap<String,Object> roulette = new HashMap<String,Object>();
		   roulette.put("id", "506");
		   roulette.put("name", "ROULETTE");
		
		  
		   HashMap<String,Object> dtiger = new HashMap<String,Object>();
		   dtiger.put("id", "503");
		   dtiger.put("name", "Dragon Tiger");
		   
		   HashMap<String,Object> teenpatti = new HashMap<String,Object>();
		   teenpatti.put("id", "504");
		   teenpatti.put("name", "TeenPatti");
		   
		   HashMap<String,Object> andarbahar = new HashMap<String,Object>();
		   andarbahar.put("id", "505");
		   andarbahar.put("name", "Andar Bahar");
		   
		  
		   HashMap<String,Object> bwcasino = new HashMap<String,Object>();
		   bwcasino.put("id", "502");
		   bwcasino.put("name", "Bollywood Casino");
		   
		   HashMap<String,Object>  eventTypelucky7 =new  HashMap<String,Object> ();
		   eventTypelucky7.put("eventType", lucky7);
		 
		   HashMap<String,Object>  eventTypedtiger =new  HashMap<String,Object> ();
		   eventTypedtiger.put("eventType",dtiger );
		   
		   HashMap<String,Object>  eventTypeteenpatti =new  HashMap<String,Object> ();
		   eventTypeteenpatti.put("eventType", teenpatti);
		   
		   HashMap<String,Object>  eventTypeandarbahar =new  HashMap<String,Object> ();
		   eventTypeandarbahar.put("eventType", andarbahar);

		   HashMap<String,Object>  eventTypebwcasino =new  HashMap<String,Object> ();
		   eventTypebwcasino.put("eventType", bwcasino);
		   
		   HashMap<String,Object>  eventTroulettecasino =new  HashMap<String,Object> ();
		   eventTroulettecasino.put("eventType", roulette);
		  
		   list.add(eventTypelucky7);
		  // list.add(eventTypebwcasino);
		   list.add(eventTypedtiger);
		   list.add(eventTypeteenpatti);
		   list.add(eventTypeandarbahar);
		   list.add(eventTypebwcasino);
		   list.add(eventTroulettecasino);
		   
		   return new ResponseEntity<Object>(list,HttpStatus.OK);

	}
	
	@RequestMapping(value ="/listEventsCasino/{sportId}",method = RequestMethod.GET,produces= "application/json")
	public ResponseEntity<Object>  listEvents(@PathVariable String sportId){
		
		ArrayList< HashMap<String,Object>> list =new  ArrayList< HashMap<String,Object>>();
		
		
		HashMap<String,Object> obj = new HashMap<String,Object>();
		if(sportId.equalsIgnoreCase("501")) {
			
			
			  HashMap<String,Object> lucky7 = new HashMap<String,Object>();
				
			  lucky7.put("id", "5011");
			  lucky7.put("name", "VLucky7");
			  
			  HashMap<String,Object>  eventTypelucky7 =new  HashMap<String,Object> ();
			  eventTypelucky7.put("event", lucky7);
			  
			  list.add(eventTypelucky7);
			   
		}else if(sportId.equalsIgnoreCase("506")) {
			
			
			  HashMap<String,Object> roulette = new HashMap<String,Object>();
				
			  roulette.put("id", "5061");
			  roulette.put("name", "VRoulette");
			  
			  HashMap<String,Object>  eventTypevroulette =new  HashMap<String,Object> ();
			  eventTypevroulette.put("event", roulette);
			  
			  list.add(eventTypevroulette);
			   
		}else if(sportId.equalsIgnoreCase("502")) {
			
			   HashMap<String,Object> aaa = new HashMap<String,Object>();
			
			   aaa.put("id", "5021");
			   aaa.put("name", "Amar Akbar Anthony");
			   
			   HashMap<String,Object> bwtable = new HashMap<String,Object>();
			   bwtable.put("id", "5022");
			   bwtable.put("name", "Bollywood Table");
			   
			   
			   HashMap<String,Object>  eventTypeaaa =new  HashMap<String,Object> ();
			   eventTypeaaa.put("event", aaa);
			   
			   HashMap<String,Object>  eventTypebwtable =new  HashMap<String,Object> ();
			   eventTypebwtable.put("event", bwtable);
			   
			   list.add(eventTypeaaa);
			   list.add(eventTypebwtable);
				
			   
		}else if(sportId.equalsIgnoreCase("503")) {
			
			
			   HashMap<String,Object> dtiger = new HashMap<String,Object>();
				
			   dtiger.put("id", "5031");
			   dtiger.put("name", "Dragon Tiger");
			   
			   HashMap<String,Object> dtigerline = new HashMap<String,Object>();
			   dtigerline.put("id", "5032");
			   dtigerline.put("name", "Dragon Tiger Lion ");
			   
			   
			   HashMap<String,Object>  eventTypedtiger =new  HashMap<String,Object> ();
			   eventTypedtiger.put("event", dtiger);
			   
			   HashMap<String,Object>  eventTypedtigerline =new  HashMap<String,Object> ();
			   eventTypedtigerline.put("event", dtigerline);
			   
			   list.add(eventTypedtiger);
			   list.add(eventTypedtigerline);
		}
		
		 else if(sportId.equalsIgnoreCase("504")) {
			
			
			   HashMap<String,Object> vteenpatti20 = new HashMap<String,Object>();
				
			   vteenpatti20.put("id", "5041");
			   vteenpatti20.put("name", "VTeenPatti20");
			   
			   HashMap<String,Object> teenpattiopen = new HashMap<String,Object>();
			   
			   teenpattiopen.put("id", "5042");
			   teenpattiopen.put("name", "VOpenTeenPatti");
			   
			   HashMap<String,Object> dteenpatti20 = new HashMap<String,Object>();
			   
			   
			   dteenpatti20.put("id", "5046");
			   dteenpatti20.put("name", "TeenPatti20");
			   
			   
			   HashMap<String,Object>  eventTypvteenpatti20 =new  HashMap<String,Object> ();
			   eventTypvteenpatti20.put("event", vteenpatti20);
			   
			   HashMap<String,Object>  eventTypeteenpattiopen =new  HashMap<String,Object> ();
			   eventTypeteenpattiopen.put("event", teenpattiopen);
			   
			   HashMap<String,Object>  eventTypdteenpatti20 =new  HashMap<String,Object> ();
			   eventTypdteenpatti20.put("event", dteenpatti20);
			   
			   list.add(eventTypvteenpatti20);
			   list.add(eventTypeteenpattiopen);
			   list.add(eventTypdteenpatti20);
			   
		}else if(sportId.equalsIgnoreCase("505")) {
			
			
			  HashMap<String,Object> andarbahar = new HashMap<String,Object>();
				
			  andarbahar.put("id", "5051");
			  andarbahar.put("name", "Andar Bahar");
			  
			  HashMap<String,Object>  eventTypeandarbahar =new  HashMap<String,Object> ();
			  eventTypeandarbahar.put("event", andarbahar);
			  
			  list.add(eventTypeandarbahar);
			   
		}
		
		  
		   
		   
		   return new ResponseEntity<Object>(list,HttpStatus.OK);
	}
}
