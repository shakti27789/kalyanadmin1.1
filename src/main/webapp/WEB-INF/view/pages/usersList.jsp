<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="userListhandler" type="text/x-handlebars-template">

	<table class="apl-table table-net-exposure transitionable-table-wrap">
	<thead>
		<tr>
		<th>User Name</th>
		<th>User ID</th>
		
		<th>IP</th>
		<th>Last Login</th>
		<th>Last Logout</th>
		<th>Logout By</th>
		<th>Action</th>
		</tr>
	</thead>
			<tbody id="myUserList">
{{#each data}}
			<tr>
			<td>{{username}}</td>
			<td>{{userid}}</td>
			
			<td>{{ipdetail}}</td>
			<td>{{lastlogin}}</td>
			<td>{{lastlogout}}</td>
			<td>{{logoutby}}</td>
			<td>
			<button type="button" class="btn btn-success btn-sm" onclick="logoutUser('{{id}}')">Logout User</button>	
			</td>
			</tr>
{{/each}}
			</tbody>

  </script>
<div id="page-wrapper">
	<div class="inner-wrap live-bets-wrap">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">User List</h1>
				<div class="filterSection">
							<ul>
								<li>
								<input type="text" placeholder="Search.." id="searchByname"class="ng-pristine ng-untouched ng-valid"></th>
								</li>
							</ul>
				
				<section class="menu-content">
				<div class="table-reponsive" id="userList"></div>
				<div class="panel-body text-center" id="noUsers">
					<h1>No Users Available</h1>
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
	$(document).ready(function() {	
		getUserList();
    	$("#searchByname").on("keyup", function() {
    	    var value = $(this).val().toLowerCase();
    	    $("#myUserList tr").filter(function() {
    	    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    	      });
    	  });
	});

	function getUserList() {

		$.ajax({
			type : 'GET',
			url : '<s:url value="api/getUserIpList"/>',
			success : function(jsondata, textStatus, xhr) {
				{
					if (xhr.status == 200) {
						$("#userList").html('')
						$("#userList").show();
						$("#noUsers").hide();
						var source = $("#userListhandler").html();
						var template = Handlebars.compile(source);
						
						$("#userList").append(template({data : jsondata}));
					} else {
						$("#userList").html('');
						$("#userList").hide();
						$("#noUsers").show();
					}
				}
			}
		});
	}
    
	 function logoutUser(id){	
		 
		   swal({
	      title: 'Are you sure?',
	      text: "Do you want to force logout this user",
	      type: 'warning',
	      html: '<div class="panel-body text-center" id="noUsers"><div>You want to force logout this user</div></div><ul><li><input type="number" id="days" placeholder="Enter Days" class="form-control"></li><li><input type="number" id="hours" placeholder="Enter Hours" class="form-control"></li></ul>',
	      showCancelButton: true,
	      confirmButtonColor: '#3085d6',
	      cancelButtonColor: '#d33',
	      confirmButtonText: 'Yes',
	      closeOnConfirm: false
	    }).then(function(isConfirm) {
	      if (isConfirm) {		
	    		  
	    	  var data = {
	    			  "days":$("#days").val(),
	    			  "hours":$("#hours").val()
	    	  }
		 		$.ajax({
		 			type:'POST',
		 			url:'<s:url value="/api/setlogoutUser?id="/>'+id,
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						getUserList();
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				    } 
		 	});
		 	
		 	 } 
		 	});
		 }
		Handlebars.registerHelper('if_eq', function(a, b, opts) {
		    if (a == b) {
		        return opts.fn(this);
		    } else {
		        return opts.inverse(this);
		    }
		});
</script>