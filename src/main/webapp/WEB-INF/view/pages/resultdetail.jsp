<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s"%> 
 
  <script id="vTeenPattiresultHandler" type="text/x-handlebars-template">
 <div class="player-number">
            <h4 class="text-center">Player A</h4>
            <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
                   <img  src="<s:url value="/images/cards/{{data.C2}}.png"/>" />
                    <img  src="<s:url value="/images/cards/{{data.C3}}.png"/>" />
                </div>
                
                <div class="col-md-12 text-center" id="winnerA">
                                    </div>
            </div>
        </div>
        <div class="player-number">
            <h4 class="text-center">Player B</h4>
            <div class="player-image-container row">
                <div class="col-md-12 text-center">
                    <img  src="<s:url value="/images/cards/{{data.C4}}.png"/>" />
                   <img  src="<s:url value="/images/cards/{{data.C5}}.png"/>" />
                    <img  src="<s:url value="/images/cards/{{data.C6}}.png"/>" />
                </div>
                <div class="col-md-12 text-center"  id="winnerB">
                                        
                                    </div>
                
            </div>
        </div>
  </script>
  
  
  
    <script id="liveTeenPattiresultHandler" type="text/x-handlebars-template">
 <div class="player-number">
            <h4 class="text-center">Player A</h4>
            <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[0]}}.png" />
                 
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[2]}}.png" />
                 
                     <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[4]}}.png" />
                 
                </div>
                
                <div class="col-md-12 text-center" id="winnerA">
                                    </div>
            </div>
        </div>
        <div class="player-number">
            <h4 class="text-center">Player B</h4>
            <div class="player-image-container row">
                <div class="col-md-12 text-center">
                      <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[1]}}.png" />
                 
              <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[3]}}.png" />
                 
                    <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[5]}}.png" />
                 
                </div>
                <div class="col-md-12 text-center"  id="winnerB">
                                        
                                    </div>
                
            </div>
        </div>
  </script>
  
   <script id="dtl20resultHandler" type="text/x-handlebars-template">
 <div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-4 text-center">
					<div>Dragon</div></br>
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
<i class="fas fa-trophy"></i>


                </div>
				
				
				<div class="col-md-4 text-center">
					<div>Tiger</div></br>
                   <img  src="<s:url value="/images/cards/{{data.C2}}.png"/>" />
                 </div>

				
				<div class="col-md-4 text-center">
					<div>Lion</div></br>
                    <img  src="<s:url value="/images/cards/{{data.C3}}.png"/>" />
                </div>
                
                <div class="col-md-12 text-center" id="winnerA">
                                    </div>
            </div>
        </div>
       
  </script>
   
   
      <script id="dt20resultHandler" type="text/x-handlebars-template">
 <div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-6 text-center">
					<div>Dragon</div></br>
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
				 </div>
				
				
				<div class="col-md-6 text-center">
					<div>Tiger</div></br>
                   <img  src="<s:url value="/images/cards/{{data.C2}}.png"/>" />
                 </div>

				
				
                
                <div class="col-md-12 text-center"> 

                         <div><span class="text-success">Result: </span>{{data.winnerDetail}}</div>
					    <div> <span class="text-success">Dragon: </span>{{data.dragonDetail}}</div>
						 <div><span class="text-success">Tiger: </span>{{data.tigerDetail}}</div>

                </div>
            </div>
        </div>
       
  </script>
  
 <script id="livedt20resultHandler" type="text/x-handlebars-template">
 <div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-6 text-center">
					<div>Dragon</div></br>
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[0]}}.png" />
				 </div>
				
				
				<div class="col-md-6 text-center">
					<div>Tiger</div></br>
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[1]}}.png" />
                 </div>

				
				
                
                <div class="col-md-12 text-center"> 

                         <div><span class="text-success result_detail"> </span></div>
					   
                </div>
            </div>
        </div>
       
  </script>
  
    
 <script id="livedtonedayresultHandler" type="text/x-handlebars-template">
 <div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-6 text-center">
					<div>Dragon</div></br>
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[0]}}.png" />
				 </div>
				
				
				<div class="col-md-6 text-center">
					<div>Tiger</div></br>
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[1]}}.png" />
                 </div>

				
				
                
                <div class="col-md-12 text-center"> 

                         <div><span class="text-success result_detail"> </span></div>
					   
                </div>
            </div>
        </div>
       
  </script>
  
   <script id="liveaaaresultHandler" type="text/x-handlebars-template">
 	<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.cards}}.png" />
                 
                </div>
                
                <div class="col-md-12 text-center result_detail" id="detail"><span class="text-success">Result:</span><span>{{data.desc}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
  
  
     <script id="livebwtableresultHandler" type="text/x-handlebars-template">
 	<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.cards}}.png" />
                 
                </div>
                
                <div class="col-md-12 text-center result_detail" id="detail"><span class="text-success">Result:</span><span>{{data.desc}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
  
  
    <script id="vLucky7resultHandler" type="text/x-handlebars-template">
 		<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
                 
                </div>
                
                <div class="col-md-12 text-center" id="detail"><span class="text-success">Result:</span><span>{{data.detail}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
  
   <script id="liveLucky7resultHandler" type="text/x-handlebars-template">
 	<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data}}.png" />
                 
                </div>
                
                <div class="col-md-12 text-center result_detail" id="detail"><span class="text-success">Result:</span><span>{{data.desc}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
  
  <script id="vaaaresultHandler" type="text/x-handlebars-template">
 		<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
                 
                </div>
                
                <div class="col-md-12 text-center" id="detail"><span class="text-success">Result:</span><span>{{data.detail}}</span>
                                    </div>
            </div>
        </div>
       
  </script>
  
  
   <script id="vAndarBaharresultHandler" type="text/x-handlebars-template">
 			 <div class="row player-number col-md-12"><br>
           <div class="col-md-12 text-center">
  				<h4 class="text-center">Andar</h4>
          
           </div>
			<div class="col-md-12 text-center">
  				 
                  {{#each data.[0]}}
							<img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
				  {{/each}}	
          
           </div>	

<div class="col-md-12 text-center">
  				<h4 class="text-center">Bahar</h4>
          
           </div>
			<div class="col-md-12 text-center">
  				 
                  {{#each data.[1]}}
							<img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
				  {{/each}}	
          
           </div>
        </div>

<hr>

 </script>
 
 
 
   <script id="vAndarBahar2resultHandler" type="text/x-handlebars-template">

 <div class="row player-number col-md-12"><br>
          
			<div class="col-md-12 text-center">
  				 
                 
						<div class="form-row w-100 justify-content-center align-items-center">
													<div class="col-2 col-lg-2 col-xl-1"> 
														<div class="title text-center set-height">Ander</div>
														<div class="title text-center set-height">Bahar</div>
													</div>
													<div class="col-3 col-lg-2 col-xl-1"> 
														<div class="card-pic set-height">
																		
														  <img  src="<s:url value="/images/cards/{{data.[2]}}.png"/>" width="35" />
														 </div>
													</div>


													<div class="col-3 col-md-2 col-lg-2 col-xl-2">
														<div class="card-pic set-height">
														{{#each data.[0]}}				
														  <img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
														 {{/each}}	
													    </div>

													<div class="card-pic set-height">
														{{#each data.[1]}}				
														  <img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
														 {{/each}}	
													    </div>
												
													</div>

													


										</div>
                      </div>	

<hr>



 </script>
 
    <script id="vbwableresultHandler" type="text/x-handlebars-template">
 		<div class="player-number">
           <div class="player-image-container row">
                
                <div class="col-md-12 text-center">
                   <img  src="<s:url value="/images/cards/{{data.C1}}.png"/>" />
                 
                </div>
                
                <div class="col-md-12 text-center" id="detail"><span class="text-success">Result:</span><span>
			{{#if_eq data.winner "1"}}  											
				Don
 			{{/if_eq}}

			{{#if_eq data.winner "2"}}  											
			   Amar Akbar Anthony

 			{{/if_eq}}
			
			{{#if_eq data.winner "3"}}  											
				Sahib Bibi Aur Ghulam

 			{{/if_eq}}
			
			{{#if_eq data.winner "4"}}  											
				Dharam Veer
 			{{/if_eq}}

			{{#if_eq data.winner "5"}}  											
				Kis KisKo Praar Karoon
 			{{/if_eq}}

			{{#if_eq data.winner "6"}}  											
				Ghulam
 			{{/if_eq}}
				</span></div>
               <div class="col-md-12 text-center" ><span>{{data.detail}}</span></div>
          
            </div>
        </div>
       
  </script>
  
   <script id="vopenteenpattiresultHandler" type="text/x-handlebars-template">
 		
	 <div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Dealer</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[8]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[17]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[26]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>


 <div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 1</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[0]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[9]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[18]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>

 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 2</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[1]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[10]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[19]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>

 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 3</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[2]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[11]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[20]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>



 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 4</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[3]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[12]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[21]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>
 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 5</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[4]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[13]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[22]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>
 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 6</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[5]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[14]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[23]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>
 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 7</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[6]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[15]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[24]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>
 <div class="row player-number col-md-12">
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player 8</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="<s:url value="/images/cards/{{data.[7]}}.png"/>" width="35" />
                   <img  src="<s:url value="/images/cards/{{data.[16]}}.png"/>"  width="35" />
                    <img  src="<s:url value="/images/cards/{{data.[25]}}.png"/>"  width="35" />
          
           </div>	
        </div><hr>

       
       
  </script>
  
   
   
      <script id="vAndarBahar2resultHandler" type="text/x-handlebars-template">
 			 <div class="row player-number col-md-12"><br>
          
			<div class="col-md-12 text-center">
  				 
                 
						<div class="form-row w-100 justify-content-center align-items-center">
													<div class="col-2 col-lg-2 col-xl-1"> 
														<div class="title text-center set-height">Ander</div>
														<div class="title text-center set-height">Bahar</div>
													</div>
													<div class="col-3 col-lg-2 col-xl-1"> 
														<div class="card-pic set-height">
															<img src="../../images/cards/{{ data.[0]}}.png" width="50" id="" class="card-opening img-fluid mb-10">
														</div>
													</div>


													<div class="col-3 col-md-2 col-lg-2 col-xl-2">
														<div class="card-pic set-height">
														{{#arrayLength  data.[1] 1}}				
														  	<img src="../../images/cards/{{removeSpace data.[1].[1]}}.png"  width="50" class="card-opening img-fluid mb-10">
														</div>
														{{/arrayLength}}
													{{#arrayLength  data.[1] 0}}	
														<div class="card-pic set-height">
															<img src="../../images/cards/{{removeSpace data.[1].[0]}}.png"  width="50" class="card-opening img-fluid mb-10">
														</div>
													{{/arrayLength}}
													</div>

													<div class="col-3 col-md-2 col-lg-2 col-xl-2">

													{{#arrayLength  data.[1] 2}}	
														<div class="card-pic set-height">
														  	<img src="../../images/cards/{{removeSpace data.[1].[3]}}.png" style="" id="cars_image_andar_sbet" width="50" class="card-opening img-fluid mb-10">
														</div>
													{{/arrayLength}}
													{{#arrayLength  data.[1] 3}}	
														<div class="card-pic set-height">
															<img src="../../images/cards/{{removeSpace data.[1].[2]}}.png" style="" id="cars_image_bahar_sbet" width="50" class="card-opening img-fluid mb-10">
														</div>
													{{/arrayLength}}
													</div>


																						</div>
           </div>	



 </script>
      <script id="livepoker20resultHandler" type="text/x-handlebars-template">
 		


<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player A</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[0]}}.png"  width="35"  />
                	 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[1]}}.png"  width="35"  />
          
          
           </div>	
        </div>
<hr>

<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player B</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[2]}}.png"  width="35"  />
                	 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[3]}}.png"  width="35"  />
          
          
           </div>	
        </div>
<hr>

<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Dealer</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[4]}}.png"  width="35"  />
                 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[5]}}.png"  width="35"  />
				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[6]}}.png"  width="35"  />
				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[7]}}.png"  width="35"  />
          		 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[8]}}.png"  width="35"  />
          
           </div>	
        </div>

 <div class="col-md-12 text-center"> 

                         <div><span class="text-success result_detail"> </span></div>
					   
                </div>
<hr>
       
  </script>
  
  
        <script id="livepokeronedayresultHandler" type="text/x-handlebars-template">
 		


<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player A</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[0]}}.png"  width="35"  />
                	 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[1]}}.png"  width="35"  />
          
          
           </div>	
        </div>
<hr>

<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Player B</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[2]}}.png"  width="35"  />
                	 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[3]}}.png"  width="35"  />
          
          
           </div>	
        </div>
<hr>

<div class="row player-number col-md-12"><br>
           <div class="col-md-3 text-center">
  				<h4 class="text-left">Dealer</h4>
          
           </div>
			<div class="col-md-9 text-right">
  				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[4]}}.png"  width="35"  />
                 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[5]}}.png"  width="35"  />
				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[6]}}.png"  width="35"  />
				 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[7]}}.png"  width="35"  />
          		 <img  src="https://sitethemedata.com/v55/static/front/img/cards/{{data.[8]}}.png"  width="35"  />
          
           </div>	
        </div>

 <div class="col-md-12 text-center"> 

                         <div><span class="text-success result_detail"> </span></div>
					   
                </div>
