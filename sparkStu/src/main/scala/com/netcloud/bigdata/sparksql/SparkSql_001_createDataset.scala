package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.SparkSession

/**
  * DataSet的创建
  * @author yangshaojun
  * #date  2019/3/8 17:21
  * @version 1.0
  */
object SparkSql_001_createDataset {
  case class Person(name: String, age: Long)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[2]")
      .getOrCreate()
    import  spark.implicits._

    val path="data/sparksql/people.json"
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()
  }
}
