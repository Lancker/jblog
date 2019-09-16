package jblog.guohai.org.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import jblog.guohai.org.model.BlogContent;

public interface AlipayDao {
    /**
     * 增加新的BLOG （not ready）
     *
     * @param blog
     * @return
     */
    @Insert("INSERT INTO `jblog_posts`\n" +
            "(`post_date`,\n" +
            "`post_title`,\n" +
            "`post_content`,\n" +
            "`post_status`,\n" +
            "`post_small_title`)\n" +
            "VALUES\n" +
            "(#{blog.postDate},\n" +
            "#{blog.postTitle},\n" +
            "#{blog.postContent},\n" +
            "'publish',\n" +
            "#{blog.postSmallTitle});\n")
    @Options(useGeneratedKeys = true, keyProperty = "blog.postCode")
    Boolean addPostBlog(@Param("blog") BlogContent blog);
}
