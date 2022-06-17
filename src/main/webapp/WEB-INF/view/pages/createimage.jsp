<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>




 <script id="images" type="text/x-handlebars-template">

      <div class="grid-body ">
              <table class="table table-striped" id="imagelist" class="imagelist">
                <thead>
                  <tr>
                    <th>Heading</th>
                    <th>Descreption</th>
                    <th>Image</th>
                    <th>Manage</th>
                  </tr>
                </thead>
                <tbody>
  {{#each data}}
                  <tr class="odd gradeX">
                    <td>{{heading}}</td>
                    <td>{{description}}</td>
                    <td><img src="/api/imagepath/{{img}}" alt="" style="height: 80px;" /></td>
                    <td class="center"><button type="button" class="btn btn-info btn-cons" onclick=editImage('{{id}}')>Manage</button></td>
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
	  
	  <div class="row viewimages">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                <button type="button" class="btn btn-success btn-cons createimagebutton" >Create Image</button>
                </div>
                
                <div id ="image_list_div">
                </div>
               
			
               
              </div>
            </div>
          </div>
	  
	  
        <div class="row image_create_div" style="display:none;">
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
                        <label class="form-label">Heading</label>
                        <div class="controls">
                          <input type="text" class="form-control" id="heading" class="heading">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Descreption</label>
                         <div class="controls">
                          <input type="text" class="form-control" id="descreption" class="descreption">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="form-label">Imgae</label>
                        <div class="controls">
                         <input type="file" name="img" id="fetureimage" class="fetureimage">
                        </div>
                      </div>
                      
                      <div class="form-group">
                        <div class="controls">
                        <button type="button" class="btn btn-success btn-cons createimage" onclick="saveFetureImage()">Save</button>
                        <button type="button" class="btn btn-success btn-cons updateimage" onclick="editSaveFetureImage()">Update</button>
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
	  $( ".fetureimage" ).change(function() {
		  uploadimage();
		});
	  
	  $(".createimagebutton").click(function(){
	          $('.viewimages').hide();
	          $('.image_create_div').show();
	       });
	  allFetureImages();
  });
  
  function uploadimage()
	{
	  var file_data = $('#fetureimage').prop('files')[0];   
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
  
     function saveFetureImage()
     {
    	 var heading =$("#heading").val();
		 var descreption =$("#descreption").val();
		 var img =$(".imagepath").val();
    	 var data={heading:heading,descreption:descreption,img:img}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveFetureImage"/>',
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

     
     function allFetureImages()
     {
    	 
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/allFetureImages"/>',
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{     
						 var source   = $("#images").html();
				    	 var template = Handlebars.compile(source);
				    	 $("#image_list_div").append( template({data:jsondata.data}) );
				    	 var oTable = $('#imagelist').dataTable( {
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
     
     function editImage(id)
     {
    	 $("#id").val(id);
    	 $.ajax({
				type:'GET',
				url:'<s:url value="/api/editImage"/>',
				data:{id:id},
				success: function(jsondata, textStatus, xhr) {
				{
					if(xhr.status == 200)
					{   
						$('.viewimages').hide();
				        $('.image_create_div').show();
						$('.createimage').hide();
						$('.updateimage').show();
						console.log(jsondata.data);
						$("#heading").val(jsondata.data.heading);
						$("#descreption").val(jsondata.data.description);
						$("#imagepath").val(jsondata.data.img);
					}
					
				}
    		 }
    	 });
    		
     }
     
     
     function editSaveFetureImage(id)
     {
    	 var heading =$("#heading").val();
		 var descreption =$("#descreption").val();
		 var img =$(".imagepath").val();
		 var id = $("#id").val();
    	 var data={heading:heading,descreption:descreption,img:img,id:id}
		 $.ajax({
				type:'POST',
				url:'<s:url value="/api/saveFetureImage"/>',
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