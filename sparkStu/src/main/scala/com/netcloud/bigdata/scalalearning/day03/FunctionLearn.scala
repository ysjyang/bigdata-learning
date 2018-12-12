package com.netcloud.bigdata.scalalearning.day03

/**
  * 方法和函数
  *
  * @author yangshaojun
  *         date  2018/12/11 22:35
  * @version 1.0
  */
/*
 *1)定义方法的基本格式是：def 方法名称（参数列表）：返回值类型 = {方法体}
 *  函数体中如果只有一行语句可以省略 {}
 */
object FunctionLearn {
  def main(args: Array[String]): Unit = {


    /**
      * 定义一个sayHello函数 返回年龄
      *
      * @param name 名称
      * @param age  年龄
      * @return
      */
    def sayHello(name: String, age: Int) = {
      if (age >= 18) {
        println("Hi " + name + " you are a bigger boy !")
        age
      } else {
        println("Hi " + name + " you are a little boy !")
        age
      }
    }

    //函数调用
    val age = sayHello("ysj", 20)
    println(age)

    /**
      * 方法的返回值只有一条语句 可以省略{}
      *
      * @return
      */
    def run() = "return one result!"

    val result = run() //函数的调用
    println(result) //打印结果
  }

}
