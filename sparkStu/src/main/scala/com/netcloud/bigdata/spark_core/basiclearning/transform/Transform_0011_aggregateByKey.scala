package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
  *aggregateByKey算子
  * Note:
  * 对PairRDD中相同的Key值进行聚合操作，在聚合过程中同样使用了一个中立的初始值。
  * 和aggregate函数类似，aggregateByKey返回值的类型不需要和RDD中value的类型一致。
  * 因为aggregateByKey是对相同Key中的值进行聚合操作，所以aggregateByKey'函数最终返回的类型还是PairRDD，
  * 对应的结果是Key和聚合后的值，而aggregate函数直接返回的是非RDD的结果。
  * @author yangshaojun
  * #date  2019/3/2 22:50
  * @version 1.0
  */
object Transform_0011_aggregateByKey {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("aggregateByKey").setMaster("local[2]")
    val sc = new SparkContext(conf)
    /**
      * 示例一:
      */
    val pairRdd = sc.parallelize(List(("cat", 2), ("cat", 5), ("mouse", 4), ("cat", 12), ("dog", 12), ("mouse", 2)), 2)
    pairRdd.aggregateByKey(100)(math.max(_, _), _ + _).foreach(println)

    /** 返回结果
      * (dog,100)
      * (mouse,200)
      * (cat,200)
      */

    /**
      * 上述结果的执行顺序
      * 1) 第一步：将每个分区内key相同数据放到一起
      *
      * 分区一
      * ("cat",(2,5)),("mouse",4)
      *
      * 分区二
      * ("cat",12),("dog",12),("mouse",2)
      *
      * 2) 第二步：局部求最大值
      * 对每个分区应用传入的第一个函数，math.max(_ , _)，这个函数的功能是求每个分区中每个key的最大值
      * 这个时候要特别注意，aggregateByKe(100)(math.max(_ , _),_+_)里面的那个100，其实是个初始值
      * 在分区一中求最大值的时候,100会被加到每个key的值中，这个时候每个分区就会变成下面的样子
      * 分区一
      * ("cat",(2,5，100)),("mouse",(4，100))
      *
      * 然后求最大值后变成：
      * ("cat",100), ("mouse",100)
      *
      * 分区二
      * ("cat",(12,100)),("dog",(12.100)),("mouse",(2,100))
      *
      * 求最大值后变成：
      * ("cat",100),("dog",100),("mouse",100)
      *
      * 3) 第三步：整体聚合
      * 将上一步的结果进一步的合成，这个时候100不会再参与进来
      *
      * 最后结果就是：
      * (dog,100),(cat,200),(mouse,200)
      *
      */


    /**
      * 示例二：
      *
      */

    val data = List((1, 3), (1, 2), (1, 4), (2, 3))
    val rdd = sc.parallelize(data, 2)
    //合并在同一个partition中的值，a的数据类型为zeroValue的数据类型，b的数据类型为原value的数据类型
    def seqOp(a: String, b: Int): String = {
      println("SeqOp:" + a + "\t" + b)
      a + b
    }

    //合并不同partition中的值，a，b得数据类型为zeroValue的数据类型
    def combOp(a: String, b: String): String = {
      println("comOp:" + a + "\t" + b)
      a + b
    }
    val aggregateByKeyRDD = rdd.aggregateByKey("100")(seqOp, combOp)
    aggregateByKeyRDD.foreach(println)

    /**
      * 运行结果:
      * 将数据拆分成两个分区
      * //分区一数据
      * (1,3)
      * (1,2)
      * //分区二数据
      * (1,4)
      * (2,3)
      * //分区一相同key的数据进行合并
      * seq: 100     3   //(1,3)开始和中立值进行合并  合并结果为 1003
      * seq: 1003     2   //(1,2)再次合并 结果为 10032
      * //分区二相同key的数据进行合并
      * seq: 100     4  //(1,4) 开始和中立值进行合并 1004
      * seq: 100     3  //(2,3) 开始和中立值进行合并 1003
      *
      * 将两个分区的结果进行合并
      * //key为2的，只在一个分区存在，不需要合并 (2,1003)
      * (2,1003)
      *
      * //key为1的, 在两个分区存在，并且数据类型一致，合并
      * comb: 10032     1004
      * (1,100321004)
      *
      */

  }

}
