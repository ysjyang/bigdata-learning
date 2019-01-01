package com.netcloud.bigdata.scala_action.chapter6

/**
  * 判断对象所属的类
  * 在scala中不需要显示的指明定义的类型，但是有时候需要打印出对象的类/类型
  * 可以调用对象的getClass方法
  *
  * @author yangshaojun
  * 2019/1/1 17:31
  * @version 1.0
  */
object JudgeObjectBelongClass {
  def main(args: Array[String]): Unit = {
    val str="Spark"
    println(str.getClass) //class java.lang.String
  }
}

