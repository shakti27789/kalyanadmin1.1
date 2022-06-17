<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!--************** start ***************** -->

 <script id="statementhandler" type="text/x-handlebars-template">

	    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>userId</th>
 					   <th>Action</th>
                       
                       
                   </tr>
               </thead>
               <tbody>
                 {{#each data}}
                   <tr> 
                      
                       <td>{{userid}}</td>
                       <td class="positive"><span class="limit-btn background_red"  onclick="RemoveBetCountUser('{{userid}}')">Remove</span></td>
                      
                   </tr>

 					
                 {{/each}} 
             </tbody>


       
           </table>

</script>


<script id="betHistoryHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
   <th>User</th>
        <th>Odds</th>
        <th>Nation</th>
        <th>Stack</th>
        <th>Pnl</th>
       <th>Time</th>
       
    </tr>
</thead>
<tbody>
  {{#each data}}

    {{#if_eq isback true}}
    <tr class="back">
<tr class="back">
  {{else}} 
 <tr class="lay">
<tr class="lay">
 {{/if_eq}}	
<td>{{userid}}</td>	
 <td>{{odds}}</td>

        <td>{{selectionname}}</td>
        <td>{{stake}}</td>
        {{#if_small netpnl 0}}
					     <td class="negative">{{netpnl}}</td>
				          {{else}}{{#if netpnl 0}}
					     <td>{{netpnl}}</td>
				       {{else}}
					     <td class="positive">{{netpnl}}</td> 
				       {{/if}}
         {{/if_small}}
        <td>{{matchedtime}}</td>
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
					<h4>User Count</h4>
				</span>
				<div class="form-row">
					
					<div class="form-group col-md-3">
					   <select id="clientlist" class="form-control">
					  
					   </select>
					</div>

					<div class="form-group col-md-3">
						<button class="btn btn-primary" onclick="AddBetCountUser()">Add User</button>
					</div>
				</div>
			</div>
         
         <%-- <div class="acont-search">
           <span class="search-left "><button class="btn btn-danger">PDF</button> </span>
           <span class="search-left  "><button class="btn btn-success">Excel</button></span>
         </div>
 --%>
         
           <div class="account-table bethistory table-responsive" id="statement">
          </div>

         
       </div>
       </div>
       
  
<div id="otherBetsModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #0088cc; padding: 10px 10px; color: white;">
			
					<h4 class="modal-title">Bets</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive" id="other-bets-div"
						style="max-height: 500px;">
						
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

</div>

<!--*************** end ***************** -->





<script>
var pageNo=0;
var childid =-1;


 $(document).ready(function(){
	 findbetCountUser();
   });


 function AddBetCountUser() {
 	$.ajax({
 		type:'POST',
 		url:'<s:url value="api/AddBetCountUser/"/>'+$("#clientlist").val(),
 		success: function(jsondata, textStatus, xhr) 
 		{
 			showMessage(jsondata.message,jsondata.type,jsondata.title);
 			findbetCountUser();
 			
 		},
 		complete: function(jsondata,textStatus,xhr) {
 			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
 			 
 	    } 
     });
 	$(".loader").fadeOut("slow");
 }

 function RemoveBetCountUser(userid) {
 	
 	$.ajax({
 		type:'POST',
 		url:'<s:url value="api/RemoveBetCountUser/"/>'+userid,
 		success: function(jsondata, textStatus, xhr) 
 		{
 			showMessage(jsondata.message,jsondata.type,jsondata.title);
 			findbetCountUser();
 			
 		},
 		complete: function(jsondata,textStatus,xhr) {
 			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
 			 
 	    } 
     });
 	$(".loader").fadeOut("slow");
 }
            
             
              function findbetCountUser() {

				
              	 $("#statement").html('');
              	 $(".account-record").append('<div class="loader"></div>')	
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/findbetCountUser"/>/',
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              				
              				  var source   = $("#statementhandler").html();
	    			    	  var template = Handlebars.compile(source);
	   				    	  $("#statement").append( template({data:jsondata}));	
	   				    	$(".loader").fadeOut("slow");
              			}else{
              				
              				 $("#statement").append('  <div>No Records Found</div>');	
              				$(".loader").fadeOut("slow");
              			}
              		}
              	});
              	
              }

              
              function betHistory(matchId,marketId,serialno,div){

            	  $("#other-bets-div").html('');
          		$('#otherBetsModal').modal('show');

          		var userId =$("#clientlist").val();  
          	    if(userId == null){
          	    	userId="-1"
          	      	}
          	  	
          			
            		  var data = {userId:userId,
            				      matchId:matchId,
            				      marketId:marketId
            				     }
            			$("#closedbetstable").append('<div class="loader"></div>')
            			//$("#"+div).append('<div class="loader"></div>')
            			  $.ajax({
            				    type:'POST',
            				    url:'<s:url value="/api/betHistoryAccStemement"/>',
            					data: JSON.stringify(data),
            					dataType: "json",
            					contentType:"application/json; charset=utf-8",
            					success: function(jsondata, textStatus, xhr) {
            						$("#"+div+serialno).html('');
            						if(xhr.status == 200){ 
            							 var source   = $("#betHistoryHandler").html();
            	     			    	  var template = Handlebars.compile(source);
            	     				     $("#other-bets-div").append( template({data:jsondata}) );
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
      		        url: '<s:url value="/api/userByUserLike"/>',
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
         </script>
<jsp:include page="handlebars.jsp" />  
