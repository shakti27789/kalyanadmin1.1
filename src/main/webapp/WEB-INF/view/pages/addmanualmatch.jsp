 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


    
    
      <div class="cliennt-container">
       <div class="body-section border-box">
        <form id="addApplicationForm">
        <div class="client-Inform">
            <h2 class="mt-2 mb-4">Add Match</h2>
           <div class="personal-Information">
           <div class="row">
            <div class="col-md-6">
                <h4>Match Detail</h4>
                 
                 
                 
                  <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Match Name:</label>
                            <input type="text" class="form-control" id="matchName" name="matchName">
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Match Id:</label>
                            <input type="text" class="form-control" id="matchId" name="matchId">
                          </div>
                    </div>
                   </div>
                   
                   
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Team1:</label>
                            <input type="text" class="form-control" id="team1" name="team2">
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Team2:</label>
                            <input type="text" class="form-control" id="team2" name="team2">
                          </div>
                    </div>
                   </div>
                   
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">MarketId:</label>
                            <input type="text" class="form-control" id="marketId" name="marketId">
                          </div>
                    </div>
                    
                      <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">start Date:</label>
                            <input type="text" class="form-control" id="opendate" name="opendate">
                          </div>
                         
                    </div>
                   
                   </div>
                   
                     <div class="form-row">
                   
                      <div class="col-sm-6">
                      
                          <div class="form-group">
                            <label for="pwd">Hour:</label>
                           <select id="hour" class="form-control">
                        <option value="01">01</option>
                        <option value="02">02</option>
                         <option value="03">03</option>
                          <option value="04">04</option>
                           <option value="05">05</option>
                            <option value="06">06</option>
                             <option value="07">07</option>
                              <option value="08">08</option>
                               <option value="09">09</option>
                                <option value="10">10</option> 
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                
                       </select>
                          </div>
                    </div>
                    
                    
                    <div class="col-sm-6">
                      
                          <div class="form-group">
                            <label for="pwd">Minut:</label>
                        <select id="minut" class="form-control">
                        <option value="00">00</option>
                        <option value="05">05</option>
                        <option value="10">10</option>
                         <option value="15">15</option>
                          <option value="20">20</option>
                           <option value="25">25</option>
                            <option value="30">30</option>
                             <option value="35">35</option>
                              <option value="40">40</option>
                               <option value="45">45</option>
                                <option value="50">50</option> 
                                <option value="55">55</option>
                                
                                
                       </select>
                          </div>
                    </div>
                   
                   </div>
                   
                     <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Match Minbet:</label>
                             <select id="minbet" name="minbet" class="form-control"></select> 
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Match MaxBet:</label>
                             <select id="maxbet" name="maxbet" class="form-control"></select> 
                          </div>
                    </div>
                   </div>
                   
                    <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Fancy Minbet:</label>
                            <select id="minbetfancy" name="minbetfancy" class="form-control"></select> 
                          </div>
                    </div>
                    
                    
                    
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Fancy MaxBet:</label>
                           <select id="maxbetfancy" name="maxbetfancy" class="form-control"></select> 
                          </div>
                    </div>
                   </div>
                    <div class="form-row">
                    
                    
                    
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Match BetDelay:</label>
                             <select id="betdelay" name="betdelay" class="form-control"></select> 
                          </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="pwd">Fancy BetDelay:</label>
                            <select id="betdelayfancy" name="betdelayfancy" class="form-control"></select> 
                          </div>
                    </div>
                   </div>
                   
                   <div class="form-row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="usr">Series Name:</label>
                           <select id="seriesid" name="seriesid" class="form-control"></select> </div>
                    </div>
                    
                    
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="playermaxbetfancy">Fancy Player MaxBet:</label>
                           <select id="playermaxbetfancy" name="playermaxbetfancy" class="form-control"></select> 
                          </div>
                    </div>
                    
                   </div>
             </div>
             </div>
           </div> 
        
     

        <div class="create-account pl-0 pr-0">
           <a href="javascript: void(0)" onclick="createMatch()">Create Match</a>
        </div>
       

        </div>
        </form>
       </div>
       
    </div>
    
    <script>
    var type = "";
   
    $(document).ready(function(){
    	

    	 $("#opendate").datepicker({
    	        format: "yyyy-mm-dd"
    	    });
    	  seriesList();
    	  getMinBet("minbet");
		  getMaxBet("maxbet");
		  getBetDelay("betdelay");
 	});

    function seriesList(){
		$.ajax({
			type:'GET',
			url:'<s:url value="/api/seriesList"/>',
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200){     
					  $.each(jsondata, function(index, element) {
							 
				             $("#seriesid").append($('<option id = "'+element.seriesid+'" value="'+element.seriesid+'" name = "'+element.seriesname+'"  > '+element.seriesname+' </option>', {
				             			                
				            }));
				            			            			       
		    			});
			    }
		}
	});
}

    function getBetDelay(type) {

    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					   if(i==0){
							  $("#betdelay").append('<option value="-1">Bet Delay</option>')
							  $("#betdelayfancy").append('<option value="-1">Bet Delay</option>')
							 } 
					   $("#betdelay").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					   $("#betdelayfancy").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					i++;
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
  }

    function getMinBet(type) {
       
	var i=0;
 	 $.ajax({
   	 
			type:'GET',
			url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
			   $.each(jsondata, function( key, value ) {
				 if(type ="minbet"){
					 if(i==0){
					  $("#minbet").append('<option value="-1">Min Bet</option>')
					  $("#minbetfancy").append('<option value="-1">Min Bet</option>');
					  
					 } $("#minbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					   $("#minbetfancy").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					   
				 }
				 i++;
			});
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}

function getMaxBet(type) {

	var i=0;
 	 $.ajax({
   	 
			type:'GET',
			url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
			   $.each(jsondata, function( key, value ) {
				   if(i==0){
						  $("#maxbet").append('<option value="-1">Max Bet</option>')
						  $("#maxbetfancy").append('<option value="-1">Max Bet</option>')
						  $("#playermaxbetfancy").append('<option value="'+value.amount+'">'+value.amount+'</option>')
						  
						 } 
				       $("#maxbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
				       $("#maxbetfancy").append('<option value="'+value.amount+'">'+value.amount+'</option>')
				       $("#playermaxbetfancy").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					   
				i++;
			});
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}
    
        function createMatch() {
        	  data = {matchName:$("#matchName").val(),matchId:$("#matchId").val(),marketId:$("#marketId").val(),opendate:$("#opendate").val(),hour:$("#hour").val(),minut:$("#minut").val(),seriesid:$("#seriesid").val(),

        			  minbet:$("#minbet").val(),maxbet:$("#maxbet").val(),betdelay:$("#betdelay").val(),minbetfancy:$("#minbetfancy").val(),maxbetfancy:$("#maxbetfancy").val(),betdelayfancy:$("#betdelayfancy").val(),
        			  team1:$("#team1").val(),team2:$("#team2").val(),playermaxbetfancy:parseInt($("#playermaxbetfancy").val())}
       	   
 	        	      	  $.ajax({
 	        					type:'POST',
 	        					url:'<s:url value="/api/createMatch"/>',
 	        					data: JSON.stringify(data),
 	        					dataType: "json",
 	        					contentType:"application/json; charset=utf-8",
 	        					success: function(jsondata, textStatus, xhr) 
 	        					{
 	        						showMessage(jsondata.message,jsondata.type,jsondata.title);
 	        						location.reload();
 	        					},
 	        					complete: function(jsondata,textStatus,xhr) {
 	        						
 	        						const obj = JSON.parse(jsondata.responseText);
 	        						showMessage(obj.message,obj.type,obj.title);
 	        						location.reload();

 	        				    }
 	        			});
 	        		}
    
    
    
     	
    </script>
    <input type="hidden" id="parentbalance">
     <input type="hidden" id="userIdExist">