 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
 <div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Super Master Registration Form</h1>
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
														<span id="adminbalance" class="credit" style="color: #00a400;">&lt;=${user.secondrybanlce}</span>
													</div>
													
													
										</div>
									</div>
									
									
									<div class="col-sm-8">
									<div class="inner-form">
										<div class="form-group">
												<ul class="radio-list">
													<li>
														<!--<label><input type="radio" id="simuser" name="partershiptype" value="simple" /> Simple</label>-->
													</li>
													<li>
														<!--<label><input type="radio" name="partershiptype"  value="special" /> Special</label>-->
													</li>
												</ul>
										 </div>
										</div>
										<!--Member Speacial-->
										<div class="inner-form" id ="simple" style="display:none;">
											
											<div class="row">
												<div class="col-sm-6">
												
													<div class="form-group">
														<div class="form-group">
															<label>Partnership (Apna Share Bharna Hai)
															
															<s:if test = "${user.usertype==5}">
															<span style="color:#cae0e8;"><b class="partnership"  id="partnershipofsubadmin" >${creatuser.subadminpartnership} </b></span>
															</s:if>
															<s:if test = "${user.usertype==0}">
															<span style="color:#cae0e8;"><b class="partnership" id="partnershipofmaster" >${creatuser.supermastepartnership} </b></span>
															</s:if>
															
															</label>
															<!--<input type="number" placeholder="Enter Partnership" id="partnershipratiosimple" name="partnrshipratio" class="form-control">-->
															 <select class="form-control" id="partnershipratiosimple">
											    
											    </select>
														</div>
														
														 <div class="form-group" id="wincommain">
															<label>Winning Commission</label>
														
												<select class="form-control" id="wincom" name="wincom" >
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
													
													
													
																										
													<!--<div class="form-group">
														<label>Commison for Dealer</label>
														<select class="form-control" id="commfromdelearselectbox">
															<option value ="-1">Select</option>
															<option value ="TAKE">Take</option>
															<option value ="GIVE">Give</option>
														</select>
													</div>
													
													<div class="form-group">
														<label>Odds Win</label>
														<input type="number" placeholder="Enter fancyLossComm" id="oddsWinCommfromdealer" name="fancyLossCommfromdelear" class="form-control">
													</div>
													
													<div class="form-group">
														<label>OddsLoss</label>
														<input type="number" placeholder="Enter OddsLossComm" id="oddsLossCommfromdealer" name="OddsLossComm" class="form-control">
													</div>
													
													<div class="form-group">
														<label>FancyLoss</label>
														<input type="number" placeholder="Enter fancyLossComm" id="fancyLossCommfromdealer" name="fancyLossCommfromdelear" class="form-control">
													</div>
													
													<div class="form-group">
														<label>Fancy Win</label>
														<input type="number" placeholder="Enter OddsLossComm" id="fancyWinCommfromdealer" name="OddsLossComm" class="form-control">
													</div>
													
													
													
												</div>
												<div class="col-sm-6">
													<div class="form-group">
														<label>Commison from User</label>
														<select class="form-control" id="commfromuserselectbox">
															<option value ="-1">Select</option>
															<option value ="TAKE">Take</option>
															<option value ="GIVE">Give</option>
														</select>
													</div>
													<div class="form-group">
														<label>OddsWin</label>
														<input type="number" placeholder="Enter OddsWinComm" id="OddsWinCommuser" name="OddsWinCommuser" class="form-control">
													</div>
													<div class="form-group">
														<label>OddsLoss</label>
														<input type="number" placeholder="Enter OddLossComm" id="OddLossCommuser" name="OddLossCommuser" class="form-control">
													</div>
													<div class="form-group">
														<label>FancyWin</label>
														<input type="number" placeholder="Enter fancyWinComm" id="fancyWinCommuser" name="fancyWinCommuser" class="form-control">
													</div>
													<div class="form-group">
														<label>FancyLoss</label>
														<input type="number" placeholder="Enter fancyLossComm" id="fancyLossCommuser" name="fancyLossCommuser" class="form-control">
													</div>
												</div>-->
											</div>
										</div>
									</div>
									
									
								</div>
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclick="clearform('inner-form')">Cancel</button>									
									<button class="btn btn-primary" type="button" onclick="createMaster()">Sign Up</button>									
								</div>
							</form>
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    
    <script>
    var type ="";
    $(document).ready(function(){
    	var id = new Date().getTime()+"";
    	id = "${user.username}".substring(0,3)+id.substring(11,15);
    	$("#userid").val(id)
    	/* if('${creatuser.mobappcharge}' == 0){
   			$("#commision").hide();
   		} */
    	
    	setMasterPart();
    	getMasterBalaceById();
    	getCommission();
    	
    	$("#oddstype").val('${creatuser.oddslosscommisiontype}');
    	
    	$("#wincommain").show();
    	if('${creatuser.commisiontype}' == 'mobapp'){
    		$("#mobappmain").show();
    		$("#wincommain").hide();
    	}
    	else{
    		$("#mobappmain").hide();
    		$("#wincom").show();
    		//wincom();
    	}
    	/* $('#wincom').attr("disabled", true)
    	$("#wincom").val('${creatuser.wincommision}'); */
    	$("#simuser").prop('checked', true);
    	$("#special").hide(); 
	    $("#simple").show();
    	$("input[type='radio']").click(function(){
            var radioValue = $("input[name='partershiptype']:checked").val();
            if(radioValue){
             	if(radioValue =="simple")
            		{
	            		$("#special").hide(); 
	            		$("#simple").show();
            		}
            	else if(radioValue =="special")
            		{
	            		$("#simple").hide();
	            		$("#special").show();
            		}
            }
        });
        
        
        type ='Commission type';
    	
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
        
        
        
        
          $("#commfrommasterselectbox").change(function(){
        var selectedevent = $(this).children("option:selected").val();
        
        if(selectedevent == 'TAKE'){
        $("#admincomm").show();
         $("#labladcomm").show();
         $("#FancywinCommmaster").hide();
          $("#OddsWinCommmaster").hide();
           $("#OddsLossCommfrommaster").hide();
            $("#fancyLossCommfrommaster").hide();
            $("#mfdl").hide();
          $("#mfdw").hide();
           $("#modl").hide();
            $("#modw").hide();
            
        }else{
        $("#admincomm").hide();
        $("#labladcomm").hide();
        $("#FancywinCommmaster").show();
         $("#OddsWinCommmaster").show();
           $("#OddsLossCommfrommaster").show();
            $("#fancyLossCommfrommaster").show();
             $("#mfdl").show();
          $("#mfdw").show();
           $("#modl").show();
            $("#modw").show();
        }
      
        
    });
        
    });
    
    
    
    function createMaster()
    {
    	//alert()	
    	var data="";
    	  var username = $("#username").val();
	   	  var userid = $("#userid").val();
	   	  var password = $("#password").val();
	   	 var contact = $("#contact").val();
	   	  var reference = $("#reference").val();
	   	  var parentid ='${creatuser.id}';
	   	  var usertype =1;
	   	  var primarybalance =$("#primarybalance").val();
	      var secondrybanlce =$("#primarybalance").val();
	      var createfrom ='${creatuser.id}';
	       var appid ='${creatuser.appid}';
	       admincomm = 0;
	       var mobcommission = "";
	     
	       if('${creatuser.commisiontype}' == "mobapp" ||'${creatuser.commisiontype}' == "commision"){
		   		if('${creatuser.mobappcharge}' == 0){
		   			mobcommission=0;
		   		}else{
		   			mobcommission = $("#commision").val();
		   		}
		   	 
		   var masterparnership =$("#partnershipratiosimple").val();
		      
		      
		      
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
		  supermastepartnership ="";
		  subadminid ="";
		  subadminname="";
		  if(${creatuser.usertype}==0){
			  supermastepartnership = '${creatuser.supermastepartnership}';
			 
			  subadminid = '${creatuser.subadminid}';
			  subadminname = '${creatuser.subadminname}';
		  }else if(${creatuser.usertype}==5){
			  supermastepartnership=0;
			  subadminid = '${creatuser.id}';
			  subadminname = '${creatuser.username}';
		  }
		  
		
		  type = $("#oddstype").val();
			  		   data = {createfrom:createfrom,webtype:true,username:username,password:password,userid:userid,type:type,parentid:parentid,adminpartership:'${creatuser.adminpartership}',usertype:usertype,
			    	  primarybalance:primarybalance,secondrybanlce:secondrybanlce,adminid:'${creatuser.adminid}',appid:appid,admincomm:admincomm,
			    	  supermastepartnership:supermastepartnership,subadminid:subadminid,subadminpartnership:'${creatuser.subadminpartnership}',supermasterid:createfrom,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:masterparnership,type:type,
		    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
			    	  reference:reference,contact:contact,bhaotype:'${creatuser.bhaotype}',mobappcharge:mobcommission,losscommision:'${creatuser.losscommision}',commisiontype:'${creatuser.commisiontype}',commision:'${creatuser.commision}',adminname:'${creatuser.adminname}',subadminname:subadminname,supermastername:'${creatuser.username}',commisionpercentage:'${creatuser.commisionpercentage}',wincommision:$("#wincom").val()}
	    	  
	     
	   
	
	      if(parseInt(primarybalance)<=$("#parentbalance").val() && $("#oddstype").val() !='Commission type')  {
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
						$("#commision").val($("#commision option:first").val());
						$("#oddsdiv").hide();
						$("#wincom").val('${creatuser.wincommision}');
    					$("#oddslossdiv").hide();
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
  	
  	
    	function getMasterBalaceById(){
    	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/getSecondaryBalanceById?id="/>'+'${creatuser.id}',
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					$("#adminbalance").html("<="+jsondata.balance);	
					$("#parentbalance").val(jsondata.balance);	
					
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
	   }
	   
	   function setMasterPart(){
  	
  		var element = parseInt(${creatuser.supermastepartnership});
  	
  		var element ="";
  	  	if(${creatuser.usertype}==0){
  	  		element = parseInt(${creatuser.supermastepartnership});
  	  	}else if(${creatuser.usertype}==5){
  	  		element = parseInt(${creatuser.subadminpartnership});
  	  	}
  		
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
	   
	 /*   function wincom(){
	  	  	
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
    <input type="hidden" id="parentbalance">