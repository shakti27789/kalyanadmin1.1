 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

    
     
      <div class="cliennt-container">
       <div class="body-section border-box">
        <form id="addUserfrom">
        <div class="client-Inform">
            <h2 class="mt-2 mb-4">Add Account Updated</h2>
           <div class="personal-Information">
           <div class="row">
            <div class="col-sm-6">
                <h4>Personal Detail</h4>
              
                   <div class="form-row"> 
                   
                   
                   <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Code:</label>
                            <input type="text" class="form-control" id="userid" name="userid">
                            
                          </div>
                    </div>
                    
                    
                  
                   </div>
                   <div class="form-row">
                    
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Name:</label>
                            <input type="text" class="form-control" id="username" name="username">
                          </div>
                    </div>
                    
                    
                   
                   </div>
                   <div class="form-row">
                   <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Reference</label>
                            <input type="password" class="form-control" id="re-password" name="repassword">
                          </div>
                    </div>
                  
                   
                    
                    
                  </div>
                  
                 <div class="form-row">
                   
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Password:</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                
                 <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Contact No:</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                   <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">CommssionType</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                  <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Match Commsssion</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                    <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Session Commsssion</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                   <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Sport Partnership</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                   <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Casino Partnership</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
                
                
                   <div class="form-row">
                   
                     <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Master Password</label>
                            <input type="text" class="form-control" id="password" name="password">
                          </div>
                    </div>
                </div>
            </div>
            
            </div>
           </div> 

          
         
          
        <div class="create-account pl-0 pr-0">
           <a href="javascript: void(0)" >Create Account</a>
        </div>
       

        </div>
        </form>   
       </div>
       
    </div>
    
    <script>
    var type = "";
    var partnerShip =0.0; 
    var partnerShipc =0.0; 
    var element =100;
    var element2 =100;
    $(document).ready(function(){
	    setAdminPart();
	    $("#userid" ).keyup(function() {
	    	 checkUserId($("#userid" ).val())
	   });
	    getMinBet();
	    getMaxBet();
	    getBetDelay();
	    getDefaultMinMaxBetSetAmount();
	     $("#uplineMatchCommssion").html(${user.oddsloss})
	     $("#uplineSessionCommssion").html('${user.fancyloss}')
	    if(${user.usertype} ==5){
	     	$("#inputaccounttype").append('<option value ="0">Super Master</option><option value ="3">Client</option>');
	    	partnerShip ='${user.adminpartership}';
	    	partnerShipc ='${user.adminpartershipc}';
	    	 element = 100-partnerShip;
	    	 partnerShipc = 100-partnerShipc;
		  	for(i = 0; i <=element; i++) { 
		  	$("#partnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
		    } 
		   for(i = 0; i <=partnerShipc; i++) { 
			  	$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
			 } 
		 $("#uplinePartnership").html(partnerShip);
		 $("#cuplinePartnership").html('${user.adminpartershipc}'); 
		
		 $( "#partnership" ).change(function() {
			 $("#downLinePartnership").html(100-(parseInt(partnerShip)+parseInt($("#partnership").val()))); 
			});

		 $( "#cpartnership" ).change(function() {

				 $("#cdownLinePartnership").html(100-(parseInt($("#cuplinePartnership").text())+parseInt($("#cpartnership").val()))); 
		   });
			
		 
	  }
	    else if(${user.usertype} ==0){

		     $("#inputaccounttype").append('<option value ="1">Master</option>'+'<option value ="3">Client</option>');
	    	 partnerShip =parseInt(${user.subadminpartnership})+parseInt(${user.adminpartership});
	    	 partnerShipc = parseInt(${user.subadminpartnershipc})+parseInt(${user.adminpartershipc});
	    	 element = 100-partnerShip;
	    	 element2 = 100-partnerShipc;
	    	for(i = 0; i <=element; i++) { 
		  	 $("#partnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
		   } 
		  	for(i = 0; i <=element2; i++) { 
			  	$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
			 } 
			
		  	$("#uplinePartnership").html(partnerShip)
		  	$("#cuplinePartnership").html(partnerShipc)
		  	 $( "#partnership" ).change(function() {
				 $("#downLinePartnership").html(100-(parseInt(partnerShip)+parseInt($("#partnership").val()))); 
			});
		  	 $( "#cpartnership" ).change(function() {

				 $("#cdownLinePartnership").html(100-(parseInt($("#cuplinePartnership").text())+parseInt($("#cpartnership").val()))); 
		   });

			
	  } else if(${user.usertype} ==1){
	     	$("#inputaccounttype").append('<option value ="2">Agent</option><option value ="3">Client</option>');
	    	partnerShip =parseInt(${user.subadminpartnership})+parseInt(${user.adminpartership})+parseInt(${user.supermastepartnership});
	    	partnerShipc =parseInt(${user.subadminpartnershipc})+parseInt(${user.adminpartershipc})+parseInt(${user.supermastepartnershipc});
	    	 element = 100-partnerShip;
	    	 element2 = 100-partnerShipc;
		  	for(i = 0; i <=element; i++) { 
		  	$("#partnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
		   }  
		  	for(i = 0; i <=element2; i++) { 
			  	$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
			 }
		  $("#uplinePartnership").html(partnerShip)
		  $("#cuplinePartnership").html(partnerShipc)
		  $( "#partnership" ).change(function() {
				 $("#downLinePartnership").html(100-(parseInt(partnerShip)+parseInt($("#partnership").val()))); 
		  });
		  $( "#cpartnership" ).change(function() {

				 $("#cdownLinePartnership").html(100-(parseInt($("#cuplinePartnership").text())+parseInt($("#cpartnership").val()))); 
		   });
	}else if(${user.usertype} ==2){
	 	$("#inputaccounttype").append('<option value ="3">Client</option>');
		partnerShip =parseInt(${user.subadminpartnership})+parseInt(${user.adminpartership})+parseInt(${user.supermastepartnership})+parseInt(${user.masterpartership});
		partnerShipc =parseInt(${user.subadminpartnershipc})+parseInt(${user.adminpartershipc})+parseInt(${user.supermastepartnershipc})+parseInt(${user.masterpartershipc});
		
		 element = 100-partnerShip;
		 element2 = 100-partnerShipc;
	  	for(i = 0; i <=element; i++) { 
	  	  $("#partnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
	    }  
	  	for(i = 0; i <=element2; i++) { 
		  	$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
		 }
		$("#uplinePartnership").html(partnerShip)
	    $("#cuplinePartnership").html(partnerShipc)
	}
	    
	    
	     $("#addUserfrom").validate({
			  rules: {
			    
				  userid: {
			      required: true,
			      minlength: 2,
			      alphanumeric: true
			      
			    }, 
			      password: {
				  required: true,
				  minlength: 3
				},
				  repassword: {
				  required: true,
				  minlength: 3
				},
			      username: {
				  required: true,
				  minlength: 2,
				  alphanumeric: true
				 },
				  inputaccounttype: {
				  required: true
			    },
			      netexposer: {
			      required: true,
			      minlength: 1
				},
				  creditref: {
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
	    
	    getCommssion();
	    $( "#inputaccounttype" ).change(function() {
	    	 if($("#inputaccounttype").val()==3){
	    		 $("#partnership").hide();
	    		 $('#downLinePartnership').hide();
	    		 $("#cpartnership").hide();
	    		 $('#cdownLinePartnership').hide();
	    		 $("#netexposer").attr("readonly", false); 
	    		 $("#min_max_bet").show();
	    	 }else{;
	    		 $("#partnership").show();
	    		 $('#downLinePartnership').show();
	    		 $("#cpartnership").show();
	    		 $('#cdownLinePartnership').show();
	    		 $("#netexposer").attr("readonly", true); 
	    		 $("#min_max_bet").hide();
	    	 }
	   });
	 });
        
      
    
    
    
    function createAdmin()
    {
    	
    	
     	var $valid = $("#addUserfrom").valid();
        var betdelaymap ="";
		   if($("#inputaccounttype").val()==3){
			   betdelaymap ={1:{betDelay:$("#footballbetdelay").val(),minBet:$("#footballmin").val(),maxBet:$("#fotballmax").val()},2:{betDelay:$("#tennisbetdelay").val(),minBet:$("#tennismin").val(),maxBet:$("#tennismax").val()},4:{betDelay:$("#cricketbetdelay").val(),minBet:$("#cricketmin").val(),maxBet:$("#cricketmax").val()}}
	  		   }else{
	  			 betdelaymap ={1:{betDelay:0,minBet:0,maxBet:0},2:{betDelay:0,minbet:0,maxBet:0},4:{betDelay:0,minBet:0,maxBet:0}}
		  		  
		  		   }

   
  	if($valid){
  		
  		if($("#userIdExist").val() =='true'){
	    	 return false;
	     }else{
	    	 var data="";
       	  var username = $("#username").val();
   	   	  var userid = $("#userid").val();
   	   	
   	   	  var password = $("#password").val();
   	   	  var compercetage = $("#percen").val();
   	   	  var parentid ='${user.id}';
   	   	  var usertype =$("inputaccounttype").val();
   	   	  var primarybalance =0;
   	      var secondrybanlce =0;
   	      var reference ="";
   	      var contact =$("#contact").val();
   	      var commisiontype =$("#commisiontype").val();
   	      var appcharge =$("#commision").val();
   	      
   	     
   	      var partnership =$("#partnership").val();
   	      var appname ='${user.appname}';
   	      var appurl ='${user.appurl}';
   	      var adminame = '${user.username}';
   	      var bhaotype = 0;  
   	    	  
   		    
   		  var adminpartership ='${user.adminpartership}';
   		  var adminpartershipc ='${user.adminpartershipc}';
   		  var subadminpartnership ='${user.subadminpartnership}';
   		  var supermastepartnership ='${user.supermastepartnership}';
   		  var masterpartership ='${user.masterpartership}';
   		  var delearpartership ='${user.delearpartership}';
   		  var oddswin= $("#oddsWinCommsimpple").val();
   		  var oddsloss = $("#oddsWinCommsimpple").val();
   		  var fancywin = $("#oddslossdiv").val();
   		  var fancyloss = $("#oddslossdiv").val();
   		  
   		  var partnershipratio =$("#partnership").val();
   		  var partnershipratioc =$("#cpartnership").val();
   		  var adminregistration =$("#adminregistration").val();
   		  var commisiontype ="mobapp";
   		
          var  creditref =$("#creditref").val();
          var parentPassword =$("#parentPasswordAddUser").val();
       
  		   
  		  
   		     data = {createfrom:parentid,username:username,password:password,userid:userid,type:$("#oddstype").val(),parentid:parentid,adminpartership:adminpartership,usertype:$("#inputaccounttype").val(),
   			    	  adminid:'${user.adminid}',subadminid:'${user.subadminid}',reference:reference,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:partnershipratio,
   		    		  fancyloss:fancyloss,oddswincommisiontype:$("#oddstype").val(),oddslosscommisiontype:$("#oddstype").val(),fancywincommisiontype:$("#oddstype").val(),fancylosscommisiontype:$("#oddstype").val(),
   			    	  contact:contact,mobappcharge:0,appurl:appurl,appname:appname,commisiontype:commisiontype,adminname:adminame,bhaotype:bhaotype,isMultiple:$("#mulornot").val(),
   			    	  creditRef:creditref,availableBalance:creditref,appid:'${user.appid}',parentPassword:parentPassword,partnershipratioc:partnershipratioc,adminpartershipc:adminpartershipc,betdelaymap:betdelaymap}

   		  
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
    
    
    
    
    	function getMasterBalaceById()
    {
    	  
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
  	
	  	var element = 100;
	  	if('${user.mobappcharge}' >0){
	  		for(i = 0; i <=element; i++) { 
	  		  	$("#commision").append('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>');
	  		   }	
	  	}else{
	  		
	  		 $("#commision").append('<option id = "0" value="0" name = "0" >0</option>');
	  		   
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
    	
    	function checkUserId(userId)  {
        	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/checkUserId?userId="/>'+userId,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					if(jsondata.type=="error"){
						
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
  	
        function getMinBet() {

        	var i=0;
    	 	 $.ajax({
    	   	 
    				type:'GET',
    				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=minbet',
    				contentType:"application/json; charset=utf-8",
    				success: function(jsondata, textStatus, xhr) {
    					
    				   $.each(jsondata, function( key, value ) {
    					
    					   $("#cricketmin").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#footballmin").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#tennismin").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    	    				
    					
    				});
    				},
    				complete: function(jsondata,textStatus,xhr) {
    					
    			    } 
    			});     	    	
        }
        function getMaxBet() {

        	var i=0;
    	 	 $.ajax({
    	   	 
    				type:'GET',
    				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=maxbet',
    				contentType:"application/json; charset=utf-8",
    				success: function(jsondata, textStatus, xhr) {
    					
    				   $.each(jsondata, function( key, value ) {
    					   $("#cricketmax").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#footballmax").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#tennismax").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    	    				 
    				});
    				},
    				complete: function(jsondata,textStatus,xhr) {
    					
    			    } 
    			});     	    	
       }
        
        function getBetDelay() {

        	var i=0;
    	 	 $.ajax({
    	   	 
    				type:'GET',
    				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=betdelay',
    				contentType:"application/json; charset=utf-8",
    				success: function(jsondata, textStatus, xhr) {
    					
    				   $.each(jsondata, function( key, value ) {
    					   $("#cricketbetdelay").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#footballbetdelay").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    					   $("#tennisbetdelay").append('<option value="'+value.amount+'">'+value.amount+'</option>');
    	    				 
    				});
    				},
    				complete: function(jsondata,textStatus,xhr) {
    					
    			    } 
    			});     	    	
      }

        function getDefaultMinMaxBetSetAmount() {

        	var i=0;
    	 	 $.ajax({
    	   	 
    				type:'GET',
    				url:'<s:url value="/api/getDefaultMinMaxBetSetAmount"/>',
    				contentType:"application/json; charset=utf-8",
    				success: function(jsondata, textStatus, xhr) {
    					
    				   $.each(jsondata, function( key, value ) {
    					   
                          if(value.type =="minbet"){
                    	   $(".defaultminbet").html(value.amount);
                    	  
                       }
                       if(value.type =="maxbet"){
                    	   $(".defaultmaxbet").html(value.amount);
                    	  // console.log(value.amount)
                       }
                       if(value.type =="betdelay"){
                    	   $(".defaultbetdelay").html(value.amount);
                       }
      					  
    					   
    	    				 
    				});
    				},
    				complete: function(jsondata,textStatus,xhr) {
    					
    			    } 
    			});     	    	
      }
    	
        
    </script>
    <input type="hidden" id="parentbalance">
    <input type="hidden" id="userIdExist">