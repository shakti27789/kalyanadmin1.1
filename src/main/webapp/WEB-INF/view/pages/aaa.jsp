 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>



<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">AMAR AKBAR ANTHONY Rules &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span"></span>
                    </span>
				</div>

<div class="score-panel">
					<div class="lucky7-screen">
						<iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="https://shroute.casinovid.in/diamondvideo/sh.php?id=3056"></iframe>
					<!--	<iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="http://cricexch.live/video/aaa"></iframe>-->
			
			
					</div>
				</div>
<div id="oddsdata">
</div>


</script>


<script id="oddsHandler" type="text/x-handlebars-template">

		<div class="paati-seven-cont-part-3 mb-2 mt-2 p-3 pb-0">
				<div class="form-row">
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>A.</span> Amar</div>
{{#if_eq jdata.data.t2.[0].gstatus "0"}}
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="amar">
                            <button class="back">
                                <span class="odd{{jdata.data.t2.[0].sid}}">2.12</span>
                            </button> 
                            <button class="lay">
                                <span class="odd{{jdata.data.t2.[0].sid}}">2.22</span>
                            </button>
                        </div>
{{else}}
  <div class="aaa-button clearfix patti-count-btn bet-info " id="amar">
                            <button class="back ">
                               <span class="odd{{jdata.data.t2.[0].sid}}">2.12</span>
                            </button> 
                            <button class="lay">
                                <span class="odd{{jdata.data.t2.[0].sid}}">2.15</span>
                            </button>
                        </div>
{{/if_eq}}	
                        <div style="color: black; font-weight: 700;" id="sid1">0</div>
                    </div>                
                </div>
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>B.</span> Akbar</div>
			{{#if_eq jdata.data.t2.[1].gstatus "0"}}
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="akabar">
                            <button class="back">
                                <span class="odd{{jdata.data.t2.[1].sid}}">3.15</span>
                            </button> 
                            <button class="lay">
                                <span class="odd{{jdata.data.t2.[1].sid}}">3.35</span>
                            </button>
                        </div>
				{{else}}
						<div class="aaa-button clearfix patti-count-btn bet-info" id="akabar">
                            <button class="back">
                                <span class="odd{{jdata.data.t2.[1].sid}}">3.15</span>
                            </button> 
                            <button class="lay">
                                <span class="odd{{jdata.data.t2.[1].sid}}">3.35</span>
                            </button>
                        </div>
				{{/if_eq}}	
                        <div style="color: black; font-weight: 700;" id="sid2">0</div>
                    </div>                
                </div>
                <div class="col-md-4">   
                    <div class="text-center">
                        <div class="mb-1 aaa-title"><span>C.</span> Anthony</div>
                      {{#if_eq jdata.data.t2.[2].gstatus "0"}} 	
                        <div class="aaa-button clearfix patti-count-btn bet-info suspended" id="anthony">
                            <button class="back">
                                <span class="odd{{jdata.data.t2.[2].sid}}">4.15</span>
                            </button> 
                            <button class="lay">
                                <span class="odd{{jdata.data.t2.[2].sid}}">4.45</span>
                            </button>
                        </div>
					{{else}}
							<div class="aaa-button clearfix patti-count-btn bet-info" id="anthony">
                            <button class="back">
                                <span class="oddodd{{jdata.data.t2.[2].sid}}">4.15</span>
                            </button> 
                            <button class="lay">
                                <span class="oddodd{{jdata.data.t2.[2].sid}}">4.45</span>
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
                        <li><span><b>Min:</b> </span>{{jdata.data.t2.[0].min}}</li>
                        <li><span><b>Max:</b> </span>{{jdata.data.t2.[0].max}}</li>
                    </ul>
                </div>
            </div>
			</div>


<!--
			<div class="form-row">
				<div class="col-sm-6">
					<div class="paati-seven-cont-part-3 mb-2 p-3">
						<div class="text-center mt-0">

							<div class="mb-2 back1"><b>{{jdata.data.t2.[3].rate}}</b></div> 	
					      {{#if_eq jdata.data.t2.[3].gstatus "0"}} 		
							 
							<button type="button" id="odd" class="patti-count-btn bet-info d-block suspended">EVEN</button>
						    {{else}}
							<button type="button" id="odd" class="patti-count-btn bet-info d-block ">
								EVEN
							</button>
						 {{/if_eq}}	
							
							<div class="positive" id="sid4">0</div>
						</div>
						<div class="text-center mt-2">
							<div class="mb-2 back1"><b>{{jdata.data.t2.[4].rate}}</b></div> 	
						  {{#if_eq jdata.data.t2.[4].gstatus "0"}} 	
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
							<div class="mb-2 back1"><b>{{jdata.data.t2.[5].rate}}</b></div> 
                      {{#if_eq jdata.data.t2.[5].gstatus "0"}} 	
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
							<div class="mb-2 back1"><b>{{jdata.data.t2.[6].rate}}</b></div> 	
						{{#if_eq jdata.data.t2.[6].gstatus "0"}} 	
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
		-->		
</script>


<script id="resulthandler" type="text/x-handlebars-template">
	
			{{#if_eq result.0.result "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDetailLiveCasino({{result.0.mid}})" >A</span> 
			{{/if_eq}}
 			{{#if_eq result.0.result "2"}}  
				<span class="ball-runs playerb last-result"  onclick="resultDetailLiveCasino({{result.0.mid}})">B</span>
			{{/if_eq}}

			{{#if_eq result.0.result "3"}}  
				<span class="ball-runs playerb last-result"  onclick="resultDetailLiveCasino({{result.0.mid}})">C</span>
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


     var starCountRefResult = secondaryApp.database().ref(liveaaadata+"/result");
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

var marketId =null;
		function loadHtml(){
			$.ajax({
						type:'GET',
						url:'<s:url value="/api/AAAData/"/>',
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


								    
								     var starCountRef = secondaryApp.database().ref(liveaaadata+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										    $.each(data.t2, function( key, value ) {
												
												$("#odd"+value.sid).html(value.b1)
											    $("#even"+value.sid).html(value.l1)
											    $("#red"+value.sid).html(value.b1)
											    $("#black"+value.sid).html(value.l1)

											   
											    if(value.gstatus == "1" ){
											    	
										    	     $(".bet-info").removeClass("suspended")		
												      
											    	
											    	  
											    }else{
											    	  $(".bet-info").addClass("suspended")
													   
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
