  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>

<script>

$(document).ready(function(){
	
	netExposureEventStatus();
	selectedBetMaximumBetSBA();
	getSelectedBookMakerBackLayDiff();
	checkBookMakerBackLayDiff();
	
	
 });
function fancyLock(){
	
		$.ajax({
   			type:'POST',
   			url:'<s:url value="/api/fancyLock/"/>'+'${eventid}',
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{   
    					showMessage(jsondata.message,jsondata.type,jsondata.title);
    					matchFancyStatus();
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}

 });
}

 function fancyUnLock(){
 	
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/fancyUnLock/"/>'+'${eventid}',
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					matchFancyStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}


 
 function matchbetLockSBA(){
   	
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/sbaBetLock?eventid="/>'+${eventid}+"&betlock=true",
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					sbaBetLockStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
 }
 
 function fancyLockSBA(){
		$.ajax({
   			type:'POST',
   			url:'<s:url value="/api/sbaFancyBetLock?eventid="/>'+${eventid}+"&fancybetlock=true",
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{   
    					showMessage(jsondata.message,jsondata.type,jsondata.title);
    					sbaFancyBetLockStatus();
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}

 });
}
 
 function matchbetUnLockSBA(){
    	
		$.ajax({
   			type:'DELETE',
   			url:'<s:url value="/api/sbaBetUnLock?eventid="/>'+${eventid},
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{   
    					showMessage(jsondata.message,jsondata.type,jsondata.title);
    					sbaBetLockStatus();
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}

 });
}
 
 function fancyUnLockSBA(){
 	
		$.ajax({
	   			type:'DELETE',
	   			url:'<s:url value="/api/sbaFancyBetUnLock?eventid="/>'+${eventid},
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					sbaFancyBetLockStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}
 
 

 function matchbetUnLock(){
  	
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/betUnLockMatch/"/>'+'${eventid}',
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					matchFancyStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}
 
 function sbaBetLockStatus(){
	 $.ajax({
   			type:'POST',
   			url:'<s:url value="/api/sbaBetLockStatus?eventid="/>'+${eventid},
  			success: function(jsondata, textStatus, xhr) {
  				//console.log("sbaBetLockStatus: "+jsondata)
    				if(xhr.status == 200)
    				{   
    					if(${user.usertype} == 5){
    					    if(jsondata.betlock){
    					    	$("#matchbetLockSBA").hide();
                                $("#matchbetUnLockSBA").show();
    					    } else {
    					    	$("#matchbetLockSBA").show();
                                $("#matchbetUnLockSBA").hide();
    					    } 
    					 }
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}
 });
}
 
 function sbaFancyBetLockStatus(){
	 $.ajax({
   			type:'POST',
   			url:'<s:url value="/api/sbaFancyBetLockStatus?eventid="/>'+${eventid},
  			success: function(jsondata, textStatus, xhr) {
  				//console.log("sbaBetLockStatus: "+jsondata)
    				if(xhr.status == 200)
    				{   
    					if(${user.usertype} == 5){
    					    if(jsondata.fancybetlock){
    					    	$("#fancyLockSBA").hide();
                                $("#fancyUnLockSBA").show();
    					    } else {
    					    	$("#fancyLockSBA").show();
                                $("#fancyUnLockSBA").hide();
    					    } 
    					 }
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}
 });
}

 function updateSBANetExposure(){
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/updateSBANetExposure?eventid="/>'+${eventid}+"&netExposure="+netexposure,
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					netExposureEventStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
 }

 function netExposureEventStatus(){
	 $.ajax({
   			type:'POST',
   			url:'<s:url value="/api/sbaNetExposureStatus?eventid="/>'+${eventid},
  			success: function(jsondata, textStatus, xhr) {
  				//console.log("sbaNetExposureStatus: "+jsondata)
    				if(xhr.status == 200)
    				{   
    					if(${user.usertype} == 5){
    					    if(jsondata.userid != null){
    					    	$("#removeSBANetExposure").show();
                                $("#updateSBANetExposure").hide();
    					    } else {
    					    	$("#updateSBANetExposure").show();
                                $("#removeSBANetExposure").hide();
    					    } 
    					 }
   					}else{
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					}
    		}
 });
}
 function updateMinMaxEvent(){
		$.ajax({
	   			type:'POST',
	   			url:'<s:url value="/api/updateSBAMinMaxEvent?eventid="/>'+${eventid}+"&amount="+amountvalue,
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					minMaxEventStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}

 function removeMinMaxEvent(){
		$.ajax({
	   			type:'DELETE',
	   			url:'<s:url value="/api/removeSBAMinMaxEvent?eventid="/>'+${eventid},
	  			success: function(jsondata, textStatus, xhr) {
	    				if(xhr.status == 200)
	    				{   
	    					showMessage(jsondata.message,jsondata.type,jsondata.title);
	    					minMaxEventStatus();
	   					}else{
	   						showMessage(jsondata.message,jsondata.type,jsondata.title);
	   					}
	    		}
	
	 });
}

 function betMaximumBet(maxBet) {
	 //  console.log("maxbet==>"+maxBet) 
	 $.ajax({
  	 
			type:'GET',
			url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=maxbet',
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
			   $.each(jsondata, function( key, value ) {
				
					   if(parseInt(maxBet) == parseInt(value.amount)){
					  $("#maximumbet").append('<option value="'+value.amount+'" selected> '+value.amount+'</option>')
					   $("#resetmaximumbetdiv").append('<button style="" onclick="resetmaximumbetSBA()" id="resetmaximumbet">Reset</button>');
					   $("#maximumbethiden").val("true")
					   
						   }else{
					   $("#maximumbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					   $("#maximumbethiden").val("false")
					   }
				  
			});
			},
			complete: function(jsondata,textStatus,xhr) {
				// $("#maximumbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
						
		    } 
		});     	    	
}



 function selectedBetMaximumBetSBA() {
 	
 	var i=0;
 	 $.ajax({
  	 
 			type:'GET',
 			url:'<s:url value="/api/selectedBetMaximumBetSBA/"/>'+${eventid},
 			contentType:"application/json; charset=utf-8",
 			success: function(jsondata, textStatus, xhr) {

 				if(xhr.status==200){
 					betMaximumBet(jsondata.maxbet);
 					}else{
 						betMaximumBet(0);
 						}
 				
 			},
 			complete: function(jsondata,textStatus,xhr) {
 				
 		    } 
 		});     	    	
 }

 function updateSBAMinMaxEvent(){

 	 swal({
 	title: 'Are you sure?',
 	text: 'You Want to Update Maximum Amount',
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
 		url :'<s:url value="/api/updateSBAMinMaxEvent/"/>'+${eventid}+"/"+$("#maximumbet").val(),
 		success: function(jsondata, textStatus, xhr) {
 		$(".loader").fadeOut("slow");
 			showMessage(jsondata.message,jsondata.type,jsondata.title);
 			   if(!$("#maximumbethiden").val()){
 				   $("#resetmaximumbetdiv").append('<button style="" onclick="resetmaximumbetSBA()" id="resetmaximumbet">Reset</button>');
 					 
 				   }
 				 
 				  
 		},
 		complete: function(jsondata,textStatus,xhr) {
 		$(".loader").fadeOut("slow");
 			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
 			
 	   } 
 	});
 	
 	} 
 	
 	});
 }

 function resetmaximumbetSBA() {
 	
 		 swal({
 		title: 'Are you sure?',
 		text: 'You Want to Reset Maximum Amount',
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
 			type:'DELETE',
 			url :'<s:url value="/api/removeSBAMinMaxEvent/"/>'+${eventid},
 			success: function(jsondata, textStatus, xhr) {
 			$(".loader").fadeOut("slow");
 				showMessage(jsondata.message,jsondata.type,jsondata.title);
 				 $("#resetmaximumbet").remove();
 			},
 			complete: function(jsondata,textStatus,xhr) {
 			$(".loader").fadeOut("slow");
 				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
 				
 		   } 
 		});
 		
 		} 
 		
 		});
 	}


 function updateEventExposure(){

	 swal({
	title: 'Are you sure?',
	text: 'You Want to Update Maximum Amount',
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
		url :'<s:url value="/api/updateEventExposure/"/>'+${eventid}+"/"+$("#eventexposure").val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			   $("#eventExpoDiv").append('<button style="" onclick="removeEventExposure()" id="reseteventExpo">Reset</button>');
				   
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
	
	} 
	
	});
}


