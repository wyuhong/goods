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

//调用校验函数
function invokeValidateFunction(iid){
	iid = iid.substring(0,1).toUpperCase() + iid.substring(1);
	var fname = "validate" + iid;
	//1 调用validateLoginname
	//2 返回一个布尔值
	return eval(fname + "()");
}


var bl = false;
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
		//符合不为空条件，并且符合长度在6~20之间，那么进行Ajax校验
		$.post(
				"/goods/userAction?flag=existLoginname",
				{"loginname":value},
				function(data){
					if(data.status == "exist"){
						//数据库中已经存在该登录名
						showHideErrorByLabelId($("#loginname"),"登录名已被占用");
						$("#"+$("#loginname").attr("id")+"Error").removeClass("successClass");
						bl = false;
					}else{
						//数据库中已经不存在该登录名
						showHideErrorByLabelId($("#loginname"),"登录名可以注册");
						$("#"+$("#loginname").attr("id")+"Error").addClass("successClass");
						bl = true;
					}
				},
				"json"
		);
		return bl;
	}
	
	
}

//这个函数是专门用来校验密码文本框
function validateLoginpass(){
	var value = $.trim($("#loginpass").val());
	if(!value){
		//什么都没填的时候
		showHideErrorByLabelId($("#loginpass"),"密码不能为空");
		bl = false;
	}else if(value.length<6 || value.length>20){
		//提示密码的长度必须在6~20之间
		showHideErrorByLabelId($("#loginpass"),"密码的长度必须在6~20之间");
		bl = false;
	}else{
		bl = true;
	}
	return bl;
}

//这个函数是专门用来校验确认密码文本框
function validateReloginpass() {
	var value = $.trim($("#reloginpass").val());
	if(!value) {
		//什么都没填的时候
		showHideErrorByLabelId($("#reloginpass"),"确认密码不能为空");
		bl = false;
	}else if(value != $.trim($("#loginpass").val())) {
		//两次输入是否一致
		showHideErrorByLabelId($("#reloginpass"),"两次密码输入不一致");
		bl = false;
	}else{
		bl = true;
	}
	return bl;	
}

//这个函数是专门用来校验邮箱文本框email
function validateEmail(){
	
	var value = $.trim($("#email").val());
	if(!value){
		//什么都没填的时候
		showHideErrorByLabelId($("#email"),"登录名不能为空");
		return false;
	}else if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
		//提示Email必须符合邮箱的正则表达式
		showHideErrorByLabelId($("#email"),"Email格式不正确");
		return false;
	}else{
		//符合不为空条件，并且符合邮箱校验规则，那么进行Ajax校验
		$.post(
				"/goods/userAction?flag=existEmail",
				{"email":value},
				function(data){
					if(data.status == "exist"){
						//数据库中已经存在该邮箱
						showHideErrorByLabelId($("#email"),"Email已被占用");
						$("#"+$("#email").attr("id")+"Error").removeClass("successClass");
						bl = false;
					}else{
						//数据库中已经不存在该邮箱
						showHideErrorByLabelId($("#email"),"Email可以注册");
						$("#"+$("#email").attr("id")+"Error").addClass("successClass");
						bl = true;
					}
				},
				"json"
		);
		return bl;
	}
}

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
						$("#"+$("#verifyCode").attr("id")+"Error").removeClass("successClass");
						bl = false;
					}else{
						//验证码校验已经通过
						showHideErrorByLabelId($("#verifyCode"),"验证码正确");
						$("#"+$("#verifyCode").attr("id")+"Error").addClass("successClass");
						bl = true;
					}
				},
				"json"
		);
		return bl;
	}
}


$(function(){
	//换一张功能
	$("#repImg").click(function(){
		$("#vCode").attr("src","/goods/vcode?a="+new Date().getTime());
	});
	//当光标划过按钮的时候，替换相应图片
	$("#btnSubmit").hover(
			//当光标放入执行
			function(){
				$(this).attr("src","/goods/images/regist2.jpg");
			},
			//当光标离开执行
			function(){
				$(this).attr("src","/goods/images/regist1.jpg");
			}
	);
	
	//先判断label标签中是否有内容，若有，则显示，若没有，则隐藏
	$(".errorClass").each(function(){
		showHideError($(this));
	});
	
	//登录名文本框的校验
	//注册页面所有文本框获得焦点的操作
	$(".input").focus(function(){
		showHideErrorByLabelId($(this),"");
	});
	
	//注册页面所有文本框失去焦点的操作
	$(".input").blur(function(){
		//通过当前控件的jquery对象获得ID
		var iid = $(this).attr("id");
		//调用校验函数
		invokeValidateFunction(iid);
	});
	
	$("#regform").submit(function(){
		var bool = true;
		$(".input").each(function() {
			var iid = $(this).attr("id");
			bool = bool + "&&" + invokeValidateFunction(iid);
		})
		return eval(bool);
	});
	
	
});