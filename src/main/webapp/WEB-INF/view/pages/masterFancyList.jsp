<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="fancylotushandle" type="text/x-handlebars-template">

     <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='getMatchesList()'>Back</a>
             	<table class="table common-table">
                <thead>
                  <tr>
                  <th>Fancy Id</th>
                    <th>Fancy Name</th>
                    <th>Status</th>
                    <th>Action</th>
                   
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                  
                  	   <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'</td>
                   	   <td>{{name}} <input type='hidden' id ="fancyname{{id}}" value='{{name}}'</td>
                   	   <td>{{status}} <input type='hidden' id ="fancystatus{{id}}" value='{{status}}'</td>
                    	<td>
							<input type="checkbox" id="addlotusFancy{{id}}"  onclick='saveLotusFancy("{{id}}")'>
						
						</td>
						
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
            
    </script>
  
    <script>
    
	function getFancyList(eventid)
    {	 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getFancyByAppid?eventid="/>'+eventid,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							$("#noActiveMatch").hide();
	    							$("#activematch").show();
	    							$("#activematch").html('');
	    							  var source   = $("#fancylotushandle").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#activematch").append( template({data:jsondata}) );
	    					    	  findFancyByStatus(); 	
	    						}else{
	    							$("#noActiveMatch").show();
	    							$("#activematch").hide();
	    						}
	    						
	    					}
	    				}
	    			});
    }
	
    function saveLotusFancy(id,eventid)
    {		
    	     isChecked =  $("#addlotusFancy"+id).is(":checked");
    	   
    	     if(isChecked){	
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="api/savesupermasterFancy"/>?id='+id,
    					
    					
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findFancyByStatus();
    				    } 
    				});
    				
    				
    	    }
    	     else{
    	     
    	     
    	     
    	      $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateSupermasterFancy"/>?id='+id+"&supermasterid="+"${user.id}",
    					
    					
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findFancyByStatus();
    				    } 
    				});
    				
    				
    	    	
    	  }
   	 
    }
  
    function findFancyByStatus(){
    	
		 $.ajax({
				type:'GET',
				url:'<s:url value="api/findSuperMasterFancyByStatus/"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						

						 $.each(jsondata, function(key,value){
							 $("#addlotusFancy"+value.id).prop('checked', true);
						 });
					}
					
				}
			}
		});}
    </script>