package com.netcloud.bigdata.scala_action

/**
  * 将通用的代码放在包对象中
  * 在不引入类或者对象的前提下，让函数、字段和其他代码在包级别可用
  * 将所有类中共享的代码放在一个包中的包对象中。
  * @author yangshaojun
  * 2019/1/1 18:10
  * @version 1.0
  */
object PackageObject {
  def main(args: Array[String]): Unit = {

  }
}
/*创建一个包对象
* 这样就可以在com.netcloud.bigdata.scala_action.chapter6包中其他类，特质，以及对象中直接访问这些代码了
* 如:下面我们创建一个Test类 进行测试*/
package object chapter6{

  val MAGIC_NUM=42
  def echo(a:Any): Unit ={
    println(a)
  }
  object Margin extends Enumeration{
    type Margin=Value
    val TOP,BOTTOM,LEFT,RIGHT=Value
  }
}

