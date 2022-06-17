<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>


<script>


    $(document).ready(function(){
    	
    	//$(".cliennt-container").html('<div class="loader"></div>');
   	 
    	  
    		 $("#Deposit-form").validate({
    			  rules: {
    			    
    			    amountdeposite: {
    			      required: true,
    			      minlength: 3
    			    },remarkdeposite: {
    			      required: true,
    			      minlength: 1
    			    },parentpassdeposite: {
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
  
   
   
  

    function userList(userlist){
    	$(".account-container").append('<div class="loader"></div>')   
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/user/${user.id}/"/>'+5+"?active=true&type=All&isaccountclosed="+userlist,
			success: function(jsondata, textStatus, xhr) {
			     $("#userListDivMaster").html('');
				 var source   = $("#subadminHandler").html();
		    	 var template = Handlebars.compile(source);
		    	 $("#userListDivMaster").append( template({data:jsondata.data.total}));
		    	    $(".loader").fadeOut("slow");
			    	 
		    	 
		    	 
		    	 $('.masterTable').DataTable( {
		    		  "pageLength": 50
		    	 } );
		    	 
		    	 $("#userListDivClient").html('');
		    	 var source1   = $("#clientHandler").html();
				 var template = Handlebars.compile(source1);
		    	 $("#userListDivClient").append( template({data:jsondata.data.client}));
		    	 $('#userTable').DataTable( {
		    		  "pageLength": 50
		    	 } );
		    	$("#downlineWithProfitLoss").html(jsondata.data.total[0].uplineAmount)
		    	$("#downlineavailabebal").html(jsondata.data.total[0].availableBalance)
		    		
			}
			
    	});
    }
    
    function modalDeposite(parentId,childId,pnl){
            
    	    getParentChildBalance(childId,parentId,'deposite');
    	     
            $(".childdeposite").text(childId+":")
           
    		$(".deposit-popUp").addClass("active");
            $(".d-cancel-btn").click(function(){
              $(".deposit-popUp").removeClass("active");
            });
           
            $(".back-d-btn").click(function(){
                $(".deposit-popUp").removeClass("active");
              });
            
          
           var chips = $("#amountdeposite").val(); 
      	   $("#parentId").val(parentId)
      	   $("#childId").val(childId)
      
	    	 
    }

    function modal_CreditRef(parentId,childId,pnl){
        
	    getParentChildBalance(childId,parentId,'credtref');
	     
        $(".childdeposite").text(childId+":")
       
		$(".deposit-popUp").addClass("active");
        $(".d-cancel-btn").click(function(){
          $(".deposit-popUp").removeClass("active");
        });
       
        $(".back-d-btn").click(function(){
            $(".deposit-popUp").removeClass("active");
          });
        
      
       var chips = $("#amountdeposite").val(); 
  	   $("#parentId").val(parentId)
  	   $("#childId").val(childId)
  
    	 
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
    
    
 /*    function changePasswordModal(childId,parentId){
        
    
       	    $("#childId").val(childId);
       	    $("#parentId").val(parentId);
    		$(".change_password_Popup").addClass("active");
            $(".d-cancel-btn").click(function(){
              $(".change_password_Popup").removeClass("active");
            });
          
      
         
     } */
     
    function getParentChildBalance(childId,parentId,type){
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/getParentChildBalance/"/>'+childId+"/"+parentId+"/"+type,
			success: function(jsondata, textStatus, xhr) {
			
				 $("#oldcredit").val(jsondata.availableBcreditRefChild);
				 $("#creditReference").val(jsondata.availableBcreditRefChild);
				 $("#creditReferencehidden").val(jsondata.availableBcreditRefChild);
				 
				 $("#mainbalanceparentdeposite").val(jsondata.availableBalanceParent);
		         $("#mainbalancechilddeposite").val(jsondata.availableBalanceChild);
		         $("#mainbalanceparentdepositehidden").val(jsondata.availableBalanceParent);
		         $("#mainbalancechilddepositehidden").val(jsondata.availableBalanceChild);
		         
		         $("#mainbalanceparentwithdraw").val(jsondata.availableBalanceParent);
		         $("#mainbalancechildwithdraw").val(jsondata.availableBalanceChild);
		         $("#mainbalanceparentwithdrawhidden").val(jsondata.availableBalanceParent);
		         $("#mainbalancechildwithdrawhidden").val(jsondata.availableBalanceChild);
				
				 $(".mainbalanceparentdepositewithraw").val(jsondata.availableBalanceParentPnl);
		         $(".mainbalancechilddepositewithraw").val(jsondata.availableBalanceChild);
		         $(".mainbalanceparentdepositewithrawhidden").val(jsondata.availableBalanceParentPnl);
		         $(".mainbalancechilddepositewithrawhidden").val(jsondata.availableBalanceChild);
			        
			}
    	
    	});
    }
    
    function betLockByParent(parentid) {
    	//$("#myTable").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/betLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			userList(false);
    			
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
        });
    	$(".loader").fadeOut("slow");
    }
    
    function betUnLockByParent(parentid){
    	//$("#userlistdiv").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/betUnLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			//$("#userlistdiv").html('');
    			userList(false);
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }


    function accouCloseByParent(parentid) {

        	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/betLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			userList(false);
    			
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
        });
    	$(".loader").fadeOut("slow");
    }
    
    function betUnLockByParent(parentid){
    	//$("#userlistdiv").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/betUnLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			//$("#userlistdiv").html('');
    			userList(false);
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }
    

    
    function casinobetLockByParent(parentid) {
    	//$("#myTable").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/casinoBetLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			userList(false);
    			
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
        });
    	$(".loader").fadeOut("slow");
    }

    function casinobetUnLockByParent(parentid){
    	//$("#userlistdiv").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/casinobetUnLockByParent/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			//$("#userlistdiv").html('');
    			userList(false);
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }


    function accountLockByParent(parentid) {
    	$("#masterTable").html('<div class="loader"></div>')
    	
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/accountLock/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			 userList(false);
    			
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
        });
    	 $(".loader").fadeOut("slow");
    }
    

    function casinoBetLock(userId,type){
    	//$("#userlistdiv").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'POST',
    		url:'<s:url value="api/casinoBetLock/"/>'+type+"/"+userId,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			userList(false);
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }
    
    function casinoBetUnLock(userId,type){
    	//$("#userlistdiv").html('<div class="loader"></div>')
    	$("#userListDivMaster").html('<div class="loader"></div>');
	 
    	$.ajax({
    		type:'POST',
    		url:'<s:url value="api/casinoBetUnLock/"/>'+type+"/"+userId,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			userList(false);
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }

    
    
    function accountCloseByParent(parentid) {
    
    		 swal({
     	      title: 'Are you sure?',
     	      text: 'You Want to close Acount!',
     	      type: 'warning',
     	      showCancelButton: true,
     	      confirmButtonColor: '#3085d6',
     	      cancelButtonColor: '#d33',
     	      confirmButtonText: 'Yes',
     	      closeOnConfirm: false
     	    }).then(function(isConfirm) {
     	      if (isConfirm) {		 	
     	    		$("#masterTable").html('<div class="loader"></div>')
     	       	
     	       	
     	       	
     	    	 $.ajax({
     	    		type:'PUT',
     	    		url:'<s:url value="api/accouCloseByParent/"/>'+parentid,
     	    		success: function(jsondata, textStatus, xhr) 
     	    		{
     	    			showMessage(jsondata.message,jsondata.type,jsondata.title);
     	    			userList(false);
     	    			
     	    		},
     	    		complete: function(jsondata,textStatus,xhr) {
     	    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
     	    			 
     	    	    } 
     	        });
     	    	 $(".loader").fadeOut("slow");
 		 	
 		 	 } 
 		 	});
    	
    	
    	
    	
    }



    function accountOpenByParent(parentid) {
        
		 swal({
	      title: 'Are you sure?',
	      text: 'You Want to Open Acount!',
	      type: 'warning',
	      showCancelButton: true,
	      confirmButtonColor: '#3085d6',
	      cancelButtonColor: '#d33',
	      confirmButtonText: 'Yes',
	      closeOnConfirm: false
	    }).then(function(isConfirm) {
	      if (isConfirm) {		 	
	    		$("#masterTable").html('<div class="loader"></div>')
	       	
	       	 $.ajax({
	    		type:'PUT',
	    		url:'<s:url value="api/accountOpenByParent/"/>'+parentid,
	    		success: function(jsondata, textStatus, xhr) 
	    		{
	    			showMessage(jsondata.message,jsondata.type,jsondata.title);
	    			userList(true);
	    			
	    		},
	    		complete: function(jsondata,textStatus,xhr) {
	    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
	    			 
	    	    } 
	        });
	    	 $(".loader").fadeOut("slow");
	 	
	 	 } 
	 	});
	
	
	
	
}
    
    function accountUnLockByParent(parentid){
    	$("#masterTable").html('<div class="loader"></div>')
    	$.ajax({
    		type:'PUT',
    		url:'<s:url value="api/accountUnLock/"/>'+parentid,
    		success: function(jsondata, textStatus, xhr) 
    		{
    			showMessage(jsondata.message,jsondata.type,jsondata.title);
    			//$("#userlistdiv").html('');
    			userList(false);
    			
    		},
    		complete: function(jsondata,textStatus,xhr) {
    			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    			 
    	    } 
      });
    	 $(".loader").fadeOut("slow");
    }

    function currentBets(){
		
        }
    
</script>