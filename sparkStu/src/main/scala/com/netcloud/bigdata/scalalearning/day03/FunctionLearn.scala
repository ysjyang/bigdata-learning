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
 *  方法可以省略返回值类型；可以省略return语句。
 *  如果方法没有返回值可以使用Unit标注，类似于java的void 一旦方法使用了Unit 即使方法中有最后一条语句，也不返回。
 *  一下两种情况不能省略返回值类型
 *  a) 使用return关键字时 返回值类型不能省略
 *  b) 函数中存在递归调用的时候，返回值类型不能省略
 *  通常情况下，返回值类型和return 我们都不会书写，而是使用自动类型推断的功能。
 *2) spark开发过程中常用的函数使用格式
 */
object FunctionLearn {
  def main(args: Array[String]): Unit = {


    /**
      * 定义一个sayHello函数 返回年龄
      * 使用自动类型推断功能 省略返回值类型和return
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

    /**
      * 自动类型推断功能有两个限制
      * 1、如果使用return关键字指定返回值 那么必须指定返回值的类型 否则编译错误
      *
      * @param a
      * @param b
      * @return
      */
    def function1(a: Int, b: Int) = {
      if (a > b) a else b
    }

    println(function1(5, 3))

    //  2、函数的方法体中存在着递归调用 必须指定返回值类型
    /* def function6(x: Int, y: Int)= {

        if (x % y == 0)
          y
        else
          function6(y, x % y)
      }*/
    /**
      * 函数的返回值为空
      *
      * @param a
      * @param b
      */
    def function2(a: Int, b: Int): Unit = {
      if (a > b) a else b
    }

    println(function2(3, 4)) //()

    /**
      * spark开发中常用格式： def 方法名称:(参数列表类型)=>返回值类型={方法体中书写具体的参数名称}
      *
      * @return
      */
    def function3(): (Int, Int, Int) => Int = {
      (x, y, z) => {
        x + y + z
      }
    }

    println(function3()(1, 2, 3)) //函数的调用

    /**
      * 柯里化函数
      * @param a 参数1
      * @param b 参数2
      * @return
      */
    def sum(a: Int)(b: Int) = {
      a + b
    }

    println(sum(1)(2))

    def add(a: Int) = (b: Int) => {
      a + b
    }
    println(add(1)(2))

    printString("hello","world!")
    defaultparam("ysj")
    defaultparam(age=25,sex="Man",name="ysj")
    face(5,5)//值函数
    face2(1,2,3)
  }

  /**
    * 可变参数的方法
    * @param args
    */
  def printString(args:String*)={
    var i=0
    for(arg <- args) {
      println("args's ["+i+"]"+"value is "+arg)
      i=i+1
    }

  }

  /**
    * 方法的参数的默认值和带参数名称调用
    * defaultparam(age=25,sex="Man",name="ysj")
    * @param name
    * @param age
    * @param sex
    */
  def defaultparam(name:String,age:Int=23,sex:String="Man")={
    println("name="+name+" "+" age="+" "+age+" sex="+" "+sex)
  }

  /**
    * 函数字面量（值函数）
    * 定义格式1
    */
  val face=(a:Int,b:Int)=>{
    println(a+b)
    a+b
  }
  /**
    * 定义格式2
    */
  val face2:(Int,Int,Int)=>Int={
    (x,y,z)=>{
      x+y+z
    }
  }

}
