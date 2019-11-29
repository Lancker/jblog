package jblog.guohai.org.model;

import lombok.Data;

/**
 * 企业微信部门信息响应实体
 */
@Data
public class QywxDepartmentResponse {
    private String errcode;
    private String errmsg;
    private QywxDepartmentDto[] department;
}
