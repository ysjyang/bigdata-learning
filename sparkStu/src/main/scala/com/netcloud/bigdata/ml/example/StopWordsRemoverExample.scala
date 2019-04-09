package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.feature.StopWordsRemover
import org.apache.spark.sql.SparkSession

/**
  * @author yangshaojun
  * #date  2019/3/28 17:20
  * @version 1.0
  */
object StopWordsRemoverExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("StopWordsRemoverExample")
      .getOrCreate()

    val dataSet = spark.createDataFrame(Seq(
      (0, Seq("I", "saw", "the", "red", "balloon")),
      (1, Seq("Mary", "had", "a", "little", "lamb"))
    )).toDF("id", "raw")

    val remover = new StopWordsRemover()
      .setInputCol("raw")
      .setOutputCol("filtered")
    remover.transform(dataSet).show(false)

    spark.stop()
  }

}
