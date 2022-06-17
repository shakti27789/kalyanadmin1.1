<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script
	src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="vdtoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">
<input type="hidden" value="{{data.t1.0.C2}}" id="cardc2text">

<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">
<div class="game-heading rounded-right-2">
				<span class="card-header-title">20-20 DRAGON TIGER Rules &nbsp; 
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
						<div class="title text-center">20-20 DRAGON TIGER</div>
						<div class="card-box">
							<div class="row">
								<div class="col-6">
									<div class="card-wrap" id="cardc1">
										{{#if_eq data.t1.0.C1 ""}}  											
											<img src="../../images/backcover.png" width="70" />
										{{else}}
											<img  src="<s:url value="/images/cards/{{data.t1.0.C1}}.png"/>" width="70" />
										{{/if_eq}}
									</div>
									<div class="card-label">D</div>
								</div>
								<div class="col-6">
									<div class="card-wrap" id="cardc2">
										{{#if_eq data.t1.0.C1 ""}}  											
											<img src="../../images/backcover.png" width="70" />
										{{else}}
											<img  src="<s:url value="/images/cards/{{data.t1.0.C1}}.png"/>" width="70" />
										{{/if_eq}}
									</div>
									<div class="card-label">T</div>
								</div>
							</div>							
							<div id="holder">
								<div class="digits active">{{data.t1.0.autotime}}</div>
							</div>
						</div>
					</div>						
				</div>
			</div>
            <div class="paati-seven-cont py-2 mt-2">
				<div class="pl-3 pr-3">
					<div class="row mt-2">
						<div class="col-md-9">
							<div class="row">
								<div class="col-sm-5 sm-mb-2 text-center">
									<div class="mb-2"><b>{{data.t2.0.rate}}</b></div>								
									{{#if_eq data.t1.0.autotime "0"}}  	
									  <button type="button" id="dragon" class="patti-count-btn bet-info suspended">Dragon</button>
									{{else}}
									  <button type="button" id="dragon" class="patti-count-btn bet-info">Dragon</button>
									{{/if_eq}}	
										<div id="sid1" class="positive">0</div>
								</div>
								<div class="col-sm-2 sm-mb-2 text-center">
									<div class="mb-2"><b>{{data.t2.2.rate}}</b></div>								
									{{#if_eq data.t1.0.autotime "0"}}  	
									  <button type="button" id="tie" class="patti-count-btn bet-info suspended">TIE</button>
									{{else}}
									  <button type="button" id="tie" class="patti-count-btn bet-info">TIE</button>
									{{/if_eq}}	
										<div id="sid3" class="positive">0</div>
								</div>
								<div class="col-sm-5 text-center">
									<div class="mb-2"><b>{{data.t2.1.rate}}</b></div>									
										{{#if_eq data.t1.0.autotime "0"}}  	
										 <button type="button" id="tiger" class="patti-count-btn bet-info suspended">Tiger</button>
										{{else}}
										  <button type="button" id="tiger" class="patti-count-btn bet-info">Tiger</button>
										{{/if_eq}}
									<div id="sid2" class="positive">0</div>
								</div>
							</div>
						</div>
						<div class="col-sm-3 pair-block text-center">
							<div class="mb-2"><b>{{data.t2.3.rate}}</b></div>									
								{{#if_eq data.t1.0.autotime "0"}}  	
								 <button type="button" id="pair" class="patti-count-btn bet-info suspended">PAIR</button>
								{{else}}
								  <button type="button" id="pair" class="patti-count-btn bet-info">PAIR</button>
								{{/if_eq}}
							<div id="sid4" class="positive">0</div>
							</div>						
					</div>
				</div>
				<div class="row px-2">
					<div class="col-sm-12 text-right mt-2">
						<ul class="cont-lenght mb-0">
							<li><span><b>Min:</b> </span>{{data.t2.0.min}}</li>
							<li><span><b>Max:</b> </span>{{data.t2.0.max}}</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="form-row mt-2">
				<div class="col-sm-6">
					<div class="paati-seven-cont py-2">
						<h5 class="text-center">DRAGON</h5>
						<div class="row pl-3 pr-3">
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.4.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="deven" class="patti-count-btn bet-info">Even</button>
								{{else}}
								  <button type="button" id="deven" class="patti-count-btn bet-info">Even</button>
								{{/if_eq}}	
									<div id="sid5" class="positive">0</div>
							</div>
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.5.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="dodd" class="patti-count-btn bet-info">Odd</button>
								{{else}}
								  <button type="button" id="dodd" class="patti-count-btn bet-info">Odd</button>
								{{/if_eq}}	
									<div id="sid6" class="positive">0</div>
							</div>
						</div>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.5.min}}</li>
									<li><span><b>Max:</b> </span>{{data.t2.5.max}}</li>
								</ul>
							</div>
						</div>
						<div class="row pl-3 pr-3 mt-3">
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.6.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="dred" class="patti-count-btn bet-info suspended">
									<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
								  </button>
								{{else}}
								  <button type="button" id="dred" class="patti-count-btn bet-info">
									<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
								  </button>
								{{/if_eq}}	
									<div id="sid7" class="positive">0</div>
							</div>
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.7.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="dblack" class="patti-count-btn bet-info suspended">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
								  </button>
								{{else}}
								  <button type="button" id="dblack" class="patti-count-btn bet-info">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
								  </button>
								{{/if_eq}}	
									<div id="sid8" class="positive">0</div>
							</div>
						</div>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.7.min}}</li>
									<li><span><b>Max:</b> </span>{{data.t2.7.max}}</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div class="paati-seven-cont py-2">
						<h5 class="text-center">TIGER</h5>
						<div class="row pl-3 pr-3">
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.21.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="teven" class="patti-count-btn bet-info">Even</button>
								{{else}}
								  <button type="button" id="teven" class="patti-count-btn bet-info">Even</button>
								{{/if_eq}}	
									<div id="sid22" class="positive">0</div>
							</div>
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.22.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="todd" class="patti-count-btn bet-info">Odd</button>
								{{else}}
								  <button type="button" id="todd" class="patti-count-btn bet-info">Odd</button>
								{{/if_eq}}	
									<div id="sid23" class="positive">0</div>
							</div>
						</div>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.22.min}}</li>
									<li><span><b>Max:</b> </span>{{data.t2.22.max}}</li>
								</ul>
							</div>
						</div>
						<div class="row pl-3 pr-3 mt-3">
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.23.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="tred" class="patti-count-btn bet-info suspended">
									<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
								  </button>
								{{else}}
								  <button type="button" id="tred" class="patti-count-btn bet-info">
									<img src="../../images/cards/ccard-1.png" width="40" class="img-fluid" />
								  </button>
								{{/if_eq}}	
									<div id="sid24" class="positive">0</div>
							</div>
							<div class="col-sm-6 sm-mb-2 text-center">
								<div class="mb-2"><b>{{data.t2.24.rate}}</b></div>								
								{{#if_eq data.t1.0.autotime "0"}}  	
								  <button type="button" id="tblack" class="patti-count-btn bet-info suspended">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
								  </button>
								{{else}}
								  <button type="button" id="tblack" class="patti-count-btn bet-info">
									<img src="../../images/cards/ccard-2.png" width="40" class="img-fluid" />
								  </button>
								{{/if_eq}}	
									<div id="sid25" class="positive">0</div>
							</div>
						</div>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>{{data.t2.24.min}}</li>
									<li><span><b>Max:</b> </span>{{data.t2.24.max}}</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>




		<!--	<div class="form-row mt-2">
				<div class="col-sm-6">
					<div class="paati-seven-cont py-2">
						<h5 class="text-center">DRAGON 12.00</h5>						
						<ul class="card-list p-0">
							<li class="suspended">
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li class="suspended">
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li class="suspended">
								<img src="../../images/cards/casino/5.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/3.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/5.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/3.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
						</ul>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>100</li>
									<li><span><b>Max:</b> </span>300000</li>
								</ul>
							</div>
						</div>
					</div>
				</div>


				<div class="col-sm-6">
					<div class="paati-seven-cont py-2">
						<h5 class="text-center">TIGER 12.00</h5>						
						<ul class="card-list p-0">
							<li class="suspended">
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li class="suspended">
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li class="suspended">
								<img src="../../images/cards/casino/5.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/3.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/1.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/5.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/3.jpg" width="45" />
								<span>0</span>
							</li>
							<li>
								<img src="../../images/cards/casino/2.jpg" width="45" />
								<span>0</span>
							</li>
						</ul>
						<div class="row px-2">
							<div class="col-sm-12 text-right mt-2">
								<ul class="cont-lenght mb-0">
									<li><span><b>Min:</b> </span>100</li>
									<li><span><b>Max:</b> </span>300000</li>
								</ul>
							</div>
						</div>
					</div> -->
				</div>
			</div>
			
</script>

<script id="dt20oddsresulthandler" type="text/x-handlebars-template">
			{{#if_eq result.0.winner "1"}}  											
				<span class="ball-runs playera last-result" onclick="resultDeatil({{result.0.mid}})" >D</span> 
 			{{else}}
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
				<div class="coupon-card" id="vdtdiv"></div>		
				
				<!--Newly Added-->
				
				<!--Newly Added-->
				<div class="fancy-marker-title pb-5 mt-2">
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



var starCountRefResult = secondaryApp.database().ref(vdtdata+"/result");
starCountRefResult.on('value', (snapshot) =>{
    const result = snapshot.val();
    
	$("#last-result").html('');
	var source   = $("#dt20oddsresulthandler").html();
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
						url:'<s:url value="/api/VDT20Data/"/>',
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200){ 		
								
								 $("#vdtdiv").html('');
									 var source   = $("#vdtoddshandler").html();
								     var template = Handlebars.compile(source);
								     $("#vdtdiv").append( template({data:JSON.parse(jsondata)}) );
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(vdtdata+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										  	marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
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
										    
										    if(parseInt(data.t1[0].autotime) <3){
											 $("#dragon").addClass("suspended")
											  $("#tiger").addClass("suspended")	
											   $("#pair").addClass("suspended")
											   $("#tie").addClass("suspended")
											    $("#deven").addClass("suspended")
											    $("#dodd").addClass("suspended")
											    $("#teven").addClass("suspended")
											    $("#todd").addClass("suspended")
											     $("#tred").addClass("suspended")
											      $("#tblack").addClass("suspended")
											      $("#dred").addClass("suspended")
											       $("#dblack").addClass("suspended")
										    }else{
										    	$("#dragon").removeClass("suspended")
												  $("#tiger").removeClass("suspended")	
												   $("#pair").removeClass("suspended")
												   $("#tie").removeClass("suspended")
												    $("#deven").removeClass("suspended")
												    $("#dodd").removeClass("suspended")
												    $("#teven").removeClass("suspended")
												    $("#todd").removeClass("suspended")
												     $("#tred").removeClass("suspended")
												      $("#tblack").removeClass("suspended")
												       $("#dred").removeClass("suspended")
											           $("#dblack").removeClass("suspended")
												      
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
													console.log("marketId==>"+marketId)
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
													getAddminBet();

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
			//url:'<s:url value="/api/getMyProfitVDT20/"/>'+$("#roundid").val(),
			url:expo_base_url+'/api/getMyProfitVDT20/'+$("#roundid").val()+"/"+${user.usertype}+"/"+${user.id},
			
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  var data = jsondata;
						console.log("pnl==>"+jsondata.pnl1)
					  $("#sid1").html(jsondata.pnl1);
					  $("#sid2").html(jsondata.pnl2);
		  	          $("#sid3").html(jsondata.pnl3);
		  	          $("#sid4").html(jsondata.pnl4);
		  	          $("#sid5").html(jsondata.pnl5);
		  	          $("#sid6").html(jsondata.pnl6);
		  	          $("#sid7").html(jsondata.pnl7);
		  	          $("#sid8").html(jsondata.pnl8);

		  	          $("#sid22").html(jsondata.pnl22);
		  	          $("#sid23").html(jsondata.pnl23);
		  	          $("#sid24").html(jsondata.pnl24);
		  	          $("#sid25").html(jsondata.pnl25);
						
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
