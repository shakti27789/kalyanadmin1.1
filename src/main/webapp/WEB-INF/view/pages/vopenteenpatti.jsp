<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<!-- The core Firebase JS SDK is always required and must be listed first -->


<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="0" id="cardc1text">


<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">Open Teenpatti Rules &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span">{{data.t1.0.mid}}</span>
                    </span>
				</div>
				<div class="score-panel">
					<div class="videostream lucky7-screen open-teenpatti-wrap">
						<div class="showcards">
							<div class="title text-center mb-4">Open Teenpatti</div>
							<div class="card-box">
								<ul class="card-list">
									<li class="card-wrap">
										<img src="../../images/backcover.png"  id="card9" class="card_image card-opening"  width="40">
									</li>
									<li class="card-wrap">
										<img src="../../images/backcover.png"  id="card18" class="card_image card-opening"  width="40">
									</li>
									<li class="card-wrap">
										<img src="../../images/backcover.png"  id="card27" class="card_image card-opening"  width="40">
									</li>
								</ul>
								<div class="card-label">DEALER</div>								
								<div class="aaa-chart">
									<table class="table table-bordered">
										<tbody>
										<tr>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card1" class="card_image card-opening" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card2" class="card_image card-opening" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card3" class="card_image card-opening" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card4" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card5" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card6" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>"id="card7" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card8" class="card_image" width="35">
											</td>
										</tr>
										<tr>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card10" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card11" class="card_image" width="35">
											</td>
											
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card12" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card13" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card14" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card15" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card16" class="card_image" width="35">
											</td>

											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card17" class="card_image" width="35">
											</td>
										</tr>
										<tr>
											
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card19" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card20" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card21" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card22" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card23" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card24" class="card_image" width="35">
											</td>

											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card25" class="card_image" width="35">
											</td>
											<td>
												<img src="<s:url value='/images/backcover.png'/>" id="card26" class="card_image" width="35">
											</td>
										</tr>
										<tr>
											<th>1</th>
											<th>2</th>
											<th>3</th>
											<th>4</th>
											<th>5</th>
											<th>6</th>
											<th>7</th>
											<th>8</th>
										</tr>
									</tbody>
									</table>
								</div>
							</div>
							<div id="holder">
								<div class="digits">GO!!</div>
							</div>
						</div>						
					</div>
				</div>


		
				<!--Newly Added-->
				<div id="oddsdata">


					<div class="table-responsive main-market m-t-0">
					<table class="table coupon-table table table-bordered ">
					  <thead>
						 <tr>
							<th class="box-4"></th>
							<th class="box-3 back-color">Back (Min: 100 Max: 10000)</th>
							<th class="box-3 back-color">(Min: 100 Max: 10000)</th>
						 </tr>
					  </thead>
					  <tbody>
						<!-- for first row start-->
						 <tr data-title="" >
							<td class="box-4" >
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 1</b>
										<b id="sid1" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player1" class="team_name"></b>
									</div>
								</div>
							</td>
							<td class="text-center box-3 back bet-info p1p">
								<button class="back">
									<span class="d-block text-bold odd"><b></b>1.98</span>
									<span class="d-block" style="color: black;">0</span>
								</button>
							</td>
							<td class="text-center box-3 back suspended">
								<button class="back"><span class="d-block text-bold odd"><b>Pair plus 1</b></span> 
								<span class="d-block" style="color: black;">0</span></button>
							</td>
						 </tr>
						<!-- for first row end-->
						<!-- for second row start-->
						 <tr data-title="">
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 2</b>
										<b id="sid2" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player2" class="team_name"></b>
									</div>
								</div>
							</td>
							<td class="text-center box-3 back bet-info p2p">
								<button class="back">
									<span class="d-block text-bold odd"><b></b>1.98</span>
									<span class="d-block" style="color: black;">0</span>
								</button>
							</td>
							<td class="text-center box-3 back suspended">
								<button class="back"><span class="d-block text-bold odd"><b>Pair plus 2</b></span> 
								<span class="d-block" style="color: black;">0</span></button>
							</td>
						 </tr>
						<!-- for second row end-->
						<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 3</b>
										<b id="sid3" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player3" class="team_name"></b>
									</div>
								</div>
							</td>
								<td class="text-center box-3 back bet-info p2p">
									<button class="back">
										<span class="d-block text-bold odd"><b></b>1.98</span>
										<span class="d-block" style="color: black;">0</span>
									</button>
								</td>
								<td class="text-center box-3 back suspended">
									<button class="back"><span class="d-block text-bold odd"><b>Pair plus 3</b></span> 
									<span class="d-block" style="color: black;">0</span></button>
								</td>
						 </tr>
						<!-- for second row end-->
						<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 4</b>
										<b id="sid4" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player4" class="team_name"></b>
									</div>
								</div>
							</td>
									<td class="text-center box-3 back bet-info p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b></b>1.98</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 back suspended">
										<button class="back"><span class="d-block text-bold odd"><b>Pair plus 4</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						<!-- for second row end-->

