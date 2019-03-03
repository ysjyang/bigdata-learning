package com.netcloud.bigdata.spark_core.basiclearning.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * takeSample(withReplacement, num, [seed]) 算子
  * 1) 从RDD里返回任意一些元素;到底返回多少由第二个参数指定。
  * 2) 参数一:withReplacement 是否放回
  *    参数二:num 返回多少个数据元素
  *    参数三：seed 每次进行获取数据时候，种子不变，那么得到的结果不变。
  *
  * @author yangshaojun
  * #date  2019/3/3 11:40
  * @version 1.0
  */
object Action_005_takeSample {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("collect")
    val sc = new SparkContext(conf)
   val lines= sc.textFile("data/basicdata/sample.txt")
   println(lines.takeSample(true,2).mkString(","))

  }

}
