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
            <div class="col-lg-6">
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
                            <label for="usr">Application Url:</label>
                            <input type="text" class="form-control" id="appurl" name="appurl">
                          </div>
                    </div>
                    
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Hisab Kitab Type:</label>
                              <select id="hisabkitabtype" class="form-control"><option value="1">Kalyan</option><option value="2">Diamond</option> </select>
                        
                          </div>
                    </div>
                  <!--   <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">City:</label>
                            <input type="text" class="form-control" id="city" name="city">
                          </div>
                    </div> -->
                   
                   <!--  <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Phone:</label>
                            <input type="number" class="form-control" id="contact" name="contact">
                          </div>
                    </div> -->
                    
                  </div>
                  
                 
                  
            </div>
            <div class="col-lg-6">
                <h4 class="mb-3">Account Detail</h4>
                
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group ">
                            <label for="inputState">Account Type</label>
                            <select id="inputaccounttype" class="form-control"> <option value ="5">Sub Admin</option> </select>
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Credit Balance</label><span id="avaltodistribute" style="color:green;"></span>
                            <input type="number" class="form-control" id="creditref" name="creditref">
                          </div>
                    </div>
					</div>
					<div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Exposer Limit:</label>
                            <input type="number" class="form-control" value ="0" id="netexposer" name="netexposer" readonly>
                          </div>
                    </div>
                    
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Application Name:</label>
                            <input type="text" class="form-control" id="appname" name="appname">
                          </div>
                    </div>
                    
                  <%--   <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Bhao Type</label>
                           <select id="bhaotype" name="bhaotype" class="form-control"> 
                            <option selected value="1">1</option>
                             <option value="2">2</option>
                           </select>
                          </div>
                    </div> --%>
                    </div>
					<div class="form-row">
                   <%--  <div class="col-sm-6">
                      <div class="form-group">
                            <label for="number">Mob App</label>
                           <select id="commision" class="form-control"></select>
                          </div>
                    </div> --%>
                    
                    
                   
					</div>
                    
                    <div class="form-row">
                     
                    
                  <%--   <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Commssion:</label>
                           <select id="oddstype" class="form-control">
                           <option value="BetByBet"> BetByBet</option>
                           <option value="MatchMinus&SessionMinus">MatchMinus&SessionMinus</option>
                           </select>
                          </div>
                    </div> --%>
                    </div>
                    
                  
                    
                   </div>
          
            </div>
           </div> 
          <div class="comision-container p-0 pt-2">
              
              <div class="table-box">
                <h4 class="mb-3">Commission Settings</h4>
				<div class="table-responsive">
                <table class="table table-striped">
                    
                    <thead>
                      <tr>
                        <th></th>
                        <th>Match Commssion</th>
                        <th>Session Commssion</th>
                         <th>Casino Commssion</th>
                     </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Upline</td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                      </tr>
                         <tr>
                        <td>Downline</td>
                        <td><div class="form-group "><select id="oddsWinCommsimpple" class="form-control"> </select></div></td>
                        <td> <div class="form-group "><select id="oddslossdiv" class="form-control"> </select></div></td>
                         <td> <div class="form-group "><select id="casinocommssion" class="form-control"> </select></div></td>
                      </tr>
                      
                      
                    
                     
                    </tbody>
                  </table>
              </div>
              </div>
          </div>
          
           <div class="partnership-container p-0 pt-2">
              
              <div class="table-box">
                <h4 class="mb-3">partnerships</h4>
				<div class="table-responsive">
                <table class="table table-striped">
                    
                    <thead>
                      <tr>
                        <th></th>
                        <th>Partnership</th>
                    </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Upline</td>
                        <td></td>
                        <td></td>
                      </tr>
                         <tr>
                        <td>Our</td>
                        <td> <div class="form-group "><select id="partnership" class="form-control"> </select></div></td>
                      </tr>
                      <tr>
                        <td>Downline</td>
                         <td id="downLinePartnership"></td>
                        <td></td>
                      </tr>
                      
                    
                     
                    </tbody>
                  </table>
              </div>
              </div>
          </div>
          
          <div class="partnership-container p-0 pt-2">
              
              <div class="table-box">
                <h4 class="mb-3">Casino partnerships</h4>
				<div class="table-responsive">
                <table class="table table-striped">
                    
                    <thead>
                      <tr>
                        <th></th>
                        <th>Partnership</th>
                    </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Upline</td>
                        <td></td>
                        <td></td>
                      </tr>
                         <tr>
                        <td>Our</td>
                        <td> <div class="form-group "><select id="cpartnership" class="form-control"> </select></div></td>
                      </tr>
                      <tr>
                        <td>Downline</td>
                         <td id="cdownLinePartnership"></td>
                        <td></td>
                      </tr>
                      
                    
                     
                    </tbody>
                  </table>
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
           <a href="javascript: void(0)" onclick="createAdmin()">Create Account</a>
        </div>
       

        </div>
        </form>
       </div>
       
    </div>
    
    <script>
    var type = "";
    var partnerShip =0.0; 
    $(document).ready(function(){
    setAdminPart();
    
     getCommssion();
     $("#userid" ).keyup(function() {
    	 checkUserId($("#userid" ).val())
    	});

     $( "#partnership" ).change(function() {
		 $("#downLinePartnership").html(100-(parseInt($("#partnership").val()))); 
		});

     $( "#cpartnership" ).change(function() {
		 $("#cdownLinePartnership").html(100-(parseInt($("#cpartnership").val()))); 
		});
       $("#addApplicationForm").validate({
			  rules: {
			    
				  userid: {
			      required: true,
			      minlength: 3,
			      alphanumeric: true
			    }, 
			      password: {
				  required: true,
				  minlength: 3
				},
				  repassword: {
				  required: true,
				  minlength: 3,
				  equalTo: "#password"
				},
			      username: {
				  required: true,
				  minlength: 3,
				  alphanumeric: true
				 },
				  inputaccounttype: {
				  required: true,
				  minlength: 3
			    },
			      netexposer: {
			      required: true,
			      minlength: 1
				},
				  creditref: {
				  required: true,
				  minlength: 3
				},
				  appname: {
				  required: true,
				  minlength: 3
				},
				appurl: {
				  required: true,
				  minlength: 3
				},
				parentPasswordAddUser: {
				  required: true,
				  minlength: 3
				}
				
			  },
			     messages: {
			    	 userid: {required:"Please Enter Client Name"},
			    	 password: {required:"Please Enter Password"},
			    	 userid: {required:"Please Enter Client Name"},
			    	 inputaccounttype: {required:"Please Select Account Type"},
		          },
			  errorPlacement: function(label, element) {
					$('<span class="arrow"></span>').insertBefore(element);
					$('<span class="error"></span>').insertAfter(element).append(label)
				}
			}); 
    
  });
        
        
    
    
    
    function createAdmin()
    {
    	var $valid = $("#addApplicationForm").valid();
    		
      	if($valid){ 
      		    
      		     
      		 if($("#userIdExist").val() =='true'){
 		    	 return false;
 		     } else{
 		    	
 	            	var id = new Date().getTime()+"";
 	            	  id = $("#appname").val().substring(0,4)+id.substring(11,13);
 	            
 	            	  var data="";
 	            	  var username = $("#username").val();
 	        	   	  var userid = $("#userid").val();
 	        	   	
 	        	   	  var password = $("#password").val();
 	        	   	  var compercetage = $("#percen").val();
 	        	   	  var parentid ='${user.id}';
 	        	   	  var usertype =5;
 	        	   	  var primarybalance =0;
 	        	      var secondrybanlce =0;
 	        	      var reference ="";
 	        	      var contact ="none";
 	        	      var appcharge ="0";
 	        	      
 	        	      var partnership =$("#partnership").val();
 	        	      var cpartnership =$("#cpartnership").val();
 	        	     
 	        	      var appname =$("#appname").val();
 	        	      var appurl =$("#appurl").val();
 	        	      var adminame = '${user.username}';
 	        	      var bhaotype = "1";   	
 	        	      
 	        	    	  
 	        		  var typeuser  = "simple";
 	        		    
 	        		  var adminpartership =$("#partnership").val();
 	        		 var  adminpartershipc =$("#cpartnership").val();
 	        		      
 	        		  var oddswin= $("#oddsWinCommsimpple").val();
 	        		  var oddsloss = $("#oddsWinCommsimpple").val();
 	        		  var fancywin = $("#oddslossdiv").val();
 	        		  var fancyloss = $("#oddslossdiv").val();
 	        		 var casinocommssion = $("#casinocommssion").val();

 	        		 
 	        		  type ="BetByBet";
 	        		  if(type == 'MatchMinus&SessionMinus'){
 	        		   oddswin= 0;
 	        		   oddsloss = $("#oddsWinCommsimpple").val();
 	        		   fancywin =$("#oddslossdiv").val();
 	        		   fancyloss =$("#oddslossdiv").val();
 	        		  }else if(type == 'BetByBet'){
 	        		   oddswin= 0;
 	        		   oddsloss = $("#oddsWinCommsimpple").val();
 	        		   fancywin =$("#oddslossdiv").val();
 	        		   fancyloss =$("#oddslossdiv").val();
 	        		  }
 	        		    var partnershipratio =$("#partnership").val();
 	        		   var partnershipratioc =$("#cpartnership").val();
 	        		    var adminregistration =$("#adminregistration").val();
 	        		    var parentPassword =$("#parentPasswordAddUser").val();
 	        		    var hisabkitabtype =$("#hisabkitabtype").val();
 	        		
 	                    var  creditref =$("#creditref").val();
 	        		     data = {createfrom:parentid,username:username,password:password,userid:userid,type:typeuser,parentid:parentid,adminpartership:adminpartership,usertype:usertype,
 	        			    	  adminid:parentid,reference:reference,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:partnershipratio,type:type,
 	        		    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
 	        			    	  contact:contact,mobappcharge:appcharge,appurl:appurl,appname:appname,adminname:adminame,bhaotype:bhaotype,isMultiple:$("#mulornot").val(),
 	        			    	  creditRef:creditref,availableBalance :creditref,parentPassword:parentPassword,adminpartershipc:adminpartershipc,partnershipratioc:partnershipratioc,hisabkitabtype:hisabkitabtype,casinocommssion:casinocommssion}

 	        			 
 	        	      	  $.ajax({
 	        					type:'POST',
 	        					url:'<s:url value="/api/masterSignup"/>',
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
      		   
        		
      	}
    	
    	
    }
    
    
    
    
    	function getMasterBalaceById()  {
    	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/getSecondaryBalanceById?id="/>'+'${user.id}',
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					$("#adminbalance").html("<="+jsondata.balance);	
					$("#parentbalance").val(jsondata.balance);	
					
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
	   }
  	
    	function checkUserId(userId)  {
      	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/checkUserId?userId="/>'+userId,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					if(jsondata.type=="error"){
					  $( "#userid-error" ).html('');
					  $('<span id="userid-error" class="error" style="display: inline;">Username already taken</span>').insertAfter( $( "#userid" ) );
					  $("#userIdExist").val('true')
					  }else{
						  $( "#userid-error" ).html('');
						  $("#userIdExist").val('false')
						  
					  }	
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
	   }
  	
    	
  	
  	function clearform(class_name) {
  	  jQuery("."+class_name).find(':input').each(function() {
  	    switch(this.type) {
  	        case 'password':
  	        case 'text':
  	        case 'textarea':
  	        case 'file':
  	       
  	        case 'date':
  	        case 'number':
  	        case 'tel':
  	        case 'email':
  	            jQuery(this).val('');
  	            break;
  	        case 'checkbox':
  	        case 'radio':
  	            this.checked = false;
  	            break;
  	    }
  	  });
  	}
  	
  	function setAdminPart(){
  	
	  	var element = 0;
	  	for(i = 100; i >=element; i--) { 
        $("#partnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
	  	$("#commision").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
		$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
	  	
	}  
  }
    
    	
    	
    	function getCommssion(){    	  	
   		 $.ajax({
					type:'GET',
					url:'<s:url value="/api/commssion?type=Match"/>',
					
				success: function(jsondata, textStatus, xhr) 
				{
					$("#oddsWinCommsimpple").html('');
						 $.each(jsondata, function(index, element) {
				         	$("#oddsWinCommsimpple").append($('<option value="'+element.commssion+'" name = "'+element.commssion+'" > '+element.commssion+' </option>', {
						 }));
				         	$("#casinocommssion").append($('<option value="'+element.commssion+'" name = "'+element.commssion+'" > '+element.commssion+' </option>', {
							 }));
					  });
					}
		         });
   		 
   		 $.ajax({
				type:'GET',
				url:'<s:url value="/api/commssion?type=Fancy"/>',
				
			success: function(jsondata, textStatus, xhr) 
			{
				$("#oddslossdiv").html('');
					 $.each(jsondata, function(index, element) {
			         	$("#oddslossdiv").append($('<option value="'+element.commssion+'" name = "'+element.commssion+'" > '+element.commssion+' </option>', {
					 }));
				  });
				}
	         });
	 }
    	
    </script>
    <input type="hidden" id="parentbalance">
     <input type="hidden" id="userIdExist">