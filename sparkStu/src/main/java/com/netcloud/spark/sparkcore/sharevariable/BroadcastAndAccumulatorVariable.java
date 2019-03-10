package com.netcloud.spark.sparkcore.sharevariable;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;

import java.util.Arrays;
import java.util.List;

/**
 * 广播变量
 * 在java中创建共享变量就是调用 sparkContext的broadcast方法
 * 获取的返回结果就是Broadcast<T>类型
 * 使用共享变量时候 通过调用 value() 即可获取其内部封装的值。
 *
 * 在java中 需要调用SparkContext的accumulator方法创建广播变量
 * 在函数内部就可以对Accumulator变量，调用 add()方法累加值。
 * 在Driver程序中，可以对Accumulator变量调用 value()方法获取累加值
 * @author yangshaojun
 * #date  2019/3/10 14:36
 * @version 1.0
 */
public class BroadcastAndAccumulatorVariable {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("BroadcastVariable").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
//        broadcast(sc);
        accumulator(sc);
    }

    /**
     * 广播变量
     * @param sc
     */
    public static void broadcast(JavaSparkContext sc) {
        int factor = 2;
        final Broadcast<Integer> factorBroadCast = sc.broadcast(factor);//定义一个广播变量，广播出去
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numbers = sc.parallelize(list);
        //让集合中的每个数字乘以算子外部的变量 factor
        JavaRDD<Integer> retRDD = numbers.map(new Function<Integer, Integer>() {

            @Override
            public Integer call(Integer v1) throws Exception {
                //获取广播变量的值
                int factor = factorBroadCast.value();
                return v1 * factor;
            }
        });
        retRDD.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer v) throws Exception {

                System.out.println(v);
            }
        });
    }


    /**
     * 累加变量
     *
     * @param sc
     */
    public static void accumulator(JavaSparkContext sc) {

        Accumulator<Integer> sum = sc.accumulator(0);//定义一个累加变量
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> numbers = sc.parallelize(list);
        numbers.foreach(new VoidFunction<Integer>() {
            @Override
            public void call(Integer v) throws Exception {
                sum.add(v);//往累加变量中添加值
            }
        });
        System.out.println(sum.value());
    }
}
