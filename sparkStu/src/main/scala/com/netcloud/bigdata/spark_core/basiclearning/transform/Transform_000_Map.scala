package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Map函数：
  *在Map中，我们会传入一个func，该函数对每个输入经过func处理后都会返回一个输出。是1:1的关系
  * 如:map函数每次输入一行，经过func处理后，输出处理后的一行。 然后将最后的结果赋值给新的RDD
  *
  * Note:RDD在没有遇到行动算子或者没有将RDD持久化的时候，RDD之间只是一种逻辑关系，并不存储数据。*
  * @author yangshaojun
  * #date  2019/2/28 16:41
  * @version 1.0
  */
object Transform_000_Map {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Map's using").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("data/basicdata/data.txt")//将加载的数据的所有行为RDD
    val newline = lines.map(line => line + "," + "end") //对读取的文件每一行（line）进行处理；每行后面加 上end
    println("total line:"+ newline.count())//统计一共多少行
    println("前两行的数据:"+newline.collect().take(2))//获取前两行的数据
    newline.foreach(println)//打印出每行的数据


  }

}