package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * repartition算子
  * 减少或者增多分区，会产生shuffle.(当多个分区分到一个分区中时不会产生shuffle)
  *
  * @author yangshaojun
  * #date  2019/3/3 1:14
  * @version 1.0
  */
object Transform_0018_repartition {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("repartition")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(1,2,3,4,5,6,7),3)
    val rdd2 = rdd1.mapPartitionsWithIndex((partitionIndex,iter)=>{
      val list = ListBuffer[String]()
      while(iter.hasNext){//遍历分区
        list += "rdd1partitionIndex : "+partitionIndex+",value :"+iter.next()//遍历分区下的元素
      }
      list.iterator
    })

    rdd2.foreach{ println }

    val rdd3 = rdd2.repartition(4)//将3个分区的RDD增加到4个分区 产生shuffle
    //    val rdd3 = rdd2.repartition(1)//将3个分区的RDD增减少到1个分区不 产生shuffle
    val result = rdd3.mapPartitionsWithIndex((partitionIndex,iter)=>{
      val list = ListBuffer[String]()
      while(iter.hasNext){
        list +=("repartitionIndex : "+partitionIndex+",value :"+iter.next())
      }
      list.iterator
    })
    result.foreach{ println}//打印出四个分区时的元素


    sc.stop()
  }
}
