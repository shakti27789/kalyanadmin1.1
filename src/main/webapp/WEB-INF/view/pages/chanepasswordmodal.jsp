<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


        <!-- Deposit box -->
     <div class="change_password_Popup" id="change_password_Popup">
      <h4>Deposit</h4> 
       
      <div class="d-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
      <form  id="changePassword-form" class="mt-4">
       <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="parentdeposite">New Password:</label></div>
        <div class="clientN-right">
          <input type="password" class="frst-input newPassword" name="newPassword" id="newPassword"  >
        </div>
      </div>
     <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="parentdeposite">Confirm Password:</label></div>
        <div class="clientN-right">
          <input type="password" class="frst-input confirmPassword" name="confirmPassword" id="confirmPassword"  >
        </div>
      </div>
     
      
      
      
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="m-password">Master Password:</label></div>
        <div class="clientN-right">
          <input type="password" id="parentPassword" name="parentPassword" class="frst-input full-input">
        </div>
      </div>
      <div class="deposit-btn-box">
        <div class="back-d-btn  deposit-button">
          <i class="fa fa-undo text-white" ></i>
          <input type="button" class="back-d-btn"  value="Back">
        </div>
        <div class="submit-d-btn deposit-button">
          <input type="button" class="submit-d-btn" onclick="changePassword()" value="Submit">
          <i class="fa fa-sign-in text-white"></i>
        </div>
       
      </div>
    </form>
    </div>
  <!-- Deposit end -->
  
  
  <script>
  var val =0;
  var amount =0;
  $(document).ready(function(){
	  
	 
	  $("#changePassword-form").validate({
		  rules: {
		    
			  newPassword: {
		      required: true,
		      minlength: 3
		    },confirmPassword: {
		      required: true,
		      minlength: 3,
		      equalTo: "#newPassword"
		    },parentPassword: {
		      required: true,
		      minlength: 3
		    }
		  },
		     messages: {
		    	 amountdeposite: {required:"Please Enter Amount"},
		    	 remarkdeposite:{required: "Please Enter remark"},
		    	 parentpassdeposite: {required: "Please Enter You Password"},
	          },
		  errorPlacement: function(label, element) {
				$('<span class="arrow"></span>').insertBefore(element);
				$('<span class="error"></span>').insertAfter(element).append(label)
			}
		});
  
	
  });
  
  
  function changePassword(){
 	
  	var $valid = $("#changePassword-form").valid();
  	
  	if($valid){
  		
  		var data = { childId:$("#childId").val(),
				parentId:$("#parentId").val(),
				newPassword:$("#newPassword").val(),
				parentPassword:$("#parentPassword").val()
				};
  		
  		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/changePassword"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					$(".change_password_Popup").removeClass("active");
				},
				complete: function(jsondata,textStatus,xhr) {
					 var myObj = JSON.parse(jsondata.responseText);
					 showMessage(myObj.message,myObj.type,myObj.title);
					 
					userList();
			 }
		 });
  	 
  	}
 }
  
</script>