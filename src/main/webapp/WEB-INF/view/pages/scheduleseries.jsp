<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <script id="series" type="text/x-handlebars-template">

      <div class="grid-body ">
              <table class="table table-striped" id="serieslist" class="serieslist">
                <thead>
                  <tr>
                    <th>Series Name</th>
                    <th>Match Number</th>
                    <th>Team1</th>
                    <th>Team2</th>
                    <th>Date Time</th>
                    <th>Manage</th>
                  </tr>
                </thead>
                <tbody>
  {{#each data}}
                  <tr class="odd gradeX">
                    <td>{{tourname}}</td>
                    <td>{{matchnumber}}</td>
                    <td>{{team2}}</td>
                    <td class="center">{{team1}}</td>
					<td class="center">{{datetime}}</td>
                    <td class="center"><button type="button" class="btn btn-info btn-cons" onclick=editUser('{{id}}')>Manage</button></td>
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
      
      
       <div class="row viewseries">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                <button type="button" class="btn btn-success btn-cons createseriesbutton" >Create Client</button>
                </div>
                
                <div id ="series_list">
                </div>
               
			
               
              </div>
            </div>
          </div>
      
      
      
      
	  <!-- BEGIN BASIC FORM ELEMENTS-->
        <div class="row createseries" style="display:none;">
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
                        <button type="button" class="btn btn-success btn-cons createuserbuton" onclick="createUser()">Create</button>
                        <button type="button" class="btn btn-success btn-cons edituserbuton" style="display:none;" onclick="editUserSave()">Update</button>
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
	  	  $(".createseriesbutton").click(function(){
         // $(".viewuser").hide( "slide", { direction: "down"  }, 1000 );
          $('.viewseries').hide();
          $('.createseries').show();
       });
	  	allScheduledSeries();
  });
  
 
     function createUser()
     {
    	 var username =$("#username").val();
		 var password =$("#password").val();
		 var email =$("#email").val();
		 var mobile = $("#mobile").val();
    	 var data={username:username,password:password,email:email,mobile:mobile}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveUpdateUser"/>',
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

     function allScheduledSeries()
     {
    	
    	 
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/allScheduledSeries"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						 var source   = $("#series").html();
				    	 var template = Handlebars.compile(source);
				    	 $("#series_list").append( template({data:jsondata.data}) );
				    	 var oTable = $('#serieslist').dataTable( {
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
     
     function editUser(id)
     {
    	 $("#userid").val(id);
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/editUser"/>',
				data:{id:id},
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{   
						$('.viewseries').hide();  
						$('.createseries').show();
						$('.createuserbuton').hide();
						$('.edituserbuton').show();
						console.log(jsondata.data);
						$("#username").val(jsondata.data.username);
						$("#email").val(jsondata.data.email);
						$("#password").val(jsondata.data.password);
						$("#mobile").val(jsondata.data.mobile);
					}
					
				}
    		 }
    	 });
    		
     }
     
     function editUserSave(id)
     {
    	 var username =$("#username").val();
		 var password =$("#password").val();
		 var email =$("#email").val();
		 var mobile = $("#mobile").val();
		 var id = $("#userid").val();
    	 var data={username:username,password:password,email:email,id:id,mobile:mobile}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveUpdateUser"/>',
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
  </script>
  
    
   
  