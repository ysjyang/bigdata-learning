package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * distinct 算子  uv统计 分析每天有多少用户访问网站
  * 而不是访问多少次。
  * Note: distinct:没有参数，将RDD里的元素进行去重操作。
  * @author yangshaojun
  * #date  2019/3/2 18:12
  * @version 1.0
  */
object Transform_008_distinct {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("distinct").setMaster("local[2]")

    val sc = new SparkContext(conf)

    val linerdds = sc.textFile("data/basicdata/distinct.txt")
    val wordRDD=linerdds.flatMap(line => line.split(","))
    val result = wordRDD.distinct()
    result.foreach(println)

  }

}

