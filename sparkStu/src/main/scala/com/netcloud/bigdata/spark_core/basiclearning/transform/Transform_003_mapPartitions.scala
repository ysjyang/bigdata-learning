package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * mapPartitions（）算子
  * Note:与map算子类似，map是处理的每条数据，而mapPartitions是处理的每个分区的数据。
  * 示例：如果在map过程中需要频繁创建额外的对象(例如将rdd中的数据通过jdbc写入数据库,map需要为每个元素创建一个链接而mapPartition为每个partition创建一个链接)
  *      则mapPartitions效率比map高的多。
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
