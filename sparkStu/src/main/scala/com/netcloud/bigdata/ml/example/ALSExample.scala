package com.netcloud.bigdata.ml.example


import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
// $example off$
import org.apache.spark.sql.SparkSession

/**
  * 一个ALS的示例
  */

object ALSExample {


  case class Rating(userId: Int, movieId: Int, rating: Float, timestamp: Long)
  // 自定义一个函数 作为map 高阶函数的输入。
  def parseRating(str: String): Rating = {
    val fields = str.split("::")
    assert(fields.size == 4) // 判断fields的大小是否是4 否则抛出异常。
    Rating(fields(0).toInt, fields(1).toInt, fields(2).toFloat, fields(3).toLong)
  }

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("ALSExample")
      .getOrCreate()
    import spark.implicits._

    val ratings = spark.read.textFile("data/mllib/als/sample_movielens_ratings.txt")
      .map(parseRating)
      .toDF() // 将RDD转为DataFrame
    val Array(training, test) = ratings.randomSplit(Array(0.8, 0.2))
    // 在训练集上使用ALS算法创建 推荐模型

    val als = new ALS()
      .setMaxIter(5)
      .setRegParam(0.01)
      .setImplicitPrefs(true)
      .setUserCol("userId")
      .setItemCol("movieId")
      .setRatingCol("rating")
    val model = als.fit(training)

    // 在测试集上使用RMSE 评估模型
    // 注意：我们将冷启动策略设置为 'drop' 以确保我们不会获得NaN评估指标
    model.setColdStartStrategy("drop")
    val predictions = model.transform(test) // 使用模型对数据进行预测

    val evaluator = new RegressionEvaluator() // 使用RMSE 评估模型 对模型进行评估。
      .setMetricName("rmse")
      .setLabelCol("rating")
      .setPredictionCol("prediction")
    val rmse = evaluator.evaluate(predictions)
    println(s"Root-mean-square error = $rmse")

    // 为每个用户生成前10个电影推荐
    val userRecs = model.recommendForAllUsers(10)
    // 为每部电影生成前10个用户推荐
    val movieRecs = model.recommendForAllItems(10)
    userRecs.show()
    movieRecs.show()

    spark.stop()
  }
}


