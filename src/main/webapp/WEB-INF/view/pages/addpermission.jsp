<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Set Min Max Bet</h1>
						<div>
						<select id="type">
  						<option value="Match Odds">Match Odds</option>
  						<option value="Fancy">Fancy</option>
						</select>
						</div>
						<br>
						<div>
						<input type="number" class="form-control" id="minbet" placeholder="Min Bet" style="width:150px"/>
						<br>
						<input type="number" class="form-control" id="maxbet" placeholder="Max Bet" style="width:150px"/>
						<br>
						<input type="number" class="form-control" id="betDelay" placeholder="Bet Delay" style="width:150px"/>						
							</div>
							<br>				 
					 <button class="btn btn-info blue-btn btn-sm" onclick='setLimit()' >Set Limit</button>
						
						</div>

					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
  
   <script>
    
    
 $(document).ready(function(){
    	$("#minbet").focus();
      });
    		
    	
  
  function setLimit() {

	  if($("#betDelay").val()== null){
		  $("#betDelay").val(0) 
	  }
	  
	  var data = {
			    "minbet":$("#minbet").val(),
			    "maxbet":$("#maxbet").val(),
			    "type":$("#type").val(),
			    "betdelay":$("#betDelay").val(),
			    "appid":${user.appid}
			    };		  

    			
			   	 $.ajax({
			   	 
						type:'POST',
						url:'<s:url value="/api/setMinMaxBet"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
							$("#minbet").val('');
							$("#maxbet").val('');
							$("#betDelay").val('')
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					    } 
					});     	    	
		    }
  
  
    </script>
 