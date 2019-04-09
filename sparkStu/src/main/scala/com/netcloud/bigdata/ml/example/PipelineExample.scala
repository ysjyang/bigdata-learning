package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

/**
  * @author yangshaojun
  * #date  2019/3/27 13:52
  * @version 1.0
  */
object PipelineExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
        .master("local")
      .appName("PipelineExample")
      .getOrCreate()

    // 准备一个训练文档 集合中存储的tuple类型(id, text, label)
    val training = spark.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark f g h", 1.0),
      (3L, "hadoop mapreduce", 0.0)
    )).toDF("id", "text", "label")

    // 配置一个 ML pipeline, 这个pipeline包含是三个阶段(步骤): tokenizer, hashingTF, 和 lr.
    val tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")
    val hashingTF = new HashingTF()
      .setNumFeatures(1000)
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("features")
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.001)
    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, hashingTF, lr))

    // 给pipeline拟合（fit）一个训练文档;pipeline也是一个Estimator.
    val model = pipeline.fit(training)

    //现在我们可以保存一个拟合后的pipeline 到磁盘
    model.write.overwrite().save("/tmp/spark-logistic-regression-model")

    // 现在我们可以保存一个未拟合的pipeline 到磁盘
    pipeline.write.overwrite().save("/tmp/unfit-lr-model")

    // 并在生产过程中将其重新加载
    val sameModel = PipelineModel.load("/tmp/spark-logistic-regression-model")

    // 准备测试文档，他们是没有打标签的 (id, text) tuples.
    val test = spark.createDataFrame(Seq(
      (4L, "spark i j k"),
      (5L, "l m n"),
      (6L, "spark hadoop spark"),
      (7L, "apache hadoop")
    )).toDF("id", "text")

    // 对测试文档进行预测
    model.transform(test)
      .select("id", "text", "probability", "prediction")
      .collect()
      .foreach { case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
        println(s"($id, $text) --> prob=$prob, prediction=$prediction")
      }

    spark.stop()
  }

}
