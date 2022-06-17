<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>  
  
  
   
    
    
    <div class="deposit-popUp" id="deposit-popUp">  
            <h4>Deposit</h4> 
       <input type ="hidden" class="mypnl" ></input>
       <input class="datepicker fa fa-calendar-o" id="date" type="hidden">
      <div class="d-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
      <form  id="Deposit-form" class="mt-4">
       <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="parentdeposite">${user.userid}:</label></div>
        <div class="clientN-right">
          <input type="hidden" class="frst-input mainbalanceparentdepositehidden" name="mainbalanceparentdepositehidden" id="mainbalanceparentdepositehidden"  readonly>
          <input type="number" class="frst-input mainbalanceparentdeposite" name="mainbalanceparentdeposite" id="mainbalanceparentdeposite"  readonly>
          <input type="text" class="scnd-input aftermainbalanceparentdeposite" name ="aftermainbalanceparentdeposite" name="aftermainbalanceparentdeposite" id="aftermainbalanceparentdeposite" readonly>
        </div>
      </div>
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="childdeposite" class="childdeposite" id="childdeposite"></label></div>
        <div class="clientN-right">
         <input type="hidden" class="frst-input mainbalancechilddepositehidden" id="mainbalancechilddepositehidden"  readonly>
          <input type="text" class="frst-input mainbalancechilddeposite" id="mainbalancechilddeposite"  readonly>
          <input type="text"  class="scnd-input aftermainbalancechilddeposite" name="aftermainbalancechilddeposite" id="aftermainbalancechilddeposite" readonly>
        </div>
      </div>
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Ammount:</label></div>
        <div class="clientN-right">
          <input type="number" class="frst-input full-input amountdeposite" id="amountdeposite" name="amountdeposite"  >
        </div> 
      </div>
     
      
       <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Remark:</label></div>
        <div class="clientN-right">
          <input type="text" class="frst-input full-input remarkdeposite" id="remarkdeposite" name="remarkdeposite" >
        </div> 
      </div>
      
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="m-password">Master Password:</label></div>
        <div class="clientN-right">
          <input type="password" id="parentpassdeposite" name="parentpassdeposite" class="frst-input full-input">
        </div>
      </div>
      <div class="deposit-btn-box">
        <div class="back-d-btn  deposit-button">
          <i class="fa fa-undo text-white" ></i>
          <input type="button" class="back-d-btn"  value="Back">
        </div>
        <div class="submit-d-btn deposit-button">
          <input type="button" class="submit-d-btn" onclick="dipositChipToMaster()" value="Submit">
          <i class="fa fa-sign-in text-white"></i>
        </div>
       
      </div>
    </form>
    </div>
    
    
  <!-- Deposit end -->
  
  
  <script>
  var val =0;
  $(document).ready(function(){
	  
	
	  $(".limit-btn").click(function(){
      $(".limit-popUp").addClass("active");
      $(".w-cancel-btn").click(function(){
          $(".withdraw-popUp").removeClass("active");
      });
      $(".back-d-btn").click(function(){
          $(".withdraw-popUp").removeClass("active");
      });
   });
	  
	  
	  $(".amountdeposite").keyup(function(){
		  if($(".amountdeposite").val()!=""){
             
			  $(".aftermainbalanceparentdeposite").val(parseFloat($("#mainbalanceparentdeposite").val().trim())-parseFloat($(".amountdeposite").val()))
			  $(".aftermainbalancechilddeposite").val(parseFloat($("#mainbalancechilddeposite").val().trim())+parseFloat($(".amountdeposite").val()))
		
			}else{
				  $(".aftermainbalanceparentdeposite").val(parseFloat($("#mainbalanceparentdepositehidden").val().trim()))
				  $(".aftermainbalancechilddeposite").val(parseFloat($("#mainbalanceparentdepositehidden").val().trim()))
			}
	  });
	  
  });
  
  
  function dipositChipToMaster(){
 	
  	var $valid = $("#Deposit-form").valid();
  	if($valid){
  		var data = {parentid:$("#parentId").val(),userid:$("#childId").val(),chips:$(".amountdeposite").val(),descreption:$("#remarkdeposite").val(),password:$("#parentpassdeposite").val()}
         	 
      	$.ajax({
  			type:'POST',
  			url:'<s:url value="api/chipCredit"/>',
  			data: JSON.stringify(data),
  			dataType: "json",
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) 
  			{
			     		
  				$("#userListDiv").html('');
  				$(".deposit-popUp").removeClass("active");
  				document.getElementById("Deposit-form").reset();
  				showMessage(jsondata.message,jsondata.type,jsondata.title);
  				userList(false);
  				
  			},
  			complete: function(jsondata,textStatus,xhr) {
  				 
  		    }
  	});
  	}
 }
  
  function updateCredtRef(){
	 	
	  	var $valid = $("#Deposit-form").valid();
	  	if($valid){
	  		 $(".deposit-popUp").removeClass("active");
		  		var data = {parentid:$("#parentId").val(),userid:$("#childId").val(),creditRef:$("#newCreditRef").val(),descreption:$("#remarkdeposite").val(),password:$("#parentpassdeposite").val()}
		  		$(".account-container").append('<div class="loader"></div>')   
	      	$.ajax({
	  			type:'POST',
	  			url:'<s:url value="api/updateCredtRef"/>',
	  			data: JSON.stringify(data),
	  			dataType: "json",
	  			contentType:"application/json; charset=utf-8",
	  			success: function(jsondata, textStatus, xhr) 
	  			{
	  				console.log(xhr.status)
	  				$("#userListDiv").html('');
	  				 $(".deposit-popUp").removeClass("active");
	  					
	  				showMessage(jsondata.message,jsondata.type,jsondata.title);
	  				 document.getElementById("Credit-form").reset();
	  			    $(".loader").fadeOut("slow");

	  				userList(false);
	  				
	  			},
	  			complete: function(jsondata,textStatus,xhr) {
	  				const obj = JSON.parse(jsondata.responseText);
					showMessage(obj.message,obj.type,obj.title);	 
					$(".loader").fadeOut("slow");
	  		    }
	  	});
	  	}
	 }
</script>