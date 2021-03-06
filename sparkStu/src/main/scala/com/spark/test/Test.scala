package com.spark.test

import java.util.Properties

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("HdfsTest")
      .getOrCreate()

        val ssc = new StreamingContext(spark.sparkContext,Seconds(5));
        val lines = ssc.socketTextStream("netcloud05", 9999)
        val words = lines.flatMap(_.split(" "))
  }

}
