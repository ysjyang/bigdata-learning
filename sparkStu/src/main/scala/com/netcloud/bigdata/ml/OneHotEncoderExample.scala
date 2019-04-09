import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer}
import org.apache.spark.sql.SparkSession

/*
  * @Author: yangshaojun
  * @Date: 2018/11/27 10:30
  * @Version 1.0
  */
/**
  * 独热编码
  * 将类别索引列映射到二进制向量列。此编码允许期望连续特征（例如Logistic回归）的算法使用分类特征。
  * 类别的索引从o开始,索引的排序顺序是根据类别出现频率决定。
  */
object OneHotEncoderExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("OneHotEncoderExample")
      .getOrCreate()

    /**
      *
      * DataFrame with columns id and category:
      * category is a string column with three labels: “a”, “b”, and “c”
      */
    val df = spark.createDataFrame(Seq(
      (0, "a"),
      (1, "b"),
      (2, "c"),
      (3, "a"),
      (4, "a"),
      (5, "c")
    )).toDF("id", "category")

    //字符串索引转换：将标签的字符串列 映射到标签索引的整数列。
    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .fit(df)
    val indexed = indexer.transform(df)
    indexed.show()
    /**
      * OneHotEncode:实际上是转换成了稀疏向量
      * Spark源码: The last category is not included by default 最后一个种类默认不包含
      *
      */
    val encoder = new OneHotEncoder()
      .setInputCol("categoryIndex")
      .setOutputCol("categoryVec")
    // 设置最后一个是否包含
          .setDropLast(false)

    val encoded = encoder.transform(indexed)
    encoded.show()

    spark.stop()

    /*
      * 这里有三个类别，由于spark默认最后的类别不包括 那么显示为标签数为2
      * 最后的类别转为独热编码为(2,[],[]) 、(2,[0],[1.0])表示两个标签 0号索引的向量值为1.0 1号索引的向量值为0
      * +---+--------+-------------+-------------+
      * | id|category|categoryIndex|  categoryVec|
      * +---+--------+-------------+-------------+
      * |  0|       a|          0.0|(2,[0],[1.0])|
      * |  1|       b|          2.0|    (2,[],[])|
      * |  2|       c|          1.0|(2,[1],[1.0])|
      * |  3|       a|          0.0|(2,[0],[1.0])|
      * |  4|       a|          0.0|(2,[0],[1.0])|
      * |  5|       c|          1.0|(2,[1],[1.0])|
      * +---+--------+-------------+-------------+
      */
  }

}
