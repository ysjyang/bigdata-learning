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
      * for循环计数器
      */
    for(i <- 0 until arr.length) println(s"$i is ${arr(i)}")

    /**
      * for循环 生成器 "<-"和卫语句(for循环的if条件过滤语句) "if i<4"
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
  }

}
