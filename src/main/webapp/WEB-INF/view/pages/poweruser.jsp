 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


    
    
      <div class="cliennt-container">
       <div class="body-section border-box">
        <form id="addApplicationForm">
        <div class="client-Inform">
            <h2 class="mt-2 mb-4">Add Account</h2>
           <div class="personal-Information">
           <div class="row">
            <div class="col-md-6">
                <h4>Personal Detail</h4>
               
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Login Id:</label>
                            <input type="text" class="form-control" id="userid" name="userid">
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">User Password:</label>
                            <input type="password" class="form-control" id="password" name="password">
                          </div>
                    </div>
                   </div>
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Retype Password:</label>
                            <input type="password" class="form-control" id="re-password" name="repassword">
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Party Name:</label>
                            <input type="text" class="form-control" id="username" name="username">
                          </div>
                    </div>
                   </div>
                   <div class="form-row">
                   
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">City:</label>
                            <input type="text" class="form-control" id="city" name="city">
                          </div>
                    </div>
                   
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Phone:</label>
                            <input type="number" class="form-control" id="contact" name="contact">
                          </div>
                    </div>
                    
                  </div>
                  
                 
                  
            </div>
            
          
            </div>
           </div> 
        
     
    
          

        <div class="master-box pl-0 pr-0">
                   <div class="master-form">
                    <div class="form-group">
                        <label for="pwd">Master Password:</label>
                        <input type="password" class="form-control" id="parentPasswordAddUser" name="parentPasswordAddUser">
                      </div>
                   </div>
        </div>

        <div class="create-account pl-0 pr-0">
           <a href="javascript: void(0)" onclick="addPowerUser()">Create Account</a>
        </div>
       

        </div>
        </form>
       </div>
       
    </div>
    
    <script>
    var type = "";
    var partnerShip =0.0; 
    $(document).ready(function(){
   
     
    });  
        
    
    
    
    function addPowerUser()
    {
    	   
      		
 	            	
 	        		     data = {userid:$("#userid").val(),password:$("#password").val()}

 	        			
 	        	      	  $.ajax({
 	        					type:'POST',
 	        					url:'<s:url value="/api/addPowerUser"/>',
 	        					data: JSON.stringify(data),
 	        					dataType: "json",
 	        					contentType:"application/json; charset=utf-8",
 	        					success: function(jsondata, textStatus, xhr) 
 	        					{
 	        						showMessage(jsondata.message,jsondata.type,jsondata.title);
 	        						location.replace("<s:url value="/userList"/>");
 	        					},
 	        					complete: function(jsondata,textStatus,xhr) {
 	        						
 	        						const obj = JSON.parse(jsondata.responseText);
 	        						showMessage(obj.message,obj.type,obj.title);
 	        						

 	        				    }
 	        			});
 	        	    
 	        		  
 	        
        
    	
    	
    }
    
    
    
     	
    </script>
    <input type="hidden" id="parentbalance">
     <input type="hidden" id="userIdExist">