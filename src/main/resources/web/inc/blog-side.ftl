<aside class="sidebar" style="margin:0px;position:fixed;right:50px;z-index: 1;">
    <header class="site-header">
        <div class="pug" style="text-align: center;">
            <a href="/">
                <img src="${blog_qrcode}" alt=""/>
            </a>
            <h1 class="site-title"><a href="/">${blog_name}</a></h1>
            <p class="site-intro">
                A blog by <a href="/about/">${blog_author}</a>.
            </p>
            <p class="site-intro">
                <svg class="icon-twitter" id="icon-twitter" viewBox="0 0 1024 1024">
                    <a xlink:href="http://twitter.com/${blog_twitter}">
                        <title>twitter</title>
                        <path class="path1"
                              d="M886.579 319.795c0.41 8.294 0.563 16.691 0.563 24.986 0 255.488-194.406 549.99-549.888 549.99-109.21 0-210.739-32-296.294-86.886 15.155 1.792 30.515 2.714 46.080 2.714 90.624 0 173.926-30.925 240.026-82.688-84.531-1.587-155.955-57.395-180.531-134.195 11.776 2.202 23.91 3.379 36.352 3.379 17.664 0 34.765-2.304 50.944-6.707-88.422-17.818-155.034-95.898-155.034-189.594 0-0.819 0-1.587 0-2.406 26.061 14.49 55.91 23.194 87.552 24.218-51.866-34.714-86.016-93.798-86.016-160.922 0-35.379 9.523-68.608 26.214-97.178 95.283 116.992 237.773 193.894 398.387 201.984-3.277-14.182-4.966-28.877-4.966-44.083 0-106.701 86.477-193.178 193.229-193.178 55.603 0 105.83 23.398 141.107 60.979 43.981-8.704 85.35-24.781 122.726-46.899-14.438 45.107-45.107 82.995-84.992 106.906 39.117-4.71 76.288-15.002 111.002-30.413-25.907 38.81-58.675 72.806-96.461 99.994z"></path>
                    </a>
                </svg>
            </p>
            <ul style="text-align:left;margin: 30px;font-size: 14px;line-height: 3px;">
                <li style="font-size:17px">博客分类：</li>
                <li>
                    <ul>
                    <#list blog_classes as classItem>
                        <li><a href="/page/class/${classItem.classCode}/1">${classItem.className}(${classItem.blogCount})</a></li>
                    </#list>
                    </ul>
                </li>
            </ul>
        </div>

    </header>
</aside>