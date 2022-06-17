package com.exchange.api.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.exchange.api.bean.Market;
import com.exchange.api.repositiry.SelectionIdRepository;

public class PlaceBetService {
	
	
	@Autowired
	static
	SelectionIdRepository selectionIdRepository;


	public static Double getLotusBets(Boolean isBack,Double price,int selectionid,String uri,Double range, String Bhaotype)
	{
		Double isMatched = 1.0;
		
	    RestTemplate restTemplate = new RestTemplate();
	    String Stresponse = restTemplate.getForObject(uri, String.class);
	    
		try{
			JSONObject response=  new JSONObject(Stresponse);
			JSONArray result = response.getJSONArray("result");
			for(int i = 0;i<result.length();i++){
				if(result.getJSONObject(i).getString("name").equals("Match Odds")){
					JSONArray runners = result.getJSONObject(i).getJSONArray("runners");
					for(int j=0;j<runners.length();j++){
						if(Integer.valueOf(runners.getJSONObject(j).getString("id")) == selectionid){
							if(isBack.equals(true)){
								JSONArray back = runners.getJSONObject(j).getJSONArray("back");
								BigDecimal bBigDecimal = new BigDecimal("0.01");
								BigDecimal fixedval = new BigDecimal("0.10");
								BigDecimal backsize = new BigDecimal(back.getJSONObject(0).getString("price"));
								if(backsize.compareTo(fixedval)==1 && Bhaotype.equalsIgnoreCase("2")){
									backsize = backsize.subtract(bBigDecimal);
								}
									if(Double.valueOf(backsize.doubleValue()) >= price&&Double.valueOf(backsize.doubleValue()) <= price+range){
										return backsize.doubleValue();
									}else{
										return 1.0;
									}
								
							}else{
								JSONArray lay = runners.getJSONObject(j).getJSONArray("lay");
									if(Double.valueOf(lay.getJSONObject(0).getString("price")) <= price&&Double.valueOf(lay.getJSONObject(0).getString("price")) >= price-range){
										return Double.valueOf(lay.getJSONObject(0).getString("price"));
									}else{
										return 1.0;
									}
								
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	    return isMatched;
	}
	
	
	public static Double getBetfairBets(Boolean isBack,Double price,int selectionids,String uri,Double range, String Bhaotype,Market market)
	{
		
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		 
		SSLContext sslContext = null;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom()
			        .loadTrustMaterial(null, acceptingTrustStrategy)
			        .build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		 
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setSSLSocketFactory(csf)
		        .build();
		 
		HttpComponentsClientHttpRequestFactory requestFactory =
		        new HttpComponentsClientHttpRequestFactory();
		 
		requestFactory.setHttpClient(httpClient);
		 
		RestTemplate restTemplate = new RestTemplate(requestFactory);
	    String Stresponse = restTemplate.getForObject(uri, String.class);
	    
		try{
		
			
			String sport = null;
			if(market.getSportid()==4){
				sport = "Cricket";
			}else if(market.getSportid()==2){
				sport = "Tennis";
			}else if(market.getSportid()==1){
				sport = "Football";
			}
			
			String json  = new String(Files.readAllBytes(Paths.get(sport+"/"+market.getEventid()+".json")), StandardCharsets.UTF_8);
			
			JSONArray response = new JSONArray(json.toString());
			JSONArray result = new JSONArray();
			
			if(Bhaotype.equalsIgnoreCase("2")){

				
            	for(int i = 0;i<response.length();i++){
					JSONObject odds = new JSONObject();	
					odds.put("id", response.getJSONObject(i).getString("marketId"));
					odds.put("name", "Match Odds");
					odds.put("betfair", true);
					JSONArray runner = new JSONArray();
					BigDecimal back1= new BigDecimal("0.0");
					BigDecimal back2 =new BigDecimal("0.0");
					BigDecimal back3 =new BigDecimal("0.0");
					BigDecimal lay1= new BigDecimal("0.0");
					BigDecimal lay2=new BigDecimal("0.0");
					BigDecimal lay3=new BigDecimal("0.0");
					BigDecimal team1 =new BigDecimal("0.0");
					BigDecimal team2 =new BigDecimal("0.0");
					BigDecimal team3 =new BigDecimal("0.0");
					BigDecimal back4 =new BigDecimal("0.0");
					JSONArray runners1 =null;
					JSONArray backrunner1 =null;
					JSONArray layrunner1 =null;
					JSONArray back =null;

					JSONArray runners = response.getJSONObject(i).getJSONArray("runners");
					for(int j=0;j<runners.length();j++){
						back = new JSONArray();
						JSONArray lay = new JSONArray();
						JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");
						if(ex.getJSONArray("availableToBack").length()!=0 && ex.getJSONArray("availableToBack")!=null){
							JSONArray availableBack = ex.getJSONArray("availableToBack");            		
							;            		
							String bhaotype = "0.0"+Bhaotype;
							for(int b=0;b<availableBack.length();b++){
								JSONObject backObject = new JSONObject();
								BigDecimal bBigDecimal = new BigDecimal("0.01");
								BigDecimal fixedval = new BigDecimal("0.10");
								BigDecimal backsize = new BigDecimal(availableBack.getJSONObject(b).getString("price"));
								if(j==0){
									if(b == 0){
										back1 =backsize;
									}
								}
								else if(j==1){
									if(b == 0){
										back2 =backsize;
									}
								}
								else if(j==2){
									if(b == 0){
										back3 =backsize;
									}
								}

								backObject.put("price", backsize);
								backObject.put("size", availableBack.getJSONObject(b).get("size"));
								back.put(backObject);
							}
						}

						if(ex.getJSONArray("availableToLay").length()!=0 && ex.getJSONArray("availableToLay")!=null){
							JSONArray availableLay = ex.getJSONArray("availableToLay");

							for(int l = 0;l<availableLay.length();l++){
								JSONObject layObject = new JSONObject();
								layObject.put("price", availableLay.getJSONObject(l).get("price")); 
								layObject.put("size", availableLay.getJSONObject(l).get("size"));
								BigDecimal laysize = new BigDecimal(availableLay.getJSONObject(l).getString("price"));
								if(j==0){
									if(l == 0){
										lay1 =laysize;
									}
								}
								else if(j==1){
									if(l == 0){
										lay2 =laysize;
									}
								}

								else if(j==2){
									if(l == 0){
										lay3 =laysize;
									}
								}

								lay.put(layObject);
							}            		
						}

						if(j==0){
							team1 = back1.add(lay1);

						}

						if(j==1){
							team2 = back2.add(lay2);

						}

						if(j==2){
							team3 = back3.add(lay3);

						}

						JSONObject selectionid = new JSONObject();
						selectionid.put("id", runners.getJSONObject(j).getString("selectionId"));
						selectionid.put("name", selectionIdRepository.findBySelectionidAndMarketid(Integer.parseInt(runners.getJSONObject(j).getString("selectionId")),market.getMarketid()).getRunnerName());
						selectionid.put("back", back);
						selectionid.put("lay",lay);
						runner.put(selectionid);
					}
					BigDecimal back5 = new BigDecimal("0.0");
					BigDecimal back6 = new BigDecimal("0.0");
				
					if(team2.compareTo(team1)==1 && team2.compareTo(team3)==1){

						back4 =back1.subtract(new BigDecimal("0.01"));

					}
					else if(team1.compareTo(team2) ==1 && team1.compareTo(team3)==1){
						back5 =back2.subtract(new BigDecimal("0.01"));
					}
					else {

						back6 =back3.subtract(new BigDecimal("0.01"));
					}

					
					for(int k =0; k<runner.length();k++){
						if(back4.compareTo(new BigDecimal("0.0")) == 1){

							if(k==0){

								runners1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
								for(int m =0; m< runners1.length(); m++){
									if(m==0){
										runner.getJSONObject(k).getJSONArray("back").getJSONObject(m).put("price", back4);
									}
								}
							}
							else{
								backrunner1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
								layrunner1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToLay");
								for(int m =0; m< backrunner1.length(); m++){
									if(m==1){

										runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", backrunner1.getJSONObject(m).getString("price"));

									}
								}	

								for(int m =0; m< layrunner1.length(); m++){
									if(m==1){

										runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", layrunner1.getJSONObject(m).getString("price"));

									}
								}	

							}
							

						}else{
							if(k==1){

								runners1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
								for(int m =0; m< runners1.length(); m++){
									if(m==0){
										
										if(back5.compareTo(new BigDecimal("0.0")) ==1){
											runners1.getJSONObject(m).put("price", back5);
											runner.getJSONObject(k).getJSONArray("back").getJSONObject(m).put("price", back5);
										}
										
									}

								}							    

							}else{
								backrunner1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToBack");
								layrunner1= runners.getJSONObject(k).getJSONObject("ex").getJSONArray("availableToLay");
								for(int m =0; m< backrunner1.length(); m++){
									if(m==1){

										runner.getJSONObject(k).getJSONArray("back").getJSONObject(0).put("price", backrunner1.getJSONObject(m).getString("price"));
						}
								}	

								for(int m =0; m< layrunner1.length(); m++){
									if(m==1){

										runner.getJSONObject(k).getJSONArray("lay").getJSONObject(0).put("price", layrunner1.getJSONObject(m).getString("price"));

									}
								}	
							}

						}

					}
					
					JSONObject event = new JSONObject();
					event.put("name", market.getMatchname());
					event.put("id", market.getEventid());
					odds.put("event",event);
					odds.put("runners", runner);
					result.put(odds);
					
				}	
            
			}
			
		//	JSONObject jObj  = new JSONObject(Stresponse);
			//JSONArray response=  jObj.getJSONArray("cricket");
            
            for (int i = 0;i<result.length();i++){
            	
            	JSONArray runners = result.getJSONObject(i).getJSONArray("runners");            		
                
            	for(int j=0;j<runners.length();j++){
            		if(runners.getJSONObject(j).getInt("selectionId")==selectionids){
    	        		//JSONObject ex = runners.getJSONObject(j).getJSONObject("ex");

    	        		if(isBack==true&&runners.getJSONObject(j).getJSONArray("back").length()!=0 && runners.getJSONObject(j).getJSONArray("back")!=null){
    	            		JSONArray availableBack = runners.getJSONObject(j).getJSONArray("back");  
    	            		BigDecimal bBigDecimal = new BigDecimal("0.01");
							BigDecimal fixedval = new BigDecimal("0.10");
							BigDecimal backsize = new BigDecimal(availableBack.getJSONObject(0).getString("price"));
							if(backsize.compareTo(fixedval)==1 && Bhaotype.equalsIgnoreCase("2")){
								backsize = backsize.subtract(bBigDecimal);
							}
							//if(Double.valueOf(backsize.doubleValue()) >= price&&(Double.valueOf(backsize.doubleValue()-range) <= price || Double.valueOf(backsize.doubleValue()+range) <= price)){
							if(Double.valueOf(backsize.doubleValue()-range) <= price && Double.valueOf(backsize.doubleValue()+range) >= price){
								return backsize.doubleValue();
							}else{
								return 1.0;
							}

    	            	}
    	        		
    	            	if(isBack==false&&runners.getJSONObject(j).getJSONArray("lay").length()!=0 && runners.getJSONObject(j).getJSONArray("lay")!=null){
    	            		JSONArray availableLay = runners.getJSONObject(j).getJSONArray("lay");
							//if(Double.valueOf(availableLay.getJSONObject(0).getString("price")) <= price&&Double.valueOf(availableLay.getJSONObject(0).getString("price")+range) >= price){
    	            		if(Double.valueOf(availableLay.getJSONObject(0).getString("price"))+range >= price && Double.valueOf(availableLay.getJSONObject(0).getString("price"))-range <= price){
								return Double.valueOf(availableLay.getJSONObject(0).getString("price"));
							}else{
								return 1.0;
							}

    	            	}

            		}
            	}
        }
		}catch(Exception e){
			e.printStackTrace();
		}
	    return 1.0;
	}
}
