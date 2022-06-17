<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

    
     <script id="matcheshandler" type="text/x-handlebars-template">

             	<table class="table common-table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Match Name</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{eventid}}<input type='hidden' id ='eventid{{eventid}}' value='{{eventid}}'></td>
                   	   <td>{{matchname}}<input type='hidden' id ='eventname{{eventid}}' value='{{matchname}}'></td>						
						<td>
							<input type="checkbox" id="addMarketcheckbox{{eventid}}"  onclick='addMarket({{marketid}},{{eventid}})'>
							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=getFancyList("{{eventid}}")>Fancy List</a> 
						</td>
                   </tr>
 			{{/each}}          
                </tbody>
              </table>
            
    </script>
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Match List</h1>						
						<div id="activematch" class="menu-content" ></div>						
						<div class="panel-body text-center" id="noActiveMatch">
								<h1>No Matches Available</h1>
						</div>
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
    	getMatchesList();
      });
    
    function findMarketByStatus(){
    	
		 $.ajax({
				type:'GET',
				url:'<s:url value="api/findMarketByStatus/"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						

						 $.each(jsondata, function(key,value){
							 $("#addMarketcheckbox"+value.eventid).prop('checked', true);
						 });
					}
					
				}
			}
		});}
		 
	function getMatchesList()
    {	 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getMatchesList"/>',
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							$("#noActiveMatch").hide();
	    							$("#activematch").show();
	    							$("#activematch").html('');
	    							  var source   = $("#matcheshandler").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#activematch").append( template({data:jsondata}) );
	    					    	  findMarketByStatus(); 	
	    						}else{
	    							$("#noActiveMatch").show();
	    							$("#activematch").hide();
	    						}
	    						
	    					}
	    				}
	    			});
    }

	
    function addMarket(marketid,eventid)
    {		
    	     isChecked =  $("#addMarketcheckbox"+eventid).is(":checked");
    	    
    	     
    	     if(isChecked){    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Match!',
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
    					url:'<s:url value="api/addmatch?marketid="/>'+marketid,
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
    				 $("#addMarketcheckbox"+eventid).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	     	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Match!',
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
    					url:'<s:url value="api/updatematch?marketid="/>'+marketid,
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
    				 $("#addMarketcheckbox"+eventid).prop("checked", true);
    				 }
    				});
    	    	 }
   	 
    }
    </script>
    
         <jsp:include page="masterFancyList.jsp" /> 