function removeEventExposure() {
	
	 swal({
	title: 'Are you sure?',
	text: 'You Want to Remove',
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
		type:'DELETE',
		url :'<s:url value="/api/removeEventExposure?eventid="/>'+${eventid},
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			 $("#reseteventExpo").remove();
			 
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
	
	} 
	
	});
}



function getSelectedEventExposure(maxBet) {
	   
	var i=0;
	 $.ajax({
	 
			type:'GET',
			url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=maxbet',
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
			   $.each(jsondata, function( key, value ) {
				   
					   if(parseInt(maxBet) == parseInt(value.amount)){
						
							  $("#eventexposure").append('<option value="'+value.amount+'" selected> '+value.amount+'</option>')
					          $("#eventExpoDiv").append('<button style="" onclick="removeEventExposure()" id="reseteventExpo">Reset</button>');
					   
						   }else{
					   $("#eventexposure").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					    //  console.log("maxbetgetSelectedEventExposure==>"+value.amount)
					   }
				  
			});
			},
			complete: function(jsondata,textStatus,xhr) {
				// $("#maximumbet").append('<option value="'+value.amount+'">'+value.amount+'</option>')
						
		    } 
		});     	    	
}

function getEvents() {
	
	 $.ajax({
	 
			type:'GET',
			url:'<s:url value="/api/getevent?eventid="/>'+${eventid},
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {

				if(${user.usertype} == 4){
					getSelectedEventExposure(jsondata.totalExposure);
				}
				//getSelectedEventExposure(jsondata.totalExposure)
			},
			complete: function(jsondata,textStatus,xhr) {
				
		    } 
		});     	    	
}

