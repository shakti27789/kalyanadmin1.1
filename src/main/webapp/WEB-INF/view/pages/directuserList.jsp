<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.loader {
    position: fixed;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    z-index: 9999;
    background: url('<s:url value="/images/loader6.gif"/>') 50% 50% no-repeat ;
    opacity: .9;
}
</style>


      <script id="userlisthandle" type="text/x-handlebars-template">

     <div class="table-responsive">
             	<table class="table common-table">
								  <thead>
									  <tr>
										  <th>Login Name</th>
										  <th>User ID</th>
										  <th>Net Exposure</th>
										  <th>CreditLimit</th>                          
										  <th>Current P&amp;L</th>           
										  <th>Commission</th>
										  <th>Balance</th>
										  <th>Action</th>
									  </tr>
								  </thead>
								  <tbody>    
								{{#each data}}                       
									  <tr>
										  <td> <a class="usericon" href="<s:url value="/statementByUser/"/>{{userid}}/{{username}}"</a>{{username}}</td>
										  <td>{{userid}}</td>
										  <td><button onclick="GetExposureList('{{userid}}')">{{userliability}}</button></td>
										  <td>{{primarybalance}}</td>
										  {{#if_eq winlossamount 0}}
										 <td class="red">{{winlossamount}}</td> 
										 {{else}}
										<td class="green">{{winlossamount}}</td>
										{{/if_eq}}
										 {{#if_eq usercomm 0}}
										<td class="green" data-toggle="modal" data-target="#partnerships" onclick="showPartnership('{{userid}}',3,'{{type}}')">{{usercomm}}</td> 
										 {{else}}
										<td class="green" data-toggle="modal" data-target="#partnerships" onclick="showPartnership('{{userid}}',3,'{{type}}')">{{usercomm}}</td>
										{{/if_eq}}
										  
										  <td class="green ng-binding">{{secondrybanlce}}</td>                         
										  <td>
											<span class="btn green" onclick="dipositChipToMaster('{{parentid}}','{{userid}}','{{username}}','{{usertype}}','{{secondrybanlce}}')" >D</span>
											<span class="btn red"  onclick="withdrawChip('{{parentid}}','{{userid}}','{{username}}','{{usertype}}','{{secondrybanlce}}')" >W</span>
											<span class="btn yellow" onclick="ShowUserDetails('{{parentid}}','{{password}}','{{primarybalance}}','{{netexposure}}','{{userid}}','{{usertype}}','{{id}}')"><i class="fa fa-eye"></i></span> 
											
											 <span class="btn btn-success btn-sm"  data-toggle="modal"  onclick="getSubadminDetails('{{userid}}',{{appid}},{{usertype}})"><i class="fa fa-edit"></i></span>
											{{#if accountlock true}}
											<span class="btn yellow"onclick="accountUnLock('{{id}}','{{usertype}}','{{parentid}}')"><i class="fa fa-lock"  ></i> A/C UNLK</span>
											{{else}}
											<span class="btn yellow" onclick="accountLock('{{parentid}}','{{id}}','{{usertype}}')"><i class="fa fa-lock"></i> A/C LK</span>		
											{{/if}}	

											{{#if betlock true}}
										<span class="btn yellow" onclick="betLock('{{id}}')"><i class="fa fa-lock" ></i> Bet LK</span>
											{{else}}
									    <span class="btn yellow" onclick="betUnLock('{{id}}')"><i class="fa fa-lock" ></i> Bet UnLK</span>
											{{/if}}	
											
												<span class="btn yellow" onclick="makeusersettlement('{{id}}','{{primarybalance}}','{{secondrybanlce}}','{{parentid}}','{{usertype}}','{{pnlamount}}','{{usercomm}}')">Transfer</span>
											<span class="btn yellow" onclick="deleteUser('{{id}}')" id="deleteUser"> INACTIVE</span>
<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#details" onclick="getdetails('{{id}}')">Details</button>
											
										  </td>                          
									  </tr>
									{{/each}}  
								  </tbody>
								</table>
</div>
    </script>
    
   <input type="hidden" value="${user.userid}" id="userid">
 <div id="page-wrapper">
 <div class="balance">
        <div class="status">
            <dl class="status-list">
                <!-- <dt>Market Exposure:</dt>
                <dd class="red">{{mExposure | number : 2}}</dd>
                <dt>Fancy Exposure:</dt>
                <dd class="red">-{{fExposure | number :2}}</dd> -->
                <dt id="pbalance"></dt>
                <dt id="dealerpnl">Curr. PnL:</dt>
               <!-- <dd  class="ng-binding green">1,600.00</dd>-->
                <dt id="exposure">Net Exposure:</dt>
                <dd  class="ng-binding"></dd>
               
                <dt id = "creditlimit">Total Credit Given:</dt>
                <dd  class="ng-binding green"></dd>
                 <dt id = "avbcreditlimit">Avaliable Credit :</dt>
                <dd  class="ng-binding green"></dd>
                <dt id="balance">User Available Bal :</dt>
                <dd  class="ng-binding green"></dd>
            </dl>  
        </div>
    </div>
    
    
 <input type ="hidden" value="" id="masterid">
<input type ="hidden" value="" id="masteruserid">

            <div class="inner-wrap">
				<div class="row">
				
					<div class="col-lg-12">
				
						<h1 class="page-header" id="header">Total Master
						<a class="btn new-member-btn pull-right" href="javascript:void(0)" id ='backtomaster' onclick='masterList()'>Back</a>
						<a class="btn new-member-btn pull-right" href="javascript:void(0)" id ='backtoDealer' onclick="dealerList()">Back</a></h1>
							<div class="panel-body text-center" id="noUsers">
					<h1>No Users Available</h1>
				</div>	
						<section class="menu-content" id="userlistdiv" style="min-height:400px;">
						
						</section>
						
						
					</div>
					<!-- /.col-lg-12 -->
				</div>
		-
			<!-- /.row --> 
            </div>  
            
                    
        </div>
        <!-- /#page-wrapper -->
         
    </div>
    <!-- /#wrapper -->
    
    <div id="partnerships" class="modal fade modal-style" style="color:white;" role="dialog">
	  <div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content modal-background" >
		
		  <div id="sessionbooks" class="modal-body" >
		  		<center><h2>Partnerships</h2></center>
										<div class="panel-body bets-table-wrap">
										<center><h4>Type:- <span id="typepart"></span></h2></center>
										<font size="2" face="Courier New" style="color:White;">
											<table>
											<tr><th>Master</th></tr>
											<tr><td>OddsLoss Comm.: <input type="text" class="modal-background" name="moloss" id="moloss" size="12" readonly onKeyUp="validateMOddsLoss()"/></td>
											<td>OddsWin Comm.: <input type="text" class="modal-background" name="mowin" id="mowin" size="12" readonly onKeyUp="validateMOddswin()"/></td>
											<td>FancyLoss Comm.: <input type="text" class="modal-background" name="mfloss" id="mfloss" size="12" readonly onKeyUp="validateMfancyloss()"/></td>
											<td>FancyWin Comm.: <input type="text" class="modal-background" name="mfwin" id="mfwin" size="12" readonly onKeyUp="validateMfancywin()"/></td>
											</tr>
											<tr>
											<th>----------------</th>
											</tr>
											
											<tr><th>Dealer</th></tr>
											<tr><td>OddsLoss Comm.: <input type="text" class="modal-background" name="doloss" id="doloss" size="12" readonly onKeyUp="validateDodloss()"/></td>
											<td>OddsWin Comm.: <input type="text" class="modal-background" name="dowin" id="dowin" size="12" readonly onKeyUp="validateDodwin()"/></td>
											<td>FancyLoss Comm.: <input type="text" class="modal-background" name="dfloss" id="dfloss" size="12" readonly onKeyUp="validateDfloss()"/></td>
											<td>FancyWin Comm.: <input type="text" class="modal-background" name="dfwin" id="dfwin" size="12" readonly onKeyUp="validateDfwin()"/></td>
											</tr>
											<tr>
											<th>----------------</th>
											</tr>
											<tr><th >User</th></tr>
											<tr><td>OddsLoss Comm.: <input type="text" class="modal-background" name="uoloss" id="uoloss" size="12" readonly onKeyUp="validateuoddsloss()"/></td>
											<td>OddsWin Comm.: <input type="text" class="modal-background" name="uowin" id="uowin" size="12" readonly onKeyup="validateuoddswins()"/></td>
											<td>FancyLoss Comm.: <input type="text" class="modal-background" name="ufloss" id="ufloss" size="12" readonly onKeyup="validateufloss()"/></td>
											<td>FancyWin Comm.: <input type="text" class="modal-background" name="ufwin" id="ufwin" size="12" readonly onKeyUp="validateufwin()"/></td>
											</tr>
											</table>
											</font>
										</div>											
			
		  </div>
		  <div class="modal-footer">
		  <button type="button" id="savePart" style="display:none" class="btn btn-default" onclick="SavePartnership(seuserid,seusertype,checkusertypes)" >Save</button>
		  	<button type="button" id="editPart" class="btn btn-default" onclick="MakepartnshipEditable()" >Edit</button>
			<button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
		  </div>
		</div>
	  </div>
	</div>
    
    
        <div id="details" class="modal fade modal-style" style="color:white;" role="dialog">
	  <div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content modal-background" >
		
		  <div id="details" class="modal-body" >
		  		<center><h2>Login id    :<span id="userid_detail"></span></h2></center>
										<div class="panel-body bets-table-wrap">
										<center><h4>Passowrd    :<span id="password_detail"></span></h2></center>
										<center><h4> Account Type    :<span id="type_detail"></span></h2></center>
										<center><h4>Commision Type    :<span id="commissionType_detail"></span></h2></center>
										<center><h4>Available Balance    :<span id="availableBal_detail"></span></h2></center>
										
											<table>
											<tbody>
											<tr><th style="color:red;">Partnership</th></tr>
											<tr><td id="adminpartnershipname_detail">Admin</td>
											<td id="adminpartnership_detail"></td></tr>
											<tr><td id="masterpartnershipname_detail">Master</td>
											<td id="masterpartnershipn_detail"></td></tr>
											<tr><td id="dealerpartnershipname_detail"> Dealer</td>
											<td id="dealerpartnership_detail"></td></tr>

											
											<tr><th style="color:red;">Commission</th></tr>
											<tr><td >Oddswin</td>
											<td id="oddswin_detail">          :3</td></tr>
											<tr><td >Oddsloss</td>
											<td id="oddsloss_detail">          :3</td></tr>
											<tr><td >Fancywin</td>
											<td id="fancywin_detail">          :3</td></tr>
											<tr><td >Fancyloss</td>
											<td id="fancyloss_detail">          :3</td></tr>							
											</tbody>
											</table>
										
										</div>											
			
		  </div>
		</div>
	  </div>
	</div>
	
	
	 <div id="editsub" class="modal fade modal-style" style="color:white;" role="dialog">
	 <div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content modal-background" >			
            
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit</h1>
						<section class="menu-content">
							<form id="masterform">
								<div class="row">									
									<div class="col-sm-4">
										<div class="inner-form">
											
											<div class="form-group">
												<label>User Name</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter name" id="subadminusername" name="subadminusername" class="form-control" required/>
											</div>
											
											
											<div class="form-group">
												<label>Reference</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Reference" id="subadminreference" name="subadminreference" class="form-control" required/>
											</div>
											
											
											
												<div class="form-group">
												<label>Contact</label>
												<input type="number" maxlength="10" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter contact number" id="sunadmincontact" name="sunadmincontact" class="form-control" required/>
											</div>
											
											<div class="form-group">
												<label>Password</label>
												<input type="password" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter password" id="subadminpassword" name="subadminpassword" class="form-control" required/>
											</div>
											
											
											<div class="form-group" >
												<label>	Bhao Type</label>
											    <select class="form-control" id="bhaotype">
											     <option value="1" >1</option>
											      <option value="2">2</option>
											    </select>
											</div>
				
											
											<div class="form-group">
												<label>	Mobile App / Commission Type</label>
											    <select class="form-control" id="sunadmincommisiontype">
											     <option value="mobapp" >Mobile App</option>
											      <option value="commision">Commssion</option>
											    </select>
											</div>
											
											<div class="form-group" id="mobappmain">
												<label>	Mobile App / Commission</label>
											    <select class="form-control" id="subadmincommision">
											   </select>
											</div>
											
											<div class="form-group"  id="percentageinput">
												<label>Commission Percentage</label>
												 <select class="form-control" id="percen">
											     <option value="0" >0</option>
											      <option value="0.5">0.5</option>
											      <option value="1.0">1.0</option>
											      <option value="1.5">1.5</option>
											      <option value="2.0">2.0</option>
											      <option value="2.5">2.5</option>
											      <option value="3.0">3.0</option>
											    </select>
											</div>
											
											<div class="form-group" id="losscommmain">
												<label>Loss Commision</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Commission" id="subadminlosscommission" name="losscommission" class="form-control" required/>
											</div>
																																															
										</div>
									</div>
									
									
									<div class="col-sm-4">
									<div class="inner-form">
									
									<div class="form-group">
														<label> Session Commission Type</label>
											<input type ="text" class ="form-control" id="oddstype"  readonly="true" >
											</div>
											<div class="form-group" id="oddsdiv">
														<label id="label1">Odds Comm.</label>
											    <select class="form-control" id="oddsWinCommsimpple">
											     <option>0.00</option>
											     <option>0.25</option>
											      <option>0.50</option>
											      <option>0.75</option>
											      <option>1.00</option>
											      <option>1.25</option>
											      <option>1.50</option>
											      <option>1.75</option>
											      <option>2.00</option>
											      <option>2.25</option>
											       <option>2.50</option>
											      <option>2.75</option>
											      <option>3.00</option>
											    </select>
											</div>											
											<div class="form-group" id="oddslossdiv">
														<label id="label2">Session Comm.</label>
											    <select class="form-control" id="oddsLossCommsimpple">
											     <option>0.00</option>
											     <option>0.25</option>
											      <option>0.50</option>
											      <option>0.75</option>
											      <option>1.00</option>
											      <option>1.25</option>
											      <option>1.50</option>
											      <option>1.75</option>
											      <option>2.00</option>
											      <option>2.25</option>
											       <option>2.50</option>
											    </select>
											</div>				
									
										
										
											<div class="form-group">
												<label>Application name</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Application name" id="subadminappname" name="appname" class="form-control" required/>
											</div>
											<div class="form-group">
												<label>application url</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Url"  id="subadminappurl" name=appurl class="form-control" required />
											</div>
									

											<div class="form-group">
												<label>Credit Limit</label>
												<input type="number"   placeholder="Enter Credit limit" id="subadminprimarybalance" name="primarybalance" class="form-control" required/>
												
											</div>

									
								</div>
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclick="dismissEditSubadmin()">Cancel</button>									
									<button class="btn btn-primary" type="button" onclick="saveSubadminDetails()">Save</button>									
								</div>
								</div>
							</form>
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
                      
        </div>
        <!-- /#page-wrapper -->
        </div></div>
    </div>
	
	
	 <div id="editspmaster" class="modal fade modal-style" style="color:white;" role="dialog">
	 <div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content modal-background" >			
          <div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit</h1>
						<section class="menu-content">
							<form id="spmasterform">
							<div class="row">									
									<div class="col-sm-4">
										<div class="inner-form">
											
											<div class="form-group">
												<label>Name</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter name" id="spusername" name="username" class="form-control" required/>
											</div>
											<div class="form-group">
												<label>LoginID</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter User Name"  id="spuserid" name=userid class="form-control" required />
											</div>
											<div class="form-group">
												<label>Reference</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Reference" id="spreference" name="reference" class="form-control" required/>
											</div>
											
											
											
											<div class="form-group">
												<label>Contact</label>
												<input type="number" maxlength="10" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter contact number" id="spcontact" name="contact" class="form-control" required/>
											</div>
											
											<div class="form-group">
												<label>Credit  limit</label>
												<input type="number" maxlength="50" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter creditlimit" id="spcreditlimit" name="creditlimit" class="form-control" required/>
											</div>
											
											<div class="form-group">
												<label>Password</label>
												<input type="password" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Password"  id="sppassword" name="password" class="form-control" required/>
											</div>		
									</div>
									</div>
									
									
									<div class="col-sm-4">
									<div class="inner-form">
									
									<div class="form-group">
														<label> Session Commission Type</label>
											    <select class="form-control" id="spoddstype">
											      <option>Commission type</option>
											      <!-- <option>No Commission</option> -->
											      <!--<option>Only on minus</option>-->
											      <option>Bet by Bet</option>
											      <!--<option>Session minus & Bet minus</option>-->
											    </select>
											</div>
											<div class="form-group" id="spoddsdiv">
														<label id="splabel1">Odds Comm.</label>
											    <select class="form-control" id="spoddsWinCommsimpple">
											     <option>0.00</option>
											     <option>0.25</option>
											      <option>0.50</option>
											      <option>0.75</option>
											      <option>1.00</option>
											      <option>1.25</option>
											      <option>1.50</option>
											      <option>1.75</option>
											      <option>2.00</option>
											      <option>2.25</option>
											       <option>2.50</option>
											      <option>2.75</option>
											      <option>3.00</option>
											    </select>
											</div>											
											<div class="form-group" id="spoddslossdiv">
														<label id="splabel2">Session Comm.</label>
											    <select class="form-control" id="spoddsLossCommsimpple">
											      <option>0.00</option>
											     <option>0.25</option>
											      <option>0.50</option>
											      <option>0.75</option>
											      <option>1.00</option>
											      <option>1.25</option>
											      <option>1.50</option>
											      <option>1.75</option>
											      <option>2.00</option>
											      <option>2.25</option>
											       <option>2.50</option>
											    </select>
											</div>				

									
								</div>
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclick="dismissEditSubadmin()">Cancel</button>									
									<button class="btn btn-primary" type="button" id="savedetailssp" onclick="">Save</button>									
								</div>
							</form>
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->  
				
        </div>
        <!-- /#page-wrapper -->
        </div></div>
    </div>
    
    <script>
    var moddsl;
    var moddsw;
    var mfl;
    var mfw;
    var doddsl;
    var doddsw;
    var dfl;
    var dfw;
    var uoddsl;
    var uoddsw;
    var ufl;
    var ufw;
    var seuserid;
    var seusertype;
    var checkusertypes;
    var partype;
    var partnershipsedit;
    $(document).ready(function(){
    
    getCommission();
    	if(${user.usertype !=0}){
        	$("#deleteUser").hide();    		
    	}
    	
    	$("#usertype").val(3);
    		userList('${user.id}','${user.userid}');
    		 //$("#backtoDealer").hide();
    		 //$("#backtomaster").hide();
    	
    	
    	
    	 $("#sunadmincommisiontype").change(function(){
        var val = $(this).children("option:selected").val();
       
       if(val == 'mobapp'){
         $("#losscommmain").hide();
       $("#mobappmain").show();
       $("#percentageinput").hide();
       }else{
       $("#percentageinput").show();
         $("#losscommmain").show();
       $("#mobappmain").hide();
       }
    });
    	
    	
    	 $("#oddstype").change(function(){
        var  type = $("#oddstype").val();
        if(type == 'No Commission'){
       
        }else if(type == 'Only on minus'){
        $("#label1").html("Match + Session Minus");
         $("#oddsdiv").show();
    	$("#oddslossdiv").hide();
        }else if(type == 'Bet by Bet'){
         $("#label1").html("Match Commission");
          $("#label2").html("Session Commission");
        $("#oddsdiv").show();
    	$("#oddslossdiv").show();
        }else{
        
        }  
    });
    
    
     $("#spoddstype").change(function(){
        var  type = $("#spoddstype").val();
        if(type == 'No Commission'){
        $("#spoddsdiv").hide();
    	$("#spoddslossdiv").hide();
        }else if(type == 'Only on minus'){
        $("#splabel1").html("Match + Session Minus");
         $("#spoddsdiv").show();
    	$("#spoddslossdiv").hide();
        }else if(type == 'Bet by Bet'){
         $("#splabel1").html("Match Commission");
          $("#splabel2").html("Session Commission");
        $("#spoddsdiv").show();
    	$("#spoddslossdiv").show();
        }else{
        
        }  
    });
    
    	 
    });
    
    
    function validateMOddsLoss() {            
		    var FieldVal = document.getElementById('moloss').value;  
		                   
		    if(FieldVal < 0) {   
		    showMessage("Commision Can't be negative","error","Oops...")        
		       $('#moloss').val(0);         
		    } else { 
		    	          
		         $('#moloss').val(FieldVal);
		    }           
		         
		}
    
     function validateMOddswin() {            
		    var FieldVal = document.getElementById('mowin').value;  
		                   
		    if(FieldVal < 0) {   
		    showMessage("Commision Can't be negative","error","Oops...")        
		       $('#mowin').val(0);         
		    } else { 
		    	          
		         $('#mowin').val(FieldVal);
		    }           
		         
		}
    
    
     function validateMfancyloss() {            
		    var FieldVal = document.getElementById('mfloss').value;  
		                   
		    if(FieldVal < 0) {   
		    showMessage("Commision Can't be negative","error","Oops...")        
		       $('#mfloss').val(0);         
		    } else { 
		    	          
		         $('#mfloss').val(FieldVal);
		    }           
		         
		}
		
		 function validateMfancywin() {            
		    var FieldVal = document.getElementById('mfwin').value;  
		                   
		    if(FieldVal < 0) {   
		    showMessage("Commision Can't be negative","error","Oops...")        
		       $('#mfwin').val(0);         
		    } else { 
		    	          
		         $('#mfwin').val(FieldVal);
		    }           
		         
		}
    
    
    function validateDodloss() {            
		    var FieldVal = document.getElementById('doloss').value;  
		    
		                   
		    if(FieldVal <= moddsl) {           
		        $('#doloss').val(FieldVal);      
		    } else {  
		    if(moddsl == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#doloss').val(moddsl);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#doloss').val(moddsl - 1);
		    }           
		         
		    } 
		  
		}
		
		
		
		 function validateDodwin() {            
		    var FieldVal = document.getElementById('dowin').value;  
		    
		                   
		    if(FieldVal <= moddsw) {           
		        $('#doloss').val(FieldVal);      
		    } else {  
		    if(moddsw == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#dowin').val(moddsw);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#dowin').val(moddsw - 1);
		    }           
		         
		    } 
		  
		}
    
    
    
		 function validateDfloss() {            
		    var FieldVal = document.getElementById('dfloss').value;  
		    
		                   
		    if(FieldVal <= mfl) {           
		        $('#dfloss').val(FieldVal);      
		    } else {  
		    if(mfl == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#dfloss').val(mfl);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#dfloss').val(mfl - 1);
		    }           
		         
		    } 
		  
		}
    
    
    
 
  	function validateDfwin() {            
		    var FieldVal = document.getElementById('dfwin').value;  
		    
		                   
		    if(FieldVal <= mfw) {           
		        $('#dfwin').val(FieldVal);      
		    } else {  
		    if(mfw == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#dfwin').val(mfw);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#dfwin').val(mfw - 1);
		    }           
		         
		    } 
		  
		}   
    
    
    function validateuoddsloss() {            
		    var FieldVal = document.getElementById('uoloss').value;  
		    
		                   
		    if(FieldVal <= doddsl) {           
		        $('#uoloss').val(FieldVal);      
		    } else {  
		    if(doddsl == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#uoloss').val(doddsl);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#uoloss').val(doddsl - 1);
		    }           
		         
		    } 
		  
		}   
    
    
    
     function validateuoddswins() {            
		    var FieldVal = document.getElementById('uowin').value;  
		    
		                   
		    if(FieldVal <= doddsw) {           
		        $('#uowin').val(FieldVal);      
		    } else {  
		    if(doddsw == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#uowin').val(doddsw);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#uowin').val(doddsw - 1);
		    }           
		         
		    } 
		  
		}   
		
		
		 function validateufloss() {            
		    var FieldVal = document.getElementById('ufloss').value;  
		    
		                   
		    if(FieldVal <= dfl) {           
		        $('#ufloss').val(FieldVal);      
		    } else {  
		    if(dfl == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#ufloss').val(dfl);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#ufloss').val(dfl - 1);
		    }           
		         
		    } 
		  
		}   
    
    
    function validateufwin() {            
		    var FieldVal = document.getElementById('ufwin').value;  
		    
		                   
		    if(FieldVal <= dfw) {           
		        $('#ufwin').val(FieldVal);      
		    } else {  
		    if(dfw == 0) { 
		    showMessage("Commision Can't be Greater than Master","error","Oops...") 
		    $('#ufwin').val(dfw);
		    }else{
		     showMessage("Commision Can't be Greater than Master","error","Oops...") 
		          
		         $('#ufwin').val(dfw - 1);
		    }           
		         
		    } 
		  
		}   
    
     function MakepartnshipEditable()
	  {
	  if($("#usertype").val()==1){
	  document.getElementById('moloss').removeAttribute('readonly');
	    document.getElementById('mowin').removeAttribute('readonly');
	    document.getElementById('mfloss').removeAttribute('readonly');
	    document.getElementById('mfwin').removeAttribute('readonly');
	     document.getElementById('doloss').removeAttribute('readonly');
	    document.getElementById('dowin').removeAttribute('readonly');
	    document.getElementById('dfloss').removeAttribute('readonly');
	    document.getElementById('dfwin').removeAttribute('readonly');
	     document.getElementById('uoloss').removeAttribute('readonly');
	    document.getElementById('uowin').removeAttribute('readonly');
	    document.getElementById('ufloss').removeAttribute('readonly');
	    document.getElementById('ufwin').removeAttribute('readonly');
	  }else if($("#usertype").val()==2){
	   document.getElementById('doloss').removeAttribute('readonly');
	    document.getElementById('dowin').removeAttribute('readonly');
	    document.getElementById('dfloss').removeAttribute('readonly');
	    document.getElementById('dfwin').removeAttribute('readonly');
	     document.getElementById('uoloss').removeAttribute('readonly');
	    document.getElementById('uowin').removeAttribute('readonly');
	    document.getElementById('ufloss').removeAttribute('readonly');
	    document.getElementById('ufwin').removeAttribute('readonly');
	  }else{
	   document.getElementById('uoloss').removeAttribute('readonly');
	    document.getElementById('uowin').removeAttribute('readonly');
	    document.getElementById('ufloss').removeAttribute('readonly');
	    document.getElementById('ufwin').removeAttribute('readonly');
	  }
	    
	    $("#editPart").hide();
	    $("#savePart").show();
	  }
    
    
    
    
    
    function userList(id,userid)
    {
    $("#newmmbr").hide();
    if('${user.usertype}'==2){
    $("#backtoDealer").hide();
    $("#backtomaster").hide();
    }else{
     $("#backtoDealer").show();
    $("#backtomaster").hide();
    }
    
    if(id===undefined && userid===undefined){
    id = $("#id").val();
    userid = $("#userid").val();
    
    }else{
  
      $("#id").val(id)
    $("#userid").val(userid)
   
    }
    
    	if(userid!=null && userid!="undefined"){
    		$("#userid").val(userid)
    	}
    	
    	 $("#header").text("Total Users [0]");
		/* $('<a class="btn new-member-btn pull-right" href="<s:url value="/createUser/6"/>'+id+'">New Member</a>').appendTo("#header");
    */
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/user/"/>'+id+"/"+3+"?active=true",
			success: function(jsondata, textStatus, xhr) {
			{

   	 				 $("#header").text("Total Users [0]");
			    	/* $('<a class="btn new-member-btn pull-right" href="<s:url value="/createUser/6/"/>'+id+'">New Member</a>').appendTo("#header");
			    	*/
				if(xhr.status==200){
				       $("#noUsers").hide();
				       $("#userlistdiv").show();

					$("#usertype").val(3);
					  $("#userlistdiv").html('');
					   var source   = $("#userlisthandle").html();
			    	  var template = Handlebars.compile(source);
			    	  $("#userlistdiv").append( template({data:jsondata.data})); 
			    	   console.log(Object.keys(jsondata).length);
			    	  var jsonObject= JSON.stringify(jsondata.data); 
			    	  $("#header").text("Total Users"+" ["+""+JSON.parse(jsonObject).length+"]");
			    	 /* $('<a class="btn new-member-btn pull-right" href="<s:url value="/createUser/6/"/>'+id+'">New Member</a>').appendTo("#header");
			    	  	*/				
				}else{
				       $("#noUsers").show();
				       $("#userlistdiv").hide();
				}

			}
		}
	});
	
	$("#balance").text('User Available Balance:');
	$("#pbalance").text('Parent Available Balance:');
	//getPnlandOthers(id,2);
	$('#avbcreditlimit').hide();
   }
    
    function accountLock(parentid,id,usertype)
    {
    	
    	
    	swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Lock This Account!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes, Lock this!',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  $.ajax({
    	  			type:'PUT',
    	  			url:'<s:url value="api/accountLock/"/>'+id+"/"+usertype,
    	  			success: function(jsondata, textStatus, xhr) 
    	  			{
    	  				showMessage(jsondata.message,jsondata.type,jsondata.title);
    	  				$("#userlistdiv").html('');
    	  				
    	  				if(usertype=='5'){
			    		 subadminlist();
			    		}else if(usertype=='0'){
			    		 suprstockagelist(parentid);
			    		}else if(usertype=='1'){
						masterList(parentid);
					
					}
    					else if(usertype =='2'){
    						dealerList(parentid);
    						
    					}
    					else{
    							userList(parentid);	
    						}
    	  			},
    	  			complete: function(jsondata,textStatus,xhr) {
    	  				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    	  				 
    	  		    }  
    	  		}); 
    	    	//  swal('Deleted!', 'Your file has been deleted!', 'success');
    	        
    	      }
    	    });
    	
    	
   }
    
    function accountUnLock(id,usertype,parentid)
    {
    	
    	$.ajax({
			type:'PUT',
			url:'<s:url value="api/accountUnLock/"/>'+id,
			success: function(jsondata, textStatus, xhr) 
			{
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				$("#userlistdiv").html('');
			if(usertype=='5'){
    		 subadminlist();
    		}else if(usertype=='0'){
    		 suprstockagelist(parentid);
    		}else if(usertype=='1'){
					masterList(parentid);
					
					}
				else if(usertype =='2'){
					dealerList(parentid);
					
				}
				else{
						userList(parentid);	
					}
				
			},
			complete: function(jsondata,textStatus,xhr) {
				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				 
		    } 
		
	});
   }
    
    function dipositChipToMaster(parentid,userid,username,usertype,secondarybalance){
    	
    	swal({
    	      title: 'Current Balance ::'+secondarybalance+'',
    	      html: '<input type="text" id="dipositebal" class="form-control" id="dipositebal">',
    	      showCancelButton: true,
    	      confirmButtonText: 'Deposit',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  var chips =$("#dipositebal").val()
    	    	
 	             var data = {parentid:parentid,userid:userid,usernameby:$("#userid").val(),usernameto:username,chips:chips}
    	    	   $.ajax({
  					type:'POST',
  					url:'<s:url value="api/chipCredit"/>',
  					data: JSON.stringify(data),
  					dataType: "json",
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) 
  					{
  						$("#userlistdiv").html('');
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						
  						if(usertype=="5"){
    							 subadminlist();
    						}else if(usertype=="0"){
    							 suprstockagelist(parentid);
    						}else if(usertype=="1"){
  							 masterList(parentid);
  						} else if(usertype=="2"){
  							 dealerList(parentid);
  						}
  						else{
  						userList(parentid);	
  						}
  						
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						 
  				    }
  			});
    	    	  
    	      }
    	    });
    	    $('#dipositebal').focus();
    }
    
		function withdrawChip(parentid,userid,username,usertype,secondarybalance){
    	
    	swal({
    	      title: 'Current Balance ::'+secondarybalance+'',
    	      html: '<input type="text" id="withdrawchip" class="form-control">',
    	      showCancelButton: true,
    	      confirmButtonText: 'WithDraw',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  var chip =$("#withdrawchip").val();
    	    	 
    	    	  if(chip!=null){
 	             var data = {parentid:parentid,userid:userid,usernameby:$("#userid").val(),usernameto:username,chips:chip}
    	    	   $.ajax({
  					type:'POST',
  					url:'<s:url value="api/chipWithdraw"/>',
  					data: JSON.stringify(data),
  					dataType: "json",
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) 
  					{
  						$("#userlistdiv").html('');
  						$("#withdrawchip").val(0);
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						if(usertype=="5"){
    							 subadminlist();
    						}else if(usertype=="0"){
    							 suprstockagelist(parentid);
    						}else if(usertype=="1"){
  							 masterList(parentid);
  						} else if(usertype=="2"){
  							 dealerList(parentid);
  						}
  						else{
  						userList(parentid);	
  						}
  						
  						
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						 
  				    }
  			});
  			}
    	   }
    	    });
    	    $('#withdrawchip').focus();
    }
    
    
     function ShowUserDetails(parentid,password,creditlimit,netexposure,userid,usertype,id){
    
    	
    	swal({
    	     title: 'Change Password',
    	      html: '<input type="text" id="password" placeholder="Enter Password" class="form-control" id="password"> <br><label> Current Credit Limit :: </label>'+creditlimit+'  <input type="number" id="usercreditlimit" placeholder="Change Credit Limit" class="form-control" id="creditlimit"> <br><label> Current Exposure limit :: </label>'+netexposure+'  <input type="number" id="usernetexposure" placeholder="Change Net Exposures" class="form-control" id="netexposure">',
    	      showCancelButton: true,
    	      confirmButtonText: 'Save',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  var pass =$("#password").val()
    	    	  var credit =$("#usercreditlimit").val()
    	          var exposure =$("#usernetexposure").val()
    	          
    	          
    	    	 if($.trim(pass).length>0 ||  $.trim(credit).length>0 ||  $.trim(exposure).length>0) {
 	             var data = {userid:userid,password:pass,primarybalance:credit,netexposure:exposure}
 	            
    	    	   $.ajax({
  					type:'POST',
  					url:'<s:url value="/api/updateUserDetials"/>',
  					data: JSON.stringify(data),
  					dataType: "json",
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) 
  					{
  						
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						if(usertype=="5"){
    							 subadminlist();
    						}else if(usertype=="0"){
    							 suprstockagelist(parentid);
    						}else if(usertype=="1"){
  							 masterList(parentid);
  						} else if(usertype=="2"){
  							 dealerList(parentid);
  						}
  						else{
  						userList(parentid);	
  						}
  						
  						forcelogout(id);
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						 
  				    }
  			});
  			
  			}  
  			else
    	{
    	showMessage("Kindly Update atleast one filed","error","Oops...")
    	}
    	    	  
    	      }
    	    });
    	    $('#password').focus();
    }
    
    
    
    function getPnlandOthers(parentid,usertype)
    {
    
    		
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/getPandLandOther/"/>'+parentid+"/"+usertype,
			success: function(jsondata, textStatus, xhr) {
			{
			$('#dealerpnl dd').hide();
			$('#creditlimit dd').hide();
			$('#exposure dd').hide();
			$('#balance dd').hide();
			$('#avbcreditlimit dd').hide();
			$('#avbcreditlimit').hide();
			$("#pbalance dd").hide();
				 //console.log(jsondata.totalpnl)
				 var val = jsondata.totalpnl;
				 var credit = jsondata.creditLimit;
				
				 if(val < 0){
				  $('<dd  class="ng-binding red">'+val+'</dd>').appendTo("#dealerpnl");
				 }else{
				  $('<dd  class="ng-binding green">'+val+'</dd>').appendTo("#dealerpnl");
				 }
				 
				 if(credit < 0){
				  $('<dd  class="ng-binding red">'+credit+'</dd>').appendTo("#creditlimit");
				 }else{
				  $('<dd  class="ng-binding green">'+credit+'</dd>').appendTo("#creditlimit");
				 }
				 
				 
				 if(jsondata.totalExpo < 0){
				 $('<dd  class="ng-binding red">'+jsondata.totalExpo+'</dd>').appendTo("#exposure");
				 }else{
				 $('<dd  class="ng-binding green">'+jsondata.totalExpo+'</dd>').appendTo("#exposure");
				 }
				 
				 
				 if(jsondata.balance < 0){
				  $('<dd  class="ng-binding red">'+jsondata.balance+'</dd>').appendTo("#balance");
				 }else{
				  $('<dd  class="ng-binding green">'+jsondata.balance+'</dd>').appendTo("#balance");
				 }
				  
				  if(jsondata.avacrdt < 0){
				  $('<dd  class="ng-binding red">'+jsondata.avacrdt+'</dd>').appendTo("#avbcreditlimit");
				 }else{
				  $('<dd  class="ng-binding green">'+jsondata.avacrdt+'</dd>').appendTo("#avbcreditlimit");
				 }
				 
				 if(jsondata.parentbal<0){
				  $('<dd  class="ng-binding red">'+jsondata.parentbal+'</dd>').appendTo("#pbalance");
				 }else{
				 $('<dd  class="ng-binding green">'+jsondata.parentbal+'</dd>').appendTo("#pbalance");
				 }
				
				  
				  
			}
		}
	});
   }
   
   
   
    function GetExposureList(userid){
    	swal({
    	     title: 'User Exposure Information',
    	     html: '<table class="table common-table"  id="exposuretable" border="1"><tr> <th style="color:red">Match Name</th><th style="color:red">Market/FancyName</th> <th style="color:red">Exposure</th></tr><tbody></tbody></table>',
    	      showCancelButton: true,
    	      showConfirmButton: false,
    	      confirmButtonText: 'Close',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    });
    	    $.ajax({
			type:'GET',
			url:'<s:url value="api/getnetExposurelistByUser/"/>'+userid,
			success: function(jsondata, textStatus, xhr) 
			{
			 $.each(jsondata, function(index, element) {
			 $('#exposuretable tr:last').after('<tr><td>'+element.matchname+'</td><td>'+element.adminid+'</td><td>'+element.liability+'</td></tr>');
			   });
			}
		}); 
    
    }
   
   
    
    

    function changePartnerShipByAdmin(parentid,userid){
    	
    	swal({
    	      title: 'Change PartnerShip::',
    	      html: '<input type="text" id="adminpartnership" class="form-control" id="dipositebal"><input type="text" id="masterpartnership" class="form-control"><input type="text" id="dealerpartnership" class="form-control">',
    	      showCancelButton: true,
    	      confirmButtonText: 'Deposit',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	
    	    	  
    	      }
    	    });
    	    
    }

  	function  changePartnerShipByMaster(){
  		swal({
  	      title: 'Change PartnerShip::',
  	      html: '<input type="text" id="masterpartnership" class="form-control"><input type="text" id="dealerpartnership" class="form-control">',
  	      showCancelButton: true,
  	      confirmButtonText: 'Deposit',
  	      closeOnConfirm: false,
  	      allowOutsideClick: false
  	    }).then(function(isConfirm) {
  	      if (isConfirm) {
  	    	
  	    	  
  	      }
  	    });
  	   
  	}
  	
  	
    function deleteUser(id){
    	
    	swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Delete this User!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
	   	 $.ajax({
				type:'DELETE',
				url:'<s:url value="/api/deleteUser/"/>'+id,
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					userList($("#id").val(),$("#userid").val());
			    } 
			}); 
 		 	 } 
		 	});
    }

    
 function betLock(id){
    	
    	swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to LockBet this User!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
	   	 $.ajax({
				type:'POST',
				url:'<s:url value="/api/betLock/"/>'+id,
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					userList($("#id").val(),$("#userid").val());
			    } 
			}); 
 		 	 } 
		 	});
    }
 
 function betUnLock(id){
 	
 	swal({
 	      title: 'Are you sure?',
 	      text: 'You Want to LockBet this User!',
 	      type: 'warning',
 	      showCancelButton: true,
 	      confirmButtonColor: '#3085d6',
 	      cancelButtonColor: '#d33',
 	      confirmButtonText: 'Yes',
 	      closeOnConfirm: false
 	    }).then(function(isConfirm) {
 	      if (isConfirm) {
	   	 $.ajax({
				type:'POST',
				url:'<s:url value="/api/betUnLock/"/>'+id,
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					userList($("#id").val(),$("#userid").val());
			    } 
			}); 
		 	 } 
		 	});
 }

 
	function showPartnership(userid,usertype,type){ 
	
			seuserid = userid;
			seusertype = usertype;
			checkusertypes = type;
    	        $.ajax({
			type:'GET',
			url:'<s:url value="api/getPartnerships/"/>'+userid+"/"+usertype,
			success: function(jsondata, textStatus, xhr) 
			{
			 partype = jsondata.type;
			 moddsl = jsondata.masteroddsloss;
		     moddsw = jsondata.masteroddswin;
		     mfl= jsondata.masterfancyloss;
		     mfw = jsondata.masterfancywin;
		     doddsl = jsondata.dealeroddsloss;
		     doddsw = jsondata.dealeroddswin;
		     dfl = jsondata.dealerfancyloss;
		     dfw = jsondata.dealerfancywin;
		     uoddsl = jsondata.useroddsloss;
		     uoddsw = jsondata.useroddswin;
		     ufl = jsondata.userfancyloss;
		     ufw = jsondata.userfancywin;
				$("#typepart").html(jsondata.type);
				$("#moloss").val(jsondata.masteroddsloss);
				$("#mowin").val(jsondata.masteroddswin);	
				$("#mfloss").val(jsondata.masterfancyloss);
				$("#mfwin").val(jsondata.masterfancywin);	
				$("#doloss").val(jsondata.dealeroddsloss);
				$("#dowin").val(jsondata.dealeroddswin);
				$("#dfloss").val(jsondata.dealerfancyloss);
				$("#dfwin").val(jsondata.dealerfancywin);	
				$("#uoloss").val(jsondata.useroddsloss);
				$("#uowin").val(jsondata.useroddswin);
				$("#ufloss").val(jsondata.userfancyloss);
				$("#ufwin").val(jsondata.userfancywin);
				
			}
				
		}); 
    	    	
    	     
    	    
    }
    
    function SavePartnership(userid,usertype,type){ 	 
    var data="";
  	 	  
	   	  if(type == 'simple'){
	   	  showMessage("You Can't set commission for simple user.","error","Opps...");
	   	  $('#partnerships').modal('hide');
	   	  }
	   	  else{
	   	   var commissionfrommaster={fancyloss:$('#mfloss').val(),oddsloss:$('#moloss').val(),oddswin:$('#mowin').val(),fancywin:$('#mfwin').val(),oddswintype:partype,oddslosstype:partype,fancywintype:partype,fancylosstype:partype}
	   	  var commissionfromuser ={oddsloss:$('#uoloss').val(),fancyloss:$('#ufloss').val(),oddswin:$('#uowin').val(),fancywin:$('#ufwin').val(),oddswintype:partype,oddslosstype:partype,fancywintype:partype,fancylosstype:partype}
	   	  var commissionfromdealer={oddswin:$('#dowin').val(),oddsloss:$('#doloss').val(),fancyloss:$('#dfloss').val(),fancywin:$('#dfwin').val(),oddswintype:partype,oddslosstype:partype,fancywintype:partype,fancylosstype:partype}  	  
	   	   var  specialpartership = {commissionfromdealer:commissionfromdealer,commissionfromuser:commissionfromuser,commissionFromMaster:commissionfrommaster}
	   	  if(usertype==1 || usertype==2){
	   	  data={id:userid,usertype:usertype,specialpartership:specialpartership,type:type}
	   	  }else{
	   	  data={userid:userid,usertype:usertype,specialpartership:specialpartership,type:type}   
	   	  }
	   	       
    	      
    	      $.ajax({
	    	
				type:'POST',
				url:'<s:url value="/api/updatePart"/>',
				data: JSON.stringify(data),
				dataType: "json",
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
				if(xhr.status == 200){
				showMessage("Partnership Updated Successfully","success","success");
				$('#partnerships').modal('hide');
				}else{
				showMessage("Kinldy Clear Liability First","error","Opps...");
				$('#partnerships').modal('hide');	 
			    } 
				},
    	  			complete: function(jsondata,textStatus,xhr) {
    	  				//showMessage("You Can't set commission for simple user.","error","Opps...");
    	  				 $('#partnerships').modal('hide');
    	  		    }  
					
			
		});
		
		}
    }
    
    
    
     function chanegDealerorMasterPassword(parentid,password,userid,usertype,id){
    
    	
    	swal({
    	     title: 'Change Password',
    	      html: '<input type="text" id="dormpassword" placeholder="Change Password" class="form-control"> ',
    	      showCancelButton: true,
    	      confirmButtonText: 'Save',
    	      closeOnConfirm: false,
    	      allowOutsideClick: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {
    	    	  var pass =$("#dormpassword").val()
    	          
    	          
    	    	 if($.trim(pass).length>0) {
 	             var data = {userid:userid,password:pass}
 	            
    	    	   $.ajax({
  					type:'POST',
  					url:'<s:url value="/api/updateUserDetials"/>',
  					data: JSON.stringify(data),
  					dataType: "json",
  					contentType:"application/json; charset=utf-8",
  					success: function(jsondata, textStatus, xhr) 
  					{
  						
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						if(usertype=="5"){
    							 subadminlist();
    						}else if(usertype=="0"){
    							 suprstockagelist(parentid);
    						}else if(usertype=="1"){
    							 masterList(parentid);
    						} else if(usertype=="2"){
    							 dealerList(parentid);
    						}else{
  						userList(parentid);	
  						}
  						
  						forcelogout(id);
  						
  					},
  					complete: function(jsondata,textStatus,xhr) {
  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						 
  				    }
  			});
  			
  			}  
  			else
    	{
    	showMessage("Kindly enter new Password","error","Oops...")
    	}
    	    	  
    	      }
    	    });
    	    $('#password').focus();
    }
    
	 function forcelogout(id){	
		 
		 console.log(id);
	    	  var data = {
	    			  "days":0,
	    			  "hours":0
	    	  }
		 		$.ajax({
		 			type:'POST',
		 			url:'<s:url value="/api/setlogoutUser?id="/>'+id,
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						
					},
		 	});		 	
		 }
	 
	    function getdetails(id){
	    	    $.ajax({
				type:'GET',
				url:'<s:url value="api/useraccount/"/>'+id,
				success: function(jsondata, textStatus, xhr) 
				{
					$("#userid_detail").html(jsondata.userid);
					$("#password_detail").html(jsondata.password);
					$("#type_detail").html(jsondata.type);
					$("#availableBal_detail").html(jsondata.secondrybanlce);
					
					$("#adminpartnership_detail").html(jsondata.adminpartership);	
					$("#masterpartnershipn_detail").html(jsondata.masterpartership);
					if(jsondata.usertype==2){
						$("#dealerpartnership_detail").html(jsondata.delearpartership);
						$("#dealerpartnershipname_detail").html(jsondata.userid);
					}else{
						$("#dealerpartnershipname_detail").html('');
					}

					if(jsondata.usertype==1){
						$("#masterpartnershipname_detail").html(jsondata.userid);
					}else{
						$("#masterpartnershipname_detail").html('Master');
					}
					$("#commissionType_detail").html(jsondata.specialpartership.commissionFromMaster.fancylosstype);
					
					if(jsondata.usertype==1){
						$("#fancyloss_detail").html(jsondata.specialpartership.commissionFromMaster.fancyloss);
						$("#fancywin_detail").html(jsondata.specialpartership.commissionFromMaster.fancywin);
						$("#oddsloss_detail").html(jsondata.specialpartership.commissionFromMaster.oddsloss);
						$("#oddswin_detail").html(jsondata.specialpartership.commissionFromMaster.oddswin);							
					}else if(jsondata.usertype==2){
						$("#fancyloss_detail").html(jsondata.specialpartership.commissionfromdealer.fancyloss);
						$("#fancywin_detail").html(jsondata.specialpartership.commissionfromdealer.fancywin);
						$("#oddsloss_detail").html(jsondata.specialpartership.commissionfromdealer.oddsloss);
						$("#oddswin_detail").html(jsondata.specialpartership.commissionfromdealer.oddswin);						
					}else{
						$("#fancyloss_detail").html(jsondata.specialpartership.commissionfromuser.fancyloss);
						$("#fancywin_detail").html(jsondata.specialpartership.commissionfromuser.fancywin);
						$("#oddsloss_detail").html(jsondata.specialpartership.commissionfromuser.oddsloss);
						$("#oddswin_detail").html(jsondata.specialpartership.commissionfromuser.oddswin);
					}
				}
			}); 
	    
	    }
	    
		 function makeusersettlement(id,creditlimit,balance,parentid,type,amount,comm){	
			 
			 var diff;
			 
			 if(type==3){
				 if(creditlimit>balance){
						diff=creditlimit-balance;	 
					 }else if(creditlimit<balance){
						 diff=balance-creditlimit;
					 }
					 if(diff<0.00){
						diff=0.00-diff; 
					 }				 
			 }else{
				 if(amount>0.00){
					 diff= parseInt(amount)-parseInt(comm)
				 }else{
					 amount=0-amount;
					 diff= parseInt(amount)+parseInt(comm)
				 }
			 }


		    	swal({
		   	      title: 'Are you sure?',
		 	      text: 'Settlement Amoount is '+diff,
		 	      type: 'warning',
		 	      showCancelButton: true,
		 	      confirmButtonColor: '#3085d6',
		 	      cancelButtonColor: '#d33',
		 	      confirmButtonText: 'Yes',
		 	      closeOnConfirm: false

		    	    }).then(function(isConfirm) {
		    	      if (isConfirm) { 
		     		$.ajax({
			 			type:'GET',
			 			url:'<s:url value="/api/makeSettlement/"/>'+id,
						contentType:"application/json; charset=utf-8",
	  					success: function(jsondata, textStatus, xhr) 
	  					{
	  						
	  						showMessage(jsondata.message,jsondata.type,jsondata.title);
	  						
	  						if(usertype=="1"){
	  							 masterList();
	  						} else if(usertype=="2"){
	  							 dealerList(parentid);
	  						}
	  						else{
	  						userList(parentid);	
	  						}	  						
	  					},
	  					complete: function(jsondata,textStatus,xhr) {
	  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	  						 
	  				    }
			 	});	
		 	} 
			});
		}
		
		
	 function makeSubadminActInct(appid,active){ 
	 if(active){
	 var title = 'You Want to block the site!'
	 }else{
	 var title = 'You Want to unblock the site!'
	 }
	 	   	       
    	    swal({
    	      title: 'Are you sure?',
    	      text: title,
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 
    	      $.ajax({
	    	
				type:'POST',
				url:'<s:url value="/api/makesubadminActInct?appid="/>'+appid,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				subadminlist();
				}
			
		});
		
		
		} 
			});
		
    }
    
    function dismissEditSubadmin(){
    $('#editsub').modal('hide');
    $('#editspmaster').modal('hide');
    }
     
    function getSubadminDetails(userid,appid,usertype){
  
   alert()
    	
   $("#subadminuserid").val(userid);
    $("#appid").val(appid);
  		    $.ajax({
			type:'GET',
			url:'<s:url value="api/getSubadminDetails/"/>'+userid,
			success: function(jsondata, textStatus, xhr) 
			{
			var type = jsondata.matchcomtype;
			partnershipsedit = jsondata.part
			if(usertype == 5){
			$("#subadminusername").val(jsondata.username);
				$("#subadminreference").val(jsondata.reference);
				$("#sunadmincontact").val(jsondata.contact);	
				$("#subadminpassword").val(jsondata.password);
				$("#bhaotype").val(jsondata.bhao).change();		
				$("#sunadmincommisiontype").val(jsondata.commtype).change();
				if(jsondata.commtype == 'mobapp'){
				$("#subadmincommision").val(jsondata.appcharge).change();
				$("#percentageinput").hide();
				 $("#losscommmain").hide();
				 
      			 $("#mobappmain").show();
				}else{ 
				$("#subadmincommision").val(jsondata.comm).change();
				$("#percentageinput").show();
				if(jsondata.part>0){
				$("#losscommmain").hide();
				$("#percentageinput").hide();
				}else{
				$("#losscommmain").show();
				$("#percentageinput").show();
				}
				 
				 $("#subadminlosscommission").val(jsondata.lossComm);
				 $("#percen").val(jsondata.commper).change();
      			 $("#mobappmain").hide();
				}
				
				
				$("#subadminappname").val(jsondata.appname);
				$("#subadminappurl").val(jsondata.appurl);
				$("#subadminprimarybalance").val(jsondata.credit);
				
				
				if(type == 'No Commission'){
			        $("#oddsdiv").hide();
			    	$("#oddslossdiv").hide();
			        }else if(type == 'Only on minus'){
			        $("#label1").html("Match + Session Minus");
			         $("#oddsdiv").show();
			    	$("#oddslossdiv").hide();
			     $("#oddsWinCommsimpple").val(jsondata.odswin).change();
			        }else if(type == 'Bet by Bet'){
			         $("#label1").html("Match Commission");
			          $("#label2").html("Session Commission");
			        $("#oddsdiv").show();
			    	$("#oddslossdiv").show();
			    	$("#oddsWinCommsimpple").val(jsondata.odslos).change();
			    	$("#oddsLossCommsimpple").val(jsondata.fncywin).change();
			        }else{
			        
			        }  
			
			 $("#oddstype").val(jsondata.matchcomtype);
				 
			}else {
				
				$("#savedetailssp").click(function(){ saveSpMaster(usertype); });
				$("#spusername").val(jsondata.username);
				$("#spuserid").val(jsondata.userid);
				$("#spreference").val(jsondata.reference);
				$("#spcontact").val(jsondata.contact);	
				$("#sppassword").val(jsondata.password);	
				if(type == 'No Commission'){
			        $("#spoddsdiv").hide();
			    	$("#spoddslossdiv").hide();
			        }else if(type == 'Only on minus'){
			        $("#splabel1").html("Match + Session Minus");
			         $("#spoddsdiv").show();
			    	$("#spoddslossdiv").hide();
			     $("#spoddsWinCommsimpple").val(jsondata.odswin).change();
			        }else if(type == 'Bet by Bet'){
			         $("#splabel1").html("Match Commission");
			          $("#splabel2").html("Session Commission");
			        $("#spoddsdiv").show();
			    	$("#spoddslossdiv").show();
			    	$("#spoddsWinCommsimpple").val(jsondata.odslos).change();
			    	$("#spoddsLossCommsimpple").val(jsondata.fncywin).change();
			        }else{
			        
			        }  
			
			 $("#spoddstype").val(jsondata.matchcomtype).change();
				
				
			}
				
				
			}
				
		}); 
    }
    
    
    function saveSubadminDetails(){
    
    var type = $("#oddstype").val();
    
   if(type !='Commission type')  {
    if($("#sunadmincommisiontype").val() == 'mobapp'){
    var appcharge =$("#subadmincommision").val();
	var commision =0;
       
    }else{
     $("#percentageinput").show();
     var appcharge = 0 ;
	  var commision =$("#subadmincommision").val();
    }
       var oddswin= 0;
		  var oddsloss = 0 ;
		  var fancywin =0;
		  var fancyloss =0;
		  
		  if($("#oddstype").val() == 'Only on minus'){
		    oddswin= $("#oddsWinCommsimpple").val();
		   oddsloss = $("#oddsWinCommsimpple").val();
		   fancywin =$("#oddsWinCommsimpple").val();
		   fancyloss =$("#oddsWinCommsimpple").val();
		  }else if($("#oddstype").val() == 'Bet by Bet'){
		    oddswin= 0;
		    oddsloss = $("#oddsWinCommsimpple").val();
		   fancywin =$("#oddsLossCommsimpple").val();
		   fancyloss =$("#oddsLossCommsimpple").val();
		  }
	
    data={username:$("#subadminusername").val(),reference:$("#subadminreference").val(),contact:$("#sunadmincontact").val(),appid:$("#appid").val(),
   password:$("#subadminpassword").val(),commisiontype:$("#sunadmincommisiontype").val(),mobappcharge:appcharge,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:partnershipsedit,type:"simple",
	  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
   commision:commision,losscommision:$("#subadminlosscommission").val(),adminpartership:$("#subadminpartnership").val(),
   appname:$("#subadminappname").val(),appurl:$("#subadminappurl").val(),userid: $("#subadminuserid").val(),commisionpercentage:$("#percen").val(),bhaotype:$("#bhaotype").val()}
  		    $.ajax({
			type:'POST',
			url:'<s:url value="api/saveSubadminDetails"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) 
			{
			if(xhr.status == 200){
			$('#editsub').modal('hide');
			}else{
			$('#editsub').modal('hide');
			}
			showMessage(jsondata.message,jsondata.type,jsondata.title);	
				subadminlist();
			}
			
			
				
		});
		}else{
		showMessage("Please select the Commision type","error","Oops...")
		} 
    }
    
    
    function saveSpMaster(usertype){
    var type = $("#spoddstype").val();
    if(type !='Commission type')  {
    var oddswin= 0;
		  var oddsloss = 0 ;
		  var fancywin =0;
		  var fancyloss =0;
		  
		  if(type == 'Only on minus'){
		    oddswin= $("#spoddsWinCommsimpple").val();
		   oddsloss = $("#spoddsWinCommsimpple").val();
		   fancywin =$("#spoddsWinCommsimpple").val();
		   fancyloss =$("#spoddsWinCommsimpple").val();
		  }else if(type == 'Bet by Bet'){
		    oddswin= 0;
		    oddsloss = $("#spoddsWinCommsimpple").val();
		   fancywin =$("#spoddsLossCommsimpple").val();
		   fancyloss =$("#spoddsLossCommsimpple").val();
		  }
		  
	
     
		    
    data={username:$("#spusername").val(),reference:$("#spreference").val(),contact:$("#spcontact").val(),appid:$("#appid").val(),oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:partnershipsedit,type:"simple",
	    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
   password:$("#sppassword").val(),userid: $("#spuserid").val()}
  		    $.ajax({
			type:'POST',
			url:'<s:url value="api/saveSpMasterDetails"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) 
			{
			if(xhr.status == 200){
			$('#editspmaster').modal('hide');
			}else{
			$('#editspmaster').modal('hide');
			}
			showMessage(jsondata.message,jsondata.type,jsondata.title);	
			if(usertype == 0){
			suprstockagelist();
			}else if(usertype == 1){
			masterList();
			}else if(usertype == 2){
			dealerList();
			}else if(usertype == 3){
			userList();
			}
			
			}
				
		});
		}else{
		showMessage("Please select the Commision type","error","Oops...")
		}  
    }
    
    
     function getCommission(){
  	
  	var element = 100;
  	
  	
  	for(i = 0; i <=element; i++) { 
  	$("#subadmincommision").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>', {
			             			                
		}));
  		
		}  	 
    
    }
    
    Handlebars.registerHelper('if_eq', function(a, b, opts) {
    if (a < b) {
        return opts.fn(this);
    } else {
        return opts.inverse(this);
    }
});

Handlebars.registerHelper('if', function(a, b, opts) {
		    if (a == b) {
		        return opts.fn(this);
		    } else {
		        return opts.inverse(this);
		    }
		});
    	
    </script>
  <input type="hidden" id="id"/>
    <input type="hidden" id="userid"/>
    <input type="hidden" id="usertype"/>
     <input type="hidden" id="subadid"/>
      <input type="hidden" id="subusrid"/>
      <input type="hidden" id="stockid"/>
      <input type="hidden" id="stockusrid"/>
    <input type="hidden" id="appid"/>
    <input type="hidden" id="subadminuserid"/>
  