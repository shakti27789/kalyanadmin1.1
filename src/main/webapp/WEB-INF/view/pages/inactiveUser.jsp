<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <script id="inactiveUserhandler" type="text/x-handlebars-template">

	<table class="apl-table table-net-exposure transitionable-table-wrap">
	<thead>
		<tr>
		<th>User</th>
		<th>Dealer</th>
		<th>Master</th>
		<th>Action</th>
		</tr>
	</thead>
			<tbody>
{{#each data}}
			<tr>
			<th>{{userid}}</th>
			<th>{{dealername}}</th>
			<th>{{mastername}}</th>
			<th>
			<button type="button" class="btn btn-success btn-sm" onclick="rollbackUser('{{id}}')">Activate</button>
			</th>
			</tr>
{{/each}}
			</tbody>

  </script>
<div id="page-wrapper">		
<div class="inner-wrap live-bets-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Inactive Users</h1>	
						<section class="menu-content">
							<div class="table-reponsive" id="inactiveusers">
								
							</div>
							<div class="panel-body text-center" id="noInactiveUser">
								<h1>No Inactive User Available</h1>
							</div>
						</section>						
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    
    <script>
    $(document).ready(function(){
    	userList();
    });
    
    function userList()
    {
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/getInactiveUser"/>',
			success: function(jsondata, textStatus, xhr) {
			{
				if(xhr.status==200){
					 $("#inactiveusers").show();
					 $("#noInactiveUser").hide();
					 	console.log(jsondata.data)
					 $("#inactiveusers").html('');
						var obj = jQuery.parseJSON(jsondata);
					   var source   = $("#inactiveUserhandler").html();
			    	  var template = Handlebars.compile(source);
			    	  $("#inactiveusers").append( template({data:obj})); 					
				}else{
					$("#inactiveusers").html('');
					 $("#inactiveusers").hide();
					 $("#noInactiveUser").show();
				}
			}
		}
	});
   }
    
	 function rollbackUser(id){	
		   swal({
  	      title: 'Are you sure?',
  	      text: 'You Want to Activate this User!',
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
		 			url:'<s:url value="/api/rollbackUser/"/>'+id,
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
						userList();
				    } 
		 	});
		 	
		 	 } 
		 	});
		 }

    </script>