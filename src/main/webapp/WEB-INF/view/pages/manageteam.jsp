<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>




 <script id="teams" type="text/x-handlebars-template">

      <div class="grid-body ">
              <table class="table table-striped" id="teamlist" class="teamlist">
                <thead>
                  <tr>
                    <th>Team Name</th>
                    <th>Short Name</th>
                    <th>Image</th>
                    <th>Manage</th>
                  </tr>
                </thead>
                <tbody>
  {{#each data}}
                  <tr class="odd gradeX">
                    <td>{{teamfullname}}</td>
                    <td>{{teamshortname}}</td>
                    <td><img src="/api/imagepath/{{img}}" alt="" style="height: 80px;" /></td>
                    <td class="center"><button type="button" class="btn btn-info btn-cons" onclick=editTeam('{{id}}')>Manage</button></td>
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
        <h3>Images</h3>
      </div>
	  <!-- BEGIN BASIC FORM ELEMENTS-->
	  
	  <div class="row viewteams">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                <button type="button" class="btn btn-success btn-cons createteambutton" >Create Team</button>
                </div>
                
                <div id ="team_list_div">
                </div>
               
			
               
              </div>
            </div>
          </div>
	  
	  
        <div class="row team_create_div" style="display:none;">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                  <h4>Simple <span class="semi-bold">Elemets</span></h4>
                  <div class="tools"> <a href="javascript:;" class="collapse"></a> <a href="#grid-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a> </div>
                </div>
                <form  method="post" enctype="multipart/form-data">
                 <input type="hidden" id="id" class="id">
                  <input type="hidden" id="imagepath" class="imagepath"> 
                	<div class="grid-body no-border"> <br>
                    <div class="row">
                    <div class="col-md-8 col-sm-8 col-xs-8">
                      <div class="form-group">
                        <label class="form-label">Team Name</label>
                        <div class="controls">
                          <input type="text" class="form-control" id="teamfullname" class="teamfullname" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Short Name</label>
                         <div class="controls">
                          <input type="text" class="form-control" id="teamshortname" class="teamshortname" required>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Imgae</label>
                        <div class="controls">
                         <input type="file" name="img" id="teamimage" class="teamimage">
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <div class="controls">
                        <button type="button" class="btn btn-success btn-cons createteam" onclick="saveTeam()">Save</button>
                        <button type="button" class="btn btn-success btn-cons updateteam" onclick="editSaveTeam()">Update</button>
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
  $(document).ready(function(){
	  $( ".teamimage" ).change(function() {
		  uploadimage();
		});
	  
	  $(".createteambutton").click(function(){
	          $('.viewteams').hide();
	          $('.team_create_div').show();
	          $('.updateteam').hide();
	       });
	  allTeam();
  });
  
  function uploadimage()
	{
	  var file_data = $('#teamimage').prop('files')[0];   
	  var form_data = new FormData();                  
	  form_data.append('file', file_data);
	  $.ajax({
				type:'POST',
				url:'<s:url value="/api/uploadfile"/>',
				cache: false,
		        contentType: false,
		        processData: false,
		        data: form_data,
			    success: function(jsondata, textStatus, xhr) {
				{
					console.log(textStatus);
					if(xhr.status == 200)
					{     
			           $(".imagepath").val(jsondata.data.path);
					}
				}
			}
		});
	}
  
     function saveTeam()
     {
    	 var teamfullname =$("#teamfullname").val();
		 var teamshortname =$("#teamshortname").val();
		 var img =$(".imagepath").val();
		 if(teamfullname.trim().length>0 && teamshortname.trim().length>0 && img.trim().length>0)
		{
			 var data={teamfullname:teamfullname,teamshortname:teamshortname,img:img}
			 $.ajax({
					type:'POST',
					url:'<s:url value="/api/saveUpdateTeam"/>',
					data: data,
				    success: function(jsondata, textStatus, xhr) {
					{
						console.log(textStatus);
						if(xhr.status == 200)
						{     
				           alert("image successfully uploded");
				           location.reload();
						}
					}
				}
			});
		}
		 else
			 {
			 alert("please fill all fields..")
			 }
    	 
     }

     
     function allTeam()
     {
    	 
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/allTeam"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						 var source   = $("#teams").html();
				    	 var template = Handlebars.compile(source);
				    	 $("#team_list_div").append( template({data:jsondata.data}) );
				    	 var oTable = $('#teamlist').dataTable( {
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
     
     function editTeam(id)
     {
    	 $("#id").val(id);
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/editTeam"/>',
				data:{id:id},
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{   
						$('.viewteams').hide();
				        $('.team_create_div').show();
						$('.createteam').hide();
						$('.updateteam').show();
						console.log(jsondata.data);
						$("#teamfullname").val(jsondata.data.teamfullname);
						$("#teamshortname").val(jsondata.data.teamshortname);
						$("#imagepath").val(jsondata.data.img);
					}
					
				}
    		 }
    	 });
    		
     }
     
     
     function editSaveTeam(id)
     {
    	 var teamfullname =$("#teamfullname").val();
		 var teamshortname =$("#teamshortname").val();
		 var img =$(".imagepath").val();
		 var id = $("#id").val();
    	 var data={teamfullname:teamfullname,teamshortname:teamshortname,img:img,id:id}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveUpdateTeam"/>',
				data: data,
			    success: function(jsondata, textStatus, xhr) {
				{
					console.log(textStatus);
					if(xhr.status == 200)
					{     
			           alert("Image Updated Successsfully");
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