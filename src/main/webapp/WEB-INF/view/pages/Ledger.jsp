<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script id="ledgerHandler" type="text/x-handlebars-template">

<div class="header-line">
                   <h4>PAYMENT RECEIVING FROM (LENA HE)</h4>
                   <div class="bottom-line"></div>    
 <table id="myTable" class="table table-striped table-bordered " style="width:100%">
       
               <thead>
                   <tr>
                       <th>Client Name</th>
                       <th> Open Bal.</th>
                       <th> curr Bal.</th>
                       <th>Cls Bal. </th>
                       
                   </tr>
               </thead>
               <thead class="bootom-head ">
                   <tr class="border border-0">
                       <th>Total</th>
                       <th id="totalAmount">0.00</th>
                       <th>0.00</th>
                       <th id="totalOsAmount">0</th>
                       
                   </tr>
               </thead>
              
               <tbody>
		{{#each data}}              
  				 <tr>
                   <td>{{username}}</td>
                   <td>{{openbal}}</td>
                   <td>{{currentbal}}</td>
                   <td>{{closebal}}</td>   
               </tr>
		{{/each}}
             
                  
              </tbody>
           </table>
</script>

<script id="denaledgerHandler" type="text/x-handlebars-template">
       <div class="header-line"> 
                   <h4>PAYMENT PAID TO (DENA HE)</h4>
                   <div class="bottom-line"></div>
	<table id="myTable1" class="table table-striped table-bordered " style="width:100%">
       
                 <thead>
                   <tr>
                       <th>Client Name</th>
                       <th> Open Bal.</th>
                       <th> curr Bal.</th>
                       <th>Cls Bal. </th>
                       
                   </tr>
               </thead>
               <thead class="bootom-head ">
                   <tr class="border border-0">
                       <th>Total</th>
                       <th id="denatotalAmount">0.00</th>
                       <th>0.00</th>
                       <th id="denatotalOsAmount">0</th>
                       
                   </tr>
               </thead>
              
               <tbody>
		{{#each data}}              
  				 <tr>
                   <td>{{username}}</td>
                   <td>{{openbal}}</td>
                   <td>{{currentbal}}</td>
                   <td>{{closebal}}</td>   
               </tr>
		{{/each}}
           </table>
</script>


<!-- ************ start  ****************** -->

<div class="cliennt-container">
       
    
       <div class="account-container client-record pt-2">

         <div class="account-record market-result Ledger">
         
           <div class="acont-box">
             <span class="acont-left ">
                <h4>Ledger</h4>
             </span>
             
           <div class="formrow ">
               <div class="form-left">
                   <div class="form-group ">
                       <select id="ledgertype" class="form-control">
                              <option value="D"> Down Line </option>
							  <option value="U">Up Line </option>
                       </select>
                   </div>
               </div>
           </div>
         </div>


         <!-- **********  First Table *************** -->
           <div class="account-table table-responsive ledger-table" id="ledgerTable">
              
           </div>
              
       
       

           <!-- *********** First table end  ************* -->

           <!-- ********** Second Table   ************* -->
           <div class="account-table table-responsive ledger-table" id="denaledgerTable">
        
           </div>
              
           

           </div>

           <!-- ************* second Table end ******************* -->

          

       </div>
       </div>
       
  


</div>

 <!-- ************  end  ******************** -->

  <script>
  
  $(document).ready(function(){
	  getLedger();

	  $("#ledgertype").change(function(){
   		getLedger();
      }); 
   });
  function getLedger(){
  	var ledgertype = $("#ledgertype").val();
  	$("#page-wrapper").append('<div class="loader"></div>')
  	$.ajax({
			type:'GET',
			url:'<s:url value="api/userLedger?parentid="/>'+'${user.id}'+"&type="+ledgertype,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status==200){
				
					$("#ledgerTable").html('');
					$("#denaledgerTable").html('');
					var obj = jsondata.lena;
					  var source   = $("#ledgerHandler").html();
			    	  var template = Handlebars.compile(source);
				      $("#ledgerTable").append( template({data:obj}) );
				      $(".loader").fadeOut("slow");
			    	  var totalAmount = 0.00;
				      
				      for(var i=0;i<obj.length;i++){

				    	  totalAmount = totalAmount+obj[i].closebal;
				      }
				      
				      $("#totalOsAmount").html(totalAmount);      				      
				    //  $("#totalAmount").html(totalAmount);

					var obj = jsondata.dena;
				  var source   = $("#denaledgerHandler").html();
		    	  var template = Handlebars.compile(source);
			      $("#denaledgerTable").append( template({data:obj}) );
			      
			    	  totalAmount = 0.00;
				      
				      for(var i=0;i<obj.length;i++){

				    	  totalAmount = totalAmount+obj[i].closebal;
				      }
				      
				      $("#denatotalOsAmount").html(totalAmount);      				      
				  

				}
				
			}
  		});
	   }
  </script>
  