<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 5</b>
										<b id="sid5" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player5" class="team_name"></b>
									</div>
								</div>
							</td>
									<td class="text-center box-3 back bet-info p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b></b>1.98</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 back suspended">
										<button class="back"><span class="d-block text-bold odd"><b>Pair plus 4</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						<!-- for second row end-->


<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 6</b>
										<b id="sid6" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player6" class="team_name"></b>
									</div>
								</div>
							</td>
									<td class="text-center box-3 back bet-info p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b></b>1.98</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 back suspended">
										<button class="back"><span class="d-block text-bold odd"><b>Pair plus 4</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						<!-- for second row end-->

<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 7</b>
										<b id="sid7" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player7" class="team_name"></b>
									</div>
								</div>
							</td>
									<td class="text-center box-3 back bet-info p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b></b>1.98</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 back suspended">
										<button class="back"><span class="d-block text-bold odd"><b>Pair plus 4</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						<!-- for second row end-->

<!-- for second row start-->
						 <tr>
							<td class="box-4">
								<div class="form-row">
									<div class="col-lg-4">
										<b>Player 8</b>
										<b id="sid8" class="ml-4"></b>
									</div>
									<div class="col-lg-8 text-left text-lg-right mt-2 mt-lg-0">
										<b id="player8" class="team_name"></b>
									</div>
								</div>
							</td>
									<td class="text-center box-3 back bet-info p2p">
										<button class="back">
											<span class="d-block text-bold odd"><b></b>1.98</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 back suspended">
										<button class="back"><span class="d-block text-bold odd"><b>Pair plus 4</b></span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						<!-- for second row end-->
					  </tbody>
					</table>
					</div>
						

				</div>
				<!--Newly Added-->
</script>

<script id="resulthandler" type="text/x-handlebars-template">
			
<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >R</span> 
 			
							
				
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
	
