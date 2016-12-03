

///////////////////////////////
//		调用原生接口			//
///////////////////////////////
;(function($) {
	"use strict"//使用严格模式
    function native(params) {
    	params = params||{};
    	if (params==="undefind")return;
    	if (params.action==="undefind")return;
    	//固定的三个属性和native端一样，否则native端和解析出错
    	var Senddata={
    		action:params.action,
    		callback:"nativeCallback",
    		data:params.data,
    	}
        window.nativeCallback = function(data) {
        	if (params.callback!=="undefind") {
        		params.callback(data);
        	}
        }
        var sendDataStr=JSON.stringify(Senddata);
        window.native.sendMessage(sendDataStr);
    }
    $.native = native;
})($);
