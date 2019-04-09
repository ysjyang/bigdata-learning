package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.feature.PolynomialExpansion
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession

/**
  * 多项式扩展
  *
  * @author yangshaojun
  * #date  2019/3/29 9:06
  * @version 1.0
  */
object PolynomialExpansionExample {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder
      .master("local")
      .appName("PolynomialExpansionExample")
      .getOrCreate()

    val data = Array(
      Vectors.dense(2.0, 1.0),
      Vectors.dense(0.0, 0.0),
      Vectors.dense(3.0, -1.0)
    )
    val df = spark.createDataFrame(data.map(Tuple1.apply)).toDF("features")

    val polyExpansion = new PolynomialExpansion()
      .setInputCol("features")
      .setOutputCol("polyFeatures")
      .setDegree(3) //设置展开度 度 >=2

    val polyDF = polyExpansion.transform(df)
    polyDF.show(false)

  }

}
