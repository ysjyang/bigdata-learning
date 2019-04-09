package com.netcloud.spark.sparkcore.projectpractice;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 对每个班级内的学生成绩，取出前三名。（分组TopN）
 *
 * @author yangshaojun
 * #date  2019/3/15 17:05
 * @version 1.0
 */
public class Demo_003_GroupTopThree {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local").setAppName("Demo_003_GroupTopThree");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lineRDD = sc.textFile("data/sparkcore/score.txt");
        JavaPairRDD<String, Integer> kvRDD = lineRDD.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {

                return new Tuple2<String, Integer>(s.split(",")[0].toString(), Integer.valueOf(s.split(",")[1]));
            }
        });

        JavaPairRDD<String, Iterable<Integer>> groupPairRDD = kvRDD.groupByKey();
        JavaPairRDD<String, Iterable<Integer>> top3RDD = groupPairRDD.mapToPair(new PairFunction<Tuple2<String, Iterable<Integer>>, String, Iterable<Integer>>() {

            @Override
            public Tuple2<String, Iterable<Integer>> call(Tuple2<String, Iterable<Integer>> t) throws Exception {
                String calssName = t._1;
                Integer[] top3 = new Integer[3];
                Iterator<Integer> scores = t._2.iterator();
                while (scores.hasNext()) {
                    Integer score = scores.next();
                    for (int i = 0; i < 3; i++) {
                        if (top3[i] == null) {
                            top3[i] = score;
                            break;
                        } else if (score > top3[i]) {
                            int tmp = top3[i];
                            top3[i] = score;
                            if (i < top3.length - 1) {
                                top3[i + 1] = tmp;
                            }
                            break;
                        }

                    }

                }

                return new Tuple2<String, Iterable<Integer>>(calssName, Arrays.asList(top3));
            }
        });

        top3RDD.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {
            @Override
            public void call(Tuple2<String, Iterable<Integer>> t) throws Exception {
                String cassName = t._1;
                Iterator<Integer> scores = t._2.iterator();
                while (scores.hasNext()) {
                    Integer score = scores.next();
                    System.out.println(cassName + ":" + score);
                }

            }
        });

    }
}
