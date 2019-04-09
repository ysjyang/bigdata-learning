package com.netcloud.bigdata.ml

import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.SparkSession

/**
  * 朴素贝叶斯实现二分类
  * @author yangshaojun
  * #date  2019/2/21 10:50
  * @version 1.0
  *
  */
object NaiveBayesExample {
  def main(args: Array[String]): Unit = {

    //创建sparkSession对象
    val spark = SparkSession
      .builder()
      .appName("NaiveBayesExample")
      .master("local[2]")
      .getOrCreate()
    //加载LIBSVM存储格式的数据作为一个DataFrame.
    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")
    println("数据文件中行数:" + data.count())
    //默认显示20行
    data.show()
    //根据提供的权重随机拆分数据集为训练集和测试集 也可以给指定一个种子
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), 100L) //返回的是数组类型，数组存储的是DataSet类型数据

    //带入训练集(fit操作)训练出一个贝叶斯模型
    val naiveBayesModel = new NaiveBayes().fit(trainingData)
    //模型调用transform()来进行预测，生成一个新的DataFrame
    val predictions = naiveBayesModel.transform(testData)
    //输出预测结果数据
    predictions.show()

    /** 模型评估
      * 创建一个MulticlassClassificationEvaluator实例，用setter方法把预测分类的列名和真实分类的列名进行设置；
      * 然后计算预测准确率和错误率
      */
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions) //准确率
    println("错误率=" + (1 - accuracy))
  }

}
