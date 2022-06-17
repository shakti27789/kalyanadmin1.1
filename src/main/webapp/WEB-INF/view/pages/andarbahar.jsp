 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>



<!-- The core Firebase JS SDK is always required and must be listed first -->


<script id="vluckyoddshandler" type="text/x-handlebars-template">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="">
<div class="game-heading rounded-right-2">
					<span class="card-header-title">20-20 POKER  Rules  &nbsp; 
						<small class="teenpatti-rules" data-toggle="modal" data-target="#modalrulesteenpatti"><u>Rules</u></small>
					</span>
					<span class="round-id">
                        Round ID:
                        <span id="roundid_span"></span>
                    </span>
				</div>

<div class="score-panel">
					<div class="lucky7-screen">
						<iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="https://shroute.casinovid.in/diamondvideo/sh.php?id=3053"></iframe>
					

	<!--<iframe _ngcontent-hnv-c80="" width="100%" height="420" style="border: 0px;" src="https://cricexch.live/video/ab"></iframe>-->
					
					</div>
				</div>

<div id="oddsdata" class="col-md-12">
</div>


</script>

<script id="oddsHandler" type="text/x-handlebars-template"> 
<input type="hidden" value="-1" id="aall">
<input type="hidden" value="-1" id="ball">
<input type="hidden" value="{{data.t1.0.C1}}" id="cardc1text">


<input type="hidden" id="roundid" value="{{data.t1.0.mid}}">




		<!--Newly Added-->
				<div class="ander-baher-table">
					<table class="table table-bordered mb-1">
						<tr class="andarbg">
							<td class="box-1">
								<strong>Ander</strong>
							</td>
							<td class="">
								<div class="ander-bahar-wrap">
									<div class="ander-bahar-carousel">
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac1" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid1">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac2" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;"id="sid2">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac3" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid3">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"id="ac4" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid4">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac5" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid5">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac6" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid6">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac7" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid7">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac8" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid8">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac9" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid9">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac10" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid10">0</span>
										</span>
										<span class="m-r-5 game-section"> 
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac11" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid11">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac12" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid12">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="ac13" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="sid13">0</span>
										</span>
									</div>
								</div>
							</td>
						</tr>
						<tr class="baharbg">
							<td class="box-1" valign="center">
								<strong>Baher</strong>
							</td>
							<td>
								<div class="ander-bahar-wrap">
									<div class="ander-bahar-carousel">
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70" id="bc21" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl21">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc22" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl22">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc23" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl23">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc24" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl24">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc25" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl25">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc26" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl26">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc27" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;"id="pnl27">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc28" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl28">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc29" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl29">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc30" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;"id="pnl30">0</span>
										</span>
										<span class="m-r-5 game-section"> 
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc31" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl31">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc32" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;" id="pnl32">0</span>
										</span>
										<span class="m-r-5 game-section">
											<img src="<s:url value='/images/backcover.png'/>" width="70"  id="bc33" class="andar-bahar-image">  
											<span class="andarbaharnumber odds" style="color: black;"id="pnl33">0</span>
										</span>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<marquee scrollamount="3" class="casino-remark">
						Payout : Bahar 1st Card 25% and All Other Andar-Bahar Cards 100%.</marquee>
				</div>
						<!--Newly Added-->
</script>

  <script id="resulthandler" type="text/x-handlebars-template">
			<span class="ball-runs playera last-result" onclick="resultDetailLiveCasino({{result.0.mid}})" >R</span> 
 			
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
 <jsp:include page="sbamaxbet.jsp" />
	
	<div class="teenpatti-wrap mt-3">
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


