package com.netcloud.spark.sparkcore.projectpractice;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.List;

/**
 * 对文本文件中的数字，获取最大的前三个
 *
 * @author yangshaojun
 * #date  2019/3/15 16:38
 * @version 1.0
 */
public class Demo_002_TopThree {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local").setAppName("Demo_001_SparkSecondarySort");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lineRDD = sc.textFile("data/sparkcore/top3.txt");
        //将读取的RDD<String>类型 映射为RDD<Integer,String>
        JavaPairRDD<Integer, String> integerRDD = lineRDD.mapToPair(new PairFunction<String, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(String s) throws Exception {
                return new Tuple2<Integer, String>(Integer.valueOf(s), s);
            }
        });
        //降序排序
        JavaPairRDD<Integer, String> sortNumberRDD = integerRDD.sortByKey(false);
        //将 RDD<Integer,String> 映射为 RDD<String>类型
        JavaRDD<String> retRDD = sortNumberRDD.map(new Function<Tuple2<Integer, String>, String>() {

            @Override
            public String call(Tuple2<Integer, String> v1) throws Exception {
                return v1._2;
            }
        });
        //获取前三条数据
        List<String> beforeThree = retRDD.take(3);
        //遍历打印输出结果
        for (String ret : beforeThree) {
            System.out.println(ret);
        }
        sc.stop();

    }
}
