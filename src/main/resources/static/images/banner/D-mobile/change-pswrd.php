<!DOCTYPE html>
<html lang="en">
<?php include 'header.php';?>
<body>
<?php include 'mainHeader.php';?>  
<div class="main-content">
       <div class="box active detail-box">
<!-- start box -->

       <div class="account-container">
  
  <div class="account-record">
      <div class="head-tittle">
          <h5>Change Password</h5>
      </div>

     <div class="form box mt-2 p-1">
     <form action="/action_page.php">

<div class="form-group">
<label for="pwd"> Current Password:</label>
<input type="password" class="form-control" placeholder="Enter password" id="pwd">
</div>
<div class="form-group">
<label for="pwd"> New Password:</label>
<input type="password" class="form-control" placeholder="Enter password" id="pwd">
</div>
<div class="form-group">
<label for="pwd"> ConfirmPassword:</label>
<input type="password" class="form-control" placeholder="Enter password" id="pwd">
</div>

<button type="submit" class="btn btn-primary changePaswrd  w-100">Change Password</button>
</form>
     </div>

  </div>
</div>
<!-- ebd box -->
       </div>
</div>




<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>


<script src="js/jquery.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/bootstrap-datepicker.min.js"></script>


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




<script>
    $(document).ready(function() {
    $('#myTable').DataTable();
    } );
</script>

<script>
   $('.datepicker').datepicker();
    </script>



</body>
</html>