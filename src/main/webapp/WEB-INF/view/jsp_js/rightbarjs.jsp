<script id="list-of-users" type="text/x-handlebars-template">
  <div class="col-md-12 col-sm-12" id="userlistdiv">
     <div class="row">
       <div class="col-md-6 text-left">
        <h4>Master List</h4>
      </div>
	 <div class="col-sm-6 col-xs-6 text-right">
      <button class="btn btn-primary" onclick='createUser()'>Create Master</button>
     </div>
   </div>
 
  

<div class="table-responsive">
   <table class="table table-striped table-flip-scroll cf">
      <thead class="cf">
        <tr>
           <th>Name</th>
           <th>User Name</th>
           <th>oddsWinComm</th>
           <th>oddsLossComm</th>
           <th>fancyWinComm</th>
		   <th>fancyLossComm</th>
           <th>AdminPartnership</th>
           <th>MasterPartnership</th>
           <th>DealerPartnership</th>
           <th>Action</th>
         </tr>
       </thead>
       <tbody>
         {{#each data}}
           <tr>
             <td>{{name}} </td>
             <td>{{username}}</td>
             <td>{{oddsWinComm}}</td>
             <td>{{oddsLossComm}}</span>
			 <td>{{fancyWinComm}}</td>
             <td>{{fancyLossComm}}</span>
			 <td>{{adminPartnership}}</td>
             <td>{{masterPartnership}}</span>
			 <td>{{dealerPartnership}}</td>
             <td>
             <button type="button" class="btn btn-white btn-xs btn-mini">DB</button>
             <button type="button" class="btn btn-white btn-xs btn-mini">CR</button>
               {{#xif " usertype == '1'" }}
                 <button type="button" class="btn btn-white btn-xs btn-mini" onclick="showDelearList({{usertype}},{{userId}})">DL</button>
                 {{else}}
                 <button type="button" class="btn btn-white btn-xs btn-mini" onclick="showUserList({{usertype}},{{userId}})">UL</button>
               {{/xif}}         
             </td>
            </tr>
          {{/each}}
       </tbody>
</div>

  </div>
</script>
<script id="no_data_available" type="text/x-handlebars-template">
 <div class="col-md-12 col-sm-12" id="userlistdiv">
     <div class="row">
       <div class="col-md-6 text-left">
        <h4>Delear List</h4>
      </div>
	 <div class="col-sm-6 col-xs-6 text-right">
      <button class="btn btn-primary" onclick='createUser()'>Create {{data}}</button>
     </div>
   </div>
     
<div>No User Addedd</div>
</script>
<script>
function showuserListByType(type,parentId)
{
	
   $("#data-render").html('');
   $.ajax({
	   url: "/api/betting/userList?usertype="+type+"&parentId="+parentId,
		type: "GET",
			dataType:"json",
           success: function(json, textStatus, xhr) {
              $("#userlistdiv").remove();
              console.log(xhr.status)
              
				if(xhr.status == 200){
					$(".right-pane").hide();
					var source   = $("#list-of-users").html();
					var template = Handlebars.compile(source);
					$("#data-render").append( template({data:json.data}) );  
				}
				else if(xhr.status == 204)
					{
					$(".right-pane").hide();
					var source   = $("#no_data_available").html();
					var template = Handlebars.compile(source);
					$("#data-render").append(template({data:"Master"})); 
					}
				
			},
			complete: function(xhr, textStatus) {
				//console.log(xhr.status);
			} 
		});
   var newurl = window.location.protocol+"//"+window.location.host+"/"+"dashboard/userList";
   window.history.pushState({path:newurl},'',newurl);
 }
 
   $(document).ready(function() {
	 if(window.location.pathname =="/dashboard/userList")
	 showuserListByType(${sessionScope.user.usertype},${sessionScope.user.userId});
	 if(window.location.pathname =="/dashboard/createUserForm")
	 createUser()
	 window.addEventListener('popstate', function(e){
	 if(window.location.pathname =="/dashboard/userList")
	 showuserListByType(${sessionScope.user.usertype},${sessionScope.user.userId});
	 }); 

	});
   
   function showDelearList(type,parentId)
   {
	   $("#data-render").html('');
	   $.ajax({
				url: "/api/betting/userList?usertype="+type+"&parentId="+parentId,
				type: "GET",
				dataType:"json",
	           success: function(json, textStatus, xhr) {
	              $("#userlistdiv").remove();
					if(xhr.status == 200){
						$(".right-pane").hide();
						var source   = $("#list-of-users").html();
						var template = Handlebars.compile(source);
						$("#data-render").append( template({data:json.data}) );

						
					}
					else if(xhr.status == 204){
						$(".right-pane").hide();
						var source   = $("#no_data_available").html();
						var template = Handlebars.compile(source);
						$("#data-render").append( template({data:"Delear"}) );

						
					}
					
				},
				complete: function(xhr, textStatus) {
					//console.log(xhr.status);
				} 
			});
	   
   }
	
   function showUserList(type,parentId)
   {
	   $("#data-render").html('');
	   $.ajax({
				url: "/api/betting/userList?usertype="+type+"&parentId="+parentId,
				type: "GET",
				dataType:"json",
	           success: function(json, textStatus, xhr) {
	              console.log(json.data)
	              $("#userlistdiv").remove();
					if(xhr.status == 200){
						$(".right-pane").hide();
						var source   = $("#list-of-users").html();
						var template = Handlebars.compile(source);
					 	$("#data-render").append( template({data:json.data}) );
	
					}
					
				},
				complete: function(xhr, textStatus) {
					//console.log(xhr.status);
				} 
			}); 
   }
	
/* function createUser(){
	// $("#myModal").modal("show"); 
	
	 var newurl = window.location.protocol+"//"+window.location.host+"/"+"dashboard/createUser";
     window.history.pushState({path:newurl},'',newurl);
	
	$("#data-render").html('');
	var source   = $("#add-user").html();
	var template = Handlebars.compile(source);
	$("#data-render").append(template);
}
 */
	
</script>




  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">CHIP DIPOSITE</h4>
        </div>
        <div class="modal-body">
       <div class="row">
			<div class="col-sm-offset-2 col-sm-8 form-group">
				<label>Chips</label>
				<input type="number" name="chips" id="chips" min="1"  required= "true" class="form-control">
				</div>								                    
			</div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" onclick="chipDisposite()" >Deposit</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
