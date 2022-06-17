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
                       <td onClick="showBetsDetail('{{id}}','{{matchId}}','down','{{marketId}}')" >{{descreption}}</td>
                       <td>{{fromTo}}</td>
                   </tr>

 					<tr class="betsetail_table_tr" id="betsdetail_table_tr{{id}}">
				       <td colspan=9>
							 <input type="hidden" id="betsdetailslideupdown{{id}}" value="up">
							 <div id="betsdetail_list_div{{id}}" style="display:none;"><div>
					   </td>
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
					<h4>Account Statement</h4>
				</span>
				<div class="form-row">
					<div class="form-group col-md-3">
					   <label for="inputState">Game Name</label>
					   <select id="reportType" class="form-control">
						 <option selected value="-1">All </option>
						<option value="balance">Balance Report </option>
						<option value="match">Game Report</option>
					   </select>
					</div>
					<div class="form-group col-md-3">
					   <label for="inputState">Search By Client Name</label>
					   <select id="clientlist" class="form-control">
					  
					   </select>
					</div>

					<div class="form-group col-md-3">
						 <label for="inputState">From</label>						 
						 <div class="input-group">
							<input class="datepicker form-control" id="startDate" style="border-radius: 5px 0 0 5px;">
							<div class="input-group-append">
								<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
							</div>
						</div>
					</div>
					<div class="form-group col-md-3">
						<label for="inputState">To</label>						
						<div class="input-group date">
							<input class="datepicker form-control" id="endDate" style="border-radius: 5px 0 0 5px;" >
							<input class="datepicker form-control" id="endDateHidden" style="border-radius: 5px 0 0 5px; display:none;" >
							<div class="input-group-append">
								<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
							</div>
						</div>
					</div>


					<div class="form-group col-md-3">
						<button class="btn btn-primary" onclick="getStatement('0','-1')">Load</button>
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

          <div class="dataTables_paginate paging_simple_numbers" id="example_paginate"><button class="btn btn-primary" onclick="previousPage()">Previous</button><button class="btn btn-primary" style="margin-left: 20px;" onclick="nextPage()">Next</button></div>

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

function showBetsDetail(serialno,matchId,slideupdown,marketId){
	

$( "#betsdetail_list_div"+serialno).slideToggle("slow");
if($("#betsdetailslideupdown"+serialno).val() =="up"){
	//commDetail(matchId,marketId,serialno)
	betHistory(matchId,marketId,serialno,'betsdetail_list_div')
	$("#betsdetailslideupdown"+serialno).val('down');
	}else{
		$("#betsdetailslideupdown"+serialno).val('up');
		}
	
}

 $(document).ready(function(){
            	  var now = new Date();
            	
				var day = ("0" + now.getDate()).slice(-2);
            	var month = ("0" + (now.getMonth() + 1)).slice(-2);
            	
            	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
            	
            	 var date = new Date();
                 var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate()-7);
                 var enddate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                 var enddateHidden = new Date(date.getFullYear(), date.getMonth(), date.getDate()+1);
            	

            	 $('#startDate').datepicker({
            			format: "yyyy-mm-dd",
            			autoclose: true,
            			todayHighlight: true
            			
             });
            	 $('#endDate').datepicker({
            		 format: "yyyy-mm-dd",
         			autoclose: true,
         			todayHighlight: true
            			
            });
            	 $('#endDateHidden').datepicker({
            		 format: "yyyy-mm-dd",
         			autoclose: true,
         			todayHighlight: true
            			
            });

            	 var enddate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
            	
            	 $('#startDate').datepicker('setDate', startdate);
            	 $('#endDate').datepicker('setDate', enddate);
            	 $('#endDateHidden').datepicker('setDate', enddateHidden);
            	
            	 getStatement('-1',pageNo);
            	 $( "#clientlist" ).change(function() {
            		 getStatement($("#clientlist").val(),pageNo);
                 });
            	// userList()
            });


              function nextPage(){
            	  pageNo = parseInt(pageNo)+1;
            	  
            	  if($("#clientlist").val() == null){
                  	childid="-1"
                  		
                  	}
                	//alert($("#clientlist").val())
            	     getStatement(childid,pageNo);
                   }

              function previousPage(){
            	  pageNo = parseInt(pageNo)-1;
            	  if($("#clientlist").val() == null){
                    	childid="-1"
                    	}
              	if(pageNo>=0){
              		 getStatement(childid,pageNo);
                  	}
            	 
                  }
             
              function getStatement(childId,pageNo) {

				//alert("childid==>"+childid)
                  var userId ="";	    
              	  var startDate = $("#startDate").val()+" 00:00:00";
              	  var endDate = $("#endDate").val()+" 00:00:50";
              	
              	  if(childId =="0"){
              		childid=$("#clientlist").val();
                  	}else{
                  		childid =childId;
                      	}
                if(childId == null){
                	console.log("child Id"+childid)
                	childid='${user.userid}';
                	}
              	  $("#statement").html('');
              	if(pageNo =="-1"){
              		pageNo =0;
                  	  }
              	  $("#pageNo").val(pageNo)
              	//  alert(childid)
              	
              	 $(".account-record").append('<div class="loader"></div>')	
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/getStatementByUserMQuery"/>/'+'${user.userid}'+"/"+startDate+"/"+endDate+"/"+$("#reportType").val()+"/"+childid+"/"+pageNo,
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              				
              				  var source   = $("#statementhandler").html();
	    			    	  var template = Handlebars.compile(source);
	   				    	  $("#statement").append( template({data:jsondata.data}));	
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
