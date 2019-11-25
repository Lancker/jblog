## 项目介绍
<<<<<<< HEAD
项目名为jBlog也就是一个由java语言开发的BLOG系统，使用了SpringBoot框架进行开发。虽然叫做jblog，但是已经不再是一个单纯的blog。目前已经做为各种新功能的演练实验基地。比如当前接入的alipay，则以spring的实践方式，将支付、转帐、扫码支付、余额查询集成进来。

## 可用性
本项目开启了Github CI功能，自动构建！查看效果地址: https://i.zuime.com

## 自动构建托管
当前工程除了使用Github CI检查，也结合github webhook+jenkins实现了自动构建部署。如果您也是一位jblog玩家，希望拥有一个在线实例环境，可以联系，仅提供人品在线的朋友。
![code size](https://img.shields.io/github/languages/code-size/guohai163/jblog.svg?style=flat-square&color=6699FF)
![rv](https://img.shields.io/github/release/guohai163/jblog.svg?style=flat-square&color=CC6666)
![rd](https://img.shields.io/github/release-date/guohai163/jblog.svg?style=flat-square&color=99CCFF)

项目名为jBlog也就是一个由java语言开发的BLOG系统，使用了SpringBoot框架进行开发。


目前已经实现的功能：

1. 前台blog展示，分页只有上一页下一页不提供具体页号。
2. Blog内容取自Mysql数据库，目前无分类。
3. 支持Markdown格式书写BLOG。使用了markdownj-core
4. 支持后台登录，后台用户名密码存储在DB中。
5. 登录后会生成一个UUID，存储于cookies中。UUID对应的服务器缓存在一个静态变量中。重启站点所有用户下线。
6. 评论功能目前使用的第三方。需要翻墙
7. 集成支付宝网站支付、扫码支付、当面付、转账、查询接口

#更新日志
2019.09.18 增加花呗支付demo https://i.zuime.com/order/alipay/payqrhb

## 更新

请查看 [CHANGELOG.md](/CHANGELOG.md)

## 文档

有关项目安装、开发说明请移步 [wiki](https://github.com/guohai163/jblog/wiki/)

## jblog背后的故事

好古老的一个blog数据，始于2004年儿童节，这个blog是从我开始接触编程就一直在保留先后有多个版本。

1. asp 3 + access [第一版始于2004年,那时还在上学，顺便学习了ASP为将来找工作做打算](http://jblog.guohai.org/2004/6/1/blog/)
2. .net2 + slqserver [2006年，第一份工作需要学习.Net 练手把BLOG改版了]
3. php + mysql [2008年左右改了一次，当时因为学习PHP]
4. springmvc + mysql [2014年 .net没落了，准备学习Java，当时最火的框架就是SpringMVC。](http://jblog.guohai.org/2014/6/29/markdown_learm/)
5. spring boot + mysql [2019年，这个BLOG已经停更5年了。这5年blog还在写，只是搬家到github上了。但想了想信年打算改版一次。纪念点什么]


## 许可协议

在项目初期为了保证项目的更新，使用比较严格的GPL许可，希望大家的更改都能开源并同步上来。当项目足够可以商业化后可能会更换成更加宽松的Apache许可。

==
欢迎订阅公众号：海哥聊技术

![http://guohai.org/assets/wechat.jpg](http://guohai.org/assets/wechat.jpg)
<<<<<<< HEAD

欢迎订阅公众号：Yuema约吗 (www_yue_ma)

![http://i.zuime.com/images/yuemaqrcode.jpg](http://i.zuime.com/images/yuemaqrcode.jpg)

