<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
    <div id="page-wrapper">			

            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Set Min Max Bet</h1>
						<div class="filterSection full-width-form">
						
						<ul>
						<li>
						<select class="form-control" id="wincom" name="wincom" >
											     <option value="-1" >Select Winning Commssion</option>
											     <option value="0" >0</option>
											     <option value="0.10">0.10</option>
											     <option value="0.20">0.20</option>
											     <option value="0.30">0.30</option>
											     <option value="0.40">0.40</option>
											     <option value="0.50">0.50</option>
											     <option value="0.60">0.60</option>
							   			         <option value="0.70">0.70</option>
										         <option value="0.80">0.80</option>
										         <option value="0.90">0.90</option>
										         <option value="1.10">1.10</option>
									             <option value="1.20">1.20</option>
						       	                 <option value="1.30">1.30</option>
											     <option value="1.40">1.40</option>
											     <option value="1.50">1.50</option>
											     <option value="1.60">1.60</option>
											     <option value="1.70">1.70</option>
											     <option value="1.80">1.80</option>
											     <option value="1.90">1.90</option>
											     <option value="2.10">2.10</option>
											     <option value="2.20">2.20</option>
											     <option value="2.30">2.30</option>
											     <option value="2.40">2.40</option>
											     <option value="2.50">2.50</option>
											     <option value="2.60">2.60</option>
											     <option value="2.70">2.70</option>
											     <option value="2.80">2.80</option>
											     <option value="2.90">2.90</option>
											     <option value="3.00">3.00</option>
											     <option value="3.10">3.10</option>
											     <option value="3.20">3.20</option>
											     <option value="3.30">3.30</option>
											     <option value="3.40">3.40</option>
											     <option value="3.50">3.50</option>
											     <option value="3.60">3.60</option>
											     <option value="3.70">3.70</option>
											     <option value="3.80">3.80</option>
											     <option value="3.90">3.90</option>
											     <option value="4.00">4.00</option>
											     <option value="4.10">4.10</option>
											     <option value="4.20">4.20</option>
											     <option value="4.30">4.30</option>
											     <option value="4.40">4.40</option>
											     <option value="4.50">4.50</option>
											     <option value="4.60">4.60</option>
											     <option value="4.70">4.70</option>
											     <option value="4.80">4.80</option>
											     <option value="4.90">4.90</option>
											     <option value="5.00">5.00</option>
											     
											      
											    </select>
						</li>
						
							<li>				 
					 <button class="btn btn-info blue-btn btn-sm" onclick='setWinnigCommssion()' >Set Winning Commssion</button>
						</li>
						</ul>
						</div>

					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
  
   <script>
    
    

  
  function setWinnigCommssion() {

	              var wincom =$("#wincom").val();
    			
			   	 $.ajax({
			   	 
						type:'POST',
						url:'<s:url value="/api/setWinnigCommssion"/>?winningcommssion='+wincom,
						success: function(jsondata, textStatus, xhr) {
							showMessage(jsondata.message,jsondata.type,jsondata.title);
							
						},
						complete: function(jsondata,textStatus,xhr) {
							showMessage(jsondata.responseJSON.message,jsondata.responseJSON.type,jsondata.responseJSON.title);
					    } 
					});     	    	
		    }
  
  
    </script>
 