package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.VectorIndexer

/**
  * @author yangshaojun
  * #date  2019/3/29 13:27
  * @version 1.0
  */
object VectorIndexerExample {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder
      .master("local")
      .appName("VectorIndexerExample")
      .getOrCreate()

    val data = spark.read.format("libsvm").load("data/mllib/sample_libsvm_data.txt")

    val indexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexed")
      .setMaxCategories(10)

    val indexerModel = indexer.fit(data)

    val categoricalFeatures: Set[Int] = indexerModel.categoryMaps.keys.toSet
    println(s"Chose ${categoricalFeatures.size} " +
      s"categorical features: ${categoricalFeatures.mkString(", ")}")

    // Create new column "indexed" with categorical values transformed to indices
    val indexedData = indexerModel.transform(data)
    indexedData.show()
  }
}
