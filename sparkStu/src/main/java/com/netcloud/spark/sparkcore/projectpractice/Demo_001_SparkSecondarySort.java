package com.netcloud.spark.sparkcore.projectpractice;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

/**
 * Demo_001_SparkSecondarySort
 * spark java版本的二次排序。
 * 1) 实现自定义的Key，要实现Ordered接口和Serializable接口，在key中实现自己对多个列的排序算法。
 * 2) 将包含文本的RDD，映射成Key为自定义的key、value为文本的RDD。
 * 3) 使用SortByKey算子 按照自定义的Key进行排序。
 * 4) 再次映射，剔除自定义的key，只保留文本行。
 * @author yangshaojun
 * #date  2019/3/14 20:53
 * @version 1.0
 */
public class Demo_001_SparkSecondarySort {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local").setAppName("Demo_001_SparkSecondarySort");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lineRDD = sc.textFile("data/sparkcore/secondarysort.txt");

        //将JavaRDD<String> 转为 JavaPairRDD<SecondarySortKey,String>  其中这里的Key是我们自己定义的Key
        JavaPairRDD<SecondarySortKey, String> pairRDD = lineRDD.mapToPair(new PairFunction<String, SecondarySortKey, String>() {
            @Override
            public Tuple2<SecondarySortKey, String> call(String line) throws Exception {
                String[] lineSplited = line.split(" ");
                SecondarySortKey key = new SecondarySortKey(Integer.valueOf(lineSplited[0]), Integer.valueOf(lineSplited[1]));
                return new Tuple2<SecondarySortKey, String>(key, line);
            }
        });
        //调用 sortByKey 方法 然后按照自定义的key进行排序
        JavaPairRDD<SecondarySortKey, String> sortByKey = pairRDD.sortByKey();
        JavaRDD<String> retRDD = sortByKey.map(new Function<Tuple2<SecondarySortKey, String>, String>() {

            @Override
            public String call(Tuple2<SecondarySortKey, String> v1) throws Exception {
                return v1._2;
            }
        });
        retRDD.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                System.out.println(s);
            }
        });
    }
}
