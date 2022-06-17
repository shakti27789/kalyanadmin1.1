<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <script id="sportshandle" type="text/x-handlebars-template">

	

 <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Id</th>
                       <th>Series Name</th>
                       <th class="action">Action</th>
                    </tr>
               </thead>
			  {{#each data}}
               <tbody>
                   <tr id="{{eventType.id}}"> 
                       <td>{{eventType.id}} <input type='hidden' id ='sportid{{eventType.id}}' value='{{eventType.id}}'></td>
                       <td>{{eventType.name}} <input type='hidden' id ='sportname{{eventType.id}}' value='{{eventType.name}}'</td>
                       <td class="action">
                      <input type="checkbox" class="sportidcheckbox mr-3" id="sportidcheckbox{{eventType.id}}" onclick='addSport({{eventType.id}},"Betfair")'>
					   <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=serirsBySport({{eventType.id}})>casino List</a> 
					   </td>
                      
                      
                   </tr>
             {{/each}}      
                </tbody>
 </table>
    </script>
    
    
      <script id="virtualcasinohandle" type="text/x-handlebars-template">

	

 <table id="virtualcasinotable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Id</th>
                       <th>Series Name</th>
                       <th class="action">Action</th>
                    </tr>
               </thead>
			  {{#each data}}
               <tbody>
                   <tr id="{{eventType.id}}"> 
                       <td>{{eventType.id}} <input type='hidden' id ='sportid{{eventType.id}}' value='{{eventType.id}}'></td>
                       <td>{{eventType.name}} <input type='hidden' id ='sportname{{eventType.id}}' value='{{eventType.name}}'</td>
                       <td class="action">
                      <input type="checkbox" class="sportidcheckbox mr-3" id="sportidcheckbox{{eventType.id}}" onclick='addSport({{eventType.id}},"VCasino")'>
					   <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=getCasinoEvent({{eventType.id}})>Casino List</a> 
					   </td>
                      
                      
                   </tr>
             {{/each}}      
                </tbody>
 </table>
    </script>
    
  
   
		
<input type ="hidden" value="" id="sportidtext">
<input type ="hidden" value="" id="seriesidtext">
<input type ="hidden" value="" id="eventidtext">
<input type ="hidden" value="" id="fancyprovider">
<input type ="hidden" value="" id="eventnametext">
<input type ="hidden" value="" id="opendate">
           <div class="cliennt-container">
       

      
            
          
       <div class="account-container client-record pt-4">

         <div class="account-record account-stment">
           <div class="acont-box">
             <span class="acont-left ">
               <div class="dash-btn"> <a href="javascript:void(0)" onclick="sportList()">Betfair</a></div>
             </span>
          
          <span class="acont-left ">
               <div class="dash-btn"> <a href="javascript:void(0)" onclick="vCasinoList()">Virtual Casino</a></div>
             </span>
             
             
         </div>
         
           <div class="account-table bethistory table-responsive" id="betfairsport"></div>
           <div id="betfairsport" class="account-table bethistory table-responsive"></div>
		   <div id="betfairseries" class="account-table bethistory table-responsive"  style="display:none;"></div>
		   <div id="betfairevents" class="account-table bethistory table-responsive"  style="display:none;"> </div>
		   <div id="betfairmarket" class="account-table bethistory table-responsive"  style="display:none;"></div>
		   <div id="lotusmatch" class="account-table bethistory table-responsive"  style="display:none;"></div>
		  
          

       </div>
       </div>
       
  


</div>
    
    <script>
    
    $(document).ready(function(){
    	sportList();
      });
    
    
   
    
    function sportList()
    { 
    	$("#betfairsport").show();
 	    $("#betfairseries").hide();
 	    $("#betfairsport").html('');
 	    
 	 	$("#betfairsport").append('<div class="loader"></div>') 
   	 	$.when(
   	 $.ajax({
				type:'GET',
				url:'<s:url value="api/getSport"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						 var arr = $.parseJSON(jsondata);
					 	  //var arr = jsondata;
                           console.log(arr)
						  var source   = $("#sportshandle").html();
				    	  var template = Handlebars.compile(source);
				    	  $("#betfairsport").append( template({data:arr}) );
				    	 
					}
					
				}
			},
	 		complete: function(jsondata,textStatus,xhr) {
	 			 $(".loader").fadeOut("slow");
		 	    } 
		})).then(function( x ) {
   			checkIfSportAdded()
     		});
 	 	
    }

    

    
 
    function vCasinoList()
    { 
    	$("#betfairsport").show();
 	    $("#betfairseries").hide();
 	    $("#betfairsport").html('');
 	   $("#betfairevents").hide();
 	   
 	 	$("#page-wrapper").append('<div class="loader"></div>') 
   	 	$.when(
   	 $.ajax({
				type:'GET',
				url:'<s:url value="api/listEventTypesCasino"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						
						  var source   = $("#virtualcasinohandle").html();
				    	  var template = Handlebars.compile(source);
				    	  $("#betfairsport").append( template({data:jsondata}) );
				    	  $(".loader").fadeOut("slow");
					}
					
				}
			}
		})).then(function( x ) {
   			checkIfSportAdded()
     		});
 	 	
    }
  
    
    function addSport(id,type){
  
     isChecked =  $("#sportidcheckbox"+id).is(":checked");
    	     $("#sportidtext").val($("#sportid"+id).val())
    	     if(isChecked){
    	    	//alert(id);
    	    	 var data={name:$("#sportname"+id).val(),sportId:parseInt($("#sportid"+id).val()),marketCount:parseInt($("#marketcount"+id).val()),status:true,adminid:'${user.id}',type:type}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Sport!',
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
    					url:'<s:url value="api/addSport"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						 $(".loader").fadeOut("slow");
    				    } 
    				}); 
    				
    				 } else{
    				   $("#sportidcheckbox"+id).prop("checked", false);
    				 }
    				}); 
    	    }
    	     else{
    	    	 var data={name:$("#sportname"+id).val(),sportId:parseInt($("#sportid"+id).val()),marketCount:parseInt($("#marketcount"+id).val()),status:false,subadminid:'${user.id}',type:type}
 	    		
 	    		 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Sport!',
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
    					type:'PUT',
    					url:'<s:url value="api/updateSport"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
//     						 $U-[I76V]=(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				 } else{
    				 $("#sportidcheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
   	 
    }
    
    
     function checkIfSportAdded(){
    		
			 $.ajax({
					type:'GET',
					url:'<s:url value="api/checkIfSportAdded/"/>',
					success: function(jsondata, textStatus, xhr) {
					{
						if(xhr.status == 200)
						{     
							
							 $.each(jsondata, function(key,value){
								 console.log(value.sportId)
								 $('#sportidcheckbox'+value.sportId).prop('checked', true);
								 $('#lotusportidcheckbox'+value.sportId).prop('checked', true);
								 
							 });
						}
						
					}
				}
			});
    
   
    }
    
   
    
    </script>
    
    
    
    
        
<script>

</script>
  
    
    <jsp:include page="betfairSeries.jsp" />  
    <jsp:include page="betfairEvent.jsp" />  
    <jsp:include page="betfairMarket.jsp" />  
    <jsp:include page="lotusEvent.jsp" />  
     <jsp:include page="fancyList.jsp" /> 