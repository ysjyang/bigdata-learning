package com.netcloud.bigdata.spark_core.basiclearning.projectpractice

import org.apache.spark.{SparkConf, SparkContext}

/** scala版本
  * 对文本文件中的数字，获取最大的前三个
  *
  * @author yangshaojun
  * #date  2019/3/15 16:40
  * @version 1.0
  */
object Demo_002_TopThree {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Demo_002_TopThree")
    val sc = new SparkContext(conf)
    val lineRDD = sc.textFile("data/sparkcore/top3.txt")
    val kvRDD = lineRDD.map(num => (num.toInt, num))
    val sortRDD = kvRDD.sortByKey(false)
    val normalRDD = sortRDD.map(kv => kv._2)
    val list = normalRDD.take(3)
    for (ret <- list) {
      println(ret)
    }
  }
}
