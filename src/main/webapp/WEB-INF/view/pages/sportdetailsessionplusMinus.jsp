<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="closedbetshandler" type="text/x-handlebars-template">
             
	{{#each data}}
          
<h1 style ="text-align: center;">{{@key}}</h1>
{{#each this}}

<h1>{{@key}}</h1><div style="overflow-x:auto;">
						<table class="table common-table">
								<thead>
										<tr><th>Sr No</th>
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

									<thead>
										<tr><th></th>
											<th></th>
											<th>M Amt</th>
									        <th>S Amt</th>
											<th>MS Amt</th> 
											<th>MC Amt</th>
											<th>SC Amt</th>
											<th>MSC Amt</th> 
											<th>Net Amt</th>
										
										{{#if_eq @../../key 'Client'}}
											<th>Mob App</th><th>Upline</th>
										{{/if_eq}}

										{{#if_eq @../../key 'Master'}}
											<th>Shr %</th><th>M Shr Amt</th><th>Amt Aft M Shr</th> 										
											<th>Upline Shr</th><th>Mob App</th><th>Upline</th>
										{{/if_eq}}
										
										{{#if_eq @../../key 'Super Master'}}
											<th>Shr %</th><th>M Shr Amt</th><th>Amt Aft M Shr</th> 										
											<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 
											<th>Upline Shr</th><th>Mob App</th><th>Upline</th>
										{{/if_eq}}

										{{#if_eq @../../key 'Sub Admin'}}
										    
											<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 										
											<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 
											<th>Shr %</th><th>SBA Shr Amt</th><th>Amt Aft SBA Shr</th> 
											<th>Upline Shr</th><th>Mob App</th><th>Upline</th>											

										{{/if_eq}}
										

										{{#if_eq @../../key 'Admin'}}	
										<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 										
											<th>Shr %</th><th>SM Shr Amt</th><th>Amt Aft SM Shr</th> 										
											<th>Shr %</th><th>SBA Shr Amt</th><th>Amt Aft SBA Shr</th>
											<th>Shr %</th><th>Admin Shr Amt</th><th>Amt Aft Admin Shr</th> 
											<th>Shr %</th><th>Mob App</th><th>Upline</th>
										{{/if_eq}}

										</tr>
									</thead>
			 <tbody>
					<tr> <td> {{inc @index}}</td> 
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
			 <tbody><tr>
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

<div id="page-wrapper">			
            <div class="inner-wrap set-fancy-result">
				<div class="row" id="sortdetail">
					<div class="col-lg-12">
						<h1 class="page-header"> Match & Session  </h1>
						
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
	sportdetailMatchAndSession();
	
});
//alert(${eventid})


 function sportdetailMatchAndSession(){
	
	var eventid =${eventid};
	$("#page-wrapper").append('<div class="loader"></div>')
	 $.ajax({
			type:'GET',
			url:'<s:url value="/api/sportdetailMatchAndSession"/>?eventid='+eventid+"&type=Fancy",
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
				    
				      
				}
		}
	});
	
	}


   



</script>

<jsp:include page="handlebars.jsp" />  