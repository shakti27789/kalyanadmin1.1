<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


	
        <div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Master Registration Form</h1>
						<section class="menu-content">
							<form>
								<div class="row">									
									<div class="col-sm-6">
										<div class="inner-form">
											<div class="form-group">
												<label>Name</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter name" id="username" name="username" class="form-control">
											</div>
											<div class="form-group">
												<label>LoginID</label>
												<input type="text" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter User Name" id="userid" name=userid class="form-control">
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
												<input type="password" readonly onfocus="this.removeAttribute('readonly');" placeholder="Enter Password" id="password" name="password" class="form-control">
											</div>
											
											<div class="form-group" id="mobappmain" style= display:none;>
												<label>	Mobile App / Commission</label>
											    <select class="form-control" id="commision">
											     
											    </select>
											</div>
											<div class="form-group">
												<label>Partnership (Apna Share Bharna Hai)
												<s:if test = "${user.usertype==5}">
												<span style="color:#cae0e8;"><b class="partnership"  id="partnershipofsubadmin" >${creatuser.subadminpartnership} </b></span>
												</s:if>
												<s:if test = "${user.usertype==1}">
												<span style="color:#cae0e8;"><b class="ng-binding" id="partnershipofmaster" >${user.masterpartership} </b></span>
												</s:if>
												
												<s:if test = "${user.usertype==0}">
												 <span style="color:#cae0e8;"><b class="ng-binding" id="partnershipofagent" >${creatuser.supermastepartnership} </b></span>
												</s:if>
												
												</label>
												<!--<input type="number" placeholder="Enter Partnership" id="partnershipratio" name="partnershipratio" class="form-control">-->
												<select class="form-control" id="partnershipratiosimple">
											    
											    </select>
											</div>
																						
										</div>
									</div>									
									<div class="col-sm-6">
										<div class="inner-form">
											<h2 class="page-header">Credit</h2>
											<div class="form-group">
												<label>Credit Limit</label> 0 &gt;=
												<input type="number" placeholder="Enter Credit limit" id="primarybalance" name="primarybalance" class="form-control">
												<span id="masterbalance" >&lt;=${user.secondrybanlce}</span>
												
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
											      <option value="3.0">3.0</option> </select>
														 	 
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
								
								
								
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclick="clearform("inner-form")">Cancel</button>									
									<button class="btn btn-primary" type="button" onclick="createDealear()">Sign Up</button>									
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
    <!-- /#wrapper -->
    
    
    <script>
    var type = "";
      $(document).ready(function(){
      setDealerPart();
    	getMasterBalaceById();
    	$('#wincom').attr("disabled", true)
    	$("#wincom").val('${creatuser.wincommision}');
    	getCommission();
    	/* if('${creatuser.mobappcharge}' == 0){
   			$("#mobappmain").hide();
   		} */
    	
    	var id = new Date().getTime()+"";
    	id = "${user.username}".substring(0,3)+id.substring(11,15);
    	$("#userid").val(id)
    	
     
   		$("#oddstype").val('${creatuser.oddslosscommisiontype}');
   
    	if('${creatuser.commisiontype}' == 'mobapp'){
    		$("#mobappmain").show();
    		$("#wincommain").hide();
    		
    	}
    	else{
    	
    		$("#mobappmain").hide();
    		$("#wincom").show();
    		$("#wincommain").show();
    		$('#wincom').attr("disabled", false);
    	//	wincom();
    	}
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
    
    });
    
    
		

	
	
		
		
		
    
    function createDealear()
    {
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
		      var dealerpartnership =$("#partnershipratiosimple").val();
		      
		      var supermastepartnership ="";
			  var subadminid ="";
			  var subadminname="";
			  var supermasterid="";
			  var masterid="";
			  var supermastername ="";
			  var mastername="";
			  var subadminpartnership ="";
		      if(${creatuser.usertype} == 1){
				  supermastepartnership = '${creatuser.supermastepartnership}';
				  subadminid = '${creatuser.subadminid}';
				  subadminname = '${creatuser.subadminname}';
				  masterid = createfrom;
				  masterpartership ='${creatuser.masterpartership}';
				  supermastername = '${creatuser.supermastername}';
				  mastername ='${creatuser.username}';
                  supermasterid =  '${creatuser.supermasterid}';	

			  }else if(${creatuser.usertype} == 5){
				  supermastepartnership=0;
				  subadminid = '${creatuser.id}';
				  subadminname = '${creatuser.username}';
				  supermasterid =  '${creatuser.id}';
				  masterid = '${creatuser.id}';
				  masterpartership=0;
				  supermastername ='${creatuser.username}';
				  mastername = '${creatuser.username}';
			  }  
		      
			  else if(${creatuser.usertype} == 0){
				
				  supermastepartnership='${creatuser.supermastepartnership}';
				  subadminid = '${creatuser.subadminid}';
				  subadminname = '${creatuser.subadminname}';
				  supermasterid =  '${creatuser.id}';
				  supermastername ='${creatuser.username}';
				  masterid = '${creatuser.id}';
				  mastername = '${creatuser.username}';
				  masterpartership= '${creatuser.masterpartership}';
				
				 
			  }  
		      
		      subadminpartnership ='${creatuser.subadminpartnership}';
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
	   	  
	   	  		   data = {createfrom:createfrom,webtype:true,username:username,password:password,userid:userid,type:type,parentid:parentid,masterid:masterid,adminpartership:'${creatuser.adminpartership}',masterpartership:masterpartership,usertype:2,
			    	  primarybalance:primarybalance,secondrybanlce:secondrybanlce,adminid:'${creatuser.adminid}',appid:appid,admincomm:admincomm,
			    	  supermastepartnership:supermastepartnership,subadminid:subadminid,subadminpartnership:subadminpartnership,supermasterid:supermasterid,oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:dealerpartnership,
		    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
			    	  reference:reference,contact:contact,bhaotype:'${creatuser.bhaotype}',mobappcharge:mobcommission,losscommision:'${creatuser.losscommision}',commisiontype:'${creatuser.commisiontype}',commision:'${creatuser.commision}',adminname:'${creatuser.adminname}',subadminname:subadminname,supermastername:supermastername,mastername:mastername,commisionpercentage:'${creatuser.commisionpercentage}',wincommision:$("#wincom").val()}
	    	  
	   	
	    if(parseInt(primarybalance)<=$("#masterbalance_hidden").val() && $("#oddstype").val() !='Commission type')  {
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
					$("#masterbalance").html("<="+jsondata.balance);	
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
    
    
     function getMasterBalaceById()
    {
    	  
	    	$.ajax({
	    	
				type:'GET',
				url:'<s:url value="/api/getSecondaryBalanceById?id="/>'+'${creatuser.id}',
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) 
				{
					$("#masterbalance").html("<="+jsondata.balance);	
					$("#masterbalance_hidden").val(jsondata.balance);	
					$("#partnershipofmaster").html(jsondata.master);
					
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
	   }
	   
	     function setDealerPart(){
  	
  	var element ="";
	  	if(${creatuser.usertype}==1){
	  		element = parseInt(${creatuser.masterpartership});
	  	}else if(${creatuser.usertype}==5){
	  		element = parseInt(${creatuser.subadminpartnership});
	  	}
	  	else if(${creatuser.usertype}==0){
	  		element = parseInt(${creatuser.supermastepartnership});
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
    
	 /*     function wincom(){
		  	  	
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
		  	 }
     */
    </script>
    <input type="hidden" id="masterbalance_hidden">