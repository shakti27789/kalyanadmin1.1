  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
  

  <div class="row no-gutters details-box ">
    <div class="col-md-12">
       <div>
		<div class="listing-grid">
			<div class="detail-row">
			<h2>Teen Patti</h2>
			<div class="row m-t-20" id="eventlist">
			
		</div>
		</div>
	  </div>
	</div>
  </div>
</div>

<script>
var sportId ='${sportId}';

	$(document).ready(function(){
		getActiveEventListBySportId(sportId);
		
	});
  function getActiveEventListBySportId(sportId)
  { 
 		$.ajax({
 			url:'<s:url value="/api/getActiveEventListBySport?sportid="/>'+sportId+"&status="+true,
			success: function(jsondata, textStatus, xhr) {

				console.log(jsondata)
				$.each(jsondata, function(key,value){
					 $('#eventlist').append('<div class="col-md-4 m-b-30 div-figure" style="margin-top: 10px;"><a href="<s:url value="/casino/'+value.eventname+'/'+value.eventid+'"/>"><img class="img-fluid" src="<s:url value="/images/banner/'+value.eventid+'.jpeg"/>"></a></div>')
				 });
				
  		}
  	});
  } 
</script>



