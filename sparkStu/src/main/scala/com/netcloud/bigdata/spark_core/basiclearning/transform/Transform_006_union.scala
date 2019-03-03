package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * union算子
  *
  * Note：是数据合并，返回一个新的数据集，由原数据集和other Dataset联合而成。
  * 即：把2个RDDs的元素合并起来。
  * @author yangshaojun
  * #date  2019/3/2 17:09
  * @version 1.0
  */
object Transform_006_union {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("union").setMaster("local[2]")

    val sc = new SparkContext(conf)

    val rdd1=sc.textFile("data/basicdata/sample.txt")
    val rdd2=sc.textFile("data/basicdata/union.txt")
    val unionresult=rdd1.union(rdd2)
    unionresult.foreach(println)

  }

}
