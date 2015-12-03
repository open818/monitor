package com.sumi.monitor.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by kery on 2015/12/2.
 */
public class DBHelper {
    public static final String url = "jdbc:mysql://192.168.1.87/test";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "sumibuy_root";
    public static final String password = "welcome";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            this.conn.close();
            this.conn = null;
            this.pst.close();
            this.pst = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
