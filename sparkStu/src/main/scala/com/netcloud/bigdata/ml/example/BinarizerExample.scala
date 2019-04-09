package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.Binarizer
/**
  * @author yangshaojun
  * #date  2019/3/28 21:43
  * @version 1.0
  */
object BinarizerExample {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder
      .master("local")
      .appName("BinarizerExample")
      .getOrCreate()
    val data = Array((0, 0.1), (1, 0.8), (2, 0.2))
    val dataFrame = spark.createDataFrame(data).toDF("id", "feature")

    val binarizer = new Binarizer()
      .setInputCol("feature")
      .setOutputCol("binarized_feature")
      .setThreshold(0.5)

    val binarizedDataFrame = binarizer.transform(dataFrame)

    println(s"Binarizer output with Threshold = ${binarizer.getThreshold}")
    binarizedDataFrame.show()
  }

}