<hr>
       
  </script>


   <script id="liveAndarBaharresultHandler" type="text/x-handlebars-template">
 			 <div class="row player-number col-md-12"><br>
           <div class="col-md-12 text-center">
  				<h4 class="text-center">Andar</h4>
          
           </div>
			<div class="col-md-12 text-center">
  				 
                  {{#each data.[0]}}
							<img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
				  {{/each}}	
          
           </div>	

<div class="col-md-12 text-center">
  				<h4 class="text-center">Bahar</h4>
          
           </div>
			<div class="col-md-12 text-center">
  				 
                  {{#each data.[1]}}
							<img  src="<s:url value="/images/cards/{{this}}.png"/>" width="35" />
				  {{/each}}	
          
           </div>
        </div>

<hr>

 </script>
   
   <div id="modalresult" class="modal modal-pop" tabindex="-1" style="padding-right: 17px;">
<div class="modal-dialog" style="min-width: 650px">
<div class="modal-content">

<div class="modal-header">
<h4 class="modal-title">Result Details</h4>
<button type="button" class="close" data-dismiss="modal">×</button>
</div>

<div class="modal-body nopading" id="result-details" style="min-height: 300px;display:block;" ><style type="text/css">
    .board-result-inner{
        background: #f9f9f9;
        padding: 10px;
        font-size: 17px;
    }
    .yellowbx{
        color: #caca03;
    }
    .player-image-container img{
        max-width: 45px;
    }
    .c-title{
        font-weight: bold;
        font-size: 24px;
        margin-bottom: 6px;
        color: #222;
        text-align: center;
        margin-top: 15px;
    }
    .eightplayer img
    {
        width: 35px;
        margin-right: 0px;
    }
    .result-row
    {
        position: relative;
        margin-bottom: 50px;
    }
    .result-row .winner-label 
    {
        font-size: 16px;
        position: absolute;
        margin-top: 10px;
        left: 50%;
        transform: translateX(-50%);
    }
    .v-t
    {
        vertical-align: top;
    }
    .winner-icon i {
        font-size: 26px;
        box-shadow: 0 0 0 var(--secondary-bg);
        -webkit-animation: winnerbox 2s infinite;
        animation: winnerbox 1.5s infinite;
        border-radius: 50%;
        color: #169733;
    }

    @-webkit-keyframes winnerbox {
        0% {
            -webkit-box-shadow: 0 0 0 0 rgba(29, 127, 30, .6)
        }

        70% {
            -webkit-box-shadow: 0 0 0 10px rgba(29, 127, 30, 0)
        }

        100% {
            -webkit-box-shadow: 0 0 0 0 rgba(29, 127, 30, 0)
        }
    }
</style>
<div class="container-fluid">
    <div class="row m-t-10">
        <div class="col-md-12">
            <span class="float-right round-id"></span>
        </div>
    </div>
    
    
       <div class="row player-container m-t-10">
       
    </div> 
        
   
</div>

</div>
</div>
</div>
</div>
   
   
  
  <!-- withdraw end -->
  
  <script>

 function resultDeatil(marketId){ 
     $('#modalresult').modal('show');
      $.ajax({
			type:'GET',
			url:'<s:url value="/api/resultDetailVteeePatti/"/>'+marketId,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200){ 		
					//console.log(jsondata)
					var data =JSON.parse(jsondata);
						
					if(${eventid} ==5032){

						  $(".round-id").html("<b>Round ID:</b>"+data.mid)
						  var source   = $("#dtl20resultHandler").html();
				    	  var template = Handlebars.compile(source);

						  $(".player-container").html( template({data:data}) );
						}else if(${eventid} ==5031){

							  $(".round-id").html("<b>Round ID:</b>"+data.mid)
							  var source   = $("#dt20resultHandler").html();
					    	  var template = Handlebars.compile(source);
							  $(".player-container").html( template({data:data}) );
							  
						}else if(${eventid} ==5011){

								  $(".round-id").html("<b>Round ID:</b>"+data.mid)
								  var source   = $("#vLucky7resultHandler").html();
						    	  var template = Handlebars.compile(source);
								  $(".player-container").html( template({data:data}) );
								  
						}else if(${eventid} ==5031){

						    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
							     var source   = $("#resultHandler").html();
					    	     var template = Handlebars.compile(source);
							     $(".player-container").html( template({data:data}) );
							     
						}else if(${eventid} ==5021){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vaaaresultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:data}) );
						     
					    }
						else if(${eventid} ==5022){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vbwableresultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:data}) );
						     
					    }
					
					
						else if(${eventid} ==5042){
							 var opnteenpattitemp = new Array();
							 opnteenpattitemp = data.C1.split(",");
							
					    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vopenteenpattiresultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:opnteenpattitemp}) );
						     $(".player-container").removeClass("row")
					    }

						else if(${eventid} == 5041){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vTeenPattiresultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:data}) );
						    
						     
					    }else if(${eventid} == 5051){
					    	   var jdata = [];
					    	 
					    	 var aalltemp = new Array();
							 aalltemp = data.aall.split(",");

							 var aalltemp = new Array();
							 balltemp = data.ball.split(",");
					 
							 
					         jdata.push(data.aall.split(","));
					         jdata.push(data.ball.split(","));
     
					    	 $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vAndarBaharresultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:jdata}) );
						   
						    
						     
					    }
					    else if(${eventid} == 5052){

					    	 var jdata = [];
					    	 var aalltemp = new Array();
					    	 var balltemp = new Array();

					    	 
					    	 var temparray = new Array();
					    	 temparray = data.C2.split(",");
  
							 for(var i=0;i<temparray.length;i++){

					        	 if ( i % 2 == 0) {
					        		 aalltemp.push(temparray[i].toString().trim());
					        		}else{
					        		 balltemp.push(temparray[i].toString().trim());
					        		}
					        	
						         }
							 
							 jdata.push(balltemp);
					         jdata.push(aalltemp);
					         jdata.push(data.C1);
						    
					         $(".round-id").html("<b>Round ID:</b>"+data.mid)
						     var source   = $("#vAndarBahar2resultHandler").html();
				    	     var template = Handlebars.compile(source);
						     $(".player-container").html( template({data:jdata}) );
					    }
					
				}
		},
 		complete: function(jsondata,textStatus,xhr) {
			 $(".loader").fadeOut("slow");
	    } 
	});
  }
      



 function resultDetailLiveCasino(marketId){ 
     $('#modalresult').modal('show');
 	$(".cliennt-container").append('<div class="loader"></div>')	
		
      $.ajax({
			type:'GET',
			url:'<s:url value="/api/resultDetailLiveCasino/"/>'+marketId,
			success: function(jsondata, textStatus, xhr) {
				if(xhr.status == 200){ 		

						var data =jsondata;
						
				 if(${eventid} == 5047 || ${eventid} == 5048){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.rid)
						     var source   = $("#liveTeenPattiresultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	     var aalltemp = new Array();
				   		      aalltemp = data.card.split(",");
				   		 
						     $(".player-container").html( template({data:aalltemp}) );
						 } if(${eventid} == 5036){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.rid)
						     var source   = $("#livedt20resultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	     aalltemp = data.card.split(",");
				    	    console.log(aalltemp)
				   		   $(".player-container").html( template({data:aalltemp}) );
						     $(".result_detail").html("<br>Result: <span style='color:black'>"+data.rdesc+"</span>")
						 }
						 if(${eventid} == 5017){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.rid)
						     var source   = $("#liveLucky7resultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				   		   //   aalltemp = data.data[0]['cards'].split(",");
				   		alert(data.card)
						     $(".player-container").html( template({data:data.card}) );
						
						 } if(${eventid} == 5037){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						     var source   = $("#livedtonedayresultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				   		      aalltemp = data.data[0]['cards'].split(",");
				   		
				   		   $(".player-container").html( template({data:aalltemp}) );
				   		 $(".result_detail").html("<br>Result: <span style='color:black'>"+data.data[0]['desc']+"</span>")
						
						 }if(${eventid} == 5026){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						     var source   = $("#liveaaaresultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				   		      aalltemp = data.data[0]['cards'].split(",");
				   		
				   		   $(".player-container").html( template({data:data.data[0]}) );
				   		   $(".result_detail").html("<br>Result: <span style='color:black'>"+data.data[0]['desc']+"</span>")
						
						 }if(${eventid} == 5027){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						     var source   = $("#livebwtableresultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				   		      aalltemp = data.data[0]['cards'].split(",");
				   		
				   		   $(".player-container").html( template({data:data.data[0]}) );
				   		  
				   		   $(".result_detail").html("<br>Result: <span style='color:black'>"+data.data[0]['desc']+"</span>")
						
						 }

						 if(${eventid} == 5076){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						     var source   = $("#livepoker20resultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				    	      var detail = new Array();
				   		      aalltemp = data.data[0]['cards'].split(",");
				   		      detail = data.data[0]['desc'].split("##");
				   		    
				   		   $(".player-container").html( template({data:aalltemp}) );
				   		   $(".result_detail").html("<br>Winner: <span style='color:black'>"+detail[0]+"</span><br>Player A: <span style='color:black'>"+detail[1]+"</span><br>Player B: <span style='color:black'>"+detail[0]+"</span>")
							
				   		   $(".player-container").removeClass("row")
				   		 
						 }
						 if(${eventid} == 5077){

					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						     var source   = $("#livepokeronedayresultHandler").html();
				    	     var template = Handlebars.compile(source);
				    	   
				    	      var aalltemp = new Array();
				    	      var detail = new Array();
				   		      aalltemp = data.data[0]['cards'].split(",");
				   		      detail = data.data[0]['desc'].split("##");
				   		     
				   		   $(".player-container").html( template({data:aalltemp}) );
				   		   $(".result_detail").html("<br>Winner: <span style='color:black'>"+detail[0]+"</span><br>Player A: <span style='color:black'>"+detail[1]+"</span><br>Player B: <span style='color:black'>"+detail[0]+"</span>")
							
				   		   $(".player-container").removeClass("row")
				   		 
						 }
						 if(${eventid} == 5056){

							 var jdata = [];
								
					    	 $(".round-id").html("<b>Round ID:</b>"+data.data[0]['mid'])
						    
				    	      var array = new Array();
				    	      var detail = new Array();
				   		      aalltemp = data.data[0]['cards'].split("*,");
				   		    

				   		      jdata.push(aalltemp[0].replace(/,\s*$/, "").split(","));
					          jdata.push(aalltemp[1].replace(/^\w+,/, '').split(","));
					         
					          $(".round-id").html("<b>Round ID:</b>"+data.mid)
							  var source   = $("#liveAndarBaharresultHandler").html();
					    	  var template = Handlebars.compile(source);
							  $(".player-container").html( template({data:jdata}) );
					          
						 }
					
						
				}
				 
		},
 		complete: function(jsondata,textStatus,xhr) {
			 $(".loader").fadeOut("slow");
	    } 
	});
  }
    
 
  </script>
  
  <style>
  
  .modal-open .modal {
    overflow-x: hidden;
    overflow-y: auto;
}

.modal {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    display: none;
    overflow: hidden;
    outline: 0;
}
  
  .modal-header .close {
    opacity: 1;
    border-radius: 50%;
    padding: 0px;
    height: 35px;
    width: 35px;
    margin-top: -2px;
        background-color: black;
    color: white;
}

.container-fluid {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
}
.player-number {
     padding-right: 20px;
    flex: 1;
}


.winner-label {
    font-size: 22px;
    color: #fff;
    box-shadow: 0px 0px 5px 2px rgba(0,0,0,0.5);
    padding: 10px;
}

.m-t-20 {
    margin-top: 20px !important;
}
  </style>