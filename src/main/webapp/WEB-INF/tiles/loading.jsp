<%@page contentType="text/html;charset=UTF-8"%>

<style type='text/css'> width: 100%;
@-ms-keyframes uil-ripple{0%,100%{opacity:0}0%{width:0;height:0;margin:0}33%{width:44%;height:44%;margin:-22% 0 0 -22%;opacity:1}100%{width:88%;height:88%;margin:-44% 0 0 -44%}}@-moz-keyframes uil-ripple{0%{width:0;height:0;opacity:0;margin:0}33%{width:44%;height:44%;margin:-22% 0 0 -22%;opacity:1}100%{width:88%;height:88%;margin:-44% 0 0 -44%;opacity:0}}@-webkit-keyframes uil-ripple{0%{width:0;height:0;opacity:0;margin:0}33%{width:44%;height:44%;margin:-22% 0 0 -22%;opacity:1}100%{width:88%;height:88%;margin:-44% 0 0 -44%;opacity:0}}@-o-keyframes uil-ripple{0%{width:0;height:0;opacity:0;margin:0}33%{width:44%;height:44%;margin:-22% 0 0 -22%;opacity:1}100%{width:88%;height:88%;margin:-44% 0 0 -44%;opacity:0}}@keyframes uil-ripple{0%{width:0;height:0;opacity:0;margin:0}33%{width:44%;height:44%;margin:-22% 0 0 -22%;opacity:1}100%{width:88%;height:88%;margin:-44% 0 0 -44%;opacity:0}}.uil-ripple-css{background:0 0;position:relative;width:200px;height:200px}.uil-ripple-css div{position:absolute;top:50%;left:50%;margin:0;width:0;height:0;opacity:0;border-radius:50%;border-width:14px;border-style:solid;-ms-animation:uil-ripple 2s ease-out infinite;-moz-animation:uil-ripple 2s ease-out infinite;-webkit-animation:uil-ripple 2s ease-out infinite;-o-animation:uil-ripple 2s ease-out infinite;animation:uil-ripple 2s ease-out infinite}.uil-ripple-css div:nth-of-type(1){border-color:#f4f4fb}.uil-ripple-css div:nth-of-type(2){border-color:#05f97e;-ms-animation-delay:1s;-moz-animation-delay:1s;-webkit-animation-delay:1s;-o-animation-delay:1s;animation-delay:1s}.cssload-loader{width:281px;height:56px;line-height:56px;text-align:center;position:absolute;left:50%;transform:translate(-50%,-50%);-o-transform:translate(-50%,-50%);-ms-transform:translate(-50%,-50%);-webkit-transform:translate(-50%,-50%);-moz-transform:translate(-50%,-50%);font-family:helvetica,arial,sans-serif;text-transform:uppercase;font-weight:900;font-size:20px;color:#e67e22;letter-spacing:.2em}.cssload-loader::after,.cssload-loader::before{content:"";display:block;width:17px;height:17px;background:#07e67a;position:absolute;animation:cssload-load .81s infinite alternate ease-in-out;-o-animation:cssload-load .81s infinite alternate ease-in-out;-ms-animation:cssload-load .81s infinite alternate ease-in-out;-webkit-animation:cssload-load .81s infinite alternate ease-in-out;-moz-animation:cssload-load .81s infinite alternate ease-in-out}.cssload-loader::before{top:0}.cssload-loader::after{bottom:0}@keyframes cssload-load{0%{left:0;height:34px;width:17px}50%{height:9px;width:45px}100%{left:264px;height:34px;width:17px}}@-o-keyframes cssload-load{0%{left:0;height:34px;width:17px}50%{height:9px;width:45px}100%{left:264px;height:34px;width:17px}}@-ms-keyframes cssload-load{0%,100%{height:34px;width:17px}0%{left:0}50%{height:9px;width:45px}100%{left:264px}}@-webkit-keyframes cssload-load{0%{left:0;height:34px;width:17px}50%{height:9px;width:45px}100%{left:264px;height:34px;width:17px}}@-moz-keyframes cssload-load{0%{left:0;height:34px;width:17px}50%{height:9px;width:45px}100%{left:264px;height:34px;width:17px}}.loader-container{position:absolute;width:100%;height:100vh;z-index:100000000}.loader-position{margin:20% auto 0;width:120px}.loader-style{text-align:center;position:fixed}
 </style>
 
<div class="modal-window fade_in" id="ajaxLoaderContainer" style="display:none;">
	<div class="loader-position" onclick="hideLoader()">
	<div class="loader-style">
	<img src="https://loading.io/spinners/palette-ring/index.svg" class="zoom1">
		</div>
	</div>
</div>