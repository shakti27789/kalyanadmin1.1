<script>

	Handlebars.registerHelper('if_eq', function(a, b, opts) {
		if (a == b) {
			return opts.fn(this);
		} else {
			return opts.inverse(this);
		}
	});

	Handlebars.registerHelper('if_neq', function(a, b, opts) {
		if (a != b) {
			return opts.fn(this);
		} else {
			return opts.inverse(this);
		}
	});

	Handlebars.registerHelper('if', function(a, b, opts) {
		if (a < b) {
			return opts.fn(this);
		} else {
			return opts.inverse(this);
		}
	});
	
	Handlebars.registerHelper('if_small', function(a, b, opts) {
	    if (a < b) {
	        return opts.fn(this);
	    } else {
	        return opts.inverse(this);
	    }
	});

	Handlebars.registerHelper("inc", function(value, options)
			{
			    return parseInt(value) + 1;
			});
			
	Handlebars.registerHelper("inctemp", function(value, options)
			{
			    return parseInt(value) + 1;
			});


	Handlebars.registerHelper('remaider', function (index, options) {
		
		if(index%2 == 0 && index !=0){
			return options.fn(this);
		   } else {
		      return options.inverse(this);
		   }

		});

	     Handlebars.registerHelper("removedot", function(value, options)
			{
		
		var checkedNew = value.split('.').join("");
		    return checkedNew;
			});

	

    Handlebars.registerHelper('if_sm', function(a, b, opts) {
        if (a < b) {
            return opts.fn(this);
        } else {
            return opts.inverse(this);
        }
    });
    
    Handlebars.registerHelper('if_greaterThan', function(a, b, opts) {
	    if (a > b) {
	        return opts.fn(this);
	    } else {
	        return opts.inverse(this);
	    }
	});
    
    Handlebars.registerHelper('if_ne', function(a, b, opts) {
        if (a != b) {
            return opts.fn(this);
        } else {
            return opts.inverse(this);
        }
    });

    Handlebars.registerHelper("if_includes", function(a, b, opts)
			{
            var status=true;
		    if(a.includes(b)){
		    	 return opts.fn(this);
		    	
			}else{
				return opts.inverse(this);
			}
    	});

    Handlebars.registerHelper("strtoArray", function(value, options){
		
    	  var aalltemp = new Array();
		  aalltemp = value.split(",");
		  return aalltemp;
	});

    Handlebars.registerHelper("arrayLength", function(a, b, opts){
		  if (a.length > b) {
			
		        return opts.fn(this);
		    } else {
			 
		        return opts.inverse(this);
		    }
	});


    Handlebars.registerHelper("removeSpace", function(value, options){
		
  	  
		  C2 = value.trim();
		  return C2;
	});


    Handlebars.registerHelper("strTODate", function(value, options){
		
    	  
    	let current_datetime  = new Date(value);
    	let formatted_date = current_datetime.getFullYear() + "-" + (current_datetime.getMonth() + 1) + "-" + current_datetime.getDate() + " " + current_datetime.getHours() + ":" + current_datetime.getMinutes() + ":" + current_datetime.getSeconds() 
    	console.log(formatted_date)
    	
		  return formatted_date;
	});
</script>