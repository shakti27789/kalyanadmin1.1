<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>
<div class="sidebar-nav navbar-collapse">
	<ul class="nav" id="side-menu">
		<s:if test="${user.usertype ne 6  && user.usertype ne 7 }">

			<li class="active"><a
				href="<s:url value="/dashboard/${user.usertype}"/>" class="active">Dashboard
			</a></li>

		</s:if>


		<s:if test="${user.usertype==4}">
			<li><a id="sports" href="<s:url value="/sport"/>">Add Sports</a>
			</li>
		</s:if>

		<s:if test="${user.usertype==6}">
			<li><a id="sports" href="<s:url value="/sport"/>">Add Sports</a></li>
			<li><a id="activeMatch" href="<s:url value="/activeMatch"/>">Active MatchList</a></li>

		</s:if>

		<s:if test="${user.usertype==7}">

			<li><a href="<s:url value="/openFancy"/>">Set FancyResult</a></li>
			<li><a href="<s:url value="/openMarket"/>">Set MarketResult</a>
			</li>

		</s:if>

		<s:if test="${user.usertype ne 6  && user.usertype ne 7 }">
			<li><a href="javascript:void(0);">Master Details <span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="<s:url value="/userList"/>">Member Listing</a></li>
					<li id="creatememberli"></li>

					<s:if test="${user.usertype==5}">
						<li id="superagentli"><a href="<s:url value="/createUser/0/${user.id}"/>">Create Super Master</a></li>
						<li id="superagentli"><a href="<s:url value="/createUser/1/${user.id}"/>">Create Master</a></li>
					</s:if>
					<s:if test="${user.usertype==0}">
						<li id="superagentli"><a href="<s:url value="/createUser/1/${user.id}"/>">Create Master</a></li>
					</s:if>

					<li id="directuserli"></li>
					<li>
				</ul> <!-- /.nav-second-level --></li>
		</s:if>


		<s:if test="${user.usertype ne 6  && user.usertype ne 7 }">
			<li><a href="javascript:void(0);">Sport Details <span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level">

					<li><a href="<s:url value="/netExposure"/>">Match Plus Minus</a></li>
					<li><a href="<s:url value="/sportDetail"/>">sport Detail</a></li>
					<li><a href="<s:url value="/liveMatchbets"/>">Live Market Bets</a></li>
					<s:if test="${user.usertype==4}">
						<li><a href="<s:url value="/openBets"/>">Open Bets</a></li>
					</s:if>
					<li>
				</ul> <!-- /.nav-second-level --></li>


			<s:if test="${user.usertype ne 6  && user.usertype ne 7 }">
				<li><a href="javascript:void(0);">Reports <span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
						<li><a href="<s:url value="/profitAndLoss"/>">Profit&Loss</a>
						</li>
						<li><a href="<s:url value="/statements"/>">Statement</a></li>
						<li><a href="<s:url value="/lenadena"/>">LenaDena</a></li>
					</ul> <!-- /.nav-second-level --></li>
			</s:if>

		</s:if>
		<s:if
			test="${(user.usertype ne 6) && (user.usertype ne 3) && (user.usertype ne 7)  }">
			<li><a id="ledger" href="<s:url value="/ledger"/>"> User Ledger</a></li>
			<li><a id="cashTransaction" href="<s:url value="/cashTransactionNew"/>"> Cash Transation</a></li>

		</s:if>


		<s:if test="${user.usertype ne 6}">
			<li><a id="result" href="javascript:void(0);"> Results <span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level">
					<li><a href="<s:url value="/openFancy"/>">Set FancyResult</a></li>
					<li><a href="<s:url value="/openMarket"/>">Set MarketResult</a></li>
				</ul> <!-- /.nav-second-level --></li>
		</s:if>
		<s:if test="${user.usertype ne 6  && user.usertype ne 7 }">
			<li><a href="javascript:void(0);">Settings <span
					class="fa arrow"></span></a>
				<ul class="nav nav-second-level">


					<li><a id="message" href="<s:url value="/message"/>">Set Message</a></li>
					<li><a href="<s:url value="/openMarketList"/>">Open Market</a></li>
					<s:if test="${user.usertype==4}">
						<li><a id="activeMatch" href="<s:url value="/activeMatch"/>">Active MatchList</a></li>
						<li><a id="inactiveMatch" href="<s:url value="/inactiveMatch"/>">Inactive MatchList</a></li>
						<li><a id="minmaxbet" href="<s:url value="/minMaxBet"/>">Min Max Bet</a></li>
					</s:if>
					<li><a href="<s:url value="/betHistory"/>">Bet History</a></li>
					<li><a id="deletebets" href="<s:url value="/deletedBets"/>">Deleted Bets</a></li>
					<li><a id="managePartnership" href="<s:url value="/managePartnership"/>">Manage Partnership</a></li>
					<li><a id="usersList" href="<s:url value="/usersList"/>">Users List</a></li>
					<s:if test="${user.usertype==4}">
						<li><a id="usersList" href="<s:url value="/userDetail"/>">Search User</a></li>
						<li><a id="minMaxBetSetAmount" href="<s:url value="minMaxBetSetAmount"/>">minMaxBetSetAmount</a> </li>
						<li><a id="inactiveuser" href="<s:url value="/inactiveUser"/>">Inactive User</a></li>
						<li><a id="inactiveuser" href="<s:url value="/editFancy"/>">Edit Fancy</a></li>
					</s:if>

					<li>
						<!-- /.nav-second-level -->
					</li>
				</ul> <!-- /.nav-second-level --></li>
		</s:if>
	</ul>
</div>
<!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
</nav>

<script>
        
        if(${user.usertype==4} ||${user.usertype==5} ||${user.usertype==0} ||${user.usertype==1}){
        	$("#managePartnership").show();  
            
        }
    	
    	
        var value = "";
       
        if(${user.usertype} ==4)
        {
        	//$("#sports").hide();
        	$("#permission").hide();
        value = "Create Admin"; 
        }else if(${user.usertype} ==5){
        value = "Create Sub Admin";
        
        }
        else if(${user.usertype} ==0){
        value = "Create Super Master";
        $("#matchSetting").show();
        }
        else if(${user.usertype} ==1){
        value = "Create Master";
        }else{
        value = "Create Client";
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
              
              $("#inactiveMatch").hide();
             
            $("#activeMatch").hide(); 
            if(${user.usertype==4}|| ${user.usertype==6}){
            $("#permission").show();
            $("#deletebets").show(); 
            $("#message").show();
            $("#activeMatch").show();
           //$("#inactiveuser").hide();
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
        
        
     /*    if(${user.appid==2}||${user.appid==1}){
            $("#cashTransaction").show();
            $("#ledger").show();	
        }else{
            $("#cashTransaction").hide();
            $("#ledger").hide();
        } */
        
        
        </script>