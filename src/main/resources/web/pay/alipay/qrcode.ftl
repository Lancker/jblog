<html>
<head>
<script src="/jquery/jquery-3.2.1.min.js"></script>
<script src="/js/qrcode.min.js"></script>
</head>
<body>
<div id="qrcode"></div>

<div>${tradeNo}</div>

<div><a href="/order/alipay/pay/query?tradeNo=${tradeNo}" target="_blank">查询结果</a></div>

</body>
<script type="text/javascript">
new QRCode(document.getElementById("qrcode"), "${qrcode}");
</script>

</html>