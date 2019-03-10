package com.netcloud.spark.sparkcore.persist;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

/**
 * RDD的持久化
 * cache或者persist的使用是有规则的
 * 必须在transform或者textFile等创建完一个RDD后直接调用cache或者persist才可以
 * 如果先创建一个RDD，然后单独另起一行执行cache或者persist是没有用的，而且还会报错大量的文件会丢失
 * @author yangshaojun
 * #date  2019/3/10 13:19
 * @version 1.0
 */
public class Persist {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Persist").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines= sc.textFile("data/sparkcore/wordcount.txt").cache();
//        JavaRDD<String> lines= sc.textFile("data/sparkcore/wordcount.txt").persist(StorageLevel.MEMORY_ONLY());
        long beginTime=System.currentTimeMillis();
        long total=lines.count();
        System.out.println(total);
        long endTime=System.currentTimeMillis();
        System.out.println(endTime-beginTime);

        beginTime=System.currentTimeMillis();
        total=lines.count();
        endTime=System.currentTimeMillis();
        System.out.println(endTime-beginTime);
    }
}
