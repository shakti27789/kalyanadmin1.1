<!DOCTYPE html>
<html lang="en">
<?php include 'header.php';?>
<body>
<?php include 'mainHeader.php';?>




<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<!-- external javascript -->
<script src="./js/mobile-script.js"></script>




<!-- /* When the user clicks on the button,
toggle between hiding and showing the dropdown content */ -->

<script>
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
  </script>



      <!-- search box toggle -->

      <script>
    $(document).ready(function(){
      $(".fa-search-plus").click(function(){
        $("#search-input").toggleClass("active");
      });
    });
</script>

  <!--************popup lay-back *************-->
  <script> 
    $(document).ready(function(){
       $(".btn-lay").click(function(){
         $(".pop-up-box-lay").show();
       });
       $(".btn-lay-close").click(function(){
         $(".pop-up-box-lay").hide();
       });
     });
    </script>

<script> 
      $(document).ready(function(){
       $(".btn-back").click(function(){
         $(".pop-up-box-back").show();
       });
       $(".btn-back-close").click(function(){
         $(".pop-up-box-back").hide();
       });
     });
    </script>
        



        <!--************** input numbring*********** -->

        <script>
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
          
               </script>
                  
        
             <script>
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
        
             </script>




</body>
</html>