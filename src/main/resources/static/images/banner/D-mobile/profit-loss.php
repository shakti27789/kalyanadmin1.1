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
      <h5>Profit Loss</h5>
  </div>
  
  <div class="input-date">

    <div class="date-box"><input class="datepicker fa fa-calendar-o" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
    <div class="date-box"><input class="datepicker fa fa-calendar-o" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
    <div class="butn-box"> <input type="button" value="Submit"></div>
  </div>
  
  <div class="account-table table-responsive">
    <table id="myTable" class="table table-striped table-bordered" id="prft-loss"  style="width:100%">

        <thead>
            <tr>
                <th>Event Type</th>
                <th>Event Name</th>
                <th>Amount</th>
              
            </tr>
        </thead>
        <tbody >
            <tr>
                <td>Football</td>
                <td>Match_Odds</td>
                <td>1000</td>
               
            </tr>
            <tr>
                <td>Cricket</td>
                <td>Match_Odds</td>
                <td>1000</td>
               
            </tr>
            <tr>
                <td>Tennis</td>
                <td>Match_Odds</td>
                <td>1000</td>
               
            </tr>
            <tr>
                <td>Kabaddi</td>
                <td>Match_Odds</td>
                <td>1000</td>
               
            </tr>
           
        </tbody>

    </table>
</div>
  </div>
</div>

              
          <!-- end box -->    
    </div>
      

</div>










 <!-- jQuery library -->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->

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