var a=0;	
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


     	var starCountRefResult = secondaryApp.database().ref(vopenteenpattidata+"/result");
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
						url:base_url+"/api/findByEventIdAndIsActive/"+${eventid}+'/true',
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
						url:base_url+"/api/VOPENTPData",
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								
								 $("#vlucky7div").html('');
									 var source   = $("#vluckyoddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#vlucky7div").append( template({data:JSON.parse(jsondata)}) );
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(vopenteenpattidata+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										  	marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								             
										     console.log("datac1==>"+data.t1[0].C1)
										            var aalltemp = new Array();
													aalltemp = data.t1[0].C1.split(",");


													if(a==0){
														for (let i = 0; i < aalltemp.length; i++) {
					                                       
															$("#cardc1text").val(aalltemp.length)
															$('#card'+(i+1)).attr('src', '../../images/cards/'+aalltemp[i]+'.png');
															 var cardname = aalltemp[i].slice(-2);
                                                      	   
                                                     	    if(cardname =="DD"){
                                                     	    	 var card ="eat.png"
                                                         	}else if(cardname =="HH"){
                                                     	    	 var card ="pan.png"
                                                         	}else if(cardname =="CC"){
                                                     	    	 var card ="cdee.png"
                                                         	}else if(cardname =="SS"){
                                                     	    	var card ="hukum.png"
                                                             }
                                                     	     var number = cardValue(aalltemp[i])
                                                               if((i+1) == 1 || (i+1) == 10 || (i+1) == 19){
                                                            	  
                                                            	   
                                                            	   $("#player1").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }

                                                               if((i+1) == 2 || (i+1) == 11 || (i+1) == 20){
                                                             	  
                                                            	   $("#player2").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }

                                                               if((i+1) == 3 || (i+1) == 12 || (i+1) == 21){
                                                             	  
                                                            	   $("#player3").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }
                                                               if((i+1) == 4 || (i+1) == 13 || (i+1) == 22){
                                                              	  
                                                            	   $("#player4").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }

                                                               if((i+1) == 5 || (i+1) == 14|| (i+1) == 23){
                                                              	  
                                                            	   $("#player5").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }
                                                               if((i+1) == 6 || (i+1) == 15 || (i+1) == 24){
                                                              	  
                                                            	   $("#player6").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }

                                                               if((i+1) == 7 || (i+1) == 16 || (i+1) == 25){
                                                              	  
                                                            	   $("#player7").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }
                                                               if((i+1) == 8 || (i+1) == 17 || (i+1) == 26){
                                                               	  
                                                            	   $("#player8").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
   																
                                                                   }
																 	
											                }
													}else{
														 if(aalltemp[0] != ""){
															 console.log("A1 is Not Blank.."+aalltemp[0])
															$("#cardc1text").val(aalltemp.length)
															
														
															 if(aalltemp[0].length>0 && aalltemp.length == 1){
                                                                var card  =cardName(aalltemp[0]) 
                                                                var number = cardValue(aalltemp[0])
																$('#card1').attr('src', '../../images/cards/'+aalltemp[0]+'.png');
																$("#player1").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1" ></b>') 
																
	                                                          
															 }
															 if(aalltemp.length>1 && aalltemp.length==2){
																 var card  =cardName(aalltemp[1]) 
																   var number = cardValue(aalltemp[1])
																 $('#card2').attr('src', '../../images/cards/'+aalltemp[1]+'.png');
																 $("#player2").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }

															 if(aalltemp.length>2 && aalltemp.length==3){
																 var card  =cardName(aalltemp[2]) 
																   var number = cardValue(aalltemp[2])
																 $('#card3').attr('src', '../../images/cards/'+aalltemp[2]+'.png');
																 $("#player3").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }
															 if(aalltemp.length>3 && aalltemp.length==4){
																 var card  =cardName(aalltemp[3]) 
																   var number = cardValue(aalltemp[3])
																 $('#card4').attr('src', '../../images/cards/'+aalltemp[3]+'.png');
																 $("#player4").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }
															 if(aalltemp.length>4 && aalltemp.length==5){
																 var card  =cardName(aalltemp[4]) 
																   var number = cardValue(aalltemp[4])
																 $('#card5').attr('src', '../../images/cards/'+aalltemp[4]+'.png');
																 $("#player5").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }
															 if(aalltemp.length>5 && aalltemp.length==6){
																 var card  =cardName(aalltemp[5]) 
																   var number = cardValue(aalltemp[5])
																 $('#card6').attr('src', '../../images/cards/'+aalltemp[5]+'.png');
																 $("#player6").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }
															 if(aalltemp.length>6 && aalltemp.length==7){
																 var card  =cardName(aalltemp[6]) 
																   var number = cardValue(aalltemp[6])
																 $('#card7').attr('src', '../../images/cards/'+aalltemp[6]+'.png');
																 $("#player7").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }
															 if(aalltemp.length>7 && aalltemp.length==8){
																 var card  =cardName(aalltemp[7]) 
																   var number = cardValue(aalltemp[7])
																 $('#card8').attr('src', '../../images/cards/'+aalltemp[7]+'.png');
																 $("#player8").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }

															 if(aalltemp.length>8 && aalltemp.length==9){
																
																 $('#card9').attr('src', '../../images/cards/'+aalltemp[8]+'.png');
															 }


															 if(aalltemp.length>9 && aalltemp.length==10){
																 var card  =cardName(aalltemp[9]) 
																   var number = cardValue(aalltemp[9])
																 $('#card10').attr('src', '../../images/cards/'+aalltemp[9]+'.png');
																 $("#player1").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }

															 if(aalltemp.length>10 && aalltemp.length==11){
																 var card  =cardName(aalltemp[10]) 
																   var number = cardValue(aalltemp[10])
																 $('#card11').attr('src', '../../images/cards/'+aalltemp[10]+'.png');
																 $("#player2").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>11 && aalltemp.length==12  ){
																 var card  =cardName(aalltemp[11]) 
																   var number = cardValue(aalltemp[11])
																 $('#card12').attr('src', '../../images/cards/'+aalltemp[11]+'.png');
																 $("#player3").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>12 && aalltemp.length==13){
																 var card  =cardName(aalltemp[12]) 
																   var number = cardValue(aalltemp[12])
																 $('#card13').attr('src', '../../images/cards/'+aalltemp[12]+'.png');
																 $("#player4").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>13 && aalltemp.length==14){
																 var card  =cardName(aalltemp[13]) 
																   var number = cardValue(aalltemp[13])
																 $('#card14').attr('src', '../../images/cards/'+aalltemp[13]+'.png');
																 $("#player5").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>14 && aalltemp.length==15){
																 var card  =cardName(aalltemp[14]) 
																   var number = cardValue(aalltemp[14])
																 $('#card15').attr('src', '../../images/cards/'+aalltemp[14]+'.png');
																 $("#player6").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>15 && aalltemp.length==16){
																 var card  =cardName(aalltemp[15]) 
																   var number = cardValue(aalltemp[15])
																 $('#card16').attr('src', '../../images/cards/'+aalltemp[15]+'.png');
																 $("#player7").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>16 && aalltemp.length==17){
																 var card  =cardName(aalltemp[16])
																   var number = cardValue(aalltemp[16]) 
																 $('#card17').attr('src', '../../images/cards/'+aalltemp[16]+'.png');
																 $("#player8").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>17 && aalltemp.length==18){

																 $('#card18').attr('src', '../../images/cards/'+aalltemp[17]+'.png');
															 }	

															 if(aalltemp.length>18 && aalltemp.length==19){

																 var card  =cardName(aalltemp[18]) 
																   var number = cardValue(aalltemp[18])
																 $('#card19').attr('src', '../../images/cards/'+aalltemp[18]+'.png');
																 $("#player1").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>19 && aalltemp.length==20){

																 var card  =cardName(aalltemp[19]) 
																   var number = cardValue(aalltemp[19])
																 $('#card20').attr('src', '../../images/cards/'+aalltemp[19]+'.png');
																 $("#player2").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>20 && aalltemp.length==21){
																 var card  =cardName(aalltemp[20]) 
																   var number = cardValue(aalltemp[20])
																 $('#card21').attr('src', '../../images/cards/'+aalltemp[20]+'.png');
																 $("#player3").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>21 && aalltemp.length==22){
																 var card  =cardName(aalltemp[21]) 
																   var number = cardValue(aalltemp[21])
																 $('#card22').attr('src', '../../images/cards/'+aalltemp[21]+'.png');
																 $("#player4").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>22  && aalltemp.length==23){

																 var card  =cardName(aalltemp[22]) 
																   var number = cardValue(aalltemp[22])
																 $('#card23').attr('src', '../../images/cards/'+aalltemp[22]+'.png');
																 $("#player5").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>23 && aalltemp.length==24){
																 var card  =cardName(aalltemp[23]) 
																   var number = cardValue(aalltemp[23])
																 $('#card24').attr('src', '../../images/cards/'+aalltemp[23]+'.png');
																 $("#player6").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>24  && aalltemp.length==25){

																 var card  =cardName(aalltemp[24]) 
																   var number = cardValue(aalltemp[24])
																 $('#card25').attr('src', '../../images/cards/'+aalltemp[24]+'.png');
																 $("#player7").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>2 && aalltemp.length==26){

																 var card  =cardName(aalltemp[25]) 
																   var number = cardValue(aalltemp[25])
																 $('#card26').attr('src', '../../images/cards/'+aalltemp[25]+'.png');
																 $("#player8").append('<b class="mr-3">'+number+'<img src="../../images/cards/'+card+'" width="15" class="ml-1"></b>') 
																	
															 }	

															 if(aalltemp.length>26  && aalltemp.length==27){

																 $('#card27').attr('src', '../../images/cards/'+aalltemp[26]+'.png');
															 }	

																
																
															 }else
																 {
																 console.log("A1 is Blank..")
																	
																 }
														}
												
													a++;
													
													
											
										
											 if(parseInt(data.t1[0].autotime)== 0){
												$(".digits").html('GO!!');
												$(".digits").removeClass('active');
													
												$(".bet-info").addClass('suspended');
												
											 }else{
												 $(".digits").html(data.t1[0].autotime);
												 $(".digits").addClass('active');
												
												 $('.card_image').attr('src', '../../images/cards/backcover.png');
												 $(".bet-info").removeClass('suspended');
												 $(".team_name").html('');
											}
										  });
									
										
							      }
					        }
				      });
			  }
			

	

