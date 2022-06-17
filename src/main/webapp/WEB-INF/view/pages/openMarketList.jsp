<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="openMarketHandler" type="text/x-handlebars-template">
						<table class="table common-table profitLossTable">
								<thead>
									<tr>
									   <th>Match Name</th>
									   <th>Full Market</th>      
									</tr>
								</thead>
								<tbody>
{{#each data}}
									<tr>
										<td>{{matchname}}</td>
										<td><a href="<s:url value="/innerExpo"/>/{{sportid}}/{{matchid}}/{{marketid}}">Go to Full Market</a></td>      
									</tr>
{{/each}}									
								</tbody>
							</table>
</script>

<div id="page-wrapper">			
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Open Market</h1>
						<div class="menu-content" id="openmarket">
							
						</div>
						<div class="panel-body text-center" id="noActiveMarket">
								<h1>No Active Market Available</h1>
						</div>
					</div>
				</div>
            </div>           
        </div>
        <script>
        setInterval(function (){ 
        	getMyProfit();
        }, 500);
        
        function getMyProfit()
        {

        	var data = {
        		"sportid":"-1",
        		"type":"${user.usertype}",
        		"table":"2",
        		"id":"${user.id}",
        		"appid":"${user.appid}"
        	};
        		$.ajax({
        			type:'POST',
        			url:'<s:url value="/api/getMyProfit"/>',
        			data: JSON.stringify(data),
        			dataType: "json",
        			contentType:"application/json; charset=utf-8",
        			success: function(jsondata, textStatus, xhr) {
        				if(xhr.status == 200)
        				{
        					$("#noActiveMarket").hide();
        					$("#openmarket").show();
            				  $("#openmarket").html('');
            				  
        					  var unMatchsource   = $("#openMarketHandler").html();
        			    	  var untemplate = Handlebars.compile(unMatchsource);
        			    	  $("#openmarket").append( untemplate({data:jsondata}) );		
        				}else {
        					$("#noActiveMarket").show();
        					$("#openmarket").hide();
        				}
        		}
        	});
        }
        </script>