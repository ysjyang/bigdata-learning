package com.netcloud.bigdata.spark_core.basiclearning.projectpractice

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Demo_000_SortWordCount
  * 将单词统计的结果 按照统计单词数目进行降序排序
  * @author yangshaojun
  * #date  2019/3/13 17:32
  * @version 1.0
  */
object Demo_000_SortWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Demo_000_SortWordCount")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile("data/sparkcore/wordcount.txt")
    val wordsRDD = linesRDD.flatMap(line => line.split(","))
    val wordRDD = wordsRDD.map(word => (word, 1))
    val retRDD = wordRDD.reduceByKey(_ + _)
    val exchangeRDD = retRDD.map(t => (t._2, t._1))
    val sortRDD = exchangeRDD.sortByKey(false)
    val normalRDD = sortRDD.map(t => (t._2, t._1))
    normalRDD.foreach(print)
  }

}
