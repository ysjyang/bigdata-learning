//package com.netcloud.bigdata.ml.example
//
//import org.apache.spark.sql.SparkSession
//import org.apache.spark.ml.feature.OneHotEncoderEstimator
///**
//  * @author yangshaojun
//  * #date  2019/3/29 11:21
//  * @version 1.0
//  */
//object OneHotEncoderEstimatorExample {
//
//  val spark=SparkSession
//    .builder
//    .master("local")
//    .appName("OneHotEncoderEstimatorExample")
//    .getOrCreate()
//
//  val df = spark.createDataFrame(Seq(
//    (0.0, 1.0),
//    (1.0, 0.0),
//    (2.0, 1.0),
//    (0.0, 2.0),
//    (0.0, 1.0),
//    (2.0, 0.0)
//  )).toDF("categoryIndex1", "categoryIndex2")
//
//  val encoder = new OneHotEncoderEstimator()
//    .setInputCols(Array("categoryIndex1", "categoryIndex2"))
//    .setOutputCols(Array("categoryVec1", "categoryVec2"))
//  val model = encoder.fit(df)
//
//  val encoded = model.transform(df)
//  encoded.show()
//
//}
