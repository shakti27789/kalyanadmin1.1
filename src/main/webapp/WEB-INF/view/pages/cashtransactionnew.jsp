<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<script id="transactionhandler" type="text/x-handlebars-template">

{{#if_greaterThan data.matchamount.length 0}}	 
<table id="myTable" class="table table-striped table-bordered cash-trans" style="width:100%">
       
               <thead>
                   <tr>
 					   <th>Action</th>
                       <th>Date</th>
                       <th>Post date</th>
                       <th>Sports Name</th>
                       <th>Collection Name</th>
                       <th> Debit</th>
                       <th>Credit</th>
                       <th>Balance</th>
                       <th>Payment Type</th>
                       <th>Remark</th>
                       <th>Plus/Minus</th>
                       
                   </tr>
               </thead>
              
               <tbody>
        {{#each data.finalamount}}
                    <tr style="background:white">
					 <th></th>
					 <th></th>
					 <th></th>                  
					 <th style="color:black">Total Amount</th>
					 <th style="color:black" id="">{{debit}}</th>
					 <th style="color:black" id="">{{credit}}</th>
					 <th id="totalBalance">{{balance}} </th>
					 <th></th>
					 <th></th>
					</tr> 
		{{/each}}
		{{#each data.matchamount}}

				  <tr><td>
                     <div class="dropdown">
  					<button class="btn btn-primary dropdown-toggle" style="background-color: dimgray;" type="button" data-toggle="dropdown">Action
  					<span class="caret"></span></button>
  					<ul class="dropdown-menu">
    				 <li><a href="<s:url value="/sportdetailMatchMinnusSession/{{matchid}}"/>">Match & Session Plus Minus</a></li>
 					 <li><a href="<s:url value="/sessionPlusMinus/{{matchid}}"/>">Session Plus Minus</a></li>
                   </ul>
				 </div>
					</td>
					<td>{{createdon}}</td>
					<td>{{date}}</td> 
				    <td>{{sportname}}</td>                 
					<td>{{collectionname}}</td>
					<td>{{debit}}</td>
					<td>{{credit}}</td>
					<td>{{balance}}</td>
					<td>{{matchname}}</td>
					<td>{{remarks}}</td>
					<td>{{plusminus}}</td>
				  </tr>
		{{/each}}
                  
            </tbody>
          </table>
{{else}}
   <div>No Records Found</div>
{{/if_greaterThan}}
	</script>

<div class="cliennt-container">
  <div class="account-container client-record pt-4">

         <div class="account-record cash-trans">

           <div class="acont-box">
             <span class="acont-left ">
                <h4>Cash Transaction</h4>
             </span>
           
           <div class="formrow mt-4">
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">User Type</label>
                       <select id="uplinedownline" class="form-control">
                        <option value ="-1">Select</option>
										<option value ="Dn">DownLine</option>
										<option value ="Up">UpLine</option>
                       </select>
                     </div>
               </div>
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">User Type</label>
                       <select id="childtype" class="form-control"> </select>
                     </div>
               </div>
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">User</label>
                       <select id="user" class="form-control"> </select>
                   </div>
               </div>
               <div class="form-left">
                   <div class="form-group">
                       <label for="usr">Collection</label>
                       <input type="text" class="form-control" id="collection" name="collection" placeholder="Collection">
                   </div>
               </div>

               <div class="date-box">
                 <label for="inputState">Date</label>
                 <jsp:useBean id="date" class="java.util.Date"/>
                 <input class="datepicker fa fa-calendar-o" id="date"  value="<fmt:formatDate value="${date}" type="date" pattern="dd-MM-yyyy"/>" >
               </div>

               <div class="form-left">
                   <div class="form-group">
                       <label for="usr">Ammount</label>
                       <input type="text" class="form-control" id="amount" name="amount" placeholder="Ammount">
                   </div>
               </div>
             
             
              
           </div>
           <div class="formrow">
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">Payment Type</label>
                       <select id="paymenttype" class="form-control">
                         <option value="-1">Payment-Type</option>
						 <option value="dena" >PAYMENT-DENA</option>
						 <option value="lena">PAYMENT-LENA</option>
                       </select>
                     </div>
               </div>
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">Payment Mode</label>
                       <select id="paymentmode" class="form-control">
                         <option value="cash" selected >Cash</option>
						 <option value="bank">Bank</option>
                       </select>
                     </div>
               </div>
              
               <div class="form-left remark">
                   <div class="form-group">
                       
                       <input type="text" class="form-control" id="remarks" name="remarks" placeholder="Remarks">
                   </div>
               </div>


                <div class="acont-right"><button class="btn" onclick="setTransaction()">SAVE</button></div>
                <div class="acont-right"><button class="btn">CANCEL</button></div>
              
           </div>
         </div>
           
         
           <div class="account-table  table-responsive" id="transactionTable">
          
          
          </div>
       </div>
  </div>
</div>
  
  <input type="hidden" id="userid"/>
  
  <script>
  $(document).ready(function(){
  	
	 $("#childtype").change(function(){
 		 userList();
 	 });
	 $("#user").change(function(){
 		$("#userid").val($("#user option:selected").attr('value'));
 		getTransaction(0,100);
 	}); 
  	
  	 $("#uplinedownline").change(function(){
   		if($("#uplinedownline").val() == "Dn"){
   			$("#childtype").html('');
   			
   			if('${user.usertype}'==5){
   				  $("#childtype").append('<option value="-1">select downLine</option><option value="0">Sub Admin</option><option value="1">Super Master</option><option value="2">Master</option> <option value="3">direct client</option>'); 
   			}else if ('${user.usertype}'==4){
   				$("#childtype").append(' <option value="-1">select downLine</option> <option value="5">Admin </option>'); 
         				
   			}
				else if ('${user.usertype}'==0){
					$("#childtype").append('<option value="-1">select</option><option value="1">Super Master</option><option value="2">Master</option><option value="3">direct client</option>'); 
   			}else if ('${user.usertype}'==1){
   				$("#childtype").append('<option value="-1">select</option><option value="2">Master</option><option value="3">direct client</option>'); 
   			}else if ('${user.usertype}'==2){
   				$("#childtype").append('<option value="-1">select</option><option value="3">Client</option>'); 
   			}
   			
   		}else if($("#uplinedownline").val() == "Up"){
   			$("#childtype").html('');
   			  $("#childtype").append('<option value="-1">select Parent</option><option value="4">Up Line</option>'); 
   		
   		}else{
   			alert($("#uplinedownline").val());
   		}
   	}); 
  });
  
  function userList(){
  	
  	var childtype =  $("#childtype").val();
  	if(childtype == 0){
  		Header ="Select Super Master"
  	}else if(childtype == 1){
  		Header ="Select Master"
  	}
  	else if(childtype == 2){
  		Header ="Select Agent"
  	}
  	else if(childtype == -1){
  		Header =""
  	}
  	else if(childtype == 5){
  		Header ="Select Subadmin"
  	}
  	$.ajax({
			type:'GET',
			url:'<s:url value="api/getChild/"/>'+childtype+"/"+$("#uplinedownline").val(),
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status==200){
					
					var obj = jsondata;
					$("#user").html('');
					$("#user").append($('<option value="-1"> <a href="#" >Select User</a> </option>'));
				  for(var i = 0;i<obj.data.length;i++) {

	             $("#user").append($('<option id = "'+obj.data[i].id+'" value="'+obj.data[i].id+'" name = "'+obj.data[i].userid+'"  > '+obj.data[i].username+' </option>', { 
	            }));    			            			       
			  }
			}else{
				$("#user").html('');
				$("#user").append($('<option> <a href="#" >No User</a> </option>'));        				
			}
		}
	});
 }
  
  function getTransaction(startpage,endpage){
 
  	$.ajax({
			type:'GET',
			url:'<s:url value="api/userCashTransaction?id="/>'+$("#userid").val()+"&downlinetype="+$("#uplinedownline").val()+"&pagintype=1&startpage="+startpage,
		 
					success: function(jsondata, textStatus, xhr) {
				if(xhr.status==200){
					$("#transactionTable").html('');
					$("#notransaction").hide();
					$("#transactionTable").show();
				    var obj = jsondata;
				    var source   = $("#transactionhandler").html();
		    	    var template = Handlebars.compile(source);
			        $("#transactionTable").append( template({data:jsondata}) );
		            $(".loader").fadeOut("slow");  
			     
			      
				}else{
					 var obj = jsondata;
					    var source   = $("#transactionhandler").html();
			    	    var template = Handlebars.compile(source);
				        $("#transactionTable").append( template({data:jsondata}) );
				}
		}
	});
 }
  
	 function setTransaction()  {	
		 if($("#user").val().trim() =="-1"){
			 showMessage("Please Select User","error","Oops!");
			 return false;
		 }else{
			 var data = {
						userid:$("#userid").val(),
						collectionname:$("#collection").val(),
						username:$("#user option:selected").attr('name'),
						date:$("#date").val(),
						amount:$("#amount").val(),
						paymenttype:$("#paymenttype").val(),
						paymentmode:$("#paymentmode").val(),
						remark:$("#remarks").val()};
				
			   	 $.ajax({
						type:'POST',
						url:'<s:url value="/api/setCashTransactionNew"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
						},
						complete: function(jsondata,textStatus,xhr) {
							 var myObj = JSON.parse(jsondata.responseText);
							 showMessage(myObj.message,myObj.type,myObj.title);
							 
							getTransaction(0,100);
							userList();
							$("#amount").val('');
							$("#remarks").val('');
						    $("#paymenttype").html('<option value ="-1">Payment-Type</option><option value="dena" >PAYMENT-DENA</option><option value="lena">PAYMENT-LENA</option>');											
						} 
					});
		 }
			 
		}
  </script>
   <jsp:include page="handlebars.jsp" />  