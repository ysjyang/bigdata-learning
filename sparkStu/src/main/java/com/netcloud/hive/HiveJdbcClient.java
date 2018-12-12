package com.netcloud.hive;

import java.sql.*;

/**
 * 通过jdbc的方式 连接hiveserver2
 * @Author: yangshaojun
 * @Date: 2018/12/5 11:23
 * @Version 1.0
 */
public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection("jdbc:hive2://124.202.155.72:61017/default", "root", "");
        Statement stmt = con.createStatement();
        String sql = "select t.* from bank_full t";
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "-" + res.getString("age"));
        }
    }
}