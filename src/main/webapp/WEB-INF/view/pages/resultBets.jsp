<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
            <div class="inner-wrap set-fancy-bet resultBetPage">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Fancy Bets</h1>
						<section class="menu-content">
												  <input id="myInput" type="text" placeholder="Search..">
							<div class="row space-row" style="overflow: visible">
							<div class="col-sm-4 col-space" id="adminpnldiv">
							<div class="mobileTableScroll">
								<table class="table common-table">
									<thead>
										<tr>
											<th colspan="5" class="text-center plhead">Admin P&amp;L</th>
										</tr>
										<tr>
											<th>A/C Name</th>
											<th>Up LineP&amp;L</th>
											<th>P&amp;L</th>
											<th>Com Upl</th>
											<th>Com Dwnl</th>
										</tr>
									</thead>
								  <tbody id="subadminlist">

								  </tbody>
							  </table>
							  </div>
							</div>
							<div class="col-sm-4 col-space" id="subadminpnldiv">
							<div class="mobileTableScroll">
								<table class="table common-table">
									<thead>
										<tr>
											<th colspan="5" class="text-center plhead">Sub Admin P&amp;L</th>
										</tr>
										<tr>
											<th>A/C Name</th>
											<th>Up LineP&amp;L</th>
											<th>P&amp;L</th>
											<th>Com Upl</th>
											<th>Com Dwnl</th>
										</tr>
									</thead>
								  <tbody id="supermasterlist">

								  </tbody>
							  </table>
							  </div>
							</div>
							<div class="col-sm-4 col-space" id="supermasterpnldiv">
							<div class="mobileTableScroll">
								<table class="table common-table">
									<thead>
										<tr>
											<th colspan="5" class="text-center plhead">Super Master P&amp;L</th>
										</tr>
										<tr>
											<th>A/C Name</th>
											<th>Up LineP&amp;L</th>
											<th>P&amp;L</th>
											<th>Com Upl</th>
											<th>Com Dwnl</th>
										</tr>
									</thead>
								  <tbody id="masterlist">

								  </tbody>
							  </table>
							  </div>
							</div>
							</div>
						<div class="row space-row" style="overflow: visible">
							<div class="col-sm-6 col-space" id="masterpnldiv">
							<div class="mobileTableScroll">
							  <table class="table common-table">
								<thead>
								  <tr>
									<th colspan="5" class="text-center plhead">Master P&amp;L</th>
								  </tr>
								  <tr>
									<th>A/C Name</th>
									<th>Up LineP&amp;L</th>
									<th>P&amp;L</th>
									<th>Com Upl</th>
									<th>Com Dwnl</th>
								  </tr>
								</thead>
							  <tbody id="dealerbody">
							  </tbody>
							  </table>
							  </div>
							</div>
							<div class="col-sm-6 col-space" id="userpnldiv">
							<div class="mobileTableScroll">
							  <table class="table common-table">
								<thead>
								  <tr>
									<th colspan="5" class="text-center plhead">User P&amp;L</th>
								  </tr>
								  <tr>
									<th>A/C Name</th>
									<th>Up LineP&L</th>
									<th>P&amp;L</th>
									<th>Com Upl</th>
									<th>Com Dwnl</th>
								  </tr>
								</thead>
								  <tbody id="userbody">
								  
								  </tbody>
							  </table>
							  </div>
							</div>
						  </div>
							<div class="mobileDiv overflow-y-table" id="resultBets"> </div>
							<div class="additional-filters">
							<form>
								<div class="panel-body text-center">
									<button class="apl-btn" id="nextBets" type="button" onclick="getBetResults('next')">NEXT</button>
									<button class="apl-btn" id="previousBets" type="button" onclick="getBetResults('previous')">PREVIOUS</button>
								</div>
							</form>							
						</div>
						</section>		
						 <input type="hidden" value="" id="subadminname">
          				 <input type="hidden" value="" id="supermastername">
          				 <input type="hidden" value="" id="mastername">
           				 <input type="hidden" value="" id="dealername">				
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>

        <script>

        var page =0;
        $("#previousBets").hide();
        $("#nextBets").hide();

        $(document).ready(function(){
        	if(${user.usertype}==0){
            	getMaster("${user.username}");     
            	$("#adminpnldiv").hide();
            	$("#subadminpnldiv").hide();
        		$("#supermasterpnldiv").removeClass("col-sm-4");
        		$("#supermasterpnldiv").addClass("col-sm-12");
        	}else if(${user.usertype}==1){
        		getDealer("${user.username}");
        		$("#adminpnldiv").hide();
            	$("#subadminpnldiv").hide();
            	$("#supermasterpnldiv").hide();
            	$("#masterpnldiv").removeClass("col-sm-4");
            	$("#userpnldiv").removeClass("col-sm-4");
        		$("#masterpnldiv").addClass("col-sm-6");
        		$("#userpnldiv").addClass("col-sm-6");
        	}else if(${user.usertype}==4){
        		getSubadmin();
        	}else if(${user.usertype}==5){
        		getSupermaster("${user.username}","${user.id}");
        		$("#adminpnldiv").hide();
        		$("#subadminpnldiv").removeClass("col-sm-4");
        		$("#subadminpnldiv").addClass("col-sm-6");
        		$("#supermasterpnldiv").removeClass("col-sm-4");
        		$("#supermasterpnldiv").addClass("col-sm-6");
        		
        	}else{
        		getuser("${user.username}");
        		$("#adminpnldiv").hide();
        		$("#subadminpnldiv").hide();
            	$("#supermasterpnldiv").hide();
            	$("#masterpnldiv").hide();
            	$("#userpnldiv").removeClass("col-sm-4");
            	$("#userpnldiv").addClass("col-sm-12");
        	}
        	
        	getBetResults();
        	
        	$("#myInput").on("keyup", function() {
        	    var value = $(this).val().toLowerCase();
        	    $("#resultbetsList tr").filter(function() {
        	    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        	      });
        	    $("#userbody tr").filter(function() {
            	    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
            	      });

        	  });
        });
        
        function getBetResults(action)
        {		    
        	$("#resultBets").append('<div class="loader"></div>')
    		if(action == "next"){
        		page = page+1;    		
    		}else if(action == "previous"){
        		page = page-1;
    		}
    		
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getBetResults?resultid="/>'+"${resultid}"+"&page="+page+"&count=200",
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        				$(".loader").fadeOut("slow");
        					$("#resultBets").html('');
        					var result=jsondata;
        					  var source   = $("#resultBetshandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#resultBets").append( template({data:result}) );

	   				    	  if(jsondata.length==200){
	   				    		  if(page!=0){
			   				    		$("#previousBets").show();	   				    				   				    			  
	   				    		  }
	   				    			$("#nextBets").show();
	   				    		}else{
	   				    			$("#nextBets").hide();
	   				    			if(page>0){
			   				    		$("#previousBets").show();		   				    				
	   				    			}else{
			   				    		$("#previousBets").hide();	   				    			
	   				    			}
	   				    		}
 				}else{
 				$(".loader").fadeOut("slow");
					$("#resultBets").html('');
	    			if(page!=0){
				    		$("#previousBets").show();		   				    				
		    			}else{
   				    		$("#previousBets").hide();	   				    			
		    			}
		    			$("#nextBets").hide();
 				}

        		}
        	});
        }

        function getMaster(supermastername)
        {		
        	$("#supermastername").val(supermastername);
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getMasterComm?resultid="/>'+"${resultid}"+"&supermastername="+supermastername+"&subadminname="+$("#subadminname").val(),
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#masterlist").html('');
        					
        					var result=jsondata;
        					  var source   = $("#masterhandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#masterlist").append( template({data:result}) );     
        				}
        		}
        	});
        }
        
        function getSubadmin()
        {		    
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getSubadminComm?resultid="/>'+"${resultid}",
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#subadminlist").html('');
        					var result=jsondata;
        					  var source   = $("#subAdminhandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#subadminlist").append( template({data:result}) );     
        				}
        		}
        	});
        }
        
        function getSupermaster(subadminname,subadminid)
        {		    
        	$("#subadminname").val(subadminname);
        
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getSupermasterComm?resultid="/>'+"${resultid}"+"&subadminname="+subadminname+"&subadminid="+subadminid,
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#supermasterlist").html('');
        					
        					var result=jsondata;
        					  var source   = $("#superMasterhandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#supermasterlist").append( template({data:result}) );     
        				}
        		}
        	});
        }
        
        function getDealer(mastername)
        {		    
        	$("#mastername").val(mastername);
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getDealerComm?resultid="/>'+"${resultid}&mastername="+mastername+"&supermastername="+$("#supermastername").val()+"&subadminname="+$("#subadminname").val(),
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#dealerbody").html('');
        					var result=jsondata;
        					  var source   = $("#dealerhandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#dealerbody").append( template({data:result}) ); 
          					$("#userbody").html('');
        				}        				
        		}
        	});
        }
        
        function getuser(dealername)
        {		 
        	$("#dealername").val(dealername);
        	
        		$.ajax({
        			type:'GET',
        			url:'<s:url value="/api/getResultUserComm?resultid="/>'+"${resultid}&dealername="+dealername,
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#userbody").html('');
        					var result=jsondata;
        					  var source   = $("#userhandler").html();
        			    	  var template = Handlebars.compile(source);
        				      $("#userbody").append( template({data:result}) );     
        				}        				
        		}
        	});
        }

        Handlebars.registerHelper('if_eq', function(a, b, opts) {
            if (a == b) {
                return opts.fn(this);
            } else {
                return opts.inverse(this);
            }
        });

        Handlebars.registerHelper('if_sm', function(a, b, opts) {
            if (a < b) {
                return opts.fn(this);
            } else {
                return opts.inverse(this);
            }
        });
        
        Handlebars.registerHelper('if_small', function(a, b, opts) {
            if (a < b) {
                return opts.fn(this);
            } else {
                return opts.inverse(this);
            }
        });

        </script>
       