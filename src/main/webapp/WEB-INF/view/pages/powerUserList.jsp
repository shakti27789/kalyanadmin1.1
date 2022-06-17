<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<script id="subadminHandler" type="text/x-handlebars-template">
  
                <table id="masterTable" class="table table-striped table-bordered masterTable" style="width:100%">
            
                    <thead>
                        <tr>
                            <th>User Name</th>
							<th>LoginId</th>
                            
                            <th>account-container</th>
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
						  
                           
                           {{#if_eq id ${user.id}}} 
							 <td></td>  
							{{else}}
							 <td>
							  {{#if_eq accountlock true}}  
								<span class="limit-btn" onclick="accountUnLockByParent({{id}})">A/UL</span>
 							  {{else}}
								<span class="limit-btn" onclick="accountLockByParent({{id}})">A/L</span>
							  {{/if_eq}} 
								
							  
							</td>
							{{/if_eq}}	 
							
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
                
                  <%-- <div class="acont-right float-right"><a href="<s:url value="/addAccountNew"/>">Add Account Normal</a></div> --%>
              </div>
              <div class="acont-search">
               
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
    });
    function userList(){
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/powerUser/"/>',
			success: function(jsondata, textStatus, xhr) {
			     $("#userListDivMaster").html('');
				 var source   = $("#subadminHandler").html();
		    	 var template = Handlebars.compile(source);
		    	 $("#userListDivMaster").append( template({data:jsondata}));
		    	 
		    	 
		    	 
		    	 $('.masterTable').DataTable( {
		    		  "pageLength": 50
		    	 } );
		    	 
		    	
		    		
			}
			
    	});
    }
    </script>


<jsp:include page="handlebars.jsp" />  

    