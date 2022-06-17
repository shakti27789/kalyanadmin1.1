 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">1 DAY POKER  Rules  &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span"></span>
                    </span>
				</div>

<div class="score-panel">
					<div class="lucky7-screen">
						<iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="https://shroute.casinovid.in/diamondvideo/sh.php?id=3051"></iframe>
						
             <!--     <iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="http://cricexch.live/video/onedaypoker"></iframe>
			-->			
					</div>
				</div>

<div id="oddsdata" class="col-md-12">
</div>


</script>


<script id="oddsHandler" type="text/x-handlebars-template">
<input type="hidden" value="{{jdata.t1.0.C1}}" id="cardc1text">
<input type="hidden" value="{{jdata.t1.0.C2}}" id="cardc2text">

<input type="hidden" id="roundid" value="{{jdata.t1.0.mid}}">




<div class="form-row mt-2">
  <div class="col-md-12">
    <div class="table-responsive main-market m-t-0">
				   <table class="table coupon-table table table-bordered ">
					  <thead>
						 <tr>
							<th class="box-2">Min :{{jdata.t2.[0].min}} Max :{{jdata.t2.[0].max}}</th>
							<th class="box-2 back-color">Back</th>
							<th class="box-3 lay-color">Lay</th>
						 </tr>
					  </thead>
					  <tbody>
					
						<!-- for first row start-->

		 
						 <tr data-title="" class="bet-info">
							<td class="box-5"><b>{{jdata.t2.[0].nation}}<span id="sid1"></span></b></td>
                          			<td class="text-center box-2 back bet-info{{jdata.t2.[0].sid}}">
										<button class="back">
											<span class="d-block text-bold odd" id="back1{{jdata.t2.[0].sid}}">{{b1}}</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 lay bet-info{{jdata.t2.[0].sid}}  ">
										<button class="back"><span class="d-block text-bold odd" id="lay1{{jdata.t2.[0].sid}}">{{l1}}</span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>


						 <tr data-title="" class="bet-info" id="bet-info">
							<td class="box-5"><b>{{jdata.t2.[1].nation}}<span id="sid1"></span></b></td>
                          			<td class="text-center box-2 back bet-info{{jdata.t2.[1].sid}}">
										<button class="back">
											<span class="d-block text-bold odd" id="back1{{jdata.t2.[1].sid}}">{{b1}}</span>
											<span class="d-block" style="color: black;">0</span>
										</button>
									</td>
									<td class="text-center box-3 lay bet-info{{jdata.t2.[1].sid}}  ">
										<button class="back"><span class="d-block text-bold odd" id="lay1{{jdata.t2.[1].sid}}">{{l1}}</span> 
										<span class="d-block" style="color: black;">0</span></button>
									</td>
						 </tr>
						<!-- for first row end-->

		 			  </tbody>
				   </table>
				</div>
			</div>	







</script>


<script id="pokeronedaysresulthandler" type="text/x-handlebars-template">
	

		    {{#if_eq result.0.result "11"}}  											
				<span class="ball-runs playera last-result" onclick="resultDetailLiveCasino({{result.0.mid}})" >A</span> 
 			{{/if_eq}}

  			{{#if_eq result.0.result "21"}}  											
				<span class="ball-runs playera last-result" onclick="resultDetailLiveCasino({{result.0.mid}})" >B</span> 
 			{{/if_eq}}

  			{{#if_eq result.0.result "0"}}  											
				<span class="ball-runs playera last-result" onclick="resultDetailLiveCasino({{result.0.mid}})" >T</span> 
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


     	var starCountRefResult = secondaryApp.database().ref(livepoker1day+"/result");
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		    console.log(result)
			
			$("#last-result").html('');
			var source   = $("#pokeronedaysresulthandler").html();
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

var marketId =null;
		function loadHtml(){
			$.ajax({
						type:'GET',
						url:'<s:url value="/api/DTPokerOnedayData/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								
								 $("#vlucky7div").html('');
									 var source   = $("#vluckyoddshandler").html();
								     var template = Handlebars.compile(source);
								    
								     var source1   = $("#oddsHandler").html();
								     var template1 = Handlebars.compile(source1);
									    
								     $("#vlucky7div").append(template({data:jsondata}) );
								     var obj =JSON.parse(jsondata.data); 
								     
								     $("#oddsdata").append( template1({jdata:obj}) );
								  //   console.log(obj)
								     var starCountRef = secondaryApp.database().ref(livepoker1day+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
									
										    
										    $.each(data.t2, function( key, value ) {
												$("#back1"+value.sid).html(value.b1)
												$("#lay1"+value.sid).html(value.l1)
											        
											        if(value.gstatus == "1" ){
															
											    	     $(".bet-info"+value.sid).removeClass("suspended")		
													      
												     }else{

												    	 $(".bet-info"+value.sid).addClass("suspended")
														   
													  }
												  
											  
												     $("#roundid").val(value.mid)
													 $("#roundid_span").html(value.mid)
											  });
										  
										   
										   
										});							





										
										
											
										  
							      }
					}
				});
			  
		}
			
		function getMyProfit()
		{
			
			
				
				$.ajax({
					type:'GET',
					url:expo_base_url+'/api/getMyProfitT20/'+$("#roundid").val(),
					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200)
						{
							  var data = jsondata;
							  $("#sid1").html(jsondata.pnl1);
				  	          $("#sid3").html(jsondata.pnl3);
							
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
		setInterval(function(){getAddminBet()},3000)
</script>
<jsp:include page="resultdetail.jsp" />
 <jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
<jsp:include page="handlebars.jsp" />
