var tvData = "";
var response = CryptojsDecrypt(apiData);
tvData = response.data;
var sslTv
tvData.ssl == true ? sslTv = true : sslTv = false

showMediaPlayer(response.data);

function showMediaPlayer(tvData){
    if(platform == "ios"){
        var webrtcPlayer = null;
        webrtcPlayer = new UnrealWebRTCPlayer("p1", tvData.camstring, "", tvData.IP, tvData.port, sslTv, true, tvData.ptype);
        // webrtcPlayer = new UnrealWebRTCPlayer("p1", tvData.camstring, tvData.sesid, tvData.IP, tvData.port, tvData.ssl, true, tvData.ptype);
        webrtcPlayer.Play();
    }else{
        if ("MediaSource" in window && "WebSocket" in window)
        {
            RunPlayer("p1", '100%', '100%', tvData.IP, tvData.port, sslTv, tvData.camstring, "", true, true, 0.01, "", false);    
            // RunPlayer("p1", '100%', '100%', tvData.IP, tvData.port, tvData.ssl, tvData.camstring, tvData.sesid, true, true, 0.01, "", false);    
            document.getElementById("p1_Video").muted = true;
            document.getElementById("p1_fullscreen").style.display = "none";
           
            // setInterval(function () 
            // { 
            //     var video = document.getElementById("p1_Video");
            //     if(!video.paused){
            //         video.currentTime = video.duration;  
            //     }
            // }, 5000);
        }
        else{
           document.getElementById("p1").innerHTML = "Media Source Extensions or Websockets are not supported in your browser.";
        }
    }
}
    