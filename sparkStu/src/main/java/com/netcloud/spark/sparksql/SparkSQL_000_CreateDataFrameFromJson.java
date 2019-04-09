package com.netcloud.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

/**
 * @author yangshaojun
 * #date  2019/4/9 15:58
 * @version 1.0
 */
public class SparkSQL_000_CreateDataFrameFromJson {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf()
                .setAppName("SparkSQL_000_CreateDataFrameFromJson")
                .setMaster("local");
        SparkContext sc=new SparkContext(conf);
        SQLContext sqlContext=new SQLContext(sc);
        Dataset<Row> json = sqlContext.read().json("data/sparksql/people.json");
        json.show();
    }
}
