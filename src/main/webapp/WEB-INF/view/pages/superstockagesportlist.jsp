<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <script id="sportshandle" type="text/x-handlebars-template">

     
             	<table class="customtable">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Market Count</th>
                 	<th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                  
                  <td>{{sportId}} <input type='hidden' id ='sportid{{sportId}}' value='{{sportId}}'></td>
                   	   <td>{{name}} <input type='hidden' id ='sportname{{sportId}}' value='{{name}}'</td>
                    	<td>{{marketCount}} <input type='hidden' id ='marketcount{{sportId}}' value='{{marketCount}}'</td>
						<td>
                    
							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=activeMarketBySubAdmin({{sportId}})>Match List</a> 
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
            
    </script>
    
    
    
      <script id="subadminmatchhandle" type="text/x-handlebars-template">

                <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='sportList()()'>Back</a>
             	
				<table class="customtable">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Match Name</th>
                    <th>Market Name</th>
                 	 <th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{marketid}} <input type='hidden' id ='matchname{{marketid}}' value='{{marketid}}'></td>
                   	   <td>{{matchname}} <input type='hidden' id ='marketname{{eventid}}' value='{{matchname}}'</td>
                    	<td>{{marketname}} <input type='hidden' id ='marketname{{eventid}}' value='{{marketname}}'</td>
                      <td>

                        <input type="checkbox" id="addMarketcheckbox{{id}}"  onclick='addMarketBySuperstockage("{{marketid}}","{{id}}","{{sportid}}")'>
						<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=activeFancyBySubadmin({{eventid}})>Fancy List</a> 
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
          
    </script>
    
     <script id="superstockagefancyhandle" type="text/x-handlebars-template">

                <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='activeMarketBySubAdmin()'>Back</a>
             	
				<table class="customtable">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Match Name</th>
                    <th>Market Name</th>
                 	 <th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{fancyid}} <input type='hidden' id ='matchname{{marketid}}' value='{{marketid}}'></td>
                   	   <td>{{name}} <input type='hidden' id ='marketname{{eventid}}' value='{{matchname}}'</td>
                    <td>

                        <input type="checkbox" id="addFancycheckbox{{id}}"  onclick='updateFancyBySuperStockage("{{fancyid}}","{{id}}")'>
						<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=activeFancyBySubadmin({{eventid}})>Fancy List</a> 
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
          
    </script>
    
  
   
<div id="page-wrapper">			
<input type ="hidden" value="" id="sportidtext">
<input type ="hidden" value="" id="seriesidtext">
<input type ="hidden" value="" id="eventidtext">
<input type ="hidden" value="" id="eventnametext">
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">All Sports</h1>
						<section class="menu-content">						
							
							
							<div class="tab-content">
																
							  <div id="betfair" class="tab-pane fade in active">
							    <div id="betfairsport"></div>
							    <div id="subadminatchlist" class=""  style="display:none;"></div>
							     <div id="supestockagefancylist" class=""  style="display:none;"></div>
							   
							 </div>
							  
							 
							</div>
						
							
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
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
 	   
   	 	$.when(
   	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/activeSportByAdmin"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					console.log(jsondata)
					if(xhr.status == 200)
					{     
						 // var arr = $.parseJSON(jsondata);
						  var source   = $("#sportshandle").html();
				    	  var template = Handlebars.compile(source);
				    	  $("#betfairsport").append( template({data:jsondata}) );
				    	 
					}
					
				}
			}
		})).then(function( x ) {
   			checkIfSportAdded()
     		});
    }
    
    
 
    function activeMarketBySubAdmin(sportid){
    	$("#betfairsport").hide();
    	$("#subadminatchlist").html('');
    	$("#subadminatchlist").show();
    	$("#supestockagefancylist").hide();
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/findMarketByStatus/"/>',
			success: function(jsondata, textStatus, xhr) {
			{
				if(xhr.status == 200)
				{     
					
					 var source   = $("#subadminmatchhandle").html();
				   	  	 var template = Handlebars.compile(source);
				   	     $("#subadminatchlist").append( template({data:jsondata}) );
				}
				
			}
		}
	}).then(function( x ) {
		findMarketByStatusAndSuperStockage()
		});
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
    
   
     function addMarketBySuperstockage(marketid,id,sportid){
    	 isChecked =  $("#addMarketcheckbox"+id).is(":checked");
 	    
		 var isunmatched= false;
		 if($("#isUnmatched"+id).is(":checked")){
			 isunmatched = true;
		 }
		 
	     if(isChecked){
	    	
	    	 var data={marketid:marketid,sportid:sportid}
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
	    	 $.ajax({
					type:'POST',
					url:'<s:url value="api/addMarketBySuperStockage"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
					
						showMessage(jsondata.message,jsondata.type,jsondata.title);
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						findMarketByStatusAndSubadminid();
				    } 
				});
				
				 } else{
				 $("#addMarketcheckbox"+id).prop("checked", false);
				 }
				});
	    }
	     else{
	    	 
	    	 var data={marketid:marketid}
	    	
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
	    	 $.ajax({
					type:'PUT',
					url:'<s:url value="api/updateMarketBySuperStockage"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
					
						showMessage(jsondata.message,jsondata.type,jsondata.title);
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						findMarketByStatusAndSuperStockage();
				    } 
				});
				
				} else{
				 $("#addMarketcheckbox"+id).prop("checked", true);
				 }
				});
	    	 }
	 
     }
    
     
     function findMarketByStatusAndSuperStockage(){
     	
		 $.ajax({
				type:'GET',
				url:'<s:url value="api/findMarketByStatusAndSuperStockage/"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						
						 $.each(jsondata, function(key,value){
							
							 $('#addMarketcheckbox'+value.id).prop('checked', true);
							 
						 });
					}
					
				}
			}
		});
  
}
     
     function activeFancyBySubadmin(eventid){
    	 $("#subadminatchlist").hide();
    	 $("#supestockagefancylist").html('');
     	 $("#supestockagefancylist").show();
    	
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/findFancyByEventId?eventid="/>'+eventid,
			success: function(jsondata, textStatus, xhr) {
			{
				if(xhr.status == 200)
				{     
					
					console.log(jsondata)
					 var source   = $("#superstockagefancyhandle").html();
			   	  	 var template = Handlebars.compile(source);
			   	     $("#supestockagefancylist").append( template({data:jsondata}) );
				   	     
				}
				
			}
		}
	}).then(function( x ) {
      findFancyByStatusAndSuperStockage(eventid)
 		});
     }
     
     
     function findFancyByStatusAndSuperStockage(eventid){
      	
		 $.ajax({
				type:'GET',
				url:'<s:url value="api/findFancyByStatusAndSuperStockage?eventid="/>'+eventid,
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						
						 $.each(jsondata, function(key,value){
							
							 $('#addFancycheckbox'+value.id).prop('checked', true);
							 
						 });
					}
					
				}
			}
		});
  
}
     
     function updateFancyBySuperStockage(fancyid,id){
      	 isChecked =  $("#addFanycheckbox"+id).is(":checked");
    	    
    		
    	     if(isChecked){
    	    	
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
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateFancyBySuperStockage"/>?fancyid='+fancyid,
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
    				 $("#addFanycheckbox"+id).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	 
    	    	 var data={fancyid:fancyid}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Fancy!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 	
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateFancyBySuperStockage"/>?fancyid='+fancyid,
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
    				 		$("#addFanycheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
    	 
       }
     
     
    </script>
    
    
    
    
        

    
 