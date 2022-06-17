<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!--************** start ***************** -->

 <script id="statementhandler" type="text/x-handlebars-template">

	    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Round Id </th>
						<th>Date/Time</th>
                       <th>Pnl</th>
                       <th>Action</th>
                       
                   </tr>
               </thead>
               <tbody>
                 {{#each data}}
                   <tr> 
                       <td>{{_id}}</td>
						<td>{{strTODate createdon}}</td>	
                   {{#if_small amount 0}}  <td class="negative">{{amount}} </td>{{else}}<td class="positive">{{amount}} </td> {{/if_small}}
                       <td><input type="hidden" value="{{marketid}}" id ="mid{{inc @index}}"><a href="javascript:void(0);" class="btn btn-success bets_btn expandArrow" style="text-decoration:none" onClick="showBets('{{inc @index}}','down','{{_id}}')">Bets</a></td>
  
                   </tr>
 <tr class="bets_table_tr" id="bets_table_tr{{inc @index}}">
				  <td colspan=9>
							<input type="hidden" class="slideupdown" id="slideupdown{{inc @index}}" value="up">
							<div id="bet_list_div{{inc @index}}" class="bet_list_div" style="display:none;"></div>
                            
							

							
                  </td></tr>
     
                 {{/each}} 
        
              
                  
                  
               </tbody>


       
           </table>

</script>


<script id="betHistoryHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
 <th>Seriol No</th>			 
<th>Markets</th>	
        <th>Odds</th>
        <th>Nation</th>
        <th>Stack</th>
        <th>Pnl</th>
      
       
    </tr>
</thead>
<tbody>
  {{#each data}}

    {{#if_eq isback true}}
    <tr class="back">
  {{else}} 
 <tr class="lay">
 {{/if_eq}}	
<td>{{serialno}}</td>		
<td><p><span>{{marketname}}</span<span>{{selectionname}}</span></p><p><span>Betid :{{id}}</span><span>placed Time:{{matchedtime}}</span></p><span>userId :{{userid}} <i class="fa fa-info-circle"  onclick="userDetail('{{id}}','{{id}}')" ></i><span id ="userdetail{{id}}" class="userdetail" ></span> </span></p>  {{#if_greaterThan sportid 500}}<p><span> roundId:{{marketid}}</span></p>  {{/if_greaterThan}}</td>	
        <td>{{odds}}</td>
        <td>{{selectionname}}</td>
        <td>{{stake}}</td>
        {{#if_small netpnl 0}}
					     <td class="negative">{{netpnl}}</td>
				          {{else}}{{#if netpnl 0}}
					     <td>{{netpnl}}</td>
				       {{else}}
					     <td class="positive">{{netpnl}}</td> 
				       {{/if}}
         {{/if_small}}
        
    </tr>
  {{/each}}
   
   
</tbody>

</table>
</script>

<div class="cliennt-container">
       <div class="account-container client-record pt-4">

         <div class="account-record account-stment">
			<div class="acont-box">
				<span class="acont-left ">
					<h4>Account Statement</h4>
				</span>
				<div class="form-row">
					<div class="form-group col-md-3">
					   <label for="inputState">Game Name</label>
					   <select id="game_list" class="form-control">
						
					   </select>
					</div>
					<div class="form-group col-md-3">
					   <label for="inputState">Search By Client Name</label>
					   <select id="clientlist" class="form-control">
					  
					   </select>
					</div>

					<div class="form-group col-md-2">
						 <label for="inputState">From</label>						 
						 <div class="input-group">
							<input class="datepicker form-control" id="startDate" style="border-radius: 5px 0 0 5px;">
							<div class="input-group-append">
								<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
							</div>
						</div>
					</div>
					
						<div class="form-group col-md-2">
						 <label for="inputState">Action</label>
						 <div class="input-group">
							<button class="btn btn-primary" onclick="getStatement()">Load</button>
						</div>
						
					</div>

					<div class="form-group col-md-3" style="margin-top: 30px;">
					<label>	Total Profit:</label><span class="totalprofit"></span> <label style="margin-left: 20px;">Today Profit:</label><span class="todayprofit"></span>
					</div>
				</div>
			</div>
         
         <%-- <div class="acont-search">
           <span class="search-left "><button class="btn btn-danger">PDF</button> </span>
           <span class="search-left  "><button class="btn btn-success">Excel</button></span>
         </div>
 --%>
         
           <div class="account-table bethistory table-responsive" id="statement">
          </div>

          

       </div>
       </div>
       
  


</div>

<!--*************** end ***************** -->





<script>

var prervId =0;

$(document).ready(function(){
            	  var now = new Date();
            	
				var day = ("0" + now.getDate()).slice(-2);
            	var month = ("0" + (now.getMonth() + 1)).slice(-2);
            	
            	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
            	
            	 var date = new Date();
                 var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                 var enddate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                 
            	

            	 $('#startDate').datepicker({
            			format: "yyyy-mm-dd",
            			autoclose: true,
            			todayHighlight: true
            			
             });
            	 
            	
            	 $('#startDate').datepicker('setDate', startdate);
            	
            	
            	 $( "#clientlist" ).change(function() {
            		 getStatement($("#clientlist").val());
                 });
            	// userList()
            	 getCasinoList();
            });




              function getCasinoList()
              {	  	    
              	 
              	  
                  
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/casinoEvent"/>/',
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){

                  				
                  				$.each(jsondata, function(index, element) {
    								$("#game_list").append('<option value='+element.eventid+'>'+element.eventname+'</option>');
    								$("#game_list").select2();
                      			});
                  				 getStatement('-1');
              				 
              			}
              		}
              	});
              }
              
              function getStatement(childid)
              {	  var userId ="";	    
              	  var startDate = $("#startDate").val()+" 00:00:00";
              	  if(childid =="0"){
              		childid=$("#clientlist").val();
                  	}
                if(childid == null){
                	console.log("child Id"+childid)
                	childid='${user.userid}';
                	}
              	  $("#statement").html('');
                   
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/getuserPnlMQuery"/>/'+childid+"/"+$("#game_list").val()+"/"+startDate+"/0",
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              				
              				  var source   = $("#statementhandler").html();
	    			    	  var template = Handlebars.compile(source);
	    			    	  $(".totalprofit").html('');
	    			    	  $(".todayprofit").html('');
	   				    	  $("#statement").append( template({data:jsondata.data}));	
	   				    	 
	   				    	  totalProfitLoss(childid,startDate);
	   				    	 

	   				    	 
	   				    	//  alert(jsondata.totalProfit[0].totalprofit)
              			}else{
              				
              				 $("#statement").append('  <div>No Records Found</div>');	
              			}
              		}
              	});
              }



              function totalProfitLoss(childid,startDate)
              {	  
              	 
              		$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/getuserPnlToday"/>/'+childid+"/"+$("#game_list").val()+"/"+startDate,
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              				
              				  
	    			    	  $(".totalprofit").html('');
	    			    	  $(".todayprofit").html('');
	   				    	  
	   				    	 $(".totalprofit").html(jsondata.totalProfit[0].totalprofit)
	   				    	 $(".todayprofit").html(jsondata.todayProfit[0].amount)
	   				    	 if(jsondata.totalProfit[0].totalprofit>0){
	   				    		$(".totalprofit").addClass("positive")
	   				    		$(".totalprofit").removeClass("negative")
	   				    		
	   				    		  }else{
		   				    		$(".totalprofit").addClass("negative")
		   				    		$(".totalprofit").removeClass("positive")
		   				    		
		   				    		 }


	   				    	if(jsondata.todayProfit[0].amount>0){
	   				    		$(".todayprofit").addClass("positive")
	   				    		$(".todayprofit").removeClass("negative")
		   				    	  }else{
		   				    		$(".todayprofit").addClass("negative")
		   				    		$(".todayprofit").removeClass("positive")
		   				    		 }
	   				    	 

	   				    	 
	   				    	//  alert(jsondata.totalProfit[0].totalprofit)
              			}
              		}
              	});
              }

              function userList() {	 
					$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/userByParentId/"/>'+'${user.id}',
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				if(xhr.status == 200){
              					$.each(jsondata, function(index, element) {
								$("#clientlist").append('<option valuue='+element.userid+'>'+element.userid+'</option>');
								$("#clientlist").select2();
                  			});
              				 
              			}else{
              				
              				 
              			}
              		}
              	});
              }

               $('#clientlist').select2({
					
        		    ajax: {
      		        url: '<s:url value="/api/userByUserLike"/>',
      		        dataType: 'json',
      		        processResults: function (data) {
						 return {
      		                results:data
      		            };
      		        },
      		        cache: false
      		    },
      		    minimumInputLength:2,
      		    allowClear:true,
      		    placeholder: {
      		        id: "",
      		        placeholder: "Leave blank to ..."
      		      },
      		});	 


               function showBets(id,slideupdown,marketId){
            		 
            		$( "#bet_list_div"+id).slideToggle("slow");
            		$(".bet_list_div").html('');
            	
            		if($("#slideupdown"+id).val() =="up"){
            			betHistory($("#game_list").val(),marketId,id,'bet_list_div')
            			$("#slideupdown"+id).val('down');
            			$( "#bet_list_div"+id).show();
            			}else{
            				$("#slideupdown"+id).val('up');
            				
            				}
            		
            	}




               function betHistory(matchId,marketId,id,div){
               	 
               	  var data = {userId:"${user.userid}",
               			      matchId:matchId,
               			      marketId:marketId	
               			     }
               		$("#statement").append('<div class="loader"></div>')
               		 $("#"+div+id).html('');
               		  $.ajax({
               			    type:'POST',
               			    url:'<s:url value="/api/betHistory"/>',
               				data: JSON.stringify(data),
               				dataType: "json",
               				contentType:"application/json; charset=utf-8",
               				success: function(jsondata, textStatus, xhr) {
               					$("#"+div+id).html('');
               					//console.log(div+id)
               					if(xhr.status == 200){ 
               						 var source   = $("#betHistoryHandler").html();
                    			    	  var template = Handlebars.compile(source);
                    				     $("#"+div+id).append( template({data:jsondata}) );
                    				     $(".loader").fadeOut("slow");
                      				    
               				}
               			}
               		  });
               		  
               	    
               }
                           	
               function userDetail(betid,id){
            		
            		$(".userdetail").html('');
            		  $.ajax({
            				type:'GET',
            				url:'<s:url value="/api/userHerarchy/"/>'+betid,
            				contentType:"application/json; charset=utf-8",
            				success: function(jsondata, textStatus, xhr) {
            		
            			 if(xhr.status == 200){     
            				 $("#userdetail"+betid).html("<span>"+jsondata.response+"</span>")
            		 }
            		}
            	});
            				
            	}
              	
         </script>
<jsp:include page="handlebars.jsp" />  
