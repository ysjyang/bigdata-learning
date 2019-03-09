package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 准换算子实战
  * map：将集合中的所有元素乘以2
  * filter：过滤出集合中的所有的偶数
  * flatMap：将文本行拆分多个单词
  * groupByKey：将班级的成绩分组
  * @author yangshaojun
  * #date  2019/3/9 17:08
  * @version 1.0
  */
object Transform_0020_TransformPractice {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("TransFormationPractice").setMaster("local[2]")
    val sc = new SparkContext(conf)
//    map(sc)
//    filter(sc)
//    flatMap(sc)
    groupByKey(sc)
    sc.stop()

  }

  /**
    * map：将集合中的所有元素乘以2
    *
    * @param sc
    */
  def map(sc: SparkContext): Unit = {
    val numlist = List(1, 2, 3, 4, 5);
    val numberRDD = sc.parallelize(numlist)
    /**
      * 使用map算子;将集合中的每个元素都乘以2
      */
    val multipleNumberRDD = numberRDD.map(_ * 2)
    multipleNumberRDD.foreach(println)
  }

  /**
    * filter：过滤出集合中的所有的偶数
    *
    * @param sc
    */
  def filter(sc: SparkContext): Unit = {
    val numlist = List(1, 2, 3, 4, 5,6,7,8,9,10);
    val numberRDD = sc.parallelize(numlist)
    /**
      * 使用map算子;将集合中的每个元素都乘以2
      */
    val multipleNumberRDD = numberRDD.filter(_ %2==0)
    multipleNumberRDD.foreach(println)
  }

  /**
    * flatMap：将文本行拆分多个单词
    */
  def flatMap(sc: SparkContext): Unit ={
    val numlist = List("hello you","hello me","hello world");
    val numberRDD = sc.parallelize(numlist)
    /**
      * 使用map算子;将集合中的每个元素都乘以2
      */
    val multipleNumberRDD = numberRDD.flatMap(_.split(" "))
    multipleNumberRDD.foreach(println)
  }

  /**
    * groupByKey：按照班级对成绩分组
    */
  def groupByKey(sc: SparkContext): Unit ={
    val numlist = List(Tuple2("class1",90),Tuple2("class1",92),Tuple2("class2",50),Tuple2("class2",60));
    val numberRDD = sc.parallelize(numlist)
    /**
      * 使用map算子;将集合中的每个元素都乘以2
      */
    val multipleNumberRDD = numberRDD.groupByKey()
    multipleNumberRDD.foreach(println)
  }
}
