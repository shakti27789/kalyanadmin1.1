  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>



  <div class="row">
	  <div class="col-lg-12">				
			<div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
				<div class="form-row" id="eventExpoDiv">
					<s:if test="${user.usertype==4}">						
						<div class="col-xl-3">
							<label>Event EXposure:</label>
							<select class="form-control form-control-sm" id="eventexposure" onchange="updateEventExposure()"></select> 
						</div>
					</s:if>
					
					
					<div class="col-xl-9 mt-4 pt-2">
						<button style="display:none;" onclick="matchbetLock()" id="matchbetLock">Bet Lock </button>
						<button style="display:none;" onclick="matchbetUnLock()" id="matchbetUnLock">Bet UnLock </button>
						<button onclick="fancyLock()" style="display:none;" id="fancyLock">Fancy Lock</button>
						<button onclick="fancyUnLock()" style="display:none;" id="fancyUnLock" >Fancy UnLock</button>
						<button style="display:none;" onclick="matchbetLockSBA()" id="matchbetLockSBA">Bet Lock</button>
						<button style="display:none;" onclick="matchbetUnLockSBA()" id="matchbetUnLockSBA">Bet UnLock</button>
						<button onclick="fancyLockSBA()" style="display:none;" id="fancyLockSBA">Fancy Lock</button>
						<button onclick="fancyUnLockSBA()" style="display:none;" id="fancyUnLockSBA" >Fancy UnLock</button>
						<s:if test="${user.usertype==4}">				
						<button style="display:none;"onclick="userCounter()" id="usercount">User Count</button>
						</s:if>
					</div>
				</div>		
				 
			</div> 	
		   
		</div>
	</div>
    <script>

    $(document).ready(function(){
    	matchFancyStatus();
    	sbaBetLockStatus();
     });
  function matchFancyStatus(){
      	
   		$.ajax({
  	   			type:'POST',
  	   			url:'<s:url value="/api/matchFancyStatus/"/>'+'${eventid}',
  	  			success: function(jsondata, textStatus, xhr) {
  	  				console.log("Test: "+jsondata.fancypause)
  	    				if(xhr.status == 200)
  	    				{   

                            
  	    					if(${user.usertype}!=4 && ${user.usertype} !=6){
  	    					    $("#fancyLock").hide();
  	    						$("#fancyUnLock").hide();
  	    						$("#matchbetLock").hide();
	                            $("#matchbetUnLock").hide();
  	    					 }else{



 	    						if(jsondata.fancypause){
  	    							if(jsondata.type !="Casino"){
							 			$("#fancyLock").hide();
  	  	                                 $("#fancyUnLock").show();
  	                               }
  	                                
  								}
  								else{
  									if(jsondata.type !="Casino"){
  										$("#fancyLock").show();
    			                        $("#fancyUnLock").hide();
  									}
  								}
  	    						if(jsondata.betLock){
	                                 $("#matchbetLock").hide();
	                                 $("#matchbetUnLock").show();
									}
								else{
									$("#matchbetLock").show();
			                        $("#matchbetUnLock").hide();
							 		}
  	  							 }
  	    					
  	    					
  	   					}else{
  	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
  	   					}
  	    		}
  	
  	 });
     }

  function matchbetLock(){
    	
 		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/betLockMatch/"/>'+'${eventid}',
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					matchFancyStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
  }
    </script>