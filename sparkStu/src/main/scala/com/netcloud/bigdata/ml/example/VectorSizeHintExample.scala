//package com.netcloud.bigdata.ml.example
//
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.ml.feature.{VectorAssembler, VectorSizeHint}
//import org.apache.spark.ml.linalg.Vectors
///**
//  * @author yangshaojun
//  * #date  2019/3/29 17:54
//  * @version 1.0
//  */
//object VectorSizeHintExample {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession
//      .builder
//      .master("local")
//      .appName("VectorSizeHintExample")
//      .getOrCreate()
//
//    val dataset = spark.createDataFrame(
//      Seq(
//        (0, 18, 1.0, Vectors.dense(0.0, 10.0, 0.5), 1.0),
//        (0, 18, 1.0, Vectors.dense(0.0, 10.0), 0.0))
//    ).toDF("id", "hour", "mobile", "userFeatures", "clicked")
//
//    val sizeHint = new VectorSizeHint()
//      .setInputCol("userFeatures")
//      .setHandleInvalid("skip")
//      .setSize(3)
//
//    val datasetWithSize = sizeHint.transform(dataset)
//    println("Rows where 'userFeatures' is not the right size are filtered out")
//    datasetWithSize.show(false)
//
//    val assembler = new VectorAssembler()
//      .setInputCols(Array("hour", "mobile", "userFeatures"))
//      .setOutputCol("features")
//
//    // This dataframe can be used by downstream transformers as before
//    val output = assembler.transform(datasetWithSize)
//    println("Assembled columns 'hour', 'mobile', 'userFeatures' to vector column 'features'")
//    output.select("features", "clicked").show(false)
//  }
//}
