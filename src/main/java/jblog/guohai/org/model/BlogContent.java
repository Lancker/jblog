package jblog.guohai.org.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * BLOG实体
 */
public class BlogContent {

    /**
     * 编号
     */
    @Getter
    @Setter
    private int postCode;

    /**
     * 中文标题
     */
    @Getter
    @Setter
    private String postTitle;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String postContent;

    /**
     * 年
     */
    @Getter
    @Setter
    private int postYear;


    /**
     * int 月
     */
    @Getter
    @Setter
    private int postMonth;

    @Getter
    @Setter
    private int postDay;

    /**
     * 完整日期
     */
    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date postDate;

    /**
     * 短标题，URL使用
     */
    @Getter
    @Setter
    private String postSmallTitle;

    @Getter
    @Setter
    private String postStatus;

    /**
     * 分类编号
     */
    @Getter
    @Setter
    private Integer classCode;

    /**
     * 构造方法
     *
     * @param postTitle      长标题
     * @param postContent    内容
     * @param postDate       日期
     * @param postSmallTitle 短标题
     */
    public BlogContent(String postTitle, String postContent, Date postDate, String postSmallTitle) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postSmallTitle = postSmallTitle;
    }


    public BlogContent() {

    }
}
