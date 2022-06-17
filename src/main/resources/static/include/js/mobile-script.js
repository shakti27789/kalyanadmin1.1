/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
// function myFunction() {
//     document.getElementById("myDropdown").classList.toggle("show");
//   }
  
//   // Close the dropdown menu if the user clicks outside of it
//   window.onclick = function(event) {
//     if (!event.target.matches('.dropbtn')) {
//       var dropdowns = document.getElementsByClassName("dropdown-content");
//       var i;
//       for (i = 0; i < dropdowns.length; i++) {
//         var openDropdown = dropdowns[i];
//         if (openDropdown.classList.contains('show')) {
//           openDropdown.classList.remove('show');
//         }
//       }
//     }
//   }

//   search box toggle

  // $(document).ready(function(){
  //   $(".fa-search-plus").click(function(){
  //     $("#search-input").toggleClass("active");
  //   });
  // });


  // popup  back window
  
    // Get the modal
    var modal = document.getElementById("back-record");
    
    // Get the button that opens the modal
    var btn = document.getElementById("back-btn");
    
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close");
    
    // When the user clicks the button, open the modal 
    btn.onclick = function() {
      modal.style.display = "block";
    }
    
    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
      modal.style.display = "none";
    }
    
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }

    // popup lay window
  
    // Get the modal
    var modal = document.getElementById("lay-record");
    
    // Get the button that opens the modal
    var btn = document.getElementById("lay-btn");
    
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    
    // When the user clicks the button, open the modal 
    btn.onclick = function() {
      modal.style.display = "block";
    }
    
    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
      modal.style.display = "none";
    }
    
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
      if (event.target == modal) {
        modal.style.display = "none";
      }
    }
   

   



    <script>
    function myFunction() {
      var x = document.getElementById("myTopnav");
      if (x.className === "topnav") {
        x.className += " responsive";
      } else {
        x.className = "topnav";
      }
    }
    // </script>
   
  //  <script>

      $(document).ready(function(){
        $(".toggle-link").click(function(){
          $(".toggle-content").slideToggle(400);
        });
      });
    // </script>
    // <script>
      function alertFunction() {
        alert("Please select user!");
      }
      // </script>
      

    // <!-- <script>

      function togglesidebar(){
        document.getElementById("side-menubar").classList.toggle("active");
      }
    // </script> -->
   
   
  //  <script>
       $(document).ready(function(){
         $(".toggle-btn").click(function(){
           $("#side-menubar").toggleClass("active");
         });
       });
    // </script>
          
  //  <script>
     $(document).ready(function(){
       $(".cross-btn").click(function(){
         $("#side-menubar").removeClass('active');
       });
     });
  //  </script>
  // <script>
    var sel = '#accordion'
  $(sel + ' li.selectelist a').click( function(){
  var checkElement = $(this).next();
  if((checkElement.is('ul.sub-listitem2')) && (checkElement.is(':visible'))) {
    checkElement.slideUp('normal').removeClass('selected');
  }
  else if((checkElement.is('ul.sub-listitem2')) && (!checkElement.is(':visible'))) {
    $(sel + ' ul.sub-listitem2:visible').slideUp('normal').removeClass('selected');
    checkElement.addClass('selected').slideDown('normal', function() {
  
    });
  }
  });
  {/* </script>
  <script> */}
    var sel = '#accordion'
  $(sel + ' li.sub-selectelist a').click( function(){
  var checkElement = $(this).next();
  if((checkElement.is('ul.date-listitem2')) && (checkElement.is(':visible'))) {
    checkElement.slideUp('normal').removeClass('selected');
  }
  else if((checkElement.is('ul.date-listitem2')) && (!checkElement.is(':visible'))) {
    $(sel + ' ul.date-listitem2:visible').slideUp('normal').removeClass('selected');
    checkElement.addClass('selected').slideDown('normal', function() {
  
    });
  }
  });
  {/* </script>
  <script> */}
    var sel = '#accordion'
  $(sel + ' li.last-selectelist a').click( function(){
  var checkElement = $(this).next();
  if((checkElement.is('ul.team-name')) && (checkElement.is(':visible'))) {
    checkElement.slideUp('normal').removeClass('selected');
  }
  else if((checkElement.is('ul.team-name')) && (!checkElement.is(':visible'))) {
    $(sel + ' ul.team-name:visible').slideUp('normal').removeClass('selected');
    checkElement.addClass('selected').slideDown('normal', function() {
  
    });
  }
  });
  {/* </script> */}
  
  

  