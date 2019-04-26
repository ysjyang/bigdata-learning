package com.netcloud.bigdata.sparksql


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.HiveContext

/**
  * @author yangshaojun
  * #date  2019/4/8 21:39
  * @version 1.0
  */
object SparkSql_011_RowNumberWindowFunction {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("SparkSql_011_RowNumberWindowFunction")
      .getOrCreate()

    val hiveContext = new HiveContext(spark.sparkContext);
    // 删除销售额表, sales表
    hiveContext.sql("DROP TABLE IF EXISTS sales")
    hiveContext.sql("CREATE TABLE IF NOT EXISTS sales (product String,category String,revenue BIGINT )")
    hiveContext.sql("LOAD DATA LOCAL INPAT '/usr/local/sales.txt INTO TABLE sales'")

    // 开始编写统计逻辑：使用 row_number() 开窗函数
    // row_number()函数的作用: 给每个分组的数据，按照其顺序排序，然后打上一个分组的行号
    hiveContext.sql("SELECT product,category,revenue" +
      "FROM ("
      + "SELECT "
      + "product,"
      + "category,"
      + "revenue,"
      + "row_number() OVER (PARTITION BY category ORDER BY revenue DESC) rank"
      + ")"
      + " WHERE rank<=3")


  }

}
