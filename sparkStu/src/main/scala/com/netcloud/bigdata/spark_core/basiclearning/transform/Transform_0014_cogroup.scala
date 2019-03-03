package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * cogroup算子
  * Note:
  * 1) 当调用类型为(K, V)和(K, W)的数据集时，
  *    返回一个(K， (Iterable， Iterable)元组的数据集。这个操作也称为groupWith。
  * 2) cogroup：将多个RDD中同一个Key对应的Value组合到一起。最多可以组合四个RDD
  *    与reduceByKey不同的是针对两个RDD中相同的key的元素进行合并。
  *
  * @author yangshaojun
  * #date  2019/3/3 0:49
  * @version 1.0
  */
object Transform_0014_cogroup {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("CogroupOperator").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val nameList = List((1,"zhangsan"),(2,"lisi"),(3,"xiaofang"))
    val nameRdd = sc.parallelize(nameList)

    val sexList = List((1,"man"),(2,"man"),(3,"woman"))
    val sexRdd = sc.parallelize(sexList)

    val scoreList = List((1,90),(2,59),(3,100))
    val scoreRdd = sc.parallelize(scoreList)

    val gradeList = List((1,"三年级"),(2,"幼儿园"),(3,"五年级"))
    val gradeRdd = sc.parallelize(gradeList)

    val resultRdd = nameRdd.cogroup(sexRdd,scoreRdd,gradeRdd)
    resultRdd.foreach(println)

    sc.stop()
  }

}
