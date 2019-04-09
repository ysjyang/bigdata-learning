package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession

/**
  * @author yangshaojun
  * #date  2019/3/27 16:28
  * @version 1.0
  */
object TfIdfExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("TfIdfExample")
      .getOrCreate()

    // 创建一个集合，每一个句子代表一个文件。
    val sentenceData = spark.createDataFrame(Seq(
      (0.0, "Hi I heard about Spark"),
      (0.0, "I wish Java could use case classes"),
      (1.0, "Logistic regression models are neat")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words") //设置分词器的属性 输入列和输出列名称。
    val wordsData = tokenizer.transform(sentenceData)// 用tokenizer分词器把每个句子分解成单词；tokenizer的transform（）方法把每个句子拆分成了一个个单词。

    // 用HashingTF的transform（）方法把句子哈希成特征向量。我们这里设置哈希表的桶数为20
    val hashingTF = new HashingTF()
      .setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)// 词频将每行句子中的单词转为稀疏向量
    val featurizedData = hashingTF.transform(wordsData) // 哈希
    /**
      * 我们可以看到每一个单词被哈希成了一个不同的索引值。
      * 以”I heard about Spark and I love Spark”为例，输出结果中2000代表哈希表的桶数，
      * “[105,365,727,1469,1858,1926]”分别代表着“i, spark, heard, about, and, love”的哈希值，
      * “[2.0,2.0,1.0,1.0,1.0,1.0]”为对应单词的出现次数。
      */
    featurizedData.show()

    // 用IDF方法来重新构造特征向量的规模，生成的idf是一个Estimator，
    // 在特征向量上应用它的fit（）方法，会产生一个IDFModel。
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
      // 同时，调用IDFModel的transform方法，可以得到每一个单词对应的TF-IDF 度量值。
    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select("label", "features").show()

    /**
      * “[105,365,727,1469,1858,1926]”分别代表着“i, spark, heard, about, and, love”的哈希值。
      * 105和365分别代表了“i”和”spark”，其TF-IDF值分别是0.2876820724517808和0.6931471805599453。
      * 这两个单词都在第一句中出现了两次，而”i”在第二句中还多出现了一次，从而导致”i”的TF-IDF 度量值较低。
      * 因此，与“i”相比，“spark”能更好的区分文档。
      * 通过TF-IDF得到的特征向量，在接下来可以被应用到相关的机器学习方法中。
      */
    spark.stop()
  }


}
