<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script>
var base_url = window.location.origin;
$(document).ready(function(){
	getSportList();
});

function getSportList()
{			
	
    
 		 $(".content-wrapper").append('<div class="loader-demo-box"><div class="pixel-loader"></div></div>') 
  		$.ajax({
			type:'GET',
			url:base_url+"betexch/v0/api/getBetfairSportslist/",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{     
						  var arr = $.parseJSON(jsondata);
					$("#sportsList").html('');
					  var source   = $("#sportListHandler").html();
			    	  var template = Handlebars.compile(source);
				    	  $("#sportsList").append( template({data:arr}) );ra
				    	  checkIfSportAdded();
				    	  $(".loader-demo-box").remove()
					}
		}
	});
}


function addSport(id){
	  
    isChecked =  $("#sportidcheckbox"+id).is(":checked");
   	     $("#sportidtext").val($("#sportid"+id).val())
   	     if(isChecked){
   	    	//alert(id);
   	    	 var data={name:$("#sportname"+id).val(),sportid:parseInt($("#sportid"+id).val())}
   	    	
   	    	 swal({
   	      title: 'Are you sure?',
   	      text: 'You Want to Add this Sport!',
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
   					url:base_url+"betexch/v0/api/addSport/",
   					data: JSON.stringify(data),
   					dataType: "json",
   					contentType:"application/json; charset=utf-8",
   					success: function(jsondata, textStatus, xhr) {
   					
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					},
   					complete: function(jsondata,textStatus,xhr) {
   						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
   						 
   				    } 
   				}); 
   				
   				 } else{
   				 $("#sportidcheckbox"+id).prop("checked", false);
   				 }
   				}); 
   	    }
   	     else{
   	    	 var data={name:$("#sportname"+id).val(),sportid:parseInt($("#sportid"+id).val())}
	    		
	    		 swal({
   	      title: 'Are you sure?',
   	      text: 'You Want to Remove this Sport!',
   	      type: 'warning',
   	      showCancelButton: true,
   	      confirmButtonColor: '#3085d6',
   	      cancelButtonColor: '#d33',
   	      confirmButtonText: 'Yes',
   	      closeOnConfirm: false
   	    }).then(function(isConfirm) {
   	      if (isConfirm) { 	
   	    	 $.ajax({
   					type:'PUT',
   					url:base_url+"betexch/v0/api/updateSport/",
   					data: JSON.stringify(data),
   					dataType: "json",
   					contentType:"application/json; charset=utf-8",
   					success: function(jsondata, textStatus, xhr) {
   					
   						showMessage(jsondata.message,jsondata.type,jsondata.title);
   					},
   					complete: function(jsondata,textStatus,xhr) {
   						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
   						 
   				    } 
   				});
   				
   				 } else{
   				 $("#sportidcheckbox"+id).prop("checked", true);
   				 }
   				});
   	    	 }
   }
   
function checkIfSportAdded(){
	
	 $.ajax({
			type:'GET',
			url:base_url+"betexch/v0/api/checkIfSportAdded/",
			success: function(jsondata, textStatus, xhr) {
			{
				if(xhr.status == 200)
				{     
					
					 $.each(jsondata, function(key,value){
						 $('#sportidcheckbox'+value.sportid).prop('checked', true);
					 });
				}
				
			}
		}
	});
}
</script>