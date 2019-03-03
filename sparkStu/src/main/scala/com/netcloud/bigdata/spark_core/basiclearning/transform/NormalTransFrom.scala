package com.netcloud.bigdata.spark_core.basiclearning.transform

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
/**
 * demo02
 * spark转换算子
 * 1、filter:参数是函数，函数会过滤掉不符合条件的元素，返回值是新的RDD。
 * 2、sample:随机抽样算子，根据传进去的小数按比例进行又放回或者无放回的抽样。
 * 3、map:在Map中，我们会传入一个函数，该函数对每个输入都会返回一个数组(或者元组、集合)（而不是一个元素）。
 * 4、flatMap:是map的一种扩展。在flatMap中，我们会传入一个函数，该函数对每个输入都会返回一个数组或者元组、集合（而不是一个元素），然后，flatMap把生成的多个集合（数组）“拍扁”成为一个集合(数组)或者字符串RDD。
 * 5、reduceByKey:将K、V格式的Rdd中相同的Key根据相应的逻辑进行处理。 如：单词统计的加法操作。
 * 6、distinct:没有参数，将RDD里的元素进行去重操作。
 * 7、intersection:参数是RDD，求出两个RDD的共同元素。
 * 8、subtract():参数是RDD，将原RDD里和参数RDD里相同的元素去掉 即：返回的数据是原RDD中不和参数RDD中相同的元素。
 * 9、cartesian:参数是RDD，求两个RDD的笛卡儿积。
 * 10、sortByKey:作用在K,V格式的RDD上，对key进行升序或者降序排序.
 * 11、mapPartiiton:与map类似，遍历的单位是每个partition上的数据。
 * 12、groupByKey:将rdd按照key分组。
 * 13、zipWithIndex: 该函数将RDD中的元素和这个元素在RDD中的索引号（从0开始）组合成（K,V）对
 */
