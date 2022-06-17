<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <script id="oddsdatahandler" type="text/x-handlebars-template">

								<table class="table common-table">
									<thead>
										<tr>
											<th>Date</th>
											<th>T1.Name</th>
											<th>Back</th>
											<th>Lay</th>							
											<th>T2.Name</th>
											<th>Back</th>
											<th>Lay</th>	
											<th>Draw</th>
											<th>Back</th>
											<th>Lay</th>	
										</tr>
									</thead>
									<tbody id="myTable">
									{{#each data}}
									
										<tr>
											<td style="display: none;">{{id}}</td>
											<td>{{date}}</td>
											<td>{{team1name}}</td>
											<td style="background-color: #a7d8fd;" >{{team1backodds}}</td>
											<td style="background-color: #f9c9d4;">{{team1layodds}}</td>
											<td>{{team2name}}</td>
											<td style="background-color: #a7d8fd;">{{team2backodds}}</td>
											<td style="background-color: #f9c9d4;">{{team2layodds}}</td>
											<td>{{draw}}</td>
											<td style="background-color: #a7d8fd;">{{drawbackodds}}</td>
											<td style="background-color: #f9c9d4;">{{drawlayodds}}</td>
										
										</tr>
										
										{{/each}}
									</tbody>
								</table>
							
				
				
            
</script>

<div id="page-wrapper">	
<div class="inner-wrap set-fancy-result">
<input type ="hidden" value="-1" id="marketid">		
<div class="">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Odds Data </h1>
						<div class="filterSection">
						<ul>
								<li>
									<select  class="purp form-control" id='sportname' onChange="getMatches()">
									  <option value="-1">Select Sport</option>
									   <option value="4">Cricket</option>	
									   <option value="2">Tennis</option>	
									    <option value="1">Football</option>	
									    
									</select>
								</li>
								<li>
								<select class="form-control purp" id='match'></select>
								</li>
							</ul>
						</div>
						            
						
								
						<input id="myInput" type="text" placeholder="Search..">					
						<section class="menu-content">
						<div class="table-scroll" id="getBets"></div>
						
							<div class="panel-body text-center" id="nolivebets">
								<h1>No Bets Available</h1>
							</div>
							<div class="additional-filters">
							<form>
								<div class="panel-body text-center">
									<button class="apl-btn" id="previousBets" type="button" onclick="getOddsData('-1','previous')">PREVIOUS</button>
									<button class="apl-btn" id="nextBets" type="button" onclick="getOddsData('-1','next')">NEXT</button>
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
        </div>
        <!-- /#page-wrapper -->
 
    <!-- /#wrapper -->
    
       <script >
   	var page = 0;
    $(document).ready(function(){
    $("#previousBets").hide();
    $("#nextBets").hide();	   
   
    
    $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    var len = $("#myInput").val();
  
    $("#myTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  });
  
    $("#match").change(function(){
        var marketid = $(this).children("option:selected").val();
       
         $("#marketid").val(marketid).val();
        
        getOddsData($("#marketid").val());
    });
  
  });
    	
    	
    	function getOddsData(marketid,action)
		{
		
		    $("#getBets").append('<div class="loader"></div>')
		
    		if(action == "next"){
        		page = page+1;    		
    		}else if(action == "previous"){
        		page = page-1;
        		
    		}
    		
						 
						$.ajax({
							type:'GET',
							url:'<s:url value="/api/getSavedOddsByMarket?marketid="/>'+$("#marketid").val()+'&page='+page+'&Count='+200,
							//data: JSON.stringify(data),
							//dataType: "json",
							contentType:"application/json; charset=utf-8",
							success: function(jsondata, textStatus, xhr) {
								if(xhr.status == 200)
								{

								$(".loader").fadeOut("slow");
									
									$("#getBets").show();
				    				 $("#getBets").html('');				
			    					  var source   = $("#oddsdatahandler").html();
			    			    	  var template = Handlebars.compile(source);
			   				    	  $("#getBets").append( template({data:jsondata}) );	
			   				    		$("#nolivebets").hide();
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
				
				 
		 
		 function getMatches(){
		        
			var sportid =$("#sportname").val();
			
		    $.ajax({
		    type:'GET',
		    url:'<s:url value="api/getMatchesforOdds?sportid="/>'+sportid,
		    contentType:"application/json; charset=utf-8",
		    success: function(jsondata, textStatus, xhr) {
						
				if(xhr.status == 200)
				{     
							$("#match").append($('<option> <a href="#" >Select Match</a> </option>'));
								  $.each(jsondata, function(index, element) {
							 	
					             $("#match").append($('<option id = "'+element.marketid+'" value="'+element.marketid+'" name = "'+element.matchname+'" > '+element.matchname+' </option>', {
					             			                
					            }));
					            			            			       
		        			});
						    	 
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
  
 		 
    
    </script>