package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.QuantileDiscretizer

/**
  * @author yangshaojun
  *         #date  2019/3/29 16:41
  * @version 1.0
  */
object QuantileDiscretizerExample {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder
      .master("local")
      .appName("QuantileDiscretizerExample")
      .getOrCreate()


    val data = Array((0, 18.0), (1, 19.0), (2, 8.0), (3, 5.0), (4, 2.2))
    val df = spark.createDataFrame(data).toDF("id", "hour")

    val discretizer = new QuantileDiscretizer()
      .setInputCol("hour")
      .setOutputCol("result")
      .setNumBuckets(2)

    val result = discretizer.fit(df).transform(df)
    result.show(false)
  }
}
