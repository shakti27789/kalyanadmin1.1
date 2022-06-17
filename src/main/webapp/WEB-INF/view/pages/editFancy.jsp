<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="activematchhandle" type="text/x-handlebars-template">
			<div class="table-responsive">   			 
             	<table class="table common-table ">
                <thead>
                  <tr>
                  <th>Sport</th>
                    <th>Match</th>
                    <th>Fancy</th>
                   
                  </tr>
                </thead>
                <tbody id="myTable">
  			{{#each data}}
                  <tr>
               {{#if_eq sportid 4}}
                    <td>
                    Cricket
                     </td>
                     {{else}}{{#if_eq sportid 2}}
                      <td>
                    Tennis
                     </td>
                     {{else}}{{#if_eq sportid 1}}
                      <td>
                    Football
                     </td>
                       {{/if_eq}}
                       {{/if_eq}}{{/if_eq}}
                   	   <td>{{matchname}}</td>
                    	
		     		  <td> <a href="#" class="btn btn-info blue-btn btn-sm" onclick = "fancyList('{{eventid}}','{{id}}','Diamond')" > Fancy List</a>	</td>
                  	 
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
            </div>
    </script>
    
    
     <script id="fancylotushandle" type="text/x-handlebars-template">

     <a class="btn new-member-btn pull-right" href="javascript:void(0)" onclick='getMatchesList()'>Back</a>
             	<table class="table common-table">
                <thead>
                  <tr>
                  <th>Fancy Id</th>
                    <th>Fancy Name</th>
                    <th>Status</th>
                    <th>Action</th>
                   
                  </tr>
                </thead>
                <tbody>
  			{{#each data}}
                  <tr>
                  
                  	   <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'</td>
                   	   <td>{{name}} <input type='hidden' id ="fancyname{{id}}" value='{{name}}'</td>
                   	   <td>{{status}} <input type='hidden' id ="fancystatus{{id}}" value='{{status}}'</td>
                    	<td>
							  <td> <a href="#" class="btn btn-info blue-btn btn-sm"  onclick = "edit('{{fancyid}}','{{eventid}}')" > Fancy List</a>	</td>
                  	
						</td>
						
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
            
    </script>
   
    
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Active Match List</h1>
						 <a  class="btn new-member-btn pull-right" href="javascript:void(0)" id ='backtoActiveMatch' onclick='onBackPressed()'>Back</a>
						 <a  class="btn new-member-btn pull-right" href="javascript:void(0)" id ='suspendBets' onclick='suspendBets()'>Suspend</a>
									<div class="filterSection">
							<ul>
								<%-- <li>
										<select class="form-control" id="selectSubadmin">
										</select>
								</li> --%>
								<li>
								<input type="text" placeholder="Search Via Match Name" id="myinput"class="ng-pristine ng-untouched ng-valid"></th>
								</li>
							</ul>
				
				<section class="menu-content">
						<div id="activematch" class="menu-content" ></div>
						
						<div id="activematchbets" class="menu-content">
						</div>
						<div class="panel-body text-center" id="noActiveMatch">
								<h1>No Active Matches Available</h1>
						</div>
						
						<div id="fancylist" class="menu-content">
						</div>
				</section>
			</div>	
						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>   
            
            <div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Edit Fancy</h4>
      </div>
      <div class="modal-body">
         <input type ="hidden" id="fancyidtext">
         <input type ="hidden" id="eventid">
         No: <input type ="text" id="no">  Rate :<input type ="text" id="norate"><br>
         Yes: <input type ="text" id="yes">   Rate :<input type ="text" id="yesrate">
           <a href="#" class="btn btn-info blue-btn btn-sm"  onclick = "editFancy()" > Save</a>	
                  	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
            
                    
        </div>
        <!-- /#page-wrapper -->
    </div>
  
   <script>
    
    var subadminid = "";
    var subadminappid;
    $(document).ready(function(){
        $("#backtoActiveMatch").hide();
        $("#activematchbets").hide();
        $("#suspendBets").hide();
        $("#myInput").show();
  		 $("#activematch").show();
	     $("#activematch").html('');
	     getActiveMatchList("Match Odds", true);
	
		if(${user.usertype}==0){
			subadminid = '${user.id}';
			subadminappid = ${user.appid};
	            $("#selectSubadmin").append($('<option id = "'+"${user.id}"+'" value="'+${user.appid}+'" name = "'+'${user.username}'+'"  > '+'${user.username}'+' </option>', { 
	            }));    			            			       
	            getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
			}
		
		if(${user.usertype}==5){
		subadminid = '${user.id}';
		subadminappid = ${user.appid};
            $("#selectSubadmin").append($('<option id = "'+"${user.id}"+'" value="'+${user.appid}+'" name = "'+'${user.username}'+'"  > '+'${user.username}'+' </option>', { 
            }));    			            			       
            getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
		}
    	
	$("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  });
	
	$("#selectSubadmin").change(function(){
			subadminid = $("#selectSubadmin option:selected").attr('id');
			subadminappid = $("#selectSubadmin option:selected").attr('value');
			getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
	});
      });
      
      
   
    function getActiveMatchList(marketname,isactive)
    {
         $("#backtoActiveMatch").hide();
         $("#activematchbets").hide();
         $("#suspendBets").hide();
         $("#myInput").show();
   		 $("#activematch").show();
 	     $("#activematch").html('');	
 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getMatchList?marketname="/>'+marketname+'&isactive='+isactive,
	    					//url:'http://localhost/api/getMatchList?marketname='+marketname+'&isactive='+isactive,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							//  var arr = $.parseJSON(jsondata);
	    							$("#noActiveMatch").hide();
	    							$("#activematch").show();
	    							$("#activematch").html('');
	    							  var source   = $("#activematchhandle").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#activematch").append( template({data:jsondata}) );
	    					    	
	    						}else{
	    							$("#noActiveMatch").show();
	    							$("#activematch").hide();
	    						}
	    						
	    					}
	    				}
	    			});
    }
    
    
         
    
    function fancyList(eventid,id,fancyprovider)
    {
   
	    $.when(	  
	    	$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/getLotusEventList?eventid="/>'+eventid+"&fancyprovider="+fancyprovider,
  			success: function(jsondata, textStatus, xhr) {
    				   $('#fancylist').html('');
    					var source   = $("#fancylotushandle").html();
    			    	  var template = Handlebars.compile(source);
   				    	  $("#fancylist").append( template({data:jsondata}) );
   				    	  $.each(jsondata, function( key, value ) {
	   				  		
   				    		 
	   				  	  });
    		}
    	})).then(function( x ) {
	    				
	    	     		});;

    }
    
     function onBackPressed()
    {	
         $("#backtoActiveMatch").hide();
         $("#activematchbets").hide();
   		 $("#activematch").show();
   		 $("suspendBets").hide();
   		 $("#myInput").show();
    }
    
     
     function edit(fancyid,eventid){
    	 
    	 $("#myModal").modal('show');
    	 $("#eventid").val(eventid);
    	 $("#fancyidtext").val(fancyid);
     }
    
    	  
     function editFancy(){
    	
    	 var data={fancyid:$("#fancyidtext").val(),eventid: $("#eventid").val(),yes:$("#yes").val(),no:$("#no").val(),norate:$("#norate").val(),yesrate:$("#yesrate").val()}
    	 $.ajax({
				type:'POST',
				url:'<s:url value="api/editFancy"/>',
				data: JSON.stringify(data),
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
				
					showMessage(jsondata.message,jsondata.type,jsondata.title);
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					  $(".loader").fadeOut("slow");
			    } 
			});
     } 	  
    		 
    		
    	
  
  
    </script>
    
    <input type="hidden" id="eventids">
    <jsp:include page="handlebars.jsp" />  