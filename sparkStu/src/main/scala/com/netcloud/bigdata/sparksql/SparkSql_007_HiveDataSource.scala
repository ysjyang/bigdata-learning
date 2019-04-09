package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.hive.HiveContext

/**
  * Hive 数据源
  * @author yangshaojun
  * #date  2019/4/8 15:01
  * @version 1.0
  */
object SparkSql_007_HiveDataSource {
  def main(args: Array[String]): Unit = {
    val  spark=SparkSession
      .builder()
      .appName("SparkSql_007_HiveDataSource")
      .getOrCreate()
    // 通过sparkContext 创建 HiveContext对象
    val hiveContext=new HiveContext(spark.sparkContext)

    // 1、第一个功能:使用hiveContext 的sql或者hql方法；可以执行hive中能够执行的hqlQL语句
    // 判断是否存在students_info 表，如果存在就删除
    hiveContext.sql("DROP TABLE IF EXISTS  students_info")
    // 如果不存在，则创建这张表
    hiveContext.sql("CREATE TABLE IF NOT EXISTS  students_info (name STRING,age INT)")
    // 将学生基本信息导入 students_info 表中
    hiveContext.sql("LOAD DATA LOCAL INPATH '/data/sparksql/students.txt' INTO TABLE students_info")

    // 用同样的方式给 scores 导入数据
    hiveContext.sql("DROP TABLE IF EXISTS  students_score")

    hiveContext.sql("CREATE TABLE IF NOT EXISTS  students_score (name STRING,score INT)")
    // 将分数信息导入 students_score 表中
    hiveContext.sql("LOAD DATA LOCAL INPATH '/data/sparksql/scores.txt' INTO TABLE students_score")

    // 2、 第二个功能 执行sql返回一个DataFrame 用于查询。
    // 执行sql查询 关联两张表。查询出学生成绩大于80分的学生
    val goodStudentDF = hiveContext.sql("select t1.name;t1.age,t2.score  " + "from students_info t1 join " + "students_score t2 on t1.name=t2.name " + "where score > =80 ")

    // 3、第三个功能 将DataFrame中的数据保存到hive表中。
    // 将好学生的信息保存到 goodstudent_info表中。
    hiveContext.sql("DROP TABLE IF EXISTS  goodstudent_info")
    goodStudentDF.write.saveAsTable("goodstudent_info")

    //4、第四个功能  可以使用table方法 针对hive表 直接创建DataFrame
    // 然后针对goodstudent_info表直接创建 DataFrame
    val retDF=hiveContext.table("goodstudent_info");
    for( ret <- retDF){
      println(ret)
    }
  }
}
