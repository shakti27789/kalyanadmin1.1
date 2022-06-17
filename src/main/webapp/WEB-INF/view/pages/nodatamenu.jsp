<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
	<div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li class="active">
                            <a href="<s:url value="/dashboard/${user.usertype}"/>" class="active" >Dashboard </a>
                        </li>
                        
                        
                       <s:if test = "${user.usertype==4}">
                         <li>
                            <a id="sports" href="">Add Sports</a>
                        </li>
                        </s:if>
                        
                           <s:if test = "${user.usertype==5}">
                         <li>
                            <a id="subadminSport" href=""> Sports</a>
                        </li>
                        </s:if>
                        
                         <s:if test = "${user.usertype==0}">
                         <li>
                            <a id="superstockagesport" href=""> Sports</a>
                        </li>
                         </s:if>
                         
                          <s:if test = "${user.usertype==1}">
                         <li>
                            <a id="superstockagesport" href=""> Sports</a>
                        </li>
                         </s:if>
                        

                          <s:if test = "${user.usertype!=3}">
                         <li>
                            <a id="cashTransaction" href=""> Cash Transation</a>
                        </li>
                        <li>
                            <a id="ledger" href=""> User Ledger</a>
                        </li>
                         </s:if>
						 
                        <li>
                            <a href="javascript:void(0);">Master Details <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="">Member Listing</a>
                                </li>
                                <li id ="creatememberli">
                                 
                               
                                </li>
                                <li id="directuserli">
                                    
                                </li>
                                 <s:if test = "${user.usertype==5}">
                                 <li id="superagentli"><a href="">Create Super Agent</a></li>
                                 <li id="superagentli"><a href="">Create  Agent</a></li>
                                </s:if>
                                <s:if test = "${user.usertype==0}">
                               
                                  <li id="superagentli"><a href="">Create  Agent</a></li>
                               
                                </s:if>
                                
                                
                                <li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a href="javascript:void(0);">Sport Details <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="">Match Plus Minus</a>
                                </li>
                                <li>
                                    <a href="">Live Market Bets</a>
                                </li>
								<li>
                                    <a href="">Open Bets</a>
                                </li>
                                <li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a href="javascript:void(0);">Reports <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="">Profit&Loss</a>
                                </li>
                                <li>
                                    <a href="">Statement</a>
                                </li>
                                
                                <%--  <li>
                                    <a href="<s:url value="/oddsdata"/>">Odds Data</a>
                                </li> --%>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a id="result" href="javascript:void(0);"> Results <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="">Set FancyResult</a>
                                </li>
                                <li>

                                    <a href="">Set MarketResult</a>

                                    

                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
						<li>
                            <a href="javascript:void(0);">Settings <span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            
                                <li>
                                    <a id="permission" href="">Set Permission</a>
                                </li>
                                  <s:if test = "${user.usertype==4}">
                                <li>
                                    <a id="permission" href="">Winning Commission</a>
                                </li>
                                </s:if>
                                <li>
                                    <a id="message" href="">Set Message</a>
                                </li>
                                <li>
                                    <a href="">Open Market</a>
                                </li>
                                 <s:if test = "${user.usertype==4}">
								<li>
                                    <a id="activeMatch" href="">Active MatchList</a>
                                </li>
                                <li>
                                    <a id="inactiveMatch" href="">Inactive MatchList</a>
                                </li>
                                
                                 <li>
                                    <a id="minmaxbet" href="">Min Max Bet</a>
                                </li>
                                </s:if>
                                 <li>
                                 <a href="">Bet History</a>
                                </li>
                                
                                
                                 <li>
                                    <a id="deletebets" href="">Deleted Bets</a>
                                </li>
                                
                                <li>
                             
                                    <a id="managePartnership" href="">Manage Partnership</a>
                                  
                                </li>
                                <li>
                                    <a id="inactiveuser" href="">Inactive User</a>
                                </li>
                                <li>
                                
                                    <a id="matchSetting" href="">Match Setting</a>
                                  
                                </li>
                                <li>
                                    <a id="usersList" href="">Users List</a>
                                </li>   
                                
                                  <li>
                                    <a id="usersList" href="">Search User</a>
                                </li> 
                                
                                
                                <li>
                           
                            <!-- /.nav-second-level -->
                        </li>                              
                            </ul>
                           
                            <!-- /.nav-second-level -->
                        </li>                        
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
        
        <script>
        
    	
    	
        var value = "";
       
        if(${user.usertype} ==4)
        {
        	//$("#sports").hide();
        	$("#permission").hide();
        value = "Create Subadmin"; 
        }else if(${user.usertype} ==5){
        value = "Create Super-Stokist";
        
        }
        else if(${user.usertype} ==0){
        value = "Create Super Agent";
        $("#matchSetting").show();
        }
        else if(${user.usertype} ==1){
        value = "Create Agent";
        }else{
        value = "Create User";
        }
        
        $("#creatememberli").append($('<a href = "<s:url value="/createUser/"/>${user.usertype}'+"/"+'${user.id}"> '+value+'</a>'));
          
          
        if(${user.usertype} ==5 || ${user.usertype} ==0 || ${user.usertype} ==1){
          
          $("#directuserli").append($('<a href = "<s:url value="/createUser/"/>6'+"/"+'${user.id}"> Direct Client</a>'));
          $("#directuserlist").append($('<a href="<s:url value="/directuserlist"/>"> Direct Client List</a>'));
          }
          
     
        
        if(${user.usertype==5}||${user.usertype==0}){
        	if(${user.usertype==5} && ${permission.sport== 'true'} || ${user.usertype==0}){
        		$("#sports").show();
        	}else{
        		$("#sports").hide();	
        	}
        	
        }
        if(${user.usertype==6}||${user.usertype==4}){
            $("#managePartnership").show();  
              
              $("#inactiveMatch").hide();
             
          
            if(${user.usertype==4}|| ${user.usertype==6}){
            $("#permission").show();
            $("#deletebets").show(); 
            $("#message").show();
            $("#activeMatch").show();
           $("#inactiveuser").hide();
            $("#minmaxbet").show(); 
             $("#usersList").show();
            }else{
            	if(${permission.result== 'true'}){
            		 $("#result").show();	
            	}else{
            		 $("#result").hide();
            	}
            $("#permission").hide();
            if(${permission.deletedbet== 'true'}){
            	$("#deletebets").show();	
		    }else{
		    	$("#deletebets").hide();
		   	}
             
            $("#message").hide();
            $("#inactiveuser").show();
            $("#minmaxbet").hide(); 
             $("#usersList").hide();
            }
             
            $("#matchSetting").show();
           
        }else{
        if(${user.usertype==1} || ${user.usertype==0}){
         $("#managePartnership").show(); 
        }else{
         $("#managePartnership").hide(); 
        }
            
            $("#deletebets").hide();  
            $("#minmaxbet").show();  
            $("#inactiveMatch").hide();  
            if(${user.usertype}==0){
                $("#activematch").show();
             }else{
                 $("#activeMatch").hide();  
             }
            $("#message").hide();
            $("#result").hide();
            $("#inactiveuser").hide();
            $("#matchSetting").hide();
            $("#usersList").hide();
        }
        
        
    
        
        
        </script>