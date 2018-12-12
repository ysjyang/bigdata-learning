import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession

/*
  * @Author: yangshaojun
  * @Date: 2018/11/27 14:02
  * @Version 1.0
  */
/**
  * 将多个列合并为单个向量列
  * VectorAssembler是一个变换器，它将给定的列列表组合到一个向量列中。
  * 将原始特征和由不同特征变换器生成的特征组合成单个特征向量非常有用，以便训练ML模型，如逻辑回归和决策树。
  * VectorAssembler接受以下输入列类型：所有数字类型，布尔类型和向量类型。
  * 在每一行中，输入列的值将按指定的顺序连接到一个向量中。
  */
object VectorAssemblerExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local[2]")
      .appName("VectorAssemblerExample")
      .getOrCreate()

    val df = spark.createDataFrame(
      Seq((0, 18, 1.0, Vectors.dense(0.0, 10.0, 0.5), 1.0))
    ).toDF("id", "hour", "mobile", "userFeatures", "clicked")

    val assembler = new VectorAssembler()
      .setInputCols(Array("hour", "mobile", "userFeatures"))
      .setOutputCol("features")

    val output = assembler.transform(df)
    println("Assembled columns 'hour', 'mobile', 'userFeatures' to vector column 'features'")
    output.select("features", "clicked").show(false)

    spark.stop()

    /*
      * +-----------------------+-------+
      * |features               |clicked|
      * +-----------------------+-------+
      * |[18.0,1.0,0.0,10.0,0.5]|1.0    |
      * +-----------------------+-------+
      */
  }
}
