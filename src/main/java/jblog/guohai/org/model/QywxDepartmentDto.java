package jblog.guohai.org.model;

import lombok.Data;

/**
 * 企业微信部门实体
 */
@Data
public class QywxDepartmentDto {
    private String id;
    private String name;
    private String name_en;
    private String parentid;
    private String order;
}
