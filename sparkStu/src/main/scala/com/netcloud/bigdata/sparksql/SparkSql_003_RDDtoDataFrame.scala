package com.netcloud.bigdata.sparksql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/** scala版本的方式将RDD转为DataFrame
  * Spark SQL 支持两种不同的方法用于转换已存在的 RDD 成为 Dataset
  * 1）java 反射
  *  a)定义一个样例类；样例类定义了表的 Schema ；Case class 的参数名使用反射读取并且成为了列名
  *  b)使用toDF函数将RDD转为DataFrame
  * 2）以编程的方式指定 Schema
  *  a) 将从文本文件中创建的原始RDD使用map函数转为ROW类型的RDD。
  *  b) Step 1 被创建后，创建 Schema 表示一个 StructType(StructField(name,StringType,true),...) 匹配 RDD 中的 Rows（行）的结构。
  *  c) 通过 SparkSession 提供的 createDataFrame 方法应用 Schema 到 RDD 的 RowS（行）。
  * @author yangshaojun
  * #date  2019/3/8 17:33
  * @version 1.0
  */
object SparkSql_003_RDDtoDataFrame {
  case class Person(name:String,age:Int)
    def main(args: Array[String]): Unit = {
      val spark = SparkSession
        .builder()
        .master("local")
        .appName("SparkSql_003_DataFrameNormalOperation")
        .getOrCreate()
      import  spark.implicits._

      val rdd = spark.sparkContext.textFile("data/sparksql/people.txt")
      val peopleDF = rdd.map(_.split(",")).map(ar => Person(ar(0), ar(1).trim.toInt)).toDF()
      // 将DataFrame注册为一个view
      peopleDF.createOrReplaceTempView("people")
      val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
      teenagersDF.map(teenager => "Name: " + teenager(0)).show()
      // 获取某个列的值
      teenagersDF.map(teenager => "Name: " + teenager.getAs("name")).show()
      /**
        * 编码的方式将RDD转为DataFrame
        */
      val schemeString = "name age"
      // 1、使用StructType 创建schema信息
      val fields = schemeString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true))
      val schema = StructType(fields)

      // 2、将原始的RDD[String] 转为 RDD[Row]
      val rowRDD = rdd.map(_.split(",")).map(attributes => Row(attributes(0), attributes(1).trim))
      // 3、使用createDataFrame方法将RDD和schema关联来创建DataFrame。
      val peopleDF2 = spark.createDataFrame(rowRDD, schema)
      peopleDF2.show()

    }

}

