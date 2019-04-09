package com.netcloud.spark.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import java.util.ArrayList;

import java.util.List;

/**
 * java版本使用反射和动态转换的方式将RDD转为DataFrame
 * @author yangshaojun
 * #date  2019/4/3 11:41
 * @version 1.0
 */
public class RDD2DataFrame {
    public static void main(String[] args) {

        JavaSparkContext sc = new JavaSparkContext(new SparkConf()
                .setMaster("local")
                .setAppName("RDD2DataFrame"));
        SQLContext sqlContext = new SQLContext(sc);
        JavaRDD<String> stringRDD = sc.textFile("data/sparksql/people.txt", 3);
        JavaRDD<People> peoples = stringRDD.map(new Function<String, People>() {

            @Override
            public People call(String str) throws Exception {
                String[] arrs = str.split(",");
                People people = new People(arrs[0], Integer.valueOf(arrs[1].trim()));
                return people;
            }
        });
        // 使用反射机制 将RDD转为DataFrame
        // People类必须实现序列化接口
        Dataset<Row> dataFrame = sqlContext.createDataFrame(peoples, People.class);
        // 将DataFrame注册为一个临时表；然后针对其中的数据执行SQL语句。
        dataFrame.registerTempTable("people");
        // 针对临时表执行SQL
        Dataset<Row> peopleDF = sqlContext.sql("select * from people where age>25");
        peopleDF.show();
        // 将DataFrame再次转为RDD
        JavaRDD<Row> peopleRDD = peopleDF.javaRDD();
        JavaRDD<People> res = peopleRDD.map(new Function<Row, People>() {
            @Override
            public People call(Row row) throws Exception {

                People people = new People();
                people.setAge(row.getInt(0));
                people.setName(row.getString(1));
                return people;
            }
        });
        List<People> retvalue = res.collect();
        for (People people : retvalue) {
            System.out.println(people);
        }

        /**
         *编码的方式 将RDD转为DataFrame
         */
        // 1、将 JavaRDD<String> 转为 JavaRDD<Row> 使用RowFactory.create()返回Row对象
        JavaRDD<Row> rowRDD = stringRDD.map(new Function<String, Row>() {

            @Override
            public Row call(String str) throws Exception {
                String[] arrs = str.split(",");
                String name = arrs[0];
                System.out.println(name);
                int age = Integer.valueOf(arrs[1].trim());

                return RowFactory.create(name, age);
            }
        });
        // 2、创建schema信息
        List<StructField> fields = new ArrayList<StructField>();
        StructField field = null;
        field = DataTypes.createStructField("name", DataTypes.StringType, true);
        fields.add(field);
        field = DataTypes.createStructField("age", DataTypes.IntegerType, true);
        fields.add(field);
        StructType schema = DataTypes.createStructType(fields);

        // 3 、将RDD和schema关联
        Dataset<Row> retsDF = sqlContext.createDataFrame(rowRDD, schema);
        retsDF.show();
    }
}
