<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <!-- Withdraw -->
  <div class="withdraw-popUp">
    <h4>Withdraw</h4> 
    <div class="w-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
    <form action="" id="Withdraw-form" class="mt-4">
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="playgame" id="parentwithdraw">${user.userid}:</label></div>
      <div class="clientN-right">
      <input type="hidden" class="frst-input mainbalanceparentwithdrawhidden" id="mainbalanceparentwithdrawhidden"  readonly>
        <input type="text" id="mainbalanceparentwithdraw"  name="mainbalanceparentwithdraw" class="frst-input">
        <input type="text"  class="scnd-input aftermainbalanceparentwithdraw" id="aftermainbalanceparentwithdraw">
      </div>
    </div>
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="sba1" id="childwithdraw" for="childwithdraw" class="childwithdraw"></label></div>
      <div class="clientN-right">
       <input type="hidden" class="frst-input mainbalancechildwithdrawhidden" id="mainbalancechildwithdrawhidden"  readonly>
        <input type="text" class="frst-input mainbalancechildwithdraw" id="mainbalancechildwithdraw">
        <input type="text"  class="scnd-input aftermainbalancechildwithdraw" class="aftermainbalancechildwithdraw">
      </div>
    </div>
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="ammount">Ammount:</label></div>
      <div class="clientN-right">
        <input type="text" class="frst-input full-input amountwithdraw" name="amountwithdraw" id="amountwithdraw">
      </div> 
    </div>
     <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Remark:</label></div>
        <div class="clientN-right">
          <input type="text" class="frst-input full-input remarkwithdraw" id="remarkwithdraw" name="remarkwithdraw" >
        </div> 
      </div>
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="m-password">Master Password:</label></div>
      <div class="clientN-right">
        <input type="password" class="frst-input full-input" name="parentpasswithdraw" id="parentpasswithdraw">
      </div>
    </div>
    <div class="deposit-btn-box">
      <div class="back-d-btn  deposit-button">
        <i class="fa fa-undo text-white" ></i>
        <input type="submit" class="back-d-btn"  value="Back">
      </div>
      <div class="submit-d-btn deposit-button">
        <input type="button" class="submit-d-btn" value="Submit" onclick="withdrawChipToMaster()">
        <i class="fa fa-sign-in text-white"></i>
      </div>
     
    </div>
  </form>
  </div>
  <!-- withdraw end -->
  
  <script>
  
  $(document).ready(function(){
  	
	  $(".amountwithdraw").keyup(function(){
		  if($(".amountwithdraw").val()!=""){
			  $(".aftermainbalanceparentwithdraw").val(parseFloat($("#mainbalanceparentwithdraw").val().trim())+parseFloat($(".amountwithdraw").val()))
			  $(".aftermainbalancechildwithdraw").val(parseFloat($("#mainbalancechildwithdraw").val().trim())-parseFloat($(".amountwithdraw").val()))
		
			}else{
				  $(".aftermainbalanceparentwithdraw").val(parseFloat($("#mainbalanceparentwithdrawhidden").val().trim()))
				  $(".aftermainbalancechildwithdraw").val(parseFloat($("#aftermainbalanceparentwithdraw").val().trim()))
			}
	  });
	  
  	  	 $("#Withdraw-form").validate({
  			  rules: {
  			    
  				amountwithdraw: {
  			      required: true,
  			      minlength: 3
  			    },parentpasswithdraw: {
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
  function modalWithdraw(parentId,childId,secondrybanlce){
	  
	  getParentChildBalance(childId,parentId,'withdraw');
	  
      $("#childwithdraw").text(childId+":")
      $("#mainbalanceparentwithdraw").val(secondrybanlce);
      $("#mainbalancechildwithdraw").val(secondrybanlce);
		
      $(".withdraw-popUp").addClass("active");
      $(".w-cancel-btn").click(function(){
          $(".withdraw-popUp").removeClass("active");
      });
      $(".back-d-btn").click(function(){
          $(".withdraw-popUp").removeClass("active");
      });
       
        var chips = $("#amountdeposite").val(); 
 	   $("#parentId").val(parentId)
 	   $("#childId").val(childId)

  }
      
    
  function withdrawChipToMaster(){
	
  	var $valid = $("#Withdraw-form").valid();
  	if($valid){
  	  $(".withdraw-popUp").removeClass("active");
  		var data = {parentid:$("#parentId").val(),userid:$("#childId").val(),chips:$("#amountwithdraw").val(),descreption:$("#remarkwithdraw").val(),password:$("#parentpasswithdraw").val()}
  		$(".account-container").append('<div class="loader"></div>')    	
      	$.ajax({
  			type:'POST',
  			url:'<s:url value="api/chipWithdraw"/>',
  			data: JSON.stringify(data),
  			dataType: "json",
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) 
  			{
  				 $("#userListDiv").html('');
  				 $(".withdraw-popUp").removeClass("active");
  				 document.getElementById("Withdraw-form").reset();
   				
  				 showMessage(jsondata.message,jsondata.type,jsondata.title);
  				 userList(false);
  				$(".loader").fadeOut("slow");
  			},
  			complete: function(jsondata,textStatus,xhr) {
  				$(".loader").fadeOut("slow");
  		    }
  	});
  	}
  	 
 }
  	 

  </script>