package com.netcloud.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.hive.HiveContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * java 版本 使用JDBC数据源 创建DataFrame
 * @author yangshaojun
 * #date  2019/4/8 17:22
 * @version 1.0
 */
public class JDBCDataSource {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf();
        conf.setAppName("JDBCDataSource").setMaster("local");
        JavaSparkContext javaSparkContext=new JavaSparkContext(conf);
        SQLContext sqlContext=new SQLContext(javaSparkContext);

        // 方式一: 将配置文件放在Map中。
        Map<String,String> options=new HashMap<String,String>();
        options.put("url","jdbc:mysql://106.12.219.51:3306/maximai");
        options.put("dbtable","sys_user");
        options.put("user","maximai");
        options.put("password","maximai");
        Dataset<Row> jdbcDF = sqlContext.read().format("jdbc").options(options).load();
        jdbcDF.show();

        // 等同于 如下的方式
        // 方式二: 将配置文件放在Properties中。
        String url = "jdbc:mysql://106.12.219.51:3306/maximai";
        String tabName = "sys_user";
        Properties prop = new Properties();
        prop.setProperty("user", "maximai");
        prop.setProperty("password", "maximai");
        Dataset<Row> jdbcDFOther = sqlContext.read().jdbc(url, tabName, prop);
        jdbcDFOther.show();


    }
}
