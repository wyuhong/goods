//希望传入的e是一个jquery对象
function showHideError(e){
	var txt = e.text();
	//当里面没有内容，则隐藏
	if(!txt){
		e.css("display","none");
	}else{
		//当label有内容则显示
		e.css("display","");
	}
}

//通过文本框控件推算对应的label,设置进文本
function showHideErrorByLabelId(ele,txt){
	var labelId = ele.attr("id")+"Error";
	$("#"+labelId).text(txt);
	showHideError($("#"+labelId));
}

//调用校验方法
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase() + inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");	
}

//这个函数是专门用来校验登录名文本框
function validateLoginname(){
	var value = $.trim($("#loginname").val());
	if(!value){
		//什么都没填的时候
		showHideErrorByLabelId($("#loginname"),"登录名不能为空");
		return false;
	}else if(value.length<6 || value.length>20){
		//提示用户登录名的长度必须在6~20之间
		showHideErrorByLabelId($("#loginname"),"登录名的长度必须在6~20之间");
		return false;
	}else{
		return true;
	}
}

//这个函数是专门用来校验密码文本框
function validateLoginpass(){
	var value = $.trim($("#loginpass").val());
	if(!value){
		//什么都没填的时候
		showHideErrorByLabelId($("#loginpass"),"密码不能为空");
		return false;
	}else if(value.length<6 || value.length>20){
		//提示密码的长度必须在6~20之间
		showHideErrorByLabelId($("#loginpass"),"密码的长度必须在6~20之间");
		return false;
	}else{
		return true;
	}
}

var bl = false;
//这个函数是专门用来校验验证码文本框
function validateVerifyCode(){
	var value = $.trim($("#verifyCode").val());
	if(!value){
		//什么都没填的时候
		showHideErrorByLabelId($("#verifyCode"),"验证码不能为空");
		return false;
	}else if(value.length != 4){
		//提示验证码的长度必须是4位
		showHideErrorByLabelId($("#verifyCode"),"验证码的长度必须4位");
		return false;
	}else{
		//符合不为空条件，并且验证码的长度必须是4位，那么进行Ajax校验
		$.post(
				"/goods/userAction?flag=validateVerifyCode",
				{"verifyCode":value},
				function(data){
					if(data.status == "nopass"){
						//验证码校验没通过
						showHideErrorByLabelId($("#verifyCode"),"验证码错误");
						bl = false;
					}else{
						//验证码校验已经通过
						showHideErrorByLabelId($("#verifyCode"),"验证码正确");
						bl = true;
					}
				},
				"json"
		);
		return bl;
	}
}



$(function() {
	
	//换一张功能
	$("#repImg").click(function() {
		$("#vCode").attr("src", "/goods/vcode?a=" + new Date().getTime());
	});

	//光标划过登录按钮切换图片事件
	$("#submit").hover(function() {
		$("#submit").attr("src", "/goods/images/login2.jpg");
	}, function() {
		$("#submit").attr("src", "/goods/images/login1.jpg");
	});
	
	//所有文本框获得焦点的事件
	$(".input").focus(function() {
		showHideErrorByLabelId($(this),"");
	});
	
	//所有文本框失去焦点的事件
	$(".input").blur(function() {
		var iid = $(this).attr("id");
		invokeValidateFunction(iid);
	});

	//校验所有文本框，全部填入后可以提交
	$("#loginForm").submit(function(){
		var bool = true;
		$(".input").each(function() {
			var iid = $(this).attr("id");
			bool = bool + "&&" + invokeValidateFunction(iid);
		})
		return eval(bool);
	});
	

});