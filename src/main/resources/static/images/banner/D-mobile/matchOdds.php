  <div class="header p-2">
         <span >Place Bet</span>
         <span  class="btn-back-close float-right " > <i class="fa fa-times text-white" ></i></span>
       </div>
        <div class="box-content mb-2">
         <div class="city-name p-2">
           <span class=" text-white">Melbourne City</span>
           <form class="float-right">
            <div class="value-button" id="decrease-back" onclick="decreaseValueback()" value="Decrease Value">-</div>
            <input type="number" id="number-back" value="0" />
            <div class="value-button" id="increase-back" onclick="increaseValueback()" value="Increase Value">+</div>
          </form>
          </div>
        </div>
        <div class="input-submit mb-2">
         
          <input type="num" id="left-box" value="1" class="start">
          <input type="button" id="middle-box" value="Submit" class="submit-value">
           <input type="num" id="right-box" value="2" class="net">
          
        </div>
        <div class="table">
          <thead>
            <tr>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </thead>
        
        <div class="tbody">
            <tr>
	            <td> <button type="button" id="laybutton1" class="btn btn-secondary stackbutton"></button></td>
	            <td> <button type="button" id="laybutton2" class="btn btn-secondary stackbutton"></button></td>
	            <td> <button type="button" id="laybutton3" class="btn btn-secondary stackbutton"></button></td>
           </tr>            
           <tr>      
	           <td><button type="button" id="laybutton4" class="btn btn-secondary stackbutton"></button></td>
	           <td> <button type="button" id="laybutton5" class="btn btn-secondary stackbutton"></button></td>
	           <td> <button type="button" id="laybutton6" class="btn btn-secondary stackbutton"></button></td>
           </tr>
           
           <tr>
	           <td><button type="button"  id="laybutton7" class="btn btn-secondary stackbutton"></button></td>
	           <td> <button type="button" id="laybutton8" class="btn btn-secondary stackbutton"></button></td>
	           <td> <button type="button" id="laybutton9" class="btn btn-secondary stackbutton"></button></td>
	           <td> <button type="button" id="laybutton10" class="btn btn-secondary stackbutton"></button></td>
	           
           </tr>
         </div>
      
          <div class="bottom-box mt-2">
            <div class="team-name-left text-white" id="left-box-bottom">Newcastle Jets</div>
            <div class="team-record-left text-white" id="middle-box-bottom">0</div>
            <div class="team-right-left text-danger" id="right-box-bottom">0.00</div>
          </div>
          <div class="bottom-box mt-2">
            <div class="team-name-left text-white" id="left-box-bottom">Melbourne City</div>
            <div class="team-record-left text-white" id="middle-box-bottom">0</div>
            <div class="team-right-left text-danger" id="right-box-bottom">0.00</div>
          </div>
         
          <div class="bottom-box mt-2">
            <div class="team-name-left text-white" id="left-box-bottom"> The Draw </div>
            <div class="team-record-left text-white" id="middle-box-bottom">0</div>
            <div class="team-right-left text-danger" id="right-box-bottom">0.00</div>
          </div>
        </div>
      
      