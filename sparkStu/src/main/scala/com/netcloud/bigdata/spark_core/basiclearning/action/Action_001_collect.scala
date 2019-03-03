package com.netcloud.bigdata.spark_core.basiclearning.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * collect 算子
  * 在驱动程序中以数组的形式返回数据集的所有元素。
  * 这通常在filter或返回足够小的数据子集的其他操作之后有用。
  * 返回RDD所有元素,将计算结果回收到Driver端(适合数据量比较小的情形)。
  * @author yangshaojun
  * #date  2019/3/3 11:32
  * @version 1.0
  */
object Action_001_collect {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("collect")
    val sc = new SparkContext(conf)


  }
}
