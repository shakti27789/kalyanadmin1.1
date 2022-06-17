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
													
												  <select class="form-control" id="wincom" name="wincom" > <option value="0" >0</option>
											      <option value="0.5">0.5</option>
											      <option value="1.0">1.0</option>
											      <option value="1.5">1.5</option>
											      <option value="2.0">2.0</option>
											      <option value="2.5">2.5</option>
											      <option value="3.0">3.0</option> </select></div>
											
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
												<span id="direct_balance" >&lt;=${user.secondrybanlce}</span>
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
    	
     	var id = new Date().getTime()+"";
    	id = "${user.username}".substring(0,3)+id.substring(11,15);
    	$("#userid").val(id)
    	/* if('${creatuser.mobappcharge}' == 0){
   			$("#mobappmain").hide();
   		} */
    	
    	getMasterBalaceById();
      
    
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
    		/* wincom(); */
    		$("#wincom").attr("disabled", false)
    		
    	}
    	
    	 type ='Commission type';
    	
    	 $("#oddstype").change(function(){
         type = $("#oddstype").val();
        if(type == 'No Commission'){
        $("#oddsdiv").hide();
    	$("#oddslossdiv").hide();
        }else if(type == 'Only on minus'){
        $("#label1").html("Match + Session Minus");
         $("#oddsdiv").show();
    	$("#oddslossdiv").hide();
        }else if(type == 'Bet by Bet'){
         $("#label1").html("Match Commission");
          $("#label2").html("Session Commission");
        $("#oddsdiv").show();
    	$("#oddslossdiv").show();
        }else if(type ="Match minus & Session minus") {
        	$("#oddsdiv").show();
        	$("#oddslossdiv").show();
        }
    });
    	
    });
    
     

		
	
	

    
    
		if('${creatuser.commisiontype}' == 'mobapp'){
    		$("#mobappmain").show();
    	}
    
    
   
    function createPunter()
    {
    
    	 var data="";
    	  var username = $("#username").val();
	   	  var userid = $("#userid").val();
	   	  var password = $("#password").val();
	   	 var contact = $("#contact").val();
	   	  var reference = $("#reference").val();
	   	  var parentid ='${user.id}';
	   	  var usertype =3;
	   	  var primarybalance =$("#primarybalance").val();
	      var secondrybanlce =$("#primarybalance").val();
	      var createfrom ='${user.id}';
	       var appid ='${user.appid}';
	       admincomm = 0;
	       var ratio = 100;
	      var adminpartnership = 0;
	      var adminid ="0";
	      var adminname = "";
	      var subadminpartnership = 0;
	      var subadminid = "0";
	      var subadminname = "";
	      var supermasterpartnership = 0;
	      var supermasterid ="0";
	      var supermastername = "";
	      var masterpartnertship = 0;
	      var masterid ="0";
	      var mastername = "";
	      var dealerpartnership = 0;
	      var dealerid = "0";
	      var dealername = "";
	    	  
		   	  
		    
		      if(${user.usertype}==4){
		      adminpartnership = 100;
		      adminid = parentid;
		      adminname = '${user.username}';
		      }else if(${user.usertype}==5){
		      adminid = '${creatuser.adminid}';
		      adminname = '${creatuser.adminname}';
		      subadminpartnership = '${creatuser.subadminpartnership}';
		      subadminid = parentid;
		      subadminname = '${user.username}';
		      }else if(${user.usertype}==0){
		      subadminid = '${creatuser.subadminid}';
		      subadminname = '${creatuser.subadminname}';
		      subadminpartnership = '${creatuser.subadminpartnership}';
		      supermasterpartnership = '${creatuser.supermastepartnership}';
		      supermasterid = parentid;
		      supermastername = '${user.username}';
		      }else if(${user.usertype}==1){
		      subadminid = '${creatuser.subadminid}';
		      subadminname = '${creatuser.subadminname}';
		      supermasterid = '${creatuser.supermasterid}';;
		      supermastername = '${creatuser.supermastername}';
		      masterid = parentid;
		      mastername = '${user.username}';
		      subadminpartnership ='${creatuser.subadminpartnership}';
		      supermasterpartnership ='${creatuser.supermastepartnership}';
		      masterpartnertship = '${creatuser.masterpartership}';
		      dealername = '${creatuser.username}'
		      mastername='${creatuser.username}'
		    
		      
		      }else{
		       subadminid = '${creatuser.subadminid}';
		      subadminname = '${creatuser.subadminname}';
		      supermasterid = '${creatuser.supermasterid}';;
		      supermastername = '${creatuser.supermastername}';
		       masterid = '${creatuser.masterid}';;
		      mastername = '${creatuser.mastername}';
		      dealerpartnership = 100;
		      dealerid = parentid;
		      dealername = '${user.username}';
		      }
		      
		     
		      var netexpo = $("#netexposure").val();
		      var wincommission = $("#wincom").val();
		      
		      
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
		  
		  var mobcommission = "";
			if('${creatuser.commisiontype}' == "mobapp"){
		   		if('${creatuser.mobappcharge}' == 0){
		   			mobcommission=0;
		   		}else{
		   			mobcommission = $("#commision").val();
		   		}
		      
		   		type = $("#oddstype").val();
		    
			  		   data = {createfrom:createfrom,webtype:true,username:username,password:password,userid:userid,type:'${creatuser.type}',parentid:parentid,adminid:adminid,adminpartership:adminpartnership,masterid:masterid,masterpartership:masterpartnertship,usertype:3,
			    	  primarybalance:primarybalance,secondrybanlce:secondrybanlce,appid:appid,admincomm:admincomm,
			    	  supermastepartnership:supermasterpartnership,subadminid:subadminid,subadminpartnership:subadminpartnership,supermasterid:supermasterid,netexposure:netexpo,delearpartership:dealerpartnership,dealerid:dealerid,isDirect:true,
			    	  reference:reference,contact:contact,bhaotype:'${creatuser.bhaotype}',mobappcharge:'${creatuser.mobappcharge}',losscommision:'${creatuser.losscommision}',commisiontype:'${creatuser.commisiontype}',commision:'${creatuser.commision}',adminname:adminname,subadminname:subadminname,supermastername:supermastername,mastername:mastername,dealername:dealername,mobappcharge:mobcommission,wincommision:wincommission,commisionpercentage:'${creatuser.commisionpercentage}',
			    	  oddswin:oddswin,oddsloss:oddsloss,fancywin:fancywin,partnershipratio:'${creatuser.partnershipratio}',type:'${creatuser.type}',
		    		  fancyloss:fancyloss,oddswincommisiontype:type,oddslosscommisiontype:type,fancywincommisiontype:type,fancylosscommisiontype:type}
	
	    if(parseInt(primarybalance)<=$("#directuser_admin").val() && $("#oddstype").val() !='Commission type')  {
	    		
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
   }		
    
    
    function clearform(class_name) {
    	  jQuery("."+class_name).find(':input').each(function() {
    	    switch(this.type) {
    	        case 'password':
    	        case 'text':
    	        case 'textarea':
    	        case 'file':
    	        case 'select-one':
    	        case 'select-multiple':
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
    	
    	
  
    	getCommission();
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
    	 
    		function getMasterBalaceById()
            {
            	  
        	     	$.ajax({
        	    	
        				type:'GET',
        				url:'<s:url value="/api/getSecondaryBalanceById?id="/>'+'${creatuser.id}',
        				contentType:"application/json; charset=utf-8",
        				success: function(jsondata, textStatus, xhr) 
        				{
        					$("#direct_balance").html("<="+jsondata.balance);	
        					$("#directuser_admin").val(jsondata.balance);	
        					
        				},
        				complete: function(jsondata,textStatus,xhr) {
        					

        			    } 
        			
        		});
        	   }
    </script>
    <input type="hidden" id="directuser_admin">