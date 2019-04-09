package com.netcloud.bigdata.ml.example

/**
  * 逻辑回归模型的参数
  * LogisticRegression parameters:
  * //1、设置分布式统计时的层数，主要用在treeAggregate中，数据量越大，可适当加大这个值，默认为2
  * aggregationDepth: suggested depth for treeAggregate (>= 2) (default: 2)
  * //2、弹性参数，用于调节L1和L2之间的比例，两种正则化比例加起来是1，详见后面正则化的设置，默认为0，只使用L2正则化，设置为1就是只用L1正则化
  * elasticNetParam: the ElasticNet mixing parameter, in range [0, 1]. For alpha = 0, the penalty is an L2 penalty. For alpha = 1, it is an L1 penalty (default: 0.0)
  * //3、binomial(二分类)/multinomial(多分类)/auto，默认为auto。设为auto时，会根据schema或者样本中实际的class情况设置是二分类还是多分类，最好明确设置
  * family: The name of family which is a description of the label distribution to be used in the model. Supported options: auto, binomial, multinomial. (default: auto)
  * //4、特征列 默认是features
  * featuresCol: features column name (default: features)
  * //5、是否拟合截距，默认为true
  * fitIntercept: whether to fit an intercept term (default: true)
  * //6、标签列名 默认是label
  * labelCol: label column name (default: label)
  * lowerBoundsOnCoefficients: The lower bounds on coefficients if fitting under bound constrained optimization. (undefined)
  * lowerBoundsOnIntercepts: The lower bounds on intercepts if fitting under bound constrained optimization. (undefined)
  * maxIter: maximum number of iterations (>= 0) (default: 100) //最大迭代次数，训练的截止条件，默认100次
  * predictionCol: prediction column name (default: prediction)
  * probabilityCol: Column name for predicted class conditional probabilities. Note: Not all models output well-calibrated probability estimates! These probabilities should be treated as confidences, not precise probabilities (default: probability)
  * rawPredictionCol: raw prediction (a.k.a. confidence) column name (default: rawPrediction)
  * regParam: regularization parameter (>= 0) (default: 0.0)
  * standardization: whether to standardize the training features before fitting the model (default: true)
  * threshold: threshold in binary classification prediction, in range [0, 1] (default: 0.5)
  * thresholds: Thresholds in multi-class classification to adjust the probability of predicting each class. Array must have length equal to the number of classes, with values > 0 excepting that at most one value may be 0. The class with largest value p/t is predicted, where p is the original probability of that class and t is the class's threshold (undefined)
  * tol: the convergence tolerance for iterative algorithms (>= 0) (default: 1.0E-6)
  * upperBoundsOnCoefficients: The upper bounds on coefficients if fitting under bound constrained optimization. (undefined)
  * upperBoundsOnIntercepts: The upper bounds on intercepts if fitting under bound constrained optimization. (undefined)
  * weightCol: weight column name. If this is not set or empty, we treat all instance weights as 1.0 (undefined)
  */

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.{Row, SparkSession}

/**
  * @author yangshaojun
  * #date  2019/3/27 13:50
  * @version 1.0
  */
object EstimatorTransformerParamExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("EstimatorTransformerParamExample")
      .getOrCreate()

    // 准备训练结集 ; 此集合中存储的是Tuple类型(label, features) 即：带有标签的数据集
    val training = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, 1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    // 创建逻辑回归实例; 此实例是一个 Estimator.
    val lr = new LogisticRegression()
    // 打印出逻辑回归算法的默认参数
    println(s"LogisticRegression parameters:\n ${lr.explainParams()}\n")
    // 使用setter方法 给逻辑回归算法设置参数
    lr.setMaxIter(10) // 算法最大迭代次数，训练的截止条件，默认100次
      .setRegParam(0.01) //正则化系数，默认为0，不使用正则化
      .setFamily("binomial") //二分类
    println(s"LogisticRegression parameters:\n ${lr.explainParams()}\n")

    // 从训练接中学到一个逻辑回归模型 这里使用存储在lr中的参数。
    val model1 = lr.fit(training)
    // model1 是一个模型 (即：Estimator生产出一个Transformer),
    println("-------------------------------------------------------")
    // 我们可以查看fit（）期间使用的参数。
    println(s"Model 1 was fit using parameters: ${model1.parent.extractParamMap}")

    // 我们也可以使用ParamMap指定参数，
    // 支持多种指定参数的方法
    val paramMap = ParamMap(lr.maxIter -> 20)
      .put(lr.maxIter, 30) // 指定一个参数 1 这会覆盖原始的 maxIter.
      .put(lr.regParam -> 0.1, lr.threshold -> 0.55) // 指定多个参数
    // 也可以组合ParamMaps。
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability") // 改变输出列名。
    val paramMapCombined = paramMap ++ paramMap2
    // 现在使用paramMapCombined参数学习一个新模型。
    // paramMapCombined参数会覆盖之前使用lr.set* 方法设置的所有的参数。
    val model2 = lr.fit(training, paramMapCombined)
    println(s"Model 2 was fit using parameters: ${model2.parent.extractParamMap}")

    // 准备测试集
    val test = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    // 使用Transformer.transform（）方法对测试数据进行预测。
    // LogisticRegression.transform仅使用“features”列。
    // 请注意，model2.transform（）输出'myProbability'列而不是通常的列
    //'probability'列，因为我们先前重命名了lr.probabilityCol参数。
    model2.transform(test)
      .select("features", "label", "myProbability", "prediction")
      .collect()
      .foreach { case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>
        println(s"($features, $label) -> prob=$prob, prediction=$prediction")
      }

    spark.stop()
  }

}
