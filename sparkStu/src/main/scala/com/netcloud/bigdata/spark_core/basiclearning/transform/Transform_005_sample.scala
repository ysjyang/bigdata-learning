package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * sample 算子 做随机抽样
  *
  * @author yangshaojun
  * #date  2019/3/2 17:01
  * @version 1.0
  */
object Transform_005_sample {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("sample").setMaster("local[2]")

    val sc = new SparkContext(conf)
    /**
      * 随机抽样算子，根据传进去的小数按比例进行又放回或者无放回的抽样。
      * 第一个参数：如果为true代表有放回的抽样，false代表无放回的抽样
      * 第二个参数代表抽样的比例 0.1或者0.9 从RDD中抽取 10%或者90%的数据。
      * 第三个参数代表种子：如果对同一批数据相同种子随机抽样，那么收到的结果相同。
      * 也就是说如果前两个参数不变的情况下，设置种子值固定 那么随机抽样出的数据相同
      */
    val linesRDD = sc.textFile("data/basicdata/sample.txt")
    val resultRDD = linesRDD.sample(true, 0.5, 1)
    resultRDD.foreach(println)
  }
}
