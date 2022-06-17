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
       <div class="account-container client-record pt-4">

         <div class="account-record account-stment">
			<div class="acont-box">
				<span class="acont-left ">
					<h4>Settlement List</h4>
				</span>
				<div class="form-row">
					
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
							<div class="input-group-append">
								<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
							</div>
						</div>
					</div>


					<div class="form-group col-md-3">
						<button class="btn btn-primary" onclick="getStatement('0')">Load</button>
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
       
  


</div>

<!--*************** end ***************** -->





<script>
              $(document).ready(function(){
            	  var now = new Date();
            	
				var day = ("0" + now.getDate()).slice(-2);
            	var month = ("0" + (now.getMonth() + 1)).slice(-2);
            	
            	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
            	
            	 var date = new Date();
                 var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate()-70);
                 var enddate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                 
            	

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
            	
            	 $('#startDate').datepicker('setDate', startdate);
            	 $('#endDate').datepicker('setDate', enddate);
            	
            	 getStatement('-1');
            	 $( "#clientlist" ).change(function() {
            		 getStatement($("#clientlist").val());
                 });
            	// userList()
            });
              
              function getStatement(childid)
              {	  var userId ="";	    
              	  var startDate = $("#startDate").val()+" 00:00:00";
              	  var endDate = $("#endDate").val()+" 23:59:59";
              	  
              	  if(childid =="0"){
              		childid=$("#clientlist").val();
                  	}
                if(childid == null){
                	console.log("child Id"+childid)
                	childid='${user.userid}';
                	}
              	  $("#statement").html('');
                   
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/getStatementSettlementMQuery"/>/'+'${user.userid}'+"/"+startDate+"/"+endDate+"/"+childid,
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              				
              				  var source   = $("#statementhandler").html();
	    			    	  var template = Handlebars.compile(source);
	   				    	  $("#statement").append( template({data:jsondata}));	
              			}else{
              				
              				 $("#statement").append('  <div>No Records Found</div>');	
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
