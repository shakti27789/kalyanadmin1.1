 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
 <div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Sub Admin Registration Form</h1>
						<section class="menu-content">
							<form id="masterform">
								<div class="row">									
									<div class="col-sm-4">
										<div class="inner-form">
											<div class="form-group">
												<label>Name</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter name" id="username" name="username" class="form-control" required/>
											</div>
											<div class="form-group">
												<label>LoginID</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter User Name"  id="userid" name=userid class="form-control" required />
											</div>
											<div class="form-group">
												<label>Reference</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Reference" id="reference" name="reference" class="form-control" required/>
											</div>
											
											
											
												<div class="form-group">
												<label>Contact</label>
												<input type="number" maxlength="10" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter contact number" id="contact" name="contact" class="form-control" required/>
											</div>
											
											<div class="form-group">
												<label>Password</label>
												<input type="password" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Password"  id="password" name="password" class="form-control" required/>
											</div>
											
											<div class="form-group" id="mobappmain" style= display:none;>
												<label>	Mobile App / Commission</label>
											    <select class="form-control" id="commision">
											     
											    </select>
											</div>
											
											
											
											
											
											
											<div class="form-group">
														<label>Credit Limit</label> 0 &gt;=
														<input type="text" autocomplete="off" placeholder="Enter Credit Limit"  id="primarybalance" name="primarybalance" class="form-control" required />
														<span id="subadminbal" class="credit" style="color: #00a400;">&lt;=${creatuser.secondrybanlce}</span>
													</div>
										</div>
									</div>
									
									
									<div class="col-sm-8">
									<!--<div class="inner-form">
										<div class="form-group">
												<ul class="radio-list">
													<li>
														<label><input type="radio" name="partershiptype" value="simple" /> Simple</label>
													</li>
													<li>
														<label><input type="radio" name="partershiptype"  value="special" /> Special</label>
													</li>
													<li>
														<label><input type="radio" name="partershiptype"  value="points" /> Point System</label>
													</li>
												</ul>
										 </div>
										</div>-->
										<!--Member Speacial-->
										<div class="inner-form" id ="simple">
											
											<div class="row">
												<div class="col-sm-6">
												
													<div class="form-group">
												<div class="form-group">
														<label>Partnership (Apna Share Bharna Hai)
														<span style="color:#cae0e8;"><b class="ng-binding" id="partnershipofmaster" >${creatuser.subadminpartnership}</b></span>
														</label>
											    <select class="form-control" id="partnershipratiosimple">
											    
											    </select>
											</div>
											
												<div class="form-group" id="wincommain">
												<label>Winning Commission</label>
											
											 <select class="form-control" id="wincom" name="wincom" style="display:none;" >
											  <option value="0" >0</option>
											      <option value="0.5">0.5</option>
											      <option value="1.0">1.0</option>
											      <option value="1.5">1.5</option>
											      <option value="2.0">2.0</option>
											      <option value="2.5">2.5</option>
											      <option value="3.0">3.0</option>
											   </select>
											 </div>
											 
											<div class="form-group">
														<label> Session Commission Type</label>
											    
											    <input type="text" id="oddstype" name="reference" class="form-control" readonly=true required="">
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
											        <option>2.75</option>
											           <option>3.00</option>
											    </select>
											</div>											
													</div>
												</div>
												
											</div>
										</div>
										
										<!-- Member Point System -->
										<div class="inner-form" id ="points" style="display:none;">								
											<div class="row">
												<div class="col-sm-6">
												
													<div class="form-group">
														<div class="form-group">
															<label>OddsWin</label>
															<input type="number" placeholder="Enter oddsWinComm" id="oddswinpoints" name="oddsWinComm" class="form-control">
														</div>
														<div class="form-group">
															<label>OddsLoss</label>
															<input type="text" placeholder="Enter oddsLossComm" id="oddslosspoints" name="oddsLossCommsimpple" class="form-control" value="0">
														</div>
													</div>
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<label>FancyWin</label>
														<input type="number" placeholder="Enter fancyWinComm" id="fancywinpoints" name="fancyWinCommsimpple" class="form-control">
													</div>
													<div class="form-group">
														<label>FancyLoss</label>
														<input type="number" value="0"  placeholder="Enter fancyLossComm" id="fancylosspoints" name="fancyLossCommsimpple" class="form-control">
													</div>											
													
												</div>
											</div>
										</div>
									<!--Member Speacial-->
									<div class="inner-form" id="special"  style="display:none;">
											
											<div class="row">
												<div class="col-sm-6">
													<div class="form-group">
														<label>Partnership</label>
														<input type="number" placeholder="Enter Partnership" id="partnershipratiospecial" name="partnershipratiospecial" class="form-control">
													</div>	
													
													
													
													<!--<div class="form-group">
														<label>Commison Level</label>
														<select class="form-control" id="commlevelbox">
															<option value ="3" select="selected">3</option>
															<option value ="2">2</option>
															<option value ="1">1</option>
														</select>
													</div>-->
													
													<div class="form-group">
														<label>Commison for Master</label>
														<select class="form-control" id="commfrommasterselectbox">
															<option value ="-1">Select</option>
															<option value ="TAKE">Take</option>
															<option value ="GIVE">Give</option>
														</select>
													</div>
													
													<div class="form-group">
														<label id="labladcomm" style="display:none">Admin Comm.</label>
														<input type="number" style="display:none" value="0" placeholder="Enter Admin Comm." id="admincomm" name="admincomm" class="form-control">
													</div>	
													
													<div class="form-group">
														<label id="modw">OddsWin</label>
														<input type="number" value="0" placeholder="Enter OddsWinComm" id="OddsWinCommmaster" name="OddsWinCommmaster" class="form-control">
													</div>
													
													<div class="form-group">
														<label id="modl">OddsLoss</label>
														<input type="number" value="0" placeholder="Enter OddsLossComm" id="OddsLossCommfrommaster" name="OddsLossComm" class="form-control">
													</div>
													
													<div class="form-group">
														<label id="mfdw">Fancy Win</label>
														<input type="number" value="0" placeholder="Enter OddLossComm" id="FancywinCommmaster" name="FancywinCommmaster" class="form-control">
													</div>
													
													
													<div class="form-group">
														<label id="mfdl">FancyLoss</label>
														<input type="number" value="0" placeholder="Enter fancyLossComm" id="fancyLossCommfrommaster" name="fancyLossCommfromdelear" class="form-control">
													</div>																										
											</div>
										</div>
									</div>
									
									
								</div>
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclick="clearform('inner-form')">Cancel</button>									
									<button class="btn btn-primary" type="button" onclick="createsupermaster()">Sign Up</button>									
								</div>
							</form>
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
    
        <script>
    var type ="";
    $(document).ready(function(){
    	getMasterBalaceById();
    	setsuperMasterPart();
    	getCommission();
    	type = $("#oddstype").val();
    	var id = new Date().getTime()+"";
    	id = "${user.username}".substring(0,3)+id.substring(11,15);
    	$("#userid").val(id)
    	
   		$("#oddstype").val('${creatuser.oddslosscommisiontype}');
  
    	$("#wincommain").show();
    	if('${creatuser.commisiontype}' == 'mobapp'){
    		$("#mobappmain").show();
    		$("#wincommain").hide();
    	}
    	else{
    		$("#mobappmain").hide();
    		$("#wincom").show();
    	//	wincom();
    	}
    
    	 $("#oddstype").change(function(){
         type = $("#oddstype").val();
         
        if(type == 'No Commission'){
        $("#oddsdiv").hide();
    	$("#oddslossdiv").hide();
        }else if(type == 'Bet by Bet'){
         $("#label1").html("Match Commission");
          $("#label2").html("Session Commission");
        $("#oddsdiv").show();
    	$("#oddslossdiv").show();
        }else{
        	  $("#label1").html("Match Commission");
             $("#label2").html("Session Commission");
           $("#oddsdiv").show();
       	$("#oddslossdiv").show();
        }  
    });
    
    	
    });
        
        
    
    
    
    function createsupermaster()
    {
    	
    	
    	  var data="";
  	 	  var username = $("#username").val();
	   	  var userid = $("#userid").val();
	   	  var password = $("#password").val();
	   	  var contact = $("#contact").val();
	   	  var reference = $("#reference").val();
	   	  var supermasterpartnership = $("#partnershipratiosimple").val();
	   	  var masterpartership =0;
	   	  var primarybalance = $("#primarybalance").val();
	   	  var secondrybanlce = $("#primarybalance").val();
	   	  var delearpartership =0;
	   	  var createfrom ='${creatuser.id}'; 
	   	  var appid ='${creatuser.appid}';
	   	  var mobcommission = "";
	   	if('${creatuser.commisiontype}' == "mobapp" ||'${creatuser.commisiontype}' == "commision"){
	   		if('${creatuser.mobappcharge}' == 0){
	   			mobcommission=0;
	   		}else{
	   			mobcommission = $("#commision").val();
	   		}
	   		
    	}else{
    		mobcommission = ${creatuser.mobappcharge};
    	}
	   	  
	   		  var oddswin= 0;
		  var oddsloss = 0 ;
		  var fancywin =0;
		  var fancyloss =0;
		  
		  if($("#oddstype").val() == 'Match minus & Session minus'){
			    oddswin= 0;
			   oddsloss = $("#oddsWinCommsimpple").val();
			   fancywin =$("#oddsLossCommsimpple").val();
			   fancyloss =$("#oddsLossCommsimpple").val();
			}else if($("#oddstype").val() == 'Bet by Bet'){
		    oddswin= 0;
		    oddsloss = $("#oddsWinCommsimpple").val();
		   fancywin =$("#oddsLossCommsimpple").val();
		   fancyloss =$("#oddsLossCommsimpple").val();
		  }
	   	  
	type = $("#oddstype").val();
	   	  data={createfrom:createfrom,webtype:true,username:username,userid:userid,password:password,masterpartership:masterpartership,primarybalance:primarybalance,secondrybanlce:secondrybanlce,adminid:'${creatuser.adminid}',
	   			type:type,parentid:'${creatuser.id}',adminpartership:'${creatuser.adminpartership}',delearpartership:delearpartership,usertype:0,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:supermasterpartnership,
	    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,appid:appid,admincomm:'${creatuser.admincomm}',pointsSystem:'${creatuser.pointsSystem}',
	   			reference:reference,contact:contact,supermastepartnership:supermasterpartnership,subadminid:'${creatuser.id}',subadminpartnership:'${creatuser.subadminpartnership}',masterpartership:'${creatuser.masterpartership}',
	   			bhaotype:'${creatuser.bhaotype}',mobappcharge:mobcommission,losscommision:'${creatuser.losscommision}',commisiontype:'${creatuser.commisiontype}',commision:'${creatuser.commision}',adminname:'${creatuser.adminname}',subadminname:'${creatuser.username}',commisionpercentage:'${creatuser.commisionpercentage}',wincommision: $("#wincom").val()}
	    
	      if(parseInt(primarybalance)<=$("#supeadminbalance").val() && $("#oddstype").val() !='Commission type')  {
	    	  $.ajax({
					type:'POST',
					url:'<s:url value="/api/masterSignup"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) 
					{
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						clearform("inner-form");	
						$("#partnershipratiosimple").val($("#partnershipratiosimple option:first").val());
						$("#oddstype").val($("#oddstype option:first").val());
						$("#oddsdiv").hide();
    					$("#oddslossdiv").hide();
    					$("#commision").val($("#commision option:first").val());
    					$("#wincom").val('${creatuser.wincommision}');
    					 $("#oddsWinCommsimpple").val($("#oddstype option:first").val());
    					 $("#oddsLossCommsimpple").val($("#oddstype option:first").val());	
						$("#adminbalance").html("<="+jsondata.balance);		
						getMasterBalaceById();			
						
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);

				    }
			});
	    }
	      else
	    	{
	    	if(type =='Commission type'){
	    	showMessage("Please select the Commision type","error","Oops...")
	    	}else{
	    	showMessage("Your balance is less then creditmilit!","error","Oops...")
	    	}
	    	
	    	}
    }
    
    
    
    
    	function getMasterBalaceById()
    {
    	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/getSecondaryBalanceById?id="/>'+'${creatuser.id}',
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					$("#subadminbal").html("<="+jsondata.balance);	
					$("#supeadminbalance").val(jsondata.balance);	
					
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
	   }
  	
  	
  	function clearform(class_name) {
  	  jQuery("."+class_name).find(':input').each(function() {
  	    switch(this.type) {
  	        case 'password':
  	        case 'text':
  	        case 'textarea':
  	        case 'file':
  	       
  	        case 'date':
  	        case 'number':
  	        case 'tel':
  	        case 'email':
  	            jQuery(this).val('');
  	            break;
  	        case 'checkbox':
  	        case 'radio':
  	            this.checked = false;
  	            break;
  	    }
  	  });
  	}
  	
  	function setsuperMasterPart(){
  	
  	var element = parseInt(${creatuser.subadminpartnership});
  	
  	for(i = 0; i <=element; i++) { 
  	$("#partnershipratiosimple").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>', {
			             			                
			            }));
  		
		}  	 
    
    }
  	
  	function getCommission(){
  	  	
  	  	var element = 100;
  	  	
  	  		if('${creatuser.mobappcharge}' == 0){
	  			
	  			
	  		 	$("#commision").append($('<option id = "'+0+'" value="'+0+'" name = "'+0+'" > '+0+' </option> selected', { })); 
	  		}else{
	  		  
	  		for(i = 0; i <=element; i++) { 
	  	   	  	$("#commision").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>', { }));
	  	   	  		
	  	   	}  
	  	} 	 
  	  }
  	
  	 /* function wincom(){
	  	  	
		 $.ajax({
				type:'GET',
				url:'<s:url value="/api/getWinnningCommssion"/>',
				success: function(jsondata, textStatus, xhr){
				
				var i = 0.00;
		  		while (i <=jsondata.winningcommssion) {
		  		$("#wincom").append($('<option id = "'+i+'" value="'+i+'" name = "'+i+'" > '+i+' </option>', {
		   	 }));
		  	
			  	i = (i + 0.10).toFixed(2);
			 	i = parseFloat(i);
			  }
			}
	       });
	  	 } */
    
    </script>
    <input type="hidden" id="supeadminbalance">