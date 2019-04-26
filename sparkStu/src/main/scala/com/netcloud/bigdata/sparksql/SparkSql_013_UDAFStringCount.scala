package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * 用户自定义聚合函数
  *
  * @author yangshaojun
  * #date  2019/4/9 17:21
  * @version 1.0
  */
object SparkSql_013_UDAFStringCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_012_UDF")
      .getOrCreate()
    // 构造模拟数据
    val arr=Array("Tom","Jerry","PeiQi","Andy","Tom","Andy","Tom")
    val strRDD=spark.sparkContext.parallelize(arr,2)
    // 将 RDD(String) 转为 RDD(Row)
    val rowRDD=strRDD.map(name => Row(name))

    // 动态转换的方式创建 schema 将RDD转为 DataFrame
    val schema=StructType(Array(StructField("name",StringType,true)))
    // 将RowRDD 转为DataFrame 然后将其注册到临时表中
    spark.createDataFrame(rowRDD,schema).registerTempTable("names")

    // 注册自定义聚合函数
    // 参数一：自定义聚合函数名称
    // 参数二： 继承UserDefinedAggregateFunction类的子类实例
    spark.udf.register("strCount",new StringCount)

    // 使用自定义函数
    spark.sql("select name,strCount(name)  from names group by name")
      .collect()
      .foreach(println)

    spark.stop()
  }

}

// 用户自定义聚合函数
class StringCount extends UserDefinedAggregateFunction {

  // inputSchema 指的是 输入数据的类型
  override def inputSchema: StructType = {
    StructType(Array(StructField("str", StringType, true)))
  }

  // bufferSchema 指的是 中间进行聚合时 所处理的数据类型
  override def bufferSchema: StructType = {
    StructType(Array(StructField("count", IntegerType, true)))
  }

  // dataType 指的是 函数返回值的数据类型
  override def dataType: DataType = {
    IntegerType
  }

  override def deterministic: Boolean = {
    true
  }

  // 为每个分组的数据进行初始化操作
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0
  }

  // 指的是 每个分组有新的值进来的时候，如何进行分组对应的聚合值的计算
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getAs[Int](0) + 1
  }

  // 由于spark是分布式的，所以一个分组的数据，可能在不同的节点上进行局部的聚合
  // 但是最后一个分组 在各个节点上的聚合值要进行Merage 也就是合并。
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[Int](0) + buffer2.getAs[Int](0)
  }

  // 最后 一个分组的聚合值，如何通过中间的缓存聚合值，最后返回一个最终的聚合值
  override def evaluate(buffer: Row): Any = {
    buffer.getAs[Int](0)
  }
}
