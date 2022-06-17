<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 


<script id="currentBetsHandler" type="text/x-handlebars-template">
<table id="myTable" class="table table-striped table-bordered " style="width:100%">

<thead>
    <tr>
		 
<th>UserId</th>	
        <th>Event Name</th>
        <th>Nation</th>
        <th>Bet Type</th>
        <th>User Rate</th>
        <th> Amount</th>
       <th >Match Date</th>
    </tr>
</thead>
<tbody>
  {{#each data}}
{{#if_eq isback true}}
  <tr >
		
        <td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
     	{{#if_eq marketname "Fancy"}}
         <td>Yes->  {{pricevalue}}</td>
     {{else}} 
        <td>BACK</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>
{{else}} 
<tr >		
<td>{{userid}}</td>	
        <td>{{matchname}}</td>
        <td>{{selectionname}}</td>
        {{#if_eq marketname "Fancy"}}
         <td>No->  {{pricevalue}}</td>
     {{else}} 
        <td>LAY</td>
      {{/if_eq}}
        <td>{{odds}}</td>
        <td>{{stake}}</td>
        <td>{{matchedtime}}</td>
    </tr>

{{/if_eq}}	

   {{/each}}  
   
</tbody>

</table>
</script>

<div id="currentbetsmodal" class="modal modal-pop" tabindex="-1" style="padding-right: 17px;">
	<div class="modal-dialog" style="width: 650px; max-width: 100%;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">UserBook</h4>
				<button type="button" class="close" data-dismiss="modal">×</button>
			</div>
			<div class="modal-body" id="result-details" style="min-height: 300px; padding: 20px 15px!important; display:block;">
				<div class="table-responsive" id="currentBets">
					
				</div>
			</div>
		</div>
	</div>
</div>
   
   
  
  <!-- withdraw end -->
  
  <script>

  function currentbetsmodal(userId){ 
	     // $(".resultdetail-popUp").addClass("active");
	      $('#currentbetsmodal').modal('show');
	      currentBets(userId);
	    
	  }
	  
  function currentBets(userId){


  	   $("#currentBets").append('<div class="loader"></div>')
    	
  	  $.ajax({
  		    type:'POST',
  			url:'<s:url value="/api/currentBetsNetExpo"/>'+"/"+userId,
  			contentType:"application/json; charset=utf-8",
  			success: function(jsondata, textStatus, xhr) {
  				$("#currentBets").html('');
  				if(xhr.status == 200){ 
   						
  					var source   = $("#currentBetsHandler").html();
					var template = Handlebars.compile(source);
			 	    $("#currentBets").append( template({data:jsondata})); 
			 	    $(".loader").fadeOut("slow");
       				    
  				
  			}
  		}
  	  });

  }
  	 

  </script>
  
  
  <style type="text/css">
    .board-result-inner{
        background: #f9f9f9;
        padding: 10px;
        font-size: 17px;
    }
    .yellowbx{
        color: #caca03;
    }
    .player-image-container img{
        max-width: 45px;
    }
    .c-title{
        font-weight: bold;
        font-size: 24px;
        margin-bottom: 6px;
        color: #222;
        text-align: center;
        margin-top: 15px;
    }
    .eightplayer img
    {
        width: 35px;
        margin-right: 0px;
    }
    .result-row
    {
        position: relative;
        margin-bottom: 50px;
    }
    .result-row .winner-label 
    {
        font-size: 16px;
        position: absolute;
        margin-top: 10px;
        left: 50%;
        transform: translateX(-50%);
    }
    .v-t
    {
        vertical-align: top;
    }
    .winner-icon i {
        font-size: 26px;
        box-shadow: 0 0 0 var(--secondary-bg);
        -webkit-animation: winnerbox 2s infinite;
        animation: winnerbox 1.5s infinite;
        border-radius: 50%;
        color: #169733;
    }

    @-webkit-keyframes winnerbox {
        0% {
            -webkit-box-shadow: 0 0 0 0 rgba(29, 127, 30, .6)
        }

        70% {
            -webkit-box-shadow: 0 0 0 10px rgba(29, 127, 30, 0)
        }

        100% {
            -webkit-box-shadow: 0 0 0 0 rgba(29, 127, 30, 0)
        }
    }

 
  .modal-open .modal {
    overflow-x: hidden;
    overflow-y: auto;
}

.modal {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    display: none;
    overflow: hidden;
    outline: 0;
}
  
  .modal-header .close {
    opacity: 1;
    border-radius: 50%;
    padding: 0px;
    height: 35px;
    width: 35px;
    margin-top: -2px;
        background-color: black;
    color: white;
}

.container-fluid {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
}
.player-number {
     padding-right: 20px;
    flex: 1;
}


.winner-label {
    font-size: 22px;
    color: #fff;
    box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.5);
    padding: 10px;
}

.m-t-20 {
    margin-top: 20px !important;
}
  </style>