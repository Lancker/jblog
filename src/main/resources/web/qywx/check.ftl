<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width" />
    <title>${blog_name}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

</head>
<body class="home blog group-blog">
<div id="main">

    <div id="content">
        <div>
            <article role="article">

                               <#if !success>
                                        <h1>${message}</h1>

                                </#if>
                        <#if success>
                                 <h1>${message}</h1>

                                <h1 class="entry-title">
                                    <a href="/${blog.postYear?c}//${blog.postDay}/${blog.postSmallTitle}/">${blog.postTitle}</a>
                                </h1>

                                <p></p>
                                <h1> ${userPos.name} - ${dept}</h1>
                                <img src="${userPos.avatar}"/>

                           </#if>
                        <footer>
                        </footer>
            </article>

        </div>

</div>



<footer class="blog-footer" role="contentinfo"><p>
    © 2019 power by Jblog


</p>

</footer>
</body>
</html>
