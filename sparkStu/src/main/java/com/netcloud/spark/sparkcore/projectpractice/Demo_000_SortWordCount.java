package com.netcloud.spark.sparkcore.projectpractice;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Demo_000_SortWordCount
 * 将单词统计的结果 按照统计单词数目进行降序排序
 * @author yangshaojun
 * #date  2019/3/13 16:37
 * @version 1.0
 */
public class Demo_000_SortWordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local").setAppName("Demo_000_SortWordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lineRDD = sc.textFile("data/sparkcore/wordcount.txt");

        /**
         * spark 2.x 使用flatMap的时候 spark 1.x中的Iterable对象 变成了 spark2.x中的Iterator对象
         * 相应的,对于返回值为list的RDD,  spark2.x中要返回list.iterator();
         */
        JavaRDD<String> wordsRDD = lineRDD.flatMap(new FlatMapFunction<String, String>() {

            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(",")).iterator();
            }
        });

        /**
         * Java 使用 mapToPair 等同于scala中的map  将读取的每个单词 记为 1
         */
        JavaPairRDD<String, Integer> wordTuple2RDD = wordsRDD.mapToPair(new PairFunction<String, String, Integer>() {

            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        /**
         * 使用reduceByKey 进行单词的统计
         */
        JavaPairRDD<String, Integer> retValueRDD = wordTuple2RDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        //截止到目前为止，就把上面数据文件中的单词统计出来了，下面我们要进行将统计的单词数目降序排序。
        /**
         * 使用mapToPaire算子将 <K,V>格式的RDD翻转 为 <V,K>
         */
        JavaPairRDD<Integer, String> exchageKVRDD = retValueRDD.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {

            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> t) throws Exception {
                return new Tuple2<Integer, String>(t._2, t._1);
            }
        });
        /**
         * 按照Key进行降序排序
         */
        JavaPairRDD<Integer, String> sortByKeyRDD = exchageKVRDD.sortByKey(false);
        /**
         * 将 Tuple 的 <Integer,String> 再次翻转<String,Integer>
         */
        JavaPairRDD<String, Integer> retSortRDD = sortByKeyRDD.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {

            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> t) throws Exception {
                return new Tuple2<String, Integer>(t._2, t._1);
            }
        });
        /**
         * 遍历最终的结果 打印输出
         */
        retSortRDD.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> tuple2) throws Exception {
                System.out.println(tuple2);
            }
        });
    }
}
