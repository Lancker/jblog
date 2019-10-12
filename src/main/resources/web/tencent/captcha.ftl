
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>腾讯云验证码 demo</title>
	
	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	
	<link rel="stylesheet" type="text/css" href="/css/markdown.css"/>
	<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
</head>
<body>
腾讯云验证码 demo
<button id="TencentCaptcha"
     data-appid="2046804662"
     data-cbfn="callback"
     type="button"
>验证</button>


<#include "/inc/foot.ftl" />
<script>
window.callback = function(res){
 console.log(res)
 // res（用户主动关闭验证码）= {ret: 2, ticket: null}
 // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
 if(res.ret === 0){
     alert(res.ticket);   // 票据
     $.get("/tencent/verify",
     	{
     		ticket:res.ticket,
     		randstr:res.randstr
     	}
     	,function(data){
     	alert(data);
     });
 }
}
</script>

</body>
</html>
