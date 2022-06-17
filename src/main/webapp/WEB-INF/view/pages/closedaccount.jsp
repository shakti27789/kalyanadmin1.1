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
                            <th>Credit Reference</th>
                            <th>Balance</th>
                            <th>LenaDena(P/L)</th>
							<th>Exposure</th>
 							<th>Available Balance</th>
                            <th>Down Level Occupy Balance</th>
                          
                            <th>Exposure Limit</th>
                            <th>Sport %</th>
							<th>Casino %</th>
                            <th>Account Type</th>
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
						   <td>{{creditRef}}</td>
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
                           
                           {{#if_eq id ${user.id}}} 
							 <td></td>  
							{{else}}
							 <td><span class="deposit-btn" onclick="modalDeposite('{{parentid}}','{{userid}}','{{availableBalance}}')" style="background-color: green;" >D</span><span class="withdraw-btn" style="background-color: red;"  onclick="modalWithdraw('{{parentid}}','{{userid}}','{{secondrybanlce}}')">W</span>
<span class="password-btn" onclick="changePasswordModal('{{userid}}','{{parentid}}')">P</span>
								<span class="password-btn" onclick="viewDetailModal('{{userid}}','{{parentid}}')">V</span>
															 
 {{#if_eq betlock true}}  
								<span class="limit-btn background_red" onclick="betUnLockByParent({{id}})">B/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="betLockByParent({{id}})">B/L</span>
							  {{/if_eq}}	
							  {{#if_eq accountlock true}}  
								<span class="limit-btn" onclick="accountUnLockByParent({{id}})">A/UL</span>
 							  {{else}}
								<span class="limit-btn" onclick="accountLockByParent({{id}})">A/L</span>
							  {{/if_eq}} 
								<span class="limit-btn" onclick="khataBookModal({{id}})">K</span>

							  

	                          {{#if_eq lCasinoLock true}}  
								<span class="limit-btn background_red" onclick="casinoBetUnLock('{{id}}','L')">LCB/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="casinoBetLock('{{id}}','L')">LCB/L</span>
							  {{/if_eq}} 

							  {{#if_eq vCasinoLock true}}  
								<span class="limit-btn background_red" onclick="casinoBetUnLock('{{id}}','V')">VCB/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="casinoBetLock('{{id}}','V')">VCB/L</span>
							  {{/if_eq}} 

  							  {{#if_eq isaccountclosed true}}  
								  <span class="limit-btn background_red" onclick="accountOpenByParent('{{id}}')">A/O</span>
  							  {{/if_eq}} 
							</td>
							{{/if_eq}}	 
							
                        </tr>

	{{/each}} 
                    </tbody>
            
                </table>
            
</script>



<script id="clientHandler" type="text/x-handlebars-template">
  
                <table id="userTable" class="table table-striped table-bordered userTable" style="width:100%">
            
                    <thead>
                        <tr>
                            <th>User Name</th>
							<th>LoginId</th>
                            <th>Credit Reference</th>
                            <th>Balance</th>
                            <th>LenaDena(P/L)</th>
							<th>Exposure</th>
 							<th>Available Balance</th>
                            <th>Down Level Occupy Balance</th>
                          
                            <th>Exposure Limit</th>
                            <th>Sport %</th>
							<th>Casino %</th>
                            <th>Account Type</th>
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
						   <td>{{creditRef}}</td>
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
                           
                           {{#if_eq id ${user.id}}} 
							 <td></td>  
							{{else}}
							 <td><span class="deposit-btn" onclick="modalDeposite('{{parentid}}','{{userid}}','{{availableBalance}}')" style="background-color: green;" >D</span><span class="withdraw-btn" style="background-color: red;"  onclick="modalWithdraw('{{parentid}}','{{userid}}','{{secondrybanlce}}')">W</span>
<span class="password-btn" onclick="changePasswordModal('{{userid}}','{{parentid}}')">P</span>
								<span class="password-btn" onclick="viewDetailModal('{{userid}}','{{parentid}}')">V</span>
															 
 {{#if_eq betlock true}}  
								<span class="limit-btn background_red" onclick="betUnLockByParent({{id}})">B/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="betLockByParent({{id}})">B/L</span>
							  {{/if_eq}}	
							  {{#if_eq accountlock true}}  
								<span class="limit-btn" onclick="accountUnLockByParent({{id}})">A/UL</span>
 							  {{else}}
								<span class="limit-btn" onclick="accountLockByParent({{id}})">A/L</span>
							  {{/if_eq}} 
								<span class="limit-btn" onclick="khataBookModal({{id}})">K</span>

							  {{#if_eq lCasinoLock true}}  
								<span class="limit-btn background_red" onclick="casinoBetUnLock('{{id}}','L')">LCB/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="casinoBetLock('{{id}}','L')">LCB/L</span>
							  {{/if_eq}} 

							  {{#if_eq vCasinoLock true}}  
								<span class="limit-btn background_red" onclick="casinoBetUnLock('{{id}}','V')">VCB/UL</span>
 							  {{else}}
								<span class="limit-btn background_green" onclick="casinoBetLock('{{id}}','V')">VCB/L</span>
							  {{/if_eq}} 

							  {{#if_eq isaccountclosed true}}  
								  <span class="limit-btn background_red" onclick="accountOpenByParent('{{id}}')">A/O</span>
  							  {{/if_eq}} 
							</td>
							{{/if_eq}}	 
							
                        </tr>

	{{/each}} 
                    </tbody>
            
                </table>
            
</script>



<script id="deposite_withdraw_modalHandler" type="text/x-handlebars-template">

          <h4>Lena/Dena</h4> 
       <input type ="hidden" class="mypnl" ></input>
       <input class="datepicker fa fa-calendar-o" id="date" type="hidden">
      <div class="d-cancel-btn"><i class="fa fa-times" aria-hidden="true"></i></div>
      <form  id="DepositWithraw-form" class="mt-4">
       <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="parentdeposite">${user.userid}:</label></div>
        <div class="clientN-right">
          <input type="hidden" class="frst-input mainbalanceparentdepositewithrawhidden" name="mainbalanceparendepositewithrawhidden" id="mainbalanceparentdepositewithrawhidden"  readonly>
          <input type="number" class="frst-input mainbalanceparentdepositewithraw" name="mainbalanceparentdepositewithraw" id="mainbalanceparentdepositewithraw"  readonly>
          <input type="text" class="scnd-input aftermainbalanceparentdepositewithraw" name ="aftermainbalanceparentdepositewithraw" name="aftermainbalanceparentdepositewithraw" id="aftermainbalanceparentdepositewithraw" readonly>
        </div>
      </div>
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="childdeposite" class="childdeposite" id="childdeposite"></label></div>
        <div class="clientN-right">
         <input type="hidden" class="frst-input mainbalancechilddepositewithrawhidden" id="mainbalancechilddepositewithrawhidden"  readonly>
          <input type="text" class="frst-input mainbalancechilddepositewithraw" id="mainbalancechilddepositewithraw"  readonly>
          <input type="text"  class="scnd-input aftermainbalancechilddepositewithraw" name="aftermainbalancechilddepositewithraw" id="aftermainbalancechilddepositewithraw" readonly>
        </div>
      </div>
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Ammount:</label></div>
        <div class="clientN-right">
          <input type="number" class="frst-input full-input amountdepositewithraw" id="amountdepositewithraw" name="amountdepositewithraw"  >
        </div> 
      </div>
     
      
       <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="ammount">Remark:</label></div>
        <div class="clientN-right">
          <input type="text" class="frst-input full-input remarkdepositewithraw" id="remarkdepositewithraw" name="remarkdepositewithraw" >
        </div> 
      </div>
      
      <div class="client-D-box client-popUp">
        <div class="clientN-left"><label for="m-password">Master Password:</label></div>
        <div class="clientN-right">
          <input type="password" id="parentpassdepositewithraw" name="parentpassdepositewithraw" class="frst-input full-input">
        </div>
      </div>
      <div class="deposit-btn-box">
        <div class="back-d-btn  deposit-button">
          <i class="fa fa-undo text-white" ></i>
          <input type="button" class="back-d-btn"  value="Back">
        </div>
        <div class="submit-d-btn deposit-button">
          <input type="button" class="submit-d-btn" onclick="chipCreditWithdraw()" value="Submit">
          <i class="fa fa-sign-in text-white"></i>
        </div>
       
      </div>
    </form>
            
</script>



<div class="cliennt-container">
        <div class="upper-container">
            <div class="client-btn"><i class="fa fa-arrow-circle-o-down" ></i></div>
            <div class="toggle-container">
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
    	userList(true);
		});
    	</script>
<jsp:include page="masterListjs.jsp" />  
<jsp:include page="currentbetsmodal.jsp" />
<jsp:include page="handlebars.jsp" />  
