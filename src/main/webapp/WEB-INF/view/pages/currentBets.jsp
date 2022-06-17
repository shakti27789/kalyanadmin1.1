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
       <th >Match Date</th>
    </tr>
</thead>
<tbody>
  {{#each data}}
{{#if_eq isback true}}
  <tr class="back">
		
        <td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
     	{{#if_eq marketname "Fancy"}}
         <td>Yes->  {{pricevalue}}</td>
     {{else}} 
        <td>BACK</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>
{{else}} 
<tr class="lay">		
<td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
        {{#if_eq marketname "Fancy"}}
         <td>No->  {{pricevalue}}</td>
     {{else}} 
        <td>LAY</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>

{{/if_eq}}	

   {{/each}}  
   
</tbody>

</table>
</script>

<div class="cliennt-container">  
     <div class="account-container">
  
  <div class="account-record">
  <div class="head-tittle">
      <h4>Current Bets</h4>
  </div>
  
  <div class="input-date">
    <div class="select-box match-box w-100 mr-0">
        <form>
			<div class="form-row">
				<div class="col-sm-6">
					<select  class="custom-select form-group" id='type'>
						<option value="-1"  label="Market" name = "ALl"></option>
						<option value="Match Odds"  label="Market" name = "Match Odds"></option>
						<option value="Fancy"  label="Fancy"  name = "Fancy"></option>
					</select>
					<select name="sport" id="sport" class="custom-select form-group">
						<option value="-1">All</option>
					</select>
				</div>
				<div class="col-sm-6">
					<select  class="custom-select form-group" id='match'>
						<option value="-1" selected>Select match</option>
					</select>
					<select class="custom-select form-group" id='market'>
						<option value="-1" selected>Select market</option>
					</select>
				</div>
			</div>
		</form>
    </div>

    <!-- <div class="date-box"><input class="datepicker fa fa-calendar-o" id="startDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
    <div class="date-box"><input class="datepicker fa fa-calendar-o" id="endDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
   -->

    <div class="form-group">
        <input class="btn btn-primary" type="button" onclick="currentBets()" value="Submit">
    </div>
   </div>
  
    <div class="account-table bethistory table-responsive" id="currentBets">
    
   </div>
</div>
      </div>
</div>
<script>
var page = 0;
var sportId =-1;
$(document).ready(function(){
	getActiveSports(true)
  //  currentBets();	
      $("#sport").change(function(){
          showMatch("Match Odds",true,$("#sport").val());	
       });
        $("#match").change(function(){
           if($("#type").val()==='Fancy'){
            
         }else{
            showMarketBymatch($("#match").val(),true,true);
       }
      }); 
          $("#market").change(function(){
           
          });
       });
            
            
function getActiveSports(status){		
    $.ajax({
				type:'GET',
				url:'<s:url value="api/getActiveSportList"/>?status='+status,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
		
			if(xhr.status == 200){  
			 $("#sport").html('');
			$("#sport").append($('<option value="-1"> Select Sport</a> </option>'));
				  $.each(jsondata, function(index, element) {
			 
	             $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" name = "'+element.name+'"  > '+element.name+' </option>', {
	             			                
	            }));
	            			            			       
			});
		    	 
			}
			
		
	}
});
    
}


 
function currentBets()
{


data = {
			
			"sportid":$("#sport").val(),
			"marketid":$("#market").val(),
			"matchid":$("#match").val(),
			"childid":0
		};
	        $("#currentBets").append('<div class="loader"></div>')
  	
	  $.ajax({
		    type:'POST',
			url:'<s:url value="api/currentBetsNew"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				$("#currentBets").html('');
				if(xhr.status == 200){ 
 						
 						var source   = $("#betHistoryHandler").html();
						var template = Handlebars.compile(source);
				 	    $("#currentBets").append( template({data:jsondata})); 
				 	    $(".loader").fadeOut("slow");
     				    
				
			}
		}
	  });

}
		
function showMatch(type,status,sportId){
	$("#match").html('');
                 $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getMatchBymarketname"/>?status='+status+'&sportid='+sportId,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					 if(xhr.status == 200){     
					   $("#match").append($('<option value="-1">Select Match</option>'));
					   $.each(jsondata, function(index, element) {
					   $("#match").append($('<option id = "'+element.eventid+'" value="'+element.eventid+'" name = "'+element.eventname+'" > '+element.eventname+' </option>', {
			         }));
			       });
				 }
				}
    		});
    
    }
    
function showMarketBymatch(eventid,isactive,status,matchaname){
                $("#market").html('');
                  $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveMarketListByEvent"/>?eventid='+eventid+'&isactive='+isactive+'&status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200){     
						  $.each(jsondata, function(index, element) {
					 
			             $("#market").append($('<option id = "'+element.marketid+'" value="'+element.marketid+'" name = "'+element.marketname+'" > '+element.marketname+' </option>', {
			             			                
			            }));
			            			            			       
        			});
				    	 
					}
				   }
    		   });
    		 }
    		 
    		 
		</script>
		<jsp:include page="handlebars.jsp" />  