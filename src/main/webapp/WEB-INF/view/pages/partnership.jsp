<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="page-wrapper">	
	<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Manage Partnership</h1>

						<p id="paratop"></p>						
						<div class="filterSection">
						
							<ul>
								<li id="master_drop">
									<label id ="userunder">Master</label>
									
								
									<select id='childtype' class = "purp">
									
									 <s:if test = "${user.usertype==4}">
									  <option value="-1">select downLine</option>
									 <option value="5">Admin</option>
									</s:if>
									
									 <s:if test = "${user.usertype==5}">
									  <option value="-1">select downLine</option>
									 <option value="0">Sub Admin</option>
									  <option value="1">Super Master</option>
									   <option value="2">Master </option>
									</s:if>
									
									 <s:if test = "${user.usertype==0}">
									 <option value="-1">select</option>
									  <option value="1">Super Master</option>
									   <option value="2">Master</option>
									</s:if>
									
									 <s:if test = "${user.usertype==1}">
									 <option value="-1">select</option>
									   <option value="2">Master</option>
									</s:if>
									
									</select>
									
									<select id='master' class = "purp">
									</select>
								
								</li>
								
							</ul>
							<ul>
								<li><label>Self Partnership</label><input type="text" id="adminPartnership" placeholder="" readonly ></li>
								<li><label>Child Partnership</label><input type="text" id="masterPartnership" placeholder="" ></li>
								<li><label>Existing Partnership</label> :<input type="text" readonly=true id="selfparnership"  ></li>
								<li><label>DownLine Max Partnership</label><input type="text" readonly=true id="downlinemaxpartnership"  ></li>
								<li><input type="hidden" id="adminPartnershiphidden" placeholder="Admin Partnership" ></li>
								<li><input type="hidden" id="masterPartnershiphidden" placeholder="Subadmin Partnership" ></li>
								
							</ul>
							
								<li>
									<label></label>
