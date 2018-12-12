import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.SparkSession

/*
  * @Author: yangshaojun
  * @Date: 2018/11/27 13:28
  * @Version 1.0
  */
/**
  * 字符串索引器:将标签的字符串列 映射到标签索引的整数列。
  * 1、如果输入列是数字，则需要其转换为字符串然后使用字符串索引器映射为整数列。
  * 2、索引在 [0, numLabels), 按照标签的频率排序.
  * 3、最频繁的标签得到索引0.
  * 4、当下游管道组件（如Estimator或Transformer）使用此字符串索引标签时，必须将组件的输入列设置为此字符串索引列名称。 在许多情况下，您可以使用setInputCol设置输入列。
  *
  */
object StringIndexerExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("StringIndexerExample")
      .getOrCreate()
    /**
      * DataFrame with columns id and category:
      * category is a string column with three labels: “a”, “b”, and “c”
      * Applying StringIndexer with category as the input column and categoryIndex as the output column
      */
    val df = spark.createDataFrame(
      Seq((0, "a"), (1, "b"), (2, "c"), (3, "a"), (4, "a"), (5, "c"))
    ).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")

    val indexed = indexer.fit(df).transform(df)
    indexed.show()

    /*
      * “a” gets index 0 because it is the most frequent, followed by “c” with index 1 and “b” with index 2.
      * +---+--------+-------------+
      * | id|category|categoryIndex|
      * +---+--------+-------------+
      * |  0|       a|          0.0|
      * |  1|       b|          2.0|
      * |  2|       c|          1.0|
      * |  3|       a|          0.0|
      * |  4|       a|          0.0|
      * |  5|       c|          1.0|
      * +---+--------+-------------+
      */

  }


}
