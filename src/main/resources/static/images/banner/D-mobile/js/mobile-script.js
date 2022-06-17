/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
  }
  
  // Close the dropdown menu if the user clicks outside of it
  window.onclick = function(event) {
    if (!event.target.matches('.dropbtn')) {
      var dropdowns = document.getElementsByClassName("dropdown-content");
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }

//   search box toggle

  $(document).ready(function(){
    $(".fa-search-plus").click(function(){
      $("#search-input").toggleClass("active");
    });
  });


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
   

   
// ************popup lay-back *************
      $(document).ready(function(){
       $(".btn-lay").click(function(){
         $(".pop-up-box-lay").show();
       });
       $(".btn-lay-close").click(function(){
         $(".pop-up-box-lay").hide();
       });
     });
    

 
      $(document).ready(function(){
       $(".btn-back").click(function(){
         $(".pop-up-box-back").show();
       });
       $(".btn-back-close").click(function(){
         $(".pop-up-box-back").hide();
       });
     });
   





// ************input number*********
  function increaseValueback() {
    var value = parseInt(document.getElementById('number-back').value, 10);
    value = isNaN(value) ? 0 : value;
    value++;
    document.getElementById('number-back').value = value;
  }
  
  function decreaseValueback() {
    var value = parseInt(document.getElementById('number-back').value, 10);
    value = isNaN(value) ? 0 : value;
    value < 1 ? value = 1 : '';
    value--;
    document.getElementById('number-back').value = value;
  }
  
      
          

   
function increaseValuelay() {
  var value = parseInt(document.getElementById('number-lay').value, 10);
  value = isNaN(value) ? 0 : value;
  value++;
  document.getElementById('number-lay').value = value;
}

function decreaseValuelay() {
  var value = parseInt(document.getElementById('number-lay').value, 10);
  value = isNaN(value) ? 0 : value;
  value < 1 ? value = 1 : '';
  value--;
  document.getElementById('number-lay').value = value;
}

  
  