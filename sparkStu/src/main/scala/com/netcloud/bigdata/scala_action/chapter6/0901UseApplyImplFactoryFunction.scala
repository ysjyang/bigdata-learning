package com.netcloud.bigdata.scala_action.chapter6

/**
  * 在scala中使用apply方法实现工厂方法
  * 在scala中实现工厂方法，让子类声明那种对象应该被创建。
  * @author yangshaojun
  * 2019/1/1 18:36
  * @version 1.0
  */
object UseApplyImplFactoryFunction {
  def main(args: Array[String]): Unit = {
    val dog = Animals("dog")
    dog.speak
    println(dog.getClass)
    val cat = Animals("cat")
    cat.speak
    println(cat.getClass)
  }
}

trait Animals {
  def speak
}

object Animals {

  private class Dog extends Animals {
    override def speak: Unit = {
      println("woof")
    }
  }

  private class Cat extends Animals {
    override def speak: Unit = {
      println("meow")
    }
  }
  // 工厂方法
  def apply(s: String): Animals = {
    if (s == "dog") new Dog
    else new Cat
  }
}
