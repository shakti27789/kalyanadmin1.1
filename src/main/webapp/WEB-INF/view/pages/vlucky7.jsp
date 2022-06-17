<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>



<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script
	src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">Lucky7 Rules &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span">{{data.t1.0.mid}}</span>
                    </span>
				</div>
				<div class="score-panel">
					<div class="videostream lucky7-screen">
						<div class="showcards">
							<div class="title text-center">Lucky7</div>
							<div class="card-box">
								<div id="vluckycard" class="card-wrap">
									{{#if_eq data.t1.0.C1 ""}}  											
										<img src="../../images/backcover.png" width="70" />
									{{else}}
										<img  src="<s:url value="/images/cards/{{data.t1.0.C1}}.png"/>" width="70" />
									{{/if_eq}}
								</div>									
								<div id="holder">
									<div class="digits active">{{data.t1.0.autotime}}</div>
								</div>
							</div>
						</div>						
					</div>
				</div>

<div class="paati-seven-cont py-2">
				<div class="pl-3 pr-3">
					<div class="row mt-2">
						<div class="col-sm-5 sm-mb-2 text-center">
							<div class="mb-2"><b>2.00</b></div>
						
							{{#if_eq data.t1.0.autotime "0"}}  	
							  <button type="button" id="low" class="patti-count-btn bet-info suspended">Low Card</button>
						    {{else}}
							  <button type="button" id="low" class="patti-count-btn bet-info">Low Card</button>
							{{/if_eq}}	
							<div id="sid1" class="positive">0</div>
						</div>
						<div class="col-sm-2 text-center pt-3">
							<img src="<s:url value="/images/cards/7.jpg"/>" width="40" />
						</div>
						<div class="col-sm-5 text-center">
							<div class="mb-2"><b>2.00</b></div>
							
							{{#if_eq data.t1.0.autotime "0"}}  	
							 <button type="button" id="high" class="patti-count-btn bet-info suspended">High Card</button>
						    {{else}}
							  <button type="button" id="high" class="patti-count-btn bet-info">High Card</button>
							{{/if_eq}}
								<div id="sid2" class="positive">0</div>
						</div>
					</div>
				</div>
				<div class="row px-2">
					<div class="col-sm-12 text-right mt-2">
						<ul class="cont-lenght mb-0">
							<li><span id="minlowhigh"><b>Min:</b> </span>{{data.t2.0.min}}</li>
							<li><span id="maxlowhigh"><b>Max:</b> </span >{{data.t2.0.max}}</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="paati-seven-cont-2 pt-2 pb-2 pl-3 pr-3">
				<div class="form-row">
					<div class="col-sm-6">
						<div class="paati-seven-cont-part-1">
							<div class="form-row mt-2">
								<div class="col-sm-6 text-center">
									<div class="mb-2"><b>2.12</b></div>
									
							{{#if_eq data.t1.0.autotime "0"}}  	
							<button type="button" id="even" class="patti-count-btn bet-info suspended">EVEN</button>
						    {{else}}
							  <button type="button" id="even" class="patti-count-btn bet-info ">EVEN</button>
							{{/if_eq}}
									<div id="sid3" class="positive">0</div>
								</div>
								<div class="col-sm-6 text-center">
									<div class="mb-2"><b>1.83</b></div>
									
							{{#if_eq data.t1.0.autotime "0"}}  	
							   <button type="button" id="odd" class="patti-count-btn bet-info suspended">ODD</button>
						    {{else}}
							  <button type="button" id="odd" class="patti-count-btn bet-info">ODD</button>
							{{/if_eq}}
									<div id="sid4" class="positive">0</div>
								</div>
							</div>
							<div class="form-row px-2 mt-2">
								<div class="col-sm-12 text-right">
									<ul class="cont-lenght mb-0">
										<li id="minevenodd"><span><b>Min:</b> </span>{{data.t2.1.min}}</li>
										<li id="maxevenodd"><span><b>Max:</b> </span>{{data.t2.1.max}}</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="paati-seven-cont-part-2">
							<div class="form-row mt-2">
								<div class="col-sm-6 text-center">
									<div class="mb-2"><b>1.97</b></div>
						{{#if_eq data.t1.0.autotime "0"}}  	
							  <button type="button"  id="red" class="patti-count-btn bet-info d-block suspended">

										<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
									</button>
						    {{else}}
							  <button type="button" id="red" class="patti-count-btn bet-info d-block">

										<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
									</button>
							{{/if_eq}}
									
									<div id="sid5" class="positive">0</div>
								</div>
								<div class="col-sm-6 text-center">
									<div class="mb-2"><b>1.97</b></div>
									{{#if_eq data.t1.0.autotime "0"}}  	
							  <button type="button" id="black" class="patti-count-btn bet-info d-block suspended">

										<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
									</button>
						    {{else}}
							  <button type="button" id="black" class="patti-count-btn bet-info d-block">

										<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
									</button>
							{{/if_eq}}
									<div id="sid6" class="positive">0</div>
								</div>
							</div>
							<div class="form-row px-2 mt-2">
								<div class="col-sm-12 text-right">
									<ul class="cont-lenght mb-0">
										
										<li id="minredblack"><span><b>Min:</b> </span >{{data.t2.2.min}}</li>
										<li id="maxredblack"><span><b>Max:</b> </span >{{data.t2.2.max}}</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- <div class="paati-seven-cont-part-3 mb-3">
				<div class="row mt-2 py-2">
					<div class="col-sm-12 text-center">
						<b>12.00</b>
					</div>
				</div>
				<div class="row mt-2">
					<div class="col-sm-12">
						<ul class="patti-list-13 text-center">
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
							<li>
								<img src="/images/cards/ACC.png" width="50" class="img-fluid" />
								<div>0</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 text-right">
							<ul class="cont-lenght">
								<li><span><b>Min:</b> </span>100</li>
								<li><span><b>Max:</b> </span>5000</li>
							</ul>
						</div>
					</div>
				</div>
				-->
</script>

<script id="vlucky7resulthandler" type="text/x-handlebars-template">
			{{#if_eq result.0.winner "H"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >H</span> 
			{{/if_eq}}
			{{#if_eq result.0.winner "L"}}  											
				<span class="ball-runs playerb last-result"  onclick="resultDeatil({{result.0.mid}})">L</span> 
			{{/if_eq}}

			{{#if_eq result.0.winner "T"}}  											
				<span class="ball-runs playerb last-result"  onclick="resultDeatil({{result.0.mid}})">T</span> 
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
			
				<div class="coupon-card" id="vlucky7div"></div>		
				
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


     	var starCountRefResult = secondaryApp.database().ref(vlucky7data+"/result");
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		    console.log(result)
			
			$("#last-result").html('');
			var source   = $("#vlucky7resulthandler").html();
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
						url:'<s:url value="/api/VLucky7Data/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								
								 $("#vlucky7div").html('');
									 var source   = $("#vluckyoddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#vlucky7div").append( template({data:JSON.parse(jsondata)}) );
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(vlucky7data+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										  	marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								             
										    /* $("#minlowhigh").html(data.t2[0].min)
										     $("#maxlowhigh").html(data.t2[1].max)*/
										    if(parseInt(data.t1[0].autotime) <3){
											 $("#low").addClass("suspended")
											  $("#high").addClass("suspended")	
											   $("#even").addClass("suspended")
											   $("#odd").addClass("suspended")
											    $("#red").addClass("suspended")
											    $("#black").addClass("suspended")
										    }else{
										    	 $("#low").removeClass("suspended")
										    	  $("#high").removeClass("suspended")	
										    	   $("#even").removeClass("suspended")
										    	    $("#odd").removeClass("suspended")
										    	    $("#red").removeClass("suspended")
										    	    $("#black").removeClass("suspended")
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
													
													/*  db.collection("t_betlist").where("marketid", "==", marketId).where(usertype, "==",userId).where("isactive", "==", true)
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
												  	       
												  	    }); */

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

			
				//getSelectedEventExposure(jsondata.totalExposure)
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}


function getMyProfit()
{
	
	
		
		$.ajax({
			type:'GET',
			url:expo_base_url+'/api/getMyProfitLucky/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  var data = jsondata;
					  $("#sid1").html(jsondata.pnl1);
					  $("#sid2").html(jsondata.pnl2);
		  	          $("#sid3").html(jsondata.pnl3);
		  	          $("#sid4").html(jsondata.pnl4);
		  	          $("#sid5").html(jsondata.pnl5);
		  	          $("#sid6").html(jsondata.pnl6);
						
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
