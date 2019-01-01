package com.netcloud.bigdata.scala_action.chapter6

/**
  * 对象的强制类型转换
  * 1) 将一个类的实例强制的转换为另一个类型
  * 2) scala中使用 asInstanceOf方法将一个实例转换为期望的类型
  * 3) asInstanceOf方法定义在Scala的Any类中，因此它对所有的对象有效
  * @author yangshaojun
  * 2019/1/1 16:00
  * @version 1.0
  */
object ObjectForceTransaction {
  def main(args: Array[String]): Unit = {

    /*
      * lookup方法返回值的对象被强制转换为Recognizer类的实例
      * val recoginzer=cm.lookup("recognizer").asInstanceOf[Recognizer]
      * 等同于java的如下的代码
      * Recognizer recognizer=(Recognizer) cm.lookup("recognizer")
      * */
    //Scala的asInstanceOf方法并不局限于此，也可以用来转换数字类型
    val a = 10
    println(a.getClass)
    val str = a.asInstanceOf[Long] //整型转为Long类型
    println(str.getClass)
    val b = str.asInstanceOf[Int] //字符串类型转为Int类型
    println(b.getClass)
    //但是无法将数字类型转为字符串类型。
    //a.asInstanceOf[String]   //无法将整型转为字符串类型
  }
}
