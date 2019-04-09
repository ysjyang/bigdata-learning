package com.netcloud.bigdata.sparksql

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * DataFrame的创建
  * 从已经存在的RDD生成，从hive表、或者其他数据源（本地或者HDFS）
  * 1）在创建一个DataFrame时候，首先要用创建一个sparksession（sparksql程序的入口）
 *  2）通过读取其他数据源的方式 创建DataFrame
  *   a) 读取csv文件创建DataFrame
  *   b) 读取json文件创建DataFrame
  *   c) 读取txt文件创建DataFrame
  *   d) 读取parquet文件创建DataFrame
  *   e) 读取JDBC文件创建DataFrame
  * 3) 通过hive读取数据 创建DataFrame
  *   spark.sql("select * from user")
  * @author yangshaojun
  * #date  2019/3/8 16:59
  * @version 1.0
  */
object SparkSql_000_createDataFrame {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .config("hadoop.home.dir", "/user/hive/warehouse")//开启对hive的支持
      .enableHiveSupport()
      .getOrCreate()

    //1、通过读取csv文件的方式 创建DataFrame; option("header",true)表示csv文件第一行是字段名(scheme信息)；
    //   读取csv文件的方式 如下几种
    val csvDF1=spark.read.option("header",true).csv("data/sparksql/bank_full.csv")// 本地文件
    //val csvDF2=spark.read.option("header",true).csv("hdfs:///ns1/data/bank_full.csv")//HDFS文件

    val csvDF3=spark.read.format("csv").option("header",true).load("data/sparksql/bank_full.csv")// 使用format指定读取的文件格式

    //2、通过读取json文件方式 创建DataFrame
    val jsonDF=spark.read.json("data/sparksql/people.json")
    //jsonDF.show()

    //3、读取parquet文件的方式 创建DataFrame
    val parquetDF=spark.read.parquet("data/parquet/*")
    //parquetDF.show()

    //4、读取text 文件创建DataFrame
    val textDF=spark.read.text("data/sparkcore/wordcount.txt")
    //textDF.show()

    //5、读取JDBC 文件创建DataFrame 然后保存到HDFS
    val url="jdbc:mysql://106.12.219.51:3306/maximai"
    val tabName="sys_user"
    val prop=new Properties()
    prop.setProperty("user","maximai")
    prop.setProperty("password","maximai")
    val jdbcDF=spark.read.jdbc(url,tabName,prop)
    jdbcDF.show()
    //保存模式 为覆盖 文件格式是csv
    //jdbcDF.write.mode(SaveMode.Overwrite).format("com.databricks.spark.csv").save("data/mysqlToHDFS/sys_user") //将mysql中的数据导入到HDFS

    //6、从Hive里面读取数据 开启对hive的支持
//    spark.sql("select * from user").show()


    jsonDF.write.mode(SaveMode.ErrorIfExists).save("data/sparksql/people.parquet")

  }
}
