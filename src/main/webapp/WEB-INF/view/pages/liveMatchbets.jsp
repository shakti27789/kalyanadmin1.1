<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <script id="livemarketbethandler" type="text/x-handlebars-template">

								<table class="table common-table">
									<thead>
										<tr>
											<th>#</th>
											<th>Sport</th>
											<th>Client</th>
											<th>Date</th>							
											<th>Description</th>
											<th>IP</th>
											<th>Odds</th>
											<th>Stake</th>
											<th>pnl</th>
										</tr>
									</thead>
									<tbody id="myTable">
									{{#each data}}
									{{#if isback true}}
										<tr class="back">
											<td style="display: none;">{{id}}</td>
{{#if ${user.usertype} 4}}

											{{#if type 'Fancy'}}
											<td><i class="fa fa-trash" style="cursor: pointer;color: RED;" onclick = "deleteFancyBet('{{id}}')"></i></td>
											{{else}}{{#if type 'Match Odds'}}								
											<td><i class="fa fa-trash" style="cursor: pointer;color: RED;" onclick = "deleteBet('{{id}}')"></i></td>
												{{/if}}
										  {{/if}}								
		
						
{{else}}
<td></td>
{{/if}}
											 {{#if sportid 4}}
							                    <td>
							                    Cricket
							                     </td>
							                     {{else}}{{#if sportid 2}}
							                      <td>
							                    Tennis
							                     </td>
							                     {{else}}{{#if sportid 1}}
							                      <td>
							                    Football
							                     </td>
							                       {{/if}}
							                       {{/if}}{{/if}}
											<td>{{username}} <span><i class="fa fa-check" style="color: GREEN;"></i></span></td>
											<!--<td>11/22/2018 <br> 16-15-58<b style="color: RED;"> / </b>16-15-58</td>-->
											<td>{{date}}</td>			
											{{#if type 'Fancy'}}	
											<td>{{matchname}}>>{{fancyname}}</td>
											{{else}}{{#if type 'Match Odds'}}								
											<td>{{matchname}}>>{{marketname}}>>{{selectionname}}</td>
												{{/if}}
										  {{/if}}	
											<td>{{userip}}</td>			
											<td>{{pricevalue}}</td>
					                    	<td>{{stake}}</td>
					                    	<td>{{pnl}}</td>
										</tr>
										{{else}}{{#if islay true}}
										<tr class="lay">
											<td style="display: none;">{{id}}</td>								
{{#if ${user.usertype} 4}}
											{{#if type 'Fancy'}}
											<td><i class="fa fa-trash" style="cursor: pointer;color: RED;" onclick = "deleteFancyBet('{{id}}')"></i></td>
											{{else}}{{#if type 'Match Odds'}}								
											<td><i class="fa fa-trash" style="cursor: pointer;color: RED;" onclick = "deleteBet('{{id}}')"></i></td>
												{{/if}}
										  {{/if}}					
{{else}}
<td></td>
{{/if}}
										  
										   {{#if sportid 4}}
							                    <td>
							                    Cricket
							                     </td>
							                     {{else}}{{#if sportid 2}}
							                      <td>
							                    Tennis
							                     </td>
							                     {{else}}{{#if sportid 1}}
							                      <td>
							                    Football
							                     </td>
							                       {{/if}}
							                       {{/if}}{{/if}}	
											<td>{{username}} <span><i class="fa fa-check" style="color: GREEN;"></i></span></td>
											<!--<td>11/22/2018 <br> 16-15-58<b style="color: RED;"> / </b>16-15-58</td>-->
											<td>{{date}}</td>			
											{{#if type 'Fancy'}}	
											<td>{{matchname}}>>{{fancyname}}</td>
											{{else}}{{#if type 'Match Odds'}}								
											<td>{{matchname}}>>{{marketname}}>>{{selectionname}}</td>
												{{/if}}
										  {{/if}}	
											<td>{{userip}}</td>				
											<td>{{pricevalue}}</td>
					                    	<td>{{stake}}</td>
					                    	<td>{{pnl}}</td>
										</tr>
										 {{/if}}
										  {{/if}}
										{{/each}}
									</tbody>
								</table>
							
				
				
            
</script>

<div id="page-wrapper">		
<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Live Bets </h1>	
										
						<section class="menu-content">
							<div class="search-wrap">
								<input id="myInput" type="text" placeholder="Search..">			
							</div>						
							<div class="table-responsive" id="getBets">
								
							</div>
							<div class="panel-body text-center" id="nolivebets">
								<h1>No Bets Available</h1>
							</div>
							<div class="additional-filters">
							<form>
								<div class="panel-body text-center">
									<button class="apl-btn" id="previousBets" type="button" onclick="getAdminLiveBets('previous')">PREVIOUS</button>
									<button class="apl-btn" id="nextBets" type="button" onclick="getAdminLiveBets('next')">NEXT</button>
								</div>
							</form>							
						</div>
						</section>						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    
       <script >
    var t;
	var page = 0;
	$("#previousBets").hide();

		$("#nextBets").hide();

    $(document).ready(function(){
    getAdminLiveBets();
    
    $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    var len = $("#myInput").val();
   /* if(value.length<=0){
   
  	  t = setInterval(function (){ 
		    	getAdminLiveBets();
			}, 500);
    }else{
   
    clearInterval(t);
    }*/
    $("#myTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  });
  });
    	
    	
    	function getAdminLiveBets(action)
		{
		
		$("#getBets").append('<div class="loader"></div>')
		
    		if(action == "next"){
        		page = page+1;    		
    		}else if(action == "previous"){
        		page = page-1;
        		
    		}
    		
		var data;		
					if(${user.usertype} == 4){
					data = {
						"adminid":"${user.id}",
						"supermasterid":"-1",
						"subadminid":"-1",
						"isactive":"true",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":"-1",
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":"-1"
					};
					}else if(${user.usertype} == 5){
					data = {
						"adminid":"-1",
						"subadminid":"${user.id}",
						"supermasterid":"-1",
						"isactive":"true",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":"-1",
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"isdeleted":"false",
				    	"count":"200",
						"type":"-1"
					};
					}else if(${user.usertype} == 0){
					data = {
						"adminid":"-1",
						"isactive":"true",
						"supermasterid":"${user.id}",
						"subadminid":"-1",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":"-1",
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":"-1"
					};
					}else if(${user.usertype} == 1){
					data = {
						"adminid":"-1",
						"isactive":"true",
						"supermasterid":"-1",
						"subadminid":"-1",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":"-1",
						"isback":"-1",
						"sportid":"-1",
						"masterid":"${user.id}",
						"status":"-1",
						"dealerid":"-1",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":"-1"
					};
					}else{
					data = {
						"adminid":"-1",
						"isactive":"true",
						"supermasterid":"-1",
						"subadminid":"-1",
						"startdate":"",
						"enddate":"",
						"ismatched":"-1",
						"matchid":"-1",
						"isback":"-1",
						"sportid":"-1",
						"masterid":"-1",
						"status":"-1",
						"dealerid":"${user.id}",
						"userid":"-1",
				    	"page":page+"",
				    	"count":"200",
				    	"isdeleted":"false",
						"type":"-1"
					};
					}
					 
						$.ajax({
							type:'POST',
							url:'<s:url value="/api/getbets"/>',
							data: JSON.stringify(data),
							dataType: "json",
							contentType:"application/json; charset=utf-8",
							success: function(jsondata, textStatus, xhr) {
								if(xhr.status == 200)
								{

								$(".loader").fadeOut("slow");
									$("#nolivebets").hide();
									$("#getBets").show();
				    				 $("#getBets").html('');				
			    					  var source   = $("#livemarketbethandler").html();
			    			    	  var template = Handlebars.compile(source);
			   				    	  $("#getBets").append( template({data:jsondata}) );	
			   				    		
			   				    	  if(jsondata.length==200){
			   				    		  if(page>0){
					   				    		$("#previousBets").show();	   				    						   				    			  
			   				    		  }else{
			   				    			$("#previousBets").hide();
			   				    		  }
			   				    			$("#nextBets").show();
			   				    		}else{
			   				    			$("#nextBets").hide();
			   				    			if(page>0){
					   				    		$("#previousBets").show();		   				    				
			   				    			}
			   				    		}
		   				}else{

		   				$(".loader").fadeOut("slow");

							$("#nolivebets").show();
							$("#getBets").hide();

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
				
				 function deleteBet(id){	
				 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Delete this Bet!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) { 
				 	 	
		 		$.ajax({
		 			type:'DELETE',
		 			url:'<s:url value="/api/deleteBet?id="/>'+id,
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						 getAdminLiveBets();
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						
				    } 
		 	});
		 	
		 	  }
    	    });
		 }
		 
		  function deleteFancyBet(id){	
		   swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Delete this Bet!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	 	
		 		$.ajax({
		 			type:'DELETE',
		 			url:'<s:url value="/api/deleteFancyBet?id="/>'+id,
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						getAdminLiveBets();
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						getMatchedBets();
				    } 
		 	});
		 	
		 	 }
    	    });
		 }

		 
		 
		 /* t = setInterval(function (){ 
		    	getAdminLiveBets();
			}, 500);*/
				    	

 		 Handlebars.registerHelper('if', function(a, b, opts) {
		    if (a == b) {
		        return opts.fn(this);
		    } else {
		        return opts.inverse(this);
		    }
		});
			
			 Handlebars.registerHelper('if_neq', function(a, b, opts) {
				    if (a != b) {
				        return opts.fn(this);
				    } else {
				        return opts.inverse(this);
				    }
				});
  
    
    </script>