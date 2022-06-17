<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Balance Information</h1>
						<section class="menu-content">
							<table class="generic">
								<tbody>
									 <tr>
										<th>Market Exposure</th>
										<td id="matchOddsExpo" class="numeric ng-binding green" >0.00</td>
									</tr>
									 <tr>
										<th>Fancy Exposure</th>
										<td id="fancyExpo" class="numeric red ng-binding">0.00</td>
									</tr>
									<tr>
										<th>Net Exposure</th>
										<td id="totalExpo" class="numeric ng-binding green" >0.00</td>
									</tr>		                
									<tr>
										<th>Balance Up</th>
										<td id="balanceup" class="numeric green" >0.00</td>
									</tr>
									<tr>
										<th>Total Commission</th>
										<td id="totalComm" class="numeric" >0.00</td>
									</tr>
									<tr>
										<th>Total P&amp;L</th>
										<td id="totalPnl" class="numeric green" >0.00</td>
									</tr>
									<tr class="-qa-total-downline">
										<th id="creditTitle">Total credit given to </th>
										<td class="numeric red" id="creditgiven_dashboard">0.00</td>
									</tr>
									<tr>
										<th>Credit Limit</th>
										<td class="numeric green" id="primarybalance">0.00</td>
									</tr>
									<tr>
										<th>Available Credit</th>
										<td class="numeric green" id="secondarybalance_dashboard" >0.00</td>
									</tr>									
									 <tr>
										<th>Balance</th>
										<td class="numeric green" id="balance_dashboard">0.00</td>
									</tr>
									<tr id="settlement">
										<th>Settlement</th>
										<td class="numeric green" id="settlement_dashboard">0.00</td>
									</tr>
								</tbody>
							</table>
						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    
    <script>
    
    $(document).ready(function(){

    	getDashboardDetails();
    	if(${user.usertype==0}){
    		$("#creditTitle").text("Given To SuperAgent")
    	}else if(${user.usertype==1}){
    		$("#creditTitle").text("Given To Agent")
    	}else if(${user.usertype==4}){
    		$("#creditTitle").text("Given To Subadmin")
    	}else if(${user.usertype==5}){
    		$("#creditTitle").text("Given To SuperStockage")
    	}else{
    		$("#creditTitle").text("Given To User")
    	}

    	masterList();
    });
    
    function getDashboardDetails()
    {			
    		$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/getUserAccountDetails"/>',
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{ 			    
    					$("#matchOddsExpo").text(jsondata.matchodds);
    					$("#fancyExpo").text(jsondata.fancy);
    					$("#totalExpo").text(jsondata.totalExpo);
    					$("#primarybalance").text(jsondata.primarybalance);
    					$("#secondarybalance_dashboard").text(jsondata.secondarybalance);
    					$("#creditgiven_dashboard").text(jsondata.creditgiven);
    					$("#balance_dashboard").text(jsondata.secondarybalance);
    					if(jsondata.pnl<0.0){
    					$("#totalPnl").text(jsondata.pnl).removeClass("green").addClass("red");
    					}else{
    					$("#totalPnl").text(jsondata.pnl).removeClass("red").addClass("green");;
    					}
    					$("#totalComm").text(jsondata.commission);
    				}
    		}
    	});
    }
    
    function masterList()
    {
    	var id = '${user.parentid}';
    	$.ajax({
			type:'GET',
			url:'<s:url value="/api/user/${user.parentid}/"/>'+${user.usertype}+"?active=true",
			success: function(jsondata, textStatus, xhr) {
			{	
				if(xhr.status==200){
					var obj = jsondata.data;
					for(var i =0;i<obj.length;i++){
						
						if('${user.id}'==obj[i].id){
							var val = obj[i].winlossamount;
								 $("#settlement_dashboard").html('');
								 if(val < 0){
									 $("#settlement_dashboard").html(val).removeClass("green").addClass("red");
								 }else{
									 $("#settlement_dashboard").html(val).removeClass("red").addClass("green");
								 }											
						}
	
				}
				}
			}
			
		}
	});
   }
    </script>