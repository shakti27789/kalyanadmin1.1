/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});
$(document).ready(function() {
	$('.navbar-toggle').click(function(){
		$('.sidebar').toggleClass('active-sidebar');
		$('#page-wrapper').toggleClass('active-page-wrapper');
		if($('.sidebar').hasClass('active-sidebar')){
			$('body,html').css('overflow','hidden');
		}else{
			$('body,html').css('overflow','auto');
		} 	
	});
}); 
//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size

