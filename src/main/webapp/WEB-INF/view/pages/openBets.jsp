<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <script id="openbethandler" type="text/x-handlebars-template">

								<table class="table common-table">
									<thead>
										<tr>
										<th>series</th>				
										<th>Description</th>	
										<th>Odds</th>			
										<th>Mode</th>		
										<th>Sport</th>
										<th>Client</th>
										<th>Date</th>
										<th>Session</th>
											<!-- <th>Matched Time</th> -->
											<th>Stake</th>
											<th>pnl</th>
											<th>Libility</th>
										</tr>
									</thead>
									<tbody id="myTable">
											{{#each data}}
									{{#if isback true}}
										<tr class="back">
									<td>{{series}}</td>		
									<td>{{matchname}}>>{{fancyname}}</td>	
									<td>{{odds}}</td>		
									<td>Yes</td>					
											 {{#if sportid 4}}
							                    <td>
							                    Cricket
							                     </td>
							                     {{else}}{{#if sportid 2}}
							                      <td>
							                    Tennis
							                     </td>
							                     {{else}}{{#if sportid 1}}
							                      <td>
							                    Football
							                     </td>
							                       {{/if}}
							                       {{/if}}{{/if}}	
											<td>{{username}} <span><i class="fa fa-check" style="color: GREEN;"></i></span></td>
											<!--<td>11/22/2018 <br> 16-15-58<b style="color: RED;"> / </b>16-15-58</td>-->
											<td>{{date}}</td>	
											{{#if type 'Fancy'}}	
											<td>{{matchname}}>>{{fancyname}}</td>
											{{else}}{{#if type 'Match Odds'}}								
											<td>{{matchname}}>>{{marketname}}>>{{selectionname}}</td>
												{{/if}}
										  {{/if}}		
											
					                    	<td>{{stake}}</td>
					                    	<td>{{pnl}}</td>
											<td>{{liability}}</td>
										</tr>
{{else}}{{#if islay true}}
	<tr class="lay">
									<td>{{series}}</td>		
									<td>{{matchname}}>>{{fancyname}}</td>	
									<td>{{odds}}</td>		
									<td>Yes</td>					
											 {{#if sportid 4}}
							                    <td>
							                    Cricket
							                     </td>
							                     {{else}}{{#if sportid 2}}
							                      <td>
							                    Tennis
							                     </td>
							                     {{else}}{{#if sportid 1}}
							                      <td>
							                    Football
							                     </td>
							                       {{/if}}
							                       {{/if}}{{/if}}	
											<td>{{username}} <span><i class="fa fa-check" style="color: GREEN;"></i></span></td>
											<!--<td>11/22/2018 <br> 16-15-58<b style="color: RED;"> / </b>16-15-58</td>-->
											<td>{{date}}</td>	
											{{#if type 'Fancy'}}	
											<td>{{matchname}}>>{{fancyname}}</td>
											{{else}}{{#if type 'Match Odds'}}								
											<td>{{matchname}}>>{{marketname}}>>{{selectionname}}</td>
												{{/if}}
										  {{/if}}		
											
					                    	<td>{{stake}}</td>
					                    	<td>{{pnl}}</td>
											<td>{{liability}}</td>
										</tr>
{{/if}}
									{{/if}}

										{{/each}}
									</tbody>								
									</tbody>
								</table>
							
				
				
            
</script>

<div id="page-wrapper">	
<input type ="hidden" value="-1" id="typeid">
<input type ="hidden" value="-1" id="myid">
<input type ="hidden" value="-1" id="matchid">
<input type ="hidden" value="-1" id="marketid">	
<input type ="hidden" value="-1" id="sportid">	
<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Open bets</h1>
						<div class="filterSection">
							<ul>
							<li>
															<label>Select Type</label>
							
							<select  class="" id='type' onchange="getNewVal(this)">
										<option value="Match Odds"  label="Market" name = "Match Odds"></option>
										<option value="Fancy"  label="Fancy"  name = "Fancy"></option>
									</select>
									</li>
									
									<li>
									<label>Select Sport</label>
								   <select  class="" id='sport' onchange="getSport(this)">
										<option value="0"  label="Select Sport" name= "0"></option>
										<option value="1"  label="Soccer" name= "1"></option>
										<option value="2"  label="Tennis"  name = "2"></option>
										<option value="4"  label="Cricket"  name = "4"></option>
									</select>
									</li>
									
								<li>
						
									<label>Select Match</label>
									<select  class="" id='match'></select>
								</li>
								
								<li id="fancynameli" style="display:none;">
									<label>Select Fancy</label>
									<select  class="" id='fancyname'></select>
								</li>
								
								<li id="fancystartli" style="display:none;">
									<label>Fancy Start from</label>
									<select  class="" id='fancystart'></select>
								</li>
								
								<li id="fancyendli" style="display:none;">
									<label>Fancy End</label>
									<select  class="" id='fancyend'></select>
								</li>
								
							
								
							<li id="marketnameli" style="display:none;">
									<label id='marketlable'>Select Market</label>
									<select class="" id='market'></select>
						    </li>
						    	<li id="matchstartli" style="display:none;">
									<label>Fancy Start from</label>
									<select  class="" id='matchstart'></select>
								</li>
								
								<li id="matchendli" style="display:none;">
									<label>Fancy End</label>
									<select  class="" id='matchend'></select>
								</li>
						    
						    
							</ul>
							<ul>
							   <li><button class="btn btn-info blue-btn btn-sm" id="delfancy" style="display:none;" onclick="deleteFancyBetBySeries()">DELETE</button></li>
							</ul>	
							
							<ul>
							   <li><button class="btn btn-info blue-btn btn-sm" id="delmarketBet" style="display:none;" onclick="deleteMarketBetBySeries()">DELETE</button></li>
							</ul>						
							</div>						
						<section class="menu-content">
							<div class="search-wrap">
								<input id="myInput" type="text" placeholder="Search..">			
							</div>
											
							<div class="table-scroll" id="getopenBets">
								
							</div>
							<div class="panel-body text-center" id="noOpenbets">
								<h1>No Bets Available</h1>
							</div>
							<div class="additional-filters">
							<form>
								<div class="panel-body text-center">
									<button class="apl-btn" id="previousBets" type="button" onclick="getOpenBets('previous')">PREVIOUS</button>
									<button class="apl-btn" id="nextBets" type="button" onclick="getOpenBets('next')">NEXT</button>
								</div>
							</form>							
						</div>
						</section>						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    
       <script >
   	var page = 0;
	$("#previousBets").hide();

	$("#nextBets").hide();

    $(document).ready(function(){
    getOpenBets();
    $("#typeid").val("Match Odds").val();
    
    $("#type option:eq(0)").attr("selected", "selected");
    
  
    $("#fancynameli").change(function(){
    	$("#fancystartli").show();
    	$("#fancyendli").show();
    	var fancyid = $("#fancynameli option:selected").attr('value');
    	getActiveFancyBet(fancyid);
    });
    
   
    
    $("#fancyend").change(function(){
    	
    	var fancyid = $("#fancynameli option:selected").attr('value'); var fancyid  =$("#fancyname").val();
  	  var start  =$("#fancystart").val();
	  var end  =$("#fancyend").val();
    	getFancyBetBySeries(fancyid);
    });
    
     $("#match").change(function(){
        
    	var market = $("#type option:selected").attr('name');
       if(market =="Fancy"){
    	 
    	   var evenid =  $("#match option:selected").attr('value');
    	   getActiveFncyList(evenid);
       }else{
    	  $("#marketnameli").show();
    	//  getActiveFncyList(evenid);
       }
    	 
    	 var selectedevent = $(this).children("option:selected").val();
        var matchaname =  $("#match option:selected").attr('name');
        $("#market").children('option').remove();
         $("#matchid").val(selectedevent).val();
       $("#matchname").val(matchaname).val();
    
         $("#marketid").val('').val();
         $("#marketname").val('').val(); 
         if($("#typeid").val()==='Fancy'){
         getOpenBets();
         }else{
         showMarketBymatch(selectedevent,true,true,matchaname);
         }
        
    });
    
     $("#market").change(function(){
        var selectedmarketid = $(this).children("option:selected").val();
        var marketname =  $("#market option:selected").attr('name');
        getOpenBets();
        $("#matchstartli").show();
     	$("#matchendli").show();
        $("#marketnameli").show();
        getActiveMatchBetList($("#market").val());
    });
    
     
   
     
    
    $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  });
    
  });
    	
    	 function getNewVal(item)
		{
		
    	$("#typeid").val(item.value).val();
		$("#sport").val($("#sport option:eq(0)").val());
		  $("#match").children('option').remove();
		$("#market").children('option').remove();
		 if(item.value==='Fancy'){
		  
		  $("#marketlable").hide();
		 $("#market").hide();
		}else{
		 
		 $("#marketlable").show();
		 $("#market").show();
		}
		   if(item.value=="Fancy"){
		    	$("#fancynameli").show();
		    }else{
		    	$("#fancynameli").hide();
		    	
		     	   $("#fancystart").html('');
		     	   $("#fancyend").html('');
		     	   $("#fancyname").html('');
		     	   $("#fancystartli").hide();
		     	   $("#fancyendli").hide();
		     	   
		     
		    	
		    }
		}
		
		function getSport(item)
		{
		$("#sportid").val(item.value).val();
		 $("#match").children('option').remove();
		$("#market").children('option').remove();
		
		showMatch("Match Odds",true);		    
		}
    	
    	 function showMatch(type,status){
		        
		    $.ajax({
		    					type:'GET',
		    					url:'<s:url value="api/getMatchBymarketname"/>?status='+status+'&sportid='+parseInt($("#sportid").val()),
		    					contentType:"application/json; charset=utf-8",
		    					success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200)
							{     
							$("#match").append($('<option> <a href="#" >Select Match</a> </option>'));
								  $.each(jsondata, function(index, element) {
							 	
					             $("#match").append($('<option id = "'+element.eventid+'" value="'+element.eventid+'" name = "'+element.matchname+'" > '+element.matchname+' </option>', {
					             			                
					            }));
					            			            			       
		        			});
						    	 
							}
							
						
								}
		    				});
		    
		    }
		    
		    
		     function showMarketBymatch(eventid,isactive,status,matchaname){
    //alert(sportid);
      $("#matchid").val(eventid).val();
     $("#matchname").val(matchaname).val();
    
    $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveMarketListByEvent"/>?eventid='+eventid+'&isactive='+isactive+'&status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
    						if(xhr.status == 200)
    						{     
    						$("#market").append($('<option> <a href="#" >Select Market</a> </option>'));
    							 // $.each(jsondata, function(index, element) {
    						 
    				             $("#market").append($('<option id = "'+jsondata.marketid+'" value="'+jsondata.marketid+'" name = "'+jsondata.marketname+'" > '+jsondata.marketname+' </option>', {
    				             			                
    				            }));
    				            			            			       
    	        			//});
    					    	 
    						}
    						
					
				
						}
    				});
    
    }
    
    function getOpenBets(action)
		{
    	$("#getopenBets").append('<div class="loader"></div>')
    	
		if(action == "next"){
    		page = page+1;    		
		}else if(action == "previous"){
    		page = page-1;
		}

		var fancyid =$("#fancyname").val();
					
					
					var data;
					if(${user.usertype} == 4){
				
						data = {
						"adminid":"${user.id}",
						"supermasterid":"-1",
						"subadminid":"-1",
						"isactive":"true",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":$("#matchid").val(),
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":$("#typeid").val(),
						"fancyid":fancyid
					};
					}else if(${user.usertype} == 5){
					data = {
						"adminid":"-1",
						"subadminid":"${user.id}",
						"supermasterid":"-1",
						"isactive":"true",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":$("#matchid").val(),
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":$("#typeid").val()
					};
					}else if(${user.usertype} == 0){
					data = {
						"adminid":"-1",
						"supermasterid":"${user.id}",
						"subadminid":"-1",
						"isactive":"true",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":$("#matchid").val(),
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":$("#typeid").val()
					};
					}else if(${user.usertype} == 1){
					data = {
						"adminid":"-1",
						"isactive":"true",
						"supermasterid":"-1",
						"subadminid":"-1",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":$("#matchid").val(),
						"isback":"-1",
						"sportid":"-1",
						"masterid":"${user.id}",
						"status":"-1",
						"dealerid":"-1",
				    	"page":page+"",
				    	"count":"200",
						"userid":"-1",
						"isdeleted":"false",
						"type":$("#typeid").val()
					};
					}else{
					data = {
						"adminid":"-1",
						"isactive":"true",
						"supermasterid":"-1",
						"subadminid":"-1",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":$("#matchid").val(),
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"${user.id}",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":$("#typeid").val()
					};
					}
					 
						$.ajax({
							type:'POST',
							url:'<s:url value="/api/getbets"/>',
							data: JSON.stringify(data),
							dataType: "json",
							contentType:"application/json; charset=utf-8",
							success: function(jsondata, textStatus, xhr) {
								if(xhr.status == 200)
								{

				    					$(".loader").fadeOut("slow");
				    								

				    				 $("#noOpenbets").hide();
				    				 $("#getopenBets").show();
				 					$("#getopenBets").html('');

			    					  var source   = $("#openbethandler").html();
			    			    	  var template = Handlebars.compile(source);
			   				    	  $("#getopenBets").append( template({data:jsondata}) );	

			   				    	  if(jsondata.length==200){
			   				    		  if(page>0){
					   				    		$("#previousBets").show();	   				    						   				    			  
			   				    		  }else{
					   				    		$("#previousBets").hide();	   				    			
			   				    		  }

			   				    			$("#nextBets").show();
			   				    		}else{
			   				    			$("#nextBets").hide();
			   				    			if(page!=0){
					   				    		$("#previousBets").show();		   				    				
			   				    			}
			   				    			 
			   				    		}
		   				}else{

		   				
			   			$(".loader").fadeOut("slow");
			   			$("#getopenBets").html(''); 	    		

		    				 $("#noOpenbets").show();
		    				 $("#getopenBets").hide();

			    			if(page!=0){
						    		$("#previousBets").show();		   				    				
				    			}
				    			$("#nextBets").hide();
		   				}

						
						}
					});
					
				}
				    	

 		 Handlebars.registerHelper('if', function(a, b, opts) {
		    if (a == b) {
		        return opts.fn(this);
		    } else {
		        return opts.inverse(this);
		    }
		});
  
 		 
 		function getActiveFncyList(evantid){
	        
		    $.ajax({
		    					type:'GET',
		    					url:'<s:url value="api/getActiveFncyList"/>?eventid='+evantid,
		    					contentType:"application/json; charset=utf-8",
		    					success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200)
							{     
							 $("#fancyname").append($('<option> <a href="#" >Select Fancy</a> </option>'));
								  $.each(jsondata, function(index, element) {
							 	
					             $("#fancyname").append($('<option id = "'+element.fancyid+'" value="'+element.fancyid+'" name = "'+element.name+'" > '+element.name+' </option>', {
					             			                
					            }));
					            			            			       
		        			}); 
		        		}
							
						}
		             });
 				}
 		
		function getActiveFancyBet(fancyid){
	        $("#fancystart").html('');
	        $("#fancyend").html('');
		    $.ajax({
		    					type:'GET',
		    					url:'<s:url value="api/getActiveFancyBet"/>?fancyid='+fancyid,
		    					contentType:"application/json; charset=utf-8",
		    					success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200)
							{     
							 $("#fancystart").append($('<option> <a href="#" >Select Start</a> </option>'));
							 $("#fancyend").append($('<option> <a href="#" >Select End</a> </option>'));
							 $("#delfancy").show();
								  $.each(jsondata, function(index, element) {
							 	
					             $("#fancystart").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
					             			                
					            }));
					            			            			       
		        			}); 
								  
						    $.each(jsondata, function(index, element) {
									 	
							             $("#fancyend").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
							             			                
							            }));
							});
						    
						    if(xhr.status == 200)
							{

			    					$(".loader").fadeOut("slow");
			    								

			    				 $("#noOpenbets").hide();
			    				 $("#getopenBets").show();
			 					$("#getopenBets").html('');

		    					  var source   = $("#openbethandler").html();
		    			    	  var template = Handlebars.compile(source);
		   				    	  $("#getopenBets").append( template({data:jsondata}) );	

		   				    	  if(jsondata.length==200){
		   				    		  if(page>0){
				   				    		$("#previousBets").show();	   				    						   				    			  
		   				    		  }else{
				   				    		$("#previousBets").hide();	   				    			
		   				    		  }

		   				    			$("#nextBets").show();
		   				    		}else{
		   				    			$("#nextBets").hide();
		   				    			if(page!=0){
				   				    		$("#previousBets").show();		   				    				
		   				    			}
		   				    			 
		   				    		}
	   				}else{

	   				
		   			$(".loader").fadeOut("slow");
		   			$("#getopenBets").html(''); 	    		

	    				 $("#noOpenbets").show();
	    				 $("#getopenBets").hide();

		    			if(page!=0){
					    		$("#previousBets").show();		   				    				
			    			}
			    			$("#nextBets").hide();
	   				}
		        		}
							else{
								$("#fancyend").html('');
								$("#fancystart").html('');
							}
							
						}
		             });
 				}
 		
		 function deleteFancyBetBySeries(){
	    	  var fancyid  =$("#fancyname").val();
	    	  var start  =$("#fancystart").val();
	    	  var end  =$("#fancyend").val();
			 swal({
	      	      title: 'Are you sure?',
	      	      text: 'You Want to DELETE SELECTED this Fancy!',
	      	      type: 'warning',
	      	      showCancelButton: true,
	      	      confirmButtonColor: '#3085d6',
	      	      cancelButtonColor: '#d33',
	      	      confirmButtonText: 'Yes',
	      	      closeOnConfirm: false
	      	    }).then(function(isConfirm) {
	      	      if (isConfirm) {		 	
	      	    	$("#fancy").html('<div class="loader"></div>')
	      	    	  $.ajax({
	  		 			type:'DELETE',
	  		 			url:'<s:url value="/api/deleteFancyBetBySeries?fancyid="/>'+fancyid+"&start="+start+"&end="+end,
	  					success: function(jsondata, textStatus, xhr) {
	  						showMessage(jsondata.message,jsondata.type,jsondata.title);
	  						
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	  						
	  				    } 
	  		 	});
	  		 	
	  		 	 } 
	  		 	});
	  		 }
		 
		 function deleteMarketBetBySeries(){
	    	  var market  =$("#market").val();
	    	  var start  =$("#matchstart").val();
	    	  var end  =$("#matchend").val();
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
	      	    	$("#fancy").html('<div class="loader"></div>')
	      	    	  $.ajax({
	  		 			type:'DELETE',
	  		 			url:'<s:url value="/api/deleteMarketBetBySeries?marketid="/>'+market+"&start="+start+"&end="+end,
	  					success: function(jsondata, textStatus, xhr) {
	  						showMessage(jsondata.message,jsondata.type,jsondata.title);
	  						
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	  						
	  				    } 
	  		 	});
	  		 	
	  		 	 } 
	  		 	});
	  		 }
		 
		 
		 function getFancyBetBySeries(){
	    	  var fancyid  =$("#fancyname").val();
	    	  var start  =$("#fancystart").val();
	    	  var end  =$("#fancyend").val();
			 		 	
	      	    	$("#fancy").html('<div class="loader"></div>')
	      	    	  $.ajax({
	  		 			type:'DELETE',
	  		 			url:'<s:url value="/api/getFancyBetBySeries?fancyid="/>'+fancyid+"&start="+start+"&end="+end,
	  					success: function(jsondata, textStatus, xhr) {
	  						if(xhr.status == 200)
							{

			    					$(".loader").fadeOut("slow");
			    								

			    				 $("#noOpenbets").hide();
			    				 $("#getopenBets").show();
			 					$("#getopenBets").html('');

		    					  var source   = $("#openbethandler").html();
		    			    	  var template = Handlebars.compile(source);
		   				    	  $("#getopenBets").append( template({data:jsondata}) );	

		   				    	  if(jsondata.length==200){
		   				    		  if(page>0){
				   				    		$("#previousBets").show();	   				    						   				    			  
		   				    		  }else{
				   				    		$("#previousBets").hide();	   				    			
		   				    		  }

		   				    			$("#nextBets").show();
		   				    		}else{
		   				    			$("#nextBets").hide();
		   				    			if(page!=0){
				   				    		$("#previousBets").show();		   				    				
		   				    			}
		   				    			 
		   				    		}
	   				}else{

	   				
		   			$(".loader").fadeOut("slow");
		   			$("#getopenBets").html(''); 	    		

	    				 $("#noOpenbets").show();
	    				 $("#getopenBets").hide();

		    			if(page!=0){
					    		$("#previousBets").show();		   				    				
			    			}
			    			$("#nextBets").hide();
	   				}

	  						
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						
	  				    } 
	  		 	});
	  		 	
	  		 	
	  		 }
    
		 function getActiveMatchBetList(marketid){
		        $("#matchstart").html('');
		        $("#matchend").html('');
			    $.ajax({
			    					type:'GET',
			    					url:'<s:url value="api/getActiveMatchBetList"/>?marketid='+marketid+"&isactive=true",
			    					contentType:"application/json; charset=utf-8",
			    					success: function(jsondata, textStatus, xhr) {
							
								if(xhr.status == 200)
								{     
								 $("#matchstart").append($('<option> <a href="#" >Select Start</a> </option>'));
								 $("#matchend").append($('<option> <a href="#" >Select End</a> </option>'));
								 $("#delmarketBet").show();
									  $.each(jsondata, function(index, element) {
								 	
						             $("#matchstart").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
						             			                
						            }));
						            			            			       
			        			}); 
									  
							    $.each(jsondata, function(index, element) {
										 	
								             $("#matchend").append($('<option id = "'+element.id+'" value="'+element.series+'" name = "'+element.series+'" > '+element.series+' </option>', {
								             			                
								            }));
								});
							    
							    if(xhr.status == 200)
								{

				    					$(".loader").fadeOut("slow");
				    								

				    				 $("#noOpenbets").hide();
				    				 $("#getopenBets").show();
				 					$("#getopenBets").html('');

			    					  var source   = $("#openbethandler").html();
			    			    	  var template = Handlebars.compile(source);
			   				    	  $("#getopenBets").append( template({data:jsondata}) );	

			   				    	  if(jsondata.length==200){
			   				    		  if(page>0){
					   				    		$("#previousBets").show();	   				    						   				    			  
			   				    		  }else{
					   				    		$("#previousBets").hide();	   				    			
			   				    		  }

			   				    			$("#nextBets").show();
			   				    		}else{
			   				    			$("#nextBets").hide();
			   				    			if(page!=0){
					   				    		$("#previousBets").show();		   				    				
			   				    			}
			   				    			 
			   				    		}
		   				}else{

		   				
			   			$(".loader").fadeOut("slow");
			   			$("#getopenBets").html(''); 	    		

		    				 $("#noOpenbets").show();
		    				 $("#getopenBets").hide();

			    			if(page!=0){
						    		$("#previousBets").show();		   				    				
				    			}
				    			$("#nextBets").hide();
		   				}
			        		}
								else{
									$("#matchyend").html('');
									$("#matchstart").html('');
								}
								
							}
			             });
	 				}
		 
    </script>