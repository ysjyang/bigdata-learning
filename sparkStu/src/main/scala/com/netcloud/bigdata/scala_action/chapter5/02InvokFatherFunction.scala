package com.netcloud.bigdata.scala_action.chapter5

/**
  * 调用父类的方法
  * 子类继承父类 然后可以使用 super的方式调用父类中的方法
  *
  * @author yangshaojun
  * 2018/12/31 15:18
  * @version 1.0
  */
object InvokFatherFunction {
  def main(args: Array[String]): Unit = {
    val c = new ChildrenClass()
    c.walkAndrun
  }
}

class FatherClass() {
  def walk: Unit = {
    println("I am Walking")
  }

  def run = {
    println("i am runnning")
  }
}

class ChildrenClass extends FatherClass {

  def walkAndrun: Unit = {
    super.walk
  }
}