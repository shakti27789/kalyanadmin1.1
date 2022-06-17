<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="base" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
    <head>
      <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exchange</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Oswald|Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<base:url value="/cssnew/admin.css"/>" media="screen" type="text/css" />
     <link rel="stylesheet" href="<base:url value="/cssnew/bootstrap-datepicker.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="/cssnew/jquery.dataTables.min.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="/cssnew/responsive.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="/cssnew/table.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
    
       
    </head>
    <body>
          <script type="text/javascript">
           
             
            //Bind keypress event to document
            $(document).keypress(function(event){
                var keycode = (event.keyCode ? event.keyCode : event.which);
                if(keycode == '13'){
                	adminLogin();
                }
            });
        </script>
        
        
         <div class="login-page">
        <div class="login">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="login-content">
						<div class="logo float-none d-block text-center">
							<h4 style="color:white;" id="logo"></h4> 
						</div>
                            <div class="form-box">
                                <form>
                                    <div class="hand-box">
                                        <div class="linebox-1"></div>
                                        <span><b>Sign In</b></span>
                                        <div class="linebox-2"></div>
                                    </div>
                                    <div class="user-box ubox">
                                        <input type="text" class="form-control" name="userid"  placeholder=" User Name" id="userid">
                                       
                                    </div>
                                    <div class="password-box ubox">
                                        <input type="password" class="form-control" placeholder="Password" name="password" id="password">
                                       
                                    </div>
                                    <div class="submit-box sbox" onclick="adminLogin()" >
                                        <button type="button" class="btn" >Login</button>
                                        <i class="fa fa-sign-in"></i>
                                    </div>
                                   
                                </form>
                                
                            </div>
                            <div class="c-right">
                               <!--  <p> &copy TIMEXSPORT</p> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        
        
<script>

/*  if('${user.usertype}'!=''){
 $("#logout").show();
 
 } */
$(document).ready(function() {

	  $.getJSON("https://api.ipify.org?format=json", 
              function(data) { 

console.log(data.ip)
var  appName='${appName}';
$("#logo").html(appName);
//$("#userid").val(data.ip)

}) 
/* 	 if ($(window).width() <= 767){	
		  var  imageUrl='${mobimageimage}';
		$('.login-page').css('background-image', 'url(' + imageUrl + ')');
	}else{
		  var  imageUrl='${desktopimage}';
		  $('.login-page').css('background-image', 'url(' + imageUrl + ')');
		
	  }
	 $(window).resize(function(){
			if ($(window).width() <= 767){	
				var  imageUrl='${mobimageimage}';
				$('.login-page').css('background-image', 'url(' + imageUrl + ')');
			}else{
				  var  imageUrl='${desktopimage}';
				  $('.login-page').css('background-image', 'url(' + imageUrl + ')');
				  //alert()
			  }	
			
		}); */
});
	

 function showMessage(message,type,title){
	 swal({
		  	position: 'top-end',  
			title: title,
           text: message,
           type: type,
           timer: 3000,
           showConfirmButton: false
       });
	 
}
 function selfLogoutUser(){

	    $.ajax({
	   			url:'<s:url value="/api/logout?id="/>'+"${user.id}",
	  			success: function(jsondata, textStatus, xhr) {
	  				if(xhr.status == 200){
	  					$.removeCookie("pop");
	  					location.replace("<s:url value="/login"/>")	  	
	 	  				//$.removeCookie("pop");
	 	  				
	  				}
	    		}
	    	});
	    
	 	  
	    }
function adminLogin(){
	data = {userid:$("#userid").val(),password:$("#password").val(),} 
	
	$.ajax({
	type:'POST',
	url:'<s:url value="/api/loginadmin"/>',
	data: JSON.stringify(data),
	dataType: "json",
	contentType:"application/json; charset=utf-8",
	
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200){
				if(jsondata.type =="error"){
					
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				}else{
					location.replace("<s:url value="/adminlogin"/>")
				}
	  					  	
				}
		}
	});
}
</script>
    </body>
</html>