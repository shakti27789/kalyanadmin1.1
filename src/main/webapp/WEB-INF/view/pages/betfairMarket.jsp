<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
    <script id="markethandle" type="text/x-handlebars-template">
<div class="back_wrap">
     <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='backToBetfairEvents()'>Back</a>
</div>
<div class="table-responsive table table-striped table-bordered account-stment">
             	<table class="table common-table">
                <thead>
                  <tr>
                    <th>Id</th>
                  
					<th>is Unmatched</th>
					<th>Action</th>
					
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{marketId}}<input type='hidden' id ='marketid{{id}}' value='{{marketId}}'></td>
                   	   <td>{{marketName}}<input type='hidden' id ='marketname{{id}}' value='{{marketName}}'></td>
                    
                        <td>
							<input type="checkbox" id="addMarketcheckbox{{id}}"  onclick='addMarket("{{id}}")'>
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
            </div>
    </script>
    
    <script>
     
   
    function marketByEvent(eventid,opendate)
    {
   	
    $("#eventidtext").val(eventid)
    $("#eventnametext").val($("#eventname"+eventid).val())
    $("#opendate").val(opendate)
     
    
	 isChecked =  $("#addEventcheckbox"+eventid).is(":checked");
	 if(isChecked){
		 $("#betfaircomp").hide();
		 $("#betfairseries").hide();
		 $("#betfairevents").hide();
         $("#betfairmarket").show();
         $("#betfairmarket").html('');
         $("#page-wrapper").append('<div class="loader"></div>')
	    	$.when(
	    	         
	    			 $.ajax({
	    					type:'GET',
	    					url:'<s:url value="/api/matchMarketList"/>?eventid='+eventid,
	    					//url:'http://localhost/api/matchMarketList?eventid='+eventid,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							//  var arr = $.parseJSON(jsondata);
	    							  var source   = $("#markethandle").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#betfairmarket").append( template({data:jsondata}) );
	    					    	  $(".loader").fadeOut("slow");
	    					    	
	    						}
	    						
	    					}
	    				}
	    			})).then(function( x ) {
	    				findMarketByStatus()
	    	     		});
	    	
	    	
	 }
	 else{
		 showMessage("please Enable Series first","error","Oops...");
			
		 }
    }
    
    
     function lotusmarketByEvent(eventid,id,fancyprovider)
    {
    $("#eventidtext").val(eventid)
    $("#eventnametext").val($("#eventname"+eventid).val())
	 isChecked =  $("#addLotusEventcheckbox"+id).is(":checked");
	 if(isChecked){
	
	  var data={eventid:eventid}
		 $("#betfaircomp").hide();
		 $("#betfairseries").hide();
		 $("#betfairevents").hide();
         $("#betfairmarket").hide();
         $("#lotusmatch").hide();
          $("#fancylist").html('');
         $("#fancylist").show();
         $("#fancyprovider").val(fancyprovider);
	    	
	    $.when(	  
	    	$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/getLotusEventList?eventid="/>'+eventid+"&fancyprovider="+fancyprovider,
  			success: function(jsondata, textStatus, xhr) {
    				   $('#fancylist').html('');
    					var source   = $("#fancylotushandle").html();
    			    	  var template = Handlebars.compile(source);
   				    	  $("#fancylist").append( template({data:jsondata}) );
   				    	  $.each(jsondata, function( key, value ) {
	   				  		 if(fancyprovider =="Diamond"){
	   				  		 skyFancy(value.eventid,value.ids,"Diamond");
	   				  		 }else{
	   				  		 skyFancy(value.eventid,value.id,"Skyexhange");
	   				  		 }
   				    		 
	   				  	  });
    		}
    	})).then(function( x ) {
	    				findLotusEventByStatus(eventid)
	    	     		});;
	    	
	 }
	 else{
		 showMessage("please Enable Fancy first","error","Oops...");
			
		 } 
    }
    
    function addMarket(id)
    {		
    	     isChecked =  $("#addMarketcheckbox"+id).is(":checked");
    	    
    		 var isunmatched= false;
    		 if($("#isUnmatched"+id).is(":checked")){
    			 isunmatched = true;
    		 }
    		 
    	     if(isChecked){
    	    	
    	    	 var data={marketid:$("#marketid"+id).val(),eventid:parseInt($("#eventidtext").val()),seriesid:parseInt($("#seriesidtext").val()),sportid:parseInt($("#sportidtext").val())
    	    			 ,marketname:$("#marketname"+id).val(),matchname:$("#eventnametext").val(),appid:${user.appid},unmatched:isunmatched,startdate:$("#opendate").val()}
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Market!',
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
    					url:'<s:url value="api/addMarketByAdmin"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findMarketByStatus();
    						 $(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				 } else{
    				 $("#addMarketcheckbox"+id).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	 
    	    	 var data={marketid:$("#marketid"+id).val(),eventid:parseInt($("#eventidtext").val()),seriesid:parseInt($("#seriesidtext").val()),sportid:parseInt($("#sportidtext").val())
    	    			 ,marketname:$("#marketname"+id).val(),matchname:$("#eventnametext").val(),subadminid:'${user.id}',appid:${user.appid}}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Market!',
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
    					url:'<s:url value="api/updateMarket"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findMarketByStatus();
    				    } 
    				});
    				
    				} else{
    				 $("#addMarketcheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
   	 
    } 
    
    function findMarketByStatus(){
    	
		 $.ajax({
				type:'GET',
				url:'<s:url value="api/findMarketByStatus/"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						
						 $.each(jsondata, function(key,value){
							
							 $('#addMarketcheckbox'+value.ids).prop('checked', true);
							 $('#addLotusEventcheckbox'+value.ids).prop('checked', true);
							 if(value.isunmatched==true){
								 $("#isUnmatched"+value.ids).prop('checked', true);
								 $("#isunmatched"+value.ids).prop('checked', true);
							 }
						 });
					}
					
				}
			}
		});
   
}


		
		 function findLotusEventByStatus(eventid){
		    	
				 $.ajax({
						type:'GET',
						url:'<s:url value="api/findLotusEventByStatus/"/>?eventid='+eventid,
						success: function(jsondata, textStatus, xhr) {
						{
							if(xhr.status == 200)
							{     
								
								 $.each(jsondata, function(key,value){
									
							           console.log(value.runnerid+value.eventid)
									 $('#addlotusFancy'+value.ids).prop('checked', true);
									 
								 });
							}
							
						}
					}
				});
		  
		}
		
		
		 function backToLotusEvent()
		    {
		   
				 $("#betfaircomp").hide();
				 $("#betfairseries").hide();
				 $("#betfairevents").hide();
		         $("#betfairmarket").hide();
		         $("#lotusmatch").show();
		         $("#fancylist").hide();
			    	
	
		    }
		
		
		 function backToBetfairEvents()
			    {
			   	
					 $("#betfaircomp").hide();
					 $("#betfairseries").hide();
					 $("#betfairevents").show();
			         $("#betfairmarket").hide();
			       
				    
			    }
    
    
    </script>
    
  