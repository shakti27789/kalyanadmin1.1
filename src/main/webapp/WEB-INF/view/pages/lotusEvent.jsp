<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

    
     <script id="eventlotushandle" type="text/x-handlebars-template">
<div class="back_wrap">
          <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='backtoShowLotusSport()'>Back</a>
</div>
<div class="table table-striped table-bordered account-stment">
             	<table class="table common-table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Match Name</th>
                    
					<th>Open Date</th>
					<th>Odds Type</th>
					<th>Fancy Type</th>
					<th>Ball Chalu Status</th>
                    <th>Action</th>
					<th>Min Bet</th>
					<th>Max Bet</th>
					<th>Bet Delay</th>
					<th>Team Id</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{eventid}}<input type='hidden' id ='eventid{{eventid}}' value='{{eventid}}'></td>
                   	   <td>{{eventname}}<input type='hidden' id ='eventname{{eventid}}' value='{{eventname}}'></td>
                    	
   						<td>{{opendate}}<input type='hidden' id =openDate{{eventid}} value='{{opendate}}'></td>
						<td><div class="filterSection full-width-form"><select id ='oddsprovider{{eventid}}' class="modal-background" onchange=updateoddsProvider("{{eventid}}")>
							<option ="-1">Select Odds Provider</option>							
							<option ="Betfair">Betfair</option>
							<option ="Betfair">Diamond</option>
							</select></div></td>

							<td>
							<div class="filterSection full-width-form"><select id ='fancyProvider{{eventid}}' class="modal-background" onchange=updatefancyProvider("{{eventid}}")>
							<option ="-1">Select Odds Provider</option>							
							<option ="Diamond">Daimond</option>
							<option ="Sky">Sky</option>
							</select>
							</div>
							</td>
					
						<td><div class="filterSection full-width-form"><select id ='exchnagematch{{eventid}}' class="modal-background exchnagematch" onchange=updateCricletExchnageId("{{eventid}}")></select></div></td>
						<td>
							<input type="checkbox" id="addLotusEventcheckbox{{id}}"  onclick='addLotusEvent("{{sportid}}","{{eventid}}","{{id}}")'>
							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=lotusmarketByEvent("{{eventid}}","{{id}}","{{fancyprovider}}")>FancyList</a> 

