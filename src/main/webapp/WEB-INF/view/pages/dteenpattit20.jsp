<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="teenpattioddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">
<input type="hidden" value="{{data.t1.0.C2}}" id="cardc2text">
<input type="hidden" value="{{data.t1.0.C3}}" id="cardc3text">
<input type="hidden" value="{{data.t1.0.C4}}" id="cardc4text">
<input type="hidden" value="{{data.t1.0.C5}}" id="cardc5text">
<input type="hidden" value="{{data.t1.0.C6}}" id="cardc6text">



<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">1 Day Live TeenPatti &nbsp; 
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
					<div data-v-91269fe6="" class="video-overlay">
						<div data-v-91269fe6="" class="videoCards">
							<div data-v-91269fe6="">
								<h3 data-v-91269fe6="" class="text-white">PLAYER A</h3>
								 <div data-v-91269fe6="">
									<img data-v-91269fe6="" id="cardc1" src=""> 
									<img data-v-91269fe6=""  id="cardc2" src="">
									<img data-v-91269fe6=""  id="cardc3" src="">
								 </div>
							</div> <div data-v-91269fe6="">
							   <h3 data-v-91269fe6="" class="text-white">PLAYER B</h3> 
								<div data-v-91269fe6="">
								  <img data-v-91269fe6=""    id="cardc4" src=""> 
									<img data-v-91269fe6=""  id="cardc5" src="">
									<img data-v-91269fe6=""  id="cardc6" src="">
								</div>
								</div>
							 </div>
					</div>					
