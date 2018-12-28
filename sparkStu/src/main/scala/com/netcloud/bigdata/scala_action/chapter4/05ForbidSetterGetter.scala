package com.netcloud.bigdata.scala_action.chapter4

/**
  * 字段私有化 阻止生成setter和getter方法
  * @author yangshaojun
  * 2018/12/28 22:07
  * @version 1.0
  */
object ForbidSetterGetter {
  def main(args: Array[String]): Unit = {
    /*Scala中使用关键字lazy来定义惰性变量，实现延迟加载(懒加载)。
   *惰性变量只能是不可变变量，并且只有在调用惰性变量时，才会去实例化这个变量。
   * */
    def init(): String = {
      println("huangbo 666")
      return "huangbo"
    }

    lazy val name = init()
    println("666")
    println(name)
  }

}

class Stock {
  var delayPrice: Double = _
  /*将字段私有化 可以阻止生成setter 和getter方法*/
  private var currentPrice: Double = _
  /*对象私有字段 表示只有当前的对象才能访问该字段*/
  private[this] var price: Double = _

  /*将代码块中的返回值赋值给字段*/
  val text = {
    var lines = ""
    try {
      lines = "This is a Test code"
    } catch {
      case e: Exception => lines = "Error happended"
    }
    lines
  }

  import scala.xml._
  /*将函数返回值结果赋值给字段*/
  val books = XML.loadFile("c://books.xml")


}