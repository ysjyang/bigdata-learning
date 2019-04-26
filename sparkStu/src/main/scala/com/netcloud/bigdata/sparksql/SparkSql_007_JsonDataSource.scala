package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.{Column, SparkSession}
import org.apache.spark.sql.functions._
/**
  * 加载json 文件创建 创建DataFrame
  * @author yangshaojun
  * #date  2019/4/9 16:34
  * @version 1.0
  */
object SparkSql_007_JsonDataSource {


  def main(args: Array[String]): Unit = {
    val  spark=SparkSession
      .builder()
      .appName("SparkSql_007_JsonDataSource")
      .master("local")
      .getOrCreate()

    // 每一行是分开的独立有效的json对象
    val jsonDF=spark.read.json("data/sparksql/people.json")
    jsonDF.show()
    jsonDF.withColumn("isErrorColumn",concat(col("name"), col("age")).rlike("""^\d+$"""))
      .filter(col("isErrorColumn"))
     .drop("isErrorColumn")
      .show()


    spark.stop()
  }

}
