
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="base" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- Favicons -->
	<link rel="shortcut icon" href="<s:url value="images/favicon.ico"/>" type="image/x-icon">
	<!-- CSS -->
	<link rel="stylesheet" type="text/css"  href="<s:url value="/css/bootstrap.min.css"/>">
	<link rel="stylesheet" type="text/css"  href="<s:url value="/css/style.css"/>">
	<script src="<s:url value="/js/jquery-3.3.1.min.js"/>" type="text/javascript"></script>
  	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
       
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>
<body>
<s:if test = "${result.type=='error'}">
   <script>
    $(document).ready(function(){

    		swal({
    		  	position: 'top-end',  
    			title: '${result.title}',
               text: '${result.message}',
               type: '${result.type}',
               timer: 3000,
               showConfirmButton: false
           });	     		
   });
  
   </script>
</s:if>
	<section class="login-page">
        <div class="login-form" id="loginform">

            <form class="ng-pristine ng-valid" method="post" action="<s:url value="/home"/>">
             <%--    <div class="text-center">
                    <img src="<s:url value="/images/logo1.jpg"/>" width="100%">
                </div> --%>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Username" value="${user.userid}" name="userid"autofocus="">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control " placeholder="Password" value="${user.password}" name="password">
                </div>
                <input type="hidden" id="logouttext" value='${user.usertype}'/>
                <div class="form-group">
                <input type="submit" id="login" class="button" value="Login">
                </div>
                
             <!--    <div class="form-group">
                <input type="button" id="logout" class="button" value="logout" onclick="selfLogoutUser()" style="display:none;">
                </div> -->
                
                
                  <%--  <div class="form-group" style="background-color: white;">
                    <h3 class="text-center">
                        <span>Powered by</span>
                        <img src="<s:url value="/images/powerdby.png"/>">
                    </h3>
                </div> --%>   
            </form>
        </div>
    </section>
	<!--Javascript-->
	<script type="text/javascript" src="<s:url value="/js/jquery-3.3.1.min.js"/>"></script>  
	<script type="text/javascript" src="<s:url value="/js/bootstrap.min.js"/>"></script>
 <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.0/jquery.cookie.min.js"  type="text/javascript"></script> 
      
<script>

 if('${user.usertype}'!=''){
 $("#logout").show();
 
 }
 var x = 0;
 $(document).ready(function() {
	
	 if ($(window).width() <= 767){	
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
			
		});
 });
	
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
 $.removeCookie("pop");
</script>
</body>
</html>