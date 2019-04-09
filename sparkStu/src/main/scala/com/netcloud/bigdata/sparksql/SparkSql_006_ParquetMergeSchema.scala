package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * parquet 数据源
  * @author yangshaojun
  * #date  2019/4/8 13:29
  * @version 1.0
  */
object SparkSql_006_ParquetMergeSchema {
  def main(args: Array[String]): Unit = {
    val  spark=SparkSession
      .builder()
      .appName("SparkSql_006_ParquetMergeSchema")
      .master("local")
      .getOrCreate()
    import spark.implicits._
    val basicData=Seq(("Andy",25),("Tome",26),("Jack",27))
    // 创建一个DataFrame 作为学生的基本信息 并写入到 parquet文件中
    val basicDF=spark.sparkContext.parallelize(basicData,2).toDF("name","age")
    basicDF.write.format("parquet").save("data/spark/sparksql/students")

    // 创建第二个DataFrame 作为学生的成绩信息 并写入到 同一个parquet文件中
    val scoreData=Seq(("ZhangSan",100),("Lisi",99),("Andy",150))
    val scoreDF=spark.sparkContext.parallelize(scoreData,2).toDF("name","score")
    scoreDF.write.mode(SaveMode.Append).format("parquet").save("data/spark/sparksql/students")

    // 首先，第一个DataFrame和第二个DataFrame的元数据肯定是不一样的
    // 一个是包含了 name和age两个列，一个包含了name 和score两个列
    // 所以这里期望的是，读取出来表的数据，自动合并两个文件的元数据，出现三个列 name 、age 、score
    // 用merageSchema的方式，读取数据进行元数据的合并。
    val mergeData= spark.sqlContext.read.option("mergeSchema","true").parquet("data/spark/sparksql/students")
    mergeData.printSchema()
    mergeData.show()
    /**
      * +--------+-----+----+
      * |    name|score| age|
      * +--------+-----+----+
      * |ZhangSan|  100|null|
      * |    Lisi|   99|null|
      * |    Andy|  150|null|
      * |    Tome| null|  26|
      * |    Jack| null|  27|
      * |    Andy| null|  25|
      * +--------+-----+----+
      */
  }
}
