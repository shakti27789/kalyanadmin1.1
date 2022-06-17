  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>

 
 
 <s:if test="${user.usertype==5}">
           <div class="card-bet opt-btn mt-3 pt-0 mt-sm-0">
            <div class="form-row" id="resetmaximumbetdiv">
				Maximum Bet:	<div class="col-sm-3">
						<select class="form-control form-control-sm" id="maximumbet" onchange="updateSBAMinMaxEvent()">
							
						</select> 
		    </div>
		</div>
		</div>
		</s:if>
		