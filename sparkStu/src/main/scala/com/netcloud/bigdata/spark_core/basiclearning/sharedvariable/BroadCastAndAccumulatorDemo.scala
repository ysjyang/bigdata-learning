package com.netcloud.bigdata.spark_core.basiclearning.sharedvariable

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 共享变量的广播变量 和累加变量
  *
  * @author yangshaojun
  * #date  2019/3/6 10:08
  * @version 1.0
  */
object BroadCastAndAccumulatorDemo {
  def main(args: Array[String]): Unit = {

    val conf=new SparkConf().setAppName("broadcast").setMaster("local[2]")
    val sc=new SparkContext(conf)
//    broadcast(sc)
    accumulator(sc)
  }

  /**
    * 广播变量
    * @param sc
    */
  def broadcast(sc:SparkContext): Unit ={
    val linesRDD=sc.textFile("data/basicdata/broadcast.txt")
    val list=List("spark")
    val broadcast=sc.broadcast(list)
    val retRDD=linesRDD.map(line  => broadcast.value.contains(line))
    retRDD.foreach(println)

  }
  /**
    * 累加器
    * @param sc
    */
  def accumulator(sc:SparkContext): Unit ={
    val linesRDD=sc.parallelize(1 to 5)
    val accumulator=sc.accumulator(0)
    linesRDD.foreach(num => accumulator.add(num))
    println(accumulator.value)

  }

}
