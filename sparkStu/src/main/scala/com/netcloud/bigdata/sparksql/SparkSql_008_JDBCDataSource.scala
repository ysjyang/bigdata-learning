package com.netcloud.bigdata.sparksql

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * scala版本 JDBC 数据源
  *
  * @author yangshaojun
  * #date  2019/4/8 17:02
  * @version 1.0
  */
object SparkSql_008_JDBCDataSource {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_008_JDBCDataSource")
      .getOrCreate()

    // 读取JDBC 文件创建DataFrame 然后保存到HDFS
    val url = "jdbc:mysql://106.12.219.51:3306/maximai"
    val tabName = "sys_user"
    val prop = new Properties()
    prop.setProperty("user", "maximai")
    prop.setProperty("password", "maximai")
    val jdbcDF = spark.read.jdbc(url, tabName, prop)
    jdbcDF.show()

    // 等同于下面的加载方式
    val jdbcDFother =spark.read.format("jdbc")
      .options(Map(
        "url" -> "jdbc:mysql://106.12.219.51:3306/maximai",
        "dbtable" -> "sys_user",
        "user" -> "maximai",
        "password" -> "maximai"
      )).load()
    jdbcDFother.select("username").show()

  }

}
