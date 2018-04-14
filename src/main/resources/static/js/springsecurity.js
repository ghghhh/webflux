(function($) {
    // 备份jquery的ajax方法
    var _ajax = $.ajax;

    // 重写jquery的ajax方法
    $.ajax = function(opt) {
        // 备份opt中error和success方法
        var fn = {
            error : function(XMLHttpRequest, textStatus, errorThrown) {
            },
            success : function(data, textStatus) {
            }
        };
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }

        // 扩展增强处理
        var _opt = $.extend(opt, {
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                // 错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success : function(data, textStatus) {
                // 成功回调方法增强处理
            	/*if(!(data instanceof Object)){
            		data=JSON.parse(data);
            	} */          	 
                if(data){
                    if (data.code=="1002") {
                        alert("权限不够");
                    } else if(data.code=="1001"){
                    	alert("登录超时,请登录再试");
                    }else{
                    	fn.success(data, textStatus);
                    }
                }


            }
        });
        _ajax(_opt);
    };
})(jQuery);

var path=""
	  $(function(){
	  	var v=location.pathname
	  	var index = v.substr(1).indexOf("/"); 
	  	path = v.substr(0,index+1);   	
	  })