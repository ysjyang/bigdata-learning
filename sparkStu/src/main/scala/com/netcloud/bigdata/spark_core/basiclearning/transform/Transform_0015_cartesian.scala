package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * cartesian 算子
  *
  * Note:参数是RDD，求两个RDD的笛卡儿积。
  *      当调用类型T和U的数据集时，返回(T, U)对(所有元素对)的数据集。
  *
  * @author yangshaojun
  * #date  2019/3/3 1:06
  * @version 1.0
  */
object Transform_0015_cartesian {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("cartesian").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val flow = sc.parallelize(List(("String1",1),("String2",2),("String3",3)));
    val org = sc.parallelize(List(("String3",3),("String4",1),("String1",2)));
    val cartesian=flow.cartesian(org)
    cartesian.foreach(println)

    /**
      * 运行结果:
      * ((String1,1),(String3,3))
      * ((String1,1),(String4,1))
      * ((String1,1),(String1,2))
      * ((String2,2),(String3,3))
      * ((String2,2),(String4,1))
      * ((String2,2),(String1,2))
      * ((String3,3),(String3,3))
      * ((String3,3),(String4,1))
      * ((String3,3),(String1,2))
      *
      */
  }

}
