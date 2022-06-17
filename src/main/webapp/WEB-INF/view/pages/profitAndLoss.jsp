<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="closedbetshandler" type="text/x-handlebars-template">

						<table class="table common-table">
									<thead>
										<tr>
											<th>Date</th>
											<th>Description</th> 
										{{#if_ne ${user.usertype} 4}}
											<th>UpLine</th>    
										{{/if_ne}}	             
											<th>P&amp;L</th>
											<th class="green">Comm+</th>
											<th class="red">Comm-</th>
											<th>Net P&amp;L</th>
											<th>DownLine</th>
											<th>Action</th>
											<th>Result</th>
										</tr>
										<tr style="color: GREEN;background-color: #f1f1f1;">
											<th colspan="2">Total</th>
											{{#if_ne ${user.usertype} 4}}
											<th id="totalupline"></th>
											{{/if_ne}}	
											<th id="totalpnl"></th>
											<th id="totalcommissiontake"></th>
											<th id="totalcommission"></th>
											<th id="totalnetpnl"></th>
											<th id="totaldownline"></th>
											<th colspan="1"></th>
										</tr>
									</thead>
{{#each data}}

{{#if date null}}

						{{#if fancyname null}}
										<tr>
										<td></td>
									{{#if sportid 4}}
										<td>Cricket&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{else}}{{#if sportid 1}}
										<td>Soccer&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{else}}{{#if sportid 2}}
										<td>Tennis&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{/if}}{{/if}}{{/if}}

									{{#if_ne ${user.usertype} 4}}
										 {{#if_small uplinepnl 0}}
										<td class="red">{{uplinepnl}}</td>
									{{else}}{{#if uplinepnl 0}}
										<td>{{uplinepnl}}</td>
									{{else}}
										<td class="green">{{uplinepnl}}</td>
									{{/if}}{{/if_small}}
								  {{/if_ne}}	 
                          			


									{{#if_small pnl 0}}
										<td class="red">{{pnl}}</td>
									{{else}}{{#if pnl 0}}
										<td>{{pnl}}</td>
									{{else}}
										<td class="green">{{pnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small commission 0}}
										<td class="red">{{commisdsion}}</td>
									{{else}}{{#if commission 0}}
										<td>{{commission}}</td>
									{{else}}
										<td class="green">{{commission}}</td>
									{{/if}}{{/if_small}}
									{{#if_small masterCommission 0}}
										<td class="red">{{masterCommission}}</td>
									{{else}}{{#if masterCommission 0}}
										<td>{{masterCommission}}</td>
									{{else}}
										<td class="green">{{masterCommission}}</td>
									{{/if}}{{/if_small}}

									{{#if_small netpnl 0}}
										<td class="red">{{netpnl}}</td>
									{{else}}{{#if netpnl 0}}
										<td>{{netpnl}}</td>
									{{else}}
										<td class="green">{{netpnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small pnlresult 0}}
										<td class="red">{{pnlresult}}</td>
									{{else}}{{#if pnlresult 0}}
										<td>{{pnlresult}}</td>
									{{else}}
										<td class="green">{{pnlresult}}</td>
									{{/if}}{{/if_small}}
										<td><a class="btn btn-info blue-btn btn-sm" href='<s:url value="/resultBets/{{resultid}}"/>'>show bet</a>
<td><a class="btn btn-info blue-btn btn-sm" href=''>{{resultname}}</a>
										</td>
										</tr>								
						{{else}}
										<tr>
										<td></td>
										<td>Cricket&gt;&gt;{{matchname}}&gt;&gt;{{fancyname}}</td>
									{{#if_ne ${user.usertype} 4}}
										{{#if_small uplinepnl 0}}
										<td class="red">{{uplinepnl}}</td>
									{{else}}{{#if uplinepnl 0}}
										<td>{{uplinepnl}}</td>
									{{else}}
										<td class="green">{{uplinepnl}}</td>
									{{/if}}{{/if_small}}
											{{/if_ne}}	 									

								
									{{#if_small pnl 0}}
										<td class="red">{{pnl}}</td>
									{{else}}{{#if pnl 0}}
										<td>{{pnl}}</td>
									{{else}}
										<td class="green">{{pnl}}</td>
									{{/if}}{{/if_small}}
									
									{{#if_small commission 0}}
										<td class="red">{{commission}}</td>
									{{else}}{{#if commission 0}}
										<td>{{commission}}</td>
									{{else}}
										<td class="green">{{commission}}</td>
									{{/if}}{{/if_small}}
									{{#if_small masterCommission 0 }}
										<td class="red">{{masterCommission}}</td>
									{{else}}{{#if masterCommission 0}}
										<td>{{masterCommission}}</td>
									{{else}}
										<td class="green">{{masterCommission}}</td>
									{{/if}}{{/if_small}}
									{{#if_small netpnl 0}}
										<td class="red">{{netpnl}}</td>
									{{else}}{{#if netpnl 0}}
										<td>{{netpnl}}</td>
									{{else}}
										<td class="green">{{netpnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small pnlresult 0}}
										<td class="red">{{pnlresult}}</td>
									{{else}}{{#if pnlresult 0}}
										<td>{{pnlresult}}</td>
									{{else}}
										<td class="green">{{pnlresult}}</td>
									{{/if}}{{/if_small}}
										<td><a class="btn btn-info blue-btn btn-sm" href='<s:url value="/resultBets/{{resultid}}"/>'>show bet</a>
										<td><a class="btn btn-info blue-btn btn-sm" href=''>{{result}}</a>
										</td>
										</tr>
									
							{{/if}}
{{else}}
									<tbody>
										<tr style="color: GREEN;background-color: #343c18;">
										<th colspan="2" >{{date}}</th>
										{{#if_ne ${user.usertype} 4}}
											<th id="uplinepnl{{date}}"></th>  
										{{/if_ne}}	        
										
										<th id="pnl{{date}}"></th>
										<th id="commisionadmin{{date}}"></th>
										<th id="commision{{date}}"></th>
										<th id="netpnl{{date}}"></th>
										<th id="downline{{date}}"></th>
										<th colspan="1"></th>
										</tr>
						{{#if fancyname null}}
										<tr>
										<td></td>
									{{#if sportid 4}}
										<td>Cricket&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{else}}{{#if sportid 1}}
										<td>Soccer&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{else}}{{#if sportid 2}}
										<td>Tennis&gt;&gt;{{matchname}}&gt;&gt;{{marketname}}</td>
									{{/if}}{{/if}}{{/if}}
									{{#if_ne ${user.usertype} 4}}
										{{#if_small uplinepnl 0}}
										<td class="red">{{uplinepnl}}</td>
										{{else}}{{#if uplinepnl 0}}
										<td>{{uplinepnl}}</td>
										{{else}}
											<td class="green">{{uplinepnl}}</td>
										{{/if}}{{/if_small}}
								{{/if_ne}}	 

									{{#if_small pnl 0}}
										<td class="red">{{pnl}}</td>
									{{else}}{{#if pnl 0}}
										<td>{{pnl}}</td>
									{{else}}
										<td class="green">{{pnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small commission 0}}
										<td class="red">{{commission}}</td>
									{{else}}{{#if commission 0}}
										<td>{{commission}}</td>
									{{else}}
										<td class="green">{{commission}}</td>
									{{/if}}{{/if_small}}
									{{#if_small masterCommission 0 }}
										<td class="red">{{masterCommission}}</td>
									{{else}}{{#if masterCommission 0}}
										<td>{{masterCommission}}</td>
									{{else}}
										<td class="green">{{masterCommission}}</td>
									{{/if}}{{/if_small}}
									{{#if_small netpnl 0}}
										<td class="red">{{netpnl}}</td>
									{{else}}{{#if netpnl 0}}
										<td>{{netpnl}}</td>
									{{else}}
										<td class="green">{{netpnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small pnlresult 0}}
										<td class="red">{{pnlresult}}</td>
									{{else}}{{#if pnlresult 0}}
										<td>{{pnlresult}}</td>
									{{else}}
										<td class="green">{{pnlresult}}</td>
									{{/if}}{{/if_small}}
										<td><a class="btn btn-info blue-btn btn-sm" href='<s:url value="/resultBets/{{resultid}}"/>'>show bet</a>
										<td><a class="btn btn-info blue-btn btn-sm" href=''>{{resultname}}</a>
										</td>
										</tr>
							{{else}}
										<tr>
										<td></td>
										<td>Cricket&gt;&gt;{{matchname}}&gt;&gt;{{fancyname}}</td>

								{{#if_ne ${user.usertype} 4}}
											{{#if_small uplinepnl 0}}
										<td class="red">{{uplinepnl}}</td>
									{{else}}{{#if uplinepnl 0}}
										<td>{{uplinepnl}}</td>
									{{else}}
										<td class="green">{{uplinepnl}}</td>
									{{/if}}{{/if_small}}
										{{/if_ne}}	  

 								 

								{{#if_small pnl 0}}
										<td class="red">{{pnl}}</td>
									{{else}}{{#if pnl 0}}
										<td>{{pnl}}</td>
									{{else}}
										<td class="green">{{pnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small commission 0}}
										<td class="red">{{commission}}</td>
									{{else}}{{#if commission 0}}
										<td>{{commission}}</td>
									{{else}}
										<td class="green">{{commission}}</td>
									{{/if}}{{/if_small}}
										{{#if_small masterCommission 0 }}
										<td class="red">{{masterCommission}}</td>
									{{else}}{{#if masterCommission 0}}
										<td>{{masterCommission}}</td>
									{{else}}
										<td class="green">{{masterCommission}}</td>
									{{/if}}{{/if_small}}				
									{{#if_small netpnl 0}}
										<td class="red">{{netpnl}}</td>
									{{else}}{{#if netpnl 0}}
										<td>{{netpnl}}</td>
									{{else}}
										<td class="green">{{netpnl}}</td>
									{{/if}}{{/if_small}}
									{{#if_small pnlresult 0}}
										<td class="red">{{pnlresult}}</td>
									{{else}}{{#if pnlresult 0}}
										<td>{{pnlresult}}</td>
									{{else}}
										<td class="green">{{pnlresult}}</td>
									{{/if}}{{/if_small}}
										<td><a class="btn btn-info blue-btn btn-sm" href='<s:url value="/resultBets/{{resultid}}"/>'>show bet</a>
										<td><a class="btn btn-info blue-btn btn-sm" href=''>{{result}}</a>
										</td>
										</tr>
									</tbody>
						{{/if}}
{{/if}}
{{/each}}
								</table>

</script>

<div id="page-wrapper">			
            <div class="inner-wrap set-fancy-result">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Profit & Loss </h1>
						<div class="additional-filters">
							<form>
								<input name="fromDate" id="betsstartDate" placeholder="From" type="date">
								<input name="toDate" id="betsendDate" placeholder="To" type="date">
								<select id="sportList">
											<option>All</option>
											<option>Fancy</option>
											<option>Cricket</option>
											<option>Soccer</option>
											<option>Tennis</option>
								</select>
								<div class="controls">
									<button class="apl-btn" type="button" onclick="getBets()">Apply</button>
									<button class="apl-btn" type="Reset">Clear</button>
								</div>

							</form>							
						</div>
						<section class="menu-content">
						
							<div class="table-responsive" id="closedbetstable">
							
							</div>
						<div class="panel-body text-center" id="nopnl">
								<h1>No Data Available</h1>
						</div>
						</section>						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
<script>

$(document).ready(function(){
	getBets();
    var now = new Date();
	
	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);
	
	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
	
	$('#betsstartDate').val(today);
	$('#betsendDate').val(today);
});

function getBets()
{		    
	var data={"startdate":$("#betsstartDate").val(),
	"enddate":$("#betsendDate").val(),
	"sportid":$("#sportList").val()};
	$("#page-wrapper").append('<div class="loader"></div>')
	
		$.ajax({
			type:'POST',
			url:'<s:url value="/api/getMainDateWisebets"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					
					$("#nopnl").hide();
					$("#closedbetstable").show();
					$("#closedbetstable").html('');
					  var source   = $("#closedbetshandler").html();
			    	  var template = Handlebars.compile(source);
				      $("#closedbetstable").append( template({data:jsondata}) );
				   	   $(".loader").fadeOut("slow");
				      var exist = 0;
				      var obj = jsondata;
				      var pnl = 0;
				      var uplinepnl = 0;
				      var downpnl = 0;
				      var comm = 0;
				      var admincomm = 0;
				      var netpnl = 0;
				      var totalpnl = 0.00;
				      var totaldownpnl = 0.00;
				      var totalcomm = 0.00;
				      var totalcommadmin = 0.00;
				      var totalnetpnl = 0.00;
				      var date = "";
				      var totalupline=0.00;
				      for(var i=0;i<obj.length;i++){
				      
				      	 totaldownpnl=totaldownpnl+obj[i].pnlresult;
				    	  totalpnl=totalpnl+obj[i].pnl;
				    	  totalupline=totalupline+obj[i].uplinepnl;
				    	  totalnetpnl=totalnetpnl+obj[i].netpnl;
				    	  if(obj[i].masterCommission!=null){
					    	  totalcomm=totalcomm+obj[i].masterCommission;
				    		  
				    	  }
				    	  totalcommadmin = totalcommadmin+obj[i].commission;
				    	  if(obj[i].date!=null){
				    		  if(exist=1){
							      if(pnl<0.00){
					    			  $("#pnl"+date).html(pnl.toFixed(2)).removeClass("green").addClass("red");				    				  
					    		 }else if(pnl==0.00){
					    			  $("#pnl"+date).html(pnl.toFixed(2));
				    			  }else{
					    			  $("#pnl"+date).html(pnl.toFixed(2)).removeClass("red").addClass("green");				    				  
					    		 }
							      
							      if(uplinepnl<0.00){
					    			  $("#uplinepnl"+date).html(uplinepnl.toFixed(2)).removeClass("green").addClass("red");				    				  
					    		  }else if(pnl==0.00){
					    			 $("#uplinepnl"+date).html(uplinepnl.toFixed(2));
				    			  }else{
					    			  $("#uplinepnl"+date).html(uplinepnl.toFixed(2)).removeClass("red").addClass("green");				    				  
					    		 }
							      
							      if(downpnl<0.00){				    				  
					    			  $("#downline"+date).html(downpnl.toFixed(2)).removeClass("green").addClass("red");
				    			  }else if(downpnl==0.00){
					    			  $("#downline"+date).html(downpnl.toFixed(2));
				    			  }else{			    				  
					    		     $("#downline"+date).html(downpnl.toFixed(2)).removeClass("red").addClass("green");
				    			  }
							      
				    			  if(netpnl<0.00){
					    			  $("#netpnl"+date).html(netpnl.toFixed(2)).removeClass("green").addClass("red");  				  
				    			  }else if(netpnl==0.00){
					    			  $("#netpnl"+date).html(netpnl.toFixed(2));				  
				    			  }else{
					    			  $("#netpnl"+date).html(netpnl.toFixed(2)).removeClass("red").addClass("green");  				  	    				  
				    			  }
				    			  
				    			  if(comm<0.00){
				    				  $("#commision"+date).html(comm.toFixed(2)).removeClass("red").addClass("green");
				    			  }else if(comm==0.00){
				    				  $("#commision"+date).html(comm.toFixed(2));
				    			  }else if(isNaN(comm)){
	    			   				$("#commision"+date).html(0);
	    						  }else{
				    				  $("#commision"+date).html(-comm.toFixed(2)).removeClass("green").addClass("red");	    				  
				    			  }
				    			  
				    			  if(admincomm<0.00){
				    				  $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("green").addClass("red");
				    			  }else if(admincomm==0.00){
				    				  $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("red").addClass("green");
				    			  }else if(isNaN(admincomm)){
	    			     			$("#commisionadmin"+date).html(0);
	    			     			}else{
				    				  $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("red").addClass("green");	    				  
				    			  }

				    			  exist=1;
				    		  }
				    		  pnl = obj[i].pnl;
				    		  downpnl = obj[i].pnlresult;
				    		  netpnl = obj[i].netpnl;
				    		  comm = obj[i].masterCommission;
				    		  admincomm = obj[i].commission
			    			  date= obj[i].date;
				    		  uplinepnl=uplinepnl+obj[i].uplinepnl;
				    	  }else{
					    	  pnl=pnl+obj[i].pnl;
					    	  uplinepnl=uplinepnl+obj[i].uplinepnl;
					    	  downpnl = downpnl+obj[i].pnlresult;
					    	  netpnl=netpnl+obj[i].netpnl;
					    	  comm=comm+obj[i].masterCommission;
					    	  admincomm = admincomm+obj[i].commission				    		  
				    	  }
				      }
				      if(pnl<0.00){
		    			  $("#pnl"+date).html(pnl.toFixed(2)).removeClass("green").addClass("red");				    				  
		    		  }else if(pnl==0.00){
		    			  $("#pnl"+date).html(pnl.toFixed(2));
	    			  }else{
		    			  $("#pnl"+date).html(pnl.toFixed(2)).removeClass("red").addClass("green");				    				  
		    		  }
	    			  
				      if(uplinepnl<0.00){
		    			  $("#uplinepnl"+date).html(uplinepnl.toFixed(2)).removeClass("green").addClass("red");				    				  
		    		  }else if(pnl==0.00){
		    			  $("#uplinepnl"+date).html(uplinepnl.toFixed(2));
	    			  }else{
		    			  $("#uplinepnl"+date).html(uplinepnl.toFixed(2)).removeClass("red").addClass("green");				    				  
		    		  }
	    			  if(downpnl<0.00){				    				  
					     $("#downline"+date).html(downpnl.toFixed(2)).removeClass("green").addClass("red");
					  }else if(downpnl==0.00){
			  			  $("#downline"+date).html(downpnl.toFixed(2));
					}else{			    				  
					   $("#downline"+date).html(downpnl.toFixed(2)).removeClass("red").addClass("green");
				    }
				      
	    			  if(netpnl<0.00){
		    			  $("#netpnl"+date).html(netpnl.toFixed(2)).removeClass("green").addClass("red");  				  
	    			  }else if(netpnl==0.00){
		    			  $("#netpnl"+date).html(netpnl.toFixed(2));				  
	    			  }else{
		    			  $("#netpnl"+date).html(netpnl.toFixed(2)).removeClass("red").addClass("green");  				  	    				  
	    			  }
	    			  
	    			  if(comm<0.00){
	    				  $("#commision"+date).html(comm.toFixed(2)).removeClass("red").addClass("green");
	    			  }else if(comm==0.00){
	    				  $("#commision"+date).html(comm.toFixed(2));
	    			  }else if(isNaN(comm)){
	    			   $("#commision"+date).html(0);
	    			  }else{
	    				  $("#commision"+date).html(-comm.toFixed(2)).removeClass("green").addClass("red");	    				  
	    			  }
	    			  
	    			  if(admincomm<0.00){
				    	 $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("green").addClass("red");
				    	}else if(admincomm==0.00){
				    	 $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("red").addClass("green");
				    	 }else if(isNaN(admincomm)){
	    			     $("#commisionadmin"+date).html(0);
	    			     } else{
				    	  $("#commisionadmin"+date).html(admincomm.toFixed(2)).removeClass("red").addClass("green");	    				  
				    	 }
	    			  
				      
	    			  if(totalpnl<0.00){
		    			  $("#totalpnl").html(totalpnl.toFixed(2)).removeClass("green").addClass("red");				    				  
		    			 // $("#totaldownline").html(totalpnl.toFixed(2)).removeClass("green").addClass("red");
	    			  }else if(totalpnl==0.00){
		    			  //$("#totaldownline").html(totalpnl.toFixed(2));
	    				  $("#totalpnl").html(totalpnl.toFixed(2));
	    			  }else{
		    			  $("#totalpnl").html(totalpnl.toFixed(2)).removeClass("red").addClass("green");				    				  
		    			  //$("#totaldownline").html(totalpnl.toFixed(2)).removeClass("red").addClass("green");
	    			  }
	    			  
	    			  
	    			  if(totaldownpnl<0.00){				    				  
		    			  $("#totaldownline").html(totaldownpnl.toFixed(2)).removeClass("green").addClass("red");
	    			  }else if(totaldownpnl==0.00){
		    			  $("#totaldownline").html(totaldownpnl.toFixed(2));
	    			  }else{				    				  
		    			  $("#totaldownline").html(totaldownpnl.toFixed(2)).removeClass("red").addClass("green");
	    			  }
	    			  
	    			  if(totalnetpnl<0.00){
		    			  $("#totalnetpnl").html(totalnetpnl.toFixed(2)).removeClass("green").addClass("red");  				  
	    			  }else if(totalnetpnl==0.00){
		    			  $("#totalnetpnl").html(totalnetpnl.toFixed(2));				  
	    			  }else{
	    				  $("#totalnetpnl").html(totalnetpnl.toFixed(2)).removeClass("red").addClass("green");  			
	    			  }
	    			  if(totalupline<0.00){
		    			  $("#totalupline").html(totalupline.toFixed(2)).removeClass("green").addClass("red");  				  
	    			  }else if(totalupline==0.00){
		    			  $("#totalupline").html(totalupline.toFixed(2));				  
	    			  }else{
	    				  $("#totalupline").html(totalupline.toFixed(2)).removeClass("red").addClass("green");  			
	    			  }
	    			  
	    			  
	    			  
	    			  if(totalcomm<0.00){
	    				  $("#totalcommission").html(totalcomm.toFixed(2)).removeClass("red").addClass("green");
	    			  }else if(totalcomm==0.00){
	    				  $("#totalcommission").html(totalcomm.toFixed(2));
	    			  }else if(isNaN(totalcomm)){
	    			   $("#totalcommission").html(0);
	    			  }else{
	    				  $("#totalcommission").html(-totalcomm.toFixed(2)).removeClass("green").addClass("red");	    				  
	    			  }
				      
				       if(totalcommadmin<0.00){
	    				  $("#totalcommissiontake").html(totalcommadmin.toFixed(2)).removeClass("green").addClass("red");
	    			  }else if(totalcomm==0.00){
	    				  $("#totalcommissiontake").html(totalcommadmin.toFixed(2)).removeClass("red").addClass("green");	
	    			  }else if(isNaN(totalcommadmin)){
	    			   $("#totalcommissiontake").html(0);
	    			  }else{
	    				  $("#totalcommissiontake").html(totalcommadmin.toFixed(2)).removeClass("red").addClass("green");	    				  
	    			  }
				      
				      
				}else{
					$("#nopnl").show();
					$("#closedbetstable").hide();
					$("#closedbetstable").html('');		
					 $(".loader").fadeOut("slow");
				}
		}
	});
}

Handlebars.registerHelper('if', function(a, b, opts) {
    if (a == b) {
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

Handlebars.registerHelper('if_ne', function(a, b, opts) {
    if (a != b) {
        return opts.fn(this);
    } else {
        return opts.inverse(this);
    }
});


</script>