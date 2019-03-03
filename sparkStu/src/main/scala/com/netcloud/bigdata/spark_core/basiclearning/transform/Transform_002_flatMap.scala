package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/** flatMap() 算子
  * 是map的一种扩展。
  * 在flatMap中，我们会传入一个函数，该函数对每个输入都会返回一个数组或者元组、集合,字符串的输出（而不是一个元素），
  * 然后，flatMap把生成的多个集合（数组）“拍扁”成为一个集合(数组)或者字符串RDD。
  *
  * Note:下面是一个wordCount例子
  * @author yangshaojun
  * #date  2019/3/1 22:10
  * @version 1.0
  */
object Transform_002_flatMap {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("use flatMap").setMaster("local[2]")

    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile("data/basicdata/flatmap-testdata.txt")
    //将读取的每行数据按逗号分割，然后返回字符串数组，有多少行数据就会返回多少字符串数组，最后将所有的字符串数组进行扁平化为一个字符串RDD。
    val newlineRDD=linesRDD.flatMap(line => line.split(","))
    //遍历每个单词 然后记为1  返回的是K、V格式的元组信息
    val word=newlineRDD.map(word => (word,1))//将读取的每个单词记为1
    //将相同key的元组 进行相加
    val resultRDD= word.reduceByKey((v1:Int,v2:Int) => v1+v2)//使用reduceByKey 将相同的Key进行累计
    resultRDD.foreach(println)
  }

}
