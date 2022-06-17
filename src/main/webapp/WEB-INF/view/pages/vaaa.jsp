<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script
	src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="oddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">AMAR AKBAR ANTHONY &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span">{{data.t1.0.mid}}</span>
                    </span>
				</div>
				<div class="score-panel">
					<div class="videostream">
						<div class="showcards">
							<div class="title text-center">AMAR AKBAR ANTHONY</div>
							<div class="card-box">
								<div id="vluckycard" class="card-wrap">
									{{#if_eq data.t1.0.C1 ""}}  											
										<img src="../../images/backcover.png" width="70" />
									{{else}}
										<img  src="<s:url value="/images/cards/{{data.t1.0.C1}}.png"/>" width="70" />
									{{/if_eq}}
								</div>	
								<div class="aaa-chart">
									<table class="table table-bordered">
										<tr>
											<td>
												<ul class="card-related">
													<li>A</li>
													<li>2</li>
													<li>3</li>
													<li>4</li>
													<li>5</li>
													<li>6</li>
												</ul>
											</td>
											<td>
												<ul class="card-related">
													<li>7</li>
													<li>8</li>
													<li>9</li>
													<li>10</li>
												</ul>
											</td>
											<td>
												<ul class="card-related">
													<li>J</li>
													<li>Q</li>
													<li>K</li>
												</ul>
											</td>
										</tr>
										<tr>
											<th>Amar</th>
											<th>Akbar</th>
											<th>Anthony</th>
										</tr>
									</table>
								</div>								
								<div id="holder">
									<div class="digits active">{{data.t1.0.autotime}}</div>
								</div>
							</div>
						</div>						
					</div>
				</div>

			
			<div class="paati-seven-cont-part-3 mb-2 mt-2 p-3 pb-0">
				<div class="form-row">
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>A.</span> Amar</div>
{{#if_eq data.t1.0.autotime "0"}}
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="amar">
                            <button class="back ">
                                <span class="odd">{{data.t2.0.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.0.layrate}}</span>
                            </button>
                        </div>
{{else}}
  <div class="aaa-button clearfix patti-count-btn bet-info " id="amar">
                            <button class="back ">
                                <span class="odd">{{data.t2.0.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.0.layrate}}</span>
                            </button>
                        </div>
{{/if_eq}}	
                        <div style="color: black; font-weight: 700;" id="sid1">0</div>
                    </div>                
                </div>
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>B.</span> Akbar</div>
				{{#if_eq data.t1.0.autotime "0"}}
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="akabar">
                            <button class="back">
                                <span class="odd">{{data.t2.1.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.0.layrate}}</span>
                            </button>
                        </div>
				{{else}}
						<div class="aaa-button clearfix patti-count-btn bet-info" id="akabar">
                            <button class="back">
                                <span class="odd">{{data.t2.1.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.0.layrate}}</span>
                            </button>
                        </div>
				{{/if_eq}}	
                        <div style="color: black; font-weight: 700;" id="sid2">0</div>
                    </div>                
                </div>
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>C.</span> Anthony</div>
                       {{#if_eq data.t1.0.autotime "0"}}  	
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="anthony">
                            <button class="back">
                                <span class="odd">{{data.t2.2.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.2.layrate}}</span>
                            </button>
                        </div>
					{{else}}
							<div class="aaa-button clearfix patti-count-btn bet-info" id="anthony">
                            <button class="back">
                                <span class="odd">{{data.t2.2.rate}}</span>
                            </button> 
                            <button class="lay">
                                <span class="odd">{{data.t2.2.layrate}}</span>
                            </button>
                        </div>
						 {{/if_eq}}	
                        <div style="color: black; font-weight: 700;" id="sid3">0</div>
                    </div>                
                </div>
            </div>
            <div class="form-row px-2 mt-2">
                <div class="col-sm-12 text-right">
                    <ul class="cont-lenght mb-0">
                        <li><span><b>Min:</b> </span>{{data.t2.0.min}}</li>
                        <li><span><b>Max:</b> </span>{{data.t2.0.max}}</li>
                    </ul>
                </div>
            </div>
			</div>
			<div class="form-row">
				<div class="col-sm-6">
					<div class="paati-seven-cont-part-3 mb-2 p-3">
						<div class="text-center mt-0">

							<div class="mb-2"><b>{{data.t2.3.rate}}</b></div> 	
					     {{#if_eq data.t1.0.autotime "0"}}  	
							 
							<button type="button" id="odd" class="patti-count-btn bet-info d-block suspended">EVEN</button>
						    {{else}}
							<button type="button" id="odd" class="patti-count-btn bet-info d-block ">
								EVEN
							</button>
						 {{/if_eq}}	
							
							<div class="positive" id="sid4">0</div>
						</div>
						<div class="text-center mt-2">
							<div class="mb-2"><b>{{data.t2.4.rate}}</b></div> 	
						{{#if_eq data.t1.0.autotime "0"}} 
							<button type="button" id="even" class="patti-count-btn bet-info d-block suspended">
								ODD
							</button>
 						{{else}}
								<button type="button" id="even" class="patti-count-btn bet-info d-block">
								ODD
							</button>
						{{/if_eq}}	
							<div class="positive" id="sid5">0</div>
						</div>
						<div class="form-row mt-2"> 
							<div class="col-sm-12 text-right">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.4.min}}</li>
									<li class="pr-0"><span><b>Max:</b> </span>{{data.t2.4.max}}</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="paati-seven-cont-part-3 mb-2 p-3">
						<div class="text-center mt-0">
							<div class="mb-2"><b>{{data.t2.5.rate}}</b></div> 
                       {{#if_eq data.t1.0.autotime "0"}} 	
							<button type="button" id="red" class="patti-count-btn bet-info d-block suspended">
								<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
							</button>
                      {{else}}
							<button type="button" id="red" class="patti-count-btn bet-info d-block">
								<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
							</button>
					  {{/if_eq}}	
							
							<div class="positive" id="sid6">0</div>
						</div>
						<div class="text-center mt-2">
							<div class="mb-2"><b>{{data.t2.6.rate}}</b></div> 	
						{{#if_eq data.t1.0.autotime "0"}} 
							<button type="button" id="black" class="patti-count-btn bet-info d-block suspended">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
							</button>
 						{{else}}
							<button type="button" id="black" class="patti-count-btn bet-info d-block ">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
							</button>
                        {{/if_eq}}	
							
							<div class="positive" id="sid7">0</div>
						</div>
						<div class="form-row mt-2"> 
							<div class="col-sm-12 text-right">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.5.min}}</li>
									<li class="pr-0"><span><b>Max:</b> </span>{{data.t2.5.max}}</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				
					</div>
				</div>
			</div>



			
</script>

<script id="resulthandler" type="text/x-handlebars-template">
			{{#if_eq result.0.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >A</span> 
 			{{/if_eq}}

			{{#if_eq result.0.winner "2"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >B</span> 
 			{{/if_eq}}


			{{#if_eq result.0.winner "3"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >C</span> 
 			{{/if_eq}}
							
				
</script>


  <script id="matchedBetsHandler" type="text/x-handlebars-template">
<button type="button" class="btn btn-info blue-btn btn-sm" onclick="getAdminViewMoreBets('Match Odds')">View More</button> 
			<div class="table-responsive">
				<table class="table table-s/triped table-bordered cash-trans dataTable no-footer" id="">
												   <thead>
													  <tr>
														{{#if_eq ${user.usertype} 4}}<th class="marketname">Series</th>{{/if_eq}}	
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
														  
 														{{#if_eq ${user.usertype} 4}}	  <td>{{series}}</td>{{/if_eq}}
														  <td>{{odds}} ->{{marketid}}</td>
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
															<td>{{userid}}</td>			 

															<td class="green">{{pnl}}</td>
														  <td class="red">{{liability}}</td>
 														<td>{{marketname}}</td>
														                                         
														  <td>{{matchedtime}}</td>
													  </tr>
												   
{{/each}}
			</tbody>									</table>
			</div>
  </script>



<div class="container-fluid">

	<div class="teenpatti-wrap mt-3">
	 <jsp:include page="sbamaxbet.jsp" />
	
		<div class="row">
			<div class="col-md-8">
			  <s:if test="${user.usertype==4}">
                <div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
                   <div class="form-row" >
				        Book Lagai Diff:	<button  onclick="updateCasinoBackDiff()" id="BookMakerBackDiff">SET </button> 
				     <div class="col-sm-2">
						<select class="form-control form-control-sm" id="bmbackdiff" ></select>
					</div>
					
				        Book khai Diff: <button onclick="updateCasinoLayDiff()"  id="BookMakerLayDiff">SET </button> 	
				    <div class="col-sm-2">
						<select class="form-control form-control-sm" id="bmlaydiff"></select>
				    </div>
		          </div>
		        </div>
		     </s:if>
			
				<div class="coupon-card" id="tablediv"></div>		
				
				<!--Newly Added-->
				<!--Newly Added-->
				<div class="fancy-marker-title pb-5">
					<h4>
						Last Result <a href="<s:url value="/casinoresult"/>" class="text-right">View All</a>
					</h4>
					<div id="last-result" class="last-result-wrap"></div>
				</div>
			</div>
			<div class="col-md-4">
				<jsp:include page="betlock.jsp" /> 
			</div>
		</div>	
		<div class="row">
			<div class="col-md-12">
				<div class="markets">
					<span id="matched-betsCount">Matched-Bets(0)</span>
					<div class="panel-group" id="accordion">
						<div class="panel panel-default">
							<div class="panel-heading" style="background: #0088cc;color: white;">
								<h4 class="panel-title">
									<a class="collapsed" style="color:white;" data-toggle="collapse" data-parent="#accordion" href="#collapsematchbet">
										Match Bet
									</a>
								</h4>
							</div>
							<div id="collapsematchbet" class="panel-collapse collapse">
								<div class="row no-gutters bookmaker-market" id="match-bet">
									<button type="button" class="btn btn-info blue-btn btn-sm" onclick="getAdminViewMoreBets('Match Odds')">View More</button> 
									<div class="table-responsive">
										<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
											<thead>
												<tr>
													<th class="marketname">Series</th>	
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
											<tbody id="matchoddsbettable"></tbody>						
										</table>
									</div>
								</div>
							</div>
						</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="fancyBookModal" tabindex="-1" role="dialog"
	aria-labelledby="fancyBookModal" aria-hidden="true"
	style="margin-top: 130px;">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Session Book</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="table-responsive">
					<table class="table table-borderless mb-1">
						<thead>
							<tr>
								<th></th>
								<th>Run</th>
								<th class="text-center">Profit/Loss</th>
							</tr>
						</thead>
						<tbody id="fancyBookTableBody"></tbody>
					</table>
				</div>
				<!-- 
				<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
				</div> -->
			</div>
		</div>
	</div>
</div>

<script>
$(document).ready(function(){
	checkEventActive();
	getEvents();
	getMyProfit();
	getAddminBet();
	
/*
	db.collection("t_betlist").where("marketid", "==", $("#roundid").val()).where("isactive", "==", true).where(usertype, "==", '${user.id}')
	 .onSnapshot(function(querySnapshot) {
       var bets = [];
       querySnapshot.forEach(function(doc) {
         bets.push(doc.data());
          
       });

       $("#match-bet").html('')
     
       $("#matched-betsCount").text("Matched-Bets("+bets.length+")");
	      var source   = $("#matchedBetsHandler").html();
         var template = Handlebars.compile(source);
   	  $("#match-bet").append( template({data:bets}) );
   });
*/
	
});
	
	
var usertype =""
var userId =""
if(${user.usertype} ==4){
	usertype ="adminid";
	userId='${user.id}';
}else if(${user.usertype} ==5){
	usertype ="subadminid";
	userId='${user.id}';
} else if(${user.usertype} ==0){
	usertype ="supermasterid";
	userId='${user.id}';
} else if(${user.usertype} ==1){
	usertype ="masterid";
	userId='${user.id}';
} else if(${user.usertype} ==2){
	usertype ="dealerid";
	userId='${user.id}';
} else if(${user.usertype} ==6){
	usertype ="adminid";
	userId ='${user.parentid}';
} 


     	var starCountRefResult = secondaryApp.database().ref(vaaadata+"/result");
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		   
			$("#last-result").html('');
			var source   = $("#resulthandler").html();
	    	  var template = Handlebars.compile(source);
	    	  $("#last-result").append( template({result:result}) );
	    	  
		  });



		
	    
	 

		function checkEventActive(){
			$.ajax({
						type:'GET',
						url:'<s:url value="/api/findByEventIdAndIsActive/"/>'+${eventid}+'/true',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								loadHtml();
							}
					}
				});
		}

		
		function loadHtml(){
			$.ajax({
						type:'GET',
						url:'<s:url value="/api/VAAAData/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		

								console.log("jsondata"+jsondata)
								 $("#tablediv").html('');
									 var source   = $("#oddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#tablediv").append( template({data:JSON.parse(jsondata)}) );
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(vaaadata+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										  	marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								            
								            console.log("autotime==>"+data.t1[0].autotime)
								            
										    if(parseInt(data.t1[0].autotime) <3){
											 $("#low").addClass("suspended")
											  $("#high").addClass("suspended")	
											   $("#even").addClass("suspended")
											   $("#odd").addClass("suspended")
											    $("#red").addClass("suspended")
											    $("#black").addClass("suspended")
											     $("#over7").addClass("suspended")
											     $("#under7").addClass("suspended")
											      $("#anthony").addClass("suspended")
											       $("#amar").addClass("suspended")
											        $("#akabar").addClass("suspended")

											    
										    }else{
										    	 $("#low").removeClass("suspended")
										    	  $("#high").removeClass("suspended")	
										    	   $("#even").removeClass("suspended")
										    	    $("#odd").removeClass("suspended")
										    	    $("#red").removeClass("suspended")
										    	    $("#black").removeClass("suspended")
										    	     $("#over7").removeClass("suspended")
											    	 $("#under7").removeClass("suspended")
											    	 $("#anthony").removeClass("suspended")
											       $("#amar").removeClass("suspended")
											        $("#akabar").removeClass("suspended")
											  }
											
										
											
									
											
											
											

											if(parseInt(data.t1[0].autotime) <3){
												$(".p1p").addClass("suspended")
												$(".p2p").addClass("suspended")
												}else{
													$(".p1p").removeClass("suspended")
													$(".p2p").removeClass("suspended")
													$("#p1rate").html(data.t2[0].rate)
													$("#p2rate").html(data.t2[2].rate)
													
													}
											
										
											 if(parseInt(data.t1[0].autotime)== 0){
												$(".digits").html('GO!!');
												$(".digits").removeClass('active');
											if(data.t1[0].C1!=""){

												$("#vluckycard").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C1+'.png"/>" class="card-opening" width="70" />')
												
												}	
												
											 }else{
												 $(".digits").html(data.t1[0].autotime);
												 $(".digits").addClass('active');
												
												 $("#vluckycard").html(' <img src="../../images/backcover.png" width="70">')
													
													 }
												
										 
											  if(marketId!="0"){
													
												/*	 db.collection("t_betlist").where("marketid", "==", marketId).where(usertype, "==",userId).where("isactive", "==", true)
												  	    .onSnapshot(function(querySnapshot) {
												  	        var sid1 = [];
												  	        var bets = [];
												  	        var sid2 = [];
												  	     
												  	        querySnapshot.forEach(function(doc) {
												  	        	 bets.push(doc.data());
													  	        	
												  	        	
												  	        
												  	        });

												  	      $("#match-bet").html('')
													        
													        $("#matched-betsCount").text("Matched-Bets("+bets.length+")");
														      var source   = $("#matchedBetsHandler").html();
												  	         var template = Handlebars.compile(source);
													    	  $("#match-bet").append( template({data:bets}) );
												  	       
												  	    });
*/
											 }else{
												  var bets = [];
												   
											        $("#matched-betsCount").text("Matched-Bets("+bets.length+")");
													
												 } 
										
											 
												
										  });
									
										
							      }
					}
				});
			  
		}
			

	
function getEvents() {
	
	 $.ajax({
	 
			type:'GET',
			url:'<s:url value="/api/getevent?eventid="/>'+${eventid},
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {

			
			//	getSelectedEventExposure(jsondata.totalExposure)
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}


function getMyProfit()
{
	
	
		
		$.ajax({
			type:'GET',
			//url:'<s:url value="/api/getMyProfitVAAAP/"/>'+$("#roundid").val(),
			url:expo_base_url+'/api/getMyProfitVAAA/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  if(jsondata.pnl1 < 0.00){
						    $("#sid1").removeClass("positive");
  						    $("#sid1").addClass("negative");
						  }else{
							$("#sid1").addClass("positive");
	  						$("#sid1").removeClass("negative");
						  }

					  if(jsondata.pnl2 < 0.00){
						    $("#sid2").removeClass("positive");
						    $("#sid2").addClass("negative");
						  }else{
							$("#sid2").addClass("positive");
	  						$("#sid2").removeClass("negative");
						  }

					  if(jsondata.pnl3 < 0.00){
						    $("#sid3").removeClass("positive");
						    $("#sid3").addClass("negative");
						  }else{
							$("#sid3").addClass("positive");
	  						$("#sid3").removeClass("negative");
						  }

					  if(jsondata.pnl4 < 0.00){
						    $("#sid4").removeClass("positive");
						    $("#sid4").addClass("negative");
						  }else{
							$("#sid4").addClass("positive");
	  						$("#sid4").removeClass("negative");
						  }

					  if(jsondata.pnl5 < 0.00){
						    $("#sid5").removeClass("positive");
						    $("#sid5").addClass("negative");
						  }else{
							$("#sid5").addClass("positive");
	  						$("#sid5").removeClass("negative");
						  }

					  if(jsondata.pnl6 < 0.00){
						    $("#sid6").removeClass("positive");
						    $("#sid6").addClass("negative");
						  }else{
							$("#sid6").addClass("positive");
	  						$("#sid6").removeClass("negative");
						  }
						  
					  if(jsondata.pnl7 < 0.00){
						    $("#sid7").removeClass("positive");
						    $("#sid7").addClass("negative");
						  }else{
							$("#sid7").addClass("positive");
	  						$("#sid7").removeClass("negative");
						  }

					  if(jsondata.pnl21 < 0.00){
						    $("#sid21").removeClass("positive");
						    $("#sid21").addClass("negative");
						  }else{
							$("#sid21").addClass("positive");
	  						$("#sid21").removeClass("negative");
						  }

					  if(jsondata.pnl22 < 0.00){
						    $("#sid22").removeClass("positive");
						    $("#sid22").addClass("negative");
						  }else{
							$("#sid22").addClass("positive");
	  						$("#sid22").removeClass("negative");
						  }

					 
					  $("#sid1").html(jsondata.pnl1);
					  $("#sid2").html(jsondata.pnl2);
		  	          $("#sid3").html(jsondata.pnl3);
		  	          $("#sid4").html(jsondata.pnl4);
		  	          $("#sid5").html(jsondata.pnl5);
		  	          $("#sid6").html(jsondata.pnl6);
		  	          $("#sid7").html(jsondata.pnl7);
		  	          $("#sid21").html(jsondata.pnl21);
		  	          $("#sid22").html(jsondata.pnl22);
						
					}
					
				}
		
	});
}

function matchbetUnLock(){
  	
	$.ajax({
   			type:'POST',
   			url:'<s:url value="/api/betUnLockMatch/"/>'+'${eventid}',
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{   
    					showMessage(jsondata.message,jsondata.type,jsondata.title);
    					matchFancyStatus();
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}

 });
}
setInterval(function(){getMyProfit()},2000)
 setInterval(function(){getAddminBet()},3000)

</script>
<jsp:include page="resultdetail.jsp" />
 <jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
<jsp:include page="handlebars.jsp" />
