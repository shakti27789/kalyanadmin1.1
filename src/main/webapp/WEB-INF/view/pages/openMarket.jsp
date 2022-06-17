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
					<td>{{date}} <input type='hidden' id ='date{{id}}' value='{{date}}'</td>
					<td><button type="button" class="btn btn-info blue-btn btn-sm" onclick = "rollbackResult('{{id}}')">RollBack</button></td>
					 
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
           
           <div class="form-row mb-4 mb-lg-0">
				<div class="col-lg-8">
					<div class="form-row">
					   <div class="form-group col-md-3">
						   <label for="inputState">Sport</label>
						   <select id="sport" class="form-control" name="sport"> </select>
						 </div>
					   <div class="form-group col-md-3">
						   <label for="inputState">Match</label>
						   <select id="match" name="match" class="form-control">
						   </select>
						 </div>
					   <div class="form-group col-md-3">
						   <label for="market">Market</label>
						   <select id="market" name="market" class="form-control">
						  </select>
					   </div>
					   <div class="form-group col-md-3">
						   <label for="inputState">Selection</label>
						   <select id="selection" name="selection" class="form-control">
							 
						   </select>
					   </div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="form-row">
						<div class="acont-right col-md-3 form-group w-100">
							<label class="d-none d-lg-block">&nbsp;</label>
							<button type="button" class="btn" onClick="setmarketResult()">Result</button>
						</div>
						<div class="acont-right col-md-3 form-group w-100">
							<label class="d-none d-lg-block">&nbsp;</label>
							<button type="button"  class="btn" onclick ="setMatchAbndTie('abandoned')">Abnd</button>
						</div>
						<div class="acont-right col-md-3 form-group w-100">
							<label class="d-none d-lg-block">&nbsp;</label>
							<button type="button"  class="btn" onclick ="setMatchAbndTie('tie')" >Tie</button>
						</div>
					</div>
				</div>
             </div>
           </form>  
         </div>


         <!-- **********  First Table *************** -->
           <div class="account-table table-responsive" id="setresult">
           
           </div>

           <!-- *********** First table end  ************* -->

           <!-- ********** Second Table   ************* -->
           <div class="account-table table-responsive  market-bottom-table mt-4" id="tieorabondandmatch">
            

           </div>

           <!-- ************* second Table end ******************* -->

          

       </div>
       </div>
       
  


</div>

