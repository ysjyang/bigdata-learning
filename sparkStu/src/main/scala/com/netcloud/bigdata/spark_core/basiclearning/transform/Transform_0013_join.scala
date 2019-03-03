package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * join算子
  * Note：
  * 当调用类型为(K, V)和(K, W)的数据集时，返回一个(K， (V, W))对的数据集，
  * 每个键对应的所有元素对。外部连接通过左侧连接、右侧连接和fullOuterJoin得到支持。
  * join：等值连接
  * @author yangshaojun
  * #date  2019/3/3 0:35
  * @version 1.0
  */
object Transform_0013_join {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("join").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data1 = List((1, "hadoop"),  (2, "spark"), (3, "elasticearch"))
    val data2 = List((1, "hive"), (2, "sparksql"))

    val rdd1 = sc.parallelize(data1)
    val rdd2 = sc.parallelize(data2)
    rdd1.join(rdd2).foreach(println)//等值连接

    /** 运行结果
      * (2,(spark,sparksql))
      * (1,(hadoop,hive))
      */
    println("======fullouterjoin======")
    rdd1.fullOuterJoin(rdd2).foreach(println)

  }

}
