<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  
    
    
     <script id="activematchhandle" type="text/x-handlebars-template">
			  			 
             	 <table id="activeMatchTable" class="table table-striped table-bordered " style="width:100%">
                <thead>
                  <tr>
                  <th>Sport</th>
                    <th>Match</th>
 					<th class="action">Action</th>
					<th>Fancy Provider</th>
 					<th>Min Bet</th>
					<th>Max Bet</th>
					<th>Bet Delay</th>	

                     <th>Min Fancy</th>
					<th>Max Fancy</th>
					<th>Ply Mx Bet Fancy</th>
					<th>Delay Fancy</th>	

					<th>Min BetBookMaker</th>
					<th>Max BetBookmaker</th>	
					<th>Max BetBookmaker2</th>	
				    <th>Bet Delay Bookmaker</th>
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
                   	   <td>{{eventname}}</td>
 					<td class="action">
						<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="loadFancyList('{{eventid}}','{{fancyprovider}}')">Fancy</a>
					 </td>
					   
						<td>
						<div class="form-group">
                   			<select class="form-control" name="sport" id="fancyprovider{{eventid}}" onchange=updateFancyProvider('{{eventid}}')></select>
                        </div>
						</td>
					
						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="minbet{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","minbet")></select>
                        </div>
						</td>
						

 
                    	

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxbet{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","maxbet")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="betdelay{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","betdelay")></select>
                        </div>
						</td>

                       <td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="minbetfancy{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","minbetfancy")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxbetfancy{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","maxbetfancy")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="playermaxbetfancy{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","playermaxbetfancy")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="betdelayfancy{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","betdelayfancy")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="minbetBookmaker{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","minbetBookmaker")></select>
                        </div>
						</td>

						<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxbetBookmaker{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","maxbetBookmaker")></select>
                        </div>
						
						</td>
                        <td>
						 <div class="form-group ">
                   			<select class="form-control" name="sport" id="maxbetBookmaker2{{eventid}}" onchange=updateMinMaxBetDelay("{{eventid}}","maxbetBookmaker2")></select>
                        </div>
						</td>	
						<td>
						<div class="form-group">
							<select  class="form-control" id =betdelayBookmaker{{eventid}} onchange=updateMinMaxBetDelay("{{eventid}}","betdelayBookmaker")></select>	
						</div>
						</td>
		

					
                        	
                  	 
                   </tr>
 			{{/each}}        
                </tbody>
              </table>
            
    </script>
    
    
         <script id="fancyHandler" type="text/x-handlebars-template">


<span><h1> Fancy Market 2 <button type="button" id="on_rs_auto_fancy_button"  class="btn" style="display:none;" onclick="AddAutoFancy('RS')">Add AutoFancy</button> <button type="button" id="off_rs_auto_fancy_button"  style="display:none;" class="btn" onclick="AddAutoFancy('RS')">Stop AutoFancy</button></h1> </span>
 <table id="fancyListTable" class="table table-striped table-bordered " style="width:100%">
   
             	 <thead>
                  <tr>
                  <th>Fancy Name</th>
                  
                    <th class="action">Action</th>
<th>active Lib/Market</th>
                   </tr>
                </thead>
                <tbody id="matchbetslist">
  			{{#each data.fancyMarket2}}
  			
                   <tr>
                    <td>{{name}}</td>


                    <td class="text-right">
					{{#if_eq isActive true}}  
					
			{{#if_eq mtype "player"}} 
 					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: green;" >Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','session','{{eventid}}')">Run</a>
		    {{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','player','{{eventid}}')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2green " style="background-color: green;" >Run</a>
		
			{{/if_eq}}			
	{{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','player','RS')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','session','RS')">Run</a>
				
	{{/if_eq}}	
					</td>

					<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxlibpermarket{{removedot fancyid}}" onchange=updateMaxLibPerMarket("{{fancyid}}","{{removedot fancyid}}")></select>
                        </div>
						</td>
                   </tr>
                      
 			{{/each}}        
                </tbody>
              </table>



<h1> Fancy Market 3</h1>
 <table id="fancyListTable" class="table table-striped table-bordered " style="width:100%">
   
             	 <thead>
                  <tr>
                  <th>Fancy Name</th>
                  
                    <th class="action">Action</th>
<th>active Lib/Market</th>
                   </tr>
                </thead>
                <tbody id="matchbetslist">
  			{{#each data.fancyMarket3}}
  			
                   <tr>
                    <td>{{name}}</td>


                    <td class="text-right">
					{{#if_eq isActive true}}  
					
			{{#if_eq mtype "player"}} 
 					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: green;" >Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','session','{{eventid}}')">Run</a>
		    {{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','player','{{eventid}}')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2green " style="background-color: green;" >Run</a>
		
			{{/if_eq}}			
	{{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','player','RS')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','session','RS')">Run</a>
				
	{{/if_eq}}	
					</td>

					<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxlibpermarket{{removedot fancyid}}" onchange=updateMaxLibPerMarket("{{fancyid}}","{{removedot fancyid}}")></select>
                        </div>
						</td>
                   </tr>
                      
 			{{/each}}        
                </tbody>
              </table>
           
<h1>oddEvenMarket</h1>
 <table id="fancyListTable" class="table table-striped table-bordered " style="width:100%">
   
             	 <thead>
                  <tr>
                  <th>Fancy Name</th>
                  
                    <th class="action">Action</th>
<th>active Lib/Market</th>
                   </tr>
                </thead>
                <tbody id="matchbetslist">
  			{{#each data.oddEvenMarket}}
  			
                   <tr>
                    <td>{{name}}</td>


                    <td class="text-right">
					{{#if_eq isActive true}}  
					
			{{#if_eq mtype "player"}} 
 					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: green;" >Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','session','{{eventid}}')">Run</a>
		    {{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','player','{{eventid}}')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2green " style="background-color: green;" >Run</a>
		
			{{/if_eq}}			
	{{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','player','RS')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','session','RS')">Run</a>
				
	{{/if_eq}}	
					</td>

					<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxlibpermarket{{removedot fancyid}}" onchange=updateMaxLibPerMarket("{{fancyid}}","{{removedot fancyid}}")></select>
                        </div>
						</td>
                   </tr>
                      
 			{{/each}}        
                </tbody>
              </table>
           

<h1> ballByBallMarket</h1>
 <table id="fancyListTable" class="table table-striped table-bordered " style="width:100%">
   
             	 <thead>
                  <tr>
                  <th>Fancy Name</th>
                  
                    <th class="action">Action</th>
<th>active Lib/Market</th>
                   </tr>
                </thead>
                <tbody id="matchbetslist">
  			{{#each data.ballByBallMarket}}
  			
                   <tr>
                    <td>{{name}}</td>


                    <td class="text-right">
					{{#if_eq isActive true}}  
					
			{{#if_eq mtype "player"}} 
 					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: green;" >Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','session','{{eventid}}')">Run</a>
		    {{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','player','{{eventid}}')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2green " style="background-color: green;" >Run</a>
		
			{{/if_eq}}			
	{{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','player','RS')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','session','RS')">Run</a>
				
	{{/if_eq}}	
					</td>

					<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxlibpermarket{{removedot fancyid}}" onchange=updateMaxLibPerMarket("{{fancyid}}","{{removedot fancyid}}")></select>
                        </div>
						</td>
                   </tr>
                      
 			{{/each}}        
                </tbody>
              </table>
           
           


           



    </script>
           
           
                    <script id="fancyHandlerDiamond" type="text/x-handlebars-template">

           

<span><h1> Diamond Fancy  <button type="button" id="on_diamond_auto_fancy_button"  class="btn" style="display:none;" onclick="AddAutoFancy('Diamond')">Add AutoFancy</button> <button type="button" id="off_diamond_auto_fancy_button"  style="display:none;" class="btn" onclick="AddAutoFancy('Diamond')">Stop AutoFancy</button></h1> </span>
<table id="fancyListTable" class="table table-striped table-bordered " style="width:100%">
   
             	 <thead>
                  <tr>
                  <th>Fancy Name</th>
                  
                    <th class="action">Action</th>
<th>active Lib/Market</th>
                   </tr>
                </thead>
                <tbody id="matchbetslist">
  			{{#each data.Daimonfancy}}
  			
                   <tr>
                    <td>{{name}}</td>


                    <td class="text-right">
					{{#if_eq isActive true}}  
					
			{{#if_eq mtype "player"}} 
 					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: green;" >Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','session','{{eventid}}')">Run</a>
		    {{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2 " style="background-color: red;" onclick="switchFancy('{{fancyid}}','player','{{eventid}}')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2green " style="background-color: green;" >Run</a>
		
			{{/if_eq}}			
	{{else}}
					<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','player','Diamond')">Player</a>
				 	<a href="javascript:void(0);" class="btn btn-info blue-btn btn-sm mr-2 mb-2" onclick="saveFancy('{{ids}}','{{ids}}','{{eventid}}','session','Diamond')">Run</a>
				
	{{/if_eq}}	
					</td>

					<td>
						<div class="form-group ">
                   			<select class="form-control" name="sport" id="maxlibpermarket{{removedot fancyid}}" onchange=updateMaxLibPerMarket("{{fancyid}}","{{removedot fancyid}}")></select>
                        </div>
						</td>
                   </tr>
                      
 			{{/each}}        
                </tbody>
              </table>
           



    </script>
   
    
 <div class="account-container client-record pt-2">

         <div class="account-record market-result">
         
           <div class="acont-box">
             <span class="acont-left ">
                <h4>Market Result</h4>
             </span>
           <form id="activematcheslist">
           	 <div class="form-row">
                   <div class="form-group col-sm-4">
                       <label for="inputState">Sport</label>
                       <select id="sport" class="form-control" name="sport"> </select>
                     </div>
					 <div class="col-sm-2 col-lg-1 mb-2 mb-sm-0">
						 <div class="acont-right w-100">
							<label class="d-none d-sm-block">&nbsp;</label>
							<button type="button" id="back_button" style="display:none;" class="btn">Back</button>
						</div>
					</div>
					<div class="col-sm-2 col-lg-1 mb-3 mb-sm-0">
						<div class="acont-right w-100">
							<label class="d-none d-sm-block">&nbsp;</label>
							<button type="button" id="refresh_button" style="display:none;" class="btn">Refresh</button>
						</div>
					</div>
				
               </div>
           </form>
           </div>
           
            <div class="account-table table-responsive" id=activematch></div>
            
            
              	 <div class="form-row">
                 
					 <div class="form-group col-sm-6">
						 <div class="acont-right w-100">
							<label class="d-none d-sm-block">&nbsp;</label>
							<button type="button" id="back_button" style="display:none;" class="btn">Back</button>
						</div>
					</div>
					
				
               </div>
            
            <div class ="form-row">
             <div class="form-group col-sm-6" id=fancyMarket2></div>
              <div class="form-group col-sm-6" id=dfancyMarket> </div></div>
             <div class="account-table table-responsive" id=fancyMarket3></div>
              <div class="account-table table-responsive" id=oddEvenmarket></div>
               <div class="account-table table-responsive" id=ballByMarket></div>
         </div>
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
	     getActiveMatchList("Match Odds", true,'4');
	     getActiveSports(true);
	     $('body').on('click','#back_button',function(){
	    
	    	 $("#back_button").hide();
	    	 $("#fancyMarket2").hide();
	    	 $("#dfancyMarket").hide();
	    	 $("#activematch").show();
	    	 $("#refresh_button").hide();
	     });

	     $('body').on('click','#refresh_button',function(){
	 	    
	    	 loadFancyList($("#eventId").val(),'Diamond')
	 	    
	     });
	     $("#sport").change(function(){
	    	 getActiveMatchList("Match Odds", true,$("#sport").val());
	     });
	 });

    function AddAutoFancy(fancyType){

   	 $.ajax({
			type:'POST',
			url:'<s:url value="api/updateAddAutoFancy"/>/'+$("#eventId").val()+'/'+fancyType,
			
			
			contentType:"application/json; charset=utf-8",
			success: function(jsondata, textStatus, xhr) {
				
				showMessage(jsondata.message,jsondata.type,jsondata.title);
				 showHideAddAutoFancyButton($("#eventId").val())
			},
			complete: function(jsondata,textStatus,xhr) {
				showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				 showHideAddAutoFancyButton($("#eventId").val())
				
		    } 
		});
		
		  
        }



    function showHideAddAutoFancyButton(eventId) {

       
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getFancyProvider/"/>'+eventId,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {

				
					if(jsondata.addautoFancy){
						console.log("Fancy Provider==>"+jsondata.addautoFancy)
						
							$("#on_rs_auto_fancy_button").hide()
						    $("#off_rs_auto_fancy_button").show()
						}else{
							console.log("Fancy Provider==>"+jsondata.addautoFancy)
							
							$("#on_rs_auto_fancy_button").show()
							$("#off_rs_auto_fancy_button").hide()
							}

					if(jsondata.addautoFancyDiamond){
						$("#on_diamond_auto_fancy_button").hide()
						$("#off_diamond_auto_fancy_button").show()
						}else{
							$("#on_diamond_auto_fancy_button").show()
							$("#off_diamond_auto_fancy_button").hide()
					}
					
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
   }
   

    function getActiveMatchList(marketname,isactive,sportId)
    {
         $("#backtoActiveMatch").hide();
         $("#activematchbets").hide();
         $("#suspendBets").hide();
         $("#myInput").show();
   		 $("#activematch").show();
 	     $("#activematch").html('');	
 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getMatchesList/"/>'+sportId,
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
	    					    	  $("#activematch").append( template({data:jsondata}));
	    					    	  $("#fancyListTable").DataTable();


	    					    	   $.each(jsondata, function( key, value ) {
	    					    		  getMinBet("minbet",value.eventid,value.minbet,value.minbetfancy,value.maxbetBookmaker);
	    					    		  getMaxBet("maxbet",value.eventid,value.maxbet,value.maxbetfancy,value.maxbetBookmaker,value.playermaxbetfancy,value.maxbetBookmaker2);
	    					    		  getBetDelay("betdelay",value.eventid,value.betdelay,value.betdelayfancy,value.betdelayBookmaker); 
	    					    		  getFancyProvider(value.eventid); 
	    					    			
	    					    		 // lo	var i=0;adCricExchMatches(value.cricexchageId,value.eventid);
	    					    	}); 

	    					    	   
	    					    	
	    						}else{
	    							$("#noActiveMatch").show();
	    							$("#activematch").hide();
	    						}
	    						
	    					}
	    				}
	    			});
    }
    
    
    function loadFancyList(eventid,fancyprovider) {
    	$( "#fancyMarket2" ).show();
	  var data={eventid:eventid}
		 $("#betfaircomp").hide();
		 $("#betfairseries").hide();
		 $("#betfairevents").hide();
         $("#betfairmarket").hide();
         $("#lotusmatch").hide();
          $("#fancylist").html('');
         $("#fancylist").show();
         $("#fancyprovider").val(fancyprovider);
	     $("#activematch").hide();
	     $("#back_button").show();
	     $("#eventId").val(eventid)
	     $("#refresh_button").show();
	     $("#activematcheslist").append('<div class="loader"></div>')
	    $.when(	   
	    	$.ajax({
   			type:'GET',
   			url:'<s:url value="/api/getLotusEventList?eventid="/>'+eventid+"&fancyprovider="+fancyprovider,
  			success: function(jsondata, textStatus, xhr) {
  			           $( "#fancyMarket2" ).empty();
  			           $( "#dfancyMarket" ).empty();
    					var source   = $("#fancyHandler").html();
    					var source1   = $("#fancyHandlerDiamond").html();
    			    	  var template = Handlebars.compile(source);
    			    	  var template1 = Handlebars.compile(source1);
   				    	  $("#fancyMarket2").append( template({data:jsondata}) );
   				       	  $("#dfancyMarket").append( template1({data:jsondata}) );
   				    	  
						  $.each(jsondata.fancyMarket2, function( key, value ) {
   				    		getMaxBetLib(value.fancyid.split('.').join(""),value.maxLiabilityPerMarket)
   				    	 });
						
						  $.each(jsondata.fancyMarket3, function( key, value ) {
	   				    		getMaxBetLib(value.fancyid.split('.').join(""),value.maxLiabilityPerMarket)
	   				    	 });

						  $.each(jsondata.oddEvenMarket, function( key, value ) {
	   				    		getMaxBetLib(value.fancyid.split('.').join(""),value.maxLiabilityPerMarket)
	   				    	 });

						  $.each(jsondata.ballByBallMarket, function( key, value ) {
	   				    		getMaxBetLib(value.fancyid.split('.').join(""),value.maxLiabilityPerMarket)
	   				    	 });

						  $.each(jsondata.Daimonfancy, function( key, value ) {
	   				    		getMaxBetLib(value.fancyid.split('.').join(""),value.maxLiabilityPerMarket)
	   				    	 });
						 
   				    	$(".loader").fadeOut("slow");
   				    	
    		}
   		
    	})

    	).then(function( x ) {
    		 showHideAddAutoFancyButton($("#eventId").val());		
	  });;
	    	
	 }
    
    function saveFancy(fancyid,id,eventid,mtype,provider){		
    	   	
    	    	 $.ajax({
    					type:'POST',
    					url:'<s:url value="api/saveLotusFancy"/>?id='+id+'&eventid='+eventid+"&fancyid="+fancyid+"&skyfancyid="+0+"&mtype="+mtype+"&provider="+provider,
    					
    					
    					contentType:"application/json; charset=utf-8",
    					success: function(jsondata, textStatus, xhr) {
    						
    						showMessage(jsondata.message,jsondata.type,jsondata.title);
    						loadFancyList(eventid,'Diamond');
    					},
    					complete: function(jsondata,textStatus,xhr) {
    						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
    						loadFancyList(eventid,'Diamond')
    				    } 
    				});
    				
    				
    	    
    	    
    }
    function switchFancy(fancyid,mtype,eventid){		
	   	
   	 $.ajax({
				type:'POST',
				url:'<s:url value="api/switchFancy"/>?fancyid='+fancyid+"&mtype="+mtype,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
					showMessage(jsondata.message,jsondata.type,jsondata.title);
					loadFancyList(eventid,'Diamond');
				},
				complete: function(jsondata,textStatus,xhr) {
					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					loadFancyList(eventid,'Diamond')
			    } 
			});
			
			
   
   
}


    function updateMaxLibPerMarket(fancyid,id){		

	   	
      	 $.ajax({
   				type:'POST',
   				url:'<s:url value="api/updateMaxLibPerMarket"/>?fancyid='+fancyid+"&Lib="+$("#maxlibpermarket"+id).val(),
   				
   				
   				contentType:"application/json; charset=utf-8",
   				success: function(jsondata, textStatus, xhr) {
   					
   					showMessage(jsondata.message,jsondata.type,jsondata.title);
   					loadFancyList(eventid,'Diamond');
   				},
   				complete: function(jsondata,textStatus,xhr) {
   					showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
   					loadFancyList(eventid,'Diamond')
   			    } 
   			});
   			
   			
      
      
   }
    
    function getActiveSports(status){		
	    $.ajax({
					type:'GET',
					url:'<s:url value="api/getActiveSportList"/>?status='+status,
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
			
				if(xhr.status == 200){  
				 $("#sport").html('');
				$("#sport").append($('<option value=""> Select Sport</a> </option>'));
					  $.each(jsondata, function(index, element) {
				 
		             $("#sport").append($('<option id = "'+element.sportId+'" value="'+element.sportId+'" name = "'+element.name+'"  > '+element.name+' </option>', {
		             			                
		            }));
		            			            			       
    			});
			    	 
				}
				
			
		}
	});
	    
 }

    function getFancyProvider(eventId) {

    
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getFancyProvider/"/>'+eventId,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {

					console.log("Fancy Provider==>"+jsondata.addautoFancy)
				 
						if(jsondata.fancyprovider =="RS"){
							  $("#fancyprovider"+eventId).append('<option value="RS" selected >RS</option><option value="BOTH">BOTH</option><option value="Diamond">Diamond</option>')
						}else if(jsondata.fancyprovider =="Diamond"){
							  $("#fancyprovider"+eventId).append('<option value="RS"  >RS</option><option value="BOTH">BOTH</option><option value="Diamond" selected>Diamond</option>')
								   
						}  
						else if(jsondata.fancyprovider =="BOTH" || jsondata.fancyprovider =="Both"){
							  $("#fancyprovider"+eventId).append('<option value="RS"  >RS</option><option value="BOTH" selected>BOTH</option><option value="Diamond" >Diamond</option>')
								   
						}  
				
					

				
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
    }

    function getMinBet(type,eventid,minbet,minbetfancy,minbetBookmaker) {

    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
					
						if(minbet ==value.amount){
							  $("#minbet"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#minbet"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
							   
						}  
				
						if(minbetfancy ==value.amount){
							  $("#minbetfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							  $("#minbetfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						} 

						if(minbetBookmaker ==value.amount){
							  $("#minbetBookmaker"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							  $("#minbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						} 

						
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
    }
    function getMaxBet(type,eventid,maxbet,maxbetfancy,maxbetBookmaker,playermaxbetfancy,maxbetBookmaker2) {


 	
    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
						if(maxbet ==value.amount){
							  $("#maxbet"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
							  
						}else{
							   $("#maxbet"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
							 
						} 

						if(maxbetfancy ==value.amount){
							  $("#maxbetfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#maxbetfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						}   
						if(maxbetBookmaker ==value.amount){
							  $("#maxbetBookmaker"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#maxbetBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						}

						if(maxbetBookmaker2 ==value.amount){
							  $("#maxbetBookmaker2"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#maxbetBookmaker2"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						}
						if(playermaxbetfancy ==value.amount){
							  $("#playermaxbetfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#playermaxbetfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						}

						
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
   }
    
    function getBetDelay(type,eventid,betdelay,betdelayfancy,betdelayBookmaker) {
console.log(betdelayBookmaker)
    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type='+type,
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
						if(betdelay ==value.amount){
							  $("#betdelay"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
							  
						}else{
							   $("#betdelay"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
							    
						}  
						if(betdelayfancy ==value.amount){
						       $("#betdelayfancy"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{
							   $("#betdelayfancy"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						} 

						if(betdelayBookmaker ==value.amount){
							console.log(value.amount)
							       $("#betdelayBookmaker"+eventid).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
						}else{

								   $("#betdelayBookmaker"+eventid).append('<option value="'+value.amount+'">'+value.amount+'</option>')
						} 
						
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
  }

    function updateMinMaxBetDelay(eventid,type){
	      var text =""
	      var url ="";

	    	if(type=="minbet"){
	    		text='You Want to Change Min Bet Amount!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=minbet&amount="+$("#minbet"+eventid).val();
	    	}else if(type=="maxbet"){
	    		text='You Want to Change Max Bet Amount!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=maxbet&amount="+$("#maxbet"+eventid).val();
	    	}else if(type=="betdelay"){
	    		text='You Want to Change Bet Delay Time!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=betdelay&amount="+$("#betdelay"+eventid).val();
	    	}else if(type=="maxbetfancy"){
	    		text='You Want to Change Bet Delay Time!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=maxbetfancy&amount="+$("#maxbetfancy"+eventid).val();
	    	}else if(type=="betdelayfancy"){
	    		text='You Want to Change Bet Delay Time!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=betdelayfancy&amount="+$("#betdelayfancy"+eventid).val();
	    	}else if(type=="maxbetBookmaker"){
	    		text='You Want to Change Bet Delay Time!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=maxbetBookmaker&amount="+$("#maxbetBookmaker"+eventid).val();
	    	}
	    	else if(type=="playermaxbetfancy"){
	    		text='You Want to Change Max Bet Amount!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=playermaxbetfancy&amount="+$("#playermaxbetfancy"+eventid).val();
	    	}
	    	else if(type=="maxbetBookmaker2"){
	    		text='You Want to Change Max Bet Amount!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=maxbetBookmaker2&amount="+$("#maxbetBookmaker2"+eventid).val();
	    	}	
	    	else if(type=="betdelayBookmaker"){
	    		text='You Want to Change Max Bet Amount!';
	    		url ='<s:url value="/api/updateMinMaxBetdelayEvent"/>?eventid='+eventid+"&type=betdelayBookmaker&amount="+$("#betdelayBookmaker"+eventid).val();
	    	}
	    	
	    	
	    swal({
  	      title: 'Are you sure?',
  	      text: text,
  	      type: 'warning',
  	      showCancelButton: true,
  	      confirmButtonColor: '#3085d6',
  	      cancelButtonColor: '#d33',
  	      confirmButtonText: 'Yes',
  	      closeOnConfirm: false
  	    }).then(function(isConfirm) {
  	      if (isConfirm) {
  	    	  $("#lotusmatch").append('<div class="loader"></div>')
  	    	  $.ajax({
  					type:'POST',
  					url:url,
  					success: function(jsondata, textStatus, xhr) {
  					$(".loader").fadeOut("slow");
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  					},
  					complete: function(jsondata,textStatus,xhr) {
  					$(".loader").fadeOut("slow");
  						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
  						
  				    } 
  				});
  	    	
  			} 
  	         else{
  	        	 $.ajax({
  				   	 
						type:'GET',
						url:'<s:url value="/api/getevent"/>?eventid='+eventid,
						contentType:"application/json; charset=utf-8",
						success: function(jsondata, textStatus, xhr) {
						
							
						
							if(type=="minbet"){
								$("#minbet"+eventid).val(jsondata.minbet)
							}else if(type=="maxbet"){
								$("#maxbet"+eventid).val(jsondata.max)
							}else if(type=="betdelay"){
								$("#betdelay"+eventid).val(jsondata.betdelay)
							}
						},
						complete: function(jsondata,textStatus,xhr) {
							
					    } 
					});    
  				 }
  		});
	    			   	 
	    	
	    }

    function loadCricExchMatches(cricexchageId,eventid){
    	
            $.ajax({
    		type:'GET',
    		url:'<s:url value="api/cricketExchMatch"/>',
    		contentType:"application/json; charset=utf-8",
    		success: function(jsondata, textStatus, xhr) {
						if(xhr.status == 200){  
							$("#cricexchmatch"+eventid).append('<option value="-1" >Select Match</option>')
							 
							  $.each(jsondata, function( key, value ) { 
							 
								if(cricexchageId == value.cricexchageId){
						             $("#cricexchmatch"+eventid).append('<option value="'+value.cricexchageId+'" selected>'+value.eventname+'</option>')
						             console.log(value.cricexchageId);
							}else{
								   $("#cricexchmatch"+eventid).append('<option value="'+value.cricexchageId+'">'+value.eventname+'</option>')
								    
							} 
						});
				     }
          	}
    	    
        });
    }

    function updateFancyProvider(eventId){


	   	 swal({
title: 'Are you sure ',
text: 'You Want to sun Score?',
type: 'warning',
showCancelButton: true,
confirmButtonColor: '#3085d6',
cancelButtonColor: '#d33',
confirmButtonText: 'Yes',
closeOnConfirm: false
}).then(function(isConfirm) {
if (isConfirm) {
$("#lotusmatch").append('<div class="loader"></div>');
$.ajax({
		type:'POST',
		url:'<s:url value="/api/updateFancyProvider/"/>'+eventId+"/"+$("#fancyprovider"+eventId).val(),
		success: function(jsondata, textStatus, xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.message,jsondata.type,jsondata.title);
		},
		complete: function(jsondata,textStatus,xhr) {
		$(".loader").fadeOut("slow");
			showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
			
	    } 
	});

} 
else{
	
	 }
});
}


    function updateLiveTv(eventId,tvStatus){
    	 $("#lotusmatch").append('<div class="loader"></div>')
        $.ajax({
		type:'POST',
		url:'<s:url value="api/TvOnOff/"/>'+tvStatus+"/"+eventId,
		contentType:"application/json; charset=utf-8",
		success: function(jsondata, textStatus, xhr) {
					if(xhr.status == 200){  
						$(".loader").fadeOut("slow");
  						showMessage(jsondata.message,jsondata.type,jsondata.title);
  						getActiveMatchList("Match Odds", true,'4');
			     }
      	}
	    
    });
}



    function getMaxBetLib(Id,maxlibpermarket) {

    	//alert(Id)
        console.log(maxlibpermarket)
     	
    	var i=0;
	 	 $.ajax({
	   	 
				type:'GET',
				url:'<s:url value="/api/getMinMaxBetSetAmount"/>?type=maxbet',
				contentType:"application/json; charset=utf-8",
				success: function(jsondata, textStatus, xhr) {
					
				   $.each(jsondata, function( key, value ) {
						if(maxlibpermarket ==value.amount){
							  $("#maxlibpermarket"+Id).append('<option value="'+value.amount+'" selected>'+value.amount+'</option>')
							  
						}else{
							   $("#maxlibpermarket"+Id).append('<option value="'+value.amount+'">'+value.amount+'</option>')
							 
						} 

							 
						
				});
				},
				complete: function(jsondata,textStatus,xhr) {
					
			    } 
			});     	    	
   } 

    
    </script>
    
    <input type="hidden" id="eventId">
    <input type="hidden" id="fancyproviderHidden">
    <jsp:include page="handlebars.jsp" />  
    