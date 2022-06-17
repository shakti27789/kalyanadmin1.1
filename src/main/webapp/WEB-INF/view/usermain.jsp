<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="base" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="<base:url value="/user/css/bootstrap.min.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/user/css/font-awesome.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/user/css/jquery.multilevelpushmenu.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/user/css/style.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/user/css/userResponsive.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value="/include/css/sweetalert2.min.css"/>" media="screen" type="text/css" />
      <link rel="stylesheet" href="<base:url value=" https://dzm0kbaskt4pv.cloudfront.net/js/videoplayer/v1.css"/>" media="screen" type="text/css" />
    
      <script src="<base:url value="/include/js/sweetalert2.min.js"/>" type="text/javascript"></script> 
      <script src="<base:url value="/js/message.js"/>" type="text/javascript"></script>
       	 
       	 
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
 	  <script src="<base:url value="/user/js/bootstrap.min.js"/>" type="text/javascript"></script>
 	  <script src="<base:url value="/user/js/jquery.multilevelpushmenu.js"/>" type="text/javascript"></script>
 	  <script src="<base:url value="/user/js/custom.js"/>" type="text/javascript"></script>
      <script src="<base:url value="/include/js/handlebars3.0.0.js"/>" type="text/javascript"></script>  
      <script src="<base:url value="/include/js/handlebars-helper-x.js"/>" type="text/javascript"></script> 
       <script src="http://13.235.68.75/player.min.js"></script>
         <%--  <script src="<base:url value="/user/js/TVload.js"/>" type="text/javascript"></script>  --%>
          
         
</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="leftpane" />
	<tiles:insertAttribute name="rightpane" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="stack" />
	<tiles:insertAttribute name="footer" />
	
	 
</body>


</html>



