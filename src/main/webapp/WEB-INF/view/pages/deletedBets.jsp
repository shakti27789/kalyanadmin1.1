<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script id="betHistoryHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
  		<th>UserId</th>       
 		<th>Event Name</th>
        <th>Nation</th>
        <th>Bet Type</th>
        <th>User Rate</th>
        <th> Amount</th>
        <th >Profit/Loss</th>
        <th >Place Date</th>
        <th >Match Date</th>
		 <th >Action</th>
    </tr>
</thead>
<tbody>
  {{#each data}}
     {{#if_eq isback true}}
    <tr class="back">
  {{else}} 
 <tr class="lay">
 {{/if_eq}}	
        <td>{{userid}}</td>
		<td>{{matchname}}</td>
        <td>{{selectionname}}</td>
        <td>BACK</td>
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{netpnl}}</td>
        <td>{{placeTime}}</td>
        <td>{{matchedtime}}</td><td>
 		{{#if_eq type "Fancy"}}
		<button type="button" class="btn btn-info blue-btn btn-sm" onclick="rollbackFancyBet({{id}})">RollBack</button>
 		{{else}} 
		<td><button type="button" class="btn btn-info blue-btn btn-sm" onclick="rollbackMatchBet({{id}})">RollBack</button>

		{{/if_eq}}
		<button type="button" class="btn btn-info blue-btn btn-sm" onclick = "betDetail('{{id}}')">i</button></td>

   </tr>
  {{/each}}
   
   
</tbody>

</table>
</script>

<div class="cliennt-container">  
     <div class="account-container">
  
  <div class="account-record">
  <div class="head-tittle">
      <h4>Bet History</h4>
  </div>
  
  <div class="input-date">
    <div class="select-box match-box w-100 mr-0">
        <form>
			<div class="form-row">
				<div class="col-sm-6">
					<select  class="custom-select form-group" id='type'>
						<option value="Match Odds"  label="Market" name = "Match Odds"></option>
						<option value="Fancy"  label="Fancy"  name = "Fancy"></option>
					</select>
					<select name="sport" id="sport" class="custom-select">
						<option value="-1">All</option>
					</select>
				</div>
				<div class="col-sm-6">
					<select  class="custom-select form-group" id='match'> <option value="-1" selected>Select match</option></select>
					<select class="custom-select form-group" id='market'><option value="-1" selected>Select market</option></select>
				</div>
			</div>
		</form>
    </div>

    <!-- <div class="date-box"><input class="datepicker fa fa-calendar-o" id="startDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
    <div class="date-box"><input class="datepicker fa fa-calendar-o" id="endDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
   -->

    <div class="form-group">
        <input class="btn btn-primary" type="button" value="Submit">
    </div>
   </div>
  
    <div class="account-table bethistory table-responsive" id="betHistory">
    
   </div>
   
   <div id="otherBetsModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #0088cc; padding: 10px 10px; color: white;">
			
					<h4 class="modal-title">Bets</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive" id="other-bets-div"
						style="max-height: 500px;">
						
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

</div>
</div>
      </div>
</div>
<script>
var page = 0;

$(document).ready(function(){
	getActiveSports(true)
    deletedBets();	
      $("#sport").change(function(){
          showMatch("Match Odds",true,$("#sport").val());	
       });
        $("#match").change(function(){
           if($("#type").val()==='Fancy'){
            getBetHistory();
         }else{
            showMarketBymatch($("#match").val(),true,true);
       }
      }); 
          $("#market").change(function(){
        	  deletedBets();
          });
       });


 function betDetail(id){
	// $('#otherBetsModal').modal('show');

	 $.ajax({
			type:'GET',
			url:'<s:url value="api/betDetail/"/>'+id,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
	
		if(xhr.status == 200){  
		 
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
			 $("#sport").html('');
			$("#sport").append($('<option value=""> Select Sport</a> </option>'));
				  $.each(jsondata, function(index, element) {
			 
	             $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" name = "'+element.name+'"  > '+element.name+' </option>', {
	             			                
	            }));
	            			            			       
			});
		    	 
			}
			
		
	}
});
    
}


 
function getBetHistory()
{

	/* if(action == "next"){
		page = page+1;    		
	}else if(action == "previous"){
		page = page-1;
	} */

	var data

if(${user.usertype}==4){
	data = {
			"adminid":"${user.id}",
			"isactive":"false",
			"supermasterid":"-1",
			"subadminid":"-1",
			"startdate":"",
			"enddate":"",
			"ismatched":"-1",
			"matchid":$("#match").val(),
			"isback":"-1",
			"sportid":"-1",
			"masterid":"-1",
			"status":"-1",
			"dealerid":"-1",
			"userid":"-1",
	    	"page":page+"",
	    	"count":"200",
	    	"isdeleted":"true",
			"type":"-1",
			"marketid":$("#market").val()
		};

}else if(${user.usertype} == 5){
			data = {
				"adminid":"-1",
				"supermasterid":"-1",
				"subadminid":"${user.id}",
				"isactive":"false",
				"startdate":"",
				"enddate":"",
				"ismatched":"-1",
				"matchid":$("#match").val(),
				"isback":"-1",
				"sportid":$("#sport").val(),
				"masterid":"-1",
				"status":"-1",
				"dealerid":"-1",
				"userid":"-1",
		    	"page":page+"",
		    	"count":"200",
		    	"isdeleted":"true",
		    	"type":"-1",
		    	"marketid":$("#market").val()
			};
			}else if(${user.usertype} == 0){
			data = {
				"adminid":"-1",
				"supermasterid":"${user.id}",
				"subadminid":"-1",
				"isactive":"false",
				"startdate":"",
				"enddate":"",
				"ismatched":"-1",
				"matchid":$("#match").val(),
				"isback":"-1",
				"sportid":$("#sport").val(),
				"masterid":"-1",
				"status":"-1",
				"dealerid":"-1",
				"userid":"-1",
		    	"page":page+"",
		    	"count":"200",
		    	"isdeleted":"true",
				"type":"-1",
				"marketid":$("#market").val()
			};
			}else if(${user.usertype}==1){
	data = {
			"adminid":"-1",
			"isactive":"false",
			"supermasterid":"-1",
			"subadminid":"-1",
			"startdate":"",
			"enddate":"",
			"ismatched":"-1",
			"matchid":$("#match").val(),
			"isback":"-1",
			"sportid":$("#sport").val(),
			"masterid":"${user.id}",
			"status":"-1",
			"dealerid":"-1",
			"userid":"-1",
	    	"page":page+"",
	    	"count":"200",
	    	"isdeleted":"true",
			"type":"-1",
			"marketid":$("#market").val()
		};

}else{
	data = {
			"adminid":"-1",
			"isactive":"false",
			"supermasterid":"-1",
			"subadminid":"-1",
			"startdate":"",
			"enddate":"",
			"ismatched":"-1",
			"matchid":$("#matchid").val(),
			"isback":"-1",
			"sportid":$("#sport").val(),
			"masterid":"-1",
			"status":"-1",
			"dealerid":"${user.id}",
			"userid":"-1",
	    	"page":page+"",
	    	"count":"200",
	    	"isdeleted":"true",
	    	"type":"-1",
	    	"marketid":$("#market").val()
		};    		
}                $("#betHistory").html('')
				$.ajax({
					type:'POST',
					url:'<s:url value="/api/getbets"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200){
		    				 var source   = $("#betHistoryHandler").html();
	    			    	 var template = Handlebars.compile(source);
	   				    	 $("#betHistory").append( template({data:jsondata}) );	
							
   						}else{

   					
						}
				}
			});
		}
		
