<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="page-wrapper">
			<div class="balance">
				<div class="status">
					<dl class="status-list">              
						<dt>Current Pnl:</dt>
						<dd>0.00</dd>
						<dt>Net Exposure:</dt>
						<dd>0.00</dd>
						<dt>Total Credit Given to:</dt>
						<dd class="green">10,000.00</dd>
						<dt>Available Credit :</dt>
						<dd class="green">10,000.00</dd>
					</dl>  
				</div>
			</div>
            <div class="inner-wrap">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">User List [1] <a class="btn new-member-btn pull-right" href="create-member.html">New Member</a></h1>
						<section class="menu-content">
							<table class="table common-table">
								  <thead>
									  <tr>
										  <th>Login Name</th>
										  <th>User ID</th>
										  <th>Net Exposure</th>
										  <th>CreditLimit</th>                          
										  <th>Current P&amp;L</th>           
										  <th>Commission</th>
										  <th>Balance</th>
										  <th>Action</th>
									  </tr>
								  </thead>
								  <tbody>                      
									  <tr>
										  <td> <a class="usericon" href="#">ABD3</a></td>
										  <td>ABD3</td>
										  <td><button>0.00</button></td>
										  <td></td>
										 <td class="green">0.00</td> 
										  <td class="green">0.00</td>
										  <td class="green ng-binding">10,000.00</td>                         
										  <td>
											<span class="btn green">D</span>
											<span class="btn red">W</span>
											<span class="btn yellow"><i class="fa fa-eye"></i></span> 
											<span class="btn yellow"><i class="fa fa-lock"></i> L</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> A/C LK</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> Bet LK</span>
											<span class="btn yellow">Transfer</span>
										  </td>                          
									  </tr>
									  <tr>
										  <td> <a class="usericon" href="#">ABD3</a></td>
										  <td>ABD3</td>
										  <td><button>0.00</button></td>
										  <td>10,000.00</td>
										 <td class="green">0.00</td> 
										  <td class="green">0.00</td>
										  <td class="green ng-binding">10,000.00</td>                         
										  <td>
											<span class="btn green">D</span>
											<span class="btn red">W</span>
											<span class="btn yellow"><i class="fa fa-eye"></i></span> 
											<span class="btn yellow"><i class="fa fa-lock"></i> L</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> A/C LK</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> Bet LK</span>
											<span class="btn yellow">Transfer</span>
										  </td>                          
									  </tr>
									  <tr>
										  <td> <a class="usericon" href="#">ABD3</a></td>
										  <td>ABD3</td>
										  <td><button>0.00</button></td>
										  <td>10,000.00</td>
										 <td class="green">0.00</td> 
										  <td class="green">0.00</td>
										  <td class="green ng-binding">10,000.00</td>                         
										  <td>
											<span class="btn green">D</span>
											<span class="btn red">W</span>
											<span class="btn yellow"><i class="fa fa-eye"></i></span> 
											<span class="btn yellow"><i class="fa fa-lock"></i> L</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> A/C LK</span>
											<span class="btn yellow"><i class="fa fa-lock"></i> Bet LK</span>
											<span class="btn yellow">Transfer</span>
										  </td>                          
									  </tr>
								  </tbody>
								</table>			  

						</section>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row --> 
            </div>           
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    
    
    <script>
    $(document).ready(function(){
    	punterList();
    	
    });
    
    function punterList()
    {
    	
    	$.ajax({
			type:'GET',
			url:'<s:url value="api/user/${user.id}/"/>'+3,
			success: function(jsondata, textStatus, xhr) {
			{
			
			}
		}
	});
    }
    </script>