package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}


/**
  * 经常使用reduceBykey进行单词的统计
  * reduceByKey(binary_function)算子
  * reduceByKey就是对元素为KV对的RDD中Key相同的元素的Value进行binary_function的reduce操作，
  * 因此，Key相同的多个元素的值被reduce为一个值，然后与原RDD中的Key组成一个新的KV对。
  * 示例：单词统计
  * @author yangshaojun
  * #date  2019/3/2 21:44
  * @version 1.0
  */
object Transform_0010_reduceByKey {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("reduceByKey").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val linesRDD = sc.textFile("data/basicdata/reducebykey.txt")
    val wordsRDD = linesRDD.flatMap(_.split(","))
    val word = wordsRDD.map((_, 1))
    val result = word.reduceByKey(_+_)
    result.foreach(println)

  }

}
