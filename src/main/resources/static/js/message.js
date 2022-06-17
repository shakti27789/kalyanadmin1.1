function showMessage(message,type,title)
{ 
	 swal({
		  	position: 'top-end',  
			title: title,
           text: message,
           type: type,
           timer: 1000,
           showConfirmButton: false
       });
	 
}

function showSessionMessage(message,type,title,rate,mode,amount,session,run)
{
	 swal({
		  	position: 'top-end',  
		   title: title,
           text: message,
           html: '<p style="display: block;">'+message+'<br>Session :'+session+'<br>Run :' +run+'<br>Mode : '+mode+'<br>Amount : '+amount+'<br>Rate : '+rate+'</p>',  
           type: type,
           button: "Aww yiss!"
       });
	 
}


function showMatchOddsMessage(message,type,title,rate,mode,amount,team)
{
	 swal({
		  	position: 'top-end',  
		   title: "Match Bet",
           text: message,
           html: '<p style="display: block;">Rate :' +rate+'<br>Mode : '+mode+'<br>Amount : '+amount+'<br>Team : '+team+'</p>',  
           type: type,
           button: "Aww yiss!"
       });
	 
}