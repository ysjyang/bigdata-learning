package com.netcloud.bigdata.scala_action.chapter9

/**
  * 函数式编程的理解
  * scala既是面向对象的编程语言、又是一门函数式编程语言。
  * scala中的函数就行变量一样可以定义，可以传递（高阶函数的参数）。
  * Scala鼓励面向表达式编程模型：也就是说每个语句都有返回值。如 if/else 表达式
  * @author yangshaojun
  * 2019/1/27 18:30
  * @version 1.0
  */
object Demo01 {
  def main(args: Array[String]): Unit = {

    /*1、面向表达式编程模型 if/else 、 try catch*/
    var a=10
    var b=5
    val bigger=if(a>b) a else b
    println("The bigger one is "+bigger)

    val str="23"
    val result=try{
      str.toInt
    }catch{
      case _=>0
    }
    println(result)
  }
}
