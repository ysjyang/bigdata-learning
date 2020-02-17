package com.netcloud.bigdata.mllib.com.svm.example

import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/*
  * @Author: yangshaojun
  * @Date: 2020/02/17 20:57
  * @Version 1.0
  * 官方文档示例
  */
object SVMWithSGDExample {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SVMWithSGDExample").setMaster("local")
    val sc = new SparkContext(conf)
    // $example on$
    // Load training data in LIBSVM format.
    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)
    val numIterations = 200
    val model = SVMWithSGD.train(training, numIterations)
    model.clearThreshold()
    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)
    val auROC = metrics.areaUnderROC()
    println(s"Area under ROC = $auROC")


  }
}
