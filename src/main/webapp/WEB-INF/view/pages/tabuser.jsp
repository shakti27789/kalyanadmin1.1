<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <script id="user" type="text/x-handlebars-template">

      <div class="grid-body ">
              <table class="table table-striped" id="userlist" class="userlist">
                <thead>
                  <tr>
                    <th>User Name</th>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Mobile</th>
                    <th>Manage</th>
                  </tr>
                </thead>
                <tbody>
  {{#each data}}
                  <tr class="odd gradeX">
                    <td>{{username}}</td>
                    <td>{{email}}</td>
                    <td>{{password}}</td>
                    <td class="center">{{mobile_no}}</td>
                    <td class="center"><button type="button" class="btn btn-info btn-cons" onclick=editTabUser('{{id}}')>Manage</button></td>
                  </tr>
  {{/each}}          
                </tbody>
              </table>
            </div>
    </script>

  <div class="page-content">
    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <div id="portlet-config" class="modal hide">
      <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button"></button>
        <h3>Widget Settings</h3>
      </div>
      <div class="modal-body"> Widget settings form goes here </div>
    </div>
    <div class="clearfix"></div>
    <div class="content">
      
      <div class="page-title"> <i class="icon-custom-left"></i>
        <h3>Back</h3>
      </div>
      
      
       <div class="row viewuser">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                <button type="button" class="btn btn-success btn-cons createuserbutton" >Create Client</button>
                </div>
                
                <div id ="user_list">
                </div>
               
			
               
              </div>
            </div>
          </div>
      
      
      
      
	  <!-- BEGIN BASIC FORM ELEMENTS-->
        <div class="row createuser" style="display:none;">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                <button type="button" class="btn btn-success btn-cons" >Create Client</button>
                </div>
                 <form  method="post" enctype="multipart/form-data">
                  <input type="hidden" id="imagepath" class="imagepath"> 
                    <input type="hidden" id="userid" class="userid">
                	<div class="grid-body no-border"> <br>
                    <div class="row">
                    <div class="col-md-8 col-sm-8 col-xs-8">
                      <div class="form-group">
                        <label class="form-label">Username</label>
                        <div class="controls">
                          <input type="text" class="form-control" id="username" class="username">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Email</label>
                         <div class="controls">
                          <input type="text" class="form-control" id="email" class="email">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Password</label>
                        <div class="controls">
                          <input type="password" class="form-control" id="password" class="password">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Mobile no</label>
                         <div class="controls">
                          <input type="text" class="form-control" id="mobile" class="mobile">
                        </div>
                      </div>
                      
                      
                      <div class="form-group">
                        <div class="controls">
                        <button type="button" class="btn btn-success btn-cons createuserbuton" onclick="createTabUser()">Create</button>
                        <button type="button" class="btn btn-success btn-cons edituserbuton" style="display:none;" onclick="editTabUserSave()">Update</button>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </div>
                
                </form>
                
              </div>
            </div>
          </div>
	<!-- END BASIC FORM ELEMENTS-->

	

      <!-- END PAGE -->
    </div>
  </div>
  <script>
 
  $(document).ready(function() {
	      //$("#userlist").dataTable();
	  	  $(".createuserbutton").click(function(){
         // $(".viewuser").hide( "slide", { direction: "down"  }, 1000 );
          $('.viewuser').hide();
          $('.createuser').show();
       });
	  	allTabUser();
  });
  
  
     
     function createTabUser()
     {
    	 var username =$("#username").val();
		 var password =$("#password").val();
		 var email =$("#email").val();
		 var mobile = $("#mobile").val();
    	 var data={username:username,password:password,email:email,mobile:mobile}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveUpdateTabUser"/>',
				data: data,
			    success: function(jsondata, textStatus, xhr) {
				{
					console.log(textStatus);
					if(xhr.status == 200)
					{     
			           alert("User Created Successsfully");
			           location.reload();
					}
					else
						{
						 alert("Something Went wrong Please try again");
						}
				}
			}
		});
     }

     function allTabUser()
     {
    	
    	 
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/allTabUser"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						 var source   = $("#user").html();
				    	 var template = Handlebars.compile(source);
				    	 $("#user_list").append( template({data:jsondata.data}) );
				    	 var oTable = $('#userlist').dataTable( {
				    		   "sDom": "<'row'<'col-md-6'l><'col-md-6 text-right'f>r>t<'row'<'col-md-12'p i>>",
				    	       "aaSorting": [],
				    					"oLanguage": {
				    				"sLengthMenu": "_MENU_ ",
				    				"sInfo": "Showing <b>_START_ to _END_</b> of _TOTAL_ entries"
				    			},
				    	    });
				    	 $('select').select2();
					}
					
				}
			}
		});
    	 
    	
     }
     
     function editTabUser(id)
     {
    	 $("#userid").val(id);
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/editTabUser"/>',
				data:{id:id},
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{   
						$('.viewuser').hide();  
						$('.createuser').show();
						$('.createuserbuton').hide();
						$('.edituserbuton').show();
						console.log(jsondata.data);
						$("#username").val(jsondata.data.username);
						$("#email").val(jsondata.data.email);
						$("#password").val(jsondata.data.password);
						$("#mobile").val(jsondata.data.mobile_no);
					}
					
				}
    		 }
    	 });
    		
     }
     
     function editTabUserSave(id)
     {
    	 var username =$("#username").val();
		 var password =$("#password").val();
		 var email =$("#email").val();
		 var mobile = $("#mobile").val();
		 var id = $("#userid").val();
    	 var data={username:username,password:password,email:email,id:id,mobile:mobile}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveUpdateTabUser"/>',
				data: data,
			    success: function(jsondata, textStatus, xhr) {
				{
					console.log(textStatus);
					if(xhr.status == 200)
					{     
			           alert("User Detail Updated Successsfully");
			           location.reload();
					}
					else
						{
						 alert("Something Went wrong Please try again");
						}
				}
			}
		});
     }
  </script>
  
    
   
  