package com.sumi.monitor.core;

import org.quartz.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kery on 2015/12/2.
 */
public class PerlJob implements Job {
    private final String PERL_PATH = "D:\\tool\\Perl64\\bin\\perl.exe";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String id = dataMap.getString("id");
        String name = dataMap.getString("name");
        String filePath = dataMap.getString("file");

        String sql = "insert into sys_cron_log(`cron_id`,`name`,`filepath`) values(" + id + ",'" + name + "','" + filePath + "')";//SQL语句
        DBHelper db1 = new DBHelper(sql);//创建DBHelper对象

        try {
            db1.pst.executeUpdate();
            ResultSet rs = db1.pst.getGeneratedKeys();
            int record_id = 0;
            if (rs.next()) {
                record_id = rs.getInt(1);//取得ID
            }
            db1.close();//关闭连接

            if (record_id > 0) {
                String[] cmd = {PERL_PATH, filePath, String.valueOf(record_id)};
                try {
                    Process p = Runtime.getRuntime().exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
