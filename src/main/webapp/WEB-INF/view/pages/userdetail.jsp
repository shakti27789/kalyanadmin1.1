<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
    
    <script id="userDetailhandler" type="text/x-handlebars-template">

	<table class="apl-table table-net-exposure transitionable-table-wrap">
	<thead>
		<tr>
		<th>User Name</th>
		<th>User ID</th>
		<th>Upline</th><th>Admin</th><th>SBA</th><th>SM</th><th>M</th>
		<th>Upline SHARE</th><th>Admin SHARE</th><th>SBA SHARE</th><th>SM SHARA</th><th>M SHARE</th>
		</tr>
	</thead>
			<tbody id="myUserList">

			<tr>
			<td>{{data.username}}</td>
			<td>{{data.userid}}</td>
			<td>{{data.adminname}}</td><td>{{data.subadminname}}</td><td>{{data.supermastername}}</td><td>{{data.mastername}}</td><td>{{data.dealername}}</td>
			<td>{{data.adminpartership}}</td><td>{{data.subadminpartnership}}</td><td>{{data.supermastepartnership}}</td><td>{{data.masterpartership}}</td><td>{{data.delearpartership}}</td>			
		
				
			</td>
			</tr>

			</tbody>

  </script>
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Searh Clinet</h1>
						<div class="filterSection full-width-form">
						
						<ul>
						<li>
						<input type ="text" id ="userid" value="" placeHolder ="please enter User id">
						</li>
						
							<li>				 
					 <button class="btn btn-info blue-btn btn-sm" onclick='searchUserdetail()' >Search</button>
						</li>
						</ul>
						
						<div class="table-reponsive" id="userdetail"></div>
						</div>

					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
  
   <script>
    
    

  
  function searchUserdetail() {

	              var userid =$("#userid").val();
    			 $("#userdetail").html('');
			   	 $.ajax({
			   	 
						type:'GET',
						url:'<s:url value="/api/searchUserdetail/"/>'+userid,
						success: function(jsondata, textStatus, xhr) {
							
							if(xhr.status ==200){
								
								var source = $("#userDetailhandler").html();
								var template = Handlebars.compile(source);
								$("#userdetail").append(template({data : jsondata}));
							}else{
								var source = $("#userDetailhandler").html();
								var template = Handlebars.compile(source);
								$("#userdetail").append("No User Found");
							}
						
						},
						complete: function(jsondata,textStatus,xhr) {
							//showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					    } 
					});     	    	
		    }
  
  
    </script>
 