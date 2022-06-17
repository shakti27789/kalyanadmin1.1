<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <script id="sporttabHandler" type="text/x-handlebars-template">
	<!--<div class="panel panel-default" style="margin-top: 10px;">
			<div class="panel-heading" style="background: #474747;color: white;">
			  <h4 class="panel-title">
				<a class="" data-toggle="collapse" data-parent="#accordion"  onclick="activeMarketBySportId('{{sportId}}')"  href="#collapse{{sportId}}">
				Inplay
				</a>
			  </h4>
			</div>
			<div id="collapse{{sportId}}" class="panel-collapse collapse">
			<div class="panel-body events-rows" id="mobile{{sportId}}">
	</div>
	 </div>
	</div> -->


{{#each data}}  	
    <input type="hidden" class="sport_id" value="{{sportId}}">
	   

     
	<div class="panel panel-default" style="margin-top: 10px;">
			<div class="panel-heading" style="background: #474747;color: white;">
			  <h4 class="panel-title">
				<a class="" data-toggle="collapse" data-parent="#accordion"  onclick="activeMarketBySportId('{{sportId}}')"  href="#collapse{{sportId}}">
				{{name}}
				</a>
			  </h4>
			</div>
			<div id="collapse{{sportId}}" class="panel-collapse collapse">
			<div class="panel-body events-rows" id="mobile{{sportId}}">
	</div>
	 </div>
	</div>
{{/each}}
  </script>
  <script id="sportoddsHandler" type="text/x-handlebars-template">
{{#each data}} 
<br>
  <div class="markets">
            <div class="row no-gutters bookmaker-market">
                <div class="col-md-12">
                    <div class="market-title">
                       <a href="<s:url value="/innerExpo"/>/{{sportid}}/{{eventid}}/{{marketid}}"><span> {{matchname}}  </span>
                        <span>{{date}}</span></a>
                     </div>
                    <!-- <div class="table-header">
                        <div class="text-info">
                            <span>Runners</span> </b>
                        </div>
                        <div class="box-info box-1 text-center"></div>
                        <div class="box-info box-1   text-center"></div>
                        <div class="box-info box-1 back text-center">Back</div>
                        <div class="box-info box-1 lay text-center">Lay</div>
                        <div class="box-info box-1  text-center"></div>
                        <div class="box-info box-1  text-center"></div>
                     </div> -->
                     <!--<div class="table-body">
                        <div class="table-row suspended" data-title="SUSPENDED">
                            <div class="text-info country-name  ">
                                <span class="team-name"><b>Markaryds</b></span>
                                <p>
                                    <span  style="color: black;">0</span>
                                    <span  style="color: black;display: none;">0</span>
                                </p>
                            </div>
                            <div class="box-info box-2  text-center"><Span>100</Span><br><Span>10</Span></div>
                            <div class="box-info box-2  text-center"><Span>200</Span><br><Span>20</Span></div>
                            <div class="box-info box-2 back text-center" onclick="backFunction()"><Span> <b> 100</b></Span><br><Span>10</Span></div>
                            <div class="box-info box-2 lay  text-center" onclick="layFunction()"><Span> <b> 200</b></Span><br><Span>20</Span></div>
                            <div class="box-info box-2  text-center"><Span>300</Span><br><Span>30</Span></div>
                            <div class="box-info box-2  text-center"><Span>400</Span><br><Span>40</Span></div>

                        </div>
                        <div class="table-row suspended" data-title="SUSPENDED">
                            <div class="text-info country-name  ">
                                <span class="team-name"><b> Delary/Pjatteryd </b></span>
                                <p>
                                    <span  style="color: black;">0</span>
                                    <span  style="color: black;display: none;">0</span>
                                </p>
                            </div>
                              <div class="box-info box-2  text-center"><Span>100</Span><br><Span>10</Span></div>
                              <div class="box-info box-2  text-center"><Span>200</Span><br><Span>20</Span></div>
                              <div class="box-info box-2 back text-center"><Span>100</Span><br><Span>10</Span></div>
                              <div class="box-info box-2 lay  text-center"><Span>100</Span><br><Span>10</Span></div>
                              <div class="box-info box-2  text-center"><Span>300</Span><br><Span>30</Span></div>
                              <div class="box-info box-2  text-center"><Span>400</Span><br><Span>40</Span></div>

                        </div>
                        <div class="table-row suspended" data-title="SUSPENDED">
                          <div class="text-info country-name  ">
                              <span class="team-name"><b>The Draw</b></span>
                              <p>
                                  <span  style="color: black;">0</span>
                                  <span  style="color: black;display: none;">0</span>
                              </p>
                          </div>
                          <div class="box-info box-2  text-center"><Span>100</Span><br><Span>10</Span></div>
                          <div class="box-info box-2  text-center"><Span>200</Span><br><Span>20</Span></div>
                          <div class="box-info box-2 back text-center" onclick="backFunction()"><Span> <b> 100</b></Span><br><Span>10</Span></div>
                          <div class="box-info box-2 lay  text-center" onclick="layFunction()"><Span> <b> 200</b></Span><br><Span>20</Span></div>
                          <div class="box-info box-2  text-center"><Span>300</Span><br><Span>30</Span></div>
                          <div class="box-info box-2  text-center"><Span>400</Span><br><Span>40</Span></div>

                      </div> -->
                     </div>

                </div>
               
             </div>
        </div>
<br>
{{/each}}
  </script>
  <div class="row no-gutters details-box ">
    <div class="col-md-12">
        
        
          
		<div class="panel-group" id="accordion"></div>

       
    
    </div>
    <input type="hidden" id="sportids">
    </div>
   
   <script>
   $(document).ready(function(){
		
	   activeSport();
	});
     function activeSport(){
    	 $.ajax({
 			type:'GET',
 			url:'<s:url value="/api/activeSportByAdmin"/>',
 			success: function(jsondata, textStatus, xhr) {
 				if(xhr.status == 200)
 				{ 
 					    $("#accordion").html('');
						var source   = $("#sporttabHandler").html();
			    	    var template = Handlebars.compile(source);
				    	$("#accordion").append( template({data:jsondata}));
				    	$("#sportids").val($('.sport_id').map((i, e) => e.value).get())
				    	//activeMarketBySportId("onLoad");
		
				    	
 	}
     }
   });
  }	
     
  function activeMarketBySportId(sportId) {
	 
	  $.ajax({
			type:'GET',
			url:'<s:url value="/api/getEventRates?sportid="/>'+sportId,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200)
				{ 
					    
					 var source   = $("#sportoddsHandler").html();
			    	 var template = Handlebars.compile(source);
				     $("#mobile"+sportId).append( template({data:jsondata})); 
				
				    	
				}
   }
 });
}  
  
  
   </script>
  