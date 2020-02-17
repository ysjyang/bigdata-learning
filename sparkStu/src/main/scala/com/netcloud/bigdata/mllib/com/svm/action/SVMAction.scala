package com.netcloud.bigdata.mllib.com.svm.action

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.optimization.{HingeGradient, SquaredL2Updater}
import org.apache.spark.mllib.util.MLUtils

object SVMAction {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SVMWithSGDExample").setMaster("local")
    val sc = new SparkContext(conf)
    // $example on$
    // Load training data in LIBSVM format.
    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data.txt")
    val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    val svm = new SVMWithSGD
    svm.setIntercept(false)
    svm.optimizer.setNumIterations(1000)
    svm.optimizer.setStepSize(1.0)
    svm.optimizer.setRegParam(0.01)
    svm.optimizer.setMiniBatchFraction(1.0)
    svm.optimizer.setConvergenceTol(0.001)
    svm.optimizer.setGradient(new HingeGradient())//new LeastSquaresGradient 或者 new LogisticGradient
    svm.optimizer.setUpdater(new SquaredL2Updater())//new SquaredL2Updater()  或 new L1Updater()
    val model=svm.run(training)
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
