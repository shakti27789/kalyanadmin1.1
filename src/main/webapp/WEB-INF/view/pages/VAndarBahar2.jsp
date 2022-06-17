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
					<span class="card-header-title">Ander Bahar 2 Rules &nbsp; 
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
							<div class="title text-center">Ander Bahar 2</div>
							<div class="card-box">
								<div class="showcards-container"> 
									<div class="showcards-wrapper">
										<div class="showcards anderBaher-showCard anderBaher2-showCard" style="overflow: visible">
											<div class="team-player-game-cards mt-4 mt-md-0 pt-4 pt-md-0"> 
												<div class="form-row w-100 justify-content-center align-items-center">
													<div class="col-2 col-lg-2 col-xl-1"> 
														<div class="title text-center set-height">Ander</div>
														<div class="title text-center set-height">Bahar</div>
													</div>
													<div class="col-3 col-lg-2 col-xl-1"> 
														<div class="card-pic set-height">
															<img src="<s:url value='/images/backcover.png'/>" width="50" id="cardc1" class="card-opening img-fluid mb-10">
														</div>
													</div>
													<div class="col-3 col-md-2 col-lg-2 col-xl-2">
														<div class="card-pic set-height" >
														  	<img src="<s:url value='/images/backcover.png'/>" id="cars_image_andar_fbet" width="50" class="card-opening img-fluid mb-10">
														</div>
														<div class="card-pic set-height" >
															<img src="<s:url value='/images/backcover.png'/>" id="cars_image_bahar_fbet" width="50" class="card-opening img-fluid mb-10">
														</div>
													</div>

													<div class="col-3 col-md-2 col-lg-2 col-xl-2">
														<div class="card-pic set-height" >
														  	<img src="<s:url value='/images/backcover.png'/>" style="display:none;" id="cars_image_andar_sbet" width="50" class="card-opening img-fluid mb-10">
														</div>
														<div class="card-pic set-height" >
															<img src="<s:url value='/images/backcover.png'/>" style="display:none;" id="cars_image_bahar_sbet" width="50" class="card-opening img-fluid mb-10">
														</div>
													</div>
											<!--		<div class="col-4 col-lg-6 col-xl-8">
														<div class="card-wrap-slider owl-carousel set-height" id="cars_image_andar">
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
														</div>
														<div class="card-wrap-slider owl-carousel set-height" id="cars_image_bahar">
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
															<div class="card-pic">
																<img src="/images/cards/8HH.png" width="50" class="card-opening img-fluid mb-10">
															</div>
														</div>
													</div> -->
												</div>
											</div>
										</div>
									</div>
								</div>								
								<div id="holder">
									<div class="digits active">{{data.t1.0.autotime}}</div>
								</div>
							</div>
						</div>						
					</div>
				</div>
			<div class="form-row mt-2 mb-2">
				<div class="col-lg-6 mb-2 mb-lg-0">
					<div class="paati-seven-cont p-3"> 
						<div class="form-row justify-content-center align-items-center">
							<div class="col-1">A</div>
							<div class="col-10">
								<div class="form-row">
								
									<div class="col-6">
										<div class="text-center">
											<button type="button" id="f_bet_andar" class="patti-count-btn bet-info">1st Bet <br/>{{data.t2.[1].[rate]}}</button>
											<div  id="sid2" class="positive">0</div>
										</div>
									</div>
									<div class="col-6">
										<div class="text-center">	
											<button type="button" id="s_bet_andar" class="patti-count-btn bet-info">2nd Bet<br/>{{data.t2.[2].[rate]}}</button>
											<div id="sid3" class="positive">0</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-1">A</div>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="paati-seven-cont p-3">
						<div class="form-row justify-content-center align-items-center">
							<div class="col-1">B</div>
							<div class="col-10">
								<div class="form-row"> 
									
									<div class="col-6">
										<div class="text-center">
											<button type="button" id="f_bet_bahar" class="patti-count-btn bet-info">1st Bet <br/>{{data.t2.[4].[rate]}}</button>
											<div  id="sid5" class="positive">0</div>
										</div>
									</div>
									<div class="col-6">
										<div class="text-center">	
											<button type="button" id="s_bet_bahar"  class="patti-count-btn bet-info">2nd Bet<br/> {{data.t2.[4].[rate]}}</button>
											<div  id="sid6" class="positive">0</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-1">B</div>
						</div>
					</div>
				</div>
			</div>
				
			<div class="form-row mb-2">
				<div class="col-lg-6 mb-2 mb-lg-0">
					<div class="paati-seven-cont p-3">
						<div class="form-row justify-content-center align-items-center">
							<div class="col-6 text-center">
								<div class="mb-2"><b>ODD</b></div>
								<button type="button" id="odd" class="patti-count-btn bet-info suspended">{{data.t2.[23].[rate]}}</button>
								<div id="sid24" class="positive">0</div>
							</div>
							<div class="col-6 text-center">
								<div class="mb-2"><b>EVEN</b></div>
								<button type="button" id="even" class="patti-count-btn bet-info suspended">{{data.t2.[24].[rate]}}</button>
								<div id="sid25" class="positive">{{data.t2.[24].[rate]}}</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="paati-seven-cont p-3 pt-2">
						<div class="form-row justify-content-center align-items-center">
							<div class="col-3 text-center">
								<div class="mb-2">
									<img src="../../images/cards/hukum.png" width="15" class="ml-1">
								</div>
								<button type="button" id="spade" class="patti-count-btn bet-info suspended">{{data.t2.[19].[rate]}}</button>
								<div id="sid20" class="positive">0</div>
							</div>
							<div class="col-3 text-center">
								<div class="mb-2">
									<img src="../../images/cards/cdee.png" width="15" class="ml-1">								
								</div>
								<button type="button" id="club" class="patti-count-btn bet-info suspended">{{data.t2.[20].[rate]}}</button>
								<div id="sid21" class="positive">2.2</div>
							</div>
							<div class="col-3 text-center">
								<div class="mb-2">
									<img src="../../images/cards/pan.png" width="15" class="ml-1">
								</div>
								<button type="button" id="heart" class="patti-count-btn bet-info suspended">{{data.t2.[21].[rate]}}</button>
								<div id="sid22" class="positive">0</div>
							</div>
							<div class="col-3 text-center">
								<div class="mb-2">
									<img src="../../images/cards/eat.png" width="15" class="ml-1">
								</div>
								<button type="button" id="diamond" class="patti-count-btn bet-info suspended">{{data.t2.[22].[rate]}}</button>
								<div id="sid23" class="positive">2.2</div>
							</div>
						</div>
					</div>
				</div>
			
		</div>
			
			
				
