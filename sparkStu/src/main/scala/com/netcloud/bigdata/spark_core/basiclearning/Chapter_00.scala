package com.netcloud.bigdata.spark_core.basiclearning

import org.apache.spark.{SparkConf, SparkContext}

/**
  * scala 闭包
  * @author yangshaojun
  * #date  2019/2/28 10:03
  * @version 1.0
  */
object Chapter_00 {
  def main(args: Array[String]): Unit = {

    val sparkConf=new SparkConf().setAppName("Create RDD from External File").setMaster("local[1]")
    val sc=new SparkContext(sparkConf)
    val linesRDD=sc.textFile("data/basicdata/*.txt")
    val lineLength=linesRDD.map(line => line.length)
//    println(lineLength)
    var count=0;
    lineLength.foreach(x => println(count+=x))
    println(count)

  }

}
