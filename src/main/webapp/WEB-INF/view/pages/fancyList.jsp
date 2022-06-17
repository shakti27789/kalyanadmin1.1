<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="fancylotushandle" type="text/x-handlebars-template">
<div class="back_wrap">
 <span><a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='refereshFancy()'>Referesh</a></span>  
  <span><a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='backToLotusEvent()'>Back</a></span>
</div>
<div class="table-responsive">
             	<table class="table common-table">
                <thead>
                  <tr>
                  <th>Fancy Id</th>
                    <th>Fancy Name</th>
                    <th>Status</th>
					<th>Sky Fancy</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                  
                  	   <td>{{fancyid}} <input type='hidden' id ="fancyid{{ids}}" value='{{fancyid}}'</td>
                   	   <td>{{name}} <input type='hidden' id ="fancyname{{ids}}" value='{{name}}'</td>
                   	   <td>{{status}} <input type='hidden' id ="fancystatus{{ids}}" value='{{status}}'</td>

							<td>
							<div class="filterSection full-width-form">
							<select id =skyfancy{{ids}}></select>
						</div>
						</td>
                    	<td>
							<input type="checkbox" id="addlotusFancy{{ids}}"  onclick='saveLotusFancy("{{fancyid}}","{{ids}}","{{eventid}}")'>
						
						</td>
						
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
	</div>
            
    </script>
  
    <script>
    
    
    function saveLotusFancy(fancyid,id,eventid)
    {		
    	     isChecked =  $("#addlotusFancy"+id).is(":checked");
    	   
    	     if(isChecked){	
    	    	var skyfancyid =$("#skyfancy"+id).val();
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="api/saveLotusFancy"/>?id='+id+'&eventid='+eventid+"&fancyid="+fancyid+"&skyfancyid="+skyfancyid,
    					
    					
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    						
    						showMessage(jsondata.message,jsondata.type,jsondata.title);

    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findLotusEventByStatus(eventid);
    				    } 
    				});
    				
    				
    	    }
    	     else{
    	      
    	     
    	     
    	      $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateFancy"/>?id='+fancyid,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
        						showMessage(jsondata.message,jsondata.type,jsondata.title);    							
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findLotusEventByStatus(eventid);
    				    } 
    				});
    				
    				
    	    	
    	  }
    }
    
    function refereshFancy(){
    	//eventidtext
    	  /* $("#eventidtext").val(eventid)
    	    $("#eventnametext").val($("#eventname"+eventid).val()) */
    		$("#setresult").append('<div class="loader"></div>') 
	    	
    		
    			 $("#betfaircomp").hide();
    			 $("#betfairseries").hide();
    			 $("#betfairevents").hide();
    	         $("#betfairmarket").hide();
    	         $("#lotusmatch").hide();
    	          $("#fancylist").html('');
    	         $("#fancylist").show();
    		     	
    		    $.when(	 
    		    	$.ajax({
    	   			type:'GET',
    	   			url:'<s:url value="/api/getLotusEventList?eventid="/>'+$("#eventidtext").val()+"&fancyprovider="+$("#fancyprovider").val(),
    	   			success: function(jsondata, textStatus, xhr) {
    	    				   $('#fancylist').html('');
    	    					var source   = $("#fancylotushandle").html();
    	    			    	  var template = Handlebars.compile(source);
    	   				    	  $("#fancylist").append( template({data:jsondata}) );
    	   				  			 $(".loader").fadeOut("slow");
    	   				  		
    	   				  		  $.each(jsondata, function( key, value ) {
    	   				  		  skyFancy(value.eventid,value.ids,$("#fancyprovider").val());
    	   				  	 		
    	   				  		  });
    	    		}
    	    	})).then(function( x ) {
    		    				findLotusEventByStatus($("#eventidtext").val())
    		    				 $(".loader").fadeOut("slow");
    		    				
    		    	     		});;
    	
    	    }
    function skyFancy(eventid,id,fancyprovider){
    	var matchfancy="";
    	var url ="";
    	 if(fancyprovider == "Diamond"){
    		 matchfancy ="Skyexhange";
    		// url ='http://jioexch.in//ldSession?eventid=/'+eventid;
    		//url ='https://www.lotusbook.io/api/member/exchange/event/LIOD/4/'+eventid;
    		 url ='http://161.35.107.2//getSession/'+eventid+'/'+matchfancy;
    		 
    	 }else{
    		 matchfancy ="Diamond";
    		 url ='http://161.35.107.2//getSession/'+eventid+'/'+matchfancy;
    	 }
    	
    	 
    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:url,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					  
					   if(i==0){
						 $("#skyfancy"+id).append('<option value="-1" selected>Select Fancy</option>');
						
					 }
					   $("#skyfancy"+id).append('<option value="'+value.id+'">'+value.name+'</option>');
					   i++;
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			}); 
	    }
    
    </script>
,