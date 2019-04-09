package com.netcloud.bigdata.ml.example

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.{Row, SparkSession}

/**
  * An example for computing correlation matrix
  * 一个计算矩阵相关性的示例
  *
  * @author yangshaojun
  * #date  2019/3/25 16:37
  * @version 1.0
  */
object CorrelationExample {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local")
      .appName("CorrelationExample")
      .getOrCreate()

    import spark.implicits._
    // 创建一个有序重复的集合 集合中存放的数据类型是向量。
    // Vector.sparse()创建一个稀疏向量；第一个参数是向量的大小；第二个参数是一个集合对象(集合中存储一个二元组)
    // Vectors.dense()创建一个稠密向量。
    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),// 等同于 (3.0,-2.0,0.0)
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )
    // map方法传入Tuple1.apply 将向量转为一元元组 然后通过隐式转换将集合数据转为DataFrame
   val df= data.map(Tuple1.apply).toDF("features")
    // 计算两两特征之间相关性 对角线值都为1.0
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println("Pearson correlation matrix:\n" + coeff1.toString)

    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println("Spearman correlation matrix:\n" + coeff2.toString)

    spark.stop()
  }
}
