//package com.netcloud.bigdata.ml.example
//
//import java.io.{File, IOException}
//import java.util.UUID
//
//import com.netcloud.bigdata.mllib.AbstractParams
//import org.apache.spark.ml.linalg.Vector
//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.stat.MultivariateOnlineSummarizer
//import org.apache.spark.sql.{DataFrame, Row, SparkSession}
//import scopt.OptionParser
//
//
///*
//  * @Author: yangshaojun
//  * @Date: 2018/11/27 20:57
//  * @Version 1.0
//  */
//object DataFrameExample {
//
//  private val MAX_DIR_CREATION_ATTEMPTS: Int = 10
//  private val shutdownDeletePaths = new scala.collection.mutable.HashSet[String]()
//
//  case class Params(input: String = "data/mllib/sample_libsvm_data.txt")
//    extends AbstractParams[Params]
//
//  def main(args: Array[String]) {
//    val defaultParams = Params()
//
//    val parser = new OptionParser[Params]("DataFrameExample") {
//      head("DataFrameExample: an example app using DataFrame for ML.")
//      opt[String]("input")
//        .text(s"input path to dataframe")
//        .action((x, c) => c.copy(input = x))
//      checkConfig { params =>
//        success
//      }
//    }
//
//    parser.parse(args, defaultParams) match {
//      case Some(params) => run(params)
//      case _ => sys.exit(1)
//    }
//  }
//
//  def run(params: Params): Unit = {
//    val spark = SparkSession
//      .builder
//      .master("local[2]")
//      .appName(s"DataFrameExample with $params")
//      .getOrCreate()
//
//    // Load input data
//    println(s"Loading LIBSVM file with UDT from ${params.input}.")
//    val df: DataFrame = spark.read.format("libsvm").load(params.input).cache()
//    println("Schema from LIBSVM:")
//    df.printSchema()
//    println(s"Loaded training data as a DataFrame with ${df.count()} records.")
//
//    // Show statistical summary of labels.
//    val labelSummary = df.describe("label")
//    labelSummary.show()
//
//    // Convert features column to an RDD of vectors.
//    val features = df.select("features").rdd.map { case Row(v: Vector) => v }
//    val featureSummary = features.aggregate(new MultivariateOnlineSummarizer())(
//      (summary, feat) => summary.add(Vectors.fromML(feat)),
//      (sum1, sum2) => sum1.merge(sum2))
//    println(s"Selected features column with average values:\n ${featureSummary.mean.toString}")
//
////     Save the records in a parquet file.
//        val tmpDir = createTempDir()
//        val outputDir = new File(tmpDir, "dataframe").toString
//        println(s"Saving to $outputDir as Parquet file.")
//        df.write.parquet(outputDir)
//
////     Load the records back.
//        println(s"Loading Parquet file with UDT from $outputDir.")
//        val newDF = spark.read.parquet(outputDir)
//        println(s"Schema from Parquet:")
//        newDF.printSchema()
//
//
//    spark.stop()
//  }
//  def createDirectory(root: String, namePrefix: String = "spark"): File = {
//    var attempts = 0
//    val maxAttempts = MAX_DIR_CREATION_ATTEMPTS
//    var dir: File = null
//    while (dir == null) {
//      attempts += 1
//      if (attempts > maxAttempts) {
//        throw new IOException("Failed to create a temp directory (under " + root + ") after " +
//          maxAttempts + " attempts!")
//      }
//      try {
//        dir = new File(root, namePrefix + "-" + UUID.randomUUID.toString)
//        if (dir.exists() || !dir.mkdirs()) {
//          dir = null
//        }
//      } catch { case e: SecurityException => dir = null; }
//    }
//
//    dir.getCanonicalFile
//  }
//
//  def createTempDir(
//                     root: String = System.getProperty("java.io.tmpdir"),
//                     namePrefix: String = "spark"): File = {
//    val dir = createDirectory(root, namePrefix)
//    registerShutdownDeleteDir(dir)
//    dir
//  }
//  /**
//    * Delete a file or directory and its contents recursively.
//    * Don't follow directories if they are symlinks.
//    * Throws an exception if deletion is unsuccessful.
//    */
//
//  private def listFilesSafely(file: File): Seq[File] = {
//    if (file.exists()) {
//      val files = file.listFiles()
//      if (files == null) {
//        throw new IOException("Failed to list files for dir: " + file)
//      }
//      files
//    } else {
//      List()
//    }
//  }
//  // Register the path to be deleted via shutdown hook
//  def registerShutdownDeleteDir(file: File) {
//    val absolutePath = file.getAbsolutePath()
//    shutdownDeletePaths.synchronized {
//      shutdownDeletePaths += absolutePath
//    }
//  }
//}
