package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession

/**
  * @author yangshaojun
  * #date  2019/3/28 14:41
  * @version 1.0
  */
object FeatureHasherExample {
  def main(args: Array[String]): Unit = {
      val spark = SparkSession
        .builder
        .master("local")
        .appName("FeatureHasherExample")
        .getOrCreate()

      val dataset = spark.createDataFrame(Seq(
        (2.2, true, "1", "foo"),
        (3.3, false, "2", "bar"),
        (4.4, false, "3", "baz"),
        (5.5, false, "4", "foo")
      )).toDF("real", "bool", "stringNum", "string")

//      val hasher = new FeatureHasher()
//        .setInputCols("real", "bool", "stringNum", "string")
//        .setOutputCol("features")
//
//      val featurized = hasher.transform(dataset)
//      featurized.show(false)
//      // $example off$

      spark.stop()
  }

}
