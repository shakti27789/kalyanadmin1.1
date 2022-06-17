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
                <h4> minMaxBetSetAmount</h4>
             </span>
           
			<div class="form-row mt-4">
				<div class="form-group col-sm-3">
					<label for="inputState">Type</label>
					<select id="type" class="form-control">
						<option value="-1">Select</option>
						<option value="minbet">Min</option>
						<option value="maxbet">Max</option>
						<option value="betdelay">Bet Delay</option>
					</select>
				</div>
				<div class="form-group col-sm-3">
					<label for="inputState">Amount</label>
					<input type="number" class="form-control" id="amount" name="amount" placeholder="amount">
				</div>


				<div class="col-sm-3">
					<label class="d-none d-sm-block">&nbsp;</label>
					<button class="btn btn-primary" onclick="setLimit()">SAVE</button>
				</div>
			   
			</div>
         </div>
           
         
           <div class="account-table  table-responsive" id="minmaxbetdelaydata">
          
          
          </div>
       </div>
  </div>
</div>
  
  
        <!-- /#page-wrapper -->
    
       <script id="minMaxBetSetAmountHandler" type="text/x-handlebars-template">

	<table id="myTable" class="table table-striped table-bordered cash-trans" style="width:100%">
	<thead>
		<tr>
		<th>Amount</th>
		<th>Action</th>
		
		</tr>
	</thead>
			<tbody id="myUserList">
{{#each data}}
			<tr>
			<td>{{amount}}</td>
			
			<td><button type="button" class="btn btn-success btn-sm" onclick="dealetAmount('{{id}}')">Delete</button>	
			</td>
			</tr>
{{/each}}
			</tbody>

 </script>
   <script>
    

   
 $(document).ready(function(){
    	$("#minbet").focus();
    	//getActiveSports(true);
      });
    		
 $( "#type" ).change(function() {
	 getData()
	});
 
  function setLimit() {

	  if($("#betDelay").val()== null){
		  $("#betDelay").val(0) 
	  }
	  
	  var data = {
			    "amount":$("#amount").val(),
			    "type":$("#type").val()
			    };		  

    			
			   	 $.ajax({
			   	 
						type:'POST',
						url:'<s:url value="/api/minMaxBetSetAmount"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
							getData()
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					    } 
					});     	    	
		    }
  
  
  function getData() {

	
    			$("#minmaxbetdelaydata").html('');
			   	 $.ajax({
			   	 
						type:'GET',
						url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+$("#type").val(),
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							minmaxbetdelaydata
							var source = $("#minMaxBetSetAmountHandler").html();
							var template = Handlebars.compile(source);
							$("#minmaxbetdelaydata").append(template({data : jsondata}));
							console.log(jsondata)
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					});     	    	
		    }
  
  
  function dealetAmount(id) {

	  
			   	 $.ajax({
			   	 
						type:'DELETE',
						url:'<s:url value="/api/deleteMinMaxBetSetAmount"/>?id='+id,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
							location.reload();
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					    } 
					});     	    	
		    }
  function getActiveSports(status){		
	    $.ajax({
					type:'GET',
					url:'<s:url value="api/getActiveSportList"/>?status='+status,
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
			
				if(xhr.status == 200){  
				 $("#sport").html('');
				$("#sport").append($('<option value=""> Select Sport</a> </option>'));
					  $.each(jsondata, function(index, element) {
				 
		             $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" name = "'+element.name+'"  > '+element.name+' </option>', {
		             			                
		            }));
		            			            			       
  			});
			    	 
				}
				
			
		}
	});
	    
}
    </script>

<script type="text/javascript">
	//Open the web socket connection to the server
	/* var socketConn = new WebSocket('ws://localhost:80/socketHandler');

	//Send Message
	function send() {
		var clientMsg = document.getElementById('clientMsg');
		if (clientMsg.value) {
			socketConn.send(clientMsg.value);
			clientMsg.value = '';
		}
	}

	// Recive Message
	socketConn.onmessage = function(event) {
		var serverMsg = document.getElementById('serverMsg');
		serverMsg.value = event.data;
	} */
</script>
</head>
<!-- <body>
   <h1>Spring MVC 5 + WebSocket + Hello World example</h1>
   <hr />
   <label>Message</label>
   <br>
   <textarea rows="8" cols="50" id="clientMsg"></textarea>
   <br>
   <button onclick="send()">Send</button>
   <br>
   <label>Response from Server</label>
   <br>
   <textarea rows="8" cols="50" id="serverMsg" readonly="readonly"></textarea> -->