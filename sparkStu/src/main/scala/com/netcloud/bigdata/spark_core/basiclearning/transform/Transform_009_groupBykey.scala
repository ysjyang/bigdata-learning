package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * groupByKey算子
  *  K、V型的RDD 调用groupBykey方法后会根据key值进行分组
  * 1） 注意:如果您是为了在每个键上执行聚合(例如求和或平均值)而进行分组，
  * 那么使用reduceByKey或aggregateByKey将获得更好的性能。
  * 2） 注意:默认情况下，输出中的并行程度取决于父RDD的分区数量。
  * 您可以传递一个可选的numPartitions参数来设置不同数量的任务。
  * @author yangshaojun
  * #date  2019/3/2 18:39
  * @version 1.0
  */
object Transform_009_groupBykey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("groupByKey").setMaster("local[2]")

    val sc = new SparkContext(conf)
    val linerdds = sc.textFile("data/basicdata/distinct.txt")
    val wordsRDD=linerdds. flatMap(line => line.split(","))
    val tupleRDD=wordsRDD.map(word => (word,1))
    val resultRDD= tupleRDD.groupByKey()
    resultRDD.foreach(println)

    val rdd1 = sc.makeRDD(Array((1,"a"), (1,"b"), (2,"c"), (3,"d")))
    val result = rdd1.groupByKey()
    result.foreach(println)
    sc.stop()
  }

}
