package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel}
import org.apache.spark.sql.SparkSession

/**
  * @author yangshaojun
  * #date  2019/3/28 13:54
  * @version 1.0
  */
object CountVectorizerExample {
  def main(args: Array[String]): Unit = {
      val spark = SparkSession
        .builder
        .master("local")
        .appName("CountVectorizerExample")
        .getOrCreate()

      // 可以看成是一个包含两个文档的迷你语料库。
      val df = spark.createDataFrame(Seq(
        (0, Array("a", "b", "c")),
        (1, Array("a", "b", "b", "c", "a"))
      )).toDF("id", "words")
      // 从语料库中拟合一个CountVectorizerModel
      val cvModel: CountVectorizerModel = new CountVectorizer()
        .setInputCol("words")
        .setOutputCol("features")
        .setVocabSize(3) // 指定显示词语频率排序前3的
        .setMinDF(2) // 指定文档的最小值
        .fit(df)

      // 或者，使用先验词汇表定义CountVectorizerModel
      val cvm = new CountVectorizerModel(Array("a", "b", "c"))
        .setInputCol("words")
        .setOutputCol("features")
      cvModel.transform(df).show(false)

      spark.stop()
  }

}
