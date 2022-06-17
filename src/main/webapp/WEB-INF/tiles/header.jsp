<!-- top header -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>

<script id="sidebarhandler" type="text/x-handlebars-template">
{{#each data}}
	 <ul class="listitem2" id="accordion">
          <li class="link-one selectelist">
            <a href="#" id="main-link">{{sportName}}<i class="fa fa-plus-square-o"></i>
              <i class="fa fa-minus-square-o  text-dark" style="display: none;"></i></a>
               <ul class="sub-listitem2" id="accordion2">
           {{#each this.series}}     
			<li class="sub-selectelist"><a href="#" id="sub-link">{{seriesName}}
                   <i class="fa fa-plus-square-o "></i>
                   <i class="fa fa-minus-square-o  text-dark" style="display: none;"></i>
                   </a>
                    <ul class="date-listitem2">
                   {{#each this.date}} 
                      <li class="matchname-selectelist"><a href="#">{{startDate}}<i class="fa fa-plus-square-o"></i></a>
                        <ul class="team-name">
						{{#each this.match}} 
                          <li class="last-selectelist"><a href="#">{{matchName}} <i class="fa fa-plus-square-o"></i></a>
                         <ul class="team-odds">
                         {{#each this.market}}
							 <li class="last-selectelist"><a href="<s:url value="/innerExpo"/>/{{sportId}}/{{eventId}}/{{marketId}}">{{marketName}}</i></a></li>
						{{/each}} 
					     </ul>
                      </li>
					{{/each}} 
                        </ul>
                      </li>
					{{/each}}
                    </ul>
                </li>
            {{/each}}	
               </ul>  
          </li>
	</ul>  

{{/each}}  
</script>

<div class="header">
     <marquee behavior="" direction="" id="message_marquee" class="text-white" ></marquee>
	 <div class="nav-row">
		<div class="nav-left">
		
		 <div class="logo">
		   <h4 style="color:white;" id="logo"></h4> 
		 </div>
		
		 <div class="navbar">
			
			 <div class="navbar-link responsive-menu">				
					<div class="cross-menu" >
						<i class="fa fa-times" ></i>
					</div>
				 <ul>
				 <s:if test="${user.usertype ne 6  }">
					  <li><a href="<s:url value="/userList"/>">List Of Clients</a></li>
					  </s:if>
					  
					  
					  
					   
						<s:if test="${user.usertype==6 }">
					  <li><a href="<s:url value="/sport"/>">Add Sport</a></li>
					  <li><a id="activeMatch" href="<s:url value="/activeMatch"/>">Active MatchList</a></li>
					  </s:if>
					  
					  
					  
						 <li><a href=" <s:url value="/netExposure"/>">Market Analysis</a></li>
					 
					<%-- 	 <li class="drop-link-one"><a href="#">Live Market<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-one" >
						   <ul id="livemarket">
						 </ul>
					   </div>
					</li> --%>
					
					<li><a href=" <s:url value="/LiveCasino"/>">LiveCasino</a></li>
					
					<li><a href=" <s:url value="/VCasino"/>">Virtual Casino</a></li>
					 
					 
					<%--  <li><a href=" <s:url value="/innerExpo/2378961/2102101733/1.2102101733333"/>">Election</a></li> --%>
					 
					 <s:if test="${user.usertype==4}">
					 <li class="drop-link-one"><a href="#">Settings<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-one" >
						   <ul>
							  
							  
							 
								<li><a href="<s:url value="/message"/>">Set Message</a></li>
							   <li><a href="<s:url value="/deletedBets"/>">Deleted Bet</a></li>
							   <li><a href="<s:url value="/deleteBets"/>">Delete Bet</a></li>
							   <li><a href="<s:url value="/minMaxBetSetAmount"/>">Min Max Bet Amont</a></li>
								<li><a href="<s:url value="/powerUser"/>">Power User</a></li>
								<li><a href="<s:url value="/powerUserList"/>">Power User List</a></li>
								<li><a href="<s:url value="/addManualMatch"/>">addManualMatch</a></li>
								<li><a href="<s:url value="changePartnership"/>">change Partnership</a></li>
								<li><a href="<s:url value="matchMessage"/>">match Message</a></li>
								<li><a href="<s:url value="userBetCount"/>">userBetCheck</a></li>
						  </ul>
					   </div>
					</li>
					 </s:if>
					 
					 
					 
					   <s:if test="${user.usertype==6}">
					 <li class="drop-link-one"><a href="#">Settings<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-one" >
						   <ul>
							  
								<li><a href="<s:url value="/addManualMatch"/>">addManualMatch</a></li>
						  </ul>
					   </div>
					</li>
					 </s:if>
					 
					 <s:if test="${user.usertype ne 6 }">
					  <li class="drop-link-two"><a href="#">Report<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-two" >
						   <ul>
							   <li><a href="<s:url value="/statements"/>">Account Statement</a></li>
							   <li><a href="<s:url value="/settlementlist"/>">Settlement List</a></li>
							   <li><a href="<s:url value="/currentBets"/>">Current Bet</a></li>
								
								  <li><a href="<s:url value="/sportDetail"/>">Profit & Loss</a></li>
								  <li><a href="<s:url value="/casinoPnL"/>">Casino Profit & Loss</a></li>
								  <li><a href="<s:url value="/casinoProfitLoss"/>">Casino Detail</a></li>
								  <li><a href="<s:url value="/casinoresult"/>">Casino Result Report</a></li>
								  <li><a href="<s:url value="/betHistory"/>">Bet History</a></li>
								 <li><a href="<s:url value="/lenadena"/>">Lena Dena</a></li>
								   <li><a href="<s:url value="/closedaccount"/>">Closed Account</a></li>
						   </ul>
					   </div>	
				   </li>
				   </s:if>
				   
				 
			  
				<s:if test="${user.usertype==6}">
					<li class="drop-link-two"><a href="#">Result<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-two" >
						   <ul>
							   <li><a href="<s:url value="/openMarket"/>">Match Result</a></li>
								 <li><a href="<s:url value="/openFancy"/>">Fancy Result</a></li>
								 <li><a href="<s:url value="/finishedMatchFancy"/>">Fancy Result New</a></li>
							   
						   </ul>
					   </div>
				   </li>
				   </s:if>
				   
				   <s:if test="${user.usertype==4}">
					<li class="drop-link-two"><a href="#">Result<i class="fa fa-caret-down text-white" ></i></a>
					   <div class="dropdown-content-two" >
						   <ul>
							   <li><a href="<s:url value="/openMarket"/>">Match Result</a></li>
								 <li><a href="<s:url value="/openFancy"/>">Fancy Result</a></li>
								 
						   </ul>
					   </div>
				   </li>
				   </s:if>
			  
				 </ul>
				  
			 </div>
	 </div>		
	<!--Mobile Items-->
	<div class="navbar navbar-link mobile-items">
		<ul>
			<s:if test="${user.usertype ne 6  }">
			  <li><a href="<s:url value="/userList"/>">Clients</a></li>
			</s:if>
			<li><a href=" <s:url value="/netExposure"/>">Markets</a></li>
		</ul>
	</div>

	 </div>
	 <div class="nav-right">
		<div class="right-link">
		<div class="toggle-btn btn-rightmenu ml-2 mt-0 pull-right">
		   <span class="t-one "></span>
		   <span class="t-two "></span>
		   <span class="t-three "></span>
		 </div>
		 <div class="toggle-play">
		   <li class="toggle-link"> <a href="#"> ${user.userid}<i class="fa fa-caret-down text-white" ></i></a>
			 <div class="toggle-content">
			   <a href="<s:url value="changePassword"/>"  >Change Password</a>
				 <s:if test="${user.usertype==4}">
			   <a href="javascript:void(0)" onclick="chipDepositeModal('${user.id}','${user.userid}')">Deposite Chip</a>
			   </s:if>
			   <a href="avascript:void(0)" onclick="selfLogoutAdmin('${user.id}')">Logout</a>
		   </div>
		   </li>
		  
		 </div>
		 
		 
		 
		 
		 <!--<div class="clint-box">
			 <input type="text" placeholder="All Clients">
			 <i class="fa fa-search-plus" onclick=" alertFunction()"></i>
		 </div>-->
		 </div>
	 </div> 
</div>
 <input type="hidden" id="parentId">
<input type="hidden" id="childId">
<input type="hidden" id="userName">
<input type="hidden" id="userType">
<!--  <marquee behavior="" direction="" id="message_marquee" class="text-white" ></marquee> -->
 </div>

<!-- end header  --> 

<!-- side menubar -->
<div class="side-menubar" id="side-menubar">
      <h2>Sports</h2>
      <div class="cross-btn" >
        <i class="fa fa-times" ></i>
      </div>
      <div class="side-navbar" id="sidebarmenu">
      
      </div>
      <jsp:include page="chanepasswordmodal.jsp" />
      <jsp:include page="selfChipDepositeModal.jsp" />
    </div>
    <!-- end sidemenubar -->
    
       <script>

      var  userType='${user.usertype}';
    //  console.log("logo==>"+${appName})
     
      $("#logo").html('${appName}')
       $(document).ready(function(){
	//	leftMenu();
	
	 $(".fa-arrow-circle-o-down").click(function(){
					          $(".toggle-container").slideToggle(400);
					        });	

	  $(".toggle-link").click(function(){
	        $(".toggle-content").slideToggle(400);
	      });
		getMessage();
		console.log("sjak")
		$(".toggle-btn.btn-rightmenu").click(function(){
			$(".responsive-menu").toggleClass("active");
			$(this).toggleClass("active");
			$("#side-menubar").removeClass("active");
			$(".toggle-btn.btn-left").removeClass("active"); 
		});

		$(".cross-menu").click(function(){
			$(".responsive-menu").removeClass('active');
			$(".toggle-btn.btn-rightmenu").removeClass('active');
		});

		$(".d-cancel-btn").click(function(){
			$(".self_chip_deposite_Popup").removeClass('active');
		});
		$(".back-d-btn").click(function(){
			$(".self_chip_deposite_Popup").removeClass('active');
		});
		headerData();
		//findSportByTypeAndStatus();
		
       });
      
        function changePasswordModal(childId,parentId){
            
            
       	    $("#childId").val(childId);
       	    $("#parentId").val(parentId);
    		$(".change_password_Popup").addClass("active");
            $(".d-cancel-btn").click(function(){
              $(".change_password_Popup").removeClass("active");
            });
       }

   function selfchangePasswordModal(childId,parentId){
            
            
       	    $("#childId").val(childId);
       	    $("#parentId").val(parentId);
    		$(".self_change_password_Popup").addClass("active");
            $(".d-cancel-btn").click(function(){
              $(".self_change_password_Popup").removeClass("active");
            });
       }
        
        function selfLogoutAdmin(id) {
       		$.ajax({
       			url:'<s:url value="/api/logout?id="/>'+id,
      			success: function(jsondata, textStatus, xhr) {
      				if(xhr.status == 200){
     	  				location.replace("<s:url value="/admin"/>")	  					
      				}
        		}
        	});
        }
        function getMessage(){
     	   $.ajax({
  				type:'GET',
  				url:'<s:url value="/api/getMessage"/>?msgtype=O',
  				contentType:"application/json; charset=utf-8",
  				success: function(jsondata, textStatus, xhr) 
  				{
  					if(xhr.status == 200){
  					
  						
  						$("#message_marquee").append('<i style="font-weight: normal;">'+jsondata.message+' </i>');
  						  
  				}
  				},
  				complete: function(jsondata,textStatus,xhr) {
  					//showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  					 
  			    }
  			});
  		} 
        
  function chipDepositeModal(childId,parentId){
            
            
       	    $("#childId").val(childId);
       	    $("#parentId").val(parentId);
    		$(".self_chip_deposite_Popup").addClass("active");
            $(".d-cancel-btn").click(function(){
              $(".self_chip_deposite_Popup").removeClass("active");
            });
            headerData();
       }
  
  function depositeChipSelf(){

	  var $valid = $("#self_chip_deposite").valid();
		if($valid){
			  var data ={
		 			  userid:'${user.userid}',
		 			  parentPassword :$("#adminPassword").val(),
		 			 chips :$("#newadminBalance").val()
		 	  }
		    	$.ajax({
						type:'POST',
						url:'<s:url value="/api/addChipToAdmin"/>',
						data: JSON.stringify(data),
						dataType: "json",
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) 
						{
						 	showMessage(jsondata.message,jsondata.type,jsondata.title);
							 $(".self_chip_deposite_Popup").removeClass("active");
							  document.getElementById("self_chip_deposite").reset();
							  headerData();
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
							
					    }
				});
			}
	  	
	 	
   }
  
  function headerData(){
  	$.ajax({
			type:'GET',
			url:'<s:url value="/api/headerData"/>/'+${user.parentid}+"/"+${user.id}+"/",
			success: function(jsondata, textStatus, xhr) {
			
				 console.log(jsondata.creditRef)
				 $("#creditref").html(jsondata.creditRef)
				  $("#totalmassterbalance").html(jsondata.totalMasterBalance)
				   $("#totalmassterbalance").html(jsondata.totalMasterBalance)
				    $("#availablebalance").html(jsondata.availableBalance)
				     $("#uplinebalance").html(jsondata.uplineAmount)
				     //  $("#downlineavailabebal").html(jsondata.downlineavailableBalance)
				        $("#downlinecreditref").html(jsondata.downlinecreditRef)
				         $("#downlineWithProfitLoss").html(jsondata.downlineWithProfitLoss)
				          $("#myProfitLoss").html(jsondata.myProfitLoss);
				            $("#availableBalanceWithPnl").html(jsondata.availableBalanceWithPnl);
				 			$("#downlinecreditRef").html(jsondata.downlinecreditRef);
				 			$("#avaltodistribute").html(',(Aval Balance :'+jsondata.availableBalance+')');
				 			$("#currentadminBalance").val(jsondata.availableBalanceWithPnl);
				 
			}
  	
  	});
  }

  function logoutUser()
  { 
 		$.ajax({
 			url:'<s:url value="/api/checklogoutUser"/>',
			success: function(jsondata, textStatus, xhr) {

				if(xhr.status == 200){
					location.replace("<s:url value="/"/>")	  					
				}
				
  		}
  	});
  } 
  
  function checklogoutUserNew()
  { 
 		$.ajax({
 			url:'<s:url value="/api/checklogoutUserNew/${user.id}"/>',
			success: function(jsondata, textStatus, xhr) {

				if(xhr.status == 204){
					location.replace("<s:url value="/"/>")	  					
				}
				
  		}
  	});
  } 

  function findSportByTypeAndStatus() { 
 		$.ajax({
 			url:'<s:url value="/api/findSportByTypeAndStatus"/>',
			success: function(jsondata, textStatus, xhr) {

				$.each(jsondata, function(key,value){
					 $('#livemarket').append('<li><a href="<s:url value="/casino/'+value.sportId+'"/>">'+value.name+'</a></li>');
					 
				 });
				
  		}
  	});
  } 
  setInterval(function (){ 
   if(userType ==6){
	//  logoutUser();
	 }
 
}, 10000);

/*
  setInterval(function (){ 
	   if(userType !=6){
		  checklogoutUserNew();
		 }
	 
	}, 10000);
*/
  function getAddminBet(){		    	
	    
		$
		$.ajax({
			type:'GET',
			url:betlist_base_url+"/api/getAddminBetCasino/${eventid}/${user.usertype}/${user.id}/",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{ 
	      			
	      			    $("#matchedCount").val(jsondata.matchedcount); 
	      		 	    $("#matched-betsCount").text("Matched-Bets("+jsondata.matchedcount+")");
	      			
	      			   $("#match-bet").html('')
	      			   var source   = $("#matchedBetsHandler").html();
	      		   	   var template = Handlebars.compile(source);
	      			   $("#match-bet").append( template({data:jsondata.matched}) );


				  	    
				}
		},
		complete: function(jsondata,textStatus,xhr) {
		
	    } 
	});
	}	


  
  
       </script>
       
        <input type ="hidden" value="" id="match_Id">
     