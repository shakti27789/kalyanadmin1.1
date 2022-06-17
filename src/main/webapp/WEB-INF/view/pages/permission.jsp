<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="page-wrapper">	
	<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Manage Permissions</h1>

						<p id="paratop"style="color:white"></p>						
						<div class="filterSection full-width-form">
						
							<ul>
								<li id="master_drop">
									<label id ="userunder">Master</label>
									<select id='master' class = "purp">
									</select>
									</select>
								</li>
								
							</ul>
							<ul>
								<li id="permission_drop">
									<label id ="userunder">Select Permission</label>
									<select id="permission_list" class = "purp">
									</select>
								</li>
							</ul>
							
							<ul>
								<li id="permission_type">
									<label id ="userunder">Select True/False</label>
									<select id="tftype" class = "purp">
									 <option value="true" >true</option>
									<option value="false" >false</option>
									</select>
								</li>
							</ul>
								<li>
									<label></label>
									<button type="button" class="btn btn-info blue-btn btn-sm" onclick ="updateStatus()">Update Permission</button> 
								</li>							
						</div>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div> 
            </div>    
            
            <script>
            var Header = "";
		var value;
		var total = 0;
            $(document).ready(function(){
            if(${user.usertype} == 4){
            value = 5;
            Header = "Select Subadmin";
            $("#userunder").html("Sub admin");
            getPermmisionList();
            }
            
            	
                	masterList();            		
            		$("#master_drop").show();
            	
            	
            	
            	$("#permission_list").change(function(){
                	
    				getStatus();
                	});
            	
            	
            
            });
            
            function masterList(){
            
            
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/user/${user.id}/"/>'+value+"?active=true",
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status==200){
        					
        					var obj = jsondata;
        					$("#master").html('');
        					$("#master").append($('<option> <a href="#" >'+Header+'</a> </option>'));
  						  for(var i = 0;i<obj.data.length;i++) {

  			             $("#master").append($('<option id = "'+obj.data[i].id+'" value="'+obj.data[i].id+'" name = "'+obj.data[i].username+'"  > '+obj.data[i].username+' </option>', { 
  			            }));    			            			       
          			  }
        			}
        		}
        	});
           }

           
            function getStatus(){
            	var id = $("#master option:selected").attr('value');
            	var per = $("#permission_list option:selected").attr('value');
            	$("#masterid").val(id);
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/getPermissionStatus/"/>'+id+'/'+per,
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status==200){
        					
        					$("#tftype").val(jsondata.status).change();
	        			}
    	    		}
        		});
        	   }
            
            function getPermmisionList(){
                //alert(sportid);                
                $.ajax({
                					type:'GET',
                					url:'<s:url value="api/getPermissionList"/>',
                					contentType:"application/json; charset=utf-8",
                					success: function(jsondata, textStatus, xhr) {
            				
            					    
            					$("#permission_list").append($('<option> <a href="#" >Select Permission</a> </option>'));
            						  $.each(jsondata, function(index, element) {
            					 
            			             $("#permission_list").append($('<option id = "'+element.permission+'" value="'+element.permission+'" name = "'+element.permission+'" > '+element.permission+' </option>', {
            			             			                
            			            }));
            			            			            			       
                    			});
            				    	 
            					
            					
            				
            						}
                				});
                
                }
            
            
            function updateStatus(){
            	var id = $("#master option:selected").attr('value');
            	var per = $("#permission_list option:selected").attr('value');
            	var status = $("#tftype option:selected").attr('value');
            	var sts=true;
            	if(status =="false"){
            		sts=false;
            	}
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/updatePermissionStatus/"/>'+id+'/'+per+'/'+sts,
        			success: function(jsondata, textStatus, xhr) {
        				showMessage(jsondata.message,jsondata.type,jsondata.title);
    	    		}
        		});
        	   }

            </script>    
            
            <input type="hidden" id="masterid"/>
                        <input type="hidden" id="dealerid"/> 