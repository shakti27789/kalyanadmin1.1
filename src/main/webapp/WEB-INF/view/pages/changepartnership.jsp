<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!--************** start ***************** -->

 <script id="statementhandler" type="text/x-handlebars-template">

	    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Date</th>
                       <th>Credit</th>
                       <th>Debit</th>
						<th>Closing</th>
                       <th>Descreption</th>
                      <th>FromTo</th>
                       
                   </tr>
               </thead>
               <tbody>
                 {{#each data}}
                   <tr> 
                       <td>{{date}}</td>
                       <td class="positive">{{credit}}</td>
                       <td class="negative">{{debit}}</td>
						<td>{{closing}}</td>
                       <td>{{descreption}}</td>
                       <td>{{fromTo}}</td>
                   </tr>
                 {{/each}} 
             
              
                  
                  
               </tbody>


       
           </table>

</script>
<div class="cliennt-container">
<input type="hidden" id="pageNo">
       <div class="account-container client-record pt-4">

         <div class="account-record account-stment">
			<div class="acont-box">
				<span class="acont-left ">
					<h4>Sport Partnership</h4>
				</span>
				
					
				
					
				<div class="form-row">
						<div class="form-group col-md-3">
					   <label for="inputState">Type</label>
					   <select id="type" class="form-control">
						 <option selected value="-1">Please Select</option>
						<option value="sport">Sport</option>
						<option value="casino">Casino</option>
					   </select>
					</div>
					<div class="form-group col-md-3">
					   <label for="inputState">Search By Client Name</label>
					   <select id="clientlist" class="form-control">
					  
					   </select>
					</div>

					<div class="form-group col-md-3">
						 <label for="inputState">Current</label>						 
						 <div class="input-group">
							<input type="text" class="datepicker form-control" id="currentpartnetship" style="border-radius: 5px 0 0 5px;">
							
						</div>
					</div>
					<div class="form-group col-md-3">
						<label for="inputState">New </label>						
						<div class="input-group date">
							
							 <select id="newpartnetship" class="form-control" style="border-radius: 5px 0 0 5px;">
						
					   </select>
						</div>
					</div>


					<div class="form-group col-md-3">
						<button class="btn btn-primary" onclick="updatePartnerShip()">Change Partnership</button>
					</div>
				</div>
			</div>
         
  
         
       
       </div>
       </div>
       
  
 <div class="master-box pl-0 pr-0">
                   <div class="master-form">
                    <div class="form-group">
                        <label for="pwd">Master Password:</label>
                        <input type="password" class="form-control" id="deletePass" name="parentPasswordAddUser">
                      </div>
                   </div>
        </div>

</div>

<!--*************** end ***************** -->





<script>
var pageNo=0;
var childid =-1;
 $(document).ready(function(){

	 $( "#clientlist" ).change(function() {
		 currentPartnership($("#clientlist").val());
		 setAdminPart();
     });


	 $( "#type" ).change(function() {
		 $("#currentpartnetship").val('');
		 setAdminPart();
		 $("#clientlist").html('');
     });
  });


       
              
              function currentPartnership(childid)
              {	 
            	
               	  if(childid =="0"){
              		childid=$("#clientlist").val();
                  	}
                if(childid == null){
                	childid='${user.userid}';
                	}
              	  $("#statement").html('');
              	
              	
              	 $(".account-record").append('<div class="loader"></div>')	
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/currentPartnership"/>/'+childid,
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              			//	alert(jsondata)
              			    if($("#type").val() == "sport"){
             			    	 $("#currentpartnetship").val(jsondata.subadminpartnership)
                  			    } else if($("#type").val() == "casino"){
             			    	 $("#currentpartnetship").val(jsondata.subadminpartnershipc)
                  			    }
              				
	   				    	$(".loader").fadeOut("slow");
              			}else{
              				
              				 $("#statement").append('  <div>No Records Found</div>');	
              				$(".loader").fadeOut("slow");
              			}
              		}
              	});
              	
              }

              function userList() {	 
					$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/userByParentId/"/>'+'${user.id}',
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              					$.each(jsondata, function(index, element) {
								$("#clientlist").append('<option valuue='+element.userid+'>'+element.userid+'</option>');
								$("#clientlist").select2();
                  			});
              				 
              			}else{
              				
              				 
              			}
              		}
              	});
              }

               $('#clientlist').select2({
					
        		    ajax: {
      		        url: '<s:url value="/api/subAdminByUserLike"/>',
      		        dataType: 'json',
      		        processResults: function (data) {
						 return {
      		                results:data
      		            };
      		        },
      		        cache: false
      		    },
      		    minimumInputLength:2,
      		    allowClear:true,
      		    placeholder: {
      		        id: "",
      		        placeholder: "Leave blank to ..."
      		      },
      		});	 

           	function setAdminPart(){
        	  	$("#newpartnetship").html('');
        	  	var element = 0;
        	  	for(i = 100; i >=element; i--) { 
                $("#newpartnetship").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
        	  	/* $("#commision").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>'));
        		$("#cpartnership").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>')); */
        	  	
        	}  
          }

            function updatePartnerShip(){
            	var deletePass =$("#deletePass").val();
           	 
                var partnership=$("#newpartnetship").val()
				var	subadminId=$("#clientlist").val()
                	$.ajax({
              			type:'POST',
              			url:'<s:url value="/api/updatePartnerShip"/>/'+subadminId+"/"+partnership+"/"+$("#type").val()+"/"+deletePass,
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              					showMessage(jsondata.message,jsondata.type,jsondata.title);
              					currentPartnership(subadminId);
	   				    	     $(".loader").fadeOut("slow");
              			}else{
              				
              				 $("#statement").append('  <div>No Records Found</div>');	
              				$(".loader").fadeOut("slow");
              			}
              		}
              	});
                 }
         </script>
<jsp:include page="handlebars.jsp" />  
