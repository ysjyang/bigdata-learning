package com.netcloud.bigdata.scala_action.chapter5

/**
  * 方法域
  * 方法的作用域 最严格到最开放的顺序
  * private[this] 、private、 protected、private[包名]、无修饰
  *
  * @author yangshaojun
  * 2018/12/31 15:14
  * @version 1.0
  */
object FunctionArea {

  def main(args: Array[String]): Unit = {

  }

}

class Fool {

  /*private[this]是最严格对的访问控制，把对象标记为对象的私有，这样的方法只能在当前对象的实例中可见
  * 同一个类的其他实例无法调用此方法。*/
  private[this] def isFool = true

  /*当前的私有方法可以对当前的实例和当前类的其他实例调用*/
  private def run = {
    "run a day "
  }

  /*标记为protected的方法只能被当前类或者子类可见*/
  protected def sayHello() {
    "I want say Hello !"
  }

  /*如果没有任何访问修饰符 方法是公开级别的*/
  def doFool(other: Fool): Unit = {
    //    other.isFool  编译报错：同一个类的其他实例无法调用此方法
    this.isFool
    other.run


  }
}

class Test001 {
  val fool = new Fool

}

class chilFool extends Fool {
  val chilfoo = new chilFool
  chilfoo.sayHello()
}

