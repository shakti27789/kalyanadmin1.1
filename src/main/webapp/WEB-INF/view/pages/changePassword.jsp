 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


    
<div class="cliennt-container">  
	<div class="account-container">
  
		<div class="account-record">
			<div class="head-tittle">
			  <h4>Change Password</h4>
			</div>

			<div class="input-date">
				<div class="select-box match-box bet-hstry p-2"> 
					<form id="self_changePassword-form1"> 
						<div class="form-group">
							<label for="pwd">New Password:</label>
							<input type="password" class="form-control" id="newPasswordnew" name="newPasswordnew">
						</div>
						<div class="form-group">
							<label for="pwd">Retype Password:</label>
							<input type="password" class="form-control" id="confirmPasswordnew" name="confirmPasswordnew">
						</div>
						<div class="form-group">
							<label for="usr">Current Password:</label>
							<input type="password" class="form-control" id="currentPasswordnew" name="currentPasswordnew">
						</div>
					</form>



					<!-- <div class="date-box"><input class="datepicker fa fa-calendar-o" id="startDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
					<div class="date-box"><input class="datepicker fa fa-calendar-o" id="endDate" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
					-->

					<div class="butn-box p-0">
						<input type="button" onclick="changePassword()" value="Submit">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
    
    
    
    <script>
    var type = "";
    var partnerShip =0.0; 
    $(document).ready(function(){
    	  $("#self_changePassword-form1").validate({
    		  rules: {
    			    
    			  newPasswordnew: {
    		      required: true,
    		      minlength: 3
    		    },confirmPasswordnew: {
    		      required: true,
    		      minlength: 3,
    		      equalTo: "#newPasswordnew"
    		    },currentPasswordnew: {
    		      required: true,
    		      minlength: 3
    		    }
    		  },
    		     messages: {
    		    	 newPasswordnew: {required:"Please Enter New Password"},
    		    	 confirmPasswordnew:{required: "Please Enter Same Password"},
    		    	 currentPasswordnew: {required: "Please Enter Your Current Password"},
    	          },
    		  errorPlacement: function(label, element) {
    				$('<span class="arrow"></span>').insertBefore(element);
    				$('<span class="error"></span>').insertAfter(element).append(label)
    			}
    		});
     
    });  
        
    
    
    

    function changePassword(){
  	 	
  	  	var $valid = $("#self_changePassword-form1").valid();
  	  	
  	  	if($valid){
  	  		
  	  		var data = { 
  	  		  		userId:'${user.userid}',
  					newPassword:$("#newPasswordnew").val(),
  					currentPassword:$("#currentPasswordnew").val()
  					};
  	  		
  	  		 $.ajax({
  					type:'POST',
  					url:'<s:url value="/api/selfChangePassword"/>',
  					data: JSON.stringify(data),
  					dataType: "json",
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) {
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						$(".self_change_password_Popup").removeClass("active");
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						 var myObj = JSON.parse(jsondata.responseText);
  						 showMessage(myObj.message,myObj.type,myObj.title);
  						 selfLogoutAdmin('${user.id}');
  				 }
  			 });
  	  	 
  	  	}
  	 }
    
     	
    </script>
    <input type="hidden" id="parentbalance">
     <input type="hidden" id="userIdExist">