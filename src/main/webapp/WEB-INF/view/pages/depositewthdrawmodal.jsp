<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


        <!-- Deposit box -->
     <div class="deposit_withdraw_Popup" id="deposit_withdraw_Popup">
      
    </div>
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
  	     $(".deposit_withdraw_Popup").removeClass("active");
  	   $(".account-container").append('<div class="loader"></div>') 
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
						$(".loader").fadeOut("slow");
						userList(false);
					},
					complete: function(jsondata,textStatus,xhr) {
						 var myObj = JSON.parse(jsondata.responseText);
						 showMessage(myObj.message,myObj.type,myObj.title);
						 
						userList(false);
						$("#amount").val('');
						$("#remarks").val('');
					    $("#paymenttype").html('<option value ="-1">Payment-Type</option><option value="dena" >PAYMENT-DENA</option><option value="lena">PAYMENT-LENA</option>');		
					  //  $(".deposit_withdraw_Popup").removeClass("active");
					    $(".loader").fadeOut("slow");
					} 
				});
  		 }
  		 	
  	    
  	      
  	     
  	}
  	 
 }
  
  $('body').on('keyup','.amountdepositewithraw',function(){
		        //alert($("#userType").val())
	 	 if($(".amountdepositewithraw").val()!=""){
		  if(parseFloat($(".mypnl").val())<0){
			      /*  if($("#userType").val()==3){
			        	 $(".aftermainbalanceparentdepositewithraw").val(parseFloat($("#mainbalanceparentdepositewithrawhidden").val().trim()))
							
				        }else{
				        	 $(".aftermainbalanceparentdepositewithraw").val(parseFloat($("#mainbalanceparentdepositewithrawhidden").val().trim())-parseFloat($(".amountdepositewithraw").val()))
							}*/
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