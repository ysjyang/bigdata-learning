package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer

/**
  * mapPartitionsWithIndex与mapPartitions基本相同，
  * 只是在处理函数的参数是一个二元元组，元组的第一个元素是当前处理的分区的index，
  * 元组的第二个元素是当前处理的分区元素组成的Iterator
  * Note:与mapPartitions类似，但是mapPartitionsWithIndex含有分区的索引。
  */
object Transform_004_mapPartitionsWithIndex {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("mapPartitionsWithIndex")
    val sc = new SparkContext(conf)
    val rdd = sc.makeRDD(List("a", "b", "c"), 3)
    val mapPartitionsWithIndex = rdd.mapPartitionsWithIndex((index, iter) => {
      val list = ListBuffer[String]()
      while (iter.hasNext) {
        val v = iter.next()
        println("index = " + index + " , value = " + v)
        list.+=(v)
      }
      list.iterator
    })
    println(mapPartitionsWithIndex.count())
    sc.stop();

  }

}