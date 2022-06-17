<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <!-- Withdraw -->
  <div class="credit-popUp">
    <h4>Credit</h4> 
    <div class="w-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
    <form action="" id="Credit-form" class="mt-4">
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="credit-user-id" id="credit-user-id"></label></div>
      <div class="clientN-right">
      <input type="hidden" class="frst-input oldcredithidden" id="oldcredithidden"  readonly>
        <input type="text" id="oldcredit"  name="oldcredit" class="frst-input">
      </div>
    </div>
    
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="ammount">New Credit:</label></div>
      <div class="clientN-right">
        <input type="text" class="frst-input full-input newcredit"  id="newcredit">
      </div> 
    </div>
    
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="m-password">Master Password:</label></div>
      <div class="clientN-right">
        <input type="password" class="frst-input full-input" name="parentpasscredit" id="parentpasscredit">
      </div>
    </div>
    <div class="deposit-btn-box">
      <div class="back-d-btn  deposit-button">
        <i class="fa fa-undo text-white" ></i>
        <input type="submit" class="back-d-btn"  value="Back">
      </div>
      <div class="submit-d-btn deposit-button">
        <input type="button" class="submit-d-btn" value="Submit" onclick="changeCredtRef()">
        <i class="fa fa-sign-in text-white"></i>
      </div>
     
    </div>
  </form>
  </div>
  <!-- withdraw end -->
  
  <script>
  
  $(document).ready(function(){
  	
	
	  
  	  	 $("#Credit-form").validate({
  			  rules: {
  			    
  				newcredit: {
  			      required: true,
  			      minlength: 1
  			    },parentpasscredit: {
  			      required: true,
  			      minlength: 3
  			    }
  			  },
  			     messages: {
  			    	newcredit: {required:"Please Enter Amount"},
  			    	parentpasscredit: {required: "Please Enter You Password"},
  		          },
  			  errorPlacement: function(label, element) {
  					$('<span class="arrow"></span>').insertBefore(element);
  					$('<span class="error"></span>').insertAfter(element).append(label)
  				}
  			});
  });
  function modalChangeCredit(parentId,childId,creditRef,userid){
	  
	 
      $("#oldcredit").val(creditRef);
      $("#credit-user-id").text(userid);
   	
      $(".credit-popUp").addClass("active");
      $(".w-cancel-btn").click(function(){
          $(".credit-popUp").removeClass("active");
      });
      $(".back-d-btn").click(function(){
          $(".credit-popUp").removeClass("active");
      });
       
       $("#parentId").val(parentId)
	   $("#childId").val(childId)
  }
      
    
  function changeCredtRef(){
	
  	var $valid = $("#Credit-form").valid();
  	if($valid){
  	  $(".credit-popUp").removeClass("active");
  		var data = {parentid:$("#parentId").val(),childid:$("#childId").val(),amount:$("#newcredit").val(),password:$("#parentpasscredit").val()}
  		$(".account-container").append('<div class="loader"></div>')    	
      	$.ajax({
  			type:'POST',
  			url:'<s:url value="api/changeCredtRef"/>',
  			data: JSON.stringify(data),
  			dataType: "json",
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) 
  			{
  				 $("#userListDiv").html('');
  				 $(".credit-popUp").removeClass("active");
  				 showMessage(jsondata.message,jsondata.type,jsondata.title);
  				 document.getElementById("Credit-form").reset();
  				 userList();
  				$(".loader").fadeOut("slow");
  			},
  			complete: function(jsondata,textStatus,xhr) {
  				$(".loader").fadeOut("slow");
  		    }
  	});
  	}
  	 
 }
  	 

  </script>