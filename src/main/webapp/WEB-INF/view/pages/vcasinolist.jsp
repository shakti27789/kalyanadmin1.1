  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
  

  <div class="row no-gutters details-box ">
    <div class="col-md-12">
       <div>
		<div class="listing-grid">
			<div class="detail-row">
			<h2>Virtual Casino</h2>
				
			<div class="row m-t-20" id="eventlist">
			
		</div>
		</div>
	  </div>
	</div>
  </div>
</div>

<script>

	$(document).ready(function(){
		getActiveEventListBySportId();
		
	});
  function getActiveEventListBySportId()
  { 
        $("#eventlist").html('');
		$.ajax({
 			url:'<s:url value="/api/getActiveCasinoList?livetv="/>'+false,
			success: function(jsondata, textStatus, xhr) {

				$.each(jsondata, function(key,value){
                    var betlock="";
                    if(${user.usertype} == 4){
                    	  if(value.betLock == true){

                          	betlock =	'<button  onclick="matchbetUnLock('+value.eventid+')" class="btn btn-success" id="matchbetUnLock">Bet UnLock </button>';
                          }else{
                          	betlock = '<button  onclick="matchbetLock('+value.eventid+')" id="matchbetLock" class="btn btn-success">Bet Lock </button>';
                          }
                        }
                  
                        

					 $('#eventlist').append('<div class="col-md-4 m-b-30 div-figure" style="margin-top: 10px;"><div>'+betlock+'</div><div style="margin-top: 10px;"><a href="<s:url value="/casino/'+value.eventname+'/'+value.eventid+'"/>"><img class="img-fluid" src="<s:url value="/images/banner/'+value.eventid+'.jpeg"/>"></a></div></div>')
				 });
				
  		}
  	});
  } 

  function matchbetLock(eventId){
  	
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/betLockMatch/"/>'+eventId,
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					getActiveEventListBySportId();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}


  function matchbetUnLock(eventId){
	  	
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/betUnLockMatch/"/>'+eventId,
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					getActiveEventListBySportId();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}

</script>



