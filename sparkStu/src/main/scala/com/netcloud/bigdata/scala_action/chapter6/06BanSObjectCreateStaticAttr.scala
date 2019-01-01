package com.netcloud.bigdata.scala_action.chapter6

/**
  * 用伴生对象创建静态成员
  * scala中没有static关键字，但是我们可以将静态成员（字段和方法）定义在伴生对象中
  *
  * Note:
  * 1)在同一个文件中定义对象和类，并赋予相同的名字
  * 2)在伴生对象中定义 "静态"成员
  * 3)在类内定义非静态成员
  * 4)伴生类和伴生对象中可以互相访问各自的私有成员变量
  * @author yangshaojun
  * 2019/1/1 17:53
  * @version 1.0
  */
object BanSObjectCreateStaticAttr {
  def main(args: Array[String]): Unit = {
    // Pizza对象可以像java类访问静态成员一样访问自己的成员
    println(Pizza.CRUST_TYPE_THIN)
    println(Pizza.getFool)

  }
}

class Pizza(var crustType: String) {
  override def toString: String = s"Crust type is " + crustType
}

object Pizza {
  val CRUST_TYPE_THIN = "thin"
  val CRUST_TYPE_THICK = "thick"

  def getFool = "fool"
}
