package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.feature.Word2Vec
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.sql.{Row, SparkSession}

/**
  * @author yangshaojun
  * #date  2019/3/28 11:20
  * @version 1.0
  */
object Word2VecExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("Word2Vec example")
      .getOrCreate()

    // 每行数据代表一个文档
    val documentDF = spark.createDataFrame(Seq(
      "Hi I heard about Spark".split(" "),
      "I wish Java could use case classes".split(" "),
      "Logistic regression models are neat".split(" ")
    ).map(Tuple1.apply)).toDF("text")
    // 单词映射一个向量
    val word2Vec = new Word2Vec()
      .setInputCol("text")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)
    val model = word2Vec.fit(documentDF)

    val result = model.transform(documentDF)
    result.collect().foreach { case Row(text: Seq[_], features: Vector) =>
      println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }

    spark.stop()
  }
}