{{#if_eq addautoFancy false}}                      
  <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=addAutoFancyOneClick("{{eventid}}")>Add Auto Fany</a> 
	{{else}}
			 <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=removeAutoFancyOneClick("{{eventid}}")>Remove Auto Fany</a> 
	
			{{/if_eq}}											
</td>
 <td>
						<div class="filterSection full-width-form">
							<select id =min{{eventid}} onchange=updateMinMaxBetDelay("{{eventid}}","minbet")></select>	
						</div>
						</td>
 						<td>
						<div class="filterSection full-width-form">
							<select id =max{{eventid}} onchange=updateMinMaxBetDelay("{{eventid}}","maxbet")></select>	
						</div>
						</td>
                        <td><div class="filterSection full-width-form">
							<select id =betdelay{{eventid}} onchange=updateMinMaxBetDelay("{{eventid}}","betdelay")></select>	
						</div>
 						
					<td>
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=teamId("{{eventid}}","{{marketId}}")>Team Id</a> 
					</td>
                   </tr>
 			{{/each}}           
                </tbody>
              </table>
</div>
            
    </script>
  
    <script>
    
    
      $(document).ready(function(){
    	
    }); 
    
    
    function addLotusEvent(sportid,eventid,id)
    {		
    	     isChecked =  $("#addLotusEventcheckbox"+id).is(":checked");
    	    var val = $("#exchnagematch"+eventid).children("option:selected").val();
   		 var isunmatched= false;
		 if($("#isUnmatched"+id).is(":checked")){
			 isunmatched = true;
		 }
		 
    	     if(isChecked){ 
    	    	
    	    	 var data={sportid:sportid,eventid:eventid,cricexchageid:val,unmatched:isunmatched}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Event!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	      $("#lotusmatch").append('<div class="loader"></div>')
    	       $.ajax({
    					type:'POST',
    					url:'<s:url value="api/addLotusEvent"/>',
    					data:data,
    					success: function(jsondata, textStatus, xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findMarketByStatus();
    				    } 
    				});
    	    	
    				
    				 } else{
    				$("#addLotusEventcheckbox"+id).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	 
    	    	 var data={marketid:1+"."+id,eventid:eventid}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Event!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	 $("#lotusmatch").append('<div class="loader"></div>')
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateLotusEvent"/>',
    					data: data,
    					success: function(jsondata, textStatus, xhr) {
    					 $(".loader").fadeOut("slow");
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    					 $(".loader").fadeOut("slow");
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						findMarketByStatus();
    				    } 
    				});
    				
    				}
    				else{
    				$("#addLotusEventcheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
    	 
    }
    
    
    
    
    
    function addAutoFancyOneClick(eventid,id)
    {		
    	  
    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Active Auto Facny!',
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
    					url:'<s:url value="/api/addAutoFancyOneClick"/>?eventid='+eventid,
    					success: function(jsondata, textStatus, xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						//findMarketByStatus();
    						 lotusEvent(4);
    				    } 
    				});
    	    	
    				
    				 } else{
    				$("#addLotusEventcheckbox"+id).prop("checked", false);
    				 }
    				});
    	    
    	    
    	 
    }
    
    function removeAutoFancyOneClick(eventid)
    {		
    	  
    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to De-Active Auto Facny!',
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
    					url:'<s:url value="/api/removeAutoFancyOneClick"/>?eventid='+eventid,
    					success: function(jsondata, textStatus, xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    					$(".loader").fadeOut("slow");
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						//findMarketByStatus();
    						 lotusEvent(4);
    				    } 
    				});
    	    	
    				
    				 } else{
    				$("#addLotusEventcheckbox"+id).prop("checked", false);
    				 }
    				});
    	    
    	    
    	 
    }

     function lotusEvent(id){
     	$("#betfairsport").hide();
        	isChecked =  $("#sportidcheckbox"+id).is(":checked");
    	     $("#sportidtext").val($("#sportid"+id).val())
    	     if(isChecked){
    	    	 $("#lotussport").hide();
    	    	 $("#lotusmatch").html('');
    	    	 $("#lotusmatch").show();
    	    	  $("#lotusmatch").append('<div class="loader"></div>')
    	    		$.when(
    	    			   	
    	    				$.ajax({
    	    							type:'GET',
    	    							url:'<s:url value="api/lotusMatchList?sportid="/>'+id,
    	    							success: function(jsondata, textStatus, xhr) {
    	    							{ 
    	    								console.log(jsondata.result)
    	    								if(xhr.status == 200)
    	    								{     
    	    								   $(".loader").fadeOut("slow");
    	    									  var source   = $("#eventlotushandle").html();
    	    							    	  var template = Handlebars.compile(source);
    	    							    	  $("#lotusmatch").append( template({data:jsondata}) );
    	    							    	  loadCricMatches(); 
    	    							    	
    	    							    	  $.each(jsondata, function( key, value ) {
    	    	    					    		  getMinBet("minbet",value.eventid,value.minbet);
    	    	    					    		  getMaxBet("maxbet",value.eventid,value.maxbet);
    	    	    					    		  getBetDelay("betdelay",value.eventid,value.betdelay); 
    	    	    					    		 /*  daimondFancy(value.eventid);
    	    	    					    		  skyFancy(value.eventid); */
    	    	    					    		});
    	    							    	 
    	    								}else{
    	    								 $(".loader").fadeOut("slow");
    	    								}
    	    								
    	    							}
    	    						}
    	    					})).then(function( x ) {
    	    						findMarketByStatus()
    	    			     		});
    	     }
        }
        
         function backtoShowLotusSport(){
        	
    	    	 $("#lotussport").show();
    	    	 $("#lotusmatch").hide();
        }
        
        
        function loadCricMatches(){
        $.ajax({
    		type:'GET',
    		url:'<s:url value="api/cricketExchMatch"/>',
    		contentType:"application/json; charset=utf-8",
    		success: function(jsondata, textStatus, xhr) {
				console.log(jsondata);
					if(xhr.status == 200)
					{  
					 
					$(".exchnagematch").append($('<option value="-1"> <a href="#" >Select Match</a> </option>'));
						  $.each(jsondata, function(index, element) {
					 
			             $(".exchnagematch").append($('<option id = "'+element.cricexchageid+'" value="'+element.cricexchageid+'" name = "'+element.matchname+'"  > '+element.matchname+' </option>', {
			             			                
			            }));
			            			            			       
        			});
				    	 
					}
					
				
			}
    				});
    	    
        }
        
		Handlebars.registerHelper('if_eq', function(a, b, opts) {
		    if (a == b) {
		        return opts.fn(this);
		    } else {
		        return opts.inverse(this);
		    }
		});
		
		 function getMinBet(type,eventid,minbet) {

		    	var i=0;
			 	 $.ajax({
			   	 
						type:'GET',
						url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							
						   $.each(jsondata, function( key, value ) {
							
								if(minbet ==value.amount){
									  $("#min"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
									   $("#minfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
									   $("#minbetBookmaker"+eventid).append('<option value="-1">Min Bet</option>');
								}else{
									   $("#min"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#minfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#minbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
								}  
						
							
						});
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					});     	    	
		    }
		    function getMaxBet(type,eventid,maxbet) {

		    	var i=0;
			 	 $.ajax({
			   	 
						type:'GET',
						url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							
						   $.each(jsondata, function( key, value ) {
								if(maxbet ==value.amount){
									  $("#max"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						 			  $("#maxfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						 			  $("#maxbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						 			  $("#playermaxbetfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						 			  $("#maxbetBookmaker2"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
								}else{
									   $("#max"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#maxfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#maxbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#playermaxbetfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									     $("#maxbetBookmaker2"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
								}  
						});
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					});     	    	
		   }
		    
		    function getBetDelay(type,eventid,betdelay) {
		    	
		    	var i=0;
			 	 $.ajax({
			   	 
						type:'GET',
						url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							
						   $.each(jsondata, function( key, value ) {
								if(betdelay ==value.amount){
									  $("#betdelay"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
									   $("#betdelayfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
									   $("#betdelayBookmaker"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
										  
								}else{
									   $("#betdelay"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#betdelayfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
									   $("#betdelayBookmaker"+eventid).append('<option value="'+value.amount+'" >'+value.amount+'</option>')
										
								}  
						});
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					});     	    	
		  }
		    function updateMinMaxBetDelay(eventid,type){
		      var text =""
		      var url ="";

		    	if(type=="minbet"){
		    		text='You Want to Change Min Bet Amount!';
		    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=minbet&amount="+$("#min"+eventid).val();
		    	}else if(type=="maxbet"){
		    		text='You Want to Change Max Bet Amount!';
		    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=maxbet&amount="+$("#max"+eventid).val();
		    	}else if(type=="betdelay"){
		    		text='You Want to Change Bet Delay Time!';
		    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=betdelay&amount="+$("#betdelay"+eventid).val();
		    	}
		    
		    			   	 swal({
	    	      title: 'Are you sure?',
	    	      text: text,
	    	      type: 'warning',
	    	      showCancelButton: true,
	    	      confirmButtonColor: '#3085d6',
	    	      cancelButtonColor: '#d33',
	    	      confirmButtonText: 'Yes',
	    	      closeOnConfirm: false
	    	    }).then(function(isConfirm) {
	    	      if (isConfirm) {
	    	    	  $("#lotusmatch").append('<div class="loader"></div>')
	    	    	  $.ajax({
	    					type:'POST',
	    					url:url,
	    					success: function(jsondata, textStatus, xhr) {
	    					$(".loader").fadeOut("slow");
	    						showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					},
	    					complete: function(jsondata,textStatus,xhr) {
	    					$(".loader").fadeOut("slow");
	    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	    						
	    				    } 
	    				});
	    	    	
	    			} 
	    	         else{
	    	        	 $.ajax({
	    				   	 
	 						type:'GET',
	 						url:'<s:url value="/api/getevent"/>?eventid='+eventid,
	 						contentType:"application/json; charset=utf-8",
	 						success: function(jsondata, textStatus, xhr) {
	 						
	 							
	 						
	 							if(type=="minbet"){
	 								$("#min"+eventid).val(jsondata.minbet)
	 							}else if(type=="maxbet"){
	 								$("#max"+eventid).val(jsondata.max)
	 							}else if(type=="betdelay"){
	 								$("#betdelay"+eventid).val(jsondata.betdelay)
	 							}
	 						},
	 						complete: function(jsondata,textStatus,xhr) {
	 							
	 					    } 
	 					});    
	    				 }
	    		});
		    			   	 
		    	
		    }
		   
		    
		    function updateCricletExchnageId(eventid){


			   	 swal({
     title: 'Are you sure ',
     text: 'You Want to sun Score?',
     type: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes',
     closeOnConfirm: false
   }).then(function(isConfirm) {
     if (isConfirm) {
   	  $("#lotusmatch").append('<div class="loader"></div>')
   	  ;
   	  $.ajax({
				type:'POST',
				url:'<s:url value="/api/updateCricketExchnageScorreId"/>?eventid='+eventid+"&cricexchageid="+$("#exchnagematch"+eventid).val(),
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			    } 
			});
   	
		} 
        else{
       	
			 }
	});
}
		    
		    
		    function updateoddsProvider(eventid){


			   	 swal({
     title: 'Are you sure ',
     text: 'You Want to chage odds Provider?',
     type: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes',
     closeOnConfirm: false
   }).then(function(isConfirm) {
     if (isConfirm) {
   	  $("#lotusmatch").append('<div class="loader"></div>');
   	  if($("#oddsprovider"+eventid).val()!="-1"){
   		  $.ajax({
				type:'POST',
				url:'<s:url value="/api/updateoddsProvider"/>?eventid='+eventid+"&oddsprovider="+$("#oddsprovider"+eventid).val(),
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			    } 
			});
   	  }else{
   		showMessage("Please Select Valid Odds Provider","success","Success");
   	  }
   
   	
		} 
        else{
       	
			 }
	});
}	  
		    
		    function updatefancyProvider(eventid){


			   	 swal({
     title: 'Are you sure ',
     text: 'You Want to change Fancy Provider?',
     type: 'warning',
     showCancelButton: true,
     confirmButtonColor: '#3085d6',
     cancelButtonColor: '#d33',
     confirmButtonText: 'Yes',
     closeOnConfirm: false
   }).then(function(isConfirm) {
     if (isConfirm) {
   	  $("#lotusmatch").append('<div class="loader"></div>');
   	  if($("#fancyProvider"+eventid).val()!="-1"){
   		  $.ajax({
				type:'POST',
				url:'<s:url value="/api/updatefancyProvider"/>?eventid='+eventid+"&fancyprovider="+$("#fancyProvider"+eventid).val(),
				success: function(jsondata, textStatus, xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
				$(".loader").fadeOut("slow");
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					
			    } 
			});
   	  }else{
   		showMessage("Please Select Valid Odds Provider","success","Success");
   	  }
   } 
        else{
       	
			 }
	});
}	
		    
		    function daimondFancy(eventid){
			
		    	var i=0;
			 	 $.ajax({
			   	 
						type:'GET',
						url:'http://http://161.35.107.2/getSession/eventid/Diamond',
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							
						   $.each(jsondata, function( key, value ) {
							   $("#daimondfancy"+eventid).append('<option value="'+value.fancyId+'" selected>'+value.name+'</option>');
						});
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					}); 
			    }
			   
		    function skyFancy(eventid,fancyprovider){
				 
		    	var url ="";
		    	 if(fancyprovider == "Diamond"){
		    		 matchfancy ="skyexhange";
		    		// url ='http://161.35.107.2//ldSession?eventid=/'+eventid;
		    		//	url ='https://www.lotusbook.io/api/member/exchange/event/LIOD/4/'+eventid;
		    			 url ='http://161.35.107.2/getSession/'+eventid+'/'+matchfancy;
		    	 }else{
		    		 matchfancy ="Diamond";
		    		 url ='http://161.35.107.2/getSession/'+eventid+'/'+matchfancy;
		    	 }
		    	var i=0;
			 	 $.ajax({
			   	 
						type:'GET',
						url:url,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							
						   $.each(jsondata, function( key, value ) {
							   $("#skyfancy"+eventid).append('<option value="'+value.id+'" selected>'+value.name+'</option>');
						});
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					}); 
			    }
		    function teamId(eventid,marketid){
		    $.ajax({
			   	 
				type:'GET',
				url:'<s:url value="/api/teamId"/>?marketid='+marketid,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					$("#teamidModaldiv").html('');
					if(jsondata.length>2){
					$("#teamidModaldiv").append('<span id="team1">'+jsondata[0].runnerName+':  ' +jsondata[0].selectionid+ '</span><br><br><span id="team2">'+jsondata[1].runnerName+':' +jsondata[1].selectionid+ '</span><br><br><span id="team2">'+jsondata[2].runnerName+':' +jsondata[2].selectionid+ '</span><br><br><span id="matchid">Matchid:  ' +eventid+ '</span><br><br><span id="marketid">Marketid:' +marketid+ '</span><br>');
						
					}else{
						$("#teamidModaldiv").append('<span id="team1">'+jsondata[0].runnerName+':  ' +jsondata[0].selectionid+ '</span><br><br><span id="team2">'+jsondata[1].runnerName+':' +jsondata[1].selectionid+ '</span><br><br><span id="matchid">Matchid:  ' +eventid+ '</span><br><br><span id="marketid">Marketid:' +marketid+ '</span><br>');
					}
					$("#teamidModal").modal('show');
				  
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			})
		   }

		 
    </script>
    
     
    
    <div id="teamidModal" class="modal fade-scale" role="dialog">
  		<div class="modal-dialog">

		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-body">
		        <div id="teamidModaldiv">
		          
			    </div>
		      </div>
		  </div>
		</div>
	</div>