function getSelectedBookMakerBackLayDiff() {


	$.ajax({

		type:'GET',
		url:'<s:url value="/api/getSelectedBookMakerBackLayDiff/"/>'+${eventid},
		contentType:"application/json; charset=utf-8",
		success: function(jsondata, textStatus, xhr) {
			//console.log(jsondata)
			getBookMakerBackLayDiff(jsondata.backdiff,jsondata.laydiff)
		},
		complete: function(jsondata,textStatus,xhr) {
			
	    } 
	});
	}
function getBookMakerBackLayDiff(backdiff,laydiff) {
           console.log(backdiff);
           console.log(laydiff);
	var i=0;
	$.ajax({

		type:'GET',
		url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=betdelay',
		contentType:"application/json; charset=utf-8",
		success: function(jsondata, textStatus, xhr) {
			
		   $.each(jsondata, function( key, value ) {
			   if(backdiff == value.amount){
				   $("#bmbackdiff").append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
				}else{
					$("#bmbackdiff").append('<option value="'+value.amount+'">'+value.amount+'</option>')
					}
			   if(laydiff ==value.amount){
				   $("#bmlaydiff").append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
				}else{
					 $("#bmlaydiff").append('<option value="'+value.amount+'">'+value.amount+'</option>')
				}
			   
			   
			   
		});
		},
		complete: function(jsondata,textStatus,xhr) {
			
	    } 
	});     	    	
	}


function updateCasinoBackDiff(){


	$("#lotusmatch").append('<div class="loader"></div>')
	$.ajax({
		type:'POST',
		url :'<s:url value="/api/updateCasinoBackDiff/"/>'+${eventid}+"/"+$("#bmbackdiff").val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			//checkBookMakerBackLayDiff();
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
  	
}

function updateCasinoLayDiff(){


	$("#lotusmatch").append('<div class="loader"></div>')
	$.ajax({
		type:'POST',
		url :'<s:url value="/api/updateCasinoLayDiff/"/>'+${eventid}+"/"+$("#bmlaydiff").val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			//checkBookMakerBackLayDiff();
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
  	
}

function updateBookMakerBackLayDiff(){


		   	 swal({
  title: 'Are you sure?',
  text: 'You Want to Update BookMaker Diffrance',
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
			url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/"+$("#bmbackdiff").val()+"/"+$("#bmlaydiff").val(),
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
     
});
}


function updateBookMakerLayDiff(){


	$.ajax({
		type:'POST',
		url :'<s:url value="/api/updateBookMakerBackLayDiff/"/>'+${eventid}+"/0/"+$("#bmlaydiff").val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
			checkBookMakerBackLayDiff();
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	   } 
	});
}


</script>