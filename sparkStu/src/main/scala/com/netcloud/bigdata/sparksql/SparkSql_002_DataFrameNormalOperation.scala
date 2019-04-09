package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.SparkSession

/**
  * scala版本的DataFrame的常用操作。
  * 通常将DataFrame 存储在一张临时表中，后面通过执行sql的方式执行操作。
  * @author yangshaojun
  * #date  2019/4/3 9:29
  * @version 1.0
  */
object SparkSql_002_DataFrameNormalOperation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_003_DataFrameNormalOperation")
      .getOrCreate()

    import spark.implicits._

    // 等同于 SQLContext对象 sc.read().json("../.json")以及 sc.read().load(../.json)
    val df = spark.read.json("data/sparksql/people.json")

    // 1、打印DataFrame数据信息
    df.show()

    // 2、打印schema信息
    df.printSchema()

    // 3、打印某列信息
    df.select("name")

    // 4、查询多列信息；并且对列使用表达式操作 (df.col(columnName))
    df.select(df.col("name"), df.col("age") + 1, df.col("age").plus(2)).show()
    df.select($"name",$"age".plus(3),$"age"+4).show() // $columnName 的语法需要引入spark.implicits._

    // 5、过滤出年龄大于25的people
    df.filter($"age".gt(25)).show()

    // 6、跟根据某个字段分组 并统计总数
    df.groupBy("age").count().show()

    // 将DataFrame注册到临时表
    df.registerTempTable("people")
    // sparksession对象调用sql方法 方法参数书写sql表达式
    spark.sql("select * from people").show()

    // 将DataFrame注册全局表
    df.createOrReplaceGlobalTempView("people")
    // sparksession对象调用sql方法 方法参数书写sql表达式
    // 全局临时视图绑定到系统保留的数据库`global_temp`
    spark.sql("select * from global_temp.people").show()

  }
}
