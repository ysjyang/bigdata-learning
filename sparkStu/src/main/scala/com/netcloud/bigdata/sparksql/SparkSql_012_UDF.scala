package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * 用户自定义函数
  * @author yangshaojun
  * #date  2019/4/9 16:50
  * @version 1.0
  */
object SparkSql_012_UDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_012_UDF")
      .getOrCreate()
    // 构造模拟数据
    val arr=Array("Tom","Jerry","PeiQi","Andy")
    val strRDD=spark.sparkContext.parallelize(arr,2)
    // 将 RDD(String) 转为 RDD(Row)
    val rowRDD=strRDD.map(name => Row(name))

    // 动态转换的方式创建 schema 将RDD转为 DataFrame
    val schema=StructType(Array(StructField("name",StringType,true)))
    // 将RowRDD 转为DataFrame 然后将其注册到临时表中
    spark.createDataFrame(rowRDD,schema).registerTempTable("names")

    // 定义和注册自定义函数
    // 定义函数: 自己写匿名函数
    // 注册函数： SQLContext.udf.register()
    spark.udf.register("strLen",(str:String) => str.length)

    // 使用自定义函数
    spark.sql("select name , strLen(name) from names")
      .collect()
      .foreach(println)

  }

}