<button type="button" class="btn btn-info blue-btn btn-sm" id="calculaterpartnership" >Check</button> 								
<button type="button" class="btn btn-info blue-btn btn-sm" disabled id="changepartnership" onclick ="changePartnership()">Change Partnership</button> 
										
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
            	document.getElementById('adminPartnership').value = "";
            	$("#masterPartnership").change(function(){
            		var val1 =$("#masterPartnershiphidden").val()-$("#masterPartnership").val();
            		var val2 = parseInt($("#adminPartnership").val())+parseInt(val1);
            		console.log(val2)
            	//	$("#adminPartnership").val(val2)
            		//document.getElementById('adminPartnership').value = "";
                	
            		minusPartnership();
            		
            		$("#masterPartnership").click(function(){
            			$("#changepartnership").attr("disabled",true);
            			//alert()
            		});
            	});
            	
            	
            if(${user.usertype} == 4){
            value = 5;
            
            $("#userunder").html("Admin");
            $("#paratop").html("Change Partnership according to Admin :: First Select Admin");
            }else if(${user.usertype} == 5){
            $("#adminPartnership").attr("placeholder", "Admin Partnership");
            $("#masterPartnership").attr("placeholder", "Subadmin Partnership");
            value = 0;
           
            $("#userunder").html("Sub Admin");
            $("#paratop").html("Change Partnership according to Sub Admin :: First Select Sub Admin");
            
            }else if(${user.usertype} == 0){
            $("#adminPartnership").attr("placeholder", "Sub Admin Partnership");
            $("#masterPartnership").attr("placeholder", "Super Master Partnership");
            value = 1;
           
            $("#userunder").html("Super Master");
            $("#paratop").html("Change Partnership according to Super Master :: First Select Super Master");
            }else if(${user.usertype} == 1){
            $("#adminPartnership").attr("placeholder", "Super Master Partnership");
            $("#masterPartnership").attr("placeholder", "Master Partnership");
            value = 2;
           
            $("#userunder").html("Master");
            $("#paratop").html("Change Partnership according to Master :: First Select Master");
            }
                      		
            $("#master_drop").show();
            	
            	
            $("#master").change(function(){
            	
			getPartnership();
           });
            	 
               $("#childtype").change(function(){
                getChild();
              });  	 
            	
            });
            
            function getChild(){
            	var childtype =  $("#childtype").val();
            	if(childtype == 0){
            		Header ="Select Sub Admin"
            	}else if(childtype == 1){
            		Header ="Select Super  Master"
            	}
            	else if(childtype == 2){
            		Header =" Select Master"
            	}
            	else if(childtype == -1){
            		Header =""
            	}
            	else if(childtype == 5){
            		Header ="Select Admin"
            	}
            	
            	
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/getChild/"/>'+childtype+"/Dn",
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

           
            function getPartnership(){
            	var id = $("#master option:selected").attr('value');
            	$("#masterid").val(id);
            	var childtype =$("#childtype").val();
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/getUserDetail/"/>'+id+"/"+childtype,
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status==200){
        					var obj = jsondata;
        				
        					 if(${user.usertype} == 4){
        					 $("#adminPartnership").val(obj.adminpartership);
        					 $("#masterPartnership").val(obj.subadminpartnership);
        					 $("#adminPartnershiphidden").val(obj.adminpartership);
        					 $("#masterPartnershiphidden").val(obj.subadminpartnership);
        					total = obj.adminpartership + obj.subadminpartnership;
        					 }
        					 
        					 else if(${user.usertype} == 5 && childtype == 0){
               				  $("#adminPartnership").val(obj.subadminpartnership);
               				  $("#masterPartnership").val(obj.supermastepartnership);
               					total = obj.subadminpartnership + obj.supermasterpartnership;
               					$("#adminPartnershiphidden").val(obj.subadminpartnership);
           					 	$("#masterPartnershiphidden").val(obj.supermastepartnership);
               				  }
        					 else if(${user.usertype} == 5 && childtype == 1){
            				  $("#adminPartnership").val(obj.subadminpartnership);
            				  $("#masterPartnership").val(obj.masterpartership);
            				  $("#adminPartnershiphidden").val(obj.subadminpartnership);
         					  $("#masterPartnershiphidden").val(obj.masterpartership);
            				  
            					total = obj.subadminpartnership + obj.masterpartership;
            				  }
        					 
        					 else if(${user.usertype} == 5 && childtype == 2){
               				  $("#adminPartnership").val(obj.subadminpartnership);
               				  $("#masterPartnership").val(obj.delearpartership);
               				  $("#adminPartnershiphidden").val(obj.subadminpartnership);
       					      $("#masterPartnershiphidden").val(obj.delearpartership);
               					total = obj.subadminpartnership + obj.delearpartership;
               				  }
        					 else if(${user.usertype} == 0 && childtype == 1){
        					 $("#adminPartnership").val(obj.supermastepartnership);
        					$("#masterPartnership").val(obj.masterpartership);
        					$("#adminPartnershiphidden").val(obj.supermastepartnership);
     					    $("#masterPartnershiphidden").val(obj.masterpartership);
        					
        					total = obj.supermastepartnership + obj.masterpartership;
        					 }
        					 else if(${user.usertype} == 0 && childtype == 2){
            					 $("#adminPartnership").val(obj.supermastepartnership);
            					$("#masterPartnership").val(obj.delearpartership);
            					$("#adminPartnershiphidden").val(obj.supermastepartnership);
         					    $("#masterPartnershiphidden").val(obj.delearpartership);
            					
            					total = obj.supermastepartnership + obj.delearpartership;
            					 }
        					 else if(${user.usertype} == 1){
        					 $("#adminPartnership").val(obj.masterpartership);
        					$("#masterPartnership").val(obj.delearpartership);
        					$("#adminPartnershiphidden").val(obj.masterpartership);
     					    $("#masterPartnershiphidden").val(obj.delearpartership);
        					total = obj.masterpartership + obj.delearpartership;
        					 }
        					 getMaxPartShip();
	        			}
    	    		}
        		});
        	   }
            
            function changePartnership(){
            	
            	
            	
            	swal({
   	     	     title: 'Verify Password',
   	     	      html: '<input type="text" id="verifypassword" placeholder="Enter Password" class="form-control"> ',
   	     	      showCancelButton: true,
   	     	      confirmButtonText: 'Save',
   	     	      closeOnConfirm: false,
   	     	      allowOutsideClick: false
   	     	    }).then(function(isConfirm) {
   	     	      if (isConfirm) {
   	     	    	  var password =$("#verifypassword").val()
   	     	          
   	     	    	 if($.trim(password).length>0) {
   	  	           $.ajax({
   						type:'GET',
   						url:'<s:url value="/api/verifyPassword"/>?userid=${user.userid}&password='+$.trim(password),
   						success: function(jsondata, textStatus, xhr) 
   						{
   							if(xhr.status==200){
   							
   								
   						    	    
   						    	   	var childtype =$("#childtype").val();
   					            	var data={            
   					            			"master":$("#masterid").val(),
   					                		"adminPartnership":$("#adminPartnership").val(),
   					                		"masterPartnership":$("#masterPartnership").val(),
   					                		"total":total,
   					                		"parenttype":${user.usertype},
   					                		"childtype":childtype,
   					                		"downlinemaxpartnership":$("#downlinemaxpartnership").val()
   					                		};
   					              	$("#page-wrapper").append('<div class="loader"></div>');
   					              	$.ajax({
   											type:'POST',
   											url:'<s:url value="/api/updatePartnership"/>',
   											data: JSON.stringify(data),
   											dataType: "json",
   											contentType:"application/json; charset=utf-8",
   											success: function(jsondata, textStatus, xhr) {
   												showMessage(jsondata.message,jsondata.type,jsondata.title);
   												 $(".loader").fadeOut("slow");
   												location.reload();
   											},
   											complete: function(jsondata,textStatus,xhr) {
   												showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
   												 $(".loader").fadeOut("slow");
   										    } 
   										});
   						    	      
   						    	
   								
   								
   							}else{
   								showMessage("Please Enter Correct Password","error","Oops...")
   							}
   							   						
   						},
   						complete: function(jsondata,textStatus,xhr) {
   							
   					    }
   				});
   	     	    	  
   	   			
   	   			}  
   	   			else
   			     	{
   			     	showMessage("Kindly enter new Password","error","Oops...")
   			     	}
   	     	    	  
   	     	     }
   	     	    });
   			
   			
   			
            	
            	
            	
             
            }
            
            function getMaxPartShip(){
            	
            	var childtype =$("#childtype").val();
            	var data={            
            			"master":$("#masterid").val(),
                		"adminPartnership":$("#adminPartnership").val(),
                		"masterPartnership":$("#masterPartnership").val(),
                		"total":total,
                		"parenttype":${user.usertype},
                		"childtype":childtype
                		};
            	
			 	 $.ajax({
						type:'POST',
						url:'<s:url value="/api/getMaxPartShip"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							$("#downlinemaxpartnership").val(jsondata.partnership);
							$("#selfparnership").val(jsondata.p1);
							console.log(jsondata.p1)
							//$("#adminPartnership")
						}
					}); 

            }
            
		function minusPartnership(){
            	
            	var data={            
            		    "adminPartnership":$("#adminPartnership").val(),
                		"masterPartnership":$("#masterPartnership").val(),
                		"adminPartnershiphidden":$("#adminPartnershiphidden").val(),
                		"masterPartnershiphidden":$("#masterPartnershiphidden").val(),
                		"downlinemaxpartnership":$("#downlinemaxpartnership").val(),
                		"master":$("#masterid").val()
                		};
            	
			 	 $.ajax({
						type:'POST',
						url:'<s:url value="/api/minusPartnership"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
						
							if(jsondata.max == "false"){
								showMessage("Please Decrease Downline Partnership","error","Oops...");
								$("#changepartnership").hide();
							}else if(jsondata.max == "minus"){
								showMessage("Partnership can not be in minus","error","Oops...");
								$("#changepartnership").hide();
							}
							
							else{
								$("#calculaterpartnership").click(function(){
									$("#changepartnership").show();
									$("#changepartnership").removeAttr("disabled");
									
				            	});
								
								$("#adminPartnership").val(jsondata.adminPartnership)
								
								
							}
						}
					}); 

            }

            </script>    
            
            <input type="hidden" id="masterid"/>
                        <input type="hidden" id="dealerid"/> 