package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
/**
 * groupBy(function) 
 * function返回key，传入的RDD的各个元素根据这个key进行分组
 * 
 */
object Operator_groupBy {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf();
    conf.setMaster("local").setAppName("Operator_groupBy")
    val sc=new SparkContext(conf);
    val a = sc.parallelize(1 to 9, 3)
    val result=a.groupBy(x => { if (x % 2 == 0) "even" else "odd" })//分成两组
    result.foreach(println)
    /*
     * 输出结果
     * (even,CompactBuffer(2, 4, 6, 8))
     * (odd,CompactBuffer(1, 3, 5, 7, 9))
     */
    sc.stop();
  }
}