<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script id="matchListhandler" type="text/x-handlebars-template">
	<table class="apl-table table-net-exposure transitionable-table-wrap">
<thead>
		<tr>
		<th>Name</th>
		<th>Date</th>
		<th>Profit Limit</th>
		<th>Loss Limit</th>
		<th>is Unmatched</th>
		<th>Provider</th>
		<th>Change Provider</th>
		<th>Action</th>
		</tr>
	</thead>
			<tbody id="myMatchList">
{{#each data}}
			<tr>
			<td id="name{{id}}">{{matchname}}</th>
			<td>{{createdon}}</th>
			<td>
			<input type="text" id="profit{{id}}" placeholder="100000" style="color: BLACK" class="ng-pristine ng-untouched ng-valid" value="{{profitlimit}}">
			</td>
			<td>
			<input type="text" id="loss{{id}}" placeholder="100000" style="color: BLACK" class="ng-pristine ng-untouched ng-valid" value="{{losslimit}}">
			</td>
			<td>
			{{#if_eq isunmatched true}}
			<input type="checkbox" class="sportidcheckbox" id="isUnmatched{{id}}" checked="true">
			{{else}}
			<input type="checkbox" class="sportidcheckbox" id="isUnmatched{{id}}">
			{{/if_eq}}
			</td>
			<td>{{oddsprovider}}</td>
			<td>
			<button type="button" class="btn btn-success btn-sm" onclick="changeMatchProvider('{{eventid}}')">Change Provider</button>	
			</td>
			<td>
			{{#if_eq settingactive true}}
			<button type="button" class="btn btn-success btn-sm" onclick="setMatchSetting('{{id}}',false)">Deactivate Setting</button>	
			{{else}}
			<button type="button" class="btn btn-success btn-sm" onclick="setMatchSetting('{{id}}',true)">Activate Setting</button>	
			{{/if_eq}}				
			</td>
			</tr>
{{/each}}
			</tbody>

  </script>
<div id="page-wrapper">
	<div class="inner-wrap live-bets-wrap">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Match Setting</h1>
				<div class="filterSection">
							<ul>
								<li>
										<select class="form-control" id="selectMatch">
											<option>Active</option>
											<option>Inactive</option>
										</select>
								</li>
								<li>
										<select class="form-control" id="selectSubadmin">
										</select>
								</li>
								<li>
								<input type="text" placeholder="Search Via Match Name" id="searchMyMatches"class="ng-pristine ng-untouched ng-valid"></th>
								</li>
							</ul>
				
				<section class="menu-content">
				<div class="table-reponsive" id="matchList"></div>
				<div class="panel-body text-center" id="nomatches">
					<h1>No Matches Available</h1>
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
		subadminList();

		if(${user.usertype}==5){
            $("#selectSubadmin").append($('<option id = "'+"${user.id}"+'" value="'+${user.appid}+'" name = "'+'${user.username}'+'"  > '+'${user.username}'+' </option>', { 
            }));    			            			       
            getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
		}
		$("#selectMatch").change(function(){
			if($("#selectMatch").val() == "Active"){
				getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
			}else{
				getInActiveMatchList("Match Odds", false,$("#selectSubadmin option:selected").attr('value'));
			}
		});
		
		$("#selectSubadmin").change(function(){
			if($("#selectMatch").val() == "Active"){
				getActiveMatchList("Match Odds", true,$("#selectSubadmin option:selected").attr('value'));
			}else{
				getInActiveMatchList("Match Odds", false,$("#selectSubadmin option:selected").attr('value'));
			}
		});
		
    	$("#searchMyMatches").on("keyup", function() {
    	    var value = $(this).val().toLowerCase();
    	    $("#myMatchList tr").filter(function() {
    	    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    	      });
    	  });
	});

    function subadminList(){
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/user/${user.id}/"/>'+5+"?active=true",
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status==200){
					
					var obj = jsondata;
					$("#selectSubadmin").html('');
					$("#selectSubadmin").append($('<option> <a href="#" >Select Sub Admin</a> </option>'));
					  for(var i = 0;i<obj.data.length;i++) {

		             $("#selectSubadmin").append($('<option id = "'+obj.data[i].id+'" value="'+obj.data[i].appid+'" name = "'+obj.data[i].username+'"  > '+obj.data[i].username+' </option>', { 
		            }));    			            			       
  			  }
			}
		}
	});
   }
    
	function getActiveMatchList(marketname, isactive,appid) {

		$.ajax({
			type : 'GET',
			url : '<s:url value="api/getMatchList?marketname="/>' + marketname
					+ '&isactive=' + isactive+"&appid="+appid,
			success : function(jsondata, textStatus, xhr) {
				{
					if (xhr.status == 200) {
						$("#matchList").html('')
						$("#matchList").show();
						$("#nomatches").hide();
						var source = $("#matchListhandler").html();
						var template = Handlebars.compile(source);
						$("#matchList").append(template({
							data : jsondata
						}));
					} else {
						$("#matchList").html('');
						$("#matchList").hide();
						$("#nomatches").show();
					}
				}
			}
		});
	}

    function getInActiveMatchList(marketname,isactive,appid)
    {	
 	     
    	     $.ajax({
	    					type:'GET',
	    					url:'<s:url value="api/getMatchList?marketname="/>'+marketname+'&isactive='+isactive+"&appid="+appid,
	    					//url:'http://localhost/api/getMatchList?marketname='+marketname+'&isactive='+isactive,
	    					success: function(jsondata, textStatus, xhr) {
	    					{
	    						{
	    							if (xhr.status == 200) {
	    								$("#matchList").html('')
	    								$("#matchList").show();
	    								$("#nomatches").hide();
	    								var source = $("#matchListhandler").html();
	    								var template = Handlebars.compile(source);
	    								$("#matchList").append(template({
	    									data : jsondata
	    								}));
	    							} else {
	    								$("#matchList").html('');
	    								$("#matchList").hide();
	    								$("#nomatches").show();
	    							}
	    						}
	    						
	    					}
	    				}
	    			});
    }
    
	 function setMatchSetting(id,setting){	
		 
		 var isunmatched= false;
		 if($("#isUnmatched"+id).is(":checked")){
			 isunmatched = true;
		 }
		 var data = {
				 "id":id,
				 "profit":$("#profit"+id).val(),
				 "loss":$("#loss"+id).val(),
				 "unmatched":isunmatched,
				 "setting":setting
		 }
		 
		 if(setting==true){
			 var text = "'You Want to Activate this Setting!'";			 
		 }else{
			 var text = "'You Want to Deactivate this Setting!'";
		 }
		   swal({
	      title: 'Are you sure?',
	      text: text,
	      type: 'warning',
	      showCancelButton: true,
	      confirmButtonColor: '#3085d6',
	      cancelButtonColor: '#d33',
	      confirmButtonText: 'Yes',
	      closeOnConfirm: false
	    }).then(function(isConfirm) {
	      if (isConfirm) {		 	
		 		$.ajax({
		 			type:'POST',
		 			url:'<s:url value="/api/setMatchSetting/"/>',
					data: JSON.stringify(data),
					dataType: "json",
					contentType:"application/json; charset=utf-8",
					success: function(jsondata, textStatus, xhr) {
						showMessage(jsondata.message,jsondata.type,jsondata.title);
						if($("#selectMatch").val() == "Active"){
							getActiveMatchList("Match Odds", true);				
						}else{
							getInActiveMatchList("Match Odds", false);
						}
						
					},
					complete: function(jsondata,textStatus,xhr) {
						showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
				    } 
		 	});
		 	
		 	 } 
		 	});
		 }
	 
	 
	 function changeMatchProvider(id){

			   swal({
		      title: 'Are you sure?',
		      text: "'You Want to Change this Provider!'",
		      type: 'warning',
		      showCancelButton: true,
		      confirmButtonColor: '#3085d6',
		      cancelButtonColor: '#d33',
		      confirmButtonText: 'Yes',
		      closeOnConfirm: false
		    }).then(function(isConfirm) {
		      if (isConfirm) {		 	
			 		$.ajax({
			 			type:'POST',
			 			url:'<s:url value="/api/changeMatchProvider?eventid="/>'+id,
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
							if($("#selectMatch").val() == "Active"){
								getActiveMatchList("Match Odds", true);				
							}else{
								getInActiveMatchList("Match Odds", false);
							}							
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