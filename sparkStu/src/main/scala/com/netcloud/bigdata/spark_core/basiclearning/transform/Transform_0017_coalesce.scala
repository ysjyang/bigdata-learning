package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * coalesce 算子减少分区
  * 第二个参数是减少分区的过程中是否产生shuffle，true是产生shuffle，false是不产生shuffle，默认是false.
  * 如果coalesce的分区数比原来的分区数还多，第二个参数设置false，即不产生shuffle,不会起作用。
  * 如果第二个参数设置成true则效果和repartition一样，即coalesce(numPartitions,true) = repartition(numPartitions)
  *
  * @author yangshaojun
  * #date  2019/3/3 1:10
  * @version 1.0
  */
object Transform_0017_coalesce {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("coalesce")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Array(1,2,3,4,5,6),4)
    val rdd2 = rdd1.mapPartitionsWithIndex((partitionIndex,iter)=>{
      val list = new ListBuffer[String]()
      while(iter.hasNext){
        list += "rdd1 PartitonIndex : "+ partitionIndex+",value :"+iter.next()
      }
      list.iterator
    })
    rdd2.foreach { println }
    val rdd3 = rdd2.coalesce(5, true)
    val rdd4 = rdd3.mapPartitionsWithIndex((partitionIndex,iter)=>{
      val list = new ListBuffer[String]()
      while(iter.hasNext){
        list += "coalesce PartitionIndex :"+partitionIndex+",value:"+iter.next()
      }
      list.iterator
    })
    rdd4.foreach { println}

    sc.stop()
  }
}
