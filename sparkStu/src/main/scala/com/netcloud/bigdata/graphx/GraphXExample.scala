package com.netcloud.bigdata.graphx

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.rdd.RDD
import org.apache.log4j.{Level, Logger}

/**
  * @author yangshaojun
  * 2018/12/17 10:21
  * @version 1.0
  */
object GraphXExample {
  def main(args: Array[String]): Unit = {
    //屏蔽日志
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val conf = new SparkConf()
    conf.setMaster("local").setAppName("GraphXExample")
    val sc = new SparkContext(conf)
    //设置顶点和边，注意顶点和边都是用元组定义的 Array
    //顶点的数据类型是 VD:(String,Int)
    val vertexArray = Array(
      (1L, ("Alice", 28)),
      (2L, ("Bob", 27)),
      (3L, ("Charlie", 65)),
      (4L, ("David", 42)),
      (5L, ("Ed", 55)),
      (6L, ("Fran", 50))
    )
    //边的数据类型 ED:Int
    val edgeArray = Array(
      Edge(2L, 1L, 7),
      Edge(2L, 4L, 2),
      Edge(3L, 2L, 4),
      Edge(3L, 6L, 3),
      Edge(4L, 1L, 1),
      Edge(5L, 2L, 2),
      Edge(5L, 3L, 8),
      Edge(5L, 6L, 3))

    //构造 vertexRDD 和 edgeRDD
    val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(vertexArray)
    val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edgeArray)
    //构造图Graph[VD,ED]
    val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
    println("***********************************************")
    println("属性演示")
    println("**********************************************************")
    println("将顶点和边数据关联")
    //triplets 操作，((srcId, srcAttr), (dstId, dstAttr), attr)
    graph.triplets.foreach(t => println(s"triplet:${t.srcId},${t.srcAttr},${t.dstId},${t.dstAttr},${t.attr}"))
    println("======遍历顶点数据======")
    graph.vertices.collect().foreach (v => println(s"${v._1} ${v._2._1},${v._2._2}"))
    println("======找出图中年龄大于 30 的顶点======")
    graph.vertices.filter { case (id, (name, age)) => age > 30 }.collect.foreach {
      case (id, (name, age)) => println(s"$name is $age")
    }
    println("======遍历边数据信息======")
    graph.edges.collect().foreach(e => println(s"${e.srcId} to ${e.dstId} attr ${e.attr}"))

    println("======找出图中属性大于5的边======")
    graph.edges.filter(e => e.attr>5).collect().foreach(e => println(s"${e.srcId} to ${e.dstId} attr ${e.attr}"))

    println("======列出边属性>5 的 tripltes======")
    for (triplet <- graph.triplets.filter(t => t.attr > 5).collect) {
      println(s"${triplet.srcAttr._1} likes ${triplet.dstAttr._1}")
    }

  }
}

