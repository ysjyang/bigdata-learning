package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
  * 根据给定的分区程序对RDD进行重新分区，并在每个生成的分区内按键对记录进行排序。
  * 这比调用重新分区，然后在每个分区内进行排序更有效率，因为它可以将排序压入洗牌机器。
  * 什么时候使用repartitionAndSortWithinPartitions？
  * 如果需要重分区，并且想要对分区中的数据进行升序排序。
  * 提高性能，替换repartition和sortBy
  *
  * @author yangshaojun
  * #date  2019/3/3 1:16
  * @version 1.0
  */
object Transform_0019_repartitionAndSortWithinPartitions {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setAppName("Test").setMaster("local[2]")
    val sc=new SparkContext(conf)
    val list=List((1,"hadoop"),(2,"hdfs"),(3,"MR"),(4,"hive"))
    val data=sc.parallelize(list,2)

    val ret=data.mapPartitionsWithIndex((partitionIndex,iter) =>{
      val list = ListBuffer[String]()
      while(iter.hasNext){
        list +=("repartitionIndex : "+partitionIndex+",value :"+iter.next())
      }
      list.iterator
    })
    ret.foreach(println)

    // 替换repartition组合sortBy
    data.zipWithIndex().repartitionAndSortWithinPartitions(new HashPartitioner(1)).foreach(println)

    /**
      * 运行结果： 后面的是分区索引
      * ((1,hadoop),0)
      * ((2,hdfs),1)
      * ((3,MR),2)
      * ((4,hive),3)
      */
  }

}
