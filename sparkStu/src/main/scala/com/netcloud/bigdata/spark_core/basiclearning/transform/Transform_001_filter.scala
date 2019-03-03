package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * filter()算子
  * 参数是函数，函数会过滤掉不符合条件的行元素，返回值是新的RDD。
  * @author yangshaojun
  * #date  2019/3/1 21:42
  * @version 1.0
  */
object Transform_001_filter {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("use filter").setMaster("local[2]")

    val sc = new SparkContext(conf)

    val linesRDD = sc.textFile("data/basicdata/bankfull.txt")
    val newlineRDD = linesRDD.filter(line => line.contains("married"))
    println("result total line :"+ newlineRDD.count())
    println("-----------------遍历过滤后的数据--------------")
    newlineRDD.foreach(println)//遍历过滤后的数据
  }

}
