<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="sessionpluminshandler" type="text/x-handlebars-template">
 <a href="<s:url value="/sportdetailSessionPlusMinus/${matchid}"/>">Show In Detail</a>				
<div class="table-responsive">

						<table class="apl-table table-net-exposure transitionable-table-wrap">
									<thead>
										<tr>
											<th>Session Name</th>
											<th>Declare Run</th>    
											<th>SESSION PLUS MINUS</th>                  
											<th>Action</th>
										</tr>
									</thead>
							<tbody>
{{#each data.sessionamount}}

											<tr>
											<td>{{fancyname}}</td>
											<td>{{result}}</td> 
											{{#if_small pnlamount 0}}
								<td class="red">{{pnlamount}}</td>
									{{else}}{{#if_eq pnlamount 0}}
										<td>{{pnlamount}}</td>
									{{else}}
										<td class="green">{{pnlamount}}</td> 
									{{/if_eq}}
								{{/if_small}}               
								
								<td> 
								<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick=showBet("{{fancyid}}")>Show Bet</a> 
								</td>
											</tr>
{{/each}}
               
											<tr style="background-color:wheat">
				                            <td></td>                  
											<td style="color:black">Total Amount</td>

										
		                  {{#if_small data.finalamount.pnlamount 0}}
								<td class="red">{{data.finalamount.pnlamount}}</td>
									{{else}}{{#if_eq data.finalamount.pnlamount 0}}
										<td>{{data.finalamount.pnlamount}}</td>
									{{else}}
										<td class="green">{{data.finalamount.pnlamount}}</td> 
									{{/if_eq}}
								{{/if_small}}    
							<td style="color:black"></td>
        </tr>
				
							</tbody>

						</table>
</div>

</script>


<script id="resultBetshandler" type="text/x-handlebars-template">
<div class="table-responsive">
{{#if_eq data.0.fancyname null}}
								<table class="table common-table">
									<thead>
										<tr>
											<th>user_name</th>
											<th>Date</th>
											<th>Match Name</th>
											<th>Market Name</th>
											<th>Selection Name</th>
											<th>Mode</th>
											<th>Odds</th>
											<th>Stack</th>
											<th>P&L</th>
											
										{{#if_eq ${user.usertype} 4}}
											 <th>M</th><th>SM</th><th>SBA</th><th>A</th><th>Upline</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th><th>A Share</th><th>Upline Share</th>
											{{/if_eq}}	
									    {{#if_eq ${user.usertype} 5}}
											
											 <th>M</th><th>SM</th><th>SBA</th><th>A</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th><th>A Share</th>	
											{{/if_eq}}	
										{{#if_eq ${user.usertype} 0 }}
											
											<th>M</th><th>SM</th><th>SBA</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th>

										{{/if_eq}}	

										{{#if_eq ${user.usertype} 1 }}
											
											<th>M</th><th>SM</th>
											 <th>M Share</th><th>SM Share</th>
										{{/if_eq}}	
										{{#if_eq ${user.usertype} 2 }}
											
											<th>M</th>			
											 <th>M Share</th>
										{{/if_eq}}
										</tr>
									</thead>
{{#each data}}
									<tbody id="resultbetsList">
{{#if_sm netpnl 0.00}}
									<tr style="background-color: red;">
{{else}}
									<tr style="background-color: green;">
{{/if_sm}}  
											<td>{{username}}</td>
											<td>{{date}}</td>
											<td class="descol">{{matchname}}</td>
											<td>{{marketname}}</td>
											<td>{{selectionname}}</td>
{{#if_eq isback true}}
											<td><strong>Back</strong></td>	
{{else}}			
											<td><strong>Lay</strong></td>	
{{/if_eq}}
											<td>{{odds}}</td>
											<td>{{stake}}</td>
											<td>{{netpnl}}</td>
{{#if_eq ${user.usertype} 4}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td><td>{{adminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td><td>{{admin}}</td>
											{{/if_eq}}
										
											 {{#if_eq ${user.usertype} 5}}
											
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td>
											{{/if_eq}}

											 {{#if_eq ${user.usertype} 0}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 1}}
											<td>{{dealername}}</td><td>{{mastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 1}}
											<td>{{dealername}}</td>
											<td>{{dealer}}</td>
											{{/if_eq}}
										</tr>
									</tbody>
{{/each}}
								</table>
{{else}}
								<table class="table common-table">
									<thead>
										<tr>
											<th>user_name</th>
											<th>Date</th>
											<th>Fancy Name</th>
											<th>Mode</th>
											<th>Odds</th>
											<th>stake</th>
											<th>Volume</th>
											<th>pnl</th>
											<th>win / Loss</th>
										{{#if_eq ${user.usertype} 4}}
												 <th>M</th><th>SM</th><th>SBA</th><th>A</th><th>Upline</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th><th>A Share</th><th>Upline Share</th>
											 
											 
											{{/if_eq}}	
									    {{#if_eq ${user.usertype} 5}}
											  <th>M</th><th>SM</th><th>SBA</th><th>A</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th><th>A Share</th>
											{{/if_eq}}	
										{{#if_eq ${user.usertype} 0 }}
											 <th>M</th><th>SM</th><th>SBA</th>
											 <th>M Share</th><th>SM Share</th><th>SBA Share</th>
											 
										{{/if_eq}}	

										{{#if_eq ${user.usertype} 1 }}
											 <th>M</th><th>SM</th>
											 <th>M Share</th><th>SM Share</th>
										{{/if_eq}}	
										{{#if_eq ${user.usertype} 2 }}
												<th>M</th>			
											 <th>M Share</th>
										{{/if_eq}}
										</tr>
									</thead>
{{#each data}}
									<tbody>
{{#if_sm netpnl 0.00}}
									<tr style="background-color: red;">
{{else}}
									<tr style="background-color: green;">
{{/if_sm}}  
											<td>{{username}}</td>
											<td>{{date}}</td>
											<td>{{fancyname}} :: Result : {{result}}</td>
{{#if_eq isback true}}
											<td><strong>Yes</strong></td>	
{{else}}			
											<td><strong>No</strong></td>	
{{/if_eq}}
											<td>{{odds}}</td>
											<td>{{stake}}</td>
											<td>{{pricevalue}}</td>
											<td>{{netpnl}}</td>
{{#if_sm netpnl 0.00}}
											<td>Loss</td>
{{else}}				
											<td>Win</td>
											{{/if_sm}}
 {{#if_eq ${user.usertype} 4}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td><td>{{adminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td><td>{{admin}}</td>
											{{/if_eq}}
										
											 {{#if_eq ${user.usertype} 5}}
											
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td><td>{{subadminname}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td><td>{{subadmin}}</td>
											{{/if_eq}}

											 {{#if_eq ${user.usertype} 0}}
											<td>{{dealername}}</td><td>{{mastername}}</td><td>{{supermastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td><td>{{supermaster}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 1}}
											<td>{{dealername}}</td><td>{{mastername}}</td>
											<td>{{dealer}}</td><td>{{master}}</td>
											{{/if_eq}}

											{{#if_eq ${user.usertype} 1}}
											<td>{{dealername}}</td>
											<td>{{dealer}}</td>
											{{/if_eq}}
										</tr>
									</tbody>
{{/each}}
								</table>
{{/if_eq}}
</div>
</script>

<script id="subAdminhandler" type="text/x-handlebars-template">

{{#each data}}
									<tr>
										<td><a class="badge selected" onclick="getSupermaster('{{subadminname}}','{{subadminid}}')">{{subadminname}}</a></td>

								{{#if_sm uplinepnl 0.00}}
										<td class="red">{{uplinepnl}}</td>
								{{else}} {{#if_eq uplinepnl 0.0}}
										<td>{{uplinepnl}}</td>
								{{else}}
										<td class="green">{{uplinepnl}}</td>
								{{/if_eq}}{{/if_sm}}
{{#if_sm netpnl 0.00}}
										<td class="red">{{netpnl}}</td>
{{else}} {{#if_eq netpnl 0.0}}
										<td>{{netpnl}}</td>
{{else}}
										<td class="green">{{netpnl}}</td>
{{/if_eq}}{{/if_sm}}
										
									
									{{#if_sm commission 0.00}}
										<td class="red">{{commission}}</td>
										{{else}} {{#if_eq commission 0.0}}
										<td>{{commission}}</td>
										{{else}}
										<td class="green">{{commission}}</td>
										{{/if_eq}}{{/if_sm}}

									{{#if_sm subadminCommission 0.00}}
										<td class="red">{{subadminCommission}}</td>
										{{else}} {{#if_eq subadminCommission 0.0}}
										<td>{{subadminCommission}}</td>
										{{else}}
										<td class="green">{{subadminCommission}}</td>
										{{/if_eq}}{{/if_sm}}
									</tr>
{{/each}}

</script>

<script id="superMasterhandler" type="text/x-handlebars-template">

{{#each data}}
									<tr>
										<td><a class="badge selected" onclick="getMaster('{{supermastername}}')">{{supermastername}}</a></td>
									
								{{#if_sm uplinepnl 0.00}}
										<td class="red">{{uplinepnl}}</td>
								{{else}} {{#if_eq uplinepnl 0.0}}
										<td>{{uplinepnl}}</td>
								{{else}}
										<td class="green">{{uplinepnl}}</td>
								{{/if_eq}}{{/if_sm}}


{{#if_sm netpnl 0.00}}
										<td class="red">{{netpnl}}</td>
{{else}} {{#if_eq netpnl 0.0}}
										<td>{{netpnl}}</td>
{{else}}
										<td class="green">{{netpnl}}</td>
{{/if_eq}}{{/if_sm}}
										
										{{#if_sm subadminCommission 0.00}}
										<td class="red">{{subadminCommission}}</td>
										{{else}} {{#if_eq subadminCommission 0.0}}
										<td>{{subadminCommission}}</td>
										{{else}}
										<td class="green">{{subadminCommission}}</td>
										{{/if_eq}}{{/if_sm}}
										{{#if_sm supermasterCommission 0.00}}
										<td class="red">{{supermasterCommission}}</td>
										{{else}} {{#if_eq supermasterCommission 0.0}}
										<td>{{supermasterCommission}}</td>
										{{else}}
										<td class="green">{{supermasterCommission}}</td>
										{{/if_eq}}{{/if_sm}}

									</tr>
{{/each}}

</script>
<script id="masterhandler" type="text/x-handlebars-template">

{{#each data}}
									<tr>
										<td><a class="badge selected" onclick="getDealer('{{mastername}}')">{{mastername}}</a></td>
							{{#if_sm uplinepnl 0.00}}
								<td class="red">{{uplinepnl}}</td>
								{{else}} {{#if_eq uplinepnl 0.0}}
										<td>{{uplinepnl}}</td>
								{{else}}
										<td class="green">{{uplinepnl}}</td>
								{{/if_eq}}{{/if_sm}}
{{#if_sm netpnl 0.00}}
										<td class="red">{{netpnl}}</td>
{{else}} {{#if_eq netpnl 0.0}}
										<td>{{netpnl}}</td>
{{else}}
										<td class="green">{{netpnl}}</td>
{{/if_eq}}{{/if_sm}}

										{{#if_sm supermasterCommission 0.00}}
										<td class="red">{{supermasterCommission}}</td>
										{{else}} {{#if_eq supermasterCommission 0.0}}
										<td>{{supermasterCommission}}</td>
										{{else}}
										<td class="green">{{supermasterCommission}}</td>
										{{/if_eq}}{{/if_sm}}										

										{{#if_sm masterCommission 0.00}}
										<td class="red">{{masterCommission}}</td>
										{{else}} {{#if_eq masterCommission 0.0}}
										<td>{{masterCommission}}</td>
										{{else}}
										<td class="green">{{masterCommission}}</td>
										{{/if_eq}}{{/if_sm}}
									</tr>
{{/each}}

</script>

<script id="dealerhandler" type="text/x-handlebars-template">

{{#each data}}
									<tr>
										<td><a class="badge selected" onclick="getuser('{{dealername}}')">{{dealername}}</a></td>

{{#if_sm uplinepnl 0.00}}
								<td class="red">{{uplinepnl}}</td>
								{{else}} {{#if_eq uplinepnl 0.0}}
										<td>{{uplinepnl}}</td>
								{{else}}
										<td class="green">{{uplinepnl}}</td>
								{{/if_eq}}{{/if_sm}}
{{#if_sm netpnl 0.00}}
										<td class="red">{{netpnl}}</td>
{{else}} {{#if_eq netpnl 0.0}}
										<td>{{netpnl}}</td>
{{else}}
										<td class="green">{{netpnl}}</td>
{{/if_eq}}{{/if_sm}}
									

{{#if_sm masterCommission 0.00}}
										<td class="red">{{masterCommission}}</td>
										{{else}} {{#if_eq masterCommission 0.0}}
										<td>{{masterCommission}}</td>
										{{else}}
										<td class="green">{{masterCommission}}</td>
										{{/if_eq}}{{/if_sm}}

	{{#if_sm dealerCommission 0.00}}
										<td class="red">{{dealerCommission}}</td>
										{{else}} {{#if_eq dealerCommission 0.0}}
										<td>{{dealerCommission}}</td>
										{{else}}
										<td class="green">{{dealerCommission}}</td>
										{{/if_eq}}{{/if_sm}}
									</tr>
{{/each}}

</script>

<script id="userhandler" type="text/x-handlebars-template">

{{#each data}}
	<tr>
		<td>{{username}}</td>

		{{#if_sm uplinepnl 0.00}}
			<td class="red">{{uplinepnl}}</td>
		{{else}} {{#if_eq uplinepnl 0.0}}
			<td>{{uplinepnl}}</td>
		{{else}}
			<td class="green">{{uplinepnl}}</td>
		{{/if_eq}}{{/if_sm}}

		{{#if_sm netpnl 0.00}}
			<td class="red">{{netpnl}}</td>
		{{else}} {{#if_eq netpnl 0.0}}
			<td>{{netpnl}}</td>
		{{else}}
			<td class="green">{{netpnl}}</td>
		{{/if_eq}}{{/if_sm}}

		{{#if_sm userCommision 0.00}}
			<td class="red">{{userCommision}}</td>
		{{else}} {{#if_eq userCommision 0.0}}
			<td>{{userCommision}}</td>
		{{else}}
			<td class="green">{{userCommision}}</td>
		{{/if_eq}}{{/if_sm}}
	</tr>								
{{/each}}

</script>



<div id="page-wrapper">	
	<div class="inner-wrap live-bets-wrap">
				
						<section class="menu-content">
							
								
							<div class="table-reponsive" id="sessionplumins">
							</div>
							<div class="mobileDiv overflow-y-table" id="resultBets"> </div>
							
						</section>				
				<!-- /.row --> 
            </div> 
            </div>    
            
    <script>
    
    $(document).ready(function(){
    	sessionPlusMinus();
    	
    });
    function sessionPlusMinus(){
    	
    	var eventid =${eventid};
    	$("#page-wrapper").append('<div class="loader"></div>')
    	 $.ajax({
    			type:'GET',
    			url:'<s:url value="/api/sessionPlusMinus"/>?eventid='+eventid,
    			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{
    					
    					$("#sessionplumins").html('');
						
    					  var source   = $("#sessionpluminshandler").html();
    			    	  var template = Handlebars.compile(source);
    			    	  
    				      $("#sessionplumins").append( template({data:jsondata}) );
    				   	   $(".loader").fadeOut("slow");
    				    
    				      
    			
    		}
    	}
    });
    }
    function showBet(fancyid){
    	$("#resultBets").append('<div class="loader"></div>')
		
		
    		$.ajax({
    			type:'GET',
    			url:'<s:url value="/api/betByFancyId?fancyid="/>'+fancyid,
    			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{
    				$(".loader").fadeOut("slow");
    					$("#resultBets").html('');
    					var result=jsondata;
    					  var source   = $("#resultBetshandler").html();
    			    	  var template = Handlebars.compile(source);
    				      $("#resultBets").append( template({data:jsondata}) );

   				    	
				}else{
				$(".loader").fadeOut("slow");
				$("#resultBets").html('');
    			
				}

    		}
    	});
    
    }

    </script>
    
    <jsp:include page="handlebars.jsp" />  