package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.MaxAbsScaler
import org.apache.spark.ml.linalg.Vectors
/**
  * @author yangshaojun
  *         #date  2019/3/29 14:53
  * @version 1.0
  */
object MaxAbsScalerExample {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local")
      .appName("MaxAbsScalerExample")
      .getOrCreate()

    val dataFrame = spark.createDataFrame(Seq(
      (0, Vectors.dense(1.0, 0.1, -8.0)),
      (1, Vectors.dense(2.0, 1.0, -4.0)),
      (2, Vectors.dense(4.0, 10.0, 8.0))
    )).toDF("id", "features")

    val scaler = new MaxAbsScaler()
      .setInputCol("features")
      .setOutputCol("scaledFeatures")

    // Compute summary statistics and generate MaxAbsScalerModel
    val scalerModel = scaler.fit(dataFrame)

    // rescale each feature to range [-1, 1]
    val scaledData = scalerModel.transform(dataFrame)
    scaledData.select("features", "scaledFeatures").show()

  }
}
