   <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
  
 
 <script id="netExpoHandler" type="text/x-handlebars-template">
<div class="match-record-box">
 {{#each data}}
				  
                    <div class="m-name-box">
                        <span><i class="fa fa-futbol-o" aria-hidden="true"></i></span>
                        <span class="d-inline">{{sportName}}</span>
                        <span><i class="fa fa-check-circle" aria-hidden="true"></i></span>
                    </div>
         
					 <div class="f-name ">
                {{#each match}}
                   			 <p class=" pl-3">{{matchName}} <span style="margin-left: 20px;">{{openDate}}</span></span>
                   			 <span><i class="fa fa-check-circle" aria-hidden="true"></i></p> 
							 <div class="match-box-record">
                        <div class="m-box-head bg-white">
                    {{#each market}} 
							
                           
						<div class="m-scnd-head">
                            <div class="l-box-head"> <div class="head-box ">{{marketName}} 
                            <span><i class="fa fa-check-circle" aria-hidden="true"></i></span>
                            </div></div>
                            <div class="s-box-head">
                           
                         {{#if_eq usertype 4}}  
							{{#if_eq marketOddStatus true}}  
							 <div class="head-box"> <button onclick ="stopStartMarketOdds('false','{{marketId}}')">Stop</button></div>
						  {{else}}
							 <div class="head-box"><button onclick ="stopStartMarketOdds('true','{{marketId}}')">Start</button></div>
						{{/if_eq}}
					{{/if_eq}}	
                            </div>
                            <div class="clear-box"></div>
                        </div>

						<a href="/innerExpo/{{sportId}}/{{matchId}}/{{marketId}}"> <div class="uniUral-box bg-white"> 
						 {{#each team}} 
                           <div class="bootom-m-box" style="margin-left: 25px;">{{selectionName}} {{#if_neq ${user.usertype} 6}} {{#if_small pnl 0}}  <span style="color: red;margin-left: 15px;">{{pnl}}</span>{{else}} <span style="color: green;margin-left: 15px;">{{pnl}}</span>{{/if_small}} {{/if_neq}}</div>
						 {{/each}}                            
                        </div></a></br>


					{{/each}}	 
                            
                        </div>
 				  
                    </div>	
				{{/each}}
			
  {{/each}}	
</div>
</script>


 <script id="sportTabHandler" type="text/x-handlebars-template">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
					 {{#each data}}	
						

						{{#if_eq sportId 4}}  
						  <li class="nav-item">
							<a class="nav-link active" id="{{sportId}}-tab" data-toggle="tab" href="javascript: void(0)" role="tab" aria-controls="cricket" onClick="NetExpo({{sportId}},false);" aria-selected="true">{{name}}</a>
						</li>
						{{else}}
							<li class="nav-item">
							<a class="nav-link " id="{{sportId}}-tab" data-toggle="tab" href="javascript: void(0)" role="tab" aria-controls="cricket" onClick="NetExpo({{sportId}},false);" aria-selected="true">{{name}}</a>
						</li>
						{{/if_eq}}
						
					{{/each}}	 
					</ul>
					
					<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade show active" id="data_sport" role="tabpanel" aria-labelledby="{{sportId}}-tab">
							Cricket Content goes here
						  </div>	
					</div>
</script>


 <div class="cliennt-container">
        <div class="account-container pt-4">
            <div class="account-record dash-board">
				
				<div class="dashboard-tabs" id="tab_sport">
					
				
				</div>				
				<div class="dashboard-container" id="netexp_container">

				</div>
            </div>
        </div>
    </div> 
    
   <!--  
    <div class="cliennt-container">
        <div class="account-container pt-4">
            <div class="account-record dash-board">
				<div class="dashboard-top">
                    <h4>Dashboard</h4>
                   <div class="dash-btn"> <a href="#">Show Client Info </a></div>
                   <div class="dash-btn"> <a href="#">Veronica Running Market </a></div>
                   <div class="dash-btn"> <a href="#">Binary Running Market </a></div>
                   <div class="top-l-btn">
                    <a href="javascript:void(0)" class="p-btn" onclick="NetExpo(true)"><i class="fa fa-refresh" aria-hidden="true"></i>In Play </a>
                    <div class="dash-btn"> <a href="javascript:void(0)" onclick="NetExpo(false)">All Match </a></div>
                   </div>
				</div>
				<div class="dashboard-tabs">
					<ul class="nav nav-tabs" id="myTab" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" id="cricket-tab" data-toggle="tab" href="#cricket" role="tab" aria-controls="cricket" aria-selected="true">Circket</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="teenPatti-tab" data-toggle="tab" href="#teenPatti" role="tab" aria-controls="teenPatti" aria-selected="false">Teen Patti</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" id="lucky7-tab" data-toggle="tab" href="#lucky7" role="tab" aria-controls="Lucky7" aria-selected="false">Lucky7</a>
						</li>
					</ul>
					<div class="tab-content" id="myTabContent">
						<div class="tab-pane fade show active" id="cricket" role="tabpanel" aria-labelledby="cricket-tab">
							Cricket Content goes here
						</div>
						<div class="tab-pane fade" id="teenPatti" role="tabpanel" aria-labelledby="teenPatti-tab">
							Teen Patti Content goes here
						</div>
						<div class="tab-pane fade" id="lucky7" role="tabpanel" aria-labelledby="lucky7-tab">
							Lucky Content goes here
						</div>
					</div>
				</div>				
				<div class="dashboard-container" id="netexp_container">

				</div>
            </div>
        </div>
    </div> -->
    
    
<!--***************** end ********************-->

<script>

$(document).ready(function(){
	
	betfairSportList();
	NetExpo(4,false)
 });
  function NetExpo(sportId,inPlay){
	  $("#data_sport").append('<div class="loader"></div>')	
    		$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/netExposure/"/>'+inPlay+"/"+sportId,
 			dataType: "json",
 			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{     
    					
    					$('#data_sport').html('');
    					var source   = $("#netExpoHandler").html();
  			    	 	var template= Handlebars.compile(source);
 				    	$("#data_sport").append( template({data:jsondata})); 
 				    	$(".loader").fadeOut("slow");
    				}
  			}
  		});  
  }
  
  function stopStartMarketOdds(status,marketId){
		
		$.ajax({
			type:'POST',
			url:'<s:url value="/api/stopStartMarketOdds/"/>'+status+"/"+marketId,
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{     
					
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				}
		}
	});  
}

  function betfairSportList(){
		
		$.ajax({
			type:'GET',
			url:'<s:url value="/api/betfairSportList/"/>',
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{     
					console.log("betfairSportList"+jsondata)
					$('#tab_sport').html('');
					var source   = $("#sportTabHandler").html();
			    	 	var template= Handlebars.compile(source);
				    	$("#tab_sport").append( template({data:jsondata})); 
				    	 NetExpo('4',false)
				}
		}
	});  
}
  
  
</script>

<jsp:include page="handlebars.jsp" />  