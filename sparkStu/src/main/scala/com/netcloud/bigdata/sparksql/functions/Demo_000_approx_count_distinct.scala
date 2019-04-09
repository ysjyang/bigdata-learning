package com.netcloud.bigdata.sparksql.functions

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Column, functions}

/**
  * 去重计数函数
  *
  * @author yangshaojun
  * #date  2019/3/11 9:50
  * @version 1.0
  */
object Demo_000_approx_count_distinct {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("approx_count_distinct")
      .getOrCreate()

    val df=spark.read.option("header",true).format("csv").load("data/sparksql/bank-full2.csv")
    df.createOrReplaceTempView("bank")
//    spark.sql("select job,collect_list(`balance`) as balance_covar_samp from bank group by job").show(5)
    import spark.implicits._
//   val df2= df.selectExpr("round(balance)")
    spark.sql("select df.col(balance).cast(DoubleType) from bank").show()


  }

}
