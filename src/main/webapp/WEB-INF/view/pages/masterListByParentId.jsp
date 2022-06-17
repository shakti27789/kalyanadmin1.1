<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<script id="subadminHandler" type="text/x-handlebars-template">
  
                       <table id="masterTable" class="table table-striped table-bordered " style="width:100%">
            
                    <thead>
                        <tr>
                            <th>User Name</th>
							<th>LoginId</th>
                            <th>Credit Reference</th>
                            <th>Balance</th>
                            <th>Client(P/L)</th>
							<th>Exposure</th>
                           <th>Available Balance</th>
                            <th>Down Level Occupy Balance</th>
                          
                          
                            <th>Exposure Limit</th>
                            <th>Sport %</th>
							<th>Casino %</th>
                            <th>Account Type</th>
                            <th>Casino Total</th>
                           
                        </tr>
                    </thead>
                    <tbody>
	{{#each data}}      
                        <tr> 
                         <td>
							{{#if_neq usertype 3}}  
							 {{#if_eq id ${user.id}}} 
							  {{else}}
								<a href="<s:url value="/userListByparentId/{{parentid}}/{{id}}"/>" target="_blank"><span class="password-btn">{{username}}</span></a>
							 {{/if_eq}}	 							  
					          {{else}}
							    <a href="javascript: void(0)" target="_blank"><span class="password-btn">{{username}} {{${user.parentid}}}</span></a>
    				         {{/if_neq}}	
  
						</td>
                           <td>{{userid}}</td>
						   <td onclick="modal_CreditRef('{{parentid}}','{{userid}}','{{availableBalance}}')">{{creditRef}}</td>
						   <td>{{availableBalance}}</td>
                       

						{{#if_eq id ${user.id}}} 
		                     <td>{{uplineAmount}}</td>
    					  {{else}}
						   {{#if uplineAmount 0}}
	  						 <td><div class="btn btn-danger text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
   						   {{else}}                         
	  						 <td><div class="btn btn-success  text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
 						   {{/if}}	
    					  {{/if_eq}}	
   
							
                           <td>  {{#if_neq usertype 3}}  
							<span>0
	                        </span> 
							 {{else}}
							<span onclick="currentbetsmodal('{{userid}}')">{{currentExpo}}
	                        </span>  {{/if_neq}}	
							</td>
                             <td>{{balance}}</td>
						  
                            <td>{{availableBalanceWithPnl}}</td>
                            
                            <td>{{netexposure}}</td>
                            <td>{{partnership}}</td>
							<td>{{partnershipc}}</td>
                            <td>{{userRoll}}</td>
                            <td></td>

                           
							
                        </tr>

	{{/each}} 
                    </tbody>
            
                </table>
            
</script>



<script id="clientHandler" type="text/x-handlebars-template">
  
                       <table id="userTable" class="table table-striped table-bordered " style="width:100%">
            
                    <thead>
                        <tr>
                            <th>User Name</th>
							<th>LoginId</th>
                            <th>Credit Reference</th>
                            <th>Balance</th>
                            <th>Client(P/L)</th>
							<th>Exposure</th>
                           <th>Available Balance</th>
                            <th>Down Level Occupy Balance</th>
                          
                          
                            <th>Exposure Limit</th>
                            <th>Sport %</th>
							<th>Casino %</th>
                            <th>Account Type</th>
                            <th>Casino Total</th>
                           
                        </tr>
                    </thead>
                    <tbody>
	{{#each data}}      
                        <tr> 
                         <td>
							{{#if_neq usertype 3}}  
							 {{#if_eq id ${user.id}}} 
							  {{else}}
								<a href="<s:url value="/userListByparentId/{{parentid}}/{{id}}"/>" target="_blank"><span class="password-btn">{{username}}</span></a>
							 {{/if_eq}}	 							  
					          {{else}}
							    <a href="javascript: void(0)" target="_blank"><span class="password-btn">{{username}} {{${user.parentid}}}</span></a>
    				         {{/if_neq}}	
  
						</td>
                           <td>{{userid}}</td>
						   <td onclick="modal_CreditRef('{{parentid}}','{{userid}}','{{availableBalance}}')">{{creditRef}}</td>
						   <td>{{availableBalance}}</td>
                       

						{{#if_eq id ${user.id}}} 
		                     <td>{{uplineAmount}}</td>
    					  {{else}}
						   {{#if uplineAmount 0}}
	  						 <td><div class="btn btn-danger text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
   						   {{else}}                         
	  						 <td><div class="btn btn-success  text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
 						   {{/if}}	
    					  {{/if_eq}}	
   
							
                           <td>  {{#if_neq usertype 3}}  
							<span>0
	                        </span> 
							 {{else}}
							<span onclick="currentbetsmodal('{{userid}}')">{{currentExpo}}
	                        </span>  {{/if_neq}}	
							</td>
                             <td>{{balance}}</td>
						  
                            <td>{{availableBalanceWithPnl}}</td>
                            
                            <td>{{netexposure}}</td>
                            <td>{{partnership}}</td>
							<td>{{partnershipc}}</td>
                            <td>{{userRoll}}</td>
                            <td></td>

                           
							
                        </tr>

	{{/each}} 
                    </tbody>
            
                </table>
            
</script>

<div class="cliennt-container">
        <div class="upper-container">
            <div class="client-btn"><i class="fa fa-arrow-circle-o-down" ></i></div>
            <div class="toggle-container ">
                <table class="table">
                   
                    <tbody>
                      <tr>
                        <th scope="row">Upper Level Credit Reference :</th>
                        <td id="creditref"></td>
                       
                      </tr>
                      <tr>
                        <th scope="row">Total Master Balance :</th>
                        <td id="totalmassterbalance"></td>
                       
                      </tr>
                      <tr>
                        <th scope="row">Available Balance :</th>
                        <td id="availablebalance"></td>
                      
                      </tr>
                    </tbody>
                  </table>
            </div>
            <div class="toggle-container">
                <table class="table">
               
                <tbody>
                  <tr>
                    <th scope="row">Down Level Occupy Balance :</th>
                    <td id="downlineavailabebal"></td>
                   
                  </tr>
                  <tr>
                    <th scope="row">Upper Level :</th>
                    <td id="uplinebalance"></td>
                  
                  </tr>
                  <tr>
                    <th scope="row">Available Balance with Profit/Loss :</th>
                    <td id="availableBalanceWithPnl"></td>
                   
                  </tr>
                </tbody>
              </table></div>
            <div class="toggle-container">
                <table class="table">
                
                <tbody>
                  <tr>
                    <th scope="row">Down Level Credit Reference :</th>
                    <td id="downlinecreditRef"> </td>
                   
                  </tr>
                  <tr>
                    <th scope="row">Down Level Profit/Loss :</th>
                    <td id="downlineWithProfitLoss"></td>
                  
                  </tr>
                  <tr>
                    <th scope="row">My Profit/Loss :</th>
                    <td id="myProfitLoss"></td>
                   
                  </tr>
                </tbody>
              </table></div>
        </div>

      
           
          
                <div class="account-container client-record pt-4">
  
              <div class="account-record">
                <div class="acont-box">
                  <span class="acont-left ">
                      Master List
                  </span>
                  <div class="acont-right float-right"><a href="<s:url value="/add-account"/>">Add Account</a></div>
              </div>
              <div class="acont-search">
                <span class="search-left "><button class="btn btn-danger" id="pdfMasterList">PDF</button> </span>
                <span class="search-left  "><button class="btn btn-success">Excel</button></span>
                <!-- <div class="search-right  float-right"><span>Search:</span><input type="text"></div> -->
            </div>
 
               <div class="account-table bethistory table-responsive" id="userListDivMaster"> </div>
               
                <span class="acont-left ">
                      Client List
                  </span>
                <div class="account-table bethistory table-responsive" id="userListDivClient"> </div>
            </div>
            </div>
       
    </div>
    
        <script>
$(document).ready(function(){
	userList();
	headerData();
})
  

	
	 function userList(){
    	
		 $.ajax({
			type:'GET',
			url:'<s:url value="/api/user/${Id}/"/>'+5+"?active=true&type=All&isaccountclosed=false",
			success: function(jsondata, textStatus, xhr) {
				  $("#userListDivMaster").html('');
				 var source   = $("#subadminHandler").html();
		    	 var template = Handlebars.compile(source);
		    	 $("#userListDivMaster").append( template({data:jsondata.data.master}));
		    	 $('#masterTable').DataTable( {
		    		  "pageLength": 50
		    	 } );
		    	 
		    	 $("#userListDivClient").html('');
		    	 var source1   = $("#clientHandler").html();
				 var template = Handlebars.compile(source1);
		    	 $("#userListDivClient").append( template({data:jsondata.data.client}));
		    	 $('#userTable').DataTable( {
		    		  "pageLength": 50
		    	 } ); 
		    	 $("#downlineavailabebal").html(jsondata.data.total[0].availableBalance)
			}
			
    	});
    }
	


function headerData(){
	$.ajax({
		type:'GET',
		url:'<s:url value="/api/headerData"/>/'+${parentId}+"/"+${Id},
		success: function(jsondata, textStatus, xhr) {
		
			 console.log(jsondata.creditRef)
			 $("#creditref").html(jsondata.creditRef)
			  $("#totalmassterbalance").html(jsondata.totalMasterBalance)
			   $("#totalmassterbalance").html(jsondata.totalMasterBalance)
			    $("#availablebalance").html(jsondata.availableBalance)
			     $("#uplinebalance").html(jsondata.uplineAmount)
			      // $("#downlineavailabebal").html(jsondata.downlineavailableBalance)
			        $("#downlinecreditref").html(jsondata.downlinecreditRef)
			         $("#downlineWithProfitLoss").html(jsondata.downlineWithProfitLoss)
			          $("#myProfitLoss").html(jsondata.myProfitLoss);
			            $("#availableBalanceWithPnl").html(jsondata.availableBalanceWithPnl);
			 			$("#downlinecreditRef").html(jsondata.downlinecreditRef);
			 
		}
	
	});
}
    </script>
    
    <jsp:include page="currentbetsmodal.jsp" />
<jsp:include page="handlebars.jsp" />  

