package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * intersection算子
  * Note：求出两个RDD共同的元素
  * @author yangshaojun
  * #date  2019/3/3 1:01
  * @version 1.0
  */
object Transform_007_intersection {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("union").setMaster("local[2]")

    val sc = new SparkContext(conf)

    val rdd1=sc.textFile("data/basicdata/sample.txt")
    val rdd2=sc.textFile("data/basicdata/union.txt")
    val result=rdd1.intersection(rdd2)
    result.foreach(println)

  }
}
