<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>



<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script
	src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="teenpattioddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">
<input type="hidden" value="{{data.t1.0.C2}}" id="cardc2text">
<input type="hidden" value="{{data.t1.0.C3}}" id="cardc3text">
<input type="hidden" value="{{data.t1.0.C4}}" id="cardc4text">
<input type="hidden" value="{{data.t1.0.C5}}" id="cardc5text">
<input type="hidden" value="{{data.t1.0.C6}}" id="cardc6text">

<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">TeenPatti 20 &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span">{{data.t1.0.mid}}</span>
                        | Min: <span>{{data.t1.0.min}}</span>
                        | Max: <span>{{data.t1.0.max}}</span>
					</span>
				</div>
				<div class="score-panel">
					<div class="videostream">
						<div class="showcards">
							<div class="title text-center">Teenpatti T20</div>
							<div class="card-box">
								<div class="row no-gutters">
									<div class="col-4 col-sm-3">	
										<div class="form-row">
											<div class="col-6">
												<div id="cardc1" class="mb-4 card-wrap">
													{{#if_eq data.t1.0.C1 ""}}  											
														<img src="../../images/backcover.png" width="70" />
													{{else}}
														<img  src="<s:url value="/images/cards/{{data.t1.0.C1}}.png"/>" width="70" />
													{{/if_eq}}
												</div>
												<div id="cardc2" class="card-wrap">
													{{#if_eq data.t1.0.C2 ""}}  											
														<img src="../../images/backcover.png" width="70" />
													{{else}}
														<img  src="<s:url value="/images/cards/{{data.t1.0.C2}}.png"/>" width="70" />
													{{/if_eq}}
												</div>
											</div>
											<div class="col-6 d-flex justify-content-center align-items-center">												
												<div id="cardc3" class="card-wrap">
													{{#if_eq data.t1.0.C3 ""}}  											
														<img src="../../images/backcover.png" width="70" />
													{{else}}
														<img  src="<s:url value="/images/cards/{{data.t1.0.C3}}.png"/>" width="70" />
													{{/if_eq}}
												</div> 
											</div>											
										</div>



										<div class="team-playing">
											Player A
										</div>
									</div>
									<div class="col-4 col-sm-6 d-flex justify-content-center align-items-center">
										<div id="holder">
											<div class="digits active">{{data.t1.0.autotime}}</div>
										</div>
									</div>
									<div class="col-4 col-sm-3">	
										<div class="form-row">
											<div class="col-6">
												<div class="mb-4 card-wrap" id="cardc4">
												{{#if_eq data.t1.0.C4 ""}}  											
													<img src="../../images/backcover.png" width="70" />
												{{else}}
													<img  src="<s:url value="/images/cards/{{data.t1.0.C4}}.png"/>" width="70" />
												{{/if_eq}}
													</div>
												<div id="cardc5" class="card-wrap">
													{{#if_eq data.t1.0.C5 ""}}  											
														<img src="../../images/backcover.png" width="70" />
													{{else}}
														<img  src="<s:url value="/images/cards/{{data.t1.0.C5}}.png"/>" width="70" />
													{{/if_eq}}
												</div>
											</div>
											<div class="col-6 d-flex justify-content-center align-items-center">												
												<div id="cardc6" class="card-wrap">
													{{#if_eq data.t1.0.C6 ""}}  											
														<img src="../../images/backcover.png" width="70" />
													{{else}}
														<img  src="<s:url value="/images/cards/{{data.t1.0.C6}}.png"/>" width="70" />
													{{/if_eq}}
												</div>
											</div>											
										</div>
										<div class="team-playing">
											Player B
										</div>
									</div>
								</div>
							</div>
						</div>						
					</div>
				</div>
				<div class="table-responsive main-market m-t-0">
				   <table class="table coupon-table table table-bordered ">
					  <thead>
						 <tr>
							<th class="box-5"></th>
							<th class="box-2 back-color">BACK</th>
							<th class="box-3 back-color"></th>
						 </tr>
					  </thead>
					  <tbody>
					
					{{#if_eq data.t1.0.autotime "0"}}
						<td class="box-5"><b>Player A</b><span id="sid1"></span></td>
							<td class="text-center box-2 back bet-info p1p suspended" >
										<button class="back">
											<span class="d-block text-bold odd" id="p1rate" ><b>{{data.t2.0.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
							</td>
							<td class="text-center box-3 back bet-info suspended">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.1.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
							</td>
						<tr data-title="" class="bet-info">
						<td class="box-5"><b>Player B</b> <span id="sid2"></span></td>
							<td class="text-center box-2 back bet-info p2p suspended">
										<button class="back">
											<span class="d-block text-bold odd" id="p2rate" ><b>{{data.t2.2.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
							</td>
							<td class="text-center box-3 back bet-info suspended">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.3.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
							</td>
					{{else}}
						<!-- for first row start-->
						 <tr data-title="" class="bet-info">
							<td class="box-5"><b>Player A<span id="sid1"></span></b></td>
							 	{{#if_eq data.t2.0.gstatus "1"}}
									<td class="text-center box-2 back p1p ">
										<button class="back">
											<span class="d-block text-bold odd"><b>{{data.t2.0.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
								{{else}}
                          			<td class="text-center box-2 back bet-info suspended p1p">
										<button class="back">
											<span class="d-block text-bold odd"><b>{{data.t2.0.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
								{{/if_eq}}
								{{#if_eq data.t2.1.gstatus "1"}}
									<td class="text-center box-3 back ">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.1.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
								{{else}}
									<td class="text-center box-3 back bet-info suspended ">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.1.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
								{{/if_eq}}
						 </tr>
						<!-- for first row end-->

						<!-- for second row start-->
						 <tr data-title="" class="bet-info">
							<td class="box-5"><b>Player B</b><span id="sid2"></span></td>
							 	{{#if_eq data.t2.2.gstatus "1"}}
									<td class="text-center box-2 back p2p ">
										<button class="back">
											<span class="d-block text-bold odd"><b>{{data.t2.2.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
								{{else}}
                          			<td class="text-center box-2 back bet-info suspended p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b>{{data.t2.2.rate}}</b></span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
								{{/if_eq}}
								{{#if_eq data.t2.3.gstatus "1"}}
									<td class="text-center box-3 back">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.3.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
								{{else}}
									<td class="text-center box-3 back bet-info suspended">
										<button class="back"><span class="d-block text-bold odd"><b>{{data.t2.3.nat}}</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
								{{/if_eq}}
						 </tr>
						{{/if_eq}}<!-- for second row end-->

 
				
					  </tbody>
				   </table>
				</div>
</script>

<script id="teenpattioddsresulthandler" type="text/x-handlebars-template">
			{{#if_eq result.0.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result"  onclick="resultDeatil({{result.0.mid}})">B</span>
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
				Book Lagai Diff:	<button  onclick="updateCasinoBackDiff()" id="BookMakerBackDiff">SET </button>  <div class="col-sm-2">
						<select class="form-control form-control-sm" id="bmbackdiff" >
							
						</select>
					</div>
					
				Book khai Diff: <button onclick="updateCasinoLayDiff()"  id="BookMakerLayDiff">SET </button> 	<div class="col-sm-2">
						<select class="form-control form-control-sm" id="bmlaydiff">
							
						</select>
				</div>
							
				
					
		</div>
		</div>
		</s:if>
				<div class="coupon-card" id="tp20div"></div>
				
				
				
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



		var starCountRefResult = secondaryApp.database().ref(devpathresult);
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		    //updateStarCount(postElement, data);
			console.log(result);
			$("#last-result").html('');
			var source   = $("#teenpattioddsresulthandler").html();
	    	  var template = Handlebars.compile(source);
	    	  $("#last-result").append( template({result:result}) );
	    	  console.log("winner: "+result[0].winner) 
	    	
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
						url:'<s:url value="/api/VTP20Data/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
									 $("#tp20div").html('');
									 var source   = $("#teenpattioddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#tp20div").append( template({data:JSON.parse(jsondata)}) );

                                     
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(devpathdata);
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										    //updateStarCount(postElement, data);
											marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								              // console.log("roundid==>"+$("#roundid").val())
								             
										 	if(data.t1[0].C1 == ""){
												$("#cardc1").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc1text").val('')
												}else{
													if($("#cardc1text").val().trim().length<1){
				
														$("#cardc1").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C1+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc1").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C1+'.png"/>"  width="70" />')
													
													}
													$("#cardc1text").val(data.t1[0].C1)
											}

											if(data.t1[0].C4 == ""){
												$("#cardc4").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc4text").val('')
												}else{
													if($("#cardc4text").val().trim().length<1){
				
														$("#cardc4").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C4+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc4").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C4+'.png"/>"  width="70" />')
													
													}
													$("#cardc4text").val(data.t1[0].C4)
											}

											if(data.t1[0].C2 == ""){
												$("#cardc2").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc2text").val('')
												}else{
													if($("#cardc2text").val().trim().length<1){
				
														$("#cardc2").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C2+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc2").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C2+'.png"/>"  width="70" />')
													
													}
													$("#cardc2text").val(data.t1[0].C2)
											}


											if(data.t1[0].C5 == ""){
												$("#cardc5").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc5text").val('')
												}else{
													if($("#cardc5text").val().trim().length<1){
				
														$("#cardc5").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C5+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc5").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C5+'.png"/>"  width="70" />')
													
													}
													$("#cardc5text").val(data.t1[0].C5)
											}

											if(data.t1[0].C3 == ""){
												$("#cardc3").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc3text").val('')
												}else{
													if($("#cardc3text").val().trim().length<1){
				
														$("#cardc3").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C3+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc3").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C3+'.png"/>"  width="70" />')
													
													}
													$("#cardc3text").val(data.t1[0].C3)
											}
											if(data.t1[0].C6 == ""){
												$("#cardc6").html('<img src="../../images/backcover.png" width="70">')
												  $("#cardc6text").val('')
												}else{
													if($("#cardc6text").val().trim().length<1){
				
														$("#cardc6").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C6+'.png"/>" class="card-opening" width="70" />')
												}else{
													$("#cardc6").html('<img  src="<s:url value="/images/cards/'+data.t1[0].C6+'.png"/>"  width="70" />')
													
													}
													$("#cardc6text").val(data.t1[0].C6)
											}
											
											
									
											
											
											

											if(parseInt(data.t1[0].autotime) <3){
												$(".p1p").addClass("suspended")
												$(".p2p").addClass("suspended")
												console.log("data=>"+parseInt(data.t1[0].autotime));
												}else{
													$(".p1p").removeClass("suspended")
													$(".p2p").removeClass("suspended")
													$("#p1rate").html(data.t2[0].rate)
													$("#p2rate").html(data.t2[2].rate)
													
													}
											
										
											 if(parseInt(data.t1[0].autotime)== 0){
												$(".digits").html('GO!!');
												$(".digits").removeClass('active');
												
												
											 }else{
												 $(".digits").html(data.t1[0].autotime);
												 $(".digits").addClass('active');
											 }
												
										 
											  if(marketId!="0"){

												/*	 db.collection("t_betlist").where("marketid", "==", marketId).where(usertype, "==",userId).where("isactive", "==", true)
												  	    .onSnapshot(function(querySnapshot) {
												  	        var sid1 = [];
												  	        var bets = [];
												  	        var sid2 = [];
												  	      sid1pnl =0;
													      sid2pnl =0;
													    
												  	        querySnapshot.forEach(function(doc) {
												  	        	 bets.push(doc.data());
													  	        	
												  	        	if(doc.data().selectionid ==1){
												  	        		sid2pnl =sid2pnl+ doc.data().liability;
												  	        		
												  		        	}else if(doc.data().selectionid ==3){
												  		        		sid1pnl =sid1pnl+ doc.data().liability;
												  		        		
												  		        	}
												  	        
												  	        });

												  	      $("#match-bet").html('')
													        
													        $("#matched-betsCount").text("Matched-Bets("+bets.length+")");
														      var source   = $("#matchedBetsHandler").html();
												  	         var template = Handlebars.compile(source);
													    	  $("#match-bet").append( template({data:bets}) );
												  	       
												  	    });*/

											
											 }else{
												  var bets = [];
												//  $("#match-bet").html('')
											        
											        $("#matched-betsCount").text("Matched-Bets("+bets.length+")");
													 // $("#match-bet").append( template({data:bets}) );
										  	       
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

			//	console.log(jsondata.totalExposure)
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
		//	url:'<s:url value="/api/getMyProfitT20/"/>'+$("#roundid").val(),
			url:expo_base_url+'/api/getMyProfitT20/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
//			 url:'http://bm.com/api/getMyProfit',
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  var data = jsondata;
					 
					  $("#sid2").html(jsondata.pnl1);
		  	          $("#sid1").html(jsondata.pnl3);
						
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
