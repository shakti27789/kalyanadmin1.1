<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <!-- Withdraw -->
  <div class="khata-popUp">
    <h4>Khata Book</h4> 
    <div class="khata-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
    <form action="" id="viewdetail-form" class="mt-4">
   <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Khata:</label></div>
        <div class="clientN-right">
          <input type="number" class="frst-input full-input khatabookamount" id="khatabookamount" name="khatabookamount">
        </div> 
      </div>
    
    <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="sba1" id="">Amount:</label></div>
      <div class="clientN-right">
     <span id="oldAmount"></span>
       
      </div> 
      </div>
    <div class="deposit-btn-box">
        <div class="back-d-btn  deposit-button">
          <i class="fa fa-undo text-white"></i>
          <input type="button" class="back-d-btn" value="Back">
        </div>
        <div class="submit-d-btn deposit-button">
          <input type="button" class="submit-d-btn" onclick="khataBookAmount()" value="Submit">
          <i class="fa fa-sign-in text-white"></i>
        </div>
       
      </div>
    
    </form>
  </div>
  <!-- withdraw end -->
  
  <script>
  
  $(document).ready(function(){
  	
	  
  });
  var childId =0;
  function khataBookModal(Id){
	  
	  
      	
      $(".khata-popUp").addClass("active");
      $(".khata-cancel-btn").click(function(){
          $(".khata-popUp").removeClass("active");
          $("#khatabookamount").val('');
      });

      $(".back-d-btn").click(function(){
          $(".khata-popUp").removeClass("active");
          $("#khatabookamount").val('');
      });
      childId =Id
      viewKhataBook(childId);
      $("#khatabookamount").val('');
     	
  }
      
    
  function khataBookAmount(){
	
  		$.ajax({
  			type:'POST',
  			url:'<s:url value="api/updateKhataBook/"/>'+childId+"/"+$("#khatabookamount").val(),
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) 
  			{
  				showMessage(jsondata.message,jsondata.type,jsondata.title);
  				
  			},
  			complete: function(jsondata,textStatus,xhr) {
  				 
  		    }
  	}); 
        $(".khata-popUp").removeClass("active");
  	 
 }

  function viewKhataBook(childId){
		
	  $("#oldAmount").html('')
 	$.ajax({
		type:'GET',
		url:'<s:url value="api/viewKhataBook/"/>'+childId,
		contentType:"application/json; charset=utf-8",
		success: function(jsondata, textStatus, xhr) 
		{
			$("#oldAmount").html(jsondata.amount)
		},
		complete: function(jsondata,textStatus,xhr) {
			 
	    }
}); 

 
}
  	 

  </script>