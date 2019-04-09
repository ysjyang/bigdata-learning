package com.netcloud.bigdata.spark_core.basiclearning.demo

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author yangshaojun
  * #date  2019/3/9 11:22
  * @version 1.0
  */
object Demo000_WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("word_count")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile("file:///opt/word.txt")
    val wordsRDD = linesRDD.flatMap(line => line.split(","))
    val wordTuple = wordsRDD.map(word => (word, 1))
    val retRdd = wordTuple.reduceByKey((a: Int, b: Int) => (a + b))
    retRdd.foreach(println)
  }
}
