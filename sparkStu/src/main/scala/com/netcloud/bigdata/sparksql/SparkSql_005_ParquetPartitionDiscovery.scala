package com.netcloud.bigdata.sparksql

import org.apache.spark.sql.SparkSession

/**
  * parquet分区发现
  *
  * @author yangshaojun
  * #date  2019/4/8 13:15
  * @version 1.0
  */
object SparkSql_005_ParquetPartitionDiscovery {
  def main(args: Array[String]): Unit = {
    val  spark=SparkSession
      .builder()
      .appName("SparkSql_005_ParquetPartitionDiscovery")
      .master("local")
      .getOrCreate()
    // 读取parquet文件 创建一个DataFrame
    val parquetDF=spark.read.parquet("hdfs://netcloud01:9000/data/sparksql/users/gender=man/country=US/users.parquet")
    parquetDF.printSchema()
    parquetDF.show()
  }
}
