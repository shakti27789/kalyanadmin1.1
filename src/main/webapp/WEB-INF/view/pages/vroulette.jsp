<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- The core Firebase JS SDK is always required and must be listed first -->
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script
	src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>

<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">Lucky7 Rules &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span"></span>
                    </span>
				</div>

<div class="score-panel">
					<div class="lucky7-screen">
						<iframe _ngcontent-hnv-c80="" width="100%" height="510" style="border: 0px;" src="{{data.url}}"></iframe>
						
					</div>
				</div>
<div id="oddsdata">
</div>


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

<script id="oddsHandler" type="text/x-handlebars-template">
<!--FOR DESKTOP VIEW-->
<div class="roulette">
	<table class="desktop-table">
		<tr class="nums">
			<td class="num green zero" rowspan="3"><span>0</span><p class="sid13"></p></td>
			<td class="num red"><span>3</span><p class="sid16"></p></td>
			<td class="num black"><span>6</span><p class="sid19"></p></td>
			<td class="num red"><span>9</span><p class="sid22"></p></td>
			<td class="num red"><span>12</span><p class="sid25"></p></td>
			<td class="num black"><span>15</span><p class="sid28"></p></td>
			<td class="num red"><span>18</span><p class="sid31"></td>
			<td class="num red"><span>21</span><p class="sid34"></td>
			<td class="num black"><span>24</span><p class="sid37"></td>
			<td class="num red"><span>27</span><p class="sid40"></td>
			<td class="num red"><span>30</span><p class="sid43"></td>
			<td class="num black"><span>33</span><p class="sid46"></td>
			<td class="num red"><span>36</span><p class="sid49"></td>
			<td class="sector" data-sector="1"><span class="">3rd Column</span><p class="sid12"></p></td>
		</tr>
		<tr class="nums">
			<td class="hidden"></td>
			<td class="num black"><span>2</span><p class="sid15"></p></td>
			<td class="num red"><span>5</span><p class="sid18"></p></td>
			<td class="num black"><span>8</span><p class="sid21"></p></td>
			<td class="num black"><span>11</span><p class="sid24"></p></td>
			<td class="num red"><span>14</span><p class="sid27"></p></td>
			<td class="num black"><span>17</span><p class="sid30"></td>
			<td class="num black"><span>20</span><p class="sid33"></td>
			<td class="num red"><span>23</span><p class="sid36"></td>
			<td class="num black"><span>26</span><p class="sid39"></td>
			<td class="num black"><span>29</span><p class="sid42"></td>
			<td class="num red"><span>32</span><p class="sid45"></td>
			<td class="num black"><span>35</span><p class="sid48"></td>
			<td class="sector" data-sector="2"><span class="">2nd Column</span><p class="sid11"></p></td>
		</tr>
		<tr class="nums">
			<td class="hidden"></td>
			<td class="num red"><span>1</span><p class="sid14"></p></td>
			<td class="num black"><span>4</span><p class="sid17"></p></td>
			<td class="num red"><span>7</span><p class="sid20"></p></td>
			<td class="num black"><span>10</span><p class="sid23"></p></td>
			<td class="num black"><span>13</span><p class="sid26"></p></td>
			<td class="num red"><span>16</span><p class="sid29"></p></td>
			<td class="num red"><span>19</span><p class="sid32"></p></td>
			<td class="num black"><span>22</span><p class="sid35"></p></td>
			<td class="num red"><span>25</span><p class="sid38"></p></td>
			<td class="num black"><span>28</span><p class="sid41"></p></td>
			<td class="num black"><span>31</span><p class="sid44"></p></td>
			<td class="num red"><span>34</span><p class="sid47"></p></td>
			<td class="sector" data-sector="3"><span class="">1st Column</span><p class="sid10"></p></td>
		</tr>
		<tr>
			<td class="empty"></td>
			<td colspan="4" class="sector" data-sector="4">1st 12 <p class="sid7"></td>
			<td colspan="4" class="sector" data-sector="5">2nd 12 <p class="sid8"></td>
			<td colspan="4" class="sector" data-sector="6">3rd 12 <p class="sid9"></td>
			<td class="empty"></td>
		</tr><tr>
			<td class="empty"></td>
			<td colspan="2" class="sector" data-sector="7">1 to 18<p class="sid5"></p></td>
			<td colspan="2" class="sector" data-sector="8">EVEN<p class="sid1"></p></td>
			<td colspan="2" class="sector red" data-sector="9">RED<p class="sid3"></p></td>
			<td colspan="2" class="sector black" data-sector="10">BLACK<p class="sid4"></p></td>
			<td colspan="2" class="sector" data-sector="11">ODD<p class="sid2"></p></td>
			<td colspan="2" class="sector" data-sector="12">19 to 36  <p class="sid6"></p></td>
			<td class="empty"></td>
		</tr>
	</table>

