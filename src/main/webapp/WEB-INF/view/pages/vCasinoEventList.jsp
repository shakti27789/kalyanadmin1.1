<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
    <script id="eventhandle" type="text/x-handlebars-template">
				<div class="back_wrap">
      				<a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='serirsBySport()'>Back</a>
				</div>
<div class="table-responsive table table-striped table-bordered account-stment">
             	<table class="table common-table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Match Name</th>
                    <th>Open Date</th>
					<th>Min Bet Match</th>
					<th>Max Bet Match</th>
					<th>Bet Delay Match</th>

                    <th>Min Bet Fancy</th>
					<th>Max Bet Fancy</th>
					<th>Bet Delay Fancy</th>
					<th>Min Bet BookMaker</th>
					<th>Max Bet BookMaker</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{event.id}}<input type='hidden' id ='eventid{{event.id}}' value='{{event.id}}'></td>
                   	   <td>{{event.name}}<input type='hidden' id ='eventname{{event.id}}' value='{{event.name}}'></td>
					  
                       <td>{{event.openDate}}<input type='hidden' id =openDate{{event.id}} value='{{event.openDate}}'></td>
					   <td>
						<div class="filterSection full-width-form">
							<select id =min{{event.id}}></select>	
						</div>
						</td>
 						<td>
						<div class="filterSection full-width-form">
							<select id =max{{event.id}}></select>	
						</div>
						</td>
                        <td><div class="filterSection full-width-form">
							<select id =betdelay{{event.id}}></select>	
						</div>
						</td>


					 <td>
						<div class="filterSection full-width-form">
							<select id =minfancy{{event.id}}></select>	
						</div>
						</td>
 						<td>
						<div class="filterSection full-width-form">
							<select id =maxfancy{{event.id}}></select>	
						</div>
						</td>
                        <td><div class="filterSection full-width-form">
							<select id =betdelayfancy{{event.id}}></select>	
						</div>
						</td>

						
 						<td>
						<div class="filterSection full-width-form">
							<select id =minbetBookmaker{{event.id}}></select>	
						</div>
						</td>
                        <td><div class="filterSection full-width-form">
							<select id =maxbetBookmaker{{event.id}}></select>	
						</div>
						</td>		

						<td>
							<input type="checkbox" id="addEventcheckbox{{event.id}}"  onclick='addEvent({{event.id}})'>
							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=marketByEvent('{{event.id}}','{{event.openDate}}')>Market List</a> 
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
</div>
            
    </script>
    
    <script>
    
   
    function eventBySeries(compid)
    {
   	
    	
	 var sportid = $("#sportidtext").val();
	 $("#seriesidtext").val(compid);
	 isChecked =  $("#addseriescheckbox"+compid).is(":checked");
	 if(isChecked){
		 $("#betfaircomp").hide();
		 $("#betfairseries").hide();
		 $("#betfairevents").show();
		 $("#betfairevents").html('');
		 $("#page-wrapper").append('<div class="loader"></div>') 
	    	$.when(
	    			 $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getEvent/"/>'+sportid+'/'+compid,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							  var arr = $.parseJSON(jsondata);
	    							 // var arr = jsondata;

	    							  var source   = $("#eventhandle").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#betfairevents").append( template({data:arr}) );
	    					    	  
	    					    	  $.each(arr, function( key, value ) {
	    					    		  getMinBet("minbet",value.event.id);
	    					    		  getMaxBet("maxbet",value.event.id);
	    					    		  getBetDelay("betdelay",value.event.id);
	    					    		});
	    					    	  $(".loader").fadeOut("slow");
	    					    	
	    						}
	    						
	    					}
	    				}
	    			})).then(function( x ) {
	    				findEventByStatus()
	    	     		});
	    	
	    	
	 }
	 else{
		 showMessage("please Enable Series first","error","Oops...");
			
		 }
    }
    
    function addEvent(id)
    {		
    	     isChecked =  $("#addEventcheckbox"+id).is(":checked");
    	   
    	     if(isChecked){
    	    	
    	    	
    	    	 var data={eventname:$("#eventname"+id).val(),eventid:parseInt($("#eventid"+id).val()),seriesid:parseInt($("#seriesidtext").val()),marketcount:parseInt($("#marketCount"+id).val()),sportid:parseInt($("#sportidtext").val())
    	    			 ,timezone:$("#timezone"+id).val(),openDate:$("#openDate"+id).val(),status:true,countrycode:$("#countryCode"+id).val(),adminid:'${user.parentid}',appid:${user.appid},minbet:parseInt($("#min"+id).val()),maxbet:parseInt($("#max"+id).val()),betdelay:parseInt($("#betdelay"+id).val())
    	    			 ,minbetfancy:parseInt($("#minfancy"+id).val()),maxbetfancy:parseInt($("#maxfancy"+id).val()),betdelayfancy:parseInt($("#betdelayfancy"+id).val()),minbetBookmaker:parseInt($("#minbetBookmaker"+id).val()),maxbetBookmaker:parseInt($("#maxbetBookmaker"+id).val())}
    	     swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Event!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 	
    	    	  $("#page-wrapper").append('<div class="loader"></div>') 
    	    	 
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="api/addEvent"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
        					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findEventByStatus();
    						 $(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				 } else{
    				$("#addEventcheckbox"+id).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	 
    	    	 var data={eventname:$("#eventname"+id).val(),eventid:parseInt($("#eventid"+id).val()),seriesid:parseInt($("#seriesidtext").val()),marketcount:parseInt($("#marketCount"+id).val()),sportid:parseInt($("#sportidtext").val())
    	    			 ,timezone:$("#timezone"+id).val(),openDate:$("#openDate"+id).val(),status:false,countrycode:$("#countryCode"+id).val(),subadminid:'${user.id}',appid:${user.appid}}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Event!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  $("#page-wrapper").append('<div class="loader"></div>') 
    	    	  $(".loader").fadeOut("slow");
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateEvent"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findEventByStatus();
    						  $(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				} 
    				else{
    				$("#addEventcheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
   	 
    }
    
    function findEventByStatus(){
    	
			 $.ajax({
					type:'GET',
					url:'<s:url value="api/findEventByStatus/"/>',
					success: function(jsondata, textStatus, xhr) {
					{
						if(xhr.status == 200)
						{     
							
							 $.each(jsondata, function(key,value){
								 console.log(value.seriesid)
								 $('#addEventcheckbox'+value.eventid).prop('checked', true);
								 
							 });
						}
						
					}
				}
			});
	   
    }
    
    function getMinBet(type,eventid) {
           
    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					 if(type ="minbet"){
						 if(i==0){
						  $("#min"+eventid).append('<option value="-1">Min Bet</option>')
						  $("#minfancy"+eventid).append('<option value="-1">Min Bet</option>');
						  $("#minbetBookmaker"+eventid).append('<option value="-1">Min Bet</option>');
						 } $("#min"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						   $("#minfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						   $("#minbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						
					 }
					 i++;
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					 
			    } 
			});     	    	
    }

    function getMaxBet(type,eventid) {

    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					   if(i==0){
							  $("#max"+eventid).append('<option value="-1">Max Bet</option>')
							 } 
					       $("#max"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
					i++;
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
   }
    
    function getBetDelay(type,eventid) {

    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					   if(i==0){
							  $("#betdelay"+eventid).append('<option value="-1">Bet Delay</option>')
							 } 
					   $("#betdelay"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
					i++;
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
  }
   
    
    
    </script>
    
  	