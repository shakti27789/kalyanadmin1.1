
  <script id="add-user" type="text/x-handlebars-template">
    <div class="row" id="createuser">
            <div class="col-md-12">
              <div class="grid simple">
                <div class="grid-title no-border">
                  <h4>Simple <span class="semi-bold">Elemets</span></h4>
                  <div class="tools"> <a href="javascript:;" class="collapse"></a> <a href="#grid-config" data-toggle="modal" class="config"></a> <a href="javascript:;" class="reload"></a> <a href="javascript:;" class="remove"></a> </div>
                </div>
                <div class="grid-body no-border"> <br>
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <div class="col-sm-4 form-group">
                        <label class="form-label">Your Name</label>
                        <div class="controls">
                          <input type="text" id="name" class="form-control">
                        </div>
                      </div>

                       <div class="col-sm-4 form-group">
                        <label class="form-label">USERNANE</label>
                        <div class="controls">
                          <input type="text" id="username" class="form-control">
                        </div>
                      </div>
                      <div class="col-sm-4 form-group">
                        <label class="form-label">Password</label>
                         <div class="controls">
                          <input type="password" id="password" class="form-control">
                        </div>
                      </div>
                     
                      <div class="col-sm-4 form-group">
                        <label class="form-label">PARTNERSHIP (Apna Share Bharna Hai)</label>
                        <div class="controls">
                          <input type="text" id="partnership" class="form-control">
                        </div>
                      </div>      

                       <div class="col-sm-4 form-group">
                        <label class="form-label">ODDSWINCOMM</label>
                        <div class="controls">
                          <input type="text" id="oddsWinComm" class="form-control">
                        </div>
                      </div>

                      <div class="col-sm-4 form-group">
                        <label class="form-label">ODDSLOSSCOMM</label>
                        <div class="controls">
                          <input type="text" id="oddsLossComm" class="form-control">
                        </div>
                      </div>      

                       <div class="col-sm-4 form-group">
                        <label class="form-label">ADMINPARTNERSHIP</label>
                        <div class="controls">
                          <input type="text" id="adminPartnership" class="form-control">
                        </div>
                      </div>  

                       <div class="col-sm-4 form-group">
                        <label class="form-label">MASTERPARTNERSHIP</label>
                        <div class="controls">
                          <input type="text" id="masterPartnership" class="form-control">
                        </div>
                      </div>      

                       <div class="col-sm-4 form-group">
                        <label class="form-label">DEALERPARTNERSHIP</label>
                        <div class="controls">
                          <input type="text" id="dealerPartnership" class="form-control">
                        </div>
                      </div>

                      <div class="col-sm-4 form-group">
                        <label class="form-label">FANCYWINCOMM</label>
                        <div class="controls">
                          <input type="text" id="fancyWinComm" class="form-control">
                        </div>
                      </div>      

                       <div class="col-sm-4 form-group">
                        <label class="form-label">FANCYLOSSCOMM</label>
                        <div class="controls">
                          <input type="text" id="fancyLossComm" class="form-control">
                        </div>
                      </div>   
                      
                      <div class="col-sm-8 form-group">
                        <input type="button" value="save" onclick="saveUser()">
                      </div>               
                     </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
  </script>
<script>
function createUser()
{
	 var newurl = window.location.protocol+"//"+window.location.host+"/"+"dashboard/createUserForm";
     window.history.pushState({path:newurl},'',newurl);
	 $("#data-render").html('');
	 $("#userlistdiv").remove();
	 var source   = $("#add-user").html();
	 var template = Handlebars.compile(source);
	 $("#data-render").append(template);
 }
 
$(document).ready(function() {
	 if(window.location.pathname =="/dashboard/createUserForm")
	 createUser();
	 window.addEventListener('popstate', function(e){
	 }); 
 });
	
	function saveUser()
	{
		var data={name:$("#name").val(),username:$("#username").val(),password:$("#password").val(),partnership:$("#partnership").val(),partnership:$("#partnership").val(),oddsWinComm:$("#oddsWinComm").val(),oddsLossComm:$("#oddsLossComm").val(),fancyWinComm:$("#fancyWinComm").val(),fancyLossComm:$("#fancyLossComm").val(),dealerPartnership:$("#dealerPartnership").val(),masterPartnership:$("#masterPartnership").val(),adminPartnership:$("#adminPartnership").val(),usertype:${sessionScope.user.usertype},userId:${sessionScope.user.userId}}
	    
		$.ajax({
				url: "/api/betting/createUser",
				type: "POST",
				dataType:"json",
				data:data,
				
	            success: function(json, textStatus, xhr) {
	            
					if(xhr.status == 200){
						$(".right-pane").hide();
						$("#createuser").hide();
						showuserListByType(${sessionScope.user.usertype},${sessionScope.user.userId});
						alert("you have addesd user success fully");
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
</script>

