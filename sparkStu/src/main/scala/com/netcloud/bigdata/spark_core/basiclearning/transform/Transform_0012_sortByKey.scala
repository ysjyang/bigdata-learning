package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * sortByKey 算子
  * Note:作用在K,V格式的RDD上，对key进行升序或者降序排序.
  *      默认升序排序  当方法参数为false时降序排序
  * @author yangshaojun
  * #date  2019/3/3 0:19
  * @version 1.0
  */
object Transform_0012_sortByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sortByKey").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile("data/basicdata/reducebykey.txt")
    val wordsRDD = linesRDD.flatMap(_.split(","))
    val word = wordsRDD.map((_, 1))
    val reduceRDD = word.reduceByKey(_+_)

    /**
      * 使用sortByKey对统计的单词进行排序
      */
    val exchangeRDD=reduceRDD.map(tuple => (tuple._2,tuple._1)) //将统计的单词tuple(word,num)位置互换 让单词数组作为key
    val exchangeRetRDD=exchangeRDD.sortByKey(false)//默认升序排序 false是降序排序
    exchangeRetRDD.map(tuple => (tuple._2,tuple._1)).foreach(println)
  }

}
