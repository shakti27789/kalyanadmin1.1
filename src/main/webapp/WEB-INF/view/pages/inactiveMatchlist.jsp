<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="inactivematchhandle" type="text/x-handlebars-template">

   			 <div class="table-responsive">
             	<table class="table common-table ">
                <thead>
                  <tr>
                  <th>Sport</th>
                    <th>Match</th>
                    <th>Date</th>
                    <th>Market</th>
                    <th>Status</th>
                    <th>Action</th>
                  
                   
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
		     		  <a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm" onclick='MakeMatchactive("{{marketid}}","{{eventid}}",subadminappid,subadminid)' >Update Status</a>
		     		
		     		  </td>
		     		 
                  	 
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
</div>
            
    </script>
    
    
   
    
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">InActive Match List</h1>
						
						
									<div class="filterSection">
							<ul>
								<li>
										<select class="form-control" id="selectSubadmin">
										</select>
								</li>
								<li>
								<input type="text" placeholder="Search Via Match Name" id="myinput"class="ng-pristine ng-untouched ng-valid"></th>
								</li>
							</ul>
				
				<section class="menu-content">
						<div id="inactivematch" class="menu-content" ></div>
						
						<div class="panel-body text-center" id="noInactiveMatch">
								<h1>No InActive Matches Available</h1>
						</div>
				</section>
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
    
     var subadminid = "";
    var subadminappid;
    $(document).ready(function(){
		subadminList();
		$("#selectSubadmin").change(function(){
		subadminid = $("#selectSubadmin option:selected").attr('id');
			subadminappid = $("#selectSubadmin option:selected").attr('value');
			getInActiveMatchList("Match Odds", false,$("#selectSubadmin option:selected").attr('value'));
	});
		if(${user.usertype}==5){
		subadminid = '${user.id}';
		subadminappid = ${user.appid};
            $("#selectSubadmin").append($('<option id = "'+"${user.id}"+'" value="'+${user.appid}+'" name = "'+'${user.username}'+'"  > '+'${user.username}'+' </option>', { 
            }));    			            			       
            getInActiveMatchList("Match Odds", false,$("#selectSubadmin option:selected").attr('value'));
		}

	$("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  });
 });
      
    function subadminList(){
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/user/${user.id}/"/>'+5+"?active=true",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status==200){
					
					var obj = jsondata;
					$("#selectSubadmin").html('');
					$("#selectSubadmin").append($('<option> <a href="#" >Select Sub Admin</a> </option>'));
					  for(var i = 0;i<obj.data.length;i++) {

		             $("#selectSubadmin").append($('<option id = "'+obj.data[i].id+'" value="'+obj.data[i].appid+'" name = "'+obj.data[i].username+'"  > '+obj.data[i].username+' </option>', { 
		            }));    			            			       
  			  }
			}
		}
	});
   }
    
    function getInActiveMatchList(marketname,isactive,appid)
    {	
   		 $("#inactivematch").show();
 	     $("#inactivematch").html('');	
 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getMatchList?marketname="/>'+marketname+'&isactive='+isactive+"&appid="+appid,
	    					//url:'http://localhost/api/getMatchList?marketname='+marketname+'&isactive='+isactive,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						if(xhr.status == 200)
	    						{     
	    							//  var arr = $.parseJSON(jsondata);
	    							$("#noInactiveMatch").hide();
	    							$("#inactivematch").show();
	    							$("#inactivematch").html('');
	    							  var source   = $("#inactivematchhandle").html();
	    					    	  var template = Handlebars.compile(source);
	    					    	  $("#inactivematch").append( template({data:jsondata}) );
	    					    	
	    						}else{
	    							$("#noInactiveMatch").show();
	    							$("#inactivematch").hide();
	    						}
	    						
	    					}
	    				}
	    			});
    }
    
    
    	  
    	     function MakeMatchactive(marketid,eventid,appid,subadminid){
    	    		 swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Active this Match!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
    	    	 $.ajax({
    					type:'PUT',
    					url:'<s:url value="api/updatematchStatus?marketid="/>'+marketid+"&eventid="+eventid+"&appid="+appid+"&subadminid="+subadminid,
    					//url:'http://localhost/api/updatematchStatus?marketid='+marketid,
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    					
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    						getInActiveMatchList("Match Odds",false,subadminappid);
    						$("#myInput").val('');
    						
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						 
    				    } 
    				}); 
    	   
    	  
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
 