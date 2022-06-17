<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


  <script id="serieshandle" type="text/x-handlebars-template">
<div class="back_wrap">
     <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='sportList()'>Back</a>
</div>
<div class="table table-striped table-bordered account-stment">              
             	
				<table class="table common-table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Series Name</th>
                    <th>Market Count</th>
                 	<th>Region</th>
                    <th class="action">Action</th>
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                       <td>{{competition.id}} <input type='hidden' id ='seriesid{{competition.id}}' value='{{competition.id}}'></td>
                   	   <td>{{competition.name}} <input type='hidden' id ='seriesname{{competition.id}}' value='{{competition.name}}'</td>
                    	<td>{{marketCount}} <input type='hidden' id ='marketcount{{competition.id}}' value='{{marketCount}}'</td>
                        <td>{{addded}} </td>
						<td class="action">

                        <input class="mr-2" type="checkbox" id="addseriescheckbox{{competition.id}}"  onclick='addSeries({{competition.id}})'>

							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=eventBySeries({{competition.id}})>Event List</a> 
						</td>
                   </tr>
 			{{/each}} 

 					<tr>
                       <td>922982<input type='hidden' id ='seriesid922982'; value='922982'></td>
                   	   <td>Virtual Cricket Leaguage<input type='hidden' id ='seriesname922982' value='Virtual Cricket Leaguage'</td>
                    	<td>0<input type='hidden' id ='marketcount922982' value='0'</td>
                        <td>k</td>
						<td class="action">

                        <input class="mr-2" type="checkbox" id="addseriescheckbox922982"  onclick=addSeries('922982')>

							<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=eventBySeries('922982')>Event List</a> 
						</td>
                   </tr>         
                </tbody>
              </table>
		</div>
          
    </script>
    
    <script>
    
    
    function serirsBySport(sportid){
        
    	if(typeof(sportid)  === "undefined") {
    		sportid =$("#sportidtext").val();
    	}
    	isChecked =  $("#sportidcheckbox"+sportid).is(":checked");
    	 if(isChecked){
            $("#betfairsport").hide();
          	 $("#betfairseries").show();
          	 $("#betfairevents").hide();
          	 $("#sportidtext").val(sportid);
          	 $("#betfairseries").html('');
          	$(".account-stment").append('<div class="loader"></div>') 
	    	  
           	$.when(
             
          	 $.ajax({
       				type:'GET',
       				url:'<s:url value="api/getSeries/"/>'+sportid,
       				success: function(jsondata, textStatus, xhr) {
       				{
       					if(xhr.status == 200)
       					{   
       						
       						  var arr = $.parseJSON(jsondata);
       						  // checkIfSeriesAdded(arr)
       							// var arr = jsondata;

       						     var source   = $("#serieshandle").html();
       					   	  	 var template = Handlebars.compile(source);
       					   	     $("#betfairseries").append( template({data:arr}) );
       					   	 	
       					}
       					
       				} 
       			},complete: function(jsondata,textStatus,xhr) {
      	 			 $(".loader").fadeOut("slow");
		 	    }
       		})).then(function( x ) {
       			checkIfSeriesAdded()
       			
         		});
         		
        	}
        	else{
         		 showMessage("please Enable Sport first","error","Oops...");
         		}
    	 $(".loader").fadeOut("slow");
           }
       
    
    function addSeries(id)
    {		
    	     isChecked =  $("#addseriescheckbox"+id).is(":checked");
    	     $("#seriesidtext").val($("#seriesid"+id).val())
    	     if(isChecked){
    	    	
    	    	 var data={seriesname:$("#seriesname"+id).val(),seriesid:parseInt($("#seriesid"+id).val()),marketcount:parseInt($("#marketcount"+id).val()),sportid:parseInt($("#sportidtext").val()),adminid:'${user.id}',appid:${user.appid},status:true}
    	    	
    	    	 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Add this Series!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 	
    	    	  $("#page-wrapper").append('<div class="loader"></div>') 
    	    	
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="api/addSeries"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						  $(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				}
    				else{
    				 $("#addseriescheckbox"+id).prop("checked", false);
    				 }
    				});
    	    }
    	     else{
    	    	 
    	    	 var data={seriesname:$("#seriesname"+id).val(),seriesid:parseInt($("#seriesid"+id).val()),marketcount:parseInt($("#marketcount"+id).val()),sportid:parseInt($("#sportidtext").val()),subadminid:'${user.id}',appid:${user.appid},status:false}
 	    		
 	    		 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Remove this Series!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 		
    	    	  $("#page-wrapper").append('<div class="loader"></div>') 
    	    	
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updateSeries"/>',
    					data: JSON.stringify(data),
    					dataType: "json",
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						  $(".loader").fadeOut("slow");
    				    } 
    				});
    				
    				}
    				else{
    				 $("#addseriescheckbox"+id).prop("checked", true);
    				 }
    				});
    	    	 }
   	 
    }
    
    function checkIfSeriesAdded(){
    		
			 $.ajax({
					type:'GET',
					url:'<s:url value="api/checkIfSeriesAdded/"/>',
					success: function(jsondata, textStatus, xhr) {
					{
						if(xhr.status == 200)
						{     
							
							 $.each(jsondata, function(key,value){
								 console.log(value.seriesid)
								 $('#addseriescheckbox'+value.seriesid).prop('checked', true);
								 
							 });
						}
						
					}
				}
			});
	   
    }
    </script>