<iframe id="tv-frame" style="background-color: black;" width="100%" height="400" src="https://oddfancyapi.in/dvideo/teen20" style="border:0px"></iframe>
<div class="clock flip-clock-wrapper clock2digit" id="clock"></div>
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
			{{#if_eq result.1.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.1.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.1.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.2.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.2.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result"onclick="resultDeatil({{result.2.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.3.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.3.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.3.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.4.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.4.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.4.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.5.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.5.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.5.mid}})" >B</span>
			{{/if_eq}}
			{{#if_eq result.6.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.6.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.6.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.7.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.7.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.7.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.8.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.8.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.8.mid}})">B</span>
			{{/if_eq}}
			{{#if_eq result.9.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.9.mid}})">A</span> 
 			{{else}}
				<span class="ball-runs playerb last-result" onclick="resultDeatil({{result.9.mid}})">B</span>
			{{/if_eq}}					
				
</script>


  <script id="matchedBetsHandler" type="text/x-handlebars-template">
<button type="button" class="btn btn-info blue-btn btn-sm" onclick="getAdminViewMoreBets('Match Odds')">View More</button> 
			<div class="table-responsive">
				<table class="table table-striped table-bordered cash-trans dataTable no-footer" id="">
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
		<div class="row">
			<div class="col-md-8">
				<div class="coupon-card" id="tp20div"></div>
				
				
		<!-- 	 -->
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
var clock;

$(document).ready(function(){
	checkEventActive();
	getEvents();
	getMyProfit();

	

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
if(${user.usertype} ==4){
	usertype ="adminid";
}else if(${user.usertype} ==5){
	usertype ="subadminid";
} else if(${user.usertype} ==0){
	usertype ="supermasterid";
} else if(${user.usertype} ==1){
	usertype ="masterid";
} else if(${user.usertype} ==2){
	usertype ="dealerid";
} 


console.log("usertype==>"+usertype);
		var starCountRefResult = secondaryApp.database().ref('webData/t20/result');
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
						url:'<s:url value="/api/DTP20Data/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
									 $("#tp20div").html('');
									 var source   = $("#teenpattioddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#tp20div").append( template({data:JSON.parse(jsondata)}) );

								     clock = $('.clock').FlipClock({
								         clockFace: 'Counter' 
								       });
								
                                     
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref('diamond/t20/data/data');
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();


										   marketId =data.t1[0].mid;

											console.log("autotime==>"+data.t1[0].autotime)
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								              
								           

											clock.setValue(data.t1[0].autotime);
								            
										 	if(data.t1[0].C1 == "1"){
												 $('#cardc1').attr('src',"../../images/backcover.png");
												
												 
												}else{
													 $('#cardc1').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C1+".png");
												
											}

											if(data.t1[0].C4 == "1"){
												 $('#cardc4').attr('src',"../../images/backcover.png");
												
												}else{ 
													$('#cardc4').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C4+".png");
												
											}

											if(data.t1[0].C2 == "1"){
												 $('#cardc2').attr('src',"../../images/backcover.png");
												 
												}else{
													 $('#cardc2').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C2+".png");
														
													
											}


											if(data.t1[0].C5 == "1"){
												 $('#cardc5').attr('src',"../../images/backcover.png");
												 
												}else{ 
													$('#cardc5').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C5+".png");
												
												}
											if(data.t1[0].C3 == "1"){
												 $('#cardc3').attr('src',"../../images/backcover.png");
												
												}else{
													$('#cardc3').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C3+".png");
												
											}
											if(data.t1[0].C6 == "1"){
												 $('#cardc6').attr('src',"../../images/backcover.png");
												 
												}else{
													$('#cardc6').attr('src',"https://dzm0kbaskt4pv.cloudfront.net/v3/static/front/img/cards/"+data.t1[0].C6+".png");
													
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
											
											//  clock.setValue(data.t1[0].autotime);
											 if(parseInt(data.t1[0].autotime)== 0){
												
												// $(".clock ").val(data.t1[0].autotime);
												
												
											 }else{
												     //clock.setValue(data.t1[0].autotime);
													// $(".clock ").val(data.t1[0].autotime);
												
												// $(".digits").addClass('active');
											 }
											
										 
											  if(marketId!="0"){

													 db.collection("t_betlist").where("marketid", "==", marketId).where(usertype, "==", '${user.id}').where("isactive", "==", true)
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
												  	       
												  	    });

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
			

		function updateEventExposure(){

	 swal({
	title: 'Are you sure?',
	text: 'You Want to Update Maximum Amount',
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
		url :'<s:url value="/api/updateEventExposure/"/>'+${eventid}+"/"+$("#eventexposure").val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			if(${user.usertype} !=6){
				$("#eventExpoDiv").append('<button style="" onclick="removeEventExposure()" id="reseteventExpo">Reset</button>');
				 
				}
			     
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
	
	} 
	
	});
}


function removeEventExposure() {
	
	 swal({
	title: 'Are you sure?',
	text: 'You Want to Remove',
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
		type:'DELETE',
		url :'<s:url value="/api/removeEventExposure?eventid="/>'+${eventid},
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			 $("#reseteventExpo").remove();
			 
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
	
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
				getSelectedEventExposure(jsondata.totalExposure)
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}

function getSelectedEventExposure(maxBet) {
	   
	var i=0;
	 $.ajax({
	 
			type:'GET',
			url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=maxbet',
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
			   $.each(jsondata, function( key, value ) {
				   
					   if(parseInt(maxBet) == parseInt(value.amount)){
						
							  $("#eventexposure").append('<option value="'+value.amount+'" selected> '+value.amount+'</option>')
							  if(${user.usertype} !=6){
								  $("#eventExpoDiv").append('<button style="" onclick="removeEventExposure()" id="reseteventExpo">Reset</button>');
								   
								  }
					         
						   }else{
					   $("#eventexposure").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					      console.log("maxbetgetSelectedEventExposure==>"+value.amount)
					   }
				  
			});
			},
			complete: function(jsondata,textStatus,xhr) {
				// $("#maximumbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
						
		    } 
		});     	    	
}

function getMyProfit()
{
	
	
		
		$.ajax({
			type:'GET',
			url:'<s:url value="/api/getMyProfitT20/"/>'+$("#roundid").val(),
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
setInterval(function(){getMyProfit()},2000)
</script>
<style>
.video-overlay {
    position: absolute;
    top: 0;
    background: rgba(0, 0, 0, 0.4);
    height: auto;
    left: 0;
    content: "";
    top: 0;
    padding: 5px;
}
.video-overlay img {
    width: 35px;
    height: auto;
    margin-right: 2px;
    margin-left: 2px;
}


</style>
<jsp:include page="resultdetail.jsp" />
<jsp:include page="handlebars.jsp" />