object NormalTransFrom {
 def main(args: Array[String]): Unit = {
   val conf=new SparkConf()
   conf.setMaster("local").setAppName("test")
   val sc=new SparkContext(conf);
   val lineRdd=sc.textFile("./data.txt");//读取的所有数据
   /**
    * 过滤出符合条件的元素，true保留，false过滤掉。
    */
   println("***************1、已进入filter方法*********************");
   val filterRdd=lineRdd.filter { line => line.equals("hello bjsxt") }//line是所有文件数据中读取的一行数据
   //line.equals("hello bjsxt") 表示：读取的一行数据为hello bjsxt的数据
   val result=filterRdd.collect();//返回RDD所有元素,将计算结果收集到Driver端
   result.foreach{
     elem => {
       println(elem);
     }
   }
   //result.foreach(println);
   println("***************filter方法结束！*********************");
   /**
    * 随机抽样算子，根据传进去的小数按比例进行又放回或者无放回的抽样。
    * 第一个参数：如果为true代表有放回的抽样，false代表无放回的抽样
    * 第二个参数代表抽样的比例
    * 第三个参数代表种子：如果对同一批数据相同种子随机抽样，那么收到的结果相同。
    * 也就是说如果前两个参数不变的情况下，设置种子值固定 那么随机抽样出的数据相同
    */
   println("***************2、已进入sample方法*********************");
   val sampleRdd=lineRdd.sample(true, 0.5)
   sampleRdd.foreach{
     println
   }
   println("***************sample方法结束！*********************");

    println("***************3、map与faltMap方法开始！*********************");
    /*map返回的是元组信息 flatMap返回的是字符串RDD*/
    val wordRdd=lineRdd.flatMap{line => {line.split(" ")}};//将读取的每行数据按空格分割，然后返回字符串数组，有多少行数据就会返回多少字符串数组，最后将所有的字符串数组进行扁平化为一个字符串RDD。
    val pairRdd=wordRdd.map{word => {(word,1)}}//遍历每个单词 然后记为1  返回的是K、V格式的元组信息
    val reduceRdd=pairRdd.reduceByKey((v1:Int,v2:Int) =>{v1+v2})//将相同key的元组 进行相加
    reduceRdd.foreach{println}
    
    /*map返回的是字符串数组 flatMap返回的是字符串RDD*/
     val mapResult= lineRdd.map{lines =>lines.split(" ")};//map函数执行后返回的是字符串数组RDD
     val flatMapResult= lineRdd.flatMap{lines =>lines.split(" ")};//flatMap函数执行后返回的是字符串RDD
     val result1=mapResult.collect();//collect 函数返回Array[Array[String]] result是二维数组
     result1.foreach(println); 
     
     /*map返回的是字符串集合 flatMap返回的是字符型集合*/
    val books = List("Hadoop","Hive","HDFS")//List[String]
    val mapResult2=books.map(s => s.toUpperCase)//List[String] = List(HADOOP, HIVE, HDFS)
    val flatMapResult2=books.flatMap(s => s.toUpperCase)//List[Char] = List(H, a, o, o, p, H, i, v, e, H, D, F, S)
    println(mapResult2);
    
    println("***************map与faltMap方法结束！*********************");

    println("***************4、distinct方法开始！*********************");
    val dintinctResult=lineRdd.distinct();//这里是去除相同的行
    dintinctResult.foreach {println}
    println("***************distinct方法结束！*********************");

    println("***************5、union方法开始！*********************");
    val flow = sc.parallelize(List(("String1",1),("String2",2),("String3",3)));
    val org = sc.parallelize(List(("String3",3),("String4",1),("String1",2)));
    flow.union(org);
    val u = flow.union(org);
    u.foreach(println);
     /* 输出: RDDs的union操作，把2个RDDs的元素合并起来。
      (String1,1)
      (String2,2)
      (String3,3)
      (String3,3)
      (String4,1)
      (String1,2)
    * */
    println("***************union方法结束！*********************");
    
   println("***************6、intersection方法开始！*********************");
   val same=flow.intersection(org);
   same.foreach(println)
    /*
     * (String3,3)
     * 
     * 
     */
   println("***************intersection方法结束！*********************");
   println("***************7、subtract方法开始！*********************");
   val different=flow.subtract(org);
   different.foreach(println)
   /*
    * (String2,2)
      (String1,1)
    */
   println("***************7、subtract方法结束！*********************");
   println("***************8、cartesian方法开始！*********************");
 
   val cartesian=flow.cartesian(org)
   cartesian.foreach(println)
   /*
((String1,1),(String3,3))
((String1,1),(String4,1))
((String1,1),(String1,2))
((String2,2),(String3,3))
((String2,2),(String4,1))
((String2,2),(String1,2))
((String3,3),(String3,3))
((String3,3),(String4,1))
((String3,3),(String1,2))
    */
   println("***************8、cartesian方法结束！*********************");
   
   
   
   
    println("=========9、sortByKey的使用===========")
    val mapRdd1=reduceRdd.map(tuple =>{(tuple._2,tuple._1)})//将reduceRdd的map中的key和value位置转换
    val sortRdd=mapRdd1.sortByKey()//默认升序排序  当方法参数为false时降序排序
    val mapRdd2=sortRdd.map(tuple =>{(tuple._2,tuple._1)})
    mapRdd2.foreach{println}
    println("========10、first方法的使用==========");
    val firstResult=mapRdd2.first();//返回数据集中的第一个元素。
    println("firstResult="+firstResult);
    println("========11、take(n)方法的使用==========");
    val takenResult=mapRdd2.take(2);//返回一个包含数据集前n个元素的集合。
    takenResult.foreach{
      println
    }
    println("========12、mapPartitions方法的开始==========");
    val rdd1 = sc.makeRDD(Array(1,2,3,4,5,6),3)
    val mapResults  = rdd1.mapPartitions(iter=>{
      println("插入数据库")
      iter
    })
    mapResults.foreach { println }
    println("========mapPartitions方法的结束==========");
    println("========13、groupByKey方法的开始==========");
     val rdd13 = sc.makeRDD(Array(
        (1,"a"),
        (1,"b"),
        (2,"c"),
        (3,"d")
       ))    
        
    val result13 = rdd13.groupByKey()
    result13.foreach(println)
    /*
     * (1,CompactBuffer(a, b))
     * (3,CompactBuffer(d))
     * (2,CompactBuffer(c))
     */
    println("========groupByKey方法的结束==========");
    println("========14、zipWithIndex方法的开始==========");
    val rdd14 = sc.makeRDD(Array((1,"a"),(2,"b"),(3,"c")))
    val result14 = rdd14.zipWithIndex()
    result14.foreach(println)  
   /*
    *((1,a),0)
    *((2,b),1)
    *((3,c),2)    
    */
    println("========zipWithIndex方法的结束==========");
    sc.stop()
  }
}