function showMatch(type,status,sportId){
    
                 $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getMatchBymarketname"/>?status='+status+'&sportid='+sportId,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					 if(xhr.status == 200){     
					   $("#match").append($('<option> <a href="#" >Select Match</a> </option>'));
					   $.each(jsondata, function(index, element) {
					   $("#match").append($('<option id = "'+element.eventid+'" value="'+element.eventid+'" name = "'+element.eventname+'" > '+element.eventname+' </option>', {
			         }));
			       });
				 }
				}
    		});
    
    }
    
function showMarketBymatch(eventid,isactive,status,matchaname){
  
                  $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveMarketListByEvent"/>?eventid='+eventid+'&isactive='+isactive+'&status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200){     
							$("#market").append($('<option> <a href="#" >Select Market</a> </option>'));
						  $.each(jsondata, function(index, element) {
					 
			             $("#market").append($('<option id = "'+element.marketid+'" value="'+element.marketid+'" name = "'+element.marketname+'" > '+element.marketname+' </option>', {
			             			                
			            }));
			            			            			       
        			});
				    	 
					}
				   }
    		   });
    		 }
    		 


function rollbackMatchBet(id){
	  
    $.ajax({
			type:'POST',
			url:'<s:url value="api/rollbackMatchBet"/>?id='+id,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
			if(xhr.status == 200){     
				showMessage(jsondata.message,jsondata.type,jsondata.title);
					
	         }
	   },
			complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			    } 
 });
}

function rollbackFancyBet(id){
	  
    $.ajax({
			type:'POST',
			url:'<s:url value="api/rollbackFancyBet"/>?id='+id,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
			if(xhr.status == 200){     
				showMessage(jsondata.message,jsondata.type,jsondata.title);
					
	         }
	   },
			complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			    } 
 });
}



function deletedBets()
{


	var data

	
	 
	data = {
			
			"sportid":$("#sport").val(),
			"marketId":$("#market").val(),
			"matchId":$("#match").val(),
			"type":$("#type").val()
		};
	// $("#betHistory").append('<div class="loader"></div>')
	          $("#myTable").html('');
				$.ajax({
					type:'POST',
					url:'<s:url value="/api/deletedBets"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200){
		    				 var source   = $("#betHistoryHandler").html();
	    			    	 var template = Handlebars.compile(source);
	   				    	 $("#betHistory").append( template({data:jsondata}) );	
	   				    	$(".loader").fadeOut("slow");
   						}else{

   							$(".loader").fadeOut("slow");
						}
				}
			});
		}


		</script>
		<jsp:include page="handlebars.jsp" />  