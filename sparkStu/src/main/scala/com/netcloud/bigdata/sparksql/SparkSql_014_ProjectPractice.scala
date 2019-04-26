package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.SparkSession

/**
  *
  * @author yangshaojun
  * #date  2019/4/10 11:11
  * @version 1.0
  */
object SparkSql_014_ProjectPractice {
  def main(args: Array[String]): Unit = {
      val spark=SparkSession.builder()
        .master("local")
        .appName("SparkSql_014_ProjectPractice")
        .getOrCreate()


    val strRDD=spark.sparkContext.textFile("data/sparksql/keyword.txt")




  }
}
