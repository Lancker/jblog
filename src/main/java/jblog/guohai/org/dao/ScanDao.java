package jblog.guohai.org.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import jblog.guohai.org.model.ScanModel;

public interface ScanDao {

	 /**
     * 增加新的BLOG
     *
     * @param blog
     * @return
     */
    @Insert("INSERT INTO `jblog_scan`\n" +
            "(`scan_date`,\n" +
            "`scan_userid`,\n" +
            "`scan_name`,\n" +
            "`scan_mobile`,\n" +
            "`scan_mobile`,\n" +
            "`scan_department`,\n" +
            "`scan_position`,\n" +
            "`scan_email`,\n" +
            "`scan_avatar`,\n" +
            "`scan_qrcode`,\n" +
            "`scan_qrcode_title`)\n" +
            "VALUES\n" +
            "(#{scan.scanDate},\n" +
            "#{scan.scanUserId},\n" +
            "#{scan.scanName},\n" +
            "#{scan.scanMobile},\n" +
            "#{scan.scanDepartment},\n" +
            "#{scan.scanPosition},\n" +
            "#{scan.scanEmail},\n" +
            "#{scan.scanAvatar},\n" +
            "#{scan.scanQrcode},\n" +
            "#{scan.scanQrcodeTitle});\n")
    @Options(useGeneratedKeys = true, keyProperty = "scan.scanCode")
    Boolean addScan(@Param("scan") ScanModel scan);
}
