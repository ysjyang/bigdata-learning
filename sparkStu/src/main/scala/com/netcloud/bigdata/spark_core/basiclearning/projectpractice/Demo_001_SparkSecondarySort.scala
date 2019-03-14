package com.netcloud.bigdata.spark_core.basiclearning.projectpractice

import org.apache.spark.{SparkConf, SparkContext}

/**
  * spark scala版本的二次排序。
  * 1)自定义key 要实现Ordered接口和Serializable接口，在key中实现自己对多个列的排序算法。
  * 2) 将包含文本的RDD，映射成Key为自定义的key、value为文本的RDD。
  * 3) 使用SortByKey算子 按照自定义的Key进行排序。
  * 4) 再次映射，剔除自定义的key，只保留文本行。
  * @author yangshaojun
  * #date  2019/3/14 8:58
  * @version 1.0
  */
object Demo_001_SparkSecondarySort {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Demo_001_SparkSecondarySort")
    val sc = new SparkContext(conf)
    val lineRDD = sc.textFile("data/sparkcore/secondarysort.txt")

    val kvRDD = lineRDD.map(line => (SecondarySortKey(line.split(" ")(0).toInt, line.split(" ")(1).toInt), line))
    val sortRDD = kvRDD.sortByKey()
    val retRDD = sortRDD.map(kv => kv._2)
    retRDD.foreach(println)
  }

}
