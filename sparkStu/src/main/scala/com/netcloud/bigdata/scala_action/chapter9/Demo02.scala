package com.netcloud.bigdata.scala_action.chapter9

/**
  * 函数字面量（匿名函数） 作为高阶函数参数的输入或者赋值给某个变量。
  * @author yangshaojun
  * 2019/1/27 18:45
  * @version 1.0
  */
object Demo02 {
  def main(args: Array[String]): Unit = {
    /*1、匿名函数作为高阶函数的参数输入*/
    val list=List.range(1,10)//创建一个从1到10的集合
    val events=list.filter((i:Int) => i%2==0)
    println(events)
    /**
      * (i:Int) => i%2==0 这一段代码就是函数字面量（匿名函数）
      * 1） => 符号可以理解为一个转化器 将符号左边的参数列表，应用符号右边的算法生成新的结果。
      * 2）下面还可以对这个匿名函数进行简化操作
      *    a)scala编译器具有类型推断功能，可以推断出i是Int类型 所有Int声明可以去掉
      *       val events= list.filter((i)=> i%2==0)
      *       因为只有一个参数 参数i的小括号就是多余的了
      *       val events=list.filter(i => i%2==0)
      *    b)当参数在函数中只出现一次，Scala允许使用 "_"通配符替换变量名
      *       val events=list.filter(_%2==0)
      * 3) 匿名函数和foreach方法打印出列表中的每个元素
      *    a) list.foreach((i:Int) => println(i))
      *    b) list.foreach((i)=>println(i))
      *    c) list.foreach(println(_))
      *    d) list.foreach(println)//如果函数字面量只有一条语句，并且语句只接受一个参数 那么参数不需要特别指定，也不需要显示的声明。
      * */
    /*2、将函数字面量赋值给某个变量 然后作为参数进行变量传递 就像面向对象语言中传递String、Int、和其他类型的变量*/
    val double = (i: Int) => i * 2 //变量double是一个实例 和string、Int、和其他类型的实例没有什么区别
    /*但是在这种情况下，他是函数的实例，也被称为函数值，现在可以像调用方法一样调用double了*/
    println(double(2))
    /*除了上述 double(2) 的方式调用 还可以将其传入到任何具有相同参数签名的方法或者函数中*/
    val x=List.range(1,6)
    val result2=x.map(double)
    println(result2)

    /*3、声明函数字面量的两种方式
    * 1）隐式推断函数的返回值（常用）
    * 2）显示的指定函数返回值类型*/
    val f = (i: Int) => i % 2 == 0 //Scala编译器会自动的推断出返回值为Boolean类型值。不需要显式指明Boolean类型
    //如果函数比较复杂、或者需要显式的指明返回值的类型
    val f2: (Int) => Boolean = i => {
      i % 2 == 0
    }
    //简化
    val f3: Int => Boolean = i => {
      i % 2 == 0
    }
    val f4: Int => Boolean = i => i % 2 == 0
    val f5: Int => Boolean = _ % 2 == 0

    //    函数接收两个Int类型的参数，返回输入参数和的Int值
    val add=(x:Int,y:Int)=>{x+y}
    val add2=(x:Int,y:Int)=>x+y

    val add3:(Int,Int) =>Int =(x,y) =>{x+y}
    val add4:(Int,Int) =>Int =(x,y) => x+y
    /*上述例子中函数体的大括号不是必须的，但是如果函数体包括一个以上的表达式，一定要使用大括号*/
    val addAndDouble: (Int, Int) => Int = (a, b) => {
      val sum = a + b
      a * b
    }
   /*4、像匿名函数一样使用方法
   *    定义一个方法 作为高阶函数的输入*/
    def modMethod(i:Int) =i%2==0
    list.filter(modMethod)
    //这和定义一个函数字面量（匿名函数）然后指派给一个变量的过程相似达到的效果是一样的。
    //modMethod 是类的方法 而函数字面量是赋给变量的函数，编码层面不同，但是达到的效果相同。

  }
}
