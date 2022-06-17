//kalyan Old



var firebaseConfig = {
		    apiKey: "AIzaSyBhRct3K_0fsEz_EhKD-wlAgqA6Fh-dpAI",
		    authDomain: "aaaa-14afe.firebaseapp.com",
		    databaseURL: "https://aaaa-14afe.firebaseio.com",
		    projectId: "aaaa-14afe",
		    storageBucket: "aaaa-14afe.appspot.com",
		    messagingSenderId: "333971305520",
		    appId: "1:333971305520:web:10cdf1a6fd5407ecf0a9f5",
		    measurementId: "G-X98DVQR0KM"
	  };


/*
var firebaseConfig =  {
apiKey: "AIzaSyALalsMpoA97_GzTuK7nVVBqIhTzqIMM8k",
authDomain: "cricket-exchange.firebaseapp.com",
databaseURL: "https://cricket-exchange.firebaseio.com",
projectId: "cricket-exchange",
storageBucket: "cricket-exchange.appspot.com",
messagingSenderId: "1038230111400",
appId: "1:1038230111400:web:08bc9cfe99a1048de62e9d",
measurementId: "G-STW4LR3BW5"
};
*/
		

//developmentNew
	

	/*var firebaseConfig = {
			    
			    			  apiKey: "AIzaSyBI730Q4ILB7VF67ZgIKYygw86tOI1L3bE",
    authDomain: "development-924a5.firebaseapp.com",
    databaseURL: "https://development-924a5.firebaseio.com",
    projectId: "development-924a5",
    storageBucket: "development-924a5.appspot.com",
    messagingSenderId: "614877823519",
    appId: "1:614877823519:web:050c1d9e10597b112acb2a",
    measurementId: "G-WWR95EZNRG"
			    			  
			    		};*/
	
	

	 
	 
	 
	 /*Fire base Casino */
	 
	var firebaseConfigCasino = {
			    apiKey: "AIzaSyBhRct3K_0fsEz_EhKD-wlAgqA6Fh-dpAI",
			    authDomain: "aaaa-14afe.firebaseapp.com",
			    databaseURL: "https://aaaa-14afe.firebaseio.com",
			    projectId: "aaaa-14afe",
			    storageBucket: "aaaa-14afe.appspot.com",
			    messagingSenderId: "333971305520",
			    appId: "1:333971305520:web:10cdf1a6fd5407ecf0a9f5",
			    measurementId: "G-X98DVQR0KM"
			  };
			  
	 
	
	/* var firebaseConfigCasino = {
			    apiKey: "AIzaSyBI730Q4ILB7VF67ZgIKYygw86tOI1L3bE",
			    authDomain: "development-924a5.firebaseapp.com",
			    databaseURL: "https://development-924a5.firebaseio.com",
			    projectId: "development-924a5",
			    storageBucket: "development-924a5.appspot.com",
			    messagingSenderId: "614877823519",
			    appId: "1:614877823519:web:3a1e378a5ca412682acb2a",
			    measurementId: "G-QDCXTWPRTD"
			  };
	 
	 */
	 
	
	 
// Initialize Firebase
	 
firebase.initializeApp(firebaseConfig);

var db = firebase.firestore();



//firebase.initializeApp(firebaseConfigCasino);
var secondaryApp = firebase.initializeApp(firebaseConfigCasino, "secondary");
firebase.analytics();

