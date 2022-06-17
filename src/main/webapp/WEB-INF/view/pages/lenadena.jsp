<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script id="lenaHandler" type="text/x-handlebars-template">
	<table id="myTable" class="table table-striped table-bordered " style="width:100%">
       
               <thead>
                   <tr>
                       <th>User Detail</th>
                       <th>Amount</th>
                    </tr>
               </thead>
              
               <tbody>
                {{#each data}}     
				  <tr>
    				<td>{{userid}} ->{{username}} </td>
	                    {{#if_eq hisabkitabtype 1}} 	
	<td><div class="btn btn-danger text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
                   
                      {{else}}  
					<td><div class="btn btn-danger text-center">{{uplineAmount}}</div></td>

					 {{/if_eq}}					
  </tr>
				{{/each}} 
	          </tbody>
	</table>
</script>


<script id="denaHandler" type="text/x-handlebars-template">
	<table id="myTable" class="table table-striped table-bordered " style="width:100%">
       
               <thead>
                   <tr>
                       <th>User Detail</th>
                       <th>Amount</th>
                    </tr>
               </thead>
              
               <tbody>
                {{#each data}}     
				  <tr>
    				<td>{{userid}} ->{{username}} </td>
   						 {{#if_eq hisabkitabtype 1}} 	
					<td><div class="btn btn-success  text-center" onclick="Client_pnl_modal('{{parentid}}','{{userid}}','{{availableBalanceWithPnl}}','{{username}}','{{uplineAmount}}','{{usertype}}')">{{uplineAmount}}</div></td>
 						{{else}}  
					<td><div class="btn btn-success  text-center">{{uplineAmount}}</div></td>
 
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
     <div class="account-container">
  
  <div class="account-record">
  <div class="head-tittle">
      <h4>Lena Dena</h4>
  </div>
  
  <div class="input-date">
    <div class="select-box match-box w-100 mr-0">
        <form>
			<div class="form-row">
				<div class="col-sm-6">
					<div class="head-tittle" style="background-color: red;">
      					<h4>Lena </h4>
  					</div>
					<div class="account-table table-responsive" id="lenadiv"></div>
				</div>
				
				
				<div class="col-sm-6">
				 <div class="head-tittle" style="background-color: green;">
                   <h4>Dena </h4>
                 </div>
				<div class="account-table table-responsive" id="denadiv"></div>
				</div>
			</div>
		</form>
    </div>
 </div>
  
    </div>
    </div>
</div>
<script>

$(document).ready(function(){
	lenaDena();
});
function lenaDena(){
	$.ajax({
		type:'GET',
		url:'<s:url value="/api/lenadena/${user.id}/"/>'+5+"?active=true&type=All",
		success: function(jsondata, textStatus, xhr) {
		     var source   = $("#lenaHandler").html();
	    	 var template = Handlebars.compile(source);
	    	 $("#lenadiv").append( template({data:jsondata.data.lena}));

	    	 var source1   = $("#denaHandler").html();
	    	 var template1 = Handlebars.compile(source1);
	    	 $("#denadiv").append( template1({data:jsondata.data.dena}));
		}
	
	});
}

function Client_pnl_modal(parentId,childId,pnl,userName,uplineAmount,userType){
    
	
	
	  $("#deposit_withdraw_Popup").html('');
	 var source   = $("#deposite_withdraw_modalHandler").html();
	    var template = Handlebars.compile(source);
	 $("#deposit_withdraw_Popup").append( template({}));
	
	
	if(parseFloat(uplineAmount)!=0){
    getParentChildBalance(childId,parentId,'lenadena');
 	 $(".mypnl").val(uplineAmount)
	 $("#userName").val(userName)
	 $("#userType").val(userType)
 
 	 $(".childdeposite").text(childId+":")
		$(".deposit_withdraw_Popup").addClass("active");
      $(".d-cancel-btn").click(function(){
        $(".deposit_withdraw_Popup").removeClass("active");
      });
     
      $(".back-d-btn").click(function(){
          $(".deposit_withdraw_Popup").removeClass("active");
        });
      
    
     var chips = $("#amountdeposite").val(); 
	   $("#parentId").val(parentId)
	   $("#childId").val(childId)

  }  
}

function getParentChildBalance(childId,parentId,type){
	$.ajax({
		type:'GET',
		url:'<s:url value="/api/getParentChildBalance/"/>'+childId+"/"+parentId+"/"+type,
		success: function(jsondata, textStatus, xhr) {
		
			 $(".mainbalanceparentdepositewithraw").val(jsondata.availableBalanceParentPnl);
	         $(".mainbalancechilddepositewithraw").val(jsondata.availableBalanceChild);
	         $(".mainbalanceparentdepositewithrawhidden").val(jsondata.availableBalanceParentPnl);
	         $(".mainbalancechilddepositewithrawhidden").val(jsondata.availableBalanceChild);
		        
		}
	});
}
</script>
   <!-- Deposit box -->
     <div class="deposit_withdraw_Popup" id="deposit_withdraw_Popup">
      
    </div>
    	<jsp:include page="handlebars.jsp" />  
  <!-- Deposit end -->
    <script>
   var val =0;
  var amount =0;
  mastetbalanceplus=true;
  $(document).ready(function(){
	  
	 var now = new Date();
	 
  	var day = ("0" + now.getDate()).slice(-2);
  	var month = ("0" + (now.getMonth() + 1)).slice(-2);
  	
  	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
  	
  	 var date = new Date();
     var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate()-30);
       

  	 $('#date').datepicker({
  			format: "yyyy-mm-dd",
  			autoclose: true,
  			todayHighlight: true
  			
   });
      
  	 $('#date').datepicker('setDate', startdate);
  	  
	  $(".limit-btn").click(function(){
      $(".limit-popUp").addClass("active");
     
        
      $(".d-cancel-btn").click(function(){
          $(".deposit_withdraw_Popup").removeClass("active");
          $('#DepositWithraw-form')[0].reset();
      });
      $(".back-d-btn").click(function(){
    	  $(".deposit_withdraw_Popup").removeClass("active");
    	  $('#DepositWithraw-form')[0].reset();
      });
    });
	  
	  $("#DepositWithraw-form").validate({
		  rules: {
			  amountdepositewithraw : {
		      required: true,
		      minlength: 1
		    },remarkdepositewithraw: {
		      required: true,
		      minlength: 3
		    },parentpassdepositewithraw: {
		      required: true,
		      minlength: 3
		    }
		  },
		     messages: {
		    	 amountdeposite: {required:"Please Enter Amount"},
		    	 remarkdeposite:{required: "Please Enter remark"},
		    	 parentpassdeposite: {required: "Please Enter You Password"},
	          },
		  errorPlacement: function(label, element) {
				$('<span class="arrow"></span>').insertBefore(element);
				$('<span class="error"></span>').insertAfter(element).append(label)
			}
		});
	  
	  
  });
  
  
  function chipCreditWithdraw(){
	
  	var $valid = $("#DepositWithraw-form").valid();
  	if($valid){
  		 var type="";	 
  	     var paymenttype ="";
  		 
  		
  		 if(parseFloat($(".mypnl").val())<0){
	  	    	paymenttype ="Dena";
	  	      }else{
	  	    	paymenttype ="Lena";
	  	      }
  	     
  		 var data = {
				userid:$("#childId").val(),
				collectionname:"cash",
				username:$("#userName").val(),
				date:$("#date").val(),
				amount:$(".amountdepositewithraw").val(),
				paymenttype:paymenttype,
				remark:$("#remarkdepositewithraw").val(),
				type:type,
				password:$("#parentpassdepositewithraw").val(),
				mastetbalanceplus:mastetbalanceplus
				};

   		
  		 if(parseFloat($("#amountdepositewithraw").val())>amount){
  			showMessage("Please Enter Valid Amount","error","Oops...");
  		 }else{
  			  $('#DepositWithraw-form')[0].reset();
			  $(".deposit_withdraw_Popup").removeClass("active");
			
				 $.ajax({
					type:'POST',
					url:'<s:url value="/api/setCashTransactionNew"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
					},
					complete: function(jsondata,textStatus,xhr) {
						 var myObj = JSON.parse(jsondata.responseText);
						 showMessage(myObj.message,myObj.type,myObj.title);
						$("#lenadiv").html(''); 
						$("#denadiv").html(''); 
						lenaDena();
						$("#amount").val('');
						$("#remarks").val('');
					    $("#paymenttype").html('<option value ="-1">Payment-Type</option><option value="dena" >PAYMENT-DENA</option><option value="lena">PAYMENT-LENA</option>');		
					  //  $(".deposit_withdraw_Popup").removeClass("active");
					} 
				});
  		 }
  		 	
  	    
  	      
  	     
  	}
  	 
 }
  
  $('body').on('keyup','.amountdepositewithraw',function(){
		
	 	 if($(".amountdepositewithraw").val()!=""){
		  if(parseFloat($(".mypnl").val())<0){
			 
				    amount = -parseFloat($(".mypnl").val());
				    $(".aftermainbalanceparentdepositewithraw").val(parseFloat($("#mainbalanceparentdepositewithrawhidden").val().trim())-parseFloat($(".amountdepositewithraw").val()))
					$(".aftermainbalancechilddepositewithraw").val(parseFloat($("#mainbalancechilddepositewithrawhidden").val().trim())+parseFloat($(".amountdepositewithraw").val()))  
				 
			 
		  }else{
			   
			  amount = parseFloat($(".mypnl").val());
			 // alert(parseFloat($(".mypnl").val()))
			 $(".aftermainbalanceparentdepositewithraw").val(parseFloat($("#mainbalanceparentdepositewithrawhidden").val().trim())+parseFloat($(".amountdepositewithraw").val()))
			$(".aftermainbalancechilddepositewithraw").val(parseFloat($("#mainbalancechilddepositewithrawhidden").val().trim())-parseFloat($(".amountdepositewithraw").val()))
					  
			  
		 }
		  
		}else{
			  $(".aftermainbalanceparentdepositewithraw").val(parseFloat($("#mainbalanceparentdepositewithrawhidden").val().trim()))
			  $(".aftermainbalancechilddepositewithraw").val(parseFloat($("#mainbalancechilddepositewithrawhidden").val().trim()))
		}
});
</script>
	