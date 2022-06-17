var base_url = window.location.origin;





var expo_base_url ="http://23.106.234.25:9001";
var activeFancy_url ="http://23.106.234.25:9001";
var betlist_base_url ="http://23.106.234.25:9001/";
var bfApi ="http://3.109.65.15/";




//var expo_base_url ="http://bm.com:9001";
//var activeFancy_url ="http://bm.com:9001";
//var betlist_base_url ="http://bm.com:9001";
// var bfApi ="http://72.10.163.178:4005/";


var devpathdata="betex/t20/data/data"
var devpathresult ="betex/t20/result"
		
var vlucky7data ="betex/lucky7a/"
var vdtdata  ="betex/dt20/"
//
var vdtldata ="betex/dtl20/"
var vaaadata  ="betex/aaa/"
var vbwtbldata  ="betex/bwd/"	
var livelucky7data  ="diamond/lucky7a/"	

	
var vandarbahardata  ="betex/ab20/"	
var vopenteenpattidata  ="betex/otp/"	
var vandarbahar2data  ="betex/ab2/"	
var vroulettedata  ="betex/roulette/"	
	
var livet20data  ="diamond/t20/"
var livet1daydata  ="diamond/odtp/"	
var livedtdata  ="diamond/dt20/"
var livedt1daydata  ="diamond/dtoneday/"
var liveaaadata  ="diamond/aaa/"
var livebwdata  ="diamond/bwd/"
var livepoker20  ="diamond/poker20/"
var livepoker1day  ="diamond/poker1day/"
var liveandarbahar  ="diamond/ab20/"	
	
	
function showMessage(message,type,title){
	swal({
		  	position: 'top-end',  
			title: title,
           text: message,
           type: type,
           timer: 3000,
           showConfirmButton: false
       });
	 
}
function showSessionMessage(message,type,title,rate,mode,amount,session,run){
	 swal({
		  	position: 'top-end',  
		   title: title,
           text: message,
           html: '<p style="display: block;">Session :'+session+'Run :' +run+'<br>Mode : '+mode+'<br>Amount : '+amount+'<br>Rate : '+rate+'</p>',  
           type: type,
           button: "Aww yiss!"
       });
	 
}
