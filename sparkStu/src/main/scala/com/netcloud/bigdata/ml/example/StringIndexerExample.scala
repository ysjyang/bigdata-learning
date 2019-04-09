package com.netcloud.bigdata.ml.example

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.StringIndexer

/**
  * @author yangshaojun
  * #date  2019/3/29 10:00
  * @version 1.0
  */
object StringIndexerExample {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession
      .builder
      .master("local")
      .appName("StringIndexerExample")
      .getOrCreate()

    val df = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    ).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .setHandleInvalid("keep")
    val df2 = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"),(6, "d"),(7, "d"))
    ).toDF("id", "category")

    val indexed = indexer.fit(df).transform(df2)
    indexed.show()
    /**
      * +---+--------+-------------+
      * | id|category|categoryIndex|
      * +---+--------+-------------+
      * |  0|       a|          0.0|
      * |  1|       b|          2.0|
      * |  2|       c|          1.0|
      * |  3|       a|          0.0|
      * |  4|       a|          0.0|
      * |  5|       c|          1.0|
      * +---+--------+-------------+
      */
  }
}
