<!DOCTYPE html>
<html lang="en">
<?php include 'header.php';?>
<body>
<?php include 'mainHeader.php';?> 
<div class="main-content">
    <div class="box active detail-box">
    <div class="account-container">
  
  <div class="account-record">
  <div class="head-tittle">
      <h4>Bet History</h4>
  </div>
  
  <div class="input-date">
    <div class="select-box match-box bet-hstry">
        <form>
            <select name="cars" class="custom-select">
              <option selected>Football</option>
              <option value="volvo">Cricket</option>
              <option value="fiat">Tennis</option>
              <option value="audi">Kabaddi</option>
            </select>
          </form>
    </div>
    <div class="select-box match-box bet-hstry">
        <form>
            <select name="cars" class="custom-select">
              <option selected>Matched</option>
              <option value="volvo">Unmathed</option>
             
            </select>
          </form>
    </div>

    <div class="date-box"><input class="datepicker fa fa-calendar-o" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
    <div class="date-box"><input class="datepicker fa fa-calendar-o" ><i class="fa fa-calendar-o" aria-hidden="true"></i></div>
  

    <div class="butn-box">
        <input type="button" value="Submit">
    </div>
   </div>
  
    <div class="account-table bethistory table-responsive ">
    <table id="myTable" class="table table-striped table-bordered " style="width:100%">

        <thead>
            <tr>
                <th>Event Name</th>
                <th>Nation</th>
                <th>Bet Type</th>
                <th>User Rate</th>
                <th> Amount</th>
                <th >Profit/Loss</th>
                <th >Place Date</th>
                <th >Match Date</th>
            </tr>
        </thead>
        <tbody>
            <tr class="back"> 
                <td>Goredaya (Res) v Shakhter Soligorsk (Res)</td>
                <td>Shakhter Soligorsk (Res)</td>
                <td>BACK</td>
                <td>16.4</td>
                <td>1000</td>
                <td>-1000</td>
                <td>-3/27/2020 4:48:07 PM</td>
                <td>-3/27/2020 4:48:07 PM</td>
            </tr>
            <tr class="lay">
                <td>Goredaya (Res) v Shakhter Soligorsk (Res)</td>
                <td>Shakhter Soligorsk (Res)</td>
                <td>Lay</td>
                <td>16.4</td>
                <td>1000</td>
                <td>-1000</td>
                <td>-3/27/2020 4:48:07 PM</td>
                <td>-3/27/2020 4:48:07 PM</td>
            </tr>
           
           
           
        </tbody>

    </table>
   </div>
</div>
</div>

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