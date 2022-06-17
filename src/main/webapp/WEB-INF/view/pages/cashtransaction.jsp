<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="transactionhandler" type="text/x-handlebars-template">
					<div class="table-responsive">
						<table class="apl-table table-net-exposure transitionable-table-wrap">
									<thead>
										<tr>
											<th>Date</th>
											<th>Post Date</th>                  
											<th>Collection Name</th>
											<th>Debit</th>
											<th>Credit</th>
											<th>Balance</th>
											<th>Payment Type</th>
											<th>Remark</th>
											<th>Plus/Minus</th>
										</tr>
									</thead>
							<tbody>
{{#each data}}
											<tr>
											<th>{{date}}</th>
											<th>{{postdate}}</th>                  
											<th>{{collectionname}}</th>
											<th>{{debit}}</th>
											<th>{{credit}}</th>
											<th>{{balance}}</th>
											<th>{{paymenttype}}</th>
											<th>{{remark}}</th>
											<th>{{plusminus}}</th>
											</tr>
{{/each}}
											<tr style="background:white">
											<th></th>
											<th></th>                  
											<th style="color:black">Total Amount</th>
											<th style="color:black" id="totalDebit"></th>
											<th style="color:black" id="totalCredit"></th>
											<th id="totalBalance"></th>
											<th></th>
											<th></th>
											<th></th>
											</tr>
							</tbody>

						</table>
</div>

</script>
<div id="page-wrapper">	
	<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Cash Trnasaction</h1>
						<div class="filterSection">
						
							<ul>
								<li id="master_drop">
									<label >User Type</label>
									<select id='uplinedownline' class = "purp">
										<option value ="-1">Select</option>
										<option value ="Dn">DownLine</option>
										<option value="Up">Upline</option>
									</select>
									
								<li id="master_drop">
									<label >User Type</label>
									
									<select id='childtype' class = "purp">
									
									</select>
								</li>
								
								<li id="master_drop">
									<label >User</label>
									<select id='user' class = "purp">
									</select>
									
								</li>
								<li>
									<label>Collection</label>
									<input type="text" readonly="true" id="collection" value="CASH" >
									
								</li>
								
								<li>
									<label>Date</label>
									<jsp:useBean id="date" class="java.util.Date"/>
									<input type="text" id="date" readonly="true" value="<fmt:formatDate value="${date}" type="date" pattern="dd-MM-yyyy"/>" >
									
								</li>
								
								<li>
									<label>Amount</label>
									<input type="number" id="amount" placeholder="Amount" >
									
								</li>
							</ul>
							<ul>
								<li id="master_drop">
									<label >PaymentType</label>
									<select id="paymenttype">
										<option>Payment-Type</option>
										<option value="dena" >PAYMENT-DENA</option>
										<option value="lena">PAYMENT-LENA</option>
									</select>
									
								</li>
								<li id="master_drop">
									<label >Payment Mode</label>
									<select id="paymentmode">
										<option value="cash" selected >Cash</option>
										<option value="bank">Bank</option>
									</select>
									
								</li>
								<li><input type="text" id="remarks" placeholder="Remarks" ></li>
								<li>
									<label></label>
									<button type="button" onclick="setTransaction()" class="btn btn-info blue-btn btn-sm" >Save</button> 
									<button type="button" class="btn btn-info blue-btn btn-sm">Cancel</button> 
								
								</li>
								<li>
							</ul>
						</div>
					</div>
					<!-- /.col-lg-12 -->
				</div>
						<section class="menu-content">
							<div class="table-reponsive" id="transactionTable">
								
							</div>
							<div class="panel-body text-center" id="notransaction">
								<h1>No Transaction Found</h1>
							</div>
						</section>				
				<!-- /.row --> 
            </div> 
            </div>    
            
            <script>
			$("#notransaction").hide();
            $(document).ready(function(){
            	//userList();
            	
            	 $("#user").change(function(){
            		$("#userid").val($("#user option:selected").attr('value'));
            		getTransaction();
            	}); 
            	
            	 $("#childtype").change(function(){
            		 userList();
            		 
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
             			}
             			
             		}else if($("#uplinedownline").val() == "Up"){
             			$("#childtype").html('');
             			  $("#childtype").append('<option value="-1">select Parent</option><option value="4">Up Line</option>'); 
             			/*if('${user.usertype}'==5){
             				  $("#childtype").append('<option value="-1">select downLine</option><option value="4">Up Line</option>'); 
             			}
						else if ('${user.usertype}'==0){
							$("#childtype").append('<option value="-1">select</option><option value="5">Admin</option>'); 
             			}else if ('${user.usertype}'==1){
             				$("#childtype").append('<option value="-1">select</option><option value="5">Admin</option><option value="1">Sub Admin</option>'); 
             			}
             			else if ('${user.usertype}'==2){
             				$("#childtype").append('<option value="-1">select</option><option value="5">Admin</option><option value="0">Sub Admin</option> <option value="1">Super Master</option>'); 
             			}*/
             		}else{
             			alert($("#uplinedownline").val());
             		}
             	}); 
            });
            
			 function setTransaction() 
			 {	
			 
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
						url:'<s:url value="/api/setCashTransaction"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
							getTransaction();
							userList();
							$("#amount").val('');
							$("#remarks").val('');
						$("#paymenttype").html('<option>Payment-Type</option><option value="dena" >PAYMENT-DENA</option><option value="lena">PAYMENT-LENA</option>');											
						} 
					}); 
			}
			 
			 
			
			 
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
        					$("#user").append($('<option> <a href="#" >Select User</a> </option>'));
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
            
            function getTransaction(){
            	            	
            	$.ajax({
        			type:'GET',
        			url:'<s:url value="api/getCashTransaction?userid="/>'+$("#userid").val()+"&downlinetype="+$("#uplinedownline").val(),
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status==200){
        					$("#transactionTable").html('');
        					$("#notransaction").hide();
        					$("#transactionTable").show();
        					var obj = jsondata;
      					  var source   = $("#transactionhandler").html();
      			    	  var template = Handlebars.compile(source);
      				      $("#transactionTable").append( template({data:jsondata}) );
  				    	  var totalDebit = 0.00;
  				    	  var totalCredit = 0.00;
      				      
      				      for(var i=0;i<obj.length;i++){

      				    	  totalDebit = totalDebit+obj[i].debit;
      				    	  totalCredit = totalCredit+obj[i].credit;
      				      }
      				   
      				      $("#totalCredit").html(totalCredit);
      				      $("#totalDebit").html(totalDebit);      				      
      				      $("#totalBalance").html(totalCredit+totalDebit);
      				      
      				      if($("#totalBalance").html()<0.00){
      				    	$("#totalBalance").css("color","red");
      				      }else{
        				    	$("#totalBalance").css("color","green");
      				      }
      				      
        				}else{
        					$("#notransaction").show();
        					$("#transactionTable").hide();
        				}
        		}
        	});
           }

            </script>    
            
            <input type="hidden" id="userid"/>