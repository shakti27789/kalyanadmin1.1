<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<script id="closedbetshandler" type="text/x-handlebars-template">

  <table class="table table-striped table-bordered account-stment">
	<thead>
	  <tr>
		<th>Serial No</th><th>Date</th><th>Match Name</th>
			 
		 <th>UpLine</th> 
		 <th>Net P&amp;L</th>
		 <th>DownLine</th>
		<th>Commsion Mila</th>
		 <th>Commssion Gaya</th>
		 <th>Action</th>
	 </tr>
	</thead>
                 <tbody>	
					{{#each data.list}}
					<tr class="table_row">
					 <td>{{serialno}}</td> <td>{{date}}</td> <td>{{matchname}}</td>
				       
				        {{#if_small uplineamount 0}}
					     <td class="negative">{{uplineamount}}</td>
				          {{else}}{{#if uplineamount 0}}
					     <td>{{uplineamount}}</td>
				       {{else}}
					     <td class="positive">{{uplineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
				 	
						{{#if_small pnlamount 0}}
							<td class="negative">{{pnlamount}}</td>
							 {{else}}
								{{#if pnlamount 0}}
								 <td>{{pnlamount}}</td>
							{{else}}
						    <td class="positive">{{pnlamount}}</td>
						{{/if}}
						{{/if_small}}
                      {{#if_small downlineamount 0}}
					     <td class="negative">{{downlineamount}}</td>
				          {{else}}{{#if downlineamount 0}}
					     <td>{{downlineamount}}</td>
				       {{else}}
					     <td class="positive">{{downlineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
					<td class="positive">{{commssionMila}}</td><td class="negative">{{commssionDiya}}</td>
				   <td><a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showBets('{{serialno}}','{{matchid}}','down','-1')">Bets</a>
					<a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Detail</a>
					<a href="javascript:void(0);" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showAmountDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Amount Detail</a>
										
</td>
                  </tr>
				  <tr class="bets_table_tr" id="bets_table_tr{{serialno}}">
				  <td colspan=9> 
							<input type="hidden" id="slideupdown{{serialno}}" value="up">
							<div id="bet_list_div{{serialno}}" style="display:none;"></div>
                            

                            <input type="hidden" id="deatilslideupdownamount{{serialno}}" value="up">
							<div id="detail_list_div_amount{{serialno}}" style="display:none;"></div>


							<input type="hidden" id="deatilslideupdown{{serialno}}" value="up">
							<div id="detail_list_div{{serialno}}" style="display:none;">

						
                             <input type="hidden" id="commdetailslideupdown{{serialno}}" value="up">
							 <div id="commdetail_list_div{{serialno}}" style="display:block;"><div>
                          <div>
                             
          
							
                  
                {{/each}}
</td>			</tr>

                     

					<td class="total">Total</td><td class="total">Total</td><td class="total"></td>
  						{{#if_small data.totalupline 0}}
					     <td class="negative">{{data.totalupline}}</td>
				       {{else}}
					     <td class="positive">{{data.totalupline}}</td> 
				        {{/if_small}}

                        {{#if_small data.totalpnl 0}}
					     <td class="negative">{{data.totalpnl}}</td>
				       {{else}}
					     <td class="positive">{{data.totalpnl}}</td> 
				        {{/if_small}}					

						{{#if_small data.totaldownline 0}}
					     <td class="negative">{{data.totaldownline}}</td>
				       {{else}}
					     <td class="positive">{{data.totaldownline}}</td> 
				        {{/if_small}}	

						{{#if_small data.commssiondmila 0}}
					     <td class="negative">{{data.commssiondmila}}</td>
				       {{else}}
					     <td class="positive">{{data.commssiondmila}}</td> 
				        {{/if_small}}	

						{{#if_small data.commssiondiya 0}}
					     <td class="negative">{{data.commssiondiya}}</td>
				       {{else}}
					     <td class="positive">{{data.commssiondiya}}</td> 
				        {{/if_small}}		
					<td class="total"></td>

					</tr>
			</tbody>
</table>

</script>


<script id="betHistoryHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
 <th>Seriol No</th>			 
<th>Markets</th>	
        <th>Odds</th>
        <th>Nation</th>
        <th>Stack</th>
        <th>Pnl</th>
      
       
    </tr>
</thead>
<tbody>
  {{#each data}}

    {{#if_eq isback true}}
    <tr class="back">
  {{else}} 
 <tr class="lay">
 {{/if_eq}}	
<td>{{serialno}}</td>		
<td><p><span>{{marketname}}</span<span>{{selectionname}}</span></p><p><span>Betid :{{id}}</span><span>placed Time:{{matchedtime}}</span></p><span>userId :{{userid}}</span><i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{inc @index}}')" ></i><p id ="userdetail{{inc @index}}" class="userdetail" ></p></p>  {{#if_greaterThan sportid 500}}<p><span> roundId:{{marketid}}</span></p>  {{/if_greaterThan}}</td>	
        <td>{{odds}}</td>
        <td>{{selectionname}}</td>
        <td>{{stake}}</td>
        {{#if_small netpnl 0}}
					     <td class="negative">{{netpnl}}</td>
				          {{else}}{{#if netpnl 0}}
					     <td>{{netpnl}}</td>
				       {{else}}
					     <td class="positive">{{netpnl}}</td> 
				       {{/if}}
         {{/if_small}}
        
    </tr>
  {{/each}}
   
   
</tbody>

</table>
</script>

<script id="profitLossDetailHistoryHandler" type="text/x-handlebars-template">
<table class="table table-striped table-bordered account-stment">
	<thead>
	  <tr>
		<th>Serial No</th><th>Match Name</th>
			
				  {{#if_small uplineamount 0}}
					<th>UpLine</th> 
				  {{else}}{{#if uplineamount 0}}
				  <th>UpLine</th> 
				{{else}}
					<th>UpLine</th> 
		  		 {{/if}}{{/if_small}}
		   
		 <th>Net P&amp;L</th>
		 <th>DownLine</th>
		 <th>Comm +</th>
       <th>Comm -</th>
     <th>Action</th>
	 </tr>
	</thead>
                 <tbody>	
					{{#each data.list}}
					<tr class="table_row">
					 <td>{{serialno}}</td>  <td>{{matchname}}-->{{marketname}}{{#if_greaterThan sportid 500}}<p><span>roundId:{{marketid}}</span></p>  {{/if_greaterThan}}</td>
				      
				        {{#if_small uplineamount 0}}
					     <td class="negative">{{uplineamount}}</td>
				          {{else}}{{#if uplineamount 0}}
					     <td>{{uplineamount}}</td>
				       {{else}}
					     <td class="positive">{{uplineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
				 
                    	
						{{#if_small pnlamount 0}}
							<td class="negative">{{pnlamount}}</td>
							 {{else}}
								{{#if pnlamount 0}}
								 <td>{{pnlamount}}</td>
							{{else}}
						    <td class="positive">{{pnlamount}}</td>
						{{/if}}
						{{/if_small}}
                      {{#if_small downlineamount 0}}
					     <td class="negative">{{downlineamount}}</td>
				          {{else}}{{#if downlineamount 0}}
					     <td>{{downlineamount}}</td>
				       {{else}}
					     <td class="positive">{{downlineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
				   <td class="positive">{{commssionMila}}</td> 
 				   <td class="negative">{{commssionDiya}}</td> 
                   <td class="negative"><a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showcommDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Comm Detail</a>
					<a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showBetsDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Bets</a>
				    <a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showMarketWiseAmountDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Amount Detail</a>
				
	</td> 
                  </tr>
				  <tr class="commdetail_table_tr" id="commdetail_table_tr{{serialno}}">
				  <td colspan=8>
							
	                         
							 <input type="hidden" id="commdetailslideupdown{{serialno}}" value="up">
							 <div id="commdetail_list_div{{serialno}}" style="display:none;"><div>

							
							
                        <div>

							
                  </td></tr>

       <tr class="betsetail_table_tr" id="betsdetail_table_tr{{serialno}}">
				  <td colspan=9>
							
							
							 <input type="hidden" id="betsdetailslideupdown{{serialno}}" value="up">
							 <div id="betsdetail_list_div{{serialno}}" style="display:none;">amit<div>
							
                        <div>
         </td></tr>
                {{/each}}

			</tbody>
</table>
</script>



<script id="amountDetailHandler" type="text/x-handlebars-template">
<table class="table table-striped table-bordered account-stment">
	<thead>
	  <tr>
		<th>Serial No</th><th>Match Name</th>
			
				  {{#if_small uplineamount 0}}
					<th>UpLine</th> 
				  {{else}}{{#if uplineamount 0}}
				  <th>UpLine</th> 
				{{else}}
					<th>UpLine</th> 
		  		 {{/if}}{{/if_small}}
		   
		 <th>Net P&amp;L</th>
		 <th>DownLine</th>
		 <th>Comm +</th>
       <th>Comm -</th>
     <th>Action</th>
	 </tr>
	</thead>
                 <tbody>	
					{{#each data.list}}
					<tr class="table_row">
					 <td>{{serialno}}</td>  <td>{{matchname}}-->{{marketname}}{{#if_greaterThan sportid 500}}<p><span>roundId:{{marketid}}</span></p>  {{/if_greaterThan}}</td>
				      
				        {{#if_small uplineamount 0}}
					     <td class="negative">{{uplineamount}}</td>
				          {{else}}{{#if uplineamount 0}}
					     <td>{{uplineamount}}</td>
				       {{else}}
					     <td class="positive">{{uplineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
				 
                    	
						{{#if_small pnlamount 0}}
							<td class="negative">{{pnlamount}}</td>
							 {{else}}
								{{#if pnlamount 0}}
								 <td>{{pnlamount}}</td>
							{{else}}
						    <td class="positive">{{pnlamount}}</td>
						{{/if}}
						{{/if_small}}
                      {{#if_small downlineamount 0}}
					     <td class="negative">{{downlineamount}}</td>
				          {{else}}{{#if downlineamount 0}}
					     <td>{{downlineamount}}</td>
				       {{else}}
					     <td class="positive">{{downlineamount}}</td> 
				       {{/if}}
                     {{/if_small}}
				   <td class="positive">{{commssionMila}}</td> 
 				   <td class="negative">{{commssionDiya}}</td> 
                   <td class="negative"><a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showcommDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Comm Detail</a>
					<a href="javascript:void(0);" data-matchid="29869541" data-sportid="4" data-marketid="0" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showBetsDetail('{{serialno}}','{{matchid}}','down','{{marketid}}')">Bets</a>
				
	</td> 
                  </tr>
				  <tr class="commdetail_table_tr" id="commdetail_table_tr{{serialno}}">
				  <td colspan=8>
							
							 <input type="hidden" id="commdetailslideupdown{{serialno}}" value="up">
							 <div id="commdetail_list_div{{serialno}}" style="display:none;"><div>

							 
							
                        <div>

							
                  </td></tr>

             <tr class="betsetail_table_tr" id="betsdetail_table_tr{{serialno}}">
				  <td colspan=9>
							
							
							 <input type="hidden" id="betsdetailslideupdown{{serialno}}" value="up">
							 <div id="betsdetail_list_div{{serialno}}" style="display:none;">amit<div>
							
                        <div>

							
                  </td></tr>
                {{/each}}

			</tbody>
</table>
</script>




<script id="commDetailHistoryHandler" type="text/x-handlebars-template">
<table class="table table-striped table-bordered account-stment">
	<thead>
	  <tr>
		<th>Client Id</th><th>Pnl</th><th>Stack</th><th>UpLinePartnership</th><th>UpLineComm</th><th>UpLine+ Our Pertnership</th> <th>TotalComm</th> 
	 </tr>
	</thead>
                 <tbody>	
					{{#each data}}
					<tr class="table_row">
                     <td class="">{{userId}}</td>  <td class="">{{profitLoss}}</td> <td class="">{{stack}}</td> <td class="">{{uplineParnership}}</td>
                     <td class="positive">{{uplineCommssion}}</td>  <td class="">{{ourParnetshipWithUpline}}</td> <td class="negative">{{totalCommssion}}</td>
					</tr>

                {{/each}}

			</tbody>
</table>
</script>

<script id="matchUserWiseAmontHandler" type="text/x-handlebars-template">
<table class="table table-striped table-bordered account-stment">
	<thead>
	  <tr>
		<th>UserId</th><th>Amount</th>
	 </tr>
	</thead>
                 <tbody>	
				 {{#each data.list}}
					<tr class="table_row">
                     <td class="">{{userid}}</td>  
						{{#if_small uplineamount 0}}
					     <td class="negative">{{uplineamount}}</td>
				       {{else}}
					     <td class="positive">{{uplineamount}}</td> 
				        {{/if_small}}
                    </tr>
                {{/each}}

			</tbody>
</table>
</script>

<script>

function showBets(serialno,matchId,slideupdown,marketId){
	
	$( "#bet_list_div"+serialno).slideToggle("slow");
	if($("#slideupdown"+serialno).val() =="up"){
		betHistory(matchId,marketId,serialno,'bet_list_div')
		$("#slideupdown"+serialno).val('down');
		//$( "#bet_list_div"+serialno).show();
		}else{
			$("#slideupdown"+serialno).val('up');
			}
	
}


function showDetail(serialno,matchId,slideupdown,marketId){
	
	$( "#detail_list_div"+serialno).slideToggle("slow");
	if($("#deatilslideupdown"+serialno).val() =="up"){
		 profitLossDetail(matchId,serialno)
		$("#deatilslideupdown"+serialno).val('down');
		//$( "#bet_list_div"+serialno).show();
		}else{
			$("#deatilslideupdown"+serialno).val('up');
			}
	
}

function showAmountDetail(serialno,matchId,slideupdown,marketId){
	
	$("#detail_list_div_amount"+serialno).slideToggle("slow");
	if($("#deatilslideupdownamount"+serialno).val() =="up"){
	     matchUserWiseAmont(matchId,serialno)
		
		$("#deatilslideupdownamount"+serialno).val('down');
		//$( "#bet_list_div"+serialno).show();
		}else{
			$("#deatilslideupdownamount"+serialno).val('up');
			}
}

function showcommDetail(serialno,matchId,slideupdown,marketId){
	
	$( "#commdetail_list_div"+serialno).slideToggle("slow");
	if($("#commdetailslideupdown"+serialno).val() =="up"){
		commDetail(matchId,marketId,serialno)
		$("#commdetailslideupdown"+serialno).val('down');
		}else{
			$("#commdetailslideupdown"+serialno).val('up');
			}
	
}

function showMarketWiseAmountDetail(serialno,matchId,slideupdown,marketId){
	
	$( "#commdetail_list_div"+serialno).slideToggle("slow");
	if($("#commdetailslideupdown"+serialno).val() =="up"){
		marketUserWiseAmont(matchId,marketId,serialno)
		$("#commdetailslideupdown"+serialno).val('down');
		}else{
			$("#commdetailslideupdown"+serialno).val('up');
			}
	
	
}



function showBetsDetail(serialno,matchId,slideupdown,marketId){
	
	$( "#betsdetail_list_div"+serialno).slideToggle("slow");
	if($("#betsdetailslideupdown"+serialno).val() =="up"){
		//commDetail(matchId,marketId,serialno)
		betHistory(matchId,marketId,serialno,'betsdetail_list_div')
		$("#betsdetailslideupdown"+serialno).val('down');
		}else{
			$("#betsdetailslideupdown"+serialno).val('up');
			}
	
}


function betHistory(matchId,marketId,serialno,div){

	 var userId =$("#clientlist").val();  
     if(userId == null){
     	userId="-1";
       	}
    
	 
	  var data = {userId:"${user.id}",
			      matchId:matchId,
			      marketId:marketId,
			      "userId":userId
			     }
		$("#closedbetstable").append('<div class="loader"></div>')
		//$("#"+div).append('<div class="loader"></div>')
		  $.ajax({
			    type:'POST',
			    url:'<s:url value="/api/betHistory"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#"+div+serialno).html('');
					if(xhr.status == 200){ 
						 var source   = $("#betHistoryHandler").html();
     			    	  var template = Handlebars.compile(source);
     				     $("#"+div+serialno).append( template({data:jsondata}) );
     				     $(".loader").fadeOut("slow");
       				    
				}
			}
		  });
	}


function amountDetail(matchId,serialno){

	var userId =$("#clientlist").val();  
    if(userId == null){
    	userId="${user.userid}"
      	}
  	
	
	  var data = {userId:userId,
			      matchId:matchId,
			      "userId":userId
			     }
		$("#closedbetstable").append('<div class="loader"></div>')
		  $.ajax({
			    type:'POST',
			    url:'<s:url value="/api/amountDetail"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#detail_list_div"+serialno).html('');
					if(xhr.status == 200){ 
						 var source   = $("#amountDetailHandler").html();
   			    	  var template = Handlebars.compile(source);
   				      $("#detail_list_div"+serialno).append( template({data:jsondata}) );
   				     $(".loader").fadeOut("slow");
     				    
				}
			}
		  });
		  
	    
}


function profitLossDetail(matchId,serialno){

	var userId =$("#clientlist").val();  
    if(userId == null){
    	userId="${user.userid}"
      	}
  	
	
	  var data = {userId:userId,
			      matchId:matchId,
			      "userId":userId
			     }
		$("#closedbetstable").append('<div class="loader"></div>')
		  $.ajax({
			    type:'POST',
			    url:'<s:url value="/api/profitLossDetail"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#detail_list_div"+serialno).html('');
					if(xhr.status == 200){ 
						 var source   = $("#profitLossDetailHistoryHandler").html();
   			    	  var template = Handlebars.compile(source);
   				      $("#detail_list_div"+serialno).append( template({data:jsondata}) );
   				     $(".loader").fadeOut("slow");
     				    
				}
			}
		  });
		  
	    
}

function matchUserWiseAmont(matchId,serialno){
	
var userId =$("#clientlist").val();  
if(userId == null){
	userId="${user.userid}"
  	}
	

  var data = {userId:userId,
		      matchId:matchId
		     }
	$("#closedbetstable").append('<div class="loader"></div>')
	  $.ajax({
		    type:'POST',
		    url:'<s:url value="/api/matchUserWiseAmont"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				$("#detail_list_div_amount"+serialno).html('');
				if(xhr.status == 200){ 
					
 				   if(xhr.status == 200){ 
						 var source   = $("#matchUserWiseAmontHandler").html();
 			    	  var template = Handlebars.compile(source);
 				      $("#detail_list_div_amount"+serialno).append( template({data:jsondata}) );
 				     $(".loader").fadeOut("slow");
   				    
				}
			}
		}
	  });
	  
}

function marketUserWiseAmont(matchId,marketId,serialno){
	
	var userId =$("#clientlist").val();  
	if(userId == null){
		userId="${user.userid}"
	  	}
		

	  var data = {userId:userId,
			      matchId:matchId,
			      marketId:marketId
			     }
		$("#closedbetstable").append('<div class="loader"></div>')
		  $.ajax({
			    type:'POST',
			    url:'<s:url value="/api/marketUserWiseAmont"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#commdetail_list_div"+serialno).html('');
						
	 				   if(xhr.status == 200){ 
							 var source   = $("#matchUserWiseAmontHandler").html();
	 			    	  var template = Handlebars.compile(source);
	 				      $("#commdetail_list_div"+serialno).append( template({data:jsondata}) );
	 				     $(".loader").fadeOut("slow");
	   				    
					
				}
			}
		  });
		  
	}

marketUserWiseAmont

function commDetail(matchId,marketId,serialno){

	var userId =$("#clientlist").val();  
    if(userId == null){
    	userId="${user.userid}"
      	}
	
	  var data = {userId:userId,
			      matchId:matchId,
			      marketId:marketId
			     }
		$("#closedbetstable").append('<div class="loader"></div>')
		  $.ajax({
			    type:'POST',
			    url:'<s:url value="/api/commDetail"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#commdetail_list_div"+serialno).html('');
					if(xhr.status == 200){ 
						 var source   = $("#commDetailHistoryHandler").html();
 			    	  var template = Handlebars.compile(source);
 				     $("#commdetail_list_div"+serialno).append( template({data:jsondata}) );
 				     $(".loader").fadeOut("slow");
   				    
				}
			}
		  });
		  
	    
}
</script>

<!-- ************ start ***************** -->

<div class="cliennt-container">
      
        <div class="account-container client-record pt-4">
  
              <div class="account-record account-stment profitLoss">
                <div class="acont-box">
                  <span class="acont-left ">
                     <h4>Profit & Loss</h4>
                     <div class="bottom-line"></div>
                  </span>
                  <!-- <div class="acont-right float-right"><a href="./addAccount.html">Add Account</a></div> -->
              ` <div class="form-row">
					<div class="form-group col-sm-4 col-md-2">					   
					   <label for="inputState">Start Date</label>
					   <div class="input-group">
						 <input class="datepicker form-control" id="betsstartDate" style="border-radius: 5px 0 0 5px;" >
						 <div class="input-group-append">
							<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
						 </div>
					  </div>
				   </div>
				   <div class="form-group col-sm-4 col-md-2">
					   <label for="inputState">End Date</label>
					   <div class="input-group">
						 <input class="datepicker form-control" id="betsendDate"  style="border-radius: 5px 0 0 5px;">
						 <div class="input-group-append">
							<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
						 </div>
					  </div>
				   </div>
				    <div class="form-group col-sm-4 col-md-2">
					  <div class="form-group ">
						 <label for="inputState">Sport</label>
						 <select id="sport" class="form-control">
							<option value="4">Cricket</option>
						 </select>
					  </div>
				   </div> 
				   <div class="form-group col-sm-3 col-md-2">
					  <label for="inputState">Search By Client Name</label>
					  <select id="clientlist" class="form-control"></select>
				   </div>				   
					<div class="form-group col-sm-2 col-md-1">
						<label class="d-none d-sm-block">&nbsp;</label>
						<button type="button" class="btn btn btn-success w-100" onclick="getBets()">Apply</button>
					</div>
				   <div class="form-group col-sm-2 col-md-1">
						<label class="d-none d-sm-block">&nbsp;</label>
						<button  type="button" class="btn btn-danger w-100">Clear</button>
				   </div>
				</div>
              </div>
              
           
 
              
                <div class="account-table bethistory table-responsive" id="closedbetstable">  </div>

               

            </div>
            </div>
            
       
   
   
    </div>
              <script>
              $(document).ready(function(){
            	  getActiveSports(true);
            	  
            	  var now = new Date();
            	
            	var day = ("0" + now.getDate()).slice(-2);
            	var month = ("0" + (now.getMonth() + 1)).slice(-2);
            	
            	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
            	
            	 var date = new Date();
                 var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate()-7);
                 var enddate = new Date(date.getFullYear(), date.getMonth(), date.getDate());

            	

            	 $('#betsstartDate').datepicker({
            			format: "yyyy-mm-dd",
            			autoclose: true,
            			todayHighlight: true
            			
             });
            	 $('#betsendDate').datepicker({
            		 format: "yyyy-mm-dd",
            			autoclose: true,
            			todayHighlight: true
            			
            });
            	 
            	 $('#betsstartDate').datepicker('setDate', startdate);
            	 $('#betsendDate').datepicker('setDate', enddate);
            	
            	 getBets();  
            });

              function getBets()
              {		   

                var userId =$("#clientlist").val();  
                if(userId == null){
                	userId="-1";
                  	}
               
              	var data={"startdate":$("#betsstartDate").val()+ " 00:00:00",
              	"enddate":$("#betsendDate").val()+ " 23:59:59",
              	"sportId":$("#sport").val(),
              	"userId":userId,
              	"matchId":"-1"

              	};
              	$("#bet_list_div1").append('<div class="loader"></div>')
              	
              		$.ajax({
              			type:'POST',
              			url:'<s:url value="/api/sportdetailHomeScreen"/>',
              			data: JSON.stringify(data),
              			dataType: "json",
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200)
              				{
              					
              					$("#nopnl").hide();
              					$("#closedbetstable").show();
              					$("#closedbetstable").html('');
              					  var source   = $("#closedbetshandler").html();
              			    	  var template = Handlebars.compile(source);
              				      $("#closedbetstable").append( template({data:jsondata}) );
              				   	   $(".loader").fadeOut("slow");
              				    
              				    
              				   }
              		}
              	});
              }
              function getActiveSports(status){		
            	    $.ajax({
            					type:'GET',
            					url:'<s:url value="api/getActiveSportList"/>?status='+status,
            					contentType:"application/json; charset=utf-8",
            					success: function(jsondata, textStatus, xhr) {
            			
            				if(xhr.status == 200){  
            				
            					  $.each(jsondata, function(index, element) {
            						  if(element.sportId !=4){
            							   $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" name = "'+element.name+'"  > '+element.name+' </option>', {
		             			                
                       		            }));
                				}
            		          
            		            			            			       
            				});
            			    	 
            				}
            				
            			
            		}
            	});
            	    
            	}

              $('#clientlist').select2({
            		
            	    ajax: {
            	      url: '<s:url value="/api/userByUserLike"/>',
            	      dataType: 'json',
            	      processResults: function (data) {
            			 return {
            	              results:data
            	          };
            	      },
            	      cache: false
            	  },
            	  minimumInputLength:2,
            	  allowClear:true,
            	  placeholder: {
            	      id: "",
            	      placeholder: "Leave blank to ..."
            	    },
            	});	

          	function userDetail(betid,id){
          		
          		$(".userdetail").html('');
          		  $.ajax({
          				type:'GET',
          				url:'<s:url value="/api/userHerarchy/"/>'+betid,
          				contentType:"application/json; charset=utf-8",
          				success: function(jsondata, textStatus, xhr) {
          		
          			 if(xhr.status == 200){     
          				 $("#userdetail"+id).html("<span>"+jsondata.response+"</span>")
          		 }
          		}
          	});
          				
          	}
              </script>
 <jsp:include page="handlebars.jsp" />  
<!-- *************** end ****************** -->