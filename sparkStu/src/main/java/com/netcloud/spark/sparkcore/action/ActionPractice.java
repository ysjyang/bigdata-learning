package com.netcloud.spark.sparkcore.action;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * reduce:对RDD中的每个元素按照指定的逻辑进行聚合（通常进行累加）
 * collect：将远程集群中RDD中所有的数据元素返回发Driver端
 * count:统计RDD中元素的个数
 * take:从远程集群中获取RDD前n条数据
 * savaAsTextFile:将RDD元素保存到本地文件或者hdfs中
 * countByKey：统计相同key 出现的次数
 * forearach：在远程集群中遍历元素
 *
 * @author yangshaojun
 * #date  2019/3/10 11:34
 * @version 1.0
 */
public class ActionPractice {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("ActionPractice").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //reduce(sc);
        //collect(sc);
        //count(sc);
        //take(sc);
        //savaAsTextFile(sc);
        countByKey(sc);
        sc.stop();
    }

    /**
     * reduce：将RDD中的每个元素按照指定的聚合逻辑进行聚合
     *
     * @param sc
     */
    public static void reduce(JavaSparkContext sc) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numRDD = sc.parallelize(list);
        /**
         * 使用reduce将集合中的元素累加
         * reduce操作原理:
         * 首先将第一个和第二个元素传入 call()方法进行计算，会获取一个结果 比如 1+2=3
         * 接着 将该结果与下一个元素传入 call()方法进行计算 不让 3+3=6 以此类推
         * 所有reduce操作的本质就是聚合，将多个元素聚合成一个元素
         *
         */
        int sum = numRDD.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        System.out.println(sum);

    }

    /**
     * collect：将远程集群中RDD中所有的数据元素返回发哦Driver端
     * 这种方式不建议使用 当数据量太大会造成内存溢出还有就是大量的网络传输，使性能变差
     *
     * @param sc
     */
    public static void collect(JavaSparkContext sc) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numRDD = sc.parallelize(list);

        List<Integer> ret = numRDD.collect();
        for (Integer num : ret) {
            System.out.println(num);
        }

    }

    /**
     * count：统计rdd中数据元素数目
     *
     * @param sc
     */
    public static void count(JavaSparkContext sc) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numRDD = sc.parallelize(list);
        Long ret = numRDD.count();
        System.out.println(ret);
    }

    /**
     * take：统计rdd中前n条数据
     *
     * @param sc
     */
    public static void take(JavaSparkContext sc) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numRDD = sc.parallelize(list);
        List<Integer> ret = numRDD.take(2);
        System.out.println(ret);
    }

    /**
     * savaAsTextFile：将RDD中的元素保存到本地文件或者hdfs文件中。
     *
     * @param sc
     */
    public static void savaAsTextFile(JavaSparkContext sc) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numRDD = sc.parallelize(list);
        numRDD.saveAsTextFile("data/result");
    }

    /**
     * countByKey：统计相同key 出现的次数
     *
     * @param sc
     */
    public static void countByKey(JavaSparkContext sc) {

        List<Tuple2<String, Integer>> list = Arrays.asList(new Tuple2("tome", 100), new Tuple2("tome", 200), new Tuple2("jack", 200), new Tuple2("marry", 10));
        JavaPairRDD<String, Integer> numRDD = sc.parallelizePairs(list);
        Map map = numRDD.countByKey();
        System.out.println(numRDD.countByKey());
        /**
         * {tome=2, marry=1, jack=1}
         */
    }
}
