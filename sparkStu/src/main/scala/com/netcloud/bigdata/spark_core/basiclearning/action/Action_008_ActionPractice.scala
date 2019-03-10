package com.netcloud.bigdata.spark_core.basiclearning.action

import org.apache.spark.{SparkConf, SparkContext}

/**
  * reduce:将RDD中的每个元素按照指定的逻辑进行聚合
  * collect：将远程集群所有RDD的元素返回给Driver端 这种方式不建议使用(数据量太大造成内存溢出)
  *          通常还是建议使用 foreach action操作将rdd进行处理
  * count:统计RDD中元素的个数
  * take:从远程集群中获取RDD前n条数据
  * saveAsTextFile：将RDD元素保存到本地文件或者hdfs中
  * countByKey：统计相同key 出现的次数
  * forearach：在远程集群中遍历元素
  * @author yangshaojun
  * #date  2019/3/10 11:48
  * @version 1.0
  */
object Action_008_ActionPractice {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("ActionPractice")
    val sc = new SparkContext(conf)
    //    reduce(sc)
    //    collect(sc)
    //    count(sc)
    take(sc)
    sc.stop()
  }

  def reduce(sc: SparkContext): Unit = {
    val c = sc.parallelize(1 to 10)
    val result = c.reduce((x, y) => {
      x + y
    }) //结果55
    println(result)
  }

  def collect(sc: SparkContext): Unit = {
    val c = sc.parallelize(1 to 10)
    val result = c.collect()
    for (ret <- result) {
      println(ret)
    }
  }

  def count(sc: SparkContext): Unit = {
    val c = sc.parallelize(1 to 10)
    val result = c.count()
    println(result)

  }

  def take(sc: SparkContext): Unit = {
    val c = sc.parallelize(1 to 10)
    val result = c.take(2)
    println(result)
  }

  def saveAsTextFile(sc: SparkContext): Unit = {
    val c = sc.parallelize(1 to 10)
    c.saveAsTextFile("data/result")
  }
}
