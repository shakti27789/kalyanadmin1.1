<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="bets" id="matchoddsbetslipback">
								<h3 class="bet-type back">Back</h3>
								<ul class="market-list">
									<li>
										<a href="#" class="event-name backmacthoddsbetslip" id="backmacthoddsbetslipmatchname" ></a>
										<ul class="bets-list">
											<li>
												<div class="bet back">
													<span class="selection-name"></span>													
													<div class="bet-fields">
														<ul>
															<li>
																<label>Odds</label>
																<div class="odd-input">
																	<input type="text" value="" class="step-input" id="backmacthoddsbetslipodds" />
																	<div class="button-wrapper">
																		<button class="step-up"><i class="fa fa-angle-up"></i></button>
																		<button class="step-down"><i class="fa fa-angle-down"></i></button>
																	</div>
																</div>
															</li>
															<li>
																<label>&nbsp;</label>
																<input type="text" class="stake-input" id="stakeinputbackmarchodds" placeholder="Stake" />
															</li>
															<li>
																<label>Profit</label>
																<span class="profit-count">12</span>
															</li>
															<li class="pull-right">
																<button class="btn close">
																	<i class="fa fa-times" aria-hidden="true"></i>
																</button>
															</li>
														</ul>
													</div>
													<div class="six-stakes back">
														<div class="row_one betplacestackrowone" id="row_one">
														
															</div>
														<div class="row_one betplacestackrowtwo"  id="row_two">
															
															
														</div>														
													</div>
												</div>
											</li>
										</ul>
									</li>
								</ul>
							</div>
							
	<script>
	   $(document).ready(function(){
	    	getStakes();
	    });
	</script>
