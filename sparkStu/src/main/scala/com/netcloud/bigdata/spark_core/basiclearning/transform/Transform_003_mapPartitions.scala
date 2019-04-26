package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * mapPartitions（）算子
  * Note:与map算子类似，map是一次处理一个分区中的一条数据，而mapPartitions是一次处理一个分区的数据。
  * 示例：如果在map过程中需要频繁创建额外的对象(例如将rdd中的数据通过jdbc写入数据库,map需要为每个元素创建一个链接而mapPartition为每个partition创建一个链接)
  *      则mapPartitions效率比map高的多。
  * 什么情况下使用mappartition?
  * 如果RDD 的数据量不是特别大的大，建议使用mappartition算子替代 map可以加快处理的速度。
  * 如果数据量特别的大，比如10亿数据，不建议使用，可能内存溢出。
  * @author yangshaojun
  * #date  2019/3/1 23:03
  * @version 1.0
  */
object Transform_003_mapPartitions {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("use flatMap").setMaster("local[2]")

    val sc = new SparkContext(conf)
    val rdd1 = sc.makeRDD(Array(1,2,3,4,5,6),4)
    val mapResult  = rdd1.mapPartitions(func);
    mapResult.foreach(println)
    sc.stop()
  }
  //mapPartitions算子中传入的函数 打印 "插入数据库" 并返回分区的元素。
  def func(iter: Iterator[Int]) : Iterator[Int]={
    println("插入数据库")
    iter
  }
}
