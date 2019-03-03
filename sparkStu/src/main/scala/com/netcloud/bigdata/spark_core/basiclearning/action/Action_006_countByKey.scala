package com.netcloud.bigdata.spark_core.basiclearning.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * countByKey 算子
  *
  * @author yangshaojun
  * #date  2019/3/3 11:59
  * @version 1.0
  */
object Action_006_countByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("countByKey")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(("a",100),("b",200), ("a",300), ("c",400)))
    val result2 = rdd1.countByKey()
    result2.foreach(println)
  }
}
