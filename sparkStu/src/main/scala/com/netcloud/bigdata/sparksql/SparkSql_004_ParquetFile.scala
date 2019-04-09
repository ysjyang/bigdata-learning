package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.SparkSession

/**
  * @author yangshaojun
  *  #date  2019/4/8 12:23
  * @version 1.0
  */
object SparkSql_004_ParquetFile {
  def main(args: Array[String]): Unit = {
    val  spark=SparkSession
      .builder()
      .appName("SparkSql_004_ParquetFile")
      .master("local")
      .getOrCreate()
    // 读取parquet文件 创建一个DataFrame
    val parquetDF=spark.read.parquet("data/sparksql/people.parquet")
     // 将DataFrame注册为临时表，然后使用SQL查询需要的数据
    parquetDF.registerTempTable("people")

    // 对查询出来的DataFrame进行transform操作，处理数据，然后进行打印
    val resultDF=spark.sql("select * from people")
    resultDF.show()
    /**
      * +----+-------+
      * | age|   name|
      * +----+-------+
      * |null|Michael|
      * |  30|   Andy|
      * |  19| Justin|
      * +----+-------+
      */
    // 将DataFrame转为RDD ;然后遍历打印 name字段信息
    val result=resultDF.rdd.map(row => row(1)).collect().foreach(name => println(name))

  }
}