function getEvents() {
	
	 $.ajax({
	 
			type:'GET',
			url:base_url+"/api/getevent?eventid="+${eventid},
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
			url:expo_base_url+'/api/getMyProfitVOpenTeenPatti/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
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
		  	          $("#sid7").html(jsondata.pnl7);
		  	          $("#sid8").html(jsondata.pnl8);
						
					}
					
				}
		
	});
}
function matchbetUnLock(){
  	
	$.ajax({
   			type:'POST',
   			url:base_url+"/api/betUnLockMatch/"+${eventid},
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

function cardName(value){

	var cardname = value.slice(-2);
	  card =""; 
	    if(cardname =="DD"){
	    	  card ="eat.png"
 	}else if(cardname =="HH"){
	    	  card ="pan.png"
 	}else if(cardname =="CC"){
	    	  card ="cdee.png"
 	}else if(cardname =="SS"){
	    	 card ="hukum.png"
     }	
	return card;
}

function cardValue (value){

	var cardvalue = value.slice(0, 1);
	  number =""; 
	    if(cardvalue =="A"){
	    	number ="A"
 	}else if(cardvalue =="2"){
 		    number ="2"
 	}else if(cardvalue =="3"){
 		    number ="3"
 	}else if(cardvalue =="4"){
 		  number ="4"
    }
 	else if(cardvalue =="5"){
		  number ="5"
   }else if(cardvalue =="6"){
		  number ="6"
   }else if(cardvalue =="7"){
		  number ="7"
   }else if(cardvalue =="8"){
		  number ="8"
   }else if(cardvalue =="9"){
		  number ="9"
   }else if(cardvalue =="1"){
		  number ="10"
   }else if(cardvalue =="J"){
		  number ="J"
   }else if(cardvalue =="Q"){
		  number ="Q"
   }else if(cardvalue =="K"){
		  number ="K"
   }		
	return number;
}
setInterval(function(){getMyProfit()},2000)
 setInterval(function(){getAddminBet()},3000)

</script>
<jsp:include page="resultdetail.jsp" />
 <jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
<jsp:include page="handlebars.jsp" />
