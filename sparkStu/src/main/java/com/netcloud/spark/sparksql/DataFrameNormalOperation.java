package com.netcloud.spark.sparksql;

import org.apache.spark.sql.*;

/**
 /**
 * java版本的DataFrame的常用操作。
 * 通常将DataFrame 存储在一张临时表中，后面通过执行sql的方式执行操作。
 *
 * @author yangshaojun
 * #date  2019/4/3 9:03
 * @version 1.0
 */
public class DataFrameNormalOperation {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("DataFrameNormalOperation")
                .master("local")
                .getOrCreate();
        // 等同于 SQLContext对象 sc.read().json("../.json")以及 sc.read().load(../.json)
        Dataset<Row> rowDataset = spark.read().json("data/sparksql/people.json");

        // 1、打印DataFrame数据信息
        rowDataset.show();
        // 2、打印schema信息
        rowDataset.printSchema();
        // 3、打印某列信息
        rowDataset.select("name").show();//等同于rowDataset.select(rowDataset.col("name")).show();
        // 4、查询多列信息；并且对列使用表达式操作 (df.col(columnName))
        rowDataset.select(rowDataset.col("name"), rowDataset.col("age").plus(1)).show();
        // 5、过滤出年龄大于25的people
        rowDataset.filter(rowDataset.col("age").gt(25)).show();
        rowDataset.select(rowDataset.col("name"), rowDataset.col("age"), rowDataset.col("age").gt(25)).show();
        // 6、跟根据某个字段分组 并统计总数
        rowDataset.groupBy("age").count().show();
        // 将DataFrame注册到临时表
        rowDataset.registerTempTable("people");
        // sparksession对象调用sql方法 方法参数书写sql表达式
        spark.sql("select * from people").show();

        // 将DataFrame注册全局表
        try {
            rowDataset.createGlobalTempView("peoples");
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
        // sparksession对象调用sql方法 方法参数书写sql表达式
        // 全局临时视图绑定到系统保留的数据库`global_temp`
        spark.sql("select * from global_temp.peoples").show();



    }
}
