package com.netcloud.bigdata.spark_core.basiclearning.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Action算子
  * reduce(binary_function)
  * reduce将RDD中元素前两个传给输入函数，产生一个新的return值，
  * 新产生的return值与RDD中下一个元素（第三个元素）组成两个元素，再被传给输入函数，直到最后只有一个值为止。
  *
  * reduce与reduceByKey却别就是reduceByKey是 pair RDD（Key,value）
  * @author yangshaojun
  * #date  2019/3/3 11:22
  * @version 1.0
  */
object Action_000_reduce {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("reduceBy")
    val sc = new SparkContext(conf)
    val c = sc.parallelize(1 to 10)
    val result=c.reduce((x, y) =>{
      println(x + y)
      x + y} )//结果55
    /*
     * 具体过程，RDD有1 2 3 4 5 6 7 8 9 10个元素，
     * 1+2=3
     * 3+3=6
     * 6+4=10
     * 10+5=15
     * 15+6=21
     * 21+7=28
     * 28+8=36
     * 36+9=45
     * 45+10=55
     */
  }

}
