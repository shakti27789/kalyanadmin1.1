<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script id="marketsetresulthandler" type="text/x-handlebars-template">

<table id="myTable" class="table table-striped table-bordered " style="width:100%">
       
               <thead>
                   <tr>
                       <th>Sport</th>
                       <th> Match</th>
                       <th> Market</th>
                       <th>Winner </th>
                       <th> Date</th>
                       <th>Action</th>
                   </tr>
               </thead>
              
               <tbody>
              {{#each data}}
                  <tr>
    				<td style="display: none;">{{sportid}} <input type='hidden' id ='sportid{{id}}' value='{{sportid}}'</td> 
					<td>{{sportname}} <input type='hidden' id ='sportname{{id}}' value='{{sportname}}'</td>
					<td style="display: none;">{{matchid}}<input type='hidden' id ='matchid{{id}}' value='{{matchid}}'</td>
					<td>{{matchname}} <input type='hidden' id ='matchname{{id}}' value='{{matchname}}'</td>
					<td style="display: none;">{{marketid}}<input type='hidden' id ='marketid{{id}}' value='{{marketid}}'</td>
					<td>{{marketname}} <input type='hidden' id ='marketname{{id}}' value='{{marketname}}'</td>
					<td style="display: none;">{{selectionid}}<input type='hidden' id ='selectionid{{id}}' value='{{selectionid}}'</td>
					<td>{{selectionname}} <input type='hidden' id ='selectionname{{id}}' value='{{selectionname}}'</td>
					<td>{{date}} <input type='hidden' id ='date{{id}}' value='{{matchedtime}}'</td>
					{{#if_eq ${user.usertype} 4}}		
					<td><button type="button" class="btn btn-info blue-btn btn-sm" onclick = "rollbackResult('{{id}}')">RollBack</button></td>
					
					{{/if_eq}} 
				 </tr>
			 {{/each}} 
            
  </tbody>
</table>
</script>
<script id="abpndedortiematchhandler" type="text/x-handlebars-template">
   <table id="myTable" class="table table-striped table-bordered sticky-header" style="width:100%">
         
                 <thead>
                     <tr>
                        
                         <th> Match Name</th>
                         <th> Result</th>
                         <th>Action</th>
                     </tr>
                 </thead>
                
                 <tbody>
                {{#each data}}
				    <tr>
    				 <td style="display: none;"></td> 
					 <td>{{matchname}}</td>
					 <td>{{result}} </td>
					  {{#if_eq ${user.usertype} 4}}
			         <td><button type="button" class="btn btn-info blue-btn btn-sm" onclick = "abandendTieMarketRollBack('{{marketid}}','{{result}}')">RollBack</button></td>
					 {{/if_eq}}										
					</tr>
                {{/each}}
               
                
             
                    
   </tbody>
  </table>
         
</script>

<script id="currentBetsHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
		 
<th>series</th>	
 <th>UserId</th>        
<th>Event Name</th>
        <th>Nation</th>
        <th>Bet Type</th>
        <th>User Rate</th>
        <th> Amount</th>
       <th >Match Date</th>
    </tr>
</thead>
<tbody>
  {{#each data}}
{{#if_eq isback true}}
  <tr class="back">
		 <td>{{series}}</td>	
        <td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
     	{{#if_eq marketname "Fancy"}}
         <td>Yes->  {{pricevalue}}</td>
     {{else}} 
        <td>BACK</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>
{{else}} 
<tr class="lay">	
<td>{{series}}</td>		
<td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
        {{#if_eq marketname "Fancy"}}
         <td>No->  {{pricevalue}}</td>
     {{else}} 
        <td>LAY</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>

{{/if_eq}}	

   {{/each}}  
   
</tbody>

</table>
</script>

<!-- **********    start   ******************* -->

<div class="cliennt-container">
       <input type ="hidden" value="" id="sportidtext">
<input type ="hidden" value="" id="sportnametext">
<input type ="hidden" value="" id="seriesid">
<input type ="hidden" value="" id="seriesname">	
<input type ="hidden" value="" id="matchid">
<input type ="hidden" value="" id="matchname">
<input type ="hidden" value="" id="marketid">
<input type ="hidden" value="" id="marketname">
<input type ="hidden" value="" id="selectionid">
<input type ="hidden" value="" id="selectioname">

      
           
          
       <div class="account-container client-record pt-2">

         <div class="account-record market-result">
         
           <div class="acont-box">
             <span class="acont-left ">
                <h4>Market Result</h4>
             </span>
           <form id="openmarkrt">
           
           <div class="form-row">
                   <div class="form-group col-sm-4 col-md-3">
                       <label for="inputState">Sport</label>
                       <select id="sport" class="form-control" name="sport"> </select>
                     </div>
                   <div class="form-group col-sm-4 col-md-3">
                       <label for="inputState">Match</label>
                       <select id="match" name="match" class="form-control">
                       </select>
                     </div>
                   <div class="form-group col-sm-4 col-md-3">
                       <label for="market">Market</label>
                       <select id="market" name="market" class="form-control">
                      </select>
                   </div>
                   <div class="form-group col-sm-4 col-md-3" id="fancylist" style="display:none;">
                       <label for="inputState">Fancy</label>
                       <select id="fancy" name="fancy" class="form-control">
                         
                       </select>
                   </div>
                   <div class="form-group col-sm-4 col-md-3" id="fancystart" style="display:none;">
                       <label for="inputState">Start </label>
                       <select id="fancystartfrom" name="fancystartfrom" class="form-control">
                         
                       </select>
                   </div>
                   <div class="form-group col-sm-4 col-md-3" id="fancyend" style="display:none;">
                       <label for="inputState">End</label>
                       <select id="fancyendto" name="fancyendto" class="form-control">
                         
                       </select>
                   </div>
                   
                  
                   
                   
                   <div class="form-group col-sm-4 col-md-3" id="marketstart" style="display:none;">
                       <label for="inputState">Start </label>
                       <select id="marketstartfrom" name="marketstartfrom" class="form-control">
                         
                       </select>
                   </div>
                   <div class="form-group col-sm-4 col-md-3" id="marketend" style="display:none;">
                       <label for="inputState">End</label>
                       <select id="marketendto" name="marketendto" class="form-control">
                         
                       </select>
                   </div>
				   <div class="col-sm-4 col-md-3" id="deleteFancyBet" style="display:none;">
						<label class="d-none d-sm-block">&nbsp;</label>
						<button type="button" class="btn btn-primary" onClick="deleteFancyBetBySeries()">Delete</button>
				   </div>
				   <div class="col-sm-4 col-md-3" id="deleteMarketBet" style="display:none;">
						<label class="d-none d-sm-block">&nbsp;</label>
						<button type="button" class="btn btn-primary" onClick="deleteMarketBetBySeries()">Delete</button>
				   </div>               
             </div>
           </form>  
         </div>

 <div class="master-box pl-0 pr-0">
                   <div class="master-form">
                    <div class="form-group">
                        <label for="pwd">Master Password:</label>
                        <input type="password" class="form-control" id="deletePass" name="parentPasswordAddUser">
                      </div>
                   </div>
        </div>
           <div class="account-table table-responsive  market-bottom-table mt-4" id="tieorabondandmatch">
            

           </div>
           <div class="account-table bethistory table-responsive" id="currentBets">
    

           <!-- ************* second Table end ******************* -->

          

       </div>
       
        
       </div>
       
  


</div>

<!-- ************** end   ******************** -->
<script>
var subadminid;
var subadminappid;
$(document).ready(function(){
	
	getActiveSports(true);
	$("#sport").change(function(){
        var selectedsport = $(this).children("option:selected").val();
        var sportname =  $("#sport option:selected").attr('name');
         $("#series").children('option').remove();
         $("#match").children('option').remove();
         $("#market").children('option').remove();
         $("#selection").children('option').remove();
         $("#sportidtext").val('').val();
         $("#sportnametext").val('').val(); 
         $("#seriesid").val('').val();
         $("#seriesname").val('').val();
         $("#matchid").val('').val();
         $("#matchname").val('').val();
         $("#marketid").val('').val();
         $("#marketname").val('').val(); 
         $("#selectionid").val('').val();
     	 $("#selectioname").val('').val();
     	 showMatchBySport(selectedsport,true,subadminid);
    });
	  $("#series").change(function(){
	         var selectedseries = $(this).children("option:selected").val();
	         var seriesname =  $("#series option:selected").attr('name');
	         $("#match").children('option').remove();
	         $("#market").children('option').remove();
	         $("#selection").children('option').remove();
	         $("#seriesid").val('').val();
	         $("#seriesname").val('').val();
	         $("#matchid").val('').val();
	         $("#matchname").val('').val();
	         $("#marketid").val('').val();
	         $("#marketname").val('').val(); 
	         $("#selectionid").val('').val();
	     	$("#selectioname").val('').val();
	        showMatchBySeries(selectedseries,true,seriesname);
	    });
	    
	    $("#match").change(function(){
	        var selectedevent = $(this).children("option:selected").val();
	        var matchaname =  $("#match option:selected").attr('name');
	        $("#market").children('option').remove();
	        $("#selection").children('option').remove();
	        $("#matchid").val('').val();
	        $("#matchname").val('').val();
	        $("#marketid").val('').val();
	        $("#marketname").val('').val(); 
	        $("#selectionid").val('').val();
	     	$("#selectioname").val('').val();
	        showMarketBymatch(selectedevent,true,true,matchaname);
	    });

	   
	    
	    
	    $("#market").change(function(){
	        var selectedmarketid = $(this).children("option:selected").val();
	        var marketname =  $("#market").val();
	         $("#selection").children('option').remove();
	         $("#marketid").val('').val();
	         $("#marketname").val('').val(); 
	         $("#selectionid").val('').val();
	     	 $("#selectioname").val('').val();
	     	 if(marketname == "Fancy"){

	     		$("#marketstart").hide();
				$("#marketend").hide();
				$("#deleteMarketBet").hide();
					$("#fancylist").show();
					$("#fancystart").show();
					$("#fancyend").show();
					$("#deleteFancyBet").show();
				
		     		getActiveFncyList($("#match").val());
		     		
			   }else{

				   $("#fancylist").hide();
					$("#fancystart").hide();
					$("#fancyend").hide();
					$("#deleteFancyBet").hide();

					$("#marketstart").show();
					$("#marketend").show();
					$("#deleteMarketBet").show();
				   getActiveMatchBetList($("#market").val())
				   }
	        
	    });
	    
	    $("#fancy").change(function(){
	    	getActiveFancyBet($("#fancy").val());
	    });
	    
	    $("#openmarkrt").validate({
			  rules: {
			    
				  sport: {
			      required: true
			    }, 
			    market: {
			      required: true
			    }, 
			    match: {
			      required: true
			    }, 
			    selection: {
			      required: true
			    }
					
			  },
			     messages: {
			    	 sport: {required:"Please Select Sport Name"},
			    	 market: {required:"Please Select market Name"},
			    	 match: {required:"Please Select Match Name"},
			    	 selection: {required:"Please Select Team Name"},
		          },
			  errorPlacement: function(label, element) {
					$('<span class="arrow"></span>').insertBefore(element);
					$('<span class="error"></span>').insertAfter(element).append(label)
				}
			}); 
});




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
 
function showMatchBySport(sportid,status,subadminid){
              $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveEventListBySport"/>?sportid='+sportid+'&status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					if(xhr.status == 200){     
					$("#match").append($('<option value="">Select Match</a> </option>'));
						  $.each(jsondata, function(index, element) {
					 
			             $("#match").append($('<option id = "'+element.eventid+'" value="'+element.eventid+'" name = "'+element.eventname+'" > '+element.eventname+' </option>', {
			             			                
			            }));
			            			            			       
        			});
				    	 
					}
				 }
    		});
         }
function showMarketBymatch(eventid,isactive,status,matchaname){
     $("#matchid").val(eventid).val();
     $("#matchname").val(matchaname).val();
    
     			$.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveMarketListByEvent"/>?eventid='+eventid+'&isactive='+isactive+'&status='+status+'&subadminid='+subadminid,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    						if(xhr.status == 200){     
    						$("#market").append($('<option value="">Select Market</option>'));
    						 $.each(jsondata, function(index, element) {
    							 $("#market").append($('<option id = "'+element.marketid+'" value="'+element.marketid+'" name = "'+element.marketname+'" > '+element.marketname+' </option>', {
    							 }));	      	 
    						 });
    						 $("#market").append($('<option value="Fancy">Fancy</option>'));
     						
    				     }
    				  }
    				});
                 }
    
    


     
		function getActiveFncyList(matchId){
			 $(".loader").fadeOut("slow");
		    $.ajax({
		    				type:'GET',
		    				url:'<s:url value="api/getActiveFncyList"/>?eventid='+matchId,
		    				contentType:"application/json; charset=utf-8",
		    				success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200){     
							 $("#fancy").append($('<option> <a href="#" >Select Fancy</a> </option>'));
								  $.each(jsondata, function(index, element) {
							 	
					             $("#fancy").append($('<option id = "'+element.fancyid+'" value="'+element.fancyid+'" name = "'+element.name+'" > '+element.name+' </option>', {
					             			                
					            }));
					            			            			       
		        			}); 
		        		}
							
						}
		             });
				}
		
		function getActiveFancyBet(fancyid){
	        $("#fancystartfrom").html('');
	        $(".loader").fadeOut("slow");
	        $("#fancyendto").html('');
		    $.ajax({
		    					type:'GET',
		    					url:'<s:url value="api/getActiveFancyBet"/>?fancyid='+fancyid,
		    					contentType:"application/json; charset=utf-8",
		    					success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200){     
							 $("#fancystartfrom").append($('<option> <a href="#" >Select Start</a> </option>'));
							 $("#fancyendto").append($('<option> <a href="#" >Select End</a> </option>'));
							 
							 $("#currentBets").html('');
							     var source   = $("#currentBetsHandler").html();
								var template = Handlebars.compile(source);
						 	    $("#currentBets").append( template({data:jsondata})); 
								  $.each(jsondata, function(index, element) {
							 	
					             $("#fancystartfrom").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
					             			                
					            }));
					            			            			       
		        			}); 
								  
						    $.each(jsondata, function(index, element) {
									 	
							    $("#fancyendto").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
							   }));
							});
						    
						
		        		}
							
						}
		             });
 				}


		 function getActiveMatchBetList(marketid){
		        $("#marketstartfrom").html('');
		        $("#marketendto").html('');
			    $.ajax({
			    					type:'GET',
			    					url:'<s:url value="api/getActiveMatchBetList"/>?marketid='+marketid+"&isactive=true",
			    					contentType:"application/json; charset=utf-8",
			    					success: function(jsondata, textStatus, xhr) {
							
								if(xhr.status == 200)
								{     
								 $("#marketstartfrom").append($('<option> <a href="#" >Select Start</a> </option>'));
								 $("#marketendto").append($('<option> <a href="#" >Select End</a> </option>'));
								 $("#delmarketBet").show();
								$("#currentBets").html('');	
								   var source   = $("#currentBetsHandler").html();
									var template = Handlebars.compile(source);
							 	    $("#currentBets").append( template({data:jsondata}));
									  $.each(jsondata, function(index, element) {
								 	
						             $("#marketstartfrom").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
						             			                
						            }));
						            			            			       
			        			}); 
									  
							    $.each(jsondata, function(index, element) {
										 	
								    $("#marketendto").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
								             			                
							    }));
						});
							    
							
			        }
								
								
							}
			             });
	 				}


		 function deleteMarketBetBySeries(){
	    	  var market  =$("#market").val();
	    	  var start  =$("#marketstartfrom").val();
	    	  var end  =$("#marketendto").val();
	    	  var deletePass =$("#deletePass").val();
	    	  
			 swal({
	      	      title: 'Are you sure?',
	      	      text: 'You Want to DELETE SELECTED Market Bet!',
	      	      type: 'warning',
	      	      showCancelButton: true,
	      	      confirmButtonColor: '#3085d6',
	      	      cancelButtonColor: '#d33',
	      	      confirmButtonText: 'Yes',
	      	      closeOnConfirm: false
	      	    }).then(function(isConfirm) {
	      	      if (isConfirm) {		 	
	      	    	$(".market-result").html('<div class="loader"></div>')
	      	    	  $.ajax({
	  		 			type:'DELETE',
	  		 			url:'<s:url value="/api/deleteMarketBetBySeries?marketid="/>'+market+"&start="+start+"&end="+end+"&deletePass="+deletePass,
	  					success: function(jsondata, textStatus, xhr) {
	  						showMessage(jsondata.message,jsondata.type,jsondata.title);
	  						$(".loader").fadeOut("slow");
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	  						$(".loader").fadeOut("slow");
	  				    } 
	  					
	  		 	});
	  		 	
	  		 	 } 
	  		 	});
	  		 }

		 function deleteFancyBetBySeries(){
	    	  var fancyid  =$("#fancy").val();
	    	  var start  =$("#fancystartfrom").val();
	    	  var end  =$("#fancyendto").val();
	    	  var deletePass =$("#deletePass").val();
	    	  
			 swal({
	      	      title: 'Are you sure?',
	      	      text: 'You Want to DELETE SELECTED Fancy Bet!',
	      	      type: 'warning',
	      	      showCancelButton: true,
	      	      confirmButtonColor: '#3085d6',
	      	      cancelButtonColor: '#d33',
	      	      confirmButtonText: 'Yes',
	      	      closeOnConfirm: false
	      	    }).then(function(isConfirm) {
	      	      if (isConfirm) {		 	
	      	    	$(".market-result").html('<div class="loader"></div>')
	      	    	  $.ajax({
	  		 			type:'DELETE',
	  		 			url:'<s:url value="/api/deleteFancyBetBySeries?fancyid="/>'+fancyid+"&start="+start+"&end="+end+"&deletePass="+deletePass,
	  					success: function(jsondata, textStatus, xhr) {
	  						showMessage(jsondata.message,jsondata.type,jsondata.title);
	  						$(".loader").fadeOut("slow");
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	  						$(".loader").fadeOut("slow");
	  				    } 
	  					
	  		 	});
	  		 	
	  		 	 } 
	  		 	});
	  		 }

		    function getFancy()
			{
		    	  
			    	if($("#match").val()!="-1"){
			    		$.ajax({
							type:'GET',
							url:'<s:url value="/api/getFancyByMatch?matchid="/>'+$("#match").val(),
							contentType:"application/json; charset=utf-8",
							success: function(jsondata, textStatus, xhr) {
								if(xhr.status == 200)
								{    $(".loader").fadeOut("slow");
									$("#fancy").html('');
				    				 var source   = $("#fancyhandler").html();
			    			    	  var template = Handlebars.compile(source);
			   				    	  $("#fancy").append( template({data:jsondata.data}) );
								}else{
									  var source   = $("#fancyhandler").html();
							    	   var template = Handlebars.compile(source);
								       $("#fancy").append( template({}) );
								
								}
						}
					});
				    	}
						
					}
		 
</script>

   <jsp:include page="handlebars.jsp" /> 
