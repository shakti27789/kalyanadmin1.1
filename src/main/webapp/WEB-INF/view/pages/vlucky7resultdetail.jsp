<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <script id="resultHandler" type="text/x-handlebars-template">
 <div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
                 
                </div>
                
                <div class="col-md-12 text-center" id="detail"><span class="text-success">Result:</span><span>{{data.detail}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
   
   <div id="modalresult" class="modal modal-pop" tabindex="-1" style="padding-right: 17px;">
<div class="modal-dialog" style="min-width: 650px">
<div class="modal-content">

<div class="modal-header">
<h4 class="modal-title">Result Details</h4>
<button type="button" class="close" data-dismiss="modal">×</button>
</div>

<div class="modal-body nopading" id="result-details" style="min-height: 300px;display:block;" ><style type="text/css">
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
</style>
<div class="container-fluid">
    <div class="row m-t-10">
        <div class="col-md-12">
            <span class="float-right round-id"></span>
        </div>
    </div>
    
    
       <div class="row player-container m-t-10">
       
    </div> 
        
   
</div>

</div>
</div>
</div>
</div>
   
   
  
  <!-- withdraw end -->
  
  <script>

 function resultDeatilVLucky7 (marketId){ 
     // $(".resultdetail-popUp").addClass("active");
      $('#modalresult').modal('show');

      $.ajax({
			type:'GET',
			url:'<s:url value="/api/resultDetailVLucky7/"/>'+marketId,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200){ 		
					//console.log(jsondata)
					var data =JSON.parse(jsondata);
					
					$(".round-id").html("<b>Round ID:</b>"+data.mid)
					var source   = $("#resultHandler").html();
			    	  var template = Handlebars.compile(source);
					$(".player-container").html( template({data:data}) );
					/*  */
					
					
					
				}
		}
	});
  }
      

  	 

  </script>
  
  <style>
  
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