<!---------------MOBILE VIEW--------------------->

	<table class="mobile-table">
		<tr>
			<td class="num green zero" colspan="3"><span>0</span></td>
			<td class="hidden">&nbsp;</td>
			<td class="hidden">&nbsp;</td>                        
		</tr>
		<tr>
			<td class="num red">1 <p class="sid14"></td>
			<td class="num black">2 <p class="sid15"></td>
			<td class="num red">3 <p class="sid16"></td>
			<td rowspan="4" class="sector">1st 12 <p class="sid7"></td>
			<td rowspan="2" class="sector">1 to 18<p class="sid5"></p></td>
		</tr>
		<tr>
			<td class="num black">4 <p class="sid17"></td>
			<td class="num red">5 <p class="sid18"></td>
			<td class="num black">6 <p class="sid19"></td>
		</tr>
		<tr>
			<td class="num red">7 <p class="sid20"></td>
			<td class="num black">8 <p class="sid21"></td>
			<td class="num red">9 <p class="sid22"></td>
			<td rowspan="2" class="sector">EVEN <p class="sid1"></td>
		</tr>
		<tr>
			<td class="num black">10 <p class="sid23"></td>
			<td class="num black">11 <p class="sid24"></td>
			<td class="num red">12 <p class="sid25"></td>
		</tr>
		<tr>
			<td class="num black">13 <p class="sid26"></td>
			<td class="num red">14 <p class="sid27"></td>
			<td rowspan="4">2nd 12 <p class="sid28"></td>
			<td rowspan="2" class="sector red">RED <p class="sid3"></td>
		</tr>
		<tr>
			<td class="num red">16 <p class="sid29"></td>
			<td class="num black">17 <p class="sid30"></td>
			<td class="num red">18 <p class="sid31"></td>
		</tr>
		<tr>
			<td class="num red">19 <p class="sid32"></td>
			<td class="num black">20 <p class="sid33"></td>
			<td class="num red">21 <p class="sid34"></td>
			<td rowspan="2" class="sector black">BLACK <p class="sid4"></td>
		</tr>
		<tr>
			<td class="num black">22 <p class="sid35"></td>
			<td class="num red">23 <p class="sid36"></td>
			<td class="num black">24 <p class="sid37"></td>
		</tr>
		<tr>
			<td class="num red">25 <p class="sid38"></td>
			<td class="num black">26 <p class="sid39"></td>
			<td class="num red">27 <p class="sid40"></td>
			<td rowspan="4" class="sector">3rd 12 <p class="sid8"></td>
			<td rowspan="2" class="sector">ODD <p class="sid2"></td>
		</tr>
		<tr>
			<td class="num black">28 <p class="sid41"></td>
			<td class="num black">29 <p class="sid42"></td>
			<td class="num red">30 <p class="sid43"></td>
		</tr>
		<tr>
			<td class="num black">31 <p class="sid44"></td>
			<td class="num red">32 <p class="sid45"></td>
			<td class="num black">33 <p class="sid46"></td>
			<td rowspan="2" class="sector">19 To 36<p class="sid6"></td>
		</tr>
		<tr>
			<td class="num red">34 <p class="sid47"></td>
			<td class="num black">35 <p class="sid48"></td>
			<td class="num red">36 <p class="sid49"></td>
		</tr>
		<tr>
			<td class="sector">1st Column <p class="sid9"></td>
			<td class="sector">2nd Column <p class="sid10"></td>
			<td class="sector">3rd Column <p class="sid11"></td>
			<td class="hidden">&nbsp;</td> 
			<td class="hidden">&nbsp;</td> 
		</tr>
	</table>
