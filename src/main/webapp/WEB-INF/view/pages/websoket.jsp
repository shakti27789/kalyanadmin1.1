  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%>

    
    <script>
    $(document).ready(function(){
    	connect();
    });
    var ws;
    function setConnected(connected) {
    	$("#connect").prop("disabled", connected);
    	$("#disconnect").prop("disabled", !connected);
    }

    function connect() {
    	ws = new WebSocket('ws://bm.com:8081/bfuser');
    	ws.onmessage = function(data) {
    		helloWorld(data.data);
    	}
    	setConnected(true);
    }

    function disconnect() {
    	if (ws != null) {
    		ws.close();
    	}
    	setConnected(false);
    	console.log("Websocket is in disconnected state");
    }

    function sendData() {
    	var data = JSON.stringify({
    		'user' : $("#user").val()
    	})
    	ws.send(data);
    }

    function helloWorld(message) {
    	$("#helloworldmessage").append(" " + message + "");
    }

    $(function() {
    	$("form").on('submit', function(e) {
    		e.preventDefault();
    	});
    	$("#connect").click(function() {
    		connect();
    	});
    	$("#disconnect").click(function() {
    		disconnect();
    	});
    	$("#send").click(function() {
    		sendData();
    	});
    });


    </script>
</head>
<body>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-8">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">Chat Application:</label>
                    <button id="connect" type="button">Start New Chat</button>
                    <button id="disconnect" type="button" disabled="disabled">End Chat
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="chat">
                <thead>
                <tr>
                    <th>Welcome user. Please enter you name</th>
                </tr>
                </thead>
                <tbody id="helloworldmessage">
                </tbody>
            </table>
        </div>
            <div class="row">
        
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <textarea id="user" placeholder="Write your message here..." required></textarea>
                </div>
                <button id="send" type="submit">Send</button>
            </form>
        </div>
        </div>
    </div>
 
</div>