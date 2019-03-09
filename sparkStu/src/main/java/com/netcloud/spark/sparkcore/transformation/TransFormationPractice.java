package com.netcloud.spark.sparkcore.transformation;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 转换算子实战
 * map;将集合中的每个元素都乘以2
 * filter：过滤出集合中的所有的偶数
 * flatMap:将文本行拆分多个单词
 * groupByKey：将班级的成绩分组
 * @author yangshaojun
 * #date  2019/3/9 17:10
 * @version 1.0
 */
public class TransFormationPractice {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //map(sc);
        //filter(sc);
        groupByKey(sc);
        sc.stop();

    }

    /**
     * Map算子的使用
     * @param sc
     */
    private static void map(JavaSparkContext sc) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numberRDD = sc.parallelize(numbers);
        /**
         * 使用map算子;将集合中的每个元素都乘以2
         * map算子，是对任何类型的RDD，都可以调用
         * 在java中map算子接收的参数是Function对象
         * 创建的Function对象，一定会让你设置第二个泛型参数，这个泛型类型，就是返回新元素的类型
         * 同时call()方法的返回类型，也必须与第二个泛型类型同步。
         * 在call方法内部，就可以对原始RDD中的每个元素进行各种处理和计算，并返回一个新的元素。
         * 所有新的元素就会组成一个新的RDD。
         */
        JavaRDD<Integer> multipleNumberRDD = numberRDD.map(new Function<Integer, Integer>() {

            /**
             * 传入call方法的就是 1,2,3,4,5
             * 返回的就是 2,4,6,8,10
             *
             * @param v1
             * @return
             * @throws Exception
             */
            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 * 2;
            }
        });
        //打印新的RDD
        multipleNumberRDD.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });

    }

    /**
     * filter：过滤出集合中的所有的偶数
     */
    private static void filter(JavaSparkContext sc) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> numbersRDD = sc.parallelize(numbers);
        JavaRDD<Integer> evennumRDD = numbersRDD.filter(new Function<Integer, Boolean>() {
            @Override
            public Boolean call(Integer v1) throws Exception {
                return v1 % 2 == 0;
            }
        });
        evennumRDD.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }

    /**
     * flatMap：将文本行拆分多个单词
     */
    private static void flatMap(JavaSparkContext sc) {
        List<String> numbers = Arrays.asList("hello you","hello me","hello world");
        JavaRDD<String> numbersRDD = sc.parallelize(numbers);
        /**
         * flatMap算子 在java中接收的参数是 FlatMapFunction
         * 我们需要自己定义FlatMapFunction的第二个参数泛型，即：代表了返回的新元素的类型；
         * call() 方法返回的类型是 不是U 而是 Iterable<<U> 这里的U也与第二个泛型类型相同。
         *
         */
//        JavaRDD<String> evennumRDD = numbersRDD.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterable<String> call(String s) throws Exception {
//                return Arrays.asList(s.split(" "));
//            }
//        });
//        evennumRDD.foreach(new VoidFunction<String>() {
//            @Override
//            public void call(String s) throws Exception {
//                System.out.println(s);
//            }
//        });
    }

    /**
     * groupByKey：按照班级将成绩分组
     * 使用groupByKey后返回的算子类型依然是JavaPairRDD 但是第二个参数泛型是Iterable<Integer>
     */
    private static void groupByKey(JavaSparkContext sc) {

        List<Tuple2<String, Integer>> scoresList = Arrays.asList(new Tuple2<String, Integer>("class1", 80),
                new Tuple2<String, Integer>("class2", 87),
                new Tuple2<String, Integer>("class1", 90),
                new Tuple2<String, Integer>("class1", 65));
        //并行化集合，创建JavaPairRDD 每个元素是tuple2
        JavaPairRDD<String, Integer> scores = sc.parallelizePairs(scoresList);
        JavaPairRDD<String, Iterable<Integer>> groupedscore = scores.groupByKey();
        groupedscore.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {
                                 @Override
                                 public void call(Tuple2<String, Iterable<Integer>> t1) throws Exception {
                                     Iterator<Integer> ite = t1._2.iterator();
                                     while (ite.hasNext()) {
                                         System.out.println(ite.next());
                                     }
                                     System.out.println("=============================");

                                 }
                             }
        );

    }
}
