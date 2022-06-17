<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="base" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

     <meta name="viewport" content="width=device-width, initial-scale = 1.0, maximum-scale = 2.0">
	 
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="<base:url value="/cssnew/owl.carousel.min.css"/>">
        <link href="https://fonts.googleapis.com/css?family=Roboto&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Oswald|Roboto&display=swap" rel="stylesheet">
           <link rel="stylesheet" href="<base:url value="/cssnew/bootstrap-datepicker.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="/cssnew/jquery.dataTables.min.css"/>" media="screen" type="text/css" />
     <link rel="stylesheet" href="<base:url value="/include/css/sweetalert2.min.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/cssnew/select2.css"/>" media="screen" type="text/css" />
      
        <link rel="stylesheet" href="<base:url value="/cssnew/admin.css"/>" media="screen" type="text/css" />
  
    <link rel="stylesheet" href="<base:url value="/cssnew/responsive.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="/cssnew/table.css"/>" media="screen" type="text/css" />
    <link rel="stylesheet" href="<base:url value="https://dzm0kbaskt4pv.cloudfront.net/v3/static/css/flipclock.css"/>" media="screen" type="text/css" /> 
    
    
     <script src="<base:url value="/include/js/handlebars3.0.0.js"/>" type="text/javascript"></script>  
     <script src="<base:url value="/include/js/handlebars-helper-x.js"/>" type="text/javascript"></script> 
    <script src="<base:url value="/js/external.js"/>" type="text/javascript"></script>
      
          <!-- jQuery library -->
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
           <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
            <script src="<base:url value="/include/js/owl.carousel.min.js"/>"></script>
           <!-- Latest compiled JavaScript -->
           <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
             <script src="<base:url value="/include/js/jquery.dataTables.min.js"/>" type="text/javascript"></script> 
                 <script src="<base:url value="/include/js/bootstrap-datepicker.min.js"/>" type="text/javascript"></script> 
                 
           <script src="<base:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.16.0/jquery.validate.min.js"/>" type="text/javascript"></script> 
      <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.0/additional-methods.js"></script>
     
      <script src="https://www.gstatic.com/firebasejs/7.12.0/firebase-app.js"></script>
		<script src="https://www.gstatic.com/firebasejs/7.12.0/firebase-firestore.js"></script>
		
		<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.16.0/firebase-analytics.js"></script>
		
        <script src="<base:url value="/include/js/firebaseinit.js"/>" type="text/javascript"></script> 
       <%--   <script src="<base:url value="/include/js/select2.js"/>" type="text/javascript"></script> 
        --%> <%-- <script src="<base:url value="/include/js/cssrefresh.js"/>" type="text/javascript"></script>  --%>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
       <script src="<base:url value="/include/js/sweetalert2.min.js"/>" type="text/javascript"></script> 
       <script src="<base:url value="/js/jquery.countdown.min.js"/>" type="text/javascript"></script> 
       <script src="<base:url value="https://dzm0kbaskt4pv.cloudfront.net/v3/static/js/flipclock.js"/>" type="text/javascript"></script> 
     <script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.10/clipboard.min.js"></script>
       
</head>



 <body oncontextmenu="">
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
					
</body>
   <script type="text/javascript">

   
   
	    $(function () {
	        //$.getJSON("http://jsonip.appspot.com?callback=?", DisplayIP);

	        /*   $.getJSON("http://ipinfo.io", function (data) {

            $("#info").html("City: " + data.city + " ,County: " + data.country + " ,IP: " + data.ip + " ,Location: " + data.loc + " ,Organisation: " + data.org + " ,Postal Code: " + data.postal + " ,Region: " + data.region + "")

        })*/
	        $(".r-simcrick-infocount").show();
	    }); 


	   
	   		 

   function DisplayIP(response) {
     // alert(response.ip)
   };
 </script>
<!--  <p id="info"></p>
  -->
</html>



