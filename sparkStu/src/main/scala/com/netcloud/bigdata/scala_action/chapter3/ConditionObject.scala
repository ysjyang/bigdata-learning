package com.netcloud.bigdata.scala_action.chapter3

/**
  * 程序控制结构
  * @author yangshaojun
  * 2018/12/17 9:46
  * @version 1.0
  */
object ConditionObject {
  def main(args: Array[String]): Unit = {

    /**
      * 1)for、foreach循环
      * 2)while
      */
    /*for、foreach循环
     *遍历集合中所有的元素，对集合中的每个元素进行操作，或者利用现有的集合创建一个新的集合*/
    /**
      * for循环遍历数组元素
      *
    * */
    val arr=Array("apple","banana","orange")
    for(e <- arr) println(e)
    //当算法需要处理多行的时候 使用同样的for循环语法，同时在代码块中处理任务
    for(e <- arr){
      val res=e.toUpperCase
      println(res)
    }
    /**
      * for循环遍历map元素
      * -> ：Map key value定义符号
      * <- 生成器 通常用在for循环遍历中
      */
    val name=Map("fname" -> "Robert",
    "lname" -> "Goren")
    for((k,v) <- name) println(s"key:$k,value:$v")
    /**
      * for/yield 循环返回值 生成新的数组
      */
    val newArray=for(e <- arr) yield e.toUpperCase
    //当算法有多行代码才能解决问题时，可以将其放在yield关键字后的代码块中
    val newArray2=for(e <- arr) yield {
      val res1=e.toUpperCase
      res1
    }

    /**
      * for循环计数器 遍历数组元素
      */
    for(i <- 0 until arr.length) println(s"$i is ${arr(i)}")

    /**
      * for循环‘ 生成器 "<-"和卫语句(for循环的if条件过滤语句) "if i<4"
      */
   for(i <- 1 to 5 if i<4 ){
     println(i)
   }

    /**
      * foreach 遍历集合元素
      *  转化器 "=>"  将符号左边的参数列表 应用符号右边的算法生成新的结果
      */
    arr.foreach(println)
    arr.foreach(e => println(e.toUpperCase)) //匿名函数方法
    //如果算法有多行实现 把它放在代码块中
    arr.foreach{
      e=>
        val s=e.toUpperCase
        println(s)
    }

    /**
      * for循环中使用多个计数器
      * 如：遍历多维数组的情况
      *
      * 总结:
      * 1) for循环中的，<-符号创建的Ranges通常称作生成器，一个for循环中可以使用多个生成器
      * 2) 对于长一点的for循环 推荐使用大括号。
      */
    //创建具有两个计数器的for循环
    for(i<- 1 to 2;j<- 1 to 2) println(s"i=$i,j=$j")
    //对于多行的for循环 推荐使用大括号的代码风格 这里的{}不是方法体而是代码块
    for{
      i <- 1 to 2
      j <- 1 to 2
    } println(s"i=$i,j=$j")
    //创建一个二维的数组
    val array=Array.ofDim[Int](2,2)
    array(0)(0)=0
    array(0)(1)=1
    array(1)(0)=2
    array(1)(1)=3
    //遍历二维数组
    for(i <- 0 to 1;j <- 0 to 1) println(s"($i)($j)=$array($i)($j)")
    //长一点的for循环 推荐使用大括号
    for{
      i <- 0 to 1
      j <- 0 to 1
    }println(s"($i)($j)=$array($i)($j)")

    /**
      * for循环中嵌入if语句（卫语句） ：这些if语句被称为过滤器、过滤表达式、卫语句 可以使用任意多的卫语句
      * 在for循序中添加一个或者多个条件语句，典型的场景就是将一些元素从集合中过滤掉。
      */
    for (i <- 1 to 10 if i % 2 == 0) println(i)
    //或者使用大括号
    for {
      i <- 1 to 10
      if i % 2 == 0
    } println(i)

    //使用多个卫语句 下面的循环使用了比较笨的方法打印数字4
    for {
      i <- 1 to 10
      if i > 3
      if i < 6
      if i % 2 == 0
    } println(i)

    /**
      * 创建for表达式(for/yield)
      * 对一个已有的集合中的每个元素应用一个算法（可能包含一个或者多个卫句）从而生成一个新的集合
      * Note:
      * 1） 除了个别的情况 一般情况下for表达式 不会改变集合的返回值类型
      */
      val names = Array("tom", "andy", "jerry")
      val newNames = for (e <- names) yield e.capitalize
      //如果算法需要多行代码，需要把他们放在yield关键字后的代码块中
      val newNamesMuiltline = for (e <- names) yield {
        //想象着这里有多行代码
        e.length
      }
    //创建一个集合
    val list = List("apple", "banana", "orange")
    //等同于 下面的方式
    val fruits = "apple" :: "banana" :: "orange" :: Nil
    val newFruits = for (fruit <- fruits) yield fruit.toUpperCase

    /**
      * 讨论
      * 不带卫句的的基本 for/yield表达式 就像是在集合上调用map方法
      * 将集合中的每个元素首字母大写
      *
      */
    val out = for (e <- fruits) yield e.capitalize
    //在集合中调用 map方法也能达到相同的效果
    val out2 = fruits.map(e => e.capitalize)//fruits.map(_.capitalize)




  }

}
