<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<style>
.loader {
    position: fixed;
    left: 0px;
    top: 0px;
    width: 100%;   
    height: 100%;
    z-index: 9999;
    background: url('<s:url value="/images/loader6.gif"/>') 50% 50% no-repeat ;
    opacity: .9;
}
</style>

 <script id="fancyhandler" type="text/x-handlebars-template"> 

<h1>Fancy Market2</h1>
   <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Open Fancy</h4>
             </span></div>
 {{#if_greaterThan data.fancyMarket2.length 0}}	        
         <table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Fancy Id</th>
					  <th>Fancy Name</th>  
                      <th>Result</th>                      
					  <th>Match Name</th>
					   <th>Result</th>
					   <th>Action</th>
                  </tr>
                </thead>
                <tbody id ="setResultTable">
  			{{#each data.fancyMarket2}}
                  <tr>
                  
                  <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'></td>
                   	   <td>{{name}}<p style="word-wrap:break-word; display:block; width:150px;"> {{remarks}}</p> <input type='hidden' id ="fancyname{{id}}" value='{{name}}'/td>    
                     <td>  <button class="btn btn-success" onclick="setFancyReslultByApi('{{fancyid}}','{{result}}','{{eventid}}')">{{result}}</button>
                     </td>
                    	<td>{{matchname}} <input type='hidden' id ="matchname{{id}}" value='{{matchname}}'</td>
                        
   						{{#if_includes fancyid 'OE'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="0" >Even</option>
  								 <option value="1" >Odd</option>
						   </select>
                            </td>
                         {{/if_includes}}

 						 {{#if_includes fancyid 'F3'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="1" >Back</option>
  								 <option value="0" >Lay</option>
						   </select>
                            </td>
                         {{/if_includes}}

 					{{#if_includes fancyid 'F2'}}
						<td><input type="number" id = "result{{id}}" name="ResultVal" style="width: 100px;"></td>        
					{{/if_includes}}
{{#if_includes fancyid 'FY'}}
<td><input type="number" id = "result{{id}}" name="ResultVal" style="width: 100px;"></td>        
						
                          {{/if_includes}}

				 <td><button class="btn btn-info blue-btn btn-sm" onclick = "setResult('{{fancyid}}','{{name}}','{{matchname}}','{{seriesId}}','{{seriesname}}','{{id}}','{{eventid}}')">Set result</button>
						<button class="btn btn-info blue-btn btn-sm">Fancy Status==>{{status}} </button>
						<button class="btn btn-info blue-btn btn-sm" onclick =suspendFancy('{{fancyid}}','{{eventid}}') >SUSPEND</button>
                 </td>
					
				  </tr>
 			{{/each}}          
                </tbody>
              </table>
            {{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}





<h1>Fancy Market3</h1>
   <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Open Fancy</h4>
             </span></div>
 {{#if_greaterThan data.fancyMarket3.length 0}}	        
         <table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Fancy Id</th>
					  <th>Fancy Name</th>  
                      <th>Result</th>                      
					  <th>Match Name</th>
					  <th>Result</th>
					  <th>Action</th>
                  </tr>
                </thead>
                <tbody id ="setResultTable">
  			{{#each data.fancyMarket3}}
                  <tr>
                  
                  <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'></td>
                   	   <td>{{name}}<p style="word-wrap:break-word; display:block; width:150px;"> {{remarks}}</p> <input type='hidden' id ="fancyname{{id}}" value='{{name}}'/td>    
                     <td>  <button class="btn btn-success" onclick="setFancyReslultByApi('{{fancyid}}','{{result}}','{{eventid}}')">{{result}}</button>
                     </td>
                    	<td>{{matchname}} <input type='hidden' id ="matchname{{id}}" value='{{matchname}}'</td>
                       

		{{#if_includes fancyid 'OE'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="0" >Even</option>
  								 <option value="1" >Odd</option>
						   </select>
                            </td>
                         {{/if_includes}}

 						 {{#if_includes fancyid 'F3'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="1" >Back</option>
  								 <option value="0" >Lay</option>
						   </select>
                            </td>
                         {{/if_includes}}

 					{{#if_includes fancyid 'F2'}}
						<td><input type="number" id = "result{{id}}" name="ResultVal" style="width: 100px;"></td>        
					{{/if_includes}}

				 <td><button class="btn btn-info blue-btn btn-sm" onclick = "setResult('{{fancyid}}','{{name}}','{{matchname}}','{{seriesId}}','{{seriesname}}','{{id}}','{{eventid}}')">Set result</button>
						<button class="btn btn-info blue-btn btn-sm">Fancy Status==>{{status}} </button>
						<button class="btn btn-info blue-btn btn-sm" onclick =suspendFancy('{{fancyid}}','{{eventid}}') >SUSPEND</button>
                 </td>

					
				  </tr>
 			{{/each}}          
                </tbody>
              </table>
            {{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}





<h1>Odd Even Market</h1>
   <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Open Fancy</h4>
             </span></div>
 {{#if_greaterThan data.oddEvenMarket.length 0}}	        
         <table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Fancy Id</th>
					  <th>Fancy Name</th>  
                      <th>Result</th>                      
					  <th>Match Name</th>
					  <th>Result</th>
					  <th>Action</th>
                  </tr>
                </thead>
                <tbody id ="setResultTable">
  			{{#each data.oddEvenMarket}}
                  <tr>
                  
                  <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'></td>
                   	   <td>{{name}} <p style="word-wrap:break-word; display:block; width:150px;"> {{remarks}}</p> <input type='hidden' id ="fancyname{{id}}" value='{{name}}'/td>    
                     <td>  <button class="btn btn-success" onclick="setFancyReslultByApi('{{fancyid}}','{{result}}','{{eventid}}')">{{result}}</button>
                     </td>
                    	<td>{{matchname}} <input type='hidden' id ="matchname{{id}}" value='{{matchname}}'</td>
                        
		{{#if_includes fancyid 'OE'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="0" >Even</option>
  								 <option value="1" >Odd</option>
						   </select>
                            </td>
                         {{/if_includes}}

 						 {{#if_includes fancyid 'F3'}}
                            <td>     <select id = "result{{id}}"  name="ResultVal" class="form-control">
                                <option value="-1">Select Result</option>
								 <option value="1" >Back</option>
  								 <option value="0" >Lay</option>
						   </select>
                            </td>
                         {{/if_includes}}

 					{{#if_includes fancyid 'F2'}}
						<td><input type="number" id = "result{{id}}" name="ResultVal" style="width: 100px;"></td>        
					{{/if_includes}}

				 <td><button class="btn btn-info blue-btn btn-sm" onclick = "setResult('{{fancyid}}','{{name}}','{{matchname}}','{{seriesId}}','{{seriesname}}','{{id}}','{{eventid}}')">Set result</button>
						<button class="btn btn-info blue-btn btn-sm">Fancy Status==>{{status}} </button>
						<button class="btn btn-info blue-btn btn-sm" onclick =suspendFancy('{{fancyid}}','{{eventid}}') >SUSPEND</button>
                 </td>



					
				  </tr>
 			{{/each}}          
                </tbody>
              </table>
            {{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}




<h1>Ball By Ball Session</h1>
   <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Open Fancy</h4>
             </span></div>
 {{#if_greaterThan data.ballByBallMarket.length 0}}	        
         <table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Fancy Id</th>
					  <th>Fancy Name</th>  
                      <th>Result</th>                      
					  <th>Match Name</th>
					  <th>Result</th>
					  <th>Action</th>
                  </tr>
                </thead>
                <tbody id ="setResultTable">
  			{{#each data.ballByBallMarket}}
                  <tr>
                  
                  <td>{{fancyid}} <input type='hidden' id ="fancyid{{id}}" value='{{fancyid}}'></td>
                   	   <td>{{name}} <p style="word-wrap:break-word; display:block; width:150px;"> {{remarks}}</p> <input type='hidden' id ="fancyname{{id}}" value='{{name}}'/td>    
                     <td>  <button class="btn btn-success" onclick="setFancyReslultByApi('{{fancyid}}','{{result}}','{{eventid}}')">{{result}}</button>
                     </td>
                    	<td>{{matchname}} <input type='hidden' id ="matchname{{id}}" value='{{matchname}}'</td>
                        
					 </tr>
 			{{/each}}          
                </tbody>
              </table>
            {{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}



    </script>
    
  
    <script id="fancyresultHandler" type="text/x-handlebars-template">

<hr>
<button type="button" class="btn btn-info blue-btn btn-sm" onclick='prevResultPage()'>Previous</button> 

<button type="button" class="btn btn-info blue-btn btn-sm" onclick='nextResultPage()'>Next</button> 
   <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Settled Fancy</h4>
             </span></div>
    {{#if_greaterThan data.length 0}}	    
             <table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Match Name</th>
					  <th>Fancy Name</th>                      
					  <th>Date</th>
					  <th>Result</th>
					  <th>Declare By</th>
					  <th>Action</th>
					
                  </tr>
                </thead>
                <tbody id="resultTable">
  			{{#each data}}
                  <tr>   
                   	   <td>{{matchname}} <input type='hidden' id ='matchname{{id}}' value='{{matchname}}'</td>
                    	<td>{{fancyname}} <input type='hidden' id ='fancyname{{id}}' value='{{fancyname}}'</td>
						<td>{{createdon}} <input type='hidden' id ='createdon{{id}}' value='{{createdon}}'</td>
						<td>{{resultDetail}} <input type='hidden' id ='result{{id}}' value='{{resultDetail}}'</td>
						<td>{{resultdeclareby}}</td>
						<td><button type="button" class="btn btn-info blue-btn btn-sm" onclick = "fancyRollBackDetail('{{fancyid}}')">RollBackDetail</button></td>
						
							           
     </tr>
 			{{/each}}          
                </tbody>
              </table>
{{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}
    </script>
    
      <script id="suspendedfancyHandler" type="text/x-handlebars-template">
<hr>
     <div class="acont-box">
             <span class="acont-left ">
                <h4 style="text-align: center;">Suspended Fancy</h4>
             </span></div>
    {{#if_greaterThan data.length 0}}	  
             	<table id="myTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
					  <th>Match Name</th>
					  <th>Fancy Name</th>                      
					  <th>Result</th>
					 
                  </tr>
                </thead>
                <tbody id="resultTable">
  			{{#each data}}
                  <tr>
                   	   <td>{{matchname}} <input type='hidden' id ='matchname{{id}}' value='{{matchname}}'</td>
                    	<td>{{name}} <p style="word-wrap:break-word; display:block; width:150px;"> {{remarks}}</p> <input type='hidden' id ='name{{id}}' value='{{name}}'</td>
						<td>SUSPENDED  By <input type='hidden' id ='result{{id}}' value='{{result}}'</input>{{suspendedBy}}</td>
					                  
				</tr>
 			{{/each}}          
                </tbody>
              </table>
{{else}}
<div> No Fancy Found</div>
{{/if_greaterThan}}
    </script>
    
      <script id="fancyRollBackHandler" type="text/x-handlebars-template">
   {{#each data}}
		<tr>
			<td id="{{doneBy}}">{{doneBy}}</td>
			
		</tr>
		
{{/each}}
  </script>
<!-- **********    start   ******************* -->

<div class="cliennt-container">

<input type ="hidden" value="-1" id="matchid">
<input type ="hidden" value="" id="matchname">


      
           
          
       <div class="account-container client-record pt-2">

         <div class="account-record market-result">
         
           <div class="acont-box">
             <span class="acont-left ">
                <h4>Facny Result</h4>
             </span>
           
           <div class="formrow ">
               
               <div class="form-left">
                   <div class="form-group ">
                       <label for="inputState">Match</label>
                       <select id="match" name="match" class="form-control">
                       </select>
                     </div>
                     
                 <span>    
                      <button class="btn btn-info blue-btn btn-sm" onclick="refereshFancy()">Referesh</button>
                     </span>
               </div>
              
             
             </div>
          
         </div>


         <!-- **********  First Table *************** -->
           <div class="account-table table-responsive" id="fancy"> </div>

           <!-- *********** First table end  ************* -->

           <!-- ********** Second Table   ************* -->
           <div class="account-table table-responsive" id="fancyResult"></div>
            <div class="account-table table-responsive" id="suspendedfancy"></div>

           <!-- ************* second Table end ******************* -->

          

       </div>
       </div>
       
  


</div>

<!-- ************** end   ******************** -->
    <script >
    var startpage =0;
    var endpage =100;
    $(document).ready(function(){
    	getSuspendedFancy();
    	 showMatch("Match Odds",false);
    	
    	 $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#setResultTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  	});
  	
  	
  	 $("#myInput1").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#resultTable tr").filter(function() {
    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
      });
  	});
  	
  	
  /* 	 $("#match").change(function(){
        var selectedevent = $(this).children("option:selected").val();
        var matchaname =  $("#match option:selected").attr('name');
         $("#matchid").val(selectedevent).val(); 
        
         getFancy();
    });
  	 */
    	
    });

    function refereshFancy(){
    	getFancy();
        getResultList(startpage,endpage);
        getSuspendedFancy();
    }
    
    
    function setResult(fancyid,name,matchname,seriesId,seriesname,id,matchId) {

    	var data = {fancyid:fancyid
    			,groupid:parseInt(matchId)
    			,fancyname: name
    			,sportid:parseInt(4)
    			,sportname:"Cricket"
    			,matchid:parseInt(matchId)
    			,matchname:matchname
    			,seriesid:parseInt(seriesId)
    			,seriesname:seriesname
    			,result:parseInt($("#result"+id).val())}
    	swal({
    	      title: 'Are you sure?',
    	      text: name+' Result is '+parseInt($("#result"+id).val()),
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
    	$("#fancy").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while setting the Fancy Result. Do not Refresh the Page.</span></center></div>')
		
   	 $.ajax({
			type:'POST',
			
			url:'<s:url value="/api/setFancyResult"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
			$(".loader").fadeOut("slow");
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList(startpage,endpage);
				fancyList();
				
				
			},
			complete: function(jsondata,textStatus,xhr) {
			$(".loader").fadeOut("slow");
				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList(startpage,endpage);
				fancyList();
				
		    } 
		}); 
		
		   } 
		}); 
    }




    function setFancyReslultByApi(fancyid,result,matchId) {

    	var data = {fancyid:fancyid,matchid:parseInt(matchId),result:result}
    	swal({
    	      title: 'Are you sure?',
    	      text: name+' Result is '+result,
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
    	$("#fancy").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while setting the Fancy Result. Do not Refresh the Page.</span></center></div>')
		
   	 $.ajax({
			type:'POST',
			
			url:'<s:url value="/api/setFancyReslultByApi"/>',
			data: JSON.stringify(data),
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
			$(".loader").fadeOut("slow");
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList(startpage,endpage);
				getFancy();
				
			},
			complete: function(jsondata,textStatus,xhr) {
			$(".loader").fadeOut("slow");
				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList(startpage,endpage);
				getFancy();
				
		    } 
		}); 
		
	 } 
	}); 
    }

    
    function getResultList(startpage,endpage)
    {
    	
   		$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/getFancyResultByMatch/"/>'+startpage+"/"+endpage+"/"+$("#match").val(),
  			success: function(jsondata, textStatus, xhr) {
    				if(xhr.status == 200)
    				{     
    					$("#noFancyResult").hide();
    					$("#fancyResult").show();
    					$("#myInput1").show();
    					$("#fancyResult").html('');
    					  var source   = $("#fancyresultHandler").html();
    			    	  var template = Handlebars.compile(source);
   				    	  $("#fancyResult").append( template({data:jsondata.data}) );
   				    	  console.log(jsondata.data)
   				    	$(".loader").fadeOut("slow");
   					}else{
   						  $("#fancyResult").html('');
  						  var source   = $("#fancyresultHandler").html();
  			    	      var template = Handlebars.compile(source);
 				    	  $("#fancyResult").append( template({}) );
 				    	 $(".loader").fadeOut("slow");
   					}
    		}
    	});
    }

    function nextResultPage(){
		startpage =startpage+1
		getResultList(startpage,endpage);
		console.log(startpage)
	}function prevResultPage(){

		if(startpage>=0){
			startpage =startpage-1
			getResultList(startpage,endpage);
			console.log(startpage)
		}
		
	}

    
    function rollbackFancy(id,fancyid,matchId) {
		  swal({
    	      title: 'Are you sure?',
    	      text: 'You Want to Rollback this Fancy!',
    	      type: 'warning',
    	      showCancelButton: true,
    	      confirmButtonColor: '#3085d6',
    	      cancelButtonColor: '#d33',
    	      confirmButtonText: 'Yes',
    	      closeOnConfirm: false
    	    }).then(function(isConfirm) {
    	      if (isConfirm) {	
    	     $("#fancyResult").append('<div class="loader"><center><span  style="background-color: rgb(255, 0, 0); font-size: 150%;">Please wait while rolling back the Fancy Result. Do not Refresh the Page.</span></center></div>') 
   	 $.ajax({
			type:'DELETE',
			url:'<s:url value="/api/rollbackFancy?id="/>'+id+"&fancyid="+fancyid+"&matchid="+matchId,
			dataType: "json",
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
			$(".loader").fadeOut("slow");
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList();
				fancyList();
			},
			complete: function(jsondata,textStatus,xhr) {
			 $(".loader").fadeOut("slow");
				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				$("#fancy").html('');
				$("#fancyResult").html('');
				getResultList();
				fancyList();
		    } 
		}); 
		
		 } 
		}); 
    }
    
    function showMatch(type,status){
		        
		    $.ajax({
		    					type:'GET',
		    					url:'<s:url value="api/findBySportid/4"/>',
		    					contentType:"application/json; charset=utf-8",
		    					success: function(jsondata, textStatus, xhr) {
						
							if(xhr.status == 200)
							{     
							$("#match").append($('<option> <a href="#" >Select Match</a> </option>'));
								  $.each(jsondata, function(index, element) {
							 
					             $("#match").append($('<option id = "'+element.eventid+'" value="'+element.eventid+'" name = "'+element.eventname+'" > '+element.eventname+' </option>', {
					             			                
					            }));
					            			            			       
		        			});
						    	 
							}
							}
		    			});
		    
		    }
		    
		    
		    function getFancy()
		{
					
		    		$.ajax({
						type:'GET',
						url:'<s:url value="/api/getFancyByMatch?matchid="/>'+$("#match").val(),
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
							if(xhr.status == 200)
							{    $(".loader").fadeOut("slow");
								$("#fancy").html('');
			    				 var source   = $("#fancyhandler").html();
		    			    	  var template = Handlebars.compile(source);
		   				    	  $("#fancy").append( template({data:jsondata}) );
							}else{

								  	$("#fancy").html('');
								   var source   = $("#fancyhandler").html();
						    	   var template = Handlebars.compile(source);
							       $("#fancy").append( template({}) );
							// $(".loader").fadeOut("slow");
							}
					}
				});
			    	
					
				}
    
		    function suspendFancy(fancyid,eventid){
		    	  swal({
		      	      title: 'Are you sure?',
		      	      text: 'You Want to SUSPEND this Fancy!',
		      	      type: 'warning',
		      	      showCancelButton: true,
		      	      confirmButtonColor: '#3085d6',
		      	      cancelButtonColor: '#d33',
		      	      confirmButtonText: 'Yes',
		      	      closeOnConfirm: false
		      	    }).then(function(isConfirm) {
		      	      if (isConfirm) {		 	
		      	    	$("#fancy").html('<div class="loader"></div>')
		      	    	  $.ajax({
		  		 			type:'DELETE',
		  		 			url:'<s:url value="/api/suspendFancy?fancyid="/>'+fancyid+"&eventid="+eventid,
		  					success: function(jsondata, textStatus, xhr) {
		  						showMessage(jsondata.message,jsondata.type,jsondata.title);
		  						getSuspendedFancy();
		  						$(".loader").fadeOut("slow");
		  					},
		  					complete: function(jsondata,textStatus,xhr) {
		  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
		  						getResultList();
								fancyList();
								getSuspendedFancy();
								$(".loader").fadeOut("slow");
		  				    } 
		  		 	});
		  		 	
		  		 	 } 
		  		 	});
		  		 }
		    
		    function getSuspendedFancy()
		    {
		   		$.ajax({
		   			type:'GET',
		   			url:'<s:url value="/api/findByEventidAndStatus/"/>'+$("#match").val(),
		  			success: function(jsondata, textStatus, xhr) {
		    				if(xhr.status == 200)
		    				{     
		    					$("#nosunpendedFancyResult").hide();
		    					$("#suspendedfancy").show();
		    					$("#suspendedfancy").html('');
		    					  var source   = $("#suspendedfancyHandler").html();
		    			    	  var template = Handlebars.compile(source);
		   				    	  $("#suspendedfancy").append( template({data:jsondata.data}) );
		   					}else{
		   						$("#suspendedfancy").html('');
			   				 var source   = $("#suspendedfancyHandler").html();
	    			    	  var template = Handlebars.compile(source);
	   				    	  $("#suspendedfancy").append( template({}) );
		    					
		   					}
		    		}
		    	});
		    }
		    
		    function rollbackSuspendedFancy(fancyid,eventid) {  
				   swal({
			    	      title: 'Are you sure?',
			    	      text: 'You Want to RollBack this Fancy!',
			    	      type: 'warning',
			    	      showCancelButton: true,
			    	      confirmButtonColor: '#3085d6',
			    	      cancelButtonColor: '#d33',
			    	      confirmButtonText: 'Yes',
			    	      closeOnConfirm: false
			    	    }).then(function(isConfirm) {
			    	      if (isConfirm) {		 	
			    	    	  $("#fancy").html('<div class="loader"></div>')
			    	    	  $.ajax({
					 			type:'PUT',
					 			url:'<s:url value="/api/rollbackSuspendedFancy?fancyid="/>'+fancyid+"&eventid="+eventid,
								success: function(jsondata, textStatus, xhr) {
									showMessage(jsondata.message,jsondata.type,jsondata.title);
									$(".loader").fadeOut("slow");
								},
								complete: function(jsondata,textStatus,xhr) {
									showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
									getResultList();
									fancyList();
									getSuspendedFancy();
									$(".loader").fadeOut("slow");
							    } 
					 	});
					 	
					 	 } 
					 	});
					 }


		    function fancyRollBackDetail(fancyId)
		    {
		    	  $('#modalresult').modal('show');
			   		$.ajax({
		   			type:'GET',
		   			url:'<s:url value="/api/fancyRollBackDetail/"/>'+fancyId,
		  			success: function(jsondata, textStatus, xhr) {
		    				if(xhr.status == 200)
		    				{     
		    					console.log(jsondata)
		    					$('#rollbackdetaul').html('');
	        					var source   = $("#fancyRollBackHandler").html();
	      			    	 	var template= Handlebars.compile(source);
	     				    	$("#rollbackdetaul").append( template({data:jsondata})); 
		   					}else{
		   					
		    					
		   					}
		    		}
		    	});
		    }
		  //  setInterval(function(){ refereshFancy()}, 60000) 
    </script>
      <jsp:include page="fancyrollbackdetail.jsp" />
     <jsp:include page="handlebars.jsp" />  