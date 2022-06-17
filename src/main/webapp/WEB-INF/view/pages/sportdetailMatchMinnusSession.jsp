<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script id="closedbetshandler" type="text/x-handlebars-template">
{{#each data}}
  	<h4 class="text-center">{{@key}}</h4>
    {{#each this}}
                <div class="account-table bethistory table-responsive session-match ">
                    
                    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
                    <h4 >{{@key}}</h4>
            
                		<thead>
						 <tr>
							<th>Sr No</th>
						 	<th>Client name</th>
						 	<th>M Amt</th>
						 	<th>S Amt</th>
						 	<th>MS Amt</th> 
						 	<th>MC Amt</th>
						 	<th>SC Amt</th>
						 	<th>MSC Amt</th> 
						 	<th>Net Amt</th>
                  		{{#if_eq @../key 'Client'}}
							<th>Mob App</th><th>Upline</th>
						{{/if_eq}}
						{{#if_eq @../key 'Master'}}
							<th>Shr %</th><th>M Shr Amt</th><th>Amt Aft M Shr</th> 										
							<th>Upline Shr</th><th>Mob App</th><th>Upline</th>
						{{/if_eq}}
										
						{{#if_eq @../key 'Super Master'}}
							<th>Shr %</th><th>M Shr Amt</th><th>Amt Aft M Shr</th> 										
							<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 
							<th>Upline Shr</th><th>Mob App</th><th>Upline</th>
						{{/if_eq}}

						{{#if_eq @../key 'Sub Admin'}}
						    <th>Shr %</th><th>M Shr Amt</th><th>Amt Aft M Shr</th> 										
							<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 
							<th>Shr %</th><th>SBA Shr Amt</th><th>Amt Aft SBA Shr</th> 
							<th>Upline Shr</th><th>Mob App</th><th>Upline</th>
						{{/if_eq}}
										
						{{#if_eq @../key 'Admin'}}	
							<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 										
							<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 										
							<th>Shr %</th><th>SBA Shr Amt</th><th>Amt Aft SBA Shr</th>
							<th>Shr %</th><th>Admin Shr Amt</th><th>Amt Aft Admin Shr</th> 
							<th>Shr %</th><th>Mob App</th><th>Upline</th>
						{{/if_eq}}
										</tr>
					  </thead>
	{{#each this.individual}}
  		{{#remaider @index}}
					
			 <tbody>
						<tr> 
							<td> {{inc @index}}</td> 
 							<td>{{clientname}}</td>
							<td>{{matchamount}}</td>
							<td>{{sessionamount}}</td>
							<td>{{matchsessionamount}}</td>
							<td>{{matchcommssion}}</td>
							<td>{{sessioncommssion}}</td>
							<td>{{matchsessioncommssion}}</td>
							<td>{{netamount}}</td>

						{{#if_eq @../../key 'Client'}}
							<td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Master'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Super Master'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Sub Admin'}}
					 
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
					
						{{/if_eq}}

				 		{{#if_eq @../../key 'Admin'}}	
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}
							</td><td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
							<td>{{adminshr}}</td><td>{{adminshramt}}</td><td>{{amtsftmadminshr}}</td></td>
							<td>{{suadminshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
				 		{{/if_eq}}
					</tr>
			</tbody>
  			{{else}}
			 <tbody>
					<tr>
 							<td> {{inc @index}}</td> 
 							<td>{{clientname}}</td>
							<td>{{matchamount}}</td>
							<td>{{sessionamount}}</td>
							<td>{{matchsessionamount}}</td>
							<td>{{matchcommssion}}</td>
							<td>{{sessioncommssion}}</td>
							<td>{{matchsessioncommssion}}</td>
							<td>{{netamount}}</td>


						{{#if_eq @../../key 'Client'}}
							<td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Master'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Super Master'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Sub Admin'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
							<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
							<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}

						{{#if_eq @../../key 'Admin'}}
							<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}
							</td><td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
							<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
							<td>{{adminshr}}</td><td>{{adminshramt}}</td><td>{{amtsftmadminshr}}</td></td>
							<td>{{suadminshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
						{{/if_eq}}
					</tr>
				</tr>
			</tbody>

 		{{/remaider}}
	{{/each}}	
                   
                 {{#each this.total}}
				<tbody>
					<tr style="background-color: #ACF8BC;"><td></td><td></td>
					<td>{{matchamount}}</td>
					<td>{{sessionamount}}</td>
					<td>{{matchsessionamount}}</td>
					<td>{{matchcommssion}}</td>
					<td>{{sessioncommssion}}</td>
					<td>{{matchsessioncommssion}}</td>
					<td>{{netamount}}</td>

						
					{{#if_eq @../../key 'Client'}}
						<td>{{mobapp}}</td><td>{{upline}}</td>
					{{/if_eq}}

					{{#if_eq @../../key 'Master'}}
						<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
						<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
					{{/if_eq}}
					
					{{#if_eq @../../key 'Super Master'}}
						<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
					<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
						<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
					{{/if_eq}}

				{{#if_eq @../../key 'Sub Admin'}}
					<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}</td>
					<td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
					<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
					<td>{{uplineshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
				{{/if_eq}}

			{{#if_eq @../../key 'Admin'}}
					<td>{{mshr}}</td><td>{{mshramt}}</td><td>{{amtsftmshr}}
					</td><td>{{sushr}}</td><td>{{sushramt}}</td><td>{{amtsftmsushr}}</td>
					<td>{{sbashr}}</td><td>{{sbashramt}}</td><td>{{amtsftmsbashr}}</td>
					<td>{{adminshr}}</td><td>{{adminshramt}}</td><td>{{amtsftmadminshr}}</td></td>
					<td>{{suadminshr}}</td><td>{{mobapp}}</td><td>{{upline}}</td>
			{{/if_eq}}

			</tbody>
	{{/each}}			

	</table>
</div>
{{/each}}
{{/each}}
</script>

<!-- ************** start ****************** -->

<div class="cliennt-container">
        <div class="account-container client-record pt-4">
  
  <div class="account-record account-stment profitLoss sessionMatch">
                <div class="acont-box">
                  <span class="acont-left ">
                     <h4 >Match & Session</h4>
                     <div class="bottom-line"></div>
                  </span> 
              </div>
              
           
 
                
              <div class="account-record account-stment profitLoss sessionMatch" id="closedbetstable">
               
              

            </div>
            </div>
            
        </div>
     <script> 
        $(document).ready(function(){
	sportdetailMatchAndSession();
	
});
//alert(${eventid})


 function sportdetailMatchAndSession(){
	
	var eventid =${eventid};
	$("#page-wrapper").append('<div class="loader"></div>')
	 $.ajax({
			type:'GET',
			url:'<s:url value="/api/sportdetailMatchAndSession"/>?eventid='+eventid+"&type=All",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{
					
					$("#closedbetstable").show();
					$("#closedbetstable").html('');

					
					 $.each(jsondata, function( key, value ) {
						   $("#skyfancy"+eventid).append('<option value="'+value.id+'" selected>'+value.name+'</option>');
						 //  console.log(value)
					});
					
					  var source   = $("#closedbetshandler").html();
			    	  var template = Handlebars.compile(source);
			    	  
				      $("#closedbetstable").append( template({data:jsondata}) );
				   	   $(".loader").fadeOut("slow");
				   //	 $('#myTable').DataTable();
				    
				      
				}
		}
	});
	
	}



</script>
<jsp:include page="handlebars.jsp" /> 