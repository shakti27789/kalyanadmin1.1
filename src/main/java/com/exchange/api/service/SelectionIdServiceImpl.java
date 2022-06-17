package com.exchange.api.service;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.SelectionIds;
import com.exchange.api.repositiry.SelectionIdRepository;
import com.exchange.util.EXConstants;
import com.exchange.util.EXGlobalConstantProperties;

@Service("SelectionIdService")
public class SelectionIdServiceImpl implements SelectionIdService{
	
	@Autowired
	SelectionIdRepository selectionRepo;
	
	@Autowired
	EXGlobalConstantProperties exglobalConst;
	

	
	@Override
	public ArrayList<SelectionIds> getSelectionsList(String marketid) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		String Stresponse = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"/marketRunnerKings/"+marketid, String.class);
		//String Stresponse = restTemplate.getForObject("http://104.248.161.200/betfairapi/matchapi.php?Action=listMarketRunner&MarketID=1.152894233", String.class);
		
		ArrayList<SelectionIds> selectionList = new ArrayList<>();
		try{
			JSONArray jArray = new JSONArray(Stresponse);
			JSONArray runners = jArray.getJSONObject(0).getJSONArray("runners");
			
			for(int i = 0; i<runners.length(); i++) {
				SelectionIds Ids = new SelectionIds();
				Ids.setMarketid(jArray.getJSONObject(0).getString("marketId"));
				Ids.setSelectionid(runners.getJSONObject(i).getInt("selectionId"));
				Ids.setRunnerName(runners.getJSONObject(i).getString("runnerName"));
				selectionList.add(Ids);
			}
			
            
		}catch (Exception e) {
			e.printStackTrace();
		}
		return selectionList;
	}

	@Override
	public ArrayList<SelectionIds> SaveSelectionId(String marketid,String eventid) {
		// TODO Auto-generated method stub
		RestTemplate restTemplate = new RestTemplate();
		
		//restTemplate.getForObject("http://3.7.102.54/addMarketShaktiApi/"+marketid, String.class);
	//	String Stresponse = restTemplate.getForObject(exglobalConst.getDeveloperbetfair()+"/listMarketRunner/"+marketid, String.class);
		String apiurl = restTemplate.getForObject(EXConstants.developerApi, String.class);
		String Stresponse = restTemplate.getForObject(apiurl.substring(1, apiurl.length() - 1)+"listMarketRunner&MarketID="+marketid, String.class);
	       
		ArrayList<SelectionIds> selectionList = new ArrayList<>();
		try{
			
			JSONObject jsonObj = new JSONObject(new JSONArray(Stresponse).get(0).toString());
			
			
			JSONArray runners = jsonObj.getJSONArray("runners");
			for(int i = 0; i<runners.length(); i++) {
				SelectionIds Ids = null;
				
					Ids = new SelectionIds();
					Ids.setMarketid(jsonObj.getString("marketId"));
					Ids.setSelectionid(runners.getJSONObject(i).getInt("selectionId"));
					Ids.setRunnerName(runners.getJSONObject(i).getString("runnerName"));
					Ids.setCreatedon(new Date());
					selectionList.add(Ids);
			 
				
				/*	SelectionIds Idsbook=new SelectionIds();
					Idsbook.setMarketid(jsonObj.getString("marketId")+"BM");
					Idsbook.setSelectionid(i+1);
					Idsbook.setRunnerName(runners.getJSONObject(i).getString("runnerName"));
					selectionList.add(Idsbook);
					*/
			}
			
            
			
                if(selectionList.size()>0) {
                	if(selectionRepo.saveAll(selectionList)!=null){
                   	 return selectionList;
                		
                	}
            	 }else {
            		 return selectionList;
            	 }
			
			
		/*	JSONArray jArray = new JSONArray(Stresponse);
			
			JSONArray runners = jArray.getJSONObject(0).getJSONArray("runners");
			for(int i = 0; i<runners.length(); i++) {
				SelectionIds Ids = null;
				
					Ids = new SelectionIds();
					Ids.setMarketid(jArray.getJSONObject(0).getString("marketId"));
					Ids.setSelectionid(runners.getJSONObject(i).getInt("selectionId"));
					Ids.setRunnerName(runners.getJSONObject(i).getString("runnerName"));
					selectionList.add(Ids);
			 
				
				
			}
			
            
                if(selectionList.size()>0) {
                	if(selectionRepo.saveAll(selectionList)!=null){
                   	 return selectionList;
                		
                	}
            	 }else {
            		 return selectionList;
            	 }*/
                
            
		}catch (Exception e) {
			e.printStackTrace();
		}
		return selectionList;
	
	}

}
