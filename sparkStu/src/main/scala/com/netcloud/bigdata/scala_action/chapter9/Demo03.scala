package com.netcloud.bigdata.scala_action.chapter9

/**
  * 定义接收简单函数作为参数的方法（自定义高阶函数）
  * 1) 定义方法，包括期望接收的函数的参数的签名
  * 2) 定义满足这个签名的一个或者多个函数
  * 3) 将函数作为参数传递给方法
  *
  * Note：定义函数作为方法参数的常用语法
  *       paramName:(paramType(s) => returnType)
  *       executeFunction(f:String => Int)
  * @author yangshaojun
  * 2019/1/27 21:52
  * @version 1.0
  */
object Demo03 {
  def main(args: Array[String]): Unit = {

    //自定义一个方法 executeFunction 该方法接收一个函数作为参数 参数名称 callback()
    //接收的函数没有输入参数，也没有返回值
    def executeFunction(callback:()=>Unit)={
      callback()
    }
    //定义一个符合方法参数签名的函数
    val sayHello=() => println("hello")
    //将sayHello函数传入executeFunction方法
    executeFunction(sayHello)
    /*例子中：callback，没有任何的特殊含义，这仅仅是方法参数的名字 就像 def sayHello(i:Int)中的i*/

  }

}
