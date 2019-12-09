<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css">
	<link rel="stylesheet" type="text/css" href="/css/jquery-confirm.min.css"/>
	
</head>
<body>
    <div id="warnInfo">正在识别过程，请耐心等一等....</div>
<script>
    var ua= window.navigator.userAgent.toLowerCase();
    if( (ua.match(/MicroMessenger/i) == 'micromessenger') && (ua.match(/wxwork/i) == 'wxwork') ){
        var redirect_uri= encodeURIComponent('http://i.zuime.com/qywx/check?uuid=${qrcode}');
        window.location='https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww1d0a73183260c1f6&redirect_uri='+redirect_uri+'&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';
    }else if( ua.match(/micromessenger/i) == 'micromessenger' ){
        $("#warnInfo").html("请使用企业微信扫码");
    }
</script>
</body>
</html>