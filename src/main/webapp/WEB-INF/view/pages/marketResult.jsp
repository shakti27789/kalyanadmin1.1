<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="markerResulthandle" type="text/x-handlebars-template">

			
   			 
             	<table class="table common-table ">
                <thead>
                  <tr>
                  <th>Sport</th>
                    <th>Match</th>
                    <th>Date</th>
                    <th>Market</th>
                    <th>Status</th>
                    <th>Action</th>
                    <th>Show Bets</th>
                   
                  </tr>
                </thead>
                <tbody id="myTable">
  			{{#each data}}
                  <tr>
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
                   	   <td>{{matchname}}</td>
                    	<td>{{createdon}}</td>
                    	<td>{{marketname}}</td>
                    	<td>{{isactive}}</td>
                        
                        	<td>
		     		  <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick='MakeMatchInactive("{{marketid}}")' >Update Status</a>
		     		  <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick= >Pause</a>
		     		  </td>
		     		  <td> <a href="#" onclick = 'showMatchBets("{{marketid}}",true)' > Show Bets</a>	</td>
                  	 
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
            
    </script>
    
    
       
    
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Market Result</h1>
						<div id="sportlist" class="white">
						 <lable class= "back">Sport</lable>
						<select id='sport' class = "purp">
						
						 
						 </select>
					
					</div>
						<div id="matchresult" class="menu-content" ></div>
						
					
					
						
						</div>
						
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
    	getActiveSports(true);
    	
    	$("#sport").change(function(){
        var selectedsport = $(this).children("option:selected").val();
        showSeriesBySport(selectedsport);
    });
      });
      
      
      function getActiveSports(status)
    {		
    	   
    	    	
    	    	
    	    	 $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveSportList"/>?status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					if(xhr.status == 200)
					{     
					$("#sport").append($('<option> <a href="#" >Select Sport</a> </option>'));
						  $.each(jsondata, function(index, element) {
					 
			             $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" > '+element.name+' </option>', {
			             			                
			            }));
			            			            			       
        			});
				    	 
					}
					
				
			}
    				});
    	    
    	    
   	 
    }
    
    function showSeriesBySport(sportid,status){
    
    $.ajax({
    					type:'GET',
    					url:'<s:url value="api/getActiveSeriesListBySport"/>?sportid='+sportid+'&status='+status,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
				
					if(xhr.status == 200)
					{     
					
				    	 
					}
					
				
						}
    				});
    
    }
   
  
  
    </script>
 