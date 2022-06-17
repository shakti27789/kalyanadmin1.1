<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


	
        <div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">User Registration Form</h1>
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
											
											<div class="form-group" id="wncommain">
															<label>Winning Commission</label>
													
														 		 	<select class="form-control" id="wincom" name="wincom" >
														 	<option value="0" >0</option>
											      <option value="0.5">0.5</option>
											      <option value="1.0">1.0</option>
											      <option value="1.5">1.5</option>
											      <option value="2.0">2.0</option>
											      <option value="2.5">2.5</option>
											      <option value="3.0">3.0</option> </select>
											
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
									<div class="col-sm-6">
										<div class="inner-form">
											<h2 class="page-header">Credit</h2>
											<div class="form-group">
												<label>Credit Limit</label> 0 &gt;=
												<input type="number" placeholder="Enter Credit Limit" id="primarybalance" name="primarybalance" class="form-control">
												<span id="dealerbalance" >&lt;=${user.secondrybanlce}</span>
											</div>
											
											<div class="form-group">
												<label>Exposer Limit</label> 
												<input type="number" placeholder="Enter Exposure Limit" id="netexposure" name="netexposure" class="form-control">
												
											</div>
										</div>
									</div>
								</div>
								<div  class="button-wrap">
									<button class="btn btn-danger" type="button" onclik="clearform('inner-form')">Cancel</button>									
									<button class="btn btn-primary" type="button" onclick="createPunter()">Sign Up</button>									
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
    	getMasterBalaceById();
     	var id = new Date().getTime()+"";
    	id = "${user.username}".substring(0,3)+id.substring(11,15);
    	$("#userid").val(id);
    	getCommission();
    	$("#oddstype").val('${creatuser.oddslosscommisiontype}');
    	
    	
    	if('${creatuser.commisiontype}' == 'mobapp'){
    		$("#mobappmain").show();
    		$("#wincom").hide();
    		$("#wncommain").hide();
    		
    	}
    	else{
    		$("#mobappmain").hide();
    		$("#wincom").show();
    		$("#wncommain").show();
    		//wincom();
    		$("#wincom").attr("disabled", false)
    		
    	}
    	 type ='Commission type';
    	 
    	 
    	
    	 $("#oddstype").change(function(){
       
        if(type == 'No Commission'){
        $("#oddsdiv").hide();
    	$("#oddslossdiv").hide();
        }else if(type == 'Bet by Bet'){
         $("#label1").html("Match Commission");
          $("#label2").html("Session Commission");
        $("#oddsdiv").show();
    	$("#oddslossdiv").show();
        }
        else if(type ="Match minus & Session minus") {
        	$("#oddsdiv").show();
        	$("#oddslossdiv").show();
        }
        else{
        	 $("#label1").html("Match Commission");
            $("#label2").html("Session Commission");
          $("#oddsdiv").show();
      	$("#oddslossdiv").show(); 
        }  
    });
    
    });
    
    
    
	
	
		
	
		
	
    
    
   
    function createPunter()
    {
    
    	 var data="";
    	  var username = $("#username").val();
	   	  var userid = $("#userid").val();
	   	  var password = $("#password").val();
	   	 var contact = $("#contact").val();
	   	  var reference = $("#reference").val();
	   	  var parentid ='${creatuser.id}';
	   	  var usertype =3;
	   	  var primarybalance =$("#primarybalance").val();
	      var secondrybanlce =$("#primarybalance").val();
	      var createfrom ='${creatuser.id}';
	       var appid ='${creatuser.appid}';
	       admincomm = 0;
	       var mobcommission = "";
		   	if('${creatuser.commisiontype}' == "mobapp"){
		   		mobcommission = $("#commision").val();
	    	}else{
	    		mobcommission = ${creatuser.mobappcharge};
	    	}
		    var netexpo = $("#netexposure").val();
		      
		      
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
		  var wincommission = $("#wincom").val();
		    if(wincommission == null){
		    	wincommission="0";
		    }
		    type = $("#oddstype").val();
			  		   data = {wincommision:wincommission,createfrom:createfrom,webtype:true,username:username,password:password,userid:userid,type:type,parentid:parentid,masterid:'${creatuser.masterid}',adminpartership:'${creatuser.adminpartership}',masterpartership:'${creatuser.masterpartership}',usertype:3,
			    	  primarybalance:primarybalance,secondrybanlce:secondrybanlce,adminid:'${creatuser.adminid}',appid:appid,admincomm:admincomm,dealerid:parentid,
			    	  supermastepartnership:'${creatuser.supermastepartnership}',subadminid:'${creatuser.subadminid}',subadminpartnership:'${creatuser.subadminpartnership}',supermasterid:'${creatuser.supermasterid}',netexposure:netexpo,delearpartership:'${creatuser.delearpartership}',oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:'${creatuser.partnershipratio}',
		    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type,
			    	  reference:reference,contact:contact,bhaotype:'${creatuser.bhaotype}',mobappcharge:mobcommission,losscommision:'${creatuser.losscommision}',commisiontype:'${creatuser.commisiontype}',commision:'${creatuser.commision}',adminname:'${creatuser.adminname}',subadminname:'${creatuser.subadminname}',supermastername:'${creatuser.supermastername}',mastername:'${creatuser.mastername}',dealername:'${creatuser.username}',commisionpercentage:'${creatuser.commisionpercentage}',wincommision:$("#wincom").val(),isDirect:false}
	   	
	    if(parseInt(primarybalance)<$("#dealerbalance_hidden").val() && $("#oddstype").val() !='Commission type')  {
	    		
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
						$("#oddstype").val($("#oddstype option:first").val());
						$("#oddsdiv").hide();
    					$("#oddslossdiv").hide();
    					$("#commision").val($("#commision option:first").val());
    					 $("#oddsWinCommsimpple").val($("#oddstype option:first").val());
    					 $("#oddsLossCommsimpple").val($("#oddstype option:first").val());	
					$("#dealerbalance").html("<="+jsondata.balance);
					getMasterBalaceById();						
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);

			    } 
			
		});
	  }else{
		   if(type =='Commission type'){
	    	showMessage("Please select the Commision type","error","Oops...")
	    	}else{
	    	showMessage("Your balance is less then creditmilit!","error","Oops...")
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
					$("#dealerbalance").html("<="+jsondata.balance);	
					$("#dealerbalance_hidden").val(jsondata.balance);	
					
				},
				complete: function(jsondata,textStatus,xhr) {
					

			    } 
			
		});
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
    	 
    	/*  function wincom(){
		  	  	
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
    <input type="hidden" id="dealerbalance_hidden">