var starCountRefResult = secondaryApp.database().ref(liveandarbahar+"/result");
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
						url:'<s:url value="/api/DTAb20Data/"/>',
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

								 
							     var obj =JSON.parse(jsondata.data); 
							     
									
                                     var marketId =null;
								     var starCountRef = secondaryApp.database().ref(liveandarbahar+"/data/data");
										starCountRef.on('value', (snapshot) =>{
										    const data = snapshot.val();
										  
										  	marketId =data.t1[0].mid;
											
											$("#roundid").val(data.t1[0].mid)
											$("#roundid_span").html(data.t1[0].mid)
								             
										    

											var aalltemp = new Array();
											aalltemp = data.t1[0].aall.split(",");


											var balltemp = new Array();
											balltemp = data.t1[0].ball.split(",");



										
                                             
											
										
												
													if(a==0){
														for (let i = 0; i < aalltemp.length; i++) {
				                                           
				                                            //$("#cars_image_andar").append("<div class='card-pic'><img src='<s:url value='/images/cards/"+aalltemp[i]+".png'/>' width='40'class=' card-openingimg-fluid mb-10'></div>")
															carousel();
															
															$('#cars_image_andar').trigger('add.owl.carousel', ["<div class='card-pic'><img src='https://dzm0kbaskt4pv.cloudfront.net/v2/static/front/img/cards"+aalltemp[i]+".png' width='40' class=' card-openingimg-fluid mb-10'></div>"]).trigger('refresh.owl.carousel');
					
															$("#aall").val(aalltemp.length);
															
															
											                }

															for (let j = 0; j < balltemp.length; j++) {
																   carousel();
																	//$("#cars_image_bahar").append("<div class='card-pic'><img src='<s:url value='/images/cards/"+balltemp[j]+".png'/>' width='40'class=' card-openingimg-fluid mb-10'></div>")
																	
																	$('#cars_image_bahar').trigger('add.owl.carousel', ["<div class='card-pic'><img src='https://dzm0kbaskt4pv.cloudfront.net/v2/static/front/img/cards/"+balltemp[j]+".png' width='40' class=' card-openingimg-fluid mb-10'></div>"]).trigger('refresh.owl.carousel');
																	
																	$("#ball").val(balltemp.length)
															}
										                
														}else{
		                                                       console.log("all==>"+aalltemp.length +"data==>"+aalltemp[0])
																if(aalltemp.length!=parseInt($("#aall").val())){
																 
																  if(aalltemp[0] != ""){
																	  console.log("A1 is Not Blank.."+aalltemp[0])
																		  //$("#cars_image_andar").append("<div class='card-pic'><img src='<s:url value='/images/cards/"+aalltemp[aalltemp.length-1]+".png'/>' width='40' class='card-opening img-fluid mb-10'></div>")
																		  $('#cars_image_andar').trigger('add.owl.carousel', ["<div class='card-pic'><img src='https://dzm0kbaskt4pv.cloudfront.net/v2/static/front/img/cards/"+aalltemp[aalltemp.length-1]+".png' width='40' class='card-opening img-fluid mb-10'></div>"]).trigger('refresh.owl.carousel');
																		  
																		  
																		 $("#aall").val(aalltemp.length);																	 
																	 }
																	 
																	 
																	
															    }

																if(balltemp.length!=parseInt($("#ball").val())){
																	 
																	  if(balltemp[0] != ""){
																		  console.log("B1 is Not Blank.."+balltemp[0])
																			  //$("#cars_image_bahar").append("<div class='card-pic'><img src='<s:url value='/images/cards/"+balltemp[balltemp.length-1]+".png'/>' width='40'class='card-opening img-fluid mb-10'></div>");
																			  $('#cars_image_bahar').trigger('add.owl.carousel', ["<div class='card-pic'><img src='https://dzm0kbaskt4pv.cloudfront.net/v2/static/front/img/cards"+balltemp[balltemp.length-1]+".png' width='40' class='card-opening img-fluid mb-10'></div>"]).trigger('refresh.owl.carousel');
																			  
																			 $("#ball").val(balltemp.length);																	 
																		 }
																}
														}
													    a++;
													   
												

											    
											
											
										
											
									
							               // console.log(aalltemp[aalltemp.length-1])
											var temp = new Array();
											temp = data.t1[0].ar.split(",");
											
											 
											 var brtemp = new Array();
											 brtemp = data.t1[0].br.split(",");

											 
											 if(parseInt(temp[0]) == 1){
												 $('#ac1').attr('src', '../../images/ab/1.jpg');
											 }else{
												 $('#ac1').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[1]) == 2){
												 $('#ac2').attr('src', '../../images/ab/2.jpg');
											 }else{
												 $('#ac2').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[2]) == 3){
												 $('#ac3').attr('src', '../../images/ab/3.jpg');
											 }else{
												 $('#ac3').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[3]) == 4){
												 $('#ac4').attr('src', '../../images/ab/4.jpg');
											 }else{
												 $('#ac4').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[4]) == 5){
												 $('#ac5').attr('src', '../../images/ab/5.jpg');
											 }else{
												 $('#ac5').attr('src', '../../images/backcover.png');
											}

											 if(parseInt(temp[5]) == 6){
												 $('#ac6').attr('src', '../../images/ab/6.jpg');
											 }else{
												 $('#ac6').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[6]) == 7){
												 $('#ac7').attr('src', '../../images/ab/7.jpg');
											 }else{
												 $('#ac7').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[7]) == 8){
												 $('#ac8').attr('src', '../../images/ab/8.jpg');
											 }else{
												 $('#ac8').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[8]) == 9){
												 $('#ac9').attr('src', '../../images/ab/9.jpg');
											 }else{
												 $('#ac9').attr('src', '../../images/backcover.png');
											}
											
											 if(parseInt(temp[9]) == 10){
												 $('#ac10').attr('src', '../../images/ab/10.jpg');
											 }else{
												 $('#ac10').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[10]) == 11){
												 $('#ac11').attr('src', '../../images/ab/11.jpg');
											 }else{
												 $('#ac11').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[11]) == 12){
												 $('#ac12').attr('src', '../../images/ab/12.jpg');
											 }else{
												 $('#ac12').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(temp[12]) == 13){
												 $('#ac13').attr('src', '../../images/ab/13.jpg');
											 }else{
												 $('#ac13').attr('src', '../../images/backcover.png');
											}



											 

											 if(parseInt(brtemp[0]) == 21){
												 $('#bc21').attr('src', '../../images/ab/21.jpg');
											 }else{
												 $('#bc21').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[1]) == 22){
												 $('#bc22').attr('src', '../../images/ab/22.jpg');
											 }else{
												 $('#bc22').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[2]) == 23){
												 $('#bc23').attr('src', '../../images/ab/23.jpg');
											 }else{
												 $('#bc23').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[3]) == 24){
												 $('#bc24').attr('src', '../../images/ab/24.jpg');
											 }else{
												 $('#bc24').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[4]) == 25){
												 $('#bc25').attr('src', '../../images/ab/25.jpg');
											 }else{
												 $('#bc25').attr('src', '../../images/backcover.png');
											}

											 if(parseInt(brtemp[5]) == 26){
												 $('#bc26').attr('src', '../../images/ab/26.jpg');
											 }else{
												 $('#bc26').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[6]) == 27){
												 $('#bc27').attr('src', '../../images/ab/27.jpg');
											 }else{
												 $('#bc27').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[7]) == 28){
												 $('#bc28').attr('src', '../../images/ab/28.jpg');
											 }else{
												 $('#bc28').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[8]) == 29){
												 $('#bc29').attr('src', '../../images/ab/29.jpg');
											 }else{
												 $('#bc29').attr('src', '../../images/backcover.png');
											}
											
											 if(parseInt(brtemp[9]) == 30){
												 $('#bc30').attr('src', '../../images/ab/30.jpg');
											 }else{
												 $('#bc30').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[10]) == 31){
												 $('#bc31').attr('src', '../../images/ab/31.jpg');
											 }else{
												 $('#bc31').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[11]) == 32){
												 $('#bc32').attr('src', '../../images/ab/32.jpg');
											 }else{
												 $('#bc32').attr('src', '../../images/backcover.png');
											}
											 if(parseInt(brtemp[12]) == 33){
												 $('#bc33').attr('src', '../../images/ab/33.jpg');
											 }else{
												 $('#bc33').attr('src', '../../images/backcover.png');
											}
											
	                                          

											if(parseInt(data.t1[0].autotime) <3){
												$(".p1p").addClass("suspended")
												$(".p2p").addClass("suspended")
												
												}else{
													$(".p1p").removeClass("suspended")
													$(".p2p").removeClass("suspended")
													$("#p1rate").html(data.t2[0].rate)
													$("#p2rate").html(data.t2[2].rate)

													$("#cars_image_andar .owl-stage").html('');
													$("#cars_image_bahar .owl-stage").html('');
													
													$("#cars_image_andar").trigger('destroy.owl.carousel');							 
													$("#cars_image_bahar").trigger('destroy.owl.carousel');
													 
													$("#aall").val(-1)
													$("#ball").val(-1)
													
													}
											
										 });
									
										mobileCarousel();
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


	var carousel = function() {	
	$('#cars_image_andar').owlCarousel({
		loop:false,
		margin: 10,
		nav:true,
		autoplay: true,
		items: 16,
		responsive:{
			0:{
				items: 6,
			},
			420:{
				items: 8
			},
			500:{
				items: 10
			},
			730:{
				items: 12
			},
			1023:{
				items: 14
			},
			1600:{
				items: 20
			}
		}

	})
	
	$('#cars_image_bahar').owlCarousel({
		margin: 10,
		nav:false,
		nav:true,
		autoplay: true,
		items: 16,
		responsive:{
			0:{
				items: 6,
			},
			420:{
				items: 8
			},
			500:{
				items: 10
			},
			730:{
				items: 12
			},
			1023:{
				items: 14
			},
			1600:{
				items: 20
			}
		}
		
	})
}
var mobileCarousel = function(){
	if ($(window).width() < 768) {
		$('.ander-bahar-carousel').addClass('owl-carousel');
		$('.ander-bahar-carousel').owlCarousel({
			margin: 10,
			nav:false,
			nav:true,
			autoplay: true,
			responsive:{
				0:{
					items: 3,
				},
				420:{
					items: 4
				},
				767:{
					items: 5,
				}
			}
			
		})
	}else {
		$(".ander-bahar-carousel").trigger('destroy.owl.carousel');
		$('.ander-bahar-carousel').removeClass('owl-carousel');
	}
}
$(document).ready(function(){
	setInterval(function(){ 
		carousel();
	}, 1000);	
	mobileCarousel();	
})
$( window ).resize(function() {
	mobileCarousel();
})
</script>
<jsp:include page="/WEB-INF/view/jsp_js/betlocakjs.jsp" />
<jsp:include page="resultdetail.jsp" />
<jsp:include page="handlebars.jsp" />
