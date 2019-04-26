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
    val arr = Array("user1 2019-01-02 23:12:23",
      "user1 2019-01-02 23:12:23",
      "user1 2019-01-02 23:12:23",
      "user2 2019-01-02 23:12:23",
      "user1 2019-01-02 23:12:23",
      "user3 2019-01-02 23:12:23",
      "user2 2019-01-02 23:12:23")

    val linerdds = sc.parallelize(arr,2)
    val result = linerdds.distinct()
    result.foreach(println)

  }

}

