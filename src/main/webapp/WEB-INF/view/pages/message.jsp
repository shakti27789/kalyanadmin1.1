<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
<div class="cliennt-container">
	<div class="account-container client-record pt-4">
         <div class="account-record cash-trans">
			<div class="acont-box">
				<span class="acont-left ">
					<h4>Set Message</h4>
				</span>           
			   <div class="form-row mt-4">
					<div class="form-group col-sm-4">
					   <label for="inputState">User Type</label>
					   <select id="msgtype" class="form-control">
						 <option value="-1"> Select </option>
							  <option value="C">Client </option>
							  <option value="O">Other</option>
					   </select>
					</div>
					<div class="col-sm-3">
						<label class="d-none d-sm-block">&nbsp;</label>
						<button class="btn btn-primary" onclick="setMessage()">SAVE</button>
					</div>               
			   </div>
			</div>
			<div class="account-table  table-responsive" id="transactionTable">
            <textarea rows="6"  class="form-control" cols="50" placeholder="Enter Your Message" id="setmessage"></textarea>
          </div>
       </div>
	</div>
</div> 
        
    
   
    

  
   <script>
    
    
 $(document).ready(function(){
    	$("#setmessage").focus();
    	
    	$( "#msgtype" ).change(function() {
    		getMessage();
    		});
      });
    		
    	
  
  function setMessage() {

		    var message = $("#setmessage").val();
		    var msgtype = $("#msgtype").val();
		    
			if($.trim(message).length>0)	{
		
			var data = {message:message,msgtype:msgtype}
		    
		    swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to set this Message!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
		   	 $.ajax({
		   	 
					type:'POST',
					//url:'http://localhost/api/setMessage',
					url:'<s:url value="/api/setMessage"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						 
						$('#add_here').remove();
						$("#setmessage").val('');
						
						
						location.reload();
						
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				    } 
				}); 
				
				 } 
				}); 
				
				}  
  			else
    	{
    	showMessage("Kindly Type a Message","error","Oops...");
    	}
    	    	
		    }
		    
		    
		    
		     function getMessage(){
		    	 var msgtype = $("#msgtype").val();
    	    	   $.ajax({
  					type:'GET',
  					url:'<s:url value="/api/getMessage"/>?msgtype='+msgtype,
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) 
  					{
  						if(xhr.status == 200){
  						
  							$("#setmessage").text(jsondata.message);
  						
  						}
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						//showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						 
  				    }
  			
    	    });
    	   
    }
    
   
  
    </script>
 