</div>
<!---->				 		
				
</script>




<div class="container-fluid">

	<div class="teenpatti-wrap mt-3">
	 <jsp:include page="sbamaxbet.jsp" />
	
		<div class="row">
			<div class="col-md-8">
				<div class="coupon-card" id="vlucky7div"></div>		
				
				
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
//	getAddminBet();
	
	

	
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


     /*	var starCountRefResult = secondaryApp.database().ref(vlucky7data+"/result");
		starCountRefResult.on('value', (snapshot) =>{
		    const result = snapshot.val();
		    console.log(result)
			
			$("#last-result").html('');
			var source   = $("#vlucky7resulthandler").html();
	    	  var template = Handlebars.compile(source);
	    	  $("#last-result").append( template({result:result}) );
	    	  
		  });
*/


		
	    
	 

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
						url:'<s:url value="/api/VRouletteData/"/>',
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

								    
								     var starCountRef = secondaryApp.database().ref(vroulettedata+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										     console.log("data==>"+data.t1[0].mid)

										     marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
											   
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
			url:expo_base_url+'/api/getMyProfitVRoulette/'+$("#roundid").val(),
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					  var data = jsondata;
					  $(".sid1").html(jsondata.pnl1);
					  $(".sid2").html(jsondata.pnl2);
		  	          $(".sid3").html(jsondata.pnl3);
		  	          $(".sid4").html(jsondata.pnl4);
		  	          $(".sid5").html(jsondata.pnl5);
		  	          $(".sid6").html(jsondata.pnl6);  $(".sid7").html(jsondata.pnl7);  $(".sid8").html(jsondata.pnl8);  $(".sid9").html(jsondata.pnl9);  $(".sid10").html(jsondata.pnl10);
		  	        $(".sid11").html(jsondata.pnl11);  $(".sid12").html(jsondata.pnl12);  $(".sid13").html(jsondata.pnl13);  $(".sid14").html(jsondata.pnl14);
		  	      $(".sid15").html(jsondata.pnl15);  $(".sid16").html(jsondata.pnl16);  $(".sid17").html(jsondata.pnl17);  $(".sid18").html(jsondata.pnl18);
		  	    $(".sid19").html(jsondata.pnl9);  $(".sid20").html(jsondata.pnl20);  $(".sid21").html(jsondata.pnl21);  $(".sid22").html(jsondata.pnl22);
		  	  $(".sid23").html(jsondata.pnl23);  $(".sid24").html(jsondata.pnl24);  $(".sid25").html(jsondata.pnl25);  $(".sid26").html(jsondata.pnl26);
		  	  $(".sid27").html(jsondata.pnl27);  $(".sid28").html(jsondata.pnl28);  $(".sid29").html(jsondata.pnl29);  $(".sid30").html(jsondata.pnl30);
		  	  $(".sid31").html(jsondata.pnl31);  $(".sid32").html(jsondata.pnl32);  $(".sid33").html(jsondata.pnl33);  $(".sid34").html(jsondata.pnl34);
		  	  $(".sid35").html(jsondata.pnl35);  $(".sid36").html(jsondata.pnl36);  $(".sid37").html(jsondata.pnl37);  $(".sid38").html(jsondata.pnl38);
		  	  $(".sid39").html(jsondata.pnl39);  $(".sid40").html(jsondata.pnl40);  $(".sid41").html(jsondata.pnl41);  $(".sid42").html(jsondata.pnl42);
		  	  $(".sid43").html(jsondata.pnl43);  $(".sid44").html(jsondata.pnl44);  $(".sid45").html(jsondata.pnl45);  $(".sid46").html(jsondata.pnl46);
		  	  $(".sid47").html(jsondata.pnl47);  $(".sid48").html(jsondata.pnl48);  $(".sid49").html(jsondata.pnl49);
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
