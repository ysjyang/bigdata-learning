package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
/**
  * 根据用户每天的访问和购买日志，统计每天的uv和销售额。
  * @author yangshaojun
  * #date  2019/4/8 17:57
  * @version 1.0
  */
object SparkSql_009_DailyUV {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_009_DailyUV")
      .getOrCreate()

    // 要使用SparkSQL的内置函数，就必须导入SQLContext的隐式转换
    import spark.sqlContext.implicits._

    // 构造用户访问日志 并创建DataFrame
    // 日志用逗号隔开 第一列 日期;第二列 用户id ;第三列 销售额 sale
    val userAccessLog=Array(
      "2019-10-01,1122,23.15",
      "2019-10-01,1123,34.5",
      "2019-10-01,1122,45.6",
      "2019-10-02,1122,44,5",
      "2019-10-02,1122,56,5",
      "2019-10-01,1122,66.6",
      "2019-10-02,1123,12,4",
      "2019-10-01,1123,77.6",
      "2019-10-02,1124,23.8"
    )
    // 将模拟出来的用户访问日志RDD 转为DataFrame
    // 首先将普通的RDD 转为元素为Row的RDD
    val userAccessRDD=spark.sparkContext.parallelize(userAccessLog,2)
    val userAccessRowRDD=userAccessRDD.map(log => Row(log.split(",")(0),log.split(",")(1).toInt))

    // 构造DataFrame的元数据
    val structType=StructType(Array(StructField("date",StringType,true),StructField("userid",IntegerType,true)))
   val  userAccessRowDF=spark.createDataFrame(userAccessRowRDD,structType);

    // 接下来使用SparkSQL中的内置函数
    /**
      * 内置函数的用法
      * 1）对DataFrame调用groupBy() 对某一列进行分组
      * 2）然后调用 agg()方法 第一个参数是必须的 即:传入之前在groupBy()方法中出现的字段
      * 3）第二个参数 传入countDistinct 、sum、first等spark内置的函数。
      * 内置函数中，传入的参数，也是用单引号作为前缀的。
      */
    userAccessRowDF.groupBy("date").agg('date, countDistinct('userid)).rdd
      .map(row => Row(row(1), row(2)))
      .collect()
      .foreach(print)


    /**
      * 统计每天的销售额
      */
    val userAccessRowSaleRDD=userAccessRDD.map(log => Row(log.split(",")(0),log.split(",")(2).toDouble))
    // 构造DataFrame的元数据
    val schema=StructType(Array(StructField("date",StringType,true),StructField("sale",DoubleType,true)))
    val  userAccessRowSaleDF=spark.createDataFrame(userAccessRowSaleRDD,schema);

    // 接下来使用SparkSQL中的内置函数
    /**
      * 内置函数的用法
      * 1）对DataFrame调用groupBy() 对某一列进行分组
      * 2）然后调用 agg()方法 第一个参数是必须的 即:传入之前在groupBy()方法中出现的字段
      * 3）第二个参数 传入countDistinct 、sum、first等spark内置的函数。
      * 内置函数中，传入的参数，也是用单引号作为前缀的。
      */
    userAccessRowSaleDF.groupBy("date").agg('date, sum('sale)).rdd
      .map(row => Row(row(1), row(2)))
      .collect()
      .foreach(print)
  }
}
