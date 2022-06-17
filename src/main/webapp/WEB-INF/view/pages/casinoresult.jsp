<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!--************** start ***************** -->

 <script id="casinoresultHandler" type="text/x-handlebars-template">

	    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Round Id</th>
                       <th>Winner</th>
                    </tr>
               </thead>
               <tbody>

                 
                 {{#each data}}
                   <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDeatil2({{mid}})">{{mid}}</a></td>
                       <td>{{winner}}</td>
                       
                   </tr>
                 {{/each}} 
                 </tbody>
			 </table>

</script>


 <script id="lotuscasinoresultHandler" type="text/x-handlebars-template">

	    <table id="myTable" class="table table-striped table-bordered account-stment" style="width:100%">
       
               <thead>
                   <tr>
                       <th>Round Id</th>
                       <th>Winner</th>
                    </tr>
               </thead>
               <tbody>

                 
                 {{#each data}}
                  {{#if_eq gameId "5047"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{#if_eq winner "1"}} player A {{/if_eq}} {{#if_eq winner "2"}} player B {{/if_eq}}</td>
                 {{/if_eq}}

 				 {{#if_eq gameId "5048"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{#if_eq winner "1"}} player A {{/if_eq}} {{#if_eq winner "2"}} player B {{/if_eq}}</td>
                 {{/if_eq}}

				 {{#if_eq gameId "5017"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.rdesc}}</td>
                 {{/if_eq}}
 				
				{{#if_eq gameId "5036"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.rdesc}}</td>
                 {{/if_eq}}


				{{#if_eq gameId "5037"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.desc}}</td>
                 {{/if_eq}}

				 {{#if_eq gameId "5026"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.desc}}</td>
                 {{/if_eq}}

				 {{#if_eq gameId "5027"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.desc}}</td>
                 {{/if_eq}}

				 {{#if_eq gameId "5077"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.desc}}</td>
                 {{/if_eq}}

				 {{#if_eq gameId "5076"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>{{data.desc}}</td>
                 {{/if_eq}}

 				  {{#if_eq gameId "5056"}}
					  {{#if_neq roundId "0"}}
					 <tr> 
                       <td><a href="javascript:void(0)" onclick="resultDetailLiveCasino({{roundId}})">{{roundId}}</a></td>
                       <td>ab20</td>
					 </tr>
					{{/if_neq}}	
                 {{/if_eq}}

                  
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
						 <label for="inputState">Date</label>						 
						 <div class="input-group">
							<input class="datepicker form-control" id="startDate" style="border-radius: 5px 0 0 5px;">
							<div class="input-group-append">
								<span class="fa fa-calendar-o input-group-text" style="border-radius: 0 5px 5px 0;"></span>
							</div>
						</div>
					</div>
					


					<div class="form-group col-md-3" style="margin-top: 30px;">
						<button class="btn btn-primary" onclick="casinoResultReport()">Load</button>
					</div>
				</div>
			</div>
         
         <%-- <div class="acont-search">
           <span class="search-left "><button class="btn btn-danger">PDF</button> </span>
           <span class="search-left  "><button class="btn btn-success">Excel</button></span>
         </div>
 --%>
         
           <div class="account-table bethistory table-responsive" id="report">
          </div>

          

       </div>
       </div>
       
  


</div>


<script>
              $(document).ready(function(){
            	  var now = new Date();
            	
				var day = ("0" + now.getDate()).slice(-2);
            	var month = ("0" + (now.getMonth() + 1)).slice(-2);
            	
            	var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
            	
            	 var date = new Date();
                 var startdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                 

            	 $('#startDate').datepicker({
            			format: "yyyy-mm-dd",
            			autoclose: true,
            			todayHighlight: true
            			
             });
            	
            	 $('#startDate').datepicker('setDate', startdate);
            	
            	 getCasinoList();
            });
              
              function getCasinoList()
              {	  	    
              	  var startDate = $("#startDate").val()+" 00:00:00";
              	  var endDate = $("#endDate").val()+" 23:59:59";
              	  
              	  
                  
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
              				 
              			}
              		}
              	});
              }

              function casinoResultReport() {	 
					$.ajax({
              			type:'GET',
              			url:'<s:url value="/api/casinoresult/"/>'+$("#game_list").val()+"/"+$("#startDate").val(),
              			contentType:"application/json; charset=utf-8",
              			success: function(jsondata, textStatus, xhr) {
              				$("#report").html('');
              				if(xhr.status == 200){
                               var matchId  =$("#game_list").val()
              					
              					if(matchId =="5047" || matchId =="5017" || matchId =="5036" ||  matchId =="5048" ||  matchId =="5037" || matchId =="5026" ||  matchId =="5027" ||  matchId =="5076" ||  matchId =="5077"  ||  matchId =="5056" ){
              						$("#report").html();
                  					var source   = $("#lotuscasinoresultHandler").html();
      	    			    	    var template = Handlebars.compile(source);
      	   				    	    $("#report").append( template({data:JSON.parse(jsondata).data}));
    								$('#myTable').DataTable({
    									"lengthMenu": [ 50, 75, 100 ]
    								});		
                  				}else{
                  					$("#report").html();
                  					var source   = $("#casinoresultHandler").html();
      	    			    	    var template = Handlebars.compile(source);
      	   				    	    $("#report").append( template({data:JSON.parse(jsondata).data}));
    								$('#myTable').DataTable({
    									"lengthMenu": [ 50, 75, 100 ]
    								});		
                      				}
              											
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
			 
         </script>
		 
         <jsp:include page="resultdetail_casinoresultreport.jsp" />
		 <jsp:include page="handlebars.jsp" />  