</script>

<script id="resulthandler" type="text/x-handlebars-template">
			{{#if_eq result.0.winner "Ander"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >A</span> 
			{{/if_eq}}
			{{#if_eq result.0.winner "Bahar"}}  											
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
var a =0;
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


      var starCountRefResult = secondaryApp.database().ref(vandarbahar2data+"/result");
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		    console.log(result)
			
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
						url:'<s:url value="/api/VAB2Data/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								
								 $("#vlucky7div").html('');
									 var source   = $("#oddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#vlucky7div").append( template({data:JSON.parse(jsondata)}) );
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(vandarbahar2data+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();

											marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
										
									
											var aalltemp = new Array();
											C2Temp = data.t1[0].C2.split(",");
										    
											  if(data.t1[0].C1 != ""){
												     $('#cardc1').attr('src', '../../images/cards/'+data.t1[0].C1+'.png');
												}else{
													 $('#cardc1').attr('src', '../../images/backcover.png');
												}
											
												 if(C2Temp[0]!= ""){

														 $("#cars_image_andar_fbet").attr('src', '../../images/cards/'+C2Temp[1].trim()+'.png');
													}else{
														 $('#cars_image_andar_fbet').attr('src', '../../images/backcover.png');
														}
												
												 if(C2Temp.length>1){
													 if(C2Temp[0]!= ""){
														 $("#cars_image_bahar_fbet").attr('src', '../../images/cards/'+C2Temp[0].trim()+'.png');
															
														 }else{
															 $('#cars_image_bahar_fbet').attr('src', '../../images/backcover.png');
															 }
													 }else{
														 $('#cars_image_bahar_fbet').attr('src', '../../images/backcover.png');
														}
												 
												 if(C2Temp.length>2){
													 if(C2Temp[2].trim() != ""){

														 $("#cars_image_andar_sbet").attr('src', '../../images/cards/'+C2Temp[3].trim()+'.png');
														 $("#cars_image_andar_sbet").css("display","block");
													}else{
														 $('#cars_image_andar_sbet').attr('src', '../../images/backcover.png');
														 $("#cars_image_andar_sbet").css("display","none");
														}
												 }else{
													 $('#cars_image_andar_sbet').attr('src', '../../images/backcover.png');
													 $("#cars_image_andar_sbet").css("display","none");
													 }
												

												 if(C2Temp.length>3){
													 if(C2Temp[3].trim() != ""){
														 $("#cars_image_bahar_sbet").attr('src', '../../images/cards/'+C2Temp[2].trim()+'.png');
														 $("#cars_image_bahar_sbet").css("display","block");
														 }else{
															 $('#cars_image_bahar_sbet').attr('src', '../../images/backcover.png');
															 $("#cars_image_bahar_sbet").css("display","none");
															 }
	                                                 }else{
	                                                	 $('#cars_image_bahar_sbet').attr('src', '../../images/backcover.png');
	                                                	 $("#cars_image_bahar_sbet").css("display","none");
		                                                 }
											
												 
												 f_bet_bahar
												 $.each(data.t2, function(index, element) {
                                                  
                                                    if(element.sid== "2" ){
                                                         if(element.gstatus == "1"){
                                                               $("#f_bet_andar").removeClass("suspended")
                                                             }else{
                                                            	 $("#f_bet_andar").addClass("suspended")
                                                                 }
                                                     }

                                                   if(element.sid== "3" ){
                                                       if(element.gstatus == "1"){
                                                             $("#s_bet_andar").removeClass("suspended")
                                                           }else{
                                                          	 $("#s_bet_andar").addClass("suspended")
                                                               }
                                                   }

                                                   if(element.sid== "5" ){
                                                       if(element.gstatus == "1"){
                                                             $("#f_bet_bahar").removeClass("suspended")
                                                           }else{
                                                          	 $("#f_bet_bahar").addClass("suspended")
                                                               }
                                                   }

                                                 if(element.sid== "6" ){
                                                     if(element.gstatus == "1"){
                                                           $("#s_bet_bahar").removeClass("suspended")
                                                         }else{
                                                        	 $("#s_bet_bahar").addClass("suspended")
                                                             }
                                                 }

                                                 if(element.sid== "24" ){
                                                     if(element.gstatus == "1"){
                                                           $("#odd").removeClass("suspended")
                                                         }else{
                                                        	 $("#odd").addClass("suspended")
                                                             }
                                                 }

                                                 if(element.sid== "25" ){
                                                     if(element.gstatus == "1"){
                                                           $("#even").removeClass("suspended")
                                                         }else{
                                                        	 $("#even").addClass("suspended")
                                                             }
                                                   }
                                                 if(element.sid== "20" ){
                                                     if(element.gstatus == "1"){
                                                           $("#spade").removeClass("suspended")
                                                         }else{
                                                        	 $("#spade").addClass("suspended")
                                                             }
                                                   }

                                                 if(element.sid== "21" ){
                                                     if(element.gstatus == "1"){
                                                           $("#club").removeClass("suspended")
                                                         }else{
                                                        	 $("#club").addClass("suspended")
                                                             }
                                                   }

                                                 if(element.sid== "22" ){
                                                     if(element.gstatus == "1"){
                                                           $("#heart").removeClass("suspended")
                                                         }else{
                                                        	 $("#heart").addClass("suspended")
                                                             }
                                                 }

                                                 if(element.sid== "23" ){
                                                     if(element.gstatus == "1"){
                                                           $("#diamond").removeClass("suspended")
                                                         }else{
                                                        	 $("#diamond").addClass("suspended")
                                                             }
                                                   }

                                             });
												    
										 
										
							
											
										
											 if(parseInt(data.t1[0].autotime)== 0){
												$(".digits").html('GO!!');
												$(".digits").removeClass('active');
										
												
											 }else{
												 $(".digits").html(data.t1[0].autotime);
												 $(".digits").addClass('active');
												
													
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
			url:expo_base_url+'/api/getMyProfitVAndarBahar2/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  var data = jsondata;
					  $("#sid2").html(jsondata.pnl2);
					  $("#sid3").html(jsondata.pnl3);
		  	          $("#sid5").html(jsondata.pnl5);
		  	          $("#sid6").html(jsondata.pnl6);
		  	          $("#sid24").html(jsondata.pnl24);
		  	          $("#sid25").html(jsondata.pnl25);
		  	          $("#sid20").html(jsondata.pnl20);
		  	          $("#sid21").html(jsondata.pnl21);
		  	          $("#sid22").html(jsondata.pnl22);
		  	          $("#sid23").html(jsondata.pnl23);
						
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


	/*var carousel = function() {	
	$('#cars_image_andar').owlCarousel({
		loop:false,
		margin: 10,
		nav:true,
		autoplay: true,
		responsive:{
			0:{
				items: 2,
			},
			420:{
				items: 2
			},
			600:{
				items: 3
			},
			1023:{
				items: 6
			},
			1365:{
				items: 10
			},
			1600:{
				items: 14,
			}
		}

	})
	
	$('#cars_image_bahar').owlCarousel({
		margin: 10,
		nav:false,
		nav:true,
		autoplay: true,
		responsive:{
			0:{  
				items: 2,
			},
			420:{
				items: 2,
			},
			600:{ 
				items:3,
			},
			1023:{
				items: 6
			},
			1365:{
				items: 10
			},
			1600:{
				items: 14,
			}
		}
		
	})
}
$(document).ready(function(){
			carousel();
	setInterval(function(){ 
		carousel();
	}, 1000);		
})*/
</script>
<jsp:include page="resultdetail.jsp" />
 <jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
<jsp:include page="handlebars.jsp" />