<!-- ************** end   ******************** -->
<script>
var subadminid;
var subadminappid;
$(document).ready(function(){
	resultList(true);
	AbondedOrTieMatchList();
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
	        var marketname =  $("#market option:selected").attr('name');
	         $("#selection").children('option').remove();
	         $("#marketid").val('').val();
	         $("#marketname").val('').val(); 
	         $("#selectionid").val('').val();
	     	 $("#selectioname").val('').val();
	         showSelectionBymarket(selectedmarketid,marketname);
	    });
	    
	    $("#selection").change(function(){
	        var selecteid = $(this).children("option:selected").val();
	        var selectioname =  $("#selection option:selected").attr('name');
	        $("#selectionid").val('').val();
	     	$("#selectioname").val('').val();
	        $("#selectionid").val(selecteid).val();
	     	$("#selectioname").val(selectioname).val();
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

function resultList(status){
		$.ajax({
			type:'GET',
			url:'<s:url value="/api/getActiveMarkerResultList"/>?status='+status,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{     
					$("#setresult").show();
				    $("#setresult").html('');
								
					  var source   = $("#marketsetresulthandler").html();
			    	  var template = Handlebars.compile(source);
				    	  $("#setresult").append( template({data:jsondata}) );
					}
		}
	});
}

function AbondedOrTieMatchList(){
		$.ajax({
			type:'GET',
			url:'<s:url value="/api/AbondedOrTieMatchList"/>',
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{     
					$("#notieorabondandmatch").hide();
					$("#tieorabondandmatch").show();
				$("#tieorabondandmatch").html('');
					console.log(jsondata.data)				
					  var source   = $("#abpndedortiematchhandler").html();
			    	  var template = Handlebars.compile(source);
				      $("#tieorabondandmatch").append( template({data:jsondata}) );
					}else{
					$("#notieorabondandmatch").show();
					$("#tieorabondandmatch").hide();
					}
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
    						$("#market").append($('<option value=""> <a href="#" >Select Market</a> </option>'));
    						 $.each(jsondata, function(index, element) {
    							 $("#market").append($('<option id = "'+element.marketid+'" value="'+element.marketid+'" name = "'+element.marketname+'" > '+element.marketname+' </option>', {
    							 }));	      	 
    						 });
    						 
    				     }
    				  }
    				});
                 }
    
    
     function showSelectionBymarket(marketid,marketname){
     $("#marketid").val(marketid).val();
     $("#marketname").val(marketname).val();
    
               $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getSelctionListByMarketId"/>?marketid='+marketid,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					if(xhr.status == 200){     
					$("#selection").append($('<option value=""> <a href="#" >Select Team</a> </option>'));
						  $.each(jsondata, function(index, element) {
					      $("#selection").append($('<option id = "'+element.selectionid+'" value="'+element.selectionid+'" name = "'+element.runnerName+'" > '+element.runnerName+' </option>', {
			           }));
			       });
				 }
				}
    		});
         }
     function setmarketResult()
     {		
    	 var $valid = $("#openmarkrt").valid();
    	  	if($valid){
    	  		
    	  	     	      	 var data={marketid:$("#marketid").val(),marketname:$("#marketname").val(),sportid:parseInt($("#sport option:selected").val())
    	  	     	    	 ,sportname:$("#sport option:selected").attr("name"),matchid:parseInt($("#match").val()),matchname:$("#matchname").val()
    	  	     	    	 ,selectionid:parseInt($("#selectionid").val())
    	  	     	    			 ,selectionname:$("#selectioname").val(),result:parseInt($("#selectionid").val()),markettype:"Odds",subadminid:subadminid,appid:subadminappid}
    	  	     	    
    	  	     	    swal({
    	  	     	      title: 'Are you sure?',
    	  	     	      text: 'You Want to set Result of this Match!',
    	  	     	      type: 'warning',
    	  	     	      showCancelButton: true,
    	  	     	      confirmButtonColor: '#3085d6',
    	  	     	      cancelButtonColor: '#d33',
    	  	     	      confirmButtonText: 'Yes',
    	  	     	      closeOnConfirm: false
    	  	     	    }).then(function(isConfirm) {
    	  	     	      if (isConfirm) {	
    	  	     	
    	  	     	      	 $(".account-table").html('<div class="loader"></div>');
	
    	  	     	    	
    	  	     	    	 $.ajax({
    	  	     					type:'POST',
    	  	     					url:'<s:url value="/api/setMarketResult"/>',
    	  	     					data: JSON.stringify(data),
    	  	     					dataType: "json",
    	  	     					contentType:"application/json; charset=utf-8",
    	  	     					success: function(jsondata, textStatus, xhr) {
    	  	     					$("#sport").val($("#sport option:first").val());
    	  	 				        $("#match").children('option').remove();
    	  	 				         $("#market").children('option').remove();
    	  	 				        $("#selection").children('option').remove();
    	  	 				         $("#sportidtext").val('').val();
    	  	 				         $("#sportnametext").val('').val(); 
    	  	 				         
    	  	 				         $("#matchid").val('').val();
    	  	 				        $("#matchname").val('').val();
    	  	 				         $("#marketid").val('').val();
    	  	 				         $("#marketname").val('').val(); 
    	  	 				         $("#selectionid").val('').val();
    	  	 				     		$("#selectioname").val('').val();
    	  	     						showMessage(jsondata.message,jsondata.type,jsondata.title);
    	  	     						resultList(true);
    	  	     						
    	  	     					},
    	  	     					complete: function(jsondata,textStatus,xhr) {
    	  	     						 $(".loader").fadeOut("slow");
    	  	     						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    	  	     						 
    	  	     				    } 
    	  	     				});
    	  	     				
    	  	     				
    	  	     				 } 
    	  	     				});
    	  	     	    
    	  	    	
    	  	}
    	
     }
     
     
     function rollbackResult(id) {
 		
    	   	var data={id:id,marketid:$("#marketid"+id).val(),marketname:$("#marketname"+id).val(),sportid:parseInt($("#sportid"+id).val())
    	    	    	 ,sportname:$("#sportname"+id).val(),matchid:parseInt($("#matchid"+id).val()),matchname:$("#matchname"+id).val()
    	    	    	 ,selectionid:parseInt($("#selectionid"+id).val())
    	    	    			 ,selectionname:$("#selectionname"+id).val(),result:parseInt($("#selectionid"+id).val()),markettype:"Odds",subadminid:subadminid,appid:subadminappid}
    	    	   swal({
    	    	      title: 'Are you sure?',
    	    	      text: 'You Want to Rollback this Market Result!',
    	    	      type: 'warning',
    	    	      showCancelButton: true,
    	    	      confirmButtonColor: '#3085d6',
    	    	      cancelButtonColor: '#d33',
    	    	      confirmButtonText: 'Yes',
    	    	      closeOnConfirm: false
    	    	    }).then(function(isConfirm) {
    	    	      if (isConfirm) {	 	
    	    	      //$(".account-container").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while rolling back the match Result. Do not Refresh the Page.</span></center></div>') 
    	    	    	 $(".account-table").html('<div class="loader"></div>');
	
	
    	    	    	 $.ajax({
    	    					type:'DELETE',
    	    					//url:'<s:url value="/api/roolbackMarketResult"/>',
    	    					//url:'<s:url value="/api/rollbackResultMarket"/>',
    	    					url:'<s:url value="/api/rollbackMarketNew"/>',
    	    					data: JSON.stringify(data),
    	    					dataType: "json",
    	    					contentType:"application/json; charset=utf-8",
    	    					success: function(jsondata, textStatus, xhr) {
    	    					$(".loader").fadeOut("slow");
    	    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    	    						resultList(true);
    	    					},
    	    					complete: function(jsondata,textStatus,xhr) {
    	    					$(".loader").fadeOut("slow");
    	    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    	    						 
    	    				    } 
    	    				});
    	    				
    	    				 } 
    	    	    });
    	    }
     
     function setMatchAbndTie(matchstatus){
 		
 	    
 	 
    	 var $valid = $("#openmarkrt").valid();
 	  	if($valid){
 	  		
 	  	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to set Result of this Match'+matchstatus,
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
    	  
    	    	  $("#setresult").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while setting the match Result. Do not Refresh the Page.</span></center></div>') 
	     	    	  
    	    	
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="/api/abandendTieMarket"/>?marketid='+$("#market").val()+'&matchstatus='+matchstatus+"&matchid="+$("#match").val(),
    					
    					success: function(jsondata, textStatus, xhr) {
    					$("#sport").val($("#sport option:first").val());
				        $("#match").children('option').remove();
				         $("#market").children('option').remove();
				        $("#selection").children('option').remove();
				         $("#sportidtext").val('').val();
				         $("#sportnametext").val('').val(); 
				         
				         $("#matchid").val('').val();
				        $("#matchname").val('').val();
				         $("#marketid").val('').val();
				         $("#marketname").val('').val(); 
				         $("#selectionid").val('').val();
				     		$("#selectioname").val('').val();
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    						AbondedOrTieMatchList();
	     						
    						
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						 $(".loader").fadeOut("slow");
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						 
    				    } 
    				});
    			} 
    		});
 	  	}
 	    	   
 	    	    
 	 }
     
     function abandendTieMarketRollBack(marketid,matchstatus){
 		
 	    
      	 
 	    swal({
 	      title: 'Are you sure?',
 	      text: 'You Want to rollBack Match',
 	      type: 'warning',
 	      showCancelButton: true,
 	      confirmButtonColor: '#3085d6',
 	      cancelButtonColor: '#d33',
 	      confirmButtonText: 'Yes',
 	      closeOnConfirm: false
 	    }).then(function(isConfirm) {
 	      if (isConfirm) {	
 	    	 $("#setresult").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while setting the match Result. Do not Refresh the Page.</span></center></div>') 
  	    	  
 	    	 $.ajax({
 					type:'POST',
 					url:'<s:url value="/api/abandendTieMarketRollBack"/>?marketid='+marketid+'&matchstatus='+matchstatus,
 					
 					success: function(jsondata, textStatus, xhr) {
 					$("#sport").val($("#sport option:first").val());
				        $("#match").children('option').remove();
				         $("#market").children('option').remove();
				        $("#selection").children('option').remove();
				         $("#sportidtext").val('').val();
				         $("#sportnametext").val('').val(); 
				         
				         $("#matchid").val('').val();
				        $("#matchname").val('').val();
				         $("#marketid").val('').val();
				         $("#marketname").val('').val(); 
				         $("#selectionid").val('').val();
				     		$("#selectioname").val('').val();
 						showMessage(jsondata.message,jsondata.type,jsondata.title);
 						AbondedOrTieMatchList();
   						
 						
 					},
 					complete: function(jsondata,textStatus,xhr) {
 						 $(".loader").fadeOut("slow");
 						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
 						 
 				    } 
 				});
 				
 			} 
 		});
 	    
	 
}

     
  
</script>

   <jsp:include page="handlebars.jsp" /> 
