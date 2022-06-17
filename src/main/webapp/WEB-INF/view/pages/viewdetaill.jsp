<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <!-- Withdraw -->
  <div class="viewdetail-popUp">
    <h4>View Detail</h4> 
    <div class="vd-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
    <form action="" id="viewdetail-form" class="mt-4">
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="playgame" id="">Match Commssion:</label></div>
      <div class="clientN-right">
         <span id="matchcomm"></span>
      </div>
    </div>
    <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="sba1" id="">Session Commssion:</label></div>
      <div class="clientN-right">
     <span id="sessioncomm"></span>
       
      </div>
    </div>
    
  <%--   <div class="client-D-box client-popUp">
      <div class="clientN-left"><label for="sba1" id="">Password:</label></div>
      <div class="clientN-right">
     <span id="childPassword"></span> 
       
      </div>
    </div>
    --%>
  
  </form>
  </div>
  <!-- withdraw end -->
  
  <script>
  
  $(document).ready(function(){
  	
	  
  });
  function viewDetailModal(userId){
	  
	  
      	
      $(".viewdetail-popUp").addClass("active");
      $(".vd-cancel-btn").click(function(){
          $(".viewdetail-popUp").removeClass("active");
      });
     
      viewDetail(userId)

  }
      
    
  function viewDetail(userId){
	
  		   	
      	$.ajax({
  			type:'GET',
  			url:'<s:url value="api/viewDetail?userId="/>'+userId,
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) 
  			{
  				$("#matchcomm").html(jsondata.matchCommssion+"%");
  				$("#sessioncomm").html(jsondata.sessionCommssion+"%");
  				/* $("#childPassword").html(jsondata.password); */
  			},
  			complete: function(jsondata,textStatus,xhr) {
  				 
  		    }
  	});
  	
  	 
 }
  	 

  </script>