
  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


 <script id="oddshandler" type="text/x-handlebars-template">

<div class="col-md-12">
   </div>
 <marquee width="100%"style="color: red"; id="messge_text11" direction="left" >
  {{data1.matchMessage.message}}</marquee>
                </div>
</div>
{{#each data1.Odds}}

{{#if_neq type "Fancy"}}  
 <div class="col-md-12">
<input type="hidden" value="{{marketId}}" class="marketIds">   
   <input type="hidden" value="{{marketIdWithOutDecimal}}" class="marketIdWithOutDecimal">                 
  <div class="market-title">
                           {{#if_eq marketName "Match Odds"}}  
							<span>{{matchName}}  </span>
                            <span> {{marketName}} </span> 
                            <span>{{date}} </span>
							<span>
						{{#if_neq ${user.usertype} 6}}
							<button class="tv-button" style="background-color: #474747;" onclick="userBook()" id="userbook">User Book</button></span>
						{{/if_neq}}
						   {{else}}
							<span> {{marketName}} </span> 
							{{/if_eq}}
						  
                       
                       
                     </div>
                     <div class="table-header">
                       
 							<div class="text-info">
						 {{#if_eq ${user.usertype} 4}}  	
                           <span id="playPause{{marketIdWithOutDecimal}}">Runner 
						{{#if_eq isPause true}}  
                              <span style="color: #0088cc;" onclick="PausePlay('{{marketId}}')"><i class="fa fa-play" aria-hidden="true"></i></span> </b>
						{{else}}
							<span style="color: #0088cc;"  onclick="PausePlay('{{marketId}}')"><i class="fa fa-pause" aria-hidden="true"></i></span> </b>
						{{/if_eq}}
						
					</span>{{else}} Odds{{/if_eq}}

					{{#if_eq ${user.usertype} 6}}  	
                           <span id="playPause{{marketIdWithOutDecimal}}">Runner 
						{{#if_eq isPause true}}  
                              <span style="color: #0088cc;" onclick="PausePlay('{{marketId}}')"><i class="fa fa-play" aria-hidden="true"></i></span> </b>
						{{else}}
							<span style="color: #0088cc;"  onclick="PausePlay('{{marketId}}')"><i class="fa fa-pause" aria-hidden="true"></i></span> </b>
						{{/if_eq}}
 
					</span>{{/if_eq}}

               </b>
                        </div>
                        <div class="box-info box-1 text-center 2paisaoddsback"></div>
                        <div class="box-info box-1   text-center 2paisaoddslay"></div>
                        <div class="box-info box-1 back text-center 1paisaoddsback">Back</div>
                        <div class="box-info box-1 lay text-center 1paisaoddslay">Lay</div>
                        <div class="box-info box-1  text-center"></div>
                        <div class="box-info box-1  text-center"></div>
                     </div>
                     <div class="table-body">
						   
					{{#each runner}}
                   
	 					{{#if_eq runnerStatus "SUSPENDED"}}  

  							<div class="table-row suspended" data-title="SUSPENDED" id="team_row{{selectionId}}">
                            <div class="text-info country-name  ">
                                <span class="team-name">{{team}}</b></span><span class="team-name curentbetpnl" id="teampnl{{selectionId}}"><b></span>
                            </div>
							

							<div class="box-info box-2  back3 text-center nouseodds backmatchodds"><span id="back3price{{selectionId}}">{{back3price}}</span><br><span>{{back3size}}</span></div>
 							<div class="box-info box-2 back2 text-center nouseodds"><span id="back2price{{selectionId}}">{{back2price}}</span><br><span>{{back2size}}</span></div>
							<div class="box-info box-2  back  text-center  backmatchodds" bet-type="back" runnername= {{team}} selectionid={{selectionId}} ><span id="back1price{{selectionId}}"  > <b> {{back1price}}</b></span><br><span>{{back1size}}</span></div>
           				    

							<div class="box-info box-2 lay laymatchodds  text-center "  bet-type="lay" runnername= {{team}}  selectionid={{selectionId}} ><span id="lay1price{{selectionId}}"> <b> {{lay1price}}</b></span><br><span>{{lay1size}}</span></div>
                            <div class="box-info box-2 lay2 text-center nouseodds"><span id="lay2price{{selectionId}}">{{lay2price}}</span><br><span>{{lay2size}}</span></div>
                            <div class="box-info box-2 lay3 text-center nouseodds"><span id="lay31price{{selectionId}}">{{lay3price}}</span><br><span>{{lay3size}}</span></div>
						{{else}}

							<div class="table-row active " data-title="SUSPENDED" id="team_row{{selectionId}}">
                            <div class="text-info country-name  ">
                                <span class="team-name">{{team}}</b></span><span class="team-name curentbetpnl" id="teampnl{{selectionId}}"><b></span>
                            </div>
							

							<div class="box-info box-2  back3 text-center nouseodds backmatchodds"><span id="back3price{{selectionId}}">{{back3price}}</span><br><span>{{back3size}}</span></div>
 							<div class="box-info box-2 back2 text-center nouseodds"><span id="back2price{{selectionId}}">{{back2price}}</span><br><span>{{back2size}}</span></div>
							<div class="box-info box-2  back  text-center  backmatchodds" bet-type="back" runnername= {{team}} selectionid={{selectionId}} ><span id="back1price{{selectionId}}"  > <b> {{back1price}}</b></span><br><span>{{back1size}}</span></div>
           				    

							<div class="box-info box-2 lay laymatchodds  text-center "  bet-type="lay" runnername= {{team}}  selectionid={{selectionId}} ><span id="lay1price{{selectionId}}"> <b> {{lay1price}}</b></span><br><span>{{lay1size}}</span></div>
                            <div class="box-info box-2 lay2 text-center nouseodds"><span id="lay2price{{selectionId}}">{{lay2price}}</span><br><span>{{lay2size}}</span></div>
                            <div class="box-info box-2 lay3 text-center nouseodds"><span id="lay31price{{selectionId}}">{{lay3price}}</span><br><span>{{lay3size}}</span></div>
						{{/if_eq}}
                        </div>
                      {{/each}}	 
 							
							
                     
 
                      
                     </div>
 
                </div>

{{/if_neq}}
{{/each}}


{{#each data1.Bookmaker}}

{{#if_neq type "Fancy"}}  
 <div class="col-md-12">
<input type="hidden" value="{{marketId}}" class="marketIds">   
   <input type="hidden" value="{{marketIdWithOutDecimal}}" class="marketIdWithOutDecimal">                 
  <div class="market-title">
                           {{#if_eq marketName "Match Odds"}}  
							<span>{{matchName}} </span>
                            <span> {{marketName}} </span> 
                            <span>{{date}} </span>
						   {{else}}
							<span> {{marketName}} </span> 
							<span>
						{{#if_neq ${user.usertype} 6}}
							<button class="tv-button" style="background-color: #474747;" onclick="bookMakerBooks('{{marketName}}')" id="bookmakerbook">Book</button>
						{{/if_neq}}</span>
							<span><button type="button" class="tv-button" style="background-color: #474747;" onclick="getBetsByMarketId('{{marketId}}')" >Bets</button>
									</span>
							{{/if_eq}}
						  
                       
                       
                     </div>
                     <div class="table-header">
                       
 							<div class="text-info">
						 {{#if_eq ${user.usertype} 4}}  	
                           <span id="playPause{{marketIdWithOutDecimal}}">Runner
						{{#if_eq isPause true}}  
                              <span style="color: #0088cc;" onclick="PausePlay('{{marketId}}')"><i class="fa fa-play" aria-hidden="true"></i></span> </b>
						{{else}}
							<span style="color: #0088cc;"  onclick="PausePlay('{{marketId}}')"><i class="fa fa-pause" aria-hidden="true"></i></span> </b>
						{{/if_eq}}

					</span>{{else}} Odds{{/if_eq}}

					{{#if_eq ${user.usertype} 6}}  	
                           <span id="playPause{{marketIdWithOutDecimal}}">Runner 
						{{#if_eq isPause true}}  
                              <span style="color: #0088cc;" onclick="PausePlay('{{marketId}}')"><i class="fa fa-play" aria-hidden="true"></i></span> </b>
						{{else}}
							<span style="color: #0088cc;"  onclick="PausePlay('{{marketId}}')"><i class="fa fa-pause" aria-hidden="true"></i></span> </b>
						{{/if_eq}}

					</span>{{/if_eq}}

 </b>
                        </div>
                        <div class="box-info box-1 text-center 2paisaoddsback"></div>
                        <div class="box-info box-1   text-center 2paisaoddslay"></div>
                        <div class="box-info box-1 back text-center 1paisaoddsback">Back</div>
                        <div class="box-info box-1 lay text-center 1paisaoddslay">Lay</div>
                        <div class="box-info box-1  text-center"></div>
                        <div class="box-info box-1  text-center"></div>
                     </div>
                     <div class="table-body">
						   
					{{#each runner}}
                   

{{#if_eq runnerStatus "SUSPENDED"}}  
 
  							<div class="table-row suspended" data-title="SUSPENDED" id="team_row{{selectionId}}">
                            <div class="text-info country-name  ">
                                <span class="team-name">{{team}}</b></span><span class="team-name curentbetpnl" id="teampnl{{selectionId}}"><b></span>
                            </div>
							

							<div class="box-info box-2  back3 text-center nouseodds backmatchodds"><span id="back3price{{selectionId}}">{{back3price}}</span><br><span>{{back3size}}</span></div>
 							<div class="box-info box-2 back2 text-center nouseodds"><span id="back2price{{selectionId}}">{{back2price}}</span><br><span>{{back2size}}</span></div>
							<div class="box-info box-2  back  text-center  backmatchodds" bet-type="back" runnername= {{team}} selectionid={{selectionId}} ><span id="back1price{{selectionId}}"  > <b> {{back1price}}</b></span><br><span>{{back1size}}</span></div>
           				    

							<div class="box-info box-2 lay laymatchodds  text-center "  bet-type="lay" runnername= {{team}}  selectionid={{selectionId}} ><span id="lay1price{{selectionId}}"> <b> {{lay1price}}</b></span><br><span>{{lay1size}}</span></div>
                            <div class="box-info box-2 lay2 text-center nouseodds"><span id="lay2price{{selectionId}}">{{lay2price}}</span><br><span>{{lay2size}}</span></div>
                            <div class="box-info box-2 lay3 text-center nouseodds"><span id="lay31price{{selectionId}}">{{lay3price}}</span><br><span>{{lay3size}}</span></div>
					  
{{else}}   
 {{#if_eq runnerStatus "CLOSED"}}  
  	<div class="table-row suspended" data-title="SUSPENDED" id="team_row{{selectionId}}">
                            <div class="text-info country-name  ">
                                <span class="team-name">{{team}}</b></span><span class="team-name curentbetpnl" id="teampnl{{selectionId}}"><b></span>
                            </div>
							

							<div class="box-info MATbox-2  back3 text-center nouseodds backmatchodds"><span id="back3price{{selectionId}}">{{back3price}}</span><br><span>{{back3size}}</span></div>
 							<div class="box-info box-2 back2 text-center nouseodds"><span id="back2price{{selectionId}}">{{back2price}}</span><br><span>{{back2size}}</span></div>
							<div class="box-info box-2  back  text-center  backmatchodds" bet-type="back" runnername= {{team}} selectionid={{selectionId}} ><span id="back1price{{selectionId}}"  > <b> {{back1price}}</b></span><br><span>{{back1size}}</span></div>
           				    

							<div class="box-info box-2 lay laymatchodds  text-center "  bet-type="lay" runnername= {{team}}  selectionid={{selectionId}} ><span id="lay1price{{selectionId}}"> <b> {{lay1price}}</b></span><br><span>{{lay1size}}</span></div>
                            <div class="box-info box-2 lay2 text-center nouseodds"><span id="lay2price{{selectionId}}">{{lay2price}}</span><br><span>{{lay2size}}</span></div>
                            <div class="box-info box-2 lay3 text-center nouseodds"><span id="lay31price{{selectionId}}">{{lay3price}}</span><br><span>{{lay3size}}</span></div>
					  
  {{else}}      
  		<div class="table-row active " data-title="SUSPENDED" id="team_row{{selectionId}}">
                            <div class="text-info country-name  ">
                                <span class="team-name">{{team}}</b></span><span class="team-name curentbetpnl" id="teampnl{{selectionId}}"><b></span>
                            </div>
							

							<div class="box-info box-2  back3 text-center nouseodds backmatchodds"><span id="back3price{{selectionId}}">{{back3price}}</span><br><span>{{back3size}}</span></div>
 							<div class="box-info box-2 back2 text-center nouseodds"><span id="back2price{{selectionId}}">{{back2price}}</span><br><span>{{back2size}}</span></div>
							<div class="box-info box-2  back  text-center  backmatchodds" bet-type="back" runnername= {{team}} selectionid={{selectionId}} ><span id="back1price{{selectionId}}"  > <b> {{back1price}}</b></span><br><span>{{back1size}}</span></div>
           				    

							<div class="box-info box-2 lay laymatchodds  text-center "  bet-type="lay" runnername= {{team}}  selectionid={{selectionId}} ><span id="lay1price{{selectionId}}"> <b> {{lay1price}}</b></span><br><span>{{lay1size}}</span></div>
                            <div class="box-info box-2 lay2 text-center nouseodds"><span id="lay2price{{selectionId}}">{{lay2price}}</span><br><span>{{lay2size}}</span></div>
                            <div class="box-info box-2 lay3 text-center nouseodds"><span id="lay31price{{selectionId}}">{{lay3price}}</span><br><span>{{lay3size}}</span></div>
					
  
{{/if_eq}}


				                            


		{{/if_eq}}
                        </div>
                      {{/each}}	 
 							
					  </div>

                </div>

{{/if_neq}}
{{/each}}
   </script>
  <script id="matchedBetsHandler" type="text/x-handlebars-template">
<button type="button" class="btn btn-info blue-btn btn-sm"  onclick="getAdminViewMoreBets('Match Odds')">View More</button> 
			<div class="table-responsive">
				<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
												   <thead>
													  <tr>
															
														<th>Odds</th>	
 														<th>Amount</th>
														<th>Mode</th>
 														<th>Selection</th>
														<th class="marketname">Member</th>
											
														
 											<th>Profit</th>
											<th>Loss</th>
											<th>Market</th>
											 
											 <th class="datetime">MatchedTime</th>
											 </tr>
												   </thead>
<tbody id="matchoddsbettable">
{{#each data}}
												   
													  <tr>
														  
 													
														  <td>{{odds}}</td>
														  <td>{{stake}}</td>
													{{#if_eq isback true}}
															<td class="back">L</td>
														  	{{else}}
															<td class="lay">k</td>
											 

														{{/if_eq}}	
														 	{{#if_eq isback true}}
															<td class="back">{{selectionname}}</td>
														  	{{else}}
															<td class="lay">{{selectionname}}</td>
														{{/if_eq}}	
															<td>{{userid}}<i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><p id ="userdetail{{id}}" class="userdetail" ></p></td>			 

															<td class="green">{{pnl}}</td>
														  <td class="red">{{liability}}</td>
 														<td>{{marketname}}</td>
														                                         
														  <td>{{matchedtime}}</td>
													  </tr>
												   
{{/each}}
			</tbody>									</table>
			</div>
  </script>
  
  
  <script id="matchedBetsBookMakerHandler" type="text/x-handlebars-template">
<button type="button" class="btn btn-info blue-btn btn-sm" onclick="getAdminViewMoreBets('Bookmaker')">View More</button> 
				<div class="table-responsive">
				<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
												   <thead>
													  <tr>
														
														<th>Odds</th>	
 														<th>Amount</th>
														<th>Mode</th>
 														<th>Selection</th>
														<th class="marketname">Member</th>
										
 											<th>Profit</th>
											<th>Loss</th>
											<th>Market</th>
											 
											 <th class="datetime">MatchedTime</th>
											 </tr>
												   </thead>
<tbody id="matchoddsbettable">
{{#each data}}
												   
													  <tr>
														  
 													
														  <td>{{odds}}</td>
														  <td>{{stake}}</td>
													{{#if_eq isback true}}
															<td class="back">L</td>
														  	{{else}}
															<td class="lay">k</td>
											 

														{{/if_eq}}	
														 	{{#if_eq isback true}}
															<td class="back">{{selectionname}}</td>
														  	{{else}}
															<td class="lay">{{selectionname}}</td>
														{{/if_eq}}	
						<td>{{userid}}<i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><p id ="userdetail{{id}}" class="userdetail" ></p></td>			 

															<td class="green">{{pnl}}</td>
														  <td class="red">{{liability}}</td>
 														<td>{{marketname}}</td>
														                                         
														  <td>{{matchedtime}}</td>
													  </tr>
												   
{{/each}}
			</tbody>									</table>
			</div>
  </script>
  
  
   <script id="matchedBetsBookMaker3Handler" type="text/x-handlebars-template">
<button type="button" class="btn btn-info blue-btn btn-sm" onclick="getAdminViewMoreBets('Bookmaker')">View More</button> 
				<div class="table-responsive">
				<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
												   <thead>
													  <tr>
														
														<th>Odds</th>	
 														<th>Amount</th>
														<th>Mode</th>
 														<th>Selection</th>
														<th class="marketname">Member</th>
										
 											<th>Profit</th>
											<th>Loss</th>
											<th>Market</th>
											 
											 <th class="datetime">MatchedTime</th>
											 </tr>
												   </thead>
<tbody id="matchoddsbettable">
{{#each data}}
												   
													  <tr>
														  
 													
														  <td>{{odds}}</td>
														  <td>{{stake}}</td>
													{{#if_eq isback true}}
															<td class="back">L</td>
														  	{{else}}
															<td class="lay">k</td>
											 

														{{/if_eq}}	
														 	{{#if_eq isback true}}
															<td class="back">{{selectionname}}</td>
														  	{{else}}
															<td class="lay">{{selectionname}}</td>
														{{/if_eq}}	
						<td>{{userid}}<i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><p id ="userdetail{{id}}" class="userdetail" ></p></td>			 

															<td class="green">{{pnl}}</td>
														  <td class="red">{{liability}}</td>
 														<td>{{marketname}}</td>
														                                         
														  <td>{{matchedtime}}</td>
													  </tr>
												   
{{/each}}
			</tbody>									</table>
			</div>
  </script>
  
  
  <script id="matchedBetsHandlerloadMore" type="text/x-handlebars-template">


										
									
											{{#each data}}
												    <tr>
													
													 <td>{{stake}}</td>
													{{#if_eq isback true}}
													 <td class="lay">k</td>
													{{else}}
													 <td class="back">L</td>
													{{/if_eq}}	
													{#if_eq isback true}}
													 <td class="lay">{{selectionname}}</td>
													{{else}}
													<td class="back">{{selectionname}}</td>
											 {{/if_eq}}	
													<td>{{userid}}<i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><p id ="userdetail{{id}}" class="userdetail" ></p></td>			 


											  
														 

										<!--	<td>{{commssionshare}}</td>-->
										{{#if_eq ${user.usertype} 4}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td><td>{{adminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td><td>{{admin}}</td>
											{{/if_eq}}
										
											 {{#if_eq ${user.usertype} 5}}
											
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td>
											{{/if_eq}}

											 {{#if_eq ${user.usertype} 0}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 1}}
											<td>{{dealername}}</td><td>{{mastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 2}}
											<td>{{dealername}}</td>
											<td>{{dealer}}</td>
											{{/if_eq}}
											<td class="green">{{pnl}}</td>
											<td class="red">{{liability}}</td>
											<td>{{marketname}}</td>
											<td>{{date}}</td>
											<td>{{placeTime}}</td>                                        
										</tr>
												   
									{{/each}}
					</script>
  
  <script id="userpnlHandler" type="text/x-handlebars-template">
   {{#each data}}
		<tr>
			<td id="{{userid}}">{{userid}}</td>
			<td>
	  			{{#if pnl1 0}}
					<strong id="sel1{{userid}}" class="negative">
	  			{{else}}
					<strong id="sel1{{userid}}" class="positive">
	 			{{/if}}
					{{pnl1}}
		     </td>
												  
 			<td>
				{{#if pnl2 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl2}}
			
			</td>
			<td>
				{{#if pnl3 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl3}}
			
			</td>

		</tr>
		
{{/each}}
  </script>
  
  
    <script id="userCounterHandler" type="text/x-handlebars-template">
   {{#each data.list}}
		<tr>
			
			<td>{{id}}</td>
			<td>{{appName}}</td>
		</tr>
		
{{/each}}

		<tr class="positive">
			
			<td>Total Visited Count</td>
			<td>{{data.totalUser}}</td>
        </tr>

<tr class="positive">
		

			<td>Total Bet Count</td>
			<td>{{data.betUserCount}}</td>
		</tr>
  </script>
  
    <script id="userpnlBookmakerHandler" type="text/x-handlebars-template">
   {{#each data}}
		<tr>
			<td id="{{userid}}">{{userid}}</td>
			<td>
	  			{{#if pnl1 0}}
					<strong id="sel1{{userid}}" class="negative">
	  			{{else}}
					<strong id="sel1{{userid}}" class="positive">
	 			{{/if}}
					{{pnl1}}
		     </td>
												  
 			<td>
				{{#if pnl2 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl2}}
			
			</td>
			<td>
				{{#if pnl3 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl3}}
			
			</td>

		</tr>
		
{{/each}}
  </script>
  
   <script id="userpnlBookmaker3Handler" type="text/x-handlebars-template">
   {{#each data}}
		<tr>
			<td id="{{userid}}">{{userid}}</td>
			<td>
	  			{{#if pnl1 0}}
					<strong id="sel1{{userid}}" class="negative">
	  			{{else}}
					<strong id="sel1{{userid}}" class="positive">
	 			{{/if}}
					{{pnl1}}
		     </td>
												  
 			<td>
				{{#if pnl2 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl2}}
			
			</td>
			<td>
				{{#if pnl3 0}}
					<strong id="sel2{{userid}}" class="negative">
				{{else}}
					<strong id="sel2{{userid}}" class="positive">
				{{/if}}														  
					{{pnl3}}
			
			</td>

		</tr>
		
{{/each}}
  </script>
<script id="fancyHandler" type="text/x-handlebars-template">
  				 		
					  <div class="col-md-12 fancy-colume">
                    <div class="row no-gutters fancy-row">
                        <div class="col-md-12 fancy-market">
                                <div class="market-title fancy-box right">
                                    <span> Fancy Market</span>
                                    <a href="">
                                        <span>
                                            <i class="fa fa-info-circle float-right"></i>
                                        </span>
                                    </a>
                                 </div>
                                
								 <div class="table-header right">
                                    <div class="text-info  right fancy-left">
                                        <span onclick="fancyMarket2HShowHide('RS')"><b>Session Market1</b></span> 
                                    </div>
                                    
                                    <div class="box-info box-1 lay  right text-center fancy-right">No</div>
                                    <div class="box-info box-1 back  right text-center fancy-right">Yes</div>
                                    <div class="box-info box-1  extra right text-center fancy-right"></div>
                                   
                                 </div>

 							
								<div class="table-body right" id="fancy_data"></div>
							 
                          {{#each data1}}

                 {{#if_neq type "Odds"}} 
                                 <div class="table-body right body-with-btn" id=""> 
                             
									 <div class="table-row suspended" id="fancy_status{{removedot fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{removedot fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>
                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{removedot fancyId}}"><span id="lay1price{{removedot fancyId}}"><strong>{{lay1price}}</strong></span><br><span id="lay1size{{removedot fancyId}}">{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{removedot fancyId}}"><span id="back1price{{removedot fancyId}}"><strong>{{back1price}}</strong></span><br><span id="back1size{{removedot fancyId}}">{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{removedot fancyId}}">

									   <div class="max-min-row">
											
										
									{{#if_eq ${user.usertype} 6}}												
										<button type="button" class="btn float-right" onclick="fancyShowHide('{{fancyId}}','{{#if_eq isShow true}}false{{else}}true{{/if_eq}}')" >{{#if_eq isShow true}}Hide{{else}}Show{{/if_eq}}</button>
									{{/if_eq}}
									<button type="button" class="btn float-right" onclick="getBetsByMarketId('{{fancyId}}')" >Bets</button>
									
									</div>
								</div>
                         </div>
						           
                           		 </div>
							{{/if_neq}}	 
						   {{/each}}	
								 
                          </div>
                      
                        
                    </div>
                </div>
 			
</script>


<script id="fancyHandlerDiamond" type="text/x-handlebars-template">
  				 		
					  <div class="col-md-12 fancy-colume">
                    <div class="row no-gutters fancy-row">
                        <div class="col-md-12 fancy-market">
                                <div class="market-title fancy-box right">
                                    <span> Fancy Market</span>
                                    <a href="">
                                        <span>
                                            <i class="fa fa-info-circle float-right"></i>
                                        </span>
                                    </a>
                                 </div>
                                
								 <div class="table-header right">
                                    <div class="text-info  right fancy-left">
                                       <span onclick="fancyMarket2HShowHide('Diamond')"><b>Session Market2</b></span>
                                    </div>
                                    
                                    <div class="box-info box-1 lay  right text-center fancy-right">No</div>
                                    <div class="box-info box-1 back  right text-center fancy-right">Yes</div>
                                    <div class="box-info box-1  extra right text-center fancy-right"></div>
                                   
                                 </div>

 							
								<div class="table-body right" id="fancy_data"></div>
							 
                          {{#each data1}}

                 {{#if_neq type "Odds"}} 
                                 <div class="table-body right body-with-btn" id=""> 
                             
									 <div class="table-row suspended" id="fancy_status{{removedot fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{removedot fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>
                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{removedot fancyId}}"><span id="lay1price{{removedot fancyId}}"><strong>{{lay1price}}</strong></span><br><span id="lay1size{{removedot fancyId}}">{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{removedot fancyId}}"><span id="back1price{{removedot fancyId}}"><strong>{{back1price}}</strong></span><br><span id="back1size{{removedot fancyId}}">{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{removedot fancyId}}">

									   <div class="max-min-row">
											
										
									{{#if_eq ${user.usertype} 6}}												
										<button type="button" class="btn float-right" onclick="fancyShowHide('{{fancyId}}','{{#if_eq isShow true}}false{{else}}true{{/if_eq}}')" >{{#if_eq isShow true}}Hide{{else}}Show{{/if_eq}}</button>
									{{/if_eq}}
									<button type="button" class="btn float-right" onclick="getBetsByMarketId('{{fancyId}}')" >Bets</button>
									
									</div>
								</div>
                         </div>
						           
                           		 </div>
							{{/if_neq}}	 
						   {{/each}}	
								 
                          </div>
                      
                        
                    </div>
                </div>
 			
</script>


<script id="fancyMarket3Handler" type="text/x-handlebars-template">
  				 		
									  <div class="col-md-12 fancy-colume">
                    <div class="row no-gutters fancy-row">
                        <div class="col-md-12 fancy-market">
                                <div class="market-title fancy-box right">
                                    <span> Other Market</span>
                                    <a href="">
                                        <span>
                                            <i class="fa fa-info-circle float-right"></i>
                                        </span>
                                    </a>
                                 </div>
                                 <div class="table-header right">
                                    <div class="text-info  right fancy-left">
                                        <b>Other Market</span> </b>
                                    </div>
                                    
                                    <div class="box-info box-1 lay  right text-center fancy-right">No</div>
                                    <div class="box-info box-1 back  right text-center fancy-right">Yes</div>
                                    <div class="box-info box-1  extra right text-center fancy-right"></div>
                                   
                                 </div>
								<div class="table-body right" id="fancy_data"></div>
							 
                          {{#each data1}}

                 {{#if_neq type "Odds"}} 
                                 <div class="table-body right body-with-btn" id=""> 
                             
									 <div class="table-row suspended" id="fancy_status{{removedot fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{removedot fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>
                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{removedot fancyId}}"><span id="lay1price{{removedot fancyId}}"><strong>{{lay1price}}</strong></span><br><span id="lay1size{{removedot fancyId}}">{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{removedot fancyId}}"><span id="back1price{{removedot fancyId}}"><strong>{{back1price}}</strong></span><br><span id="back1size{{removedot fancyId}}">{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{removedot fancyId}}">

									   <div class="max-min-row">
											
										
									
	<button type="button" class="btn float-right" onclick="getBetsByMarketId('{{fancyId}}')" >Bets</button>
									
									</div>
								</div>
                         </div>
						           
                           		 </div>
							{{/if_neq}}	 
						   {{/each}}	
								 
                          </div>
                      
                        
                    </div>
                </div>
 			
</script>




<script id="oddEvenfancyMarketHandler" type="text/x-handlebars-template">
  				 		
												  <div class="col-md-12 fancy-colume">
                    <div class="row no-gutters fancy-row">
                        <div class="col-md-12 fancy-market">
                                <div class="market-title fancy-box right">
                                    <span> Odd Even Market</span>
                                    <a href="">
                                        <span>
                                            <i class="fa fa-info-circle float-right"></i>
                                        </span>
                                    </a>
                                 </div>
                                 <div class="table-header right">
                                    <div class="text-info  right fancy-left">
                                        <b>Odd Even Market</span> </b>
                                    </div>
                                    
                                    <div class="box-info box-1 lay  right text-center fancy-right">No</div>
                                    <div class="box-info box-1 back  right text-center fancy-right">Yes</div>
                                    <div class="box-info box-1  extra right text-center fancy-right"></div>
                                   
                                 </div>
								<div class="table-body right" id="fancy_data"></div>
							 
                          {{#each data1}}

                 {{#if_neq type "Odds"}} 
                                 <div class="table-body right body-with-btn" id=""> 
                             
									 <div class="table-row suspended" id="fancy_status{{removedot fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{removedot fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>
                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{removedot fancyId}}"><span id="lay1price{{removedot fancyId}}"><strong>{{lay1price}}</strong></span><br><span id="lay1size{{removedot fancyId}}">{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{removedot fancyId}}"><span id="back1price{{removedot fancyId}}"><strong>{{back1price}}</strong></span><br><span id="back1size{{removedot fancyId}}">{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{removedot fancyId}}">

									   <div class="max-min-row">
										<button type="button" class="btn float-right" onclick="getBetsByMarketId('{{fancyId}}')" >Bets</button>
									
									</div>
								</div>
                         </div>
						           
                           		 </div>
							{{/if_neq}}	 
						   {{/each}}	
								 
                          </div>
                      
                        
                    </div>
                </div>
 			
</script>




<script id="ballByBallfancyMarketHandler" type="text/x-handlebars-template">
  				 		
						  <div class="col-md-12 fancy-colume">
                    <div class="row no-gutters fancy-row">
                        <div class="col-md-12 fancy-market">
                                <div class="market-title fancy-box right">
                                    <span> Ball By Ball Market</span>
                                    <a href="">
                                        <span>
                                            <i class="fa fa-info-circle float-right"></i>
                                        </span>
                                    </a>
                                 </div>
                                 <div class="table-header right">
                                    <div class="text-info  right fancy-left">
                                        <b>Ball By Ball Market</span> </b>
                                    </div>
                                    
                                    <div class="box-info box-1 lay  right text-center fancy-right">No</div>
                                    <div class="box-info box-1 back  right text-center fancy-right">Yes</div>
                                    <div class="box-info box-1  extra right text-center fancy-right"></div>
                                   
                                 </div>
								<div class="table-body right" id="fancy_data"></div>
							 
                          {{#each data1}}
         {{#if_neq type "Odds"}} 
                                 <div class="table-body right body-with-btn" id=""> 
                               {{#if_eq status "OPEN"}}

									 <div class="table-row active" id="fancy_status{{fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>
                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{fancyId}}"><span id="lay1price{{fancyId}}"><strong>{{lay1price}}</strong></span><br><span>{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{fancyId}}"><span id="back1price{{fancyId}}"><strong>{{back1price}}</strong></span><br><span>{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{fancyId}}">

											<div class="max-min-row">
												<div class="max-min-btn">
													min: {{minBet}} <br> max: {{maxBet}} 
												</div>
{{#if_eq ${user.usertype} 6}}												
<button type="button" class="btn float-right" onclick="fancyShowHide('{{fancyId}}','{{#if_eq isShow true}}false{{else}}true{{/if_eq}}')" >{{#if_eq isShow true}}Hide{{else}}Show{{/if_eq}}</button>
{{/if_eq}}
											</div>
</div>
                                   
                         </div>
							{{else}} 

 									<div class="table-row suspended suspended-body" id="fancy_status{{fancyId}}"  data-title="SUSPENDED">
									 <div class="text-info country-name right fancy-left">
                                            <span class="team-name"><b id ="fancy_Name{{fancyId}}">{{runnerName}}</b><span id=fancyexpo{{removedot fancyId}}  onclick="getFancyBook('{{fancyId}}','{{lay1price}}','{{back1price}}')"  class="negative">{{pnl}}</span></span>
                                            <p>
                                                <span  style="color: black;">0</span>
                                                <span  style="color: black;display: none;">0</span>                                            </p>
                                        </div>
                                         
                                        <div class="box-info box-1 lay  right text-center fancy-right laymatchoddsfancy" markettype="Fancy" bet-type="lay" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" LaySize1="{{lay1size}}" id="backDiv{{fancyId}}"><span id="lay1price{{fancyId}}"><strong>{{lay1price}}</strong></span><br><span>{{lay1size}}</span></div>
                                        <div class="box-info box-1 back right text-center fancy-right backmatchoddsfancy"  markettype="Fancy" bet-type="back" marketid="{{fancyId}}" runnername= "{{runnerName}}" selectionid="{{fancyId}}" BackSize1="{{back1size}}" id="layDiv{{fancyId}}"><span id="back1price{{fancyId}}"><strong>{{back1price}}</strong></span><br><span>{{back1size}}</span></div>
                                        <div class="box-info box-1  extra right text-center fancy-right" id="min_max_fancy{{fancyId}}">
<div class="max-min-row">
												<div class="max-min-btn">
													min: {{minBet}} <br> max: {{maxBet}} 
												</div>
		{{#if_eq ${user.usertype} 6}}												
										<button type="button" class="btn float-right" onclick="fancyShowHide('{{fancyId}}','{{#if_eq isShow true}}false{{else}}true{{/if_eq}}')" >{{#if_eq isShow true}}Hide{{else}}Show{{/if_eq}}</button>
									{{/if_eq}}
											</div>
</div>
                                      </div>

                          {{/if_eq}}	
					  	
                                      
                           		 </div>
							{{/if_neq}}	 
						   {{/each}}	
								 
                          </div>
                      
                        
                    </div>
                </div>
 			
</script>

  <script id="fancyBetsHandler" type="text/x-handlebars-template">
	<button type="button" class="btn btn-info blue-btn btn-sm" id="view_more" onclick="getAdminViewMoreBets('Fancy')">View More</button> 
				<div class="table-responsive">
				<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
												   <thead>
													  <tr>
														
														<th>Odds</th>	
 														<th>Amount</th>
														<th>Mode</th>
 														<th>Selection</th>
														<th class="marketname">Member</th>
										
														
 											<th>Profit</th>
											<th>Loss</th>
											<th>Market</th>
											 
											 <th class="datetime">MatchedTime</th>
											 </tr>
												   </thead>
<tbody id="matchoddsbettable">
{{#each data}}
												   
													  <tr>
														  
 													
														  <td>{{odds}}</td>
														  <td>{{stake}}</td>
													{{#if_eq isback true}}
															<td class="back">Yes</td>
														  	{{else}}
															<td class="lay">No</td>
											 

														{{/if_eq}}	
														 {{#if_eq isback true}}
															<td class="back">{{selectionname}}/{{pricevalue}}</td>
														  	{{else}}
															<td class="lay">{{selectionname}}/{{pricevalue}}</td>
														{{/if_eq}}	
															<td>{{userid}}<i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><p id ="userdetail{{id}}" class="userdetail" ></p></td>			 

															<td class="green">{{pnl}}</td>
														  <td class="red">{{liability}}</td>
 														<td>{{marketname}}</td>
														                                         
														  <td>{{matchedtime}}</td>
													  </tr>
												   
{{/each}}
			</tbody>									</table>
			</div>
  </script>
  
  <script id="fancyBookHandler" type="text/x-handlebars-template">


 {{#if_includes data.fancyid 'F3'}}

	<table class="table table-borderless mb-1">
		<thead>
			<tr>
				 <th class="text-center">Back</th>
				 <th class="text-center">Lay</th>
		    </tr>
	   </thead>
       <tbody id="fancyBookTableBody">
		 {{#each data.data}}	 						
         <tr>
           {{#if_small back 0}}
			  <td class="text-center"><span class="negative"  style="background-color: white;">{{back}}</span> </td>
 		    {{else}}  
 			<td class="text-center"><span class="positive"  style="background-color: white;">{{back}}</span> </td>
 		  {{/if_small}}	

			{{#if_small lay 0}}
			  <td class="text-center"><span class="negative"  style="background-color: white;">{{lay}}</span> </td>
 		    {{else}}  
 			<td class="text-center"><span class="positive"  style="background-color: white;">{{lay}}</span> </td>
 		  {{/if_small}}	
         </tr>
     {{/each}}
	   </tbody>
	</table>

 {{/if_includes}}



{{#if_includes data.fancyid 'OE'}}

	<table class="table table-borderless mb-1">
		<thead>
			<tr>
				 <th class="text-center">OOD</th>
				 <th class="text-center">EVEN</th>
		    </tr>
	   </thead>
       <tbody id="fancyBookTableBody">
		 {{#each data.data}}	 						
         <tr>
           {{#if_small back 0}}
			  <td class="text-center"><span class="negative"  style="background-color: white;">{{back}}</span> </td>
 		    {{else}}  
 			<td class="text-center"><span class="positive"  style="background-color: white;">{{back}}</span> </td>
 		  {{/if_small}}	

			{{#if_small lay 0}}
			  <td class="text-center"><span class="negative"  style="background-color: white;">{{lay}}</span> </td>
 		    {{else}}  
 			<td class="text-center"><span class="positive"  style="background-color: white;">{{lay}}</span> </td>
 		  {{/if_small}}	
         </tr>
     {{/each}}
	   </tbody>
	</table>

 {{/if_includes}}


{{#if_includes data.fancyid 'F2'}}
  {{#if_greaterThan data.data.length 0}}  				
     

	<table class="table table-borderless mb-1">
		<thead>
			<tr>
				 <th class="text-center">Run</th>
				 <th class="text-center">Profit/Loss</th>
		    </tr>
	   </thead>
       <tbody id="fancyBookTableBody">
		 {{#each data.data}}	 						
         <tr>
            <td class="text-center"> <span class="" style="background-color: white;color:black;">{{rates}}</span> </td>
            {{#if_small pnl 0}}
		     <td class="text-center"><span class="negative"  style="background-color: white;">{{pnl}}</span> </td>
 		    {{else}}  
             <td class="text-center"><span class="positive"  style="background-color: white;">{{pnl}}</span> </td>
 		    {{/if_small}}	
         </tr>
     {{/each}}
	   </tbody>
	</table>

{{else}}


   <div>No Records Found</div>
{{/if_greaterThan}}
{{/if_includes}}



{{#if_includes data.fancyid 'BB'}}
  {{#if_greaterThan data.data.length 0}}  				
     

	<table class="table table-borderless mb-1">
		<thead>
			<tr>
				 <th class="text-center">Run</th>
				 <th class="text-center">Profit/Loss</th>
		    </tr>
	   </thead>
       <tbody id="fancyBookTableBody">
		 {{#each data.data}}	 						
         <tr>
            <td class="text-center"> <span class="" style="background-color: white;color:black;">{{rates}}</span> </td>
            {{#if_small pnl 0}}
		     <td class="text-center"><span class="negative"  style="background-color: white;">{{pnl}}</span> </td>
 		    {{else}}  
             <td class="text-center"><span class="positive"  style="background-color: white;">{{pnl}}</span> </td>
 		    {{/if_small}}	
         </tr>
     {{/each}}
	   </tbody>
	</table>

{{else}}


   <div>No Records Found</div>
{{/if_greaterThan}}
{{/if_includes}}
</script>

 <script id="ViewMoreBetsHandler" type="text/x-handlebars-template">
						

{{#each data}}
		<tr>
		<td>{{userid}}</td>
<td>{{selectionname}}</td>
			{{#if_eq marketname "Fancy"}} 
			 {{#if_eq isback true}}
				 <td class="back">Yes</td>
			    {{else}}
				 <td class="lay">No</td>
				{{/if_eq}}
			{{else}}
				{{#if_eq isback true}}
				 <td class="back">Back</td>
			    {{else}}
				 <td class="lay">Lay</td>
				{{/if_eq}}	
 
		{{/if_eq}}
		<td>{{stake}}</td><td>{{odds}}</td><td>{{matchedtime}}</td>
		</tr>										   
												
												   
{{/each}}
		
  </script>
  
  
  <script id="scoreCardsHandler" type="text/x-handlebars-template">
						

<div class="scorecard p-3">
					<div class="row">        
						<span class="team-name col-3"id="team1">{{data.spnnation1}}</span>        
						<span class="score col-3" id="score1">{{data.score1}}</span>
{{#if_eq data.activenation1 "1"}}  
						<span class="col-3 run-rate text-left text-xl-right">CRR{{data.spnrunrate1}}</span>
{{/if_eq}}

{{#if_eq data.activenation2 "1"}} 
						<span class="col-3 run-rate text-left text-xl-right">CRR{{data.spnrunrate2}}</span>
{{/if_eq}}
{{#if_eq data.activenation2 "1"}} 
						<span class="col-3 run-rate text-left text-xl-right"> RR {{data.spnreqrate2}}</span>
{{/if_eq}}   
					</div>    
					<div class="row m-t-10">        
						<span class="team-name col-3">{{data.spnnation2}}</span>        
						<span class="score col-3">{{data.score2}}</span>        
						<span class="col-3 run-rate"></span>        
						<span class="col-3 run-rate"></span>    
					</div>    
					<div class="row">        
						<div class="col-xl-5 m-t-10">
							<p>{{data.spnmessage}}</p>        
						</div>        
						<div class="col-xl-7 ball-runs-container m-t-5">   
         
							<p class="text-left text-xl-right ball-by-ball">

<span class="ball-runs {{data.balls.[0]}}">{{data.balls.[0]}}</span> 
								<span class="ball-runs {{data.balls.[1]}}">{{data.balls.[1]}}</span> 
								<span class="ball-runs {{data.balls.[2]}} ">{{data.balls.[2]}}</span> 
								<span class="ball-runs {{data.balls.[3]}} ">{{data.balls.[3]}}</span> 
								<span class="ball-runs {{data.balls.[4]}} ">{{data.balls.[4]}}</span> 
								<span class="ball-runs {{data.balls.[5]}} ">{{data.balls.[5]}}</span> 
								
						          
							</p>        
						</div>    
					</div>
				</div>
		
  </script>
  
  
  <div class="row no-gutters details-box mt-3">
  
  
                  
                
                	
   
    <div class="col-lg-8">
    <div class="card-body scoreboard-detail" id="score_div" >
			<iframe id="" width="100%" height="288" src="http://internal-consumer-apis.jmk888.com/go-score/template/4/${eventid}" ></iframe>
			</div>
      <s:if test="${user.usertype==4}">
    <div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
        <div class="form-row" >
				 Lagai Diff:	<button  onclick="updateBookMakerBackDiff()" style="display:none;" id="BookMakerBackDiff">On </button> <button style="display:none;" onclick="updateBookMakerBackDiffOff()" id="BookMakerBackDiffOff">Off </button> <div class="col-sm-1">
						<select class="form-control form-control-sm" id="bmbackdiff" >
							
						</select>
					</div>
					
				Book khai Diff: <button onclick="updateBookMakerLayDiff()" style="display:none;" id="BookMakerLayDiff">On </button> <button style="display:none;" onclick="updateBookMakerBackDiff()" id="BookMakerLayDiffOff">Off </button>	<div class="col-sm-1">
						<select class="form-control form-control-sm" id="bmlaydiff">
							
						</select>
				</div>
							
				Message	<button onclick="setMessage()"  >Save </button> 	<div class="col-sm-2"><input type ="text" id="setmessage" class="form-control form-control-sm" value="" >
				
						
				</div>
				
				
				Odds Status 	<button onclick="updateMarketOddsStatus()"  >Change </button>	<div class="col-sm-1"><select class="form-control form-control-sm" id="oddsstatus">
						
						</select>
				
						
				</div>
				
				<button id="hide_score"  onclick="hideScore()"  >Hide Score </button>
				<button id="show_score" onclick="showScore()"  style="display:none"  >Show Score </button>	
					
					 
					
		</div>
		</div>
		</s:if>
		
		   <s:if test="${user.usertype==6}">
           <div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
            <div class="form-row" id="resetmaximumbetdiv">
				Message:		<div class="col-sm-2"><input type ="text" id="setmessage" class="form-control form-control-sm" value="" >
				
						
				</div>
					
					 <button onclick="setMessage()"  >Save </button> 
					
				
		</div>
		</div>
		</s:if>
	
		
		   <s:if test="${user.usertype==5}">
           <div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
            <div class="form-row" id="resetmaximumbetdiv">
				Maximum Bet:	<div class="col-sm-3">
						<select class="form-control form-control-sm" id="maximumbet" onchange="updateSBAMinMaxEvent()">
							
						</select> 
					</div>
				
					
				
		</div>
		</div>
		</s:if>
	
		
		
        
        <div class="markets">
            <div class="row no-gutters bookmaker-market" id="matchoddsdiv"></div>
            <div class="row no-gutters bookmaker-marke col-md-12" id=""> 
              <div class="row col-md-12">
              <div class="row no-gutters bookmaker-marke col-md-6" >
             <div class="row no-gutters bookmaker-marke col-md-12"  id="fancyratesList"></div>
           
                                    
               <div class="row no-gutters bookmaker-marke col-md-12"   id="fancyratesListdiamond"></div>
               
              </div>
               <div class="row no-gutters bookmaker-market col-md-6"  id="fancyMarket3HandlerratesList"></div>
        
              </div>
            
               
            
             </div>
             
             <div class="row no-gutters bookmaker-marke col-md-12" id=""> 
             <div class="row no-gutters bookmaker-marke col-md-6" id="oddevenratesList"> </div>
             <div class="row no-gutters bookmaker-marke col-md-6" id="ballbyballratesList">  </div>
            
            
            </div>
             
            
        </div>
    </div>
    <div class=" col-lg-4 sidebar-right p-0 pl-md-3" id="sidebar-riight-info"> 
        <div class="sidebar-inner">
		
		<jsp:include page="betlock.jsp" />
         
          <div class="card-bet live-match" id="live_tv-div1">
            <div class="card-head mb-2">
                <p class="card-title"> <a href="#">Live Match</a><span class="float-right" onclick="loadTv()" ><i class="fa fa-tv"></i></span></p>
            </div>
        </div>
           
	 	   <div class="card-bet live-match" id="live_tv-div2">
            <div class="card-head mb-2">
                <p class="card-title"> <a href="#">Live Match2</a><span class="float-right" onclick="loadTv2()" ><i class="fa fa-tv"></i></span></p>
            </div>
        </div> 
             

		<div class="card-bet live-match mb-2"> 
			<div class="card-head">
				<p class="card-title m-0"> 
					<a href="#">Score Card</a>
				</p>
			</div>
			<!--Tannis-->
			<%-- 	<div class="coupon-card">
					<div class="tannis-field">
						<div class="form-row">
							<div class="col-3">
								<span class="teamName playing">
									Aryna Sabalenka
								</span>
							</div>
							<div class="col-7">
								<ul class="score-list"> 
									<li>7</li>
									<li class="green-text">2</li>
								</ul>
							</div>
							<div class="col-2">
								<span class="total-point green-text">
								0
								</span>
							</div>
						</div>
						<div class="form-row">
							<div class="col-3">
								<span class="teamName">
									Aryna Sabalenka
								</span>
							</div>
							<div class="col-7">
								<ul class="score-list">
									<li>7</li>
									<li class="green-text">2</li>
								</ul>
							</div>
							<div class="col-2">
								<span class="total-point green-text">
								0
								</span>
							</div>
						</div>
						<div class="form-row">
							<div class="col-3">
								<ul class="game-status d-flex">
									<li class="green-text">Set 2</li>
									<li class="pl-2 pr-2">|</li>
									<li>In Progress</li>
								</ul>
							</div>
							<div class="col-7">
								<ul class="score-list sets-list yellow-text">
									<li>1</li>
									<li>2</li>
									<li>3</li>
									<li>4</li>
									<li>5</li>
								</ul>
							</div>
							<div class="col-2">
								<span class="total-point yellow-text">
								Points
								</span>
							</div>
						</div>
					</div>
				</div> --%>
				<!--Tannis-->
			
	  </div>
              
              
              
              
               
        </div>
    </div>
    
      <div class="col-md-12">
        <div class="videos-image">
          <div class="live-record"></div>
        </div>
        <div class="markets">
             <span id="matched-betsCount"></span>
            
           <div class="panel-group" id="accordion">
		
		  
		
		   <div class="panel panel-default">
			<div class="panel-heading" style="background: #0088cc;color: white;">
			  <h4 class="panel-title">
				<a class="collapsed" style="color:white;" data-toggle="collapse" data-parent="#accordion"  href="#collapsematchbet">
				  Match Bet
				</a>
			  </h4>
			</div>
			<div id="collapsematchbet" class="panel-collapse collapse">
			  <div class="row no-gutters bookmaker-market" id="match-bet"> </div>
			</div>
		  </div>
		  
		</div> 
           </div>
           
           
           
            <div class="markets">
             <span id="fancy-betsCount"></span>
            
           <div class="panel-group" id="accordionfancy">
		
		  
		  
		   <div class="panel panel-default">
			<div class="panel-heading" style="background: #0088cc;color: white;">
			  <h4 class="panel-title">
				<a class="collapsed" style="color:white;" data-toggle="collapse" data-parent="#accordionfancy"   href="#collapsefancybet">
				Fancy Bet
				</a>
			  </h4>
			</div>
			<div id="collapsefancybet" class="panel-collapse collapse">
			  <div class="row no-gutters bookmaker-market" id="fancy-bet"> </div>
			</div>
		  </div>
		
		</div> 
           </div>
           
            <div class="markets">
             <span id="matched-betsCountBookmaker"></span>
            
           <div class="panel-group" id="accordionbookmaker">
		
		  
		
		   <div class="panel panel-default">
			<div class="panel-heading" style="background: #0088cc;color: white;">
			  <h4 class="panel-title">
				<a class="collapsed" style="color:white;" data-toggle="collapse" data-parent="#accordionbookmaker"   href="#collapsematchbetbookmaker">
				Bookmaker Bet
				</a>
			  </h4>
			</div>
			<div id="collapsematchbetbookmaker" class="panel-collapse collapse">
			  <div class="row no-gutters bookmaker-market" id="match-betbookmaker"> </div>
			</div>
		  </div>
		  
		</div> 
		
		
		   <div class="markets">
             <span id="matched-betsCountBookmaker3"></span>
            
           <div class="panel-group" id="accordionbookmaker3">
		
		  
		
		   <div class="panel panel-default">
			<div class="panel-heading" style="background: #0088cc;color: white;">
			  <h4 class="panel-title">
				<a class="collapsed" style="color:white;" data-toggle="collapse" data-parent="#accordionbookmaker3"   href="#collapsematchbetbookmaker3">
				Bookmaker 3 Bet
				</a>
			  </h4>
			</div>
			<div id="collapsematchbetbookmaker3" class="panel-collapse collapse">
			  <div class="row no-gutters bookmaker-market" id="match-betbookmaker3"> </div>
			</div>
		  </div>
		  
		</div> 
		
		
           </div>
    </div>
    
        <!-- Modal -->
<div class="modal fade" id="fancyBookModal" tabindex="-1" role="dialog" aria-labelledby="fancyBookModal" aria-hidden="true" style="margin-top:130px;">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Session Book</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="table-responsive" id="fancyBookTable">
                   
      </div>
      <!-- <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div> -->
    </div>
  </div>
</div>
</div>
	
	<div id="viewMoreModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header"style="background-color: #0088cc;padding: 10px 10px;color: white;">
       <!--  <button type="button" class="close" data-dismiss="modal">&times;</button>
        --> <h4 class="modal-title">View More</h4>
      </div>
      <div class="modal-body">
     <div class="table-responsive" id="view-more-matched-tbl" style="max-height:500px;">
        <table class="table table-bordered">
    <thead>
        <tr>
          
            <th>UserName</th>
            <th>Nation</th>
            <th>Bet Type</th>
            <th>Amount</th>
            <th>User Rate</th>
            <th>Match Date</th>
                       
        </tr>
    </thead>
             <tbody id="viewmoretable">
                <!-- <tr><td colspan="10">No records found</td></tr> -->
            </tbody>
</table>
    </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

<!-- Other Bets Modal-->
,
	<div id="otherBetsModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #0088cc; padding: 10px 10px; color: white;">
			
					<h4 class="modal-title">Bets</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive" id="other-bets-div"
						style="max-height: 500px;">
						
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


</div>


<script>
var startpage =0;
var endpage =100;
var amountvalue = 2000000;
var netexposure = 10000;
$(document).ready(function(){
	matchOdds("onLoad");
	
	Userpnlhtml();
	//activeFancy();
	matchFancyStatus();
	//sbaBetLockStatus();
	sbaFancyBetLockStatus();
	minMaxEventStatus();
	netExposureEventStatus();
	//scoreData();
	teamName();
	getEvents();
	if('${user.usertype}'!=6){
		getMyProfit();
		
	}
	marketDetail();
	getAddminBet();



	function fancyMarket2HShowHide(fancyType){

	        if(fancyType ="RS"){
					$("#fancyratesList").show();
					$("#fancyratesListdiamond").hide();
            }else{
            	$("#fancyratesList").hide();
            	$("#fancyratesListdiamond").show();
              }
        }

	  
	

	$("#userbook").show()
	$("#bookmakerbook").show()
	$("#usercount").show()
 });

function hideScore (){
	
			$("#show_score").show();
			$("#hide_score").hide();
			$("#score_div").hide();
			}

function showScore (){
	
	$("#show_score").hide();
	$("#hide_score").show();
	$("#score_div").show();
}

    $( ".matchoddsbookbutton" ).click(function() {
    	$("#matchoddsbook").show();
    	$("#bookmakerbook").hide();
	});

    $( ".bookmakerbookbutton" ).click(function() {
   	 $("#matchoddsbook").hide();
   	 $("#bookmakerbook").show();
	});


var userId ="";
var Id="";
  if('${user.usertype}' == 4){
	   userId = "adminid";
	   Id ='${user.id}';
	  }else  if('${user.usertype}' == 5){
	   userId = "subadminid";
	   Id ='${user.id}';
	  }else  if('${user.usertype}' == 0){
	   userId = "supermasterid";
	   Id ='${user.id}';
	  }else  if('${user.usertype}' == 1){
	   userId = "masterid";
	   Id ='${user.id}';
	  }else  if('${user.usertype}' == 2){
	   userId = "dealerid";
	   Id ='${user.id}';
	  }else  if('${user.usertype}' == 6){
	   userId = "adminid";
	   Id ='${user.parentid}';
	  }



  db.collection("commentary")
	.onSnapshot(function(querySnapshot) {
 var bets = [];
 var betslength = [];
 var i=0;
	querySnapshot.forEach(function(doc) {

		console.log(doc)
});
  
	});
  /*  db.collection("t_betlist").where("marketname", "==", "Bookmaker").where("isactive", "==", true).where(userId, "==", Id).where("matchid", "==",${eventid}).orderBy("id","desc")
		.onSnapshot(function(querySnapshot) {
  	 var bets = [];
  	 var betslength = [];
  	 var i=0;
   	querySnapshot.forEach(function(doc) {
		if(i<100){
	  		bets.push(doc.data());
		}
			betslength.push(doc.data());
   	   		i++;
   });


    

    
		
	$("#matched-betsCountBookmaker").text("Matched-Bets("+betslength.length+")");
		$("#match-betbookmaker").html('')
		var source   = $("#matchedBetsBookMakerHandler").html();
		var template = Handlebars.compile(source);
	  	$("#match-betbookmaker").append( template({data:bets}) );
  	
   
});

    db.collection("t_betlist").where("marketname", "==", "Match Odds").where("isactive", "==", true).where(userId, "==", Id).where("matchid", "==",${eventid}).orderBy("id","desc")
	.onSnapshot(function(querySnapshot) {
		var bets = [];
	  	 var betslength = [];
	  	 var i=0;
	   	querySnapshot.forEach(function(doc) {
			if(i<100){
		  		bets.push(doc.data());
			}
				betslength.push(doc.data());
	   	   		i++;
	   });
		 

	   $("#matchedCount").val(betslength.length); 
 	   $("#matched-betsCount").text("Matched-Bets("+betslength.length+")");
	
	   $("#match-bet").html('')
	   var source   = $("#matchedBetsHandler").html();
   	   var template = Handlebars.compile(source);
	   $("#match-bet").append( template({data:bets}) );
		 
      

});
 

  db.collection("t_betlist").where("marketname", "==", "Fancy").where("isactive", "==", true).where(userId, "==", Id).where("matchid", "==",${eventid}).orderBy("id","desc")
	.onSnapshot(function(querySnapshot) {
		var bets = [];
	  	 var betslength = [];
	  	 var i=0;
	   	querySnapshot.forEach(function(doc) {
			if(i<100){
		  		bets.push(doc.data());
		  		//console.log(doc.data())
			}
				betslength.push(doc.data());
	   	   		i++;
	   });


$("#fancy-betsCount").text("Matched-Bets("+betslength.length+")");
	
	   $("#fancy-bet").html('')
	   var source   = $("#fancyBetsHandler").html();
   	   var template = Handlebars.compile(source);
	   $("#fancy-bet").append( template({data:bets}) );
	    	 
    

});
 */
 /*  var i=0;
	
   db.collection("${eventid}").where("Fancy.isActive", "==", true)
	.onSnapshot(function(querySnapshot) {
		var fancyList = [];
	  	 var betslength = [];
	  querySnapshot.forEach(function(doc) {
			
	 		fancyList.push(doc.data()['Fancy']);
	  		
            i++;   
	   });

	 
	 		
	  var source   = $("#fancyHandler").html();
		var template = Handlebars.compile(source);
	    $("#fancyratesList").html( template({data1:fancyList}));
		
		 	
	 	
});*/

	/* 	
   db.collection("${eventid}").where("Odds.isActive", "==", true)
	.onSnapshot(function(querySnapshot) {
		var OddsList = [];
	  querySnapshot.forEach(function(doc) {
			
	 	
	 		
	  	   $.each(doc.data()['Odds']['runner'], function(key,value) {
		    	 $('#back1price'+value.selectionId).html(value.back1price);
	    		 $('#back2price'+value.selectionId).html(value.back2price);
	    		 $('#back3price'+value.selectionId).html(value.back3price);
	    		 $('#lay1price'+value.selectionId).html(value.lay1price);
	    		 $('#lay2price'+value.selectionId).html(value.lay2price);
	    		 $('#lay3price'+value.selectionId).html(value.lay3price);

	    		 $('#back1size'+value.selectionId).html(value.back1price);
	    		 $('#back2size'+value.selectionId).html(value.back2price);
	    		 $('#back3size'+value.selectionId).html(value.back3price);
	    		 $('#lay1size'+value.selectionId).html(value.lay1price);
	    		 $('#lay2size'+value.selectionId).html(value.lay2price);
	    		 $('#lay3size'+value.selectionId).html(value.lay3price);
	    			 
	    		
	    	});
	   });

			
	
	 	
});*/
 
function fancyMarket2HShowHide(fancyType){
   
		        if(fancyType == "RS"){
						$("#fancyratesList").show();
						$("#fancyratesListdiamond").hide();
	            }else{
                   if($("#fancymarket2diamond").val()>0){
                	   $("#fancyratesList").hide();
   	            	   $("#fancyratesListdiamond").show();
                       } 
	            	
	              }
	        }
   
	function nextPage(){
		startpage =startpage+100
		getMatchedBetsLoadMore(startpage,endpage);
	}function prevPage(){
		if(startpage>9){
			startpage =startpage-100
			getMatchedBetsLoadMore(startpage,endpage);
		}
		
	}
	 function getMatchedBetsLoadMore(startpage,endpage) {		    	
	   		
			$("#mainview").append('<div class="loader"></div>')	
 		
		 $.ajax({
	   			type:'GET',
	   			url:'<s:url value="/api/getAdminMatchedBets?eventid="/>'+${eventid}+"&startpage="+startpage,
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{
	    					var map = jsondata;
	    					 var match = $("#matchedCount").val();
								
			      				  var source   = $("#matchedBetsHandlerloadMore").html();
		    			    	  var template = Handlebars.compile(source);
		   				    	  $("#matchoddsbettable").append( template({data:map.matched}) );
		   				    	  $(".loader").fadeOut("slow");
	    					  
 					  
	   				}
	    		}
	    	});
	    }
	
	 function getMyProfit()
	 {

			var sport="-1";
	 	var type="2";

	 	if($("#myprofit").is(":checked")){
	 		type="1";
	 	}
	 	
	 	var data = {
	 		"sportid":sport,
	 		"usertype":"${user.usertype}",
	 		"table":type,
	 		"id":"${user.id}",
	 		"appid":"${user.appid}",
	 		"eventId":"${eventid}"
	 	};
	 		$.ajax({
	 			type:'POST',
	 			// url:'<s:url value="/api/getMyProfit"/>',
	 		     url:expo_base_url+'/api/getMyProfit',
	 		//	 url:'http://bm.com/api/getMyProfit',
	 			data: JSON.stringify(data),
	 			dataType: "json",
	 			contentType:"application/json; charset=utf-8",
	 			success: function(jsondata, textStatus, xhr) {
	 				if(xhr.status == 200)
	 				{
	 					var data = jsondata;
	 					
	 					for(var i=0;i<data.length;i++){
	 						   
	 							if(data[i].pnl1 < 0.00){
		    						$("#teampnl"+data[i].selection1).html(data[i].pnl1);
		    						$("#teampnl"+data[i].selection1).removeClass("positive");
		    						$("#teampnl"+data[i].selection1).addClass("negative");
		    					//	console.log("selectionId=>"+data[i].pnl1)  
	 							}else{
		    						$("#teampnl"+data[i].selection1).html(data[i].pnl1);
		    						$("#teampnl"+data[i].selection1).removeClass("negative");
		    						$("#teampnl"+data[i].selection1).addClass("positive");
		    						
	 							}
	 							if(data[i].pnl2 < 0.00){
		    						$("#teampnl"+data[i].selection2).html(data[i].pnl2);
		    						$("#teampnl"+data[i].selection2).removeClass("positive");
		    						$("#teampnl"+data[i].selection2).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection2).html(data[i].pnl2);
		    						$("#teampnl"+data[i].selection2).removeClass("negative");
		    						$("#teampnl"+data[i].selection2).addClass("positive");
	 							}
	 							if(data[i].pnl3 < 0.00){
		    						$("#teampnl"+data[i].selection3).html(data[i].pnl3);		
		    						$("#teampnl"+data[i].selection3).removeClass("positive");
		    						$("#teampnl"+data[i].selection3).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection3).html(data[i].pnl3);
		    						$("#teampnl"+data[i].selection3).removeClass("negative");
		    						$("#teampnl"+data[i].selection3).addClass("positive");
	 							}	
	 							if(data[i].pnl4 < 0.00){
		    						$("#teampnl"+data[i].selection4).html(data[i].pnl4);		
		    						$("#teampnl"+data[i].selection4).removeClass("positive");
		    						$("#teampnl"+data[i].selection4).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection4).html(data[i].pnl4);
		    						$("#teampnl"+data[i].selection4).removeClass("negative");
		    						$("#teampnl"+data[i].selection4).addClass("positive");
	 							}	
	 							if(data[i].pnl5 < 0.00){
		    						$("#teampnl"+data[i].selection5).html(data[i].pnl5);		
		    						$("#teampnl"+data[i].selection5).removeClass("positive");
		    						$("#teampnl"+data[i].selection5).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection5).html(data[i].pnl5);
		    						$("#teampnl"+data[i].selection5).removeClass("negative");
		    						$("#teampnl"+data[i].selection5).addClass("positive");
	 							}	
	 							if(data[i].pnl6 < 0.00){
		    						$("#teampnl"+data[i].selection6).html(data[i].pnl6);		
		    						$("#teampnl"+data[i].selection6).removeClass("positive");
		    						$("#teampnl"+data[i].selection6).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection6).html(data[i].pnl6);
		    						$("#teampnl"+data[i].selection6).removeClass("negative");
		    						$("#teampnl"+data[i].selection6).addClass("positive");
	 							}	
	 							if(data[i].pnl7 < 0.00){
		    						$("#teampnl"+data[i].selection7).html(data[i].pnl7);		
		    						$("#teampnl"+data[i].selection7).removeClass("positive");
		    						$("#teampnl"+data[i].selection7).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection7).html(data[i].pnl3);
		    						$("#teampnl"+data[i].selection7).removeClass("negative");
		    						$("#teampnl"+data[i].selection7).addClass("positive");
	 							}	
	 							if(data[i].pnl8 < 0.00){
		    						$("#teampnl"+data[i].selection8).html(data[i].pnl8);		
		    						$("#teampnl"+data[i].selection8).removeClass("positive");
		    						$("#teampnl"+data[i].selection8).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection8).html(data[i].pnl8);
		    						$("#teampnl"+data[i].selection8).removeClass("negative");
		    						$("#teampnl"+data[i].selection8).addClass("positive");
	 							}
	 							if(data[i].pnl9 < 0.00){
		    						$("#teampnl"+data[i].selection9).html(data[i].pnl9);		
		    						$("#teampnl"+data[i].selection9).removeClass("positive");
		    						$("#teampnl"+data[i].selection9).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection9).html(data[i].pnl9);
		    						$("#teampnl"+data[i].selection9).removeClass("negative");
		    						$("#teampnl"+data[i].selection9).addClass("positive");
	 							}
	 							if(data[i].pnl10 < 0.00){
		    						$("#teampnl"+data[i].selection10).html(data[i].pnl10);		
		    						$("#teampnl"+data[i].selection10).removeClass("positive");
		    						$("#teampnl"+data[i].selection10).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection10).html(data[i].pnl10);
		    						$("#teampnl"+data[i].selection10).removeClass("negative");
		    						$("#teampnl"+data[i].selection10).addClass("positive");
	 							}
	 							if(data[i].pnl11 < 0.00){
		    						$("#teampnl"+data[i].selection11).html(data[i].pnl11);		
		    						$("#teampnl"+data[i].selection11).removeClass("positive");
		    						$("#teampnl"+data[i].selection11).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection11).html(data[i].pnl11);
		    						$("#teampnl"+data[i].selection11).removeClass("negative");
		    						$("#teampnl"+data[i].selection11).addClass("positive");
	 							}
	 							if(data[i].pnl12 < 0.00){
		    						$("#teampnl"+data[i].selection12).html(data[i].pnl12);		
		    						$("#teampnl"+data[i].selection12).removeClass("positive");
		    						$("#teampnl"+data[i].selection12).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection12).html(data[i].pnl12);
		    						$("#teampnl"+data[i].selection12).removeClass("negative");
		    						$("#teampnl"+data[i].selection12).addClass("positive");
	 							}	
	 							if(data[i].pnl13 < 0.00){
		    						$("#teampnl"+data[i].selection13).html(data[i].pnl13);		
		    						$("#teampnl"+data[i].selection13).removeClass("positive");
		    						$("#teampnl"+data[i].selection13).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection13).html(data[i].pnl13);
		    						$("#teampnl"+data[i].selection13).removeClass("negative");
		    						$("#teampnl"+data[i].selection13).addClass("positive");
	 							}	

	 							if(data[i].pnl14 < 0.00){
		    						$("#teampnl"+data[i].selection14).html(data[i].pnl14);		
		    						$("#teampnl"+data[i].selection14).removeClass("positive");
		    						$("#teampnl"+data[i].selection14).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection14).html(data[i].pnl14);
		    						$("#teampnl"+data[i].selection14).removeClass("negative");
		    						$("#teampnl"+data[i].selection14).addClass("positive");
	 							}	

	 							if(data[i].pnl15 < 0.00){
		    						$("#teampnl"+data[i].selection15).html(data[i].pnl15);		
		    						$("#teampnl"+data[i].selection15).removeClass("positive");
		    						$("#teampnl"+data[i].selection15).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection15).html(data[i].pnl15);
		    						$("#teampnl"+data[i].selection15).removeClass("negative");
		    						$("#teampnl"+data[i].selection15).addClass("positive");
	 							}	

	 							if(data[i].pnl16 < 0.00){
		    						$("#teampnl"+data[i].selection16).html(data[i].pnl16);		
		    						$("#teampnl"+data[i].selection16).removeClass("positive");
		    						$("#teampnl"+data[i].selection16).addClass("negative");
	 							}else{
		    						$("#teampnl"+data[i].selection16).html(data[i].pnl16);
		    						$("#teampnl"+data[i].selection16).removeClass("negative");
		    						$("#teampnl"+data[i].selection16).addClass("positive");
	 							}				
	 						
	 					}
	 					
	 				}
	 		},
	 		complete: function(jsondata,textStatus,xhr) {
	 	
	 	    } 
	 	});
	 }
	 
	  function Userpnlhtml(){
		  $(".bookmodal").append('<div class="loader"></div>')	
	 		
	    	var type="2";

	    	var data = {
	    		"userType":"${user.usertype}",
	    		"eventid":"${eventid}",
	    		"marketid":"${marketid}",
	    		"table":type,
	    		"Id":'${user.id}'
	    	};
	    	url:expo_base_url
	    		$.ajax({
	   			type:'POST',
	   		  
	   			 url:expo_base_url+'/api/getUserProfitNew',
	   			/* url:"http://bm.com/api/getUserProfitNew",*/

	 			data: JSON.stringify(data),
	 			dataType: "json",
	 			contentType:"application/json; charset=utf-8",
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{     
		    				
	    					var obj = jsondata.bf;
		    				var objbm = jsondata.bm;
	    					
	    				
								$('#user-profitmatchodds').html('');
	        					var source   = $("#userpnlHandler").html();
	      			    	 	var template= Handlebars.compile(source);
	     				    	$("#user-profitmatchodds").append( template({data:jsondata.bf})); 
								 
	     				    	 $('#user-profitbookmaker').html('');
	        					var sourcebookmaker   = $("#userpnlBookmakerHandler").html();
	      			    	 	var templatebookmaker= Handlebars.compile(sourcebookmaker);
	     				    	$("#user-profitbookmaker").append( templatebookmaker({data:jsondata.bm})); 
	     				    	$(".loader").fadeOut("slow");

	     				    	    $('#user-profitbookmaker3').html('');
		        					var sourcebookmaker3   = $("#userpnlBookmaker3Handler").html();
		      			    	 	var templatebookmaker3= Handlebars.compile(sourcebookmaker3);
		     				    	$("#user-profitbookmaker3").append( templatebookmaker3({data:jsondata.bm3})); 
		     				    	$(".loader").fadeOut("slow");
	    					
	    				}else{
	    					$('#user-profitmatchodds').html('');
	    					$('#user-profitbookmaker').html('');
	    					$('#user-profitbookmaker3').html('');
	    					$("#noUser").show();
	    				}
	    			  //  Userpnlhtml();
	  			}
	  		});
	    }



	  function UserCounterData(){
		 // $(".bookmodal").append('<div class="loader"></div>')	
	 		
	    	
	    	url:expo_base_url
	    		$.ajax({
	   			type:'GET',
	   		  	url:'<s:url value="/api/getMatchUserCount/${eventid}"/>',
	   			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{     
		    				
		    				$('#user-counter').html('');
        					var source   = $("#userCounterHandler").html();
      			    	 	var template= Handlebars.compile(source);
     				    	$("#user-counter").append( template({data:jsondata})); 
							 
	    					
	    				}else{
	    					
	    				}
	    			  //  Userpnlhtml();
	  			}
	  		});
	    }

	  
	  function PausePlay(marketId){	
	         
	    	  $.ajax({
		      type:'PUT',
		      url:'<s:url value="/api/pauseplaymarket?marketid="/>'+marketId,
		      success: function(jsondata, textStatus, xhr) {
		      {
		    	if(xhr.status == 200){     
		    	showMessage(jsondata.message,jsondata.type,jsondata.title);
		    	location.reload();
		       }
		    }
		 }
	   });
	  }


	  function activeFancy(){
		  
			 $.ajax({
				    type:'GET',
				    url:activeFancy_url+'/api/activeFancyNew/'+'${eventid}/'+Id+"/"+'${user.usertype}',
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
					
						if(xhr.status == 200){ 

							$.each(jsondata, function(index, element) {
								var checkedNew = element.fancyid.split('.').join("");
                                 $("#fancyexpo"+checkedNew).html(element.pnl)
                                            
                                         //   console.log(('Content of div: ' + document.getElementById('fancyexpo'+element.fancyid).textContent))
//document.getElementById('fancyexpo'+element.fancyid).innerHTML += '';
                                      //      document.getElementById('fancyexpo'+element.fancyid).innerHTML += element.pnl;
						         });
                                    
					/*	var source   = $("#fancyHandler").html();
							var template = Handlebars.compile(source);
					 	    $("#fancyratesList").html( template({data1:jsondata}));
					 	   db.collection("${eventid}").where("Fancy.isActive", "==", true)
					 		.onSnapshot(function(querySnapshot) {
					 			 	querySnapshot.forEach(function(doc) {
					 				
					 			  		
					 			  		if(doc.data()['Fancy'].status == "OPEN"){
					 			  			$("#fancy_status"+doc.data()['Fancy'].fancyId).addClass("active")
					 			  			$("#fancy_status"+doc.data()['Fancy'].fancyId).removeClass("suspended suspended-body")
						 			  		}else{
						 			  			$("#fancy_status"+doc.data()['Fancy'].fancyId).addClass("suspended suspended-body")
						 			  			$("#fancy_status"+doc.data()['Fancy'].fancyId).removeClass("active")
							 			  		}
					 			  		$("#fancy_Name"+doc.data()['Fancy'].fancyId).html(doc.data()['Fancy'].runnerName)
					 			  		$("#min_max_fancy"+doc.data()['Fancy'].fancyId).html("min: "+doc.data()['Fancy'].minBet+ "<br> max: "+doc.data()['Fancy'].maxBet+"")
						 				$("#backDiv"+doc.data()['Fancy'].fancyId).html("<span><strong>"+doc.data()['Fancy'].back1price+ "</strong></span><br><span>"+doc.data()['Fancy'].back1size+ "</span>")
						 				$("#layDiv"+doc.data()['Fancy'].fancyId).html("<span><strong>"+doc.data()['Fancy'].lay1price+ "</strong></span><br><span>"+doc.data()['Fancy'].lay1size+ "</span>")
						 				
					 			  		
					 		   });



					 	});  */
								
						}else{
							    $("#fancyratesList").html('');
						}
						
					},
			 
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			  });
			} 

	  function getFancyBook(fancyid,back,lay,matchId)
	    {
		  $(".details-box").append('<div class="loader"></div>')	
	 		
	    	 $("#noBook").hide();
	    		$("#fancyBook").html('');
	    		 $('#fancyBookModal').modal('show'); 
	    		    $("#fancyBookTable").html('');
	    	if(back!="Ball Running" && back!="SUSPENDED"){
	    		$.ajax({
		   			type:'GET',
		   			url:'<s:url value="/api/getParentFancyBookNew?fancyid="/>'+fancyid+"&back="+back+"&lay="+lay+"&matchId="+'${eventid}',
		  			success: function(jsondata, textStatus, xhr) {
		    				if(xhr.status == 200)
		    				{   
			    			   

		    					var source   = $("#fancyBookHandler").html();
		    					var template = Handlebars.compile(source);
		    			 	    $("#fancyBookTable").append( template({data:jsondata})); 
		    					
		   					}else{
		    					$("#fancyBook").html('');
		    			    	 $("#noBook").show();
		    			    	 $("#fancyBook").hide();
		   					}
		    				$(".loader").fadeOut("slow");
		    		}
		    	});
	    	} 
	   		
	    }

         
 
   
      function removeSBANetExposure(){
   		$.ajax({
  	   			type:'DELETE',
  	   			url:'<s:url value="/api/removeEventExposure?eventid="/>'+${eventid},
  	  			success: function(jsondata, textStatus, xhr) {
  	    				if(xhr.status == 200)
  	    				{   
  	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
  	    					netExposureEventStatus();
  	   					}else{
  	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
  	   					}
  	    		}
  	
  	 });
    }
     
     function minMaxEventStatus(){
    	 $.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/minMaxEventStatus?eventid="/>'+${eventid},
	  			success: function(jsondata, textStatus, xhr) {
	  			//	console.log("minMaxEventStatus: "+jsondata)
	    				if(xhr.status == 200)
	    				{   
	    					if(${user.usertype} == 5){
	    					    if(jsondata.userid != null){
	    					    	$("#removeMinMaxEvent").show();
	                                $("#updateMinMaxEvent").hide();
	    					    } else {
	    					    	$("#updateMinMaxEvent").show();
	                                $("#removeMinMaxEvent").hide();
	    					    } 
	    					 }
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	 });
    }
     
 
   
	//setInterval(function(){  matchOdds("repeate")}, 1000);

	function loadTv(){


		 if($("#tvvalue").val() == "false"){
	    	      $("#tvvalue").val(true);
	    	    // $( "#live_tv-div" ).after( '<div id="liveStream"><button class="tv-button"  onclick="loadLiveTv(1)" >Tv1</button><button class="tv-button"  onclick="loadLiveTv(2)">Tv2</button><button class="tv-button" onclick="loadLiveTv(3)">Tv3</button><button class="tv-button" onclick="loadLiveTv(4)" id="matchbetLockSBA">Tv4</button></div>' );
	    	      $( "#live_tv-div1" ).after('<div id="liveStreamTv1"><div id="video-tv" align="center" class="tv-container collapse show" style=""> <iframe id="gamelivetv" width="100%" height="400" src="http://18.168.176.234/tv/" ></iframe> </div></div>');
	    	 }else{
	     	        $("#tvvalue").val(false);
	     	        $( "#liveStream1" ).remove();
	     	        $("#liveStreamTv1").remove('');
	           }

		    $("#tvvalue2").val(false);
   	        $( "#liveStreamTv2").remove();
   	        $( "#liveStream2").remove();
		 }

	function loadLiveTv(id){

          $("#liveStreamTv2").html('');
		 $( "#liveStream2" ).after('<div id="liveStreamTv2"><div id="video-tv" align="center" class="tv-container collapse show" style=""> <iframe id="gamelivetv" width="100%" height="280" src="http://bm.com:8082/data-provider/scoreApi?evntId=31496201" ></iframe> </div></div>');
         // $( "#liveStream" ).after( '<div id="liveStream"><button class="tv-button"  onclick="loadLiveTv(1)" >Tv1</button><button class="tv-button"  onclick="loadLiveTv(2)">Tv2</button><button class="tv-button" onclick="loadLiveTv(3)">Tv3</button><button class="tv-button" onclick="loadLiveTv(4)" id="matchbetLockSBA">Tv4</button></div>' );
   	   
          }
	

	function loadTv2(){
	       
			 if($("#tvvalue2").val() == "false"){
				
			   	$("#tvvalue2").val(true);
	   	        // $( "#live_tv-div" ).after( '<div id="liveStream"><button class="tv-button"  onclick="loadLiveTv(1)" >Tv1</button><button class="tv-button"  onclick="loadLiveTv(2)">Tv2</button><button class="tv-button" onclick="loadLiveTv(3)">Tv3</button><button class="tv-button" onclick="loadLiveTv(4)" id="matchbetLockSBA">Tv4</button></div>' );
	   	        //  $( "#llive_tv-div2" ).after('<div id="liveStreamTv"><div id="video-tv" align="center" class="tv-container collapse show" style=""> <iframe id="gamelivetv" width="100%" height="400" src="http://18.135.67.118/tv/" ></iframe> </div></div>');
	   	  		
	       	     $( "#live_tv-div2" ).after( '<div id="liveStream2"><button class="tv-button"  onclick="loadLiveTv(1)" >Tv1</button><button class="tv-button"  onclick="loadLiveTv(2)">Tv2</button><button class="tv-button" onclick="loadLiveTv(3)">Tv3</button><button class="tv-button" onclick="loadLiveTv(4)" id="matchbetLockSBA">Tv4</button></div>' );
		   }else{
	    	   
	    	    $("#tvvalue2").val(false);
	    	    $( "#liveStreamTv2").remove();
	    	    $( "#liveStream2").remove();
	    	 }

			 $("#tvvalue").val(false);
	  	     $( "#liveStream1" ).remove();
	  	     $("#liveStreamTv1").remove('');
		 
		 }


		  

	
	function eventDetail(){
		  
		  $.ajax({
	 			type:'GET',
	 			url:'<s:url value="/api/eventDetail/"/>'+'${eventid}',
				success: function(jsondata, textStatus, xhr) {
					if(xhr.status == 200)
	  				{   
	                   if(jsondata.liveTv){
	                	   $( "#live_tv-div" ).after( '<div id="liveStream"><div id="video-tv" align="center" class="tv-container collapse show" style=""> <iframe src="<s:url value="/api/tvApi?eventId=${eventid}"/>" width="100%" height="250" class="video-iframe"></iframe> </div></div>' );
	                   }
	  					
	 			   }
	  		}
	  	});
	}

	function marketDetail(){
		  $.ajax({
	 			type:'GET',
	 			url:'<s:url value="/api/marketDetail/"/>'+'${marketid}',
				success: function(jsondata, textStatus, xhr) {
					if(xhr.status == 200)
	  				{   
                      if(jsondata.odds_suspension_on_bm_status =="both"){
                    	  $("#oddsstatus").append('<option value="both" selected >BOTH</option><option value="ball_running">BALL_RUNNING</option><option value="suspended">SUSPENDED</option><option value="off">OFF</option>')
                          }else  if(jsondata.odds_suspension_on_bm_status =="off"){
                        	  $("#oddsstatus").append('<option value="both" >BOTH</option><option value="ball_running">BALL_RUNNING</option><option value="suspended">SUSPENDED</option><option value="off" selected>OFF</option>')
                                  }
                          else  if(jsondata.odds_suspension_on_bm_status =="suspended"){
                        	  $("#oddsstatus").append('<option value="both" >BOTH</option><option value="ball_running">BALL_RUNNING</option><option value="suspended" selected>SUSPENDED</option><option value="off">OFF</option>')
                                    }
                          else  if(jsondata.odds_suspension_on_bm_status =="ball_running"){
                        	  $("#oddsstatus").append('<option value="both" >BOTH</option><option value="ball_running" selected>BALL_RUNNING</option><option value="suspended">SUSPENDED</option><option value="off">OFF</option>')
                                  }
						
	  					
	 			   }
	  		}
	  	});
	}

	function getAdminViewMoreBets(type){		    	
      
		$("#view-more-matched-tbl").append('<div class="loader"></div>')	
 		
		$("#viewmoretable").html('');
		$('#viewMoreModal').modal('show');
		
		$.ajax({
			type:'GET',
			url:expo_base_url+"/api/getAdminViewMoreBets/${eventid}/${user.id}/${user.usertype}/"+type,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					var map = jsondata;
					
	      			   
					  
	      				  $("#viewmoretable").html('')
    					  var source   = $("#ViewMoreBetsHandler").html();
    			    	  var template = Handlebars.compile(source);
   				    	  $("#viewmoretable").append( template({data:map.matched}) );
   				    	

					  
					  $(".loader").fadeOut("slow");
					  
				}
		}
	});
}	

	function scoreData(){		    	
    
	
	$.ajax({
		type:'GET',
		url:base_url+"/api/scoreApi/"+${eventid},
		success: function(jsondata, textStatus, xhr) {
			if(xhr.status == 200)
			{
				
				
				  
      			//	console.log("jsona"+jsondata)
				  
      			  $(".score_div").html('')
				 $("#score_div").html(jsondata);
		    	 
			}
	}
});
}

function teamName(){		    	
    
//	alert('${marketid}')
	$.ajax({
		type:'GET',
		url:'<s:url value="/api/teamname/${marketid}"/>',
		
		success: function(jsondata, textStatus, xhr) {
			if(xhr.status == 200)
			{  
				 $.each(jsondata, function( key, value ) { 
					 
					 
			     });	 
			    // console.log("length"+jsondata.length) 	
			     if(jsondata.length>2){
			    	 $("#t1m").html(jsondata[0].runnerName)
					  $("#t2m").html(jsondata[1].runnerName)
					  $("#t3m").html(jsondata[2].runnerName)
					   $("#t1b").html(jsondata[0].runnerName)
					  $("#t2b").html(jsondata[1].runnerName)
					  $("#t3b").html(jsondata[2].runnerName)
				     }else{
				    	 $("#t1m").html(jsondata[0].runnerName)
						  $("#t2m").html(jsondata[1].runnerName)
						   $("#t1b").html(jsondata[0].runnerName)
						  $("#t2b").html(jsondata[1].runnerName)
						  }			  
				
				  // $("#t3).html(jsondata[2].runnerName)
			}
	}
});
}



function getBookMakerBackLayDiff(backdiff,laydiff) {

var i=0;
$.ajax({

	type:'GET',
	url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=betdelay',
	contentType:"application/json; charset=utf-8",
	success: function(jsondata, textStatus, xhr) {
		
	   $.each(jsondata, function( key, value ) {
		   if(backdiff ==value.amount){
			   $("#bmbackdiff").append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
			}else{
				$("#bmbackdiff").append('<option value="'+value.amount+'">'+value.amount+'</option>')
				}
		   if(laydiff ==value.amount){
			   $("#bmlaydiff").append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
			}else{
				 $("#bmlaydiff").append('<option value="'+value.amount+'">'+value.amount+'</option>')
			}
		   
		   
		   
	});
	},
	complete: function(jsondata,textStatus,xhr) {
		
    } 
});     	    	
}










function userDetail(betid,id){
	
	$(".userdetail").html('');
	  $.ajax({
			type:'GET',
			url:'<s:url value="/api/userHerarchy/"/>'+betid,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
	
		 if(xhr.status == 200){     
			 $("#userdetail"+betid).html("<span>"+jsondata.response+"</span>")
	 }
	}
});
			
}

	 function fancyShowHide(fancyId,isShow){
			
			$(".userdetail").html('');
			  $.ajax({
					type:'POST',
					url:'<s:url value="/api/fancyShowHide/"/>'+fancyId+"/"+isShow,
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						
				
			}
		});
					
		}
	 function getAddminBet(){		    	
	      
			$
			$.ajax({
				type:'GET',
				url:betlist_base_url+"/api/getAddminBet/${eventid}/${user.usertype}/${user.id}/",
				success: function(jsondata, textStatus, xhr) {
					if(xhr.status == 200)
					{ 
		      			    $("#matched-betsCountBookmaker").text("Matched-Bets("+jsondata.bookmakerbetcount+")");
		      				$("#match-betbookmaker").html('')
		      				var source   = $("#matchedBetsBookMakerHandler").html();
		      				var template = Handlebars.compile(source);
		      			  	$("#match-betbookmaker").append( template({data:jsondata.bookmakerbets}) );


		      			    $("#matched-betsCountBookmaker3").text("Matched-Bets("+jsondata.bookmaker3betcount+")");
		      				$("#match-betbookmaker3").html('')
		      				var source   = $("#matchedBetsBookMaker3Handler").html();
		      				var template = Handlebars.compile(source);
		      			  	$("#match-betbookmaker3").append( template({data:jsondata.bookmaker3bets}) );
		      			  	

		      			    $("#matchedCount").val(jsondata.matchedcount); 
		      		 	    $("#matched-betsCount").text("Matched-Bets("+jsondata.matchedcount+")");
		      			
		      			   $("#match-bet").html('')
		      			   var source   = $("#matchedBetsHandler").html();
		      		   	   var template = Handlebars.compile(source);
		      			   $("#match-bet").append( template({data:jsondata.matched}) );


		      			 $("#fancy-betsCount").text("Matched-Bets("+jsondata.fancycount+")");
		      			
		      		   $("#fancy-bet").html('')
		      		   var source   = $("#fancyBetsHandler").html();
		      	   	   var template = Handlebars.compile(source);
		      		   $("#fancy-bet").append( template({data:jsondata.fancy}) );
		      		    	 
						  
					}
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});
	}	

	 function getBetsByMarketId(marketId){		    	
	     // $("#otherBetsModal").show();
		
		$("#other-bets-div").html('');
		$('#otherBetsModal').modal('show');
		
		$("#otherBetsModal").append('<div class="loader"></div>')	
 		
			$.ajax({
				type:'GET',
				url:betlist_base_url+"/api/getBetsByMarketId/${eventid}/${user.usertype}/${user.id}/"+marketId,
				success: function(jsondata, textStatus, xhr) {
					if(xhr.status == 200)
					{ 
		      			


		      		  	
		      		   $("#other-bets-div").html('')
		      		   var source   = $("#fancyBetsHandler").html();
		      	   	   var template = Handlebars.compile(source);
		      		   $("#other-bets-div").append( template({data:jsondata.fancy}) );
		      		   $("#view_more").hide()
		      		 $(".loader").fadeOut("slow"); 	 
						  
					}
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});
	}	

	 function matchOdds(type){
			
		  
		    $.ajax({
					type:'GET',
					  headers: {
					        'Authorization':'Bearer ${token}'
					    },
					  
					url:bfApi+'event-odds/odds-auth/'+${eventid},
					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200)
						{ 		
							if(type =="onLoad"){
							 	 var source   = $("#oddshandler").html();
								var template = Handlebars.compile(source);



								
							    $("#matchoddsdiv").html( template({data1:jsondata.data})); 
							    if('${user.usertype}'!=6){
							    	getMyProfit(); 
							    }
	                                

							    var source   = $("#fancyHandler").html();
			    				var template = Handlebars.compile(source);
			    		 	    $("#fancyratesList").html( template({data1:jsondata.data.Bookmaker}));

			    		 	   

				    		 	if(jsondata.data.hasOwnProperty('Fancy2Market')){

				    		 		
				    		 		 var source   = $("#fancyHandler").html();
					    			 var template = Handlebars.compile(source);
					    		 	 $("#fancyratesList").html( template({data1:jsondata.data.Fancy2Market}));
									 $("#fancymarket2").val(jsondata.data.Fancy2Market.length)	
				    				
				    		 	}

				    			if(jsondata.data.hasOwnProperty('Fancy2DiamondMarket')){

				    		 		
				    		 		 var source   = $("#fancyHandlerDiamond").html();
					    			 var template = Handlebars.compile(source);
					    		 	 $("#fancyratesListdiamond").html( template({data1:jsondata.data.Fancy2DiamondMarket}));
									 $("#fancymarket2diamond").val(jsondata.data.Fancy2DiamondMarket.length)	
				    				
				    		 	}
				    		 	
				    		 	if(jsondata.data.hasOwnProperty('Fancy3Market')){

				    		 		$("#fancyMarket3HandlerratesList").show();
				    		 		 var sourcefancyMarkr3Handler   = $("#fancyMarket3Handler").html();
					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
					    		 	 $("#fancyMarket3HandlerratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.Fancy3Market}));
					    		 	$("#fancymarket3").val(jsondata.data.Fancy3Market.length)	
				    				
				    		 	}
				    				

				    		 	if(jsondata.data.hasOwnProperty('OddEven')){

				    		 		$("#oddevenratesList").show();
				    		 		 var sourcefancyMarkr3Handler   = $("#oddEvenfancyMarketHandler").html();
					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
					    		 	 $("#oddevenratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.OddEven}));
					    		 	$("#oddevenmarket").val(jsondata.data.OddEven.length)	
				    				
				    				
				    		 	}

				    		 	if(jsondata.data.hasOwnProperty('BallSession')){

				    		 		 $("#ballbyballratesList").show();
				    		 		 var sourcefancyMarkr3Handler   = $("#ballByBallfancyMarketHandler").html();
					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
					    		 	 $("#ballbyballratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.BallSession}));
				    				 
				    		 	}
				    		 	 if('${user.usertype}'!=6){
				    		 		activeFancy();
				    		 	 }
				    		 	
							}else{



								if(jsondata.data.hasOwnProperty('Fancy2Market')){

				    		 		 if($("#fancymarket2").val() ==  jsondata.data.Fancy2Market.length){
                                    	   
                                    	     $.each(jsondata.data.Fancy2Market, function( key, value ) {
                                    	    	/*  if(value.fancyId == "4.1486384268-F2"){
                                    	    		 console.log("fancyId=>"+value.fancyId+"status==>"+value.status)
                                         	    	 
                                        	    	 } */
     												if(value.status == "SUSPENDED"){
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
 													 
													   
												}else if(value.status =="OPEN"){

													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("active")
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("suspended")
													}else{

														 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
														 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
															
													}

     												 $("#lay1size"+value.fancyId.split('.').join("")).html(value.lay1size)
     												 $("#back1size"+value.fancyId.split('.').join("")).html(value.back1size)
     												 $("#back1price"+value.fancyId.split('.').join("")).html(value.back1price)
     												 $("#lay1price"+value.fancyId.split('.').join("")).html(value.lay1price)
     												
                 	   				    	 });
                                         }else{
                                        	    var source   = $("#fancyHandler").html();
        					    			    var template = Handlebars.compile(source);
        					    		 	    $("#fancyratesList").html( template({data1:jsondata.data.Fancy2Market}));
        									    $("#fancymarket2").val(jsondata.data.Fancy2Market.length)	
                                            }
                                        
				    				
				    		 	}

								if(jsondata.data.hasOwnProperty('Fancy2DiamondMarket')){

				    		 		 if($("#fancymarket2diamond").val() ==  jsondata.data.Fancy2DiamondMarket.length){
                                  	   
                                  	     $.each(jsondata.data.Fancy2DiamondMarket, function( key, value ) {
                                  	    	
   												if(value.status == "SUSPENDED"){
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
													 
													   
												}else if(value.status =="OPEN"){

													
													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("active")
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("suspended")
													}else{

														 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
														 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
															
													}

   												 $("#lay1size"+value.fancyId.split('.').join("")).html(value.lay1size)
   												 $("#back1size"+value.fancyId.split('.').join("")).html(value.back1size)
   												 $("#back1price"+value.fancyId.split('.').join("")).html(value.back1price)
   												 $("#lay1price"+value.fancyId.split('.').join("")).html(value.lay1price)
   												
               	   				    	 });
                                       }else{
                                      	    var source   = $("#fancyHandler").html();
      					    			    var template = Handlebars.compile(source);
      					    		 	    $("#fancyratesListdiamond").html( template({data1:jsondata.data.Fancy2DiamondMarket}));
      									    $("#fancymarket2diamond").val(jsondata.data.Fancy2DiamondMarket.length)	
                                          }
                                      
				    				
				    		 	}
				    		 	

							
				    		 	if(jsondata.data.hasOwnProperty('Fancy3Market')){

				    		 	

				    		 		 if($("#fancymarket3").val() ==  jsondata.data.Fancy3Market.length){
                                  	   
                                	     $.each(jsondata.data.Fancy3Market, function( key, value ) {
                                	    	
 												if(value.status == "SUSPENDED"){
												 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
												 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
													 
												   
											}else if(value.status =="OPEN"){

												 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("active")
												 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("suspended")
												}else{

													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
														
												}

 												 $("#lay1size"+value.fancyId.split('.').join("")).html(value.lay1size)
 												 $("#back1size"+value.fancyId.split('.').join("")).html(value.back1size)
 												 $("#back1price"+value.fancyId.split('.').join("")).html(value.back1price)
 												 $("#lay1price"+value.fancyId.split('.').join("")).html(value.lay1price)
 												
             	   				    	 });
                                     }else{
                                    	 $("#fancyMarket3HandlerratesList").show();
    				    		 		 var sourcefancyMarkr3Handler   = $("#fancyMarket3Handler").html();
    					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
    					    		 	 $("#fancyMarket3HandlerratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.Fancy3Market}));
                                       }
                                 }
				    				

				    		 	if(jsondata.data.hasOwnProperty('OddEven')){

				    		 		 /*$("#oddevenratesList").show();
				    		 		 var sourcefancyMarkr3Handler   = $("#oddEvenfancyMarketHandler").html();
					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
					    		 	 $("#oddevenratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.OddEven}));*/



				    		 		 if($("#oddevenmarket").val() ==  jsondata.data.OddEven.length){
                                 	   
                               	     $.each(jsondata.data.OddEven, function( key, value ) {
                               	    	
												if(value.status == "SUSPENDED"){
												 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
												 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
													 
												   
											}else if(value.status =="OPEN"){

												 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("active")
												 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("suspended")
												}else{

													 $("#fancy_status"+value.fancyId.split('.').join("")).addClass("suspended")
													 $("#fancy_status"+value.fancyId.split('.').join("")).removeClass("active")
														
												}

												 $("#lay1size"+value.fancyId.split('.').join("")).html(value.lay1size)
												 $("#back1size"+value.fancyId.split('.').join("")).html(value.back1size)
												 $("#back1price"+value.fancyId.split('.').join("")).html(value.back1price)
												 $("#lay1price"+value.fancyId.split('.').join("")).html(value.lay1price)
												
            	   				    	 });
                                    }else{
                                    	$("#oddevenratesList").show();
   				    		 		 var sourcefancyMarkr3Handler   = $("#oddEvenfancyMarketHandler").html();
   					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
   					    		 	 $("#oddevenratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.OddEven}));
                                      }
                                   
			    				
			    		 	
				    				
				    		 	}

				    		 	if(jsondata.data.hasOwnProperty('BallSession')){

				    		 		 $("#ballbyballratesList").show();
				    		 		 var sourcefancyMarkr3Handler   = $("#ballByBallfancyMarketHandler").html();
					    			 var templatefancyMarkr3Handler = Handlebars.compile(sourcefancyMarkr3Handler);
					    		 	 $("#ballbyballratesList").html( templatefancyMarkr3Handler({data1:jsondata.data.BallSession}));
				    				 
				    		 	}
				    			
								  $.each(jsondata.data.Bookmaker, function(key,value) {
									  
									   $.each(value.runner, function(keys,values) {
										

										 $('#back1price'+values.selectionId).html(values.back1price);
							    		 $('#back2price'+values.selectionId).html(values.back2price);
							    		 $('#back3price'+values.selectionId).html(values.back3price);
							    		 $('#lay1price'+values.selectionId).html(values.lay1price);
							    		 $('#lay2price'+values.selectionId).html(values.lay2price);
							    		 $('#lay3price'+values.selectionId).html(values.lay3price);

							    		 $('#back1size'+values.selectionId).html(values.back1price);
							    		 $('#back2size'+values.selectionId).html(values.back2price);
							    		 $('#back3size'+values.selectionId).html(values.back3price);
							    		 $('#lay1size'+values.selectionId).html(values.lay1price);
							    		 $('#lay2size'+values.selectionId).html(values.lay2price);
							    		 $('#lay3size'+values.selectionId).html(values.lay3price);

							    		 if(value.type=="Odds"){
							    			 if(values.runnerStatus == "SUSPENDED"  || values.runnerStatus == "CLOSED"){
													$("#team_row"+values.selectionId).addClass("suspended")
													$("#team_row"+values.selectionId).removeClass("active")
										    	}else{
										    		$("#team_row"+values.selectionId).removeClass("suspended")
										    		$("#team_row"+values.selectionId).addClass("active")
											    	}
								    		 }
							    		
										
									   });
									  
								    });


								  $.each(jsondata.data.Odds, function(key,value) {

									 console.log(value.matchMessage)
										  $("#messge_text").html(value.matchMessage)
									   $.each(value.runner, function(keys,values) {
										

										 $('#back1price'+values.selectionId).html(values.back1price);
							    		 $('#back2price'+values.selectionId).html(values.back2price);
							    		 $('#back3price'+values.selectionId).html(values.back3price);
							    		 $('#lay1price'+values.selectionId).html(values.lay1price);
							    		 $('#lay2price'+values.selectionId).html(values.lay2price);
							    		 $('#lay3price'+values.selectionId).html(values.lay3price);

							    		 $('#back1size'+values.selectionId).html(values.back1price);
							    		 $('#back2size'+values.selectionId).html(values.back2price);
							    		 $('#back3size'+values.selectionId).html(values.back3price);
							    		 $('#lay1size'+values.selectionId).html(values.lay1price);
							    		 $('#lay2size'+values.selectionId).html(values.lay2price);
							    		 $('#lay3size'+values.selectionId).html(values.lay3price);

							    		 if(value.type=="Odds"){
							    			 if(values.runnerStatus == "SUSPENDED"){
													$("#team_row"+values.selectionId).addClass("suspended")
													$("#team_row"+values.selectionId).removeClass("active")
										    	}else{
										    		$("#team_row"+values.selectionId).removeClass("suspended")
										    		$("#team_row"+values.selectionId).addClass("active")
											    	}

										    	
								    		 }
							    		
										
									   });
									  
								    });
								    
								}	
								
						   }
				      }
			});
		  
	   }


	 function setMessage() {

		    var message = $("#setmessage").val();
		    var matchid = ${eventid};
		    
			if($.trim(message).length>0)	{
		
			var data = {message:message,matchid:matchid}
		    
		    swal({
 	      title: 'Are you sure?',
 	      text: 'You Want to set this Message!',
 	      type: 'warning',
 	      showCancelButton: true,
 	      confirmButtonColor: '#3085d6',
 	      cancelButtonColor: '#d33',
 	      confirmButtonText: 'Yes',
 	      closeOnConfirm: false
 	    }).then(function(isConfirm) {
 	      if (isConfirm) {	
		   	 $.ajax({
		   	 
					type:'POST',
					//url:'http://localhost/api/setMessage',
					url:'<s:url value="/api/setMatchMessage"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						 
						$('#add_here').remove();
						$("#setmessage").val('');
						
						
						location.reload();
						
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				    } 
				}); 
				
				 } 
				}); 
				
				}  
			else
 	{
 	showMessage("Kindly Type a Message","error","Oops...");
 	}
 	    	
		    }
	   function showOtherBets(){
		  
		   $("#other-bets-div").append('<div class="loader"></div>')	
	 		
			$("#other-bets-div").html('');
			$('#otherBetsModal').modal('show');
			
	   }

	 // setInterval(function(){ scoreData()}, 5000)
	  setInterval(function(){ matchOdds("Repeate")}, 500)
	  
		   
	 if('${user.usertype}'!=6){
		 setInterval(function(){ activeFancy()}, 10000)
		  setInterval(function(){ getMyProfit()}, 8000)
	 }


	  function updateMarketOddsStatus(){


			$("#lotusmatch").append('<div class="loader"></div>')
			$.ajax({
				type:'PUT',
				url :'<s:url value="/api/updateMarketOddsStatus/${marketid}/"/>'+$("#oddsstatus").val(),
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			   } 
			});
		  	
		}	    		 	

	  function updateBookMakerBackDiff(){


			$("#lotusmatch").append('<div class="loader"></div>')
			$.ajax({
				type:'POST',
				url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/"+$("#bmbackdiff").val()+"/0",
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					checkBookMakerBackLayDiff();
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			   } 
			});
		  	
		}

		function updateBookMakerBackDiffOff(){


			$("#lotusmatch").append('<div class="loader"></div>')
			$.ajax({
				type:'POST',
				url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/0/0",
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					checkBookMakerBackLayDiff();
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			   } 
			});
		  	
		}


		function updateBookMakerBackLayDiff(){


				   	 swal({
		  title: 'Are you sure?',
		  text: 'You Want to Update BookMaker Diffrance',
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes',
		  closeOnConfirm: false
		}).then(function(isConfirm) {
		  if (isConfirm) {
			  $("#lotusmatch").append('<div class="loader"></div>')
			  $.ajax({
					type:'POST',
					url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/"+$("#bmbackdiff").val()+"/"+$("#bmlaydiff").val(),
					success: function(jsondata, textStatus, xhr) {
					$(".loader").fadeOut("slow");
						showMessage(jsondata.message,jsondata.type,jsondata.title);
					},
					complete: function(jsondata,textStatus,xhr) {
					$(".loader").fadeOut("slow");
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						
				    } 
				});
			
			} 
		     
		});
		}


		function updateBookMakerLayDiff(){


			$.ajax({
				type:'POST',
				url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/0/"+$("#bmlaydiff").val(),
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					checkBookMakerBackLayDiff();
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			   } 
			});
		}


		function checkBookMakerBackLayDiff(){


			$.ajax({
				type:'GET',
				url :'<s:url value="/api/checkBookMakerBackLayDiff/"/>'+${eventid},
				success: function(jsondata, textStatus, xhr) {

				   if(jsondata.laydiff ==0){
						$("#BookMakerLayDiff").show();
						$("#BookMakerLayDiffOff").hide();
					   }else{
						   $("#BookMakerLayDiff").hide();
							$("#BookMakerLayDiffOff").show();
					    }

				   if(jsondata.backdiff ==0){
						$("#BookMakerBackDiff").show();
						$("#BookMakerBackDiffOff").hide();
					   }else{
						   $("#BookMakerBackDiff").hide();
							$("#BookMakerBackDiffOff").show();
					    }
					
				},
				complete: function(jsondata,textStatus,xhr) {
				
			   } 
			});
		  	
		}


				 
	  setInterval(function(){ getAddminBet()}, 10000)
		

	 
	  //shakti
</script>

   <input type ="hidden" value="false" id="tvvalue">
     <input type ="hidden" value="false" id="tvvalue2">
     <input type ="hidden" value="" id="maximumbethiden">
      <input type ="hidden" value="" id="fancymarket2">
       <input type ="hidden" value="0" id="fancymarket2diamond">
      <input type ="hidden" value="" id="fancymarket3">
      <input type ="hidden" value="" id="oddevenmarket">
      
      <jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
     <jsp:include page="userbook.jsp" />
     <jsp:include page="bookmakerbooks.jsp" />
     <jsp:include page="bookmaker3book.jsp" />
     <jsp:include page="userCounter.jsp" />
<jsp:include page="handlebars.jsp" />  